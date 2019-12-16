/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 * 
 */
package Ventas.ZafVen22;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07;
import Ventas.ZafVen01.ZafVen01;
import Ventas.ZafVen02.ZafVen02;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author gaby 
 * REPORTE DE SEGUIMIENTO DE CLIENTES: ULTIMA COTIZACION Y ULTIMA FACTURA 
 * 
 */
public class ZafVen22 extends JInternalFrame {
    //Constantes: Columnas del JTable:
    /*static final int INT_TBL_DAT_LIN=0;                        //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    static final int INT_TBL_DAT_COD_CLI=3;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=4;                    //Nombre del cliente.
    static final int INT_TBL_DAT_MON_CRE=5;                    //Monto del credito
    static final int INT_TBL_DAT_BUT_ANX1=6;                   //ANEXO1
    static final int INT_TBL_DAT_FEC_ULT_COT=7;                //Fecha del documento
    static final int INT_TBL_DAT_DIAS_ULT_COT=8;               //Código del documento.
    static final int INT_TBL_DAT_COD_COT=9;                    //Codigo de cotizacion
    static final int INT_TBL_DAT_BUT_ANX2=10;                  //ANEXO2
    static final int INT_TBL_DAT_FEC_ULT_FAC=11;               //Código del registro.
    static final int INT_TBL_DAT_DIAS_ULT_FAC=12;              //Número de documento.
    static final int INT_TBL_DAT_COD_FACT=13;                  //Codigo de factura
    static final int INT_TBL_DAT_BUT_ANX3=14;                  //ANEXO3 */
    
    /*private static final int INT_TBL_DAT_LIN=0;                        //Línea
    private static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    private static final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    private static final int INT_TBL_DAT_COD_CLI=3;                    //Código del cliente.
    private static final int INT_TBL_DAT_NOM_CLI=4;                    //Nombre del cliente.
    private static final int INT_TBL_DAT_MON_CRE=5;                    //Monto del credito
    private static final int INT_TBL_DAT_NOM_VEN=6;                    //CODIGO DE VENDEDOR. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_BUT_ANX1=7;                   //ANEXO1
    private static final int INT_TBL_DAT_FEC_ULT_COT=8;                //Fecha del documento
    private static final int INT_TBL_DAT_DIAS_ULT_COT=9;               //Código del documento.
    private static final int INT_TBL_DAT_COD_COT=10;                   //Codigo de cotizacion
    private static final int INT_TBL_DAT_BUT_ANX2=11;                  //ANEXO2
    private static final int INT_TBL_DAT_FEC_ULT_FAC=12;               //Código del registro.
    private static final int INT_TBL_DAT_DIAS_ULT_FAC=13;              //Número de documento.
    private static final int INT_TBL_DAT_COD_FACT=14;                  //Codigo de factura
    private static final int INT_TBL_DAT_BUT_ANX3=15;                  //ANEXO3*/
    
    /*private static final int INT_TBL_DAT_LIN=0;                        //Línea
    private static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    private static final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    private static final int INT_TBL_DAT_COD_CLI=3;                    //Código del cliente.
    private static final int INT_TBL_DAT_NOM_CLI=4;                    //Nombre del cliente.
    private static final int INT_TBL_DAT_MON_CRE=5;                    //Monto del credito
    private static final int INT_TBL_DAT_NOM_VEN=6;                    //VENDEDOR. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_DIR_CLI=7;                    //DIRECCION DEL CLIENTE. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_TEL_CLI=8;                    //TELEFONO. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_CIU_CLI=9;                    //CIUDAD. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_BUT_ANX1=10;                   //ANEXO1
    private static final int INT_TBL_DAT_FEC_ULT_COT=11;                //Fecha del documento
    private static final int INT_TBL_DAT_DIAS_ULT_COT=12;               //Código del documento.
    private static final int INT_TBL_DAT_COD_COT=13;                   //Codigo de cotizacion
    private static final int INT_TBL_DAT_BUT_ANX2=14;                  //ANEXO2
    private static final int INT_TBL_DAT_FEC_ULT_FAC=15;               //Código del registro.
    private static final int INT_TBL_DAT_DIAS_ULT_FAC=16;              //Número de documento.
    private static final int INT_TBL_DAT_COD_FACT=17;                  //Codigo de factura
    private static final int INT_TBL_DAT_BUT_ANX3=18;                  //ANEXO3*/
    
    private static final int INT_TBL_DAT_LIN=0;                        //Línea
    private static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    private static final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    private static final int INT_TBL_DAT_COD_CLI=3;                    //Código del cliente.
    private static final int INT_TBL_DAT_NOM_CLI=4;                    //Nombre del cliente.
    private static final int INT_TBL_DAT_MON_CRE=5;                    //Monto del credito
    private static final int INT_TBL_DAT_NOM_VEN=6;                    //VENDEDOR. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_DIR_CLI=7;                    //DIRECCION DEL CLIENTE. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_TEL_CLI=8;                    //TELEFONO. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_CIU_CLI=9;                    //CIUDAD. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_COR_ELE=10;                    //DIR. CORREO ELECTRONICO. ap LENIN YUMISEBA 
    private static final int INT_TBL_DAT_BUT_ANX1=11;                   //ANEXO1
    private static final int INT_TBL_DAT_FEC_ULT_COT=12;                //Fecha del documento
    private static final int INT_TBL_DAT_DIAS_ULT_COT=13;               //Código del documento.
    private static final int INT_TBL_DAT_COD_COT=14;                   //Codigo de cotizacion
    private static final int INT_TBL_DAT_BUT_ANX2=15;                  //ANEXO2
    private static final int INT_TBL_DAT_FEC_ULT_FAC=16;               //Código del registro.
    private static final int INT_TBL_DAT_DIAS_ULT_FAC=17;              //Número de documento.
    private static final int INT_TBL_DAT_COD_FACT=18;                  //Codigo de factura
    private static final int INT_TBL_DAT_BUT_ANX3=19;                  //ANEXO3
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafVenCon vcoLoc;
    private ZafTblMod objTblMod;
    private ZafTblHeaColGrp objTblHeaColGrpAmeSur;
    private ZafTblHeaGrp objTblHeaGrp;
    private ZafTblBus objTblBus;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblOrd objTblOrd;
    private ZafMouMotAda objMouMotAda;
    private ZafThreadGUI objThrGUI;
    private ZafVenCon vcoCli;
    private ZafTblCelRenLbl objTblCelRenLbl;
    
