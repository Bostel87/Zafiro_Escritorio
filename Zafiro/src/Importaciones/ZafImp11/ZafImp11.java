/*
 * ZafImp11.java  
 *
 * Created on Ago 25, 2017, 11:00:03 AM
 */
package Importaciones.ZafImp11;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafImp.ZafAjuInv;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JFrame;

/**
 *
 * @author Rosa Zambrano
 */
public class ZafImp11 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Jtable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                               //Línea
    private static final int INT_TBL_DAT_CODSEG = 1;                            //Código de Seguimiento
    private static final int INT_TBL_DAT_CODREG = 2;                            //Código de Registro
    private static final int INT_TBL_DAT_FECDOCPED = 3;                         //Fecha del pedido.
    private static final int INT_TBL_DAT_NUMPED = 4;                            //Numero de pedido.
    private static final int INT_TBL_DAT_CDI=5;                                 //Columna dinámica: Primera Columna
    
    //Constantes de indices para columnas por grupo (tabla).
    private static final int INT_IND_COL_COD_EMP=0;                             //Columna: Código de Empresa
    private static final int INT_IND_COL_COD_LOC=1;                             //Columna: Código de Local
    private static final int INT_IND_COL_COD_TIP_DOC=2;                         //Columna: Código de Tipo de Documento
    private static final int INT_IND_COL_COD_DOC=3;                             //Columna: Código de Documento.
    private static final int INT_IND_COL_DES_COR_TIPDOC=4;                      //Columna: Descripcion corta tipo de documento.
    private static final int INT_IND_COL_DES_LAR_TIPDOC=5;                      //Columna: Descripcion larga tipo de documento.
    private static final int INT_IND_COL_NUM_DOC=6;                             //Columna: Número de Documento.
    private static final int INT_IND_COL_BTN=7;                                 //Columna: Botón 
    
    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_NUM_TOT_CES=5;                                 //Número total de columnas estáticas.
    private static final int INT_NUM_CDI_GRP=8;                                 //Número de columnas dinámicas por grupo.
    private static final int INT_FAC_CDI=1;                                     //Factor para calcular total de columnas dinámicas. 
    
    //Constantes de indices para grupos (Tablas).
    //En esta parte se detallan los indices de los grupos que se van a mostrar en el seguimiento.
    //private static final int INT_IND_GRP_NOT_PED_PRV=0;                       //Grupo: Tabla Notas de Pedido Previa.
    private static final int INT_IND_GRP_NOT_PED=0;                             //Grupo: Tabla Notas de Pedido
    private static final int INT_IND_GRP_PED_EMB=1;                             //Grupo: Tabla Pedido Embarcado
    private static final int INT_IND_GRP_MOV_INV=2;                             //Grupo: Tabla Movimientos de Inventario
    private static final int INT_IND_GRP_SOL_TRA=3;                             //Grupo: Tabla Solicitudes de transferencia
    private static final int INT_IND_GRP_CAB_DIA=4;                             //Grupo: Tabla Asientos Diario.
    
    //ArrayList: Datos Tabla Dinamica
    private ArrayList arlDatGrpTblDin, arlRegGrpTblDin;
    private static final int INT_ARL_GRP_COD_TBL=0;
    private static final int INT_ARL_GRP_NOM_TBL=1;
    private static final int INT_ARL_GRP_NOM_GRP=2;
    
    //Constantes: Códigos de Menú.
    private static final int INT_COD_MNU_INGIMP=2889;                           //Ingresos por Importacion.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblMod;
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblEdi objTblEdi;                                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLblColEst, objTblCelRenLblColDin;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiBut;
    private ZafThreadGUI objThrGUI;
    private ZafTblPopMnu objTblPopMnu;
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafImp objImp;
    private ZafAjuInv objAjuInv;   
    private ZafVenCon vcoImp;                                                   //Ventana de consulta "Empresa Importadora".
    private ZafVenCon vcoPed;                                                   //Ventana de consulta "Pedidos".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private Color colFonColNar = new Color(255, 221, 187); //Color Naranja.
    private Color colFonColVer = new Color(228, 228, 203); //Color Verde Agua.
            
    private Vector vecDat, vecCab, vecReg, vecAux /*, vecEstReg*/;
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnVenEmeCon=false;                                         //Ventana Emergente de Consulta. true: Consulta el seguimiento de acuerdo a lo indicado.
        
    private int intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp;
    private int intNumTotColDin, intColIni, intColFin, intNumColEst; 
    
    private String strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="";   //Utilizadas para la pk del seguimiento.
    private String strCodImp, strNomImp;                                        //Contenido del campo al obtener el foco.
    private String strCodPed, strPedImp, strNumDocPed;                          //Contenido del campo al obtener el foco.
    private String strSQL="", strAux="", strDat="";
    
    private String strVersion = " v0.1.2";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp11(ZafParSis obj) 
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
    
    /** Constructor para Botón Consultar Seguimiento de Movimientos de importaciones. */
    public ZafImp11(HashMap map) 
    {
        try 
        {
            blnVenEmeCon=true;
            
            ZafParSis obj  = (ZafParSis)map.get("objParSis");
            this.strCodEmp  = (String)map.get("strCodEmp");
            this.strCodLoc  = (String)map.get("strCodLoc");
            this.strCodTipDoc  = (String)map.get("strCodTipDoc");
            this.strCodDoc     = (String)map.get("strCodDoc");
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            cargarDetReg();
            tabFrm.setSelectedIndex(1);
            tabFrm.removeTabAt(0); 
            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText("Seguimiento de movimientos de importaciones...");
        } 
        catch (CloneNotSupportedException e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
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
            objUti = new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objTblCelEdiBut = new ZafTblCelEdiButGen();
            objTblCelRenBut = new ZafTblCelRenBut();
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3230))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3231))
            {
                butCer.setVisible(false);
            }
                
            //Configurar ZafSelFec
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            //objSelFec.setTitulo("Rango de fecha");
            panFecDoc.add(objSelFec);
            objSelFec.setBounds(10, 20, 472, 72);
            
            configurarFechaDesde(); //Consulta 1 mes hacia atras
            //configurarFechaDesdeActual();
            
            //Configurar objetos.
            txtCodPed.setVisible(false);

            configurarTblDat();
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
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
    
    /* Configurar Fecha Desde el mismo día. */
    private void configurarFechaDesdeActual()
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
            objFec.set(Calendar.MONTH, fecDoc[1] );
            objFec.set(Calendar.YEAR, fecDoc[2]);
        }
        Calendar objFecPagActual = Calendar.getInstance();
        objFecPagActual.setTime(objFec.getTime());

        dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
        dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH));
        dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
        String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
        java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
        objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
    }
    
        /**
     * Esta función configura el JTable "tblDat".
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
            vecCab = new Vector(INT_NUM_TOT_CES);   //Almacena las cabeceras
            //vecCab = new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_CODREG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_FECDOCPED,"Fec.Ped.");
            vecCab.add(INT_TBL_DAT_NUMPED,"Núm.Ped.");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CODREG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCPED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUMPED).setPreferredWidth(70);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODSEG, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblColEst = new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setCellRenderer(objTblCelRenLblColEst);
            tcmAux.getColumn(INT_TBL_DAT_CODREG).setCellRenderer(objTblCelRenLblColEst);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCPED).setCellRenderer(objTblCelRenLblColEst);
            tcmAux.getColumn(INT_TBL_DAT_NUMPED).setCellRenderer(objTblCelRenLblColEst);
            
            //Combina los colores de acuerdo al codigo de seguimiento.
            objTblCelRenLblColEst.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    objTblCelRenLblColEst.setForeground(Color.BLACK);
                    int intCell=objTblCelRenLblColEst.getRowRender();
                    int intNumSeg=Integer.parseInt(tblDat.getValueAt(intCell, INT_TBL_DAT_CODSEG).toString());
                  
                    if (intNumSeg%2!=0) 
                        objTblCelRenLblColEst.setBackground(colFonColNar);
                    else 
                        objTblCelRenLblColEst.setBackground(colFonColVer);
                }
            });
             
            //Combina los colores de acuerdo al codigo de seguimiento.
            //for(int i=0; i<objTblMod.getRowCount();i++)
            //{
            //    int intCodReg=Integer.parseInt(tblDat.getValueAt(i, INT_TBL_DAT_CODREG).toString());
            //    if( (intCodReg==1) && (objTblCelRenLblColEst.getBackground()==colFonColVer))  
            //    {
            //        objTblCelRenLblColEst.setBackground(colFonColNar);
            //        colFonColNuev=objTblCelRenLblColEst.getBackground();
            //    }
            //    else if( (intCodReg==1)  )  
            //    {
            //        objTblCelRenLblColEst.setBackground(colFonColVer);
            //        colFonColNuev=objTblCelRenLblColEst.getBackground();
            //    }
            //    else{
            //        objTblCelRenLblColEst.setBackground(colFonColNuev);
            //        if(colFonColNuev==colFonColNar)
            //            colFonColNuev=colFonColVer;
            //        else
            //            colFonColNuev=colFonColNar;
            //    }
            //}
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);  
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);    
            
	    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEst=objTblMod.getColumnCount();
        }
        catch (Exception e)
        {
            blnRes = false;
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
            int intColSel=-1;
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            if (intCol>INT_NUM_TOT_CES){
                intCol=(intCol-INT_NUM_TOT_CES)%INT_FAC_CDI+INT_NUM_TOT_CES;
            }
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código de seguimiento";
                    break;
                case INT_TBL_DAT_CODREG:
                    strMsg="Código de registro";
                    break;
                case INT_TBL_DAT_FECDOCPED:
                    strMsg="Fecha de documento del pedido.";
                    break;
                case INT_TBL_DAT_NUMPED:
                    strMsg="Número de Pedido";
                    break;                    
                case INT_TBL_DAT_CDI:
                       
                    for(int i=0; i<(arlDatGrpTblDin.size()); i++) //Cantidad de tablas
                    {
                        for(int j=0; j<(INT_NUM_CDI_GRP); j++) //Número de columnas dinámicas por tabla.
                        {
                            intColSel=tblDat.columnAtPoint(evt.getPoint());
                            int intIndColGrp =(intColIni+j+(i*INT_NUM_CDI_GRP)); //Obtengo Indice de la Columna.
                            
                            if(intColSel == intIndColGrp && j==INT_IND_COL_COD_EMP)  {                    
                                strMsg="Código de Empresa";
                            }
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_COD_LOC)  {   
                                strMsg="Código de Local";
                            } 
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_COD_TIP_DOC)  {   
                                strMsg="Código de Tipo de Documento";
                            }
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_COD_DOC)  {   
                                strMsg="Código de Documento";
                            }
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_DES_COR_TIPDOC)  {   
                                strMsg="Descripción corta del Tipo de Documento";
                            }
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_DES_LAR_TIPDOC)  {   
                                strMsg="Descripción larga del Tipo de Documento";
                            }
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_NUM_DOC)  {   
                                strMsg="Número de Documento";
                            }  
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_BTN)  {   
                                strMsg="Botón de Documento";
                            }        
                        }
                    }                                         
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }  

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Importadores".
     */
    private boolean configurarVenConEmpImp()
    {
        boolean blnRes=true;
        try
        {
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
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_emp";
            vcoImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc ";
            //strSQL+=" WHERE a1.st_Reg='A' AND ";
            strSQL+=" WHERE a1.co_tipDoc in(select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+INT_COD_MNU_INGIMP+")";
            strSQL+=" AND a1.co_mnu="+INT_COD_MNU_INGIMP;
            strSQL+=" ORDER BY a1.fe_doc DESC";
            
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
        
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarEmpImp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoImp.setCampoBusqueda(2);
                    vcoImp.setVisible(true);
                    if (vcoImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoImp.buscar("a1.co_emp", txtCodImp.getText()))
                    {
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                    }
                    else
                    {
                        vcoImp.setCampoBusqueda(0);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.setVisible(true);
                        if (vcoImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                        }
                        else
                        {
                            txtCodImp.setText(strCodImp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoImp.buscar("a1.tx_nom", txtNomImp.getText()))
                    {
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                    }
                    else
                    {
                        vcoImp.setCampoBusqueda(1);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.setVisible(true);
                        if (vcoImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                        }
                        else
                        {
                            txtNomImp.setText(strNomImp);
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
               
                case 2: //Búsqueda directa por "Número Documento del Pedido".
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
                            txtNumDocIngImp.setText(strNumDocPed); 
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Pedido".
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
   
    private void mostrarMovInv(String CodEmp, String CodLoc, String CodTipDoc, String CodDoc) 
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strClaBus = "", strCodMnu = "";
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL = "";
                strSQL+=" SELECT a1.tx_Acc, a2.co_tipDoc, a1.co_mnu ";
                strSQL+=" FROM tbm_mnusis as a1 \n";
                strSQL+=" INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_mnu=a2.co_mnu)\n";
                strSQL+=" RIGHT JOIN tbr_tipDocDetPrg as a3 ON (a2.co_tipDoc=a3.co_tipDoc AND a3.co_mnu=" + objParSis.getCodigoMenu() + ") ";
                strSQL+=" WHERE a2.co_mnu in (14, 45, 779, 1918, 327, 3932, "+INT_COD_MNU_INGIMP+", "+objImp.INT_COD_MNU_PRG_AJU_INV+")  ";
                strSQL+=" AND a3.co_Emp= " + objParSis.getCodigoEmpresa();
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal(); 
                strSQL+=" AND a3.co_tipdoc= " + CodTipDoc;
                strSQL+=" GROUP BY a1.tx_Acc, a2.co_tipDoc, a1.co_mnu";
                System.out.println("mostrarMovInv: "+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                while (rstLoc.next()) 
                {
                    strClaBus = rstLoc.getString("tx_acc");
                    strCodMnu = rstLoc.getString("co_mnu");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
            }
            
            if(strCodMnu.equals(""+INT_COD_MNU_INGIMP+"")) {
                mostrarIngImp(Integer.valueOf(CodEmp), Integer.valueOf(CodLoc), Integer.valueOf(CodTipDoc), Integer.valueOf(CodDoc));
            }
            else if(strCodMnu.equals(""+objImp.INT_COD_MNU_PRG_AJU_INV+""))   {
                mostrarAjuInv(Integer.valueOf(CodEmp), Integer.valueOf(CodLoc), Integer.valueOf(CodTipDoc), Integer.valueOf(CodDoc));
            }
            else {
                invocarClase(strClaBus, CodEmp, CodLoc, CodTipDoc, CodDoc, Integer.valueOf(strCodMnu));
            }
        }
        catch (Exception Evt) 
        {
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }
        
    private boolean invocarClase(String clase, String CodEmp, String CodLoc, String CodTipDoc, String CodDoc, int CodMnu) 
    {
        boolean blnRes = true;
        java.util.HashMap map = new java.util.HashMap();
        //HashMap<Object,String> map = new HashMap<Object, String>();
        try
        {
            //Agregar parametros al HashMap para enviarlos al constructor respectivo
            map.put("objParSis", objParSis);
            map.put("strCodEmp", CodEmp);
            map.put("strCodLoc", CodLoc);
            map.put("strCodTipDoc", CodTipDoc);
            map.put("strCodDoc", CodDoc);
            map.put("intCodMnu", CodMnu);

            //Obtener el constructor de la clase que se va a invocar.
            Class objVen = Class.forName(clase);
            Class objCla[] = new Class[1];
            objCla[0] = map.getClass();

            java.lang.reflect.Constructor objCon = objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[] = new Object[1];
            objObj[0] = map;
            javax.swing.JInternalFrame ifrVen;
            ifrVen = (javax.swing.JInternalFrame) objCon.newInstance(objObj);
            this.getParent().add(ifrVen, javax.swing.JLayeredPane.DEFAULT_LAYER);

            ifrVen.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                }

                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                    System.gc();
                }

                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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
            ifrVen.show();
        } 
        catch (ClassNotFoundException e)   {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (NoSuchMethodException e)    {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    }
        catch (SecurityException e)        {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (InstantiationException e)   {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (IllegalAccessException e)   {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (IllegalArgumentException e) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (java.lang.reflect.InvocationTargetException e) {     blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    private boolean mostrarNotPed(String CodEmp, String CodLoc, String CodTipDoc, String CodDoc) 
    {
        boolean blnRes=true;
        try 
        {
            Compras.ZafCom75.ZafCom75 obj = new Compras.ZafCom75.ZafCom75(objParSis, Integer.parseInt(CodLoc), Integer.parseInt(CodTipDoc), Integer.parseInt(CodDoc));
            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            obj=null;
        } 
        catch (Exception Evt) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, Evt);     }
        return blnRes;
    }
     
    private boolean mostrarPedEmb(String CodEmp, String CodLoc, String CodTipDoc, String CodDoc) 
    {
        boolean blnRes=true;
        try 
        {
            Compras.ZafCom76.ZafCom76 obj = new Compras.ZafCom76.ZafCom76(objParSis, Integer.valueOf(CodTipDoc), Integer.valueOf(CodDoc));
            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            obj=null;
        } 
        catch (Exception Evt) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, Evt);     }
        return blnRes;
    }
    
    private boolean mostrarIngImp(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        boolean blnRes=true;
        try 
        {
            Compras.ZafCom77.ZafCom77 obj = new Compras.ZafCom77.ZafCom77(objParSis, CodEmp, CodLoc, CodTipDoc, CodDoc);
            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            obj=null;
        } 
        catch (Exception Evt) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, Evt);     }
        return blnRes;
    }
    
    private boolean mostrarSolTra(String CodEmp, String CodLoc, String CodTipDoc, String CodDoc) 
    {
        boolean blnRes=true;
        try 
        {
            Importaciones.ZafImp21.ZafImp21 obj = new Importaciones.ZafImp21.ZafImp21(objParSis, CodEmp, CodLoc, CodTipDoc, CodDoc);
            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            obj=null;
        } 
        catch (Exception Evt) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, Evt);     }
        return blnRes;
    }
    
    private boolean mostrarAsiDia(String CodEmp, String CodLoc, String CodTipDoc, String CodDoc) 
    {
        boolean blnRes=true;
        try 
        {
            Contabilidad.ZafCon02.ZafCon02 obj = new Contabilidad.ZafCon02.ZafCon02(objParSis, Integer.parseInt(CodEmp), Integer.parseInt(CodLoc), Integer.parseInt(CodTipDoc), Integer.parseInt(CodDoc), 327);
            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            obj=null;
        } 
        catch (Exception Evt) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, Evt);     }
        return blnRes;
    }    
        
    private boolean mostrarAjuInv(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        boolean blnRes=true;
        try
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                //Se carga el programa Ajuste de Inventario
                objAjuInv = new ZafAjuInv( javax.swing.JOptionPane.getFrameForComponent(this), objParSis, con, CodEmp, CodLoc, CodTipDoc, CodDoc, objImp.INT_COD_MNU_PRG_AJU_INV, 'c'); 
                objAjuInv.show();
                //this.getParent().add(objAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                //objAjuInv.setVisible(true);
                objAjuInv=null; 

                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException  Evt){  blnRes=false;   objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }
        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFilDet = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        panRep = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panSur = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panFil.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 80));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panFil.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panFilDet.setPreferredSize(new java.awt.Dimension(0, 450));
        panFilDet.setLayout(null);

        buttonGroup1.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFilDet.add(optTod);
        optTod.setBounds(10, 20, 550, 20);

        buttonGroup1.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optFilStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFilDet.add(optFil);
        optFil.setBounds(10, 40, 590, 20);

        lblEmpImp.setText("Importador:");
        lblEmpImp.setToolTipText("Empresa");
        panFilDet.add(lblEmpImp);
        lblEmpImp.setBounds(30, 80, 80, 20);

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
        panFilDet.add(txtCodImp);
        txtCodImp.setBounds(120, 80, 80, 20);

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
        panFilDet.add(txtNomImp);
        txtNomImp.setBounds(200, 80, 440, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFilDet.add(butImp);
        butImp.setBounds(640, 80, 20, 20);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panFilDet.add(lblPed);
        lblPed.setBounds(30, 100, 70, 20);

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
        panFilDet.add(txtCodPed);
        txtCodPed.setBounds(70, 100, 45, 20);

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
        panFilDet.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(120, 100, 80, 20);

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
        panFilDet.add(txtPedIngImp);
        txtPedIngImp.setBounds(200, 100, 440, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFilDet.add(butPedImp);
        butPedImp.setBounds(640, 100, 20, 20);

        panFil.add(panFilDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

        panRep.setLayout(new java.awt.BorderLayout());

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

        panRep.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        panCen.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.BorderLayout());

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

        panSur.add(panBot, java.awt.BorderLayout.CENTER);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //if (isCamVal()) 
        //{
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
        //}
}//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
            optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
     if (optTod.isSelected())
        {
            optFil.setSelected(false);
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodPed.setText("");
            txtPedIngImp.setText("");
            txtNumDocIngImp.setText("");
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) 
            optTod.setSelected(false);
    }//GEN-LAST:event_optFilActionPerformed

    private void optFilStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optFilStateChanged
        if (optFil.isSelected())
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilStateChanged

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodImp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodImp.getText().equalsIgnoreCase(strCodImp))
        {
            if (txtCodImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
            }
            else
            {
                configurarVenConEmpImp();
                mostrarEmpImp(1);
            }
        }
        else
            txtCodImp.setText(strCodImp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomImp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomImp.getText().equalsIgnoreCase(strNomImp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
            }
            else
            {
                configurarVenConEmpImp();
                mostrarEmpImp(2);
            }
        }
        else
        txtNomImp.setText(strNomImp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        configurarVenConEmpImp();
        mostrarEmpImp(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butImpActionPerformed

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
        strNumDocPed=txtNumDocIngImp.getText();
        txtNumDocIngImp.selectAll();
    }//GEN-LAST:event_txtNumDocIngImpFocusGained

    private void txtNumDocIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusLost
        if (!txtNumDocIngImp.getText().equalsIgnoreCase(strNumDocPed))
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
            txtNumDocIngImp.setText(strNumDocPed);
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
                txtPedIngImp.setText("");
                txtNumDocIngImp.setText("");
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

    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
            dispose();
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butPedImp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilDet;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panSur;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNumDocIngImp;
    private javax.swing.JTextField txtPedIngImp;
    // End of variables declaration//GEN-END:variables

    
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
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
       
    private boolean cargarDetReg() 
    {
        boolean blnRes=false;
        try
        {
             if(eliminaColumnasAdicionadas()) {
                if(obtenerColumnasAdicionar()) {
                    if(agregarColTblDat()) {
                        if(cargarDat()) {
                            blnRes=true;
                        }
                    }
                }
             }
        }
        catch(Exception e){        blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    } 
        
    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaColumnasAdicionadas()
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--)
            {
                objTblMod.removeColumn(tblDat, i);                
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);  System.err.println("eliminaColumnasAdicionadas");  blnRes=false;      }
        return blnRes;
    
    }
    
    /**
     * Función que obtiene las columnas a adicionar
     * @return 
     */ 
    private boolean obtenerColumnasAdicionar()
    {
        boolean blnRes=true;
        try
        {
            //Grupos: Tablas a presentar en seguimiento.
            arlDatGrpTblDin=new ArrayList();
            //arlRegGrpTblDin=new ArrayList();
            //arlRegGrpTblDin.add(INT_ARL_GRP_COD_TBL, INT_IND_GRP_NOT_PED_PRV);
            //arlRegGrpTblDin.add(INT_ARL_GRP_NOM_TBL, "CabNotPedPreImp");
            //arlRegGrpTblDin.add(INT_ARL_GRP_NOM_GRP, "Nota de Pedido Previa");
            //arlDatGrpTblDin.add(arlRegGrpTblDin);
                    
            arlRegGrpTblDin=new ArrayList();
            arlRegGrpTblDin.add(INT_ARL_GRP_COD_TBL, INT_IND_GRP_NOT_PED);
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_TBL, "CabNotPedImp");
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_GRP, "Nota de Pedido");
            arlDatGrpTblDin.add(arlRegGrpTblDin);
                    
            arlRegGrpTblDin=new ArrayList();
            arlRegGrpTblDin.add(INT_ARL_GRP_COD_TBL, INT_IND_GRP_PED_EMB);
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_TBL, "CabPedEmbImp");
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_GRP, "Pedido Embarcado");
            arlDatGrpTblDin.add(arlRegGrpTblDin);
                    
            arlRegGrpTblDin=new ArrayList();
            arlRegGrpTblDin.add(INT_ARL_GRP_COD_TBL, INT_IND_GRP_MOV_INV);
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_TBL, "CabMovInv");
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_GRP, "Pedido/Transferencias");
            arlDatGrpTblDin.add(arlRegGrpTblDin);
            
            arlRegGrpTblDin=new ArrayList();
            arlRegGrpTblDin.add(INT_ARL_GRP_COD_TBL, INT_IND_GRP_SOL_TRA);
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_TBL, "CabSolTraInv");
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_GRP, "Solicitudes de Transferencia");
            arlDatGrpTblDin.add(arlRegGrpTblDin);
            
            arlRegGrpTblDin=new ArrayList();
            arlRegGrpTblDin.add(INT_ARL_GRP_COD_TBL, INT_IND_GRP_CAB_DIA);
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_TBL, "CabDia");
            arlRegGrpTblDin.add(INT_ARL_GRP_NOM_GRP, "Asientos de Diario");
            arlDatGrpTblDin.add(arlRegGrpTblDin); 
            
            System.out.println("arlDatGrpTblDin: "+arlDatGrpTblDin);
        }
        catch (Exception e){        
            blnRes = false;     
            objUti.mostrarMsgErr_F1(this, e);
            System.out.println(e);
        }
        return blnRes;
   }
     
     /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo a la cantidad de usuarios".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        boolean blnRes=true;
        ZafTblHeaGrp objTblHeaTbl = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaTbl.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColTbl = null;
        javax.swing.table.TableColumn tbc;
        String strTitTbl="";
        try 
        {
            intColIni=objTblMod.getColumnCount();
            objTblCelRenLblColDin = new ZafTblCelRenLbl();
            for(int i=0; i<(arlDatGrpTblDin.size()); i++) //Cantidad de tablas
            {
                for(int j=0; j<(INT_NUM_CDI_GRP); j++) //Número de columnas dinámicas por tabla.
                {
                    strTitTbl = (j==INT_IND_COL_COD_EMP ? "Cód.Emp." : 
                                (j==INT_IND_COL_COD_LOC ? "Cód.Loc." : 
                                (j==INT_IND_COL_COD_TIP_DOC ? "Cód.Tip.Doc." :
                                (j==INT_IND_COL_COD_DOC ? "Cód.Doc." : 
                                (j==INT_IND_COL_DES_COR_TIPDOC ? "Tip.Doc." : 
                                (j==INT_IND_COL_DES_LAR_TIPDOC ? "Tipo documento" :
                                (j==INT_IND_COL_NUM_DOC ? "Núm.Doc." : ""    )))))));
                    int intIndColGrp =(intColIni+j+(i*INT_NUM_CDI_GRP)); //Obtengo Indice de la Columna.

                    tbc=new javax.swing.table.TableColumn(intIndColGrp);     //Creo la columna dinamica.
                    tbc.setHeaderValue("" + strTitTbl + "");

                    //Configurar JTable: Establecer el ancho de la columna.
                    if((j==INT_IND_COL_DES_LAR_TIPDOC)) //Tipo documento 
                    {
                        tbc.setWidth(0);
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblColDin);   
                    }
                    else if((j==INT_IND_COL_DES_COR_TIPDOC) ) { //Tip.Doc.  
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblColDin);   
                        tbc.setPreferredWidth(60);
                        tbc.setResizable(true);
                    }
                    else if( (j==INT_IND_COL_NUM_DOC)) { //Núm.Doc.
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblColDin);   
                        tbc.setPreferredWidth(70);
                        tbc.setResizable(true);
                    }
                    else if((j==INT_IND_COL_BTN) ) { //Btn.Doc. 7
                        tbc.setPreferredWidth(20);
                        tbc.setResizable(false);
                        objTblCelRenBut=new ZafTblCelRenBut();
                        tbc.setCellRenderer(objTblCelRenBut);
                        tbc.setCellEditor(objTblCelEdiBut);
                        
                        //Configurar JTable: Establecer columnas editables.
                        Vector vecAux=new Vector();
                        vecAux.add("" + intIndColGrp);
                        objTblMod.addColumnasEditables(vecAux);
                        vecAux=null;
                    }
                    else {
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblColDin);   
                        tbc.setPreferredWidth(35);
                        tbc.setResizable(true);
                    }    
                    
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);
                    if(j==INT_IND_COL_COD_EMP) //Primera Columna del Grupo.
                    {
                        objTblHeaColTbl=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatGrpTblDin, i, INT_ARL_GRP_NOM_GRP) + "");
                        objTblHeaColTbl.setHeight(16);
                    }
                    objTblHeaTbl.addColumnGroup(objTblHeaColTbl);
                    objTblHeaColTbl.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                    
                }
            }
            
            //Combina los colores de acuerdo al codigo de seguimiento.
            objTblCelRenLblColDin.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    objTblCelRenLblColDin.setForeground(Color.BLACK);
                    int intCell=objTblCelRenLblColDin.getRowRender();
                    int intNumSeg=Integer.parseInt(tblDat.getValueAt(intCell, INT_TBL_DAT_CODSEG).toString());

                    if (intNumSeg%2!=0)
                        objTblCelRenLblColDin.setBackground(colFonColNar);
                    else 
                        objTblCelRenLblColDin.setBackground(colFonColVer);
                }
            });    
            
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        if (objTblMod.getValueAt(intFilSel, (intColSel-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_EMP+INT_FAC_CDI))) ).equals("")) {
                            objTblCelEdiBut.setCancelarEdicion(true);
                        }
                        else {
                            objTblCelEdiBut.setCancelarEdicion(false);
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                          
                    for(int i=intFilSel; i>=intFilSel; i--)
                    {   
                        int intColBtnNotPed=(INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_NOT_PED+INT_FAC_CDI)*INT_NUM_CDI_GRP);
                        int intColBtnPedEmb=(INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_PED_EMB+INT_FAC_CDI)*INT_NUM_CDI_GRP);
                        int intColBtnMovInv=(INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_MOV_INV+INT_FAC_CDI)*INT_NUM_CDI_GRP);
                        int intColBtnSolTra=(INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_SOL_TRA+INT_FAC_CDI)*INT_NUM_CDI_GRP);
                        int intColBtnAsiDia=(INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_CAB_DIA+INT_FAC_CDI)*INT_NUM_CDI_GRP);
                        
                        if(intColSel==intColBtnNotPed) //Nota de Pedido
                        {
                            mostrarNotPed (objTblMod.getValueAt(intFilSel, (intColBtnNotPed-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_EMP+INT_FAC_CDI)))).toString()
                                         , objTblMod.getValueAt(intFilSel, (intColBtnNotPed-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_LOC+INT_FAC_CDI)))).toString()
                                         , objTblMod.getValueAt(intFilSel, (intColBtnNotPed-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_TIP_DOC+INT_FAC_CDI)))).toString() 
                                         , objTblMod.getValueAt(intFilSel, (intColBtnNotPed-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_DOC+INT_FAC_CDI)))).toString() 
                                    );
                            break;
                        }                        
                        else if(intColSel==intColBtnPedEmb) //Pedido Embarcado
                        {
                            mostrarPedEmb (objTblMod.getValueAt(intFilSel, (intColBtnPedEmb-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_EMP+INT_FAC_CDI)))).toString()
                                         , objTblMod.getValueAt(intFilSel, (intColBtnPedEmb-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_LOC+INT_FAC_CDI)))).toString()
                                         , objTblMod.getValueAt(intFilSel, (intColBtnPedEmb-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_TIP_DOC+INT_FAC_CDI)))).toString() 
                                         , objTblMod.getValueAt(intFilSel, (intColBtnPedEmb-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_DOC+INT_FAC_CDI)))).toString() 
                                     );
                            break;
                        }
                        else if(intColSel==intColBtnMovInv) //Movimiento de Inventario
                        {
                            mostrarMovInv(objTblMod.getValueAt(intFilSel, (intColBtnMovInv-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_EMP+INT_FAC_CDI)))).toString()
                                        , objTblMod.getValueAt(intFilSel, (intColBtnMovInv-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_LOC+INT_FAC_CDI)))).toString()
                                        , objTblMod.getValueAt(intFilSel, (intColBtnMovInv-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_TIP_DOC+INT_FAC_CDI)))).toString() 
                                        , objTblMod.getValueAt(intFilSel, (intColBtnMovInv-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_DOC+INT_FAC_CDI)))).toString() 
                                    );
                            break;
                        }
                        else if(intColSel==intColBtnSolTra) //Solicitudes de Transferencia
                        {
                            mostrarSolTra(objTblMod.getValueAt(intFilSel, (intColBtnSolTra-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_EMP+INT_FAC_CDI)))).toString()
                                        , objTblMod.getValueAt(intFilSel, (intColBtnSolTra-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_LOC+INT_FAC_CDI)))).toString()
                                        , objTblMod.getValueAt(intFilSel, (intColBtnSolTra-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_TIP_DOC+INT_FAC_CDI)))).toString() 
                                        , objTblMod.getValueAt(intFilSel, (intColBtnSolTra-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_DOC+INT_FAC_CDI)))).toString() 
                                    );
                            break;
                        }
                        else if(intColSel==intColBtnAsiDia) //Asiento de Diario
                        {
                            mostrarAsiDia(objTblMod.getValueAt(intFilSel, (intColBtnAsiDia-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_EMP+INT_FAC_CDI)))).toString()
                                        , objTblMod.getValueAt(intFilSel, (intColBtnAsiDia-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_LOC+INT_FAC_CDI)))).toString()
                                        , objTblMod.getValueAt(intFilSel, (intColBtnAsiDia-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_TIP_DOC+INT_FAC_CDI)))).toString() 
                                        , objTblMod.getValueAt(intFilSel, (intColBtnAsiDia-(INT_NUM_CDI_GRP-(INT_IND_COL_COD_DOC+INT_FAC_CDI)))).toString() 
                                    );
                            break;
                        }
                    }
                }
            });
            
            
            
            //objTblCelRenBut.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter()
            //{
            //    public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) 
            //    {
            //        switch (objTblCelRenBut.getColumnRender()) 
            //        {
            //            case ((INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_NOT_PED+INT_FAC_CDI)*INT_NUM_CDI_GRP)):
            //                objTblCelRenBut.setText("...");
            //                break;
            //            case ((INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_PED_EMB+INT_FAC_CDI)*INT_NUM_CDI_GRP)):
            //                objTblCelRenBut.setText("...");
            //                break;
            //            case ((INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_MOV_INV+INT_FAC_CDI)*INT_NUM_CDI_GRP)):
            //                objTblCelRenBut.setText("...");
            //                break;
            //            case ((INT_TBL_DAT_CDI-INT_FAC_CDI)+((INT_IND_GRP_SOL_TRA+INT_FAC_CDI)*INT_NUM_CDI_GRP)):
            //                objTblCelRenBut.setText("...");
            //                break;
            //        }
            //    }
            //});
            
            intColFin=objTblMod.getColumnCount();
            
            objTblHeaTbl=null;
            objTblHeaColTbl=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean cargarDat() 
    {
        boolean blnRes = true;
        String strBut="", strFec="", strNomTblAux="";
        try 
        {
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Obteniendo datos...");
            butCon.setText("Detener");
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                strFec="";
                if(objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strFec+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strFec+=" AND  a1.fe_doc <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strFec+=" AND a1.fe_doc >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 3: //Todo....
                                break;
                    }
                }
                
                stm = con.createStatement();
                strSQL ="";
                strSQL+=" SELECT Seg.co_seg, Seg.co_reg";
                for(int i=0; i<(arlDatGrpTblDin.size()); i++) 
                {
                    strAux=objUti.getStringValueAt(arlDatGrpTblDin, i, INT_ARL_GRP_NOM_TBL);
                    strSQL+="      , t"+(i+1)+".tx_DesCor as tx_desCor"+strAux+", t"+(i+1)+".tx_DesLar as tx_desLar"+strAux+", a"+(i+1)+".co_emp as co_emp"+strAux+", a"+(i+1)+".co_loc as co_loc"+strAux+"";
                    strSQL+="      , a"+(i+1)+".co_tipDoc as co_tipDoc"+strAux+"";
                    if(i==INT_IND_GRP_CAB_DIA) {
                        strSQL+="      , a"+(i+1)+".co_dia as co_doc"+strAux+"";
                    }
                    else {
                        strSQL+="      , a"+(i+1)+".co_doc as co_doc"+strAux+"";
                    }
                    
                    if(i==INT_IND_GRP_PED_EMB) {
                        strSQL+="  , a"+(i+1)+".tx_numDoc2 as ne_numDoc"+strAux+"";  
                    }
                    else if(i==INT_IND_GRP_CAB_DIA) {
                        strSQL+="  , a"+(i+1)+".tx_numDia as ne_numDoc"+strAux+"";  
                    }
                    else {
                        strSQL+="  , a"+(i+1)+".ne_numDoc as ne_numDoc"+strAux+"";  
                    }
                   
                    if(i==INT_IND_GRP_MOV_INV) {
                        strSQL+="      , a"+(i+1)+".tx_numDoc2 as ne_numPed"+strAux+"";
                        strSQL+="      , a"+(i+1)+".fe_doc as fe_doc"+strAux+"";
                    }
                }
                strSQL+=" FROM tbm_cabSegMovInv as Seg ";
                for(int i=0; i<(arlDatGrpTblDin.size()); i++) 
                {
                    strAux=objUti.getStringValueAt(arlDatGrpTblDin, i, INT_ARL_GRP_NOM_TBL);
                    strSQL+=" LEFT JOIN tbm_"+strAux+" a"+(i+1)+" ON (Seg.co_empRel"+strAux+"=a"+(i+1)+".co_emp and Seg.co_locRel"+strAux+"=a"+(i+1)+".co_loc and Seg.co_tipDocRel"+strAux+"=a"+(i+1)+".co_tipdoc ";
                    if(i==INT_IND_GRP_CAB_DIA) {
                        strSQL+=" and Seg.co_diaRel"+strAux+"=a"+(i+1)+".co_dia ) ";
                    }
                    else {
                        strSQL+=" and Seg.co_docRel"+strAux+"=a"+(i+1)+".co_doc ) ";
                    }
                    
                    strSQL+=" LEFT JOIN tbm_CabTipDoc as t"+(i+1)+" ON (a"+(i+1)+".co_emp=t"+(i+1)+".co_emp AND a"+(i+1)+".co_loc=t"+(i+1)+".co_loc AND a"+(i+1)+".co_tipDoc=t"+(i+1)+".co_tipDoc)";  
                }
                strSQL+=" WHERE Seg.co_Seg>0";
                
                //Filtro
                for(int i=0; i<(arlDatGrpTblDin.size()); i++) 
                {
                    strAux=objUti.getStringValueAt(arlDatGrpTblDin, i, INT_ARL_GRP_NOM_TBL);
                    if(i==INT_IND_GRP_MOV_INV)
                    {
                        strSQL+=" AND Seg.co_seg IN (  ";
                        strSQL+="    SELECT a.co_Seg FROM tbm_cabSegMovInv as a";
                        strSQL+="    INNER JOIN tbm_"+strAux+" as a1 ON a.co_empRel"+strAux+"=a1.co_emp AND a.co_locRel"+strAux+"=a1.co_loc";
                        strSQL+="    AND a.co_tipDocRel"+strAux+"=a1.co_tipDoc AND a.co_docRel"+strAux+"=a1.co_doc";
                        strSQL+="    INNER JOIN tbm_cabTipDoc as a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                        strSQL+="    WHERE a.co_Seg>0 ";
                        strSQL+="    AND a1.co_tipDoc IN(select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+INT_COD_MNU_INGIMP+")";
                        if(txtCodPed.getText().length()>0)
                        {
                            strSQL+="    AND a.co_empRel"+strAux+"="+intCodEmpIngImp;
                            strSQL+="    AND a.co_locRel"+strAux+"="+intCodLocIngImp;
                            strSQL+="    AND a.co_tipDocRel"+strAux+"="+intCodTipDocIngImp;
                            strSQL+="    AND a.co_docRel"+strAux+"="+ txtCodPed.getText();
                        }
                        if(txtCodImp.getText().length()>0){
                            strSQL+="    AND a.co_empRel"+strAux+"="+ objUti.codificar(txtCodImp.getText());
                        }
                            
                        strSQL+="    "+strFec;
                        strSQL+=" )";
                    }
                }

                strSQL+=" ORDER BY Seg.co_seg, Seg.co_Reg \n";
                System.out.println("cargarDat: "+strSQL);
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                while (rst.next()) 
                {
                    strNomTblAux=objUti.getStringValueAt(arlDatGrpTblDin, INT_IND_GRP_MOV_INV, INT_ARL_GRP_NOM_TBL);
                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CODSEG,rst.getString("co_seg"));
                    vecReg.add(INT_TBL_DAT_CODREG,rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_FECDOCPED, rst.getString("ne_numPed"+strNomTblAux+"")==null?"":rst.getString("fe_doc"+strNomTblAux+""));
                    vecReg.add(INT_TBL_DAT_NUMPED,rst.getString("ne_numPed"+strNomTblAux+""));
                         
                    for(int i=0; i<(arlDatGrpTblDin.size()); i++) //Cantidad de tablas
                    {
                        for(int j=0; j<(INT_NUM_CDI_GRP); j++) //Número de columnas dinámicas por tabla.        
                        {
                            int intIndColGrp =(intColIni+j+(i*INT_NUM_CDI_GRP)); //Obtengo Indice de la Columna.
                            strAux=objUti.getStringValueAt(arlDatGrpTblDin, i, INT_ARL_GRP_NOM_TBL);
                            strBut=rst.getString("co_emp"+strAux+"")==null?"":"...";
                            strDat = (j==INT_IND_COL_COD_EMP ? rst.getString("co_emp"+strAux+"")==null? "":rst.getString("co_emp"+strAux+"")  : 
                                     (j==INT_IND_COL_COD_LOC ? rst.getString("co_loc"+strAux+"") : 
                                     (j==INT_IND_COL_COD_TIP_DOC ? rst.getString("co_tipDoc"+strAux+"") : 
                                     (j==INT_IND_COL_COD_DOC ? rst.getString("co_doc"+strAux+"") : 
                                     (j==INT_IND_COL_DES_COR_TIPDOC ? rst.getString("tx_DesCor"+strAux+"") : 
                                     (j==INT_IND_COL_DES_LAR_TIPDOC ? rst.getString("tx_DesLar"+strAux+"") : 
                                     (j==INT_IND_COL_NUM_DOC ? rst.getString("ne_numDoc"+strAux+"") : strBut   )))))));
                            vecReg.add(intIndColGrp, strDat);
                        }
                    }
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
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
        catch (java.sql.SQLException e) {       blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        catch (Exception e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }


     
     
    
}
