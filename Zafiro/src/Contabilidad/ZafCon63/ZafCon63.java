/*
 * ZafCon63.java
 *
 * Created on 22 de Febrero del 2016, 14:31
 */
package Contabilidad.ZafCon63;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.DropMode;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafCon63 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable: tblDatEmp
    static final int INT_TBL_DAT_EMP_LIN = 0;                   //Linea (Plan Cuenta Empresa).
    static final int INT_TBL_DAT_EMP_COD_CTA = 1;               //Código de la cuenta (Plan Cuenta Empresa).
    static final int INT_TBL_DAT_EMP_NUM_CTA = 2;               //Número de la cuenta (Plan Cuenta Empresa).
    static final int INT_TBL_DAT_EMP_NOM_CTA = 3;               //Nombre de la cuenta (Plan Cuenta Empresa).
    static final int INT_TBL_DAT_EMP_EST_CAB = 4;               //Si es Cabecera (Plan Cuenta Empresa).
    static final int INT_TBL_DAT_EMP_EST_DET = 5;               //Si es Detalle (Plan Cuenta Empresa).

    //Constantes: Columnas del JTable: tblDatFor
    static final int INT_TBL_DAT_FOR_LIN = 0;                   //Linea (Plan Cuenta Formato).
    static final int INT_TBL_DAT_FOR_COD_CTA = 1;               //Código de la cuenta (Plan Cuenta Formato).
    static final int INT_TBL_DAT_FOR_NUM_CTA = 2;               //Número de la cuenta (Plan Cuenta Formato).
    static final int INT_TBL_DAT_FOR_NOM_CTA = 3;               //Nombre de la cuenta (Plan Cuenta Formato).
    static final int INT_TBL_DAT_FOR_EST_CAB = 4;               //Si es Cabecera (Plan Cuenta Formato).
    static final int INT_TBL_DAT_FOR_EST_DET = 5;               //Si es Detalle (Plan Cuenta Formato).
    
    private String strVersion=" v0.3 ";                         //Versión del Programa.                    
    
    //Variables: Generales
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCabEmp, objTblFilCabFor;
    private ZafTblMod objTblModEmp, objTblModFor;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader tblDatEmp.
    private ZafMouMotAdaFor objMouMotAdaFor;                    //ToolTipText en TableHeader tblDatFor.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafVenCon vcoFor;                                   //Ventana de consulta "Formato".
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private Connection con;
    private Vector vecDatEmp, vecDatFor, vecCab, vecReg;
    private boolean blnCon;  
    private String strSQL, strAux;                              //true: Continua la ejecución del hilo.
    
    //Variables: Varias.
    private boolean blnInsActReg=false;                         //Permite saber si inserta o actualiza el registro de cuentas.
    private int intTipVal=1;                                    //Tipo de Validacion. 1=Validacion para trasladar cuenta de un Jtable a otro. 2=Validación para Guardar.
    private String strCodFor, strFor;                           //Contenido del campo al obtener el foco.
    private String strIsCtaCab="";                              //Valida si es cuenta de cabecera.
    private String strIsCtaDet="";                              //Valida si es cuenta de detalle.
    private String strCodCtaPlaEmp="";                          //Codigo de Cuenta Formato Empresa, Permite validar si existe cuenta en el nuevo formato.                   
   
    /**
     * Estado de la Cuenta a Trasladar al nuevo formato de plan de cuentas. 
     * C=Cuenta Consultada Base Datos; E=Cuenta Trasladada para Eliminar, N=Nueva Cuenta a registrar.                    
     */
    private String strTipRegLin="";                             
                                       
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCon63(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
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
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenChk = new ZafTblCelRenChk();
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(4123)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4124)) 
            {
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4125)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar los JTables.
            configurarTblDatEmp();
            configurarTblDatFor();
            
            //Configurar las ZafVenCon.
            configurarVenConFor();
            
            //Carga Formato Predeterminado.
            cargaFormatoPredeterminado();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    //<editor-fold defaultstate="collapsed" desc="/* Configuraciones y ZafVenCon */">
    
    /**
     * Esta función configura el JTable "tblDatEmp".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatEmp()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatEmp = new Vector();    //Almacena los datos
            vecCab = new Vector(6);      //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_EMP_LIN,"");
            vecCab.add(INT_TBL_DAT_EMP_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_EMP_NUM_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_EMP_NOM_CTA,"Nombre");
            vecCab.add(INT_TBL_DAT_EMP_EST_CAB,"Cabecera");
            vecCab.add(INT_TBL_DAT_EMP_EST_DET,"Detalle");
             
            objTblModEmp=new ZafTblMod();
            objTblModEmp.setHeader(vecCab);
            tblDatEmp.setModel(objTblModEmp);
            //Configurar JTable: Establecer tipo de selección.
            tblDatEmp.setRowSelectionAllowed(true);
            tblDatEmp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatEmp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatEmp.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_EMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_EMP_COD_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_EMP_NUM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_EMP_NOM_CTA).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_DAT_EMP_EST_CAB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_EMP_EST_DET).setPreferredWidth(60);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_EMP_EST_CAB).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_EMP_EST_DET).setResizable(false);
            
           //Configurar JTable: Establecer el check en columnas
            tcmAux.getColumn(INT_TBL_DAT_EMP_EST_CAB).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_EMP_EST_DET).setCellRenderer(objTblCelRenChk);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatEmp);    
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatEmp.getTableHeader().setReorderingAllowed(false);            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatEmp.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatEmp);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabEmp=new ZafTblFilCab(tblDatEmp);
            tcmAux.getColumn(INT_TBL_DAT_EMP_LIN).setCellRenderer(objTblFilCabEmp);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDatEmp);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModEmp.addSystemHiddenColumn(INT_TBL_DAT_EMP_COD_CTA, tblDatEmp);
            
            //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
         
            tblDatEmp.setDragEnabled(true);
            tblDatEmp.setDropMode(DropMode.INSERT_ROWS);
            
            tblDatFor.setDropTarget(new DropTarget()
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    //Filas a Mover.
                    int intFilMovFor[];
                    intFilMovFor = tblDatFor.getSelectedRows();
                    
                    //Filas Seleccionadas.
                    int intFilSel[];
                    intFilSel = tblDatEmp.getSelectedRows();
                    
                    Point point = dtde.getLocation();
                    
                    int intRowEmpOrigen = tblDatEmp.getSelectedRow();
                    //System.out.println("intRowEmpOrigen: " + intRowEmpOrigen);
                    
                    int intRowForPtoDestino = tblDatFor.rowAtPoint(point);
                    //System.out.println("intRowForPtoDestino: " + intRowForPtoDestino);
                    
                    int intRowForDestino = tblDatFor.getSelectedRow();
                    //System.out.println("intRowForDestino: " + intRowForDestino);
                    
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    
                    boolean blnAdd=true;
                    boolean blnValCtaDet=false;
                    boolean blnValCtaExiFor=false;
                    
                    if (intRowEmpOrigen > -1 && intRowForDestino == -1 && intRowForPtoDestino>0) //TRASLADAR CUENTAS A OTRO JTABLE.
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
                        for(int j=0 ; j<intFilSel.length;j++)
                        {
                            if (intFilSel[j] > -1 && intRowForDestino == -1) 
                            {
                                intTipVal=2;
                                strIsCtaDet = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_EST_DET).toString();
                                strCodCtaPlaEmp = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_COD_CTA).toString();
                                strTipRegLin = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_LIN).toString();

                                if (strIsCtaDet.equals("true"))
                                {
                                    if (validaCuentaDetalle(strCodCtaPlaEmp, strTipRegLin, intTipVal)) 
                                    {
                                        System.out.println("DUMMY CODE!");
                                    }
                                    else 
                                    {
                                        blnAdd=false;
                                        blnValCtaExiFor=true;
                                    }
                                }
                                else 
                                {
                                    blnAdd=false;
                                    blnValCtaDet=true;
                                }
                            }
                        }

                        if (blnValCtaDet) {
                            mostrarMsgInf("Solo se pueden trasladar Cuentas de Detalle.");
                            limpiarJtableSelection();
                        }
                        if (blnValCtaExiFor) {
                            mostrarMsgInf("Alguna(s) de las cuenta(s) ya existe(n) en el Plan Cuenta Formato.");
                            limpiarJtableSelection();
                        }
                        
                        //</editor-fold>
                    
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        if(blnAdd)
                        {
                            //AGREGAR ELEMENTOS A TBLDATFOR
                            for (int j = 0; j < intFilSel.length; j++) 
                            {
                                if (intFilSel[j] > -1 && intRowForDestino == -1) // TENGA VALORES 
                                {
                                    strTipRegLin = objTblModEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_LIN).toString();

                                    vecReg = new Vector();
                                    vecReg.add(INT_TBL_DAT_FOR_LIN, strTipRegLin.equals("E") ? "C" : "N");
                                    vecReg.add(INT_TBL_DAT_FOR_COD_CTA, tblDatEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_COD_CTA));
                                    vecReg.add(INT_TBL_DAT_FOR_NUM_CTA, tblDatEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_NUM_CTA));
                                    vecReg.add(INT_TBL_DAT_FOR_NOM_CTA, tblDatEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_NOM_CTA));
                                    vecReg.add(INT_TBL_DAT_FOR_EST_CAB, tblDatEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_EST_CAB));
                                    vecReg.add(INT_TBL_DAT_FOR_EST_DET, tblDatEmp.getValueAt(intFilSel[j], INT_TBL_DAT_EMP_EST_DET));
                                    boolean blnBoo = false;
                                    for (int i = 0; i < objTblModFor.getRowCount(); i++)
                                    {
                                        if (i == intRowForPtoDestino && !blnBoo) 
                                        {
                                            vecDatFor.add(vecReg);
                                            i--;
                                            blnBoo = true;
                                        }
                                        else 
                                        {
                                            vecDatFor.add(objTblModFor.getData().get(i));
                                        }
                                    }
                                    objTblModFor.setData(vecDatFor);
                                    tblDatFor.setModel(objTblModFor);
                                    vecDatFor.clear();
                                    intRowForPtoDestino++;
                                }
                            }
                    
                            //ELIMINAR REGISTROS TBLDATEMP
                            boolean blnExi = false;

                            for (int i = 0; i < objTblModEmp.getRowCount(); i++)
                            {
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    if (i == intFilSel[j]) 
                                    {
                                        blnExi = true;
                                    }
                                }
                                if (!blnExi) 
                                {
                                    vecDatEmp.add(objTblModEmp.getData().get(i));
                                }
                                blnExi = false;

                            }
                            objTblModEmp.setData(vecDatEmp);
                            tblDatEmp.setModel(objTblModEmp);
                            vecDatEmp.clear();
                        }
                    //</editor-fold>
                    
                    }
                    else if (intRowForDestino > -1 && intRowEmpOrigen > -1 && intRowForPtoDestino > -1) 
                    {
                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
                        limpiarJtableSelection();
                    }
                    else if ( intRowForPtoDestino <=0 ) 
                    {
                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
                        limpiarJtableSelection();
                    }
                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMovFor.length; i++) 
                        {
                            if (intFilMovFor[i] <= 0)
                            {
                                blnMov=false;
                            }
                        }
                        if(blnMov)
                        {
                            //System.out.println("MOVER-FOR!!");
                            //System.out.println("intFilMovFor: " + intFilMovFor + " intRowForPtoDestino: " + intRowForPtoDestino);
                            //objTblModFor.moveRow(intRowForDestino, intRowForPtoDestino);
                            objTblModFor.moveRows(intFilMovFor, intRowForPtoDestino);
                        }
                        else
                        {
                            mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
                            limpiarJtableSelection();
                        }
                                
                    }
                }
            });
            //</editor-fold>            
            
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
     * Esta función configura el JTable "tblDatFor".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatFor()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFor = new Vector();    //Almacena los datos
            vecCab = new Vector(6);      //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_FOR_LIN,"");
            vecCab.add(INT_TBL_DAT_FOR_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_FOR_NUM_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_FOR_NOM_CTA,"Nombre");
            vecCab.add(INT_TBL_DAT_FOR_EST_CAB,"Cabecera");
            vecCab.add(INT_TBL_DAT_FOR_EST_DET,"Detalle");
             
            objTblModFor=new ZafTblMod();
            objTblModFor.setHeader(vecCab);
            tblDatFor.setModel(objTblModFor);
            //Configurar JTable: Establecer tipo de selección.
            tblDatFor.setRowSelectionAllowed(true);
            tblDatFor.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatFor);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFor.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFor.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FOR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FOR_COD_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FOR_NUM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_FOR_NOM_CTA).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_DAT_FOR_EST_CAB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FOR_EST_DET).setPreferredWidth(60);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_FOR_EST_CAB).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_FOR_EST_DET).setResizable(false);
            
           //Configurar JTable: Establecer el check en columnas
            tcmAux.getColumn(INT_TBL_DAT_FOR_EST_CAB).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_FOR_EST_DET).setCellRenderer(objTblCelRenChk);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatFor);    
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFor.getTableHeader().setReorderingAllowed(false);  
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFor=new ZafMouMotAdaFor();
            tblDatFor.getTableHeader().addMouseMotionListener(objMouMotAdaFor);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFor);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFor=new ZafTblFilCab(tblDatFor);
            tcmAux.getColumn(INT_TBL_DAT_FOR_LIN).setCellRenderer(objTblFilCabFor);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDatFor);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFor.addSystemHiddenColumn(INT_TBL_DAT_FOR_COD_CTA, tblDatFor);
            
            //Habilitar Opción de Pegar.
            objTblPopMnu.setPegarEnabled(true);
            
            //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
            tblDatFor.setDragEnabled(true);
            tblDatFor.setDropMode(DropMode.INSERT_ROWS);
            
            tblDatEmp.setDropTarget(new DropTarget() 
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    //Filas a Mover.
                    int intFilMovEmp[];
                    intFilMovEmp = tblDatEmp.getSelectedRows();
                    
                    //Filas Seleccionadas.
                    int intFilSel[];
                    intFilSel = tblDatFor.getSelectedRows();
                    
                    Point point = dtde.getLocation();
                    
                    int intRowForOrigen = tblDatFor.getSelectedRow();                    
                    //System.out.println("intRowForOrigen: " + intRowForOrigen);
                    
                    int intRowEmpPtoDestino = tblDatEmp.rowAtPoint(point);
                    //System.out.println("intRowEmpPtoDestino: " + intRowEmpPtoDestino);
                    
                    int intRowEmpDestino = tblDatEmp.getSelectedRow();
                    //System.out.println("intRowEmpDestino: " + intRowEmpDestino);
                    
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    
                    boolean blnAdd=true;
                    boolean blnValCtaDet=false;

                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 && intRowEmpPtoDestino>0)
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
                        for(int j=0 ; j<intFilSel.length;j++)
                        {
                            if (intFilSel[j] > -1 && intRowEmpDestino == -1) 
                            {
                                strIsCtaCab = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_CAB).toString();
                                strTipRegLin = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_LIN).toString();

                                if (strIsCtaCab.equals("false")) 
                                {
                                    System.out.println("DUMMY CODE!");
                                }
                                else 
                                {
                                    blnAdd=false;
                                    blnValCtaDet=true;
                                }
                            }
                        }
                        if (blnValCtaDet) {
                            mostrarMsgInf("Solo se pueden trasladar Cuentas de Detalle.");
                            limpiarJtableSelection();
                        }
                        
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        if(blnAdd)
                        {
                            //AGREGAR ELEMENTOS A TBLDATEMP
                            for (int j = 0; j < intFilSel.length; j++) 
                            {
                                if (intFilSel[j] > -1 && intRowEmpDestino == -1) // TENGA VALORES 
                                {
                                    strTipRegLin = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_LIN).toString();

                                    vecReg = new Vector();
                                    vecReg.add(INT_TBL_DAT_EMP_LIN, strTipRegLin.equals("C") ? "E" : "N");
                                    vecReg.add(INT_TBL_DAT_EMP_COD_CTA, tblDatFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_COD_CTA));
                                    vecReg.add(INT_TBL_DAT_EMP_NUM_CTA, tblDatFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_NUM_CTA));
                                    vecReg.add(INT_TBL_DAT_EMP_NOM_CTA, tblDatFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_NOM_CTA));
                                    vecReg.add(INT_TBL_DAT_EMP_EST_CAB, tblDatFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_CAB));
                                    vecReg.add(INT_TBL_DAT_EMP_EST_DET, tblDatFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_DET));
                                    boolean blnBoo = false;
                                    for (int i = 0; i < objTblModEmp.getRowCount(); i++)
                                    {
                                        if (i == intRowEmpPtoDestino && !blnBoo) 
                                        {
                                            vecDatEmp.add(vecReg);
                                            i--;
                                            blnBoo = true;
                                        }
                                        else 
                                        {
                                            vecDatEmp.add(objTblModEmp.getData().get(i));
                                        }
                                    }
                                    objTblModEmp.setData(vecDatEmp);
                                    tblDatEmp.setModel(objTblModEmp);
                                    vecDatEmp.clear();
                                    intRowEmpPtoDestino++;
                                }
                            }
                    
                            //ELIMINAR REGISTROS TBLDATEMP
                            boolean blnExi = false;

                            for (int i = 0; i < objTblModFor.getRowCount(); i++)
                            {
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    if (i == intFilSel[j]) 
                                    {
                                        blnExi = true;
                                    }
                                }
                                if (!blnExi) 
                                {
                                    vecDatFor.add(objTblModFor.getData().get(i));
                                }
                                blnExi = false;

                            }
                            objTblModFor.setData(vecDatFor);
                            tblDatFor.setModel(objTblModFor);
                            vecDatFor.clear();
                        }
                    //</editor-fold>
                    }
                    else if (intRowEmpDestino > -1 && intRowEmpPtoDestino > -1 && intRowForOrigen > -1) 
                    {
                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
                        limpiarJtableSelection();
                    }
                    else if ( intRowEmpPtoDestino <=0 ) 
                    {
                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
                        limpiarJtableSelection();
                    }
                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMovEmp.length; i++) 
                        {
                            if (intFilMovEmp[i] <= 0)
                            {
                                blnMov=false;
                            }
                        }
                        if(blnMov)
                        {
                            //System.out.println("MOVER-EMP!!");
                            //System.out.println("intFilMovEmp: " + intFilMovEmp + " intRowEmp: " + intRowEmpPtoDestino);
                            //objTblModEmp.moveRow(intRowEmpDestino, intRowEmpPtoDestino);
                            objTblModEmp.moveRows(intFilMovEmp, intRowEmpPtoDestino);
                        }
                        else
                        {
                            mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
                            limpiarJtableSelection();
                        }
                    }    
                }        
            });          
            
            
            //</editor-fold>
            
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
     * ToolTips TblDatEmp.
     */
    private class ZafMouMotAda extends MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDatEmp.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_EMP_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_EMP_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_EMP_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_EMP_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_EMP_EST_CAB:
                    strMsg="Cuenta de cabecera";
                    break;
                case INT_TBL_DAT_EMP_EST_DET:
                    strMsg="Cuenta de detalle";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatEmp.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * ToolTips TblDatFor.
     */
    private class ZafMouMotAdaFor extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDatFor.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_DAT_FOR_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_FOR_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_FOR_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_FOR_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_FOR_EST_CAB:
                    strMsg="Cuenta de cabecera";
                    break;
                case INT_TBL_DAT_FOR_EST_DET:
                    strMsg="Cuenta de detalle";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatFor.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Configura Ventana de Consulta Formato de Cuentas.
     * Permite mostrar "Listado de formatos para el plan de cuentas".
     * @return 
     */
    private boolean configurarVenConFor() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_for");
            arlCam.add("a.tx_DesLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.For.");
            arlAli.add("Nombre");

            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300");
            
            //Armar la sentencia SQL.
            strSQL ="";   
            strSQL+=" SELECT a.co_for, a.tx_DesLar ";
            strSQL+=" FROM tbm_cabForPlaCta as a ";
            strSQL+=" WHERE a.st_reg='A' ";
            strSQL+=" AND a.co_Emp="+objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a.co_for ";
            
            //System.out.println("configurarVenConFor: " + strSQL);
   
            vcoFor = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de formatos para el plan de cuentas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoFor.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {      blnRes = false;        objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }    
    
     /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una busqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */    
    private boolean mostrarVenConFor(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoFor.setCampoBusqueda(1);
                    vcoFor.show();
                    if (vcoFor.getSelectedButton() == vcoFor.INT_BUT_ACE) 
                    {
                        txtCodFor.setText(vcoFor.getValueAt(1));
                        txtFor.setText(vcoFor.getValueAt(2));
                        limpiarJtableDatFor();
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoFor.buscar("a.co_for", txtCodFor.getText()))
                    {
                        txtCodFor.setText(vcoFor.getValueAt(1));
                        txtFor.setText(vcoFor.getValueAt(2));
                        limpiarJtableDatFor();
                    }
                    else 
                    {
                        vcoFor.setCampoBusqueda(0);
                        vcoFor.setCriterio1(11);
                        vcoFor.cargarDatos();
                        vcoFor.show();
                        if (vcoFor.getSelectedButton() == vcoFor.INT_BUT_ACE) 
                        {
                            txtCodFor.setText(vcoFor.getValueAt(1));
                            txtFor.setText(vcoFor.getValueAt(2));
                            limpiarJtableDatFor();
                        } 
                        else 
                        {
                            txtCodFor.setText(strCodFor);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoFor.buscar("a.tx_DesLar", txtFor.getText())) 
                    {
                        txtCodFor.setText(vcoFor.getValueAt(1));
                        txtFor.setText(vcoFor.getValueAt(2));
                        limpiarJtableDatFor();
                    } 
                    else 
                    {
                        vcoFor.setCampoBusqueda(1);
                        vcoFor.setCriterio1(11);
                        vcoFor.cargarDatos();
                        vcoFor.show();
                        if (vcoFor.getSelectedButton() == vcoFor.INT_BUT_ACE) 
                        {
                            txtCodFor.setText(vcoFor.getValueAt(1));
                            txtFor.setText(vcoFor.getValueAt(2));
                            limpiarJtableDatFor();
                        } 
                        else 
                        {
                            txtFor.setText(strFor);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
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
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        lblFor = new javax.swing.JLabel();
        txtCodFor = new javax.swing.JTextField();
        txtFor = new javax.swing.JTextField();
        butFor = new javax.swing.JButton();
        lblPlaCtaEmp = new javax.swing.JLabel();
        spnDatEmp = new javax.swing.JScrollPane();
        tblDatEmp = new javax.swing.JTable();
        lblPlaCtaFor = new javax.swing.JLabel();
        spnDatFor = new javax.swing.JScrollPane();
        tblDatFor = new javax.swing.JTable();
        lblMsgInf = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        spnFil.setName(""); // NOI18N
        spnFil.setPreferredSize(new java.awt.Dimension(950, 565));

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(1250, 565));
        panFil.setLayout(null);

        lblFor.setText("Formato:");
        panFil.add(lblFor);
        lblFor.setBounds(10, 20, 90, 14);

        txtCodFor.setBackground(objParSis.getColorCamposObligatorios());
        txtCodFor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForFocusLost(evt);
            }
        });
        txtCodFor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForActionPerformed(evt);
            }
        });
        panFil.add(txtCodFor);
        txtCodFor.setBounds(90, 20, 40, 20);

        txtFor.setBackground(objParSis.getColorCamposObligatorios());
        txtFor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtForFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtForFocusLost(evt);
            }
        });
        txtFor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForActionPerformed(evt);
            }
        });
        panFil.add(txtFor);
        txtFor.setBounds(130, 20, 470, 20);

        butFor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        butFor.setText("...");
        butFor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForActionPerformed(evt);
            }
        });
        panFil.add(butFor);
        butFor.setBounds(600, 20, 20, 20);

        lblPlaCtaEmp.setText("Plan de cuentas: Empresa");
        panFil.add(lblPlaCtaEmp);
        lblPlaCtaEmp.setBounds(10, 50, 410, 14);

        tblDatEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatEmp.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDatEmp.setViewportView(tblDatEmp);

        panFil.add(spnDatEmp);
        spnDatEmp.setBounds(10, 70, 600, 470);

        lblPlaCtaFor.setText("Plan de cuentas: Formato");
        panFil.add(lblPlaCtaFor);
        lblPlaCtaFor.setBounds(630, 50, 400, 14);

        tblDatFor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatFor.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDatFor.setViewportView(tblDatFor);

        panFil.add(spnDatFor);
        spnDatFor.setBounds(630, 70, 600, 470);

        lblMsgInf.setForeground(new java.awt.Color(0, 0, 255));
        lblMsgInf.setText("Trasladar las cuentas de detalle:   Plan de Cuentas: Empresa   <<<  A  >>>  Plan de Cuentas: *** Formato***");
        panFil.add(lblMsgInf);
        lblMsgInf.setBounds(10, 548, 740, 14);

        spnFil.setViewportView(panFil);
        panFil.getAccessibleContext().setAccessibleParent(spnFil);

        tabFrm.addTab("Filtro", spnFil);
        spnFil.getAccessibleContext().setAccessibleParent(tabFrm);

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

        setBounds(430, 180, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
    
    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
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
    }//GEN-LAST:event_butConActionPerformed

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

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (isCamVal()) 
        {
            if(validaExistaDetalleFormatoGuardar())
            {
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) 
                {
                    if (guardarDat())
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    else
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
            }
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butForActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForActionPerformed
        mostrarVenConFor(0);
    }//GEN-LAST:event_butForActionPerformed

    private void txtForActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForActionPerformed
        txtFor.transferFocus();
    }//GEN-LAST:event_txtForActionPerformed

    private void txtForFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForFocusLost
        if (!txtFor.getText().equalsIgnoreCase(strFor))
        {
            if (txtFor.getText().equals(""))
            {
                txtCodFor.setText("");
                txtFor.setText("");
            }
            else
            {
                mostrarVenConFor(2);
            }
        }
        else
        {
            txtFor.setText(strFor);
        }
    }//GEN-LAST:event_txtForFocusLost

    private void txtForFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForFocusGained
        strFor = txtFor.getText();
        txtFor.selectAll();
    }//GEN-LAST:event_txtForFocusGained

    private void txtCodForActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForActionPerformed
        txtCodFor.transferFocus();
    }//GEN-LAST:event_txtCodForActionPerformed

    private void txtCodForFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForFocusLost
        if (!txtCodFor.getText().equalsIgnoreCase(strCodFor))
        {
            if (txtCodFor.getText().equals(""))
            {
                txtCodFor.setText("");
                txtFor.setText("");
            }
            else
            {
                mostrarVenConFor(1);
            }
        }
        else
        {
            txtCodFor.setText(strCodFor);
        }
    }//GEN-LAST:event_txtCodForFocusLost

    private void txtCodForFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForFocusGained
        strCodFor=txtCodFor.getText();
        txtCodFor.selectAll();
    }//GEN-LAST:event_txtCodForFocusGained
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Funciones Generales */">
    
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butFor;
    private javax.swing.JButton butGua;
    private javax.swing.JLabel lblFor;
    private javax.swing.JLabel lblMsgInf;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPlaCtaEmp;
    private javax.swing.JLabel lblPlaCtaFor;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDatEmp;
    private javax.swing.JScrollPane spnDatFor;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatEmp;
    private javax.swing.JTable tblDatFor;
    private javax.swing.JTextField txtCodFor;
    private javax.swing.JTextField txtFor;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrá presentar mensajes informativos en un JLabel e ir incrementando
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
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if (!cargarDetReg()) 
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            objThrGUI = null;

            pgrSis.setValue(0);
            pgrSis.setIndeterminate(false);
        }
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {     
        if ((!(txtCodFor.getText().length() > 0)) || (!(txtFor.getText().length() > 0))) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe ingresar un formato de plan de cuentas y luego vuelva a intentarlo.</HTML>");
            txtCodFor.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Valida que existan cuentas en el nuevo formato antes de Guardar.
     * @return 
     */
    private boolean validaExistaDetalleFormatoGuardar() 
    {     
        if(tblDatFor.getRowCount()<=0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Deben existir cuentas en el nuevo formato de plan de cuentas para guardar datos.</HTML>");
            return false;
        }
        return true;
    }
  
    /**
     * Limpia el Jtable del Plan de Cuentas: Formato.
     * Esta función es utilizada cada vez que se elija un formato distinto al actual.
     */
    private void limpiarJtableDatFor()
    {
        vecDatFor.clear();
        objTblModFor.setData(vecDatFor);
        tblDatFor.setModel(objTblModFor);
        lblPlaCtaFor.setText("Plan de cuentas: Formato");
        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
    }
    
    /**
     * Limpia el Jtable de la selección actual.
     * Esta función es utilizada cada vez que se realice una validación se limpie la selección actual en los jtables.
     */
    private void limpiarJtableSelection()
    {
        tblDatEmp.clearSelection();
        tblDatFor.clearSelection();
    }
    
    /**
     * Carga Formato Predeterminado de Plan de Cuentas.
     * Actualmente carga el primer formato activo del plan de cuentas.
     */
    private void cargaFormatoPredeterminado()
    {
        txtCodFor.setText(getForPre().get(0));
        txtFor.setText(getForPre().get(1));
    }
    
    /**
     * Función que retorna los datos del formato predeterminado.
     * @return arlDatFor Arreglo que contiene el formato predeterminado.
     */
    private ArrayList<String> getForPre() 
    {
        ArrayList<String> arlDatFor = new ArrayList<String>();
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        try 
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();

                strSQL ="";
                strSQL+=" SELECT a.co_for, a.tx_DesLar FROM tbm_cabForPlaCta as a  ";
                strSQL+=" WHERE a.co_Emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.st_reg='A'" ;
                strSQL+=" ORDER BY a.co_for limit 1";
                //System.out.println("getForPre: " + strSQL);
                
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                   arlDatFor.add(rstLoc.getString("co_for"));
                   arlDatFor.add(rstLoc.getString("tx_DesLar"));
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);    }
        return arlDatFor;
    }
    

    /**
     * Función que retorna los datos de la cuenta.
     * Se utiliza para obtener el nivel y la naturaleza de la cuenta de detalle a insertar.
     * @param strCodFor Código de Formato de plan de cuentas.
     * @param strCodCta Código de Cuenta de Cabecera.
     * @return arlDatCta Arreglo que contiene los datos de la cuenta. 
     */
    private ArrayList<String> getDatCta(String strCodFor, String strCodCta) 
    {
        ArrayList<String> arlDatCta = new ArrayList<String>();
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        try 
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();

                strSQL ="";
                strSQL+=" SELECT ne_niv, tx_natCta, (substring(tx_codCta from 1 for 1 ) ) as tx_niv1";
                strSQL+=" FROM tbm_detForPlaCta ";
                strSQL+=" WHERE co_Emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_For=" + strCodFor;
                strSQL+=" AND co_Cta=" + strCodCta;
                strSQL+=" AND tx_tipCta='C'";
                
                //System.out.println("getDatCta: " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                   arlDatCta.add(rstLoc.getString("ne_niv"));
                   arlDatCta.add(rstLoc.getString("tx_natCta"));
                   arlDatCta.add(rstLoc.getString("tx_niv1"));
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception e) {    objUti.mostrarMsgErr_F1(this, e);    }
        return arlDatCta;
    }
    
    /**
     * Función que retorna el último código de cuenta disponible para la cuenta de detalle a insertar.
     * @param conLoc Conexión.
     * @param strCodFor Código de Formato de plan de cuentas.
     * @return 
     */
    private int getUltCodCta(java.sql.Connection conLoc, String strCodFor) 
    {
        int intUltNumCta = 0;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        try 
        {
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();

                strSQL =" SELECT CASE WHEN MAX (co_Cta+1) IS NULL THEN 1 ELSE MAX (co_Cta+1) END AS numCta ";
                strSQL+=" FROM tbm_detForPlaCta ";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() ;
                strSQL+=" AND co_for=" + strCodFor ;
                //System.out.println("getUltCodCta:"+strSQL);
                
                
                rstLoc = stmLoc.executeQuery(strSQL);
                
                if (rstLoc.next()) 
                {
                    intUltNumCta = rstLoc.getInt("numCta");
                }

                rstLoc.close();
                stmLoc.close();
                rstLoc = null;
                stmLoc = null;
            }
        } 
        catch (java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex); } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
        return intUltNumCta;
    }
    

    /**
     * Función que valida si existe cuenta de detalle en el plan de cuentas del formato seleccionado.
     * @param strCtaDet Código de cuenta de detalle de la tabla tbm_detForPlaCta, campo co_ctaDet.
     * @param strTipRegLin  C=Cuenta Consultada Base Datos; E=Cuenta Trasladada para Eliminar, N=Nueva Cuenta a registrar.                    
     * @param intTipVal Tipo de Validacion. 1=Validación para Guardar, 2=Validacion para trasladar cuenta de un Jtable a otro.
     * @return true: Si existe cuenta de detalle Registrada.
     * <BR>false: En el caso contrario.
     */
    private boolean validaCuentaDetalle(String strCtaDet, String strTipRegLin, int intTipVal) 
    {
        boolean blnRes = true;
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT * FROM tbm_detForPlaCta\n";
                strSQL+=" WHERE co_emp =" + objParSis.getCodigoEmpresa() + "\n";
                strSQL+=" AND co_for =" + txtCodFor.getText() + "\n";
                strSQL+=" AND co_ctaDet>0 and tx_tipCta='D' \n";
                strSQL+=" AND co_ctaDet =" + strCtaDet+ "\n";
                //System.out.println("validaCuentaDetalle:"+strSQL);
                
                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next()) 
                {
                    if (intTipVal == 1) //Guardar Cuenta.
                    {
                        blnRes = false;
                        //System.out.println("CUENTA DETALLE YA EXISTE!");
                    }
                    else if (intTipVal == 2) //Trasladar Cuenta.
                    {
                        if (!strTipRegLin.equals("E")) 
                        {
                            blnRes = false;
                            //System.out.println("CUENTA DETALLE YA EXISTE PARA PODER TRASLADAR!");
                        }
                        else 
                        {
                            //System.out.println("CUENTA VUELVE A TRASLADARSE!");
                        }
                    }
                }
            }
            conLoc.close();
            rstLoc.close();
            stmLoc.close();
            conLoc=null;
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    /**
     * Función que valida si existe registro de saldo en la cuenta antes de eliminar cuentas del nuevo formato.
     * @param strCodFor Código de Formato a actualizar.
     * @param strCtaDet  Código de cuenta de detalle de la tabla tbm_detForPlaCta, campo co_ctaDet.
     * @return true: NO existe saldo en la cuenta.
     * <BR>false: En el caso contrario.
     */
    private boolean validaSaldoCuenta(String strCodFor, String strCtaDet) 
    {
        boolean blnRes = true;
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                
                strSQL ="";
                strSQL+=" SELECT a.nd_SalCta FROM tbm_salCtaForPlaCta as a \n";
                strSQL+=" INNER JOIN tbm_DetForPlaCta as  b ON (a.co_Emp=b.co_Emp and a.co_for=b.co_for and a.co_cta=b.co_cta) ";
                strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + "\n";
                strSQL+=" AND a.co_for=" + strCodFor + "\n"; 
                //strSQL+=" AND a.nd_SalCta > 0.00 \n";
                strSQL+=" AND a.co_cta in ";
                strSQL+=" (select co_Cta from tbm_DetForPlaCta ";
                strSQL+="  where co_emp=" + objParSis.getCodigoEmpresa() ;
                strSQL+="  and co_for=" + strCodFor; 
                strSQL+="  and co_CtaDet="+ strCtaDet+")";
                
                //System.out.println("validaSaldoCuenta:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next()) 
                {
                    blnRes=false;
                }
            }
            conLoc.close();
            rstLoc.close();
            stmLoc.close();
            conLoc=null;
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
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
                cargarDetRegPlaCtaEmp(con);   // tblDatEmp 
                cargarDetRegPlaCtaFor(con);   // tblDatFor
                
                con.close();
                con=null;
                
               if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDatFor.getRowCount() + " registros en el nuevo formato.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatEmp.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                System.gc();         //Libera memoria del sistema.
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, e);        }
        catch (Exception e) {   blnRes = false;     objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de la tabla tblDatEmp de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegPlaCtaEmp(java.sql.Connection conLoc)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        int intNiv, c;
        String strAuxCab;
        
        try
        {
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();      
                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.tx_nom as NomEmp, a1.ne_niv ";
                strSQL+=" FROM tbm_plaCta AS a1 ";
                strSQL+=" INNER JOIN tbm_emp as a2 ON (a1.co_Emp=a2.co_emp) ";
                strSQL+=" WHERE a1.st_reg='A' AND a1.co_Emp<>"+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa();
                //strSQL+=" AND co_Cta NOT IN ";
                //strSQL+=" (select co_ctaDet from tbm_detForPlaCta ";
                //strSQL+="  where co_emp="+objParSis.getCodigoEmpresa();
                //strSQL+="  and co_for="+txtCodFor.getText();
                //strSQL+="  and tx_tipCta='D' )";
                strSQL+=" ORDER BY a1.tx_codCta ";

                //System.out.println("cargarDetPlaCtaEmp:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatEmp.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next())
                {
                    if (blnCon)
                    {
                        lblPlaCtaEmp.setText("Plan de cuentas: "+rstLoc.getString("NomEmp"));
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_EMP_LIN,"");
                        vecReg.add(INT_TBL_DAT_EMP_COD_CTA,rstLoc.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_EMP_NUM_CTA,rstLoc.getString("tx_codCta"));
                        
                        //Generar la tabulación para el campo "tx_desLar".
                        intNiv=rstLoc.getInt("ne_niv");
                        strAuxCab="";
                        for ( c=1; c<intNiv; c++) {     strAuxCab+="      ";           }
                        //vecReg.add(INT_TBL_DAT_EMP_NOM_CTA, (rstLoc.getString("tx_tipCta").equals("D")?"     "+ rstLoc.getString("tx_desLar"):rstLoc.getString("tx_desLar")) );
                        vecReg.add(INT_TBL_DAT_EMP_NOM_CTA, (strAuxCab + rstLoc.getString("tx_desLar")) );
                        
                        if(rstLoc.getString("tx_tipCta")!=null)   strAux=rstLoc.getString("tx_tipCta");
                        vecReg.add(INT_TBL_DAT_EMP_EST_CAB,((strAux.compareTo("C")==0)? Boolean.TRUE:Boolean.FALSE));
                        vecReg.add(INT_TBL_DAT_EMP_EST_DET,((strAux.compareTo("D")==0)? Boolean.TRUE:Boolean.FALSE));
                    
                        vecDatEmp.add(vecReg);
                    }
                }

                rstLoc.close();
                stmLoc.close();
                rstLoc=null;
                stmLoc=null;
                //Asignar vectores al modelo.
                objTblModEmp.setData(vecDatEmp);
                tblDatEmp.setModel(objTblModEmp);
                vecDatEmp.clear();
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, e);        }
        catch (Exception e) {   blnRes = false;     objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de la tabla tblDatFor de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegPlaCtaFor(java.sql.Connection conLoc)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc = null, stmLocAux=null;
        java.sql.ResultSet rstLoc = null, rstLocAux=null;
        int intNiv, c, d;
        String strAuxCab, strAuxDet;
        
        try
        {
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();      
                
                //Armar la sentencia SQL (Cuentas Cabeceras).
                strSQL ="";
                strSQL+=" SELECT  a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.tx_desLar as nomFor, a1.ne_niv, ";         
                strSQL+="        (substring(a1.tx_codCta from 1 for 1 ) || '.' || substring(a1.tx_codCta from 2 for 100 )) as tx_codCta2 ";     
                strSQL+=" FROM tbm_detForPlaCta  as a1 ";
                strSQL+=" INNER JOIN tbm_cabForPlaCta as a2 ON (a1.co_Emp=a2.co_emp and a1.co_for=a2.co_for) ";   
                strSQL+=" WHERE  a1.tx_tipCta='C' ";
                strSQL+=" AND a1.co_Emp<>"+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_for="+txtCodFor.getText();
                strSQL+=" ORDER BY tx_codCta2 ";
                //System.out.println("cargarDetRegPlaCtaFor.Cuentas Cabeceras:"+strSQL);
                
                rstLoc = stmLoc.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatFor.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next())
                {
                    lblPlaCtaFor.setText("Plan de cuentas: *** " + rstLoc.getString("nomFor") + " ***");
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_FOR_LIN, "C");
                    vecReg.add(INT_TBL_DAT_FOR_COD_CTA, rstLoc.getString("co_cta"));
                    vecReg.add(INT_TBL_DAT_FOR_NUM_CTA, rstLoc.getString("tx_codCta"));
                    
                    //Generar la tabulación para el campo "tx_desLar".
                    intNiv=rstLoc.getInt("ne_niv");
                    strAuxCab="";
                    for ( c=1; c<intNiv; c++) {     strAuxCab+="      ";          }
                    
                    //vecReg.add(INT_TBL_DAT_FOR_NOM_CTA, (rstLoc.getString("tx_tipCta").equals("D") ? "     " + rstLoc.getString("tx_desLar") : rstLoc.getString("tx_desLar")));
                    vecReg.add(INT_TBL_DAT_FOR_NOM_CTA, strAuxCab + rstLoc.getString("tx_desLar"));
                    if(rstLoc.getString("tx_tipCta")!=null)   strAux=rstLoc.getString("tx_tipCta");
                    vecReg.add(INT_TBL_DAT_FOR_EST_CAB,((strAux.compareTo("C")==0)? Boolean.TRUE:Boolean.FALSE));
                    vecReg.add(INT_TBL_DAT_FOR_EST_DET,((strAux.compareTo("D")==0)? Boolean.TRUE:Boolean.FALSE));
                
                    vecDatFor.add(vecReg);

                    //Armar la sentencia SQL (Cuentas Detalles).
                    stmLocAux = conLoc.createStatement();    
                    strSQL ="";
                    strSQL+=" SELECT co_ctaDet as co_cta, tx_codCta, tx_desLar, tx_tipCta, ne_niv ";
                    strSQL+=" FROM tbm_detForPlaCta ";    
                    strSQL+=" WHERE co_ctaDet>0 AND tx_tipCta='D' ";
                    strSQL+=" AND co_Emp<>"+objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND co_emp="+objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_for="+txtCodFor.getText();
                    strSQL+=" AND ne_pad="+rstLoc.getInt("co_cta");
                    strSQL+=" ORDER BY tx_codCta, tx_DesLar ";
                    //System.out.println("cargarDetRegPlaCtaFor.Cuentas Detalle:"+strSQL);
                    rstLocAux = stmLocAux.executeQuery(strSQL);

                    while (rstLocAux.next()) 
                    {
                        
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_FOR_LIN, "C");
                        vecReg.add(INT_TBL_DAT_FOR_COD_CTA, rstLocAux.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_FOR_NUM_CTA, rstLocAux.getString("tx_codCta"));
                        
                        //Generar la tabulación para el campo "tx_desLar".
                        strAuxDet="";
                        intNiv=rstLocAux.getInt("ne_niv");
                        for (d=1; d<intNiv; d++){    strAuxDet+="      ";       }
                        
                        //vecReg.add(INT_TBL_DAT_FOR_NOM_CTA, (rstLocAux.getString("tx_tipCta").equals("D") ? "     " + rstLocAux.getString("tx_desLar") : rstLocAux.getString("tx_desLar")));
                        vecReg.add(INT_TBL_DAT_FOR_NOM_CTA, strAuxDet + rstLocAux.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_FOR_EST_CAB, (rstLocAux.getString("tx_tipCta").equals("C") ? true : false));
                        vecReg.add(INT_TBL_DAT_FOR_EST_DET, (rstLocAux.getString("tx_tipCta").equals("D") ? true : false));
                        vecDatFor.add(vecReg);
                    }
                    rstLocAux.close();
                    stmLocAux.close();
                    rstLocAux = null;
                    stmLocAux = null;
                }
                rstLoc.close();
                stmLoc.close();
                rstLoc = null;
                stmLoc = null;
            
                //Asignar vectores al modelo.
                objTblModFor.setData(vecDatFor);
                tblDatFor.setModel(objTblModFor);
                vecDatFor.clear();
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, e);        }
        catch (Exception e) {   blnRes = false;     objUti.mostrarMsgErr_F1(this, e);    }

        return blnRes;
    }
    
    /**
     * Función que se encarga de realizar el proceso de guardar el nuevo formato de plan de cuentas.
     * @return true: Si se pudo realizar el proceso de guardado.
     * <BR> false: Caso contrario.
     */
    private boolean guardarDat()
    {
        boolean blnRes=false;
        Connection con=null;
        
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                if (guardarDatFormatos(con)) 
                {
                    con.commit();
                    blnRes = true;
                    cargarDetReg();
                }
                else
                {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } 
        catch (java.sql.SQLException e)  {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    /**
     * Función que lee la tabla tblDatFor para realizar el proceso de inserción o actualización del nuevo formato de plan de cuentas.
     * @param conLoc Conexión 
     * @return true: Si se pudo realizar el proceso de inserción o actualización.
     * <BR> false: Caso contrario.
     */
    private boolean guardarDatFormatos(java.sql.Connection conLoc) 
    {
        boolean blnRes = true;
        
        String  strNomCta="",strNumCta="", strNatCta="";
        String  strCodCtaDet="", strCodCtaPad="", strTxtNiv1="";
        int intCodCta=0, intNivCta=0;
        
        try 
        {
            if (conLoc != null) 
            {
                strCodFor  = txtCodFor.getText();
                
                System.gc();      //Libera memoria del sistema.

                for (int i = 0; i < tblDatFor.getRowCount(); i++) 
                {
                    if (tblDatFor.getValueAt(i, INT_TBL_DAT_FOR_EST_CAB).toString().compareTo("true") == 0) 
                    {
                        strCodCtaPad = objTblModFor.getValueAt(i, INT_TBL_DAT_FOR_COD_CTA).toString(); //Cuenta Padre (Cabecera)
                        intNivCta = Integer.parseInt(getDatCta(strCodFor, strCodCtaPad).get(0))+1;     //Nivel a asignar a la cuenta de detalle.
                        strNatCta =  getDatCta(strCodFor, strCodCtaPad).get(1);                        //Naturaleza de la cuenta de detalle.
                        strTxtNiv1 =  getDatCta(strCodFor, strCodCtaPad).get(2);                       //Nivel de Cuenta. 
                    }
                    
                    if (tblDatFor.getValueAt(i, INT_TBL_DAT_FOR_EST_DET).toString().compareTo("true") == 0) 
                    {
                        strTipRegLin = objTblModFor.getValueAt(i, INT_TBL_DAT_FOR_LIN).toString();
                        strCodCtaDet = objTblModFor.getValueAt(i, INT_TBL_DAT_FOR_COD_CTA).toString();
                        strNomCta = objTblModFor.getValueAt(i, INT_TBL_DAT_FOR_NOM_CTA).toString().trim();
                        strNumCta = objTblModFor.getValueAt(i, INT_TBL_DAT_FOR_NUM_CTA).toString();
                        intCodCta = getUltCodCta(conLoc, strCodFor);

                        //Valida si la cuenta existe o no para decidir si se inserta o actualiza la cuenta de detalle.
                        blnInsActReg = false;          
                        intTipVal=1;
                        if (!validaCuentaDetalle(strCodCtaDet, strTipRegLin, intTipVal)) 
                        {
                            blnInsActReg = true;
                        }
                        
                        //Si el registro existe actualiza datos.
                        if(blnInsActReg)
                        {
                            if(!actualizaDat(conLoc, strCodFor, strCodCtaDet, strCodCtaPad, intNivCta, strNatCta, strTxtNiv1 ))
                            {
                                blnRes = false;
                                break;
                            }
                        }
                        else
                        {
                            if(!ingresaDat(conLoc, strCodFor, intCodCta, strNomCta, strCodCtaPad, intNivCta, strNumCta, strNatCta, strTxtNiv1, strCodCtaDet ))
                            {
                                blnRes = false;
                                break;
                            }
                        }
                    }                   
                }
                
                //Elimina Cuentas Trasladadas a TblDatEmp.
                //eliminaCuentasTrasladadas(conLoc); //Juan Solicito que no se eliminen las cuentas de detalle por ningún motivo.
                
            }
        }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    /**
     * Actualiza las cuentas de detalle previamente insertadas en el nuevo formato de plan de cuentas.
     * Los campos a actualizarse son Cuenta Padre, Nivel de la cuenta, Naturaleza de la Cuenta.
     * @param conLoc Conexión
     * @param strCodFor Código de Formato a actualizar.
     * @param strCodCtaDet Código Cuenta de detalle en el formato de empresa.
     * @param strCodCtaPad Código Cuenta Padre.
     * @param intNivCta Nivel de cuenta a actualzar.
     * @param strNatCta Naturaleza de la cuenta a actualizar.
     * @return true: Si se actualizó registro.
     * <BR> false: Si la sentencia no se ejecuta.
     */
    private boolean actualizaDat(java.sql.Connection conLoc, String strCodFor, String strCodCtaDet,  String strCodCtaPad, int intNivCta,  String strNatCta, String strTxtNiv1) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc=null;
       
        try 
        {
            if (conLoc != null) 
            {
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
                strSQL ="";                
                strSQL+=" UPDATE tbm_detForPlaCta ";
                strSQL+=" SET ne_pad="+strCodCtaPad+", ";
                strSQL+=" ne_niv="+intNivCta+", ";
                strSQL+=" tx_natCta='"+strNatCta+"', ";
                strSQL+=" tx_niv1='"+strTxtNiv1+"'";
                strSQL+=" WHERE co_Emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND co_For="+strCodFor;
                strSQL+=" AND co_CtaDet="+strCodCtaDet;
                strSQL+=" ; ";
      
                //System.out.println("actualizaDat: " + strSQL);
           
                stmLoc.executeUpdate(strSQL);
                blnRes=true; 
            }
            stmLoc.close();
            stmLoc = null;
        }
        catch (java.sql.SQLException e)  {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    /**
     * Inserta las cuentas de detalle en el nuevo formato de plan de cuentas.
     * @param conLoc Conexión
     * @param strCodFor Código de Formato a insertar.
     * @param intCodCta Código de Cuenta generado en base al último código (co_cta) disponible en tbm_detForPlaCta.
     * @param strNomCta Nombre de cuenta a insertar.
     * @param strCodCtaPad Código de cuenta padre de la cuenta de detalle a insertar.
     * @param intNivCta Nivel de la cuenta de detalle a insertar.
     * @param strNumCta Número de cuenta de detalle a insertar.
     * @param strNatCta Naturaleza de cuenta de detalle a insertar.
     * @param strCodCtaDet Código Cuenta de detalle en el formato de empresa.
     * @return true: Si se pudo realizar la inserción de la cuenta de detalle.
     * <BR> false: Caso Contrario.
     */
    private boolean ingresaDat(java.sql.Connection conLoc, String strCodFor, int intCodCta, String strNomCta, String strCodCtaPad, int intNivCta, String strNumCta,  String strNatCta, String strTxtNiv1, String strCodCtaDet) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc=null;
       
        try 
        {
            if (conLoc != null) 
            {
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
                
                strSQL ="";                
                strSQL+=" INSERT INTO tbm_detForPlaCta ( ";
                strSQL+=" co_emp, co_for, co_cta, ";
                strSQL+=" tx_DesLar, ne_pad, ne_niv, ";
                strSQL+=" tx_codCta, tx_tipCta, tx_natCta, tx_niv1, co_ctaDet) ";
                strSQL+=" VALUES( ";
                strSQL+=" " +objParSis.getCodigoEmpresa()+ ", " +strCodFor+ ", " +intCodCta+ ", ";
                strSQL+=" '" +strNomCta+ "', " +strCodCtaPad+ ", " +intNivCta+ ", ";
                strSQL+=" '" +strNumCta+ "', 'D' , '" +strNatCta+ "', '" +strTxtNiv1+ "',"+strCodCtaDet+" ";
                strSQL+=" ) ; ";
                
                //System.out.println("ingresaDat: " + strSQL);
                
                stmLoc.executeUpdate(strSQL);
                blnRes=true; 
            }
            stmLoc.close();
            stmLoc = null;
        }
        catch (java.sql.SQLException e)  {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    /**
     * Elimina del nuevo formato las cuentas de detalle trasladadas al plan de cuentas de la empresa.
     * @param conLoc
     * @return true: Si se eliminan cuentas del nuevo formato.
     * <BR> false: En el caso contrario.
     */
    private boolean eliminaCuentasTrasladadas(java.sql.Connection conLoc) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc=null;
        String strCtaDet="";
       
        try 
        {
            if (conLoc != null) 
            {
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
                
                strCodFor  = txtCodFor.getText();
                
                for (int i = 0; i < tblDatEmp.getRowCount(); i++) 
                {
                    strTipRegLin=objTblModEmp.getValueAt(i, INT_TBL_DAT_EMP_LIN).toString();
                    if (strTipRegLin.equals("E")) 
                    {
                        strCtaDet = objTblModEmp.getValueAt(i, INT_TBL_DAT_EMP_COD_CTA).toString();
                        if(validaSaldoCuenta(strCodFor, strCtaDet))
                        {
                            strSQL ="";                
                            strSQL+=" DELETE FROM tbm_DetForPlaCta  ";
                            strSQL+=" WHERE co_Emp="+objParSis.getCodigoEmpresa();
                            strSQL+=" AND co_for="+strCodFor;
                            strSQL+=" AND co_CtaDet="+strCtaDet+";";

                            //System.out.println("eliminaCuentasTrasladadas: " + strSQL);
                            stmLoc.executeUpdate(strSQL);
                            blnRes=true; 
                        }
                    }
                }
            }
            stmLoc.close();
            stmLoc = null;
        }
        catch (java.sql.SQLException e)  {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    

}

