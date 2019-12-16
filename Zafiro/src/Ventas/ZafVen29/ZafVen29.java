/*
 * ZafVen29.java  
 *
 * Created on 13 de agosto de 2008, 11:41  
 */
package Ventas.ZafVen29;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
     
/**
 * 
 * @author  lmunoz
 * SEGUIMIENTO DE SOLICITUDES DE DEVOLUCION DE VENTAS
 * 
 */
public class ZafVen29 extends JInternalFrame
{

    private static final int INT_TBL_LINEA=0;
    private static final int INT_TBL_NUMFAC=1;
    private static final int INT_TBL_CODEMP=2;
    private static final int INT_TBL_CODLOC=3;
    private static final int INT_TBL_CODTIPDOC=4;
    private static final int INT_TBL_DESCORTIPDOC=5;
    private static final int INT_TBL_DESLARTIPDOC=6;
    private static final int INT_TBL_CODDOC=7;
    private static final int INT_TBL_NUMDOC=8;
    private static final int INT_TBL_FECDOC=9;
    private static final int INT_TBL_CODCLI=10;
    private static final int INT_TBL_NOMCLI=11;
    private static final int INT_TBL_USUSOLDEV=12;
    private static final int INT_TBL_TIPDEV=13;
    private static final int INT_TBL_MOTIVO=14;
    private static final int INT_TBL_FALSTK=15;
    private static final int INT_TBL_REVTEC=16;
    private static final int INT_TBL_VOL_FAC=17;
    
    private static final int INT_TBL_CANRESDEV=18;
    
    private static final int INT_TBL_BUTMOSSOL=19;
    private static final int INT_TBL_BUTMOSFAC=20;
    private static final int INT_TBL_DEVAUT=21;
    private static final int INT_TBL_DEVDEN=22;
    private static final int INT_TBL_DEVCAN=23;
    private static final int INT_TBL_FECAUT=24;
    private static final int INT_TBL_OBSAUT=25;
    private static final int INT_TBL_BUTOBS=26;
    private static final int INT_TBL_RECMERDEV=27;
    private static final int INT_TBL_BUTRECMER=28;
    private static final int INT_TBL_REVTECMERDEV=29;
    private static final int INT_TBL_BUTREVTEC=30;
    private static final int INT_TBL_REVCONINGBOD=31;
    private static final int INT_TBL_BUTREVINGB=32;
    private static final int INT_TBL_INGDEVVENAUT=33;
    private static final int INT_TBL_BUTINGDEVVEN=34;
    private static final int INT_TBL_DEVCLIMERRCH=35;
    private static final int INT_TBL_BUTDEVMERREC=36;
    private static final int INT_TBL_PROCOM=37;
    private static final int INT_TBL_HAYMERRCH=38;
    private static final int INT_TBL_BUTANE=39;
    private static final int INT_TBL_ESTPAS3=40;
    private static final int INT_TBL_ESTPAS4=41;
    private static final int INT_TBL_ESTPAS5=42;
    private static final int INT_TBL_EXISTE=43; // José Marín 10-Ene-2014

    private String strSQL, strConSQL, strAux;
    private String strCodMotDev, strDesLarMotDev;               //Contenido del campo al obtener el foco.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc="";
    private String strDesLarTipDoc="";
    private String strCodTipDoc="";
    private String strCodSol="";
    private String strDesSol="";

    private Vector vecAux;
    private Vector vecCab=new Vector();
    private Vector vecCboTipDev=new Vector();
    private Vector vecCboEstAut=new Vector();
    private Vector vecCboPasEst=new Vector();
    private Vector vecEmp;

    private ZafTblMod objTblMod;
    private ZafParSis objParSis;
    private ZafSelFec objSelFec;
    private ZafThreadGUI objThrGUI;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenLbl objTblCelLbl;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafTblCelRenChk objTblCelRenChk2;
    private ZafUtil objUti;
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafVenCon vcoMotDev;                                //Ventana de consulta.
    private ZafVenCon objVenConTipdoc; 
    private ZafVenCon objVenConVen;
    private ZafVenCon vcoCli;
    private JTextField txtCodTipDoc = new JTextField();
    private final Color colFonCol2 = new Color(228,228,203);
    private final String strVersion = " v1.9";
    //José Marín 10-Ene-2014 v1.4
    private final String strTitulo = "Mensaje del Sistema Zafiro";
     
