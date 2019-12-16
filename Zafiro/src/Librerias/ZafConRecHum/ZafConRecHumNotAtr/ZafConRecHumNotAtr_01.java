
package Librerias.ZafConRecHum.ZafConRecHumNotAtr;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import RecursosHumanos.ZafRecHum33.ZafRecHum33;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
/**a
 * Clase nueva para mostrar información del personal que llega atrasado a la compañía.
 * @author Roberto Flores
 * Guayaquil 08/01/2013
 */
public class ZafConRecHumNotAtr_01 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    private final int INT_TBL_LIN_ATR=0;                        //Línea
    private final int INT_TBL_CODEMP_ATR=1;                     // código de empresa
    private final int INT_TBL_EMP_ATR=2;                        // empresa
    private final int INT_TBL_FECHA_ATR=3;                      // fecha
    private final int INT_TBL_CODTRA_ATR=4;                     // código del empleado
    private final int INT_TBL_NOMTRA_ATR=5;                     // empleado
    private final int INT_TBL_HORENT_ATR=6;                     // hora de entrada del empleado
    
    private final int INT_TBL_LIN_FAL=0;                        //Línea
    private final int INT_TBL_CODEMP_FAL=1;                     // código de empresa
    private final int INT_TBL_EMP_FAL=2;                        // empresa
    private final int INT_TBL_CODTRA_FAL=3;                     // código del empleado
    private final int INT_TBL_NOMTRA_FAL=4;                     // empleado
    private final int INT_TBL_CANFAL_FAL=5;                     // hora de entrada del empleado
    
    private final int INT_TBL_LIN_MARINC=0;                        //Línea
    private final int INT_TBL_CODEMP_MARINC=1;                     // código de empresa
    private final int INT_TBL_EMP_MARINC=2;                        // empresa
    private final int INT_TBL_CODTRA_MARINC=3;                     // código del empleado
    private final int INT_TBL_NOMTRA_MARINC=4;                     // empleado
    private final int INT_TBL_CANFAL_MARINC=5;                     // hora de entrada del empleado
    
    private final int INT_TBL_LIN_DET_FAL=0;                        //Línea
    private final int INT_TBL_CODEMP_DET_FAL=1;                     // código de empresa
    private final int INT_TBL_EMP_DET_FAL=2;                        // empresa
    private final int INT_TBL_FECHA_DET_FAL=3;                      // fecha
    private final int INT_TBL_CODTRA_DET_FAL=4;                     // código del empleado
    private final int INT_TBL_NOMTRA_DET_FAL=5;                     // empleado
    
    private final int INT_TBL_LIN_DET_MI=0;                        //Línea
    private final int INT_TBL_CODEMP_DET_MI=1;                     // código de empresa
    private final int INT_TBL_EMP_DET_MI=2;                        // empresa
    private final int INT_TBL_FECHA_DET_MI=3;                      // fecha
    private final int INT_TBL_CODTRA_DET_MI=4;                     // código del empleado
    private final int INT_TBL_NOMTRA_DET_MI=5;                     // empleado
    private final int INT_TBL_NOMTRA_DET_HOENT=6;                     // entrada
    private final int INT_TBL_NOMTRA_DET_HOSAL=7;                     // salida
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblModAtr;
    private ZafTblMod objTblModFal;
    private ZafTblMod objTblModMarInc;
    private ZafTblMod objTblModDetFal;
    private ZafTblMod objTblModDetMarInc;