    private Vector vecCab, vecDat;
    private Vector vecReg;
    
    private String strSql;
    private String strCodLoc, strNomLoc;
    //José Marín  20/Ene/2014
    private String strVersion=" v1.9";
    //José Marín M. 1.8  20/Ene/2014
    private String strTit="Mensaje del Sistema Zafiro";
    private String strMsg="";
    private String strCodCli;
    private String strDesLarCli;
    
    private int intFilSel, intColSel;
    
    private Connection conn;
    private Statement stm;
    private ResultSet rst;
    
    private ZafPerUsr objPerUsr;
    
    /**
     * Creates new form ZafVen22
     */
    public ZafVen22(ZafParSis objZafParsis) {
        try {
            //Inicializar objetos.
            this.objParSis=(ZafParSis) objZafParsis.clone(); 
            //Inicializar objetos.
            objUti=new ZafUtil();
            initComponents(); 
            
            this.setTitle(objParSis.getNombreMenu() + strVersion );
            lblTit.setText(objParSis.getNombreMenu());
            objPerUsr=new ZafPerUsr(objParSis);
            txtCodEmpRel.setVisible(false);
        }catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR] ");
        }     
    }       
    
    /*
     * Configurar el formulario. 
     */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_MON_CRE,"Mon.Cré.");
            vecCab.add(INT_TBL_DAT_NOM_VEN, "Vendedor");
            vecCab.add(INT_TBL_DAT_DIR_CLI, "Dirección");
            vecCab.add(INT_TBL_DAT_TEL_CLI, "Teléfono");
            vecCab.add(INT_TBL_DAT_CIU_CLI, "Ciudad");
            vecCab.add(INT_TBL_DAT_COR_ELE, "e-mail");
            vecCab.add(INT_TBL_DAT_BUT_ANX1,"");
            vecCab.add(INT_TBL_DAT_FEC_ULT_COT,"Fec.Últ.Cot.");
            vecCab.add(INT_TBL_DAT_DIAS_ULT_COT,"Día.Últ.Cot.");
            vecCab.add(INT_TBL_DAT_COD_COT,"");
            vecCab.add(INT_TBL_DAT_BUT_ANX2,"");
            vecCab.add(INT_TBL_DAT_FEC_ULT_FAC,"Fec.Últ.Fac.");
            vecCab.add(INT_TBL_DAT_DIAS_ULT_FAC,"Dia.Últ.Fac.");
            vecCab.add(INT_TBL_DAT_COD_FACT,"");
            vecCab.add(INT_TBL_DAT_BUT_ANX3,"");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            vecDat=new Vector();    //Almacena los datos
            //vecDat.clear();            
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            TableColumnModel tcmAux=tblDat.getColumnModel();

            //objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_MON_CRE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIR_CLI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_TEL_CLI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CIU_CLI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COR_ELE).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX1).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_COT).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_DIAS_ULT_COT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_COT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX2).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_FAC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_DIAS_ULT_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_FACT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX3).setPreferredWidth(20);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX1).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX2).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX3).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANX1);
            vecAux.add("" + INT_TBL_DAT_BUT_ANX2);
            vecAux.add("" + INT_TBL_DAT_BUT_ANX3);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_COT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_FACT, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);            
            
            objTblMod.setColumnDataType(INT_TBL_DAT_DIAS_ULT_COT, ZafTblMod.INT_COL_INT, new Integer(0), null );
            objTblMod.setColumnDataType(INT_TBL_DAT_DIAS_ULT_FAC, ZafTblMod.INT_COL_INT, new Integer(0), null );
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_DIAS_ULT_COT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIAS_ULT_FAC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX1).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX2).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX3).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX1).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void actionPerformed(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if(!objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_CLI).toString().equals(""))
                    llamarVenCli(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString(),objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_CLI).toString());
                }
            });

            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX2).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void actionPerformed(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if(!(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_COT).toString().equals("") || objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_COT).toString().equals("0")))
                        llamarVenCot(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString(),objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString(),objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_COT).toString());
                }
            });

            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANX3).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void actionPerformed(ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if(!(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_FACT).toString().equals("") || objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_FACT).toString().equals("0")))
                        llamarVenFac(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC).toString(), "1", objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_FACT).toString());
                }
            });

            //Configurar JTable: Modo de operación del JTable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);                       
            
            //AGRUPAR COLUMNAS
            objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Datos del cliente" );
            objTblHeaColGrpAmeSur.setHeight(16);            
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_CLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MON_CRE));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_NOM_VEN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DIR_CLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TEL_CLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_CIU_CLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COR_ELE));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_ANX1));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Última cotización" );
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_COT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DIAS_ULT_COT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_COT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_ANX2));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Última factura" );
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_FAC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DIAS_ULT_FAC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COD_FACT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_ANX3));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            tcmAux=null;  
            this.setTitle( objParSis.getNombreMenu() +" "+ strVersion);    
            lblTit.setText(objParSis.getNombreMenu());
            
            radTodCli.setSelected(true);
            radCliTod.setSelected(true);
            
            if (isOpcionHabilitada())
                MostrarCol(INT_TBL_DAT_MON_CRE);
            else
                ocultaCol(INT_TBL_DAT_MON_CRE);
            
            objTblFilCab=null;

        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);  
        }
        return blnRes;
    }

    private boolean mostrarVenConCli(int intTipBus){
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        //txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        //txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }else{
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)  {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            //txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }else 
                            txtCodCli.setText(strCodCli);                        
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    /*if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        //txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }else {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            //txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }else 
                            txtIdeCli.setText(strIdeCli);                        
                    }*/
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText())) {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        //txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }else {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            //txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }else 
                            txtDesLarCli.setText(strDesLarCli);                        
                    }
                break;
            }
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /// ventana de consulta del  local a la cual peretence el cliente 
    private boolean mostrarVenConLoc(int intTipBus) {
        boolean blnRes=true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(1);
                    //vcoLoc.show();
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                        txtCodEmpRel.setText(vcoLoc.getValueAt(1)); //José Marín 17/Ene/2014
                        txtCodLoc.setText(vcoLoc.getValueAt(2));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(3));
                    }
                break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText())) {
                        txtCodEmpRel.setText(vcoLoc.getValueAt(1));//José Marín 17/Ene/2014
                        txtCodLoc.setText(vcoLoc.getValueAt(2));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(3));
                    }else {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        //vcoLoc.show();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodEmpRel.setText(vcoLoc.getValueAt(1));//José Marín 17/Ene/2014
                            txtCodLoc.setText(vcoLoc.getValueAt(2));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(3));
                        }else
                            txtCodLoc.setText(strCodLoc);                        
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtDesLarLoc.getText())){
                        txtCodEmpRel.setText(vcoLoc.getValueAt(1));//José Marín 17/Ene/2014
                        txtCodLoc.setText(vcoLoc.getValueAt(2));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(3));
                    }else{
                        vcoLoc.setCampoBusqueda(1);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        //vcoLoc.show();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) {
                            txtCodEmpRel.setText(vcoLoc.getValueAt(1));//José Marín 17/Ene/2014
                            txtCodLoc.setText(vcoLoc.getValueAt(2));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(3));
                        }else 
                            txtDesLarLoc.setText(strNomLoc);                        
                    }
                    break;
            }
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * MOSTRAR LOCALES DE ACUERDO A TIPO DE USUARIO
     * 
     */
    private boolean configurarVenConLoc() {
        boolean blnRes=true;
        String strSQL="";
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");//José Marín 17/Ene/2014
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");//José Marín 17/Ene/2014
            arlAli.add("Cód.Loc.");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("5");//José Marín 17/Ene/2014
            arlAncCol.add("5");
            arlAncCol.add("492");

            //Cargar el listado de locales de acuerdo al usuario.
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
            if(objParSis.getCodigoUsuario()==1){ //José Marín 17/Ene/2014
               if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                   strSQL="";
                   strSQL+=" SELECT a1.co_emp,a1.co_loc ,a1.tx_nom";
                    strSQL+=" FROM tbm_Loc AS a1";
                    strSQL+=" WHERE a1.co_emp>0";
                    strSQL+=" ORDER BY a1.co_emp,a1.co_loc";
               }
               else{
                   strSQL="";
                   strSQL+=" SELECT a1.co_emp, a1.co_loc ,a1.tx_nom";
                    strSQL+=" FROM tbm_Loc AS a1";
                    strSQL+=" WHERE a1.co_emp>0 and a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.co_emp,a1.co_loc";
               }
           }//José Marín 17/Ene/2014
           else{//José Marín 17/Ene/2014
                strSQL="";
                strSQL+=" SELECT DISTINCT a3.co_emp, a3.co_loc as co_loc, a3.tx_nom as tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a2";
                strSQL+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc)";
                strSQL+=" INNER JOIN tbm_emp as a4 ON (a2.co_emprel=a4.co_emp)";
                strSQL+=" WHERE a2.st_reg in ('A','P')";
                strSQL+=" and a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" and a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a3.co_emp,a3.co_loc";
            }//José Marín 17/Ene/2014
            System.out.println("ZafVen22.configurarVenConLoc" + strSQL);
           
            vcoLoc=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, JLabel.RIGHT);
            
        }catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

   /**
    * MODIFICADO EFLORESA 2012-04-04
    * SE HACE LA CONSULTA SOLO LOS CLIENTES DEL LOCAL SELECCIONADO O EMPRESA/GRUPO
    * 
    */
    private boolean configurarVenConCli() {
        boolean blnRes=true;
        try {
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
            
//            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))            {
                //Armar la sentencia SQL.
                strSql= " SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " + 
                        " FROM tbm_cli AS a " +
                        " WHERE a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                        " AND a.st_reg='A' " +
                        " and a.st_cli='S' " +
                        " ORDER BY a.tx_nom";
            } else {
                //Armar la sentencia SQL.
                /*strSql= " select a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " +
                        " from tbm_cli a, tbr_cliloc b " +
                        " where a.co_emp = b.co_emp " +
                        " and a.co_cli = b.co_cli " +
                        " and a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                        " and b.co_loc=" +objParSis.getCodigoLocal() + " " + 
                        " and a.st_reg='A' " +
                        " and a.st_cli='S' " + 
                        " order by 3 ";*/
                strSql= " select distinct a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " +
                        " from tbr_locprgusr c " +
                        " inner join tbr_cliloc b on (b.co_emp = c.co_emprel and b.co_loc = c.co_locrel) " +
                        " inner join tbm_cli a on (a.co_emp = b.co_emp and a.co_cli = b.co_cli) " +
//                        " and a.co_emp = c.co_emp " +
//                        " and b.co_loc = c.co_loc " +
//                        " and a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                        " where c.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                        " and c.co_loc=" +objParSis.getCodigoLocal() + " " + 
                        " and c.co_usr=" + objParSis.getCodigoUsuario() + " " +
                        " and c.co_mnu=" + objParSis.getCodigoMenu() + " " +
                        " and a.st_reg='A' " +
                        " and a.st_cli='S' " + 
                        " order by a.tx_nom ";
            }
            
            System.out.println("ZafVen22.configurarVenConCli" + strSql);
            
            vcoCli=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, JLabel.RIGHT);
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
     * FILTRO PARA LAS CONSULTAS.
     */
    private String filtro(){
        String strAux=" ", strCampo= "";
        
        if(radTodCli.isSelected()) strAux = strAux + " ";
        if(radFilCli.isSelected()) {
            if(!txtCodCli.getText().trim().equals("")) 
                strAux = strAux + " and co_cli = " + txtCodCli.getText().trim() + " " ;

            if(!txtCodLoc.getText().trim().equals("")) { 
                strAux+=" and co_emp=" + txtCodEmpRel.getText()  + " and co_loc = " + txtCodLoc.getText().trim() + " ";
            }
            else {
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                {
                    if(objParSis.getCodigoUsuario()==1)
                    {
                        strAux+=" and co_emp=" + objParSis.getCodigoEmpresa();
                    }
                    else{
                         //                strAux+=" and co_loc = " + objParSis.getCodigoLocal() + " ";
                    strAux+=" and co_emp || co_loc in (SELECT DISTINCT  a3.co_emp || a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                                             strAux+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                             strAux+=" a2.co_mnu=" + objParSis.getCodigoMenu();
                                             strAux+=" and a2.co_emp=" + objParSis.getCodigoEmpresa();
                                             strAux+=" and a2.co_loc=" + objParSis.getCodigoLocal();
                                              strAux+=" and a2.st_reg in ('A','P') and a2.co_usr="+ objParSis.getCodigoUsuario() +")";
                    }
                }
                
            //José Marín 17/Ene/2014
            }
        }
        if(radCliCot.isSelected()) strCampo = " fe_cot ";
        if(radCliFac.isSelected()) strCampo = " fe_doc ";
        if(radCliTod.isSelected()){
            optPer.setSelected(false);
//            radUltSeisMeses.setSelected(false); 
//            radUltAnio.setSelected(false); 
//            radMasAnio.setSelected(false);
            optNun.setSelected(false);
        }
        /*if(radUltTresMeses.isSelected()) strAux = strAux + " and "+ strCampo +" = (current_date - interval '3 month') :: date " ;
        if(radUltSeisMeses.isSelected()) strAux = strAux + " and "+ strCampo +" = (current_date - interval '6 month') :: date " ;
        if(radUltAnio.isSelected()) strAux = strAux + " and "+ strCampo +" = (current_date - interval '12 month') :: date " ;
        if(radMasAnio.isSelected()) strAux = strAux + " and "+ strCampo +" < (current_date - interval '12 month') :: date " ; */
        if(optPer.isSelected() && !radCliTod.isSelected() ) {
            strAux +=" and " + strCampo + " between (current_date - interval '" + cmbHas.getSelectedItem().toString() + "";
            strAux +="  month') :: date and (current_date - interval '" + cmbDes.getSelectedItem().toString() + " month') :: date ";
        }
//        if(radUltSeisMeses.isSelected() && !radCliTod.isSelected() ) strAux = strAux  + " and " + strCampo + " between (current_date - interval '12 month') :: date and (current_date - interval '6 month') :: date " ;
//        if(radUltAnio.isSelected() && !radCliTod.isSelected() ) strAux = strAux + " and " + strCampo + " between (current_date - interval '12 month') :: date and (current_date - interval '3 month') :: date " ;
//        if(radMasAnio.isSelected() && !radCliTod.isSelected() ) strAux = strAux + " and " + strCampo + " < (current_date - interval '12 month') :: date " ; 
        if(optNun.isSelected() && !radCliTod.isSelected() ) strAux = strAux + " and " + strCampo + " is null " ;           
        
        return strAux; 
    }
    
    /*
     * QUERY DE CONSULTA PRINCIPAL Y DETALLE DE LA TABLA DEL REPORTE.
     */
    private boolean cargarDetReg(String strAux){
        boolean blnRes = false;
        String strCodCot="", strFecCot="", strDiasCot="", StrCodFac="", strFecFac="", strDiasFac="", strSql2;
        butCon.setText("Detener");
        lblMsgSis.setText("Obteniendo datos...");

        if (objParSis.getCodigoUsuario()==1) {        
            strSql=" select b.co_emp, e.co_loc, b.co_cli, b.tx_nom as nom_cli, case when b.nd_moncre is null then 0 else b.nd_moncre end as nd_moncre, " +
                   " (select t.tx_nom from tbm_usr as t where t.co_usr=b.co_ven) as vendedor, " +
                   " b.tx_dir as direccion, b.tx_tel as telefono, " +
                   " (select t.tx_deslar from tbm_ciu as t where t.co_ciu=b.co_ciu) as ciudad, " +
                   " b.tx_corele as email " +
                   " from tbm_cli as b, tbr_cliloc as e " + 
                   " where b.co_emp = " + objParSis.getCodigoEmpresa() + " " +
//                   " and e.co_loc = " + objParSis.getCodigoLocal() + " " +
                   " and b.st_reg = 'A' " +   
                   " and b.co_emp = e.co_emp " +   
                   " and b.co_cli = e.co_cli " +   
                   " and b.st_cli = 'S' ";   
        } else {
            strSql=" select b.co_emp, e.co_loc, b.co_cli, b.tx_nom as nom_cli, case when b.nd_moncre is null then 0 else b.nd_moncre end as nd_moncre, " +
                   " (select t.tx_nom from tbm_usr as t where t.co_usr=b.co_ven) as vendedor, " +
                   " b.tx_dir as direccion, b.tx_tel as telefono, " +
                   " (select t.tx_deslar from tbm_ciu as t where t.co_ciu=b.co_ciu) as ciudad, " +
                   " b.tx_corele as email " +
                   " from tbr_locprgusr as f " + 
                   " inner join tbr_cliloc as e on (e.co_emp = f.co_emprel and e.co_loc = f.co_locrel) " + 
                   " inner join tbm_cli as b on (b.co_emp = e.co_emp and b.co_cli = e.co_cli) " + 
                   " where f.co_emp = " + objParSis.getCodigoEmpresa() + " " +
                   " and f.co_loc = " + objParSis.getCodigoLocal() + " " +
                   " and f.co_usr = " + objParSis.getCodigoUsuario() + " " +
                   " and f.co_mnu = " + objParSis.getCodigoMenu() + " " +
                   " and b.st_reg = 'A' " +   
                   " and b.st_cli = 'S' ";   
        }
        
        if(radCliCot.isSelected()){
            strSql=" select * from (" +
                   " select '' :: text as fe_doc, 0 as co_doc, '' :: text as dias_doc, max(a.fe_cot) as fe_cot, max(a.co_cot) as co_cot, min((current_date - fe_cot)) as dias_cot, cli.co_cli, cli.nom_cli, cli.co_emp, cli.co_loc, cli.nd_moncre, cli.vendedor, cli.direccion, cli.telefono, cli.ciudad, cli.email " +
                   " from ( " +
                   strSql + 
                   " ) as cli " + 
                   " left outer join tbm_cabcotven a on (a.co_emp=cli.co_emp and a.co_loc=cli.co_loc and a.co_cli=cli.co_cli) " +  
                   " group by cli.co_cli, cli.nom_cli, cli.co_emp, cli.co_loc, cli.nd_moncre, cli.vendedor, cli.direccion, cli.telefono, cli.ciudad, cli.email " +
                   " order by cli.nom_cli ) as f "  +
                   " where co_emp=" + objParSis.getCodigoEmpresa() + " " +
//                   " and co_loc=" + objParSis.getCodigoLocal() + " " +
                   strAux ;
        }
        
        if(radCliFac.isSelected()){
            strSql=" select * from (" +
                   " select max(d.fe_doc) as fe_doc, max(d.co_doc) as co_doc, min((current_date - d.fe_doc)) as dias_doc, '' :: text as fe_cot, 0 as co_cot, '' :: text as dias_cot, cli.co_cli, cli.nom_cli, cli.co_emp, cli.co_loc, cli.nd_moncre, cli.vendedor, cli.direccion, cli.telefono, cli.ciudad, cli.email " +
                   " from ( " +
                   strSql + 
                   " ) as cli " + 
                   " inner join tbm_cabtipdoc g on (g.co_emp=cli.co_emp and g.co_loc=cli.co_loc and g.co_cat=3) " +  
                   " left outer join tbm_cabmovinv d on (d.co_emp=g.co_emp and d.co_loc=g.co_loc and d.co_cli=cli.co_cli and d.co_tipdoc=g.co_tipdoc) " +  
                   " group by cli.co_cli, cli.nom_cli, cli.co_emp, cli.co_loc, cli.nd_moncre, cli.vendedor, cli.direccion, cli.telefono, cli.ciudad, cli.email " +
                   " order by cli.nom_cli ) as f "  +
                   " where co_emp=" + objParSis.getCodigoEmpresa() + " " +
//                   " and co_loc=" + objParSis.getCodigoLocal() + " " +
                   strAux ;
        }
        
        if(radCliTod.isSelected()){
           strSql2="";
            strSql2+=" select DISTINCT * from (" ;
            strSql2+=" select max(d.fe_doc) as fe_doc, case when max(d.co_doc) is null then 0 else max(d.co_doc) end as co_doc," ;
            strSql2+=" min((current_date - d.fe_doc)) as dias_doc, max(a.fe_cot) as fe_cot, case when max(a.co_cot) is null then 0 else max(a.co_cot) end as co_cot,";
            strSql2+=" min((current_date - fe_cot)) as dias_cot, cli.co_cli, cli.nom_cli, cli.co_emp, ";
            if(optPer.isSelected())
                strSql2+=" cli.co_loc, cli.nd_moncre, ";
            if(optNun.isSelected())
                strSql2+=" 4 as co_loc, 1 as nd_moncre, ";
            strSql2+=" ";
            strSql2+=" cli.vendedor, cli.direccion, cli.telefono, cli.ciudad, cli.email ";
            strSql2+=" from ( " ;
            strSql2+=strSql ;
            strSql2+=" ) as cli " ;
            strSql2+=" inner join tbm_cabtipdoc g on (g.co_emp=cli.co_emp and g.co_loc=cli.co_loc and g.co_cat=3)" ;
            if(optPer.isSelected()){
                strSql2+=" left outer join tbm_cabcotven a on (a.co_emp=cli.co_emp and a.co_loc=cli.co_loc and a.co_cli=cli.co_cli AND" ;
                strSql2+=" fe_cot between (current_date - interval '" + cmbHas.getSelectedItem().toString() + "";
                strSql2+="  month') :: date and (current_date - interval '" + cmbDes.getSelectedItem().toString() + " month') :: date )";
                strSql2+=" left outer join tbm_cabmovinv d on (d.co_emp=g.co_emp and d.co_loc=g.co_loc and d.co_cli=cli.co_cli and d.co_tipdoc=g.co_tipdoc AND" ;  
                strSql2+=" fe_doc between (current_date - interval '" + cmbHas.getSelectedItem().toString() + "";
                strSql2+="  month') :: date and (current_date - interval '" + cmbDes.getSelectedItem().toString() + " month') :: date )";
            }
            else{
                strSql2+=" left outer join tbm_cabcotven a on (a.co_emp=cli.co_emp and a.co_loc=cli.co_loc and a.co_cli=cli.co_cli AND" ;
                strSql2+=" fe_cot is null)";
                strSql2+=" left outer join tbm_cabmovinv d on (d.co_emp=g.co_emp and d.co_loc=g.co_loc and d.co_cli=cli.co_cli and d.co_tipdoc=g.co_tipdoc AND" ;  
                strSql2+=" fe_doc is null )";
            }
          strSql2+=" group by cli.co_cli, cli.nom_cli, cli.co_emp, cli.co_loc, cli.nd_moncre, cli.vendedor, cli.direccion, cli.telefono, cli.ciudad, cli.email " ;
          strSql2+=" order by cli.nom_cli ) as f "  ;
          strSql2+=" where co_emp=" + objParSis.getCodigoEmpresa() + " " ;
          if(optPer.isSelected())
            strSql2+=" AND fe_doc IS NOT NULL ";
          
//                   " and co_loc=" + objParSis.getCodigoLocal() + " " +
            strSql2+=       strAux ;
            strSql = strSql2;            
        }
        
        System.out.println("ZafVen22.cargarDetReg: " + strSql);
        
        try{             
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stm=conn.createStatement();
            rst=stm.executeQuery(strSql);

            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            
            while(rst.next()){
                vecReg=new Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("nom_cli"));
                vecReg.add(INT_TBL_DAT_MON_CRE,objUti.redondear(objUti.parseDouble(rst.getString("nd_moncre")), 2));
                vecReg.add(INT_TBL_DAT_NOM_VEN, rst.getString("vendedor"));
                vecReg.add(INT_TBL_DAT_DIR_CLI, rst.getString("direccion"));
                vecReg.add(INT_TBL_DAT_TEL_CLI, rst.getString("telefono"));
                vecReg.add(INT_TBL_DAT_CIU_CLI, rst.getString("ciudad"));
                vecReg.add(INT_TBL_DAT_COR_ELE, rst.getString("email"));
                vecReg.add(INT_TBL_DAT_BUT_ANX1,"");
                //if(radCliCot.isSelected()){
                strFecCot=rst.getString("fe_cot"); 
                strDiasCot=rst.getString("dias_cot"); 
                strCodCot=rst.getString("co_cot"); 
                //}
                vecReg.add(INT_TBL_DAT_FEC_ULT_COT,strFecCot);
                vecReg.add(INT_TBL_DAT_DIAS_ULT_COT,strDiasCot);                    
                vecReg.add(INT_TBL_DAT_COD_COT,strCodCot);
                vecReg.add(INT_TBL_DAT_BUT_ANX2,"");
                //if(radCliFac.isSelected()){
                StrCodFac=rst.getString("co_doc"); 
                strFecFac=rst.getString("fe_doc"); 
                strDiasFac=rst.getString("dias_doc");
                //}
                vecReg.add(INT_TBL_DAT_FEC_ULT_FAC,strFecFac);
                vecReg.add(INT_TBL_DAT_DIAS_ULT_FAC,strDiasFac);
                vecReg.add(INT_TBL_DAT_COD_FACT,StrCodFac);
                vecReg.add(INT_TBL_DAT_BUT_ANX3,"");
                vecDat.add(vecReg);
                blnRes=true;
            }
            
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();

            rst.close();
            stm.close();
            conn.close();
            rst=null;
            stm=null;
            conn=null; 
            
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros."); 
            pgrSis.setValue(0); 
            butCon.setText("Consultar");
            
        }catch(SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);            
        }        
        return blnRes;
    }
    
    private void llamarVenFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc){
        try{
            ZafVen02 obj1 = new ZafVen02(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, 14 );
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
            obj1.show();
        }catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
    }

    private void llamarVenCot(String strCodEmp, String strCodLoc, String strCodDoc){
        try{
            ZafVen01 obj1 = new ZafVen01(objParSis, strCodEmp, strCodLoc, strCodDoc );
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
            obj1.show();
        }catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
    }

    private void llamarVenCli(String strCodEmp, String strCodCli){
        try{
            //ZafMae07 obj1 = new ZafMae07(objParSis, Integer.valueOf(strCodCli), Integer.valueOf("828"));
            ZafMae07 obj1 = new ZafMae07(objParSis, Integer.valueOf(strCodEmp), Integer.valueOf(strCodCli), Integer.valueOf("828"));
            this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
            obj1.show();
        }catch(Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
    }
                    
    private void MostrarCol(int intCol){ 
            tblDat.getColumnModel().getColumn(intCol).setWidth(70);
            tblDat.getColumnModel().getColumn(intCol).setMaxWidth(70);
            tblDat.getColumnModel().getColumn(intCol).setMinWidth(70);
            tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(intCol).setResizable(false); 
    }

    private void ocultaCol(int intCol){
             tblDat.getColumnModel().getColumn(intCol).setWidth(0);
             tblDat.getColumnModel().getColumn(intCol).setMaxWidth(0);
             tblDat.getColumnModel().getColumn(intCol).setMinWidth(0);
             tblDat.getColumnModel().getColumn(intCol).setPreferredWidth(0);
             tblDat.getColumnModel().getColumn(intCol).setResizable(false); 
    }

    private boolean isOpcionHabilitada(){
        boolean blnRes=false;
        blnRes=objPerUsr.isOpcionEnabled(0xbfb);
        return blnRes;
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFiltro = new javax.swing.ButtonGroup();
        bgrFilCli = new javax.swing.ButtonGroup();
        bgrFilPlazo = new javax.swing.ButtonGroup();
        Pan_frm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tab_frm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        radTodCli = new javax.swing.JRadioButton();
        radFilCli = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLarLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        radCliTod = new javax.swing.JRadioButton();
        radCliCot = new javax.swing.JRadioButton();
        radCliFac = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        optPer = new javax.swing.JRadioButton();
        optNun = new javax.swing.JRadioButton();
        cmbDes = new javax.swing.JComboBox();
        cmbHas = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtCodEmpRel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable(){
            protected javax.swing.table.JTableHeader createDefaultTableHeader(){
                return new ZafTblHeaGrp(columnModel);
            }
        };
        pan_bar = new javax.swing.JPanel();
        Pan_bot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCerrar = new javax.swing.JButton();
        pan_barest = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
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

        Pan_frm.setName("pan_frm"); // NOI18N
        Pan_frm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana"); // NOI18N
        Pan_frm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFiltro.add(radTodCli);
        radTodCli.setSelected(true);
        radTodCli.setText("Todos los clientes"); // NOI18N
        radTodCli.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radTodCliStateChanged(evt);
            }
        });
        radTodCli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radTodCliItemStateChanged(evt);
            }
        });
        radTodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTodCliActionPerformed(evt);
            }
        });
        panFil.add(radTodCli);
        radTodCli.setBounds(4, 4, 400, 20);

        bgrFiltro.add(radFilCli);
        radFilCli.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        radFilCli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radFilCliItemStateChanged(evt);
            }
        });
        panFil.add(radFilCli);
        radFilCli.setBounds(4, 24, 400, 20);

        lblLoc.setText("Local:"); // NOI18N
        lblLoc.setToolTipText(""); // NOI18N
        panFil.add(lblLoc);
        lblLoc.setBounds(24, 44, 80, 20);

        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(144, 44, 56, 20);

        txtDesLarLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarLocActionPerformed(evt);
            }
        });
        txtDesLarLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusLost(evt);
            }
        });
        panFil.add(txtDesLarLoc);
        txtDesLarLoc.setBounds(200, 44, 460, 20);

        butLoc.setText("..."); // NOI18N
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(660, 44, 20, 20);

        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText(""); // NOI18N
        panFil.add(lblCli);
        lblCli.setBounds(24, 64, 120, 20);

        txtCodCli.setToolTipText("");
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
        txtCodCli.setBounds(144, 64, 56, 20);

        txtDesLarCli.setToolTipText("Nombre del cliente/proveedor");
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
        txtDesLarCli.setBounds(200, 64, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(660, 64, 20, 20);

        bgrFilCli.add(radCliTod);
        radCliTod.setText("Todos");
        radCliTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radCliTodStateChanged(evt);
            }
        });
        radCliTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCliTodActionPerformed(evt);
            }
        });
        panFil.add(radCliTod);
        radCliTod.setBounds(28, 84, 80, 23);

        bgrFilCli.add(radCliCot);
        radCliCot.setText("Clientes que no le hemos cotizado en:");
        panFil.add(radCliCot);
        radCliCot.setBounds(108, 84, 260, 23);

        bgrFilCli.add(radCliFac);
        radCliFac.setText("Clientes que no le hemos facturado en:");
        panFil.add(radCliFac);
        radCliFac.setBounds(364, 84, 260, 23);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jPanel1.setLayout(null);

        bgrFilPlazo.add(optPer);
        optPer.setSelected(true);
        optPer.setText("Un período de:");
        optPer.setActionCommand("3 meses");
        jPanel1.add(optPer);
        optPer.setBounds(12, 20, 110, 20);

        bgrFilPlazo.add(optNun);
        optNun.setText("Nunca");
        jPanel1.add(optNun);
        optNun.setBounds(280, 20, 100, 20);

        cmbDes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36" }));
        cmbDes.setSelectedIndex(2);
        jPanel1.add(cmbDes);
        cmbDes.setBounds(125, 20, 50, 20);

        cmbHas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36" }));
        cmbHas.setSelectedIndex(5);
        jPanel1.add(cmbHas);
        cmbHas.setBounds(210, 20, 50, 20);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("a");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(185, 20, 20, 20);

        panFil.add(jPanel1);
        jPanel1.setBounds(24, 92, 660, 52);

        txtCodEmpRel.setEditable(false);
        txtCodEmpRel.setEnabled(false);
        txtCodEmpRel.setFocusable(false);
        panFil.add(txtCodEmpRel);
        txtCodEmpRel.setBounds(120, 44, 20, 20);

        tab_frm.addTab("Filtro", panFil);

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
        jScrollPane1.setViewportView(tblDat);

        tab_frm.addTab("Reporte", jScrollPane1);

        Pan_frm.add(tab_frm, java.awt.BorderLayout.CENTER);
        tab_frm.getAccessibleContext().setAccessibleName("Filtro");
        tab_frm.getAccessibleContext().setAccessibleDescription("");

        pan_bar.setLayout(new java.awt.BorderLayout());

        Pan_bot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        Pan_bot.add(butCon);

        butCerrar.setText("Cerrar");
        butCerrar.setPreferredSize(new java.awt.Dimension(92, 25));
        butCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrarActionPerformed(evt);
            }
        });
        Pan_bot.add(butCerrar);

        pan_bar.add(Pan_bot, java.awt.BorderLayout.CENTER);

        pan_barest.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo"); // NOI18N
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pan_barest.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 16));
        jPanel2.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 15));
        jPanel2.add(pgrSis, java.awt.BorderLayout.CENTER);

        pan_barest.add(jPanel2, java.awt.BorderLayout.EAST);

        pan_bar.add(pan_barest, java.awt.BorderLayout.SOUTH);
        pan_barest.getAccessibleContext().setAccessibleParent(pan_bar);

        Pan_frm.add(pan_bar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(Pan_frm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        strMsg="¿Esta seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
            Runtime.getRuntime().gc();
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        configurarVenConLoc();
        configurarVenConCli();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrarActionPerformed
        // TODO add your handling code here:
        exitForm(null);
    }//GEN-LAST:event_butCerrarActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        mostrarVenConCli(0);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0){
            radFilCli.setSelected(true);
        }        
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        // TODO add your handling code here:
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtDesLarCli.getText().equals("")) {
                txtCodCli.setText("");
                //txtIdeCli.setText("");
                txtDesLarCli.setText("");
            }else 
                mostrarVenConCli(3);
        } else
            txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0) 
            radFilCli.setSelected(true);
        
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        // TODO add your handling code here:
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                //txtIdeCli.setText("");
                txtDesLarCli.setText("");
            }else 
                mostrarVenConCli(1);            
        }else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            radFilCli.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();        
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0){
            radFilCli.setSelected(true);
        }          
    }//GEN-LAST:event_butLocActionPerformed

    private void txtDesLarLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusLost
        // TODO add your handling code here:
        if (txtDesLarLoc.isEditable()){
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarLoc.getText().equalsIgnoreCase(strNomLoc)) {
                if (txtDesLarLoc.getText().equals("")) {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                }else 
                    if(!mostrarVenConLoc(2)){
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                    }                
            }else
                txtDesLarLoc.setText(strNomLoc);
        }
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0) 
            radFilCli.setSelected(true);
    }//GEN-LAST:event_txtDesLarLocFocusLost

    private void txtDesLarLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusGained
        // TODO add your handling code here:
        strNomLoc=txtDesLarLoc.getText();
        txtDesLarLoc.selectAll();
    }//GEN-LAST:event_txtDesLarLocFocusGained

    private void txtDesLarLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarLocActionPerformed
        // TODO add your handling code here:
        txtDesLarLoc.transferFocus();
    }//GEN-LAST:event_txtDesLarLocActionPerformed

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        if (txtCodLoc.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) {
                if (txtCodLoc.getText().equals("")) {
                    txtCodLoc.setText("");
                    txtDesLarLoc.setText("");
                }else
                   if(!mostrarVenConLoc(1)){
                        txtCodLoc.setText("");
                        txtDesLarLoc.setText("");
                    }                
            }else
                txtCodLoc.setText(strCodLoc);
        }            
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0) 
            radFilCli.setSelected(true);
        
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void radFilCliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radFilCliItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_radFilCliItemStateChanged

    private void radTodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTodCliActionPerformed
        // TODO add your handling code here:
        if (radTodCli.isSelected()){
            txtCodLoc.setText("");
            txtDesLarLoc.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            strCodLoc="";
            strNomLoc="";
        }        
    }//GEN-LAST:event_radTodCliActionPerformed

    private void radTodCliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radTodCliItemStateChanged
        // TODO add your handling code here:
        radCliCot.setSelected(false) ; 
        radCliFac.setSelected(false) ; 
   }//GEN-LAST:event_radTodCliItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        if (butCon.getText().equals("Consultar"))
            if (radTodCli.isSelected() || radFilCli.isSelected()){
                if (radCliCot.isSelected() || radCliFac.isSelected() || radCliTod.isSelected()){
                    //if (radCliCot.isSelected() || radCliFac.isSelected()){
                    //    radFilCli.setSelected(true);
                    //} 
                    //if (radUltTresMeses.isSelected() || radUltSeisMeses.isSelected() || radUltAnio.isSelected() || radMasAnio.isSelected() || radNever.isSelected()){
                        //mostrarMsgInf("Hola Mundo!!");
                        if (objThrGUI==null){
                            objThrGUI=new ZafThreadGUI();
                            objThrGUI.start();
                        }
                    //}else 
                        //mostrarMsgInf("No ha seleccionado criterio de busqueda de tiempo desde que no se ha facturado o cotizado.  ");
                }else
                    mostrarMsgInf("No ha seleccionado criterio de busqueda por clientes facturados o clientes cotizados. ");
            }else
                mostrarMsgInf("No ha seleccionado ningun criterio de busqueda. ");
    }//GEN-LAST:event_butConActionPerformed

    private void radCliTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCliTodActionPerformed
        // TODO add your handling code here:
        optPer.setSelected(false);
