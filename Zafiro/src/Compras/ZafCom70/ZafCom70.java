/*
 * Created on 13 de agosto de 2008, 11:41  
 */
package Compras.ZafCom70;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafObtConCen.ZafObtConCen;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
                  
/**
 * @author  ANL. JAVIER AYAPATA
 * CONFIRMACION DE INGRESOS A BODEGA (RELACIONADAS)
 */
public class ZafCom70 extends javax.swing.JInternalFrame 
{
    //Constantes 
    private static final int INT_TBL_LINEA =0;
    private static final int INT_TBL_CODEMP=1;
    private static final int INT_TBL_CODLOC=2;
    private static final int INT_TBL_CODTIPDOC=3;
    private static final int INT_TBL_CODDOC=4;
    private static final int INT_TBL_CODREG=5;
    private static final int INT_TBL_DESCORTIPDOC=6;
    private static final int INT_TBL_DESLARTIPDOC=7;
    private static final int INT_TBL_NUMDOC=8;
    private static final int INT_TBL_FECDOC=9;
    private static final int INT_TBL_CODITM=10;
    private static final int INT_TBL_CODALTITM=11;
    private static final int INT_TBL_NOMITM=12;
    private static final int INT_TBL_DESUNI=13;
    private static final int INT_TBL_CANMOV=14;
    private static final int INT_TBL_CANTOTCON=15;
    private static final int INT_TBL_CANCON=16;
    private static final int INT_TBL_CANNUNREC=17; //18
    private static final int INT_TBL_CANMALEST=18; //17
    private static final int INT_TBL_CANTOTNUNREC=19;
    private static final int INT_TBL_CANTOTMALEST=20; //23
    private static final int INT_TBL_OBSITMCON=21;
    private static final int INT_TBL_BUTOBS=22;
    private static final int INT_TBL_IEBODFIS=23;
    private static final int INT_TBL_CODBOD =24;
    private static final int INT_TBL_NATDOC =25;
    private static final int INT_TBL_CANNUNREC_ORI=26;
    private static final int INT_TBL_CANCONGUI_ORI=27;
    private static final int INT_TBL_CODEMPRELGUIREM=28;
    private static final int INT_TBL_CODLOCRELGUIREM=29;
    private static final int INT_TBL_CODTIPDOCRELGUIREM=30;
    private static final int INT_TBL_CODDOCRELGUIREM=31;
    private static final int INT_TBL_CODREGRELGUIREM=32;
    private static final int INT_TBL_CANCONGUITOT=33;
    private static final int INT_TBL_CODLETITM=34;
    private static final int INT_TBL_STIMPORD=35;

    //Variables
    private ZafInvItm objInvItm; 
    private ZafTblMod objTblMod, objTblMod2;
    private ZafParSis objZafParSis;
    private UltDocPrint objUltDocPrint;
    private ZafUtil objUti;
    private ZafObtConCen  objObtConCen;
    private ZafTblCelEdiTxt objTblCelEdiTxtCanConf;
    private ZafTblCelEdiTxt objTblCelEdiTxtCanNumRec, objTblCelEdiTxtCanMalEst;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtCodAlt;
    private ZafTblCelRenBut objTblCelRenButDG;
    private ZafTblCelEdiButGen objTblCelEdiButGenDG;
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoBodUsrIng; 
    private ZafVenCon vcoBodUsrEgr; 
    private ZafRptSis objRptSis;                                //Reportes del Sistema. // José Marín M.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario. 
    private ZafThreadGUI objThrGUI;
    private java.util.Date datFecAux;                           // José Marín M  
    
    private JTextField txtCodTipDoc = new JTextField();
    
    private Connection CONN_GLO=null, conCab=null,  con;
    private Statement  STM_GLO=null,stm;
    private ResultSet  rstCab=null,rst;
    
    private Vector vecAux;
    private Vector vecCab=new Vector();
    private Vector vecDat, vecReg;
    private Vector vecTblDatDos, vecTblRegDos;
    
    private boolean blnMarTodCanTblDat=true;                    //Coloca en todas las celdas la cantidad vendida.
    private boolean blnEstCarConf=false;                       
    private boolean blnEstCanNunEnv=false;
    boolean blnEstMalEst = false;
    
    StringBuffer strBuf;
    
    private int intCodRegCen=0;
    private int intNunItm=0;
    private int intTipIngEgr=0;                                 // 1 Egr, 2 Ing
    
    private String strTipIngEgr="";
    private String strCodBod="", strNomBod="";
    private String strCodTipDoc="", strDesCorTipDoc="",strDesLarTipDoc="";
    private String strCodEmpConf="", strCodLocConf="", strCodTipDocConf="", strCodDocConf="";
    private String strCodLocRelConf="", strCodTipDocRelConf="", strCodDocRelConf="";
    private String strCodEmpGui, strCodLocGui, strCodTipDocGui, strCodDocGui;       // José Marín M.
    private String strCodEmpFact, strCodLocFact, strCodTipDocFact, strCodDocFact;   // José Marín M.
    private String strNomArchHis="c:/Zafiro/control_conf_egre.txt";
    private String strItmConCanNunEnv="";
   
    private final String strVersion = "v1.8";
    
    /* Rose */
    //Variables Documento Origen de Confirmación. Ej.: TRAINV, TRINRB
    private int intCodEmpDocOri=0;          //Código Empresa.
    private int intCodLocDocOri=0;          //Código Local del Documento.
    private int intCodTipDocDocOri=0;       //Código Tipo Documento.
    private int intCodDocDocOri=0;          //Código Documento. 

    //Variables de Documento a Ingresar. Ej.: COIEBO
    private int intCodTipDocIng=0;           //Código Tipo Documento.
    private int intCodDocIng=0;              //Código Documento.
    private int intNumDocIng=0;              //Número de Documento.
    private int intCodBodIng=0;              //Código de Bodega de Ingreso.
    
    //Variables de la Guia Relacionada al Ingreso. Ej.:GUIREM
    private int intCodEmpGuiRemRel=0;        //Código Empresa.
    private int intCodLocGuiRemRel=0;        //Código Local del Documento.
    private int intCodTipDocGuiRemRel=0;     //Código Tipo Documento.
    private int intCodDocGuiRemRel=0;        //Código Documento.
    
    private double dblTotalConfirmado=0;     //Total confirmado de ingreso. (CanCon+CanNunRec+CanMalEst)
    
    //<editor-fold defaultstate="collapsed" desc="/* Constructores */">
    
