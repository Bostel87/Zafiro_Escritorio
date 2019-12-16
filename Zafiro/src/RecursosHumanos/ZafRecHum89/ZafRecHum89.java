/*
 * ZafRecHum89.java
 *
 * Created on 01 de Febrero del 2016, 11:25
 */

package RecursosHumanos.ZafRecHum89;

import Librerias.ZafInventario.ZafInvItm;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafRecHum89 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable
    static final int INT_TBL_DAT_LIN = 0;                       //Linea
    static final int INT_TBL_DAT_COD_EMP = 1;                   //Código de la empresa.
    static final int INT_TBL_DAT_NOM_EMP = 2;                   //Nombre de la empresa.
    static final int INT_TBL_DAT_COD_TRA = 3;                   //Código del trabajador.
    static final int INT_TBL_DAT_NOM_TRA = 4;                   //Nombre del trabajador.
    static final int INT_TBL_DAT_VAL_ING = 5;                   //Valor del ingreso.
    
    private String STRVERSION="v0.2";                           //Versión del Programa.                    
    private String STRCODRUBCOM = "33";                         //Constante Código Rubro Comisiones.
    private int INTCODTIPDOCROLPAG = 192;                       //Constante Código Tipo Documento RolPag.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafVenCon vcoRub;                                   //Ventana de consulta "Rubros".
    private ZafVenCon vcoEmp;                                   //Ventana de consulta.
    private ZafVenCon vcoTra;                                   //Ventana de consulta.
    private ZafInvItm objInvItm; 
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnDat=false;                               //true: Si existen registros
    private boolean blnRub=false;                               //true: Si existen registros con los rubros.
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;
    private String strCodRub, strRub;                           //Contenido del campo al obtener el foco.
    private String strCodEmp, strEmp;                           //Contenido del campo al obtener el foco.
    private String strCodTra, strNomTra;                        //Contenido del campo al obtener el foco.
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafRecHum89(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objInvItm = new ZafInvItm(this, objParSis);
            
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
            objTblCelRenChk = new ZafTblCelRenChk();
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + STRVERSION);
            lblTit.setText(strAux);
            
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(4118)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4119)) 
            {
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4120)) 
            {
                butCer.setVisible(false);
            }
            
            //Establecer Obligatoriedad Empresa cuando se ingrese por Grupo.
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                txtCodEmp.setBackground(objParSis.getColorCamposObligatorios());
                txtEmp.setBackground(objParSis.getColorCamposObligatorios());
            }
            
            //Configurar los JTables.
            configurarTblDat();
            
            //Configurar Combos.
            cargarPeriodo();
            
            //Configurar las ZafVenCon.
            configurarVenConRub();
            configurarVenConEmp();
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
            vecCab = new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
            vecCab.add(INT_TBL_DAT_NOM_TRA,"Empleado");
            vecCab.add(INT_TBL_DAT_VAL_ING,"Valor");
             
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
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TRA).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ING).setPreferredWidth(90);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_ING, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);    
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_VAL_ING);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ING).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Edición de la celda Valor a Ingresar.
            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ING).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Calcular totales.
                    objTblTot.calcularTotales();
                }
            });
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_ING};
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
                case INT_TBL_DAT_VAL_ING:
                    strMsg="Valor";
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
        String strMes="" , strAnio;
        int intMes ;
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
                
                System.out.println("cargarPeriodo: "+strSQL);
                rst = stm.executeQuery(strSQL);
                DefaultComboBoxModel model = new DefaultComboBoxModel();//Creo un model para luego cargarlo en un combobox.
                
                //Obtener los registros.
                while (rst.next())
                {
                    if (blnUltFec) 
                    {
                        strMes = rst.getString("ne_mes");
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
                //Presenta el mes que aun no ha sido cerrado el rol.
                intMes = Integer.valueOf(strMes);
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

    private boolean configurarVenConRub() 
    {
        boolean blnRes = true;
        
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_rub");
            arlCam.add("a.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Rubro");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300");
            
            //Armar la sentencia SQL.
            //Presenta los rubros catalogados como ingresos y manuales.
            strSQL ="";   
            strSQL+=" SELECT a.co_rub, a.tx_nom ";
            strSQL+=" FROM tbm_rubRolPag as a ";
            strSQL+=" INNER JOIN tbm_ingEgrMenPrgTra as b ON (a.co_rub=b.co_rub) ";
            strSQL+=" WHERE a.tx_tipRub='I' ";
            strSQL+=" AND a.tx_tipValRub='M' ";
            strSQL+=" AND a.st_Reg='A' ";
            strSQL+=" GROUP BY a.co_rub, a.tx_nom ";
            strSQL+=" ORDER BY a.co_rub ";
            
            System.out.println("configurarVenConRub: " + strSQL);
   
            vcoRub = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Rubros", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoRub.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            
        } 
        catch (Exception e) {      blnRes = false;        objUti.mostrarMsgErr_F1(this, e);       }
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
            System.out.println("configurarVenConEmp: " + strSQL);
       
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
            
            if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM) ) 
               strSQL+=" AND b.st_recComVen='S'";
            
            strSQL+=" ORDER BY tx_nomTra ";
            
            System.out.println("configurarVenConTra: " + strSQL);
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
    private boolean mostrarVenConRub(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoRub.setCampoBusqueda(1);
                    vcoRub.show();
                    if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) 
                    {
                        txtCodRub.setText(vcoRub.getValueAt(1));
                        txtRub.setText(vcoRub.getValueAt(2));
                        if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM))
                        {
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoRub.buscar("a.co_rub", txtCodRub.getText()))
                    {
                        txtCodRub.setText(vcoRub.getValueAt(1));
                        txtRub.setText(vcoRub.getValueAt(2));
                        if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM))
                        {
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        }
                    }
                    else 
                    {
                        vcoRub.setCampoBusqueda(0);
                        vcoRub.setCriterio1(11);
                        vcoRub.cargarDatos();
                        vcoRub.show();
                        if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) 
                        {
                            txtCodRub.setText(vcoRub.getValueAt(1));
                            txtRub.setText(vcoRub.getValueAt(2));
                            if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM))
                            {
                                txtCodTra.setText("");
                                txtNomTra.setText("");
                            }
                        } 
                        else 
                        {
                            txtCodRub.setText(strCodRub);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoRub.buscar("a.tx_nom", txtRub.getText())) 
                    {
                        txtCodRub.setText(vcoRub.getValueAt(1));
                        txtRub.setText(vcoRub.getValueAt(2));
                        if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM)) 
                        {
                            txtCodTra.setText("");
                            txtNomTra.setText("");
                        }
                    } 
                    else 
                    {
                        vcoRub.setCampoBusqueda(1);
                        vcoRub.setCriterio1(11);
                        vcoRub.cargarDatos();
                        vcoRub.show();
                        if (vcoRub.getSelectedButton() == vcoRub.INT_BUT_ACE) 
                        {
                            txtCodRub.setText(vcoRub.getValueAt(1));
                            txtRub.setText(vcoRub.getValueAt(2));
                            if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM))
                            {
                                txtCodTra.setText("");
                                txtNomTra.setText("");
                            }
                        } 
                        else 
                        {
                            txtRub.setText(strRub);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    
    
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
        lblRub = new javax.swing.JLabel();
        txtCodRub = new javax.swing.JTextField();
        txtRub = new javax.swing.JTextField();
        butRub = new javax.swing.JButton();
        lblPer = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblTra = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
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

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setLayout(null);

        lblRub.setText("Rubro:");
        panFil.add(lblRub);
        lblRub.setBounds(20, 20, 90, 14);

        txtCodRub.setBackground(objParSis.getColorCamposObligatorios());
        txtCodRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRubFocusLost(evt);
            }
        });
        txtCodRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRubActionPerformed(evt);
            }
        });
        panFil.add(txtCodRub);
        txtCodRub.setBounds(100, 20, 40, 20);

        txtRub.setBackground(objParSis.getColorCamposObligatorios());
        txtRub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRubFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRubFocusLost(evt);
            }
        });
        txtRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRubActionPerformed(evt);
            }
        });
        panFil.add(txtRub);
        txtRub.setBounds(140, 20, 390, 20);

        butRub.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        butRub.setText("...");
        butRub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRubActionPerformed(evt);
            }
        });
        panFil.add(butRub);
        butRub.setBounds(530, 20, 20, 20);

        lblPer.setText("Periodo:");
        panFil.add(lblPer);
        lblPer.setBounds(20, 50, 70, 20);

        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        panFil.add(cboPerAAAA);
        cboPerAAAA.setBounds(100, 50, 80, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cboPerMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerMesActionPerformed(evt);
            }
        });
        panFil.add(cboPerMes);
        cboPerMes.setBounds(180, 50, 120, 20);

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
        optTod.setBounds(20, 110, 400, 20);

        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 130, 400, 20);

        lblEmp.setText("Empresa:");
        panFil.add(lblEmp);
        lblEmp.setBounds(60, 160, 90, 14);

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
        txtCodEmp.setBounds(140, 160, 50, 20);

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
        txtEmp.setBounds(190, 160, 340, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(530, 159, 20, 20);

        lblTra.setText("Empleado:");
        panFil.add(lblTra);
        lblTra.setBounds(60, 180, 90, 20);

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
        txtCodTra.setBounds(140, 181, 50, 20);

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
        txtNomTra.setBounds(190, 181, 340, 20);

        butTra.setText("...");
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(530, 181, 20, 20);

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

        setBounds(0, 0, 700, 450);
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
            
            txtCodTra.setText("");
            txtNomTra.setText("");

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
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
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

    private void txtCodRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRubFocusGained
        strCodRub=txtCodRub.getText();
        //cadena= cadena.toString().trim();
       // strCodRub=(objUti.remplazarEspacios(txtCodRub.getText())).replaceAll("'", "''")).equals("") ? "Null" : "'" + strCodRub + "'");
         //((strAux = (objUti.remplazarEspacios(txtNomCli.getText())).replaceAll("'", "''")).equals("") ? "Null" : "'" + strAux + "'") + ","
        txtCodRub.selectAll();
    }//GEN-LAST:event_txtCodRubFocusGained

    private void txtCodRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRubFocusLost
        if (!txtCodRub.getText().equalsIgnoreCase(strCodRub)) 
        {
            if (txtCodRub.getText().equals(""))
            {
                txtCodRub.setText("");
                txtRub.setText("");
            } 
            else
            {
                mostrarVenConRub(1);
                limpiarJtableDat();
            }
        } 
        else
        {
            txtCodRub.setText(strCodRub);
        }

    }//GEN-LAST:event_txtCodRubFocusLost

    private void txtCodRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRubActionPerformed
       txtCodRub.transferFocus();
    }//GEN-LAST:event_txtCodRubActionPerformed

    private void txtRubFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRubFocusGained
        strRub = txtRub.getText();
        txtRub.selectAll();
    }//GEN-LAST:event_txtRubFocusGained

    private void txtRubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRubFocusLost
        if (!txtRub.getText().equalsIgnoreCase(strRub)) 
        {
            if (txtRub.getText().equals(""))
            {
                txtCodRub.setText("");
                txtRub.setText("");
            } 
            else
            {
                mostrarVenConRub(2);
                limpiarJtableDat();
            }
        } 
        else
        {
            txtRub.setText(strRub);
        }
    }//GEN-LAST:event_txtRubFocusLost

    private void txtRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRubActionPerformed
        txtRub.transferFocus();
    }//GEN-LAST:event_txtRubActionPerformed

    private void butRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRubActionPerformed
        mostrarVenConRub(0);
        limpiarJtableDat();
    }//GEN-LAST:event_butRubActionPerformed

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

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()) 
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) 
            {
                if (guardarDat())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void cboPerMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerMesActionPerformed
        limpiarJtableDat();
    }//GEN-LAST:event_cboPerMesActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed
        limpiarJtableDat();
    }//GEN-LAST:event_cboPerAAAAActionPerformed
    
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
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butRub;
    private javax.swing.JButton butTra;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPer;
    private javax.swing.JLabel lblRub;
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
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodRub;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtEmp;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtRub;
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
        if ((!(txtCodRub.getText().length() > 0)) || (!(txtRub.getText().length() > 0))) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe ingresar un rubro y luego vuelva a intentarlo.</HTML>");
            txtCodRub.requestFocus();
            return false;
        }
       
        if(objParSis.getCodigoEmpresa()== objParSis.getCodigoEmpresaGrupo())
        {
            if ((!(txtCodEmp.getText().length() > 0)) || (!(txtEmp.getText().length() > 0))) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe ingresar la empresa y luego vuelva a intentarlo.</HTML>");
                txtCodEmp.requestFocus();
                return false;
            }
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
        if ((txtCodEmp.getText().length() > 0) && (txtEmp.getText().length() > 0)) {
            sqlFil+=" AND b.co_emp = " + txtCodEmp.getText().replaceAll("'", "''") + "";
        }
        else if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
            sqlFil+=" AND b.co_emp = " + objParSis.getCodigoEmpresa();
        }
        
        if ((txtCodTra.getText().length() > 0) && (txtNomTra.getText().length() > 0)) {
            sqlFil+=" AND a.co_tra = " + txtCodTra.getText().replaceAll("'", "''") + "";
        }
        
        if (txtCodRub.getText().equalsIgnoreCase(STRCODRUBCOM) ) {
            sqlFil+=" AND b.st_recComVen='S'"; 
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
        blnDat=false;  
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
                strSQL+=" SELECT b.co_emp, c.tx_nom as tx_nomEmp, b.co_tra, (a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra, d.nd_valRub \n";
                strSQL+=" FROM tbm_Tra as a \n";
                strSQL+=" INNER JOIN tbm_traEmp as b ON (a.co_tra=b.co_tra) \n";
                strSQL+=" INNER JOIN tbm_Emp as c ON (b.co_Emp=c.co_emp) \n";
                strSQL+=" INNER JOIN \n";
                strSQL+=" ( \n";
                strSQL+="    select * from tbm_ingEgrMenPrgTra \n"; 
                strSQL+="    where co_Emp <>"+objParSis.getCodigoEmpresaGrupo()+"\n" ;
                strSQL+="    and ne_Ani=" + cboPerAAAA.getSelectedItem().toString()+"\n" ;
                strSQL+="    and ne_mes=" + cboPerMes.getSelectedIndex() + " and co_rub="+txtCodRub.getText()+"\n";
                strSQL+=" ) as d ON (b.co_Emp=d.co_Emp AND a.co_Tra=d.co_Tra) \n";
                strSQL+=" WHERE b.st_reg='A' AND b.co_Emp <>"+objParSis.getCodigoEmpresaGrupo();
                strSQL+= strFil;
                strSQL+=" AND d.co_rub=" + txtCodRub.getText();
                strSQL+=" AND d.ne_Ani="+ cboPerAAAA.getSelectedItem().toString();
                strSQL+=" AND d.ne_mes="+ cboPerMes.getSelectedIndex()+"\n" ;
                strSQL+=" ORDER BY b.co_emp, tx_nomTra ";
                
                System.out.println("cargarDetReg: "+strSQL);
                rst = stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    blnDat=true;
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP, rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_TRA, rst.getString("co_tra"));
                        vecReg.add(INT_TBL_DAT_NOM_TRA, rst.getString("tx_nomTra"));
                        vecReg.add(INT_TBL_DAT_VAL_ING, rst.getString("nd_valRub"));
                        vecDat.add(vecReg);
                    }
                }
               
                if(!blnDat)
                {
                    strSQL ="";
                    strSQL+=" SELECT b.co_emp, c.tx_nom as tx_nomEmp, b.co_tra, (a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra \n";
                    strSQL+=" FROM tbm_Tra as a \n";
                    strSQL+=" INNER JOIN tbm_traEmp as b ON (a.co_tra=b.co_tra) \n";
                    strSQL+=" INNER JOIN tbm_Emp as c ON (b.co_Emp=c.co_emp) \n";
                    strSQL+=" WHERE b.st_reg='A' AND b.co_Emp <>"+objParSis.getCodigoEmpresaGrupo();
                    strSQL+= strFil + "\n";
                    strSQL+=" ORDER BY b.co_emp, tx_nomTra ";
                    
                    System.out.println("cargarDetReg2: "+strSQL);
                    rst = stm.executeQuery(strSQL);

                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    lblMsgSis.setText("Cargando datos...");
                    while (rst.next())
                    {
                        blnDat=true;
                        if (blnCon)
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                            vecReg.add(INT_TBL_DAT_NOM_EMP, rst.getString("tx_nomEmp"));
                            vecReg.add(INT_TBL_DAT_COD_TRA, rst.getString("co_tra"));
                            vecReg.add(INT_TBL_DAT_NOM_TRA, rst.getString("tx_nomTra"));
                            vecReg.add(INT_TBL_DAT_VAL_ING, "");
                            vecDat.add(vecReg);
                        }
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
    
    
    private boolean guardarDat()
    {
        boolean blnRes=false;
        Connection con=null;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                if (validaRolPagPerExiste(con))
                {
                    if (validaRevisionRRHH(con))
                    {
                        if (guardarDatRubros(con)) 
                        {
                            con.commit();
                            blnRes = true;
                        } 
                        else 
                        {
                            con.rollback();
                        }
                    }
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
     * Función que valida si existe un rol de pago generado en el periodo seleccionado.
     * @return true: Si existe un rol de pago generado.
     * <BR>false: En el caso contrario.
     */
    private boolean validaRolPagPerExiste(java.sql.Connection conLoc) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT * FROM tbm_cabrolpag \n";
                strSQL+=" WHERE co_emp <> " + objParSis.getCodigoEmpresaGrupo() + "\n";
                if(txtCodEmp.getText().length()>0)
                {
                   strSQL+=" AND co_emp = " + txtCodEmp.getText() + "\n";
                }
                else
                {
                   strSQL+=" AND co_emp = " + objParSis.getCodigoEmpresa() + "\n";
                }
                strSQL+=" AND co_tipdoc in ("+INTCODTIPDOCROLPAG+") \n"; 
                strSQL+=" AND ne_ani = " + cboPerAAAA.getSelectedItem().toString() + "\n";
                strSQL+=" AND ne_mes = " + cboPerMes.getSelectedIndex() + "\n";
                strSQL+=" AND st_reg = 'A'";
                System.out.println("validaRolPagPerExiste:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    blnRes = false;
                }
                else
                {
                    blnRes=true;
                }
                if(!blnRes)
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El rol de pago del periodo seleccionado ya ha sido generado. <BR>Solo se pueden realizar ingresos programados masivos antes de realizar el rol de pago. <BR>Por favor Seleccione otro periodo y vuelva a intentarlo.</HTML>");
                    cboPerMes.requestFocus();
                    System.out.println("ROL YA EXISTE!");
                }
            }
            rstLoc.close();
            stmLoc.close();
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    
    /**
     * Función que valida si existe un rol de pago generado en el periodo seleccionado.
     * @return true: Si existe un rol de pago generado.
     * <BR>false: En el caso contrario.
     */
    private boolean validaRevisionRRHH(java.sql.Connection conLoc) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL ="";
                strSQL+=" SELECT st_revfal, fe_autrevfal,co_usrrevfal, tx_comrevfal \n";
                strSQL+=" FROM tbm_feccorrolpag \n";
                strSQL+=" WHERE co_emp <> " + objParSis.getCodigoEmpresaGrupo() + "\n";
                if (txtCodEmp.getText().length() > 0) {
                    strSQL += " AND co_emp = " + txtCodEmp.getText() + "\n";
                } else {
                    strSQL += " AND co_emp = " + objParSis.getCodigoEmpresa() + "\n";
                }
                strSQL+=" AND ne_ani =" + cboPerAAAA.getSelectedItem().toString() + "\n";
                strSQL+=" AND ne_mes =" + cboPerMes.getSelectedIndex() + "\n";
                strSQL+=" AND st_RevFal='S' ";
                System.out.println("validaRevisionRRHH:"+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    blnRes = false;
                }
                else
                {
                    blnRes=true;
                }
                if(!blnRes)
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El proceso de revision de RRHH del periodo seleccionado ya ha sido realizado. <BR>Solo se pueden realizar ingresos programados masivos antes de realizar el proceso de revision de RRHH. <BR>Por favor Seleccione otro periodo y vuelva a intentarlo.</HTML>");
                    cboPerMes.requestFocus();
                    System.out.println("REVISION RRHH YA EXISTE!");
                }
            }
            rstLoc.close();
            stmLoc.close();
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    private boolean guardarDatRubros(java.sql.Connection conLoc) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc=null;
        java.sql.ResultSet rstLoc=null;
        String strCodEmp="", strCodTra="", strValRub="";
        String strPerAnio="", strCodRub="";
        int intPerMes=0;
        double dblValRub=0.00;
        
        try 
        {
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();

                strPerAnio = cboPerAAAA.getSelectedItem().toString();
                intPerMes  = cboPerMes.getSelectedIndex();
                strCodRub  = txtCodRub.getText();
                
                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    //if (tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M") == 0) 
                    //{
                        blnRub=false;
                        strCodEmp = objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                        strCodTra = objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
                        strValRub = objInvItm.getIntDatoValidado(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_ING));
                        dblValRub = objUti.redondear(objUti.parseDouble(strValRub), 2);
                        
                        strSQL ="";
                        strSQL+=" SELECT * FROM tbm_ingegrmenprgtra ";
                        strSQL+=" WHERE co_Emp <>"+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_Emp="+strCodEmp;
                        strSQL+=" AND co_tra="+strCodTra;
                        strSQL+=" AND ne_Ani="+strPerAnio;
                        strSQL+=" AND ne_mes="+intPerMes;
                        strSQL+=" AND co_rub="+strCodRub;
                        
                        System.out.println("guardarDatRubros.verificaDatosExisten: " + strSQL);
                        rstLoc = stmLoc.executeQuery(strSQL);
                        
                        if(rstLoc!=null && rstLoc.next()) {    blnRub=true;    }
                        
                        if(blnRub)
                        {
                            if(!actualizaDat(conLoc, strCodEmp, strCodTra, strPerAnio, intPerMes, dblValRub,strCodRub))
                            {
                                blnRes = false;
                                break;
                            }
                        }
                        else
                        {
                            if(!ingresaDat(conLoc, strCodEmp, strCodTra, strPerAnio, intPerMes, dblValRub,strCodRub))
                            {
                                blnRes = false;
                                break;
                            }
                        }
                    //}
                }
            }
            rstLoc.close();
            stmLoc.close();
            rstLoc = null;
            stmLoc = null;
        }
        catch (java.sql.SQLException e)  {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    
    private boolean actualizaDat(java.sql.Connection conLoc, String strCodEmp, String strCodTra, String strPerAnio, int intPerMes, double dblValRub, String strCodRub ) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc=null;
       
        try 
        {
            if (conLoc != null) 
            {
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
               
                strSQL="";                
                strSQL+=" UPDATE tbm_ingEgrMenPrgTra ";
                strSQL+=" SET nd_valRub="+dblValRub;
                strSQL+=" WHERE co_Emp <>"+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND co_Emp="+strCodEmp;
                strSQL+=" AND co_tra="+strCodTra;
                strSQL+=" AND ne_Ani="+strPerAnio;
                strSQL+=" AND ne_mes="+intPerMes;
                strSQL+=" AND co_rub="+strCodRub;
                strSQL+=" ; ";
                System.out.println("actualizaDat: " + strSQL);
           
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
    
    private boolean ingresaDat(java.sql.Connection conLoc, String strCodEmp, String strCodTra, String strPerAnio, int intPerMes, double dblValRub, String strCodRub ) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc=null;
       
        try 
        {
            if (conLoc != null) 
            {
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
           
                strSQL="";                
                strSQL+=" INSERT INTO tbm_ingEgrMenPrgTra ( ";
                strSQL+=" co_Emp, co_tra, co_rub, ";
                strSQL+=" ne_Ani, ne_mes, nd_valRub) ";
                strSQL+=" VALUES( ";
                strSQL+=" " +strCodEmp+ ", " +strCodTra+ ", " +strCodRub+ ", ";
                strSQL+=" " +strPerAnio+ ", " +intPerMes+ ", " +dblValRub+ " ";
                strSQL+=" ) ; ";
                System.out.println("ingresaDat: " + strSQL);
           
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

}