//    private ZafTblMod objTblModDab;

    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;  
    private Vector vecAux;
    private String strAux;
    private ArrayList arlDat;
    private ArrayList arlDatFal;
    private ArrayList arlDatMarInc;
    
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;
           
    private javax.swing.JDesktopPane dskGen;
    
    private String strFecDesAtr;
    private String strFecHasAtr;
    private String strFecDesFal;
    private String strFecHasFal;
    
    private String strVersion="v1.12";
    
    /** Creates new form ZafConRecHum_01 */
    public ZafConRecHumNotAtr_01(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , java.util.ArrayList vectorFal , java.util.ArrayList vectorMarInc , ZafParSis obj,  javax.swing.JDesktopPane dskGe
            , String strFecDesAtr, String strFecHasAtr, String strFecDesFal, String strFecHasFal) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        objParSis=obj;
        initComponents();
        arlDat=vector;
        arlDatFal=vectorFal;
        arlDatMarInc=vectorMarInc;
        dskGen=dskGe;
        this.strFecDesAtr=strFecDesAtr;
        this.strFecHasAtr=strFecHasAtr;
        this.strFecDesFal=strFecDesFal;
        this.strFecHasFal=strFecHasFal;
        configurarFrm();
        configurarTblDet();
        configurarTblMIDet();
        configurarFechaCorte();
        cargarDatos();  
    }
    
    private boolean configurarFechaCorte()
    {
        boolean blnRes=true;
        try
        {
            this.strAux="Fecha de corte:   Desde:   " + strFecDesFal + "   Hasta:   " + strFecHasFal;
            lblFal.setText(strAux);
            
            this.strAux="Fecha de corte:   Desde:   " + strFecDesAtr + "   Hasta:   " + strFecHasAtr;
            lblAtr.setText(strAux);
            
            
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDet".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDet()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
//            strAux="Novedades en Asistencias ";  //objParSis.getNombreMenu();
//            this.setTitle(strAux);
//            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN_DET_FAL,"");
            vecCab.add(INT_TBL_CODEMP_DET_FAL,"Cód. Emp.");
            vecCab.add(INT_TBL_EMP_DET_FAL,"Empresa");
            vecCab.add(INT_TBL_FECHA_DET_FAL,"Fecha");
            vecCab.add(INT_TBL_CODTRA_DET_FAL,"Cód. Tra");
            vecCab.add(INT_TBL_NOMTRA_DET_FAL,"Empleado");
            
            objTblModDetFal=new ZafTblMod();
            objTblModDetFal.setHeader(vecCab);
            tblMovReg.setModel(objTblModDetFal);

            //Configurar JTable: Establecer tipo de selección.
             tblMovReg.setRowSelectionAllowed(true);
             tblMovReg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
             
             //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblMovReg);
             
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblMovReg,INT_TBL_LIN_DET_FAL);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblMovReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblMovReg.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN_DET_FAL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODEMP_DET_FAL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_DET_FAL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FECHA_DET_FAL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODTRA_DET_FAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA_DET_FAL).setPreferredWidth(200);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMovReg.getTableHeader().setReorderingAllowed(false);
            
            /* Aqui se agrega las columnas que van a ser ocultas*/
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP_DET_FAL);
            arlColHid.add(""+INT_TBL_CODTRA_DET_FAL);
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                arlColHid.add(""+INT_TBL_EMP_DET_FAL);
            }
            objTblModDetFal.setSystemHiddenColumns(arlColHid, tblMovReg);
            arlColHid=null;
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblMovReg); 
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblMovReg);
            
            //Configurar JTable: Establecer que el JTable sea editable.
//            objTblModAtr.setModoOperacion(objTblModAtr.INT_TBL_EDI);
            
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
     * Esta función configura el JTable "tblDet".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblMIDet()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
//            strAux="Novedades en Asistencias ";  //objParSis.getNombreMenu();
//            this.setTitle(strAux);
//            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN_DET_MI,"");
            vecCab.add(INT_TBL_CODEMP_DET_MI,"Cód. Emp.");
            vecCab.add(INT_TBL_EMP_DET_MI,"Empresa");
            vecCab.add(INT_TBL_FECHA_DET_MI,"Fecha");
            vecCab.add(INT_TBL_CODTRA_DET_MI,"Cód. Tra");
            vecCab.add(INT_TBL_NOMTRA_DET_MI,"Empleado");
            vecCab.add(INT_TBL_NOMTRA_DET_HOENT,"Entrada");
            vecCab.add(INT_TBL_NOMTRA_DET_HOSAL,"Salida");
            
            objTblModDetMarInc=new ZafTblMod();
            objTblModDetMarInc.setHeader(vecCab);
            tblMovRegMI.setModel(objTblModDetMarInc);

            //Configurar JTable: Establecer tipo de selección.
             tblMovRegMI.setRowSelectionAllowed(true);
             tblMovRegMI.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
             
             //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblMovRegMI);
             
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblMovRegMI,INT_TBL_LIN_DET_MI);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblMovReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblMovRegMI.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN_DET_MI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODEMP_DET_MI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_DET_MI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FECHA_DET_MI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODTRA_DET_MI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA_DET_MI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_NOMTRA_DET_HOENT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMTRA_DET_HOSAL).setPreferredWidth(60);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMovRegMI.getTableHeader().setReorderingAllowed(false);
            
            /* Aqui se agrega las columnas que van a ser ocultas*/
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP_DET_MI);
            arlColHid.add(""+INT_TBL_CODTRA_DET_MI);
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                arlColHid.add(""+INT_TBL_EMP_DET_MI);
            }
            objTblModDetMarInc.setSystemHiddenColumns(arlColHid, tblMovRegMI);
            arlColHid=null;
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblMovRegMI); 
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblMovRegMI);
            
            //Configurar JTable: Establecer que el JTable sea editable.