    /** Creates new form revisionTecMer */
    public ZafVen29(Librerias.ZafParSis.ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu()); 
            objUti = new ZafUtil();  
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setTitulo("Fecha del documento");
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);            
            //Configurar el combo "Tipo de devolución".
            vecCboTipDev.add("");
            vecCboTipDev.add("C");
            vecCboTipDev.add("P");
            vecCboTipDev.add("V");
            vecCboTipDev.add("E");
            cboTipDev.addItem("(Todos)");
            cboTipDev.addItem("Cantidad");
            cboTipDev.addItem("Porcentaje de descuento");
            cboTipDev.addItem("Precio unitario");
            cboTipDev.addItem("Descuento especial");
            //Configurar el combo "Estado de autorización".
            vecCboEstAut.add("");
            vecCboEstAut.add("P");
            vecCboEstAut.add("A");
            vecCboEstAut.add("D");
            vecCboEstAut.add("C");
            cboEstAut.addItem("(Todos)");
            cboEstAut.addItem("Pendiente");
            cboEstAut.addItem("Autorizado");
            cboEstAut.addItem("Denegado");
            cboEstAut.addItem("Cancelado");                    
            //Configurar el combo "Paso en el que está".
            vecCboPasEst.add("");
            vecCboPasEst.add("P1");
            vecCboPasEst.add("P2");
            vecCboPasEst.add("P3");
            vecCboPasEst.add("P4");
            vecCboPasEst.add("P5");
            vecCboPasEst.add("P6");
            vecCboPasEst.add("P7");
            vecCboPasEst.add("P8");
            cboPasEst.addItem("(Todos)");
            cboPasEst.addItem("Paso1: Esperando autorización");
            cboPasEst.addItem("Paso2: Solicitud revisada");
            cboPasEst.addItem("Paso3: Recepción de mercadería a devolver sujeta a revisión");
            cboPasEst.addItem("Paso4: Revisión técnica de la mercadería devuelta");
            cboPasEst.addItem("Paso5: Revisión y confirmación de ingreso a bodega de la mercaderia devuelta");
            cboPasEst.addItem("Paso6: (Mercadería Aceptada) Devolución procesada");
            cboPasEst.addItem("Paso7: (Mercadería Rechazada) Devolución al cliente de la mercadería rechazada");
            cboPasEst.addItem("Paso8: Proceso completo");
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                vecEmp = new Vector();
                llenaCboEmp();
            }
            else
            {
                lblEmp.setVisible(false);
                cboEmp.setVisible(false);
            }
            ZafDatePicker txtFecDoc;
            txtFecDoc = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new Dimension(70, 20));
            txtFecDoc.setHoy();
            Calendar objFec = Calendar.getInstance();
            ZafDatePicker dtePckPag =  new ZafDatePicker(new JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc!=null)
            {
                objFec.set(Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(Calendar.YEAR, fecDoc[2]);
            }
            Calendar objFecPagActual = Calendar.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(Calendar.DATE, -30 );

            dtePckPag.setAnio( objFecPagActual.get(Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");
            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
        }
        catch(CloneNotSupportedException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
        }
    }

    private void llenaCboEmp()
    {
        java.sql.Connection conn;
        java.sql.Statement  stm;
        java.sql.ResultSet rst;
        String strSql=""; 
        try
        {
            conn = java.sql.DriverManager.getConnection( objParSis.getStringConexion() , objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                stm=conn.createStatement();
                cboEmp.removeAllItems();
                vecEmp.clear();
                strSql="select co_emp , tx_nom from tbm_emp where co_emp<>"+objParSis.getCodigoEmpresaGrupo()+" order by co_emp";
                vecEmp.add("0");
                cboEmp.addItem("<< Seleccione >>");
                rst = stm.executeQuery(strSql);
                while(rst.next())
                {
                    vecEmp.add(rst.getString(1));
                    cboEmp.addItem(rst.getString(2));
                }
                cboEmp.setSelectedIndex(0);  
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                conn.close();
                conn=null;
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
    }

    public void configurar_ventana_consulta()
    {
        configurarVenConLoc();
        configurarVenConCli();
        configurarVenConTipDoc();
        configurarVenConSolicitante();
        configurarVenConMotDev();
        configurarTabla(); 
    } 

    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            String Str_Sql="";
            Str_Sql="Select a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
            " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
            " where   b.co_emp="+objParSis.getCodigoEmpresa()+" and " +
            " b.co_loc = " + objParSis.getCodigoLocal()   + " and " +
            " b.co_mnu = "+objParSis.getCodigoMenu();
            objVenConTipdoc=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            objVenConTipdoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    private boolean configurarVenConSolicitante()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
            " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
            " where b.co_emp="+objParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";
            objVenConVen=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    private boolean configurarTabla()
    {
        boolean blnRes=false;
        try
        {
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,""); //Linea 
            vecCab.add(INT_TBL_NUMFAC,"Núm.Fac."); //Numero de factura
            vecCab.add(INT_TBL_CODEMP,"Cód.Emp."); // codigo de la empresa 
            vecCab.add(INT_TBL_CODLOC,"Cód.Loc."); // codigo del local 
            vecCab.add(INT_TBL_CODTIPDOC,"Cód.Tip.Doc."); //codigo tipo de documento
            vecCab.add(INT_TBL_DESCORTIPDOC,"Tip.Doc."); //descripcion corta del tipo de doc
            vecCab.add(INT_TBL_DESLARTIPDOC,"Tipo del Documento"); // tipo del documento
            vecCab.add(INT_TBL_CODDOC,"Cód.Doc."); //codigo del documetno
            vecCab.add(INT_TBL_NUMDOC,"Núm.Doc."); //numero del documento
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc."); //fecha del documento
            vecCab.add(INT_TBL_CODCLI,"Cód.Cli."); // codigo del cliente
            vecCab.add(INT_TBL_NOMCLI,"Cliente"); // nombre del cliente
            vecCab.add(INT_TBL_USUSOLDEV,"Solicitante."); // nombre solicitante
            vecCab.add(INT_TBL_TIPDEV,"Tip.Dev."); //Tipo de devolucion
            vecCab.add(INT_TBL_MOTIVO,"Motivo"); //Tipo de devolucion
            
            
            vecCab.add(INT_TBL_FALSTK,"Fal.Stk"); //Tiene marcado falta de stock fisico en bodega
            vecCab.add(INT_TBL_REVTEC,"Rev.Téc."); // Tiene marcado revision tecnica
            vecCab.add(INT_TBL_VOL_FAC,"Vol.Fac."); //Volver a facturar
            
            vecCab.add(INT_TBL_CANRESDEV,"Can.Res.Dev ");
            
            vecCab.add(INT_TBL_BUTMOSSOL,""); //BTN ven solicitud de devolucion
            vecCab.add(INT_TBL_BUTMOSFAC,""); //BTN ver factura 
            vecCab.add(INT_TBL_DEVAUT,"Autorizar."); //CHK Autorizar
            vecCab.add(INT_TBL_DEVDEN,"Denegar.");  //CHK Denegar
            vecCab.add(INT_TBL_DEVCAN,"Cancelar."); //CHK Cancelar
            vecCab.add(INT_TBL_FECAUT,"Fec.Aut"); //Fecha de autorización
            vecCab.add(INT_TBL_OBSAUT,"Obs.Aut."); //Observacion de autorización
            vecCab.add(INT_TBL_BUTOBS,""); //BTN observación
            vecCab.add(INT_TBL_RECMERDEV,"Rec.Mer"); //CHK Recepcion de mercaderia devuelta
            vecCab.add(INT_TBL_BUTRECMER,""); //BTN Muestra la recepción de la mercaderia 
            vecCab.add(INT_TBL_REVTECMERDEV,"Rev.Téc"); //CHK revision tecnica de mercaderia devuelta
            vecCab.add(INT_TBL_BUTREVTEC,""); //BTN Muestra la revision tecnica de la mercaderia devuelta
            vecCab.add(INT_TBL_REVCONINGBOD,"Ing.Bod."); // CHK confirmación de ingreso a bodega de mercaderia devuelta
            vecCab.add(INT_TBL_BUTREVINGB,""); //BTN INgreso a devouciones de ventas autorizadas por el sistema
            vecCab.add(INT_TBL_INGDEVVENAUT,"Dev.Ven.");// CHK ingreso de devoluciones de ventas autorizadas por el sistema
            vecCab.add(INT_TBL_BUTINGDEVVEN,""); //BTN muestra la devolucion de ventas asociadas
            vecCab.add(INT_TBL_DEVCLIMERRCH,"Dev.Cli."); // CHK devolusion al cliente de la mercaderia rechazada 
            vecCab.add(INT_TBL_BUTDEVMERREC,""); //BTN Muestra la devolucion al cliente de la mercaderia rechazada
            vecCab.add(INT_TBL_PROCOM,"Pro.Com.");
            vecCab.add(INT_TBL_HAYMERRCH,"Hay.Mer.Rec."); 
            vecCab.add(INT_TBL_BUTANE,"");
            vecCab.add(INT_TBL_ESTPAS3,"");
            vecCab.add(INT_TBL_ESTPAS4,"");
            vecCab.add(INT_TBL_ESTPAS5,"");
            ///////////////////////
            //José Marín 10-Ene-2014 
            vecCab.add(INT_TBL_EXISTE,"existe");
            /////////////////////////
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod); 
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
            TableColumnModel tcmAux=tblDat.getColumnModel(); 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Para hacer editable las celdas.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTANE);
            vecAux.add("" + INT_TBL_BUTOBS);
            vecAux.add("" + INT_TBL_BUTMOSSOL);
            vecAux.add("" + INT_TBL_BUTMOSFAC);
            vecAux.add("" + INT_TBL_BUTRECMER);
            vecAux.add("" + INT_TBL_BUTREVTEC);
            vecAux.add("" + INT_TBL_BUTREVINGB);
            vecAux.add("" + INT_TBL_BUTINGDEVVEN);
            vecAux.add("" + INT_TBL_BUTDEVMERREC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Aqui se agrega las columnas que van ha estar ocultas.
            ArrayList arlColHid=new ArrayList();
            arlColHid.add("" + INT_TBL_ESTPAS3);
            arlColHid.add("" + INT_TBL_ESTPAS4);
            arlColHid.add("" + INT_TBL_ESTPAS5);
            //José Marín M 13/Ene/2014
            arlColHid.add("" + INT_TBL_EXISTE);
            arlColHid.add("" + INT_TBL_CODEMP);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            //José Marín 13/Ene/2014
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUMFAC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESLARTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODDOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_USUSOLDEV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_TIPDEV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_MOTIVO).setPreferredWidth(100);
            
            tcmAux.getColumn(INT_TBL_FALSTK).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_REVTEC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_VOL_FAC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANRESDEV).setPreferredWidth(50);            
            tcmAux.getColumn(INT_TBL_DEVAUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DEVDEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DEVCAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_RECMERDEV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_REVTECMERDEV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_REVCONINGBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_INGDEVVENAUT).setPreferredWidth(80); 
            tcmAux.getColumn(INT_TBL_DEVCLIMERRCH).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PROCOM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_HAYMERRCH).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTANE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_OBSAUT).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FECAUT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_BUTMOSSOL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTMOSFAC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTRECMER).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTREVTEC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTREVINGB).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTINGDEVVEN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTDEVMERREC).setPreferredWidth(20);
            //////////
            //José Marín 10-Ene-2014
            tcmAux.getColumn(INT_TBL_EXISTE).setPreferredWidth(20);
            ////////////////
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            Color colFonCol;
            colFonCol=new Color(228,228,203);
            ZafTblCelRenChk objTblCelRenChk;
            ZafTblCelEdiChk objTblCelEdiChk; 
            objTblCelRenChk = new ZafTblCelRenChk();
            objTblCelRenChk.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_DEVAUT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DEVDEN).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DEVCAN).setCellRenderer(objTblCelRenChk);
            /***********************************************************************************************/
            objTblCelRenChk2 = new ZafTblCelRenChk();
            objTblCelRenChk2.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_RECMERDEV).setCellRenderer(objTblCelRenChk2);
            tcmAux.getColumn(INT_TBL_REVTECMERDEV).setCellRenderer(objTblCelRenChk2);
            tcmAux.getColumn(INT_TBL_REVCONINGBOD).setCellRenderer(objTblCelRenChk2);
            tcmAux.getColumn(INT_TBL_INGDEVVENAUT).setCellRenderer(objTblCelRenChk2);
            tcmAux.getColumn(INT_TBL_PROCOM).setCellRenderer(objTblCelRenChk2);
            objTblCelRenChk2.addTblCelRenListener(new ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.
                switch (objTblCelRenChk2.getColumnRender())
                {
                    case INT_TBL_RECMERDEV:
                        if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_TIPDEV).toString().equals("Cantidad") )
                        {
                            if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_DEVAUT).toString().equals("true") )
                            {
                                if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_ESTPAS3).toString().equals("S") )
                                {
                                    objTblCelRenChk2.setBackground(colFonCol2);  
                                }
                                else
                                    objTblCelRenChk2.setBackground(Color.WHITE);
                            }
                            else
                                objTblCelRenChk2.setBackground(Color.WHITE);
                        }
                        else
                            objTblCelRenChk2.setBackground(Color.WHITE);
                        break;
                    case INT_TBL_REVTECMERDEV:
                        if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_TIPDEV).toString().equals("Cantidad") )
                        {
                            if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_DEVAUT).toString().equals("true") )
                            {
                                if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_ESTPAS4).toString().equals("S") )
                                {
                                    objTblCelRenChk2.setBackground(colFonCol2);
                                }
                                else
                                    objTblCelRenChk2.setBackground(Color.WHITE);
                            }
                            else
                                objTblCelRenChk2.setBackground(Color.WHITE);
                        }
                        else
                            objTblCelRenChk2.setBackground(Color.WHITE);
                        break;
                    case INT_TBL_REVCONINGBOD:
                        if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_TIPDEV).toString().equals("Cantidad") )
                        {
                            if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_DEVAUT).toString().equals("true") )
                            {
                                if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_ESTPAS3).toString().equals("S") )
                                {
                                    objTblCelRenChk2.setBackground(colFonCol2);
                                }
                                else objTblCelRenChk2.setBackground(Color.WHITE);
                            }
                            else objTblCelRenChk2.setBackground(Color.WHITE);
                        }
                        else objTblCelRenChk2.setBackground(Color.WHITE);
                        break;
                    case INT_TBL_INGDEVVENAUT:
                        if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_DEVAUT).toString().equals("true") )
                        {
                            objTblCelRenChk2.setBackground(colFonCol2);
                        }
                        else
                            objTblCelRenChk2.setBackground(Color.WHITE);
                        break;
                    case INT_TBL_PROCOM:
