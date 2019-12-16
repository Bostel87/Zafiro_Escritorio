/*
 * ZafCxC20.java
 *
 * Created on 16 de enero de 2007, 10:10 AM
 * Programa ESTADO DE CUENTA DE CLIENTES   
 */    
package CxC.ZafCxC20;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafGetDat.ZafDatLocInm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafLocPrgUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;

/**
 *
 * @author Darío Cárdenas
 */
public class ZafCxC20 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable de la Tabla de Datos:
    private static final int INT_TBL_DAT_LIN=0;                        //Línea
    private static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la Empresa.
    private static final int INT_TBL_DAT_COD_LOC=2;                    //Código del Local.
    private static final int INT_TBL_DAT_COD_TIP_DOC=3;                //Código del Tipo de Documento.  
    private static final int INT_TBL_DAT_TIP_DOC_COR=4;                //Descripcion Corta del Tipo de Documento.
    private static final int INT_TBL_DAT_TIP_DOC_LAR=5;                //Descripcion Larga del Tipo de Documento.
    private static final int INT_TBL_DAT_COD_DOC=6;                    //Código del documento.
    private static final int INT_TBL_DAT_COD_REG=7;                    //Código de Registro
    private static final int INT_TBL_DAT_NUM_DOC=8;                    //Número de documento.
    private static final int INT_TBL_DAT_FEC_DOC=9;                    //Fecha del documento.
    private static final int INT_TBL_DAT_DIA_CRE=10;                   //Dias de Credito.
    private static final int INT_TBL_DAT_FEC_VEN=11;                   //Fecha de vencimiento del documento.
    private static final int INT_TBL_DAT_POR_RET=12;                   //Porcentaje de Retencion
    private static final int INT_TBL_DAT_VAL_DOC=13;                   //Valor del documento.
    private static final int INT_TBL_DAT_VAL_ABO=14;                   //Valor del Abono.
    private static final int INT_TBL_DAT_VAL_PEN=15;                   //Valor por vencer.    
    private static final int INT_TBL_DAT_COD_BAN=16;                   //Codigo del Banco.
    private static final int INT_TBL_DAT_NOM_BAN=17;                   //Nombre del Banco.    
    private static final int INT_TBL_DAT_NUM_CTA=18;                   //Numero de Cuenta del Banco.
    private static final int INT_TBL_DAT_NUM_CHQ=19;                   //Numero de Cheque.
    private static final int INT_TBL_DAT_FEC_REC_CHQ=20;               //Fecha de Recepcion del Cheque.
    private static final int INT_TBL_DAT_FEC_VEN_CHQ=21;               //Fecha de Vencimiento del Cheque.
    private static final int INT_TBL_DAT_VAL_CHQ=22;                   //Valor de Cheque.
    
    //Constantes: Columnas del JTable de la Tabla de Datos Vencidos:
    private static final int INT_TBL_VEN_LIN=0;                        //Línea
    private static final int INT_TBL_VEN_DAT=1;                        //Datos por vencer
    private static final int INT_TBL_VEN_30D=2;                        //Rango de Dias de 1 a 30
    private static final int INT_TBL_VEN_60D=3;                        //Rango de Dias de 31 a 60
    private static final int INT_TBL_VEN_90D=4;                        //Rango de Dias de 61 a 90
    private static final int INT_TBL_VEN_MAS=5;                        //Rango de Dias de 91 en Adelante
    private static final int INT_TBL_VEN_TOT_1=6;                      //Valores de Totales Vencidos
    private static final int INT_TBL_VEN_TOT_2=7;                      //Valores de Totales Pendientes
    
    //Variables
    private ZafDatePicker dtpDat;                               //esto es para calcular el numero de dias transcurridos
    private ZafUtil objAux;
    private ZafParSis objParSis;
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
    private ZafDatLocInm objDatLocInm;                          //Objeto que almacena los locales enlazados de inmaconsa.
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod, objTblModVen;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.    
    private ZafTblPopMnu objTblPopMnu, objTblPopMnu_2; 
    private ZafTblEdi objTblEdi;                        //PopupMenu: Establecer PopMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafRptSis objRptSis;                        //Reportes del Sistema.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta.
    private ZafVenCon vcoCli;                           //Ventana de consulta "Cliente".
    private ZafSelFec objSelFec;    
    private ZafTblOrd objTblOrd;
    private ZafDatePicker txtFecCor;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private JLabel lblNomfec = new JLabel();
    javax.swing.JInternalFrame jfrThis,ThisAux;         //Hace referencia a this

    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecDatVen, vecCab, vecCabVen, vecReg , vecRegVen, vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private int intButCon=0, INTCODEMP=0, INTCODEMPGRP=0;
    private String strCodCli, strDesLarCli;             //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String STRBAN="";       
    private String strFecCor="", strAnd="";
    private String strVersion = " v1.0.6";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC20(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();

            if (! configurarFrm())
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
            ///Inicializar objetos.///
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            dtpDat=new ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");//inicializa el objeto DatePicker                                           
                        
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            
            //Obtener los locales por Usuario y Programa.
            objDatLocInm=new ZafDatLocInm(objParSis);
            
            //configurar los campoos Obligatorios//
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
            
            //Configurar ZafSelFec://
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxChecked(false);
            panFil.add(objSelFec);
            //objSelFec.setBounds(24, 170, 472, 72);
            objSelFec.setBounds(20, 30,500, 72);
            
            /* JoseMario 23/Feb/2017 */
            lblNomfec.setFont(new java.awt.Font("SansSerif", 0, 12));
            lblNomfec.setText("Fecha de Corte:");
            txtFecCor = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecCor.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecCor.setText("");
            txtFecCor.setEnabled(false);
            panFil.add(lblNomfec);
            panFil.add(txtFecCor);
            lblNomfec.setBounds(564, 35, 92, 10);// Coordenadas para la fecha de corte
            txtFecCor.setBounds(564, 50, 92, 20);
            txtFecCor.setVisible(false);
            lblNomfec.setVisible(false);// Al inicio no seran visibles
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            
            //Configurar JTable:    //Establecer el modelo.//
            vecDat=new Vector();    //Almacena los datos.//
            vecCab=new Vector(25);  //Almacena las cabeceras.//
            vecCab.clear();            
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            vecCab.add(INT_TBL_DAT_LIN,"");//0
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");//1
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cod.Loc.");//2
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cod.Tip.Doc.");//3
            vecCab.add(INT_TBL_DAT_TIP_DOC_COR,"Tip.Doc.");//4
            vecCab.add(INT_TBL_DAT_TIP_DOC_LAR,"Nombre");//5
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");//6
            vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");//7
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");//8
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");//9
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Dia.Cre.");//10
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");//11
            vecCab.add(INT_TBL_DAT_POR_RET,"%.Ret.");//12
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");//13
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Val.Abo.");//14
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");//15
            vecCab.add(INT_TBL_DAT_COD_BAN,"Cód.Ban.");//16
            vecCab.add(INT_TBL_DAT_NOM_BAN,"Banco");//17
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Num.Cta.");//18
            vecCab.add(INT_TBL_DAT_NUM_CHQ,"Num.Chq.");//19
            vecCab.add(INT_TBL_DAT_FEC_REC_CHQ,"Fec.Rec.Chq.");//20
            vecCab.add(INT_TBL_DAT_FEC_VEN_CHQ,"Fec.Ven.Chq.");//21
            vecCab.add(INT_TBL_DAT_VAL_CHQ,"Val.Chq.");//22
            
            ///tabla de Datos///
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
                       
            //Configurar JTable: Establecer tipo de selección.//
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menú de contexto. en la Tabla de Datos///
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);//0
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);//1
	    tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);//2
	    tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);//3
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC_COR).setPreferredWidth(60);//4
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC_LAR).setPreferredWidth(40);//5
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);//6
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(30);//7
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);//8
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(90);//9
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(30);//10
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(90);//11
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(35);//12
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(85);//13
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(85);//14
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(85);//15
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setPreferredWidth(10);//16
            tcmAux.getColumn(INT_TBL_DAT_NOM_BAN).setPreferredWidth(60);//17
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(80);//18
            tcmAux.getColumn(INT_TBL_DAT_NUM_CHQ).setPreferredWidth(50);//19
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC_CHQ).setPreferredWidth(90);//20
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_CHQ).setPreferredWidth(90);//21
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setPreferredWidth(80);//22
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Ocultando columnas para el usuario que son del sistema///
            hideColumns(INT_TBL_DAT_COD_EMP);//1
            hideColumns(INT_TBL_DAT_COD_TIP_DOC);//3
            hideColumns(INT_TBL_DAT_TIP_DOC_LAR);//5
            hideColumns(INT_TBL_DAT_COD_DOC);//6
            hideColumns(INT_TBL_DAT_COD_REG);//7
            hideColumns(INT_TBL_DAT_COD_BAN);//16
            
            ///////CONDICION SEGUN EL CHK QUE ESTE ACTIVO/////            
                if(!((chkMosConRes.isSelected()) && (chkMosSinRes.isSelected())))
                {                                        
                    if((chkMosConRes.isSelected()) == true)   ///este es el caso de que el chk CON RESPALDO este activo///
                    {                                                
                        showColumns(INT_TBL_DAT_NOM_BAN, 60);//17
                        showColumns(INT_TBL_DAT_NUM_CTA, 80);//18
                        showColumns(INT_TBL_DAT_NUM_CHQ, 50);//19
                        showColumns(INT_TBL_DAT_FEC_REC_CHQ, 60);//20
                        showColumns(INT_TBL_DAT_FEC_VEN_CHQ, 60);//21
                        showColumns(INT_TBL_DAT_VAL_CHQ, 80);//22
                    }
                    if((chkMosSinRes.isSelected()) == true)   /// este es el caso de que el chk SIN RESPALDO este activo///
                    {                        
                        hideColumns(INT_TBL_DAT_NOM_BAN);//17
                        hideColumns(INT_TBL_DAT_NUM_CTA);//18
                        hideColumns(INT_TBL_DAT_NUM_CHQ);//19
                        hideColumns(INT_TBL_DAT_FEC_REC_CHQ);//20
                        hideColumns(INT_TBL_DAT_FEC_VEN_CHQ);//21
                        hideColumns(INT_TBL_DAT_VAL_CHQ);//22
                    }
                }
                else
                {
                    showColumns(INT_TBL_DAT_NOM_BAN, 60);//17
                    showColumns(INT_TBL_DAT_NUM_CTA, 80);//18
                    showColumns(INT_TBL_DAT_NUM_CHQ, 50);//19
                    showColumns(INT_TBL_DAT_FEC_REC_CHQ, 60);//20
                    showColumns(INT_TBL_DAT_FEC_VEN_CHQ, 60);//21
                    showColumns(INT_TBL_DAT_VAL_CHQ, 80);//22
                }
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ///renderizador para centrar los datos de las columnas///
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC_COR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            ///tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ///Configurar la Tabla para Datos x Vencer///
            configurarTblValVen();
            ///Configurar la Ventana de Consulta de Cliente///
            configurarVenConCli();
                        
            //Configurar JTable: Establecer relaciï¿½n entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_ABO, INT_TBL_DAT_VAL_PEN};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            objTblCelRenLbl=null;
            
            txtCodCli.requestFocus();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    /** Configurar el formulario. */
    private boolean configurarTblValVen()
    {        
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatVen=new Vector();    //Almacena los datos
            vecCabVen=new Vector(15);  //Almacena las cabeceras
            vecCabVen.clear();
            vecCabVen.add(INT_TBL_VEN_LIN, "");
            vecCabVen.add(INT_TBL_VEN_DAT, "Dat.x.Ven");
            vecCabVen.add(INT_TBL_VEN_30D, "1 - 30 Dias");
            vecCabVen.add(INT_TBL_VEN_60D, "31 - 60 Dias");
            vecCabVen.add(INT_TBL_VEN_90D, "61 - 90 Dias");
            vecCabVen.add(INT_TBL_VEN_MAS, "+ 91 Dias");
            vecCabVen.add(INT_TBL_VEN_TOT_1, "Tot.Ven.");
            vecCabVen.add(INT_TBL_VEN_TOT_2, "Tot.Pen.");
            
            objTblModVen=new ZafTblMod();
            objTblModVen.setHeader(vecCabVen);            
            tblTotVen.setModel(objTblModVen);
            
            //Configurar JTable: Establecer tipo de selección.
            tblTotVen.setRowSelectionAllowed(true);
            tblTotVen.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblTotVen,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu_2=new ZafTblPopMnu(tblTotVen);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotVen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblTotVen.getColumnModel();
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);            
            tcmAux.getColumn(INT_TBL_VEN_DAT).setPreferredWidth(90);//antes estaba 60
            tcmAux.getColumn(INT_TBL_VEN_30D).setPreferredWidth(90);//antes estaba 60
            tcmAux.getColumn(INT_TBL_VEN_60D).setPreferredWidth(90);//antes estaba 60
            tcmAux.getColumn(INT_TBL_VEN_90D).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_VEN_MAS).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_VEN_TOT_1).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_VEN_TOT_2).setPreferredWidth(90);                        
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.                       
            tblTotVen.getTableHeader().setReorderingAllowed(false);                        
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.            
            tblTotVen.getTableHeader().addMouseMotionListener(new ZafMouMotAdaVen());
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblTotVen);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);            
            tcmAux.getColumn(INT_TBL_VEN_DAT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VEN_30D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VEN_60D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VEN_90D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VEN_MAS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VEN_TOT_1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VEN_TOT_2).setCellRenderer(objTblCelRenLbl);
                        
            objTblCelRenLbl=null;            
            
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Codigo de la Empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Codigo del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Codigo del Tipo de Documento";
                    break;
                case INT_TBL_DAT_TIP_DOC_COR:
                    strMsg="Descripcion Corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_TIP_DOC_LAR:
                    strMsg="Descripcion Larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Codigo de Documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Codigo del Registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Numero del Documento";
                    break;               
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del Documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Dias de Credito";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de Vencimiento del Registro";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Número Porcentaje de Retencion";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor Total del Documento";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Valor Abonado del Documento";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor por Vencer/Cobrar del Documento";
                    break;
                case INT_TBL_DAT_COD_BAN:
                    strMsg="Codigo del Banco del Cheque";
                    break;
                case INT_TBL_DAT_NOM_BAN:
                    strMsg="Nombre del Banco del Cheque";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Numero de la Cuenta";
                    break;
                case INT_TBL_DAT_NUM_CHQ:
                    strMsg="Numero del Cheque";
                    break;
                case INT_TBL_DAT_FEC_REC_CHQ:
                    strMsg="Fecha de Recepcion del Cheque";
                    break;
                case INT_TBL_DAT_FEC_VEN_CHQ:
                    strMsg="Fecha de Vencimiento del Cheque";
                    break;
                case INT_TBL_DAT_VAL_CHQ:
                    strMsg="Valor del Cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAdaVen extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            /////para los datos x vencer//////////
            int intColVen = tblTotVen.columnAtPoint(evt.getPoint());
            String strMsgVen="";
            switch (intColVen)
            {   
                case INT_TBL_VEN_LIN:
                    strMsgVen="";
                    break;                                                
                case INT_TBL_VEN_DAT:
                    strMsgVen="Datos por Vencer";
                    break;
                case INT_TBL_VEN_30D:
                    strMsgVen="Datos Vencidos de 1 - 30 Dias";
                    break;
                case INT_TBL_VEN_60D:
                    strMsgVen="Datos Vencidos de 31 - 60 Dias";
                    break;
                case INT_TBL_VEN_90D:
                    strMsgVen="Datos Vencidos de 61 - 90 Dias";
                    break;
                case INT_TBL_VEN_MAS:
                    strMsgVen="Datos Vencidos de 90  o Mas Dias";
                    break;               
                case INT_TBL_VEN_TOT_1:
                    strMsgVen="Total en Valores de Dias Vencidos";
                    break;
                case INT_TBL_VEN_TOT_2:
                    strMsgVen="Total Pendientes";
                    break;
                default:
                    strMsgVen="";
                    break;
            }
            tblTotVen.getTableHeader().setToolTipText(strMsgVen);
        }
    } 
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
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
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Último documento");
            arlAli.add("Naturaleza de documento");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("50");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";   
            strSQL+=" FROM tbm_cabTipDoc AS a1 ";    
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal(); 
            strSQL+=" ORDER BY a1.tx_desCor ";    
            //System.out.println("configurarVenConTipDoc: "+strSQL);
            
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtNomDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtNomDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }  
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        int intCodEmp, intCodEmpGrp;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
            INTCODEMP = intCodEmp;
            INTCODEMPGRP = intCodEmpGrp;
            
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
            if(intCodEmp == intCodEmpGrp)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT c2.co_cli, c2.tx_ide, c2.tx_nom, c2.tx_dir";
                strSQL+=" FROM (";
                strSQL+="        SELECT b2.co_emp, MAX(b2.co_cli) AS co_cli, b2.tx_ide";
                strSQL+="        FROM (";
                strSQL+="               SELECT MAX(co_emp) AS co_emp, tx_ide";
                strSQL+="               FROM tbm_cli";
                strSQL+="               GROUP BY tx_ide";
                strSQL+="               ORDER BY tx_ide";
                strSQL+="             ) AS b1";
                strSQL+="        INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.tx_ide=b2.tx_ide)";
                strSQL+="        GROUP BY b2.co_emp, b2.tx_ide";
                strSQL+="      ) AS c1";
                strSQL+=" INNER JOIN tbm_cli AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_cli=c2.co_cli)";
                strSQL+=" ORDER BY c2.tx_nom";
            }
            else
            {
                /*Query mejorado para consultar Clientes filtrado por local y empresa*/            
                //Armar la sentencia SQL.          
                        
//                //Rose: Valida si el usuario tiene acceso a locales.
//                if (objParSis.getCodigoUsuario() != 1) 
//                {
//                    strSQL="";
//                    strSQL+=" SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
//                    strSQL+=" FROM  tbr_cliloc AS a1";
//                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    if ((objLocPrgUsr.validaLocUsr()))  {
//                        strSQL += " AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(1) + ")";
//                    } 
//                    else   {
//                        strSQL += " AND a1.co_loc not in (" + objLocPrgUsr.cargarLoc(1) + ")";
//                    }
//                    strSQL+=" AND a2.st_reg='A'";
//                    strSQL+=" ORDER BY a2.tx_nom";
//                }
//                else  //Admin
//                {
//                    strSQL="";
//                    strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//                    strSQL+=" FROM tbm_cli AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" AND a1.st_reg='A'";
//                    strSQL+=" ORDER BY a1.tx_nom";
//                }
                
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" FROM  tbr_cliloc AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.st_reg='A'";
                    strSQL+=" ORDER BY a2.tx_nom";
                }
            }
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se
     * debe hacer una busqueda directa (No se muestra la ventana de consulta a
     * menos que no exista lo que se está buscando) o presentar la ventana de
     * consulta para que el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIde.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIde.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIde.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIde.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIde.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
        btnGrpFec = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtIde = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtTipDoc = new javax.swing.JTextField();
        txtNomDoc = new javax.swing.JTextField();
        butDoc = new javax.swing.JButton();
        chkMosCre = new javax.swing.JCheckBox();
        chkMosDeb = new javax.swing.JCheckBox();
        chkMosSinRes = new javax.swing.JCheckBox();
        chkMosRet = new javax.swing.JCheckBox();
        chkMosDocVen = new javax.swing.JCheckBox();
        chkMosConRes = new javax.swing.JCheckBox();
        optRanFec = new javax.swing.JRadioButton();
        optFecCor = new javax.swing.JRadioButton();
        optFecAct = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panSubDet = new javax.swing.JPanel();
        spnVen = new javax.swing.JScrollPane();
        tblTotVen = new javax.swing.JTable();
        lblObs = new javax.swing.JLabel();
        txaObs = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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

        panFil.setLayout(null);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(22, 115, 120, 20);
        panFil.add(txtIde);
        txtIde.setBounds(120, 100, 0, 0);

        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(154, 115, 56, 20);

        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(210, 115, 430, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 115, 20, 20);

        lblTipDoc.setText("Tipo de documento:");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(22, 135, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(115, 60, 0, 0);

        txtTipDoc.setMaximumSize(null);
        txtTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocActionPerformed(evt);
            }
        });
        txtTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipDocFocusLost(evt);
            }
        });
        panFil.add(txtTipDoc);
        txtTipDoc.setBounds(154, 135, 56, 20);

        txtNomDoc.setMaximumSize(null);
        txtNomDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDocActionPerformed(evt);
            }
        });
        txtNomDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDocFocusLost(evt);
            }
        });
        panFil.add(txtNomDoc);
        txtNomDoc.setBounds(210, 135, 430, 20);

        butDoc.setText("...");
        butDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDocActionPerformed(evt);
            }
        });
        panFil.add(butDoc);
        butDoc.setBounds(640, 135, 20, 20);

        chkMosCre.setSelected(true);
        chkMosCre.setText("Mostrar los Créditos");
        chkMosCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCreActionPerformed(evt);
            }
        });
        panFil.add(chkMosCre);
        chkMosCre.setBounds(30, 175, 190, 23);

        chkMosDeb.setSelected(true);
        chkMosDeb.setText("Mostrar los Débitos");
        chkMosDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDebActionPerformed(evt);
            }
        });
        panFil.add(chkMosDeb);
        chkMosDeb.setBounds(30, 195, 190, 23);

        chkMosSinRes.setSelected(true);
        chkMosSinRes.setText("Mostrar los documentos sin Respaldo");
        chkMosSinRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosSinResActionPerformed(evt);
            }
        });
        panFil.add(chkMosSinRes);
        chkMosSinRes.setBounds(30, 215, 280, 23);

        chkMosRet.setSelected(true);
        chkMosRet.setText("Mostrar las Retenciones");
        panFil.add(chkMosRet);
        chkMosRet.setBounds(350, 175, 190, 23);

        chkMosDocVen.setText("Mostrar sólo los documentos vencidos");
        panFil.add(chkMosDocVen);
        chkMosDocVen.setBounds(350, 195, 270, 23);

        chkMosConRes.setSelected(true);
        chkMosConRes.setText("Mostrar los documentos con respaldos");
        chkMosConRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosConResActionPerformed(evt);
            }
        });
        panFil.add(chkMosConRes);
        chkMosConRes.setBounds(350, 215, 280, 23);

        btnGrpFec.add(optRanFec);
        optRanFec.setText("Rango de Fecha");
        optRanFec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optRanFecActionPerformed(evt);
            }
        });
        panFil.add(optRanFec);
        optRanFec.setBounds(140, 8, 140, 23);

        btnGrpFec.add(optFecCor);
        optFecCor.setText("Fecha de Corte");
        optFecCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFecCorActionPerformed(evt);
            }
        });
        panFil.add(optFecCor);
        optFecCor.setBounds(280, 8, 130, 23);

        btnGrpFec.add(optFecAct);
        optFecAct.setSelected(true);
        optFecAct.setText("Fecha Actual");
        optFecAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFecActActionPerformed(evt);
            }
        });
        panFil.add(optFecAct);
        optFecAct.setBounds(30, 8, 100, 23);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setPreferredSize(new java.awt.Dimension(453, 403));
        panRpt.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panDet.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(3, 18));

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

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        panSubDet.setPreferredSize(new java.awt.Dimension(10, 90));
        panSubDet.setLayout(new java.awt.BorderLayout());

        spnVen.setPreferredSize(new java.awt.Dimension(454, 40));

        tblTotVen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnVen.setViewportView(tblTotVen);

        panSubDet.add(spnVen, java.awt.BorderLayout.NORTH);

        lblObs.setText("Observacion: ");
        panSubDet.add(lblObs, java.awt.BorderLayout.WEST);

        txaObs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panSubDet.add(txaObs, java.awt.BorderLayout.CENTER);

        panRpt.add(panSubDet, java.awt.BorderLayout.SOUTH);

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

        butVisPre.setText("Vista Preliminar");
        butVisPre.setToolTipText("Vista Preliminar del Documento");
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
        butImp.setToolTipText("Imprime Documento");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkMosConResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosConResActionPerformed
        if (!chkMosSinRes.isSelected())
        {
            chkMosConRes.setSelected(true);
        }
    }//GEN-LAST:event_chkMosConResActionPerformed

    private void chkMosSinResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosSinResActionPerformed
        if (!chkMosConRes.isSelected())
        {
            chkMosSinRes.setSelected(true);
        }
    }//GEN-LAST:event_chkMosSinResActionPerformed

    private void chkMosDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDebActionPerformed
        if (!chkMosCre.isSelected())
        {
            chkMosDeb.setSelected(true);
        }
    }//GEN-LAST:event_chkMosDebActionPerformed

    private void chkMosCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCreActionPerformed
        if (!chkMosDeb.isSelected())
        {
            chkMosCre.setSelected(true);
            //  System.out.println("---activo Credito---");
            //  System.out.println("---NO activo Debito---");
        }
    }//GEN-LAST:event_chkMosCreActionPerformed

    private void butDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butDocActionPerformed

 
    
    private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        if (!txtNomDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtNomDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtNomDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtNomDocFocusLost

    private void txtNomDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusGained
        strDesLarTipDoc=txtNomDoc.getText();
        txtNomDoc.selectAll();
    }//GEN-LAST:event_txtNomDocFocusGained

    private void txtNomDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDocActionPerformed
        txtNomDoc.transferFocus();
    }//GEN-LAST:event_txtNomDocActionPerformed

    private void txtTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtTipDocFocusLost

    private void txtTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusGained
        strDesCorTipDoc=txtTipDoc.getText();
        txtTipDoc.selectAll();        
    }//GEN-LAST:event_txtTipDocFocusGained

    private void txtTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocActionPerformed
        txtTipDoc.transferFocus();
    }//GEN-LAST:event_txtTipDocActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        ////Verificar que el campo de Codigo del cliente/proveedor sea ingresado antes de imprimir///
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Impresion...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
                intButCon=3;
            }
        }
    }//GEN-LAST:event_butImpActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        ////Verificar que el campo de Codigo del cliente/proveedor sea ingresado antes de imprimir///
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Vista Preliminar...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
                intButCon=2;
            }
        }
    }//GEN-LAST:event_butVisPreActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").             
        ////Verificar que el campo de Codigo del cliente/proveedor sea ingresado antes de imprimir///
        if(txtCodCli.getText().equals(""))
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente/Proveedor </FONT> debe ser <BR><FONT COLOR=\"blue\">Ingresado</FONT> antes de Realizar una Consulta...</HTML>");
            txtCodCli.requestFocus();
        }
        else
        {
            if(INTCODEMP == INTCODEMPGRP)
            {
                //System.out.println("---ENTRO POR CONCLI EN GRUPO---");
                if (!cargarDetReg_GRP()) 
                {
                    ///lblMsgSis.setText("Listo");
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
            }
            else
            {
                //System.out.println("---ENTRO POR CONCLI EN EMPRESA---");
                if (!cargarDetReg()) 
                {
                    ///lblMsgSis.setText("Listo");
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
            }
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

    /**
     * Trabaja con Fecha de Corte
     * 
     * @param evt 
     */
    private void optFecCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFecCorActionPerformed
        optRanFec.setSelected(false);
        objSelFec.setCheckBoxChecked(false);
        objSelFec.setEnabled(false);
        objSelFec.setVisible(false);
        
        txtFecCor.setEnabled(true);
        txtFecCor.setVisible(true);
        lblNomfec.setVisible(true);
    }//GEN-LAST:event_optFecCorActionPerformed

    /**
     * Trabaja con Rango de Fecha
     * @param evt 
     */
    private void optRanFecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optRanFecActionPerformed
        optFecCor.setSelected(false);
        txtFecCor.setText("");
        txtFecCor.setEnabled(false);
        txtFecCor.setVisible(false);
        lblNomfec.setVisible(false);
        
        objSelFec.setVisible(true);
        objSelFec.setEnabled(true);
        objSelFec.setCheckBoxChecked(true);
    }//GEN-LAST:event_optRanFecActionPerformed

    private void optFecActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFecActActionPerformed
        optFecCor.setSelected(false);
        txtFecCor.setText("");
        txtFecCor.setEnabled(false);
        txtFecCor.setVisible(false);
        lblNomfec.setVisible(false);
        
        objSelFec.setVisible(true);
        objSelFec.setEnabled(true);
        objSelFec.setCheckBoxChecked(false);
    }//GEN-LAST:event_optFecActActionPerformed

    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup btnGrpFec;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDoc;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butVisPre;
    private javax.swing.JCheckBox chkMosConRes;
    private javax.swing.JCheckBox chkMosCre;
    private javax.swing.JCheckBox chkMosDeb;
    private javax.swing.JCheckBox chkMosDocVen;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JCheckBox chkMosSinRes;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFecAct;
    private javax.swing.JRadioButton optFecCor;
    private javax.swing.JRadioButton optRanFec;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panSubDet;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JScrollPane spnVen;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTable tblTotVen;
    private javax.swing.JTextArea txaObs;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtIde;
    private javax.swing.JTextField txtNomDoc;
    private javax.swing.JTextField txtTipDoc;
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
        
    private void mostrarMsgInfReg(String strMsg) {
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

    //estos tres metodos me sirven para calcular los dias  que han transcurrido de una fecha a la actual
    /**
     * Esta función permite calcular el numero de dias transcurridos entre 2 fechas.
     * @param intAniIni,intMesIni,intDiaIni,intAniFin,intMesFin,intDiaFin recibe 6 enteros que representan el anio inicial y final, mes inicial y final, dia inicial y final
     * @return intNumDia: Retorna el numero de dias. 
     */
   private int intCalDiaTra (int intAniIni, int intMesIni,int intDiaIni,int intAniFin, int intMesFin,int intDiaFin)
   {
        int intNumDia=-1;
        int intNumAux=0;
        //CALCULA LOS DIAS DE LOS AÑOS QUE ESTAN ENTRE LAS FECHAS INICIAL Y FINAL
        for(int j=intAniIni+1;j<intAniFin;j++)
        {
            if(blnSiBis(j))
                intNumAux+=366;//SI ES BISIESTO EL AÑO EN CUESTION
            else
                intNumAux+=365;
        }

        //1. Cuando el año ini y fin son igules
        if(intAniIni==intAniFin)
            intNumDia=intDiaTran(intAniFin,intMesFin,intDiaFin) - intDiaTran(intAniIni,intMesIni,intDiaIni);
        else
        {
              //2.1 si no es bisiesto calculo el numero de dias transcurridos 
              //en el año inicial hasta la fecha ini y eso le resto de los dias de ese año 
              //para calcular los dias que faltan de ese año
              //2.2 sumo el numero de dias de los años entre las fechas ini y fin
              //2.3 finalmente sumo los dias que han transcurrido en el año de la fecha fin hasta esa fecha
            if(intAniIni>intAniFin)
                intNumDia= intDiaTran(intAniIni,intMesIni,intDiaIni) - intDiaTran(intAniFin,intMesFin,intDiaFin); ///funcion provisional///
            else{
                if (blnSiBis(intAniIni))
                    ///System.out.println("---AÑO BISIESTO--- ");
                    intNumDia=(366-intDiaTran(intAniIni,intMesIni,intDiaIni)) + intNumAux + intDiaTran(intAniFin,intMesFin,intDiaFin); ///funcion original///
                else
                    intNumDia=(365-intDiaTran(intAniIni,intMesIni,intDiaIni)) + intNumAux + intDiaTran(intAniFin,intMesFin,intDiaFin);
            }
        }
        return intNumDia;
        
    }
   
   /**
     * Esta función permite verificar si un anio es bisiesto o no
     *@param intAni recibe 1 entero que representan el anio que queremos verificar 
     *@return true: Booleano que indica si es bisiesto
     * <Br> false: Si no es bisiesto
     */
    private boolean blnSiBis(int intAni)
    {
        int intMod1,intMod2,intMod3;
        intMod1=intAni%4;
        intMod2=intAni%100;
        intMod3=intAni%400;
        if(intMod1==0)
        {
            if(intMod2==0)
            {
                if(intMod3==0)
                    return true;
                else
                    return false;
            }
            else
                return true;
        }
        else
            return false;
    }
    
    /**
     * Esta función calcula el numero de dias que han transcurrido en un año hasta la fecha dada
     *@param anio,mes,dia recibe 3 enteros que representan el anio, el mes y el dia  respectivamente 
     *@return numDia: entero con el numero de dias que han transcurrido en ese año hasta ese dia de ese mes
     */
    private int intDiaTran(int intAni,int intMes, int intDia)
    {
        int intNumDia=0,i;
        for(i=1;i<intMes;i++)
        {
            switch (i)
            {
                case 1:
                    intNumDia+=31;
                    break;
                case 2:
                    if(blnSiBis(intAni))
                        intNumDia+=29;
                    else
                        intNumDia+=28;
                    break;
                case 3:
                    intNumDia+=31;
                    break;
                case 4:
                    intNumDia+=30;
                    break;
                case 5:
                    intNumDia+=31;
                    break;
                case 6:
                    intNumDia+=30;
                    break;
                case 7:
                    intNumDia+=31;
                    break;
                case 8:
                    intNumDia+=31;
                    break;
                case 9:
                    intNumDia+=30;
                    break;
                case 10:
                    intNumDia+=31;
                    break;
                case 11:
                    intNumDia+=30;
                    break;
            }
        }
        return (intNumDia+=intDia);
    }

    private void hideColumns(int intCol)
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblDat.getColumnModel().getColumn(intCol).setResizable(false);         
    }
    
    /**
     * MODIFICADO EFLORESA 2012-06-20
     * SOLICITADO POR LYUMISEBA: PODER REDIMENSIONAR LAS COLUMNAS DEL REPORTE.
     */
    private void showColumns(int intCol, int tamCol)
    {
        tblDat.getColumnModel().getColumn(intCol).setWidth(tamCol);
        //tblDat.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        //tblDat.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        //tblDat.getColumnModel().getColumn(intCol).setResizable(true);         
    }
    
    private String FilCorte()
    {
        String fecHasta="" ;
        if(optFecCor.isSelected()){
            if(txtFecCor.getText().length()>0){
                fecHasta = objUti.formatearFecha(txtFecCor.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                if(fecHasta.equals("[ERROR]")) {
                        fecHasta=""; 
                        strAnd=" AND (a2.mo_pag+a2.nd_abo)!=0 ";
                }
                else{ 
                    fecHasta=" AND  a1.fe_doc <= '"+fecHasta+"'";
                    strAnd="AND (a2.mo_pag + CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END) <> 0";
                }
            }
        }
        else{
            fecHasta=""; 
            strAnd=" AND (a2.mo_pag+a2.nd_abo)!=0 ";
        }
        
        return fecHasta;
    }

    /*
     * 2012-02-08 EFLORESA
     * Se agrega conversion explicita de numero a caracter con la funcion to_char
     * por motivo que la nueva version de la base no soporta conversiones implicitas. 
     */
    private String FilSql() 
    {
        String strAux = "";
        String strFecSis, strFecIni, strFecsisFor, strFecVen;
        java.util.Date datFecSer, datFecVen, datFecAux;
        boolean blnRes=true;        
        int intCodEmp, intCodEmpGrp;
                
        datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
        intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
        
        if (datFecSer==null)
            blnRes=false;
        
        datFecAux=datFecSer;
                
        strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
        strFecsisFor=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
        //Obtener la condición

        if(intCodEmpGrp==intCodEmp)
        {
                //Condicion para filtro por cliente.
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a4.tx_ide = '" + txtIde.getText() + "'";
        }
        else
        {
                //Condicion para filtro por cliente
                if (txtCodCli.getText().length()>0)
                    //strAux+=" AND a1.co_cli=" + txtCodCli.getText();     
                    strAux+=" AND trim(to_char(a1.co_cli, '999999999'))='" + txtCodCli.getText() + "' ";                     
        }
        
        //Condicion para filtro por tipo de documento
        if (txtCodTipDoc.getText().length()>0)
            strAux+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText();
        
        //Filtro para fechas/
        String fecHasta="";
        if(optFecCor.isSelected()){
            if(txtFecCor.getText().length()>0){
                fecHasta = objUti.formatearFecha(txtFecCor.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                if(fecHasta.equals("[ERROR]")) {
                        fecHasta=""; 

                }
                else{ 
                    fecHasta=" AND  x2.fe_doc <= '"+fecHasta+"'";

                }
            }
        } 
        if(optRanFec.isSelected()){
            if (objSelFec.isCheckBoxChecked())
            {
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
            }
        }

        //FILTRO PARA MOSTRAR CREDITOS Y DEBITOS
        if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
        {
            //System.out.println("ENTRO POR FILTRO DE CREDITOS Y DEBITOS ");
            if (chkMosCre.isSelected())
                strAux+=" AND a3.tx_natDoc='I'";
            else
                strAux+=" AND a3.tx_natDoc='E'";
        }
        
        //FILTRO PARA DOCUMENTOS VENCIDOS
        if (chkMosDocVen.isSelected())
        {
            //System.out.println("ENTRO POR FILTRO DE DOCUMENTOS VENCIDOS ");
            strFecVen=objUti.formatearFecha(datFecAux, "yyyy-MM-dd");
            strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
        }
        //FILTRO PARA MOSTRAR RETENCIONES
        if (!chkMosRet.isSelected())
        {
            //System.out.println("ENTRO POR FILTRO DE MOSTRAR RETENCIONES");
            strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
        }

        //Condicion para Tipos de Documentos
        if(!((chkMosConRes.isSelected()) && (chkMosSinRes.isSelected())))
        {                                        
            if((chkMosConRes.isSelected()) == true)   ///este es el caso de que el chk CON RESPALDO este activo///
            {                        
                //System.out.println("ENTRO X DOC CON RESPALDOS");
                strAux+= " AND a2.nd_monchq > 0 ";
            }
            if((chkMosSinRes.isSelected()) == true)   /// este es el caso de que el chk SIN RESPALDO este activo///
            {
                //System.out.println("ENTRO X DOC SIN RESPALDOS");
                strAux+= " AND a2.nd_monchq IS NULL ";
            }
        }
        else
        {
            //System.out.println("---ENTRO X Q' LOS 2 CHQ CON Y SIN RESPALDOS ESTAN ACTIVOS---");
            strAux+= " ";
        }
        return strAux;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i, intCodEmpGrp;
        
        int intNumDia, intCodMenu;
        String strFecSis, strFecIni, strFecsisFor, strFecVen;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        String fecHasta="" ;
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            intCodMenu=objParSis.getCodigoMenu();
            intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
                strFecsisFor=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
             
                //Condicion para Tipos de Documentos          
                if(!((chkMosConRes.isSelected()) && (chkMosSinRes.isSelected())))
                {                                        
                    if((chkMosConRes.isSelected()) == true)   ///este es el caso de que el chk CON RESPALDO este activo///
                    {                        
                        showColumns(INT_TBL_DAT_NOM_BAN, 60);//17
                        showColumns(INT_TBL_DAT_NUM_CTA, 80);//18
                        showColumns(INT_TBL_DAT_NUM_CHQ, 50);//19
                        showColumns(INT_TBL_DAT_FEC_REC_CHQ, 60);//20
                        showColumns(INT_TBL_DAT_FEC_VEN_CHQ, 60);//21
                        showColumns(INT_TBL_DAT_VAL_CHQ, 80);//22
                    }
                    if((chkMosSinRes.isSelected()) == true)   /// este es el caso de que el chk SIN RESPALDO este activo///
                    {
                        hideColumns(INT_TBL_DAT_NOM_BAN);//17
                        hideColumns(INT_TBL_DAT_NUM_CTA);//18
                        hideColumns(INT_TBL_DAT_NUM_CHQ);//19
                        hideColumns(INT_TBL_DAT_FEC_REC_CHQ);//20
                        hideColumns(INT_TBL_DAT_FEC_VEN_CHQ);//21
                        hideColumns(INT_TBL_DAT_VAL_CHQ);//22
                    }
                }
                else
                {                    
                    showColumns(INT_TBL_DAT_NOM_BAN, 60);//17
                    showColumns(INT_TBL_DAT_NUM_CTA, 80);//18
                    showColumns(INT_TBL_DAT_NUM_CHQ, 50);//19
                    showColumns(INT_TBL_DAT_FEC_REC_CHQ, 60);//20
                    showColumns(INT_TBL_DAT_FEC_VEN_CHQ, 60);//21
                    showColumns(INT_TBL_DAT_VAL_CHQ, 80);//22
                }
                
                if(intCodEmpGrp==intCodEmp) {
                    showColumns(INT_TBL_DAT_COD_EMP, 30);//1
                }
                else {
                    hideColumns(INT_TBL_DAT_COD_EMP);//1
                }
                    
                String strFechaCabMovInvCorte="";
                
                if(optFecCor.isSelected()){
                    if(txtFecCor.getText().length()>0){
                        fecHasta = objUti.formatearFecha(txtFecCor.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                        if(fecHasta.equals("[ERROR]")) {
                                fecHasta=""; 
                        }
                        else{ 
                            strFechaCabMovInvCorte=" AND  a1.fe_doc <= '"+fecHasta+"'";
                            fecHasta=" AND  x2.fe_doc <= '"+fecHasta+"'";
                        }
                    }
                }
                
                //Obtener el número total de registros.
                strSQL="";
                strSQL+=" SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                /* JM */
                strSQL+=" LEFT JOIN ( ";
                strSQL+="       SELECT x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  , ";
                strSQL+="              sum(CASE WHEN x1.nd_abo IS NULL THEN 0 ELSE x1.nd_abo END) as sumabodet ";
                strSQL+="       FROM tbm_detpag as x1 ";
                strSQL+="       INNER JOIN tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and ";
                strSQL+="                                       x1.co_doc=x2.co_doc)  ";
                strSQL+="       WHERE x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I') ";
                strSQL+="             " + fecHasta;
                strSQL+="       GROUP BY x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag   ";
                strSQL+=" ) as x1 ON  (a2.co_emp=x1.co_emp and a2.co_loc=x1.co_locpag and a2.co_tipdoc=x1.co_tipdocpag and ";
                strSQL+="                a2.co_doc=x1.co_docpag  and a2.co_reg=x1.co_regpag )";
                strSQL+=" WHERE a1.st_reg NOT IN ('E','I')  AND a2.st_reg IN ('A','C') ";      
                strSQL+=strFechaCabMovInvCorte;
                 if(optFecCor.isSelected()){
                    strSQL+=" AND (a2.mo_pag+CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END)<>0 ";
                }else{
                    strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0   "; ///AND a1.co_tipdoc IN (1,7,51)
                }
                 
                /* JM */
                //strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";    JM              
                strSQL+="  AND a3.ne_mod IN (1,3)"; ////AND a1.co_tipdoc IN (1,7,51)
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                //AND (a2.mo_pag+a2.nd_abo)<>0 JM
                
//                //Rose: Valida si el usuario tiene acceso a locales.
//                if (objParSis.getCodigoUsuario() != 1) 
//                {
//                    if ((objLocPrgUsr.validaLocUsr())) 
//                    {
//                        strSQL += " AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(1) + ")";
//                    } 
//                    else 
//                    {
//                        strSQL += " AND a1.co_loc not in (" + objLocPrgUsr.cargarLoc(1) + ")";
//                    }
//                }
                
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())))
                {
                    strSQL+=" AND a1.co_loc=" + intCodLoc;  ///POR AHORA NO SE PROCESA POR LOCAL///
                }
                
                //Condicion para filtro por Menu para saber si es cliente
                if (intCodMenu == 325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=FilSql();                
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp AS COD_EMP, a1.co_loc AS COD_LOC, a1.co_tipdoc AS COD_TIP_DOC, a3.tx_descor AS NOM_COR, a3.tx_deslar AS NOM_LAR, a1.co_doc AS COD_DOC, ";
                strSQL+=" a2.co_reg AS COD_REG, a1.ne_numDoc AS NUM_DOC, a1.fe_doc AS FEC_DOC, a2.ne_diacre AS DIA_CRE, a2.fe_ven AS FEC_VEN, a2.nd_porret AS POR_RET, ";
                strSQL+=" (a2.mo_pag) AS VAL_DOC,  a2.nd_abo AS VAL_ABO, (a2.mo_pag+a2.nd_abo) AS VAL_PEN, ";
                strSQL+=" a2.co_banChq AS COD_BAN, a5.tx_desLar AS NOM_BAN, a2.tx_numCtaChq AS NUM_CTA, a2.tx_numChq AS NUM_CHQ, ";
                strSQL+=" a2.fe_recChq AS FEC_REC_CHQ, a2.fe_venChq AS FEC_VEN_CHQ, a2.nd_monChq AS VAL_CHQ,";
                strSQL+=" (a2.mo_pag+CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END) AS VAL_PEN_JOTA,  ";
                strSQL+=" CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END as VAL_ABO_JOTA ";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" LEFT JOIN ( ";
                strSQL+="       SELECT x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  , ";
                strSQL+="              sum(CASE WHEN x1.nd_abo IS NULL THEN 0 ELSE x1.nd_abo END) as sumabodet ";
                strSQL+="       FROM tbm_detpag as x1 ";
                strSQL+="       INNER JOIN tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and ";
                strSQL+="                                       x1.co_doc=x2.co_doc)  ";
                strSQL+="       WHERE x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I') ";
                strSQL+="             " + fecHasta;
                strSQL+="       GROUP BY x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag   ";
                strSQL+=" ) as x1 ON  (a2.co_emp=x1.co_emp and a2.co_loc=x1.co_locpag and a2.co_tipdoc=x1.co_tipdocpag and ";
                strSQL+="                a2.co_doc=x1.co_docpag  and a2.co_reg=x1.co_regpag )";
                strSQL+=" WHERE a1.st_reg NOT IN ('E','I')  AND a2.st_reg IN ('A','C') ";      
                strSQL+=strFechaCabMovInvCorte;
                 
                if(optFecCor.isSelected()){
                    strSQL+=" AND (a2.mo_pag+CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END)<>0 ";
                }else{
                    strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0   "; ///AND a1.co_tipdoc IN (1,7,51)
                }
                
                strSQL+="  AND a3.ne_mod IN (1,3)   ";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                             
//                //Rose: Valida si el usuario tiene acceso a locales.
//                if (objParSis.getCodigoUsuario() != 1) 
//                {
//                    if ((objLocPrgUsr.validaLocUsr())) 
//                    {
//                        strSQL += " AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(1) + ")";
//                    } 
//                    else 
//                    {
//                        strSQL += " AND a1.co_loc not in (" + objLocPrgUsr.cargarLoc(1) + ")";
//                    }
//                }
                
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())))
                {
                    strSQL+=" AND a1.co_loc=" + intCodLoc;  ///POR AHORA NO SE PROCESA POR LOCAL///
                }
                
                if (intCodMenu==325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=FilSql();
                strSQL+=" ORDER BY a1.co_tipdoc, a1.fe_doc, a1.ne_numdoc, a2.co_reg ";
            
                System.out.println("PASO1.- Query funcion <<cargarDetReg()>> de registros Detallado: "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                i=0;
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");//0
                    vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("COD_EMP"));//1
                    vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("COD_LOC"));//2
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("COD_TIP_DOC"));//3
                    vecReg.add(INT_TBL_DAT_TIP_DOC_COR,rst.getString("NOM_COR"));//4
                    vecReg.add(INT_TBL_DAT_TIP_DOC_LAR,rst.getString("NOM_LAR"));//5
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));//6
                    vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("COD_REG"));//7
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));//8
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("FEC_DOC"));//9
                    vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("DIA_CRE"));//10
                    vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("FEC_VEN"));//11
                    vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("POR_RET"));//12
                    vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("VAL_DOC"));//13
                    if(optFecCor.isSelected()){
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("VAL_ABO_JOTA"));//14
                    }else{
                        vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("VAL_ABO"));//14
                    }

                    if(optFecCor.isSelected()){
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("VAL_PEN_JOTA"));//15
                    }else{
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("VAL_PEN"));//15
                    }

                    vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("COD_BAN"));//16
                    vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("NOM_BAN"));//17
                    vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("NUM_CTA"));//18
                    vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("NUM_CHQ"));//19
                    vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("FEC_REC_CHQ"));//20
                    vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("FEC_VEN_CHQ"));//21
                    vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("VAL_CHQ"));//22
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
                //Calcular totales.
                objTblTot.calcularTotales();
               
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros. ");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");

                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                cargarDetRegValVen();
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegValVen()
    {
        int intCodEmp, intCodLoc, intNumTotReg, i, intCodMenu;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecsisFor, strFecVen,fecHasta="";
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodLoc=objParSis.getCodigoLocal();//obtiene el codigo del local que selecciono el usuario al ingresar al sistema
            intCodMenu=objParSis.getCodigoMenu();//obtiene el codigo del menu del programa que esta activo///
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");                
                strFecsisFor=objUti.formatearFecha(datFecAux, "yyyy/MM/dd");
                
                //Obtener la condición.
                strAux="";
                
                //Condicion para filtro por cliente/////
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a1.co_cli=" + txtCodCli.getText();
                
                //Condicion para filtro por tipo de documento
                if (txtCodTipDoc.getText().length()>0)
                    strAux+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText();
                
                /* JoseMario 24/Feb/2017 */
                String strFechaCabMovInvCorte="";
                if(optFecCor.isSelected()){
                    if(txtFecCor.getText().length()>0){
                        fecHasta = objUti.formatearFecha(txtFecCor.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                        if(fecHasta.equals("[ERROR]")) {
                                fecHasta=""; 
                        }
                        else{ 
                            strFechaCabMovInvCorte=" AND  a1.fe_doc <= '"+fecHasta+"'";
                            fecHasta=" AND  x2.fe_doc <= '"+fecHasta+"'";
                        }
                    }
                }
                
                //Filtro para fechas
                if(optRanFec.isSelected()){
                    if (objSelFec.isCheckBoxChecked())
                    {
                        switch (objSelFec.getTipoSeleccion())
                        {
                            case 0: //Búsqueda por rangos
                                strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 1: //Fechas menores o iguales que "Hasta".
                                strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 2: //Fechas mayores o iguales que "Desde".
                                strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 3: //Todo.
                                break;
                        }
                    }
                }
                
                //FILTRO PARA MOSTRAR CREDITOS Y DEBITOS
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                //FILTRO PARA DOCUMENTOS VENCIDOS
                if (chkMosDocVen.isSelected())
                {
                    strFecVen=objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos());
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                //FILTRO PARA MOSTRAR RETENCIONES
                if (!chkMosRet.isSelected())
                {
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                //Obtener el número total de registros.
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" LEFT JOIN ( ";
                strSQL+="       SELECT x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  , ";
                strSQL+="              sum(CASE WHEN x1.nd_abo IS NULL THEN 0 ELSE x1.nd_abo END) as sumabodet ";
                strSQL+="       FROM tbm_detpag as x1 ";
                strSQL+="       INNER JOIN tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and ";
                strSQL+="                                       x1.co_doc=x2.co_doc)  ";
                strSQL+="       WHERE x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I') ";
                strSQL+="             " + fecHasta;
                strSQL+="       GROUP BY x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag   ";
                strSQL+=" ) as x1 ON  (a2.co_emp=x1.co_emp and a2.co_loc=x1.co_locpag and a2.co_tipdoc=x1.co_tipdocpag and ";
                strSQL+="                a2.co_doc=x1.co_docpag  and a2.co_reg=x1.co_regpag )";
                strSQL+=" WHERE a1.st_reg NOT IN ('E','I') ";
                strSQL+=strFechaCabMovInvCorte;
                strSQL+=" AND a2.st_reg IN ('A','C') AND a3.ne_mod IN (1,3)";                
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0   ";
                if(optFecCor.isSelected()){
                    strSQL+=" AND (a2.mo_pag+CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END)<>0 ";
                }else{
                    strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0   "; ///AND a1.co_tipdoc IN (1,7,51)
                }
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                
//                //Rose: Valida si el usuario tiene acceso a locales.
//                if (objParSis.getCodigoUsuario() != 1) 
//                {
//                    if ((objLocPrgUsr.validaLocUsr())) 
//                    {
//                        strSQL += " AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(1) + ")";
//                    } 
//                    else 
//                    {
//                        strSQL += " AND a1.co_loc not in (" + objLocPrgUsr.cargarLoc(1) + ")";
//                    }
//                }
                
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())))
                {
                    strSQL+=" AND a1.co_loc=" + intCodLoc;  ///POR AHORA NO SE PROCESA POR LOCAL///
                }
                
                if (intCodMenu==325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=strAux;
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar";
                strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a1.fe_doc, a2.nd_monChq";
                strSQL+=" ,(a2.mo_pag+CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END) AS VAL_PEN_JOTA,  ";
                strSQL+=" CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END as VAL_ABO_JOTA ";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                /* JM */
                strSQL+=" LEFT JOIN ( ";
                strSQL+="       SELECT x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  , ";
                strSQL+="              sum(CASE WHEN x1.nd_abo IS NULL THEN 0 ELSE x1.nd_abo END) as sumabodet ";
                strSQL+="       FROM tbm_detpag as x1 ";
                strSQL+="       INNER JOIN tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and ";
                strSQL+="                                       x1.co_doc=x2.co_doc)  ";
                strSQL+="       WHERE x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I') ";
                strSQL+="             " + fecHasta;
                strSQL+="       GROUP BY x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag   ";
                strSQL+=" ) as x1 ON  (a2.co_emp=x1.co_emp and a2.co_loc=x1.co_locpag and a2.co_tipdoc=x1.co_tipdocpag and ";
                strSQL+="                a2.co_doc=x1.co_docpag  and a2.co_reg=x1.co_regpag )";
                /* JM */
                strSQL+=" WHERE a1.st_reg NOT IN ('E','I') ";
                strSQL+=strFechaCabMovInvCorte;
                strSQL+=" AND a2.st_reg IN ('A','C') AND a3.ne_mod IN (1,3)";                
                if(optFecCor.isSelected()){
                    strSQL+=" AND (a2.mo_pag+CASE WHEN x1.sumabodet IS NULL THEN 0 ELSE x1.sumabodet END)<>0 ";
                }else{
                    strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0   "; ///AND a1.co_tipdoc IN (1,7,51)
                }
                
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                
//                //Rose: Valida si el usuario tiene acceso a locales.
//                if (objParSis.getCodigoUsuario() != 1) 
//                {
//                    if ((objLocPrgUsr.validaLocUsr()))  {
//                        strSQL += " AND a1.co_loc in (" + objLocPrgUsr.cargarLocUsr(1) + ")";
//                    } 
//                    else {
//                        strSQL += " AND a1.co_loc not in (" + objLocPrgUsr.cargarLoc(1) + ")";
//                    }
//                }
                
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())))
                {
                    strSQL+=" AND a1.co_loc=" + intCodLoc;  ///POR AHORA NO SE PROCESA POR LOCAL///
                }
                
                if (intCodMenu==325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=strAux;
                ////strSQL+=" ORDER BY a2.co_emp, a4.tx_nom, a1.fe_doc";                
                strSQL+=" ORDER BY a1.co_tipdoc, a1.fe_doc, a1.ne_numdoc, a2.co_reg "; 
                System.out.println("PASO 2.- Query de la funcion <<cargarDetRegValVen()>> para registros de Consultas: "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatVen.clear();
                i=0;
                
                double ValDoc = 0, ValVen = 0, Val30D = 0, Val60D = 0, Val90D = 0, ValMas = 0, SumTot1 = 0, SumTot2 = 0;
                
                while (rst.next())
                {
                         
                    datFecVen=rst.getDate("fe_ven");
                          
                    ValDoc  = ValDoc  + rst.getDouble("mo_pag");           

                    try
                    {                                                        
                        int intdiaCre=Integer.parseInt( rst.getString("ne_diaCre"));
                        
                       //almaceno la fecha del sistema en un arreglo de enteros
                        intFecFin=dtpDat.getFecha(strFecSis);

                        //tomo la fecha de la base y la almaceno en un arreglo como entero
                        strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
                        intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);

                        ////calcula los dias transcurridos entre 2 fechas
                        intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);

                        //ubico el valor vencido en el rango correspondiente
                        if(intNumDia<=0){
                            if(optFecCor.isSelected()){
                                ValVen = ValVen + rst.getDouble("VAL_PEN_JOTA");          
                            }
                            else{
                                ValVen = ValVen + rst.getDouble("nd_pen");             
                            }
                        }else{
                            if(intNumDia>=0 && intNumDia<=30){
                                if(optFecCor.isSelected()){
                                    Val30D = Val30D + rst.getDouble("VAL_PEN_JOTA");          
                                }
                                else{
                                    Val30D = Val30D + rst.getDouble("nd_pen");            
                                }

                            }else{
                                if(intNumDia>31 && intNumDia<=60){
                                    if(optFecCor.isSelected()){
                                        Val60D = Val60D + rst.getDouble("VAL_PEN_JOTA");          
                                    }
                                    else{
                                        Val60D = Val60D + rst.getDouble("nd_pen");  
                                    }

                                }else{
                                    if(intNumDia>61 && intNumDia<=90){
                                        if(optFecCor.isSelected()){
                                             Val90D = Val90D + rst.getDouble("VAL_PEN_JOTA");          
                                        }
                                        else{
                                            Val90D = Val90D + rst.getDouble("nd_pen");
                                        }
                                    }else{
                                        if(optFecCor.isSelected()){
                                               ValMas = ValMas + rst.getDouble("VAL_PEN_JOTA");          
                                        }
                                        else{
                                             ValMas = ValMas + rst.getDouble("nd_pen");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        objUti.mostrarMsgErr_F1(this, e);
                    }
                    
                }///fin del while///
                
                //**********************************************************************************
                vecRegVen=new Vector();                                   
                vecRegVen.add(INT_TBL_VEN_LIN,"");
                vecRegVen.add(INT_TBL_VEN_DAT,String.valueOf(ValVen));
                vecRegVen.add(INT_TBL_VEN_30D,String.valueOf(Val30D));
                vecRegVen.add(INT_TBL_VEN_60D,String.valueOf(Val60D));
                vecRegVen.add(INT_TBL_VEN_90D,String.valueOf(Val90D));
                vecRegVen.add(INT_TBL_VEN_MAS,String.valueOf(ValMas));

                SumTot1 = Val30D + Val60D + Val90D + ValMas;
                vecRegVen.add(INT_TBL_VEN_TOT_1,String.valueOf(SumTot1));

                SumTot2 = ValVen + SumTot1; 
                vecRegVen.add(INT_TBL_VEN_TOT_2,String.valueOf(SumTot2));

                vecDatVen.add(vecRegVen);     
                //**********************************************************************************
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModVen.setData(vecDatVen);
                tblTotVen.setModel(objTblModVen);
                vecDatVen.clear();
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg_GRP()
    {
        int intCodEmp, intNumTotReg, i, intCodEmpGrp;
        
        int intNumDia, intCodMenu; 
        String strFecSis, strFecIni;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodMenu=objParSis.getCodigoMenu();
            intCodEmpGrp=objParSis.getCodigoEmpresaGrupo();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.
                strAux="";
                
                //Condicion para Tipos de Documentos.             
                if(!((chkMosConRes.isSelected()) && (chkMosSinRes.isSelected())))
                {                                        
                    if((chkMosConRes.isSelected()) == true)   ///este es el caso de que el chk CON RESPALDO este activo///
                    { 
                        showColumns(INT_TBL_DAT_NOM_BAN, 60);//17
                        showColumns(INT_TBL_DAT_NUM_CTA, 80);//18
                        showColumns(INT_TBL_DAT_NUM_CHQ, 50);//19
                        showColumns(INT_TBL_DAT_FEC_REC_CHQ, 60);//20
                        showColumns(INT_TBL_DAT_FEC_VEN_CHQ, 60);//21
                        showColumns(INT_TBL_DAT_VAL_CHQ, 80);//22
                    }
                    if((chkMosSinRes.isSelected()) == true)   /// este es el caso de que el chk SIN RESPALDO este activo///
                    {
                        hideColumns(INT_TBL_DAT_NOM_BAN);//17
                        hideColumns(INT_TBL_DAT_NUM_CTA);//18
                        hideColumns(INT_TBL_DAT_NUM_CHQ);//19
                        hideColumns(INT_TBL_DAT_FEC_REC_CHQ);//20
                        hideColumns(INT_TBL_DAT_FEC_VEN_CHQ);//21
                        hideColumns(INT_TBL_DAT_VAL_CHQ);//22
                    }
                }
                else
                {
                    showColumns(INT_TBL_DAT_NOM_BAN, 60);//17
                    showColumns(INT_TBL_DAT_NUM_CTA, 80);//18
                    showColumns(INT_TBL_DAT_NUM_CHQ, 50);//19
                    showColumns(INT_TBL_DAT_FEC_REC_CHQ, 60);//20
                    showColumns(INT_TBL_DAT_FEC_VEN_CHQ, 60);//21
                    showColumns(INT_TBL_DAT_VAL_CHQ, 80);//22
                }
                
                if(intCodEmpGrp==intCodEmp)  {
                    showColumns(INT_TBL_DAT_COD_EMP, 30);//1
                }
                else {
                    hideColumns(INT_TBL_DAT_COD_EMP);//1
                }
                    
                //Obtener el número total de registros.
                strSQL="";
                strSQL+=" SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0  AND a3.ne_mod IN (1,3)"; ////AND a1.co_tipdoc IN (1,7,51)
                
                //Condicion para filtro por estado de cliente
                if (intCodMenu == 325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=FilSql();
                
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_emp AS COD_EMP, a1.co_loc AS COD_LOC, a1.co_tipdoc AS COD_TIP_DOC, a3.tx_descor AS NOM_COR, a3.tx_deslar AS NOM_LAR, a1.co_doc AS COD_DOC, ";
                strSQL+=" a2.co_reg AS COD_REG, a1.ne_numDoc AS NUM_DOC, a1.fe_doc AS FEC_DOC, a2.ne_diacre AS DIA_CRE, a2.fe_ven AS FEC_VEN, a2.nd_porret AS POR_RET, ";
                strSQL+=" (a2.mo_pag) AS VAL_DOC,  a2.nd_abo AS VAL_ABO, (a2.mo_pag+a2.nd_abo) AS VAL_PEN, ";
                strSQL+=" a2.co_banChq AS COD_BAN, a5.tx_desLar AS NOM_BAN, a2.tx_numCtaChq AS NUM_CTA, a2.tx_numChq AS NUM_CHQ, ";
                strSQL+=" a2.fe_recChq AS FEC_REC_CHQ, a2.fe_venChq AS FEC_VEN_CHQ, a2.nd_monChq AS VAL_CHQ";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') ";                
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod IN (1,3) ";
                
                if (intCodMenu==325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=FilSql();
                strSQL+=" ORDER BY a1.co_tipdoc, a1.fe_doc, a1.ne_numdoc, a2.co_reg "; 
                
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                i=0;
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");//0
                    vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("COD_EMP"));//1
                    vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("COD_LOC"));//2
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("COD_TIP_DOC"));//3
                    vecReg.add(INT_TBL_DAT_TIP_DOC_COR,rst.getString("NOM_COR"));//4
                    vecReg.add(INT_TBL_DAT_TIP_DOC_LAR,rst.getString("NOM_LAR"));//5
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("COD_DOC"));//6
                    vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("COD_REG"));//7
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("NUM_DOC"));//8
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("FEC_DOC"));//9
                    vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("DIA_CRE"));//10
                    vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("FEC_VEN"));//11
                    vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("POR_RET"));//12
                    vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("VAL_DOC"));//13
                    vecReg.add(INT_TBL_DAT_VAL_ABO,rst.getString("VAL_ABO"));//14
                    vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("VAL_PEN"));//15
                    vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("COD_BAN"));//16
                    vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("NOM_BAN"));//17
                    vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("NUM_CTA"));//18
                    vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("NUM_CHQ"));//19
                    vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("FEC_REC_CHQ"));//20
                    vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("FEC_VEN_CHQ"));//21
                    vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("VAL_CHQ"));//22
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
                //Calcular totales.
                objTblTot.calcularTotales();
               
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros. ");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");

                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                cargarDetRegValVen_GRP();
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegValVen_GRP()
    {
        int intCodEmp, intNumTotRegGrp, i, intCodMenu;
        
        int intNumDia; 
        String strFecSis, strFecIni, strFecVen;
        int intFecIni[];
        int intFecFin[];//para calcular los dias entre fechas
        
        double dblSub, dblIva;
        java.util.Date datFecSer, datFecVen, datFecAux;
        
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();//obtiene el codigo de la empresa que selecciono el usuario al ingresar al sistema
            intCodMenu=objParSis.getCodigoMenu();//obtiene el codigo del menu del programa que esta activo///
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                datFecAux=datFecSer;
                strFecSis=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");

                //Obtener la condición.
                strAux="";
                
                //Condicion para filtro por RUC / CED del cliente
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a4.tx_ide = '" + txtIde.getText() + "'";
                
                //Condicion para filtro por tipo de documento
                if (txtCodTipDoc.getText().length()>0)
                    strAux+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText();
                
                //Filtro para fechas
                if(optRanFec.isSelected()){
                    if (objSelFec.isCheckBoxChecked()){
                        switch (objSelFec.getTipoSeleccion()){
                            case 0: //Búsqueda por rangos
                                strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 1: //Fechas menores o iguales que "Hasta".
                                strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 2: //Fechas mayores o iguales que "Desde".
                                strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 3: //Todo.
                                break;
                        }
                    }
                }
                
                //FILTRO PARA MOSTRAR CREDITOS Y DEBITOS
                if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                {
                    if (chkMosCre.isSelected())
                        strAux+=" AND a3.tx_natDoc='I'";
                    else
                        strAux+=" AND a3.tx_natDoc='E'";
                }
                //FILTRO PARA DOCUMENTOS VENCIDOS
                if (chkMosDocVen.isSelected())
                {
                    strFecVen=objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos());
                    strAux+=" AND a2.fe_ven<='" + strFecVen + "'";
                }
                ///FILTRO PARA MOSTRAR RETENCIONES///
                if (!chkMosRet.isSelected())
                {
                    strAux+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                }
                
                //Obtener el número total de registros.
                strSQL="";
                strSQL+=" SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C') AND a3.ne_mod IN (1,3) ";                
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0 ";
                
                if (intCodMenu==325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=strAux;
                intNumTotRegGrp=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotRegGrp==-1)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar";
                strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a1.fe_doc, a2.nd_monChq";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C') AND a3.ne_mod IN (1,3) ";                
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0 "; ///AND a1.co_tipdoc IN (1,7,51)
                
                if (intCodMenu==325) ///reporte de Estado de cuenta co_mnu
                    strSQL+=" AND a4.st_cli='S'";
                else
                    strSQL+=" AND a4.st_prv='S'";
                
                strSQL+=strAux;
                strSQL+=" ORDER BY a1.co_tipdoc, a1.fe_doc, a1.ne_numdoc, a2.co_reg "; 
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDatVen.clear();
                i=0;
                
                double ValDoc = 0, ValVen = 0, Val30D = 0, Val60D = 0, Val90D = 0, ValMas = 0, SumTot1 = 0, SumTot2 = 0;
                
                while (rst.next())
                {
                    datFecVen=rst.getDate("fe_ven");
                    ValDoc  = ValDoc  + rst.getDouble("mo_pag");                                                                                                                                                      
                        
                    try
                    {                                                        
                       int intdiaCre=Integer.parseInt( rst.getString("ne_diaCre"));

                        //almaceno la fecha del sistema en un arreglo de enteros
                        intFecFin=dtpDat.getFecha(strFecSis);

                        //tomo la fecha de la base y la almaceno en un arreglo como entero
                        strFecIni=objUti.formatearFecha(datFecVen,"dd/MM/yyyy");
                        intFecIni=dtpDat.getFecha(strFecIni);//(strFecIni);

                        ////calcula los dias transcurridos entre 2 fechas
                        intNumDia=intCalDiaTra(intFecIni[2], intFecIni[1], intFecIni[0], intFecFin[2], intFecFin[1], intFecFin[0]);

                        //ubico el valor vencido en el rango correspondiente
                        if(intNumDia<=0){
                            ValVen = ValVen + rst.getDouble("nd_pen");                                                                
                        }else{
                            if(intNumDia>=0 && intNumDia<=30){
                                Val30D = Val30D + rst.getDouble("nd_pen");
                            }else{
                                if(intNumDia>31 && intNumDia<=60){
                                    Val60D = Val60D + rst.getDouble("nd_pen");
                                }else{
                                    if(intNumDia>61 && intNumDia<=90){
                                        Val90D = Val90D + rst.getDouble("nd_pen");
                                    }else{
                                        ValMas = ValMas + rst.getDouble("nd_pen");
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        objUti.mostrarMsgErr_F1(this, e);
                    }
                    
                }///fin del while///
                
                
                //**********************************************************************************
                vecRegVen=new Vector();                                   
                vecRegVen.add(INT_TBL_VEN_LIN,"");
                vecRegVen.add(INT_TBL_VEN_DAT,String.valueOf(ValVen));
                vecRegVen.add(INT_TBL_VEN_30D,String.valueOf(Val30D));
                vecRegVen.add(INT_TBL_VEN_60D,String.valueOf(Val60D));
                vecRegVen.add(INT_TBL_VEN_90D,String.valueOf(Val90D));
                vecRegVen.add(INT_TBL_VEN_MAS,String.valueOf(ValMas));

                SumTot1 = Val30D + Val60D + Val90D + ValMas;
                vecRegVen.add(INT_TBL_VEN_TOT_1,String.valueOf(SumTot1));

                SumTot2 = ValVen + SumTot1; 
                vecRegVen.add(INT_TBL_VEN_TOT_2,String.valueOf(SumTot2));

                vecDatVen.add(vecRegVen);        
                        
                //**********************************************************************************
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModVen.setData(vecDatVen);
                tblTotVen.setModel(objTblModVen);
                vecDatVen.clear();
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
    
    protected String rtnNomUsrSis()
    {
	java.sql.Connection conUltRegDoc;
        java.sql.Statement stmUltRegDoc;
        java.sql.ResultSet rstUltRegDoc;
        String strSQL, stRegRep="";
        int intAux=0;
        int codusr= objParSis.getCodigoUsuario();
        try
        {
            conUltRegDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conUltRegDoc.setAutoCommit(false);
            if (conUltRegDoc!=null)
            {                        
                stmUltRegDoc=conUltRegDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+=" SELECT co_usr, tx_usr, tx_pwd, tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" WHERE a1.co_usr= " + codusr;
                strSQL+=" AND a1.st_usrSis='S'";
                strSQL+=" AND a1.st_reg='A'";
                //System.out.println("Query para el mostrar usuarios del sistema es: " + strSQL);
                rstUltRegDoc=stmUltRegDoc.executeQuery(strSQL);

                if (rstUltRegDoc.next())
                {
                    stRegRep = rstUltRegDoc.getString("tx_nom");
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
        return stRegRep;
    }
   
    /**
     * Función que obtiene la primera palabra dentro de una cadena de Caracteres. 
     * @param str
     * @return 
     */
    public String getFirstWord(String str)
    {
        String strRes="";
        for(int i=0; i<str.length();i++)
        {
            if (str.charAt(i) ==' ') {
                strRes=str.substring(0, i);
                break; 
            }
        }
        return strRes;
    }
    
     /**
     * Función que obtiene la dirección del local indicado.
     * @param str
     * @return 
     */
    public String getDirLoc(int codigoEmpresa, int codigoLocal)
    {
        String strRes="";
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL="";
                strSQL+=" SELECT tx_dir FROM tbm_loc";
                strSQL+=" WHERE co_emp="+codigoEmpresa;
                strSQL+=" AND co_loc="+codigoLocal;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    strRes=rst.getString("tx_dir");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
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
        return strRes;
    }
         
    /**
     * Función que obtiene la dirección del local indicado.
     * @param str
     * @return 
     */
    public String getTelLoc(int codigoEmpresa, int codigoLocal)
    {
        String strRes="";
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL="";
                strSQL+=" SELECT tx_tel FROM tbm_loc";
                strSQL+=" WHERE co_emp="+codigoEmpresa;
                strSQL+=" AND co_loc="+codigoLocal;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    strRes=rst.getString("tx_tel");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
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
        return strRes;
    }
    
    /**
     * Función que obtiene la ciudad del local indicado.
     * @param str
     * @return 
     */
    public String getCiuLoc(int codigoEmpresa, int codigoLocal)
    {
        String strRes="";
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.tx_DesLar as tx_nomCiu FROM TBM_LOC as a";
                strSQL+=" INNER JOIN tbm_ciu as a1 on a.co_ciu=a1.co_ciu";
                strSQL+=" WHERE a.co_emp="+codigoEmpresa;
                strSQL+=" AND a.co_loc="+codigoLocal;
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    strRes=rst.getString("tx_nomCiu")+" - Ecuador";
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
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
        return strRes;
    }
    
    /**
     * Función que obtiene la dirección de correo electrónico del Dpto de Cobranzas del local indicado.
     * @param str
     * @return 
     */
    public String getCorEleEmp(int codigoEmpresa)
    {
        String strRes="";
        try 
        {
            switch(codigoEmpresa)
            {
                case 1: //Tuval
                    strRes="cobranzas@tuvalsa.com";
                    break;
                case 2: //Castek
                    strRes="cobranzas@castek.ec";
                    break;
                case 4: //Dimulti
                    strRes="cobranzas@dimulti.com";
                    break;                    
            }
        }    
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strRes;
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
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    ///objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    ///objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    ///objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    ///objTooBar.setEnabledVistaPreliminar(true);
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
     * <LI>0: impresión directa.
     * <LI>1: impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer, strBan="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=-1;

        String CodCli = txtCodCli.getText();
        String NomCli = txtDesLarCli.getText();
        String Observacion = txaObs.getText();
        //String NomUsr = objParSis.getNombreUsuario();
        String NomUsrFun = rtnNomUsrSis();
        String strCodEmp = String.valueOf(intCodEmp);
        String strRutImgLogo="";
        String strNomEmp="", strCiuLoc="", strDirLoc="", strTelLoc="", strCorEle="";
        
        STRBAN="";        
        STRBAN=FilSql();       
        
        strFecCor="";
        strFecCor=FilCorte();       
        
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.show();
            
            //Inicializar los parametros que se van a pasar al reporte.
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.//
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd HH:mm:ss");
                datFecAux=null;
                
                strNomEmp=getFirstWord(objParSis.getNombreEmpresa().toUpperCase()) ;
                
                /* Obtener el local del cual se quiere traer toda la información */
                /* Cuando es un local de Inmaconsa, obtengo los datos del local predeterminado que le corresponde. */
                if(objDatLocInm.cargarLocPre()) {  //Obtiene el local predeterminado de cada sucursal.
                    intCodLoc=objDatLocInm.getLocPre();  
                }
                else {
                    intCodLoc=objParSis.getCodigoLocal();
                }
                
                strCiuLoc=getCiuLoc(intCodEmp, intCodLoc);                
                strDirLoc=getDirLoc(intCodEmp, intCodLoc);
                strTelLoc=getTelLoc(intCodEmp, intCodLoc);
                strCorEle=getCorEleEmp(intCodEmp);
                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 92:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strRutImgLogo=strRutRpt+"LOGO_"+strNomEmp+".jpg";

                                //Inicializar los parametros que se van a pasar al reporte.//
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("RUTA_LOGO", strRutImgLogo);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("CodEmp", Integer.parseInt(strCodEmp));
                                mapPar.put("CodCli", Integer.parseInt(CodCli));
                                mapPar.put("NomCli", ""+NomCli);
                                mapPar.put("FecImp", ""+strFecHorSer);
                                mapPar.put("Obser",  ""+Observacion);
                                mapPar.put("NomUsr", ""+NomUsrFun);
                                mapPar.put("CiuLoc", strCiuLoc);
                                mapPar.put("DirLoc", strDirLoc);
                                mapPar.put("TelLoc", strTelLoc);
                                mapPar.put("CorEle", strCorEle);
                                mapPar.put("Ban", ""+STRBAN);
                                /*JM*/
                                mapPar.put("strFecCor", ""+strFecCor);
                                mapPar.put("strAnd", ""+strAnd);
                                /*JM*/
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                            break;
                            
                            case 261:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strRutImgLogo=strRutRpt+"LOGO_"+strNomEmp+".jpg";

                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapParAux=new java.util.HashMap();
                                mapParAux.put("RUTA_LOGO", strRutImgLogo);
                                mapParAux.put("SUBREPORT_DIR", strRutRpt);
                                mapParAux.put("CodEmp", Integer.parseInt(strCodEmp));
                                mapParAux.put("CodCli", Integer.parseInt(CodCli));
                                mapParAux.put("NomCli", NomCli);
                                mapParAux.put("FecImp", strFecHorSer);
                                mapParAux.put("Obser",  Observacion);
                                mapParAux.put("NomUsr", NomUsrFun);
                                mapParAux.put("CiuLoc", strCiuLoc);
                                mapParAux.put("DirLoc", strDirLoc);
                                mapParAux.put("TelLoc", strTelLoc);
                                mapParAux.put("CorEle", strCorEle);
                                mapParAux.put("Ban", STRBAN);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapParAux, intTipRpt);
                            break;
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
        return blnRes;
    }
    
    
    
}

