/*
 * ZafImp06.java
 *
 * Created on 16 de Octubre de 2013, 12:35 PM
 */
package Importaciones.ZafImp06;
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
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author  Jose Mario Marin
 */

public class ZafImp06 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable
    private static final int INT_TBL_DAT_LIN=0;    
    private static final int INT_TBL_DAT_COD_ITM=1;             
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;             
    private static final int INT_TBL_DAT_NOM_ITM=3;         
    private static final int INT_TBL_DAT_UNI_MED=4;            
    private static final int INT_TBL_DAT_COD_GRP_ITM=5;          
    private static final int INT_TBL_DAT_DES_COR_GRP=6;            
    private static final int INT_TBL_DAT_DES_LAR_GRP=7;   
    private static final int INT_TBL_DAT_COD_PAR_ARA=8;          
    private static final int INT_TBL_DAT_DES_COR_PAR=9;            
    private static final int INT_TBL_DAT_DES_LAR_PAR=10;        
    private static final int INT_TBL_DAT_POR_PAR_ARA=11;  
    private static final int INT_TBL_DAT_CHK_ISD_COS=12;  
    private static final int INT_TBL_DAT_CHK_ISD_CRE=13; 

    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafPerUsr objPerUsr; 
    private ZafVenCon vcoItm;                           //Ventana de consulta.
   
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    
    private String strSQL, strAux;
    private String strCodItm, strCodAlt, strNomItm;     //Contenido del campo al obtener el foco.
    private String strVer=" v0.4"; 

    /** 
     * Crea una nueva instancia de la clase ZafIndRpt.
     * @param obj El objeto ZafParSis.
     */
    public ZafImp06(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }            
        }
        catch (CloneNotSupportedException e)  {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try{
            //Título de la ventana 
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);   
            
            //Inicializa las variables.
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);

            txtCodItm.setVisible(false);
            
            //Configurar los JTables.
            configurarTblDat();
            
            //Configurar las ZafVenCon.
            configurarVenConItm();
        }
        catch(Exception e){
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
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,    "Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,    "Descripción del ítem");
            vecCab.add(INT_TBL_DAT_UNI_MED,    "Uni.Med.");
            vecCab.add(INT_TBL_DAT_COD_GRP_ITM,"Cód.Grp.");
            vecCab.add(INT_TBL_DAT_DES_COR_GRP,"Des.Cor.Grp.");
            vecCab.add(INT_TBL_DAT_DES_LAR_GRP,"Des.Lar.Grp.");
            vecCab.add(INT_TBL_DAT_COD_PAR_ARA,"Cód.Par.");
            vecCab.add(INT_TBL_DAT_DES_COR_PAR,"Des.Cor.Par.");
            vecCab.add(INT_TBL_DAT_DES_LAR_PAR,"Des.Lar.Par.");            
            vecCab.add(INT_TBL_DAT_POR_PAR_ARA,"% Ara.");      
            vecCab.add(INT_TBL_DAT_CHK_ISD_COS,"Apl.Cos.");
            vecCab.add(INT_TBL_DAT_CHK_ISD_CRE,"Cré.Tri.");
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50); 
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80); 
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(180); 
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50); 
            tcmAux.getColumn(INT_TBL_DAT_COD_GRP_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_GRP).setPreferredWidth(160);
            tcmAux.getColumn(INT_TBL_DAT_COD_PAR_ARA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_PAR).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_PAR).setPreferredWidth(160);  
            tcmAux.getColumn(INT_TBL_DAT_POR_PAR_ARA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ISD_COS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ISD_CRE).setPreferredWidth(50);
            
            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrpDatItm=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatItm.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatItm=new ZafTblHeaColGrp("Datos del ítem");
            objTblHeaColGrpDatItm.setHeight(16);
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_COD_ITM));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_NOM_ITM));
            objTblHeaColGrpDatItm.add(tcmAux.getColumn(INT_TBL_DAT_UNI_MED));
            objTblHeaGrpDatItm.addColumnGroup(objTblHeaColGrpDatItm);    
            
            ZafTblHeaGrp objTblHeaGrpItmGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpItmGrp.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpItmGrp=new ZafTblHeaColGrp("Grupo del ítem");
            objTblHeaColGrpItmGrp.setHeight(16);
            objTblHeaColGrpItmGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_GRP_ITM));
            objTblHeaColGrpItmGrp.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_GRP));
            objTblHeaColGrpItmGrp.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_GRP));
            objTblHeaGrpItmGrp.addColumnGroup(objTblHeaColGrpItmGrp);    

            ZafTblHeaGrp objTblHeaGrpItmParAra=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpItmParAra.setHeight(16*2);            
            ZafTblHeaColGrp objTblHeaColGrpItmParAra=new ZafTblHeaColGrp("Partida arancelaria del ítem");
            objTblHeaColGrpItmParAra.setHeight(16);
            objTblHeaColGrpItmParAra.add(tcmAux.getColumn(INT_TBL_DAT_COD_PAR_ARA));
            objTblHeaColGrpItmParAra.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_PAR));
            objTblHeaColGrpItmParAra.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_PAR));
            objTblHeaColGrpItmParAra.add(tcmAux.getColumn(INT_TBL_DAT_POR_PAR_ARA));
            objTblHeaGrpItmParAra.addColumnGroup(objTblHeaColGrpItmParAra);    
            
            ZafTblHeaGrp objTblHeaGrpISD=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpISD.setHeight(16*2);            
            ZafTblHeaColGrp objTblHeaColGrpISD=new ZafTblHeaColGrp("ISD");
            objTblHeaColGrpISD.setHeight(16);
            objTblHeaColGrpISD.add(tcmAux.getColumn(INT_TBL_DAT_CHK_ISD_COS));
            objTblHeaColGrpISD.add(tcmAux.getColumn(INT_TBL_DAT_CHK_ISD_CRE));
            objTblHeaGrpISD.addColumnGroup(objTblHeaColGrpISD);                
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_PAR, tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_ISD_COS).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ISD_CRE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;                  
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_PAR_ARA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_GRP_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_PAR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;            
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);                
            
            tcmAux=null;
        }
        catch(Exception e){
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
                 case INT_TBL_DAT_COD_ITM:  
                    strMsg="Código del Item";
                    break;
                 case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del Item";
                    break;
                case INT_TBL_DAT_NOM_ITM:  
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_UNI_MED: 
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_COD_GRP_ITM:
                    strMsg="Código del Grupo";
                    break;
                case INT_TBL_DAT_DES_COR_GRP:
                    strMsg="Descripción corta del Grupo";
                    break;
                case INT_TBL_DAT_DES_LAR_GRP:
                    strMsg="Descripción larga del Grupo";
                    break;
                case INT_TBL_DAT_COD_PAR_ARA:
                    strMsg="Código de partida arancelaria";
                    break;
                case INT_TBL_DAT_DES_COR_PAR:
                    strMsg="Descripción corta de partida arancelaria";
                    break;
                case INT_TBL_DAT_DES_LAR_PAR:
                    strMsg="Descripción larga de partida arancelaria";
                    break;      
                case INT_TBL_DAT_POR_PAR_ARA:
                    strMsg="Descripción larga de partida arancelaria";
                    break;                      
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
           //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a1.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Nombre");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo();
            strSQL+=" AND a1.st_reg='A' AND a1.tx_codAlt like '%I'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            //System.out.println("Inventario: "+strSQL);
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
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
    private boolean mostrarVcoItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
              switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
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
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panDesHasItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        chkItmSinGrp = new javax.swing.JCheckBox();
        chkItmGrp = new javax.swing.JCheckBox();
        chkIsdCos = new javax.swing.JCheckBox();
        chkIsdSinAsi = new javax.swing.JCheckBox();
        chkIsdCreTri = new javax.swing.JCheckBox();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setBorder(null);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los ítems");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 10, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los ítems que cumplan el criterio Seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 30, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Vendedor/Comprador");
        panFil.add(lblItm);
        lblItm.setBounds(30, 50, 40, 20);

        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        txtCodItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmFocusLost(evt);
            }
        });
        panFil.add(txtCodItm);
        txtCodItm.setBounds(60, 50, 30, 20);

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
        txtCodAlt.setBounds(90, 50, 92, 20);

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
        txtNomItm.setBounds(180, 50, 460, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 50, 20, 20);

        panDesHasItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panDesHasItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panDesHasItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(20, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(80, 20, 80, 20);

        lblCodAltHas.setText("Hasta:");
        panDesHasItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(180, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(236, 20, 80, 20);

        panFil.add(panDesHasItm);
        panDesHasItm.setBounds(40, 75, 328, 52);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(30, 20, 100, 20);

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
        txtCodAltItmTer.setBounds(140, 20, 120, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(380, 75, 280, 52);

        chkItmSinGrp.setText("Mostrar ítems sin grupo asociado");
        chkItmSinGrp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkItmSinGrpItemStateChanged(evt);
            }
        });
        panFil.add(chkItmSinGrp);
        chkItmSinGrp.setBounds(40, 160, 260, 23);
        chkItmSinGrp.getAccessibleContext().setAccessibleName("txtMosItmSinGru");

        chkItmGrp.setSelected(true);
        chkItmGrp.setText("Mostrar ítems con grupo asociado");
        chkItmGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkItmGrpActionPerformed(evt);
            }
        });
        panFil.add(chkItmGrp);
        chkItmGrp.setBounds(40, 140, 260, 23);
        chkItmGrp.getAccessibleContext().setAccessibleName("txtMosItmAso");

        chkIsdCos.setText("Mostrar ítems con ISD al Costo");
        chkIsdCos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkIsdCosItemStateChanged(evt);
            }
        });
        chkIsdCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIsdCosActionPerformed(evt);
            }
        });
        panFil.add(chkIsdCos);
        chkIsdCos.setBounds(40, 180, 250, 23);

        chkIsdSinAsi.setText("Mostrar ítems sin ISD asignado");
        chkIsdSinAsi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkIsdSinAsiItemStateChanged(evt);
            }
        });
        panFil.add(chkIsdSinAsi);
        chkIsdSinAsi.setBounds(40, 220, 250, 20);

        chkIsdCreTri.setText("Mostrar ítems con ISD Crédito Tributario");
        chkIsdCreTri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkIsdCreTriItemStateChanged(evt);
            }
        });
        panFil.add(chkIsdCreTri);
        chkIsdCreTri.setBounds(40, 200, 340, 23);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);

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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            chkItmSinGrp.setSelected(false);                        
            chkIsdCos.setSelected(false);                        
            chkIsdCreTri.setSelected(false);                        
            chkIsdSinAsi.setSelected(false);                        
        }
    }//GEN-LAST:event_optTodItemStateChanged
    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        objTblMod.removeAllRows();
        if (isCamVal())
        {
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
    }//GEN-LAST:event_butConActionPerformed
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm


    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
       strCodItm=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodItm))
        {
            if (txtCodAlt.getText().equals("")){
                txtCodAlt.setText("");
                txtNomItm.setText("");
                txtCodItm.setText("");
            }
            else{
                mostrarVcoItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodAlt.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals("")){
                txtCodAlt.setText("");
                txtNomItm.setText("");
                txtCodItm.setText("");
            }
            else{
                mostrarVcoItm(2);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodAlt.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        //configurarVenConItm();
        mostrarVcoItm(0);
        if (txtCodAlt.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if(txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
            txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmActionPerformed

    private void txtCodItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmFocusGained

    private void txtCodItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmFocusLost

    private void chkItmGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkItmGrpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkItmGrpActionPerformed

    private void chkIsdCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIsdCosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkIsdCosActionPerformed

    private void chkItmSinGrpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkItmSinGrpItemStateChanged
        if (chkItmSinGrp.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkItmSinGrpItemStateChanged

    private void chkIsdCosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkIsdCosItemStateChanged
        if (chkIsdCos.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkIsdCosItemStateChanged

    private void chkIsdCreTriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkIsdCreTriItemStateChanged
        if (chkIsdCreTri.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkIsdCreTriItemStateChanged

    private void chkIsdSinAsiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkIsdSinAsiItemStateChanged
        if (chkIsdSinAsi.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkIsdSinAsiItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkIsdCos;
    private javax.swing.JCheckBox chkIsdCreTri;
    private javax.swing.JCheckBox chkIsdSinAsi;
    private javax.swing.JCheckBox chkItmGrp;
    private javax.swing.JCheckBox chkItmSinGrp;
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
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panDesHasItm;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
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
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
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
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        return true;
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
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        
        try 
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;    
                String strFecAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos());
                
                /* Filtro de ISD */
                String strIsdAux="";
                if(chkIsdCos.isSelected())  
                    strIsdAux+=strIsdAux.equals("")?"'S'":",'S'";
                if(chkIsdCreTri.isSelected())
                    strIsdAux+=strIsdAux.equals("")?"'N'":",'N'";
                if(chkIsdSinAsi.isSelected())
                    strIsdAux+=strIsdAux.equals("")?"'P'":",'P'";
                
                stm = con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT * FROM (";
                strSQL+="    SELECT a1.co_emp, a1.co_itm, a1.tx_codalt AS tx_codAltItm, a1.tx_nomItm, a2.tx_desCor AS tx_uniMed";
                strSQL+="         , a1.co_grpImp, a3.co_grp, a3.tx_desCor AS tx_desCorGrp, a3.tx_desLar AS tx_desLarGrp";
                strSQL+="         , a4.co_parAra, a4.tx_par AS tx_desCorPar, a4.tx_des AS tx_desLarPar, a5.nd_Ara AS nd_porParAra";
                strSQL+="         , (CASE WHEN a4.st_isdCos IS NULL THEN 'P' ELSE a4.st_isdCos END) AS st_isdCos";
                strSQL+="    FROM ( tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg )";
                strSQL+="    LEFT OUTER JOIN tbm_grpInvImp AS a3 on (a1.co_grpImp=a3.co_grp)";
                strSQL+="    LEFT OUTER JOIN (";
                strSQL+="       tbm_parAraImp AS a4 LEFT OUTER JOIN tbm_vigParAraImp AS a5 ON a4.co_parAra=a5.co_parAra ";
                strSQL+="       AND CAST('" + strFecAux + "' AS date) BETWEEN a5.fe_vigDes AND (CASE WHEN a5.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a5.fe_vigHas END)";
                strSQL+="    ) ON a3.co_parAra=a4.co_parAra ";
                strSQL+="    WHERE a1.st_reg='A' AND a1.tx_codAlt LIKE '%I' ";    
                strSQL+=" ) AS x ";
                strSQL+=" WHERE x.co_emp="+objParSis.getCodigoEmpresaGrupo();
                
                if(txtCodItm.getText().length()>0)
                    strSQL+=" AND x.co_itm=" + txtCodItm.getText();
                if(chkItmGrp.isSelected() && !(chkItmSinGrp.isSelected()))
                    strSQL+=" AND x.co_grpImp IS NOT NULL";
                else if (!chkItmGrp.isSelected() && (chkItmSinGrp.isSelected()))
                    strSQL+=" AND x.co_grpImp IS NULL";
                
                if(!strIsdAux.equals(""))
                    strSQL+=" AND x.st_isdCos IN ("+strIsdAux+")";   
                
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strSQL+=" AND ((LOWER(x.tx_codAltItm) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(x.tx_codAltItm) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                if (txtCodAltItmTer.getText().length()>0)
                    strSQL+=getConSQLAdiCamTer("x.tx_codAltItm", txtCodAltItmTer.getText());

                strSQL+=" ORDER BY x.tx_codAltItm";
                System.out.println("cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_ITM,      rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,  rst.getString("tx_codAltItm"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,      rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED,      rst.getString("tx_uniMed"));
                    vecReg.add(INT_TBL_DAT_COD_GRP_ITM,  rst.getString("co_grp"));
                    vecReg.add(INT_TBL_DAT_DES_COR_GRP,  rst.getString("tx_desCorGrp"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_GRP,  rst.getString("tx_desLarGrp"));
                    vecReg.add(INT_TBL_DAT_COD_PAR_ARA,  rst.getString("co_parAra"));
                    vecReg.add(INT_TBL_DAT_DES_COR_PAR,  rst.getString("tx_desCorPar"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_PAR,  rst.getString("tx_desLarPar"));                    
                    vecReg.add(INT_TBL_DAT_POR_PAR_ARA,  rst.getString("nd_porParAra"));
                    vecReg.add(INT_TBL_DAT_CHK_ISD_COS,  null);
                    vecReg.add(INT_TBL_DAT_CHK_ISD_CRE,  null);
                    
                    /* Cuando la partida arancelaria aplica al costo, NO genera credito Tributario. */
                    String strChkISD=rst.getObject("st_isdCos")==null?"P":(rst.getString("st_isdCos").equals("")?"P":rst.getString("st_isdCos"));
                    if(strChkISD.equals("S")){
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_ISD_COS);
                    }   
                    else if(strChkISD.equals("N")){
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_ISD_CRE);
                    }
                    
                    vecDat.add(vecReg);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e) {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception e) {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }    
    
    

    
    
}