//            objTblModAtr.setModoOperacion(objTblModAtr.INT_TBL_EDI);
            
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
    
       /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Novedades en Asistencias " + strVersion;  //objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN_ATR,"");
            vecCab.add(INT_TBL_CODEMP_ATR,"Cód. Emp.");
            vecCab.add(INT_TBL_EMP_ATR,"Empresa");
            vecCab.add(INT_TBL_FECHA_ATR,"Fecha");
            vecCab.add(INT_TBL_CODTRA_ATR,"Cód. Tra");
            vecCab.add(INT_TBL_NOMTRA_ATR,"Empleado");
            vecCab.add(INT_TBL_HORENT_ATR,"Hor. Ent.");
            
            objTblModAtr=new ZafTblMod();
            objTblModAtr.setHeader(vecCab);
            tblDatAtr.setModel(objTblModAtr);

            
            vecCab=new Vector();
            vecCab.add(INT_TBL_LIN_FAL,"");
            vecCab.add(INT_TBL_CODEMP_FAL,"Cód. Emp.");
            vecCab.add(INT_TBL_EMP_FAL,"Empresa");
            vecCab.add(INT_TBL_CODTRA_FAL,"Cód. Tra");
            vecCab.add(INT_TBL_NOMTRA_FAL,"Empleado");
            vecCab.add(INT_TBL_CANFAL_FAL,"Can. Fal.");
//            vecCab.add(INT_TBL_HORENT,"Num. Fal..");
            
            objTblModFal=new ZafTblMod();
            objTblModFal.setHeader(vecCab);
            tblDatFal.setModel(objTblModFal);
            
            vecCab=new Vector();
            vecCab.add(INT_TBL_LIN_MARINC,"");
            vecCab.add(INT_TBL_CODEMP_MARINC,"Cód. Emp.");
            vecCab.add(INT_TBL_EMP_MARINC,"Empresa");
            vecCab.add(INT_TBL_CODTRA_MARINC,"Cód. Tra");
            vecCab.add(INT_TBL_NOMTRA_MARINC,"Empleado");
            vecCab.add(INT_TBL_CANFAL_MARINC,"Can. Mar. Inc");
//            vecCab.add(INT_TBL_HORENT,"Num. Fal..");
            
            objTblModMarInc=new ZafTblMod();
            objTblModMarInc.setHeader(vecCab);
            tblDatMarInc.setModel(objTblModMarInc);
            
//            vecCab.add(INT_TBL_HORENT,"Hor. Ent.");
            
            //Configurar JTable: Establecer tipo de selección.
             tblDatFal.setRowSelectionAllowed(true);
             tblDatFal.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
             tblDatAtr.setRowSelectionAllowed(true);
             tblDatAtr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
             tblDatMarInc.setRowSelectionAllowed(true);
             tblDatMarInc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
             
             //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatAtr);
            objTblPopMnu=new ZafTblPopMnu(tblDatFal);
            objTblPopMnu=new ZafTblPopMnu(tblDatMarInc);
             
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDatFal,INT_TBL_LIN_ATR);
            new ZafColNumerada(tblDatAtr,INT_TBL_LIN_FAL);
            new ZafColNumerada(tblDatMarInc,INT_TBL_LIN_MARINC);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDatAtr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDatMarInc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
           
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatAtr.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN_ATR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODEMP_ATR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_ATR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FECHA_ATR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODTRA_ATR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMTRA_ATR).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_HORENT_ATR).setPreferredWidth(60);
            
            javax.swing.table.TableColumnModel tcmAuxFal=tblDatFal.getColumnModel();
            tcmAuxFal.getColumn(INT_TBL_LIN_FAL).setPreferredWidth(20);
            tcmAuxFal.getColumn(INT_TBL_CODEMP_FAL).setPreferredWidth(30);
            tcmAuxFal.getColumn(INT_TBL_EMP_FAL).setPreferredWidth(80);
            tcmAuxFal.getColumn(INT_TBL_CODTRA_FAL).setPreferredWidth(50);
            tcmAuxFal.getColumn(INT_TBL_NOMTRA_FAL).setPreferredWidth(220);
            tcmAuxFal.getColumn(INT_TBL_CANFAL_FAL).setPreferredWidth(50);
//            tcmAuxFal.getColumn(INT_TBL_HORENT).setPreferredWidth(60);
            
            javax.swing.table.TableColumnModel tcmAuxMarInc=tblDatMarInc.getColumnModel();
            tcmAuxMarInc.getColumn(INT_TBL_LIN_MARINC).setPreferredWidth(20);
            tcmAuxMarInc.getColumn(INT_TBL_CODEMP_MARINC).setPreferredWidth(30);
            tcmAuxMarInc.getColumn(INT_TBL_EMP_MARINC).setPreferredWidth(80);
            tcmAuxMarInc.getColumn(INT_TBL_CODTRA_MARINC).setPreferredWidth(50);
            tcmAuxMarInc.getColumn(INT_TBL_NOMTRA_MARINC).setPreferredWidth(220);
            tcmAuxMarInc.getColumn(INT_TBL_CANFAL_MARINC).setPreferredWidth(50);