//                        if (objTblMod.getValueAt(objTblCelRenChk2.getRowRender(), INT_TBL_DEVAUT).toString().equals("true") )
//                        {
                            objTblCelRenChk2.setBackground(colFonCol2);
//                        }
//                        else
//                            objTblCelRenChk2.setBackground(Color.WHITE);
//                        break;
                }
            }
            });
            /***********************************************************************************************/
            ZafTblCelEdiTxt objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            objTblCelEdiTxt.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_OBSAUT).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt)
                {

                }                
                @Override
                public void afterEdit(ZafTableEvent evt)
                {

                }
            });
            objTblCelEdiTxt=null; 
            objTblCelLbl = new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_FECAUT).setCellRenderer(objTblCelLbl);
            objTblCelLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt)
                {
                    objTblCelLbl.setToolTipText("Fecha Autorizacón: "+objTblCelLbl.getText());
                }
            });
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DEVAUT).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_DEVDEN).setCellEditor(objTblCelEdiChk);
            tcmAux.getColumn(INT_TBL_DEVCAN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt)
                {

                }        
                @Override
                public void afterEdit(ZafTableEvent evt)
                {

                }
            });
            objTblCelRenChk=null;
            objTblCelEdiChk=null;              
            objTblCelRenButDG=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTRECMER).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTREVTEC).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTREVINGB).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTINGDEVVEN).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTDEVMERREC).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTANE).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTMOSSOL).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTMOSFAC).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new ZafTblCelRenAdapter() {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt)
                {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                        case INT_TBL_BUTRECMER:
                            //José Marín 13/Ene/2014
                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_EXISTE).toString().equals("S") ) 
                                objTblCelRenButDG.setText("..."); 
                            else
                                objTblCelRenButDG.setText("");
                            /// José Marín 13/Ene/2014
                            break;
                        case INT_TBL_BUTREVTEC:
                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_REVTECMERDEV).toString().equals("true") )
                                objTblCelRenButDG.setText("...");
                            else
                                objTblCelRenButDG.setText("");
                            break;
                        case INT_TBL_BUTREVINGB:
                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_REVCONINGBOD).toString().equals("true") )
                                objTblCelRenButDG.setText("...");
                            else
                                objTblCelRenButDG.setText("");
                            break;
                        case INT_TBL_BUTINGDEVVEN:
                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_INGDEVVENAUT).toString().equals("true") )
                                objTblCelRenButDG.setText("...");
                            else
                                objTblCelRenButDG.setText("");
                            break;
                        case INT_TBL_BUTDEVMERREC:
                            if (objTblMod.getValueAt(objTblCelRenButDG.getRowRender(), INT_TBL_DEVCLIMERRCH).toString().equals("true") )
                                objTblCelRenButDG.setText("...");
                            else
                                objTblCelRenButDG.setText("");
                            break;
                        case INT_TBL_BUTANE:
                            objTblCelRenButDG.setText("...");
                            break;
                        case INT_TBL_BUTOBS:
                            objTblCelRenButDG.setText("...");
                            break;
                        case INT_TBL_BUTMOSSOL:
                            objTblCelRenButDG.setText("...");
                            break;
                        case INT_TBL_BUTMOSFAC:
                        objTblCelRenButDG.setText("...");
                        break;
                    }
                }
            });
            objTblCelEdiButGenDG=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUTRECMER).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTREVTEC).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTREVINGB).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTINGDEVVEN).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTDEVMERREC).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTANE).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTMOSSOL).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUTMOSFAC).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt)
                {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                            case INT_TBL_BUTRECMER:
                                //José Marín 
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_EXISTE).toString().equals("N") )
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                //
                                break;
                            case INT_TBL_BUTREVTEC:
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_REVTECMERDEV).toString().equals("false") )
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTREVINGB:
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_REVCONINGBOD).toString().equals("false") )
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTINGDEVVEN:
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_INGDEVVENAUT).toString().equals("false") )
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTDEVMERREC:
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_DEVCLIMERRCH).toString().equals("false") )
                                    objTblCelEdiButGenDG.setCancelarEdicion(true);
                                break;
                            case INT_TBL_BUTANE:
                                break;
                            case INT_TBL_BUTOBS:
                                break;
                            case INT_TBL_BUTMOSSOL:
                                break;
                            case INT_TBL_BUTMOSFAC:
                                break;
                        }
                    }
                }
                @Override
                public void afterEdit(ZafTableEvent evt)
                {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                            case INT_TBL_BUTRECMER:
                                llamarRecMer( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                            case INT_TBL_BUTREVTEC:
                                llamarRevTec( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                            case INT_TBL_BUTREVINGB:
                                llamarRevIngBod( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_REVTECMERDEV).toString()
                                );
                                break;
                            case INT_TBL_BUTINGDEVVEN:
                                llamarIngDevBod( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                            case INT_TBL_BUTDEVMERREC:
                                break;
                            case INT_TBL_BUTANE:
                                llamarVentanaSol( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                            case INT_TBL_BUTOBS:
                                llamarVenObs( objTblMod.getValueAt(intFilSel, INT_TBL_OBSAUT).toString() );
                                break;
                            case INT_TBL_BUTMOSSOL:
                                llamarVenSolVen( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                            case INT_TBL_BUTMOSFAC:
                                llamarVenFac( objTblMod.getValueAt(intFilSel, INT_TBL_CODEMP).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODLOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODTIPDOC).toString()
                                ,objTblMod.getValueAt(intFilSel, INT_TBL_CODDOC).toString()
                                );
                                break;
                        }
                    }
                }
            });
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 1: Solicitudes de devoluciones de ventas");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODEMP));    
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODLOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODTIPDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESCORTIPDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESLARTIPDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMCLI));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_USUSOLDEV));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TIPDEV));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_MOTIVO));
            
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FALSTK));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_REVTEC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_VOL_FAC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANRESDEV));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTMOSSOL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTMOSFAC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 2: Autorización");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DEVAUT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DEVDEN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DEVCAN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECAUT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_OBSAUT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBS));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 3: Bodega");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_RECMERDEV));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTRECMER));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 4: Dep.Téc.");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_REVTECMERDEV));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTREVTEC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 5: Bodega");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_REVCONINGBOD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTREVINGB));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 6: Contabilidad");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_INGDEVVENAUT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTINGDEVVEN));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 7: Cliente");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DEVCLIMERRCH));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTDEVMERREC));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("Paso 8: Resumen");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_PROCOM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_HAYMERRCH));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTANE));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            tcmAux=null;
            setEditable(true); 
            blnRes=true;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    private class ButCotSol extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButCotSol(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la 'Solicitud de devolución' ");
        }
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
        }
    }

    private void llamarVentanaSol(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
       Ventas.ZafVen29.ZafVen29_01 obj = new Ventas.ZafVen29.ZafVen29_01(objParSis, Integer.parseInt( strCodEmp) , Integer.parseInt( strCodLoc) ,  Integer.parseInt( strCodTipDoc)  , Integer.parseInt( strCodDoc)  );
       this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj.show(); 
    }

    private class ButObs extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButObs(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la 'Observación de la autorización'.");
        }
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strObs = ( tblDat.getValueAt(intCol,  INT_TBL_OBSAUT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_OBSAUT  ).toString());
            llamarVenObs(strObs);
        }
    }
    
    private void llamarVenObs(String strObs)
    {
        ZafVen29_02 obj1 = new  ZafVen29_02(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
        obj1.show();
        obj1=null; 
    }

    private class ButMosSol extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButMosSol(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Ver Solicitud de Devolución.");
        }

        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC).toString());
            String strCodTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC).toString());
            String strCodDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODDOC).toString());
            llamarVenSolVen(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
        }
    }
    
    private void llamarVenSolVen(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        Ventas.ZafVen11.ZafVen11 obj1 = new Ventas.ZafVen11.ZafVen11(objParSis , strCodEmp , strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
        obj1.show();
    }

    private class ButMosFac extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButMosFac(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Ver Factura Comercial.");
        }
        
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC).toString());
            String strCodTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC).toString());
            String strCodDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODDOC).toString());
            llamarVenFac(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
        }
    }

    private void llamarVenFac(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        String strsql="";
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strsql="select co_emp, co_locrel, co_tipdocrel, co_docrel from tbm_cabsoldevven where co_emp="+strCodEmp+" AND  co_loc="+strCodLoc+" AND  co_tipdoc="+strCodTipDoc+" AND  co_doc="+strCodDoc+" ";
                rstLoc=stmLoc.executeQuery(strsql);
                if (rstLoc.next())
                {
                    Ventas.ZafVen02.ZafVen02 obj1 = new Ventas.ZafVen02.ZafVen02( objParSis , rstLoc.getString("co_emp") , rstLoc.getString("co_locrel"), rstLoc.getString("co_tipdocrel"), rstLoc.getString("co_docrel"), 14 );
                    this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
                    obj1.show();
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
            }
        }
        catch(java.sql.SQLException Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch(Exception Evt)
        {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    private class ButRecMer extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButRecMer(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la Recepccion de mercaderia a devolver sujeta a revisión .");
        }
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC).toString());
            String strCodTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC).toString());
            String strCodDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODDOC).toString());
            llamarRecMer(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
        }
    }
    
    private void llamarRecMer(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        String strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a.ne_numdoc, a.fe_doc, a.co_mnu from tbm_cabRecMerSolDevVen as a " +
        " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
        " where a.co_emp="+strCodEmp+" and a.co_locrel="+strCodLoc+" and a.co_tipdocrel="+strCodTipDoc+" and a.co_docrel="+strCodDoc+" " +
        " and a.tx_tiprev='B'  AND a.co_mnu=1888 ";
        Ventas.ZafVen29.ZafVen29_03 obj1 = new Ventas.ZafVen29.ZafVen29_03( objParSis , strSql  );
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
        obj1.show();
    }

    private class ButRevTec extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButRevTec(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la revision técnica de mercaderia a devuelta.");
        }
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC).toString());
            String strCodTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC).toString());
            String strCodDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODDOC).toString());
            llamarRevTec(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
        }
    }

    private void llamarRevTec(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        String strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a.ne_numdoc, a.fe_doc, a.co_mnu from tbm_cabRecMerSolDevVen as a " +
        " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
        " where a.co_emp="+strCodEmp+" and a.co_locrel="+strCodLoc+" and a.co_tipdocrel="+strCodTipDoc+" and a.co_docrel="+strCodDoc+" " +
        " and a.tx_tiprev='T'  AND a.co_mnu=1898 ";
        Ventas.ZafVen29.ZafVen29_03 obj1 = new Ventas.ZafVen29.ZafVen29_03( objParSis , strSql  );
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
        obj1.show();
    }

    private class ButRevIngBod extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButRevIngBod(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la revision y confirmación de ingreso a bodega de la mercaderia devuelta.");
        }
        
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC).toString());
            String strCodTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC).toString());
            String strCodDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODDOC).toString());
            String strEstRevTec = ( tblDat.getValueAt(intCol,  INT_TBL_REVTECMERDEV  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_REVTECMERDEV).toString());
            llamarRevIngBod(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strEstRevTec );
        }
    }
    
    private void llamarRevIngBod(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strEstRevTec )
    {
        String strSql="";
        if (strEstRevTec.equals("true"))
        {
            strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a.ne_numdoc, a.fe_doc, a.co_mnu from tbm_cabRecMerSolDevVen as a " +
            " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
            " where a.co_emp="+strCodEmp+" and a.co_locrel="+strCodLoc+" and a.co_tipdocrel="+strCodTipDoc+" and a.co_docrel="+strCodDoc+" " +
            " and a.tx_tiprev='B'  AND a.co_mnu=1908 ";
        }
        else
        {
            strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a.ne_numdoc, a.fe_doc, a.co_mnu from tbm_cabRecMerSolDevVen as a " +
            " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
            " where a.co_emp="+strCodEmp+" and a.co_locrel="+strCodLoc+" and a.co_tipdocrel="+strCodTipDoc+" and a.co_docrel="+strCodDoc+" " +
            " and a.tx_tiprev='B'  AND a.co_mnu=1888  ";
        }
        Ventas.ZafVen29.ZafVen29_03 obj1 = new Ventas.ZafVen29.ZafVen29_03( objParSis , strSql);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER );
        obj1.show();
    }

    private class ButIngDevBod extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButIngDevBod(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la devolución de ventas autorizadas.");
        }
        
        @Override
        public void butCLick()
        {
            int intCol = tblDat.getSelectedRow();
            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP).toString());
            String strCodLoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC).toString());
            String strCodTipDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODTIPDOC).toString());
            String strCodDoc = ( tblDat.getValueAt(intCol,  INT_TBL_CODDOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODDOC).toString());
            llamarIngDevBod(strCodEmp, strCodLoc, strCodTipDoc, strCodDoc );
        }
    }

    private void llamarIngDevBod(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        String strSql="select  a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor, a.ne_numdoc, a.fe_doc, a.co_mnu  from tbm_cabmovinv as a " +
        " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc) " +
        " where a.co_emp="+strCodEmp+" and a.co_locrelsoldevven="+strCodLoc+" and a.co_tipdocrelsoldevven="+strCodTipDoc+" and a.co_docrelsoldevven="+strCodDoc+" " +
        " ";
        ZafVen29_03 obj1 = new ZafVen29_03( objParSis , strSql  );
        this.getParent().add(obj1, JLayeredPane.DEFAULT_LAYER );
        obj1.show();
    }

    private class ButDevMerRec extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButDevMerRec(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl,intIdx, "Muestra la devolución al cliente de la mercaderia rechazada.");
        }
        
        @Override
        public void butCLick()
        {
           int intCol = tblDat.getSelectedRow();
        }
    }

    private void llamarDevMerRec(String strObs)
    {
        //ZafVen29_02 obj1 = new  ZafVen29_02(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs );
        //obj1.show();
        //obj1=null;
    }
    
    private void mostrarMensajeError(String strMsg)
    {
        JOptionPane.showMessageDialog(this, strMsg, strTitulo, JOptionPane.ERROR_MESSAGE);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        butGrp = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipdoc = new javax.swing.JLabel();
        lblCli1 = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        lblSol1 = new javax.swing.JLabel();
        txtCodSol = new javax.swing.JTextField();
        txtDesSol = new javax.swing.JTextField();
        butBusTipDoc = new javax.swing.JButton();
        butBusCli = new javax.swing.JButton();
        butBusSol = new javax.swing.JButton();
        lblTipDev = new javax.swing.JLabel();
        lblEstAut = new javax.swing.JLabel();
        cboTipDev = new javax.swing.JComboBox();
        cboEstAut = new javax.swing.JComboBox();
        lblEmp = new javax.swing.JLabel();
        cboEmp = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        radPasRea = new javax.swing.JRadioButton();
        radPasPen = new javax.swing.JRadioButton();
        cboPasEst = new javax.swing.JComboBox();
        lblMotDev = new javax.swing.JLabel();
        txtCodMotDev = new javax.swing.JTextField();
        txtDesLarMotDev = new javax.swing.JTextField();
        butMotDev = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        chkMosVolFac = new javax.swing.JCheckBox();
        PanRpt = new javax.swing.JPanel();
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

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(115, 100));

        panFil.setPreferredSize(new java.awt.Dimension(100, 200));
        panFil.setRequestFocusEnabled(false);
        panFil.setLayout(null);

        butGrp.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 76, 400, 20);

        butGrp.add(optFil);
        optFil.setText("Solo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 96, 400, 20);

        lblTipdoc.setText("Tipo de documento:");
        lblTipdoc.setToolTipText("Tipo de documento:");
        panFil.add(lblTipdoc);
        lblTipdoc.setBounds(24, 136, 120, 20);

        lblCli1.setText("Cliente:");
        lblCli1.setToolTipText("Cliente:");
        panFil.add(lblCli1);
        lblCli1.setBounds(24, 156, 120, 20);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(144, 136, 56, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 136, 460, 20);

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
        txtCodCli.setBounds(144, 156, 56, 20);

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
        txtDesLarCli.setBounds(200, 156, 460, 20);

        lblSol1.setText("Solicitante:");
        lblSol1.setToolTipText("Solicitante:");
        panFil.add(lblSol1);
        lblSol1.setBounds(24, 176, 120, 20);

        txtCodSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSolActionPerformed(evt);
            }
        });
        txtCodSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSolFocusLost(evt);
            }
        });
        panFil.add(txtCodSol);
        txtCodSol.setBounds(144, 176, 56, 20);

        txtDesSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesSolActionPerformed(evt);
            }
        });
        txtDesSol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesSolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesSolFocusLost(evt);
            }
        });
        panFil.add(txtDesSol);
        txtDesSol.setBounds(200, 176, 460, 20);

        butBusTipDoc.setText("jButton2");
        butBusTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusTipDocActionPerformed(evt);
            }
        });
        panFil.add(butBusTipDoc);
        butBusTipDoc.setBounds(660, 136, 20, 20);

        butBusCli.setText("jButton2");
        butBusCli.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusCliActionPerformed(evt);
            }
        });
        panFil.add(butBusCli);
        butBusCli.setBounds(660, 156, 20, 20);

        butBusSol.setText("jButton2");
        butBusSol.setPreferredSize(new java.awt.Dimension(20, 20));
        butBusSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBusSolActionPerformed(evt);
            }
        });
        panFil.add(butBusSol);
        butBusSol.setBounds(660, 176, 20, 20);

        lblTipDev.setText("Tipo de devolución:");
        lblTipDev.setToolTipText("Tipo de devolución:");
        panFil.add(lblTipDev);
        lblTipDev.setBounds(24, 216, 120, 20);

        lblEstAut.setText("Estado de autorización:");
        lblEstAut.setToolTipText("Estado de autorización:");
        panFil.add(lblEstAut);
        lblEstAut.setBounds(356, 216, 120, 20);

        cboTipDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipDevActionPerformed(evt);
            }
        });
        panFil.add(cboTipDev);
        cboTipDev.setBounds(144, 216, 184, 20);

        cboEstAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstAutActionPerformed(evt);
            }
        });
        panFil.add(cboEstAut);
        cboEstAut.setBounds(476, 216, 184, 20);

        lblEmp.setText("Empresa:"); // NOI18N
        panFil.add(lblEmp);
        lblEmp.setBounds(404, 76, 80, 20);

        cboEmp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboEmpItemStateChanged(evt);
            }
        });
        cboEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpActionPerformed(evt);
            }
        });
        panFil.add(cboEmp);
        cboEmp.setBounds(484, 76, 176, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Paso en el que esta:"));
        jPanel1.setLayout(null);

        buttonGroup1.add(radPasRea);
        radPasRea.setText("Realizado");
        jPanel1.add(radPasRea);
        radPasRea.setBounds(112, 20, 100, 20);

        buttonGroup1.add(radPasPen);
        radPasPen.setSelected(true);
        radPasPen.setText("Pendiente");
        jPanel1.add(radPasPen);
        radPasPen.setBounds(12, 20, 100, 20);

        cboPasEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPasEstActionPerformed(evt);
            }
        });
        jPanel1.add(cboPasEst);
        cboPasEst.setBounds(212, 20, 424, 20);

        panFil.add(jPanel1);
        jPanel1.setBounds(24, 256, 660, 52);

        lblMotDev.setText("Motivo:");
        lblMotDev.setToolTipText("Motivo:");
        panFil.add(lblMotDev);
        lblMotDev.setBounds(24, 196, 120, 20);

        txtCodMotDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMotDevActionPerformed(evt);
            }
        });
        txtCodMotDev.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMotDevFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMotDevFocusLost(evt);
            }
        });
        panFil.add(txtCodMotDev);
        txtCodMotDev.setBounds(144, 196, 56, 20);

        txtDesLarMotDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarMotDevActionPerformed(evt);
            }
        });
        txtDesLarMotDev.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarMotDevFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarMotDevFocusLost(evt);
            }
        });
        panFil.add(txtDesLarMotDev);
        txtDesLarMotDev.setBounds(200, 196, 460, 20);

        butMotDev.setText("jButton2");
        butMotDev.setPreferredSize(new java.awt.Dimension(20, 20));
        butMotDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMotDevActionPerformed(evt);
            }
        });
        panFil.add(butMotDev);
        butMotDev.setBounds(660, 196, 20, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Local");
        panFil.add(lblLoc);
        lblLoc.setBounds(24, 116, 120, 20);

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
        txtCodLoc.setBounds(144, 116, 56, 20);

        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(200, 116, 460, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(660, 116, 20, 20);

        chkMosVolFac.setText("Mostrar sólo los documentos que tienen \"Volver a facturar\"");
        chkMosVolFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosVolFacActionPerformed(evt);
            }
        });
        panFil.add(chkMosVolFac);
        chkMosVolFac.setBounds(24, 236, 400, 23);

        tabFrm.addTab("Filtro", panFil);

        PanRpt.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(454, 400));

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

        PanRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", PanRpt);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(199, 30));
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

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        limpiarText();  
}//GEN-LAST:event_optTodActionPerformed


    private void limpiarText()
    {
        txtCodLoc.setText("");
        txtNomLoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodCli.setText("");
        txtDesLarCli.setText("");
        txtCodSol.setText("");
        txtDesSol.setText("");
        txtCodMotDev.setText("");
        txtDesLarMotDev.setText("");
        cboTipDev.setSelectedIndex(0);
        cboEstAut.setSelectedIndex(0);
        cboPasEst.setSelectedIndex(0);   
        chkMosVolFac.setSelected(false);
    }

