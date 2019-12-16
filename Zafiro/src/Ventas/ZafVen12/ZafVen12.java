/*
 * ZafVen12.java
 *
 * Created on 7 de abril de 2008, 10:17 AM
 */
package Ventas.ZafVen12;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafLocPrgUsr;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author jayapata
 */
public class ZafVen12 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Locales.
    private final int INT_TBL_LOC_LIN=0;
    private final int INT_TBL_LOC_CHKSEL=1;
    private final int INT_TBL_LOC_CODEMP=2;
    private final int INT_TBL_LOC_NOMEMP=3;
    private final int INT_TBL_LOC_CODLOC=4;
    private final int INT_TBL_LOC_NOMLOC=5;
    
    //JTable: Tabla de Datos  
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_CODEMP=1;
    private final int INT_TBL_DAT_CODLOC=2;
    private final int INT_TBL_DAT_CODCOT=3;
    private final int INT_TBL_DAT_FECCOT=4;
    private final int INT_TBL_DAT_CODCLI=5;
    private final int INT_TBL_DAT_NOMCLI=6;
    private final int INT_TBL_DAT_NOMVEN=7;
    private final int INT_TBL_DAT_TOTCOT=8;
    private final int INT_TBL_DAT_BUTCOT=9;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblMod objTblModLoc, objTblModDat;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblPopMnu objTblPopMnu;  
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAda objMouMotAda, objMouMotAdaLoc;         //ToolTipText en TableHeader.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafThreadGUI objThrGUI;
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
    private ZafPerUsr objPerUsr;
    private ZafVenCon vcoCli; 
    private ZafVenCon vcoItm; 
    
    private Vector vecDat, vecCab, vecReg;
    boolean blnCon=false;
    private boolean blnMarTodChkTblLoc=true;                   //Marcar todas las casillas de verificación del JTable de Locales.                 
    String strFecSis="", strSQL="";
    String strCodCli, strIdeCli, strNomCli;
    String strCodAlt, strNomItm;
    Vector vecFec;
    String strVer=" v0.2";
 
    public ZafVen12(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            initComponents();
        }
        catch (CloneNotSupportedException e) {     objUti.mostrarMsgErr_F1(this, e);   }
    } 
                                
     
    private void cargarVentanasCon()
    {
        configurarForm();
        configurarTblDat();
        configurarTblLoc();
        cargarTblLoc();
        cargarFecCot();
        configurarVenConClientes();
        configurarVenConItm();
    }
      
     private void configurarForm()
     {
        try 
        {
            this.setTitle(objParSis.getNombreMenu() + strVer);
            lblTit.setText(objParSis.getNombreMenu());

            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setTitulo("");
            objSelFec.setBounds(10, 58, 649, 70);
            panFil.add(objSelFec);
            
            panDatGen.setLocation(10, 140);
            panFecCot.setLocation(10, 4);
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());

            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(411)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(412)) 
            {
                butCer.setVisible(false);
            }
            
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) { txtCodCli.setVisible(false); }

        } catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
    }

     /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN, "");
            vecCab.add(INT_TBL_LOC_CHKSEL, " ");
            vecCab.add(INT_TBL_LOC_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_LOC_NOMEMP, "Empresa");
            vecCab.add(INT_TBL_LOC_CODLOC, "Cod.Loc");
            vecCab.add(INT_TBL_LOC_NOMLOC, "Local");
            
            objTblModLoc = new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblDatLoc.setModel(objTblModLoc);

            //Configurar JTable: Establecer tipo de selección.
            tblDatLoc.setRowSelectionAllowed(true);
            tblDatLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_LOC_CODEMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_LOC_NOMEMP).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_LOC_CODLOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_LOC_NOMLOC).setPreferredWidth(220);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatLoc, INT_TBL_LOC_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatLoc);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaLoc=new ZafMouMotAda();
            tblDatLoc.getTableHeader().addMouseMotionListener(objMouMotAdaLoc);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatLoc);
            
            
            tblDatLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            tblDatLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_LOC_CHKSEL);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblModLoc.setModoOperacion(objTblModLoc.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
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
            vecCab = new Vector(10);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_DAT_CODLOC, "Cod.Loc");
            vecCab.add(INT_TBL_DAT_CODCOT, "Cod.Cot");
            vecCab.add(INT_TBL_DAT_FECCOT, "Fec.Cot");
            vecCab.add(INT_TBL_DAT_CODCLI, "Cod.Cli");
            vecCab.add(INT_TBL_DAT_NOMCLI, "Cliente");
            vecCab.add(INT_TBL_DAT_NOMVEN, "Vendedor");
            vecCab.add(INT_TBL_DAT_TOTCOT, "Total");
            vecCab.add(INT_TBL_DAT_BUTCOT, " ");

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
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CODCOT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECCOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOMCLI).setPreferredWidth(205);
            tcmAux.getColumn(INT_TBL_DAT_NOMVEN).setPreferredWidth(105);
            tcmAux.getColumn(INT_TBL_DAT_TOTCOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUTCOT).setPreferredWidth(20);
            
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
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTCOT);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
            
            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTCOT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;

            objTblModDat.setColumnDataType(INT_TBL_DAT_TOTCOT, objTblModDat.INT_COL_DBL, new Integer(0), null);
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            tcmAux.getColumn(INT_TBL_DAT_TOTCOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            ButCot ObjButCot = new ButCot(tblDat, INT_TBL_DAT_BUTCOT);  
            
            setEditableDat(false);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
     
     
    private class ButCot extends Librerias.ZafTableColBut.ZafTableColBut_uni 
    {
        public ButCot(javax.swing.JTable tbl, int intIdx) 
        {
            super(tbl, intIdx, "Cotización.");
        }

        public void butCLick() 
        {
            cargarCotizacion();
        }
    }

    private void cargarCotizacion()
    {
        String strCodEmp = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString();
        String strCodLoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString();
        String strCodCot = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODCOT).toString();

        Ventas.ZafVen01.ZafVen01 obj = new Ventas.ZafVen01.ZafVen01(objParSis, new Integer(Integer.parseInt(strCodEmp)), new Integer(Integer.parseInt(strCodLoc)), new Integer(Integer.parseInt(strCodCot)));
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }

    public void setEditableDat(boolean editable)
    {
        if (editable == true)
        {
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_INS);
        } 
        else 
        {
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
        }
    }

    private boolean configurarVenConClientes() 
    {
        boolean blnRes = true;
        int intTipLoc=1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
         
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Cli.");
            arlAli.add("Identificación");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("20");
            arlAncCol.add("90");
            arlAncCol.add("200");      
            arlAncCol.add("180");
            arlAncCol.add("75");
            
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) 
            {
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario() == 1)
                {
                    strSQL = "";
                    strSQL += " SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir, a.tx_tel";
                    strSQL += " FROM tbm_cli as a";
                    strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a.st_reg='A' AND a.st_cli='S'";
                    strSQL += " ORDER BY a.tx_nom";
                } 
                else 
                {
                    strSQL = "";
                    strSQL += "SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_Tel";
                    strSQL += " FROM tbm_cli AS a1";
                    strSQL += " INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    //Valida si el usuario tiene acceso a locales.
                    if ((objLocPrgUsr.validaLocUsr())) 
                    {
                        strSQL += " AND a2.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                    }
                    else 
                    {
                        strSQL += " AND a2.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                    }
                    strSQL += " AND a1.st_reg='A' AND a1.st_cli='S'";
                    strSQL += " ORDER BY a1.tx_nom";
                }
            }
            else 
            {
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL+=" SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir, '' as tx_tel ";
                strSQL+=" FROM ";
                strSQL+=" ( ";
                strSQL+="   select b2.co_emp, ' ' as co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir "; 
                strSQL+="   from ";
                strSQL+="   ( "; 
                strSQL+="     select a2.co_emp, MAX(a2.co_cli) as co_cli, a2.tx_ide ";  
                strSQL+="     from ( select MIN(co_emp) as co_emp, tx_ide from tbm_cli group by tx_ide  ) as a1 ";
                strSQL+="     inner join tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.tx_ide=a2.tx_ide) ";     
                strSQL+="     group by a2.co_emp, a2.tx_ide ";
                strSQL+="   ) as b1 ";
                strSQL+="   inner join tbm_cli as b2 on (b1.co_emp=b2.co_emp and b1.co_cli=b2.co_cli) ";
                strSQL+="   where b2.co_Emp not in (select  co_Emp from tbm_Emp where st_reg='I') ";
                strSQL+="   order by b2.tx_nom ";  
                strSQL+=" ) AS a ";
            }      
                
            //System.out.println("configurarVenConClientes: "+strSQL);
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis,  "Listado de clientes" , strSQL, arlCam, arlAli, arlAncCol );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
               
        }  
        catch (Exception e) {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    private void cargarTblLoc() 
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        int intTipLoc=1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
        try 
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stm=conn.createStatement();
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Arma la sentencia.
                    strSQL ="";
                    strSQL+=" SELECT a.co_emp, b.co_loc, a.tx_nom as empresa, b.tx_nom as local ";
                    strSQL+=" FROM tbm_emp as a ";
                    strSQL+=" INNER JOIN tbm_loc as b  ON (b.co_emp=a.co_emp) ";
                    strSQL+=" WHERE a.co_emp <> "+objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND a.st_reg != 'I' ";         
                    strSQL+=" ORDER BY a.co_emp, b.co_loc ";
                }
                else
                {
                    //Arma la sentencia.
                    strSQL ="";
                    strSQL+=" SELECT a.co_emp, b.co_loc, a.tx_nom as empresa, b.tx_nom as local ";
                    strSQL+=" FROM tbm_emp as a ";
                    strSQL+=" INNER JOIN tbm_loc as b  ON (b.co_emp=a.co_emp) ";
                    strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                    //Valida si el usuario tiene acceso a locales.
                    if (objParSis.getCodigoUsuario() != 1) 
                    {
                        if ((objLocPrgUsr.validaLocUsr())) 
                        {
                            strSQL += " AND b.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                        } 
                        else 
                        {
                            strSQL += " AND b.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                        }
                    }
                    strSQL+=" AND a.st_reg != 'I' ";
                    strSQL+=" ORDER BY a.co_emp, b.co_loc ";
                }
                //System.out.println("cargarTblLoc: "+strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while(rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHKSEL,true);
                    vecReg.add(INT_TBL_LOC_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_LOC_NOMEMP, rst.getString("empresa") );
                    vecReg.add(INT_TBL_LOC_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_LOC_NOMLOC, rst.getString("local") );
                    vecDat.add(vecReg);
                 }
                 objTblModLoc.setData(vecDat);
                 tblDatLoc.setModel(objTblModLoc);
                 
                 rst.close();
                 stm.close();
                 conn.close();
                 rst=null;
                 stm=null;
                 conn=null;
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }
    
    
    private void cargarFecCot() 
    {
        java.sql.Connection conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try 
        {
            conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stm = conn.createStatement();
                
                //Arma la sentencia.
                strSQL ="";
                strSQL+=" SELECT DATE('"+strFecSis+"') as fecha UNION ALL ";
                strSQL+=" SELECT DATE('"+strFecSis+"')-3 as fecha UNION ALL " ;
                strSQL+=" SELECT DATE('"+strFecSis+"')-7 as fecha UNION ALL " ;
                strSQL+=" SELECT DATE('"+strFecSis+"')-15 as fecha UNION ALL " ;
                strSQL+=" SELECT DATE('"+strFecSis+"')-30 as fecha ";
                //System.out.println("cargarFecCot: "+ strSQL );  
                rst=stm.executeQuery(strSQL);
                vecFec =  new  java.util.Vector();
                while(rst.next())
                {
                    vecFec.add(rst.getString(1));  
                }
                   
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                conn.close();
                conn=null;
              }
      }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
       catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }
     
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.   
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butOptGrp = new javax.swing.ButtonGroup();
        butGrpcot = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panDat = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        Panfil = new javax.swing.JPanel();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panFecCot = new javax.swing.JPanel();
        RadHoy = new javax.swing.JRadioButton();
        Radultdia = new javax.swing.JRadioButton();
        Radsem = new javax.swing.JRadioButton();
        RadQuin = new javax.swing.JRadioButton();
        Ultmes = new javax.swing.JRadioButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblDatLoc = new javax.swing.JTable();
        panDatGen = new javax.swing.JPanel();
        optTodCot = new javax.swing.JRadioButton();
        optFilCot = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panCli = new javax.swing.JPanel();
        lblDesCli = new javax.swing.JLabel();
        txtDesCli = new javax.swing.JTextField();
        lblHasCli = new javax.swing.JLabel();
        txtHasCli = new javax.swing.JTextField();
        lblItm = new javax.swing.JLabel();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblDesItm = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblHasItm = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        chkCotFac = new javax.swing.JCheckBox();
        PanRep = new javax.swing.JPanel();
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
                formInternalFrameOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(0, 25));

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        Panfil.setLayout(new java.awt.BorderLayout());

        spnFil.setAutoscrolls(true);

        panFil.setMinimumSize(new java.awt.Dimension(0, 200));
        panFil.setPreferredSize(new java.awt.Dimension(0, 500));
        panFil.setLayout(null);

        panFecCot.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de la Cotización"));
        panFecCot.setLayout(null);

        butGrpcot.add(RadHoy);
        RadHoy.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        RadHoy.setSelected(true);
        RadHoy.setText("Hoy");
        RadHoy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadHoyItemStateChanged(evt);
            }
        });
        panFecCot.add(RadHoy);
        RadHoy.setBounds(12, 20, 52, 23);

        butGrpcot.add(Radultdia);
        Radultdia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        Radultdia.setText("Ultimos 3 días");
        Radultdia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadultdiaItemStateChanged(evt);
            }
        });
        panFecCot.add(Radultdia);
        Radultdia.setBounds(108, 20, 104, 23);

        butGrpcot.add(Radsem);
        Radsem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        Radsem.setText("Última semana");
        Radsem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadsemItemStateChanged(evt);
            }
        });
        panFecCot.add(Radsem);
        Radsem.setBounds(240, 20, 120, 23);

        butGrpcot.add(RadQuin);
        RadQuin.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        RadQuin.setText("Última quincena");
        RadQuin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadQuinItemStateChanged(evt);
            }
        });
        panFecCot.add(RadQuin);
        RadQuin.setBounds(364, 20, 124, 23);

        butGrpcot.add(Ultmes);
        Ultmes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        Ultmes.setText("Último mes");
        Ultmes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                UltmesItemStateChanged(evt);
            }
        });
        panFecCot.add(Ultmes);
        Ultmes.setBounds(508, 20, 108, 23);

        panFil.add(panFecCot);
        panFecCot.setBounds(10, 10, 650, 48);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Locales"));
        panLoc.setLayout(null);

        tblDatLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblDatLoc);

        panLoc.add(spnLoc);
        spnLoc.setBounds(20, 20, 620, 93);

        panFil.add(panLoc);
        panLoc.setBounds(10, 378, 650, 120);

        panDatGen.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panDatGen.setLayout(null);

        butOptGrp.add(optTodCot);
        optTodCot.setSelected(true);
        optTodCot.setText("Todas las cotizaciones de ventas");
        optTodCot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodCotItemStateChanged(evt);
            }
        });
        panDatGen.add(optTodCot);
        optTodCot.setBounds(8, 4, 500, 20);

        butOptGrp.add(optFilCot);
        optFilCot.setText("Solo las Cotizaciones de venta que cumplan el criterio seleccionado. ");
        panDatGen.add(optFilCot);
        optFilCot.setBounds(8, 24, 500, 20);

        lblCli.setText("Cliente:");
        panDatGen.add(lblCli);
        lblCli.setBounds(16, 48, 60, 14);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panDatGen.add(txtCodCli);
        txtCodCli.setBounds(75, 47, 64, 20);

        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        panDatGen.add(txtIdeCli);
        txtIdeCli.setBounds(140, 47, 109, 20);

        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        panDatGen.add(txtNomCli);
        txtNomCli.setBounds(250, 47, 372, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panDatGen.add(butCli);
        butCli.setBounds(620, 47, 20, 20);

        panCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre del Cliente"));
        panCli.setLayout(null);

        lblDesCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesCli.setText("Desde:");
        panCli.add(lblDesCli);
        lblDesCli.setBounds(30, 20, 40, 16);

        txtDesCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCliFocusLost(evt);
            }
        });
        panCli.add(txtDesCli);
        txtDesCli.setBounds(80, 20, 110, 20);

        lblHasCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblHasCli.setText("Hasta:");
        panCli.add(lblHasCli);
        lblHasCli.setBounds(220, 20, 40, 16);

        txtHasCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHasCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHasCliFocusLost(evt);
            }
        });
        panCli.add(txtHasCli);
        txtHasCli.setBounds(270, 20, 100, 20);

        panDatGen.add(panCli);
        panCli.setBounds(20, 70, 620, 48);

        lblItm.setText("Item:");
        panDatGen.add(lblItm);
        lblItm.setBounds(16, 130, 60, 14);

        txtCodAltItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusLost(evt);
            }
        });
        txtCodAltItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmActionPerformed(evt);
            }
        });
        panDatGen.add(txtCodAltItm);
        txtCodAltItm.setBounds(75, 125, 84, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panDatGen.add(txtNomItm);
        txtNomItm.setBounds(160, 125, 460, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panDatGen.add(butItm);
        butItm.setBounds(620, 125, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmDesHas.setLayout(null);

        lblDesItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesItm.setText("Desde:");
        panItmDesHas.add(lblDesItm);
        lblDesItm.setBounds(12, 20, 60, 16);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(80, 20, 100, 20);

        lblHasItm.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblHasItm.setText("Hasta:");
        panItmDesHas.add(lblHasItm);
        lblHasItm.setBounds(200, 20, 50, 16);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(260, 20, 100, 20);

        panDatGen.add(panItmDesHas);
        panItmDesHas.setBounds(20, 150, 380, 48);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmTer.setLayout(null);

        lblItmTer.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(12, 20, 80, 16);

        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(96, 20, 92, 20);

        panDatGen.add(panItmTer);
        panItmTer.setBounds(402, 150, 230, 48);

        chkCotFac.setText("Mostrar solo cotizaciones ya facturadas.");
        panDatGen.add(chkCotFac);
        chkCotFac.setBounds(16, 200, 344, 23);

        panFil.add(panDatGen);
        panDatGen.setBounds(10, 140, 650, 230);

        spnFil.setViewportView(panFil);

        Panfil.add(spnFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", Panfil);

        PanRep.setLayout(new java.awt.BorderLayout());

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

        PanRep.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", PanRep);

        panDat.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panDat, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(79, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.EAST);

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

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
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

    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBod, i, j;
        //Validar que esté seleccionada al menos un local si es la empresa grupo.
        intNumFilTblBod=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblBod; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHKSEL))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un Local.<BR>Seleccione un Local y vuelva a intentarlo.</HTML>");
            tblDatLoc.requestFocus();
            return false;
        }
        
        return true;
    }
    
    
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
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        
        public void run()
        {
            pgrSis.setIndeterminate(true);
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) 
            {
                if (cargarDatosGrp(sqlConFilGrp())) 
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }
            else
            {
                if (cargarDatos(sqlConFil())) 
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) 
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
           
            objThrGUI=null;
        }
        
        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }

    
    private String sqlConFilGrp() 
    {
        String sqlFil = "";
        String strLoc="", strTerItm="";
        boolean blnLoc=false, blnTerItm=false;
        
        //Estado de Cotizaciones.
        if (chkCotFac.isSelected()) 
        {
            sqlFil+=" a.st_reg IN ('F') ";
        }
        else
        {
            sqlFil+=" a.st_reg NOT IN ('E','I','F') ";
        }

        //Fecha Seleccionada.
        switch (objSelFec.getTipoSeleccion()) 
        {
            case 0: //Búsqueda por rangos
                sqlFil+=" AND a.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                sqlFil+=" AND a.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                sqlFil+=" AND a.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 3: //Todo.
                break;
        }
    
        //Locales Seleccionados.
        for (int j = 0; j < tblDatLoc.getRowCount(); j++) 
        {
            if (tblDatLoc.getValueAt(j, INT_TBL_LOC_CHKSEL).toString().equals("true")) 
            {
                if(!blnLoc) //Primera vez que ingresa.
                {
                    strLoc+=" AND ( ";
                }
                else 
                {
                    strLoc+=" OR ";
                }
                strLoc+=" (a.co_emp=" + tblDatLoc.getValueAt(j, INT_TBL_LOC_CODEMP).toString() + " and a.co_loc=" + tblDatLoc.getValueAt(j, INT_TBL_LOC_CODLOC).toString() + ") ";
                blnLoc=true;
            }
        }
        if (blnLoc)
        {
            strLoc+= " ) ";
            sqlFil+= strLoc;
        }
        
        //Otros Filtros Seleccionados.
        if(optFilCot.isSelected()==true)
        {  
            if (txtIdeCli.getText().length()>0)
                sqlFil+=" AND trim(cli.tx_ide)='"+txtIdeCli.getText()+"'";
            if (txtDesCli.getText().length()>0 || txtHasCli.getText().length()>0)
                sqlFil+=" AND ((LOWER(cli.tx_nom) BETWEEN '" + txtDesCli.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtHasCli.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(cli.tx_nom) LIKE '" + txtHasCli.getText().replaceAll("'", "''").toLowerCase() + "%')";
            if (txtCodAltItm.getText().length()>0)
                sqlFil+=" AND b.tx_codalt='"+txtCodAltItm.getText()+"'";
            if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                sqlFil+=" AND ((LOWER(b.tx_codalt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b.tx_codalt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
        
            //Items Terminan Con.
            if (!txtCodAltItmTer.getText().equals("")) 
            {
                //blnTerItm=false;
                String[] result = txtCodAltItmTer.getText().split(",");
                strTerItm = " AND  ( ";
                for (int x = 0; x < result.length; x++)
                {
                    if (blnTerItm) 
                    {
                        strTerItm += " or ";
                    }
                    strTerItm += "  upper(b.tx_codalt) like '%" + result[x].toString().toUpperCase() + "'";
                    blnTerItm=true;
                }
                strTerItm+= " ) ";
                sqlFil+=strTerItm;
            }
        }

        return sqlFil;
    }
    
    
    private boolean cargarDatosGrp(String strFil) 
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnres = true;
      
        try 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_cot, a.fe_cot, a.co_cli, cli.tx_nom as cliente, usr.tx_nom as vendedor, a.nd_tot ";
                strSQL+=" FROM tbm_cabcotven as a "; 
                strSQL+=" INNER JOIN tbm_detcotven as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_cot=a.co_cot) ";  
                strSQL+=" INNER JOIN tbm_cli as cli on(cli.co_emp=a.co_emp and cli.co_cli=a.co_cli) ";  
                strSQL+=" INNER JOIN tbm_usr as usr on(usr.co_usr=a.co_ven)  ";  
                strSQL+=" WHERE "+strFil;
                strSQL+=" GROUP BY  a.co_emp, a.co_loc, a.co_cot, a.fe_cot, a.co_cli, cli.tx_nom , usr.tx_nom, a.nd_tot "; 
                strSQL+=" ORDER BY  a.co_emp, a.co_loc, a.co_cot, a.fe_cot "; 

                //System.out.println("cargarDatosGrp: "+strSQL);
                
                rstLoc = stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next())
                {
                    if (blnCon)
                    {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODCOT, rstLoc.getString("co_cot"));
                        vecReg.add(INT_TBL_DAT_FECCOT, rstLoc.getString("fe_cot"));
                        vecReg.add(INT_TBL_DAT_CODCLI, rstLoc.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOMCLI, rstLoc.getString("cliente"));
                        vecReg.add(INT_TBL_DAT_NOMVEN, rstLoc.getString("vendedor"));
                        vecReg.add(INT_TBL_DAT_TOTCOT, rstLoc.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_BUTCOT, " ");
                        vecDat.add(vecReg);
                    }
                }
                
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
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
        catch (SQLException  Evt){ blnres=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e){  blnres=false; objUti.mostrarMsgErr_F1(this, e);   } 
      return blnres;
    }
    
    
    private String sqlConFil() 
    {
        String sqlFil = "";
        String strLoc="", strTerItm="";
        boolean blnLoc=false, blnTerItm=false;
        
        //Estado de Cotizaciones.
        if (chkCotFac.isSelected()) 
        {
            sqlFil+=" a.st_reg IN ('F') ";
        }
        else
        {
            sqlFil+=" a.st_reg NOT IN ('E','I','F') ";
        }

        //Fecha Seleccionada.
        switch (objSelFec.getTipoSeleccion()) 
        {
            case 0: //Búsqueda por rangos
                sqlFil+=" AND a.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 1: //Fechas menores o iguales que "Hasta".
                sqlFil+=" AND a.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 2: //Fechas mayores o iguales que "Desde".
                sqlFil+=" AND a.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                break;
            case 3: //Todo.
                break;
        }
    
        //Locales Seleccionados.
        for (int j = 0; j < tblDatLoc.getRowCount(); j++) 
        {
            if (tblDatLoc.getValueAt(j, INT_TBL_LOC_CHKSEL).toString().equals("true")) 
            {
                if(!blnLoc) //Primera vez que ingresa.
                {
                    strLoc+=" AND ( ";
                }
                else 
                {
                    strLoc+=" OR ";
                }
                strLoc+=" (a.co_emp=" + tblDatLoc.getValueAt(j, INT_TBL_LOC_CODEMP).toString() + " and a.co_loc=" + tblDatLoc.getValueAt(j, INT_TBL_LOC_CODLOC).toString() + ") ";
                blnLoc=true;
            }
        }
        if (blnLoc)
        {
            strLoc+= " ) ";
            sqlFil+= strLoc;
        }
        
        //Otros Filtros Seleccionados.
        if(optFilCot.isSelected()==true)
        {  
            if (txtCodCli.getText().length()>0)
                sqlFil+=" AND a.co_cli=" + txtCodCli.getText();
            if (txtIdeCli.getText().length()>0)
                sqlFil+=" AND trim(cli.tx_ide)='"+txtIdeCli.getText()+"'";
            if (txtDesCli.getText().length()>0 || txtHasCli.getText().length()>0)
                sqlFil+=" AND ((LOWER(cli.tx_nom) BETWEEN '" + txtDesCli.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtHasCli.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(cli.tx_nom) LIKE '" + txtHasCli.getText().replaceAll("'", "''").toLowerCase() + "%')";
            if (txtCodAltItm.getText().length()>0)
                sqlFil+=" AND b.tx_codalt='"+txtCodAltItm.getText()+"'";
            if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                sqlFil+=" AND ((LOWER(b.tx_codalt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b.tx_codalt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
        
            //Items Terminan Con.
            if (!txtCodAltItmTer.getText().equals("")) 
            {
                //blnTerItm=false;
                String[] result = txtCodAltItmTer.getText().split(",");
                strTerItm = " AND  ( ";
                for (int x = 0; x < result.length; x++)
                {
                    if (blnTerItm) 
                    {
                        strTerItm += " or ";
                    }
                    strTerItm += "  upper(b.tx_codalt) like '%" + result[x].toString().toUpperCase() + "'";
                    blnTerItm=true;
                }
                strTerItm+= " ) ";
                sqlFil+=strTerItm;
            }
        }

        return sqlFil;
    }
    
  
    private boolean cargarDatos(String strFil) 
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnres = true;
      
        try 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_cot, a.fe_cot, a.co_cli, cli.tx_nom as cliente, usr.tx_nom as vendedor, a.nd_tot ";
                strSQL+=" FROM tbm_cabcotven as a "; 
                strSQL+=" INNER JOIN tbm_detcotven as b on(b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_cot=a.co_cot) ";  
                strSQL+=" INNER JOIN tbm_cli as cli on(cli.co_emp=a.co_emp and cli.co_cli=a.co_cli) ";  
                strSQL+=" INNER JOIN tbm_usr as usr on(usr.co_usr=a.co_ven)  ";  
                strSQL+=" WHERE "+strFil;
                strSQL+=" GROUP BY  a.co_emp, a.co_loc, a.co_cot, a.fe_cot, a.co_cli, cli.tx_nom , usr.tx_nom, a.nd_tot "; 
                strSQL+=" ORDER BY  a.co_emp, a.co_loc, a.co_cot, a.fe_cot "; 

                //System.out.println("cargarDatosGrp: "+strSQL);
                
                rstLoc = stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next())
                {
                    if (blnCon)
                    {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODCOT, rstLoc.getString("co_cot"));
                        vecReg.add(INT_TBL_DAT_FECCOT, rstLoc.getString("fe_cot"));
                        vecReg.add(INT_TBL_DAT_CODCLI, rstLoc.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOMCLI, rstLoc.getString("cliente"));
                        vecReg.add(INT_TBL_DAT_NOMVEN, rstLoc.getString("vendedor"));
                        vecReg.add(INT_TBL_DAT_TOTCOT, rstLoc.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_BUTCOT, " ");
                        vecDat.add(vecReg);
                    }
                }
                
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
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
        catch (SQLException  Evt){ blnres=false; objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e){  blnres=false; objUti.mostrarMsgErr_F1(this, e);   } 
      return blnres;
    }
    
    
    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing
     
        
    private void exitForm()
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }   
    }
    
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodCotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodCotItemStateChanged
        if (optTodCot.isSelected()) 
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtNomCli.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
            txtDesCli.setText("");
            txtHasCli.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }  
    }//GEN-LAST:event_optTodCotItemStateChanged
    
    
    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm)) 
        {
            if (txtNomItm.getText().equals("")) 
            {
                txtCodAltItm.setText("");
                txtNomItm.setText("");
            } 
            else 
            {
                mostrarVenConItm(2);
            }
        } 
        else 
        {
            txtNomItm.setText(strNomItm);
        }

        if (txtNomItm.getText().length() > 0) 
        {
            optTodCot.setSelected(false);
            optFilCot.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAltItm.getText().equals("")) 
            {
                txtCodAltItm.setText("");
                txtNomItm.setText("");
            }
            else 
            {
                mostrarVenConItm(1);
            }
        }
        else
        {
            txtCodAltItm.setText(strCodAlt);
        }

        if (txtCodAltItm.getText().length() > 0)
        {
            optTodCot.setSelected(false);
            optFilCot.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strNomCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(3);
            }
        }
        else
            txtNomCli.setText(strNomCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomCli.getText().length()>0)
        {
            optTodCot.setSelected(false);
            optFilCot.setSelected(true);
        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
       //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli))
        {
            if (txtIdeCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtIdeCli.getText().length()>0)
        {
            optTodCot.setSelected(false);
            optFilCot.setSelected(true);
        }
    }//GEN-LAST:event_txtIdeCliFocusLost
   

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) 
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            } 
            else 
            {
                mostrarVenConCli(1);
            }
        } 
        else 
        {
            txtCodCli.setText(strCodCli);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optTodCot.setSelected(false);
            optFilCot.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm = txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt = txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strNomCli = txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli = txtIdeCli.getText();
        txtIdeCli.selectAll();
    }//GEN-LAST:event_txtIdeCliFocusGained

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli = txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus(); 
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
    }//GEN-LAST:event_txtIdeCliActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed
 
    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if (txtCodAltItm.getText().length() > 0) 
        {
            optTodCot.setSelected(false);
            optFilCot.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        } 
    }//GEN-LAST:event_butItmActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        cargarVentanasCon();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFilCot.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        if (txtCodAltItmHas.getText().length() > 0) 
        {
            optFilCot.setSelected(true);
            optTodCot.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtHasCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHasCliFocusLost
        if (txtHasCli.getText().length()>0)
            optFilCot.setSelected(true);
    }//GEN-LAST:event_txtHasCliFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained
   
    private void txtHasCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHasCliFocusGained
        txtHasCli.selectAll();
    }//GEN-LAST:event_txtHasCliFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
    if (txtCodAltItmDes.getText().length() > 0) 
    {
        optFilCot.setSelected(true);
        optTodCot.setSelected(false);
        if (txtCodAltItmHas.getText().length() == 0) 
        {
            txtCodAltItmHas.setText(txtCodAltItmDes.getText());
        }
    }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtDesCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCliFocusLost
        if (txtDesCli.getText().length()>0)
        {
            optFilCot.setSelected(true);
            if (txtHasCli.getText().length()==0)
                txtHasCli.setText(txtDesCli.getText());
        }
    }//GEN-LAST:event_txtDesCliFocusLost

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtDesCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCliFocusGained
        txtDesCli.selectAll();
    }//GEN-LAST:event_txtDesCliFocusGained

    private void UltmesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_UltmesItemStateChanged
        if (Ultmes.isSelected()) 
        {
            String strFec = vecFec.get(4).toString();
            strFec = objUti.formatearFecha(strFec, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaDesde(strFec);

            String strFecHas = vecFec.get(0).toString();
            strFecHas = objUti.formatearFecha(strFecHas, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaHasta(strFecHas);

        }
    }//GEN-LAST:event_UltmesItemStateChanged

    private void RadQuinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadQuinItemStateChanged
        if (RadQuin.isSelected()) 
        {
            String strFec = vecFec.get(3).toString();
            strFec = objUti.formatearFecha(strFec, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaDesde(strFec);

            String strFecHas = vecFec.get(0).toString();
            strFecHas = objUti.formatearFecha(strFecHas, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaHasta(strFecHas);
        }
    }//GEN-LAST:event_RadQuinItemStateChanged

    private void RadsemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadsemItemStateChanged
        if (Radsem.isSelected())
        {
            String strFec = vecFec.get(2).toString();
            strFec = objUti.formatearFecha(strFec, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaDesde(strFec);

            String strFecHas = vecFec.get(0).toString();
            strFecHas = objUti.formatearFecha(strFecHas, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaHasta(strFecHas);
        }
    }//GEN-LAST:event_RadsemItemStateChanged

    private void RadultdiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadultdiaItemStateChanged
        if (Radultdia.isSelected())
        {
            String strFec = vecFec.get(1).toString();
            strFec = objUti.formatearFecha(strFec, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaDesde(strFec);

            String strFecHas = vecFec.get(0).toString();
            strFecHas = objUti.formatearFecha(strFecHas, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaHasta(strFecHas);
        }
    }//GEN-LAST:event_RadultdiaItemStateChanged
   
    private void RadHoyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadHoyItemStateChanged
        if (RadHoy.isSelected()) 
        {
            String strFec = vecFec.get(0).toString();
            strFec = objUti.formatearFecha(strFec, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaDesde(strFec);

            String strFecHas = vecFec.get(0).toString();
            strFecHas = objUti.formatearFecha(strFecHas, "yyyy-MM-dd", "dd/MM/yyyy");
            objSelFec.setFechaHasta(strFecHas);
        }
    }//GEN-LAST:event_RadHoyItemStateChanged

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length() > 0)
        {
            optFilCot.setSelected(true);
            optTodCot.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanRep;
    private javax.swing.JPanel Panfil;
    private javax.swing.JRadioButton RadHoy;
    private javax.swing.JRadioButton RadQuin;
    private javax.swing.JRadioButton Radsem;
    private javax.swing.JRadioButton Radultdia;
    private javax.swing.JRadioButton Ultmes;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.ButtonGroup butGrpcot;
    private javax.swing.JButton butItm;
    private javax.swing.ButtonGroup butOptGrp;
    private javax.swing.JCheckBox chkCotFac;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblDesCli;
    private javax.swing.JLabel lblDesItm;
    private javax.swing.JLabel lblHasCli;
    private javax.swing.JLabel lblHasItm;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFilCot;
    private javax.swing.JRadioButton optTodCot;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCli;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDatGen;
    private javax.swing.JPanel panFecCot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatLoc;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtDesCli;
    private javax.swing.JTextField txtHasCli;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables
 
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica el Local seleccionado en el el JTable de Locales.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try 
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDatLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHKSEL)
            {
                if (blnMarTodChkTblLoc)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHKSEL);
                    }
                    blnMarTodChkTblLoc=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHKSEL);
                    }
                    blnMarTodChkTblLoc=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
     private class ZafMouMotAdaLoc extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDatLoc.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_CODEMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOMEMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_CODLOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOMLOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
     
     private class ZafMouMotAda extends MouseMotionAdapter   
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
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
                case INT_TBL_DAT_CODCOT:
                    strMsg="Código de cotización";
                    break;
                case INT_TBL_DAT_FECCOT:
                    strMsg="Fecha de la Cotización";
                    break;
                case INT_TBL_DAT_CODCLI:
                    strMsg = "Código del cliente";
                    break;
                case INT_TBL_DAT_NOMCLI:
                    strMsg = "Nombre del cliente";
                    break;
                case INT_TBL_DAT_NOMVEN:
                    strMsg = "Nombre del vendedor";
                    break;
                case INT_TBL_DAT_TOTCOT:
                    strMsg = "Total Cotización";
                    break;
                case INT_TBL_DAT_BUTCOT:
                    strMsg = "Cotización";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
   
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus)  
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.show();
                    if (vcoItm.getSelectedButton() == vcoItm.INT_BUT_ACE) 
                    {
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText())) 
                    {
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    } 
                    else 
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton() == vcoItm.INT_BUT_ACE) 
                        {
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        } 
                        else 
                        {
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    } 
                    else 
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.show();
                        if (vcoItm.getSelectedButton() == vcoItm.INT_BUT_ACE) 
                        {
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm() 
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("350");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL += " FROM tbm_inv AS a1";
            strSQL += " LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            //strSQL += " AND (   (UPPER(a1.tx_codAlt) LIKE '%I') OR  (UPPER(a1.tx_codAlt) LIKE '%S')  ) AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
            strSQL += " ORDER BY a1.tx_codAlt";
            
            vcoItm = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strNomCli);
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
    
    
    
    
    
    
}