//        radUltSeisMeses.setSelected(false); 
//        radUltAnio.setSelected(false); 
//        radMasAnio.setSelected(false);
        optNun.setSelected(false);
    }//GEN-LAST:event_radCliTodActionPerformed

    private void radCliTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radCliTodStateChanged
        // TODO add your handling code here:
        optPer.setSelected(false);
//        radUltSeisMeses.setSelected(false); 
//        radUltAnio.setSelected(false); 
//        radMasAnio.setSelected(false);
        optNun.setSelected(false);
    }//GEN-LAST:event_radCliTodStateChanged

    private void radTodCliStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radTodCliStateChanged
        // TODO add your handling code here:
        radCliCot.setSelected(false) ; 
        radCliFac.setSelected(false) ;         
    }//GEN-LAST:event_radTodCliStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pan_bot;
    private javax.swing.JPanel Pan_frm;
    private javax.swing.ButtonGroup bgrFilCli;
    private javax.swing.ButtonGroup bgrFilPlazo;
    private javax.swing.ButtonGroup bgrFiltro;
    private javax.swing.JButton butCerrar;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLoc;
    private javax.swing.JComboBox cmbDes;
    private javax.swing.JComboBox cmbHas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optNun;
    private javax.swing.JRadioButton optPer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel pan_bar;
    private javax.swing.JPanel pan_barest;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radCliCot;
    private javax.swing.JRadioButton radCliFac;
    private javax.swing.JRadioButton radCliTod;
    private javax.swing.JRadioButton radFilCli;
    private javax.swing.JRadioButton radTodCli;
    private javax.swing.JTabbedPane tab_frm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmpRel;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarLoc;
    // End of variables declaration//GEN-END:variables

    private class ZafThreadGUI extends Thread {

        public ZafThreadGUI() { 
        }
        
        @Override
        public void run(){
            pgrSis.setIndeterminate(true);
            
            if(!cargarDetReg(filtro())){
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            
            pgrSis.setIndeterminate(false);
            if (tblDat.getRowCount()>0) {
                tab_frm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;            
        }
    }
    
    private class ZafMouMotAda extends MouseMotionAdapter {

        public ZafMouMotAda() { 
        }
        
        @Override
        public void mouseMoved(MouseEvent evt){
            String msg;
            int col = tblDat.columnAtPoint(evt.getPoint());
            
            switch(col){
                case INT_TBL_DAT_LIN: msg=""; break;
                case INT_TBL_DAT_COD_EMP: msg="Código de la empresa"; break;
                case INT_TBL_DAT_COD_LOC: msg="Código del local"; break;
                case INT_TBL_DAT_COD_CLI: msg="Código del cliente"; break;
                case INT_TBL_DAT_NOM_CLI: msg="Nombre del cliente"; break;
                case INT_TBL_DAT_MON_CRE: msg="Monto de crédito"; break;
                case INT_TBL_DAT_NOM_VEN: msg="Vendedor asignado"; break;
                case INT_TBL_DAT_DIR_CLI: msg="Dirección del cliente"; break;
                case INT_TBL_DAT_TEL_CLI: msg="Teléfono del cliente"; break;
                case INT_TBL_DAT_CIU_CLI: msg="Ciudad del cliente"; break;
                case INT_TBL_DAT_COR_ELE: msg="e-mail del cliente"; break;
                case INT_TBL_DAT_BUT_ANX1: msg="Muestra el maestro de clientes"; break;
                case INT_TBL_DAT_FEC_ULT_COT: msg="Fecha de última cotización"; break;
                case INT_TBL_DAT_DIAS_ULT_COT: msg="Días desde la última cotización"; break;
                case INT_TBL_DAT_BUT_ANX2: msg="Muestra la última cotización de ventas"; break;
                case INT_TBL_DAT_FEC_ULT_FAC: msg="Fecha de última facturación"; break;
                case INT_TBL_DAT_DIAS_ULT_FAC: msg="Dias desde la última facturación"; break;
                case INT_TBL_DAT_BUT_ANX3: msg="Muestra la última factura de ventas"; break;
                default: msg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(msg);            
        }
    }
    
}