private void cboTipDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipDevActionPerformed
    if (cboTipDev.getSelectedIndex() > 0 )
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_cboTipDevActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
    if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }        
}//GEN-LAST:event_butConActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
    cerrarVen();
//GEN-LAST:event_butCerActionPerformed
}   
       
    private void cerrarVen()
    {
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 )
        {
            Runtime.getRuntime().gc();
            dispose();
        }
     }    

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc=txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
    {
        if (txtDesCorTipDoc.getText().equals(""))
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        }
        else
            BuscarTipDoc("a.tx_descor",txtDesCorTipDoc.getText(),1);
    }
    else
        txtDesCorTipDoc.setText(strDesCorTipDoc); 
}//GEN-LAST:event_txtDesCorTipDocFocusLost
    
    public void BuscarTipDoc(String campo,String strBusqueda,int tipo)
    {
        objVenConTipdoc.setTitle("Listado de Vendedores");
        if (objVenConTipdoc.buscar(campo, strBusqueda ))
        {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
            strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
            strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
            optFil.setSelected(true);
        }
        else
        {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc=objVenConTipdoc.getValueAt(1);
                strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
                strDesLarTipDoc=objVenConTipdoc.getValueAt(3); 
                optFil.setSelected(true);
            }
            else
            {
                txtCodTipDoc.setText(strCodTipDoc); 
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
    {
        if (txtDesLarTipDoc.getText().equals(""))
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        }
        else 
            BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
    }
    else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butBusTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusTipDocActionPerformed
    objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
    objVenConTipdoc.setCampoBusqueda(1);
    objVenConTipdoc.show();
    if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
    {
        txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
        txtDesCorTipDoc.setText(objVenConTipdoc.getValueAt(2));
        txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
        strCodTipDoc=objVenConTipdoc.getValueAt(1);
        strDesCorTipDoc=objVenConTipdoc.getValueAt(2);
        strDesLarTipDoc=objVenConTipdoc.getValueAt(3);
        optFil.setSelected(true);
    }
}//GEN-LAST:event_butBusTipDocActionPerformed

