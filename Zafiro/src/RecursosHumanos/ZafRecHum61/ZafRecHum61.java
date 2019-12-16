
/**
 * ZafRecHum61.java
 * 
 * Created on 18 de Marzo del 2016, 09:20
 */
package RecursosHumanos.ZafRecHum61;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafRecHum61 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable
    static final int INT_TBL_DAT_LIN = 0;                       //Linea
    static final int INT_TBL_DAT_COD_EMP = 1;                   //Código de la empresa.
    static final int INT_TBL_DAT_NOM_EMP = 2;                   //Nombre de la empresa.
    static final int INT_TBL_DAT_COD_TRA = 3;                   //Código del trabajador.
    static final int INT_TBL_DAT_NOM_TRA = 4;                   //Nombre del trabajador.
    static final int INT_TBL_DAT_TIP_PRV = 5;                   //Tipo de Provisión.
    static final int INT_TBL_DAT_COD_PLA_TRA = 6;               //Código Plantilla del Trabajador.
    static final int INT_TBL_DAT_DESCOR_PLA_TRA = 7;            //Descripción Corta Plantilla del Trabajador.
    static final int INT_TBL_DAT_VAL_TOTING = 8;                //Total de Ingresos.
    static final int INT_TBL_DAT_DIA_LAB = 9;                   //Días Laborados por el trabajador.
    static final int INT_TBL_DAT_SBU = 10;                      //Suedo Básico Unificado.
    static final int INT_TBL_DAT_POR_FONRES = 11;               //Porcentaje de Fondos de Reserva.
    static final int INT_TBL_DAT_POR_APOPAT = 12;               //Porcentaje de Aporte Patronal.
    static final int INT_TBL_DAT_VAL_DTS = 13;                  //Valor de Décima Tercera remuneración.
    static final int INT_TBL_DAT_VAL_DCS = 14;                  //Valor de Décima Cuarta remuneración.
    static final int INT_TBL_DAT_VAL_FONRES = 15;               //Valor Fondos de Reserva.
    static final int INT_TBL_DAT_VAL_VAC = 16;                  //Valor de Vacaciones.
    static final int INT_TBL_DAT_VAL_APOPAT = 17;               //Valor de Aporte Patronal.
    
    private String strVersion="v0.4";                           //Versión del Programa.                    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafVenCon vcoDpto;                                  //Ventana de consulta.
    private ZafVenCon vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon vcoTra;                                   //Ventana de consulta.
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private Vector vecDat, vecCab, vecReg, vecEstReg;
    private String strSQL, strAux;
    private String strCodDpto, strDpto;                         //Contenido del campo al obtener el foco.
    private String strCodEmp, strEmp;                           //Contenido del campo al obtener el foco.
    private String strCodTra, strNomTra;                        //Contenido del campo al obtener el foco.
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafRecHum61(ZafParSis obj) 
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
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);

            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3576)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3577)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar los JTables.
            configurarTblDat();
            
            //Configurar Combos.
            cargarPeriodo();
            configurarComboEstReg();
            
            //Configurar las ZafVenCon.
            configurarVenConEmp();
            configurarVenConDpto();
            configurarVenConTra();
            
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
            vecCab = new Vector(18);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_NOM_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_TIP_PRV,"Tipo Provisión");
            vecCab.add(INT_TBL_DAT_COD_PLA_TRA,"Cód.Pla.");
            vecCab.add(INT_TBL_DAT_DESCOR_PLA_TRA,"Plantilla");
            vecCab.add(INT_TBL_DAT_VAL_TOTING,"Tot.Ing.");
            vecCab.add(INT_TBL_DAT_DIA_LAB,"Dias Lab.");
            vecCab.add(INT_TBL_DAT_SBU,"SBU");
            vecCab.add(INT_TBL_DAT_POR_FONRES,"% Fon.Res.");
            vecCab.add(INT_TBL_DAT_POR_APOPAT,"% Apo.Pat.");
            vecCab.add(INT_TBL_DAT_VAL_DTS,"Décima Tercera Remuneración");
            vecCab.add(INT_TBL_DAT_VAL_DCS,"Décima Cuarta Remuneración");
            vecCab.add(INT_TBL_DAT_VAL_FONRES,"Fondos Reserva");
            vecCab.add(INT_TBL_DAT_VAL_VAC,"Vacaciones");
            vecCab.add(INT_TBL_DAT_VAL_APOPAT,"Aporte Patronal");
             
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
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TRA).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_TIP_PRV).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_PLA_TRA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR_PLA_TRA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOTING).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DIA_LAB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SBU).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_POR_FONRES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_APOPAT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DTS).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DCS).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FONRES).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VAC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APOPAT).setPreferredWidth(90);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);    
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_PRV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_PLA_TRA, tblDat);
            //Solicitado por Juan Rodas que se visualice la plantilla para motivos de auditoría.
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESCOR_PLA_TRA, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Agrupar Columnas.
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);

            //Grupo: Datos Generales.
            ZafTblHeaColGrp objTblHeaColGrpDatGen=new ZafTblHeaColGrp("Datos Generales");
            objTblHeaColGrpDatGen.setHeight(16);
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_NOM_EMP));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_COD_TRA));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_NOM_TRA));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_TIP_PRV));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_COD_PLA_TRA));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_DESCOR_PLA_TRA));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOTING));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_DIA_LAB));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_SBU));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_POR_FONRES));
            objTblHeaColGrpDatGen.add(tcmAux.getColumn(INT_TBL_DAT_POR_APOPAT));
            
            //Grupo: Beneficios Sociales.
            ZafTblHeaColGrp objTblHeaColGrpBenSoc=new ZafTblHeaColGrp("Beneficios Sociales");
            objTblHeaColGrpBenSoc.setHeight(16);
            objTblHeaColGrpBenSoc.add(tcmAux.getColumn(INT_TBL_DAT_VAL_DTS));
            objTblHeaColGrpBenSoc.add(tcmAux.getColumn(INT_TBL_DAT_VAL_DCS));
            objTblHeaColGrpBenSoc.add(tcmAux.getColumn(INT_TBL_DAT_VAL_FONRES));
            objTblHeaColGrpBenSoc.add(tcmAux.getColumn(INT_TBL_DAT_VAL_VAC));
            objTblHeaColGrpBenSoc.add(tcmAux.getColumn(INT_TBL_DAT_VAL_APOPAT));
            
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDatGen);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpBenSoc);
                        
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_TOTING, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_SBU, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_POR_FONRES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_POR_APOPAT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DTS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DCS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_FONRES, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_VAC, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_APOPAT, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);    
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_TIP_PRV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DESCOR_PLA_TRA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_DIA_LAB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_FONRES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_APOPAT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOTING).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SBU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DTS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DCS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FONRES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_APOPAT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_TOTING, INT_TBL_DAT_VAL_DTS, INT_TBL_DAT_VAL_DCS, INT_TBL_DAT_VAL_FONRES, INT_TBL_DAT_VAL_VAC, INT_TBL_DAT_VAL_APOPAT};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_TIP_PRV:
                    strMsg="Tipo de Provisión";
                    break;
                case INT_TBL_DAT_COD_PLA_TRA:
                    strMsg="Código de Plantilla";
                    break;
                case INT_TBL_DAT_DESCOR_PLA_TRA:
                    strMsg="Plantilla del Trabajador";
                    break;
                case INT_TBL_DAT_VAL_TOTING:
                    strMsg="<HTML>Total de Ingresos <BR>Valor Base Tot.Ing. = Sue+H1+H2+Com</BR></HTML>";
                    break;
                case INT_TBL_DAT_DIA_LAB:
                    strMsg="Días laborados por el empleado";
                    break;
                case INT_TBL_DAT_SBU:
                    strMsg="Salario básico unificado";
                    break;
                case INT_TBL_DAT_POR_FONRES:
                    strMsg="Porcentaje fondos de reserva";
                    break;
                case INT_TBL_DAT_POR_APOPAT:
                    strMsg="Porcentaje aporte patronal al IESS";
                    break;
                case INT_TBL_DAT_VAL_DTS:
                    strMsg="Décima Tercera Remuneración";
                    break;
                case INT_TBL_DAT_VAL_DCS:
                    strMsg="Décima Cuarta Remuneración";
                    break;    
                case INT_TBL_DAT_VAL_FONRES:
                    strMsg="Fondos Reserva";
                    break;
                case INT_TBL_DAT_VAL_VAC:
                    strMsg="Vacaciones";
                    break;
                case INT_TBL_DAT_VAL_APOPAT:
                    strMsg="Aporte Patronal";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función permite cargar el ultimo período.(Anio y Mes).
     * @return true: Si se pudo consultar el período.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarPeriodo()
    {
        boolean blnRes=true;
        boolean blnUltFec = true;
        String  strAnio;
        int intMes=0 ;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT ne_ani,ne_mes ";
                strSQL+=" FROM tbm_ingegrmentra ";
                strSQL+=" GROUP BY ne_ani,ne_mes ";
                strSQL+=" ORDER BY ne_ani desc, ne_mes desc ";
                
                //System.out.println("cargarPeriodo: "+strSQL);
                rst = stm.executeQuery(strSQL);
                DefaultComboBoxModel model = new DefaultComboBoxModel();//Creo un model para luego cargarlo en un combobox.
                
                //Obtener los registros.
                while (rst.next())
                {
                    if (blnUltFec) 
                    {
                        intMes= ((rst.getInt("ne_mes")-1)==0?12:(rst.getInt("ne_mes")-1)) ;
                        blnUltFec = false;
                    }
                    strAnio = rst.getString("ne_ani");
                    
                    //Pregunto si existe el anio para que no se repita al momento de llenarlo en el combobox
                    if (model.getIndexOf(strAnio) == -1)  
                    {
                        model.addElement(strAnio);
                        cboPerAAAA.addItem(strAnio);
                    }
                }
                cboPerMes.setSelectedIndex(intMes);
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)  {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {    blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }     
    
    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("Activos");
            cboEstReg.addItem("Inactivos");
            cboEstReg.setSelectedIndex(0);   
        } 
        catch (Exception e) {     blnRes = false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    private boolean configurarVenConEmp()
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
            arlAncCol.add("374");
            
            //Armar la sentencia SQL.
            strSQL ="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                strSQL ="";
                strSQL+=" SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom";
                strSQL+=" FROM tbm_Emp AS a1";
                strSQL+=" WHERE a1.st_reg like 'A' and a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" ORDER BY a1.co_emp";
            }
            else
            {
                strSQL+=" SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom";
                strSQL+=" FROM tbm_Emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            }
            //System.out.println("configurarVenConEmp: " + strSQL);
       
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    private boolean configurarVenConDpto() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_dep");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            arlCam.add("a.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Est.Reg.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("150");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL ="";   
            strSQL+=" SELECT co_dep, tx_descor, tx_deslar, st_reg  ";
            strSQL+=" FROM tbm_dep ";
            strSQL+=" WHERE st_reg like 'A' ";
            strSQL+=" ORDER BY co_dep ";
           
            //System.out.println("configurarVenConDpto: " + strSQL);
   
            vcoDpto = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Departamentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoDpto.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            
        } 
        catch (Exception e) {      blnRes = false;        objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }  
    
    private boolean configurarVenConTra() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tra");
            arlCam.add("a.tx_nomTra");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            
            //Armar la sentencia SQL.
            strSQL ="";            
            strSQL+=" SELECT a.co_tra,(a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra ";
            strSQL+=" FROM tbm_tra a ";
            strSQL+=" INNER JOIN tbm_traemp b ON(a.co_tra=b.co_tra) ";
            strSQL+=" WHERE b.st_reg like 'A' ";
            strSQL+=" AND b.co_emp<>"+objParSis.getCodigoEmpresaGrupo();
            
            if(txtCodEmp.getText().length()>0)
               strSQL+=" AND b.co_emp ="+txtCodEmp.getText(); 
            else if (objParSis.getCodigoEmpresa()!= objParSis.getCodigoEmpresaGrupo())
               strSQL+=" AND b.co_emp =" + objParSis.getCodigoEmpresa();
            
            if(txtCodDpto.getText().length()>0)
               strSQL+=" AND b.co_dep ="+txtCodDpto.getText(); 
            
            strSQL+=" ORDER BY tx_nomTra ";
            
            //System.out.println("configurarVenConTra: " + strSQL);
            //Ocultar columnas.
            //int intColOcu[]=new int[1];
            //intColOcu[0]=1;  // Código
   
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoTra.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } 
        catch (Exception e) {      blnRes = false;     objUti.mostrarMsgErr_F1(this, e);     }
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
    private boolean mostrarVenConEmp(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(1);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) 
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtEmp.setText(vcoEmp.getValueAt(2));
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtEmp.setText(vcoEmp.getValueAt(2));
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    }
                    else 
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) 
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtEmp.setText(vcoEmp.getValueAt(2));
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        } 
                        else 
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoEmp.buscar("a.tx_nom", txtEmp.getText())) 
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtEmp.setText(vcoEmp.getValueAt(2));
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    } 
                    else 
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) 
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtEmp.setText(vcoEmp.getValueAt(2));
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        } 
                        else 
                        {
                            txtEmp.setText(strEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {  blnRes = false;    objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    private boolean mostrarVenConDpto(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDpto.setCampoBusqueda(1);
                    vcoDpto.show();
                    if (vcoDpto.getSelectedButton() == vcoDpto.INT_BUT_ACE) 
                    {
                        txtCodDpto.setText(vcoDpto.getValueAt(1));
                        txtDpto.setText(vcoDpto.getValueAt(3));
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoDpto.buscar("a.co_dep", txtCodDpto.getText()))
                    {
                        txtCodDpto.setText(vcoDpto.getValueAt(1));
                        txtDpto.setText(vcoDpto.getValueAt(3));
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    }
                    else 
                    {
                        vcoDpto.setCampoBusqueda(0);
                        vcoDpto.setCriterio1(11);
                        vcoDpto.cargarDatos();
                        vcoDpto.show();
                        if (vcoDpto.getSelectedButton() == vcoDpto.INT_BUT_ACE) 
                        {
                            txtCodDpto.setText(vcoDpto.getValueAt(1));
                            txtDpto.setText(vcoDpto.getValueAt(3));
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        } 
                        else 
                        {
                            txtCodDpto.setText(strCodDpto);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoDpto.buscar("a.tx_deslar", txtDpto.getText())) 
                    {
                        txtCodDpto.setText(vcoDpto.getValueAt(1));
                        txtDpto.setText(vcoDpto.getValueAt(3));
                        txtCodTra.setText("");
                        txtNomTra.setText("");
                    } 
                    else 
                    {
                        vcoDpto.setCampoBusqueda(1);
                        vcoDpto.setCriterio1(11);
                        vcoDpto.cargarDatos();
                        vcoDpto.show();
                        if (vcoDpto.getSelectedButton() == vcoDpto.INT_BUT_ACE) 
                        {
                            txtCodDpto.setText(vcoDpto.getValueAt(1));
                            txtDpto.setText(vcoDpto.getValueAt(3));
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        } 
                        else 
                        {
                            txtDpto.setText(strDpto);
                        }
                    }
                    break;
                  
            }
        }
        catch (Exception e) {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    private boolean mostrarVenConTra(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) 
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoTra.buscar("a.co_tra", txtCodTra.getText()))
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2));
                    }
                    else 
                    {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) 
                        {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2));
                        } 
                        else 
                        {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoTra.buscar("a.tx_nomTra", txtNomTra.getText())) 
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2));
                    } 
                    else 
                    {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) 
                        {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2));
                        } 
                        else 
                        {
                            txtNomTra.setText(strNomTra);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {    blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
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
        panFil = new javax.swing.JPanel();
        lblPer = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblDpto = new javax.swing.JLabel();
        txtCodDpto = new javax.swing.JTextField();
        txtDpto = new javax.swing.JTextField();
        butDpto = new javax.swing.JButton();
        lblTra = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        chkTraNoMen = new javax.swing.JCheckBox();
        chkTraMenDTS = new javax.swing.JCheckBox();
        chkTraMenDCS = new javax.swing.JCheckBox();
        chkTraMenFonResRolPag = new javax.swing.JCheckBox();
        chkTraMenFonResIess = new javax.swing.JCheckBox();
        cboEstReg = new javax.swing.JComboBox();
        lblEstReg = new javax.swing.JLabel();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat =  new javax.swing.JTable()
        {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
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

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setLayout(null);

        lblPer.setText("Periodo:");
        panFil.add(lblPer);
        lblPer.setBounds(20, 20, 70, 20);

        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panFil.add(cboPerAAAA);
        cboPerAAAA.setBounds(100, 20, 80, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cboPerMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerMesActionPerformed(evt);
            }
        });
        panFil.add(cboPerMes);
        cboPerMes.setBounds(180, 20, 120, 20);

        optTod.setSelected(true);
        optTod.setText("Todos los empleados");
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
        panFil.add(optTod);
        optTod.setBounds(20, 50, 400, 20);

        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 70, 400, 20);

        lblEmp.setText("Empresa:");
        panFil.add(lblEmp);
        lblEmp.setBounds(60, 104, 90, 14);

        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(160, 100, 50, 20);

        txtEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmpFocusLost(evt);
            }
        });
        txtEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpActionPerformed(evt);
            }
        });
        panFil.add(txtEmp);
        txtEmp.setBounds(210, 100, 340, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(550, 100, 20, 20);

        lblDpto.setText("Departamento:");
        panFil.add(lblDpto);
        lblDpto.setBounds(60, 120, 90, 20);

        txtCodDpto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDptoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDptoFocusLost(evt);
            }
        });
        txtCodDpto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDptoActionPerformed(evt);
            }
        });
        panFil.add(txtCodDpto);
        txtCodDpto.setBounds(160, 120, 50, 20);

        txtDpto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDptoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDptoFocusLost(evt);
            }
        });
        txtDpto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDptoActionPerformed(evt);
            }
        });
        panFil.add(txtDpto);
        txtDpto.setBounds(210, 120, 340, 20);

        butDpto.setText("...");
        butDpto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDptoActionPerformed(evt);
            }
        });
        panFil.add(butDpto);
        butDpto.setBounds(550, 120, 20, 20);

        lblTra.setText("Empleado:");
        panFil.add(lblTra);
        lblTra.setBounds(60, 140, 90, 20);

        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(160, 140, 50, 20);

        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(210, 140, 340, 20);

        butTra.setText("...");
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(550, 140, 20, 20);

        chkTraNoMen.setText("Mostrar sólo empleados que no mensualizan décimos.");
        chkTraNoMen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkTraNoMenStateChanged(evt);
            }
        });
        panFil.add(chkTraNoMen);
        chkTraNoMen.setBounds(40, 170, 470, 23);

        chkTraMenDTS.setText("Mostrar sólo empleados que reciben DTS Mensualizado.");
        chkTraMenDTS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkTraMenDTSStateChanged(evt);
            }
        });
        panFil.add(chkTraMenDTS);
        chkTraMenDTS.setBounds(40, 190, 480, 23);

        chkTraMenDCS.setText("Mostrar sólo empleados que reciben DCS Mensualizado.");
        chkTraMenDCS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkTraMenDCSStateChanged(evt);
            }
        });
        panFil.add(chkTraMenDCS);
        chkTraMenDCS.setBounds(40, 210, 480, 23);

        chkTraMenFonResRolPag.setText("Mostrar sólo empleados que reciben Fondos de Reserva por Rol de Pagos.");
        chkTraMenFonResRolPag.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkTraMenFonResRolPagStateChanged(evt);
            }
        });
        panFil.add(chkTraMenFonResRolPag);
        chkTraMenFonResRolPag.setBounds(40, 230, 480, 23);

        chkTraMenFonResIess.setText("Mostrar sólo empleados que reciben Fondos de Reserva por IESS.");
        chkTraMenFonResIess.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkTraMenFonResIessStateChanged(evt);
            }
        });
        panFil.add(chkTraMenFonResIess);
        chkTraMenFonResIess.setBounds(40, 250, 480, 23);

        cboEstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstRegActionPerformed(evt);
            }
        });
        panFil.add(cboEstReg);
        cboEstReg.setBounds(190, 280, 152, 20);

        lblEstReg.setText("Estado del empleado:");
        lblEstReg.setToolTipText("Estado del registro:");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(50, 280, 130, 16);

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

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

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodEmp.setText("");
            txtEmp.setText("");
            txtCodDpto.setText("");
            txtDpto.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");
            
            chkTraNoMen.setSelected(false);
            chkTraMenDTS.setSelected(false);
            chkTraMenDCS.setSelected(false);
            chkTraMenFonResRolPag.setSelected(false);
            chkTraMenFonResIess.setSelected(false);
            
            cboEstReg.setSelectedIndex(0);
            
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) 
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtEmp.setText("");
            } 
            else
            {
                mostrarVenConEmp(1);
            }
        } 
        else
        {
            txtCodEmp.setText(strCodEmp);
        }
        
        if (txtCodEmp.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected())
            optTod.setSelected(false);
        else
            optTod.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
            optFil.setSelected(false);
        else
            optFil.setSelected(true);
    }//GEN-LAST:event_optTodActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpFocusLost
        if (!txtEmp.getText().equalsIgnoreCase(strEmp)) 
        {
            if (txtEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtEmp.setText("");
            } 
            else
            {
                mostrarVenConEmp(2);
            }
        } 
        else
        {
            txtEmp.setText(strEmp);
        }
        
        if (txtEmp.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }   
    }//GEN-LAST:event_txtEmpFocusLost

    private void txtEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpActionPerformed
        txtEmp.transferFocus();
    }//GEN-LAST:event_txtEmpActionPerformed

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
        if (txtCodEmp.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butEmpActionPerformed

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

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpFocusGained
        strEmp = txtEmp.getText();
        txtEmp.selectAll();
    }//GEN-LAST:event_txtEmpFocusGained

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) 
        {
            if (txtCodTra.getText().equals(""))
            {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } 
            else
            {
                mostrarVenConTra(1);
            }
        } 
        else
        {
            txtCodTra.setText(strCodTra);
        }
        
        if (txtCodTra.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        configurarVenConTra();
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) 
        {
            if (txtNomTra.getText().equals(""))
            {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } 
            else
            {
                mostrarVenConTra(2);
            }
        } 
        else
        {
            txtNomTra.setText(strNomTra);
        }
        
        if (txtNomTra.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        configurarVenConTra();
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        configurarVenConTra();
        mostrarVenConTra(0);
        if (txtCodTra.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butTraActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed
        limpiarJtableDat();
    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private void txtCodDptoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDptoFocusGained
        strCodDpto=txtCodDpto.getText();
        txtCodDpto.selectAll();
    }//GEN-LAST:event_txtCodDptoFocusGained

    private void txtCodDptoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDptoFocusLost
        if (!txtCodDpto.getText().equalsIgnoreCase(strCodDpto)) 
        {
            if (txtCodDpto.getText().equals(""))
            {
                txtCodDpto.setText("");
                txtDpto.setText("");
            } 
            else
            {
                mostrarVenConDpto(1);
            }
        } 
        else
        {
            txtCodDpto.setText(strCodDpto);
        }
        
        if (txtCodDpto.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodDptoFocusLost

    private void txtCodDptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDptoActionPerformed
        txtCodDpto.transferFocus();
    }//GEN-LAST:event_txtCodDptoActionPerformed

    private void txtDptoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDptoFocusGained
        strDpto = txtDpto.getText();
        txtDpto.selectAll();
    }//GEN-LAST:event_txtDptoFocusGained

    private void txtDptoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDptoFocusLost
        if (!txtDpto.getText().equalsIgnoreCase(strDpto)) 
        {
            if (txtDpto.getText().equals(""))
            {
                txtCodDpto.setText("");
                txtDpto.setText("");
            } 
            else
            {
                mostrarVenConDpto(2);
            }
        } 
        else
        {
            txtDpto.setText(strDpto);
        }
        
        if (txtDpto.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }   
    }//GEN-LAST:event_txtDptoFocusLost

    private void txtDptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDptoActionPerformed
        txtDpto.transferFocus();
    }//GEN-LAST:event_txtDptoActionPerformed

    private void butDptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDptoActionPerformed
        mostrarVenConDpto(0);
        if (txtCodDpto.getText().length()>0 )
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butDptoActionPerformed

    private void chkTraNoMenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkTraNoMenStateChanged
        chkTraMenDTS.setSelected(false);
        chkTraMenDCS.setSelected(false);
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_chkTraNoMenStateChanged

    private void chkTraMenDTSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkTraMenDTSStateChanged
        chkTraNoMen.setSelected(false);
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_chkTraMenDTSStateChanged

    private void chkTraMenFonResIessStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkTraMenFonResIessStateChanged
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_chkTraMenFonResIessStateChanged

    private void cboPerMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerMesActionPerformed
        limpiarJtableDat();
    }//GEN-LAST:event_cboPerMesActionPerformed

    private void cboEstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstRegActionPerformed
        if (cboEstReg.getSelectedIndex() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_cboEstRegActionPerformed

    private void chkTraMenDCSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkTraMenDCSStateChanged
        chkTraNoMen.setSelected(false);
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_chkTraMenDCSStateChanged

    private void chkTraMenFonResRolPagStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkTraMenFonResRolPagStateChanged
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_chkTraMenFonResRolPagStateChanged
    
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
    private javax.swing.JButton butDpto;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTra;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JCheckBox chkTraMenDCS;
    private javax.swing.JCheckBox chkTraMenDTS;
    private javax.swing.JCheckBox chkTraMenFonResIess;
    private javax.swing.JCheckBox chkTraMenFonResRolPag;
    private javax.swing.JCheckBox chkTraNoMen;
    private javax.swing.JLabel lblDpto;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTra;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodDpto;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDpto;
    private javax.swing.JTextField txtEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
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

            if (!cargarDetReg(sqlConFil())) 
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) 
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
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
        if(cboPerAAAA.getSelectedItem().toString().compareTo("Año")==0)
        {
            cboPerAAAA.requestFocus();
            cboPerAAAA.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El <FONT COLOR=\"blue\">Año</FONT> no ha sido seleccionado.<BR>Seleccione un año y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }
  
    private void limpiarJtableDat()
    {
        vecDat.clear();
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        objTblTot.calcularTotales();
        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
    }
    
    /**
     * Función que retorna los filtros seleccionados por el usuario.
     * @return sqlFil 
     */
    private String sqlConFil() 
    {
        String sqlFil = "";
        
        //Obtener la condición.
        sqlFil+=" AND a.ne_tipPro = 1";
        
        if ((txtCodEmp.getText().length() > 0) && (txtEmp.getText().length() > 0)) {
            sqlFil+=" AND b.co_emp = " + txtCodEmp.getText().replaceAll("'", "''") + "";
        }
        else if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
            sqlFil+=" AND b.co_emp = " + objParSis.getCodigoEmpresa();
        }
        
        if ((txtCodDpto.getText().length() > 0) && (txtDpto.getText().length() > 0)) {
            sqlFil+=" AND b.co_dep = " + txtCodDpto.getText().replaceAll("'", "''") + "";
        }
        
        if ((txtCodTra.getText().length() > 0) && (txtNomTra.getText().length() > 0)) {
            sqlFil+=" AND a.co_tra = " + txtCodTra.getText().replaceAll("'", "''") + "";
        }
        
        if (cboEstReg.getSelectedIndex() > 0) {
            sqlFil += " AND b.st_reg = '" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
        }
        
        if(chkTraNoMen.isSelected())
        {
            sqlFil+=" AND a.co_Tra NOT IN (select co_tra from tbm_traEmp WHERE (tx_forPagDecTerSue='M' or tx_forPagDecCuaSue='M' ))";
        }
        else
        {
            if ((chkTraMenDTS.isSelected() || chkTraMenDCS.isSelected())) 
            {
                if (chkTraMenDTS.isSelected()) {
                    sqlFil+="  AND b.tx_forPagDecTerSue='M'  ";
                }
                if (chkTraMenDCS.isSelected()) {
                    sqlFil+="  AND b.tx_forPagDecCuaSue='M'  ";
                }
            }
        }
        
        if(chkTraMenFonResRolPag.isSelected() && chkTraMenFonResIess.isSelected())
        {
           sqlFil+="  AND b.st_recFonRes ='S' ";
        }
        else
        {
            if(chkTraMenFonResRolPag.isSelected())
            {
                sqlFil+="  AND b.tx_forRecFonRes='R'  ";
            }
            else if(chkTraMenFonResIess.isSelected())
            {
                sqlFil+="  AND b.tx_forRecFonRes='I'  ";
            }
        
        }
        
        return sqlFil;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
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
                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, e.tx_nom as tx_nomEmp, a.co_Tra, (c.tx_ape ||' '|| c.tx_nom) as tx_nomTra, a.ne_tipPro, b.co_pla, f.tx_desCor as DesCorPlaTra, "+"\n";
                strSQL+="       a.nd_sue  as ValTotIng, d.DiaLab, a.nd_valSbu as SBU,  a.nd_porFonRes as PorFonRes, a.nd_porApoPatIes as PorApoPat, "+"\n";
                strSQL+="       a.nd_valDecTerSue as ValDTS, a.nd_valDecCuaSue as ValDCS,  a.nd_valFonRes as ValFonRes, a.nd_valVac as ValVac, a.nd_valApoPatIes as ValApoPatIes "+"\n";
                strSQL+=" FROM tbm_benSocMenTra as a "+"\n";
                strSQL+=" INNER JOIN tbm_traEmp as b ON (a.co_emp=b.co_Emp and a.co_Tra=b.co_tra) "+"\n";
                strSQL+=" INNER JOIN tbm_tra as c ON (b.co_Tra=c.co_tra) "+"\n";
                strSQL+=" INNER JOIN "+"\n";
                strSQL+=" ( "+"\n";
                strSQL+="   select  co_Emp, co_Tra, ne_numDiaLab as DiaLab "+"\n";
                strSQL+="   from tbm_datGenIngEgrMenTra "+"\n";
                strSQL+="   where (ne_Ani="+ cboPerAAAA.getSelectedItem().toString()+ " and ne_mes="+ cboPerMes.getSelectedIndex() +") "+"\n"; 
                strSQL+="   group by co_emp, co_Tra, DiaLab "+"\n";
                strSQL+=" ) as d ON (d.co_Emp=b.co_Emp and d.co_Tra=b.co_Tra) "+"\n";
                strSQL+=" INNER JOIN tbm_emp as e ON (b.co_emp=e.co_emp) "+"\n";
                strSQL+=" LEFT JOIN tbm_cabplarolpag as f ON (f.co_emp=b.co_emp and f.co_pla=b.co_pla) "+"\n";
                strSQL+=" WHERE (a.ne_Ani="+ cboPerAAAA.getSelectedItem().toString()+ " and a.ne_mes="+ cboPerMes.getSelectedIndex() +") "+"\n"; 
                strSQL+=strFil+"\n";
                strSQL+=" GROUP BY a.co_emp, tx_nomEmp, a.co_Tra,  tx_nomTra, a.ne_tipPro, b.co_pla, f.tx_desCor, "+"\n";
                strSQL+="          ValTotIng, d.DiaLab,  SBU,  PorFonRes,  PorApoPat, ValDTS, ValDCS,  ValFonRes, ValVac,  ValApoPatIes "+"\n";
                strSQL+=" ORDER BY a.co_emp, tx_nomTra "+" \n";

                System.out.println("cargarDetReg: "+strSQL);
                rst = stm.executeQuery(strSQL);
                
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
                        vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP, rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_TRA, rst.getString("co_Tra"));
                        vecReg.add(INT_TBL_DAT_NOM_TRA, rst.getString("tx_nomTra"));
                        vecReg.add(INT_TBL_DAT_TIP_PRV, (rst.getString("ne_tipPro").equals("1")?"ROLPAG":rst.getString("ne_tipPro").equals("2")?"PREFUN":rst.getString("ne_tipPro").equals("3")?"REEMOV":""));
                        vecReg.add(INT_TBL_DAT_COD_PLA_TRA, rst.getString("co_pla"));
                        vecReg.add(INT_TBL_DAT_DESCOR_PLA_TRA, rst.getString("DesCorPlaTra"));
                        vecReg.add(INT_TBL_DAT_VAL_TOTING, rst.getString("ValTotIng"));
                        vecReg.add(INT_TBL_DAT_DIA_LAB, rst.getString("DiaLab"));
                        vecReg.add(INT_TBL_DAT_SBU, rst.getString("SBU"));
                        vecReg.add(INT_TBL_DAT_POR_FONRES, rst.getString("PorFonRes"));
                        vecReg.add(INT_TBL_DAT_POR_APOPAT, rst.getString("PorApoPat"));
                        vecReg.add(INT_TBL_DAT_VAL_DTS, rst.getString("ValDTS"));
                        vecReg.add(INT_TBL_DAT_VAL_DCS, rst.getString("ValDCS"));
                        vecReg.add(INT_TBL_DAT_VAL_FONRES, rst.getString("ValFonRes"));
                        vecReg.add(INT_TBL_DAT_VAL_VAC, rst.getString("ValVac"));
                        vecReg.add(INT_TBL_DAT_VAL_APOPAT, rst.getString("ValApoPatIes"));
                        vecDat.add(vecReg);
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
                //Calcular totales.
                objTblTot.calcularTotales();
                vecDat.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
            }
        }
        catch (java.sql.SQLException e) {     blnRes = false;       objUti.mostrarMsgErr_F1(this, e);        }
        catch (Exception e) {   blnRes = false;     objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    
    
}
