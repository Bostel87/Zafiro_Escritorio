/*
 * ZafCon52.java
 * Created on 12 de JULIO de 2008, 14:20 PM
 * "Confirmación de depósitos bancarios..."
 */
package Contabilidad.ZafCon52;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenCon.ZafVenConCxC01;
import Librerias.ZafRptSis.ZafRptSis;

/**
 *
 * @author  Dario Cardenas
 */ 
public class ZafCon52 extends javax.swing.JInternalFrame 
{
    //Constantes: JTable Dat
    private final int INT_TBL_LINEA       = 0;            //LINEA DE NUMEROS DE REGISTROS EN LA TABLA///
    private final int INT_TBL_SEL         = 1;            //LINEA PARA CASILLA DE SELECCION DE REGISTROS///
    private final int INT_TBL_COD_LOC     = 2;            //CODIGO DEL LOCAL///
    private final int INT_TBL_TIP_DOC     = 3;            //TIPO DE DOCUMENTO DEL DEPOSITO///
    private final int INT_TBL_COD_DOC     = 4;            //CODIGO DEL DOCUMENTO///
    private final int INT_TBL_COD_TIP_DOC = 5;            //CODIGO DEL TIPO DE DOCUMENTO///
    private final int INT_TBL_DES_COR_TIP_DOC = 6;            //DESCRIPCION CORTA DEL TIPO DE DOCUMENTO///
    private final int INT_TBL_DES_LAR_TIP_DOC = 7;            //DESCRIPCION LARGA DEL TIPO DE DOCUMENTO///    
    private final int INT_TBL_NUM_DOC     = 8;            //NUMERO DEL DOCUMENTO///
    private final int INT_TBL_FEC_DOC     = 9;            //FECHA DEL DOCUMENTO///
    private final int INT_TBL_VAL_DEP     =10;            //VALOR DEL DOCUMENTO///
    private final int INT_TBL_ABO_DOC     =11;            //ABONO SELECCIONADO DEL DOCUMENTO///
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private int intIndiceCon=0;    

    private final int INT_GRP_BCO = 8;
    
    //Variables
    private Statement stm;
    private ResultSet rst;
    private Connection con;        
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafDatePicker dtpFecDoc;
    private ZafColNumerada objColNum;
    private MiToolBar objTooBar;
    private ZafAsiDia objAsiDia; 
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    private ZafTblMod objTblMod;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.        
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;    
    private ZafVenCon vcoTipDoc, vcoBan;             
    private ZafVenCon vcoTipDocCtaCon; 
    private ZafVenCon vcoCli;  
    private ZafThreadGUI objThrGUI;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    private ZafVenConCxC01 objVenConCxC01;              //Eddye_ventana de documentos pendientes.
    
    private ArrayList arlRegTbhFacSel;
    private Vector vecDat, vecCab, vecReg, vecAux, vecAuxDat;
    
    private boolean blnCon;
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private boolean blnMarTodChkTblDat=true;            //Marcar todas las casillas de verificación del JTable de empresas.
    
    javax.swing.JInternalFrame jfrThis;                 //Hace referencia a this

    private int INT_TOT_REG_CON=0;
    private int INTNUMTOTREGCONFEC=0;
    private int CAN_REG_DET=0;
    private int intSig;
    private int banclick=0, intClickIns=0, clickbutcon=0;
    
    private double dblValTotDoc=0;
    
    private String STRESTIMP="";
    private String strNatDocIng="";
    private String strSQLCtaCon;
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strSQL, strAux;

    private String strVer=" v0.2.2 ";
    
    /** Crea una nueva instancia de la clase ZafCon52. */
    public ZafCon52(ZafParSis obj) 
    {
        try{
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();   
            
            initComponents();
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana
            this.setTitle(objParSis.getNombreMenu()+strVer);
            lblTit.setText(objParSis.getNombreMenu());
            
            objUti=new ZafUtil();

            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y"); 
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(575, 10, 110, 20); 

            ////Ejemplo para Agregar un Boton a la Barra de Botones///
            objTooBar=new MiToolBar(this);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butBusDep);
            objTooBar.agregarSeparador();

            panBar.add(objTooBar);//llama a la barra de botones

            objAsiDia=new ZafAsiDia(objParSis);
            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);