private void txtCodSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSolActionPerformed
    txtCodSol.transferFocus();
}//GEN-LAST:event_txtCodSolActionPerformed

private void txtCodSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSolFocusGained
    strCodSol=txtCodSol.getText();
    txtCodSol.selectAll();
}//GEN-LAST:event_txtCodSolFocusGained

private void txtCodSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSolFocusLost
    if (!txtCodSol.getText().equalsIgnoreCase(strCodSol))
    {
        if (txtCodSol.getText().equals(""))
        {
            txtCodSol.setText("");
            txtDesSol.setText("");
        }
        else 
            BuscarSolicitante("a.co_usr",txtCodSol.getText(),0);
    }
    else
        txtCodSol.setText(strCodSol);
}//GEN-LAST:event_txtCodSolFocusLost

private void txtDesSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesSolActionPerformed
    txtDesSol.transferFocus();
}//GEN-LAST:event_txtDesSolActionPerformed

private void txtDesSolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesSolFocusGained
    strDesSol=txtDesSol.getText();
    txtDesSol.selectAll();
}//GEN-LAST:event_txtDesSolFocusGained

private void butBusSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusSolActionPerformed
    String strSQL = null;
    objVenConVen.setTitle("Listado de Solicitantes");
    if ( objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
    {
        if ((vecEmp.elementAt(cboEmp.getSelectedIndex()).toString().equals("0")))
        { 
            strSQL=" select distinct a.co_usr, a.tx_nom  from tbr_usremp as b " +
            " inner join tbm_usr as a on (a.co_usr=b.co_usr) " + 
            " where b.st_ven='S' " +
            " and a.st_reg not in ('I') " +
            " order by a.tx_nom ";                
        }
        else
        {
            strSQL=" select a.co_usr, a.tx_nom  from tbr_usremp as b " +
            " inner join tbm_usr as a on (a.co_usr=b.co_usr) " + 
            " where b.co_emp="+vecEmp.elementAt(cboEmp.getSelectedIndex())+" " +
            " and b.st_ven='S' " +
            " and a.st_reg not in ('I') " +
            " order by a.tx_nom ";
        }
        objVenConVen.setSentenciaSQL( strSQL );
    }     
    objVenConVen.setCampoBusqueda(1);
    objVenConVen.show();
    if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
    {
        txtCodSol.setText(objVenConVen.getValueAt(1));
        txtDesSol.setText(objVenConVen.getValueAt(2));
        strCodSol=objVenConVen.getValueAt(1);
        strDesSol=objVenConVen.getValueAt(2);
        optFil.setSelected(true);
    }
}//GEN-LAST:event_butBusSolActionPerformed

