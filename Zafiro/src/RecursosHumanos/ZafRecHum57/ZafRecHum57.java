package RecursosHumanos.ZafRecHum57;


import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Solicitud de novedades de recursos humanos
 * @author Roberto Flores
 * Guayaquil 09/03/2013
 */
public class ZafRecHum57 extends javax.swing.JInternalFrame {
  
    private Librerias.ZafParSis.ZafParSis objZafParSis;
    private ZafUtil objUti; /**/
    private MiToolBar objTooBar;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private String strVersion=" v1.04";

    private String strUsr="";
    private String strNomUsr="";
    private String strCodUsr="";

    private String strUsrRem="";
    private String strNomUsrRem="";
    private String strCodUsrRem="";
    
    private String strCodTipNov="";
    private String strDesLarTipNov="";

    private final int INT_TBL_LINEA=0;
    private final int INT_TBL_FEC_REE=1;
    private final int INT_TBL_REM=2;
    private final int INT_TBL_DES=3;
    private final int INT_TBL_OBS=4;
    private final int INT_TBL_BUT_OBS = 5;

    private java.sql.Connection CONN_GLO=null;
    private java.sql.Statement  STM_GLO=null;
    private java.sql.ResultSet  rstLoc=null;
    
    private boolean blnActPosRel;

    private ZafVenCon vcoUsr;                           //Ventana de consulta "Usuario".
    private ZafVenCon vcoTipNovRecHum;                           //Ventana de consulta "Usuario".
    private String strDesCorUsr, strDesLarUsr;          //Contenido del campo al obtener el foco.
    private String strRemCorUsr, strRemLarUsr;          //Contenido del campo al obtener el foco.

    private java.util.Date datFecAux;
    
    private String strSQL, strAux;
    private boolean blnHayCam=false;
    
    private java.sql.ResultSet  rstCab=null;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst;//, rstCab;60
    private ZafMouMotAda objMouMotAda;
    
    private String strCodNov="";
    private boolean blnEstCarNov=false;
    
    private Vector vecDat=null;
    
//    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
    
    private ZafParSis objParSis;
    
    
    /** Creates new form ZafRecHum57 */
    public ZafRecHum57(Librerias.ZafParSis.ZafParSis obj) {
        try{
	    
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            
            String strAux=objZafParSis.getNombreMenu();
            if(strAux==null){
                strAux="Novedades de recursos humanos...";
            }
            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());  /**/

            objUti = new ZafUtil(); /**/
            objTooBar = new MiToolBar(this);
            this.getContentPane().add(objTooBar,"South");
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
            
            blnActPosRel = false;
        }catch (CloneNotSupportedException e){  e.printStackTrace();objUti.mostrarMsgErr_F1(this, e); }  /**/
        catch (Exception e){  e.printStackTrace();objUti.mostrarMsgErr_F1(this, e); }
    }

    /** Creates new form ZafRecHum57 */
//    public ZafRecHum57(Librerias.ZafParSis.ZafParSis obj, String strCodnov ) {
    public ZafRecHum57(Librerias.ZafParSis.ZafParSis obj, int empresa , int local, int menu , String strCodnov ) {
        try{
            
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
//            this.objZafParSis = new ZafParSis();
            objZafParSis.setCodigoEmpresa(empresa);
            objZafParSis.setCodigoLocal(local);
            objZafParSis.setCodigoMenu(menu);
//            objParSis=(ZafParSis)obj.clone();
	    initComponents();

            String strAux=objZafParSis.getNombreMenu();
            if(strAux==null){
                strAux="Novedades de recursos humanos...";
            }
            this.setTitle(strAux+" "+ strVersion ); /**/
            lblTit.setText(objZafParSis.getNombreMenu());

            this.strCodNov=strCodnov;
            blnEstCarNov=true;

            objUti = new ZafUtil();
            objTooBar = new ZafRecHum57.MiToolBar(this);

            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleAceptar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleConsultar(false);
            objTooBar.setVisibleCancelar(true);
            objTooBar.setEstado('w');

//            if(objZafParSis.getCodigoMenu()==3475){
//                
//            }
            this.getContentPane().add(objTooBar,"South");
//            invocarClase("Compras.ZafCom20.ZafCom20");
//            invocarClase("RecursosHumanos.ZafRecHum57.ZafRecHum57");
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
        }catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    private boolean invocarClase(String clase)
    {
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[1];
            objCla[0]=objParSis.getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[1];
            objObj[0]=objParSis;
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
//            dskGen.add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                }
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                    System.gc();
                }
                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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
            ifrVen.show();
        }
        catch (ClassNotFoundException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e) 
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
    * Carga ventanas de consulta y configuracion de la tabla
    */
    public void Configura_ventana_consulta(){
        configurarVenConTipNovRecHum();
        configurarVenConUsr();
        configurarForm();
        if(blnEstCarNov){
            vecDat=null; 
            cargarDatos();
        }
    }
    
    private boolean cargarDatos(){
  boolean blnRes=true;
  try
  {
      conCab=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
      if (conCab!=null)
      {
          if (cargarCabReg())
          {
              blnRes=true;
          }
          blnHayCam=false;
      }
  }
  catch (Exception e)
  {
      blnRes=false;
  }
  return blnRes;
}

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        java.util.Date datFecDoc;
        int intPosRel;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom, d.co_tipnov, d.tx_deslar FROM tbm_novrechum AS a";
                strSQL+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
                strSQL+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
                strSQL+=" inner join tbm_tipnovrechum d on (d.co_tipnov=a.co_tipnov)";
                strSQL+=" where a.co_nov = " + strCodNov;
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_nov");
                    txtCodNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_usrrem");
                    txtCodRem.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrrem");
                    txtCodRemNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrremnom");
                    txtDesRemNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_usrdes");
                    txtCodUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrdes");
                    txtDesCorUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrdesnom");
                    txtDesLarUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_tipnov");
                    txtCodTipNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_deslar");
                    txtDesTipNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_asu");
                    txtDesAsuNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_men");
                    txtMsj.setText((strAux==null)?"":strAux);
                    
                    datFecDoc=rst.getDate("fe_ing");