//            tcmAuxFal.getColumn(INT_TBL_HORENT).setPreferredWidth(60);
            
            tblDatFal.getTableHeader().setReorderingAllowed(false);
            tblDatAtr.getTableHeader().setReorderingAllowed(false);
            tblDatMarInc.getTableHeader().setReorderingAllowed(false);
            
            /* Aqui se agrega las columnas que van a ser ocultas*/
            ArrayList arlColHidAtr=new ArrayList();
            ArrayList arlColHidFal=new ArrayList();
            ArrayList arlColHidMI=new ArrayList();
            arlColHidAtr.add(""+INT_TBL_CODEMP_ATR);
            arlColHidFal.add(""+INT_TBL_CODEMP_FAL);
            arlColHidMI.add(""+INT_TBL_CODEMP_MARINC);
            arlColHidAtr.add(""+INT_TBL_CODTRA_ATR);
            arlColHidFal.add(""+INT_TBL_CODTRA_FAL);
            arlColHidMI.add(""+INT_TBL_CODTRA_MARINC);
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                arlColHidAtr.add(""+INT_TBL_EMP_ATR);
                arlColHidFal.add(""+INT_TBL_EMP_FAL);
                arlColHidMI.add(""+INT_TBL_EMP_MARINC);
            }
            objTblModAtr.setSystemHiddenColumns(arlColHidAtr, tblDatAtr);
            objTblModFal.setSystemHiddenColumns(arlColHidFal, tblDatFal);
            objTblModMarInc.setSystemHiddenColumns(arlColHidMI, tblDatMarInc);
            arlColHidAtr=null;
            arlColHidFal=null;
            arlColHidMI=null;
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDatFal); 
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDatMarInc); 
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatAtr);
            objTblBus=new ZafTblBus(tblDatFal);
            objTblBus=new ZafTblBus(tblDatMarInc);
            