    /** Creates new form ZafCom70 */
    public ZafCom70(Librerias.ZafParSis.ZafParSis obj) 
    {
      try
       {
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          initComponents();
          this.setTitle(objZafParSis.getNombreMenu() + strVersion);
          lblTit.setText(objZafParSis.getNombreMenu()); 
          objUti = new ZafUtil(); 
          //Obtener los permisos del usuario. 
          objPerUsr=new ZafPerUsr(objZafParSis);          
          objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
          
          vecDat=new Vector();
          vecTblDatDos=new Vector();
          //José Marín M 12/Agos/2014
          tblDat2.setVisible(false);
          
          objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
          objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
          objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
          objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);  
                   
          intCodRegCen=objObtConCen.intCodReg;
          
          /* Habilitar/Inhabilitar las opciones según el perfil del usuario. // Permisos de Usuarios*/ 
          // Consultar
          if (!objPerUsr.isOpcionEnabled(2751))
          {
              butCon.setVisible(false);   
          }
          // Guardar
          if (!objPerUsr.isOpcionEnabled(2752))
          {
              butGua.setVisible(false);   
          }
           // Cerrar 
          if (!objPerUsr.isOpcionEnabled(2753))
          {
              butCer.setVisible(false);   
          }
          // Vista Preliminar
          if (!objPerUsr.isOpcionEnabled(3959))
          {
              butVisPre.setVisible(false);   
          }
          // Imprimir
          if (!objPerUsr.isOpcionEnabled(3960))
          {
              butImp.setVisible(false);   
          }          
          
     
          if(System.getProperty("os.name").equals("Linux"))
          {
                strNomArchHis="//Zafiro//control_conf_egre.txt";
          }
             
       }
       catch(CloneNotSupportedException e) {objUti.mostrarMsgErr_F1(this, e);}  
      }
    
    

    /** Creates new form  */
    public ZafCom70(Librerias.ZafParSis.ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodLocRel, String strCodTipDocRel, String strCodDocRel,  int intTipo ) 
    {
      try
       {
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
          initComponents();
          this.setTitle(objZafParSis.getNombreMenu() + strVersion);
          lblTit.setText(objZafParSis.getNombreMenu());
          objUti = new ZafUtil();
          
           //José Marín M 12/Agos/2014
           tblDat2.setVisible(false);
           
           strCodEmpConf= strCodEmp;
           strCodLocConf= strCodLoc;
           strCodTipDocConf= strCodTipDoc;
           strCodDocConf= strCodDoc;
           strCodLocRelConf=strCodLocRel;
           strCodTipDocRelConf=strCodTipDocRel;
           strCodDocRelConf=strCodDocRel;
           blnEstCarConf=true;
           intTipIngEgr = intTipo;

           butVisPre.setVisible(false);
           butImp.setVisible(false);
	  
          //this.getContentPane().add(objTooBar,"South");

          objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis);
          objObtConCen = new Librerias.ZafObtConCen.ZafObtConCen(objZafParSis);
          objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
          intCodRegCen=objObtConCen.intCodReg;

       }
       catch(CloneNotSupportedException e)  {objUti.mostrarMsgErr_F1(this, e);}
      }
    
     //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Conexiones */">
    
    public void abrirCon()
    {
        try
        {
            CONN_GLO=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    
    public void CerrarCon()
    {
        try
        {
            CONN_GLO.close();
            CONN_GLO=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="/* Configuraciones de Ventana de Consulta */">
    
    public void Configura_ventana_consulta()
    {
        configurarTablaConf();
        configurarVenConTipDoc();
        configurarVenConBodUsrIng();
        configurarVenConBodUsrEgr();
        cargarTipoDoc();
        cargarBodPre();
    }
    
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_tipdoc");
            arlCam.add("a.tx_descor");
            arlCam.add("a.tx_deslar");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("110");
            arlAncCol.add("350");

            //Armar la sentencia SQL.  
            String strSql = "";
            if (objZafParSis.getCodigoUsuario() == 1) 
            {
                strSql =  " select a.co_tipdoc,a.tx_descor,a.tx_deslar "
                        + " from tbr_tipdocprg as b "
                        + " inner join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)"
                        + " where b.co_emp=" + objZafParSis.getCodigoEmpresa() + "  "
                        + " and b.co_loc = " + objZafParSis.getCodigoLocal() + "  "
                        + " and b.co_mnu = " + objZafParSis.getCodigoMenu();
            } 
            else
            {
                strSql =  " select b.co_TipDoc, b.tx_desCor, b.tx_desLar "
                        + " from tbr_tipDocUsr as a "
                        + " inner join tbm_cabTipDoc as b on (a.co_Emp=b.co_Emp and a.co_loc=b.co_loc and a.co_tipDoc=b.co_tipDoc)"
                        + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + " and a.co_mnu=" + objZafParSis.getCodigoMenu() + " "
                        + " and a.co_usr=" + objZafParSis.getCodigoUsuario();
            }
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    private boolean configurarVenConBodUsrIng() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod.");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   
            String strSql = "";
            //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
            if (objZafParSis.getCodigoUsuario() == 1)
            {
                strSql =  " SELECT co_bod, tx_nom "
                        + " FROM ( "
                        + "        select a2.co_bod, a2.tx_nom "
                        + "        from tbm_emp AS a1 "
                        + "        inner join tbm_bod AS a2 on (a1.co_emp=a2.co_emp) "
                        + "        where a1.co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " "
                        + "        order by a1.co_emp, a2.co_bod "
                        + " ) as a ";
            }
            else 
            {
                strSql =  " SELECT co_bod, tx_nom "
                        + " FROM ( "
                        + "        select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                        + "        inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                        + "        inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                        + "        where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + "        and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + "        and a.co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "        and a.co_mnu=" + objZafParSis.getCodigoMenu() + "  "
                        + "        and tx_natbod='I' "
                        + " ) as a";
            }
            vcoBodUsrIng = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoBodUsrIng.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {     blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }

    private boolean configurarVenConBodUsrEgr() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.  
            String strSql = "";
            //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
            if (objZafParSis.getCodigoUsuario() == 1) 
            {
                strSql =  " SELECT co_bod, tx_nom "
                        + " FROM ( "
                        + "      select a2.co_bod, a2.tx_nom "
                        + "      from tbm_emp AS a1 "
                        + "      inner join tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "
                        + "      where a1.co_emp=" + objZafParSis.getCodigoEmpresaGrupo() + " "
                        + "      order by a1.co_emp, a2.co_bod "
                        + " ) as a ";
            } 
            else 
            {
                strSql =  " SELECT co_bod, tx_nom "
                        + " FROM ( "
                        + "        select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                        + "        inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                        + "        inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                        + "        where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + "        and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + "        and a.co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "        and a.co_mnu=" + objZafParSis.getCodigoMenu() + "  "
                        + "        and tx_natbod='E' "
                        + " ) as a";

            }
            vcoBodUsrEgr = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), strSql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

            vcoBodUsrEgr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        } 
        catch (Exception e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    public void cargarTipoDoc()
    {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stmLoc = conn.createStatement();
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSql =  " select  doc.co_tipdoc,doc.tx_deslar,doc.tx_descor  "
                            + " from tbr_tipdocprg as menu  "
                            + " inner join  tbm_cabtipdoc as doc on ( doc.co_emp = menu.co_emp and doc.co_loc=menu.co_loc and  doc.co_tipdoc=menu.co_tipdoc)   "
                            + " where menu.co_emp = " + objZafParSis.getCodigoEmpresa() + "  "
                            + " and menu.co_loc = " + objZafParSis.getCodigoLocal() + "  "
                            + " and menu.co_mnu = " + objZafParSis.getCodigoMenu() + "  "
                            + " and menu.st_reg = 'S'";
                }
                else
                {
                    strSql =  " select b.co_tipdoc, b.tx_descor, b.tx_deslar "
                            + " from tbr_tipDocUsr as a "
                            + " inner join tbm_cabTipDoc as b on (a.co_Emp=b.co_Emp and a.co_loc=b.co_loc and a.co_tipDoc=b.co_tipDoc)"
                            + " where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                            + " and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                            + " and a.co_mnu=" + objZafParSis.getCodigoMenu() + " "
                            + " and a.co_usr=" + objZafParSis.getCodigoUsuario() + " "
                            + " and a.st_reg='S'";
                }
                
                System.out.println("cargarTipoDoc: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) 
                {
                    txtCodTipDoc.setText(((rstLoc.getString("co_tipdoc") == null) ? "" : rstLoc.getString("co_tipdoc")));
                    txtDesCorTipDoc.setText(((rstLoc.getString("tx_descor") == null) ? "" : rstLoc.getString("tx_descor")));
                    txtDesLarTipDoc.setText(((rstLoc.getString("tx_deslar") == null) ? "" : rstLoc.getString("tx_deslar")));
                    strCodTipDoc = txtCodTipDoc.getText();
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conn.close();
                conn = null;
            }
        }
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);    } 
        catch (Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
    }
     
    public void cargarBodPre() 
    {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                strSql =  " SELECT co_bod, tx_nom "
                        + " FROM ( "
                        + "       select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a "
                        + "       inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "
                        + "       inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) "
                        + "       where a.co_emp=" + objZafParSis.getCodigoEmpresa() + " "
                        + "       and a.co_loc=" + objZafParSis.getCodigoLocal() + " "
                        + "       and a.co_usr=" + objZafParSis.getCodigoUsuario() + " "
                        + "       and a.co_mnu=" + objZafParSis.getCodigoMenu() + "  "
                        + "       and a.st_reg='P' "
                        + "       and tx_natbod='I' "
                        + " ) as a";

                System.out.println("cargarBodPre: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) 
                {
                    txtCodBodIng.setText(rstLoc.getString("co_bod"));
                    txtNomBodIng.setText(rstLoc.getString("tx_nom"));
                    strCodBod = rstLoc.getString("co_bod");
                    strNomBod = rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
                conn.close();
                conn = null;
            }
        }
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  }
    }

    
    public void BuscarTipDoc(String campo, String strBusqueda, int tipo) 
    {
        vcoTipDoc.setTitle("Listado Tipos de Documentos");
        if (vcoTipDoc.buscar(campo, strBusqueda)) 
        {
            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
            strCodTipDoc = vcoTipDoc.getValueAt(1);
        } 
        else 
        {
            vcoTipDoc.setCampoBusqueda(tipo);
            vcoTipDoc.cargarDatos();
            vcoTipDoc.show();
            if (vcoTipDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                strCodTipDoc = vcoTipDoc.getValueAt(1);
            } 
            else 
            {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCorTipDoc.setText(strDesCorTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    public void BuscarBod(String campo, String strBusqueda, int tipo) 
    {
        vcoBodUsrIng.setTitle("Listado de Bodegas");
        if (vcoBodUsrIng.buscar(campo, strBusqueda)) 
        {
            txtCodBodIng.setText(vcoBodUsrIng.getValueAt(1));
            txtNomBodIng.setText(vcoBodUsrIng.getValueAt(2));
            strCodBod = vcoBodUsrIng.getValueAt(1);
            strNomBod = vcoBodUsrIng.getValueAt(2);
        } 
        else 
        {
            vcoBodUsrIng.setCampoBusqueda(tipo);
            vcoBodUsrIng.cargarDatos();
            vcoBodUsrIng.show();
            if (vcoBodUsrIng.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodBodIng.setText(vcoBodUsrIng.getValueAt(1));
                txtNomBodIng.setText(vcoBodUsrIng.getValueAt(2));
                strCodBod = vcoBodUsrIng.getValueAt(1);
                strNomBod = vcoBodUsrIng.getValueAt(2);
            } 
            else 
            {
                txtCodBodIng.setText(strCodBod);
                txtNomBodIng.setText(strNomBod);
            }
        }
    }

    String strCodBodEgr = "", strNomBodEgr = "";

    public void BuscarBodEgr(String campo, String strBusqueda, int tipo) 
    {
        vcoBodUsrEgr.setTitle("Listado de Bodegas");
        if (vcoBodUsrEgr.buscar(campo, strBusqueda))
        {
            txtCodBodEgr.setText(vcoBodUsrEgr.getValueAt(1));
            txtNomBodEgr.setText(vcoBodUsrEgr.getValueAt(2));
            strCodBodEgr = vcoBodUsrEgr.getValueAt(1);
            strNomBodEgr = vcoBodUsrEgr.getValueAt(2);
        }
        else 
        {
            vcoBodUsrEgr.setCampoBusqueda(tipo);
            vcoBodUsrEgr.cargarDatos();
            vcoBodUsrEgr.show();
            if (vcoBodUsrEgr.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
            {
                txtCodBodEgr.setText(vcoBodUsrEgr.getValueAt(1));
                txtNomBodEgr.setText(vcoBodUsrEgr.getValueAt(2));
                strCodBodEgr = vcoBodUsrEgr.getValueAt(1);
                strNomBodEgr = vcoBodUsrEgr.getValueAt(2);
            }
            else 
            {
                txtCodBodEgr.setText(strCodBodEgr);
                txtNomBodEgr.setText(strNomBodEgr);
            }
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Configurar Jtable de Confirmación */">
   
    private boolean configurarTablaConf()
    {
        boolean blnRes = false;
        try 
        {
            //System.out.println("configurarTablaConf!");
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA, "");
            vecCab.add(INT_TBL_CODEMP, "Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC, "Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC, "Cod.TipDoc.");
            vecCab.add(INT_TBL_CODDOC, "Cod.Doc.");
            vecCab.add(INT_TBL_CODREG, "Cod.Reg.");
            vecCab.add(INT_TBL_DESCORTIPDOC, "TipDoc.Des.Cor.");
            vecCab.add(INT_TBL_DESLARTIPDOC, "TipDoc.Des.Lar.");
            vecCab.add(INT_TBL_NUMDOC, "Num.Doc.");
            vecCab.add(INT_TBL_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_CODITM, "Cod.Itm.");
            vecCab.add(INT_TBL_CODALTITM, "Cod.Alt.");
            vecCab.add(INT_TBL_NOMITM, "Nom.Itm.");
            vecCab.add(INT_TBL_DESUNI, "Uni.Med.");
            vecCab.add(INT_TBL_CANMOV, "Cantidad.");
            vecCab.add(INT_TBL_CANTOTCON, "Can.Tot.Con.");
            vecCab.add(INT_TBL_CANCON, "Can.Conf.");
            vecCab.add(INT_TBL_CANNUNREC, "Can.Nun.Rec");
            vecCab.add(INT_TBL_CANMALEST, "Can.Mal.Est");
            vecCab.add(INT_TBL_CANTOTNUNREC, "Can.Tot.Nun.Rec.");
            vecCab.add(INT_TBL_CANTOTMALEST, "Can.Tot.Mal.Est."); //Rose
            vecCab.add(INT_TBL_OBSITMCON, "Obs.Itm.Con.");
            vecCab.add(INT_TBL_BUTOBS, "");
            vecCab.add(INT_TBL_IEBODFIS, "");
            
            vecCab.add(INT_TBL_CODBOD, "");
            vecCab.add(INT_TBL_NATDOC, "");
            vecCab.add(INT_TBL_CANNUNREC_ORI, "");
            vecCab.add(INT_TBL_CANCONGUI_ORI, "");
            vecCab.add(INT_TBL_CODEMPRELGUIREM, "");
            vecCab.add(INT_TBL_CODLOCRELGUIREM, "");
            vecCab.add(INT_TBL_CODTIPDOCRELGUIREM, "");
            vecCab.add(INT_TBL_CODDOCRELGUIREM, "");
            vecCab.add(INT_TBL_CODREGRELGUIREM, "");
            vecCab.add(INT_TBL_CANCONGUITOT, "");
            vecCab.add(INT_TBL_CODLETITM, "");
            vecCab.add(INT_TBL_STIMPORD, "");
            objTblMod = new ZafTblMod();
            objTblMod2 = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            objTblMod2.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //José Marín 12/Agos/2014
            tblDat2.setModel(objTblMod2);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat, INT_TBL_LINEA);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAda objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DESCORTIPDOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CODALTITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_DESUNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CANMOV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANCON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANNUNREC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANMALEST).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_OBSITMCON).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CANTOTMALEST).setPreferredWidth(100);

            objTblMod.setColumnDataType(INT_TBL_CANMOV, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANCON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANMALEST, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTNUNREC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_CANTOTMALEST, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            ZafTblCelRenLbl objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANNUNREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANMALEST).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_CANMOV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTCON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTNUNREC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CANTOTMALEST).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_OBSITMCON).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {

                }

                @Override
                public void afterEdit(ZafTableEvent evt) {

                }
            });

            /* José Marín M.  */
            objTblCelEdiTxtCodAlt = new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CODALTITM).setCellEditor(objTblCelEdiTxtCodAlt);
            objTblCelEdiTxtCodAlt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_NOMITM) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_NOMITM).toString().equals("")) ? false : true)) {
                        objTblCelEdiTxtCodAlt.setCancelarEdicion(true);
                        //System.out.println("BEFORE EDIT " + tblDat.getValueAt(intCelSel, INT_TBL_NOMITM).toString());
                    }
                }

                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    boolean blnCodAltDos = false;
                    if (tblDat.getValueAt(intCelSel, INT_TBL_CODALTITM) != null) 
                    {
                        tblDat.setValueAt(tblDat.getValueAt(intCelSel, INT_TBL_CODALTITM).toString().toUpperCase(), intCelSel, INT_TBL_CODALTITM);
                        //PONE EL VALOR INGRESADO EN MAYUSCULAS 
                        for (int i = 0; i < intNunItm; i++) 
                        {
                            //System.out.println("FOR " + i);
                            //System.out.println("CODIGO 3 LETRAS: " + tblDat2.getValueAt(i, INT_TBL_CODLETITM).toString());
                            if (tblDat.getValueAt(intCelSel, INT_TBL_CODALTITM).equals(tblDat2.getValueAt(i, INT_TBL_CODLETITM))) 
                            {
                                blnCodAltDos = true; //Si tiene codigo alterno 2
                                //System.out.println("Si existe: " + tblDat2.getValueAt(i, INT_TBL_CODLETITM).toString());
                                //LLENAR LOS CAMPOS
                                if (repetidos(intCelSel)) 
                                {
                                    tblDat.repaint();
                                    tblDat.requestFocus();
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODEMP), intCelSel, INT_TBL_CODEMP);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODLOC), intCelSel, INT_TBL_CODLOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODTIPDOC), intCelSel, INT_TBL_CODTIPDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODDOC), intCelSel, INT_TBL_CODDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODREG), intCelSel, INT_TBL_CODREG);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_DESCORTIPDOC), intCelSel, INT_TBL_DESCORTIPDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_DESLARTIPDOC), intCelSel, INT_TBL_DESLARTIPDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_NUMDOC), intCelSel, INT_TBL_NUMDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_FECDOC), intCelSel, INT_TBL_FECDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODITM), intCelSel, INT_TBL_CODITM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_NOMITM), intCelSel, INT_TBL_NOMITM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_DESUNI), intCelSel, INT_TBL_DESUNI);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CANMOV), intCelSel, INT_TBL_CANMOV);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CANTOTCON), intCelSel, INT_TBL_CANTOTCON);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CANTOTNUNREC), intCelSel, INT_TBL_CANTOTNUNREC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CANTOTMALEST), intCelSel, INT_TBL_CANTOTMALEST);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_IEBODFIS), intCelSel, INT_TBL_IEBODFIS);
                                 
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODBOD), intCelSel, INT_TBL_CODBOD);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_NATDOC), intCelSel, INT_TBL_NATDOC);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CANCONGUI_ORI), intCelSel, INT_TBL_CANCONGUI_ORI);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODEMPRELGUIREM), intCelSel, INT_TBL_CODEMPRELGUIREM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODLOCRELGUIREM), intCelSel, INT_TBL_CODLOCRELGUIREM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODTIPDOCRELGUIREM), intCelSel, INT_TBL_CODTIPDOCRELGUIREM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODDOCRELGUIREM), intCelSel, INT_TBL_CODDOCRELGUIREM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODREGRELGUIREM), intCelSel, INT_TBL_CODREGRELGUIREM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CANCONGUITOT), intCelSel, INT_TBL_CANCONGUITOT);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_CODLETITM), intCelSel, INT_TBL_CODLETITM);
                                    tblDat.setValueAt(tblDat2.getValueAt(i, INT_TBL_STIMPORD), intCelSel, INT_TBL_STIMPORD);
                                }
                            }
                        }
                        if (!blnCodAltDos) 
                        {
                            mostrarMsgInf("No se encontró ninguna coincidencia con el código alterno 2 indicado.\nVerifique y vuelva a intentarlo.");
                            tblDat.repaint();
                            tblDat.requestFocus();
                            tblDat.setValueAt("", intCelSel, INT_TBL_CODALTITM);
                            tblDat.setValueAt("", intCelSel, INT_TBL_CODREG);
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANMOV);
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANTOTCON);
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANCON);
                            tblDat.setValueAt("", intCelSel, INT_TBL_IEBODFIS);
                            tblDat.editCellAt(intCelSel, INT_TBL_CODALTITM);
                        }
                    }
                }
            });

            /* José Marín M.   11/Agosto/2014 */
            objTblCelEdiTxtCanMalEst = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANMALEST).setCellEditor(objTblCelEdiTxtCanMalEst);
            objTblCelEdiTxtCanMalEst.addTableEditorListener(new ZafTableAdapter() 
            {
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) 
                    {
                        objTblCelEdiTxtCanMalEst.setCancelarEdicion(true);
                    }

                    /*if (verificaCanConIng(CONN_GLO, 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM) )), 
                     objZafParSis.getCodigoEmpresa() )){
                     objTblCelEdiTxtCanMalEst.setCancelarEdicion(true);                     
                     }*/
                }

                @Override
                public void afterEdit(ZafTableEvent evt)
                {
                    int intCelSel = tblDat.getSelectedRow();

                    String strTotConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                    String strConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                    String strNumRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC));
                    String strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));
                    String strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC));
                    String strTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));

                    double dblCanTotMalEst = objUti.redondear(strTotMalEst, 4);
                    double dblCantidadMalEstado = objUti.redondear(objUti.parseDouble(strCanMalEst), 4);
                    double dblCanMalEstOri = 0;
                    double dblCanNumRec = objUti.redondear(objUti.parseDouble(strNumRec), 4);
                    double dblCanTotCon = objUti.redondear(strTotConf, 4);
                    double dblCanConf = objUti.redondear(objUti.parseDouble(strConf), 4);
                    double dblCanConfOri = 0;
                    double dblCanTotNunRec = objUti.redondear(strCanTotNunRec, 4);

                    String strCanConGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUI_ORI));
                    double dblCanTotConGuiOri = objUti.redondear(objUti.parseDouble(strCanConGui), 4);

                    dblCanTotCon = dblCanTotCon - dblCanConfOri;
                    dblCanTotMalEst = dblCanTotMalEst - dblCanMalEstOri;
                    dblCanTotCon = (dblCanConf + dblCanTotCon) + (dblCanNumRec + dblCanTotNunRec) + (dblCantidadMalEstado + dblCanTotMalEst);

                    if (dblCanTotCon > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUITOT).toString(), 4)) {
                        mostrarMsgInf("Error! La cantidad en mal estado ha sobrepasado la cantidad confirmada en punto remoto.");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANMALEST);
                    } else {
                        if (dblCanTotCon > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) {
                            mostrarMsgInf("Error! La cantidad en mal estado ha sobrepasado la cantidad de la confirmada.");
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANMALEST);
                        }
                    }

                    if (!verificaCanConEgr(CONN_GLO,
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODREGRELGUIREM))),
                            (dblCanConf + dblCantidadMalEstado + dblCanNumRec))) {
                        mostrarMsgInf("Este item tiene una confirmacion y no puede exceder a la cantidad confirmada en la guia.\nVerifique los datos e intente nuevamente.");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANMALEST);
                    }

                }
            });

            objTblCelEdiTxtCanNumRec = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANNUNREC).setCellEditor(objTblCelEdiTxtCanNumRec);
            objTblCelEdiTxtCanNumRec.addTableEditorListener(new ZafTableAdapter() {
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) {
                        objTblCelEdiTxtCanNumRec.setCancelarEdicion(true);
                    }

                    /*if (verificaCanConIng(CONN_GLO, 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM) )), 
                     objZafParSis.getCodigoEmpresa() )){
                     objTblCelEdiTxtCanNumRec.setCancelarEdicion(true);                     
                     }*/
                }

                @Override
                public void afterEdit(ZafTableEvent evt)
                {
                    int intCelSel = tblDat.getSelectedRow();

                    String strTotConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                    String strConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                    String strNumRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC));
                    String strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));
                    String strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC));
                    String strTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));
                    
                    double dblCanTotMalEst = objUti.redondear(strTotMalEst, 4);
                    double dblCantidadMalEstado = objUti.redondear(objUti.parseDouble(strCanMalEst), 4);
                    double dblCanNumRec = objUti.redondear(objUti.parseDouble(strNumRec), 4);
                    double dblCanTotCon = objUti.redondear(strTotConf, 4);
                    double dblCanConf = objUti.redondear(objUti.parseDouble(strConf), 4);
                    double dblCanConfOri = 0;
                    double dblCanTotNunRec = objUti.redondear(strCanTotNunRec, 4);

                    String strCanConGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUI_ORI));
                    double dblCanTotConGuiOri = objUti.redondear(objUti.parseDouble(strCanConGui), 4);

                    dblCanTotCon = dblCanTotCon - dblCanConfOri;
                    dblCanTotCon = (dblCanConf + dblCanTotCon) + (dblCanNumRec + dblCanTotNunRec) + (dblCantidadMalEstado + dblCanTotMalEst);

                    if (dblCanTotCon > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUITOT).toString(), 4))
                    {
                        mostrarMsgInf("Error! La cantidad nunca recibida ha sobrepasado la cantidad confirmada en punto remoto.");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANMALEST);
                    } 
                    else 
                    {
                        if (dblCanTotCon > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) 
                        {
                            mostrarMsgInf("Error! La cantidad nunca recibida ha sobrepasado la cantidad de la confirmada.");
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANNUNREC);
                        }
                    }

                    if (!verificaCanConEgr(CONN_GLO,
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM))),
                            Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODREGRELGUIREM))),
                            (dblCanConf + dblCantidadMalEstado + dblCanNumRec))) 
                    {
                        mostrarMsgInf("Este item tiene una confirmacion y no puede exceder a la cantidad confirmada en la guia.\nVerifique los datos e intente nuevamente.");
                        tblDat.setValueAt("", intCelSel, INT_TBL_CANNUNREC);
                    }

                }
            });

            objTblCelEdiTxtCanConf = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_CANCON).setCellEditor(objTblCelEdiTxtCanConf);
            objTblCelEdiTxtCanConf.addTableEditorListener(new ZafTableAdapter() 
            {
                @Override
                public void beforeEdit(ZafTableEvent evt)
                {
                    int intCelSel = tblDat.getSelectedRow();
                    if ((tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? false : (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N") ? true : false))) 
                    {
                        objTblCelEdiTxtCanConf.setCancelarEdicion(true);
                    }

                    /*if (verificaCanConIng(CONN_GLO, 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM) )), 
                     Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM) )), 
                     objZafParSis.getCodigoEmpresa() )){
                     objTblCelEdiTxtCanConf.setCancelarEdicion(true); 
                     tblDat.setValueAt( "" , intCelSel, INT_TBL_CANCON);
                     }*/
                }

                /**
                 * MODIFICADO EFLORESA NO PERMITIR REGISTRAR UNA CANTIDAD MAYOR
                 * A LA CANTIDAD CONFIRMADA EN LA GUIA.
                 */
                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    if (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) != null) 
                    {
                        String strCanMov = (tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString());
                        String strTotConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                        String strConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                        String strNumRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC));
                        String strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));
                        String strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC));
                        String strTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));
                        String strCanConGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUI_ORI));

                        double dblCanTotMalEst = objUti.redondear(strTotMalEst, 4);
                        double dblCantidadMalEstado = objUti.redondear(objUti.parseDouble(strCanMalEst), 4);
                        double dblCanMalEstOri = 0;
                        double dblCanNumRec = objUti.redondear(objUti.parseDouble(strNumRec), 4);
                        double dblCanTotCon = objUti.redondear(strTotConf, 4);
                        double dblCanConf = objUti.redondear(objUti.parseDouble(strConf), 4);
                        double dblCanConfOri = 0;
                        double dblCanTotNunRec = objUti.redondear(strCanTotNunRec, 4);

                        double dblCanTotConGuiOri = objUti.redondear(objUti.parseDouble(strCanConGui), 4);
                        double dblCanMov = objUti.redondear(objUti.parseDouble(strCanMov), 4);

                        dblCanTotMalEst = dblCanTotMalEst - dblCanMalEstOri;

                        if (((dblCanTotCon - dblCanConfOri) + (dblCanConf + (dblCanNumRec + dblCanTotNunRec)) + (dblCantidadMalEstado + dblCanTotMalEst)) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUITOT).toString(), 4)) 
                        {
                            mostrarMsgInf("Este item tiene una confirmacion y no puede exceder la cantidad confirmada en punto remoto.\nVerifique los datos e intente nuevamente.");
                            tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                        }
                        else
                        {
                            if (((dblCanTotCon - dblCanConfOri) + (dblCanConf + (dblCanNumRec + dblCanTotNunRec)) + (dblCantidadMalEstado + dblCanTotMalEst)) > objUti.redondear(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV).toString(), 4)) 
                            {
                                if (dblCanConfOri > 0) 
                                {
                                    mostrarMsgInf("Este item ya tiene una confirmacion y no puede exceder a la cantidad.\nVerifique los datos e intente nuevamente.");
                                } 
                                else 
                                {
                                    mostrarMsgInf("No puede exceder a la cantidad.\nVerifique los datos e intente nuevamente.");
                                }

                                tblDat.setValueAt("" + dblCanConfOri, intCelSel, INT_TBL_CANCON);
                            }
                        }

                        if (!verificaCanConEgr(CONN_GLO,
                                Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM))),
                                Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM))),
                                Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM))),
                                Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM))),
                                Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODREGRELGUIREM))),
                                (dblCanConf + dblCantidadMalEstado + dblCanNumRec)))
                        {
                            mostrarMsgInf("Este item tiene una confirmacion y no puede exceder a la cantidad confirmada en la guia.\nVerifique los datos e intente nuevamente.");
                            tblDat.setValueAt("", intCelSel, INT_TBL_CANCON);
                        }

                    }

                }
            });

            objTblCelRenButDG = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                        case INT_TBL_BUTOBS:
                            objTblCelRenButDG.setText("...");
                            break;
                    }
                }
            });

            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new ZafTableAdapter()
            {
                int intFilSel, intColSel;

                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_BUTOBS:
                                break;
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt)
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                       // System.out.println(" -  "+ intColSel +"   -  "+ INT_TBL_BUTOBS );
                        switch (intColSel) 
                        {
                            case INT_TBL_BUTOBS:
                                llamarVenObs((objTblMod.getValueAt(intFilSel, INT_TBL_OBSITMCON) == null ? "" : objTblMod.getValueAt(intFilSel, INT_TBL_OBSITMCON).toString()), intFilSel, INT_TBL_OBSITMCON, true);
                                break;
                        }
                    }
                }
            });

            //Columnas Ocultas.
            ArrayList arlColHid = new ArrayList();
            arlColHid.add("" + INT_TBL_CODEMP);
            arlColHid.add("" + INT_TBL_CODLOC);
            arlColHid.add("" + INT_TBL_CODTIPDOC);
            arlColHid.add("" + INT_TBL_CODDOC);
            arlColHid.add("" + INT_TBL_CODITM);
            arlColHid.add("" + INT_TBL_CODREG);
            arlColHid.add("" + INT_TBL_DESLARTIPDOC);
            arlColHid.add("" + INT_TBL_IEBODFIS);
            //arlColHid.add("" + INT_TBL_CANTOTMALEST); //Rose: Se muestra el total de cantidad en mal estado. 03/Feb/2016
            arlColHid.add("" + INT_TBL_CODBOD);
            arlColHid.add("" + INT_TBL_NATDOC);
            arlColHid.add("" + INT_TBL_CANNUNREC_ORI);
            arlColHid.add("" + INT_TBL_CANCONGUI_ORI);
            arlColHid.add("" + INT_TBL_CODEMPRELGUIREM);
            arlColHid.add("" + INT_TBL_CODLOCRELGUIREM);
            arlColHid.add("" + INT_TBL_CODTIPDOCRELGUIREM);
            arlColHid.add("" + INT_TBL_CODDOCRELGUIREM);
            arlColHid.add("" + INT_TBL_CODREGRELGUIREM);
            arlColHid.add("" + INT_TBL_CANCONGUITOT);
            arlColHid.add("" + INT_TBL_CODLETITM);
            arlColHid.add("" + INT_TBL_STIMPORD);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid = null;

            //Para hacer editable las celdas
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_CODALTITM);  // José Marín M 12/Agosto/2014
            vecAux.add("" + INT_TBL_CANCON);
            vecAux.add("" + INT_TBL_CANNUNREC);
            vecAux.add("" + INT_TBL_CANMALEST);
            vecAux.add("" + INT_TBL_OBSITMCON);
            vecAux.add("" + INT_TBL_BUTOBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });

            tcmAux = null;
            setEditable(true);
            blnRes = true;

        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    public boolean repetidos(int rowSelected) 
    {
        boolean blnRes = true;
        try 
        {
            //System.out.println("Repetidos: ");
            for (int i = 0; i < intNunItm; i++) 
            {
                if (i != rowSelected) 
                {
                    if (tblDat.getValueAt(rowSelected, INT_TBL_CODALTITM).equals(tblDat.getValueAt(i, INT_TBL_CODALTITM))) {
                        mostrarMsgInf("CODIGO ALTERNO 2 ESTA REPETIDO, REVISE EL CODIGO Y VUELVA A INTENTARLO ");
                        tblDat.setValueAt("", rowSelected, INT_TBL_CODALTITM);
                        blnRes = false;
                    }
                }
            }
            return blnRes;
        } 
        catch (Exception e) {   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_CANCON)
            {
                if (blnMarTodCanTblDat)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                     int intCelSel=i; 
                     
                     if(!(tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS)==null?false:(tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString().equals("N")?true:false)))
                     {
                        String strCanven=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV) );  
                        String strTotConf=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON) );
                        String strNumRec=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
                        String strCanMalEst=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST) );
                        String strCanTotNunRec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC) );
                        String strTotMalEst=objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST) );
                        String strCanConGui= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUI_ORI) );
                        String strCanConGuiTot= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUITOT) );

                        double dblCanTotMalEst= objUti.redondear(strTotMalEst,4);
                        double dblCantidadMalEstado   = objUti.redondear(objUti.parseDouble(strCanMalEst),4);
                        double dblCanMalEstOri= 0;
                        double dblCanNumRec= objUti.redondear(objUti.parseDouble(strNumRec),4);
                        double dblCanTotCon= objUti.redondear(strTotConf,4);
                        double dblCanMov   = objUti.redondear(objUti.parseDouble(strCanven),4);
                        double dblCanConfOri=0;
                        double dblCanTotNunRec = objUti.redondear(strCanTotNunRec,4);

                        double dblCanTotConGuiOri = objUti.redondear(objUti.parseDouble(strCanConGui), 4);                      
                        double dblCanTotConGui = objUti.redondear(objUti.parseDouble(strCanConGuiTot), 4);                      

                        // dblCanMov =  ( (dblCanMov - (dblCanTotCon+(dblCanNumRec+dblCanTotNunRec)))-(dblCanTotMalEst+dblCantidadMalEstado) );
                        dblCanMov =  ( (dblCanTotConGui - (dblCanTotCon+(dblCanNumRec+dblCanTotNunRec)))-(dblCanTotMalEst+dblCantidadMalEstado) );

                        if (dblCanMov > dblCanTotConGuiOri)
                        {
                            dblCanMov = ( (dblCanTotConGuiOri - (dblCanTotCon+(dblCanNumRec+dblCanTotNunRec)))-(dblCanTotMalEst+dblCantidadMalEstado) );                            
                        }
                        else 
                        {
                            dblCanMov = ( (dblCanTotConGui - (dblCanTotCon+(dblCanNumRec+dblCanTotNunRec)))-(dblCanTotMalEst+dblCantidadMalEstado) );                            
                        }
                        
                        if( dblCanConfOri <= 0 )
                        {
                           if(dblCanConfOri >0)  tblDat.setValueAt(""+dblCanConfOri , intCelSel, INT_TBL_CANCON); 
                           else  tblDat.setValueAt(""+dblCanMov , intCelSel, INT_TBL_CANCON); 
                        }

                        if (! verificaCanConEgr(CONN_GLO, 
                                                    Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM) )), 
                                                    Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM) )), 
                                                    Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM) )), 
                                                    Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM) )), 
                                                    Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODREGRELGUIREM) )), 
                                                    (dblCanMov+dblCantidadMalEstado+dblCanNumRec))){
                            mostrarMsgInf("Este item tiene una confirmacion y no puede exceder a la cantidad confirmada en la guia.\nVerifique los datos e intente nuevamente."); 
                            tblDat.setValueAt( "" , intCelSel, INT_TBL_CANCON);

                        }

                        /*if (verificaCanConIng(CONN_GLO, 
                                                        Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM) )), 
                                                        Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM) )), 
                                                        Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM) )), 
                                                        Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM) )), 
                                                        objZafParSis.getCodigoEmpresa() )){
                            tblDat.setValueAt( "" , intCelSel, INT_TBL_CANCON);
                        }*/
                        
                    } 
                    }
                    blnMarTodCanTblDat=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        tblDat.setValueAt("0", i, INT_TBL_CANCON);
                    }
                    blnMarTodCanTblDat=true;
                }
                
            }
        }
        catch (Exception e)  {       objUti.mostrarMsgErr_F1(this, e);        }
    }
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";

            switch (intCol)
            {
                case INT_TBL_LINEA:
                    strMsg = "";
                    break;
                case INT_TBL_CODEMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DESCORTIPDOC:
                    strMsg="Descripcion corta del tipo de documento.";
                    break;
                case INT_TBL_DESLARTIPDOC:
                    strMsg="Descripcion larga del tipo de documento.";
                    break;
                case INT_TBL_NUMDOC:
                    strMsg="Número de documento.";
                    break;
                case INT_TBL_FECDOC:
                    strMsg="Fecha del documento.";
                    break;
                case INT_TBL_CODALTITM:
                    strMsg="Código alterno del ítem";
                    break;
                case INT_TBL_NOMITM:
                    strMsg="Nombre del ítem";
                    break;
                case INT_TBL_DESUNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_CANMOV:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_CANTOTCON:
                    strMsg="Cantidad total confirmada";
                    break;
                case INT_TBL_CANCON:
                    strMsg="Cantidad a Confirmar.";
                    break;
                case INT_TBL_CANNUNREC:
                    strMsg="Cantidad nunca recibida";
                    break;
                case INT_TBL_CANMALEST:
                    strMsg="Cantidad en mal estado ";
                    break;
                case INT_TBL_CANTOTNUNREC:
                    strMsg = "Cantidad total nunca recibida. ";
                    break;
                case INT_TBL_CANTOTMALEST:
                    strMsg = "Cantidad total en Mal Estado. ";
                    break;
                case INT_TBL_OBSITMCON:
                    strMsg="Observación sobre el ítem a confirmar";
                    break;
            }

            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    //</editor-fold>
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblBodIng = new javax.swing.JLabel();
        txtCodBodIng = new javax.swing.JTextField();
        txtNomBodIng = new javax.swing.JTextField();
        butBodIng = new javax.swing.JButton();
        panDatGuiRec = new javax.swing.JPanel();
        lblBodEgr = new javax.swing.JLabel();
        txtCodBodEgr = new javax.swing.JTextField();
        txtNomBodEgr = new javax.swing.JTextField();
        butBodEgr = new javax.swing.JButton();
        lblNumGuiRem = new javax.swing.JLabel();
        txtNumGuiRem = new javax.swing.JTextField();
        lblMsg = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnDat2 = new javax.swing.JScrollPane();
        tblDat2 = new javax.swing.JTable();
        panTooBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setText("Título de la ventana");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        tabFrm.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(585, 150));
        panCab.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Tipo de Documento:");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 110, 20);

        txtDesCorTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(130, 10, 80, 20);

        txtDesLarTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(210, 10, 340, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(550, 10, 20, 20);

        lblBodIng.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblBodIng.setText("Bodega:");
        panCab.add(lblBodIng);
        lblBodIng.setBounds(10, 30, 110, 20);

        txtCodBodIng.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodBodIng.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodBodIng.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodIngFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodIngFocusLost(evt);
            }
        });
        txtCodBodIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodIngActionPerformed(evt);
            }
        });
        panCab.add(txtCodBodIng);
        txtCodBodIng.setBounds(130, 30, 80, 20);

        txtNomBodIng.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomBodIng.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomBodIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodIngActionPerformed(evt);
            }
        });
        txtNomBodIng.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodIngFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodIngFocusLost(evt);
            }
        });
        panCab.add(txtNomBodIng);
        txtNomBodIng.setBounds(210, 30, 340, 20);

        butBodIng.setText("jButton2");
        butBodIng.setPreferredSize(new java.awt.Dimension(20, 20));
        butBodIng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodIngActionPerformed(evt);
            }
        });
        panCab.add(butBodIng);
        butBodIng.setBounds(550, 30, 20, 20);

        panDatGuiRec.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la guía remision recibida"));
        panDatGuiRec.setLayout(null);

        lblBodEgr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblBodEgr.setText("Bodega:");
        panDatGuiRec.add(lblBodEgr);
        lblBodEgr.setBounds(10, 20, 110, 20);

        txtCodBodEgr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodBodEgr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodBodEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodEgrActionPerformed(evt);
            }
        });
        txtCodBodEgr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodEgrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodEgrFocusLost(evt);
            }
        });
        panDatGuiRec.add(txtCodBodEgr);
        txtCodBodEgr.setBounds(120, 20, 80, 20);

        txtNomBodEgr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNomBodEgr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomBodEgr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodEgrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodEgrFocusLost(evt);
            }
        });
        txtNomBodEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodEgrActionPerformed(evt);
            }
        });
        panDatGuiRec.add(txtNomBodEgr);
        txtNomBodEgr.setBounds(200, 20, 340, 20);

        butBodEgr.setText("jButton2");
        butBodEgr.setPreferredSize(new java.awt.Dimension(20, 20));
        butBodEgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodEgrActionPerformed(evt);
            }
        });
        panDatGuiRec.add(butBodEgr);
        butBodEgr.setBounds(540, 20, 20, 20);

        lblNumGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblNumGuiRem.setText("Número de guía :");
        panDatGuiRec.add(lblNumGuiRem);
        lblNumGuiRem.setBounds(10, 40, 100, 20);

        txtNumGuiRem.setBackground(objZafParSis.getColorCamposObligatorios());
        txtNumGuiRem.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNumGuiRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumGuiRemKeyPressed(evt);
            }
        });
        panDatGuiRec.add(txtNumGuiRem);
        txtNumGuiRem.setBounds(120, 40, 80, 20);

        panCab.add(panDatGuiRec);
        panDatGuiRec.setBounds(10, 60, 580, 70);

        lblMsg.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMsg.setForeground(new java.awt.Color(0, 0, 255));
        panCab.add(lblMsg);
        lblMsg.setBounds(10, 130, 570, 14);

        panTabGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setPreferredSize(new java.awt.Dimension(685, 160));
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

        spnDat2.setEnabled(false);
        spnDat2.setFocusable(false);
        spnDat2.setPreferredSize(new java.awt.Dimension(1, 1));
        spnDat2.setRequestFocusEnabled(false);

        tblDat2.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat2.setViewportView(tblDat2);
        tblDat2.getAccessibleContext().setAccessibleName("tblDatDos");

        panDet.add(spnDat2, java.awt.BorderLayout.LINE_END);
        spnDat2.getAccessibleContext().setAccessibleName("tblDatDos");

        panTabGen.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panTabGen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        panTooBar.setLayout(new java.awt.BorderLayout());

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
        butGua.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butVisPre.setText("VistaPreliminar");
        butVisPre.setToolTipText("Imprime la orden de Almacenamiento");
        butVisPre.setPreferredSize(new java.awt.Dimension(92, 25));
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

        butImp.setText("Imprimir");
        butImp.setToolTipText("Imprime la orden de Almacenamiento");
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

        panTooBar.add(panBot, java.awt.BorderLayout.CENTER);

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

        panTooBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panTooBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="/* Eventos */">
    
