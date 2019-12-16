/*
 * ZafCom46.java
 *
 * Created on 14 de septiembre de 2010, 12:58 PM
 */
package Compras.ZafCom46;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom46 extends javax.swing.JInternalFrame
{
    //Constantes: 
    //Columnas del JTable: tblDat
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALTDOS=4;                  //Código alterno 2 del item.
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_DEC_UNI=6;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_CDI_SOB=7;                     //Columna dinámica: Sobrante.
    
    static final int INT_TBL_DAT_NUM_TOT_CES=7;                 //Número total de columnas estáticas.

    //Columnas del JTable: tblBod
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    //Otras Constantes
    final int INT_ARL_COD_BOD=0;
    final int INT_ARL_COL_BOD=1;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnuBod;                       //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblPopMnu objTblPopMnuDet;                       //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafCom46_01 objCom46_01;
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatBod, vecCabBod;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnMarTodChkTblBod=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
    private String strSQL, strAux;
    private String strCodAlt,strCodAlt2, strNomItm;             //Contenido del campo al obtener el foco.
    private String strVersion=" v0.2.9 ";
    private int intNumColAddBod, intNumColIniBod, intNumColFinBod;
    private ArrayList arlRegColAdi, arlDatColAdi;
   
    /**
     * Crea una nueva instancia de la clase ZafCom46.
     * @param obj El objeto ZafParSis.
     */
    public ZafCom46(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatColAdi=new ArrayList();
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
            //Titulo de ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objHisTblBasDat=new ZafHisTblBasDat();
            objPerUsr=new ZafPerUsr(objParSis);

            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();
            cargarBod();

            arlDatColAdi.clear();
            llenarArregloBodegas();
            
            objCom46_01=new ZafCom46_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            agregarColTblDat();
            
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
            vecDat=new Vector();                          //Almacena los datos
            vecCab=new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALTDOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuDet=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALTDOS).setPreferredWidth(50);            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
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
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Formato Columna: Código Alterno 2 
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALTDOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            butCon.setVisible(false);
            butCer.setVisible(false);
            if(objParSis.getCodigoUsuario()==1){
                butCon.setVisible(true);
                butCer.setVisible(true);
            }
            else{
                if(objParSis.getCodigoMenu()==2594){//bodegas
                    if(objPerUsr.isOpcionEnabled(2595)){//consultar
                         butCon.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2596)){//guardar
                        butGua.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2597)){//cerrar
                        butCer.setVisible(true);
                    }
                }       
            }
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        int i;
        javax.swing.table.TableColumn tbc;
        String strCodEmp="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