//            objTblModAtr.setModoOperacion(objTblModAtr.INT_TBL_EDI);
//            objTblModFal.setModoOperacion(objTblModFal.INT_TBL_EDI);
            
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDatFal.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsmMI=tblDatMarInc.getSelectionModel();
            lsmMI.addListSelectionListener(new ZafLisSelLisMI());
            
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
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            if (!lsm.isSelectionEmpty())
            {
                if (chkMosMovReg.isSelected())
                    cargarMovReg();
                else
                    objTblModDetFal.removeAllRows();
            }
        }
    }
    
    
    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisMI implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            if (!lsm.isSelectionEmpty())
            {
                if (chkMosMovMI.isSelected())
                    cargarMovRegMI();
                else
                    objTblModDetMarInc.removeAllRows();
            }
        }
    }
    
    private void cargarDatos(){
       
        vecDat.clear();
       
//       if(arlDat.size()==0){
//           jTabbedPane1.remove(1);
//       }else{
           
           for(int i=0; i<arlDat.size(); i++){
          
                vecReg=new Vector();
                vecReg.add(INT_TBL_LIN_ATR,"");
                vecReg.add(INT_TBL_CODEMP_ATR, objUti.getStringValueAt(arlDat, i, 0) );
                vecReg.add(INT_TBL_EMP_ATR, objUti.getStringValueAt(arlDat, i, 1) );
                vecReg.add(INT_TBL_FECHA_ATR, objUti.getStringValueAt(arlDat, i, 2) );
                vecReg.add(INT_TBL_CODTRA_ATR, objUti.getStringValueAt(arlDat, i, 3) );
                vecReg.add(INT_TBL_NOMTRA_ATR, objUti.getStringValueAt(arlDat, i, 4) ); 
                vecReg.add(INT_TBL_HORENT_ATR, objUti.getStringValueAt(arlDat, i, 5) ); 
                vecDat.add(vecReg);
           }
           
           objTblModAtr.setData(vecDat);  
           tblDatAtr.setModel(objTblModAtr);
           vecDat.clear();
//       }
       
        

//        if(arlDatFal.size()==0){
//            jTabbedPane1.remove(0);
//        }else{
            
            for(int i=0; i<arlDatFal.size(); i++){
          
                vecReg=new Vector();
                vecReg.add(INT_TBL_LIN_FAL,"");
                vecReg.add(INT_TBL_CODEMP_FAL, objUti.getStringValueAt(arlDatFal, i, 0) );
                vecReg.add(INT_TBL_EMP_FAL, objUti.getStringValueAt(arlDatFal, i, 1) );
                vecReg.add(INT_TBL_CODTRA_FAL, objUti.getStringValueAt(arlDatFal, i, 2) );
                vecReg.add(INT_TBL_NOMTRA_FAL, objUti.getStringValueAt(arlDatFal, i, 3) ); 
                vecReg.add(INT_TBL_CANFAL_FAL, objUti.getStringValueAt(arlDatFal, i, 4) ); 
                vecDat.add(vecReg);
            }   
        
            objTblModFal.setData(vecDat);  
            tblDatFal.setModel(objTblModFal);
            vecDat.clear();
//        }
        
//        if(arlDatMarInc.size()==0){
//            jTabbedPane1.remove(0);
//        }else{
            
            for(int i=0; i<arlDatMarInc.size(); i++){
          
                vecReg=new Vector();
                vecReg.add(INT_TBL_LIN_MARINC,"");
                vecReg.add(INT_TBL_CODEMP_MARINC, objUti.getStringValueAt(arlDatMarInc, i, 0) );
                vecReg.add(INT_TBL_EMP_MARINC, objUti.getStringValueAt(arlDatMarInc, i, 1) );
                vecReg.add(INT_TBL_CODTRA_MARINC, objUti.getStringValueAt(arlDatMarInc, i, 2) );
                vecReg.add(INT_TBL_NOMTRA_MARINC, objUti.getStringValueAt(arlDatMarInc, i, 3) ); 
                vecReg.add(INT_TBL_CANFAL_MARINC, objUti.getStringValueAt(arlDatMarInc, i, 4) ); 
                vecDat.add(vecReg);
            }   
        
            objTblModMarInc.setData(vecDat);  
            tblDatMarInc.setModel(objTblModMarInc);
            vecDat.clear();
//        }
        
        vecReg=null;
        vecDat=null;
                
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panRptReg = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDatFal = new javax.swing.JTable();
        lblFal = new javax.swing.JLabel();
        panRptMovReg = new javax.swing.JPanel();
        chkMosMovReg = new javax.swing.JCheckBox();
        panMovReg = new javax.swing.JPanel();
        spnMovReg = new javax.swing.JScrollPane();
        tblMovReg = new javax.swing.JTable();
        panAtr = new javax.swing.JPanel();
        panSAtr = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDatAtr = new javax.swing.JTable();
        lblAtr = new javax.swing.JLabel();
        panMarInc = new javax.swing.JPanel();
        sppRptMI = new javax.swing.JSplitPane();
        panRptRegMI = new javax.swing.JPanel();
        spnDatMI = new javax.swing.JScrollPane();
        tblDatMarInc = new javax.swing.JTable();
        lblMI = new javax.swing.JLabel();
        panRptMovRegMI = new javax.swing.JPanel();
        chkMosMovMI = new javax.swing.JCheckBox();
        panMovRegMI = new javax.swing.JPanel();
        spnMovRegMI = new javax.swing.JScrollPane();
        tblMovRegMI = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        jPanel2.add(lblTit, java.awt.BorderLayout.NORTH);

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(53, 50));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(459, 300));

        panRpt.setEnabled(false);
        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.8);
        sppRpt.setOneTouchExpandable(true);

        panRptReg.setLayout(new java.awt.BorderLayout(0, 1));

        tblDatFal.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatFal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFalFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDatFal);

        panRptReg.add(spnDat, java.awt.BorderLayout.CENTER);

        lblFal.setText("Fecha de corte:");
        panRptReg.add(lblFal, java.awt.BorderLayout.PAGE_START);

        sppRpt.setTopComponent(panRptReg);

        panRptMovReg.setLayout(new java.awt.BorderLayout());

        chkMosMovReg.setText("Mostrar detalle de faltas");
        chkMosMovReg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkMosMovRegMouseClicked(evt);
            }
        });
        chkMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosMovRegActionPerformed(evt);
            }
        });
        panRptMovReg.add(chkMosMovReg, java.awt.BorderLayout.NORTH);

        panMovReg.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panMovReg.setLayout(new java.awt.BorderLayout());

        tblMovReg.setModel(new javax.swing.table.DefaultTableModel(
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
        spnMovReg.setViewportView(tblMovReg);

        panMovReg.add(spnMovReg, java.awt.BorderLayout.CENTER);

        panRptMovReg.add(panMovReg, java.awt.BorderLayout.CENTER);

        sppRpt.setBottomComponent(panRptMovReg);

        panRpt.add(sppRpt, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Faltas", panRpt);

        panAtr.setLayout(new java.awt.BorderLayout());

        panSAtr.setLayout(new java.awt.BorderLayout());

        tblDatAtr.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDatAtr);

        panSAtr.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        lblAtr.setText("Fecha de corte:");
        panSAtr.add(lblAtr, java.awt.BorderLayout.PAGE_START);

        panAtr.add(panSAtr, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Atrasos", panAtr);

        panMarInc.setLayout(new java.awt.BorderLayout());

        sppRptMI.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRptMI.setResizeWeight(0.8);
        sppRptMI.setOneTouchExpandable(true);

        panRptRegMI.setLayout(new java.awt.BorderLayout(0, 1));

        tblDatMarInc.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatMarInc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatMarIncFocusGained(evt);
            }
        });
        spnDatMI.setViewportView(tblDatMarInc);

        panRptRegMI.add(spnDatMI, java.awt.BorderLayout.CENTER);

        lblMI.setText("Fecha de corte:");
        panRptRegMI.add(lblMI, java.awt.BorderLayout.PAGE_START);

        sppRptMI.setTopComponent(panRptRegMI);

        panRptMovRegMI.setLayout(new java.awt.BorderLayout());

        chkMosMovMI.setText("Mostrar detalle de marcaciones incompletas");
        chkMosMovMI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkMosMovMIMouseClicked(evt);
            }
        });
        chkMosMovMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosMovMIActionPerformed(evt);
            }
        });
        panRptMovRegMI.add(chkMosMovMI, java.awt.BorderLayout.NORTH);

        panMovRegMI.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panMovRegMI.setLayout(new java.awt.BorderLayout());

        tblMovRegMI.setModel(new javax.swing.table.DefaultTableModel(
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
        spnMovRegMI.setViewportView(tblMovRegMI);

        panMovRegMI.add(spnMovRegMI, java.awt.BorderLayout.CENTER);

        panRptMovRegMI.add(panMovRegMI, java.awt.BorderLayout.CENTER);

        sppRptMI.setBottomComponent(panRptMovRegMI);

        panMarInc.add(sppRptMI, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Marcaciones Incompletas", panMarInc);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel4.add(butCan);

        panBot.add(jPanel4, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        jPanel2.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        setBounds(800, 600, 460, 410);
    }// </editor-fold>//GEN-END:initComponents
   
        
  
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
            dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void chkMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMovRegActionPerformed
        if (chkMosMovReg.isSelected()){
            cargarMovReg();
        }else{
            objTblModDetFal.removeAllRows();
        }
    }//GEN-LAST:event_chkMosMovRegActionPerformed

    /**
     * Esta función permite cargar el movimiento del item seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarMovReg(){
        boolean blnRes=true;
        int intFilSel=-1;
//        String strCodEmp="";
//        String strCodTra="";
        String strSql="";
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocSue = null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstLocSue = null;
        double dblTot  =0;
        try{
            intFilSel=tblDatFal.getSelectedRow();
            if (intFilSel!=-1){
                
                String strCodEmp=objTblModFal.getValueAt(tblDatFal.getSelectedRow(), INT_TBL_CODEMP_FAL).toString();
                String strCodTra=objTblModFal.getValueAt(tblDatFal.getSelectedRow(), INT_TBL_CODTRA_FAL).toString();
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                
                if (con!=null){
                    stmLoc=con.createStatement();
                    vecDat=new Vector();    //Almacena los datos
                    //strSql= "select * from tbh_traemp where co_emp = "+strCodEmp+" and co_tra = "+strCodTra + " order by co_reg asc";
                    strSql+="select c.co_emp, e.tx_nom as empresa, c.co_tra, a.fe_dia , (b.tx_ape || ' ' || b.tx_nom) as empleado from tbm_cabconasitra a " +"\n";
                    strSql+="inner join tbm_tra b on (b.co_tra=a.co_tra) " +"\n";
                    strSql+="inner join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A') " +"\n";
                    strSql+="inner join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " +"\n";
                    strSql+="inner join tbm_emp e on (e.co_emp=c.co_emp) " +"\n";
                    strSql+="where a.fe_dia between  (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " +"\n";
                    strSql+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " +"\n";
                    strSql+="and (a.ho_ent is null and a.ho_sal is null) " +"\n";
                    strSql+="and a.st_jusfal is null " +"\n";
                    strSql+="and c.co_emp = " +strCodEmp+"\n";
                    strSql+="and c.co_tra = " +strCodTra+"\n";
                    strSql+="AND NOT (EXTRACT(DOW FROM a.fe_dia ) in (6,7))" + "\n";
                    strSql+="order by fe_dia " +"\n";
                    rstLoc=stmLoc.executeQuery(strSql);
                    
                    /*
                     private final int INT_TBL_LIN_DET_FAL=0;                        //Línea
                    private final int INT_TBL_CODEMP_DET_FAL=1;                     // código de empresa
                    private final int INT_TBL_EMP_DET_FAL=2;                        // empresa
                    private final int INT_TBL_FECHA_DET_FAL=3;                      // fecha
                    private final int INT_TBL_CODTRA_DET_FAL=4;                     // código del empleado
                    private final int INT_TBL_NOMTRA_DET_FAL=5;                     // empleado
                     */
                    
                    while(rstLoc.next()){
                        
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_LIN_DET_FAL,"");
                        vecReg.add(INT_TBL_CODEMP_DET_FAL,rstLoc.getInt("co_emp"));
                        vecReg.add(INT_TBL_EMP_DET_FAL,rstLoc.getString("empresa"));
                        vecReg.add(INT_TBL_FECHA_DET_FAL,rstLoc.getString("fe_dia"));
                        vecReg.add(INT_TBL_CODTRA_DET_FAL,rstLoc.getInt("co_tra"));
                        vecReg.add(INT_TBL_NOMTRA_DET_FAL,rstLoc.getString("empleado"));
                        vecDat.add(vecReg);
                    }
                    //Asignar vectores al modelo.
                    objTblModDetFal.setData(vecDat);
                    tblMovReg.setModel(objTblModDetFal);
//                    vecDatMov.clear();
                    vecDat.clear();
                }else{
                    objTblModDetFal.removeAllRows();
                }
            }
        }catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
        try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
        try{rstLocSue.close();rstLocSue=null;}catch(Throwable ignore){}
        try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
        try{stmLocSue.close();stmLocSue=null;}catch(Throwable ignore){}
        try{con.close();con=null;}catch(Throwable ignore){}
        }
        
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el movimiento del item seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarMovRegMI(){
        boolean blnRes=true;
        int intFilSel=-1;
//        String strCodEmp="";
//        String strCodTra="";
        String strSql="";
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocSue = null;
        java.sql.ResultSet rstLoc = null;
        java.sql.ResultSet rstLocSue = null;
        double dblTot  =0;
        try{
            intFilSel=tblDatMarInc.getSelectedRow();
            if (intFilSel!=-1){
                
                String strCodEmp=objTblModMarInc.getValueAt(tblDatMarInc.getSelectedRow(), INT_TBL_CODEMP_MARINC).toString();
                String strCodTra=objTblModMarInc.getValueAt(tblDatMarInc.getSelectedRow(), INT_TBL_CODTRA_MARINC).toString();
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                
                if (con!=null){
                    stmLoc=con.createStatement();
                    vecDat=new Vector();    //Almacena los datos
                    //strSql= "select * from tbh_traemp where co_emp = "+strCodEmp+" and co_tra = "+strCodTra + " order by co_reg asc";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSql+="select c.co_emp, e.tx_nom as empresa, c.co_tra, a.fe_dia , (b.tx_ape || ' ' || b.tx_nom) as empleado , a.ho_ent , a.ho_sal from tbm_cabconasitra a " +"\n";
                        strSql+="left outer join tbm_tra b on (b.co_tra=a.co_tra) " +"\n";
                        strSql+="left outer join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " +"\n";
                        strSql+="left outer join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " +"\n";
                        strSql+="left outer join tbm_emp e on (e.co_emp=c.co_emp) " +"\n";
                        strSql+="where a.fe_dia between  (select fe_des from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " +"\n";
                        strSql+="and (select fe_has from tbm_feccorrolpag where co_emp=1 and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " +"\n";
                        strSql+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                        strSql+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                        strSql+="and c.co_emp = " +strCodEmp+"\n";
                        strSql+="and c.co_tra = " +strCodTra+"\n";
                        strSql+="order by fe_dia " +"\n";
                    }else{
                        strSql+="select c.co_emp, e.tx_nom as empresa, c.co_tra, a.fe_dia , (b.tx_ape || ' ' || b.tx_nom) as empleado , a.ho_ent , a.ho_sal from tbm_cabconasitra a " +"\n";
                        strSql+="left outer join tbm_tra b on (b.co_tra=a.co_tra) " +"\n";
                        strSql+="left outer join tbm_traemp c on (c.co_tra=b.co_tra and c.st_reg='A'  and c.st_horfij not like 'N') " +"\n";
                        strSql+="left outer join tbm_callab d on (d.st_dialab='S' and d.co_hor=c.co_hor and d.fe_dia=a.fe_dia) " +"\n";
                        strSql+="left outer join tbm_emp e on (e.co_emp=c.co_emp) " +"\n";
                        strSql+="where a.fe_dia between  (select fe_des from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+" and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=1) " +"\n";
                        strSql+="and (select fe_has from tbm_feccorrolpag where co_emp="+objParSis.getCodigoEmpresa()+"  and ne_ani=EXTRACT(year from current_date) and ne_mes=EXTRACT(month from current_date) and ne_per=2) " +"\n";
                        strSql+="and  ( (a.ho_ent is not null and a.ho_sal is null) " + "\n";
                        strSql+="or (a.ho_ent is null and a.ho_sal is not null))  " + "\n";
                        strSql+="and c.co_emp = " +strCodEmp+"\n";
                        strSql+="and c.co_tra = " +strCodTra+"\n";
                        strSql+="order by fe_dia " +"\n";
                    }
                    
                    rstLoc=stmLoc.executeQuery(strSql);
                    
                    /*
                     private final int INT_TBL_LIN_DET_FAL=0;                        //Línea
                    private final int INT_TBL_CODEMP_DET_FAL=1;                     // código de empresa
                    private final int INT_TBL_EMP_DET_FAL=2;                        // empresa
                    private final int INT_TBL_FECHA_DET_FAL=3;                      // fecha
                    private final int INT_TBL_CODTRA_DET_FAL=4;                     // código del empleado
                    private final int INT_TBL_NOMTRA_DET_FAL=5;                     // empleado
                     */
                    
                    while(rstLoc.next()){
                        
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_LIN_DET_MI,"");
                        vecReg.add(INT_TBL_CODEMP_DET_MI,rstLoc.getInt("co_emp"));
                        vecReg.add(INT_TBL_EMP_DET_MI,rstLoc.getString("empresa"));
                        vecReg.add(INT_TBL_FECHA_DET_MI,rstLoc.getString("fe_dia"));
                        vecReg.add(INT_TBL_CODTRA_DET_MI,rstLoc.getInt("co_tra"));
                        vecReg.add(INT_TBL_NOMTRA_DET_MI,rstLoc.getString("empleado"));
                        vecReg.add(INT_TBL_NOMTRA_DET_HOENT,rstLoc.getString("ho_ent"));
                        vecReg.add(INT_TBL_NOMTRA_DET_HOSAL,rstLoc.getString("ho_sal"));
                        vecDat.add(vecReg);
                    }
                    //Asignar vectores al modelo.
                    objTblModDetMarInc.setData(vecDat);
                    tblMovRegMI.setModel(objTblModDetMarInc);
//                    vecDatMov.clear();
                    vecDat.clear();
                }else{
                    objTblModDetMarInc.removeAllRows();
                }
            }
        }catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }finally {
        try{rstLoc.close();rstLoc=null;}catch(Throwable ignore){}
        try{rstLocSue.close();rstLocSue=null;}catch(Throwable ignore){}
        try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
        try{stmLocSue.close();stmLocSue=null;}catch(Throwable ignore){}
        try{con.close();con=null;}catch(Throwable ignore){}
        }
        
        return blnRes;
    }
    
    private void tblDatFalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFalFocusGained
       
    }//GEN-LAST:event_tblDatFalFocusGained

    private void tblDatMarIncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatMarIncFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDatMarIncFocusGained

    private void chkMosMovMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMovMIActionPerformed
        // TODO add your handling code here:
        if (chkMosMovMI.isSelected()){
            cargarMovRegMI();
        }else{
            objTblModDetMarInc.removeAllRows();
        }
    }//GEN-LAST:event_chkMosMovMIActionPerformed

    private void chkMosMovRegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkMosMovRegMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosMovRegMouseClicked

    private void chkMosMovMIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkMosMovMIMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosMovMIMouseClicked
    
      
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JCheckBox chkMosMovMI;
    private javax.swing.JCheckBox chkMosMovReg;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAtr;
    private javax.swing.JLabel lblFal;
    private javax.swing.JLabel lblMI;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panAtr;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panMarInc;
    private javax.swing.JPanel panMovReg;
    private javax.swing.JPanel panMovRegMI;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptMovReg;
    private javax.swing.JPanel panRptMovRegMI;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JPanel panRptRegMI;
    private javax.swing.JPanel panSAtr;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDatMI;
    private javax.swing.JScrollPane spnMovReg;
    private javax.swing.JScrollPane spnMovRegMI;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JSplitPane sppRptMI;
    private javax.swing.JTable tblDatAtr;
    public javax.swing.JTable tblDatFal;
    public javax.swing.JTable tblDatMarInc;
    private javax.swing.JTable tblMovReg;
    private javax.swing.JTable tblMovRegMI;
    // End of variables declaration//GEN-END:variables
    
}