private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    Configura_ventana_consulta();
}//GEN-LAST:event_formInternalFrameOpened

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

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    vcoTipDoc.setTitle("Listado de Tipo de Documentos");
    vcoTipDoc.setCampoBusqueda(1);
    vcoTipDoc.show();
    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE) 
    {
        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
        strCodTipDoc=vcoTipDoc.getValueAt(1);
    }
}//GEN-LAST:event_butTipDocActionPerformed

private void butBodIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodIngActionPerformed
    vcoBodUsrIng.setTitle("Listado de Bodegas");
    vcoBodUsrIng.setCampoBusqueda(1);
    vcoBodUsrIng.show();
    if (vcoBodUsrIng.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
        txtCodBodIng.setText(vcoBodUsrIng.getValueAt(1));
        txtNomBodIng.setText(vcoBodUsrIng.getValueAt(2));
        strCodBod = vcoBodUsrIng.getValueAt(1);
        strNomBod = vcoBodUsrIng.getValueAt(2);
    }
}//GEN-LAST:event_butBodIngActionPerformed

private void txtCodBodIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodIngActionPerformed
    txtCodBodIng.transferFocus();
}//GEN-LAST:event_txtCodBodIngActionPerformed

private void txtNomBodIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodIngActionPerformed
    txtNomBodIng.transferFocus();
}//GEN-LAST:event_txtNomBodIngActionPerformed