//        java.awt.Color colFonCol;
        boolean blnRes=true;
        intNumColIniBod=objTblMod.getColumnCount();
        int p=0, f=-1;
        try
        {
            intNumColAddBod=(objTblModBod.getRowCountTrue()*2);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            //cuando es columna de sobrante
            objTblCelEdiTxt=new ZafTblCelEdiTxt();
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel=-1;
                int intColSel=-1;
                int intArlColBod=-1;
                int intArlCodBodGrp=0;
                
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("actionPerformed");           
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("beforeEdit");
                };
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit");
                }
            });
            
            //cuando es columna de boton
            objTblCelRenBut=new ZafTblCelRenBut();
            objTblCelEdiButGen= new ZafTblCelEdiButGen();            
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                int intArlColBod=-1;
                int intArlCodBodGrp=0;
                
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("actionPerformed");
                    objCom46_01.cargarHistorico();
                    objCom46_01.setVisible(true);
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("beforeEdit");
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    
                    //System.out.println("objTblCelEdiButGen-intFilSel: " + intFilSel);
                    //System.out.println("objTblCelEdiButGen-intColSel: " + intColSel);
                    
                    //se obtiene el codigo de la bodega almacenado en el arraylist
                    //System.out.println("arlDatColAdi:  " + arlDatColAdi.toString()  );
                    for(int a=0; a<arlDatColAdi.size();a++){
                        //System.out.println("--------------");
                        intArlColBod=objUti.getIntValueAt(arlDatColAdi, a, INT_ARL_COL_BOD);
                        //System.out.println("intArlColBod: " + intArlColBod);
                        if(intArlColBod==(intColSel-1)){
                            //System.out.println("if-intArlColBod: " + intArlColBod);
                            intArlCodBodGrp=objUti.getIntValueAt(arlDatColAdi, a, INT_ARL_COD_BOD);
                            break;
                        }
                    }
                    //se envian los datos a la clase ZafCom46_01
                    objCom46_01.setCodigoItemGrupo(Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SIS).toString()));
                    objCom46_01.setCodigoBodegaGrupo(intArlCodBodGrp);
                };
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit");
//                    arlDat=objCom33_01.getArlDat();
//                    System.out.println("ARRAYLIST EN CLASE PRINCIPAL: " + arlDat.toString());
//                    cargarDatosAnexo(intFilSel);
                }
            });
            
            for (i=0; i<intNumColAddBod; i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?("Sobrante "+ (objTblModBod.getValueAt(f, INT_TBL_BOD_NOM_BOD))):"...");
                tbc=new javax.swing.table.TableColumn(intNumColIniBod+i);
                tbc.setHeaderValue("" + strSubTit );
                //objUti.setStringValueAt(arlDatColAdi, f, INT_ARL_COL_BOD, String.valueOf((intNumColIniBod+i)));
                //Configurar JTable: Establecer el ancho de la columna.
                if(p==0){
                    objUti.setStringValueAt(arlDatColAdi, f, INT_ARL_COL_BOD, String.valueOf((intNumColIniBod+i)));
                    tbc.setPreferredWidth(60);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLbl);
                    tbc.setCellEditor(objTblCelEdiTxt);
                }
                else{
                    tbc.setPreferredWidth(20);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenBut);
                    tbc.setCellEditor(objTblCelEdiButGen);
                }                
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strCodEmp.equals(objTblModBod.getValueAt(f, INT_TBL_BOD_COD_EMP).toString())){
                    objTblHeaColGrpEmp=new ZafTblHeaColGrp(objTblModBod.getValueAt(f, INT_TBL_BOD_NOM_EMP).toString());
                    objTblHeaColGrpEmp.setHeight(16);
                    //objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                    strCodEmp=objTblModBod.getValueAt(f, INT_TBL_BOD_COD_EMP).toString();
                }
                //objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_SOB+i));
                objTblHeaColGrpEmp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                if(p==1){
                    p=0;
                }
                else{
                    p++;
                }
            }
            
            //System.out.println("arlDatColAdi-final: " + arlDatColAdi.toString());
            
            objTblCelRenLbl=null;
            intNumColFinBod=objTblMod.getColumnCount();
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
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_NUM_TOT_CES+(intNumColAddBod/2);
            
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALTDOS:
                    strMsg="Código alterno 2 del item";
                    break;    
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CDI_SOB:
                    strMsg=objTblMod.getColumnName(tblDat.columnAtPoint(evt.getPoint()));
                    break;
                default:
                    //strMsg="";
                    strMsg=objTblMod.getColumnName(tblDat.columnAtPoint(evt.getPoint()));
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatBod=new Vector();    //Almacena los datos
            vecCabBod=new Vector(6);   //Almacena las cabeceras
            vecCabBod.clear();
            vecCabBod.add(INT_TBL_BOD_LIN,"");
            vecCabBod.add(INT_TBL_BOD_CHK,"");
            vecCabBod.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCabBod.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCabBod.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCabBod.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCabBod);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuBod=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    if (objTblCelEdiChk.isChecked())
                    {
                        //Mostrar columnas.
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+(i*2), tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+((i*2)+1), tblDat);
                    }
                    else
                    {
                        //Ocultar columnas.
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+(i*2), tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+((i*2)+1), tblDat);
                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las bodegas.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo las bodegas de la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL= "";
                    strSQL+=" SELECT a1.st_reg, a3.co_emp, a3.tx_nom, a1.co_bod, a1.tx_nom AS a1_tx_nom";
                    strSQL+=" FROM tbm_emp AS a3 INNER JOIN tbm_bod AS a1";
                    strSQL+=" ON (a3.co_emp=a1.co_emp)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" ORDER BY a3.co_emp, a1.co_bod";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a1.st_reg, a3.co_emp, a3.tx_nom, a1.co_bod, a1.tx_nom AS a1_tx_nom";
                    strSQL+=" FROM tbm_emp AS a3 INNER JOIN tbm_bod AS a1";
                    strSQL+=" ON (a3.co_emp=a1.co_emp)";
                    strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" ORDER BY a3.co_emp, a1.co_bod";
                }                
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatBod.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    if (rst.getString("st_reg").equals("A"))
                        vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_BOD_CHK,null);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a1_tx_nom"));
                    vecDatBod.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDatBod);
                tblBod.setModel(objTblModBod);
                vecDatBod.clear();
                blnMarTodChkTblBod=false;
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        int p=-1;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                        p++;
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+p, tblDat);
                        p++;
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+p, tblDat);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                        p++;
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+p, tblDat);
                        p++;
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_SOB+p, tblDat);
                        
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    private boolean llenarArregloBodegas()
    {
        boolean blnRes=true;
        try
        {
            arlDatColAdi.clear();
            for(int i=0; i<objTblModBod.getRowCountTrue(); i++)
            {
                arlRegColAdi=new ArrayList();
                arlRegColAdi.add(INT_ARL_COD_BOD, objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD));
                arlRegColAdi.add(INT_ARL_COL_BOD, "");
                arlDatColAdi.add(arlRegColAdi);
            }
            //System.out.println("llenarArregloBodegas - arlDatColAdi: " + arlDatColAdi.toString());
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
 
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
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
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panRngCodAltItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkSolSob = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 44, 80, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(70, 44, 25, 20);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(95, 44, 100, 20);

        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(195, 44, 65, 20);

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(260, 44, 400, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(660, 44, 20, 20);

        panRngCodAltItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panRngCodAltItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panRngCodAltItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(20, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panRngCodAltItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(70, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panRngCodAltItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(180, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panRngCodAltItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(230, 20, 100, 20);

        panFil.add(panRngCodAltItm);
        panRngCodAltItm.setBounds(24, 64, 350, 52);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(30, 20, 90, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(122, 20, 170, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(374, 64, 305, 52);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(24, 116, 660, 150);

        chkSolSob.setText("Sólo items con sobrantes");
        chkSolSob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolSobActionPerformed(evt);
            }
        });
        panFil.add(chkSolSob);
        chkSolSob.setBounds(24, 266, 324, 20);

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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkSolSobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolSobActionPerformed
        if (chkSolSob.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolSobActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            chkSolSob.setSelected(false);
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

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //Validar que el programa sólo funcione en la empresa grupo
        if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
        {
            mostrarMsgInf("Este programa sólo funciona en la empresa grupo.\nIngrese a la empresa grupo y vuelva a intentarlo.");
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (guardar())
                {
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    cargarDetReg();
                }
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodAlt2.setText(strCodAlt2);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

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
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkSolSob;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRngCodAltItm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
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

    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo:
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    //private void mostrarFrmHistorico(int fila){
    //    int intCodItmGrp=Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_SIS).toString());
    //    objCom46_01=new ZafCom46_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, intCodItmGrp);
    //    this.getParent().add(objCom46_01,javax.swing.JLayeredPane.DEFAULT_LAYER);
    //    objCom46_01.show();
    //}

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
        int intCodEmp, intNumFilTblBod, j;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (txtCodItm.getText().length()>0)
                    strAux+=" AND a1.co_itm=" + txtCodItm.getText();
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        strSQL+=", b" + (j+2) + ".nd_canSob AS b" + (j+2) + "nd_canSob, null AS b" + (j+2) + "butCanSob";
                    }
                    else
                    {
                        strSQL+=", Null AS b" + (j+2) + "nd_canSob, null AS b" + (j+2) + "butCanSob";
                    }
                }
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=strAux;
                strSQL+=" ) AS b1";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        strSQL+=" INNER JOIN (";
                        strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_canSob";
                        strSQL+=" FROM tbm_invBod AS a1";
                        strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+=" WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                        strSQL+=" AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                        strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";

                    }
                }
                strSQL+=" ORDER BY b1.tx_codAlt";
                System.out.println("cargarDetReg: " + strSQL);
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
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALTDOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        
                        int h=0;
                        
                        for (j=0; j<intNumFilTblBod; j++)
                        {
                            vecReg.add(INT_TBL_DAT_CDI_SOB+h,rst.getString("b" + (j+2) + "nd_canSob"));
                            h++;
                            vecReg.add(INT_TBL_DAT_CDI_SOB+h,rst.getObject("b" + (j+2) + "butCanSob"));
                            h++;
                        }
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
                }


                vecAux=new Vector();
                for(int k=intNumColIniBod; k<intNumColFinBod;k++){
                    vecAux.add("" + k);
                }
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;

                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

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

                if(chkSolSob.isSelected())
                    mostrarSoloItemsSobrantes();

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
    
    private boolean mostrarSoloItemsSobrantes()
    {
        boolean blnRes=true;
        BigDecimal bdeSumSob=new BigDecimal("0");
        BigDecimal bdeSob=new BigDecimal("0");

        try
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                for(int j=intNumColIniBod; j<intNumColFinBod; j++)
                {
                    bdeSob=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bdeSumSob=bdeSumSob.add(bdeSob);
                }
                if(bdeSumSob.compareTo(new BigDecimal("0"))==0)
                    objTblMod.removeRow(i);

                bdeSumSob=new BigDecimal("0");
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeEmptyRows();
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                con.setAutoCommit(false);
                if(actualizarDatos())
                {
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();

                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDatos()
    {
        boolean blnRes=true;
        String strUpd="", strFecUltMod;
        int intCodBod=-1;
        int intColBod=-1;
        BigDecimal bdeCanSob=new BigDecimal("0");
        try
        {
            if(con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M")){
                        for(int j=intNumColIniBod; j<intNumColFinBod; j++){
                            bdeCanSob=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            for(int y=0; y<arlDatColAdi.size(); y++){
                                intColBod=objUti.getIntValueAt(arlDatColAdi, y, INT_ARL_COL_BOD);
                                if(j==intColBod){
                                    intCodBod=objUti.getIntValueAt(arlDatColAdi, y, INT_ARL_COD_BOD);
                                    //Actualiza cantidad sobrante en tbm_invBod
                                    strSQL="";
                                    strSQL+=" UPDATE tbm_invBod";
                                    strSQL+=" SET nd_canSob=" + bdeCanSob + "";
                                    strSQL+=", fe_ultMod='" + strFecUltMod + "'";
                                    strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                    strSQL+=" AND co_bod=" + intCodBod + "";
                                    strSQL+=" AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS) + "";
                                    strSQL+=" AND (CASE WHEN nd_cansob IS NULL THEN 0 ELSE nd_cansob END)<>" + bdeCanSob + "";
                                    strSQL+=" ;\n";
                                    strUpd+=strSQL;
                                    break;
                                }
                            }
                        }
                    }
                }
                System.out.println("UPDATE actualizarDatos : " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm=null;
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_invBod", "tbh_invBod", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

 
    
    
    

}