//                    txtFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                    txtFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy hh:mm:ss"));
                    String strFeDoc = rst.getString("fe_ing");
                    String[] strArrFeDoc=strFeDoc.split(" ");
                    String strFeDocOrd=strArrFeDoc[0].split("-")[2] +"/"+strArrFeDoc[0].split("-")[1]+"/"+strArrFeDoc[0].split("-")[0];
                    txtFecDoc.setText(""+strFeDocOrd+" " + strArrFeDoc[1].substring(0,8));
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
             
                        strAux="Otro";
                    
                    cargarHistorialReenvios();
                    
                    objTooBar.setEstadoRegistro(strAux);
                }else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    blnRes=false;
                }

                //Mostrar la posición relativa del registro.
                if(!blnEstCarNov){
                    intPosRel=rstCab.getRow();
                    rstCab.last();
                    objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                    rstCab.absolute(intPosRel);
                }else{
                    objTooBar.setPosicionRelativa("" + 1 + " / " + 1);
                }
                
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
        }
        return blnRes;
    }
    
    private boolean cargarHistorialReenvios(){
        
        boolean blnRes=true;
        java.util.Date datFecDoc;
        java.sql.ResultSet rstLoc;
        Vector vecDataCon = null;
        java.sql.Statement stm;
        int intPosRel;
        String strSql="";
        int intCanRee=0;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                vecDataCon = new Vector();
                stm=con.createStatement();
                strSql="";
                
                strSql+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom FROM tbm_reenovrechum AS a";
                strSql+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
                strSql+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
                strSql+=" where a.co_nov = " + txtCodNov.getText();
                strSql+=" order by co_reg";
                
                rstLoc=stm.executeQuery(strSql);
                while (rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_FEC_REE,rstLoc.getString("fe_ing").substring(0,10));
                    vecReg.add(INT_TBL_REM,rstLoc.getString("tx_usrrem"));
                    vecReg.add(INT_TBL_DES,rstLoc.getString("tx_usrdes"));
                    vecReg.add(INT_TBL_OBS,rstLoc.getString("tx_obs1"));
                    vecReg.add(INT_TBL_BUT_OBS,"...");
                    vecDataCon.add(vecReg);
                    intCanRee++;
                }
                objTblMod.setData(vecDataCon);
                tblDat .setModel(objTblMod); 
                if(intCanRee>0){
                    tabGen.addTab("Historial de reenvios("+intCanRee+")", panGenTabRee);
                }else{
                    tabGen.addTab("Historial de reenvios", panGenTabRee);
                }
            }  
        }catch(java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
        finally {
                try{con.close();con=null;}catch(Throwable ignore){}
            }
        return blnRes;
    }
    

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Usuarios".
     */
    private boolean configurarVenConTipNovRecHum() {
        boolean blnRes = true;
        String strSQL="";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipnov");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "select * from tbm_tipnovrechum";
            strSQL += " order by co_tipnov asc";
            vcoTipNovRecHum = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Tipos de Novedades", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipNovRecHum.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Usuarios".
     */
    private boolean configurarVenConUsr() {
        boolean blnRes = true;
        String strSQL="";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("80");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL += " FROM tbm_usr AS a1";
            strSQL += " WHERE a1.st_usrSis='S'";
            strSQL += " AND a1.st_reg='A'";
            strSQL += " ORDER BY a1.tx_usr";
            vcoUsr = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoUsr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarForm(){

        boolean blnres=false;
        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();

        vecCab.add(INT_TBL_LINEA,"");
        vecCab.add(INT_TBL_FEC_REE,"Fecha");
        vecCab.add(INT_TBL_REM,"Remitente");
        vecCab.add(INT_TBL_DES,"Destinatario");
        vecCab.add(INT_TBL_OBS,"Observación");
        vecCab.add(INT_TBL_BUT_OBS, "...");
   
        objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblDat.setModel(objTblMod);

        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);
        
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_FEC_REE).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_REM).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DES).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(350);
        tcmAux.getColumn(INT_TBL_BUT_OBS).setPreferredWidth(20);
        
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_BUT_OBS);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        
        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

        //Configurar JTable: Renderizar celdas.
        Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
        zafTblDocCelRenBut = new ZafTblCelRenBut();
                tcmAux.getColumn(INT_TBL_BUT_OBS).setCellRenderer(zafTblDocCelRenBut);

                ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUT_OBS, "Observación") {
                    public void butCLick() {
                        int intSelFil = tblDat.getSelectedRow();
                        String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
                        ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum57.this), true, strObs);
                        zafMae07_01.show();
                        if (zafMae07_01.getAceptar()) {
                          tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
                         }
                    }
                };

        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

        return blnres;
    }

    public void abrirCon(){
        try{
            CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    public void CerrarCon(){
        try{
            CONN_GLO.close();
            CONN_GLO=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
    }

    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panGen = new javax.swing.JPanel();
        lblFecNov = new javax.swing.JLabel();
        txtCodNov = new javax.swing.JTextField();
        lblRemNov = new javax.swing.JLabel();
        txtCodRemNov = new javax.swing.JTextField();
        txtDesRemNov = new javax.swing.JTextField();
        lblCodNov = new javax.swing.JLabel();
        lblDesNov = new javax.swing.JLabel();
        lblTipNov = new javax.swing.JLabel();
        lblMsjNov = new javax.swing.JLabel();
        txtDesAsuNov = new javax.swing.JTextField();
        lblAsuNov = new javax.swing.JLabel();
        txtCodUsr = new javax.swing.JTextField();
        txtDesCorUsr = new javax.swing.JTextField();
        txtDesLarUsr = new javax.swing.JTextField();
        butUsr = new javax.swing.JButton();
        txtCodTipNov = new javax.swing.JTextField();
        txtDesTipNov = new javax.swing.JTextField();
        butTipNov = new javax.swing.JButton();
        txtCodRem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMsj = new javax.swing.JTextArea();
        txtFecDoc = new javax.swing.JTextField();
        panGenTabRee = new javax.swing.JPanel();
        panGenRee = new javax.swing.JPanel();
        jspReen = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panGen.setPreferredSize(new java.awt.Dimension(100, 320));
        panGen.setLayout(null);

        lblFecNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblFecNov.setText("Fecha:"); // NOI18N
        panGen.add(lblFecNov);
        lblFecNov.setBounds(490, 10, 70, 20);

        txtCodNov.setBackground(objZafParSis.getColorCamposSistema());
        txtCodNov.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodNovActionPerformed(evt);
            }
        });
        txtCodNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodNovFocusLost(evt);
            }
        });
        panGen.add(txtCodNov);
        txtCodNov.setBounds(70, 10, 70, 20);

        lblRemNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblRemNov.setText("Remitente:"); // NOI18N
        panGen.add(lblRemNov);
        lblRemNov.setBounds(10, 30, 60, 20);

        txtCodRemNov.setBackground(objZafParSis.getColorCamposSistema());
        txtCodRemNov.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodRemNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodRemNovActionPerformed(evt);
            }
        });
        txtCodRemNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodRemNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodRemNovFocusLost(evt);
            }
        });
        panGen.add(txtCodRemNov);
        txtCodRemNov.setBounds(70, 30, 70, 20);

        txtDesRemNov.setBackground(objZafParSis.getColorCamposSistema());
        txtDesRemNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesRemNovNovActionPerformed(evt);
            }
        });
        txtDesRemNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesRemNovNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesRemNovNovFocusLost(evt);
            }
        });
        panGen.add(txtDesRemNov);
        txtDesRemNov.setBounds(140, 30, 290, 20);

        lblCodNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCodNov.setText("Código:"); // NOI18N
        panGen.add(lblCodNov);
        lblCodNov.setBounds(10, 10, 110, 20);

        lblDesNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblDesNov.setText("Destinatario:"); // NOI18N
        panGen.add(lblDesNov);
        lblDesNov.setBounds(10, 50, 60, 20);

        lblTipNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipNov.setText("Tipo:"); // NOI18N
        panGen.add(lblTipNov);
        lblTipNov.setBounds(10, 70, 40, 20);

        lblMsjNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblMsjNov.setText("Mensaje:"); // NOI18N
        panGen.add(lblMsjNov);
        lblMsjNov.setBounds(10, 110, 70, 20);

        txtDesAsuNov.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesAsuNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesAsuNovNovActionPerformed(evt);
            }
        });
        txtDesAsuNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesAsuNovNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesAsuNovNovFocusLost(evt);
            }
        });
        panGen.add(txtDesAsuNov);
        txtDesAsuNov.setBounds(70, 90, 590, 20);

        lblAsuNov.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblAsuNov.setText("Asunto:"); // NOI18N
        panGen.add(lblAsuNov);
        lblAsuNov.setBounds(10, 90, 70, 20);

        txtCodUsr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGen.add(txtCodUsr);
        txtCodUsr.setBounds(40, 50, 32, 20);
        txtCodUsr.setVisible(false);

        txtDesCorUsr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCorUsr.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDesCorUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorUsrActionPerformed(evt);
            }
        });
        txtDesCorUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorUsrFocusLost(evt);
            }
        });
        panGen.add(txtDesCorUsr);
        txtDesCorUsr.setBounds(70, 50, 70, 20);

        txtDesLarUsr.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesLarUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarUsrActionPerformed(evt);
            }
        });
        txtDesLarUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarUsrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarUsrFocusLost(evt);
            }
        });
        panGen.add(txtDesLarUsr);
        txtDesLarUsr.setBounds(140, 50, 290, 20);

        butUsr.setText(".."); // NOI18N
        butUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUsrActionPerformed(evt);
            }
        });
        panGen.add(butUsr);
        butUsr.setBounds(430, 50, 20, 20);

        txtCodTipNov.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodTipNov.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTipNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipNovActionPerformed(evt);
            }
        });
        txtCodTipNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTipNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTipNovFocusLost(evt);
            }
        });
        panGen.add(txtCodTipNov);
        txtCodTipNov.setBounds(70, 70, 70, 20);

        txtDesTipNov.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesTipNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesTipNovActionPerformed(evt);
            }
        });
        txtDesTipNov.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesTipNovFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesTipNovFocusLost(evt);
            }
        });
        panGen.add(txtDesTipNov);
        txtDesTipNov.setBounds(140, 70, 290, 20);

        butTipNov.setText(".."); // NOI18N
        butTipNov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipNovActionPerformed(evt);
            }
        });
        panGen.add(butTipNov);
        butTipNov.setBounds(430, 70, 20, 20);

        txtCodRem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGen.add(txtCodRem);
        txtCodRem.setBounds(40, 30, 32, 20);
        txtCodRem.setVisible(false);

        txtMsj.setColumns(20);
        txtMsj.setLineWrap(true);
        txtMsj.setRows(5);
        jScrollPane1.setViewportView(txtMsj);

        panGen.add(jScrollPane1);
        jScrollPane1.setBounds(70, 110, 590, 210);

        txtFecDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtFecDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFecDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFecDocActionPerformed(evt);
            }
        });
        panGen.add(txtFecDoc);
        txtFecDoc.setBounds(530, 10, 130, 20);
        txtFecDoc.setEditable(false);

        panGenTabGen.add(panGen, java.awt.BorderLayout.NORTH);

        tabGen.addTab("General", panGenTabGen);

        panGenTabRee.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panGenTabRee.setLayout(new java.awt.BorderLayout());

        panGenRee.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panGenRee.setLayout(new java.awt.BorderLayout());

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
        jspReen.setViewportView(tblDat);

        panGenRee.add(jspReen, java.awt.BorderLayout.CENTER);

        panGenTabRee.add(panGenRee, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Historial de reenvios", panGenTabRee);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    } 
    
    private void txtCodNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodNovActionPerformed
        // TODO add your handling code here:
        objTooBar.consultar();
}//GEN-LAST:event_txtCodNovActionPerformed

    private void txtCodNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodNovFocusGained
        // TODO add your handling code here:
}//GEN-LAST:event_txtCodNovFocusGained

    private void txtCodNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodNovFocusLost
        // TODO add your handling code here:
}//GEN-LAST:event_txtCodNovFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodRemNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRemNovFocusGained
        // TODO add your handling code here:
        strUsrRem=txtCodRemNov.getText();
        txtCodRemNov.selectAll();
    }//GEN-LAST:event_txtCodRemNovFocusGained

    private void txtCodRemNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodRemNovFocusLost
        // TODO add your handling code here:
                // TODO add your handling code here:
        if (txtCodRemNov.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodRemNov.getText().equalsIgnoreCase(strRemCorUsr)) {
                if (txtCodRemNov.getText().equals("")) {
                    txtCodRem.setText("");
                    txtDesRemNov.setText("");
                } else {
                    mostrarVenConUsrRem(1);
                }
            } else {
                txtCodRemNov.setText(strRemCorUsr);
            }
        }
    }//GEN-LAST:event_txtCodRemNovFocusLost

    private void txtDesRemNovNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesRemNovNovFocusLost
        // TODO add your handling code here:
        if (txtDesRemNov.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesRemNov.getText().equalsIgnoreCase(strRemLarUsr)) {
                if (txtDesRemNov.getText().equals("")) {
                    txtCodRem.setText("");
                    txtCodRemNov.setText("");
                } else {
                    mostrarVenConUsrRem(2);
                }
            } else {
                txtDesRemNov.setText(strRemLarUsr);
            }
        }
    }//GEN-LAST:event_txtDesRemNovNovFocusLost

    private void txtDesRemNovNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesRemNovNovActionPerformed
        // TODO add your handling code here:
        txtDesRemNov.transferFocus();
    }//GEN-LAST:event_txtDesRemNovNovActionPerformed

    private void txtCodRemNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodRemNovActionPerformed
        // TODO add your handling code here:
        txtCodRemNov.transferFocus();
    }//GEN-LAST:event_txtCodRemNovActionPerformed

    private void txtDesRemNovNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesRemNovNovFocusGained
        // TODO add your handling code here:
        strRemLarUsr = txtDesRemNov.getText();
        txtDesRemNov.selectAll();
    }//GEN-LAST:event_txtDesRemNovNovFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtDesAsuNovNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesAsuNovNovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesAsuNovNovActionPerformed

    private void txtDesAsuNovNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesAsuNovNovFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesAsuNovNovFocusGained

    private void txtDesAsuNovNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesAsuNovNovFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesAsuNovNovFocusLost

    private void txtDesCorUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorUsrActionPerformed
        // TODO add your handling code here:
        txtDesCorUsr.transferFocus();
    }//GEN-LAST:event_txtDesCorUsrActionPerformed

    private void txtDesCorUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorUsrFocusGained
        // TODO add your handling code here:
        strUsr=txtDesCorUsr.getText();
        txtDesCorUsr.selectAll();
    }//GEN-LAST:event_txtDesCorUsrFocusGained

    private void txtDesCorUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorUsrFocusLost
        // TODO add your handling code here:
        if (txtDesCorUsr.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorUsr.getText().equalsIgnoreCase(strDesCorUsr)) {
                if (txtDesCorUsr.getText().equals("")) {
                    txtCodUsr.setText("");
                    txtDesLarUsr.setText("");
                } else {
                    mostrarVenConUsr(1);
                }
            } else {
                txtDesCorUsr.setText(strDesCorUsr);
            }
        }
    }//GEN-LAST:event_txtDesCorUsrFocusLost

    private void txtDesLarUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarUsrActionPerformed
        // TODO add your handling code here:
        txtDesLarUsr.transferFocus();
    }//GEN-LAST:event_txtDesLarUsrActionPerformed

    private void txtDesLarUsrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUsrFocusGained
        // TODO add your handling code here:
        strDesLarUsr = txtDesLarUsr.getText();
        txtDesLarUsr.selectAll();
    }//GEN-LAST:event_txtDesLarUsrFocusGained

    private void txtDesLarUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarUsrFocusLost
        // TODO add your handling code here:
        if (txtDesLarUsr.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarUsr.getText().equalsIgnoreCase(strDesLarUsr)) {
                if (txtDesLarUsr.getText().equals("")) {
                    txtCodUsr.setText("");
                    txtDesCorUsr.setText("");
                } else {
                    mostrarVenConUsr(2);
                }
            } else {
                txtDesLarUsr.setText(strDesLarUsr);
            }
        }
    }//GEN-LAST:event_txtDesLarUsrFocusLost

    private void butUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUsrActionPerformed

        mostrarVenConUsr(0);
    }//GEN-LAST:event_butUsrActionPerformed

    private void txtCodTipNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipNovActionPerformed
        //consultarRepDep();
        txtCodTipNov.transferFocus();
    }//GEN-LAST:event_txtCodTipNovActionPerformed

    private void txtCodTipNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipNovFocusGained
        // TODO add your handling code here:
        strCodTipNov = txtCodTipNov.getText();
        txtCodTipNov.selectAll();
    }//GEN-LAST:event_txtCodTipNovFocusGained

    private void txtCodTipNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipNovFocusLost
        // TODO add your handling code here:
        if (!txtCodTipNov.getText().equalsIgnoreCase(strCodTipNov)) {
            if (txtCodTipNov.getText().equals("")) {
                txtCodTipNov.setText("");
                txtDesTipNov.setText("");
            } else {
                BuscarTipNov("a1.co_tipnov", txtCodTipNov.getText(), 0);
            }
        } else {
            txtCodTipNov.setText(strCodTipNov);
        }
    }//GEN-LAST:event_txtCodTipNovFocusLost

    public void BuscarTipNov(String campo,String strBusqueda,int tipo){
        
        vcoTipNovRecHum.setTitle("Listado de Departamentos");
        if(vcoTipNovRecHum.buscar(campo, strBusqueda )) {
            txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
            txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
        }else{
            vcoTipNovRecHum.setCampoBusqueda(tipo);
            vcoTipNovRecHum.cargarDatos();
            vcoTipNovRecHum.show();
            if (vcoTipNovRecHum.getSelectedButton()==vcoTipNovRecHum.INT_BUT_ACE) {
                txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(2));
        }else{
                txtCodTipNov.setText(strCodTipNov);
                txtDesTipNov.setText(strDesLarTipNov);
  }}}
    
    private void txtDesTipNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesTipNovActionPerformed

        // TODO add your handling code here:
        txtDesTipNov.transferFocus();
    }//GEN-LAST:event_txtDesTipNovActionPerformed

    private void txtDesTipNovFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesTipNovFocusGained
        // TODO add your handling code here:
        strDesLarTipNov=txtDesTipNov.getText();
        txtDesTipNov.selectAll();
    }//GEN-LAST:event_txtDesTipNovFocusGained

    private void txtDesTipNovFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesTipNovFocusLost
        // TODO add your handling code here:
        if (txtDesTipNov.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesTipNov.getText().equalsIgnoreCase(strDesLarTipNov))
            {
                if (txtDesTipNov.getText().equals(""))
                {
                    txtCodTipNov.setText("");
                    txtDesTipNov.setText("");
                }
                else
                {
                    mostrarVenConTipNov(2);
                }
            }
            else
            txtDesTipNov.setText(strDesLarTipNov);
        }
    }//GEN-LAST:event_txtDesTipNovFocusLost

    private void butTipNovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipNovActionPerformed

        mostrarVenConTipNov(0);
    }//GEN-LAST:event_butTipNovActionPerformed

    private void txtFecDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFecDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFecDocActionPerformed

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }
    }
    
    /**
     * Esta funciï¿½n inicializa la tabla.
     * @return true: Si se pudo inicializar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean inicializar()
    {
        boolean blnRes=true;
        txtCodNov.setText("");
//        txtFecNov.setText("");
        txtCodUsr.setText("");
        txtDesRemNov.setText("");
        txtCodRemNov.setText("");
        objTblMod.removeAllRows();
        objTblMod.inicializarEstadoFilas();
        return blnRes;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butTipNov;
    private javax.swing.JButton butUsr;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jspReen;
    private javax.swing.JLabel lblAsuNov;
    private javax.swing.JLabel lblCodNov;
    private javax.swing.JLabel lblDesNov;
    private javax.swing.JLabel lblFecNov;
    private javax.swing.JLabel lblMsjNov;
    private javax.swing.JLabel lblRemNov;
    private javax.swing.JLabel lblTipNov;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenRee;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panGenTabRee;
    private javax.swing.JPanel panNor;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodNov;
    private javax.swing.JTextField txtCodRem;
    private javax.swing.JTextField txtCodRemNov;
    private javax.swing.JTextField txtCodTipNov;
    private javax.swing.JTextField txtCodUsr;
    private javax.swing.JTextField txtDesAsuNov;
    private javax.swing.JTextField txtDesCorUsr;
    private javax.swing.JTextField txtDesLarUsr;
    private javax.swing.JTextField txtDesRemNov;
    private javax.swing.JTextField txtDesTipNov;
    private javax.swing.JTextField txtFecDoc;
    private javax.swing.JTextArea txtMsj;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objZafParSis);
            setVisibleEliminar(false);
        }

        public boolean anular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;

            }
            if (strAux.equals("Anulado")) {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }

            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }
        
        private boolean anularReg(){

            boolean blnRes=false;
            try
            {
                con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if (con!=null)
                {
                    if (anularCab())
                    {
                        con.commit();
                        blnRes=true;
                    }
                    else{
                        con.rollback();
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
            }finally {
                try{con.close();con=null;}catch(Throwable ignore){}
            }
            return blnRes;
    
    }
        
     /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab()
    {
        boolean blnRes=true;
        String strSQL="";
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_novrechum";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objZafParSis.getCodigoUsuario();
                strSQL+=" WHERE co_nov = " + rstCab.getString("co_nov");
                System.out.println("strAnl: " + strSQL);
                stm.executeUpdate(strSQL);
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally {
            try{stm.close();stm=null;}catch(Throwable ignore){}
            }
        return blnRes;
    }

        public void clickAceptar()
        {
        
        }


    public void clickAnterior() 
        {
            try
            {
                if(objZafParSis.getCodigoMenu()!=3475){
                    if (!rstCab.isFirst())
                    {
                        rstCab.previous();
                        cargarReg();
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
        }


    public void clickAnular()
    {

    }

    public void clickCancelar()
    {

    }

    public void clickConsultar(){
        
        java.sql.ResultSet rst = null,rstAux=null;
        java.sql.Statement stm = null,stmAux=null;
        java.sql.Connection con=null;
        try{
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                stmAux=con.createStatement();
                clnTextos();
//                bloquea(txtCodNov, objZafParSis.getColorCamposSistema(), false);
//                bloquea(txtCodRemNov, objZafParSis.getColorCamposSistema(), false);
//                bloquea(txtDesRemNov, objZafParSis.getColorCamposSistema(), false);
                bloquea(txtFecDoc, objZafParSis.getColorCamposSistema(), false);
                txtCodNov.requestFocus();
                String strSql="";
                if(objZafParSis.getCodigoUsuario()!=1){
                    strSql+="select * from  tbr_perUsr a ";
                    strSql+=" where co_mnu = 3482 and co_usr = " + objZafParSis.getCodigoUsuario();
                    System.out.println("sqlCon: " + strSql);
                    rst=stm.executeQuery(strSql);
                    if(rst.next()){
                        
                    }else{
                        strSql="";
                        strSql="select * from tbm_usr where co_usr = " + objZafParSis.getCodigoUsuario();
                        rstAux=stmAux.executeQuery(strSql);
                        if(rstAux.next()){
                            txtCodRem.setText(""+rstAux.getInt("co_usr"));
                            txtCodRemNov.setText(""+rstAux.getString("tx_usr"));
                            txtDesRemNov.setText(""+rstAux.getString("tx_nom"));
                        }
                    }
                }
            }
             if(rstCab!=null) {
                 rstCab.close();
                 rstCab=null;
             }
        }catch (Exception e) { e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); }
        finally {
                try{stm.close();stm=null;}catch(Throwable ignore){}
                try{stmAux.close();stmAux=null;}catch(Throwable ignore){}
                try{rst.close();rst=null;}catch(Throwable ignore){}
                try{rstAux.close();rstAux=null;}catch(Throwable ignore){}
                try{con.close();con=null;}catch(Throwable ignore){}
            }
    }

    public void clickEliminar()
    {
        System.out.println();
    }


    public void clickFin()
    {
        try
        {
            if(objZafParSis.getCodigoMenu()!=3475){
                if (!rstCab.isLast())
                {
                    rstCab.last();
                    cargarReg();
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
    }

    public void clickImprimir()
    {

    }

    public void clickInicio()
    {
        try
        {
            if(objZafParSis.getCodigoMenu()!=3475){
                if (!rstCab.isFirst())
                {
                    rstCab.first();
                    cargarReg();
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
    }

    public void clickInsertar(){

        try{
                clnTextos();
                bloquea(txtCodNov, objZafParSis.getColorCamposSistema(), false);
                bloquea(txtCodRemNov, objZafParSis.getColorCamposSistema(), false);
                bloquea(txtDesRemNov, objZafParSis.getColorCamposSistema(), false);
                bloquea(txtFecDoc, objZafParSis.getColorCamposSistema(), false);
             if(rstCab!=null) {
                 rstCab.close();
                 rstCab=null;
             }
        }catch (Exception e) { e.printStackTrace(); objUti.mostrarMsgErr_F1(this, e); }
    }

public void setEditable(boolean editable) {
  if (editable==true){
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

 }else{  objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); }
}

public void clickModificar(){
    java.sql.Connection conn = null;
        try{
            if(validaCampos()){

                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if(conn!=null){
                    if(isUsuarioRem(conn)){
                        if(verificarLeido(conn)){
                        setEditable(true);

                          java.awt.Color colBack;
                          colBack = txtCodNov.getBackground();
                          objTblMod.setDataModelChanged(false);
                        }
                        else{
                            mostrarMsgInf("La novedad no puede ser modificada porque ya ha sido leída.");
                            setEstado('w');
                        }
                    }else{
                        mostrarMsgInf("La novedad solo puede ser modificada por su remitente.");
                        setEstado('w');
                    }
                }    
            }
  }catch(java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
        finally {
                try{conn.close();conn=null;}catch(Throwable ignore){}
            }
}

    private void bloquea(javax.swing.JTextField txtFiel,  java.awt.Color colBack, boolean blnEst){
        colBack = txtFiel.getBackground();
        txtFiel.setEditable(blnEst);
        txtFiel.setBackground(colBack);
    }

public void clickSiguiente()
{
    try
    {
        if(objZafParSis.getCodigoMenu()!=3475){
            if (!rstCab.isLast())
            {
                rstCab.next();
                cargarReg();
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
}

        public void clickVisPreliminar()
        {
            
        }

        public boolean consultar(){
            consultarReg();
            setEditable(true);
            return true;
        }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
                  
    boolean blnRes=true;
        String strSQL="";
        try
        {
            conCab=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    strSQL+="select a.*, b.tx_usr as tx_usrrem, c.tx_usr as tx_usrdes, d.tx_deslar FROM tbm_novrechum AS a";
                    strSQL+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
                    strSQL+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
                    strSQL+=" inner join tbm_tipnovrechum d on (d.co_tipnov=a.co_tipnov)";
                    strSQL+=" where a.st_reg IN ('P','I','T')";  
                
                strAux=txtCodRem.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a.co_usrrem=" + strAux;
                
                strAux=txtCodNov.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a.co_nov = " + strAux;
                strAux=txtCodUsr.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a.co_usrdes=" + strAux;
                strAux=txtCodTipNov.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a.co_tipnov=" + strAux;
                
                strSQL+=" ORDER BY a.co_nov";
                System.out.println("sqlCon: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    clnTextos();
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

    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        java.util.Date datFecDoc;
        int intPosRel;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom, d.co_tipnov, d.tx_deslar FROM tbm_novrechum AS a";
                strSQL+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
                strSQL+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
                strSQL+=" inner join tbm_tipnovrechum d on (d.co_tipnov=a.co_tipnov)";
                strSQL+=" where a.co_nov = " + rstCab.getString("co_nov");
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_nov");
                    txtCodNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_usrrem");
                    txtCodRem.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrrem");
                    txtCodRemNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrremnom");
                    txtDesRemNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_usrdes");
                    txtCodUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrdes");
                    txtDesCorUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_usrdesnom");
                    txtDesLarUsr.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_tipnov");
                    txtCodTipNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_deslar");
                    txtDesTipNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_asu");
                    txtDesAsuNov.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_men");
                    txtMsj.setText((strAux==null)?"":strAux);
                    
                    datFecDoc=rst.getDate("fe_ing");
//                    txtFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy hh:mm:ss"));
                    String strFeDoc = rst.getString("fe_ing");
                    String[] strArrFeDoc=strFeDoc.split(" ");
                    String strFeDocOrd=strArrFeDoc[0].split("-")[2] +"/"+strArrFeDoc[0].split("-")[1]+"/"+strArrFeDoc[0].split("-")[0];
                    txtFecDoc.setText(""+strFeDocOrd+" " + strArrFeDoc[1].substring(0,8));
                    
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("P"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    
                    cargarHistorialReenvios();
                    
                }else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                    blnRes=false;
                }
                
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarHistorialReenvios(){
        
        boolean blnRes=true;
        java.util.Date datFecDoc;
        java.sql.ResultSet rstLoc;
        Vector vecDataCon = null;
        java.sql.Statement stm;
        int intPosRel;
        String strSql="";
        int intCanRee=0;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                vecDataCon = new Vector();
                stm=conCab.createStatement();
                strSql="";
                
                strSql+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom FROM tbm_reenovrechum AS a";
                strSql+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
                strSql+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
                strSql+=" where a.co_nov = " + txtCodNov.getText();
                strSql+=" order by co_reg";
                
                rstLoc=stm.executeQuery(strSql);
                while (rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add(INT_TBL_FEC_REE,rstLoc.getString("fe_ing").substring(0,10));
                    vecReg.add(INT_TBL_REM,rstLoc.getString("tx_usrrem"));
                    vecReg.add(INT_TBL_DES,rstLoc.getString("tx_usrdes"));
                    vecReg.add(INT_TBL_OBS,rstLoc.getString("tx_obs1"));
                    vecReg.add(INT_TBL_BUT_OBS,"...");
                    vecDataCon.add(vecReg);
                    intCanRee++;
                }
                objTblMod.setData(vecDataCon);
                tblDat .setModel(objTblMod); 
                if(intCanRee>0){
                    tabGen.addTab("Historial de reenvios("+intCanRee+")", panGenTabRee);
                    setEditable(true);
                }else{
                    tabGen.addTab("Historial de reenvios", panGenTabRee);
                }
                
            }  
        }catch(java.sql.SQLException Evt) { objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
        finally {
                try{con.close();con=null;}catch(Throwable ignore){}
            }
        return blnRes;
    }

 public void  clnTextos(){
    strUsr=""; strNomUsr=""; strCodUsr="";
    txtCodNov.setText("");
    txtCodRem.setText("");
    txtCodRemNov.setText("");
    txtDesRemNov.setText("");
    txtCodUsr.setText("");
    txtDesCorUsr.setText("");
    txtDesLarUsr.setText("");
    txtCodTipNov.setText("");
    txtDesTipNov.setText("");
    txtDesAsuNov.setText("");
    txtMsj.setText("");
    txtFecDoc.setText("");
    tabGen.addTab("Historial de reenvios", panGenTabRee);
    objTblMod.removeAllRows();
 }
/**
 * validad campos requeridos antes de insertar o modificar
 * @return  true si esta todo bien   false   falta algun dato
 */
      private boolean validaCampos(){
          
          if(txtCodUsr.getText().equals("") ){
              tabGen.setSelectedIndex(0);
              mostrarMsgInf("El campo << Destinatario >> es obligatorio.\nEscoja y vuelva a intentarlo.");
              txtCodUsr.requestFocus();
              return false;
          }
          
          if(txtCodTipNov.getText().equals("") ){
              tabGen.setSelectedIndex(0);
              mostrarMsgInf("El campo << Tipo >> es obligatorio.\nEscoja y vuelva a intentarlo.");
              txtCodUsr.requestFocus();
              return false;
          }
          
          if(txtDesAsuNov.getText().equals("") ){
              tabGen.setSelectedIndex(0);
              mostrarMsgInf("El campo << Asunto >> es obligatorio.\nEscoja y vuelva a intentarlo.");
              txtCodUsr.requestFocus();
              return false;
          }
          return true;
      }

    public boolean insertar(){
        boolean blnRes=false;
        java.sql.Connection con=null;
            try{
                if(validaCampos()){
                        if(insertarReg())
                            blnRes=true;
                }
            } catch (Exception Evt) {
                blnRes = false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }finally {
                try{con.close();}catch(Throwable ignore){}
            }
            return blnRes;
    }
    
    public boolean insertarReg(){
        boolean blnRes=false;
        
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        String strSQL="";
        int intCodNov=0;
        
        try{
            con =java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(con!=null){ 
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                
                strSQL="SELECT max(co_nov) as co_nov from tbm_novRecHum";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                    intCodNov = rstLoc.getInt("co_nov")+1;
                rstLoc.close();
                rstLoc=null;
                
//                int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
//                String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                String FecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos(),"dd/MM/yyyy HH:MM:SS");
                
                if (insertarReg(con,intCodNov,FecAux)) { 
                    con.commit();
                    blnRes = true;
                    txtCodNov.setText(""+intCodNov);
                    txtFecDoc.setText(""+FecAux);
                }else{
                    con.rollback();
                }
            }
        }catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
            try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            try{con.close();con=null;}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
        public boolean insertarReg(java.sql.Connection con, int intCodNov,String FecAux){
            boolean blnRes=false;
            String strFecSis="";
            String strSql="";
            java.sql.Statement stmLoc = null;
            java.sql.ResultSet rstLoc = null;

            try{
                if(con!=null){
                    stmLoc=con.createStatement();

                      strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
                      String strStReg="P";

                      strSql="INSERT INTO tbm_novrechum(co_nov, co_usrrem, co_usrdes, co_tipnov, tx_asu, tx_men, st_lec, fe_lec, ne_numree, co_usrdesree,";
                      strSql+=" tx_obsfin, fe_fin, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod) VALUES (";
                      strSql+= " " + intCodNov + ",";
                      strSql+= " " + objZafParSis.getCodigoUsuario() + ",";
                      strSql+= " " + txtCodUsr.getText() + ",";
                      strSql+= " " + txtCodTipNov.getText() + ",";
                      strSql+= " '" + txtDesAsuNov.getText() + "',";
                      strSql+= " " + objUti.codificar(txtMsj.getText()) + ",";
                      strSql+= " NULL ,";
                      strSql+= " NULL ,";
                      strSql+= " NULL ,";
                      strSql+= " NULL ,";
                      strSql+= " NULL ,";
                      strSql+= " NULL ,";
                      strSql+= " " + objUti.codificar(strStReg) + ",";
                      strSql+= " CURRENT_TIMESTAMP ,";
                      strSql+= " NULL ,";
                      strSql+= " " + objZafParSis.getCodigoUsuario() + ",";
                      strSql+= " NULL ";
                      strSql+= ")";

                      System.out.println("insertTbm_NovRecHum: " + strSql);
                      stmLoc.executeUpdate(strSql);
                      blnRes=true;
                }
            }catch(java.sql.SQLException Evt){ 
                blnRes=false;  
                objUti.mostrarMsgErr_F1(this, Evt); 
            }catch(Exception Evt){ 
                blnRes=false;  
                objUti.mostrarMsgErr_F1(this, Evt); 
            }finally {
                try{stmLoc.close();}catch(Throwable ignore){}
                try{rstLoc.close();}catch(Throwable ignore){}
            }
           return blnRes;         
 }

public boolean eliminar() {
        boolean blnRes = false;
//        java.sql.Connection conn;
//        try {
//            
//            if(validaCampos()){
//
//                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
//                if(conn!=null){
//                    conn.setAutoCommit(false);
//    
//                    if(eliminarCab(conn)){
//                        conn.commit();
//                        blnRes=true;
//                    }else conn.rollback();
//
//                    conn.close();
//                    conn=null;
//                    clnTextos();
//                    objTooBar.setEstado('l');
//                    
//                }
//            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;            
    }

//    private boolean eliminarCab(java.sql.Connection conn){
//        boolean blnRes=false;
//        java.sql.Statement stmLoc;
//        String strSql="";
//        
//        try{
//            if(conn!=null){
//                stmLoc=conn.createStatement();
//                strSql="DELETE FROM tbr_depPrgUsr where co_mnu="+txtCodNov.getText()+" " +
//                       " and co_usr="+txtCodUsr.getText()+"  ";
//                stmLoc.executeUpdate(strSql);
//                stmLoc.close();
//                stmLoc=null;
//                blnRes=true;
//            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
//        return blnRes;
//    }
    
    public boolean modificar(){
        boolean blnRes=false;
        java.sql.Connection conn = null;
        try{
            if(validaCampos()){
                conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                if(conn!=null){
                    conn.setAutoCommit(false);
                        if(actualizar(conn)){
                        conn.commit();
                        blnRes=true;
                        }else conn.rollback();
                }
            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        finally {
                try{conn.close();conn=null;}catch(Throwable ignore){}
            }
        return blnRes;
    }
    
    private boolean isUsuarioRem(java.sql.Connection conn){
    
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
         try{
             if(conn!=null){
                 stmLoc=conn.createStatement();
                 strSql+="select co_usrrem from tbm_novrechum";
                 strSql+=" where co_nov = " + txtCodNov.getText();
                 rstLoc=stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                     if(rstLoc.getInt("co_usrrem")!=objZafParSis.getCodigoUsuario()){
                         blnRes=false;
                     }else if(rstLoc.getInt("co_usrrem")==objZafParSis.getCodigoUsuario()){
                         blnRes=true;
                     }
                 }
             }
         }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          finally {
                try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            }
         return blnRes;
    }
    
    private boolean verificarLeido(java.sql.Connection conn){
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
         try{
             if(conn!=null){
                 stmLoc=conn.createStatement();
                 strSql+="select st_lec from tbm_novrechum";
                 strSql+=" where co_nov = " + txtCodNov.getText();
                 rstLoc=stmLoc.executeQuery(strSql);
                 if(rstLoc.next()){
                     String strStLec=rstLoc.getString("st_lec");
                     if(strStLec!=null){
                         if(rstLoc.getString("st_lec").compareTo("S")==0){
                            blnRes=false;
                        }
                     }
                 }
             }
         }catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
          finally {
                try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            }
         return blnRes;
    }

    private boolean actualizar(java.sql.Connection conn){
        boolean blnRes=false;
        java.sql.Statement stmLoc = null;
        String strSql="";
        
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                strSql="update tbm_novrechum set co_tipnov =  " + txtCodTipNov.getText() + " , ";
                strSql+=" tx_asu =  " + objUti.codificar(txtDesAsuNov.getText()) + " , ";
                strSql+=" tx_men = " + objUti.codificar(txtMsj.getText()) + " , ";
                strSql+=" fe_ultmod = current_timestamp"+ " , ";
                strSql+=" co_usrmod = " + objZafParSis.getCodigoUsuario();
                strSql+=" where co_nov = " + txtCodNov.getText();
                stmLoc.executeUpdate(strSql);
                blnRes=true;
            }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
        catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        finally {
                try{stmLoc.close();stmLoc=null;}catch(Throwable ignore){}
            }
         return blnRes;
    }

        public boolean cancelar()
        {
           boolean blnRes=true;

            try {
                if (rstCab!=null) {
                    rstCab.close();
                    if (STM_GLO!=null){
                        STM_GLO.close();
                        STM_GLO=null;
                    }
                rstCab=null;

                }
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            clnTextos();
            return blnRes;
        }

        public boolean vistaPreliminar()
        {
            if (objThrGUIRpt==null)
            {
                objThrGUIRpt=new ZafThreadGUIRpt();
                objThrGUIRpt.setIndFunEje(1);
                objThrGUIRpt.start();
            }
    return true;
        }

        public boolean aceptar()
        {
            return true;
        }

        public boolean imprimir()
        {
            if (objThrGUIRpt==null)
            {

                objThrGUIRpt=new ZafThreadGUIRpt();
                objThrGUIRpt.setIndFunEje(0);
                objThrGUIRpt.start();
            }
            return true;
        }

        public boolean beforeInsertar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeConsultar(){
            boolean blnRes=true;

            return blnRes;
        }

        public boolean beforeModificar()
        {

            return true;
        }

        public boolean beforeEliminar()
        {

            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }

        public boolean afterInsertar()
        {
            objTooBar.setEstado('w');
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
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

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }

        public boolean afterCancelar()
        {
            return true;
        }
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
    private boolean mostrarVenConUsr(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(1);
                    vcoUsr.show();
                    if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtDesCorUsr.setText(vcoUsr.getValueAt(2));
                        txtDesLarUsr.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoUsr.buscar("a1.tx_usr", txtDesCorUsr.getText())) {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtDesCorUsr.setText(vcoUsr.getValueAt(2));
                        txtDesLarUsr.setText(vcoUsr.getValueAt(3));
                    } else {
                        vcoUsr.setCampoBusqueda(1);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtDesCorUsr.setText(vcoUsr.getValueAt(2));
                            txtDesLarUsr.setText(vcoUsr.getValueAt(3));
                        } 
                        else {
                            txtDesCorUsr.setText(strUsr);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoUsr.buscar("a1.tx_nom", txtDesLarUsr.getText())) {
                        txtCodUsr.setText(vcoUsr.getValueAt(1));
                        txtDesCorUsr.setText(vcoUsr.getValueAt(2));
                        txtDesLarUsr.setText(vcoUsr.getValueAt(3));
                    } else {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) {
                            txtCodUsr.setText(vcoUsr.getValueAt(1));
                            txtDesCorUsr.setText(vcoUsr.getValueAt(2));
                            txtDesLarUsr.setText(vcoUsr.getValueAt(3));
                        } else {
                            txtDesLarUsr.setText(strDesLarUsr);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConUsrRem(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoUsr.setCampoBusqueda(1);
                    vcoUsr.show();
                    if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) {
                        txtCodRem.setText(vcoUsr.getValueAt(1));
                        txtCodRemNov.setText(vcoUsr.getValueAt(2));
                        txtDesRemNov.setText(vcoUsr.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoUsr.buscar("a1.tx_usr", txtCodRemNov.getText())) {
                        txtCodRem.setText(vcoUsr.getValueAt(1));
                        txtCodRemNov.setText(vcoUsr.getValueAt(2));
                        txtDesRemNov.setText(vcoUsr.getValueAt(3));
                    } else {
                        vcoUsr.setCampoBusqueda(1);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) {
                            txtCodRem.setText(vcoUsr.getValueAt(1));
                            txtCodRemNov.setText(vcoUsr.getValueAt(2));
                            txtDesRemNov.setText(vcoUsr.getValueAt(3));
                        } 
                        else {
                            txtCodRemNov.setText(strUsr);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoUsr.buscar("a1.tx_nom", txtDesRemNov.getText())) {
                        txtCodRem.setText(vcoUsr.getValueAt(1));
                        txtCodRemNov.setText(vcoUsr.getValueAt(2));
                        txtDesRemNov.setText(vcoUsr.getValueAt(3));
                    } else {
                        vcoUsr.setCampoBusqueda(2);
                        vcoUsr.setCriterio1(11);
                        vcoUsr.cargarDatos();
                        vcoUsr.show();
                        if (vcoUsr.getSelectedButton() == vcoUsr.INT_BUT_ACE) {
                            txtCodRem.setText(vcoUsr.getValueAt(1));
                            txtCodRemNov.setText(vcoUsr.getValueAt(2));
                            txtDesRemNov.setText(vcoUsr.getValueAt(3));
                        } else {
                            txtDesRemNov.setText(strDesLarUsr);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipNov(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipNovRecHum.setCampoBusqueda(2);
                    vcoTipNovRecHum.setVisible(true);
                    if (vcoTipNovRecHum.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                        txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo".
                    if (vcoTipNovRecHum.buscar("a1.co_nov", txtCodTipNov.getText())) {
                        txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                        txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                    } else {
                        vcoTipNovRecHum.setCampoBusqueda(1);
                        vcoTipNovRecHum.setCriterio1(11);
                        vcoTipNovRecHum.cargarDatos();
                        vcoTipNovRecHum.show();
                        if (vcoTipNovRecHum.getSelectedButton() == vcoTipNovRecHum.INT_BUT_ACE) {
                            txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                            txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                        } else {
                            txtDesTipNov.setText(strDesLarTipNov);
                        }
                    }
                    break;
                 case 2: //Búsqueda directa por "Descripción Larga".
                     vcoTipNovRecHum.setCampoBusqueda(2);
                        vcoTipNovRecHum.setCriterio1(11);
                    if (vcoTipNovRecHum.buscar("a1.tx_deslar", txtDesTipNov.getText())) {
                        txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                        txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                    } else {
                        vcoTipNovRecHum.setCampoBusqueda(1);
                        vcoTipNovRecHum.setCriterio1(11);
                        vcoTipNovRecHum.cargarDatos();
                        vcoTipNovRecHum.show();
                        if (vcoTipNovRecHum.getSelectedButton() == vcoTipNovRecHum.INT_BUT_ACE) {
                            txtCodTipNov.setText(vcoTipNovRecHum.getValueAt(1));
                            txtDesTipNov.setText(vcoTipNovRecHum.getValueAt(2));
                        } else {
                            txtDesTipNov.setText(strDesLarTipNov);
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
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_FEC_REE:
                    strMsg="Fecha de reenvio";
                    break;
                case INT_TBL_REM:
                    strMsg="Remitente";
                    break;
                case INT_TBL_DES:
                    strMsg="Destinatario";
                    break;
                case INT_TBL_OBS:
                    strMsg="";
                    break;
                case INT_TBL_BUT_OBS:
                    strMsg = "Observación";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                   // objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                  //  objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                 //   objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                   // objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUIRpt=null;
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
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strNomUsr="",  strFecHorSer="", strCamAudRpt = "";
        String strNomEmp="", strPago="", strPeriodo="", strImpresión="";
        int i, intNumTotRpt;
        boolean blnRes=true;
        String sqlAux="", strSql = "";
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {

                
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            //case 415:
                            default:

                                 //Obtener la fecha y hora del servidor.
                                Date datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                                if (datFecAux!=null){
                                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                                }
                                
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                strCamAudRpt = "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ";
                               
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("co_nov", Integer.parseInt(txtCodNov.getText()) );
                                mapPar.put("strCamAudRpt", strCamAudRpt);
                                
                                /*java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("coemp", new Integer(objZafParSis.getCodigoEmpresa()) );
                                mapPar.put("strAux",  sqlAux );
                                mapPar.put("nomusr", strNomUsr );
                                mapPar.put("fecimp", strFecHorSer );
                                mapPar.put("SUBREPORT_DIR", strRutRpt );
                                mapPar.put("strCamAudRpt", "" + objZafParSis.getNombreUsuario() + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                */

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
}