private void txtCodBodIngFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodIngFocusGained
    strCodBod=txtCodBodIng.getText();
    txtCodBodIng.selectAll();
}//GEN-LAST:event_txtCodBodIngFocusGained

private void txtNomBodIngFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodIngFocusGained
    strNomBod=txtNomBodIng.getText();
    txtNomBodIng.selectAll();
}//GEN-LAST:event_txtNomBodIngFocusGained

private void txtCodBodIngFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodIngFocusLost
    if (!txtCodBodIng.getText().equalsIgnoreCase(strCodBod)) 
    {
        if (txtCodBodIng.getText().equals("")) 
        {
            txtCodBodIng.setText("");
            txtNomBodIng.setText("");
        }
        else
            BuscarBod("a.co_bod",txtCodBodIng.getText(),0);
    }
    else
        txtCodBodIng.setText(strCodBod);
}//GEN-LAST:event_txtCodBodIngFocusLost

private void txtNomBodIngFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodIngFocusLost
    if (!txtNomBodIng.getText().equalsIgnoreCase(strNomBod))
    {
        if (txtNomBodIng.getText().equals("")) 
        {
            txtCodBodIng.setText("");
            txtNomBodIng.setText("");
        } 
        else
            BuscarBod("a.tx_nom",txtNomBodIng.getText(),1);
    }
    else
        txtNomBodIng.setText(strNomBod);
}//GEN-LAST:event_txtNomBodIngFocusLost

private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
    exitForm();
}//GEN-LAST:event_formInternalFrameClosing

private void txtCodBodEgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodEgrActionPerformed
    txtCodBodEgr.transferFocus();
}//GEN-LAST:event_txtCodBodEgrActionPerformed

private void txtCodBodEgrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodEgrFocusGained
    strCodBodEgr=txtCodBodEgr.getText();
    txtCodBodEgr.selectAll();
}//GEN-LAST:event_txtCodBodEgrFocusGained

private void txtCodBodEgrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodEgrFocusLost
    if (!txtCodBodEgr.getText().equalsIgnoreCase(strCodBodEgr)) 
    {
        if (txtCodBodEgr.getText().equals("")) 
        {
            txtCodBodEgr.setText("");
            txtNomBodEgr.setText("");
        }
        else
            BuscarBodEgr("a.co_bod",txtCodBodEgr.getText(),0);
    }
    else
        txtCodBodEgr.setText(strCodBodEgr);
}//GEN-LAST:event_txtCodBodEgrFocusLost

private void txtNomBodEgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodEgrActionPerformed
     txtNomBodEgr.transferFocus();
}//GEN-LAST:event_txtNomBodEgrActionPerformed

private void txtNomBodEgrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodEgrFocusGained
    strNomBodEgr=txtNomBodEgr.getText();
    txtNomBodEgr.selectAll();
}//GEN-LAST:event_txtNomBodEgrFocusGained

private void txtNomBodEgrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodEgrFocusLost
     if (!txtNomBodEgr.getText().equalsIgnoreCase(strNomBodEgr)) 
     {
        if (txtNomBodEgr.getText().equals("")) 
        {
            txtCodBodEgr.setText("");
            txtNomBodEgr.setText("");
        }
        else
            BuscarBodEgr("a.co_bod",txtNomBodEgr.getText(),0);
    }
     else
        txtNomBodEgr.setText(strNomBodEgr);
}//GEN-LAST:event_txtNomBodEgrFocusLost

private void butBodEgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodEgrActionPerformed
    vcoBodUsrEgr.setTitle("Listado de Bodegas");
    vcoBodUsrEgr.setCampoBusqueda(1);
    vcoBodUsrEgr.show();
    if (vcoBodUsrEgr.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
    {
        txtCodBodEgr.setText(vcoBodUsrEgr.getValueAt(1));
        txtNomBodEgr.setText(vcoBodUsrEgr.getValueAt(2));
        strCodBodEgr = vcoBodUsrEgr.getValueAt(1);
        strNomBodEgr = vcoBodUsrEgr.getValueAt(2);
    }
}//GEN-LAST:event_butBodEgrActionPerformed

private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
    vistaPreliminar();
}//GEN-LAST:event_butVisPreActionPerformed

private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
    if (validarDat()) 
    {
        guardarDat();
        consultaGuia(txtCodBodEgr.getText(), txtCodBodIng.getText(), txtNumGuiRem.getText() );
        //Se habilita el uso de la función imprimirOrdenAlmacenamiento() para que se imprima solo los ítems que se confirman al momento y no la totalidad. 
        imprimirOrdenAlmacenamiento();
    }
}//GEN-LAST:event_butGuaActionPerformed
   