private void txtDesSolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesSolFocusLost
    if (!txtDesSol.getText().equalsIgnoreCase(strDesSol))
    {
        if (txtDesSol.getText().equals(""))
        {
            txtCodSol.setText("");
            txtDesSol.setText("");
        }
        else 
            BuscarSolicitante("a.tx_nom",txtDesSol.getText(),1);
    }
    else
        txtDesSol.setText(strDesSol);
}//GEN-LAST:event_txtDesSolFocusLost

    public void BuscarSolicitante(String campo,String strBusqueda,int tipo)
    {
        objVenConVen.setTitle("Listado de Vendedores");
        if ( objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            if (!(vecEmp.elementAt(cboEmp.getSelectedIndex()).toString().equals("0")))
            {
                String strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
                " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
                " where b.co_emp="+vecEmp.elementAt(cboEmp.getSelectedIndex())+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";
                objVenConVen.setSentenciaSQL( strSQL );
            }
        }
        if (objVenConVen.buscar(campo, strBusqueda ))
        {
            txtCodSol.setText(objVenConVen.getValueAt(1));
            txtDesSol.setText(objVenConVen.getValueAt(2));
            strCodSol=objVenConVen.getValueAt(1);
            strDesSol=objVenConVen.getValueAt(2);
            optFil.setSelected(true);
        }
        else
        {
            objVenConVen.setCampoBusqueda(tipo);
            objVenConVen.cargarDatos();
            objVenConVen.show();
            if (objVenConVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
            {
                txtCodSol.setText(objVenConVen.getValueAt(1));
                txtDesSol.setText(objVenConVen.getValueAt(2));
                strCodSol=objVenConVen.getValueAt(1);
                strDesSol=objVenConVen.getValueAt(2);
                optFil.setSelected(true);
            }
            else
            {
                txtCodSol.setText(strCodSol);
                txtDesSol.setText(strDesSol);
            }
        }
    }

private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
    txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
    strCodCli=txtCodCli.getText();
    txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

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
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodCli.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtCodCliFocusLost
  
private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
    txtDesLarCli.transferFocus();
}//GEN-LAST:event_txtDesLarCliActionPerformed

private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
    strDesLarCli=txtDesLarCli.getText();
    txtDesLarCli.selectAll();
}//GEN-LAST:event_txtDesLarCliFocusGained

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
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodCli.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtDesLarCliFocusLost

private void butBusCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBusCliActionPerformed
    mostrarVenConCli(0);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodCli.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_butBusCliActionPerformed

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    //Validar que el programa no funcione en la empresa grupo
    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
    {
        mostrarMsgInf("Este programa no funciona en la empresa grupo.\nIngrese a otra empresa y vuelva a intentarlo.");
        dispose();
    }
    configurar_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

private void cboEstAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstAutActionPerformed
    if (cboEstAut.getSelectedIndex() > 0 )
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_cboEstAutActionPerformed

private void cboPasEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPasEstActionPerformed
    if (cboPasEst.getSelectedIndex() > 0 )
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_cboPasEstActionPerformed

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    cerrarVen();
}//GEN-LAST:event_formInternalFrameClosing

private void cboEmpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboEmpItemStateChanged

}//GEN-LAST:event_cboEmpItemStateChanged

private void cboEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpActionPerformed

}//GEN-LAST:event_cboEmpActionPerformed

private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
    txtCodLoc.transferFocus();
}//GEN-LAST:event_txtCodLocActionPerformed

private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
    strCodLoc=txtCodLoc.getText();
    txtCodLoc.selectAll();
}//GEN-LAST:event_txtCodLocFocusGained

private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
    {
        if (txtCodLoc.getText().equals(""))
        {
            txtCodLoc.setText("");
            txtNomLoc.setText("");
        }
        else
        {
            mostrarVenConLoc(1);
        }
    }
    else
        txtCodLoc.setText(strCodLoc);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodLoc.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtCodLocFocusLost

private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
    txtNomLoc.transferFocus();
}//GEN-LAST:event_txtNomLocActionPerformed

private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
    strNomLoc=txtNomLoc.getText();
    txtNomLoc.selectAll();
}//GEN-LAST:event_txtNomLocFocusGained

private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
    {
        if (txtNomLoc.getText().equals(""))
        {
            txtCodLoc.setText("");
            txtNomLoc.setText("");
        }
        else
        {
            mostrarVenConLoc(2);
        }
    }
    else
        txtNomLoc.setText(strNomLoc);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodLoc.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtNomLocFocusLost

private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
    mostrarVenConLoc(0);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodLoc.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_butLocActionPerformed

private void chkMosVolFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosVolFacActionPerformed
    if (chkMosVolFac.isSelected())
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_chkMosVolFacActionPerformed

private void txtCodMotDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotDevActionPerformed
    txtCodMotDev.transferFocus();
}//GEN-LAST:event_txtCodMotDevActionPerformed

private void txtCodMotDevFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotDevFocusGained
    strCodMotDev=txtCodMotDev.getText();
    txtCodMotDev.selectAll();
}//GEN-LAST:event_txtCodMotDevFocusGained

private void txtCodMotDevFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotDevFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtCodMotDev.getText().equalsIgnoreCase(strCodMotDev))
    {
        if (txtCodMotDev.getText().equals(""))
        {
            txtCodMotDev.setText("");
            txtDesLarMotDev.setText("");
        }
        else
        {
            mostrarVenConMotDev(1);
        }
    }
    else
        txtCodMotDev.setText(strCodMotDev);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodMotDev.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtCodMotDevFocusLost

private void txtDesLarMotDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarMotDevActionPerformed
    txtDesLarMotDev.transferFocus();
}//GEN-LAST:event_txtDesLarMotDevActionPerformed

private void txtDesLarMotDevFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotDevFocusGained
    strDesLarMotDev=txtDesLarMotDev.getText();
    txtDesLarMotDev.selectAll();
}//GEN-LAST:event_txtDesLarMotDevFocusGained

private void txtDesLarMotDevFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotDevFocusLost
    //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtDesLarMotDev.getText().equalsIgnoreCase(strDesLarMotDev))
    {
        if (txtDesLarMotDev.getText().equals(""))
        {
            txtCodMotDev.setText("");
            txtDesLarMotDev.setText("");
        }
        else
        {
            mostrarVenConMotDev(2);
        }
    }
    else
        txtDesLarMotDev.setText(strDesLarMotDev);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodMotDev.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtDesLarMotDevFocusLost

private void butMotDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotDevActionPerformed
    mostrarVenConMotDev(0);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodMotDev.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_butMotDevActionPerformed

    /**
    * MODIFICADO EFLORESA 2012-04-25
    *      ADICIONAR FILTRO PARA CONSULTAR LAS SOLICITUDES DE DEVOLUCION DE VENTAS PENDIENTES DE REALIZAR EN BODEGA.
    * MODIFICADO EFLORESA 2012-07-30
    *      ADICIONAR FILTRO PARA CONSULTAR POR EMPRESA/GRUPO
    */
    private String condiconSql()
    {
        String sqlFil="";
        if (optFil.isSelected())
        {
            if (txtCodLoc.getText().length()>0)
                sqlFil+=" AND b1.co_loc=" + txtCodLoc.getText();
            if (!txtCodTipDoc.getText().equals(""))
                sqlFil+=" AND b1.co_tipdoc="+txtCodTipDoc.getText();
            if (!txtCodCli.getText().equals(""))
                sqlFil+=" AND b1.co_cli="+txtCodCli.getText();
            if (!txtCodSol.getText().equals(""))
                sqlFil+=" AND b1.co_usrsol="+txtCodSol.getText();
            if (cboTipDev.getSelectedIndex() > 0 )
                sqlFil+=" AND b1.st_tipDev = '"+vecCboTipDev.get(cboTipDev.getSelectedIndex()).toString()+"'";
            if (txtCodMotDev.getText().length()>0)
                sqlFil+=" AND b1.co_motDev=" + txtCodMotDev.getText();
            if (cboEstAut.getSelectedIndex() > 0 )
                sqlFil+=" AND b1.st_aut = '"+vecCboEstAut.get(cboEstAut.getSelectedIndex()).toString()+"'";
            if (chkMosVolFac.isSelected())
            {
                sqlFil+=" AND b1.st_volFacMerSinDev='S'";
            }
        }
        if (objSelFec.isCheckBoxChecked() )
        {
            switch (objSelFec.getTipoSeleccion())
            {
                case 0: //Búsqueda por rangos
                    sqlFil+=" AND b1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil+=" AND b1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil+=" AND b1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }
        }
        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            if (!(vecEmp.elementAt(cboEmp.getSelectedIndex()).toString().equals("0")))
            {
                sqlFil+=" and b1.co_emp= "+vecEmp.elementAt(cboEmp.getSelectedIndex()).toString() + " ";
            }
        }
        return sqlFil;             
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
            consultar(condiconSql());  
            tabFrm.setSelectedIndex(1);
            objThrGUI=null;
        }
    }
     
    /*
    * MODIFICADO EFLORESA 2012-04-17
    * DESCUENTO ESPECIAL
    */
    private void consultar(String strFil)
    {
        Connection conn;
        Statement stm;
        ResultSet rst;
        String strSql="", strSQLAux="";
        boolean blnExiMerRec=false;
        try
        {
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Obteniendo datos...");
            conn=DriverManager.getConnection( objParSis.getStringConexion() , objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conn!=null)
            {
                stm=conn.createStatement();
                if (!(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()))
                {
                    strSQLAux=" b1.co_emp= " + objParSis.getCodigoEmpresa() + " AND ";
                }
                strSql="SELECT ";
                strSql+=" (select x.st_devfalstk from tbm_detsoldevven as x  where x.co_emp=a.co_emp and  x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc= a.co_doc and x.st_devfalstk='S' group by x.st_devfalstk ) as falstk";
                strSql+=" , CASE WHEN ( ";
                strSql+=" select sum((x1.nd_candev - x1.nd_canvolfac)) as dat from  tbm_cabsoldevven as x";
                strSql+=" inner join tbm_detsoldevven as x1 on (x1.co_emp=x.co_emp and x1.co_loc=x.co_loc and x1.co_tipdoc=x.co_tipdoc and x1.co_doc=x.co_doc )";
                strSql+=" where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc";
                strSql+=" ) != 0  THEN 'N' ELSE 'S' END as st_volfac";
                strSql+=" , a.co_emp,  a2.ne_numdoc as numDocfac,  a.co_loc , a.co_tipdoc, a1.tx_descor, a1.tx_deslar, a.st_tipdev, a.co_Doc, a.ne_numdoc, a.fe_doc, a.co_cli ";
                strSql+=" , a.tx_nomcli, a.tx_nomusrsol ,a.st_revTec";
                strSql+=" , a.st_aut, a.st_reg, a.st_recMerDev, a.st_revTecMerDev, a.st_revBodMerDev, a.st_merAceIngSis, a.st_merRecDevCli, a.st_exiMerRec";
                strSql+=" ,a.tx_obsaut, a.fe_aut, a.st_impguiremaut ";
                ////////////////////////////
                //// José Marín 10-Ene-2014
                ////
                //Se agrego para habilitar el boton en el caso de que cuando sea una recepción 
                //de parciales 
                ////
                strSql+=" ,( SELECT CASE WHEN exists (select true FROM tbm_detSolDevVen as EXI";
                strSql+=" WHERE EXI.co_emp=a.co_emp and EXI.co_loc=a.co_loc";
                strSql+=" AND EXI.co_tipDoc=a.co_tipDoc and EXI.co_doc=a.co_doc and EXI.nd_canRec>0";
                strSql+=" ) = TRUE   then 'S' else 'N' END ) as existe ";
                ////////////        	   
                strSql+=" , a.st_volFacMerSinDev";
                strSql+=" , (CASE WHEN a.st_aut IN ('D', 'C') OR a3.st_canProCom='S' THEN 'S' ELSE 'N' END) AS st_proCom, a.st_canResDev, a5.tx_desLar as tx_motivo";
                strSql+=" FROM tbm_cabSolDevVen AS a";
                strSql+=" INNER JOIN tbm_cabtipdoc AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc)";
                strSql+=" INNER JOIN tbm_cabmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_locrel AND a2.co_tipdoc=a.co_tipdocrel AND a2.co_doc=a.co_docrel)";
                strSql+=" INNER JOIN (";
                strSql+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSql+=" , (CASE WHEN SUM(b2.nd_canDev-b2.nd_canNunRec-b2.nd_canRevTecRec-b2.nd_canRevBodRec-b2.nd_canAceIngSis)=0 THEN 'S' ELSE 'N' END) AS st_canProCom";
                strSql+=" FROM tbm_cabSolDevVen AS b1";
                strSql+=" INNER JOIN tbm_detSolDevVen AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
                strSql+=" WHERE " + strSQLAux + " b1.st_reg='A' " + strFil;
                strSql+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSql+=" ) AS a3 ON (a.co_emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_tipDoc=a3.co_tipDoc AND a.co_doc=a3.co_doc)";
                strSql+=" LEFT JOIN tbm_motDevInv as a5 ON (a.co_emp=a5.co_emp AND a.co_motDev=a5.co_motDev) ";
                
//Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()!=1)
                {
                    strSql+=" INNER JOIN";
                    strSql+=" (";
                    strSql+=" SELECT co_empRel AS co_emp, co_locRel AS co_loc";
                    strSql+=" FROM tbr_locPrgUsr";
                    strSql+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSql+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSql+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSql+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSql+=" ) AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc)";
                }
                strSql+=" WHERE a.st_reg='A'";
                if (radPasRea.isSelected())
                {
                    if (cboPasEst.getSelectedIndex()>0)
                    {
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P1"))  
                            strSql+=" AND a.st_aut = 'P'"; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P2"))  
                            strSql+=" AND a.st_aut <> 'P'"; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P3"))  
                            strSql+=" AND a.st_recMerDev = 'S' and st_impguiremaut='S' "; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P4"))  
                            strSql+=" AND a.st_revTecMerDev = 'S' and st_impguiremaut='S' "; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P5"))  
                            strSql+=" AND a.st_revBodMerDev = 'S' and st_impguiremaut='S' "; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P6"))  
                            strSql+=" AND a.st_merAceIngSis = 'S'"; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P7"))  
                            strSql+=" AND a.st_merRecDevCli = 'S'"; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P8"))  
                            strSql+=" AND CASE WHEN a.st_tipDev IN ('C') THEN "+
                            " a.st_recMerDev = 'S'  AND " +
                            " CASE WHEN a.st_revTec IN ('S') THEN a.st_revTecMerDev='S' ELSE  1=1 END  "+ //a.st_revTecMerDev = 'S' " +
                            " AND a.st_revBodMerDev = 'S'  AND a.st_merAceIngSis = 'S' " +
                            " AND  CASE WHEN a.st_exiMerRec IN ('S') THEN a.st_merRecDevCli='S' ELSE  1=1 END   ELSE  a.st_merAceIngSis = 'S' END  "; 
                    }
                } 
                if (radPasPen.isSelected())
                {
                    if (cboPasEst.getSelectedIndex() > 0 )
                    {
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P1"))
                            strSql+=" AND a.st_aut = 'P'"; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P2"))
                            strSql+=" AND a.st_aut <> 'P'"; 
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P3"))
                            strSql+=" AND a.st_recMerDev = 'N' and st_impguiremaut='S' and a.st_merAceIngSis = 'N'";
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P4"))
                            strSql+=" AND a.st_revTecMerDev = 'N' and st_impguiremaut='S' and a.st_merAceIngSis = 'N'";
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P5"))
                            strSql+=" AND a.st_revBodMerDev = 'N' and st_impguiremaut='S' and a.st_merAceIngSis = 'N'";
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P6"))
                            strSql+=" AND a.st_merAceIngSis = 'N'";
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P7"))
                            strSql+=" AND a.st_merRecDevCli = 'N'";
                        if (vecCboPasEst.get(cboPasEst.getSelectedIndex()).toString().equals("P8"))
                            strSql+=" AND (CASE WHEN a.st_aut IN ('D', 'C') OR a3.st_canProCom='S' THEN 'S' ELSE 'N' END)='N'";
                    } 
                }
                strSql+=" ORDER BY a.co_emp, a.co_loc, a.ne_numdoc ";
                Vector vecData = new Vector();
                rst = stm.executeQuery(strSql);
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while(rst.next())
                {
                    Vector vecReg = new Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_NUMFAC, rst.getString("numDocfac") );
                    vecReg.add(INT_TBL_CODEMP, rst.getString("co_emp") );
                    vecReg.add(INT_TBL_CODLOC, rst.getString("co_loc") );
                    vecReg.add(INT_TBL_CODTIPDOC, rst.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_DESCORTIPDOC, rst.getString("tx_descor") );
                    vecReg.add(INT_TBL_DESLARTIPDOC, rst.getString("tx_deslar") );
                    vecReg.add(INT_TBL_CODDOC, rst.getString("co_Doc") );
                    vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc") );
                    vecReg.add(INT_TBL_CODCLI, rst.getString("co_cli") );
                    vecReg.add(INT_TBL_NOMCLI, rst.getString("tx_nomcli") );
                    vecReg.add(INT_TBL_USUSOLDEV, rst.getString("tx_nomusrsol") );
                    vecReg.add(INT_TBL_TIPDEV, (rst.getString("st_tipdev").equals("C")?"Cantidad":(rst.getString("st_tipdev").equals("P")?"Porcentaje":(rst.getString("st_tipdev").equals("E")?"Descuento Especial":"Valores"))) );
                    vecReg.add(INT_TBL_MOTIVO, rst.getString("tx_motivo") );
                    
                    vecReg.add(INT_TBL_FALSTK , (rst.getString("falstk")==null?false:(rst.getString("falstk").equals("S")?true:false)));
                    vecReg.add(INT_TBL_REVTEC, (rst.getString("st_revTec")==null?false:(rst.getString("st_revTec").equals("S")?true:false)));
                    vecReg.add(INT_TBL_VOL_FAC, (rst.getString("st_volFacMerSinDev")==null?false:(rst.getString("st_volFacMerSinDev").equals("S")?true:false)));
                    vecReg.add(INT_TBL_CANRESDEV, rst.getString("st_canResDev")==null?false:(rst.getString("st_canResDev").equals("S")?true:false));
                    vecReg.add(INT_TBL_BUTMOSSOL,"...");
                    vecReg.add(INT_TBL_BUTMOSFAC,"...");
                    vecReg.add(INT_TBL_DEVAUT, (rst.getString("st_aut").equals("A")?true:false));
                    vecReg.add(INT_TBL_DEVDEN, (rst.getString("st_aut").equals("D")?true:false));
                    vecReg.add(INT_TBL_DEVCAN, (rst.getString("st_aut").equals("C")?true:false));
                    vecReg.add(INT_TBL_FECAUT, rst.getString("fe_aut") );
                    vecReg.add(INT_TBL_OBSAUT, rst.getString("tx_obsaut") );
                    vecReg.add(INT_TBL_BUTOBS, ".." );
                    vecReg.add(INT_TBL_RECMERDEV, (rst.getString("st_recMerDev").equals("S")?true:false));
                    vecReg.add(INT_TBL_BUTRECMER,"...");
                    vecReg.add(INT_TBL_REVTECMERDEV, (rst.getString("st_revTecMerDev").equals("S")?true:false));
                    vecReg.add(INT_TBL_BUTREVTEC,"...");
                    vecReg.add(INT_TBL_REVCONINGBOD, (rst.getString("st_revBodMerDev").equals("S")?true:false));
                    vecReg.add(INT_TBL_BUTREVINGB,"...");
                    vecReg.add(INT_TBL_INGDEVVENAUT, (rst.getString("st_merAceIngSis").equals("S")?true:false));          
                    vecReg.add(INT_TBL_BUTINGDEVVEN,"...");
                    vecReg.add(INT_TBL_DEVCLIMERRCH, (rst.getString("st_merRecDevCli").equals("S")?true:false));
                    vecReg.add(INT_TBL_BUTDEVMERREC,"...");
                    blnExiMerRec=(rst.getString("st_exiMerRec").equals("S")?true:false); 
                    //vecReg.add(INT_TBL_PROCOM, (rst.getString("st_merAceIngSis").equals("S")?true:false));
                    vecReg.add(INT_TBL_PROCOM, (rst.getString("st_proCom").equals("S")?true:false));
                    vecReg.add(INT_TBL_HAYMERRCH, blnExiMerRec);  
                    vecReg.add(INT_TBL_BUTANE,"..."); 
                    vecReg.add(INT_TBL_ESTPAS3, rst.getString("st_impguiremaut") );
                    vecReg.add(INT_TBL_ESTPAS4, rst.getString("st_revtec") );
                    vecReg.add(INT_TBL_ESTPAS5,"");
                    ////////////
                    //José Marín 10-Ene-2014 
                    vecReg.add(INT_TBL_EXISTE,rst.getString("existe"));
                    /////////
                    vecData.add(vecReg); 
                }
                rst.close();
                stm.close();
                conn.close();
                rst=null;
                stm=null;
                conn=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecData);
                tblDat.setModel(objTblMod);
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
 
    //Para adicionar el Tooltip
    private class ZafMouMotAda extends MouseMotionAdapter
    {
        @Override
        public void mouseMoved(MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_NUMFAC:
                    strMsg="Número de factura";
                    break;
                case INT_TBL_CODEMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_CODLOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_CODTIPDOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DESCORTIPDOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DESLARTIPDOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CODDOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_FECDOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_CODCLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_NOMCLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_USUSOLDEV:
                    strMsg="Usuario que solicita la devolución";
                    break;
                case INT_TBL_TIPDEV:
                    strMsg="Tipo de devolución";
                    break;
                case INT_TBL_MOTIVO:
                    strMsg="Motivo de la Devolución";
                    break;    
                    
                case INT_TBL_FALSTK:
                    strMsg="Faltante de stock";
                    break;
                case INT_TBL_REVTEC:
                    strMsg="¿Es necesaria revisión técnica?";
                    break;
                case INT_TBL_VOL_FAC:
                    strMsg="Volver a facturar";
                    break;
                case INT_TBL_BUTMOSSOL:
                    strMsg="Muestra la \"Solicitud de devolución\"";
                    break;
                case INT_TBL_BUTMOSFAC:
                    strMsg="Muestra la \"Factura de ventas\"";
                    break;
                case INT_TBL_DEVAUT:
                    strMsg="Autorizar";
                    break;
                case INT_TBL_DEVDEN:
                    strMsg="Denegar";
                    break;
                case INT_TBL_DEVCAN:
                    strMsg="Cancelar";
                    break;                     
                case INT_TBL_FECAUT:
                    strMsg="Fecha de autorización";
                    break;
                case INT_TBL_OBSAUT:
                    strMsg="Observación de autorización";
                    break;
                case INT_TBL_BUTOBS:
                    strMsg="Muestra la \"Observación de la autorización\"";
                    break;
                case INT_TBL_RECMERDEV:
                    strMsg="Recepción de mercadería a devolver sujeta a revisión";
                    break;
                case INT_TBL_BUTRECMER:
                    strMsg="Muestra el listado de \"Recepciones de mercadería a devolver sujeta a revisión\"";
                    break;
                case INT_TBL_REVTECMERDEV:
                    strMsg="Revisión técnica de la mercadería devuelta";
                    break;
                case INT_TBL_BUTREVTEC:
                    strMsg="Muestra el listado de \"Revisiones técnicas de la mercadería devuelta\"";
                    break;
                case INT_TBL_REVCONINGBOD:
                    strMsg="Revisión y confirmación de ingreso a bodega de la mercadería devuelta";
                    break;
                case INT_TBL_BUTREVINGB:
                    strMsg="Muestra el listado de \"Revisiones y confirmaciones de ingreso a bodega de la mercadería devuelta\"";
                    break;
                case INT_TBL_INGDEVVENAUT:
                    strMsg="Devoluciones de ventas";
                    break;
                case INT_TBL_BUTINGDEVVEN:
                    strMsg="Muestra el listado de \"Devoluciones de ventas\"";
                    break;
                case INT_TBL_DEVCLIMERRCH:
                    strMsg="Devolución al cliente de la mercadería rechazada";
                    break;
                case INT_TBL_BUTDEVMERREC:
                    strMsg="Muestra el listado de \"Devoluciones al cliente de la mercadería rechazada\"";
                    break;
                case INT_TBL_PROCOM:
                    strMsg="Proceso completo";
                    break;   
                case INT_TBL_HAYMERRCH:
                    strMsg="¿Hay mercadería rechazada?";
                    break;
                case INT_TBL_BUTANE:
                    strMsg="Muestra el \"Resumen de la solicitud de devolución de ventas\"";
                    break;       
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanRpt;
    private javax.swing.JButton butBusCli;
    private javax.swing.JButton butBusSol;
    private javax.swing.JButton butBusTipDoc;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.ButtonGroup butGrp;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butMotDev;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboEmp;
    private javax.swing.JComboBox cboEstAut;
    private javax.swing.JComboBox cboPasEst;
    private javax.swing.JComboBox cboTipDev;
    private javax.swing.JCheckBox chkMosVolFac;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCli1;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblEstAut;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMotDev;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblSol1;
    private javax.swing.JLabel lblTipDev;
    private javax.swing.JLabel lblTipdoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton radPasPen;
    private javax.swing.JRadioButton radPasRea;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodMotDev;
    private javax.swing.JTextField txtCodSol;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarMotDev;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesSol;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");
            //Validar los locales a presentar.
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
//                    strSQL="";
//                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
//                    strSQL+=" FROM tbm_loc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" ORDER BY a1.co_loc";
                }
                else
                {
                    //Armar la sentencia SQL.
//                    strSQL="";
//                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
//                    strSQL+=" FROM tbm_loc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
//                    strSQL+=" ORDER BY a1.co_loc";
                }
            }
            else
            {
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
                    strSQL+=" FROM tbm_loc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.co_loc";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.tx_nom";
                    strSQL+=" FROM tbm_loc AS a1";
                    strSQL+=" INNER JOIN";
                    strSQL+=" (";
                    strSQL+=" SELECT co_empRel AS co_emp, co_locRel AS co_loc";
                    strSQL+=" FROM tbr_locPrgUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" ) AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc)";
                    strSQL+=" ORDER BY a1.co_loc";
                }
            }
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
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
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los clientes.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" INNER JOIN";
                strSQL+=" (";
                strSQL+=" SELECT co_empRel AS co_emp, co_locRel AS co_loc";
                strSQL+=" FROM tbr_locPrgUsr";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc)";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" GROUP BY a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Motivos de devoluciones".
     */
    private boolean configurarVenConMotDev()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_motDev");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_motDev, a1.tx_desLar";
            strSQL+=" FROM tbm_motDevInv AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoMotDev=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de motivos de devoluciones", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMotDev.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
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
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
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
    private boolean mostrarVenConMotDev(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoMotDev.setCampoBusqueda(2);
                    vcoMotDev.setVisible(true);
                    if (vcoMotDev.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodMotDev.setText(vcoMotDev.getValueAt(1));
                        txtDesLarMotDev.setText(vcoMotDev.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoMotDev.buscar("a1.co_motDev", txtCodMotDev.getText()))
                    {
                        txtCodMotDev.setText(vcoMotDev.getValueAt(1));
                        txtDesLarMotDev.setText(vcoMotDev.getValueAt(2));
                    }
                    else
                    {
                        vcoMotDev.setCampoBusqueda(0);
                        vcoMotDev.setCriterio1(11);
                        vcoMotDev.cargarDatos();
                        vcoMotDev.setVisible(true);
                        if (vcoMotDev.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMotDev.setText(vcoMotDev.getValueAt(1));
                            txtDesLarMotDev.setText(vcoMotDev.getValueAt(2));
                        }
                        else
                        {
                            txtCodMotDev.setText(strCodMotDev);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoMotDev.buscar("a1.tx_desLar", txtDesLarMotDev.getText()))
                    {
                        txtCodMotDev.setText(vcoMotDev.getValueAt(1));
                        txtDesLarMotDev.setText(vcoMotDev.getValueAt(2));
                    }
                    else
                    {
                        vcoMotDev.setCampoBusqueda(1);
                        vcoMotDev.setCriterio1(11);
                        vcoMotDev.cargarDatos();
                        vcoMotDev.setVisible(true);
                        if (vcoMotDev.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMotDev.setText(vcoMotDev.getValueAt(1));
                            txtDesLarMotDev.setText(vcoMotDev.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarMotDev.setText(strDesLarMotDev);
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
    
}
