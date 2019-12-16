/*
 * ZafImp28_04.java
 *
 * Created on 17 de enero de 2018, 9:12
 *
 */
package Importaciones.ZafImp28;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Rosa Zambrano
 */
public class ZafImp28_04 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.    
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_COD_BOD = 1;
    private static final int INT_TBL_DAT_NOM_BOD = 2;
    private static final int INT_TBL_DAT_CDI_AUT=3;                             //Columna dinámica: Autorización.
    
    //Constantes de indices para columnas por grupo.
    private static final int INT_IND_COL_CHK_BOD = 0;
    private static final int INT_IND_COL_CHK_BOD_AUX = 1;
    private static final int INT_IND_COL_ING_IMP_COD_EMP = 2;
    private static final int INT_IND_COL_ING_IMP_COD_LOC = 3;
    private static final int INT_IND_COL_ING_IMP_COD_TIP_DOC = 4;
    private static final int INT_IND_COL_ING_IMP_COD_DOC = 5;
    private static final int INT_IND_COL_ING_IMP_COD_REG = 6;
    
    private static final int INT_IND_COL_ORD_DIS_COD_EMP = 7;
    private static final int INT_IND_COL_ORD_DIS_COD_LOC = 8;
    private static final int INT_IND_COL_ORD_DIS_COD_TIP_DOC = 9;
    private static final int INT_IND_COL_ORD_DIS_COD_DOC = 10;
    private static final int INT_IND_COL_ORD_DIS_COD_REG = 11;
    
    private static final int INT_IND_COL_CAN_ING_IMP = 12;
    
    private static final int INT_IND_COL_CAN_AUT_USR = 13;
    private static final int INT_IND_COL_CAN_TOT_AUT_AUX = 14;
    private static final int INT_IND_COL_CAN_TOT_TRS = 15;
    private static final int INT_IND_COL_CAN_PEN_TRS = 16;
       
    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_NUM_TOT_CES=3;                                 //Número total de columnas estáticas.
    private static final int INT_NUM_CDI_GRP=17;                                //Número de columnas dinámicas por grupo.
    private static final int INT_FAC_CDI=1;                                     //Factor para calcular total de columnas dinámicas. 
    
    //Arreglos: Datos Usuario
    private ArrayList arlDatGrpAdi, arlRegGrpAdi;
    private static final int INT_ARL_DAT_COD_EMP=0;
    private static final int INT_ARL_DAT_COD_LOC=1;
    private static final int INT_ARL_DAT_COD_TIPDOC=2;
    private static final int INT_ARL_DAT_COD_DOC=3;
    private static final int INT_ARL_DAT_NUM_PED=4;
    private static final int INT_ARL_DAT_COD_REG=5;
    private static final int INT_ARL_DAT_EST_INGIMP=6;
    
    //Constantes: 
    private static final int INT_COD_BOD_CEN_DIS = 15;                          //En este caso es para el Inmaconsa, ya que esta bodega es el centro de distribución
    private static final int INT_COD_TIP_DOC_ORD_DIS = 203;             
    
    //Constantes: Para manejar autorización.
    public static final int INT_BUT_AUT=0;                                      /**Un valor para getSelectedButton: Indica "Botón Autorizar".*/
    public static final int INT_BUT_CER=1;                                      /**Un valor para getSelectedButton: Indica "Botón Cerrar".*/
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblTot objTblTot;                                                //JTable de totales.
    private ZafPerUsr objPerUsr;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkAux;                //Render: Presentar JCheckBox en JTable.
    private ZafTblCelRenChk objTblCelRenChkColDin;                              //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkAux;                //Editor: JCheckBox en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                    //Editor: Texto en celda.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCan;                //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblColDin;                              //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblEdi objTblEdi;                                                //Editor: Editor del JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafImp objImp;
    
    //Variables de la clase.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private Vector vecReg, vecDat, vecCab, vecAux, vecColEdi;
    private boolean blnCon;                                              //true: Continua la ejecución del hilo.
    private boolean isAutMod;  
    private int intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, intCodRegIngImp, intCodItm, intCodItmMae, intCodItmGrp;
    private BigDecimal bdgCanPerAut, bdgCanIngAju, bdgCanTotCon;
    private int intButSelDlg;                                            //Botón seleccionado en el JDialog.
    private int intNumColEst;                                            //Número de Columnas Estaticas
    private int intNumColGrpAdi=0;
    private int intColIni, intColFin;    
    
    private StringBuffer stbAut;
    private String strCodAltItm, strNomItm, strCodLetItm;
    private String strSQL="";
    private String strEstAutIng="";

    private String strVersion=" v0.1";
    
    public ZafImp28_04(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
        
            isAutMod=false;
        } 
        catch (CloneNotSupportedException e) {       this.setTitle(this.getTitle() + " [ERROR]");      }
    }    
    
    /**
     * Constructor para visualización de la ventana
     * @param parent
     * @param modal
     * @param obj
     * @param codItmMae
     * @param codItm
     * @param codItmGrp
     * @param codAltItm
     * @param nomItm
     * @param codLetItm
     * @param codEmpIngImp
     * @param codLocIngImp
     * @param codTipDocIngImp
     * @param codDocIngImp
     * @param codRegIngImp
     * @param canIngCanAju
     * @param canTotCon 
     */
    public ZafImp28_04(java.awt.Frame parent, boolean modal, ZafParSis obj, int codItmMae, int codItm, int codItmGrp, String codAltItm, String nomItm, String codLetItm, int codEmpIngImp, int codLocIngImp, int codTipDocIngImp, int codDocIngImp, int codRegIngImp, BigDecimal canIngCanAju, BigDecimal canTotCon)
    {
        super(parent, modal);
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            
            initComponents();
            
            intCodEmpIngImp = codEmpIngImp;
            intCodLocIngImp = codLocIngImp;
            intCodTipDocIngImp = codTipDocIngImp;
            intCodDocIngImp = codDocIngImp;
            intCodRegIngImp = codRegIngImp;
            intCodItm = codItm;
            intCodItmGrp = codItmGrp;
            intCodItmMae = codItmMae;
            strCodAltItm = codAltItm;
            strNomItm = nomItm;
            strCodLetItm = codLetItm;
            bdgCanIngAju = canIngCanAju;
            bdgCanTotCon = canTotCon;
            stbAut=null;
            
            //Cantidad permitida autorizar
            bdgCanPerAut = bdgCanIngAju.compareTo(bdgCanTotCon) < 0 ? bdgCanIngAju : bdgCanTotCon;
            
            if (!configurarFrm())
                exitForm();

            isAutMod=false;
        } 
        catch (CloneNotSupportedException e) {       this.setTitle(this.getTitle() + " [ERROR]");      }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Titulo Programa.                        
            lblTit.setText("Autorización de transferencia en bodegas "+strVersion);
            
            //Configurar los JTables.
            if(configurarTblDat())
            {
                txtCodItmEmp.setText("" + intCodItm);
                txtCodItmMae.setText("" + intCodItmMae);
                txtCodItmGrp.setText("" + intCodItmGrp);
                txtCodAltItm.setText(strCodAltItm);
                txtCodLetItm.setText(strCodLetItm);
                txtNomItm.setText(strNomItm);
                txtCanIngImp.setText("" + (objUti.redondearBigDecimal(bdgCanIngAju, objParSis.getDecimalesMostrar())));
                txtCanCon.setText("" + (objUti.redondearBigDecimal(bdgCanTotCon, objParSis.getDecimalesMostrar())));

                //Ocultar campos
                txtCodItmEmp.setVisible(false);
                txtCodItmGrp.setVisible(false);
                txtCodItmMae.setVisible(false);

                //Obtener los permisos del usuario.
                objPerUsr=new ZafPerUsr(objParSis);

                //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                //Diálogo "Listado de bodegas a autorizar": Guardar
                if(!objPerUsr.isOpcionEnabled(4076)){
                    butAut.setVisible(false);
                }	

                //No editables
                txtCodItmEmp.setEnabled(false);
                txtCodItmGrp.setEnabled(false);
                txtCodItmMae.setEnabled(false);
                txtCodAltItm.setEnabled(false);
                txtCodLetItm.setEnabled(false);
                txtNomItm.setEnabled(false);
                txtCanIngImp.setEnabled(false);
                txtCanCon.setEnabled(false);

                if (cargarDatos())
                {
                    calcularCanBodInm();  
                    calcularCanPenTrs();  
                    objTblMod.initRowsState();
                }            
            }
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
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(INT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Nom.Bod.");
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(130);
            
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
            
            //Configurar JTable: Renderizar celdas
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;         
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblDat);  
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);  

            //Establecer Modo: Editar
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
                case INT_TBL_DAT_COD_BOD:
                    strMsg="Código Bodega Destino";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Nombre de la bodega a la que se esta autorizando transferir.";
                    break;  
                case INT_TBL_DAT_CDI_AUT:
                    for(int i=0; i<(arlDatGrpAdi.size()); i++) //Cantidad de tablas
                    {
                        for(int j=0; j<(INT_NUM_CDI_GRP); j++) //Número de columnas dinámicas por tabla.
                        {
                            intColSel=tblDat.columnAtPoint(evt.getPoint());
                            int intIndColGrp =(intColIni+j+(i*INT_NUM_CDI_GRP)); //Obtengo Indice de la Columna.
                            
                            if(intColSel == intIndColGrp && j==INT_IND_COL_CHK_BOD)  {                    
                                strMsg="Indica que se ha autorizado a transferir a la bodega seleccionada.";
                            }
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_CHK_BOD_AUX)  {
                                strMsg="Indica que ya existia autorizacion para transferir a la bodega seleccionada.";
                            } 
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_CAN_AUT_USR)  {
                                strMsg="Cantidad autorizada por el usuario";
                            } 
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_CAN_TOT_AUT_AUX) {
                                strMsg="Cantidad total que ya habia sido autorizada en la bodega seleccionada";
                            } 
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_CAN_TOT_TRS) {
                                strMsg="Cantidad total transferida en la bodega seleccionada";
                            }   
                            else if(intColSel == intIndColGrp && j==INT_IND_COL_CAN_PEN_TRS) {
                                strMsg="Cantidad pendiente a transferir en la bodega seleccionada";
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panItm = new javax.swing.JPanel();
        lblItm = new javax.swing.JLabel();
        txtCodItmEmp = new javax.swing.JTextField();
        txtCodItmGrp = new javax.swing.JTextField();
        txtCodItmMae = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtCodLetItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        txtCanIngImp = new javax.swing.JTextField();
        lblCanIngImp = new javax.swing.JLabel();
        lblCanCon = new javax.swing.JLabel();
        txtCanCon = new javax.swing.JTextField();
        panDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAut = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 70));
        panCab.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Autorización de transferencia en bodegas");
        panCab.add(lblTit, java.awt.BorderLayout.NORTH);

        panItm.setPreferredSize(new java.awt.Dimension(100, 30));
        panItm.setLayout(null);

        lblItm.setText("Item:");
        panItm.add(lblItm);
        lblItm.setBounds(10, 10, 40, 14);
        panItm.add(txtCodItmEmp);
        txtCodItmEmp.setBounds(20, 10, 10, 20);
        panItm.add(txtCodItmGrp);
        txtCodItmGrp.setBounds(30, 10, 10, 20);
        panItm.add(txtCodItmMae);
        txtCodItmMae.setBounds(40, 10, 10, 20);
        panItm.add(txtCodAltItm);
        txtCodAltItm.setBounds(50, 10, 94, 20);
        panItm.add(txtCodLetItm);
        txtCodLetItm.setBounds(145, 10, 40, 20);

        txtNomItm.setVerifyInputWhenFocusTarget(false);
        panItm.add(txtNomItm);
        txtNomItm.setBounds(186, 10, 300, 20);

        txtCanIngImp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanIngImp.setVerifyInputWhenFocusTarget(false);
        panItm.add(txtCanIngImp);
        txtCanIngImp.setBounds(186, 32, 110, 20);

        lblCanIngImp.setText("Can.Ing.Imp/Can.Aju:");
        panItm.add(lblCanIngImp);
        lblCanIngImp.setBounds(50, 32, 130, 20);

        lblCanCon.setText("Can.Con:");
        panItm.add(lblCanCon);
        lblCanCon.setBounds(310, 32, 67, 20);

        txtCanCon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanCon.setVerifyInputWhenFocusTarget(false);
        panItm.add(txtCanCon);
        txtCanCon.setBounds(376, 32, 110, 20);

        panCab.add(panItm, java.awt.BorderLayout.CENTER);

        PanFrm.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

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

        panDet.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        PanFrm.add(panDet, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butAut.setText("Autorizar");
        butAut.setPreferredSize(new java.awt.Dimension(92, 25));
        butAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAutActionPerformed(evt);
            }
        });
        panBot.add(butAut);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.LINE_END);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(516, 389));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAutActionPerformed
        if (objTblMod.isDataModelChanged()) {
            if(isCamVal()){
                //Genera la Orden de Distribución
                if (guardarAut()) 
                {
                    //mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
                    intButSelDlg=INT_BUT_AUT;
                    isAutMod=true;
                    mostrarMsgInf("<HTML>Información correcta.</HTML>");
                    dispose();
                } 
                else {
                    mostrarMsgInf("<HTML>Verifique los datos y vuelva a intentarlo.</HTML>");
                }
            } 
            else {
                mostrarMsgInf("<HTML>Verifique los datos.</HTML>");
            }
        } 
        else {
            mostrarMsgInf("<HTML>Seleccione un registro y de click en el botón Autorizar.<BR>Si no desea seleccionar registro alguno de click en Cerrar.</HTML>");
        }
    }//GEN-LAST:event_butAutActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        intButSelDlg=INT_BUT_CER;
        isAutMod=false;
        setSQLAut(null);
        dispose();
    }//GEN-LAST:event_butCerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAut;
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblCanCon;
    private javax.swing.JLabel lblCanIngImp;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panItm;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCanCon;
    private javax.swing.JTextField txtCanIngImp;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodItmEmp;
    private javax.swing.JTextField txtCodItmGrp;
    private javax.swing.JTextField txtCodItmMae;
    private javax.swing.JTextField txtCodLetItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
       
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podrá utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y
     * que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_DEN).
     * <LI>1: Click en el botón Aceptar (INT_BUT_AUT).
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()    {
        return intButSelDlg;
    }
    
    /**
    * Función que permite conocer si el modelo ha cambiado
    * @return true Si el modelo ha cambiado y se da click en Autorizar
    * <BR> false Si el modelo no ha cambiado, o si el modelo ha cambiado pero se da click en Cerrar
    */
    public boolean isAutMod() {
        return isAutMod;
    }
        
    /**
     * Obtiene la sentencia a ejecutarse en la autorización de bodegas.
     * @return La cadena que contiene la sentencia.
     */    
    public StringBuffer getSQLAut()
    {
        return stbAut;
    }
        
    /**
     * Establece la sentencia a ejecutarse en la autorización de bodegas.
     * @param sentencia La cadena que contiene la sentencia.
     */    
    public void setSQLAut(StringBuffer sentencia)
    {
        stbAut=sentencia;
    }
        
 
    /** Cerrar la aplicación. */
    private void exitForm() {
        intButSelDlg=INT_BUT_CER;
        isAutMod=false;
        dispose();
    }

    private boolean cargarDatos() 
    {
        boolean blnRes=false;
        try
        {
             if(deleteColAdd()) {
                if(getColAdd()) {
                    if(addColTblDat()) {
                        if(cargarReg()) {
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
    private boolean deleteColAdd()
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--)
            {
                objTblMod.removeColumn(tblDat, i);                
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);  System.err.println("deleteColAdd");  blnRes=false;      }
        return blnRes;
    }
    
    /**
     * Función que obtiene las columnas a adicionar
     * @return 
     */ 
    private boolean getColAdd()
    {
        boolean blnRes=true;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                /* Grupos */ 
                strSQL ="";
                strSQL+=" SELECT * FROM(";
                strSQL+="     SELECT CASE WHEN a.st_ingImp IS NULL THEN 'N' ";
                strSQL+="            ELSE CASE WHEN a.st_ingImp = 'A' THEN 'S' ";
                strSQL+="            ELSE CASE WHEN a.st_ingImp = 'B' THEN 'S' ";
                strSQL+="            ELSE CASE WHEN a.st_ingImp = 'T' THEN 'S' ";
                strSQL+="            ELSE a.st_ingImp END END END END AS st_ingImp ";
                strSQL+="          , a.fe_doc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_numDoc2, a1.co_Reg";
                strSQL+="     FROM tbm_CabMovInv AS a";
                strSQL+="     INNER JOIN tbm_DetMovInv as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc";
                strSQL+="     INNER JOIN tbm_equInv as a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="     WHERE a.co_emp=" + intCodEmpIngImp;
                strSQL+="     AND a.co_loc=" + intCodLocIngImp;
                strSQL+="     AND a.co_tipDoc=" + intCodTipDocIngImp;
                strSQL+="     AND a.co_doc=" + intCodDocIngImp;
                strSQL+="     AND a2.co_itmMae=" + intCodItmMae;
                strSQL+="   UNION ALL ";
                strSQL+="     SELECT CASE WHEN a1.st_ingImp IS NULL THEN 'N' ";
                strSQL+="            ELSE CASE WHEN a1.st_ingImp = 'A' THEN 'S' ";
                strSQL+="            ELSE CASE WHEN a1.st_ingImp = 'B' THEN 'S' ";
                strSQL+="            ELSE CASE WHEN a1.st_ingImp = 'T' THEN 'S' ";
                strSQL+="            ELSE a1.st_ingImp END END END END AS st_ingImp ";
                strSQL+="          , a1.fe_doc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a1.tx_numDoc2, a2.co_Reg";
                strSQL+="     FROM tbr_CabMovInv AS a";
                strSQL+="     INNER JOIN tbm_cabMovInv as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+="     INNER JOIN tbm_DetMovInv as a2 ON a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_Doc";
                strSQL+="     INNER JOIN tbm_equInv as a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm"; 
                strSQL+="     WHERE a1.st_reg IN ('A')"; //Ajustes activos, excluye los ajustes con st_Reg IN ('O', 'I', 'E')
                strSQL+="     AND (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) NOT IN ('I')";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+="     AND a2.nd_can>0 "; //Solo items de ajustes de ingreso
                strSQL+="     AND a.co_empRel=" + intCodEmpIngImp;
                strSQL+="     AND a.co_locRel=" + intCodLocIngImp; 
                strSQL+="     AND a.co_tipDocRel=" + intCodTipDocIngImp; 
                strSQL+="     AND a.co_docRel=" + intCodDocIngImp;
                strSQL+="     AND a3.co_itmMae=" + intCodItmMae;
                strSQL+="     AND a.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa()+" ";
                strSQL+="                         and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+=" ) AS a";
                strSQL+=" GROUP BY a.st_ingImp, a.fe_doc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_numDoc2, a.co_Reg ";
                strSQL+=" ORDER BY a.fe_doc DESC, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc ";  
                System.out.println("getColAdd: "+strSQL);
                rst=stm.executeQuery(strSQL);
                intNumColGrpAdi=0;
                arlDatGrpAdi =new ArrayList();
                while(rst.next()) 
                {
                    arlRegGrpAdi= new ArrayList();
                    arlRegGrpAdi.add(INT_ARL_DAT_COD_EMP, rst.getString("co_emp"));
                    arlRegGrpAdi.add(INT_ARL_DAT_COD_LOC, rst.getString("co_loc"));
                    arlRegGrpAdi.add(INT_ARL_DAT_COD_TIPDOC, rst.getString("co_tipDoc"));
                    arlRegGrpAdi.add(INT_ARL_DAT_COD_DOC, rst.getString("co_doc"));
                    arlRegGrpAdi.add(INT_ARL_DAT_NUM_PED, rst.getString("tx_numDoc2"));
                    arlRegGrpAdi.add(INT_ARL_DAT_COD_REG, rst.getString("co_Reg"));
                    arlRegGrpAdi.add(INT_ARL_DAT_EST_INGIMP, rst.getString("st_ingImp"));
                    arlDatGrpAdi.add(arlRegGrpAdi);
                }
                intNumColGrpAdi=arlDatGrpAdi.size();
                rst.close();                         
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){         blnRes = false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;        
    }
    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo a la cantidad de usuarios".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean addColTblDat()
    {
        boolean blnRes=true;
        ZafTblHeaGrp objTblHeaTbl = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaTbl.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColTbl = null;
        javax.swing.table.TableColumn tbc;
        String strTitTbl="";
        int i;
        try 
        {
            intColIni=objTblMod.getColumnCount();
            objTblCelRenLblColDin = new ZafTblCelRenLbl();
            vecColEdi = new Vector();  //Configurar JTable: Establecer columnas editables.
            int []intCol= new int [intNumColGrpAdi*4];
            int intTotColSum=0;
            for(i=0; i<intNumColGrpAdi; i++) //Cantidad de grupos
            {
                for(int j=0; j<(INT_NUM_CDI_GRP); j++) //Número de columnas dinámicas por grupo.
                {
                    strTitTbl =   (j==INT_IND_COL_ING_IMP_COD_EMP ? "Cód.Emp.Ing.Imp." 
                                : (j==INT_IND_COL_ING_IMP_COD_LOC ? "Cód.Loc.Ing.Imp." 
                                : (j==INT_IND_COL_ING_IMP_COD_TIP_DOC ? "Cód.Tip.Doc.Ing.Imp." 
                                : (j==INT_IND_COL_ING_IMP_COD_DOC ? "Cód.Doc.Ing.Imp." 
                                : (j==INT_IND_COL_ING_IMP_COD_REG ? "Cód.Reg.Ing.Imp." 
                                : (j==INT_IND_COL_ORD_DIS_COD_EMP ? "Cód.Emp.Ord.Dis." 
                                : (j==INT_IND_COL_ORD_DIS_COD_LOC ? "Cód.Loc.Ord.Dis." 
                                : (j==INT_IND_COL_ORD_DIS_COD_TIP_DOC ? "Cód.Tip.Doc.Ord.Dis." 
                                : (j==INT_IND_COL_ORD_DIS_COD_DOC ? "Cód.Doc.Ord.Dis."
                                : (j==INT_IND_COL_ORD_DIS_COD_REG ? "Cód.Reg.Ord.Dis."
                                : (j==INT_IND_COL_CAN_ING_IMP ? "Can.Ing.Imp." 
                                : (j==INT_IND_COL_CAN_AUT_USR ? "Can.Aut.Usr." 
                                : (j==INT_IND_COL_CAN_TOT_AUT_AUX ? "Can.Tot.Aut." 
                                : (j==INT_IND_COL_CAN_TOT_TRS ? "Can.Trs." 
                                : (j==INT_IND_COL_CAN_PEN_TRS ? "Can.Pen.Trs."
                                : "" ))))))))))))))) ;
                    
                    int intIndColGrp =(intColIni+j+(i*INT_NUM_CDI_GRP)); //Obtengo Indice de la Columna.
                    int intIndColChkBod=(intColIni+INT_IND_COL_CHK_BOD+(i*INT_NUM_CDI_GRP));
                    int intIndColChkBodAux=(intColIni+INT_IND_COL_CHK_BOD_AUX+(i*INT_NUM_CDI_GRP));                    
                    int intIndColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(i*INT_NUM_CDI_GRP));
                    int intIndColCanTotAut=(intColIni+INT_IND_COL_CAN_TOT_AUT_AUX+(i*INT_NUM_CDI_GRP));
                    int intIndColCanTotTrs=(intColIni+INT_IND_COL_CAN_TOT_TRS+(i*INT_NUM_CDI_GRP));
                    int intIndColCanPenTrs=(intColIni+INT_IND_COL_CAN_PEN_TRS+(i*INT_NUM_CDI_GRP));

                    tbc=new javax.swing.table.TableColumn(intIndColGrp);     //Creo la columna dinamica.
                    tbc.setHeaderValue("" + strTitTbl + "");

                    //Configurar JTable: Establecer el ancho de la columna.
                    if((j==INT_IND_COL_CHK_BOD) || (j==INT_IND_COL_CHK_BOD_AUX) ) //Check 
                    {
                        tbc.setPreferredWidth(25);
                        if((j==INT_IND_COL_CHK_BOD_AUX)){
                            vecColEdi.add("" + (intColIni+INT_IND_COL_CHK_BOD_AUX+(i*INT_NUM_CDI_GRP)));
                            if(objParSis.getCodigoUsuario()!=1){
                                //Ocultar columnas
                                tbc.setWidth(0);
                                tbc.setResizable(false);
                                tbc.setMaxWidth(0);
                                tbc.setMinWidth(0);
                                tbc.setPreferredWidth(0);      
                            }
                        }
                        
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenChkColDin=new ZafTblCelRenChk();
                        tbc.setCellRenderer(objTblCelRenChkColDin);   
                        //objTblCelRenChkColDin=null;
                        
                        //Configurar JTable: Editor de celdas
                        if((j==INT_IND_COL_CHK_BOD)){
                            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
                            tbc.setCellEditor(objTblCelEdiChk);
                            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                                int intFil=-1;
                                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                    intFil=tblDat.getSelectedRow();
                                    if (objTblCelEdiChk.isChecked()) {
                                        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                                            objTblMod.setValueAt(false, i, intIndColChkBod);
                                        }
                                    }
                                }
                                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                    if (objTblCelEdiChk.isChecked()) {
                                        for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                                            if (intFil != i) {
                                                objTblMod.setValueAt(false, i, intIndColChkBod);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                        //Configurar JTable: Editor de celdas.
                        if((j==INT_IND_COL_CHK_BOD_AUX)){
                            objTblCelEdiChkAux=new ZafTblCelEdiChk(tblDat);
                            tbc.setCellEditor(objTblCelEdiChkAux);
                            objTblCelEdiChkAux.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                    /**
                                     * Esta validación es utilizada para crear la Orden de distribución a los items que ingresan por ajuste.
                                     * <BR> Hasta que se realice la implementación en la Libreria de ZafAjuInv, se realizará la creación a través de este programa.
                                     */
                                    if(objParSis.getCodigoUsuario()==1)
                                    {
                                        if(objTblMod.isChecked(tblDat.getSelectedRow(), intIndColChkBodAux))
                                        {
                                            if (mostrarMsgCon("<HTML>Si desmarca se creará una nueva autorización para la bodega seleccionada.<BR>¿Está seguro que desea realizar esta operación?</HTML>")==0)
                                            {
                                                //Se asigna false al check para que cree una nueva autorización (OrdDis)
                                                objTblCelEdiChkAux.setChecked(false, tblDat.getSelectedRow(), intIndColChkBodAux);
                                            }
                                            else{
                                                objTblCelEdiChkAux.setCancelarEdicion(true);
                                                if(objTblMod.isChecked(tblDat.getSelectedRow(), intIndColChkBodAux)) {
                                                    objTblCelEdiChkAux.setChecked(true, tblDat.getSelectedRow(), intIndColChkBodAux);
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        objTblCelEdiChkAux.setCancelarEdicion(true);
                                    }
                                }
                                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                }
                            });
                        }
                    }
                    else if((j==INT_IND_COL_CAN_ING_IMP) || (j==INT_IND_COL_CAN_AUT_USR) ||(j==INT_IND_COL_CAN_TOT_AUT_AUX) || (j==INT_IND_COL_CAN_TOT_TRS)|| (j==INT_IND_COL_CAN_PEN_TRS)) 
                    {
                        if( (j==INT_IND_COL_CAN_ING_IMP) || (j==INT_IND_COL_CAN_TOT_AUT_AUX) ){
                            //Ocultar columnas
                            tbc.setWidth(0);
                            tbc.setResizable(false);
                            tbc.setMaxWidth(0);
                            tbc.setMinWidth(0);
                            tbc.setPreferredWidth(0);                        
                        }
                        else{
                            tbc.setPreferredWidth(70);
                        }
                   
                        //Establecer Tipo de Columna
                        objTblMod.setColumnDataType(intIndColCanAutUsr, objTblMod.INT_COL_DBL, new Integer(0), null);
                        
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblCan = new ZafTblCelRenLbl();
                        objTblCelRenLblCan.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblCan.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                        objTblCelRenLblCan.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
                        if((j==INT_IND_COL_CAN_AUT_USR)) {
                            objTblCelRenLblCan.setBackground(objParSis.getColorCamposObligatorios());
                            vecColEdi.add("" + (intColIni+INT_IND_COL_CAN_AUT_USR+(i*INT_NUM_CDI_GRP)));
                        }
                        tbc.setCellRenderer(objTblCelRenLblCan);
                        objTblCelRenLblCan = null;
                        
                        //Configurar Jtable: Establecer tipo de dato.
                        objTblMod.setColumnDataType(intIndColGrp, objTblMod.INT_COL_DBL, new Integer(0), null);
                        
                        //Configurar Jtable: Texto celdas
                        if((j==INT_IND_COL_CAN_AUT_USR)) {
                            final int colGrp=i;                      
                            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
                            tbc.setCellEditor(objTblCelEdiTxt);
                            objTblCelEdiTxt.setCancelarEdicion(true);
                            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                                int intFil = -1;
                                BigDecimal bdeValCanAutBef = new BigDecimal("0"), bdeValCanAutAft = new BigDecimal("0");
                                BigDecimal bdeValCanTrsBef = new BigDecimal("0");
                                BigDecimal bdeValCanAutTotAft = new BigDecimal("0");
                                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                    intFil = tblDat.getSelectedRow();
                                    bdeValCanAutBef = new BigDecimal(objTblMod.getValueAt(intFil, intIndColCanAutUsr) == null ? "0" : (objTblMod.getValueAt(intFil, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, intIndColCanAutUsr).toString()));
                                    bdeValCanTrsBef = new BigDecimal(objTblMod.getValueAt(intFil, intIndColCanTotTrs) == null ? "0" : (objTblMod.getValueAt(intFil, intIndColCanTotTrs).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, intIndColCanTotTrs).toString()));
                                    for(int k=0; k<(intNumColGrpAdi); k++) //Cantidad de grupos
                                    {
                                        //System.out.println("Antes: > k: "+k+"   <> colGrp: "+colGrp);
                                        if(k==colGrp)
                                        {   
                                            String strEstIngImp=objUti.getStringValueAt(arlDatGrpAdi, colGrp, INT_ARL_DAT_EST_INGIMP);
                                            //Valida que solo se pueda editar cuando el pedido no ha sido cerrado.
                                            if (!strEstIngImp.equals("S")){
                                                mostrarMsgInf("<HTML>No se puede realizar la autorización de bodega de un pedido cerrado.</HTML>");
                                                objTblCelEdiTxt.setCancelarEdicion(true);
                                                tblDat.setRowSelectionInterval(intFil, intFil);
                                                break;
                                            }
                                            else {
                                                objTblCelEdiTxt.setCancelarEdicion(false);
                                                break;
                                            }
                                        }
                                    }
                                }

                                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                    for(int k=0; k<(intNumColGrpAdi); k++) //Cantidad de grupos
                                    {
                                        if(k==colGrp)
                                        {     
                                            bdeValCanAutAft = new BigDecimal(objTblMod.getValueAt(intFil, intIndColCanAutUsr) == null ? "0" : (objTblMod.getValueAt(intFil, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, intIndColCanAutUsr).toString()));
                                            objTblMod.setChecked(true, intFil, intIndColChkBod);
                                            calcularCanBodInm();
                                            calcularCanPenTrs(intFil);

                                            if (getCanPenTrsInm().compareTo(new BigDecimal("0")) >= 0) //se permite
                                            {
                                                if (bdgCanPerAut.compareTo(getCanAutBod()) >= 0) {
                                                    //el valor es mayor a lo q ya se ha transferido
                                                } else {
                                                    objTblMod.setValueAt(bdeValCanAutBef, intFil, intIndColCanAutUsr);
                                                    mostrarMsgInf("<HTML>La cantidad ingresada excede el valor del ingreso por importación.<BR>Verifique el dato ingresado y vuelva a intentarlo.</HTML>");
                                                    tblDat.setRowSelectionInterval(intFil, intFil);
                                                }
                                            } 
                                            else  //no se permite
                                            {
                                                objTblMod.setValueAt(bdeValCanAutBef, intFil, intIndColCanAutUsr);
                                                mostrarMsgInf("<HTML>La cantidad ingresada excede el valor del ingreso por importación.<BR>Verifique el dato ingresado y vuelva a intentarlo.</HTML>");
                                                tblDat.setRowSelectionInterval(intFil, intFil);
                                            }

                                            bdeValCanAutAft = new BigDecimal(objTblMod.getValueAt(intFil, intIndColCanAutUsr) == null ? "0" : (objTblMod.getValueAt(intFil, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(intFil, intIndColCanAutUsr).toString()));
                                            if (bdeValCanAutAft.compareTo(new BigDecimal("0")) <=0) 
                                            {
                                                if (objTblMod.isChecked(intFil, intIndColChkBod)) {
                                                    objTblMod.setChecked(false, intFil, intIndColChkBod);
                                                }
                                            }
                                            calcularCanBodInm();
                                            calcularCanPenTrs(intFil);
                                        }
                                    }
                                }
                            });

                            //Configurar JTable ZafTblTot: Establecer relación entre el JTable de datos y JTable de totales.
                            intCol[intTotColSum]=intIndColCanAutUsr;
                            intTotColSum++;
                            intCol[intTotColSum]=intIndColCanTotAut;
                            intTotColSum++;
                            intCol[intTotColSum]=intIndColCanTotTrs;
                            intTotColSum++;
                            intCol[intTotColSum]=intIndColCanPenTrs;
                            intTotColSum++;

                        }
                    }
                    else {  
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblColDin=new ZafTblCelRenLbl();
                        tbc.setCellRenderer(objTblCelRenLblColDin);
                        objTblCelRenLblColDin=null;
                        //Ocultar columnas
                        tbc.setWidth(0);
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);
                        tbc.setResizable(true);
                    }
                    
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);
                    if(j==INT_IND_COL_CHK_BOD) //Primera Columna del Grupo.
                    {
                        objTblHeaColTbl=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatGrpAdi, i, INT_ARL_DAT_NUM_PED) + "");
                        objTblHeaColTbl.setHeight(16);
                    }
                    objTblHeaTbl.addColumnGroup(objTblHeaColTbl);
                    objTblHeaColTbl.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                }
            }
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Configurar JTable: Establecer columnas editables.
            objTblMod.setColumnasEditables(vecColEdi);
            
            //Obtiene última Columna
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

    /**
     * Esta función permite cargar los registros 
     * @return true: Si se pudo cargar los datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() 
    {
        boolean blnRes=true;
        String strDat="";
        int intIndChkBod=0, intIndChkBodAux=0;
        Boolean blnChkBod=false;
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                stm = con.createStatement();
                strSQL ="";
                strSQL+=" SELECT b.co_emp, b.co_bod, b.tx_nomBodAutDis\n";
                for(int i=0; i<(intNumColGrpAdi); i++) {
                    strSQL+="        , a"+(i+1)+".co_empOrdDis as a"+(i+1)+"_co_empOrdDis, a"+(i+1)+".co_locOrdDis as a"+(i+1)+"_co_locOrdDis, a"+(i+1)+".co_tipDocOrdDis as a"+(i+1)+"_co_tipDocOrdDis, a"+(i+1)+".co_docOrdDis as a"+(i+1)+"_co_docOrdDis";
                    strSQL+="        , a"+(i+1)+".co_RegOrdDis as a"+(i+1)+"_co_RegOrdDis, a"+(i+1)+".co_itmMae as a"+(i+1)+"_co_itmMae, a"+(i+1)+".co_itmGrp as a"+(i+1)+"_co_itmGrp";
                    strSQL+="        , a"+(i+1)+".nd_CanAutBod as a"+(i+1)+"_nd_CanAutBod, t"+(i+1)+".nd_CanTrs as t"+(i+1)+"_nd_CanTrs, c"+(i+1)+".nd_CanIngImp as c"+(i+1)+"_nd_CanIngImp";
                    strSQL+="        , (a"+(i+1)+".nd_CanAutBod - ( CASE WHEN t"+(i+1)+".nd_CanTrs IS NULL THEN 0 ELSE t"+(i+1)+".nd_CanTrs END) ) as y"+(i+1)+"_nd_CanPenInm ";                    
                    strSQL+="        , a"+(i+1)+".co_empIngImp as a"+(i+1)+"_co_empIngImp, a"+(i+1)+".co_locIngImp as a"+(i+1)+"_co_locIngImp, a"+(i+1)+".co_tipDocIngImp as a"+(i+1)+"_co_tipDocIngImp, a"+(i+1)+".co_docIngImp as a"+(i+1)+"_co_docIngImp, a"+(i+1)+".co_regIngImp as a"+(i+1)+"_co_regIngImp\n";
                    strSQL+="        , c"+(i+1)+".st_ingImp as c"+(i+1)+"_st_ingImp";
                }
                strSQL+=" FROM (  /* Consulta Bodegas */\n";
                strSQL+="       SELECT a1.co_emp, a1.co_bod, a1.tx_nom AS tx_nomBodAutDis";
                strSQL+="       FROM tbm_bod AS a1";
                strSQL+="       WHERE a1.st_reg='A' AND a1.co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+="       AND a1.co_bod NOT IN(4,6,8,9,16,18,20,21,22,23,27,29,33) ";
                strSQL+=" ) AS b\n";
                for(int i=0; i<(intNumColGrpAdi); i++) 
                {
                    strSQL+=" LEFT OUTER JOIN( /* Autorización a Bodegas */\n"; 
                    strSQL+="       SELECT a.co_bod as co_bodOrdDis, a.co_emp AS co_empOrdDis, a.co_loc as co_locOrdDis, a.co_tipDoc as co_tipDocOrdDis, a.co_doc as co_docOrdDis";
                    strSQL+="            , a1.co_Reg as co_RegOrdDis, a2.co_itmMae, a1.co_itm as co_itmGrp, a1.nd_Can as nd_CanAutBod";
                    strSQL+=" 	         , a1.co_EmpRel as co_empIngImp, a1.co_locRel as co_locIngImp, a1.co_tipDocRel as co_tipDocIngImp, a1.co_docRel as co_docIngImp, a1.co_regRel as co_regIngImp";
                    strSQL+="       FROM tbm_cabOrdDis AS a ";                
                    strSQL+="       INNER JOIN tbm_detOrdDis AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                    strSQL+="       INNER JOIN tbm_equInv AS a2 ON a.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="       WHERE a.st_reg='A'";
                    strSQL+="       AND a1.co_empRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_EMP)+" AND a1.co_locRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_LOC);
                    strSQL+="       AND a1.co_tipDocRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_TIPDOC)+" AND a1.co_docRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_DOC);
                    strSQL+="       AND a2.co_itmMae="+intCodItmMae+"\n";
                    strSQL+=" ) AS a"+(i+1)+" ON b.co_emp=a"+(i+1)+".co_empOrdDis AND b.co_bod=a"+(i+1)+".co_bodOrdDis\n";
                    strSQL+=" LEFT OUTER JOIN( /* Inimpo o Ajuste */\n";
                    strSQL+="       SELECT CASE WHEN a.st_ingImp IS NULL THEN 'N' ";
                    strSQL+="              ELSE CASE WHEN a.st_ingImp = 'A' THEN 'S' ";
                    strSQL+="              ELSE CASE WHEN a.st_ingImp = 'B' THEN 'S' ";
                    strSQL+="              ELSE CASE WHEN a.st_ingImp = 'T' THEN 'S' ";
                    strSQL+="              ELSE a.st_ingImp END END END END AS st_ingImp ";
                    strSQL+="            , a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a1.co_reg, a1.nd_can as nd_CanIngImp ";
                    strSQL+="       FROM tbm_cabMovInv as a";
                    strSQL+="       INNER JOIN tbm_detMovInv as a1 ON a.co_Emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_Doc=a1.co_doc";
                    strSQL+="       INNER JOIN tbm_equInv as a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+="       WHERE a.st_reg='A'   AND a1.nd_can>0";
                    strSQL+="       AND a1.co_emp="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_EMP)+" AND a1.co_loc="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_LOC);
                    strSQL+="       AND a1.co_tipDoc="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_TIPDOC)+" AND a1.co_doc="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_DOC);
                    strSQL+="       AND a2.co_itmMae="+intCodItmMae+"\n";
                    strSQL+=" )AS c"+(i+1)+" ON a"+(i+1)+".co_empIngImp=c"+(i+1)+".co_emp AND a"+(i+1)+".co_locIngImp=c"+(i+1)+".co_loc AND a"+(i+1)+".co_tipDocIngImp=c"+(i+1)+".co_tipDoc AND a"+(i+1)+".co_docIngImp=c"+(i+1)+".co_doc AND a"+(i+1)+".co_regIngImp=c"+(i+1)+".co_reg\n";
                    strSQL+=" LEFT OUTER JOIN(  /* Transferencias */\n";
                    strSQL+="       SELECT a1.co_itmMae, a1.co_bodGrp, a1.tx_codAlt2, SUM(a1.nd_can) AS nd_canTrs";
                    strSQL+="       FROM(";
                    strSQL+=" 	       SELECT a1.co_itmMae, a2.co_bodGrp, a1.tx_codAlt2, a1.nd_can";
                    strSQL+=" 	       FROM(";
                    strSQL+=" 		  SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_codAlt, a4.co_itmMae, a2.nd_can";
                    strSQL+=" 		       , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                    strSQL+=" 		       , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel, a2.co_bod AS co_bodTrs, a2.co_reg";
                    strSQL+=" 		  FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                    strSQL+=" 			ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                    strSQL+=" 		  INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 		  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		  INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		  INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		  WHERE a2.nd_can>0 AND a1.st_Reg IN ('A') ";
                    strSQL+=" 		  AND a1.co_tipDoc NOT IN ( select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa()+" and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";                                          
                    strSQL+=" 		  AND a5.co_empRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_EMP)+" AND a5.co_locRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_LOC);
                    strSQL+=" 		  AND a5.co_tipDocRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_TIPDOC)+" AND a5.co_docRel="+objUti.getIntValueAt(arlDatGrpAdi, i, INT_ARL_DAT_COD_DOC);
                    strSQL+=" 		  AND a4.co_itmMae="+intCodItmMae;
                    strSQL+=" 	       ) AS a1"; 
                    strSQL+=" 	       INNER JOIN("; 
                    strSQL+=" 		  SELECT a2.co_emp, a2.co_bod, a2.co_empGrp, a2.co_bodGrp ";
                    strSQL+=" 		  FROM tbm_bod AS a1";
                    strSQL+="             INNER JOIN tbr_bodEmpBodGrp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod ";
                    strSQL+=" 	       ) AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bodTrs=a2.co_bod"; 
                    strSQL+="       ) AS a1 ";
                    strSQL+="       GROUP BY a1.co_itmMae, a1.co_bodGrp, a1.tx_codAlt2\n";
                    strSQL+=" ) AS t"+(i+1)+" ON a"+(i+1)+".co_itmMae=t"+(i+1)+".co_itmMae AND a"+(i+1)+".co_bodOrdDis=t"+(i+1)+".co_bodGrp\n";
                }
                strSQL+=" GROUP BY b.co_emp, b.co_bod, b.tx_nomBodAutDis\n";
                for(int i=0; i<(intNumColGrpAdi); i++) {
                    strSQL+="        , a"+(i+1)+".co_empOrdDis, a"+(i+1)+".co_locOrdDis, a"+(i+1)+".co_tipDocOrdDis, a"+(i+1)+".co_docOrdDis";
                    strSQL+="        , a"+(i+1)+".co_RegOrdDis, a"+(i+1)+".co_itmMae, a"+(i+1)+".co_itmGrp, a"+(i+1)+".nd_CanAutBod, t"+(i+1)+".nd_CanTrs, c"+(i+1)+".nd_CanIngImp";
                    strSQL+="        , a"+(i+1)+".co_empIngImp, a"+(i+1)+".co_locIngImp, a"+(i+1)+".co_tipDocIngImp, a"+(i+1)+".co_docIngImp, a"+(i+1)+".co_regIngImp\n";
                    strSQL+="        , c"+(i+1)+".st_ingImp";
                }
                strSQL+=" ORDER BY b.co_emp, b.co_bod\n";     
                System.out.println("cargarReg: "+strSQL);
                int intIndColGrp =0;
                int k=0;
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                while (rst.next()) 
                {
                    k++;
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_BOD, rst.getString("co_bod"));
                    vecReg.add(INT_TBL_DAT_NOM_BOD, rst.getString("tx_nomBodAutDis"));
    
                    for(int i=0; i<(intNumColGrpAdi); i++) //Cantidad de grupos
                    {
                        for(int j=0; j<(INT_NUM_CDI_GRP); j++) //Número de columnas dinámicas por grupo.        
                        {
                            blnChkBod=false;
                            intIndColGrp =(intColIni+j+(i*INT_NUM_CDI_GRP)); //Obtengo Indice de la Columna.
                            intIndChkBod=(intColIni+INT_IND_COL_CHK_BOD+(i*INT_NUM_CDI_GRP)); //índice de ChkBod
                            intIndChkBodAux=(intColIni+INT_IND_COL_CHK_BOD_AUX+(i*INT_NUM_CDI_GRP)); //índice de ChkBodAux
                            
                            strDat = (j==INT_IND_COL_CHK_BOD ? null
                                   : (j==INT_IND_COL_CHK_BOD_AUX ? null
                                   : (j==INT_IND_COL_ING_IMP_COD_EMP ? rst.getString("a"+(i+1)+"_co_empIngImp") 
                                   : (j==INT_IND_COL_ING_IMP_COD_LOC ? rst.getString("a"+(i+1)+"_co_locIngImp")
                                   : (j==INT_IND_COL_ING_IMP_COD_TIP_DOC ? rst.getString("a"+(i+1)+"_co_tipDocIngImp")
                                   : (j==INT_IND_COL_ING_IMP_COD_DOC ? rst.getString("a"+(i+1)+"_co_docIngImp")
                                   : (j==INT_IND_COL_ING_IMP_COD_REG ? rst.getString("a"+(i+1)+"_co_regIngImp")
                                   : (j==INT_IND_COL_ORD_DIS_COD_EMP ? rst.getString("a"+(i+1)+"_co_empOrdDis")
                                   : (j==INT_IND_COL_ORD_DIS_COD_LOC ? rst.getString("a"+(i+1)+"_co_locOrdDis")
                                   : (j==INT_IND_COL_ORD_DIS_COD_TIP_DOC ? rst.getString("a"+(i+1)+"_co_tipDocOrdDis")
                                   : (j==INT_IND_COL_ORD_DIS_COD_DOC ? rst.getString("a"+(i+1)+"_co_docOrdDis")
                                   : (j==INT_IND_COL_ORD_DIS_COD_REG ? rst.getString("a"+(i+1)+"_co_regOrdDis")
                                   : (j==INT_IND_COL_CAN_ING_IMP ? rst.getString("c"+(i+1)+"_nd_canIngImp") //nd_canIngImp
                                   : (j==INT_IND_COL_CAN_AUT_USR ? rst.getString("a"+(i+1)+"_nd_canAutBod")
                                   : (j==INT_IND_COL_CAN_TOT_AUT_AUX ? rst.getString("a"+(i+1)+"_nd_canAutBod")
                                   : (j==INT_IND_COL_CAN_TOT_TRS ? rst.getString("t"+(i+1)+"_nd_canTrs") 
                                   : (j==INT_IND_COL_CAN_PEN_TRS ? rst.getString("y"+(i+1)+"_nd_CanPenInm") //nd_canPenInm
                                   : ""  )))))))))))))))));                   
                            vecReg.add(intIndColGrp, strDat);
                            
                            //Valores de Chk
                            blnChkBod = (j==INT_IND_COL_CHK_BOD ? 
                                        (rst.getString("co_bod").equals(""+INT_COD_BOD_CEN_DIS)? true:( rst.getString("a"+(i+1)+"_co_empOrdDis")!=null && rst.getDouble("a"+(i+1)+"_nd_canAutBod")>0 ) ? true: false)
                                      : (j==INT_IND_COL_CHK_BOD_AUX ? 
                                        (rst.getString("co_bod").equals(""+INT_COD_BOD_CEN_DIS)? true:( rst.getString("a"+(i+1)+"_co_empOrdDis")!=null ) ? true: false)
                                      :  false  )); 
                            
                            if(j==INT_IND_COL_CHK_BOD){
                                vecReg.setElementAt(blnChkBod, intIndChkBod); 
                            }
                            else if(j==INT_IND_COL_CHK_BOD_AUX){
                                vecReg.setElementAt(blnChkBod, intIndChkBodAux); 
                            }
                        }
                    }
                    vecDat.add(vecReg);
                }
                //System.out.println("vecDat: "+vecDat);
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTblTot.calcularTotales();
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e) {       blnRes = false;      objUti.mostrarMsgErr_F1(this, e);    }
        catch (Exception e) {        blnRes = false;        objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    /**
     * Función que permite calcular el valor que aún está pendiente de autorizar
     * en bodega
     */
    private void calcularCanBodInm() 
    {
        int intCodBod = -1;
        int intFilCodBodCenDis = -1;
        int intIndColChkBod=-1;
        int intIndColCanAutUsr=-1;
        int intIndColCanIngImp=-1;

        for(int k=0; k<intNumColGrpAdi; k++) //Cantidad de grupos
        {
            intIndColChkBod=(intColIni+INT_IND_COL_CHK_BOD+(k*INT_NUM_CDI_GRP));
            intIndColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(k*INT_NUM_CDI_GRP));
            intIndColCanIngImp=(intColIni+INT_IND_COL_CAN_ING_IMP+(k*INT_NUM_CDI_GRP));
            
            BigDecimal bdeCanIngImp = new BigDecimal("0");
            BigDecimal bdeCanAut = new BigDecimal("0");//no incluye Inmaconsa, porque para esa bodega no se autoriza. El resto que no se autoriza va a Inmaconsa
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                intCodBod = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD).toString());
                if (objTblMod.isChecked(i, intIndColChkBod)) {
                    if (intCodBod == INT_COD_BOD_CEN_DIS) {
                        intFilCodBodCenDis = i;    
                        bdeCanIngImp = bdeCanIngImp.add(new BigDecimal(objTblMod.getValueAt(i, intIndColCanIngImp) == null ? "0" : objTblMod.getValueAt(i, intIndColCanIngImp).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanIngImp).toString()));
                    } else {
                        bdeCanAut = bdeCanAut.add(new BigDecimal(objTblMod.getValueAt(i, intIndColCanAutUsr) == null ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString()));
                    }
                }                
            }
                           
            //Se coloca la cantidad que aún esta pendiente de ese item en la bodega inmaconsa(por cálculo)
            //if (bdeCanIngImp.compareTo(bdeCanAut) >= 0) { //Se cambia validacion cuando Ing.Imp>Can.Con.
            if (bdgCanPerAut.compareTo(bdeCanAut) >= 0) {
                //objTblMod.setValueAt((bdeCanIngImp.subtract(bdeCanAut)), intFilCodBodCenDis, intIndColCanAutUsr); 
                objTblMod.setValueAt((bdgCanPerAut.subtract(bdeCanAut)), intFilCodBodCenDis, intIndColCanAutUsr);//Se cambia validacion cuando Ing.Imp>Can.Con.
            }
        }
        objTblTot.calcularTotales();
    }
    
    /**
     * Función que permite calcular la cantidad pendiente de transferir
     */
    private void calcularCanPenTrs() 
    {
        BigDecimal bdeCanAut = new BigDecimal("0");
        BigDecimal bdeCanTrs = new BigDecimal("0");
        int intIndColCanAutUsr=-1;
        int intIndColCanTotTrs=-1;
        int intIndColCanPenTrs=-1;
        
        for(int k=0; k<intNumColGrpAdi; k++) //Cantidad de grupos
        {
            intIndColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(k*INT_NUM_CDI_GRP));
            intIndColCanTotTrs=(intColIni+INT_IND_COL_CAN_TOT_TRS+(k*INT_NUM_CDI_GRP));
            intIndColCanPenTrs=(intColIni+INT_IND_COL_CAN_PEN_TRS+(k*INT_NUM_CDI_GRP));
        
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                bdeCanAut = (new BigDecimal(objTblMod.getValueAt(i, intIndColCanAutUsr) == null ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString()));
                bdeCanTrs = (new BigDecimal(objTblMod.getValueAt(i, intIndColCanTotTrs) == null ? "0" : objTblMod.getValueAt(i, intIndColCanTotTrs).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanTotTrs).toString()));

                objTblMod.setValueAt((bdeCanAut.subtract(bdeCanTrs)), i, intIndColCanPenTrs);
            }
        }
        objTblTot.calcularTotales();
    }
    
    /**
     * Función que permite calcular la cantidad pendiente de transferir
     */
    private void calcularCanPenTrs(int filaSeleccionada) 
    {
        BigDecimal bdeCanAut = new BigDecimal("0");
        BigDecimal bdeCanTrs = new BigDecimal("0");
        int intCodBod = -1;
        int intIndColCanAutUsr=-1;
        int intIndColCanTotTrs=-1;
        int intIndColCanPenTrs=-1;        

        for(int k=0; k<intNumColGrpAdi; k++) //Cantidad de grupos
        {
            intIndColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(k*INT_NUM_CDI_GRP));
            intIndColCanTotTrs=(intColIni+INT_IND_COL_CAN_TOT_TRS+(k*INT_NUM_CDI_GRP));
            intIndColCanPenTrs=(intColIni+INT_IND_COL_CAN_PEN_TRS+(k*INT_NUM_CDI_GRP));
        
            bdeCanAut = (new BigDecimal(objTblMod.getValueAt(filaSeleccionada, intIndColCanAutUsr) == null ? "0" : objTblMod.getValueAt(filaSeleccionada, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(filaSeleccionada, intIndColCanAutUsr).toString()));
            bdeCanTrs = (new BigDecimal(objTblMod.getValueAt(filaSeleccionada, intIndColCanTotTrs) == null ? "0" : objTblMod.getValueAt(filaSeleccionada, intIndColCanTotTrs).toString().equals("") ? "0" : objTblMod.getValueAt(filaSeleccionada, intIndColCanTotTrs).toString()));

            objTblMod.setValueAt((bdeCanAut.subtract(bdeCanTrs)), filaSeleccionada, intIndColCanPenTrs);

            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                intCodBod = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD).toString());
                bdeCanAut = (new BigDecimal(objTblMod.getValueAt(i, intIndColCanAutUsr) == null ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString()));
                bdeCanTrs = (new BigDecimal(objTblMod.getValueAt(i, intIndColCanTotTrs) == null ? "0" : objTblMod.getValueAt(i, intIndColCanTotTrs).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanTotTrs).toString()));

                if (intCodBod == INT_COD_BOD_CEN_DIS) {
                    objTblMod.setValueAt((bdeCanAut.subtract(bdeCanTrs)), i, intIndColCanPenTrs);
                }
            }
        }
        objTblTot.calcularTotales();
    }
    
    /**
     * Función que permite saber la cantidad autorizada. Incluye el valor de la
     * Bodega Centro de Distribución
     *
     * @return Total de la cantidad que se puede transferir
     */
    private BigDecimal getCanAutBod() 
    {    
        //objTblTot.calcularTotales();
        BigDecimal bdeCanAut = new BigDecimal("0");
        //int intIndChkBod=-1;
        int intIndColCanAutUsr=-1;
        for(int k=0; k<intNumColGrpAdi; k++) //Cantidad de grupos
        {
            //intIndChkBod=(intColIni+INT_IND_COL_CHK_BOD+(k*INT_NUM_CDI_GRP)); //índice de ChkBod
            intIndColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(k*INT_NUM_CDI_GRP));
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                //if (objTblMod.isChecked(i, intIndChkBod)) {
                    bdeCanAut = bdeCanAut.add(new BigDecimal(objTblMod.getValueAt(i, intIndColCanAutUsr) == null ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanAutUsr).toString()));
                //}
            }
        }
        System.out.println("getCanAutBod: "+bdeCanAut);
        return bdeCanAut;
    }

    /**
     * Función que permite saber la cantidad pendiente de transferir en la
     * Bodega del Centro de Distribución Incluye el valor de la Bodega Centro de
     * Distribución
     *
     * @return Total de la cantidad que se puede transferir
     */
    private BigDecimal getCanPenTrsInm() 
    {
        objTblTot.calcularTotales();
        int intCodBod = -1;
        BigDecimal bdeCanPenTrs = new BigDecimal("0");
        int intIndColCanPenTrs=-1;
        for(int k=0; k<intNumColGrpAdi; k++) //Cantidad de grupos
        {
            intIndColCanPenTrs=(intColIni+INT_IND_COL_CAN_PEN_TRS+(k*INT_NUM_CDI_GRP));
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                intCodBod = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD).toString());
                //if (objTblMod.isChecked(i, INT_IND_COL_CHK_BOD)) {
                    if (intCodBod == INT_COD_BOD_CEN_DIS) {
                        bdeCanPenTrs = bdeCanPenTrs.add( new BigDecimal(objTblMod.getValueAt(i, intIndColCanPenTrs) == null ? "0" : objTblMod.getValueAt(i, intIndColCanPenTrs).toString().equals("") ? "0" : objTblMod.getValueAt(i, intIndColCanPenTrs).toString())) ;
                    }
                //}
            }
        }
        System.out.println("getCanPenTrsInm: "+bdeCanPenTrs);
        return bdeCanPenTrs;
    }
    
    /**
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean isCamVal(){
        if(!validarEstIngImp()){
            if(strEstAutIng.equals("A")) {
                mostrarMsgInf("<HTML>El ingreso por Importación no ha sido arribado.<BR>Verifique y vuelva a intentarlo.</HTML>");
            }
            else if(strEstAutIng.equals("C")) {
                mostrarMsgInf("<HTML>El ingreso por importación ya ha sido cerrado.</HTML>");
            }
            return false;  
        }
        return true;
    }
    
    /**
     * Función que valida el estado del documento que se esta autorizando.
     * Ejemplo: El ingreso por importación está cerrado, pero el ajuste está abierto.
     * En este caso se podrá guardar la autorización del ajuste.
     * @return 
     */ 
    private boolean validarEstIngImp() 
    {
        boolean blnRes=false;
        java.sql.Connection conLoc=null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                for(int k=0; k<intNumColGrpAdi; k++) //Cantidad de grupos
                {
                    stmLoc=conLoc.createStatement();
                    strSQL ="";                
                    strSQL+=" SELECT a1.co_emp AS co_empIngImp, a1.co_loc AS co_locIngImp, a1.co_tipDoc AS co_tipDocIngImp, a1.co_doc AS co_docIngImp";
                    strSQL+="      , CASE WHEN a1.st_ingImp IS NULL THEN 'N' ";
                    strSQL+="        ELSE CASE WHEN a1.st_ingImp = 'A' THEN 'S' ";
                    strSQL+="        ELSE CASE WHEN a1.st_ingImp = 'B' THEN 'S' ";
                    strSQL+="        ELSE CASE WHEN a1.st_ingImp = 'T' THEN 'S' ";
                    strSQL+="        ELSE a1.st_ingImp END END END END AS st_ingImp ";
                    strSQL+=" FROM tbm_cabMovInv AS a1 "; 		
                    strSQL+=" WHERE a1.co_emp="+ objUti.getIntValueAt(arlDatGrpAdi, k, INT_ARL_DAT_COD_EMP);   
                    strSQL+=" AND a1.co_loc="+ objUti.getIntValueAt(arlDatGrpAdi, k, INT_ARL_DAT_COD_LOC);   
                    strSQL+=" AND a1.co_tipDoc="+ objUti.getIntValueAt(arlDatGrpAdi, k, INT_ARL_DAT_COD_TIPDOC);   
                    strSQL+=" AND a1.co_doc="+ objUti.getIntValueAt(arlDatGrpAdi, k, INT_ARL_DAT_COD_DOC);   	
                    System.out.println("validarEstIngImp: "+strSQL);
                    
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) 
                    {
                        strEstAutIng=rstLoc.getString("st_ingImp");
                    }
                    if(strEstAutIng.equals("S"))
                    {
                        blnRes=true;
                    }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e) { blnRes=false;   objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }    

    /**
     * Esta función genera la orden de distribución a las bodegas seleccionadas.     *
     * @return true: Si se pudo guardarAut cambios en la orden de distribución
     * <BR>false: En el caso contrario.
     */
    private boolean guardarAut()
    {
        System.out.println("guardarAut");
        boolean blnRes = true;
        String strLin="";
        int intCodBod = -1;
        int intIndChkBod=0;
        int intIndChkBodAux=0;
        try 
        {         
            //Inicializa el stringBuffer autorizaciones
            stbAut=new StringBuffer();
            for(int k=0; k<(intNumColGrpAdi); k++) //Cantidad de grupos
            {
                intIndChkBod=(intColIni+INT_IND_COL_CHK_BOD+(k*INT_NUM_CDI_GRP)); //índice de ChkBod
                intIndChkBodAux=(intColIni+INT_IND_COL_CHK_BOD_AUX+(k*INT_NUM_CDI_GRP)); //índice de ChkBodAux
                System.out.println("Grupooo: "+objUti.getStringValueAt(arlDatGrpAdi, k, INT_ARL_DAT_NUM_PED)); 
                for (int i = 0; i < objTblMod.getRowCountTrue(); i++) 
                {
                    strLin = objTblMod.getValueAt(i, INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    intCodBod = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD).toString());//Bodega de destino autorizada
                    System.out.println("Bodega: "+intCodBod);
                    
                    if (strLin.equals("M")) 
                    {
                        /**
                         * Valida el check intIndChkBodAux
                         * Puede ser que este marcado o desmarcado el chk.
                         * <BR>Si esta marcado significa que ya existe orden de distribución para la bodega seleccionada.
                         * <BR>Caso contrario: No existe orden de distribución activa para la bodega seleccionada.
                         */
                        if (objTblMod.isChecked(i, intIndChkBodAux))  //SI esta marcado, es UPDATE (Modifica la Orden de Distribución)
                        { 
                            if (actualizar_tbmCabOrdDis(i, k)) {
                                if (actualizar_tbmDetOrdDis(i, k))  {
                                    System.out.print("Update!");
                                }
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                        }
                        else //Si NO esta marcado, es INSERT (Crea la Orden de Distribución)
                        {
                            if (objTblMod.isChecked(i, intIndChkBod))
                            {
                                /* No va 'I' porque no se inserta nueva linea, solo se modifica en las bodegas que estan cargadas.
                                 * Recordemos que se cargan las bodegas no necesariamente autorizadas, sino todas las asociadas al programa*/
                                if (insertar_tbmCabOrdDis(intCodBod))  {
                                    if (insertar_tbmDetOrdDis(i, k)) {
                                        System.out.print("Insert!");
                                    } 
                                    else
                                        blnRes=false;
                                } 
                                else
                                    blnRes=false;
                            }
                        }
                    }
                }                    
            }

            System.out.println("STRING.BUFFER.stbAut: "+stbAut);

            if(!blnRes) {  //Valida que se guarde sin errores.
                setSQLAut(null);
                System.out.println("<ERROR AL GUARDAR AUTORIZACION>");
            }
            else {  //Si todo esta OK
                System.out.println("TODO OKKKK!!!");
            }
        } 
        catch (Exception e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar la cabecera de un registro.
     *
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR> false en el caso contrario
     */
    private boolean insertar_tbmCabOrdDis(int codigoBodega) 
    {
        boolean blnRes = true;
        try 
        {
            //Armar sentencia SQL
            stbAut.append(" INSERT INTO tbm_caborddis(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, tx_obs1, tx_obs2"
                         +"                         , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep)"
                         +" VALUES ("
                         +"   " + objParSis.getCodigoEmpresa()+"" //co_Emp
                         +" , " + objParSis.getCodigoLocal()+""   //co_loc
                         +" , " + INT_COD_TIP_DOC_ORD_DIS +""     //co_tipdoc
                         +" , (SELECT CASE WHEN MAX (a1.co_doc) IS NULL THEN 1 ELSE MAX(a1.co_doc)+1 END AS co_doc " //co_doc
                         +"    FROM tbm_cabOrdDis AS a1 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa()+""
                         +"    AND a1.co_loc=" + objParSis.getCodigoLocal()+" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_DIS+") "
                         +" , CURRENT_DATE" //fe_doc
                         +" , (SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_numDoc " //ne_numdoc
                         +"    FROM tbm_cabTipDoc AS a1 WHERE a1.co_emp="+ objParSis.getCodigoEmpresa()+""
                         +"    AND a1.co_loc="+ objParSis.getCodigoLocal()+" AND a1.co_tipDoc="+ INT_COD_TIP_DOC_ORD_DIS+") "
                         +" , " + codigoBodega+"" //co_bod
                         +" , Null " //tx_obs1
                         +" , Null " //tx_obs2
                         +" , 'A'  " //st_reg
                         +" , CURRENT_TIMESTAMP" //fe_ing
                         +" , CURRENT_TIMESTAMP" //fe_ultmod
                         +" , " + objParSis.getCodigoUsuario()+"" //co_usring
                         +" , " + objParSis.getCodigoUsuario()+"" //co_usrmod
                         +" , 'I'" //st_regrep
                         +" ); \n");

            //Actualiza el último documento de la Orden de distribución.
            stbAut.append(" UPDATE tbm_cabTipDoc SET ne_ultDoc="
                         +" (SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_numDoc "
                         +"  FROM tbm_cabTipDoc AS a1 WHERE a1.co_emp="+ objParSis.getCodigoEmpresa()+""
                         +"  AND a1.co_loc="+ objParSis.getCodigoLocal()+" AND a1.co_tipDoc="+ INT_COD_TIP_DOC_ORD_DIS+") "
                         +" WHERE co_emp=" + objParSis.getCodigoEmpresa() + ""
                         +" AND co_loc=" + objParSis.getCodigoLocal() + ""
                         +" AND co_tipDoc=" + INT_COD_TIP_DOC_ORD_DIS + ";  \n");
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Función que permite guardarAut información return true Si se pudo realizar
     * la operación
     * <BR> false en el caso contrario
     */
    private boolean insertar_tbmDetOrdDis(int filSel, int grpCol) 
    {
        boolean blnRes = true;
        BigDecimal bdeCanAut = new BigDecimal("0");
        int intCodColCanAutUsr=-1;
        int j = 0;
        try
        {
            for(int k=0; k<(intNumColGrpAdi); k++) //Cantidad de grupos
            {
                if(k==grpCol)
                {
                    //Armar sentencia SQL
                    j++;
                    intCodColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(k*INT_NUM_CDI_GRP));
                    bdeCanAut = new BigDecimal(objTblMod.getValueAt(filSel, intCodColCanAutUsr) == null ? "0" : (objTblMod.getValueAt(filSel, intCodColCanAutUsr).equals("") ? "0" : objTblMod.getValueAt(filSel, intCodColCanAutUsr).toString()));
                    stbAut.append(" INSERT INTO tbm_detorddis(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_can, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)"
                                 +" VALUES ("
                                 +"  " + objParSis.getCodigoEmpresa()+"" //co_emp
                                 +" ," + objParSis.getCodigoLocal()+""   //co_loc
                                 +" ," + INT_COD_TIP_DOC_ORD_DIS +""     //co_tipdoc
                                 +" ,(SELECT CASE WHEN MAX (a1.co_doc) IS NULL THEN 1 ELSE MAX(a1.co_doc) END AS co_doc " //co_doc
                                 +"    FROM tbm_cabOrdDis AS a1 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa()+""
                                 +"    AND a1.co_loc=" + objParSis.getCodigoLocal()+" AND a1.co_tipDoc=" + INT_COD_TIP_DOC_ORD_DIS+") "                    
                                 +" ," + j +"" //co_reg
                                 +" ," + intCodItmGrp +"" //co_itm
                                 +" ," + bdeCanAut +""    //nd_can
                                 +" ," + objUti.getIntValueAt(arlDatGrpAdi, grpCol, INT_ARL_DAT_COD_EMP)+""   //co_empRel
                                 +" ," + objUti.getIntValueAt(arlDatGrpAdi, grpCol, INT_ARL_DAT_COD_LOC)+""   //co_locRel
                                 +" ," + objUti.getIntValueAt(arlDatGrpAdi, grpCol, INT_ARL_DAT_COD_TIPDOC)+""//co_tipDocRel
                                 +" ," + objUti.getIntValueAt(arlDatGrpAdi, grpCol, INT_ARL_DAT_COD_DOC)+""   //co_docRel
                                 +" ," + objUti.getIntValueAt(arlDatGrpAdi, grpCol, INT_ARL_DAT_COD_REG)+""   //co_regRel
                                 +" );  \n");
                }
            }
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite actualizar la cabecera de un registro.
     *
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabOrdDis(int filSel, int grpCol) 
    {
        boolean blnRes = true;
        try 
        {
            for(int k=0; k<(intNumColGrpAdi); k++) //Cantidad de grupos
            {
                if(k==grpCol)
                {
                    //Armar la sentencia SQL.
                    stbAut.append(" UPDATE tbm_cabOrdDis SET fe_ultMod=" + objParSis.getFuncionFechaHoraBaseDatos() + ", co_usrMod=" + objParSis.getCodigoUsuario()+""
                                 +" WHERE co_emp=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_EMP+(k*INT_NUM_CDI_GRP))) +""
                                 +" AND co_loc=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_LOC+(k*INT_NUM_CDI_GRP))) + ""
                                 +" AND co_tipDoc=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_TIP_DOC+(k*INT_NUM_CDI_GRP))) + ""
                                 +" AND co_doc=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_DOC+(k*INT_NUM_CDI_GRP))) + "; \n");
                }
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return blnRes;
    }

    /**
     * Esta función permite actualizar el detalle de un registro.
     *
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmDetOrdDis(int filSel, int grpCol) 
    {
        boolean blnRes = true;
        BigDecimal bdeCanSelAct = new BigDecimal("0");
        int intCodColCanAutUsr=-1;
        try 
        {
            for(int k=0; k<(intNumColGrpAdi); k++) //Cantidad de grupos
            {
                if(k==grpCol)
                {
                    //Armar la sentencia SQL.
                    intCodColCanAutUsr=(intColIni+INT_IND_COL_CAN_AUT_USR+(k*INT_NUM_CDI_GRP));
                    bdeCanSelAct = new BigDecimal(objTblMod.getValueAt(filSel,intCodColCanAutUsr) == null ? "0" : (objTblMod.getValueAt(filSel, intCodColCanAutUsr).equals("") ? "0" : objTblMod.getValueAt(filSel, intCodColCanAutUsr).toString()));
                    stbAut.append(" UPDATE tbm_detOrdDis SET nd_can=" + bdeCanSelAct+""
                                 +" WHERE co_emp=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_EMP+(k*INT_NUM_CDI_GRP))) + ""
                                 +" AND co_loc=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_LOC+(k*INT_NUM_CDI_GRP))) + ""
                                 +" AND co_tipDoc=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_TIP_DOC+(k*INT_NUM_CDI_GRP))) + ""
                                 +" AND co_doc=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_DOC+(k*INT_NUM_CDI_GRP))) + ""
                                 +" AND co_reg=" + objTblMod.getValueAt(filSel, (intColIni+INT_IND_COL_ORD_DIS_COD_REG+(k*INT_NUM_CDI_GRP))) + ";  \n");
                }
            }
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }   
        return blnRes;
    }

    

    
    
}