            ///////asiento mejorado////
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() 
            {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) 
                {
                     if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                     else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
           
            txtCodTipDoc.setVisible(false);
            lblDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtValDoc.setEnabled(false);
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            
            if(objParSis.getCodigoUsuario() == 1)  {
                objTooBar.setVisibleModificar(true);
            }
            else  {
                objTooBar.setVisibleModificar(false);
            }
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                        
            configurarVenConCli();
            configurarVenConTipDoc();
            configurarVenConTipDocCtaCon();
            configurarVenConBco();
            configurarVenConDocAbi();
            
            configurarTblDat();
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
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecDat.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_SEL,"");
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc.");
            vecCab.add(INT_TBL_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_VAL_DEP,"Val.Dep.");
            vecCab.add(INT_TBL_ABO_DOC,"Abo.Doc.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            
            //Configurar JTable: Establecer tipo de seleccion.
            tblDat.setRowSelectionAllowed(true);
            
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LINEA);     
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            tblDat.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(40);//0
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setPreferredWidth(25);//1            
            tblDat.getColumnModel().getColumn(INT_TBL_COD_LOC).setPreferredWidth(50);//2
            tblDat.getColumnModel().getColumn(INT_TBL_TIP_DOC).setPreferredWidth(50);//3
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(80);//4
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(30);//5
            tblDat.getColumnModel().getColumn(INT_TBL_DES_COR_TIP_DOC).setPreferredWidth(65);//6
            tblDat.getColumnModel().getColumn(INT_TBL_DES_LAR_TIP_DOC).setPreferredWidth(140);//7
            tblDat.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(90);//8
            tblDat.getColumnModel().getColumn(INT_TBL_FEC_DOC).setPreferredWidth(90);//9
            tblDat.getColumnModel().getColumn(INT_TBL_VAL_DEP).setPreferredWidth(90);//10
            tblDat.getColumnModel().getColumn(INT_TBL_ABO_DOC).setPreferredWidth(90);//11
                        
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setResizable(false);
            
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatHeaMouseClicked(evt);
                }
            });
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_TIP_DOC, tblDat);            
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_DOC, tblDat);            
            
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_SEL).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer columnas editables
            vecAux=new Vector();
            vecAuxDat=new Vector();
            vecAux.add("" + INT_TBL_SEL);///1
            objTblMod.setColumnasEditables(vecAux);
            vecAuxDat = vecAux;
            vecAux=null;
            
            //Configurar JTable: Establecer columnas renderizadas
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ///renderizador para alinear a la derecha los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VAL_DEP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ABO_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ///renderizador para centrar los datos de las columnas///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_COD_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            setEditable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //metodo de renderizador de la columna de seleccion//
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelRenChkMain=null;
            
            objTblCelEdiChkMain=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_SEL).setCellEditor(objTblCelEdiChkMain);            
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if(objTblCelEdiChkMain.isChecked())
                    {
                        banclick++;
                        double valDoc = Math.abs(objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_VAL_DEP)));
                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_VAL_DEP),tblDat.getSelectedRow(),INT_TBL_ABO_DOC);
                    }
                    else
                    {
                        String strEstDepBan="";
                        strEstDepBan = getEstConDep();
                        if(strEstDepBan.equals("S"))
                        {
                           mostrarMsgInf("<HTML>El Depòsito NO PUEDE SER DESACTIVADO porque esta CONFIRMADO.<BR>Favor Verifique el documento y vuelva a intentarlo.</HTML>"); 
                           objTblMod.setValueAt(new Boolean(true), tblDat.getSelectedRow(), INT_TBL_SEL);
                        }
                        else
                        {
                            objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_ABO_DOC);
                        }
                        
                        if (CAN_REG_DET>0)
                        {
                            banclick = CAN_REG_DET;
                            banclick--;
                            CAN_REG_DET = banclick;
                        }
                        else
                        {
                            if(banclick == 0)
                                banclick = INT_TOT_REG_CON;
                            
                            banclick--;
                        }
                    }
                    calcularAboTot();
                    
                    String strCodTipDoc = objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_COD_TIP_DOC).toString();
                    String strNumDoc = objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_DOC).toString();
                    String strRef="Confirmacion Deposito # ";
                    strRef = " " + strNumDoc;
                    objAsiDia.generarDiario(tblDat, INT_TBL_SEL, INT_TBL_COD_TIP_DOC, INT_TBL_ABO_DOC);
                }
            });
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter()
	    {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                {   
                    calcularAboTot();
     
                    if (objTblPopMnu.isClickEliminarFila())
                    {
                        objAsiDia.generarDiario(tblDat, INT_TBL_SEL, INT_TBL_COD_TIP_DOC, INT_TBL_ABO_DOC);
                    }
                }
            });
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica los cheques en el JTable de Depositos.
     */
    private void tblDatHeaMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_SEL)
            {
                if (blnMarTodChkTblDat)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_SEL);
                        objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_VAL_DEP),i,INT_TBL_ABO_DOC);
                        banclick++;
                    }
                    blnMarTodChkTblDat=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_SEL);
                        objTblMod.setValueAt(null, i, INT_TBL_ABO_DOC);
                        banclick--;
                    }
                    blnMarTodChkTblDat=true;
                }
                
                calcularAboTot();
                objAsiDia.generarDiario(tblDat, INT_TBL_SEL, INT_TBL_COD_TIP_DOC, INT_TBL_ABO_DOC);                
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
      
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
//                case INT_TBL_COD_LOC:
//                    strMsg="Codigo Local Documento";
//                    break;
//                case INT_TBL_COD_CLI:
//                    strMsg="Codigo Cliente";
//                    break;
//                case INT_TBL_NOM_CLI:
//                    strMsg="Nombre Cliente";
//                    break;
//                case INT_TBL_COD_TIP_DOC:
//                    strMsg="Tipo de documento a cancelar";
//                    break;
//                case INT_TBL_DES_LAR_TIP_DOC:
//                    strMsg="Tipo de Documento";
//                    break;
//                case INT_TBL_NUM_DOC:
//                    strMsg="Numero de documento";
//                    break;
//                case INT_TBL_FEC_DOC:
//                    strMsg="Fecha del documento";
//                    break;
//                case INT_TBL_DIA_CRE:
//                    strMsg="Dias de credito";
//                    break;
//                case INT_TBL_FEC_VEN:
//                    strMsg="Fecha de Vencimiento";
//                    break;
//                case INT_TBL_POR_RET:
//                    strMsg="Porcentaje de Retencion";
//                    break;
//                case INT_TBL_VAL_DEP:
//                    strMsg="Valor del documento";
//                    break;
//                case INT_TBL_VAL_PEN:
//                    strMsg="Valor Pendiente del documento";
//                    break;
//                case INT_TBL_ABO_DOC:
//                    strMsg="Valor a abono";
//                    break;
//                case INT_TBL_BUT_BAN:
//                    strMsg="Boton de Datos del Banco ";
//                    break;
//                case INT_TBL_SOP_NEC:
//                    strMsg="Soporte necesario del documento";
//                    break;
//                case INT_TBL_SOP_ENT:
//                    strMsg="Soporte Entregado";
//                    break;
//                case INT_TBL_CTA_TIP_DOC:
//                    strMsg="Tipo de documento";
//                    break;
//                case INT_TBL_DES_CTA_DOC:
//                    strMsg="Nombre del documento ";
//                    break;
//                case INT_TBL_COD_BAN_CTA:
//                    strMsg="Codigo del Banco";
//                    break;
//                case INT_TBL_DES_BAN_CTA:
//                    strMsg="Descripcion del Banco";
//                    break;
//                case INT_TBL_NOM_BAN_CTA:
//                    strMsg="Alias del Banco";
//                    break;
//                case INT_TBL_NUM_CTA_BAN:
//                    strMsg="Numero de cuenta";
//                    break;
//                case INT_TBL_NUM_CHQ_CTA:
//                    strMsg="Numero de cheque o Retencion";
//                    break;
//                case INT_TBL_VAL_CHQ_CTA:
//                    strMsg="Valor del Cheque PostFechado";
//                    break;
//                case INT_TBL_NUM_SEC:
//                    strMsg="# Secuencia de Retencion del Cliente";
//                    break;
//                case INT_TBL_NUM_AUT:
//                    strMsg="# AutSri Retencion Cliente";
//                    break;
//                case INT_TBL_FEC_CAD:
//                    strMsg="Fecha Caducidad de Retencion Cliente";
//                    break;
//                case INT_TBL_COD_RET:
//                    strMsg="Codigo Aplicado para Retencion";
//                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularAboTot();
                    objAsiDia.generarDiario(tblDat, INT_TBL_SEL, INT_TBL_COD_TIP_DOC, INT_TBL_ABO_DOC);
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }             
    
    private void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }    
    
    /**
     * Esta función configura la "Ventana de consulta" que sería utilizada para
     * mostrar los "Clientes".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Cli.");
            arlAli.add("Cód.Ide.");
            arlAli.add("Nom.Cli.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("350");
            
            //Armar la sentencia SQL.            
            strSQL ="";
            strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom ";
            strSQL+=" FROM tbm_cli as a1 ";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" and a1.st_cli='S'";
            strSQL+=" ORDER BY a1.tx_nom ";
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Clientes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoCli.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
            vcoCli.setCampoBusqueda(2);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            
            //Armar la sentencia SQL.
            if(intCodUsr==1)
            {
                strSQL="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDocCtaCon()
    {
        boolean blnRes=true;
        int intCodUsrCtaCon = objParSis.getCodigoUsuario();
        try
        {
            //Listado de campos.
            ArrayList arlCamCtaCon=new ArrayList();
            arlCamCtaCon.add("a1.co_tipdoc");
            arlCamCtaCon.add("a1.tx_desCor");
            arlCamCtaCon.add("a1.tx_desLar");
            arlCamCtaCon.add("a1.ne_ultDoc");
            ///arlCam.add("a1.tx_natDoc");
            
            //Alias de los campos.
            ArrayList arlAliCtaCon=new ArrayList();
            arlAliCtaCon.add("Cï¿½digo");
            arlAliCtaCon.add("Tip.Doc.");
            arlAliCtaCon.add("Tipo de documento");
            arlAliCtaCon.add("Ult.Doc.");
            ///arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncColCtaCon=new ArrayList();
            arlAncColCtaCon.add("50");
            arlAncColCtaCon.add("80");
            arlAncColCtaCon.add("334");
            arlAncColCtaCon.add("80");
            ///arlAncCol.add("80");
            
            if(intCodUsrCtaCon==1)
            {
                //Armar la sentencia SQL.
                strSQLCtaCon="";
                strSQLCtaCon+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc ";
                strSQLCtaCon+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQLCtaCon+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQLCtaCon+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQLCtaCon+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQLCtaCon+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQLCtaCon="";
                strSQLCtaCon+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc ";
                strSQLCtaCon+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQLCtaCon+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQLCtaCon+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQLCtaCon+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQLCtaCon+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQLCtaCon+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
            }
            
            //Ocultar columnas.
            int intColOcuCtaCon[]=new int[1];
            intColOcuCtaCon[0]=4;
            vcoTipDocCtaCon=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQLCtaCon, arlCamCtaCon, arlAliCtaCon, arlAncColCtaCon, intColOcuCtaCon);
            arlCamCtaCon=null;
            arlAliCtaCon=null;
            arlAncColCtaCon=null;
            intColOcuCtaCon=null;
            //Configurar columnas.
            vcoTipDocCtaCon.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDocCtaCon.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConBco()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a2.co_reg");
            arlCam.add("a2.tx_descor");
            arlCam.add("a2.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Reg.");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("100");
            arlAncCol.add("300");
            
            //Armar la sentencia SQL.            
            strSQL="";
            strSQL+=" SELECT a2.co_reg, a2.tx_descor, a2.tx_deslar ";
            strSQL+=" FROM tbm_grpvar AS a1, tbm_var AS a2";
            strSQL+=" WHERE a1.co_grp=a2.co_grp and a1.co_grp=" + INT_GRP_BCO + "";
            strSQL+=" and a2.st_reg='A'";
            strSQL+=" ORDER BY a2.co_reg ";
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoBan=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bancos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoBan.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
            vcoBan.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoBan.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
            vcoBan.setCampoBusqueda(2);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarDatDep()
    {
        boolean blnRes=true;
        int intCodEmp, intCodLoc, intNumTotReg;
        String strFecDoc="", strCodTipDoc="", strGetTipDoc="";
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                strFecDoc = objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd");
                dtpFecDoc.setText(objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd"));

                strGetTipDoc = txtCodTipDoc.getText();

                if (INT_TOT_REG_CON > 0)
                {
                    //Para cuando se este en el modo "Modificar" se muestra: documentos insertados + los depositos pendientes. 
                    //Armar la sentencia SQL.//
                    strSQL="";
                    strSQL+=" SELECT COUNT(*)";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, ";
                    strSQL+=" a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban ";
                    strSQL+=" FROM tbm_detpag as a1 ";
                    strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                    strSQL+=" INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) ";
                    strSQL+=" INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) ";
                    strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) ";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    
                    if(intCodEmp==1)
                        strSQL+=" AND a1.co_loc= " + intCodLoc;
                    
                    strSQL+=" AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I') ";
                    strSQL+=" AND a1.co_tipdoc NOT IN (" + txtCodTipDoc.getText() + ") ";
                    ////strSQL+=" AND a2.st_condepban NOT IN ('S') ";
                    strSQL+=" AND a2.st_condepban IN ('N') AND a3.st_utiCtaTra IN ('S') ";
                    strSQL+=" AND a2.fe_doc = '" + strFecDoc + "'";
                    strSQL+=" AND a4.st_ctaban IN ('S') AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 ";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, ";
                    strSQL+=" a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban ";
                    strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                    strSQL+=" ) AS A1";

                    intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumTotReg==-1)
                        return false;
                    
                    INTNUMTOTREGCONFEC = intNumTotReg;

                    if(intNumTotReg > 0)
                    {
                        //Armar la sentencia SQL.//
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, ";
                        strSQL+=" a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban ";
                        strSQL+=" FROM tbm_detpag as a1 ";
                        strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) ";
                        strSQL+=" INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) ";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        
                        if(intCodEmp==1)
                            strSQL+=" AND a1.co_loc= " + intCodLoc;
                        
                        strSQL+=" AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I') ";
                        strSQL+=" AND a1.co_tipdoc NOT IN (" + txtCodTipDoc.getText() + ") ";
                        ////strSQL+=" AND a2.st_condepban NOT IN ('S') ";
                        strSQL+=" AND a2.st_condepban IN ('N') AND a3.st_utiCtaTra IN ('S') ";
                        strSQL+=" AND a2.fe_doc = '" + strFecDoc + "'";
                        strSQL+=" AND a4.st_ctaban IN ('S') AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 ";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, ";
                        strSQL+=" a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban ";
                        strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                        rst=stm.executeQuery(strSQL);

                        //Limpiar vector de datos.
    //                    vecDat.clear();
                        for(int i=INT_TOT_REG_CON;rst.next();i++)  
                        {

                            tblDat.setValueAt("", i, INT_TBL_LINEA);////0
                            tblDat.setValueAt("", i, INT_TBL_SEL);////1
                            tblDat.setValueAt(rst.getString("co_loc"), i, INT_TBL_COD_LOC);///2
                            tblDat.setValueAt(rst.getString("co_tipdoc"), i, INT_TBL_TIP_DOC);///3
                            tblDat.setValueAt(rst.getString("co_doc"), i, INT_TBL_COD_DOC);///4
                            tblDat.setValueAt(rst.getString("co_tipdoccon"), i, INT_TBL_COD_TIP_DOC);///5
                            tblDat.setValueAt(rst.getString("tx_descor"), i, INT_TBL_DES_COR_TIP_DOC);///6
                            tblDat.setValueAt(rst.getString("tx_deslar"), i, INT_TBL_DES_LAR_TIP_DOC);///7
                            tblDat.setValueAt(rst.getString("ne_numdoc1"), i, INT_TBL_NUM_DOC);///8
                            tblDat.setValueAt(objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy"), i, INT_TBL_FEC_DOC);///9
                            tblDat.setValueAt(rst.getString("mondoc"), i, INT_TBL_VAL_DEP);///10
                            tblDat.setValueAt("", i, INT_TBL_ABO_DOC);////11
                            
                            strCodTipDoc = rst.getString("co_tipdoc");
                            objTblMod.insertRow();
                        }
                    }
                    else
                    {
                        cargarDetReg();
                    }
                }
                else
                {
                    if(intClickIns>0)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT COUNT(*)";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, ";
                        strSQL+=" a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" FROM tbm_detpag as a1 ";
                        strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) ";
                        strSQL+=" INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) ";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        
                        if(intCodEmp==1)
                            strSQL+=" AND a1.co_loc= " + intCodLoc;
                        
                        strSQL+=" AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I')";
                        strSQL+=" AND a1.co_tipdoc NOT IN (" + txtCodTipDoc.getText() + ") ";
                        strSQL+=" AND a2.st_condepban NOT IN ('S') ";
                        strSQL+=" AND a2.fe_doc = '" + strFecDoc + "'";
                        strSQL+=" AND a4.st_ctaban IN ('S') AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 ";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, ";
                        strSQL+=" a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                        strSQL+=" ) AS A1";

                        intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intNumTotReg==-1)
                            return false;

                        INTNUMTOTREGCONFEC = intNumTotReg;
                        

                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, ";
                        strSQL+=" a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" FROM tbm_detpag as a1 ";
                        strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) ";
                        strSQL+=" INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) ";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        
                        if(intCodEmp==1)
                            strSQL+=" AND a1.co_loc= " + intCodLoc;
                        
                        strSQL+=" AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I')";
                        strSQL+=" AND a1.co_tipdoc NOT IN (" + txtCodTipDoc.getText() + ") ";
                        strSQL+=" AND a2.st_condepban NOT IN ('S') ";
                        strSQL+=" AND a2.fe_doc = '" + strFecDoc + "'";
                        strSQL+=" AND a4.st_ctaban IN ('S') AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 ";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, ";
                        strSQL+=" a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                        rst=stm.executeQuery(strSQL);

                        //Limpiar vector de datos.
                        vecDat.clear();

                        for(int i=0;rst.next();i++)
                        ///while(rst.next())
                        {
                                strCodTipDoc = rst.getString("co_tipdoc");
                                if(!strCodTipDoc.equals(strGetTipDoc))
                                {
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_LINEA,"");//0
                                    vecReg.add(INT_TBL_SEL,"");//1
                                    vecReg.add(INT_TBL_COD_LOC,rst.getString("co_loc"));//2
                                    vecReg.add(INT_TBL_TIP_DOC,rst.getString("co_tipdoc"));//3
                                    vecReg.add(INT_TBL_COD_DOC,rst.getString("co_doc"));//4
                                    vecReg.add(INT_TBL_COD_TIP_DOC,rst.getString("co_tipdoccon"));//5
                                    vecReg.add(INT_TBL_DES_COR_TIP_DOC,rst.getString("tx_descor"));//6
                                    vecReg.add(INT_TBL_DES_LAR_TIP_DOC,rst.getString("tx_deslar"));//7
                                    vecReg.add(INT_TBL_NUM_DOC,rst.getString("ne_numdoc1"));//8
                                    vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy"));////9
                                    vecReg.add(INT_TBL_VAL_DEP,rst.getString("mondoc"));//10
                                    vecReg.add(INT_TBL_ABO_DOC,"");//11
                                }
                                vecDat.add(vecReg);
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
                        vecDat.clear();
                    }
                    else
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT COUNT(*)";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, ";
                        strSQL+=" a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" FROM tbm_detpag as a1 ";
                        strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) ";
                        strSQL+=" INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) ";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        
                        if(intCodEmp==1)
                            strSQL+=" AND a1.co_loc= " + intCodLoc;
                        
                        strSQL+=" AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I')";
                        strSQL+=" AND a1.co_tipdoc NOT IN (" + txtCodTipDoc.getText() + ") ";
                        strSQL+=" AND a2.st_condepban NOT IN ('S') ";
                        strSQL+=" AND a2.fe_doc = '" + strFecDoc + "'";
                        strSQL+=" AND a4.st_ctaban IN ('S') AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 ";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, ";
                        strSQL+=" a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                        strSQL+=" ) AS A1";

                        intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intNumTotReg==-1)
                            return false;

                        INTNUMTOTREGCONFEC = intNumTotReg;

                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, ";
                        strSQL+=" a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" FROM tbm_detpag as a1 ";
                        strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) ";
                        strSQL+=" INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) ";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        
                        if(intCodEmp==1)
                            strSQL+=" AND a1.co_loc= " + intCodLoc;
                        
                        strSQL+=" AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I')";
                        strSQL+=" AND a1.co_tipdoc NOT IN (" + txtCodTipDoc.getText() + ") ";
                        strSQL+=" AND a2.st_condepban NOT IN ('S') ";
                        strSQL+=" AND a2.fe_doc = '" + strFecDoc + "'";
                        strSQL+=" AND a4.st_ctaban IN ('S') AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 ";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, ";
                        strSQL+=" a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod ";
                        strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                        rst=stm.executeQuery(strSQL);

                        //Limpiar vector de datos.
                        vecDat.clear();

                        for(int i=0;rst.next();i++)  
                        {
                            strCodTipDoc = rst.getString("co_tipdoc");

                            if(!strCodTipDoc.equals(strGetTipDoc))
                            {
                                vecReg=new Vector();
                                vecReg.add(INT_TBL_LINEA,"");//0
                                vecReg.add(INT_TBL_SEL,"");//1
                                vecReg.add(INT_TBL_COD_LOC,rst.getString("co_loc"));//2
                                vecReg.add(INT_TBL_TIP_DOC,rst.getString("co_tipdoc"));//3
                                vecReg.add(INT_TBL_COD_DOC,rst.getString("co_doc"));//4
                                vecReg.add(INT_TBL_COD_TIP_DOC,rst.getString("co_tipdoccon"));//5
                                vecReg.add(INT_TBL_DES_COR_TIP_DOC,rst.getString("tx_descor"));//6
                                vecReg.add(INT_TBL_DES_LAR_TIP_DOC,rst.getString("tx_deslar"));//7
                                vecReg.add(INT_TBL_NUM_DOC,rst.getString("ne_numdoc1"));//8
                                vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy"));////9
                                vecReg.add(INT_TBL_VAL_DEP,rst.getString("mondoc"));//10
                                vecReg.add(INT_TBL_ABO_DOC,"");//11
                            }
                            vecDat.add(vecReg);
                        }

                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;

                        ///FECHA ESCOGIDA POR EL USUARIO///
                        dtpFecDoc.setText(objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy/MM/dd"));

                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        vecDat.clear();
                    }
                } 
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentará ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));///txtCodTipDoc   ///txtCodTipDoc
                        lblDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);

                    }
                    break;
                case 1: //Bï¿½squeda directa por "Descripciï¿½n corta".
                    if (vcoTipDoc.buscar("a1.tx_descor", lblDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        lblDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            lblDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            lblDesCorTipDoc.setText(strTipDoc);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoTipDoc.buscar("a1.tx_deslar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        lblDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            lblDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                default:
                    lblDesCorTipDoc.requestFocus();
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

    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc AS TIPDOC, a1.tx_desCor AS DESCOR, a1.tx_desLar AS DESLAR, a1.ne_ultDoc AS ULTDOC, a1.tx_natDoc, a1.ne_mod";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" (";
                strSQL+=" SELECT co_tipDoc ";
                strSQL+=" FROM tbr_tipDocPrg ";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND st_reg='S'";
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("TIPDOC"));   ///txtCodTipDoc
                    lblDesCorTipDoc.setText(rst.getString("DESCOR"));
                    txtDesLarTipDoc.setText(rst.getString("DESLAR"));
                    txtNumDoc.setText("" + (rst.getInt("ULTDOC")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strNatDocIng = rst.getString("tx_natDoc");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
     * Esta función configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Documentos abiertos".
     */
    private boolean configurarVenConDocAbi()
    {
        boolean blnRes=true;
        try
        {
            objVenConCxC01=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de documentos abiertos");
            
            //Cobros masivos
            if(objParSis.getCodigoMenu()== 256)
                objVenConCxC01.setTipoConsulta(2);
            else
            {
                if(objParSis.getCodigoMenu()!= 488) //Retenciones Recibidas
                    objVenConCxC01.setTipoConsulta(3);
                else  ///Pagos Masivos
                    objVenConCxC01.setTipoConsulta(4);
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

        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        lblDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        butBusDep = new javax.swing.JButton();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setPreferredSize(new java.awt.Dimension(116, 210));
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
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 90));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 10, 125, 20);

        txtCodTipDoc.setMaximumSize(null);
        txtCodTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(120, 10, 10, 20);

        lblDesCorTipDoc.setMaximumSize(null);
        lblDesCorTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        lblDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblDesCorTipDocFocusLost(evt);
            }
        });
        lblDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblDesCorTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(lblDesCorTipDoc);
        lblDesCorTipDoc.setBounds(130, 10, 70, 20);

        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 10, 220, 20);

        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(420, 10, 20, 20);

        lblCodDoc.setText("Código de documento:");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(4, 30, 135, 20);

        txtCodDoc.setBackground(objParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(130, 30, 70, 20);

        lblFecDoc.setText("Fecha documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(440, 10, 140, 20);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(440, 30, 140, 20);

        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(575, 30, 110, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Valor del documento");
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(440, 50, 140, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(575, 50, 110, 20);

        butBusDep.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butBusDep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Multimedia/Imagenes/buscar.gif"))); // NOI18N
        butBusDep.setToolTipText("Mostrar Documentos bajo la Fecha Seleccionada");
        butBusDep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        butBusDep.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusDepActionPerformed(evt);
            }
        });
        panGenCab.add(butBusDep);
        butBusDep.setBounds(240, 30, 30, 30);

        panDat.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setPreferredSize(new java.awt.Dimension(500, 300));
        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panDat.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(100, 62));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panDat.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panDat);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost

    }//GEN-LAST:event_txtNumDocFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

    }//GEN-LAST:event_formInternalFrameOpened

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                lblDesCorTipDoc.setText("");                
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void lblDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!lblDesCorTipDoc.getText().equalsIgnoreCase(strTipDoc))
        {
            if (lblDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            lblDesCorTipDoc.setText(strTipDoc);
    }//GEN-LAST:event_lblDesCorTipDocFocusLost

    private void lblDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblDesCorTipDocFocusGained
        strTipDoc=lblDesCorTipDoc.getText();
        lblDesCorTipDoc.selectAll();        
    }//GEN-LAST:event_lblDesCorTipDocFocusGained

    private void lblDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDesCorTipDocActionPerformed
        lblDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_lblDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        configurarVenConTipDoc();
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }                        
    }//GEN-LAST:event_exitForm

    private void butBusDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusDepActionPerformed
        clearCampos();
        mostrarDatDep();
        if(INTNUMTOTREGCONFEC == 0)
            mostrarMsgInf("<HTML>No existen Registros con la <FONT COLOR=\"blue\">FECHA SELECCIONADA</FONT>.<BR>Favor consulte una Fecha y vuelva a intentarlo.</HTML>");
}//GEN-LAST:event_butBusDepActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butBusDep;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JTextField lblDesCorTipDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    public javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    private boolean isCamValMod()
    {
        
        if (!txtNumDoc.getText().equals(""))
         { 
                String strTit, strMsg;
                int intNumAltDoc=0, intUltNumDocAlt=0;
                strSQL="";
                strSQL+=" SELECT a1.ne_numdoc1";
                strSQL+=" FROM tbm_cabpag AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.ne_numdoc1='" + txtNumDoc.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                if (objTooBar.getEstado()=='m')
                    strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                
                if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    tabFrm.setSelectedIndex(0);
                    intNumAltDoc = Integer.parseInt(txtNumDoc.getText());
                    intUltNumDocAlt = intNumAltDoc;
                    try
                    { 
                        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="<HTML>El N�mero de documento <FONT COLOR=\"blue\">" + txtNumDoc.getText() + " </FONT> ya existe,  el siguiente n�mero de documento es: <FONT COLOR=\"blue\">" +  ((intUltNumDocAlt)+1) + " </FONT> <BR> �Desea que el sistema le asigne dicho n�mero al documento..? </HTML>";

                        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            txtNumDoc.setText("" +((intUltNumDocAlt)+1));
                        }
                        else
                        {
                            txtNumDoc.setText("");
                            txtNumDoc.requestFocus();
                        }
                    }
                    catch (Exception e)
                    {
                        dispose();
                    }
                    return false;
                }
        }

        if(banclick == 0)
        {
            if(INT_TOT_REG_CON==0)
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El documento no posee Regitros Seleccionado <BR>Favor seleccionar un Registro y vuelva intentarlo</HTML>");
                return false;
            }
        }
        
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba un Tipo de documento y vuelva a intentarlo.</HTML>");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if (txtDesLarTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
            txtDesLarTipDoc.requestFocus();
            return false;
        }

        if(dtpFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del Documento</FONT> es obligatorio.<BR>Escriba la fecha del documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del Documento</FONT> es obligatorio.<BR>Escriba la fecha del documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nï¿½mero Alterno 1</FONT> es obligatorio.<BR>Escriba un nï¿½mero alterno 1 y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }                        
        
        //Validar que el "Diario está cuadrado".
       if(!objAsiDia.isDiarioCuadrado())
       {
            mostrarMsgInf("<HTML>El asiento de diario no esta cuadrado.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
       }

       //Validar que el "Monto del diario" sea igual al monto del documento.
       if (!objAsiDia.isDocumentoCuadrado(dblValTotDoc))
       {
           mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
           txtValDoc.selectAll();
           txtValDoc.requestFocus();
           return false;
       }    
        
        String strEstDepBan="";
        strEstDepBan = getEstConDep();
        if(strEstDepBan.equals("S"))
        {
           mostrarMsgInf("<HTML>El Depòsito NO PUEDE SER MODIFICADO porque esta CONFIRMADO.<BR>Favor Verifique el documento y vuelva a intentarlo.</HTML>"); 
           return false;
        }
        
        return true;
    }      
      
    private boolean isCamVal()
    {
        
         if (!txtNumDoc.getText().equals(""))
         { 
                String strTit, strMsg;
                int intNumAltDoc=0, intUltNumDocAlt=0;
                strSQL="";
                strSQL+="SELECT a1.ne_numdoc1";
                strSQL+=" FROM tbm_cabpag AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.ne_numdoc1='" + txtNumDoc.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                if (objTooBar.getEstado()=='m')
                    strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                
                if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    tabFrm.setSelectedIndex(0);
                    intNumAltDoc = Integer.parseInt(txtNumDoc.getText());
                    
                    intUltNumDocAlt = intNumAltDoc;
     
                    try
                    { 
                        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="<HTML>El N�mero de documento <FONT COLOR=\"blue\">" + txtNumDoc.getText() + " </FONT> ya existe,  el siguiente n�mero de documento es: <FONT COLOR=\"blue\">" +  ((intUltNumDocAlt)+1) + " </FONT> <BR> �Desea que el sistema le asigne dicho n�mero al documento..? </HTML>";

                        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            txtNumDoc.setText("" +((intUltNumDocAlt)+1));
                        }
                        else
                        {
                            txtNumDoc.setText("");
                            txtNumDoc.requestFocus();
                        }
                    }
                    catch (Exception e)
                    {
                        dispose();
                    }
                    return false;
                }
        }

        if(banclick == 0)
        {
            if(INT_TOT_REG_CON==0)
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El documento no posee Regitros Seleccionado <BR>Favor seleccionar un Registro y vuelva intentarlo</HTML>");
                return false;
            }
        }
        else
        {
            if(INT_TOT_REG_CON!=0)
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El documento posee Regitros anteriores Seleccionados pero no ha seleccionado nuevo registros <BR>Favor seleccionar un nuevo Registro y vuelva intentarlo</HTML>");
                return false;
            }
        }
        
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba un Tipo de documento y vuelva a intentarlo.</HTML>");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if (lblDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
            lblDesCorTipDoc.requestFocus();
            return false;
        }
        
        if (txtDesLarTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
            txtDesLarTipDoc.requestFocus();
            return false;
        }

        if(dtpFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del Documento</FONT> es obligatorio.<BR>Escriba la fecha del documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del Documento</FONT> es obligatorio.<BR>Escriba la fecha del documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nï¿½mero Alterno 1</FONT> es obligatorio.<BR>Escriba un nï¿½mero alterno 1 y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        
        //Validar que el "Diario está cuadrado".
       if(!objAsiDia.isDiarioCuadrado())
       {
            javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="EL diario no esta cuadrado, no se puede grabar";
                obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return false;
       }

       //Validar que el "Monto del diario" sea igual al monto del documento.
        if (!objAsiDia.isDocumentoCuadrado(dblValTotDoc))
        {
           mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
           txtValDoc.selectAll();
           txtValDoc.requestFocus();
           return false;
        }
        
       return true;
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.beforeInsertar();
                        if (blnRes)
                            blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.beforeModificar();
                        if (blnRes)
                            blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
      
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {               
                if (insertarCab())
                {
                    if (insertarDet())
                    {
                        if (insertarConDepBanPag())
                        {
                            if(actualizarTbmDetRecDoc(0))
                            {
                                if(actualizarTbmCabPag(0))
                                {
                                    if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))
                                    {
                                        if (objUti.set_ne_ultDoc_tbm_cabTipDoc(this, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtNumDoc.getText())))
                                        {
                                            con.commit();
                                            blnRes=true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
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
     * Esta función permite insertar la cabecera de un documento.
     * @return true: Si se pudo insertar el documento.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        boolean blnRes=true;
        int intCodUsr, intUltReg;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                
                objTblMod.removeEmptyRows();
                
                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                if(strNatDocIng.equals("I"))
                {
                    //Armar la sentencia SQL.
                    strSQL="";                                                
                    strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc1, ne_numDoc2";
                    strSQL+=", nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, co_mnu)";
                    strSQL+=" VALUES (";
                    strSQL+=objParSis.getCodigoEmpresa();
                    strSQL+=", " + objParSis.getCodigoLocal();
                    strSQL+=", " + txtCodTipDoc.getText();
                    strSQL+=", " + intUltReg;
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                
                    strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);
                    strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);
                    ///strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                    strSQL+=", " + dblValTotDoc;
                    strSQL+=", " + objUti.codificar(txaObs1.getText());
                    strSQL+=", " + objUti.codificar(txaObs2.getText());
                    strSQL+=", 'A'";
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'";
                    strSQL+=", '" + strAux + "'";
                    strSQL+=", " + intCodUsr;
                    strSQL+=", " + intCodUsr;
                    strSQL+=", " + objParSis.getCodigoMenu();
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";                                                
                    strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc1, ne_numDoc2";
                    strSQL+=", nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, co_mnu)";
                    strSQL+=" VALUES (";
                    strSQL+=objParSis.getCodigoEmpresa();
                    strSQL+=", " + objParSis.getCodigoLocal();
                    strSQL+=", " + txtCodTipDoc.getText();
                    strSQL+=", " + intUltReg;
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                
                    strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);
                    strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);
                    ///strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                    strSQL+=", " + (intSig * dblValTotDoc);
                    strSQL+=", " + objUti.codificar(txaObs1.getText());
                    strSQL+=", " + objUti.codificar(txaObs2.getText());
                    strSQL+=", 'A'";
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'";
                    strSQL+=", '" + strAux + "'";
                    strSQL+=", " + intCodUsr;
                    strSQL+=", " + intCodUsr;
                    strSQL+=", " + objParSis.getCodigoMenu();
                    strSQL+=")";
                    stm.executeUpdate(strSQL);
                }
                
                stm.close();
                stm=null;
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()
    {
        boolean blnRes=true;
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc="", strCodDoc="";
        try
        {
            if (con!=null)
            {                
                stm=con.createStatement();
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                
                j=1;

                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_SEL))
                    {   
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag, ";
                        strSQL+=" nd_abo, co_tipDocCon )";
                        strSQL+=" VALUES (";
                        strSQL+=" " + intCodEmp;
                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_LOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_TIP_DOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_DOC);
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_ABO_DOC), 3);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC);
                        strSQL+=" )";
                        stm.executeUpdate(strSQL);
                        j++;                        
                    }/////FIN DEL IF DE SELECCION DE REGISTRO////
                }//////FIN DEL FOR/////////
                
                stm.close();
                stm=null;
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarConDepBanPag()
    {
        boolean blnRes=true;
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc="", strCodDoc="";
        try
        {
            if (con!=null)
            {   
                stm=con.createStatement();
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                
                j=1;
                
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_SEL))
                    {
                        
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="INSERT INTO tbm_condepbanpag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag ) ";
                        strSQL+=" VALUES (";
                        strSQL+=" " + intCodEmp;
                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_LOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_TIP_DOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_DOC);
                        strSQL+=" )";
                        stm.executeUpdate(strSQL);
                        j++;
                    }/////FIN DEL IF DE SELECCION DE REGISTRO////
                }//////FIN DEL FOR/////////
                stm.close();
                stm=null;
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
    
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarCab())   ///Actualiza Registros tabla tbm_CabPag
                {
                    if (eliminarDet())    ///Elimina Registros en tabla tbm_DetPag
                    {
                        if (insertarDet())    ///Inserta Registros en tabla tbm_DetPag  if(actualizarTbmDetRecDoc(0))
                        {
                            if (eliminarConDepBanPag())    ///Elimina Registros en tabla tbm_DetPag
                            {
                                if (insertarConDepBanPag())
                                {
                                    if(actualizarTbmDetRecDoc(0))
                                    {
                                        if(actualizarTbmCabPag(0))
                                        {
                                            if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "A"))
                                            {
                                                con.commit();
                                                blnRes=true;
                                            }
                                            else
                                            {
                                                con.rollback();
                                            }
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean actualizarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                int intCodEmp=objParSis.getCodigoEmpresa();
                int intCodLoc=objParSis.getCodigoLocal();
                if (datFecAux==null)
                    return false;
                //para calcular el monto del documento en base al detalle
                objTblMod.removeEmptyRows();                               
                
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumDoc.getText(),2);
                ///strSQL+=", nd_monDoc=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", nd_monDoc=" + dblValTotDoc;
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + intCodEmp;///txtCodTipDoc
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                stm.executeUpdate(strSQL);

                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta funcion permite actualizar la tabla "tbm_detRecDoc".
     * @param intTipOpe El tipo de operacion.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operacion</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */ 
    private boolean actualizarTbmDetRecDoc(int intTipOpe)
    {
        boolean blnRes=true;
        int intCanRegDetRec=0;
        Statement stmLoc01, stmLoc02;
        ResultSet rstLoc01, rstLoc02;
        String strCodLoc="", strTipDoc="", strCodDoc="", strNumChq="", strCodBanChq="", strFecVenChq="";
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                if (con!=null)
                {
                    stm=con.createStatement();
                    stmLoc01=con.createStatement();
                    stmLoc02=con.createStatement();

                    switch (intTipOpe)
                    {
                       case 0:
                            for (int i=0;i<objTblMod.getRowCount();i++)
                            {
                                if (objTblMod.isChecked(i, INT_TBL_SEL))
                                {
                                    strCodLoc=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_LOC));
                                    strTipDoc=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_TIP_DOC));
                                    strCodDoc=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_DOC));

                                    strSQL="";
                                    strSQL+="SELECT count(*)";
                                    strSQL+="FROM(";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_banchq, a1.co_tipdoccon,  a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, sum(a1.nd_abo) as valchq  ";
                                    strSQL+=" FROM tbm_detpag AS a1 ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp and a1.co_locpag=a2.co_loc and a1.co_tipdocpag=a2.co_tipdoc and a1.co_docpag=a2.co_doc and a1.co_regpag=a2.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv AS a3 ON (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and a2.co_doc=a3.co_doc) ";
                                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                    strSQL+=" AND a1.co_loc=" + strCodLoc;
                                    strSQL+=" AND a1.co_tipdoc = " + strTipDoc;
                                    strSQL+=" AND a1.co_doc = " + strCodDoc;
                                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_tipdoccon, a1.co_banchq, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq ";
                                    strSQL+=" ORDER BY a3.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                                    strSQL+=") AS A1";

                                    intCanRegDetRec=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);

                                    if (intCanRegDetRec==-1)
                                        return false;

                                    strSQL="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_banchq, a1.co_tipdoccon,  a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, sum(a1.nd_abo) as valchq  ";
                                    strSQL+=" FROM tbm_detpag AS a1 ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp and a1.co_locpag=a2.co_loc and a1.co_tipdocpag=a2.co_tipdoc and a1.co_docpag=a2.co_doc and a1.co_regpag=a2.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv AS a3 ON (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and a2.co_doc=a3.co_doc) ";
                                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                    strSQL+=" AND a1.co_loc=" + strCodLoc;
                                    strSQL+=" AND a1.co_tipdoc = " + strTipDoc;
                                    strSQL+=" AND a1.co_doc = " + strCodDoc;
                                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_tipdoccon, a1.co_banchq, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq ";
                                    strSQL+=" ORDER BY a3.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                                    rstLoc01=stmLoc01.executeQuery(strSQL);

                                    for(int k=0; rstLoc01.next(); k++)
                                    {
                                        /////strCodLoc = rstLoc01.getString("co_loc");
                                        strCodBanChq = rstLoc01.getString("co_banchq");
                                        strNumChq = rstLoc01.getString("tx_numchq");
                                        strFecVenChq = rstLoc01.getString("fe_venchq");

                                        if(strCodBanChq!=null && strNumChq!=null && strFecVenChq!=null)
                                        {
                                            strSQL="";
                                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_cli, a2.tx_nomcli, a1.co_banchq, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq ";
                                            strSQL+=" FROM tbm_pagmovinv AS a1 ";
                                            strSQL+=" INNER JOIN tbm_cabmovinv AS a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                            strSQL+=" AND a1.co_banchq = " + strCodBanChq;
                                            strSQL+=" AND a1.tx_numchq = '" + strNumChq + "'";
                                            strSQL+=" AND a1.fe_venchq = '" + strFecVenChq + "'";
                                            rstLoc02=stmLoc02.executeQuery(strSQL);
                                            if(rstLoc02.next())
                                            {
                                                //Armar la sentencia SQL.//
                                                strSQL="";
                                                strSQL+="UPDATE tbm_detRecDoc";
                                                strSQL+=" SET nd_valCon = nd_valApl ";
                                                strSQL+=" WHERE co_emp=" + rstLoc02.getString("co_emp");
                                                strSQL+=" AND co_loc=" + rstLoc02.getString("co_loc");
                                                strSQL+=" AND co_banchq=" + rstLoc02.getString("co_banchq");
                                                strSQL+=" AND tx_numchq like ('" + rstLoc02.getString("tx_numchq") + "')";
                                                strSQL+=" AND st_reg IN ('A'); ";
                                                stm.executeUpdate(strSQL);
                                            }
                                            rstLoc02.close();
                                            rstLoc02=null;
                                        }
                                    }
                                    rstLoc01.close();
                                    rstLoc01 = null;
                                }
                            }
                       break;

                       case 1:
                            for (int i=0;i<objTblMod.getRowCount();i++) 
                            {
                                if (objTblMod.isChecked(i, INT_TBL_SEL))
                                {
                                    strCodLoc=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_LOC));
                                    strTipDoc=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_TIP_DOC));
                                    strCodDoc=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_COD_DOC));

                                    strSQL="";
                                    strSQL+="SELECT count(*)";
                                    strSQL+="FROM(";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_banchq, a1.co_tipdoccon,  a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, sum(a1.nd_abo) as valchq  ";
                                    strSQL+=" FROM tbm_detpag AS a1 ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp and a1.co_locpag=a2.co_loc and a1.co_tipdocpag=a2.co_tipdoc and a1.co_docpag=a2.co_doc and a1.co_regpag=a2.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv AS a3 ON (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and a2.co_doc=a3.co_doc) ";
                                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                    strSQL+=" AND a1.co_loc=" + strCodLoc;
                                    strSQL+=" AND a1.co_tipdoc = " + strTipDoc;
                                    strSQL+=" AND a1.co_doc = " + strCodDoc;
                                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_tipdoccon, a1.co_banchq, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq ";
                                    strSQL+=" ORDER BY a3.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                                    strSQL+=") AS A1";

                                    intCanRegDetRec=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);

                                    if (intCanRegDetRec==-1)
                                        return false;

                                    strSQL="";
                                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_banchq, a1.co_tipdoccon,  a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, sum(a1.nd_abo) as valchq  ";
                                    strSQL+=" FROM tbm_detpag AS a1 ";
                                    strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp and a1.co_locpag=a2.co_loc and a1.co_tipdocpag=a2.co_tipdoc and a1.co_docpag=a2.co_doc and a1.co_regpag=a2.co_reg) ";
                                    strSQL+=" INNER JOIN tbm_cabmovinv AS a3 ON (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_loc and a2.co_tipdoc=a3.co_tipdoc and a2.co_doc=a3.co_doc) ";
                                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                    strSQL+=" AND a1.co_loc=" + strCodLoc;
                                    strSQL+=" AND a1.co_tipdoc = " + strTipDoc;
                                    strSQL+=" AND a1.co_doc = " + strCodDoc;
                                    strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.co_cli, a3.tx_nomcli, a1.co_tipdoccon, a1.co_banchq, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq ";
                                    strSQL+=" ORDER BY a3.tx_nomcli, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                                    rstLoc01=stmLoc01.executeQuery(strSQL);

                                    for(int k=0; rstLoc01.next(); k++)
                                    {
                                        strCodBanChq = rstLoc01.getString("co_banchq");
                                        strNumChq = rstLoc01.getString("tx_numchq");
                                        strFecVenChq = rstLoc01.getString("fe_venchq");

                                        if(strCodBanChq!=null && strNumChq!=null && strFecVenChq!=null)
                                        {
                                            strSQL="";
                                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_cli, a2.tx_nomcli, a1.co_banchq, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq ";
                                            strSQL+=" FROM tbm_pagmovinv AS a1 ";
                                            strSQL+=" INNER JOIN tbm_cabmovinv AS a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                            strSQL+=" AND a1.co_banchq = " + strCodBanChq;
                                            strSQL+=" AND a1.tx_numchq = '" + strNumChq + "'";
                                            strSQL+=" AND a1.fe_venchq = '" + strFecVenChq + "'";
                                            rstLoc02=stmLoc02.executeQuery(strSQL);
                                            if(rstLoc02.next())
                                            {
                                                ////Armar la sentencia SQL.////
                                                strSQL="";
                                                strSQL+="UPDATE tbm_detRecDoc";
                                                strSQL+=" SET nd_valCon = nd_valApl - nd_valApl";
                                                strSQL+=" WHERE co_emp=" + rstLoc02.getString("co_emp");
                                                strSQL+=" AND co_loc=" + rstLoc02.getString("co_loc");
                                                strSQL+=" AND co_banchq=" + rstLoc02.getString("co_banchq");
                                                strSQL+=" AND tx_numchq like ('" + rstLoc02.getString("tx_numchq") + "')";
                                                strSQL+=" AND st_reg IN ('A'); ";
                                                stm.executeUpdate(strSQL);
                                            }
                                            rstLoc02.close();
                                            rstLoc02=null;
                                        }
                                    }
                                    rstLoc01.close();
                                    rstLoc01 = null;
                                }
                            }
                       break;
                    }
                    stm.close();
                    stm=null;
                    stmLoc01.close();
                    stmLoc01=null;
                    stmLoc02.close();
                    stmLoc02=null;
                }
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
     * Esta funcion permite actualizar la tabla "tbm_detRecDoc".
     * @param intTipOpe El tipo de operacion.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operacion</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */ 
    private boolean actualizarTbmCabPag(int intTipOpe)
    {        
        boolean blnRes=true;
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                if (con!=null)
                {
                    stm=con.createStatement();
                    switch (intTipOpe)
                    {
                       case 0:
                            for (int i=0;i<objTblMod.getRowCount();i++) 
                            {
                                if (objTblMod.isChecked(i, INT_TBL_SEL))
                                {
                                    ////Armar la sentencia SQL.////
                                    strSQL ="";
                                    strSQL+=" UPDATE tbm_cabpag";
                                    strSQL+=" SET st_condepban = 'S'";
                                    strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa(); 
                                    strSQL+=" AND co_loc = " + objTblMod.getValueAt(i,INT_TBL_COD_LOC);
                                    strSQL+=" AND co_tipdoc =  " + objTblMod.getValueAt(i,INT_TBL_TIP_DOC);
                                    strSQL+=" AND co_doc = " + objTblMod.getValueAt(i,INT_TBL_COD_DOC);
                                    stm.executeUpdate(strSQL);
                                }
                            }
                       break;

                       case 1:
                            for (int i=0;i<objTblMod.getRowCount();i++) 
                            {
                                ////Armar la sentencia SQL.////
                                strSQL ="";
                                strSQL+=" UPDATE tbm_cabpag";
                                strSQL+=" SET st_condepban = 'N'";
                                strSQL+=" WHERE co_emp = " + objParSis.getCodigoEmpresa(); 
                                strSQL+=" AND co_loc = " + objTblMod.getValueAt(i,INT_TBL_COD_LOC);
                                strSQL+=" AND co_tipdoc =  " + objTblMod.getValueAt(i,INT_TBL_TIP_DOC);
                                strSQL+=" AND co_doc = " + objTblMod.getValueAt(i,INT_TBL_COD_DOC);
                                stm.executeUpdate(strSQL);
                            }
                       break;

                    }
                    stm.close();
                    stm=null;
                }
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
    
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.                
                strSQL="";
                strSQL+="DELETE FROM tbm_detPag";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    private boolean eliminarConDepBanPag()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" DELETE FROM tbm_conDepBanPag";
                strSQL+=" WHERE co_emp=" +objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.st_reg <> 'E' ";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = '" + strAux + "'";
		if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1 LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,    rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,    rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC, rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,    rst.getInt("co_doc"));
                    arlDatCon.add(arlRegCon);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;                
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }                  
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
    
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                    objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                            , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                            , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                            , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC) );
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
        
    }  
    
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        int intPosRel;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL ="";
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc";
                strSQL+="      , a1.ne_numDoc1, a1.ne_numDoc2, a1.st_imp";
                strSQL+="      , a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.nd_mondoc";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a1.st_reg <> 'E' ";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    lblDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    txtValDoc.setText("" + Math.abs(rst.getDouble("nd_monDoc")));
                    dblValTotDoc = Math.abs(rst.getDouble("nd_monDoc"));
                    strAux=rst.getString("ne_numDoc1");
                    txtNumDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I") )  
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    
                    ///para almacenar el estado de impresion///
                    String strEstImp = rst.getString("st_imp");
                    STRESTIMP = strEstImp;
                    
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        int  intNumTotReg;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                arlRegTbhFacSel=new ArrayList();

                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT COUNT(*)";
                strSQL+=" FROM (";
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.co_emp as EmpDep, a1.co_loc as LocDep, a1.co_tipdoc as TipDocDep,  ";
                strSQL+=" a1.co_doc as DocDep, sum(a1.nd_abo) as valdoc, a1.co_tipdoccon, a4.tx_descor, a4.tx_deslar, a2.fe_doc, a2.ne_numdoc1, a2.nd_mondoc ";
                strSQL+=" FROM tbm_detpag as a1 ";
                strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                strSQL+=" INNER JOIN tbm_condepbanpag as a3 ON (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_locpag and a2.co_tipdoc=a3.co_tipdocpag and a2.co_doc=a3.co_docpag) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a4 ON (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoccon=a4.co_tipdoc) ";                    
                strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg NOT IN ('E','I') ";
                strSQL+=" GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.co_emp, a1.co_loc, a1.co_tipdoc, ";
                strSQL+=" a1.co_doc, a1.co_tipdoccon, a4.tx_descor, a4.tx_deslar, a2.fe_doc, a2.ne_numdoc1, a2.nd_mondoc ";
                strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";

                strSQL+=" ) AS A1";

                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;

                INT_TOT_REG_CON = intNumTotReg;


                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.co_emp as EmpDep, a1.co_loc as LocDep, a1.co_tipdoc as TipDocDep,  ";
                strSQL+=" a1.co_doc as DocDep, sum(a1.nd_abo) as valdoc, a1.co_tipdoccon, a4.tx_descor, a4.tx_deslar, a2.fe_doc, a2.ne_numdoc1, a2.nd_mondoc ";
                strSQL+=" FROM tbm_detpag as a1 ";
                strSQL+=" INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) ";
                strSQL+=" INNER JOIN tbm_condepbanpag as a3 ON (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_locpag and a2.co_tipdoc=a3.co_tipdocpag and a2.co_doc=a3.co_docpag) ";
                strSQL+=" INNER JOIN tbm_cabtipdoc as a4 ON (a1.co_emp=a4.co_emp and a1.co_loc=a4.co_loc and a1.co_tipdoccon=a4.co_tipdoc) ";                    
                strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg NOT IN ('E','I') ";
                strSQL+=" GROUP BY a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc, a1.co_emp, a1.co_loc, a1.co_tipdoc, ";
                strSQL+=" a1.co_doc, a1.co_tipdoccon, a4.tx_descor, a4.tx_deslar, a2.fe_doc, a2.ne_numdoc1, a2.nd_mondoc ";
                strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc";
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();

                for(int i=0;rst.next();i++)
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");//0
                    vecReg.add(INT_TBL_SEL,"");//1
                    vecReg.add(INT_TBL_COD_LOC,rst.getString("LocDep"));//2
                    vecReg.add(INT_TBL_TIP_DOC,rst.getString("TipDocDep"));//3
                    vecReg.add(INT_TBL_COD_DOC,rst.getString("DocDep"));//4
                    vecReg.add(INT_TBL_COD_TIP_DOC,rst.getString("co_tipdoccon"));//5
                    vecReg.add(INT_TBL_DES_COR_TIP_DOC,rst.getString("tx_descor"));//6
                    vecReg.add(INT_TBL_DES_LAR_TIP_DOC,rst.getString("tx_deslar"));//7                            
                    vecReg.add(INT_TBL_NUM_DOC,rst.getString("ne_numdoc1"));//8
                    vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy"));////9
                    vecReg.add(INT_TBL_VAL_DEP,rst.getString("valdoc"));//10
                    vecReg.add(INT_TBL_ABO_DOC,rst.getString("valdoc"));//11

                    arlRegTbhFacSel.add(i, rst.getString("DocDep"));

                     double dblAuxAbo=Math.abs(rst.getDouble("valdoc"));   ////este valor es de la tabla tbm_detpag////
                     if (dblAuxAbo!=0)
                        vecReg.setElementAt(new Boolean(true),INT_TBL_SEL);

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
    
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCab())
                {
                    if (actualizarTbmDetRecDoc(1))
                    {
                        if(actualizarTbmCabPag(1))
                        {
                            if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                            {
                                con.commit();
                                blnRes=true;
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
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
    
    private boolean anularCab()
    {
        boolean blnRes=true;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                if(!txtCodDoc.getText().equals(""))
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_cabPag";
                    strSQL+=" SET st_reg='I'";                
                    strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                    strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND co_doc=" + txtCodDoc.getText();
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                    datFecAux=null;
                }
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
    
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {                
                if (eliminarCab())
                {
                    if (actualizarTbmDetRecDoc(1))
                    {
                        if(actualizarTbmCabPag(1))
                        {
                            if (objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
                            {                                
                                con.commit();
                                blnRes=true;
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();             
            }
            con.close();
            con=null;
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
     
    private boolean eliminarCab()
    {    
        boolean blnRes=true;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                if(!txtCodDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+="UPDATE tbm_cabPag";
                    strSQL+=" SET st_reg='E'";     ///////Eliminacion Logica//////
                    strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                    strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                    strSQL+=" WHERE co_emp=" + intCodEmp;
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND co_doc=" + txtCodDoc.getText();
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                    datFecAux=null;
                }
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
    

    public class MiToolBar extends ZafToolBar
    {      
        public MiToolBar(javax.swing.JInternalFrame jfrThis)
        {
            super(jfrThis, objParSis);
        }
            
        public void clickInsertar() 
        {
            intClickIns++;
            objTooBar.setEnabledImprimir(true);
            objTooBar.setEnabledVistaPreliminar(true);

            try
            {
                vecDat.clear();
                if (blnHayCam || objTblMod.isDataModelChanged())
                {
                    isRegPro();
                }
                limpiarFrm();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                butBusDep.setEnabled(true);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setColumnasEditables(vecAuxDat);
                objAsiDia.setEditable(true);
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (Exception e)   {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
     
        public boolean insertar()
        {
            boolean blnRes=false;
            if(banclick > 0)
            {
                if (!insertarReg())
                    return false;
                blnRes = true;
            }
            else  {
                mostrarMsgInf("¡¡¡ FAVOR SELECCIONE UN REGISTRO !!!");
            }
            return blnRes;                                 
        }
            
        public void clickConsultar()
        {
            clickbutcon++;
            vecDat.clear();
            lblDesCorTipDoc.requestFocus();
            objTblMod.setColumnasEditables(null);
        }
            
        public boolean consultar() {
            consultarReg(); 
            return true;                                
        }
                        
        public void clickModificar()
        {
            //Tipo de Documento Principal//
            lblDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtCodTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            butBusDep.setEnabled(false);

            txtCodDoc.setEditable(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblMod.setColumnasEditables(vecAuxDat);
            objAsiDia.setEditable(true);
            txtNumDoc.selectAll();
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
        }
    
        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
     
        public void clickEliminar()
        {
            if(clickbutcon==0)
                consultarReg();
            else
                cargarDetReg();
        }
            
        public boolean eliminar()
        {
            try
            {               
                if(!eliminarReg())
                    return false;
            
                //Desplazarse al siguiente registro si es posible.
                if(arlDatCon.size()>0){
                   if(intIndiceCon < arlDatCon.size()-1){
                        intIndiceCon++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Eliminado");
                       limpiarFrm();
                   }
                }
                blnHayCam=false;            
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            } 
            return true;            
        }
            
        public void clickAnular()
        {
            if(clickbutcon==0)
                consultarReg();
            else
                cargarDetReg();
        }
            
        public boolean anular()
        {
            try
            {               
                if(!anularReg())
                    return false;
            
                //Desplazarse al siguiente registro si es posible.
                if(arlDatCon.size()>0){
                   if(intIndiceCon < arlDatCon.size()-1){
                        intIndiceCon++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Anulado");
                   }
                }
                blnHayCam=false;            
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            } 
            return true;               
        }            
            
        public void clickInicio() 
        {
            try{
                vecDat.clear();
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ((blnHayCam) || objAsiDia.isDiarioModificado() ){
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }
        
        public void clickAnterior() {
            try{
                vecDat.clear();
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ((blnHayCam) ){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickSiguiente() {
            try{
                vecDat.clear();
                if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if ((blnHayCam) ){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }
        
        public void clickFin() {
            try{
                vecDat.clear();
                if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if ((blnHayCam) || objAsiDia.isDiarioModificado() ){
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
            
        public void clickImprimir()
        {

        }
        
        public void clickVisPreliminar()
        {                

        }

        public void clickAceptar()
        {
          vecDat.clear();
        }

        public void clickCancelar()
        {
            limpiarFrm();
            vecDat.clear();
            objTblMod.removeAllRows();
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }            

        public boolean afterVistaPreliminar() {
            return true;
        }            

        public boolean beforeModificar() 
        {
            calcularAboTot();

            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamValMod())
                return false;

            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();

            if(STRESTIMP.equals("S"))
            {
                mostrarMsgInf("El documento está IMPRESO.\n No es posible modificar este documento.\n Necesita una Autorizacion para modificar el documento .");
                return false;
            }
            return true;
        }
            
        public boolean beforeEliminar() 
        {                
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }
            
        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterInsertar() 
        {
            this.setEstado('w');
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            consultarReg();
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean afterImprimir() {
            actualizarEstImp();
            return true;
        }

        public boolean afterModificar() 
        {
            objTblMod.clearDataSavedBeforeRemoveRow();
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
            cargarDetReg();
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterEliminar() 
        {                
            return true;
        }

        public boolean afterAnular() 
        {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeInsertar() 
        {
            calcularAboTot();

            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean beforeAnular() 
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

            
        public boolean cancelar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean imprimir() 
        {        
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

        public boolean vistaPreliminar() 
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
        
        
    }
    
    private void limpiarFrm() 
    {
        //Cabecera
        txtCodDoc.setText("");
        txtCodDoc.setEditable(false);
        dtpFecDoc.setText("");
        txtCodTipDoc.setText("");
        txtCodTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        lblDesCorTipDoc.setText("");
        txtNumDoc.setText("");
        txtValDoc.setText("");
        txaObs1.setText("");
        txaObs2.setText("");

        objAsiDia.inicializar();
        objAsiDia.setEditable(false);
        objTblMod.removeAllRows();

        clickbutcon=0;
    
        dblValTotDoc=0;
        banclick=0;
        INT_TOT_REG_CON=0;
        INTNUMTOTREGCONFEC = 0;
    }
    
    private void clearCampos() 
    {
        txtValDoc.setText("");
        objAsiDia.inicializar();
        objAsiDia.setEditable(false);
        objTblMod.removeAllRows();
        clickbutcon=0;
        dblValTotDoc=0;
        banclick=0;
        INT_TOT_REG_CON=0;
        INTNUMTOTREGCONFEC = 0;
    }
    
    private String getNomEmp(int codEmp)  
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                sqlAux="";
                sqlAux+=" select tx_nom from tbm_emp";
                sqlAux+=" where co_emp=" + codEmp + "";
                rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;

    }

    private String getDirEmp(int codEmp)
    {
            java.sql.Connection conTipDoc;
            java.sql.Statement stmTipDoc; 
            java.sql.ResultSet rstTipDoc;
            String sqlAux, auxTipDoc="";
            try{
                conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTipDoc!=null){
                    stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    sqlAux="";
                    sqlAux+=" select tx_dir from tbm_emp";
                    sqlAux+=" where co_emp=" + codEmp + "";
                    rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                    if (rstTipDoc.next()){
                        auxTipDoc=rstTipDoc.getString("tx_dir");
                    }
                }
                conTipDoc.close();
                conTipDoc=null;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return auxTipDoc;

        }

    private String getRucEmp(int codEmp)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                sqlAux="";
                sqlAux+=" select tx_ruc from tbm_emp";
                sqlAux+=" where co_emp=" + codEmp + "";
                rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_ruc");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;

    }

    private String getGlo(int codDoc)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                sqlAux="";
                sqlAux+="select tx_glo from tbm_cabdia";
                sqlAux+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlAux+=" and co_loc=" + objParSis.getCodigoLocal() + "";
                sqlAux+=" and co_tipdoc=" + txtCodTipDoc.getText() + "";
                sqlAux+=" and co_dia=" + txtCodDoc.getText() + "";
                rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_glo");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;

    }
    
    private String getNomLarCtaCon(int codTipDoc)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="";
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                sqlAux="";
                sqlAux+="select a2.tx_deslar from tbm_cabtipdoc as a1 inner join tbm_placta as a2";
                sqlAux+=" on a1.co_emp=a2.co_emp and a1.co_ctadeb=a2.co_cta";
                sqlAux+=" and a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                sqlAux+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                sqlAux+=" and a1.co_tipdoc=" + codTipDoc + "";                
                rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_deslar");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;

    }

    private String getEstConDep()
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="", strcodLoc="", strcodTipDoc="", strcodDoc="";
        try
        {
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null)
            {
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                for(int j=0; j < objTblMod.getRowCountTrue(); j++)
                {
                    if (objTblMod.isChecked(j, INT_TBL_SEL))
                    {
                        strcodLoc=objUti.parseString(objTblMod.getValueAt(j,INT_TBL_COD_LOC));
                        strcodTipDoc=objUti.parseString(objTblMod.getValueAt(j,INT_TBL_TIP_DOC));
                        strcodDoc=objUti.parseString(objTblMod.getValueAt(j,INT_TBL_COD_DOC));

                        if(!strcodLoc.equals(""))
                        {
                            sqlAux="";//,
                            sqlAux+=" select a1.st_condepban ";
                            sqlAux+=" from tbm_cabpag as a1";
                            sqlAux+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            sqlAux+=" and a1.co_loc=" + strcodLoc + "";
                            sqlAux+=" and a1.co_tipdoc=" + strcodTipDoc + "";
                            sqlAux+=" and a1.co_doc=" + strcodDoc + "";
                            rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                            if (rstTipDoc.next())
                            {
                                auxTipDoc=rstTipDoc.getString("st_condepban");
                            }
                        }
                    }
                }
            }
            conTipDoc.close();
            conTipDoc=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return auxTipDoc;
    }

    protected String strNomDocDet(int tmp)
    {
        int tmpFun=tmp;
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        String strSQL,strAux="";
        
        try
        {
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conTmp!=null)
            {
                stmTmp=conTmp.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="select tx_descor from tbm_cabtipdoc";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" and co_tipdoc=" + tmpFun + "";
                rstTmp=stmTmp.executeQuery(strSQL);
                if (rstTmp.next())
                {
                    strAux=rstTmp.getString("tx_descor");
                }
            }            
            //rstTmp.close();
            //stmTmp.close();
            conTmp.close();
            rstTmp=null;
            stmTmp=null;
            conTmp=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strAux;            
    }
    
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
        if (objAsiDia.getGeneracionDiario()==2)
        {
            strAux="¿Desea regenerar el asiento de diario?\n";
            strAux+="El asiento de diario ha sido modificado manualmente.";
            strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
            strAux+="\nSi desea grabar el asiento de diario tal como está de click en NO.";
            strAux+="\nSi desea cancelar ï¿½sta operaciï¿½n de click en CANCELAR.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    objAsiDia.setGeneracionDiario((byte)0);                    
                    objAsiDia.generarDiario(tblDat, INT_TBL_SEL, INT_TBL_COD_TIP_DOC, INT_TBL_ABO_DOC);
                    break;
                case 1: //NO_OPTION
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
            }
        }
        else
        {
            objAsiDia.setGeneracionDiario((byte)0);
            objAsiDia.generarDiario(tblDat, INT_TBL_SEL, INT_TBL_COD_TIP_DOC, INT_TBL_ABO_DOC);
        }
        return blnRes;
    }    
    
    //Funcion para saber Nombre Corto de Tipo de Documento de Cuenta Contable//
    protected String strNomDctoDos(String codDocDos)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="";        
        int intCodDos=0;
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null)
            {
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if(!codDocDos.equals(""))
                {
                    intCodDos=Integer.parseInt(codDocDos);

                    sqlAux="";
                    sqlAux+=" select a1.tx_descor from tbm_cabtipdoc as a1";
                    sqlAux+=" where";
                    sqlAux+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    sqlAux+=" and a1.co_tipdoc=" + intCodDos + "";                                                
                    rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                    if (rstTipDoc.next()){
                        auxTipDoc=rstTipDoc.getString("tx_descor");
                    }
                    conTipDoc.close();
                    rstTipDoc.close();
                    stmTipDoc.close();
                    conTipDoc = null;
                    rstTipDoc = null;
                    stmTipDoc = null;
                }                               
            }
            
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;
        
    }        

    //Funcion para saber Nombre Largo de Tipo de Documento de Cuenta Contable//
    protected String strNomLarDctoDos(String codDocDos){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String sqlAux, auxTipDoc="";        
        int intCodDos=0;
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null)
            {
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                if(!codDocDos.equals(""))
                {
                    intCodDos=Integer.parseInt(codDocDos);

                    sqlAux="";
                    sqlAux+=" select a1.tx_deslar from tbm_cabtipdoc as a1";
                    sqlAux+=" where";
                    sqlAux+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    sqlAux+=" and a1.co_tipdoc=" + intCodDos + "";      
                    rstTipDoc=stmTipDoc.executeQuery(sqlAux);
                    if (rstTipDoc.next()){
                        auxTipDoc=rstTipDoc.getString("tx_deslar");
                    }
                    conTipDoc.close();
                    rstTipDoc.close();
                    stmTipDoc.close();
                    conTipDoc = null;
                    rstTipDoc = null;                
                    stmTipDoc = null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;        
    }

    /*Cantidad de Registros con Depositos Aplicados*/
    protected int strCanFacApl(String strBanChq, String strNumChq)
    {
        String sqlAux;        
        int intCanRegDetRecPag=0;
        Statement stmNumFac;
        Connection conNumFac;
        try
        {         
            conNumFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumFac!=null)
            {
                stmNumFac=conNumFac.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);                                
                sqlAux="";                                
                sqlAux+=" select count(*) as CanFacApl";
                sqlAux+=" from tbm_pagmovinv as a1";
                sqlAux+=" where a1.co_emp = " + objParSis.getCodigoEmpresa();                
                sqlAux+=" and a1.co_banchq = " + strBanChq;
                sqlAux+=" and a1.tx_numchq = " + strNumChq;                
                intCanRegDetRecPag = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), sqlAux);                
                stmNumFac.close();
                stmNumFac = null;
                conNumFac.close();
                conNumFac=null;
            }            
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return intCanRegDetRecPag;
    }
    
    private boolean actualizarEstImp()
    {
        boolean blnRes=true;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_imp='S'";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                stm.executeUpdate(strSQL);
            }
            stm.close();
            con.close();
            stm=null;
            con=null;
            datFecAux=null;
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
    
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {            
            intFilPro=objTblMod.getRowCountTrue();
            
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_ABO_DOC)==null)?"":objTblMod.getValueAt(i, INT_TBL_ABO_DOC).toString();
                dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTot+=dblVal;
            }
            dblValTotDoc = objUti.redondear(dblTot,objParSis.getDecimalesBaseDatos());
            //Mostrar el Valor Total en el txtMonDoc//
            txtValDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            txtValDoc.setText("[ERROR]");
        }
    }   
    
    /**
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ï¿½sta clase
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
            switch (intIndFun)
            {
                case 0: //Boton "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Boton "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
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
     
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        boolean blnRes=true;
        String strRutRpt, strNomRpt;
        int i, intNumTotRpt;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        String nomEmp=getNomEmp(intCodEmp);
        String nomUsu=objParSis.getNombreUsuario();
        String dirEmp=getDirEmp(intCodEmp);
        String rucEmp=getRucEmp(intCodEmp);
        String glo=getGlo(Integer.parseInt(txtCodDoc.getText()));
        int intCodTipDocCtaCon = 0;
        String nomLarCtaCon="";

        for(i=0; i < objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_SEL))
            {
                intCodTipDocCtaCon = Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_TIP_DOC).toString());
                nomLarCtaCon=getNomLarCtaCon(intCodTipDocCtaCon);
            }
        }
        
        //Inicializar los parametros que se van a pasar al reporte.
        java.util.Map mapPar=new java.util.HashMap();
        Connection conIns;
        try
        {
            conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try
            {
                if(conIns!=null)
                {
                    objRptSis.cargarListadoReportes();
                    objRptSis.show();
                    if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
                    {
                        //Obtener la fecha y hora del servidor.
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        if (datFecAux==null)
                            return false;

                        intNumTotRpt=objRptSis.getNumeroTotalReportes();
                        for (i=0;i<intNumTotRpt;i++)
                        {
                            if (objRptSis.isReporteSeleccionado(i))
                            {
                                switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                {
                                    case 64:
                                    default:
                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);

                                            mapPar.put("codEmp", ""+intCodEmp);
                                            mapPar.put("codLoc", ""+intCodLoc);
                                            mapPar.put("codTipDoc", ""+txtCodTipDoc.getText());
                                            mapPar.put("codDia", txtCodDoc.getText());
                                            mapPar.put("nomEmp", nomEmp);
                                            mapPar.put("dirEmp", dirEmp);
                                            mapPar.put("rucEmp", rucEmp);
                                            mapPar.put("fecDoc", dtpFecDoc.getText());
                                            mapPar.put("nomCta", nomLarCtaCon);
                                            mapPar.put("glo", glo);
                                            mapPar.put("monDoc", txtValDoc.getText());
                                            mapPar.put("numAlt", txtNumDoc.getText());
                                            mapPar.put("nomUsu", nomUsu);

                                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;
                                }
                            }
                        }
                    }                  
                }
            }
            catch (Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }           
        }
        catch(SQLException ex)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, ex);
        }
        return blnRes;
    }

    
    
    
}