private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
    exitForm();
}//GEN-LAST:event_butCerActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if(validarDat())
             consultaGuia(txtCodBodEgr.getText(), txtCodBodIng.getText(), txtNumGuiRem.getText() );
    }//GEN-LAST:event_butConActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        imprimeDirecto();
    }//GEN-LAST:event_butImpActionPerformed

    private void txtNumGuiRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumGuiRemKeyPressed
        if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) {
            if (validarDat()) {
                consultaGuia(txtCodBodEgr.getText(), txtCodBodIng.getText(), txtNumGuiRem.getText());
            }
        }
    }//GEN-LAST:event_txtNumGuiRemKeyPressed

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Hilos */">
    private boolean vistaPreliminar() 
    {
        if (objThrGUI == null) 
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(1);
            objThrGUI.start();
        }
        return true;
    }
    
    private boolean imprimeDirecto() 
    {
        if (objThrGUI == null)
        {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.setIndFunEje(0);
            objThrGUI.start();
        }
        return true;
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
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
            if(cargarDoc())
            {
                    switch (intIndFun)
                    {
                        case 0: //Botón "Imprimir".
                            //System.out.println("HILO: Imprimir");
                            generarRpt(1);
                            break;
                        case 1: //Botón "Vista Preliminar".
                            //System.out.println("HILO: Vista Preliminar");
                            generarRpt(2);
                            break;
                    }
            }
            objThrGUI=null;
        }
        
        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/*  Variables declaration - do not modify  */">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBodEgr;
    private javax.swing.JButton butBodIng;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVisPre;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel lblBodEgr;
    private javax.swing.JLabel lblBodIng;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumGuiRem;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDatGuiRec;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JPanel panTooBar;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDat2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDat2;
    private javax.swing.JTextField txtCodBodEgr;
    private javax.swing.JTextField txtCodBodIng;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodEgr;
    private javax.swing.JTextField txtNomBodIng;
    private javax.swing.JTextField txtNumGuiRem;
    // End of variables declaration//GEN-END:variables
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Funciones Varias */">
    
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
    
    private void almacenarArchHis(String strMenArchHis) 
    {
        try
        {
            if (objZafParSis.getCodigoEmpresa() == 1) 
            {
                java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(strNomArchHis, true));
                java.io.PrintWriter salida = new java.io.PrintWriter(bw);
                salida.println(strMenArchHis);
                // salida.println("");
                salida.close();
            }
        } 
        catch (java.io.IOException Ext) { objUti.mostrarMsgErr_F1(this, Ext);  }
    }

    private void bloquea(javax.swing.JTextField txtFiel, boolean blnEst)
    {
        java.awt.Color colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }
    
    private void llamarVenObs(String strObs, int intFil, int intCol, boolean blnEstButAce) 
    {
        CxC.ZafCxC72.ZafCxC72_02 obj1 = new CxC.ZafCxC72.ZafCxC72_02(javax.swing.JOptionPane.getFrameForComponent(this), true, strObs, blnEstButAce);
        obj1.show();
        if (obj1.getAceptar()) 
        {
            tblDat.setValueAt(obj1.getObser(), intFil, intCol);
        }
        obj1 = null;
    }
    
    private void exitForm()
    {
        
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) 
        {
            if (rstCab != null) 
                rstCab = null;
            
            if (STM_GLO != null) 
                STM_GLO = null;

            dispose();
            Runtime.getRuntime().gc();
        }
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
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="/* Funciones Y Métodos */">
    
    /**
     * Función que realiza las validaciones correspondientes de los datos.
     * @return true: Cumple con todas las validaciones correspondientes.
     * <BR>false: En el caso contrario.
     */
    private boolean validarDat()
    {
        boolean blnRes = true;

        if (txtCodTipDoc.getText().equals("")) 
        {
            mostrarMsgInf("EL TIPO DE DOCUMENTO ES OBLIGATORIO.");
            tabFrm.setSelectedIndex(0);
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        if (txtCodBodIng.getText().equals("")) 
        {
            mostrarMsgInf("LA BODEGA DE INGRESO ES OBLIGATORIA.");
            tabFrm.setSelectedIndex(0);
            txtCodBodIng.requestFocus();
            return false;
        }

        if (txtCodBodEgr.getText().equals("")) 
        {
            mostrarMsgInf("LA BODEGA DE EGRESO ES OBLIGATORIA.");
            tabFrm.setSelectedIndex(0);
            txtCodBodEgr.requestFocus();
            return false;
        }

        if (txtNumGuiRem.getText().equals("")) 
        {
            mostrarMsgInf("EL NÚMERO DE LA GUÍA DE REMISIÓN ES OBLIGATORIO.");
            tabFrm.setSelectedIndex(0);
            txtNumGuiRem.requestFocus();
            return false;
        }

        return blnRes;
    }

    /**
     * Método que realiza la consulta de la guía relacionada a confirmarse el ingreso.
     * Esta función es utilizada por el botón CONSULTAR.
     * @param strCodBodEgrGuia Código de la bodega origen, bodega que envía la mercadería.
     * @param strCodBodIngGuia Código de la bodega destino, bodega que recibe la mercadería.
     * @param strNumGui Número de la guía de remisión relacionada.
     */
    public void consultaGuia(String strCodBodEgrGuia, String strCodBodIngGuia, String strNumGui)
    {
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=false;
        int intCanReg=0;
        String strSQL = "";
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null)
            {
                stmLoc = conn.createStatement();

                objTblMod.removeAllRows();

                strSQL =  " SELECT count(a.co_emp) as cantReg \n"
                        + " FROM tbm_cabguirem as a \n"
                        + " INNER JOIN tbm_cabtipdoc  AS a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc ) \n"
                        + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) \n"
                        + " INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_emp=a.co_emp AND a7.co_bod=a.co_ptoDes) \n" 
                        + " WHERE ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=( " + strCodBodEgrGuia + " ) ) "
                        + " AND ( a7.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a7.co_bodGrp=( " + strCodBodIngGuia + " ) )\n" 
                        + " AND ne_numdoc = " + strNumGui + "  ";
                
                System.out.println("consultaGuia: "+strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next())
                {
                    blnRes=true;
                    intCanReg=rstLoc.getInt("cantReg");
        
                }
                if(blnRes)
                {
                    if (intCanReg == 1) 
                    {
                        cargarDocGuia(conn, strCodBodEgrGuia, strCodBodIngGuia, strNumGui);
                    } 
                    else 
                    {
                        cargarVenBusDoc(conn, strCodBodEgrGuia, strCodBodIngGuia, strNumGui);
                    }
                }
                
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conn.close();
                conn = null;
            }
        }
        catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
        catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
    }
    
    /**
     * Función que carga en pantalla los datos de la guía de remisión relacionada de la cual se confirmará el ingreso.
     * Ésta función es utilizada en caso de existir solo un registro con el mismo número de guía de remisión.
     * @param conn Conexión enviada.
     * @param strCodBodEgrGuia Código de la bodega origen, bodega que envía la mercadería.
     * @param strCodBodIngGuia Código de la bodega destino, bodega que recibe la mercadería.
     * @param strNumGui Número de la guía de remisión relacionada.
     */
    public void cargarDocGuia(java.sql.Connection conn, String strCodBodEgrGuia, String strCodBodIngGuia, String strNumGui) 
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSQL = "";
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                strSQL =  " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a4.tx_descor, a4.tx_deslar, a.ne_numdoc, a.fe_doc, \n"
                        + "        a.tx_nomclides,  a.st_tipguirem, a.tx_datdocoriguirem, a.co_ptoPar , a.co_ptoDes \n"
                        + " FROM tbm_cabguirem as a \n"
                        + " INNER JOIN tbm_cabtipdoc AS a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc ) \n"
                        + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) \n"
                        + " INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_emp=a.co_emp AND a7.co_bod=a.co_ptoDes) \n" 
                        + " WHERE  ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=( " + strCodBodEgrGuia + " ) ) "
                        + " AND ( a7.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a7.co_bodGrp=( " + strCodBodIngGuia + " ) )\n" 
                        + " AND ne_numdoc = " + strNumGui + "  ";

                System.out.println("cargarDocGuia: " + strSQL);

                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next()) 
                {
                    if (rstLoc.getString("st_tipguirem").equals("P")) 
                    {
                        lblMsg.setText("ESTO ES UNA ORDEN DE DESPACHO.");
                    } 
                    else 
                    {
                        lblMsg.setText("ESTO ES UNA GUIA DE REMISIÓN Y LA ORDEN DE DESPACHO ES " + rstLoc.getString("tx_datdocoriguirem"));
                    }
                    
                    cargarDetReg(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getString("st_tipguirem"));
                    
                }
                rstLoc.close();
                rstLoc = null;

                stmLoc.close();
                stmLoc = null;
            }
        } 
        catch (java.sql.SQLException e) {    objUti.mostrarMsgErr_F1(this, e);  } 
        catch (Exception Evt) {  objUti.mostrarMsgErr_F1(this, Evt); }
    }
    
    /**
     * Función que carga una ventana de consulta con un listado de guías de remisión con el mismo número de documento.
     * Ésta función es utilizada en caso de existir más de un registro con el mismo número de guía de remisión.
     * Carga en pantalla los datos de la guía de remisión seleccionada de la ventana de consulta.
     * @param conn Conexión enviada.
     * @param strCodBodEgrGuia Código de la bodega origen, bodega que envía la mercadería.
     * @param strCodBodIngGuia Código de la bodega destino, bodega que recibe la mercadería.
     * @param strNumGui Número de la guía de remisión relacionada.
     */
    private void cargarVenBusDoc(java.sql.Connection conn, String strCodBodEgrGuia, String strCodBodIngGuia, String strNumGui)
     {
        String strSQL = "";

        strSQL =  " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a4.tx_descor, a4.tx_deslar, a.co_doc,"
                + "        a.ne_numdoc, a.fe_doc, a.co_clides, a.tx_nomclides, a.st_tipguirem  \n"
                + " FROM tbm_cabguirem as a \n"
                + " INNER JOIN tbm_cabtipdoc  AS a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc ) \n"
                + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) \n"
                + " INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_emp=a.co_emp AND a7.co_bod=a.co_ptoDes) \n" 
                + " WHERE  ( a6.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a6.co_bodGrp=( " + strCodBodEgrGuia + " ) ) "
                + " AND ( a7.co_empGrp=" + objZafParSis.getCodigoEmpresaGrupo() + " AND a7.co_bodGrp=( " + strCodBodIngGuia + " ) )\n" 
                + " AND ne_numdoc = " + strNumGui + "  ";

        System.out.println("cargarVenBusDoc: "+strSQL);
        Compras.ZafCom70.ZafCom70_01 obj = new Compras.ZafCom70.ZafCom70_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis, strSQL);
        obj.show();
        if (obj.acepta()) 
        {
            cargarDetReg(conn, Integer.parseInt(obj.GetCamSel(1)), Integer.parseInt(obj.GetCamSel(2)), Integer.parseInt(obj.GetCamSel(3)), Integer.parseInt(obj.GetCamSel(4)), obj.GetCamSel(5));
        }
        obj.dispose();
        obj = null;
    }
    
    /**
     * Esta función permite consultar los registros de la guía de remisión consultada.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     * Además valida que no se permita registrar una cantidad distinta a la cantidad confirmada en la guía.
     */
    private boolean cargarDetReg(java.sql.Connection conn, int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodDocGui, String strTipGui) 
    {
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                // Arma sentencia SQL: José Marín M. 11/Agosto/2014
                if (strTipGui.equals("P")) // OD
                {
                    strSql =  " SELECT * \n ";
                    strSql += " FROM (   \n";
                    strSql += "       SELECT cantotconf, case when cantotconf = can then 'C' else  case when cantotconf > 0 then  'E' else 'P' end end  as estconf, x.* \n ";
                    strSql += "       FROM( \n";
                    strSql += "               SELECT (cancon+cannunrec+canmalest) as cantotconf, x.*  \n";
                    strSql += "               FROM ( \n";
                    strSql += "                       SELECT (case when a2.nd_can is null then 0 else a2.nd_can end ) as can, \n";
                    strSql += "                              (case when a2.nd_cancon is null then 0 else a2.nd_cancon end ) as cancon, \n";
                    strSql += "                              (case when a2.nd_cannunrec is null then 0 else a2.nd_cannunrec end ) as cannunrec, \n";
                    strSql += "                              (case when a2.nd_cantotmalest is null then 0 else a2.nd_cantotmalest end ) as canmalest, \n ";
                    strSql += "                              a4.tx_natDoc, a2.co_Bod, a2.st_meringegrfisbod, a2.co_emp, a2.co_loc,   \n";
                    strSql += "                              a2.co_tipdoc, a2.co_doc, a2.co_reg, a4.tx_descor, a4.tx_deslar, a3.ne_numdoc,\n";
                    strSql += "                              a3.fe_doc ,a2.co_itm, a2.tx_codalt,a7.tx_codAlt2, a2.tx_nomitm, a2.tx_unimed,  \n";
                    strSql += "                              a2.nd_can, a2.nd_cancon, a2.nd_cantotmalest ,a2.nd_cannunrec,  \n";
                    strSql += "                              a.co_loc as co_locrelguirem, a.co_tipdoc as co_tipdocguirem,  \n";
                    strSql += "                              a.co_doc as co_docrelguirem, a.co_reg as co_regrelguirem,   \n";
                    strSql += "                              case when a.nd_cancon is null then 0 else a.nd_cancon end as nd_cancongui  \n";
                    strSql += "                       FROM tbm_detguirem as a  \n";
                    strSql += "                       INNER JOIN tbr_detmovinv AS a1 ON (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and  \n ";
                    strSql += "                                                          a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel)\n";
                    strSql += "                       INNER JOIN tbm_detmovinv AS a2 ON (a2.co_emp=a1.co_emprel and a2.co_loc=a1.co_locrel and   \n";
                    strSql += "                                                          a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel) \n";
                    strSql += "                       INNER JOIN ( \n";
                    strSql += "                                   SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2,a2.co_bod,a2.st_impOrd \n";
                    strSql += "                                   FROM tbm_inv as a1 \n";
                    strSql += "                                   INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                    strSql += "                                  ) as a7 ON (a2.co_emp=a7.co_emp AND a2.co_itm=a7.co_itm AND a2.co_bod=a7.co_bod) \n";
                    strSql += "                       INNER JOIN tbm_cabmovinv AS a3 ON (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc )  \n";
                    strSql += "                       INNER JOIN tbm_cabtipdoc  AS a4 ON (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc )  \n";
                    strSql += "                       WHERE  a.co_emp=" + intCodEmpGui + " and a.co_loc=" + intCodLocGui + " and a.co_tipdoc=" + intCodTipDocGui + " and a.co_doc= " + intCodDocGui + "  \n";
                    strSql += "               ) AS x ) AS x ) AS x /*WHERE  estconf in ('P','E')*/  \n ";
                } 
                else // GR
                {
                    strSql = " SELECT *  \n";
                    strSql += " FROM ( \n";
                    strSql += "      SELECT cantotconf, case when cantotconf = can then 'C' else case when cantotconf > 0 then  'E' else 'P' end end  as estconf, x.*  \n";
                    strSql += "      FROM ( \n";
                    strSql += "               SELECT (cancon+cannunrec+canmalest) as cantotconf, x.*  \n";
                    strSql += "               FROM ( \n";
                    strSql += "                     SELECT (case when a4.nd_can is null then 0 else a4.nd_can end ) as can, \n";
                    strSql += "                            (case when a4.nd_cancon is null then 0 else a4.nd_cancon end ) as cancon, \n ";
                    strSql += "                            (case when a4.nd_cannunrec is null then 0 else a4.nd_cannunrec end ) as cannunrec, \n ";
                    strSql += "                            (case when a4.nd_cantotmalest is null then 0 else a4.nd_cantotmalest end ) as canmalest, \n";
                    strSql += "                             a6.tx_natDoc, a4.co_Bod, a4.st_meringegrfisbod, a4.co_emp, a4.co_loc, a4.co_tipdoc, \n ";
                    strSql += "                             a4.co_doc, a4.co_reg, a6.tx_descor, a6.tx_deslar, a5.ne_numdoc, \n";
                    strSql += "                             a5.fe_doc ,a4.co_itm, a4.tx_codalt, a7.tx_codAlt2, a4.tx_nomitm, a4.tx_unimed, a4.nd_can, \n ";
                    strSql += "                             a4.nd_cancon, a4.nd_cantotmalest, a4.nd_cannunrec, \n";
                    strSql += "                             a.co_loc as co_locrelguirem, a.co_tipdoc as co_tipDocGuiRem, a.co_doc as co_docrelguirem, a.co_reg as co_regrelguirem,  \n";
                    strSql += "                             case when a.nd_cancon is null then 0 else a.nd_cancon end as nd_cancongui, \n ";
                    strSql += "                             case when a2.nd_cancon is null then 0 else a2.nd_cancon end as nd_canconguitot,a7.st_impOrd \n";
                    strSql += "                     FROM tbm_detguirem as a \n";
                    strSql += "                     INNER JOIN tbr_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and ";
                    strSql += "                                                        a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg) \n";
                    strSql += "                     INNER JOIN tbm_detguirem AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and  ";
                    strSql += "                                                        a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel)  \n";
                    strSql += "                     INNER JOIN tbr_detmovinv AS a3 ON (a3.co_emp=a2.co_emprel and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and ";
                    strSql += "                                                        a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel)  \n";
                    strSql += "                     INNER JOIN tbm_detmovinv AS a4 ON (a4.co_emp=a3.co_emprel and a4.co_loc=a3.co_locrel and a4.co_tipdoc=a3.co_tipdocrel and  ";
                    strSql += "                                                        a4.co_doc=a3.co_docrel and a4.co_reg=a3.co_regrel) \n";
                    strSql += "                     INNER JOIN ( \n";
                    strSql += "                                 SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2,a2.co_bod,a2.st_impOrd \n";
                    strSql += "                                 FROM tbm_inv as a1 \n";
                    strSql += "                                 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                    strSql += "                                 ) as a7 ON (a4.co_emp=a7.co_emp AND a4.co_itm=a7.co_itm AND a4.co_bod=a7.co_bod) \n";
                    strSql += "                     INNER JOIN tbm_cabmovinv AS a5 ON (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc and a5.co_doc=a4.co_doc ) \n";
                    strSql += "                     INNER JOIN tbm_cabtipdoc AS a6 ON (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_loc and a6.co_tipdoc=a5.co_tipdoc ) \n";
                    strSql += "                     WHERE  a.co_emp=" + intCodEmpGui + " and a.co_loc=" + intCodLocGui + " and a.co_tipdoc=" + intCodTipDocGui + " and a.co_doc= " + intCodDocGui + " \n";
                    strSql += " ) AS x ) AS x ) AS x /*WHERE estconf in ('P','E')*/ \n";
                }
                
                System.out.println("ZafCom70.cargarDetReg: \n" + strSql); //// José Marín M.
                
                //Variables que contienen la pk de la guia relacionada a confirmar.
                strCodEmpGui = String.valueOf(intCodEmpGui);
                strCodLocGui = String.valueOf(intCodLocGui);
                strCodTipDocGui = String.valueOf(intCodTipDocGui);
                strCodDocGui = String.valueOf(intCodDocGui);
                
                
                System.out.println("cargarDetReg.PK.GuiRem:   " + strCodEmpGui + " = " + strCodLocGui + " = " + strCodTipDocGui + " = " + strCodDocGui);
                
                vecDat.clear();
                vecTblDatDos.clear();
                rstLoc = stmLoc.executeQuery(strSql);
                intNunItm = 0;
                while (rstLoc.next()) 
                {
                    //RoseABC
                    intCodEmpDocOri=rstLoc.getInt("co_emp");           //Código Empresa del Documento Origen de Confirmación.Ej.:TRAINV, TRINRB
                    intCodLocDocOri=rstLoc.getInt("co_loc");           //Código Local del Documento Origen de Confirmación.Ej.:TRAINV, TRINRB
                    intCodTipDocDocOri=rstLoc.getInt("co_tipdoc");     //Código Tipo Documento del Documento Origen de Confirmación.Ej.:TRAINV, TRINRB
                    intCodDocDocOri=rstLoc.getInt("co_doc");           //Código Documento del Documento Origen de Confirmación.Ej.:TRAINV, TRINRB
                    intCodBodIng=rstLoc.getInt("co_Bod");              //Código de Bodega donde ingresa la mercaderia, código de acuerdo a la empresa.
                    
                    //Calcular el total de Confirmacion de Ingreso.
                    dblTotalConfirmado = objUti.redondear(rstLoc.getDouble("nd_cancon")  + rstLoc.getDouble("nd_cannunrec") + rstLoc.getDouble("nd_cantotmalest"), objZafParSis.getDecimalesMostrar());

                    System.out.println("dblTotalCantidad:"+rstLoc.getDouble("nd_can")+" = dblTotalConfirmado: "+dblTotalConfirmado);
                    
                    vecReg = new Vector();
                    vecTblRegDos = new Vector();
                    
                    //if (!rstLoc.getString("st_impOrd").equals("S") || rstLoc.getString("nd_cancon").equals(rstLoc.getString("nd_can"))) 
                    if (!rstLoc.getString("st_impOrd").equals("S") || (rstLoc.getDouble("nd_can")==dblTotalConfirmado) )
                    {
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                        vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                        vecReg.add(INT_TBL_DESCORTIPDOC, rstLoc.getString("tx_descor"));
                        vecReg.add(INT_TBL_DESLARTIPDOC, rstLoc.getString("tx_deslar"));
                        vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                        vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                        vecReg.add(INT_TBL_CODALTITM, rstLoc.getString("tx_codalt"));
                        vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                        vecReg.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                        vecReg.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));                // CANTIDAD
                        vecReg.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));          // CANTIDAD TOTAL CONFIRMADA 
                        vecReg.add(INT_TBL_CANCON, "");                                        // CANTIDAD A CONFIRMAR 
                        vecReg.add(INT_TBL_CANNUNREC, "");                                     // CANTIDAD NUNCA RECIBIDA
                        vecReg.add(INT_TBL_CANMALEST, "");                                     // CANTIDAD EN MAL ESTADO
                        vecReg.add(INT_TBL_CANTOTNUNREC, rstLoc.getString("nd_cannunrec"));    // CANTIDAD TOTAL NUNCA RECIBIDA 
                        vecReg.add(INT_TBL_CANTOTMALEST, rstLoc.getString("nd_cantotmalest")); // CANTIDAD TOTAL EN MAL ESTADO
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_BUTOBS, "");
                        
                        vecReg.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                        vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_Bod"));
                        vecReg.add(INT_TBL_NATDOC, rstLoc.getString("tx_natDoc"));
                        vecReg.add(INT_TBL_CANNUNREC_ORI, "");
                        vecReg.add(INT_TBL_CANCONGUI_ORI, rstLoc.getString("nd_cancongui"));
                        vecReg.add(INT_TBL_CODEMPRELGUIREM, intCodEmpGui);
                        vecReg.add(INT_TBL_CODLOCRELGUIREM, rstLoc.getString("co_locrelguirem"));
                        vecReg.add(INT_TBL_CODTIPDOCRELGUIREM, rstLoc.getString("co_tipdocguirem"));
                        vecReg.add(INT_TBL_CODDOCRELGUIREM, rstLoc.getString("co_docrelguirem"));
                        vecReg.add(INT_TBL_CODREGRELGUIREM, rstLoc.getString("co_regrelguirem"));
                        vecReg.add(INT_TBL_CANCONGUITOT, rstLoc.getString("nd_canconguitot"));
                        vecReg.add(INT_TBL_CODLETITM, rstLoc.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_STIMPORD, rstLoc.getString("st_impOrd"));
                    }
                    else 
                    {
                        vecReg.add(INT_TBL_LINEA, "");
                        vecReg.add(INT_TBL_CODEMP, "");
                        vecReg.add(INT_TBL_CODLOC, "");
                        vecReg.add(INT_TBL_CODTIPDOC, "");
                        vecReg.add(INT_TBL_CODDOC, "");
                        vecReg.add(INT_TBL_CODREG, "");
                        vecReg.add(INT_TBL_DESCORTIPDOC, "");
                        vecReg.add(INT_TBL_DESLARTIPDOC, "");
                        vecReg.add(INT_TBL_NUMDOC, "");
                        vecReg.add(INT_TBL_FECDOC, "");
                        vecReg.add(INT_TBL_CODITM, "");
                        vecReg.add(INT_TBL_CODALTITM, "");
                        vecReg.add(INT_TBL_NOMITM, "");
                        vecReg.add(INT_TBL_DESUNI, "");
                        vecReg.add(INT_TBL_CANMOV, "");             // CANTIDAD
                        vecReg.add(INT_TBL_CANTOTCON, "");          // CANTIDAD TOTAL CONFIRMADA 
                        vecReg.add(INT_TBL_CANCON, "");             // CANTIDAD A CONFIRMAR \
                        vecReg.add(INT_TBL_CANNUNREC, "");          // CANTIDAD NUNCA RECIBIDA
                        vecReg.add(INT_TBL_CANMALEST, "");          // CANTIDAD EN MAL ESTADO
                        vecReg.add(INT_TBL_CANTOTNUNREC, "");       // CANTIDAD TOTAL NUNCA RECIBIDA 
                        vecReg.add(INT_TBL_CANTOTMALEST, "");
                        vecReg.add(INT_TBL_OBSITMCON, "");
                        vecReg.add(INT_TBL_BUTOBS, "");
                        
                        vecReg.add(INT_TBL_IEBODFIS, "");
                        vecReg.add(INT_TBL_CODBOD, "");
                        vecReg.add(INT_TBL_NATDOC, "");
                        vecReg.add(INT_TBL_CANNUNREC_ORI, "");
                        vecReg.add(INT_TBL_CANCONGUI_ORI, "");
                        vecReg.add(INT_TBL_CODEMPRELGUIREM, "");
                        vecReg.add(INT_TBL_CODLOCRELGUIREM, "");
                        vecReg.add(INT_TBL_CODTIPDOCRELGUIREM, "");
                        vecReg.add(INT_TBL_CODDOCRELGUIREM, "");
                        vecReg.add(INT_TBL_CODREGRELGUIREM, "");
                        vecReg.add(INT_TBL_CANCONGUITOT, "");
                        vecReg.add(INT_TBL_CODLETITM, "");
                        vecReg.add(INT_TBL_STIMPORD, "");
                    }

                    intNunItm++;

                    vecTblRegDos.add(INT_TBL_LINEA, "");
                    vecTblRegDos.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
                    vecTblRegDos.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
                    vecTblRegDos.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoc"));
                    vecTblRegDos.add(INT_TBL_CODDOC, rstLoc.getString("co_doc"));
                    vecTblRegDos.add(INT_TBL_CODREG, rstLoc.getString("co_reg"));
                    vecTblRegDos.add(INT_TBL_DESCORTIPDOC, rstLoc.getString("tx_descor"));
                    vecTblRegDos.add(INT_TBL_DESLARTIPDOC, rstLoc.getString("tx_deslar"));
                    vecTblRegDos.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numDoc"));
                    vecTblRegDos.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc"));
                    vecTblRegDos.add(INT_TBL_CODITM, rstLoc.getString("co_itm"));
                    vecTblRegDos.add(INT_TBL_CODALTITM, rstLoc.getString("tx_codalt"));
                    vecTblRegDos.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm"));
                    vecTblRegDos.add(INT_TBL_DESUNI, rstLoc.getString("tx_unimed"));
                    vecTblRegDos.add(INT_TBL_CANMOV, rstLoc.getString("nd_can"));               // CANTIDAD
                    vecTblRegDos.add(INT_TBL_CANTOTCON, rstLoc.getString("nd_cancon"));         // CANTIDAD TOTAL CONFIRMADA 
                    vecTblRegDos.add(INT_TBL_CANCON, "");                                       // CANTIDAD A CONFIRMAR 
                    vecTblRegDos.add(INT_TBL_CANNUNREC, "");                                    // CANTIDAD NUNCA RECIBIDA
                    vecTblRegDos.add(INT_TBL_CANMALEST, "");                                    // CANTIDAD EN MAL ESTADO
                    vecTblRegDos.add(INT_TBL_CANTOTNUNREC, rstLoc.getString("nd_cannunrec"));   // CANTIDAD TOTAL NUNCA RECIBIDA 
                    vecTblRegDos.add(INT_TBL_CANTOTMALEST, rstLoc.getString("nd_cantotmalest"));
                    vecTblRegDos.add(INT_TBL_OBSITMCON, "");
                    vecTblRegDos.add(INT_TBL_BUTOBS, "");
                    
                    vecTblRegDos.add(INT_TBL_IEBODFIS, rstLoc.getString("st_meringegrfisbod"));
                    vecTblRegDos.add(INT_TBL_CODBOD, rstLoc.getString("co_Bod"));
                    vecTblRegDos.add(INT_TBL_NATDOC, rstLoc.getString("tx_natDoc"));
                    vecTblRegDos.add(INT_TBL_CANNUNREC_ORI, "");
                    vecTblRegDos.add(INT_TBL_CANCONGUI_ORI, rstLoc.getString("nd_cancongui"));
                    vecTblRegDos.add(INT_TBL_CODEMPRELGUIREM, intCodEmpGui);
                    vecTblRegDos.add(INT_TBL_CODLOCRELGUIREM, rstLoc.getString("co_locrelguirem"));
                    vecTblRegDos.add(INT_TBL_CODTIPDOCRELGUIREM, rstLoc.getString("co_tipdocguirem"));
                    vecTblRegDos.add(INT_TBL_CODDOCRELGUIREM, rstLoc.getString("co_docrelguirem"));
                    vecTblRegDos.add(INT_TBL_CODREGRELGUIREM, rstLoc.getString("co_regrelguirem"));
                    vecTblRegDos.add(INT_TBL_CANCONGUITOT, rstLoc.getString("nd_canconguitot"));
                    vecTblRegDos.add(INT_TBL_CODLETITM, rstLoc.getString("tx_codAlt2"));
                    vecTblRegDos.add(INT_TBL_STIMPORD, rstLoc.getString("st_impOrd"));

                    vecDat.add(vecReg);// No se agregan los que tengan codigo de las 3 letras José Marín 
                    vecTblDatDos.add(vecTblRegDos);
                }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);

                objTblMod2.setData(vecTblDatDos);
                // José Marín M 12/Agos/2014
                tblDat2.setModel(objTblMod2);

                vecDat.clear();

                rstLoc.close();
                stmLoc.close();
                rstLoc = null;
                stmLoc = null;

            }
        }
        catch (java.sql.SQLException e) {    blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e) {     blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
 
    private boolean guardarDat() 
    {
        boolean blnRes = false;
        java.sql.Connection conn;
        String strCorEleTo = "";
        try 
        {
            conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conn != null) 
            {
                conn.setAutoCommit(false);

                strBuf = new StringBuffer();
                blnEstMalEst = false;

                Librerias.ZafCorElePrg.ZafCorElePrg objCorEle = new Librerias.ZafCorElePrg.ZafCorElePrg(objZafParSis, this);
                strCorEleTo = objCorEle._getCorEleConfIngRel_01();
                objCorEle = null;

                if (guardarReg(conn)) 
                {
                    if (verificaCanNunEnv()) 
                    {
                        conn.commit();
                        blnRes = true;

                        if (blnEstMalEst) 
                        {
                            String strMensCorEle = "Confirmacion de mercaderia en mal estado:  ";
                            strMensCorEle += " <br> " + strBuf.toString();
                            objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia en mal estado.", strMensCorEle);
                        }

                        if (blnEstCanNunEnv) 
                        {
                            strItmConCanNunEnv = "Confirmacion de mercaderia que no se recibe en: <br> " + txtNomBodIng.getText() + "  <br>  " + strItmConCanNunEnv;
                            objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Mercaderia que no se recibe.", strItmConCanNunEnv);
                        }

                        mostrarMsgInf("LOS DATOS SE GUARDARON CON ÉXITO. ");

                    } 
                    else
                        conn.rollback();
                } 
                else
                    conn.rollback();

                strBuf = null;

                conn.close();
                conn = null;
            }
        } 
        catch (java.sql.SQLException Evt) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception Evt) {   blnRes = false;   objUti.mostrarMsgErr_F1(this, Evt);    }

        //System.gc();
        Runtime.getRuntime().gc();
        return blnRes;
    }

    /**
     * Función que guarda el registro de la confirmación de ingreso realizada.
     * Esta función no permite registrar una cantidad distinta a la confirmada en la guía de remisión relacionada.
     * @param conn
     * @return true: Si se guardan los registros.
     * <BR>false: En el caso contrario.
     */
    public boolean guardarReg(java.sql.Connection conn) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.util.Date datFecAux;         //Fecha del servidor.  
        String strSQL = "", strObs = "";
        
        /* Variables de Registros y código del Item */
        int intCodRegItmIng = 1;          //Código de Registro del ítem a Ingresarse.
        int intCodRegGuiRemRel=0;         //Código Registro del Guia.Ej.:GUIREM
        String strCodRegItmDocOri = "", strCodItmIng = "";  
        
        /* Can_Con -  Can_MalEst - Can_NunRec */
        String strCanConf = "", strCanMalEst = "", strCantNunRec = "";        
        double dblCanConf = 0, dblCanMalEst = 0, dblCanNunRec = 0;        

        /* Totales: Can_Con -  Can_MalEst - Can_NunRec  - Can_TotGuiRem  - Can_GuiRem */
        String strCanTotCon = "", strCanTotMalEst = "", strCanTotNunRec = "", strCanTotConGui = "", strCanConGui = "";     
        double dblCanTotCon = 0, dblCanTotMalEst = 0, dblCanTotNunRec = 0, dblCanTotConGui = 0, dblCanConGui = 0;      
        
        String strEstMerFisBod = "";     //Estado de Mercaderia Ingreso Fisico de Bodega.
        
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();
                intCodTipDocIng= Integer.parseInt(txtCodTipDoc.getText());
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) 
                {
                    return false;
                }

                strBuf.append("<table border=1> <tr><td> BODEGA </TD> <td> CODIGO </td> <TD> DESCRIPCION</TD> <td> CANTIDAD </td></tr> ");

                /* Rose */
                
                //Cabecera de Confirmación de Ingreso (Relacionadas)
                intCodDocIng = getCodigoDoc(conn, intCodEmpDocOri, intCodLocDocOri, intCodTipDocIng); 
                intNumDocIng = getUltNumDoc(conn, intCodEmpDocOri, intCodLocDocOri, intCodTipDocIng);
                
                System.out.println("intCodDocIng: "+intCodDocIng); System.out.println("intNumDocIng: "+intNumDocIng);
                
                strSQL  = " INSERT INTO tbm_cabingegrmerbod("
                        + " co_emp, co_loc, co_tipdoc, co_doc, "
                        + " fe_doc, ne_numdoc, co_bod, "
                        + " co_locrel, co_tipdocrel, co_docrel, "
                        + " co_mnu, st_imp, tx_obs1, tx_obs2, st_reg, "
                        + " fe_ing, co_usring ) "
                        + " VALUES( "
                        + " " + intCodEmpDocOri + ", " + intCodLocDocOri + ", " +intCodTipDocIng+ ", " + intCodDocIng + ", "
                        + " '" + datFecAux + "', " + intNumDocIng + ", " + intCodBodIng + ", "
                        + " " + intCodLocDocOri + ", " + intCodTipDocDocOri + ", " + intCodDocDocOri + ", "
                        + " " + objZafParSis.getCodigoMenu() + ", 'N', '" + strObs + "', '[Ing.Maq]', 'A', "
                        + " " + objZafParSis.getFuncionFechaHoraBaseDatos() + ", " + objZafParSis.getCodigoUsuario() + " "
                        + " ) ; ";

                strSQL += " UPDATE tbm_cabtipdoc SET ne_ultdoc=" + intNumDocIng + " "
                        + " WHERE co_emp=" + intCodEmpDocOri + " AND co_loc=" + intCodLocDocOri + " "
                        + " AND co_tipdoc=" + intCodTipDocIng + " ; ";
                
                //Detalle de Confirmación de Ingreso (Relacionadas)
                for (int intCelSel = 0; intCelSel < tblDat.getRowCount(); intCelSel++) 
                {
                    strCodRegItmDocOri = tblDat.getValueAt(intCelSel, INT_TBL_CODREG).toString();
                    strCodItmIng = tblDat.getValueAt(intCelSel, INT_TBL_CODITM).toString();

                    //strCanMov = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMOV));
                    strCanConf = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCON));
                    strCantNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC));
                    strCanMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST));

                    strCanTotCon = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTCON));
                    strCanTotNunRec = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTNUNREC));
                    strCanTotMalEst = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANTOTMALEST));
                    strCanTotConGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUITOT));

                    strEstMerFisBod = (tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS) == null ? "N" : tblDat.getValueAt(intCelSel, INT_TBL_IEBODFIS).toString());
                    strTipIngEgr = tblDat.getValueAt(intCelSel, INT_TBL_NATDOC).toString();

                    strObs = (tblDat.getValueAt(intCelSel, INT_TBL_OBSITMCON) == null ? "" : tblDat.getValueAt(intCelSel, INT_TBL_OBSITMCON).toString());

                    strCanConGui = objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CANCONGUI_ORI));

                    dblCanConf = objUti.redondear(objUti.parseDouble(strCanConf), 4);
                    dblCanMalEst = objUti.redondear(objUti.parseDouble(strCanMalEst), 4);
                    dblCanNunRec = objUti.redondear(objUti.parseDouble(strCantNunRec), 4);

                    dblCanTotCon = objUti.redondear(objUti.parseDouble(strCanTotCon), 4);
                    dblCanTotMalEst = objUti.redondear(objUti.parseDouble(strCanTotMalEst), 4);
                    dblCanTotNunRec = objUti.redondear(objUti.parseDouble(strCanTotNunRec), 4);
                    dblCanTotConGui = objUti.redondear(objUti.parseDouble(strCanTotConGui), 4);

                    dblCanConGui = objUti.redondear(objUti.parseDouble(strCanConGui), 4);

                    intCodEmpGuiRemRel = Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODEMPRELGUIREM)));
                    intCodLocGuiRemRel = Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODLOCRELGUIREM)));
                    intCodTipDocGuiRemRel = Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODTIPDOCRELGUIREM)));
                    intCodDocGuiRemRel = Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODDOCRELGUIREM)));
                    intCodRegGuiRemRel = Integer.parseInt(objInvItm.getIntDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODREGRELGUIREM)));

                    if (dblCanConf > 0) 
                    {
                        String strMerMalEst = "";
                        if (dblCanMalEst > 0)
                        {
                            strMerMalEst = "P";
                            String text = " <tr>"
                                        + " <td> " + txtNomBodIng.getText() + " </td> "
                                        + " <td> " + objInvItm.getStringDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_CODALTITM)) + " </td> "
                                        + " <td> " + objInvItm.getStringDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_NOMITM)) + " </td>"
                                        + " <td> " + dblCanMalEst + " </td> "
                                        + " </tr>";
                            strBuf.append(text);
                            blnEstMalEst = true;
                        }

                        String strMerNunRec = null;
                        if (dblCanNunRec > 0) {
                            strMerNunRec = "P";
                        }

                        if (((dblCanConf + dblCanTotCon) + (dblCanNunRec + dblCanTotNunRec) + (dblCanMalEst + dblCanTotMalEst)) > dblCanTotConGui) 
                        {
                            mostrarMsgInf("La cantidad ingresada no puede exceder la cantidad confirmada en punto remoto.\nVerifique los datos e intente nuevamente.");
                            break;
                        }

                        blnRes = verificaCanConEgr(conn, intCodEmpGuiRemRel, intCodLocGuiRemRel, intCodTipDocGuiRemRel, intCodDocGuiRemRel, intCodRegGuiRemRel, (dblCanConf + dblCanMalEst + dblCanNunRec));
                        if (blnRes == false) 
                        {
                            mostrarMsgInf("La cantidad ingresada no puede ser mayor a la cantidad confirmada en la guia. \n Por favor revise la informacion y reintente.");
                            break;
                        }

                        strSQL += " INSERT INTO tbm_detingegrmerbod( "
                                + " co_emp, co_loc, co_tipdoc, co_doc, co_reg,"
                                + " co_locrel, co_tipdocrel,co_docrel, co_regrel,"
                                + " co_itm, co_bod, nd_can, tx_obs1, nd_canMalEst,"
                                + " st_solProReaMerMalEst, nd_cannunrec, st_solProReaMerNunRec ) "
                                + " VALUES("
                                + " " + intCodEmpDocOri + ", " + intCodLocDocOri + ", " + intCodTipDocIng + ", " + intCodDocIng + ", "+ intCodRegItmIng+", "
                                + " " + intCodLocDocOri + ", " + intCodTipDocDocOri + ", " + intCodDocDocOri + ", " + strCodRegItmDocOri + ", "
                                + " " + strCodItmIng + ", " + intCodBodIng + ", " + dblCanConf + ", null, " + dblCanMalEst + " ," 
                                + " " + objUti.codificar(strMerMalEst) + ", " + dblCanNunRec + ",  " + objUti.codificar(strMerNunRec) + " "
                                + " ) ; ";
                        intCodRegItmIng++;
                        
                        if (strEstMerFisBod.equals("S"))
                        {
                            String strSqlAux = "";
                            strSqlAux = " nd_caningbod=nd_caningbod-" + (dblCanConf + dblCanNunRec + dblCanMalEst);

                            strSQL += " UPDATE tbm_invbod SET " + strSqlAux + " WHERE  co_emp=" + intCodEmpDocOri + " AND co_bod=" + intCodBodIng + " AND co_itm=" + strCodItmIng + " ; ";
                        }

                        strSQL += " UPDATE tbm_detmovinv "
                                + " SET st_regrep='M', co_usrCon=" + objZafParSis.getCodigoUsuario() + ", "
                                + "     nd_canCon = ( CASE WHEN nd_canCon IS NULL THEN 0 ELSE nd_canCon END ) + " + dblCanConf + ",  "
                                + "     nd_canNunRec = case when nd_canNunRec is null then 0 else nd_canNunRec end +  " + dblCanNunRec + ",  "
                                + "     nd_canTotMalEst= ( CASE WHEN nd_canTotMalEst IS NULL THEN 0 ELSE nd_canTotMalEst END ) + " + dblCanMalEst + " "
                                + " WHERE co_emp=" + intCodEmpDocOri + " "
                                + " AND co_loc=" + intCodLocDocOri + " "
                                + " AND co_tipdoc=" + intCodTipDocDocOri + " "
                                + " AND co_doc=" + intCodDocDocOri + " "
                                + " AND co_reg=" + strCodRegItmDocOri+ "; ";
                    }
                }
                System.out.println("guardarReg: " + strSQL);

                stmLoc.executeUpdate(strSQL);

                if (actualizarEstConfTbmCabMovInv(conn, intCodEmpDocOri, intCodLocDocOri, intCodTipDocDocOri, intCodDocDocOri))
                {
                    blnRes = true;
                }
                else
                {
                    blnRes = false;
                }

                strBuf.append("</table>");

                stmLoc.close();
                stmLoc = null;

            }
        } 
        catch (java.sql.SQLException Evt) {   blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception Evt) {     blnRes = false;    objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;
    }    
    
    private int getCodigoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc) 
    {
        int intCodDoc = 0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                strSql = " SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc "
                       + " FROM tbm_cabingegrmerbod "
                       + " WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipDoc=" + intCodTipDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                
                if (rstLoc.next()) 
                {
                    intCodDoc = rstLoc.getInt("co_doc");
                }

                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;

            }
        } 
        catch (java.sql.SQLException ex) { objUti.mostrarMsgErr_F1(this, ex);  } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e); }
        return intCodDoc;
    }

    private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc) 
    {
        int intUltNumDoc = 0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try 
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                strSql = " SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc "
                       + " FROM tbm_cabtipdoc "
                       + " WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                
                if (rstLoc.next()) 
                {
                    intUltNumDoc = rstLoc.getInt("numdoc");
                }

                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;

            }
        } 
        catch (java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex); } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
        return intUltNumDoc;
    }
    
    private boolean verificaCanConEgr(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg, double canEgre) 
    {
        boolean blnRes = false;
        Connection conLoc = null;
        PreparedStatement pstLoc = null;
        ResultSet rstLoc = null;
        double dblCanConEgr = 0;
        String strSql;
        
        //System.out.println(" verificaCanConEgr: " + intCodEmp + " " + intCodLoc + " " + intCodTipDoc + " " + intCodDoc + " " + intCodReg + " " + canEgre);
        
        strSql = " select case when a.nd_cancon is null then 0 else a.nd_cancon end as nd_cancongui, "
                + " case when a7.nd_can is null then 0 else abs(a7.nd_can) end as nd_canegreso "
                + " from tbm_detguirem as a "
                + " inner join tbm_detingegrmerbod as a7 on (a.co_emp=a7.co_emp and a.co_loc=a7.co_locrelguirem and a.co_tipdoc=a7.co_tipdocrelguirem and a.co_doc=a7.co_docrelguirem and a.co_reg=a7.co_regrelguirem ) "
                + " where a.co_emp=? "
                + " and a.co_loc=? "
                + " and a.co_tipdoc=? "
                + " and a.co_doc= ? "
                + " and a.co_reg= ? ";

        try 
        {
            if (conLoc == null) 
            {
                conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            }

            pstLoc = conLoc.prepareStatement(strSql);
            pstLoc.setInt(1, intCodEmp);
            pstLoc.setInt(2, intCodLoc);
            pstLoc.setInt(3, intCodTipDoc);
            pstLoc.setInt(4, intCodDoc);
            pstLoc.setInt(5, intCodReg);

            //System.out.println("verificaCanConEgr: " + strSql);
            rstLoc = pstLoc.executeQuery();
            while (rstLoc.next()) 
            {
                dblCanConEgr = objUti.parseDouble(rstLoc.getInt("nd_canegreso"));
            }

            if (canEgre <= dblCanConEgr)
                blnRes= Boolean.TRUE;
            else
                blnRes = Boolean.FALSE;

        } 
        catch (SQLException e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e); } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   } 
        finally 
        {
            try 
            {
                if (rstLoc != null) 
                {
                    rstLoc.close();
                }
                rstLoc = null;

                if (pstLoc != null) 
                {
                    pstLoc.close();
                }
                pstLoc = null;

                if (conLoc != null) 
                {
                    conLoc.close();
                }
                conLoc = null;
            }
            catch (Throwable e) {   e.printStackTrace();   }
        }
        return blnRes;
    }

    private boolean verificaCanConIng(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpIng) 
    {
        boolean blnRes = false;
        Connection conLoc = null;
        PreparedStatement pstLoc = null;
        ResultSet rstLoc = null;
        String strGuiProcesada = "";
        String strSql;
        
        strSql =  " select case when (b.nd_canmov-b.nd_caningreso) = 0 then 'S' else 'N' end as st_guiaprocesada "
                + " from ( "
                + "       select case when a7.nd_can is null then 0 else abs(a7.nd_can) end as nd_caningreso, case when a.nd_can is null then 0 else abs(a.nd_can) end as nd_canmov "
                + "       from tbm_detguirem as a "
                + "       left outer join tbm_detingegrmerbod as a7 on (a7.co_emp=? and a.co_loc=a7.co_locrelguirem and a.co_tipdoc=a7.co_tipdocrelguirem and a.co_doc=a7.co_docrelguirem ) "
                + "       where a.co_emp=? "
                + "       and a.co_loc=? "
                + "       and a.co_tipdoc=? "
                + "       and a.co_doc=? "
                + " ) as b "
                + " group by st_guiaprocesada ";
        try 
        {
            if (conLoc == null) 
            {
                conLoc = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            }

            pstLoc= conLoc.prepareStatement(strSql);
            pstLoc.setInt(1, intCodEmpIng);
            pstLoc.setInt(2, intCodEmp);
            pstLoc.setInt(3, intCodLoc);
            pstLoc.setInt(4, intCodTipDoc);
            pstLoc.setInt(5, intCodDoc);
            
            rstLoc= pstLoc.executeQuery();
            if (rstLoc.next()){
                strGuiProcesada= rstLoc.getString("st_guiaprocesada");
            }

            if (strGuiProcesada.equals("S"))
                blnRes= Boolean.TRUE;
            else
                blnRes= Boolean.FALSE;
      
        } 
        catch (SQLException e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);  } 
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);   } 
        finally 
        {
            try 
            {
                if (rstLoc != null)
                    rstLoc.close();
                rstLoc=null;

                if (pstLoc!=null)
                    pstLoc.close();
                pstLoc=null;

                if (conLoc!=null)
                    conLoc.close();
                conLoc=null;
            }
            catch(Throwable e){  e.printStackTrace();       }
        }
        return blnRes;
    }     

    private boolean actualizarEstConfTbmCabMovInv(java.sql.Connection conn, int intCodEmpDocOri, int intCodLocDocOri, int intCodTipDocDocOri, int intCodDocDocOri) 
    {
        boolean blnRes = false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql = "";
        try
        {
            if (conn != null) 
            {
                stmLoc = conn.createStatement();

                strSql =  " SELECT *, case when canTotConf  = 0 then 'P' else  case when nd_can = canTotConf then 'C' else 'E' end  end as estConf \n"
                        + " FROM ( "
                        + "       select sum(abs(nd_can)) as nd_can, \n"
                        + "              ("
                        + "                sum(case when nd_cancon is null then 0 else nd_cancon end ) + "
                        + "                sum(case when nd_cannunrec is null then 0 else nd_cannunrec end )  + "
                        + "                sum(case when nd_canTotMalEst is null then 0 else nd_canTotMalEst end  )"
                        + "               ) as canTotConf  \n"
                        + "       from tbm_detmovinv \n"
                        + "       where co_emp=" + intCodEmpDocOri + " \n"
                        + "       and co_loc=" + intCodLocDocOri + " \n"
                        + "       and co_tipdoc=" + intCodTipDocDocOri + " \n"
                        + "       and co_doc=" + intCodDocDocOri + " \n"
                        + "       and st_meringegrfisbod = 'S' \n"
                        + "       and nd_can > 0 \n"
                        + " ) AS x ";

                System.out.println("actualizarEstConfTbmCabMovInv: " + strSql);

                rstLoc = stmLoc.executeQuery(strSql);
                if (rstLoc.next()) 
                {
                    strSql =  " UPDATE tbm_cabmovinv SET st_coninv='" + rstLoc.getString("estConf") + "', st_autingegrinvcon='N' "
                            + " WHERE co_emp=" + intCodEmpDocOri + "  "
                            + " AND co_loc=" + intCodLocDocOri + " "
                            + " AND co_tipdoc=" + intCodTipDocDocOri + ""
                            + " AND co_doc=" + intCodDocDocOri;
                    System.out.println("actualizarEstConfTbmCabMovInv2: " + strSql);
                    stmLoc.executeUpdate(strSql);
                    blnRes = true;
                }
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;

            }
        } 
        catch (java.sql.SQLException ex) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, ex); } 
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    private boolean verificaCanNunEnv()
    {
        boolean blnRes = true;
        double dlbCanNumEnv = 0;
        try 
        {
            blnEstCanNunEnv = false;
            strItmConCanNunEnv = "";

            strItmConCanNunEnv = "<table border=1><tr> <td> BODEGA </td>  <td> CODIGO </TD> <TD> DESCRIPCION </TD>  <TD> CANTIDAD </TD> </TR>";
            for (int i = 0; i < tblDat.getRowCount(); i++) 
            {
                dlbCanNumEnv = objUti.redondear(objInvItm.getIntDatoValidado(tblDat.getValueAt(i, INT_TBL_CANNUNREC)), 4);
                if (dlbCanNumEnv > 0)
                {
                    blnEstCanNunEnv = true;
                    strItmConCanNunEnv += "<tr> <td> " + txtNomBodEgr.getText() + " </td> <td>" + tblDat.getValueAt(i, INT_TBL_CODALTITM).toString() + " </td><td>   " + tblDat.getValueAt(i, INT_TBL_NOMITM).toString() + " </td><td>  " + dlbCanNumEnv + " </td></tr> ";
                }
            }
            strItmConCanNunEnv += "</table>";

        } 
        catch (Exception Evt) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, Evt);   }
        return blnRes;
    }
    
 
    /**Función que permite imprimir automáticamente la Orden de Almacenamiento
     * de lo que se está confirmando de la guía de remisión al momento.
     * @return true: Imprime Orden Almacenamiento.
     * <BR>false: En el caso contrario.
     */
    private boolean imprimirOrdenAlmacenamiento() 
    {
        boolean blnRes = true;
        boolean blnPruebas=false; // Cambiar a False antes de pasar a producción. 
        
        Connection conImpOrdAlm;
        Statement stmImpOrdAlm;
        ResultSet rstImpOrdAlm;
        
        String strSQLRep = "";
        String strUsu = "", strFecHorSer = "";
        String strNomPrnSer = "", strRutRepOrdAlm="";
        java.util.Date datFecAux;
        try 
        {
            conImpOrdAlm =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conImpOrdAlm != null) 
            {
                if(blnPruebas)
                {
                    //strNomPrnSer = "laser_sistemas";
                    strNomPrnSer = "sistemas_pruebas";
                }
                else
                {
                    switch (Integer.parseInt(txtCodBodIng.getText()))
                    {
                        case 1://California
                            strNomPrnSer = "od_califormia";
                            break;
                        case 2://Vía Daule
                            strNomPrnSer = "od_dimulti";
                            break;
                        case 3://Quito Norte
                            strNomPrnSer = "od_quito";
                            break;
                        case 5://Manta
                            strNomPrnSer="od_manta";
                            break;
                        case 11://Santo Domingo
                            strNomPrnSer="od_stodgo";
                            break;
                        case 15://Inmaconsa
                            strNomPrnSer = "od_inmaconsa";
                            break;
                        case 28://Cuenca
                            strNomPrnSer="od_cuenca";
                            break;
                        default:
                            break;
                    }
                }
                
                //Obtener la fecha y hora del servidor.
                datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux == null) {
                    return false;
                }
                strFecHorSer = objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                datFecAux = null;

                stmImpOrdAlm = conImpOrdAlm.createStatement();
                strSQLRep ="";
                strSQLRep+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, \n";
                strSQLRep+="        CASE WHEN a5.st_impord='S' ";
                strSQLRep+="        THEN (substring(a4.tx_codalt2 from 1 for 1 ) || ' ' || substring(a4.tx_codalt2 from 2 for 1 ) || ' ' || substring(a4.tx_codalt2 from 3 for  1 )) ";
                strSQLRep+="        ELSE a4.tx_codAlt END AS tx_codAlt, \n";
                strSQLRep+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm, \n";
                strSQLRep+="        CASE WHEN a5.st_impord='S' THEN a5.tx_ubi ELSE '' END AS tx_ubi,\n";
                strSQLRep+="        a2.nd_can, a6.tx_desCor as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc, \n";
                strSQLRep+="        a9.ne_numDoc,a3.tx_desLar as tx_desTipDoc, b1.co_usr, b1.tx_usr, b1.tx_nom AS tx_nomUsrIng \n";
                strSQLRep+=" FROM (tbm_cabIngEgrMerBod  as a1 INNER JOIN tbm_usr AS b1 ON a1.co_usrIng=b1.co_usr) \n";
                strSQLRep+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc) \n";
                strSQLRep+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc) \n";
                strSQLRep+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) \n";
                strSQLRep+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm)\n";
                strSQLRep+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg) \n";
                strSQLRep+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp) \n";
                strSQLRep+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc) \n";
                strSQLRep+=" INNER JOIN tbm_cabMovInv as a9 ON (a1.co_emp=a9.co_emp AND a1.co_locRel=a9.co_loc AND a1.co_tipDocRel=a9.co_tipDoc AND a1.co_docRel=a9.co_doc) \n";
                strSQLRep+=" WHERE a1.co_emp="+ intCodEmpDocOri;
                strSQLRep+=" AND a1.co_loc="+ intCodLocDocOri;
                strSQLRep+=" AND a1.co_tipDoc="+ intCodTipDocIng;
                strSQLRep+=" AND a1.co_doc="+ intCodDocIng;
                strSQLRep+=" AND a2.nd_can NOT IN(0) \n";
                strSQLRep+=" ORDER BY a2.co_reg \n";
 
                System.out.println("imprimirOrdenAlmacenamientoCOIEBO: \n" + strSQLRep);
                
                rstImpOrdAlm = stmImpOrdAlm.executeQuery(strSQLRep);
                if (rstImpOrdAlm.next()) 
                {
                    strUsu = rstImpOrdAlm.getString("tx_usr");
                }
                
                //Inicializar los parametros que se van a pasar al reporte.
                java.util.Map mapPar = new java.util.HashMap();
                mapPar.put("strSQLRep", strSQLRep);
                mapPar.put("strCamAudRpt", "" + strUsu + "    " + strFecHorSer + "");

                //Ruta del Reporte
                if (System.getProperty("os.name").equals("Linux")) //Linux
                {
                    strRutRepOrdAlm = "/Zafiro/Reportes/Compras/ZafCom19/ZafRptCom19.jasper";
                } else //Windows
                {
                    strRutRepOrdAlm = "//172.16.1.2/Zafiro/Reportes/Compras/ZafCom19/ZafRptCom19.jasper";
                }

                // strRutRepOrdAlm=objZafParSis.getDirectorioSistema() + "/Reportes/Compras/ZafCom19/ZafRptCom19.jasper"; //Buscaba la ruta C:Zafiro

                System.out.println("Ruta Orden Almacenamiento: " + strRutRepOrdAlm);

                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet = new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

                JasperPrint reportGuiaRem = JasperFillManager.fillReport(strRutRepOrdAlm, mapPar, conImpOrdAlm);
                javax.print.attribute.standard.PrinterName printerName = new javax.print.attribute.standard.PrinterName(strNomPrnSer, null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet = new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp = new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();
                objPriReqAttSet = null;

                System.out.println("Se imprimio Orden de Almacenamiento");

                stmImpOrdAlm.close();
                stmImpOrdAlm = null;
                rstImpOrdAlm.close();
                rstImpOrdAlm = null;

            }
        } 
        catch (java.sql.SQLException e) {   objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    
    public boolean cargarDoc() 
    {
        boolean blnRes = false;
        String strSQL;
        try
        {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con != null)
            {
                stm = con.createStatement();
                strSQL ="";
                strSQL+=" SELECT  a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc \n";
                strSQL+=" FROM tbm_detguirem as a \n";
                strSQL+=" INNER JOIN tbr_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg) \n";
                strSQL+=" INNER JOIN tbm_detguirem AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel ) \n";
                strSQL+=" INNER JOIN tbr_detmovinv AS a3 ON (a3.co_emp=a2.co_emprel and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel) \n";
                strSQL+=" INNER JOIN tbm_detmovinv AS a4 ON (a4.co_emp=a3.co_emprel and a4.co_loc=a3.co_locrel and a4.co_tipdoc=a3.co_tipdocrel and a4.co_doc=a3.co_docrel and a4.co_reg=a3.co_regrel) \n";
                strSQL+=" INNER JOIN tbm_cabmovinv AS a5 ON (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc and a5.co_doc=a4.co_doc ) \n";
                strSQL+=" WHERE  a.co_emp="  + strCodEmpGui + "  AND a.co_loc=" +strCodLocGui +" AND a.co_tipdoc=" + strCodTipDocGui + " AND a.co_doc=" + strCodDocGui;  //pk Guia de Remisión.
                //System.out.println("cargarDoc \n" + strSQL);
                rst = stm.executeQuery(strSQL);
                   if (rst.next()) 
                   {
                       strCodEmpFact = rst.getString("co_emp");
                       strCodLocFact = rst.getString("co_loc");
                       strCodTipDocFact = rst.getString("co_tipDoc");
                       strCodDocFact = rst.getString("co_doc");
                       blnRes = true;
                   }
               }
               else
                   blnRes = false;

               return blnRes;
        }
        catch (java.sql.SQLException e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }

    private boolean generarRpt(int intTipRpt) 
    {
        String strRutRpt, strNomRpt, strFecHorSer, strSQL;
        int i, intNumTotRpt;
        boolean blnRes = true;
        try 
        {
            strSQL = "";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a2.co_reg,a2.co_itm,a2.co_bod, \n";
            strSQL+="        CASE WHEN a5.st_impord='S' ";
            strSQL+="        THEN (substring(a4.tx_codalt2 from 1 for 1 ) || ' ' || substring(a4.tx_codalt2 from 2 for 1 ) || ' ' || substring(a4.tx_codalt2 from 3 for  1 ))";
            strSQL+="        ELSE a4.tx_codAlt END AS tx_codAlt,\n";
            strSQL+="        CASE WHEN a5.st_impord='N' THEN a4.tx_nomItm ELSE '' END AS tx_nomItm,\n";
            strSQL+="        CASE WHEN a5.st_impord='S' THEN a5.tx_ubi ELSE '' END AS tx_ubi,\n";
            strSQL+="        a2.nd_can, a6.tx_desCor as tx_uniMed, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc, a9.ne_numDoc,a3.tx_desLar as tx_desTipDoc \n";        
            strSQL+=" FROM tbm_cabIngEgrMerBod  as a1 \n";
            strSQL+=" INNER JOIN tbm_detIngEgrMerBod as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_TipDoc AND a1.co_doc=a2.co_doc) \n";
            strSQL+=" INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_TipDoc) \n";
            strSQL+=" INNER JOIN tbm_inv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm) \n";
            strSQL+=" INNER JOIN tbm_invBod as a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod AND a2.co_itm=a5.co_itm) \n";
            strSQL+=" LEFT OUTER JOIN tbm_var as a6 ON (a4.co_uni=a6.co_reg) \n";
            strSQL+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp) \n";
            strSQL+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc) \n";
            strSQL+=" INNER JOIN tbm_cabMovInv as a9 ON (a1.co_emp=a9.co_emp AND a1.co_locRel=a9.co_loc AND a1.co_tipDocRel=a9.co_tipDoc AND a1.co_docRel=a9.co_doc)\n";
            strSQL+=" WHERE a1.co_emp="+strCodEmpFact+"\n";
            strSQL+=" AND a1.co_locRel="+strCodLocFact+"\n";
            strSQL+=" AND a1.co_tipDocRel="+strCodTipDocFact+"\n";
            strSQL+=" AND a1.co_docRel="+strCodDocFact+"\n";
            System.out.println("generarRpt " + strSQL);
            
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strSQLRep", strSQL);
                                mapPar.put("strCamAudRpt", objZafParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                           break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    //</editor-fold>
    
}
