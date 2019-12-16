/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom69.java
 *
 * Created on Jan 25, 2011, 10:06:36 AM
 */

package Compras.ZafCom69;
  
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;

/**
 *
 * @author jayapata
 */
public class ZafCom69 extends javax.swing.JInternalFrame {

    ZafParSis objParSis;
    ZafUtil objUti;
    ZafVenCon objVenConBodUsr;
    ZafVenCon objVenConItm;
    private ZafSelFec objSelFec;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModBod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl3;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblFilCab objTblFilCab;
    Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt,objTblCelEdiTxtNunRec, objTblCelEdiTxtAut, objTblCelEdiTxtDen, objTblCelEdiTxtAutNunRec, objTblCelEdiTxtDenNunRec;
    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG;
    Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk1, objTblCelRenChk2;
    Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk1, objTblCelEdiChk2;
    Librerias.ZafInventario.ZafInvItm objInvItm;
    Librerias.ZafUtil.UltDocPrint objUltDocPrint;

    String strVersion="Ver 0.5";
    String strCodBod="", strNomBod="";
    String strCodItm="", strCodAlt="", strDesItm="";
 
       //Constantes:  Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_BOD=2;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=3;                     //Nombre de la bodega.


     // TABLA DE DATOS
    final int INT_TBL_LIN= 0 ;
    final int INT_TBL_CODEMP= 1 ;
    final int INT_TBL_CODLOC= 2 ;
    final int INT_TBL_COTIDO= 3 ;
    final int INT_TBL_CODDOC= 4 ;
    final int INT_TBL_CODREG= 5 ;
    final int INT_TBL_CODBOD= 6 ;
    final int INT_TBL_NOMBOD= 7 ;
    final int INT_TBL_DESTIDC= 8 ;
    final int INT_TBL_DESTIDL= 9 ;
    final int INT_TBL_NUMDOC= 10 ;
    final int INT_TBL_FECDOC= 11 ;
    final int INT_TBL_BUT01= 12 ;
    final int INT_TBL_TXCODAL = 13 ;
    final int INT_TBL_TXNOMITM= 14 ;
    final int INT_TBL_CANTIDA=  15;
    final int INT_TBL_CANMALEST= 16;
    final int INT_TBL_CHKPRO =  17 ;
    final int INT_TBL_CANAUT= 18 ;
    final int INT_TBL_CANDEN= 19 ;
    final int INT_TBL_TXTOBS =20 ;
    final int INT_TBL_BUTOBS= 21 ;
    final int INT_TBL_CODEMP1= 22;
    final int INT_TBL_CODLOC1= 23 ;
    final int INT_TBL_COTIDO1= 24 ;
    final int INT_TBL_CODDOC1= 25 ;
    final int INT_TBL_CODREG1= 26 ;
    final int INT_TBL_ESTPROCE= 27 ;
    final int INT_TBL_CODITM= 28 ;
    final int INT_TBL_CODITMMAE=29;
    final int INT_TBL_UNIMED=30;
    final int INT_TBL_COSUNI=31;
    final int INT_TBL_CANNUNREC= 32;
    final int INT_TBL_CHKPRONUNREC =33 ;
    final int INT_TBL_CANNUNRECAUT= 34 ;
    final int INT_TBL_CANNUNRECDEN= 35 ;
    final int INT_TBL_TXTOBSNUNREC =36 ;
    final int INT_TBL_BUTOBSNUNREC= 37 ;
    final int INT_TBL_ESTPROCENUNREC=  38 ;

    
    
    private Vector vecDat, vecCab, vecReg;

    private boolean blnMarTodChkTblBod=true;
    private Vector vecAux;

    javax.swing.JTextField txtCodItm = new javax.swing.JTextField();

    StringBuffer stbDatDevCom=null;
    StringBuffer stbDatDevVen=null;
    int intDatDevCom=0;
    int intDatDevVen=0;

    StringBuffer stbDatDocRel=null;
    int intDatDocRel=0;
    
    int intCodBodGrpDan=7;  // BODEGA DANADOS GRUPO
    int intCodBodGrpFal=14;  // BODEGA FALTANTE GRUPO


    /** Creates new form ZafCom61 */
    public ZafCom69(ZafParSis objZafParsis) {
        try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();
        objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelEdiButGenDG=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
        objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis );
        objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
        
        initComponents();

        //Configurar ZafSelFec:
        objSelFec=new ZafSelFec();
        objSelFec.setCheckBoxVisible(true);
        objSelFec.setCheckBoxChecked(true);
        objSelFec.setTitulo("Fecha del documento");
        objSelFec.setCheckBoxVisible(true);
        panFilCab.add(objSelFec);
        objSelFec.setBounds(4, 100, 472, 72);

        vecDat=new Vector();

        this.setTitle(objParSis.getNombreMenu()+" "+ strVersion );
        lblTit.setText( objParSis.getNombreMenu() );
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }



 public void Configura_ventana_consulta(){

      configurarVenConItm();

      configurarForm();

      configurarTblBod();
      
     
      cargarBod();
     

    }


  /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN,"");
            vecCab.add(INT_TBL_BOD_CHK,"");
            vecCab.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModBod.setHeader(vecCab);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            //objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
           
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelRenLbl3=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl3);
            objTblCelRenLbl3=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk=null;

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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




 public void cargarBod(){
  java.sql.Connection  conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     conn =  java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
        stmLoc=conn.createStatement();

           //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSql="SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "+
                " FROM tbm_emp AS a1 "+
                " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "+
                " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" "+
                " ORDER BY a1.co_emp, a2.co_bod  ) as a ";
             }
            else
            {
                //Armar la sentencia SQL.

            if(objParSis.getCodigoEmpresaGrupo() == objParSis.getCodigoEmpresa() ){

                 strSql="SELECT co_bod, tx_nom FROM ( " +
                " select a1.co_bod, a1.tx_nom from tbr_bodlocprgusr as a " +
                " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
                " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
                " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+" " +
                " and a.tx_natbod = 'A' " +
                " ) as a";
                
                
            }else{

            strSql="SELECT co_bod, tx_nom FROM ( " +
            " select a2.co_bodgrp as co_bod,  a1.tx_nom from tbr_bodlocprgusr as a " +
            " inner join tbr_bodEmpBodGrp as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod) "+
            " inner join tbm_bod as a1 on (a1.co_emp=a2.co_empgrp and a1.co_bod=a2.co_bodgrp) " +
            " where a.co_emp="+objParSis.getCodigoEmpresa()+" AND a.co_loc="+objParSis.getCodigoLocal()+" " +
            " and a.co_usr="+objParSis.getCodigoUsuario()+" and a.co_mnu="+objParSis.getCodigoMenu()+" " +
            " and a.tx_natbod = 'A' " +
            " ) as a";
            
            }

            }


         //Limpiar vector de datos.
                vecDat.clear();
               
                //Obtener los registros.
                rstLoc=stmLoc.executeQuery(strSql);
                while (rstLoc.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rstLoc.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rstLoc.getString("tx_nom"));
                    
                    vecDat.add(vecReg);
                }

                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                blnMarTodChkTblBod=false;

                

        rstLoc.close();
        stmLoc.close();
        stmLoc=null;
        rstLoc=null;
        conn.close();
        conn=null;
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
}


        /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }






 private boolean configurarVenConItm() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_itm");
        arlCam.add("tx_codalt");
        arlCam.add("tx_descor");
        arlCam.add("tx_nomitm");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Cód.Itm");
        arlAli.add("Cód.Alt.");
        arlAli.add("Unidad.");
        arlAli.add("Nom.Itm.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("100");
        arlAncCol.add("60");
        arlAncCol.add("300");
       //Armar la sentencia SQL.
        String  strSQL="";
        String strAuxInv="",strConInv="";

 
        strSQL="SELECT * FROM( SELECT * "+strAuxInv+" FROM( select a.co_itm, a.tx_codalt, a1.tx_descor, a.tx_nomitm   from tbm_inv as a " +
        " left join tbm_var as a1 on (a1.co_reg=a.co_uni) " +
        " WHERE  a.co_emp="+objParSis.getCodigoEmpresa()+" and a.st_reg not in ('U','T')   ) AS x ) AS x  "+strConInv+"  ORDER BY tx_codAlt   ";
        objVenConItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }
    catch (Exception e) {
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}



private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CODEMP,"Cód.Emp");
    vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
    vecCab.add(INT_TBL_COTIDO,"Cód.Tip.Doc");
    vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
    vecCab.add(INT_TBL_CODREG,"Cód.Reg");
    vecCab.add(INT_TBL_CODBOD,"Cód.Bod");
    vecCab.add(INT_TBL_NOMBOD,"Nom.Bod");
    vecCab.add(INT_TBL_DESTIDC,"Tip.Doc");
    vecCab.add(INT_TBL_DESTIDL,"Des.Tip.Doc");
    vecCab.add(INT_TBL_NUMDOC,"Núm.Doc");
    vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
    vecCab.add(INT_TBL_BUT01,"");
    vecCab.add(INT_TBL_TXCODAL,"Cod.Alt");
    vecCab.add(INT_TBL_TXNOMITM,"Descripcion");
    vecCab.add(INT_TBL_CANTIDA,"Cantidad");
    vecCab.add(INT_TBL_CANMALEST,"Can.Mal.Est");
    vecCab.add(INT_TBL_CHKPRO,"Procesar");
    vecCab.add(INT_TBL_CANAUT,"Can.Aut");
    vecCab.add(INT_TBL_CANDEN,"Can.Den");
    vecCab.add(INT_TBL_TXTOBS,"Obsercancion");
    vecCab.add(INT_TBL_BUTOBS,"");
    vecCab.add(INT_TBL_CODEMP1,"Cód.Emp");
    vecCab.add(INT_TBL_CODLOC1,"Cód.Loc");
    vecCab.add(INT_TBL_COTIDO1,"Cód.Tip.Doc");
    vecCab.add(INT_TBL_CODDOC1,"Cód.Doc");
    vecCab.add(INT_TBL_CODREG1,"Cód.Reg");
    vecCab.add(INT_TBL_ESTPROCE,"");
    vecCab.add(INT_TBL_CODITM,"Cod.Itm");
    vecCab.add(INT_TBL_CODITMMAE,"Cod.Itm.Mae");
    vecCab.add(INT_TBL_UNIMED,"Uni.Med");
    vecCab.add(INT_TBL_COSUNI,"Cos.Uni");

    vecCab.add(INT_TBL_CANNUNREC,"Can.Nun.Rec.");
    vecCab.add(INT_TBL_CHKPRONUNREC,"Procesar");
    vecCab.add(INT_TBL_CANNUNRECAUT,"Can.Aut.");
    vecCab.add(INT_TBL_CANNUNRECDEN,"Can.Den.");
    vecCab.add(INT_TBL_TXTOBSNUNREC,"Obsercancion.");
    vecCab.add(INT_TBL_BUTOBSNUNREC,"");
    vecCab.add(INT_TBL_ESTPROCENUNREC,"");




    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

     //Configurar JTable: Establecer tipo de selección.
    tblDat.setRowSelectionAllowed(true);
    tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


      //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_CANTIDA, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANMALEST, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANAUT, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANDEN, objTblMod.INT_COL_DBL, new Integer(0), null);

    objTblMod.setColumnDataType(INT_TBL_CANNUNREC, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANNUNRECAUT, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANNUNRECDEN, objTblMod.INT_COL_DBL, new Integer(0), null);



    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);
    
    //Configurar JTable: Editor de búsqueda.
//    new ZafTblBus(tblDat);
//    new ZafTblOrd(tblDat);


    new ZafTblPopMnu(tblDat);

    ZafMouMotAda objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(35);
    tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_DESTIDC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DESTIDL).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_TXCODAL).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_TXNOMITM).setPreferredWidth(150);
    tcmAux.getColumn(INT_TBL_CANTIDA).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CANMALEST).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_BUT01).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CHKPRO).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CANAUT).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CANDEN).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_TXTOBS).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_BUTOBS).setPreferredWidth(25);

    tcmAux.getColumn(INT_TBL_CANNUNREC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CHKPRONUNREC).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CANNUNRECAUT).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CANNUNRECDEN).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_TXTOBSNUNREC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_BUTOBSNUNREC).setPreferredWidth(25);




     ArrayList arlColHid=new ArrayList();
         arlColHid.add(""+INT_TBL_CODEMP);
         arlColHid.add(""+INT_TBL_CODLOC);
         arlColHid.add(""+INT_TBL_COTIDO);
         arlColHid.add(""+INT_TBL_CODDOC);
         arlColHid.add(""+INT_TBL_CODREG);
         arlColHid.add(""+INT_TBL_CODBOD);
         arlColHid.add(""+INT_TBL_DESTIDC);
         arlColHid.add(""+INT_TBL_CODEMP1);
         arlColHid.add(""+INT_TBL_CODLOC1);
         arlColHid.add(""+INT_TBL_COTIDO1);
         arlColHid.add(""+INT_TBL_CODDOC1);
         arlColHid.add(""+INT_TBL_CODREG1);
         arlColHid.add(""+INT_TBL_ESTPROCE);
         arlColHid.add(""+INT_TBL_CODITM);
         arlColHid.add(""+INT_TBL_CODITMMAE);
         arlColHid.add(""+INT_TBL_UNIMED);
         arlColHid.add(""+INT_TBL_COSUNI);
         arlColHid.add(""+INT_TBL_ESTPROCENUNREC);
     objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
     arlColHid=null;



    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();

       vecAux.add("" + INT_TBL_BUT01);
       vecAux.add("" + INT_TBL_CHKPRO);
       vecAux.add("" + INT_TBL_CANAUT);
       vecAux.add("" + INT_TBL_CANDEN);
       vecAux.add("" + INT_TBL_TXTOBS);
       vecAux.add("" + INT_TBL_BUTOBS);
       vecAux.add("" + INT_TBL_BUTOBSNUNREC);
       vecAux.add("" + INT_TBL_CHKPRONUNREC);
       vecAux.add("" + INT_TBL_CANNUNRECAUT);
       vecAux.add("" + INT_TBL_CANNUNRECDEN);
       vecAux.add("" + INT_TBL_TXTOBSNUNREC);

    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.



            objTblCelRenButDG=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTOBS).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUT01).setCellRenderer(objTblCelRenButDG);
            tcmAux.getColumn(INT_TBL_BUTOBSNUNREC).setCellRenderer(objTblCelRenButDG);
            objTblCelRenButDG.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButDG.getColumnRender())
                    {
                      case INT_TBL_BUTOBS:
                            objTblCelRenButDG.setText("...");
                       break;
                       case INT_TBL_BUT01:
                            objTblCelRenButDG.setText("...");
                       break;

                        case INT_TBL_BUTOBSNUNREC:
                            objTblCelRenButDG.setText("...");
                       break;

                    }
                }
            });


          //Configurar JTable: Editor de celdas.

            tcmAux.getColumn(INT_TBL_BUTOBS).setCellEditor(objTblCelEdiButGenDG);
            tcmAux.getColumn(INT_TBL_BUT01).setCellEditor(objTblCelEdiButGenDG);
             tcmAux.getColumn(INT_TBL_BUTOBSNUNREC).setCellEditor(objTblCelEdiButGenDG);
            objTblCelEdiButGenDG.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTOBS:
                          break;

                          case INT_TBL_BUT01:
                          break;

                          case INT_TBL_BUTOBSNUNREC:
                          break;

                       }
                     }
                    }
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel=tblDat.getSelectedColumn();
                    int intCol = tblDat.getSelectedRow();
                    boolean blnEstBut=false;
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                          case INT_TBL_BUTOBS:

                            if(objTblMod.getValueAt(intFilSel, INT_TBL_ESTPROCE).toString().equals("N")) blnEstBut=true;
                            else blnEstBut=false;

                            llamarVenObs( (objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBS)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBS).toString()) ,intFilSel, INT_TBL_TXTOBS, blnEstBut );

                           break;

                        case INT_TBL_BUTOBSNUNREC:

                            if(objTblMod.getValueAt(intFilSel, INT_TBL_ESTPROCENUNREC).toString().equals("N")) blnEstBut=true;
                            else blnEstBut=false;

                           String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intFilSel, INT_TBL_CANNUNREC) );
                           double dblCanNunRec= objUti.redondear(strCannunrec,4);
                           if( dblCanNunRec <= 0  )  blnEstBut=false;

                            llamarVenObs( (objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBSNUNREC)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_TXTOBSNUNREC).toString()) ,intFilSel, INT_TBL_TXTOBSNUNREC, blnEstBut );

                           break;

                           case INT_TBL_BUT01:

                             llamarVentana( tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString()
                                    ,tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString()
                                    ,tblDat.getValueAt(intCol, INT_TBL_COTIDO).toString()
                                    ,tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString()
                                    ,tblDat.getValueAt(intCol, INT_TBL_CODLOC1).toString()
                                    ,tblDat.getValueAt(intCol, INT_TBL_COTIDO1).toString()
                                    ,tblDat.getValueAt(intCol, INT_TBL_CODDOC1).toString()
                                    );


                           break;



                        }
                }}
           });



    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();//inincializo el renderizador
    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
    tcmAux.getColumn(INT_TBL_CANTIDA).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANMALEST).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANAUT).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANDEN).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANNUNREC).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANNUNRECAUT).setCellRenderer(objTblCelRenLbl);
    tcmAux.getColumn(INT_TBL_CANNUNRECDEN).setCellRenderer(objTblCelRenLbl);
    objTblCelRenLbl=null;


     objTblCelEdiTxtAut=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_CANAUT).setCellEditor(objTblCelEdiTxtAut);
     objTblCelEdiTxtAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intCelSel=tblDat.getSelectedRow();

           if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCE).toString().equals("S") ){
             objTblCelEdiTxtAut.setCancelarEdicion(true);
           }

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intCelSel=tblDat.getSelectedRow();

           String strCanMalEst= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST) );
           String strCanAut=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANAUT));
           String strCanDen=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANDEN) );

           double dblCanMalEst= objUti.redondear(strCanMalEst,4);
           double dblCanAut= objUti.redondear(strCanAut,4);
           double dblCanDen = objUti.redondear(strCanDen,4);

            if( (dblCanAut+dblCanDen) > dblCanMalEst ){
               MensajeInf("HA SOPREPASADO A LA CANTIDAD EN MAL ESTADO.\nVERIFIQUE LOS DATOS E INTENTE DE NUEVO.");
               tblDat.setValueAt( "" , intCelSel, INT_TBL_CANAUT);
               dblCanAut=0;
            }
           if( (dblCanDen+dblCanAut)  > 0 ){
                tblDat.setValueAt( new Boolean(true), intCelSel,INT_TBL_CHKPRO);
            }else
                 tblDat.setValueAt( new Boolean(false), intCelSel,INT_TBL_CHKPRO);
      }
      });


     objTblCelEdiTxtAutNunRec=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_CANNUNRECAUT).setCellEditor(objTblCelEdiTxtAutNunRec);
     objTblCelEdiTxtAutNunRec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intCelSel=tblDat.getSelectedRow();

           String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
           double dblCanNunRec= objUti.redondear(strCannunrec,4);
           if( dblCanNunRec <= 0  )  objTblCelEdiTxtAutNunRec.setCancelarEdicion(true);

           if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCENUNREC).toString().equals("S") ){
             objTblCelEdiTxtAutNunRec.setCancelarEdicion(true);
           }

           

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intCelSel=tblDat.getSelectedRow();

           String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
           String strCanAut=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECAUT));
           String strCanDen=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECDEN) );

           double dblCanNunRec= objUti.redondear(strCannunrec,4);
           double dblCanAut= objUti.redondear(strCanAut,4);
           double dblCanDen = objUti.redondear(strCanDen,4);

            if( (dblCanAut+dblCanDen) > dblCanNunRec ){
               MensajeInf("HA SOPREPASADO A LA CANTIDAD NUNCA RECIBIDA.\nVERIFIQUE LOS DATOS E INTENTE DE NUEVO.");
               tblDat.setValueAt( "" , intCelSel, INT_TBL_CANNUNRECAUT);
               dblCanAut=0;
            }
            if( (dblCanDen+dblCanAut)  > 0 ){
                tblDat.setValueAt( new Boolean(true), intCelSel,INT_TBL_CHKPRONUNREC);
            }else
                 tblDat.setValueAt( new Boolean(false), intCelSel,INT_TBL_CHKPRONUNREC);
      }
      });



     objTblCelEdiTxtDen=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_CANDEN).setCellEditor(objTblCelEdiTxtDen);
     objTblCelEdiTxtDen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intCelSel=tblDat.getSelectedRow();

           if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCE).toString().equals("S") ){
             objTblCelEdiTxtDen.setCancelarEdicion(true);
           }
      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intCelSel=tblDat.getSelectedRow();

           String strCanMalEst= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST) );
           String strCanAut=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANAUT));
           String strCanDen=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANDEN) );

           double dblCanMalEst= objUti.redondear(strCanMalEst,4);
           double dblCanAut= objUti.redondear(strCanAut,4);
           double dblCanDen = objUti.redondear(strCanDen,4);

            if( (dblCanAut+dblCanDen) > dblCanMalEst ){
               MensajeInf("HA SOPREPASADO A LA CANTIDAD EN MAL ESTADO.\nVERIFIQUE LOS DATOS E INTENTE DE NUEVO.");
               tblDat.setValueAt( "" , intCelSel, INT_TBL_CANDEN);
               dblCanDen=0;
            }

            if( (dblCanDen+dblCanAut)  > 0 ){
                tblDat.setValueAt( new Boolean(true), intCelSel,INT_TBL_CHKPRO);
            }else
                 tblDat.setValueAt( new Boolean(false), intCelSel,INT_TBL_CHKPRO);
      }
      });
      


        
     objTblCelEdiTxtDenNunRec=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_CANNUNRECDEN).setCellEditor(objTblCelEdiTxtDenNunRec);
     objTblCelEdiTxtDenNunRec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
          int intCelSel=tblDat.getSelectedRow();

           String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
           double dblCanNunRec= objUti.redondear(strCannunrec,4);
           if( dblCanNunRec <= 0  )  objTblCelEdiTxtDenNunRec.setCancelarEdicion(true);


           if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCENUNREC).toString().equals("S") ){
             objTblCelEdiTxtDenNunRec.setCancelarEdicion(true);
           }
      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intCelSel=tblDat.getSelectedRow();

           String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
           String strCanAut=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECAUT));
           String strCanDen=objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECDEN) );

           double dblCanNunRec= objUti.redondear(strCannunrec,4);
           double dblCanAut= objUti.redondear(strCanAut,4);
           double dblCanDen = objUti.redondear(strCanDen,4);


            if( (dblCanAut+dblCanDen) > dblCanNunRec ){
               MensajeInf("HA SOPREPASADO A LA CANTIDAD NUNCA RECIBIDA.\nVERIFIQUE LOS DATOS E INTENTE DE NUEVO.");
               tblDat.setValueAt( "" , intCelSel, INT_TBL_CANNUNRECDEN);
               dblCanDen=0;
            }

            if( (dblCanDen+dblCanAut)  > 0 ){
                tblDat.setValueAt( new Boolean(true), intCelSel,INT_TBL_CHKPRONUNREC);
            }else
                 tblDat.setValueAt( new Boolean(false), intCelSel,INT_TBL_CHKPRONUNREC );
      }
      });





     objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_TXTOBS).setCellEditor(objTblCelEdiTxt);
     objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intCelSel=tblDat.getSelectedRow();

           if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCE).toString().equals("S") ){
             objTblCelEdiTxt.setCancelarEdicion(true);
           }

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
      }
      });
   

     objTblCelEdiTxtNunRec=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
     tcmAux.getColumn(INT_TBL_TXTOBSNUNREC).setCellEditor(objTblCelEdiTxtNunRec);
     objTblCelEdiTxtNunRec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
           int intCelSel=tblDat.getSelectedRow();

           String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
           double dblCanNunRec= objUti.redondear(strCannunrec,4);
           if( dblCanNunRec <= 0  )  objTblCelEdiTxtNunRec.setCancelarEdicion(true);
           
           if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCENUNREC).toString().equals("S") ){
             objTblCelEdiTxtNunRec.setCancelarEdicion(true);
           }

      }
      public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
      }
      });




     

            objTblCelRenChk1 = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKPRO).setCellRenderer(objTblCelRenChk1);
            objTblCelEdiChk1 = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_CHKPRO).setCellEditor(objTblCelEdiChk1);
                objTblCelEdiChk1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                       int intCelSel=tblDat.getSelectedRow();

                       if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCE).toString().equals("S") ){
                         objTblCelEdiChk1.setCancelarEdicion(true);
                       }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();

                   if( (tblDat.getValueAt(intCelSel,INT_TBL_CHKPRO)==null?"false":tblDat.getValueAt(intCelSel,INT_TBL_CHKPRO).toString()).equals("false")){

                    tblDat.setValueAt( "" , intCelSel, INT_TBL_CANDEN);
                    tblDat.setValueAt( "" , intCelSel, INT_TBL_CANAUT);
                    
                   }else{

                       String strCanMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST).toString()));
                       tblDat.setValueAt( "" , intCelSel, INT_TBL_CANDEN);
                       tblDat.setValueAt( strCanMalEst , intCelSel, INT_TBL_CANAUT);

                   }

                }
               });


            objTblCelRenChk2 = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKPRONUNREC).setCellRenderer(objTblCelRenChk2);
            objTblCelEdiChk2 = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_CHKPRONUNREC).setCellEditor(objTblCelEdiChk2);
                objTblCelEdiChk2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                       int intCelSel=tblDat.getSelectedRow();

                       String strCannunrec= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
                       double dblCanNunRec= objUti.redondear(strCannunrec,4);
                       if( dblCanNunRec <= 0  )  objTblCelEdiChk2.setCancelarEdicion(true);


                       if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCENUNREC).toString().equals("S") ){
                         objTblCelEdiChk2.setCancelarEdicion(true);
                       }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCelSel = tblDat.getSelectedRow();

                   if( (tblDat.getValueAt(intCelSel,INT_TBL_CHKPRONUNREC)==null?"false":tblDat.getValueAt(intCelSel,INT_TBL_CHKPRONUNREC).toString()).equals("false")){

                    tblDat.setValueAt( "" , intCelSel, INT_TBL_CANNUNRECDEN);
                    tblDat.setValueAt( "" , intCelSel, INT_TBL_CANNUNRECAUT);

                   }else{

                       String strCanMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC).toString()));
                       tblDat.setValueAt( "" , intCelSel, INT_TBL_CANNUNRECDEN);
                       tblDat.setValueAt( strCanMalEst , intCelSel, INT_TBL_CANNUNRECAUT);
                   }
                }
               });




    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

    tblDat.getTableHeader().setReorderingAllowed(false);

    
    /**************************************************************************/


     ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
     objTblHeaGrp.setHeight(16*2);

     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("   " );
     objTblHeaColGrpAmeSur.setHeight(16);

      objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_LIN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CODBOD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NOMBOD));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESTIDC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DESTIDL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_NUMDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_FECDOC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXCODAL));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXNOMITM));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANTIDA));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUT01));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


      objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" MERCADERIA EN MAL ESTADO  " );
      objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANMALEST));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKPRO));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANAUT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANDEN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXTOBS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBS));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


      objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" MERCADERIA FALTANTE  " );
      objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANNUNREC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CHKPRONUNREC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANNUNRECAUT));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_CANNUNRECDEN));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_TXTOBSNUNREC));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_BUTOBSNUNREC));
       objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
       objTblHeaColGrpAmeSur=null;


    /**************************************************************************/



    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}



private void llamarVenObs(String strObs, int intFil, int intCol, boolean blnEstButAce){
 CxC.ZafCxC72.ZafCxC72_02 obj1 = new  CxC.ZafCxC72.ZafCxC72_02(javax.swing.JOptionPane.getFrameForComponent(this), true , strObs, blnEstButAce );
 obj1.show();
 if(obj1.getAceptar())
   tblDat.setValueAt( obj1.getObser(), intFil, intCol );
 obj1=null;
}



private void llamarVentana(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodLocRel, String strCodTipDocRel, String strCodDocRel ){
       Compras.ZafCom19.ZafCom19 obj1 = new  Compras.ZafCom19.ZafCom19(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strCodLocRel, strCodTipDocRel, strCodDocRel, 2 );
       this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
       obj1.show();
}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        chkMerAut = new javax.swing.JCheckBox();
        chkMerPen = new javax.swing.JCheckBox();
        chkMerDen = new javax.swing.JCheckBox();
        panRepot = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer2 = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFilCab.setLayout(null);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFilCab.add(panBod);
        panBod.setBounds(0, 0, 660, 92);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Mostrar las confirmaciones que:"));
        jPanel3.setLayout(null);

        chkMerAut.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMerAut.setText("Tienen mercaderia autorizado  ( en mal estado ) o ( Faltante )");
        chkMerAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMerAutActionPerformed(evt);
            }
        });
        jPanel3.add(chkMerAut);
        chkMerAut.setBounds(20, 40, 390, 20);

        chkMerPen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMerPen.setSelected(true);
        chkMerPen.setText("Tienen mercaderia pendiente   ( en mal estado ) o ( Faltante )");
        chkMerPen.setActionCommand("Tienen mercaderia pendiente ( en mal estado ) o ( nunca recibida )");
        chkMerPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMerPenActionPerformed(evt);
            }
        });
        jPanel3.add(chkMerPen);
        chkMerPen.setBounds(20, 20, 380, 20);

        chkMerDen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        chkMerDen.setText("Tienen mercaderia denegada   ( en mal estado ) o ( Faltante )");
        chkMerDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMerDenActionPerformed(evt);
            }
        });
        jPanel3.add(chkMerDen);
        chkMerDen.setBounds(20, 60, 390, 20);

        panFilCab.add(jPanel3);
        jPanel3.setBounds(10, 180, 610, 90);

        tabFrm.addTab("General", panFilCab);

        panRepot.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        panRepot.add(jPanel1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRepot);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

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

        butCer2.setText("Cerrar");
        butCer2.setToolTipText("Cierra la ventana.");
        butCer2.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCer2ActionPerformed(evt);
            }
        });
        panBot.add(butCer2);

        jPanel2.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel2.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents






    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").


      
   
            if (butCon.getText().equals("Consultar")) {
                if (objThrGUI==null) {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
                objThrGUI=null;
            }
   


    }//GEN-LAST:event_butConActionPerformed



private boolean validarDat(){
  boolean blnRes=true;

   if(txtCodItm.getText().equals("")){
     MensajeInf("EL ITEM ES OBLIGATORIO...");
     tabFrm.setSelectedIndex(0);
     txtCodItm.requestFocus();
     return false;
  }

  int intEstBod=0;
        for (int j=0; j< tblBod.getRowCount(); j++){
          if (tblBod.getValueAt(j, INT_TBL_BOD_CHK)!=null){
           if (tblBod.getValueAt(j, INT_TBL_BOD_CHK).toString().equals("true")){

               intEstBod=1;

           }}
        }

  if( intEstBod==0 ){
     MensajeInf("LA BODEGA ES OBLIGATORIO...");
     tabFrm.setSelectedIndex(0);
     return false;
  }
  


  return blnRes;
}

private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }




    private class ZafThreadGUI extends Thread
    {
        public void run()
        {


            pgrSis.setIndeterminate(true);

             if (!cargarDetReg( sqlConFil()))
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                butCon.setText("Consultar");
            }


            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }

           pgrSis.setIndeterminate(false);

            objThrGUI=null;
        }
    }








    
private String sqlConFil(){
   String sqlFil="";
   if(objSelFec.isCheckBoxChecked() ){
      switch (objSelFec.getTipoSeleccion())
      {
                case 0: //Búsqueda por rangos
                    sqlFil+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    sqlFil+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    sqlFil+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
    }}
     return sqlFil;
}





 private boolean cargarDetReg(String strFilSql){
    boolean blnRes=true;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql = "";
    String strAuxBod="";
    String strAuxFil1="",strAuxFil2="";
    boolean blnCont=false;
    try{
        butCon.setText("Detener");
        lblMsgSis.setText("Obteniendo datos...");

     java.sql.Connection con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
     if(con!=null){
        stmLoc=con.createStatement();
        
        for (int j=0; j< tblBod.getRowCount(); j++){
          if (tblBod.getValueAt(j, INT_TBL_BOD_CHK)!=null){
           if (tblBod.getValueAt(j, INT_TBL_BOD_CHK).toString().equals("true")){

               if(strAuxBod.equals("")) strAuxBod +=  tblBod.getValueAt(j, INT_TBL_BOD_COD_BOD).toString();
               else strAuxBod += ","+tblBod.getValueAt(j, INT_TBL_BOD_COD_BOD).toString();

           }}
        }

    strAuxFil1=" AND ( st_solProReaMerMalEst in ('P','D')  OR st_solproreamernunrec in ('P','D') )  ";

    if( chkMerPen.isSelected() ){
         strAuxFil2 = "  ( st_solProReaMerMalEst in ('P') OR st_solproreamernunrec in ('P') ) ";
         blnCont=true;
    }
    if( chkMerAut.isSelected() ){
         if(!strAuxFil2.equals("")) strAuxFil2+=" OR ";
         strAuxFil2+=" ( case when nd_cannunrecaut is null then 0 else nd_cannunrecaut end  > 0 or  case when nd_canMalEstAut is null then 0 else nd_canMalEstAut end   > 0 ) ";
         blnCont=true;
    }
    if( chkMerDen.isSelected() ){
         if(!strAuxFil2.equals("")) strAuxFil2+=" OR ";
         strAuxFil2+=" ( case when nd_cannunrecden is null then 0 else nd_cannunrecden end   > 0 or case when nd_canMalEstDen is null then 0 else nd_canMalEstDen end   > 0 ) ";
         blnCont=true;
    }
 
    if(blnCont)  strAuxFil2="AND (  "+strAuxFil2+" ) ";

    if(blnCont) strFilSql += strAuxFil2;
    else   strFilSql += strAuxFil1;

    strSql ="select a.st_solproreamernunrec, a.nd_cannunrec, a.nd_cannunrecaut, a.nd_cannunrecden, a.tx_obsautproreamernunrec,  "
    + " inv.nd_cosuni,  a4.tx_unimed, a.co_itm, equinv.co_itmmae,  a.st_solProReaMerMalEst, a.tx_obsAutProReaMerMalEst, a.nd_canMalEstAut, a.nd_canMalEstDen, "
    + " a4.co_emp as co_emp1, a4.co_loc as co_loc1, a4.co_tipdoc as co_tipdoc1,"
    + " a4.co_Doc as co_Doc1, a4.co_reg as co_reg1 ,"
    + " a.co_emp, a.co_loc, a.co_tipdoc, a.co_Doc, a.co_reg, a6.co_bodGrp ,  "
    + " a2.tx_nom, a3.tx_descor, a3.tx_deslar, a1.ne_numDoc, a1.fe_doc "
    + " ,a4.tx_codalt, a4.tx_nomitm, a4.nd_can,  a.nd_canmalest "
    + " from tbm_detingegrmerbod as a "
    + " inner join tbm_cabingegrmerbod as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
    + " inner join tbm_bod as a2 on (a2.co_emp=a.co_emp and a2.co_bod=a.co_bod ) "
    + " inner join tbm_cabtipdoc as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc  )  "
    + " inner join tbm_detmovinv as a4 on (a4.co_emp=a.co_emp and a4.co_loc=a.co_locrel and a4.co_tipdoc=a.co_tipdocrel and a4.co_doc=a.co_docrel and a4.co_reg=a.co_regrel ) "
    + " INNER JOIN tbm_inv AS inv ON (inv.co_emp=a4.co_emp AND inv.co_itm=a4.co_itm) "
    + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_bod) "
    + " INNER JOIN tbm_equinv AS equinv ON(equinv.co_emp=a.co_emp and equinv.co_itm=a.co_itm ) " +
    "  where  ( a6.co_empGrp=0  AND a6.co_bodGrp in ( "+strAuxBod+" ) ) "
    + " and a.co_tipdoc=114 "
    + "  and a1.st_reg not in ('E','I') AND ( a.nd_canmalest > 0   or  a.nd_cannunrec > 0 )   "+strFilSql;

  //  System.out.println("--> "+strSql );

    rstLoc=stmLoc.executeQuery(strSql);
    vecDat.clear();
    lblMsgSis.setText("Cargando datos...");
    while (rstLoc.next()){

        vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
            vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
            vecReg.add(INT_TBL_COTIDO, rstLoc.getString("co_tipdoc") );
            vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
            vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
            vecReg.add(INT_TBL_CODBOD, rstLoc.getString("co_bodGrp") );
            vecReg.add(INT_TBL_NOMBOD, rstLoc.getString("tx_nom") );
            vecReg.add(INT_TBL_DESTIDC, rstLoc.getString("tx_descor") );
            vecReg.add(INT_TBL_DESTIDL, rstLoc.getString("tx_deslar") );
            vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numDoc") );
            vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
            vecReg.add(INT_TBL_BUT01, "");
            vecReg.add(INT_TBL_TXCODAL, rstLoc.getString("tx_codalt") );
            vecReg.add(INT_TBL_TXNOMITM, rstLoc.getString("tx_nomitm") );
            vecReg.add(INT_TBL_CANTIDA, rstLoc.getString("nd_can") );
            vecReg.add(INT_TBL_CANMALEST, rstLoc.getString("nd_canmalest") );
            vecReg.add(INT_TBL_CHKPRO, new Boolean(rstLoc.getString("st_solProReaMerMalEst")==null?false:(rstLoc.getString("st_solProReaMerMalEst").equals("D")?true:false)) );
            vecReg.add(INT_TBL_CANAUT, rstLoc.getString("nd_canMalEstAut") );
            vecReg.add(INT_TBL_CANDEN, rstLoc.getString("nd_canMalEstDen") );
            vecReg.add(INT_TBL_TXTOBS, rstLoc.getString("tx_obsAutProReaMerMalEst") );
            vecReg.add(INT_TBL_BUTOBS, "" );
            vecReg.add(INT_TBL_CODEMP1, rstLoc.getString("co_emp1") );
            vecReg.add(INT_TBL_CODLOC1, rstLoc.getString("co_loc1") );
            vecReg.add(INT_TBL_COTIDO1, rstLoc.getString("co_tipdoc1") );
            vecReg.add(INT_TBL_CODDOC1, rstLoc.getString("co_doc1") );
            vecReg.add(INT_TBL_CODREG1, rstLoc.getString("co_reg1") );
            vecReg.add(INT_TBL_ESTPROCE, (rstLoc.getString("st_solProReaMerMalEst")==null?"N":(rstLoc.getString("st_solProReaMerMalEst").equals("P")?"N":"S")) );
            vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
            vecReg.add(INT_TBL_CODITMMAE, rstLoc.getString("co_itmmae") );
            vecReg.add(INT_TBL_UNIMED, rstLoc.getString("tx_unimed") );
            vecReg.add(INT_TBL_COSUNI, rstLoc.getString("nd_cosuni") );
            vecReg.add(INT_TBL_CANNUNREC, rstLoc.getString("nd_cannunrec") );
            vecReg.add(INT_TBL_CHKPRONUNREC, new Boolean(rstLoc.getString("st_solproreamernunrec")==null?false:(rstLoc.getString("st_solproreamernunrec").equals("")?false:(rstLoc.getString("st_solproreamernunrec").equals("P")?false:true))    ) );
            vecReg.add(INT_TBL_CANNUNRECAUT, rstLoc.getString("nd_cannunrecaut") );
            vecReg.add(INT_TBL_CANNUNRECDEN, rstLoc.getString("nd_cannunrecden") );
            vecReg.add(INT_TBL_TXTOBSNUNREC, rstLoc.getString("tx_obsautproreamernunrec") );
            vecReg.add(INT_TBL_BUTOBSNUNREC, "" );
            vecReg.add(INT_TBL_ESTPROCENUNREC, (rstLoc.getString("st_solproreamernunrec")==null?"N":(rstLoc.getString("st_solproreamernunrec").equals("P")?"N":"S")) );
        vecDat.add(vecReg);
    }
     objTblMod.setData(vecDat);
     tblDat.setModel(objTblMod);
     vecDat.clear();

    rstLoc.close();
    stmLoc.close();
    con.close();
    rstLoc=null;
    stmLoc=null;
    con=null;

    pgrSis.setValue(0);
    lblMsgSis.setText("Listo...");
    butCon.setText("Consultar");

  }}catch (java.sql.SQLException e){  blnRes=false;   objUti.mostrarMsgErr_F1(this, e); }
    catch (Exception e){   blnRes=false;  objUti.mostrarMsgErr_F1(this, e);  }
   return blnRes;
}



















        


    private void butCer2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCer2ActionPerformed
        // TODO add your handling code here:
      exitForm();
}//GEN-LAST:event_butCer2ActionPerformed



    private void exitForm(){

     String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }





    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:

       
exitForm();
        
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

         Configura_ventana_consulta();
         
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkMerAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMerAutActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_chkMerAutActionPerformed

    private void chkMerPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMerPenActionPerformed
        // TODO add your handling code here:

       
    }//GEN-LAST:event_chkMerPenActionPerformed

    private void chkMerDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMerDenActionPerformed
        // TODO add your handling code here:

        
    }//GEN-LAST:event_chkMerDenActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:

        if(validaCampos()){
         if(guardarDat()){

             
         }
          cargarDetReg( sqlConFil() );
        }

       


    }//GEN-LAST:event_butGuaActionPerformed




 private boolean validaCampos(){

   for(int intCelSel=0; intCelSel<tblDat.getRowCount(); intCelSel++){
    if( (tblDat.getValueAt(intCelSel,INT_TBL_CHKPRO)==null?"false":tblDat.getValueAt(intCelSel,INT_TBL_CHKPRO).toString()).equals("true")){

           String strCanMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANMALEST).toString()));
           String strCanAutMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANAUT)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANAUT).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANAUT).toString()));
           String strCanDenMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANDEN)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANDEN).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEN).toString()));

           double dblCanMalEst= objUti.redondear(strCanMalEst,4);
           double dblCanAutMalEst= objUti.redondear(strCanAutMalEst,4);
           double dblCanDenMalEst = objUti.redondear(strCanDenMalEst,4);

            if( ((dblCanAutMalEst+dblCanDenMalEst) - dblCanMalEst) != 0  ){
               MensajeInf("Existe un campo en el Detalle que la cantidad sumada entre autorizada y denegada "
                       + "\nno es igual a la cantidad en mal estado, Tiene que ser igual .\nVerifique el campo y vuelva a intentarlo.");
               return false;

            }

    }
    if( (tblDat.getValueAt(intCelSel,INT_TBL_CHKPRONUNREC)==null?"false":tblDat.getValueAt(intCelSel,INT_TBL_CHKPRONUNREC).toString()).equals("true")){

           String strCanNunRect= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNREC) );
           String strCanAut= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECAUT) );
           String strCanDen= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECDEN) );
           
           double dblCanNunrec= objUti.redondear(strCanNunRect,4);
           double dblCanAut= objUti.redondear(strCanAut,4);
           double dblCanDen = objUti.redondear(strCanDen,4);

            if( ((dblCanAut+dblCanDen) - dblCanNunrec) != 0  ){
               MensajeInf("Existe un campo en el Detalle que la cantidad sumada entre autorizada y denegada  "
                       + "\nno es igual a la cantidad nunca recibida, Tiene que ser igual .\nVerifique el campo y vuelva a intentarlo.");
               return false;

            }

    }

   }

   return true;
 }



boolean blnMerFal=false;
String strMensMerFal="";

private boolean guardarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  String strCorEleTo=""; 
  try{
     conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
       conn.setAutoCommit(false);

       stbDatDocRel=new StringBuffer();
       intDatDocRel=0;

       
     Librerias.ZafCorElePrg.ZafCorElePrg objCorEle=new Librerias.ZafCorElePrg.ZafCorElePrg(objParSis, this);
       strCorEleTo=objCorEle._getCorEleAutMerNunRec_01();
     objCorEle=null;
     blnMerFal=false;
     strMensMerFal="Se envia a Bodega de Faltante : <br>"
     + "<table border=1> <tr><td>Cod.Alt.</td><td>Cantidad</td></tr>";

       if(guardarRegMalEst(conn)){
        if(guardarRegNunRec(conn)){

           conn.commit();
           conn.setAutoCommit(true);

             if(! stbDatDocRel.toString().equals("") ){
                asignaSecEmpGrp(conn, stbDatDocRel );
              }

           strMensMerFal+="</table>";
           if(blnMerFal){
                objInvItm.enviarCorreo(strCorEleTo, "Zafiro: Envio a bodega Faltante.", strMensMerFal );
           }

           blnRes=true;
           MensajeInf("Los datos se Guardaron con éxito.. ");

           
       }else conn.rollback();
      }else conn.rollback();


       stbDatDocRel=null;

       conn.close();
       conn=null;
   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }

    System.gc();
    return blnRes;
}


private boolean asignaSecEmpGrp(java.sql.Connection connLoc, StringBuffer stbDocRelSec ){
  boolean blnRes=false;
  java.sql.ResultSet rstLoc;
  java.sql.Statement stmLoc, stmLoc01;
  String strSql="";
  int intSecEmp=0,intSecGrp=0;
  try{
   if(connLoc!=null){
     stmLoc=connLoc.createStatement();
     stmLoc01=connLoc.createStatement();
     strSql="SELECT * FROM( "+stbDocRelSec.toString()+" ) AS x";

     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        intSecEmp=objUltDocPrint.getNumSecDoc(connLoc, rstLoc.getInt("coemp") );
        intSecGrp=objUltDocPrint.getNumSecDoc(connLoc, objParSis.getCodigoEmpresaGrupo() );

         strSql="UPDATE tbm_cabmovinv SET ne_SecEmp="+intSecEmp+", ne_SecGrp="+intSecGrp+" WHERE co_emp="+rstLoc.getInt("coemp")+" AND co_loc="+rstLoc.getInt("coloc")+" " +
         " AND co_tipdoc="+rstLoc.getInt("cotipdoc")+" AND  co_doc="+rstLoc.getInt("codoc")+"";
         stmLoc01.executeUpdate(strSql);
     }
     rstLoc.close();
     rstLoc=null;

    stmLoc.close();
    stmLoc=null;
    stmLoc01.close();
    stmLoc01=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    catch(Exception Evt) {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
  return blnRes;
}







int intCodBodGrpPri=1; // Bodega Tuval califormia

public boolean guardarRegMalEst(java.sql.Connection conn ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   String strSql="";
   String strObs="", strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
   String strCodEmp1="", strCodLoc1="", strCodTipDoc1="", strCodDoc1="", strCodReg1="";
   String strCanAutMalEst="", strCanDenMalEst="", strCodBodGrp="";
  int intCodBodGrpDoc=0;
   double dblCanAutMalEst=0;
   try{
     if(conn!=null){
      stmLoc=conn.createStatement();



      for(int intCelSel=0; intCelSel<tblDat.getRowCount(); intCelSel++){
        if( (tblDat.getValueAt(intCelSel,INT_TBL_CHKPRO)==null?"false":tblDat.getValueAt(intCelSel,INT_TBL_CHKPRO).toString()).equals("true")){
         if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCE).toString().equals("N") ){

           strCodEmp = tblDat.getValueAt(intCelSel, INT_TBL_CODEMP).toString();
           strCodLoc = tblDat.getValueAt(intCelSel, INT_TBL_CODLOC).toString();
           strCodTipDoc=tblDat.getValueAt(intCelSel, INT_TBL_COTIDO).toString();
           strCodDoc = tblDat.getValueAt(intCelSel, INT_TBL_CODDOC).toString();
           strCodReg = tblDat.getValueAt(intCelSel, INT_TBL_CODREG).toString();

           strCodEmp1 = tblDat.getValueAt(intCelSel, INT_TBL_CODEMP1).toString();
           strCodLoc1 = tblDat.getValueAt(intCelSel, INT_TBL_CODLOC1).toString();
           strCodTipDoc1=tblDat.getValueAt(intCelSel, INT_TBL_COTIDO1).toString();
           strCodDoc1 = tblDat.getValueAt(intCelSel, INT_TBL_CODDOC1).toString();
           strCodReg1 = tblDat.getValueAt(intCelSel, INT_TBL_CODREG1).toString();

           strCodBodGrp = tblDat.getValueAt(intCelSel, INT_TBL_CODBOD).toString();
           intCodBodGrpDoc=Integer.parseInt(strCodBodGrp);;

           strCanAutMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANAUT)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANAUT).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANAUT).toString()));
           strCanDenMalEst=(tblDat.getValueAt(intCelSel, INT_TBL_CANDEN)==null?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CANDEN).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CANDEN).toString()));
           strObs = (tblDat.getValueAt(intCelSel, INT_TBL_TXTOBS)==null?"":tblDat.getValueAt(intCelSel, INT_TBL_TXTOBS).toString());

         

           dblCanAutMalEst= objUti.redondear(strCanAutMalEst,6);

           String strTipProRea="";
           if(intCodBodGrpPri==intCodBodGrpDoc){
               if(!realizarTransBodDan(conn, dblCanAutMalEst, intCelSel, intCodBodGrpDan, "P" )){
                   blnRes=false;
                   break;
               }
               strTipProRea="T";
           }
           else{
              if( dblCanAutMalEst > 0 ){
               if(!realizarCompVenAut(conn, dblCanAutMalEst, intCelSel, "P", false )){
                   blnRes=false;
                   break;
               }
              }
               strTipProRea="D";
           }

           strSql=" UPDATE tbm_detingegrmerbod SET "
           + " nd_canMalEstAut="+strCanAutMalEst+", nd_canMalEstDen="+strCanDenMalEst+" "
           + " ,tx_obsAutProReaMerMalEst='"+strObs+"', fe_ingAutProReaMerMalEst="+objParSis.getFuncionFechaHoraBaseDatos()+"  "
           + " ,co_usrIngAutProReaMerMalEst="+objParSis.getCodigoUsuario()+""
           + " , tx_comIngAutProReaMerMalEst='"+objParSis.getNombreComputadoraConDirIP()+"'"
           + " ,st_solProReaMerMalEst='D' "
           + " ,st_tipProReaMerMalEst='"+strTipProRea+"' "
           + " WHERE "
           + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" ; ";

           strSql+=" UPDATE tbm_detmovinv SET "
           + " nd_canTotMalEstPro= ( CASE WHEN nd_canTotMalEstPro IS NULL THEN 0 ELSE nd_canTotMalEstPro END ) + ("+strCanAutMalEst+"+"+strCanDenMalEst+" ) "
           + " WHERE "
           + " co_emp="+strCodEmp1+" and co_loc="+strCodLoc1+" and co_tipdoc="+strCodTipDoc1+" "
           + " and co_doc="+strCodDoc1+" and co_reg="+strCodReg1+" ; ";
           
           stmLoc.executeUpdate(strSql);
           blnRes=true;


       }}}

      

      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}

public boolean guardarRegNunRec(java.sql.Connection conn ){
   boolean blnRes=true;
   java.sql.Statement stmLoc;
   String strSql="";
   String strObs="", strCodEmp="", strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
   String strCodEmp1="", strCodLoc1="", strCodTipDoc1="", strCodDoc1="", strCodReg1="";
   String strCanAut="", strCanDen="", strCodBodGrp="";
   int intCodBodGrpDoc=0;
   double dblCanAut=0;
   try{
     if(conn!=null){
      stmLoc=conn.createStatement();
      
      for(int intCelSel=0; intCelSel<tblDat.getRowCount(); intCelSel++){
        if( (tblDat.getValueAt(intCelSel,INT_TBL_CHKPRONUNREC)==null?"false":tblDat.getValueAt(intCelSel,INT_TBL_CHKPRONUNREC).toString()).equals("true")){
         if( tblDat.getValueAt(intCelSel, INT_TBL_ESTPROCENUNREC).toString().equals("N") ){
         
           strCodEmp = tblDat.getValueAt(intCelSel, INT_TBL_CODEMP).toString();
           strCodLoc = tblDat.getValueAt(intCelSel, INT_TBL_CODLOC).toString();
           strCodTipDoc=tblDat.getValueAt(intCelSel, INT_TBL_COTIDO).toString();
           strCodDoc = tblDat.getValueAt(intCelSel, INT_TBL_CODDOC).toString();
           strCodReg = tblDat.getValueAt(intCelSel, INT_TBL_CODREG).toString();

           strCodEmp1 = tblDat.getValueAt(intCelSel, INT_TBL_CODEMP1).toString();
           strCodLoc1 = tblDat.getValueAt(intCelSel, INT_TBL_CODLOC1).toString();
           strCodTipDoc1=tblDat.getValueAt(intCelSel, INT_TBL_COTIDO1).toString();
           strCodDoc1 = tblDat.getValueAt(intCelSel, INT_TBL_CODDOC1).toString();
           strCodReg1 = tblDat.getValueAt(intCelSel, INT_TBL_CODREG1).toString();

           strCodBodGrp = tblDat.getValueAt(intCelSel, INT_TBL_CODBOD).toString();
           intCodBodGrpDoc=Integer.parseInt(strCodBodGrp);;

           strCanAut= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECAUT) );
           strCanDen= objInvItm.getIntDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_CANNUNRECDEN) );
           strObs = objInvItm.getStringDatoValidado( tblDat.getValueAt(intCelSel, INT_TBL_TXTOBSNUNREC) );

           dblCanAut= objUti.redondear(strCanAut,4);

           strMensMerFal+="<tr><td>"+objInvItm.getStringDatoValidado(tblDat.getValueAt(intCelSel, INT_TBL_TXCODAL))+"</td><td> "+dblCanAut+" </td></tr> ";
           
           if(intCodBodGrpPri==intCodBodGrpDoc){
               if(!realizarTransBodDan(conn, dblCanAut, intCelSel, intCodBodGrpFal, "C" )){
                   blnRes=false;
                   break;
               }
              
           }
           else{
              if( dblCanAut > 0 ){
               if(!realizarCompVenAut(conn, dblCanAut, intCelSel, "C", true )){
                   blnRes=false;
                   break;
               }
              }
              
           }

           strSql=" UPDATE tbm_detingegrmerbod SET "
           + " nd_cannunrecaut="+strCanAut+", nd_cannunrecden="+strCanDen+" "
           + " ,tx_obsautproreamernunrec='"+strObs+"', fe_ingautproreamernunrec="+objParSis.getFuncionFechaHoraBaseDatos()+"  "
           + " ,co_usringautproreamernunrec="+objParSis.getCodigoUsuario()+""
           + " , tx_comingautproreamernunrec='"+objParSis.getNombreComputadoraConDirIP()+"'"
           + " ,st_solproreamernunrec='D' "
           + " WHERE "
           + " co_emp="+strCodEmp+" and co_loc="+strCodLoc+" and co_tipdoc="+strCodTipDoc+" "
           + " and co_doc="+strCodDoc+" and co_reg="+strCodReg+" ; ";

           strSql+=" UPDATE tbm_detmovinv SET "
           + " nd_cantotnunrecpro= ( CASE WHEN nd_cantotnunrecpro IS NULL THEN 0 ELSE nd_cantotnunrecpro END ) "
           + " + ("+strCanAut+"+"+strCanDen+" ) "
           + " WHERE "
           + " co_emp="+strCodEmp1+" and co_loc="+strCodLoc1+" and co_tipdoc="+strCodTipDoc1+" "
           + " and co_doc="+strCodDoc1+" and co_reg="+strCodReg1+" ; ";

           stmLoc.executeUpdate(strSql);
           blnRes=true;
           blnMerFal=true;


       }}}



      stmLoc.close();
      stmLoc=null;

   }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
     catch(Exception Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
    return blnRes;
}


private boolean realizarCompVenAut(java.sql.Connection conn, double dblCanAutMalEst, int intCelSel,String strEstConInv, boolean blnReaTrans  ){
  boolean blnRes=false;

   if(_generaDevoluciones(conn, dblCanAutMalEst, intCelSel, strEstConInv, blnReaTrans  )){
      blnRes=true;
   }

  return blnRes;
}


private boolean realizarTransBodDan(java.sql.Connection conn, double dblCanAutMalEst, int intCelSel, int intCodBodDanGrp, String strEstConInv  ){
  boolean blnRes=false;

  if(_generaTransferenciaBod(conn, dblCanAutMalEst, intCelSel, intCodBodDanGrp, strEstConInv  )){
      blnRes=true;
   }
  
  return blnRes;
}




 private boolean _generaTransferenciaBod(java.sql.Connection conn, double dblCanAutMalEst, int intCelSel, int intCodBodDanGrp, String strEstConInv  ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 double dblCanFal=0;
 String strCodEmp="", strCodLoc="", strCodBodGrp="", strCodItmmae="";
 int intCodLocTra=0;
 int intCodTipDocTra=0;
 double dblCanTrans=0;
 int intCodBodDanEmp=0;
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();
    stmLoc01=conn.createStatement();

    stbLisBodItmTemp=new StringBuffer();
    stbDocRelEmpTemp=new StringBuffer();


                   strCodEmp = tblDat.getValueAt(intCelSel, INT_TBL_CODEMP).toString();
                   strCodLoc = tblDat.getValueAt(intCelSel, INT_TBL_CODLOC).toString();
                   strCodItm = tblDat.getValueAt(intCelSel, INT_TBL_CODITM).toString();
                   strCodItmmae = ((tblDat.getValueAt(intCelSel, INT_TBL_CODITMMAE)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CODITMMAE).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CODITMMAE).toString()));

                   strCodBodGrp = tblDat.getValueAt(intCelSel, INT_TBL_CODBOD).toString();
                   //intCodBodGrpDoc=Integer.parseInt(strCodBodGrp);;

                   intCodLocTra=Integer.parseInt(strCodLoc);
                   intCodTipDocTra=153;


               dblCanFal= dblCanAutMalEst;

               Glo_dblCanFalCom=dblCanFal;
               Glo_dblCanFalDevCom=Glo_dblCanFalCom;
               Glo_dblCanFalDevVen=Glo_dblCanFalCom;


    /**************************************************************************************************************/



          if(Glo_dblCanFalDevCom > 0 ){
              // GENERA TRANSFERENCIA


              strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm   from tbm_invbod as a " +
              " inner join tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm )   "
              + " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) "+
              " where a.co_emp="+intCodEmpEnvMer+" and a2.co_itmmae= "+strCodItmmae+"  " +
              " AND ( a1.co_empGrp=0 AND a1.co_bodGrp= "+strCodBodGrp+"   )  and a.nd_stkact > 0  ";
               System.out.println("Paso Trans 1 --> "+strSql );
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){


                 if( rstLoc.getDouble("nd_stkact") >= Glo_dblCanFalDevCom ){
                     dblCanTrans=Glo_dblCanFalDevCom;
                 }else
                    dblCanTrans=rstLoc.getDouble("nd_stkact");


                   strSql="select a.co_emp, a.co_bod, a1.tx_nom  from tbr_bodEmpBodGrp as a "
                   + " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod )  "
                   + " where a.co_empgrp="+objParSis.getCodigoEmpresaGrupo()+""
                   + " and a.co_bodgrp="+intCodBodDanGrp+" and a.co_emp= "+strCodEmp+"  ";

                   rstLoc01 = stmLoc01.executeQuery(strSql);
                   if(rstLoc01.next()){
                     intCodBodDanEmp=rstLoc01.getInt("co_bod");
                     strNomBodSal=rstLoc01.getString("tx_nom");
                   }
                   rstLoc01.close();
                   rstLoc01=null;

                    if(!generaTrans(conn, conn, null, intCodEmpEnvMer, intCodLocTra, intCodTipDocTra, intCodBodGrpPri, intCodBodDanEmp,  rstLoc.getString("co_itm"), dblCanTrans, strNomBodSal, strNomBodIng ,  strEstConInv  ) ){
                       blnRes=false;  break;
                    }else{
                        Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dblCanTrans;
                        System.out.println(" GENERE TRANSFERENCIA  ");
                    }
              }
              rstLoc.close();
              rstLoc=null;

          }

  /**************************************************************************************************************/




    if(Glo_dblCanFalDevCom > 0 ){
        MensajeInf("PROBLEMA NO HAY STOCK PARA REALIZAR EL PROCESO.. ");
        blnRes=false;
    }

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}




 private boolean _generaTransferenciaBodFaltante(java.sql.Connection conn, int intCodEmp, int intCodLoc, double dblCanTrans, String strCodItmmae, int intCodBodSal, int intCodBodFalGrp, boolean blnReaTrans ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 int intCodLocTra=0;
 int intCodTipDocTra=0;
 int intCodBodDanEmp=0;
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();
    stmLoc01=conn.createStatement();

    if(blnReaTrans){

    stbLisBodItmTemp=new StringBuffer();
    stbDocRelEmpTemp=new StringBuffer();

       intCodLocTra=intCodLoc;
       intCodTipDocTra=153;

    /**************************************************************************************************************/

              strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm   from tbm_invbod as a " +
              " inner join tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm )   "
              + " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) "+
              " where a.co_emp="+intCodEmpEnvMer+" and a2.co_itmmae= "+strCodItmmae+"  " +
              " AND ( a1.co_empGrp=0 AND a1.co_bodGrp= "+intCodBodSal+"   )  and a.nd_stkact > 0  ";
              //System.out.println("Paso Trans fal 1 --> "+strSql );
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){

                   strSql="select a.co_emp, a.co_bod, a1.tx_nom  from tbr_bodEmpBodGrp as a "
                   + " inner join tbm_bod as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod )  "
                   + " where a.co_empgrp="+objParSis.getCodigoEmpresaGrupo()+""
                   + " and a.co_bodgrp="+intCodBodFalGrp+" and a.co_emp= "+intCodEmp+"  ";
                   // System.out.println("Paso Trans 1 --> "+strSql );
                   rstLoc01 = stmLoc01.executeQuery(strSql);
                   if(rstLoc01.next()){
                     intCodBodDanEmp=rstLoc01.getInt("co_bod");
                     strNomBodSal=rstLoc01.getString("tx_nom");
                   }
                   rstLoc01.close();
                   rstLoc01=null;

                    if(!generaTrans(conn, conn, null, intCodEmpEnvMer, intCodLocTra, intCodTipDocTra, intCodBodSal, intCodBodDanEmp,  rstLoc.getString("co_itm"), dblCanTrans, strNomBodSal, strNomBodIng, "C"  ) ){
                       blnRes=false;  break;
                    }
              }
              rstLoc.close();
              rstLoc=null;

  /**************************************************************************************************************/
     }
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}





  String strNomBodIng="Califormia";
  String strNomBodSal="";

  StringBuffer stbDevTemp, stbDevInvTemp, stbLisBodItmTemp, stbDocRelEmpTemp;
  double Glo_dblTotDevCom=0.00;
  double Glo_dblTotDevVen=0.00;

  double Glo_dblCanFalCom=0.00;
  double Glo_dblCanFalDevCom=0.00;
  double Glo_dblCanFalDevVen=0.00;
  double bldivaEmp=12.00;

  int intCodEmpEnvMer = 1; //  tuval
  int intCodLocEnvMer = 4; //  local tuval


int intCodDocFacCom=0;
int intCodLocFacCom=0;
int intCodTipDocFacCom=0;

int intCodDocFacVen=0;
int intCodLocFacVen=0;
int intCodTipDocFacVen=0;
int intCodDocDevCom=0;
int intCodDocDevven=0;


 private boolean _generaDevoluciones(java.sql.Connection conn, double dblCanAutMalEst, int intCelSel, String strEstConInv, boolean blnReaTrans  ){
 boolean blnRes=true;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 double dlbStkBobDevCom=0, dblCanFal=0, dblDesVta=0;
 int intCodBod=0;
 String strCodEmp="", strCodBodGrp="", strCodItm="", strCodItmmae="";
 int intCodLocTra=0;
 int intCodTipDocTra=0;
 int intCodItm=0;
 int intCodItmCom=0;
 double dblPreVta=0, dblCosUni=0;
 int intNumFila=0;
 double dblCanVenCom=0;
 double dblCanTrans=0;
 String strMerIngEgrFisBod="S";
  
 try{
   if(conn!=null){
    stmLoc=conn.createStatement();
    stmLoc01=conn.createStatement();


    stbLisBodItmTemp=new StringBuffer();
    stbDocRelEmpTemp=new StringBuffer();

   

                   strCodEmp = tblDat.getValueAt(intCelSel, INT_TBL_CODEMP).toString();
                   strCodItm = tblDat.getValueAt(intCelSel, INT_TBL_CODITM).toString();
                   strCodItmmae = ((tblDat.getValueAt(intCelSel, INT_TBL_CODITMMAE)==null)?"0":(tblDat.getValueAt(intCelSel, INT_TBL_CODITMMAE).toString().equals("")?"0":tblDat.getValueAt(intCelSel, INT_TBL_CODITMMAE).toString()));

                   strCodBodGrp = tblDat.getValueAt(intCelSel, INT_TBL_CODBOD).toString();
                   //intCodBodGrpDoc=Integer.parseInt(strCodBodGrp);;

                   strNomBodSal= tblDat.getValueAt(intCelSel, INT_TBL_NOMBOD).toString();



               dblCanFal= dblCanAutMalEst;

               Glo_dblCanFalCom=dblCanFal;
               Glo_dblCanFalDevCom=Glo_dblCanFalCom;
               Glo_dblCanFalDevVen=Glo_dblCanFalCom;

               

 //System.out.println(" Glo_dblCanFalDevCom ==>  "+ Glo_dblCanFalDevCom  );



               strSql="select distinct(co_empper) as empper from tbr_bodemp where co_emp="+intCodEmpEnvMer+" and co_empper not in ( "+intCodEmpEnvMer+" ) ";
               System.out.println("Paso 1 --> "+strSql );
               rstLoc=stmLoc.executeQuery(strSql);
               while(rstLoc.next()){

                   if(Glo_dblCanFalDevCom > 0 ){

                     strSql="SELECT a.co_emp, a3.co_empper, a.co_itm, a.co_itmrel, a3.co_loc, a3.co_locdevcom, a3.co_tipdocdevcom, a3.co_locdevven, a3.co_tipdocdevven, a2.nd_stkact, a.nd_salven "+
                     " ,a2.co_bod, a5.co_itmmae FROM tbm_invmovempgrp as a "+
                     " inner join tbm_inv as a1 on (a1.co_emp=a.co_emprel and a1.co_itm=a.co_itmrel) "+
                     " inner join tbr_bodemp as a3 on (a3.co_emp=a.co_emp and a3.co_loc="+intCodLocEnvMer+"  and a3.co_empper=a.co_emprel  and a3.st_reg='A') "+
                     " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm and a2.co_bod=a3.co_bodper) "+
                     " inner join tbm_equinv as a5 on (a5.co_emp=a2.co_emp and a5.co_itm=a2.co_itm )  " +
                     "     inner join tbr_bodEmpBodGrp as a4 on (a4.co_emp=a2.co_emp and a4.co_bod=a2.co_bod) "+
                     " where a.co_emp="+intCodEmpEnvMer+" and a5.co_itmmae= "+strCodItmmae+"  AND a.nd_salven < 0   AND  a2.nd_stkact > 0 "+
                     " AND ( a4.co_empGrp=0 AND a4.co_bodGrp= "+strCodBodGrp+"  ) "+
                     " AND  a3.co_empper="+rstLoc.getInt("empper")+" ";
                     System.out.println("Paso 2 --> "+strSql );
                     rstLoc01=stmLoc01.executeQuery(strSql);
                     while(rstLoc01.next()){

                          dlbStkBobDevCom= rstLoc01.getDouble("nd_stkact");

                           stbDatDevCom=new StringBuffer();
                           stbDatDevVen=new StringBuffer();
                           intDatDevCom=0;
                           intDatDevVen=0;

                          _getMovDevCompra(conn, rstLoc01.getInt("co_emp"),  rstLoc01.getInt("co_empper"), rstLoc01.getInt("co_locdevcom"), rstLoc01.getInt("co_tipdocdevcom"), rstLoc01.getInt("co_itmmae"), Glo_dblCanFalDevCom,  rstLoc01.getInt("co_bod"), dlbStkBobDevCom, rstLoc01.getInt("co_tipdocdevven"), strCodItm );
                          _getMovDevVenta(conn, rstLoc01.getInt("co_emp"),  rstLoc01.getInt("co_locdevven"), rstLoc01.getInt("co_tipdocdevven"),  rstLoc01.getInt("co_itmmae"), rstLoc01.getInt("co_empper"),   Glo_dblCanFalDevCom, dlbStkBobDevCom , rstLoc01.getInt("co_bod")   );

                           if( (!stbDatDevCom.toString().equals(""))  && (!stbDatDevVen.toString().equals("")) ){

                             if(_RealizarDevoluciones(conn, strMerIngEgrFisBod , rstLoc01.getInt("co_locdevcom"), rstLoc01.getInt("co_locdevven"), rstLoc01.getInt("co_tipdocdevcom"), rstLoc01.getInt("co_tipdocdevven"), rstLoc01.getInt("co_bod"), dlbStkBobDevCom, strCodItm, strNomBodSal,  blnReaTrans, strCodItmmae  ) ){

                               
                                    blnRes=true;
                                   System.out.println(" GENERE DEVOLUCIONES  ");
                           
                              
                             }else{ blnRes=false;  break;  }

                           }
                          stbDatDevCom=null;
                          stbDatDevVen=null;
                     }
                     rstLoc01.close();
                     rstLoc01=null;
                  }
               }
               rstLoc.close();
               rstLoc=null;

    /**************************************************************************************************************/


 //System.out.println(" Glo_dblCanFalDevCom ==>  "+ Glo_dblCanFalDevCom  );


          if(Glo_dblCanFalDevCom > 0 ){
              // GENERA TRANSFERENCIA


              strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm  from tbm_invbod as a " +
              " inner join tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm )   "
              + " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) "
              + " where a.co_emp="+intCodEmpEnvMer+" and a2.co_itmmae= "+strCodItmmae+"  " +
              " AND ( a1.co_empGrp=0 AND a1.co_bodGrp= "+strCodBodGrp+"   )  and a.nd_stkact > 0  ";
               //System.out.println("Paso Trans 1 --> "+strSql );
              rstLoc=stmLoc.executeQuery(strSql);
              while(rstLoc.next()){


                 if( rstLoc.getDouble("nd_stkact") >= Glo_dblCanFalDevCom ){
                     dblCanTrans=Glo_dblCanFalDevCom;
                 }else
                    dblCanTrans=rstLoc.getDouble("nd_stkact");


                 
                   strSql="SELECT a.co_loctra, a.co_tipdoctra, a.st_reg FROM tbr_bodemp as a"
                   + " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bodper) "
                   +" where a.co_emp="+intCodEmpEnvMer+" and a.co_empper="+intCodEmpEnvMer+" "
                   + " AND ( a1.co_empGrp=0 AND a1.co_bodGrp= "+strCodBodGrp+"   ) ";
                   
                   // System.out.println("Paso Trans 1 --> "+strSql );
                    
                   rstLoc01 = stmLoc01.executeQuery(strSql);
                   if(rstLoc01.next()){
                     intCodLocTra=rstLoc01.getInt("co_loctra");
                     intCodTipDocTra=rstLoc01.getInt("co_tipdoctra");
                   }
                   rstLoc01.close();
                   rstLoc01=null;

                    if(generaTrans(conn, conn, null, intCodEmpEnvMer, intCodLocTra, intCodTipDocTra, rstLoc.getInt("co_bod"), intCodBodGrpPri,  rstLoc.getString("co_itm"), dblCanTrans, strNomBodIng, strNomBodSal, strEstConInv   ) ){
                    
                     if(_generaTransferenciaBodFaltante(conn, intCodEmpEnvMer, intCodLocTra , dblCanTrans, strCodItmmae, intCodBodGrpPri,  intCodBodGrpFal,  blnReaTrans )){
                                  
                        Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dblCanTrans;
                        System.out.println(" GENERE TRANSFERENCIA  ");


                      }else{ blnRes=false;  break; }
                    }else{ blnRes=false;  break; }



              }
              rstLoc.close();
              rstLoc=null;

          }

  /**************************************************************************************************************/



       if(Glo_dblCanFalDevCom > 0 ){



          strSql="select a.co_emp , a.co_bod, a.nd_stkact, a.co_itm " +
          " from tbm_invbod as a " +
          " inner join tbm_equinv as a2 on (a2.co_emp=a.co_emp and a2.co_itm=a.co_itm ) " +
          " inner join tbr_bodEmpBodGrp as a1 on (a1.co_emp=a.co_emp and a1.co_bod=a.co_bod) " +
          " where   ( a1.co_empGrp=0 AND a1.co_bodGrp= "+strCodBodGrp+"  ) AND a2.co_itmmae= "+strCodItmmae+" AND a.nd_stkact > 0  " +
          " and a.co_emp != "+intCodEmpEnvMer+"  ";

          //System.out.println("Paso Compra venta 1 --> "+strSql );
          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

                 strCodEmp=rstLoc.getString("co_emp");

                 if( rstLoc.getDouble("nd_stkact") >= Glo_dblCanFalDevCom )
                     dblCanVenCom=Glo_dblCanFalDevCom;
                 else
                    dblCanVenCom=rstLoc.getDouble("nd_stkact");


        if(dblCanVenCom >  0 ){

                 intCodBod=rstLoc.getInt("co_bod");
                 /*********************************************************************/
                  intNumFila=intCelSel;
                  if(intCodEmpEnvMer==1){
                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=2854";
                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=3117";
                  }
//                  if(objZafParSis.getCodigoEmpresa()==2){
//                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=603";
//                   if(strCodEmp.equals("4")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=498";
//                  }
//                  if(objZafParSis.getCodigoEmpresa()==4){
//                   if(strCodEmp.equals("2")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=789";
//                   if(strCodEmp.equals("1")) strSql="SELECT nd_maxDes FROM tbm_cli WHERE co_emp="+strCodEmp+" AND co_cli=1039";
//                  }
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next())
                     dblDesVta=rstLoc01.getDouble("nd_maxDes");
                  rstLoc01.close();
                  rstLoc01=null;
                /*********************************************************************/
                  strSql="SELECT a.co_emp, a.co_itm, a1.nd_prevta1, nd_stkact, a1.tx_codalt FROM tbm_equinv as a " +
                  " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) "+
                  " WHERE a.co_emp="+strCodEmp+" AND a.co_itmmae = "+strCodItmmae+" ";
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next()){
                     intCodItm=rstLoc01.getInt("co_itm");
                     dblPreVta=rstLoc01.getDouble("nd_prevta1");
                     strCodAlt=rstLoc01.getString("tx_codalt");
                  }
                  rstLoc01.close();
                  rstLoc01=null;



                  strSql="SELECT a.co_emp, a.co_itm, a1.nd_prevta1, nd_stkact, a1.tx_codalt FROM tbm_equinv as a " +
                  " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) "+
                  " WHERE a.co_emp="+intCodEmpEnvMer+" AND a.co_itmmae = "+strCodItmmae+" ";
                  rstLoc01 = stmLoc01.executeQuery(strSql);
                  if(rstLoc01.next()){
                     intCodItmCom=rstLoc01.getInt("co_itm");
                  }
                  rstLoc01.close();
                  rstLoc01=null;


                  if(dblPreVta > 0 ){

                   int intCodBodDes =  _getCodigoBodDes(conn, intCodEmpEnvMer, intCodBodGrpPri, Integer.parseInt(strCodEmp) );
                   if(!(intCodBodDes== -1)){



                   intCodDocFacCom=0;
                   intCodLocFacCom=0;
                   intCodTipDocFacCom=0;

                   intCodDocFacVen=0;
                   intCodLocFacVen=0;
                   intCodTipDocFacVen=0;


                   dblCosUni  =  dblPreVta - ( dblPreVta * ( dblDesVta / 100 ) );
                 
                   if(generaVen(conn, strMerIngEgrFisBod,  Integer.parseInt(strCodEmp), intCodBod, intNumFila, dblCanVenCom, dblDesVta, intCodItm, dblPreVta, intCodBodDes, blnReaTrans )){
               
                     if(generaCom(conn, intCodItmCom,  strMerIngEgrFisBod, Integer.parseInt(strCodEmp),  intCodEmpEnvMer, intCodBodGrpPri, intNumFila, dblCanVenCom, intCodBod, dblCosUni, strNomBodSal, blnReaTrans )){

                       if(_generaTransferenciaBodFaltante(conn, intCodEmpEnvMer, intCodLocEnvMer ,  dblCanVenCom, strCodItmmae, intCodBodGrpPri,  intCodBodGrpFal,  blnReaTrans )){
                                  
                              
                         
                         if(ingresarEnTblRelacional(conn, Integer.parseInt(strCodEmp), intCodLocFacVen, intCodTipDocFacVen, intCodDocFacVen, (intNumFila+1)
                           , intCodEmpEnvMer, intCodLocFacCom, intCodTipDocFacCom, intCodDocFacCom, (intNumFila+1) ) ){

                           
                         blnRes=true;
                         Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dblCanVenCom;
                         ///System.out.println(" GENERE COMPRA VENTA  ");

                         

                        }else { blnRes=false;  break; }
                      }else { blnRes=false;  break; }
                     }else { blnRes=false;  break; }
                    }else { blnRes=false;  break; }

                   }else{  MensajeInf("PROBLEMA AL OBTENER BODEGA DESTINO.. ");  blnRes=false;  break; }

                }else{  MensajeInf("El item "+strCodAlt+" no tiene precio de lista.");  blnRes=false;  break; }

          }

          }
          rstLoc.close();
          rstLoc=null;

       }

  /*************************************************************************************************************/


  

    if(Glo_dblCanFalDevCom > 0 ){
        MensajeInf("PROBLEMA NO HAY STOCK PARA REALIZAR EL PROCESO.. ");
        blnRes=false;
    }

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
  return blnRes;
}

private boolean generaTrans(java.sql.Connection conn, java.sql.Connection connLoc, java.sql.Connection connRemota, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, String strCodItm, double dblanmov,String strNomBodIngTra, String strNomBodSalTra, String strEstConInv  ){
boolean blnRes=false;
try{
   if(conn != null ){

     if(consultarTansAut(conn, connLoc, connRemota, intCodEmp,  intCodLoc, intCodTipDoc, intCodBodOri, intCodBodDes, strCodItm, dblanmov, strNomBodIngTra, strNomBodSalTra,  strEstConInv  )){
         blnRes=true;
     }else conn.rollback();

}}catch(java.sql.SQLException e)  {  blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
   catch(Exception Evt)  { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
return blnRes;
}

private int getCodigoDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
  int intCodDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
     " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intCodDoc = rstLoc.getInt("co_doc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
   catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
 return intCodDoc;
}


private boolean consultarTansAut(java.sql.Connection conn, java.sql.Connection connLoc, java.sql.Connection connRemota,  int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodBodOri, int intCodBodDes, String strCodItm, double dblanmov, String strNomBodIngTra, String strNomBodSalTra, String strEstConInv  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc, stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String str_MerIEFisBod="N";
  int intCodDoc=0;
  int intNumDoc=0;
  int intSecEmp=0;
  int intSecGrp=0;
  int intCodRegEgr=0;
  int intCodRegIng=0;


  try{
  if(conn!=null){

     stmLoc=conn.createStatement();
     stmLoc01=conn.createStatement();

     StringBuffer stbTbl1;

     double dblTot=0;
     int intReg=1;
     int intNumFil=1;
     int intControl=0;



     dblTot=0;
     intReg=1;
     intNumFil=1;
     stbTbl1=new StringBuffer();
     intControl=0;
     intCodDoc=0;


     objInvItm.limpiarObjeto();
     objInvItm.inicializaObjeto();

//     if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
//     stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBodOri+" AS COBOD, "+strCodItm+" AS COITM, "+dblanmov+" AS NDCAN ");
//     intConStbBod=1;



     strSql="select a.co_itm, a.tx_codalt, a.tx_nomitm, a1.tx_descor as tx_unimed,  "+intCodBodOri+" as co_bodori, " +
     " "+intCodBodDes+" as co_boddes,  "+dblanmov+" as nd_can, a.nd_cosuni, ( "+dblanmov+" * a.nd_cosuni) as costot " +
     " from tbm_inv as a " +
     " left join  tbm_var as a1 on (a1.co_reg=a.co_uni) " +
     " where  a.co_emp="+intCodEmp+"  and a.co_itm="+strCodItm;
     rstLoc=stmLoc.executeQuery(strSql);
     while(rstLoc.next()){

        if(intControl==0)
           intCodDoc=getCodigoDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );

         dblTot += rstLoc.getDouble("costot");



         intCodRegEgr= intReg;
         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec, tx_nombodorgdes ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_bodori")+", "+rstLoc.getDouble("nd_can")*-1+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")*-1+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")*-1+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")*-1+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " 'S', 0, '"+strNomBodIngTra+"'  ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="E";
         

         objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), rstLoc.getInt("co_bodori"), rstLoc.getDouble("nd_can"),  -1 , "N", "S", str_MerIEFisBod, 1);
        

         intCodRegIng= intReg;
         strSql="INSERT INTO tbm_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, " +
         " tx_codalt, tx_nomitm, tx_unimed, co_bod, nd_can, nd_cosuni, nd_preuni, nd_pordes, st_ivacom, " +
         " nd_cancon,  ne_numfil, nd_tot, tx_codalt2, nd_costot, nd_cospro, " +
         " nd_cosunigrp, nd_costotgrp, nd_candev, st_regrep, co_itmact, st_meringegrfisbod, nd_cannunrec, tx_nombodorgdes ) " +
         " VALUES( "+intCodEmp+","+intCodLoc+","+intCodTipDoc+", "+intCodDoc+", "+intReg+", "+rstLoc.getInt("co_itm")+"," +
         " '"+rstLoc.getString("tx_codalt")+"', '"+rstLoc.getString("tx_nomitm")+"', '"+rstLoc.getString("tx_unimed")+"',"+
         " "+rstLoc.getInt("co_boddes")+", "+rstLoc.getDouble("nd_can")+", "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getString("nd_cosuni")+"," +
         " 0, 'N', 0, "+intNumFil+", "+rstLoc.getDouble("costot")+", '"+rstLoc.getString("tx_codalt")+"', "+rstLoc.getDouble("costot")+", "+rstLoc.getString("nd_cosuni")+"," +
         " "+rstLoc.getString("nd_cosuni")+", "+rstLoc.getDouble("costot")+",  0, 'I', "+rstLoc.getInt("co_itm")+", " +
         " 'S', "+(strEstConInv.equals("P")?0:rstLoc.getDouble("nd_can"))+"   , '"+strNomBodSalTra+"' ) ; ";
         stbTbl1.append( strSql );
         intReg++;
         str_MerIEFisBod="I";

         

         strSql = " INSERT INTO tbr_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep  ) " +
          " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodRegEgr+", 'A' " +
          " ,"+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodRegIng+", 'P' ) ; ";
          stbTbl1.append( strSql );



         objInvItm.generaQueryStock(intCodEmp, Integer.parseInt(strCodItm), rstLoc.getInt("co_boddes"), rstLoc.getDouble("nd_can"),  1 , "N", "S", str_MerIEFisBod, 1);



        intNumFil++;
        intControl=1;
     }
     rstLoc.close();
     rstLoc=null;




     if(intControl==1){
       intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc );

//       intSecEmp=getNumeroOrdenEmpresa(conn, intCodEmp ); *
//       intSecGrp=getNumeroOrdenGrupo(conn );

//        intSecEmp=objUltDocPrint.getNumSecDoc(conn, intCodEmp );
//        intSecGrp=objUltDocPrint.getNumSecDoc(conn, objZafParSis.getCodigoEmpresaGrupo() );

//       if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//        stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//       intDocRelEmp=1;


      if( insertarCabTrans(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc, dblTot ,  intSecEmp, intSecGrp, strEstConInv  ) ){
       if(insertarCabDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intNumDoc ) ){
         if(insertarDetDiaTrans( conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, dblTot, intCodBodOri , intCodBodDes ) ){
            stmLoc.executeUpdate(stbTbl1.toString());
       }}}
     }
     stbTbl1=null;




      if(!objInvItm.ejecutaActStock(connLoc, 3))
          return false;

       if(connRemota!=null){
            //System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, 3 ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, 3))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(connLoc, 3 ))
            return false;
       }
      objInvItm.limpiarObjeto();





     blnRes=true;
     stmLoc.close();
     stmLoc=null;
     stmLoc01.close();
     stmLoc01=null;
  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
  return  blnRes;
}



private boolean insertarCabTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc, double dlbTot , int intSecEmp, int intSecGrp, String strEstConInv  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();

    java.util.Date datFecAux =objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());


     if(intDatDocRel==1)  stbDatDocRel.append(" UNION ALL ");
     stbDatDocRel.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC, "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
     intDatDocRel=1;

    strSql="INSERT INTO tbm_cabmovinv(co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc, " +
    " nd_sub, nd_tot, nd_poriva, st_reg, fe_ing , co_usring , " +
    " ne_secemp, ne_secgrp, nd_valiva,  co_mnu, st_tipdev, st_coninv, " +
    " st_imp, st_creguirem, st_coninvtraaut,  st_docconmersaldemdebfac , st_regrep ,st_docGenDevMerMalEst  ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intNumDoc+" ,'"+datFecAux+"', " +
    " "+(dlbTot*-1)+", "+(dlbTot*-1)+", 0, 'A', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+objParSis.getCodigoUsuario()+", " +
    " "+intSecEmp+", "+intSecGrp+", 0,  779, 'C', '"+strEstConInv+"',  'S', '"+(strEstConInv.equals("P")?"N":"S")+"', 'N', 'N', 'I', null ) ";

    strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" ";


    stmLoc.executeUpdate(strSql);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean insertarCabDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intNumDoc ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 String strSql="";
 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();

    java.util.Date datFecAux =objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

    strSql="INSERT INTO tbm_cabdia( co_emp, co_loc, co_tipdoc, co_dia, tx_numdia, fe_dia, " +
    " st_reg, fe_ing , co_usring, st_regrep, st_imp, st_usrmodasidiapre, st_asidiareg ) "+
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", '"+intNumDoc+"' ,'"+datFecAux+"', " +
    " 'A', "+objParSis.getFuncionFechaHoraBaseDatos()+", "+objParSis.getCodigoUsuario()+", 'I','N','N','S' )";
    stmLoc.executeUpdate(strSql);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

private boolean insertarDetDiaTrans(java.sql.Connection  conIns, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dlbTot, int intBodOri, int intBodDes ){
 boolean blnRes=false;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc;
 String strSql="", strSql2="";
 int intCodReg=1;
 int intCtaOri=0;
 int intCtaDes=0;

 try{
    if(conIns!=null){
      stmLoc=conIns.createStatement();
      stmLoc01=conIns.createStatement();

        strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodOri+" ";
        rstLoc=stmLoc01.executeQuery(strSql2);
        if(rstLoc.next()){
            intCtaOri= rstLoc.getInt("co_ctaexi");
        }
        rstLoc.close();
        rstLoc=null;

        strSql2="select co_ctaexi from tbm_bod where co_emp="+intCodEmp+" and co_bod="+intBodDes+" ";
        rstLoc=stmLoc01.executeQuery(strSql2);
        if(rstLoc.next()){
            intCtaDes= rstLoc.getInt("co_ctaexi");
        }
        rstLoc.close();
        rstLoc=null;



    strSql="INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaOri+", " +
    " 0, "+dlbTot+", 'I' ) ; ";

    intCodReg++;
     strSql +=" INSERT INTO tbm_detdia( co_emp, co_loc, co_tipdoc, co_dia, co_reg,  co_cta, nd_mondeb, nd_monhab, st_regrep ) " +
    " VALUES ("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+" ,"+intCtaDes+", " +
    " "+dlbTot+", 0,  'I' ) ; ";
    stmLoc.executeUpdate(strSql);

   stmLoc.close();
   stmLoc=null;
   blnRes=true;
}}
catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }

return blnRes;
}

      

private boolean generaComDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, String strIngEgrFisBod, String strNomBodVen, String strNomBodSal, boolean blnReaTrans  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblPorDes=0;
       double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);



        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_obs1, tx_nombodorgdes, nd_cannunrec  ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+((tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString()))+"' "+
        " ,'"+((tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString()))+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_TXNOMITM)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_TXNOMITM).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_TXNOMITM).toString()))+"' "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_UNIMED)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString()))+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strIngEgrFisBod+"', 0 ,"+dblCosUni+", '"+strNomBodVen+"', '"+strNomBodSal+"', "+(blnReaTrans?dblCan:0)+" ) ";
        stmLoc.executeUpdate(strSql);

//        if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//        intDatVen=1;

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom+"+dblCan+" WHERE co_emp="+intCodEmpEnvMer+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}




private boolean generaCom(java.sql.Connection conn, int intCodItmCom,  String strMerIngEgrFisBod,  int intCodEmpCom, int intCodEmp, int intCodBod, int intNumFil, double dblCan, int intCodBodVen, double dblCosUni, String strNomBodSal, boolean blnReaTrans ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
 // int intCodItm=0;
  String strEstConf="P";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      intCodLoc=intCodLocEnvMer;

      String strNomBodVen="";

        if(intCodEmpCom==1){
          if( intCodBodVen==1 )  strNomBodVen="BC";
          if( intCodBodVen==7 )  strNomBodVen="BV";
          if( intCodBodVen==8 )  strNomBodVen="BQ";
          if( intCodBodVen==10 )  strNomBodVen="BM";
          if( intCodBodVen==12 )  strNomBodVen="BSD";
        }
        if(intCodEmpCom==2){
          if( intCodBodVen==1 )  strNomBodVen="BQ";
          if( intCodBodVen==3 )  strNomBodVen="BC";
          if( intCodBodVen==4 )  strNomBodVen="BM";
          if( intCodBodVen==9 )  strNomBodVen="BV";
          if( intCodBodVen==12 )  strNomBodVen="BSD";
        }
        if(intCodEmpCom==4){
          if( intCodBodVen==1 )  strNomBodVen="BV";
          if( intCodBodVen==2 )  strNomBodVen="BC";
          if( intCodBodVen==7 )  strNomBodVen="BQ";
          if( intCodBodVen==9 )  strNomBodVen="BM";
          if( intCodBodVen==11 )  strNomBodVen="BSD";
        }




 if(intCodEmpEnvMer==1){
    if(intCodEmpCom==1){
     if( intCodBodVen==1 ) strEstConf="F";
    }
    if(intCodEmpCom==2){
     if( intCodBodVen==3 ) strEstConf="F";
    }
    if(intCodEmpCom==4){
     if( intCodBodVen==2 ) strEstConf="F";
    }
 }


      strSql="SELECT co_loccom as co_loc, co_tipdoccom FROM tbr_bodemp WHERE co_emp="+intCodEmpEnvMer+" AND" +
      " co_loc="+intCodLocEnvMer+" AND co_empper="+intCodEmpCom+" AND co_bodper="+intCodBodVen;
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         intCodTipDoc=rstLoc.getInt("co_tipdoccom");
      }
      rstLoc.close();
      rstLoc=null;

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;

      //intCodItm=Integer.parseInt( objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM)) );

      objInvItm.limpiarObjeto();


      double dblPorDes=0;
      double dblValDes = ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal,2);
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;


      intCodDocFacCom=intCodDoc;
      intCodLocFacCom=intCodLoc;
      intCodTipDocFacCom=intCodTipDoc;

      if(blnReaTrans) strEstConf="C";

      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpCom , 2, dlbSub, dlbValIva, dlbTot, strEstConf, 0, blnReaTrans )){
       if(generaComDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItmCom, 1, intCodEmpCom, dblCosUni, strMerIngEgrFisBod, strNomBodVen, strNomBodSal, blnReaTrans )){
        if(actualizaStock(conn, null, intCodEmp, intCodBod , dblCan, 1, intCodItmCom , strMerIngEgrFisBod, "I" )){
           blnRes=true;
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}





private boolean generaVen(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodBod, int intNumFil, double dblCan, double intDesVta, int intCodItm, double dblPreVta, int intCodBodDes, boolean blnReaTrans ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodLoc=0;
  int intCodTipDoc=0;
  int intCodDoc=0;
  String strEstConf="C";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT co_locven as co_loc, co_tipdocven FROM tbr_bodemp WHERE co_emp="+intCodEmpEnvMer+" AND" +
      " co_loc="+intCodLocEnvMer+" AND co_empper="+intCodEmp+" AND co_bodper="+intCodBod;
      rstLoc=stmLoc.executeQuery(strSql);
      if(rstLoc.next()){
         intCodLoc=rstLoc.getInt("co_loc");
         intCodTipDoc=rstLoc.getInt("co_tipdocven");
      }
      rstLoc.close();
      rstLoc=null;

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;

      objInvItm.limpiarObjeto();

       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;
       double  dlbSub =  objUti.redondear(dblTotal,2);
       double dlbValIva=  dlbSub * (bldivaEmp/100);
       double dlbTot=  dlbSub + dlbValIva;
       dlbSub=dlbSub*-1;
       dlbValIva=dlbValIva*-1;
       dlbTot=dlbTot*-1;

//       if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
//       stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBod+" AS COBOD, "+
//       objInvItm.getIntDatoValidado(tblDat.getValueAt(intNumFil, INT_TBL_CODITM))+" AS COITM, "+dblCan+" AS NDCAN ");
//       intConStbBod=1;


 /*************************************************************************/

      intCodLocFacVen=intCodLoc;
      intCodTipDocFacVen=intCodTipDoc;
      intCodDocFacVen=intCodDoc;

      if(generaVenCab(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1, dlbSub, dlbValIva, dlbTot, strEstConf , intCodBodDes, blnReaTrans  )){
       if(generaVenDet(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBod ,intNumFil,  dblCan, intCodItm, -1 , intDesVta, dblPreVta, strMerIngEgrFisBod )){
        if(actualizaStock(conn, null, intCodEmp, intCodBod , dblCan, -1, intCodItm, strMerIngEgrFisBod, "E" )){
           blnRes=true;
      }}}
      objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}


private boolean generaVenDet(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intNumFil, double dblCan, int intCodItm, int intTipMov , double intDesVta, double dblPreVta, String strMerIngEgrFisBod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  String strPreCos="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       //strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_PREVTA)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_PREVTA).toString()));
       //double dblPreVta = objUti.redondear(strPreCos, 2);
       strPreCos = ((tblDat.getValueAt(intNumFil, INT_TBL_COSUNI)==null)?"0":(tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_COSUNI).toString()));
       double dblCosUni = objUti.redondear(strPreCos, 2);

       double dblValDes = ((dblCan * dblPreVta)==0)?0:((dblCan * dblPreVta) * (intDesVta / 100));
       double dblTotal = (dblCan * dblPreVta)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal,2);

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+(intNumFil+1)+", " +
        " '"+((tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString()))+"' "+
        " ,'"+((tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_TXCODAL).toString()))+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_TXNOMITM)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_TXNOMITM).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_TXNOMITM).toString()))+"' "+
        ", '"+((tblDat.getValueAt(intNumFil, INT_TBL_UNIMED)==null)?"":(tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString().equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_UNIMED).toString()))+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblCan*dblCosUni)+","+dblPreVta+", "+intDesVta+", 'S' "+
        " ,"+(dblCan*dblCosUni)+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+" ,'"+strNomBodIng+"' ) ";
        stmLoc.executeUpdate(strSql);
        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpEnvMer;
        stmLoc.executeUpdate(strSql);

//         if(intDatVen==1) stbDatVen.append(" UNION ALL ");
//          stbDatVen.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//         intDatVen=1;


      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



private boolean generaVenCab(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot, String strEstConf, int intCodBodDes, boolean blnReaTrans  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDoc=0;
  String strCui="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

//      if(CONN_GLO_REM!=null) {
//            intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(CONN_GLO_REM, intCodEmp);
//            intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(CONN_GLO_REM);
//      }else{
//            intSecEmp = objUltDocPrint.getNumeroOrdenEmpresa(CONN_GLO, intCodEmp);
//            intSecGrp = objUltDocPrint.getNumeroOrdenGrupo(CONN_GLO);
//      }


      java.util.Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

     if(TipMov==1){
       if(intCodEmpEnvMer==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmp==1){
//              if(intCodTipDoc==126){
//                  strCui="MANTA ";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
//              }else if(intCodTipDoc==166){
//                  strCui="SANTO DOMINGO";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
//              }
//              else{ strCui="QUITO ";
//                 strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603"; }
//          }
//          if(intCodEmp==4){
//              if(intCodTipDoc==126){
//                  strCui="MANTA ";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
//              } else if(intCodTipDoc==166){
//                  strCui="SANTO DOMINGO";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
//              }
//              else{ strCui="QUITO ";
//              strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";  }
//       }}
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
//          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
//        }
    }
    if(TipMov==2){
       if(intCodEmpEnvMer==1){
          if(intCodEmpCom==2){
              if(intCodTipDoc==130){
                  strCui="MANTA";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
                  " WHERE a.co_emp="+intCodEmpEnvMer+" AND a.co_cli=603";

              }else if(intCodTipDoc==165){
                  strCui="SANTO DOMINGO";
                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
                  " WHERE a.co_emp="+intCodEmpEnvMer+" AND a.co_cli=603";
              }
              else{
                  strCui="QUITO ";
                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpEnvMer+" AND co_cli=603";
              }

          }
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmpEnvMer+" AND co_cli=1039";
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=2854";
//          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=789";
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=3117";
//          if(intCodEmpCom==2){
//
//              if(intCodTipDoc==130){
//                  strCui="MANTA";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
//                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";
//
//              }else if(intCodTipDoc==165){
//                  strCui="SANTO DOMINGO";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
//                  " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_cli=498";
//              }else{
//                  strCui="QUITO ";
//                  strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=498";
//              }
//
//
//          }
//
//       }
    }
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDoc=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

//          if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//          stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//          intDocRelEmp=1;


     if(intDatDocRel==1)  stbDatDocRel.append(" UNION ALL ");
     stbDatDocRel.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC, "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
     intDatDocRel=1;



          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot, " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, co_ptodes, st_docGenDevMerMalEst, st_creguirem ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"'," +
          "'"+strCui+"','', "+intNumDoc+" , 0,"+
          " '' ,'RELIZADO AUTOMATICO' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', 45 "+
          " ,'S', 'S', '"+strEstConf+"', "+(intCodBodDes==0?null:String.valueOf(intCodBodDes))+", 'S', '"+(blnReaTrans?"S":"N")+"' )";
          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDoc+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);

      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}




private boolean actualizaStock(java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodBod,  double dblCan, int intTipMov, int intCodItm, String strMerIngEgr, String str_MerIEFisBod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  int intTipCli=3;
  double dlbCanMov=0.00;
  try{
      stmLoc=conn.createStatement();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

      dlbCanMov=dblCan;

      objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, intTipMov, "N", strMerIngEgr, str_MerIEFisBod, 1);

      if(!objInvItm.ejecutaActStock(conn, intTipCli))
          return false;

       if(connRemota!=null){
            //System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
            return false;
       }
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}




private boolean _RealizarDevoluciones(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodLocDevCom , int intCodLocDevVen, int intCodTipDocDevCom, int intCodTipDocDevVen, int intCodBodDevCom, double dlbStkBobDevCom,  String strCodItm, String strNomBodSal, boolean blnReaTrans, String strCodItmmae ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 double dlbCanDev=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

    strSql="SELECT * FROM ( " +
    " SELECT * FROM ( "+stbDatDevCom.toString()+" ) AS x "+
    " LEFT JOIN ( "+stbDatDevVen.toString()+" ) AS x1 ON(x1.regsec1=x.regsec) " +
    " ) AS x  WHERE  saldo1=saldo ";

    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){

       if(Glo_dblCanFalDevCom > 0 ){
        if( dlbStkBobDevCom > 0 ){

         if(rstLoc.getDouble("saldo") >= Glo_dblCanFalDevCom ) {

            if( dlbStkBobDevCom <= Glo_dblCanFalDevCom ) dlbCanDev=dlbStkBobDevCom;
             else dlbCanDev = Glo_dblCanFalDevCom;

            Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
         }else{

             if( dlbStkBobDevCom <= rstLoc.getDouble("saldo") )  dlbCanDev=dlbStkBobDevCom;
             else dlbCanDev = rstLoc.getDouble("saldo");

            Glo_dblCanFalDevCom=Glo_dblCanFalDevCom-dlbCanDev;
            dlbStkBobDevCom=dlbStkBobDevCom-dlbCanDev;
         }


      if( dlbCanDev > 0 ){

       stbDevTemp=new StringBuffer();
       stbDevInvTemp=new StringBuffer();

        intCodDocDevCom=0;
        intCodDocDevven=0;

         if(generaDevCompra(conn, strMerIngEgrFisBod,  rstLoc.getInt("co_emp"),  intCodLocDevCom, rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"),  rstLoc.getInt("co_reg")
            , rstLoc.getInt("ne_numdoc"), intCodTipDocDevCom,  rstLoc.getInt("co_bod"), rstLoc.getString("tx_codalt"), rstLoc.getString("tx_codalt2"), rstLoc.getString("tx_nomitm"), rstLoc.getString("tx_unimed"),     dlbCanDev , rstLoc.getDouble("nd_pordes"),  rstLoc.getInt("co_itm"),  rstLoc.getDouble("nd_cosuni"),  rstLoc.getDouble("nd_can") , strCodItm, rstLoc.getInt("co_loc") , intCodBodDevCom , blnReaTrans )){

          if(generaDevVenta(conn, strMerIngEgrFisBod,  rstLoc.getInt("co_emp"),  rstLoc.getInt("co_emp1"),  intCodLocDevVen, rstLoc.getInt("co_tipdoc1"), rstLoc.getInt("co_doc1"),  rstLoc.getInt("co_reg1")
            , rstLoc.getInt("ne_numdoc1"), intCodTipDocDevVen,  rstLoc.getInt("co_bod1"), rstLoc.getString("tx_codalt1"), rstLoc.getString("tx_codalt21"), rstLoc.getString("tx_nomitm1"), rstLoc.getString("tx_unimed1"),   dlbCanDev  , rstLoc.getDouble("nd_pordes1"),  rstLoc.getInt("co_itm1"),  rstLoc.getDouble("nd_preuni1"), rstLoc.getDouble("nd_can"), intCodBodDevCom , rstLoc.getInt("co_loc1"), intCodBodGrpPri, strNomBodSal, blnReaTrans )){


            if(_generaTransferenciaBodFaltante(conn, rstLoc.getInt("co_emp1"), intCodLocDevVen , dlbCanDev, strCodItmmae, intCodBodGrpPri,  intCodBodGrpFal,  blnReaTrans )){


             if(ingresarEnTblRelacional(conn, rstLoc.getInt("co_emp"),intCodLocDevCom, intCodTipDocDevCom, intCodDocDevCom, 1 , rstLoc.getInt("co_emp1"), intCodLocDevVen
                , intCodTipDocDevVen, intCodDocDevven, 1 ) ) {

             blnRes=true;
             Glo_dblCanFalDevVen = Glo_dblCanFalDevCom;

             }
         } } }

        stbDevTemp=null;
        stbDevInvTemp=null;

      }
     }}
    }
    rstLoc.close();
    rstLoc=null;


    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}




private boolean ingresarEnTblRelacional(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg
        , int intCodEmp1, int intCodLoc1, int intCodTipDoc1, int intCodDoc1, int intCodReg1  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql = "INSERT INTO tbr_detmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, st_reg, co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep  ) " +
      " VALUES( "+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", "+intCodReg+", 'A' " +
      " ,"+intCodEmp1+", "+intCodLoc1+", "+intCodTipDoc1+", "+intCodDoc1+", "+intCodReg1+", 'P' ) ";
      stmLoc.executeUpdate(strSql);

    stmLoc.close();
    stmLoc=null;
    blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}






private boolean generaDevVenta(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmpRel, int intCodEmp, int intCodLoc, int intCodTipDocOc, int intCodDocOc, int intCodRegOc
        , int intNumDoc, int intCodTipDoc, int intCodBod,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed,  double dblCan, double dblPorDes, int intCodItm, double dblCosUni, double dblCanFac, int intCodBodDevCom, int intCodLocDev, int intCodBodPredeterminada, String strNomBodSal, boolean blnReaTrans ){
   boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strConfDevVen="F";
  int intCodDoc=0;
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;
      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;
      objInvItm.limpiarObjeto();


       intCodDocDevven=intCodDoc;


       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
       double  dlbSub =  objUti.redondear(dblTotal, objParSis.getDecimalesBaseDatos() );
       double dlbValIva=  dlbSub * (bldivaEmp/100);
       double dlbTot=  dlbSub + dlbValIva;

       Glo_dblTotDevVen += dlbSub;


       if(strMerIngEgrFisBod.equals("S")) strConfDevVen="P";

       if(blnReaTrans) strConfDevVen="C";

      if(generaDevVenCab(conn, intNumDoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmpRel, 2, dlbSub, dlbValIva, dlbTot, intCodTipDocOc, intCodDocOc, intCodLocDev , strConfDevVen, 0 )){
       if(generaDevVenDet(conn, strMerIngEgrFisBod, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodPredeterminada, intCodTipDocOc, intCodDocOc, intCodRegOc, strCodAlt, strCodAlt2, strNomItm, strUniMed, dblCan, intCodItm, 1 ,  intCodEmpRel, dblCosUni, dblPorDes, dblCanFac, intCodLocDev, strNomBodSal, blnReaTrans )){
        if(actualizaStockDev(conn, null, intCodEmp, intCodBodPredeterminada , dblCan, 1, intCodItm, strMerIngEgrFisBod  ,"I" )){

            blnRes=true;
      }}}
    objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



private boolean generaDevCompra(java.sql.Connection conn, String strMerIngEgrFisBod,  int intCodEmp, int intCodLoc, int intCodTipDocOc, int intCodDocOc, int intCodRegOc
        , int intNumDoc, int intCodTipDoc, int intCodBod,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed,  double dblCan, double dblPorDes, int intCodItm, double dblCosUni, double dblCanOc, String strCodItm, int intCodLocDevCom, int intCodBodDevCom, boolean blnReaTrans  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  int intCodDoc=0;
  String strEstGuiRem="N";
   try{
    if(conn!=null){
      stmLoc=conn.createStatement();


      strSql="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc FROM tbm_cabMovInv WHERE " +
      " co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc;

      rstLoc = stmLoc.executeQuery(strSql);
      if(rstLoc.next()) intCodDoc=rstLoc.getInt("co_doc");
      rstLoc.close();
      rstLoc=null;

      intCodDocDevCom=intCodDoc;

      objInvItm.limpiarObjeto();

      double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
      double dblTotal = (dblCan * dblCosUni)- dblValDes;
      double dlbSub =  objUti.redondear(dblTotal, objParSis.getDecimalesBaseDatos() );
      double dlbValIva=  dlbSub * (bldivaEmp/100);
      double dlbTot=  dlbSub + dlbValIva;

      Glo_dblTotDevCom += dlbSub;

      dlbSub=dlbSub*-1;
      dlbValIva=dlbValIva*-1;
      dlbTot=dlbTot*-1;


//       if(intConStbBod==1)  stbLisBodItm.append(" UNION ALL ");
//       stbLisBodItm.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodBodDevCom+" AS COBOD, "+
//       strCodItm+" AS COITM, "+dblCan+" AS NDCAN ");
//       intConStbBod=1;


        int intCodBodDes =  _getCodigoBodDes(conn, 1, intCodBodGrpPri, intCodEmp );
        if(intCodBodDes== -1){
           intCodBodDes=0;
        }

      if(strMerIngEgrFisBod.equals("N")) strEstGuiRem="S";

      if(blnReaTrans) strEstGuiRem="S";

      if(generaDevComCab(conn, intNumDoc, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp, 1,  dlbSub, dlbValIva, dlbTot, strEstGuiRem, intCodBodDes , blnReaTrans )){
       if(generaDevComDet(conn, strMerIngEgrFisBod, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodBodDevCom, intCodTipDocOc, intCodDocOc, intCodRegOc, strCodAlt, strCodAlt2, strNomItm, strUniMed, dblCan, intCodItm, -1 ,  objParSis.getCodigoEmpresa(), dblCosUni, dblPorDes, dblCanOc, intCodLocDevCom , strEstGuiRem  )){
        if(actualizaStockDev(conn, null, intCodEmp, intCodBodDevCom , dblCan, -1, intCodItm, strMerIngEgrFisBod, "E" )){

            blnRes=true;
      }}}



     objInvItm.limpiarObjeto();
    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



// nd_candev
private boolean generaDevComDet(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanOc , int intCodLocDevCom, String strEstGuiRem  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal, objParSis.getDecimalesBaseDatos() );

        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_canorg, nd_candev, tx_nombodorgdes ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+(dblTotal*intTipMov)+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+(dblTotal*intTipMov)+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+", "+dblCanOc+", "+(dblCan*intTipMov)+", '"+strNomBodIng+"' ) ";
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salcom=nd_salcom-"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDevCom+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc;
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );


//         if(intDatDevVenCom==1) stbDatDevVenCom.append(" UNION ALL ");
//          stbDatDevVenCom.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//         intDatDevVenCom=1;


      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}


private boolean generaDevComCab(java.sql.Connection conn, int intNumDoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot , String strEstGuiRem, int intCodBodDes, boolean blnReaTrans  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDocDev=0;
  String strCui="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

      java.util.Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

//     if(TipMov==1){
//
//       if(objZafParSis.getCodigoEmpresa()==1){
//          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";
//          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";
//       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603";
//          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";
//          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";
//        }
//    }

   if(TipMov==1){
       if(1==1){
          if(intCodEmp==2) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";  }
          if(intCodEmp==4) { strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";  }
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmp==1){
//              if(intCodTipDoc==138){
//                  strCui="MANTA ";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
//              }else if(intCodTipDoc==167){
//                  strCui="SANTO DOMINGO";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=603";
//              }
//              else{ strCui="QUITO ";
//                 strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603"; }
//          }
//          if(intCodEmp==4){
//              if(intCodTipDoc==138){
//                  strCui="MANTA ";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=2 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
//              } else if(intCodTipDoc==167){
//                  strCui="SANTO DOMINGO";
//                  strSql="select a.co_cli, a.tx_nom, a.tx_ide, a1.tx_dir, a1.tx_tel1 as tx_tel from tbm_cli as a " +
//                  " left join tbm_dircli as a1 on (a1.co_emp=a.co_emp and a1.co_cli=a.co_cli and a1.co_reg=3 ) " +
//                  " WHERE a.co_emp="+intCodEmp+" AND a.co_cli=498";
//              }
//              else{ strCui="QUITO ";
//              strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";  }
//       }}
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmp==2){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";  }
//          if(intCodEmp==1){ strCui="GUAYAQUIL "; strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";  }
//        }
    }

    if(TipMov==2){
       if(1==1){
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp=1 AND co_cli=603";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp=1 AND co_cli=1039";
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=2854";
//          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=789";
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=3117";
//          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=498";
//       }
    }

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

//         if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
//             stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//          intDocRelEmpTemp=1;
//
//          if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//                stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//           intDocRelEmp=1;



     if(intDatDocRel==1)  stbDatDocRel.append(" UNION ALL ");
     stbDatDocRel.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC, "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
     intDatDocRel=1;


          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv, st_creguirem, co_ptodes, st_docGenDevMerMalEst  ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"', "+
          " '"+strCui+"','',"+intNumDocDev+" , "+intNumDoc+","+
          "  '' ,'' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+(blnReaTrans?"C":"P")+"', '"+strEstGuiRem+"' ,   "+(intCodBodDes==0?null:String.valueOf(intCodBodDes))+" , 'S'  )";
          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);
          stbDevTemp.append( strSql +" ; " );
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}


private int _getCodigoBodDes(java.sql.Connection conn, int CodEmpCom, int intCodBodCom, int intCodEmpVen){
 int intCodBodDes=-1;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="select  a1.co_bod  from tbr_bodEmpBodGrp as a " +
     " inner join tbr_bodEmpBodGrp as a1 on (a1.co_empgrp=a.co_empgrp and a1.co_bodgrp=a.co_bodgrp ) " +
     " where a.co_emp="+CodEmpCom+" and a.co_bod="+intCodBodCom+"   and a1.co_emp="+intCodEmpVen+" ";
     System.out.println(" Bodega Destino "+strSql );
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
         intCodBodDes=rstLoc.getInt("co_bod");
     }
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException Evt){ intCodBodDes=-1;    }
    catch(Exception Evt)   { intCodBodDes=-1;   }
 return intCodBodDes;
}




private boolean actualizaStockDev(java.sql.Connection conn, java.sql.Connection connRemota, int intCodEmp, int intCodBod,  double dblCan, int intTipMov, int intCodItm, String strMerIngEgr, String str_MerIEFisBod ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  int intTipCli=3;
  double dlbCanMov=0.00;
  try{
      stmLoc=conn.createStatement();

      objInvItm.limpiarObjeto();
      objInvItm.inicializaObjeto();

      dlbCanMov=dblCan;

      objInvItm.generaQueryStock(intCodEmp, intCodItm, intCodBod, dlbCanMov, intTipMov, "N", strMerIngEgr, str_MerIEFisBod, 1);

      stbDevInvTemp.append( objInvItm.getQueryEjecutaActStock() +" ; " );


      if(!objInvItm.ejecutaActStock(conn, intTipCli))
          return false;

       if(connRemota!=null){
            //System.out.println("remoto.."+connRemota );

           if(!objInvItm.ejecutaActStock(connRemota, intTipCli ))
             return false;
           if(!objInvItm.ejecutaVerificacionStock(connRemota, intTipCli))
             return false;
       }else{
         if(!objInvItm.ejecutaVerificacionStock(conn, intTipCli ))
            return false;
       }
      objInvItm.limpiarObjeto();
      stmLoc.close();
      stmLoc=null;
      blnRes=true;
   }catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



private boolean generaDevVenDet(java.sql.Connection conn, String strMerIngEgrFisBod, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodBod, int intCodTipDocOc, int intCodDocOc, int intCodRegOc,  String strCodAlt, String strCodAlt2, String strNomItm, String strUniMed, double dblCan, int intCodItm, int intTipMov, int intCodEmpCom, double dblCosUni, double dblPorDes, double dblCanFac, int intCodLocDev, String strNomBodSal, boolean blnReaTrans  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

       double dblValDes= ((dblCan * dblCosUni)==0)?0:((dblCan * dblCosUni) * (dblPorDes / 100));
       double dblTotal = (dblCan * dblCosUni)- dblValDes;
              dblTotal =  objUti.redondear(dblTotal, objParSis.getDecimalesBaseDatos() );


        strSql="INSERT INTO  tbm_detMovInv(co_emp, co_loc, co_tipdoc , co_doc, co_reg, "+ //CAMPOS PrimayKey
        " tx_codAlt, tx_codAlt2, co_itm, co_itmact,  tx_nomItm, tx_unimed, " +//<==Campos que aparecen en la parte superior del 1er Tab
        " co_bod, nd_can, nd_tot, nd_cosUnigrp,nd_costot, nd_preUni, nd_porDes, st_ivaCom " +//<==Campos que aparecen en la parte inferior del 1er Tab
        ",nd_costotgrp , st_regrep, st_meringegrfisbod , nd_cancon, nd_cosUni, nd_candev, nd_canorg, tx_nombodorgdes, nd_cannunrec ) " +
        " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 1, " +
        " '"+strCodAlt+"' "+
        " ,'"+strCodAlt2+"' "+
        ", "+intCodItm+" "+
        ", "+intCodItm+" "+
        ", '"+strNomItm+"' "+
        ", '"+strUniMed+"' "+
        ", "+intCodBod+", "+(dblCan*intTipMov)+", "+dblTotal+","+dblCosUni+", "+dblTotal+","+dblCosUni+", "+dblPorDes+", 'S' "+
        " ,"+dblTotal+", 'I' , '"+strMerIngEgrFisBod+"', 0 ,"+dblCosUni+", "+(dblCan*intTipMov)+", "+dblCanFac+", '"+strNomBodSal+"' "
        + " , "+(blnReaTrans?dblCan:0)+" ) ";
        //System.out.println("---->   "+strSql );
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_invmovempgrp SET st_regrep='M', nd_salven=nd_salven+"+dblCan+" WHERE co_emp="+intCodEmp+" AND " +
        " co_itm="+intCodItm+" AND co_emprel="+intCodEmpCom;
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );

        strSql ="  UPDATE tbm_detMovInv SET nd_candev=(case when nd_candev is null then 0 else nd_candev end) + "+dblCan+" " +
        " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLocDev+" AND co_tipdoc="+intCodTipDocOc+" AND co_doc="+intCodDocOc+" "+
        " AND co_reg="+intCodRegOc;
        stmLoc.executeUpdate(strSql);
        stbDevTemp.append( strSql +" ; " );


//         if(intDatDevVenCom==1) stbDatDevVenCom.append(" UNION ALL ");
//          stbDatDevVenCom.append("SELECT "+intCodEmp+" as coemp, "+intCodLoc+" as coloc, "+intCodTipDoc+"  as cotipdoc, "+intCodDoc+" AS codoc, "+intCodBod+" as cobod  ");
//         intDatDevVenCom=1;


      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}



private boolean generaDevVenCab(java.sql.Connection conn, int intNumDoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpCom,  int TipMov, double dlbSub, double dlbValIva, double dlbTot ,int intCodTipDocOc, int intCodDocOc, int intCodLocDev, String strConfDevVen, int intCodBodDes  ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  String strFecSistema="";
  int intSecEmp=0;
  int intSecGrp=0;
  int intNumDocDev=0;
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();


      java.util.Date datFecAux =objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
      strFecSistema=objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());

     if(TipMov==1){

       if(1==1){
          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=2854";
          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=3117";
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=603";
//          if(intCodEmp==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=498";
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmp==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=789";
//          if(intCodEmp==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+intCodEmp+" AND co_cli=1039";
//        }
    }
    if(TipMov==2){
       if(1==1){
          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp=1 AND co_cli=603";
          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp=1 AND co_cli=1039";
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=2854";
//          if(intCodEmpCom==4) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=789";
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmpCom==1) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=3117";
//          if(intCodEmpCom==2) strSql="SELECT * FROM tbm_cli WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cli=498";
//       }
    }

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

         intNumDocDev=getUltNumDoc(conn, intCodEmp, intCodLoc, intCodTipDoc);

//         if(intDocRelEmpTemp==1) stbDocRelEmpTemp.append(" UNION ALL ");
//           stbDocRelEmpTemp.append( " SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//        intDocRelEmpTemp=1;
//
//
//          if(intDocRelEmp==1) stbDocRelEmp.append(" UNION ALL ");
//                stbDocRelEmp.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC , "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC" );
//           intDocRelEmp=1;


     if(intDatDocRel==1)  stbDatDocRel.append(" UNION ALL ");
     stbDatDocRel.append(" SELECT "+intCodEmp+" AS COEMP, "+intCodLoc+" AS COLOC, "+intCodTipDoc+" AS COTIPDOC, "+intCodDoc+" AS CODOC ");
     intDatDocRel=1;


          strSql="INSERT INTO tbm_cabMovInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, co_cli, co_com, tx_ate, "+
          " tx_nomCli, tx_dirCli,  tx_ruc, tx_telCli, tx_ciuCli, tx_nomven, ne_numDoc, ne_numCot,  " +
          " tx_obs1, tx_obs2, nd_sub, nd_porIva, nd_tot,nd_valiva, co_forPag, tx_desforpag, fe_ing, co_usrIng, fe_ultMod, " +
          " co_usrMod,co_forret,tx_vehret,tx_choret,st_reg, ne_secgrp,ne_secemp,tx_numped , st_regrep , st_tipdev, st_imp , co_mnu" +
          " ,st_coninvtraaut, st_excDocConVenCon, st_coninv , co_ptodes, st_docGenDevMerMalEst ) " +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+
          intCodTipDoc+", "+intCodDoc+", '"+datFecAux+"', "+rstLoc.getString("co_cli")+" ,null,'','"+
          rstLoc.getString("tx_nom")+"','"+rstLoc.getString("tx_dir")+"','"+rstLoc.getString("tx_ide")+ "','"+rstLoc.getString("tx_tel")+"','"+
          " ','',"+intNumDocDev+" , "+intNumDoc+","+
          "  '' ,'' , "+dlbSub+" ,0 , "+dlbTot+", "+dlbValIva+" , 1 ,'Contado', '"+strFecSistema+"', "+
          objParSis.getCodigoUsuario()+", '"+strFecSistema+"', "+objParSis.getCodigoUsuario()+" , null, null," +
          " null, 'A', "+intSecGrp+", "+intSecEmp+", '', 'I' ,'C' , 'S', null "+
          " ,'S', 'S', '"+strConfDevVen+"'  ,   "+(intCodBodDes==0?null:String.valueOf(intCodBodDes))+" ,'S'  )";

          strSql +=" ; INSERT INTO tbr_cabmovinv( co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, co_docrel, st_regrep, co_emprel )" +
          " VALUES("+intCodEmp+", "+intCodLoc+", "+intCodTipDoc+", "+intCodDoc+", 'A', "+intCodLocDev+", "+intCodTipDocOc+", "+intCodDocOc+", 'I', "+intCodEmp+" ) ";

          strSql +=" ; UPDATE tbm_cabtipdoc SET ne_ultdoc="+intNumDocDev+" WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc;
          stmLoc.executeUpdate(strSql);
          stbDevTemp.append( strSql +" ; " );
      }
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
      blnRes=true;

  }}catch(java.sql.SQLException Evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
    catch(Exception Evt)   { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);     }
 return blnRes;
}




private int getUltNumDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc ){
  int intUltNumDoc=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
    if(conn!=null){
      stmLoc=conn.createStatement();

     strSql="SELECT CASE WHEN (ne_ultdoc+1) IS NULL THEN 1 ELSE (ne_ultdoc+1) END AS numdoc FROM tbm_cabtipdoc WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc;
     rstLoc = stmLoc.executeQuery(strSql);
     if(rstLoc.next()) intUltNumDoc = rstLoc.getInt("numdoc");

    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;

 }}catch(java.sql.SQLException ex) {  objUti.mostrarMsgErr_F1(this, ex);   }
   catch(Exception e) {  objUti.mostrarMsgErr_F1(this, e);  }
 return intUltNumDoc;
}







private boolean _getMovDevCompra( java.sql.Connection conn,  int intCodEmpDev, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodItmmae,  double dblCanFal, int intCodBodDevCom, double dlbStkBobDevCom, int intTipDocDevVen, String strCodItm  ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocCom=2;
 int intCodCli=0;
 int intRegSec=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

      if(intCodEmp==1){
          if(intCodEmpDev==2) intCodCli=603;
          if(intCodEmpDev==4) intCodCli=1039;
       }
       if(intCodEmp==2){
          if(intCodEmpDev==1) intCodCli=2854;
          if(intCodEmpDev==4) intCodCli=789;
       }
       if(intCodEmp==4){
          if(intCodEmpDev==2) intCodCli=498;
          if(intCodEmpDev==1) intCodCli=3117;
       }



   String strAuxLoc="";
//   if(intCodEmp==4)
//       strAuxLoc="";
//   else
//     strAuxLoc=" AND a.co_loc="+intCodLoc+" ";


    strSql="select * FROM ( " +
    " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,  " +
    " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, a1.nd_can, a1.nd_preuni,  "+
    " a1.co_bod, ( abs(a1.nd_can) - abs(case when a1.nd_candev is null then 0 else a1.nd_candev end )) as saldo " +
    " FROM tbm_cabmovinv  AS a " +
    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
    " inner join tbm_equinv as a4 on (a4.co_emp= a1.co_emp and a4.co_itm=a1.co_itm ) "+
    " WHERE a.co_emp="+intCodEmp+"   "+strAuxLoc+" "+
    " AND a.co_tipdoc="+intTipDocCom+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2009-01-01' and a.st_reg not in ('E','I') and a4.co_itmmae="+intCodItmmae+"   " +
    " ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";

    System.out.println("_getMovDevCompra --> "+strSql);

    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
     intRegSec++;
     if(intDatDevCom==1) stbDatDevCom.append(" UNION ALL ");
     stbDatDevCom.append(" SELECT "+intRegSec+" AS regsec,  "+rstLoc.getInt("co_emp")+" AS co_emp, "+rstLoc.getInt("co_loc")+" AS co_loc, " +
     " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc, "+rstLoc.getInt("co_doc")+" AS co_doc, "+rstLoc.getInt("co_reg")+" AS co_reg," +
     " "+rstLoc.getInt("co_bod")+" AS co_bod, "+rstLoc.getDouble("saldo")+" AS saldo, " +
     " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt2," +
     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed, "+rstLoc.getString("nd_pordes")+" AS nd_pordes, " +
     " "+rstLoc.getString("co_itm")+" AS co_itm, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni, "+rstLoc.getString("nd_can")+" AS nd_can,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni  ");

     intDatDevCom=1;

    }
    rstLoc.close();
    rstLoc=null;



    stmLoc.close();
    stmLoc=null;

  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}


private boolean _getMovDevVenta(java.sql.Connection conn, int intCodEmp,  int intCodLoc, int  intCodTipDoc, int intCodItmmae, int intCodEmpRel, double dblCanFal, double dlbStkBobDevCom, int intCodBodDevCom ){
 boolean blnRes=false;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 int intTipDocVen=1;
 int intCodCli=0;
 int intRegSec=0;
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();

      if(1==1){
          if(intCodEmpRel==2) intCodCli=603;
          if(intCodEmpRel==4) intCodCli=1039;
       }
//       if(objZafParSis.getCodigoEmpresa()==2){
//          if(intCodEmpRel==1) intCodCli=2854;
//          if(intCodEmpRel==4) intCodCli=789;
//       }
//       if(objZafParSis.getCodigoEmpresa()==4){
//          if(intCodEmpRel==2) intCodCli=498;
//          if(intCodEmpRel==1) intCodCli=3117;
//       }
//

   String strAuxLoc="";
//   if(intCodEmp==4)
//       strAuxLoc="";
//   else
//     strAuxLoc=" AND a.co_loc="+intCodLoc+" ";

    strSql="select * FROM ( " +
    " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, " +
    " a.ne_numdoc,  a1.tx_codalt, a1.tx_codalt2, a1.tx_nomitm, a1.tx_unimed, a1.nd_pordes, a1.co_itm, a1.nd_cosuni, abs(a1.nd_can) as nd_can, a1.nd_preuni,   "+
    " a1.co_bod, ( abs(a1.nd_can) - abs( case when a1.nd_candev is null then 0 else a1.nd_candev end ) ) as saldo " +
    " FROM tbm_cabmovinv  AS a " +
    " INNER JOIN tbm_detmovinv AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc) " +
    " inner join tbm_equinv as a4 on (a4.co_emp= a1.co_emp and a4.co_itm=a1.co_itm ) " +
    " WHERE a.co_emp="+intCodEmp+"    "+strAuxLoc+" "+
    " AND a.co_tipdoc="+intTipDocVen+" AND a.co_cli="+intCodCli+" " +
    " AND a.fe_doc>='2009-01-01' and a.st_reg not in ('E','I') and a4.co_itmmae="+intCodItmmae+"  ORDER BY a.fe_Doc, a.co_doc " +
    " ) as x  where saldo <> 0 ";

   // System.out.println("Paso 2 verificaMovDevVen : "+ strSql );

    System.out.println("_getMovDevVenta --> "+strSql);


    rstLoc=stmLoc.executeQuery(strSql);
    while(rstLoc.next()){
     intRegSec++;
     if(intDatDevVen==1) stbDatDevVen.append(" UNION ALL ");
     stbDatDevVen.append(" SELECT "+intRegSec+" AS regsec1,  "+rstLoc.getInt("co_emp")+" AS co_emp1, "+rstLoc.getInt("co_loc")+" AS co_loc1, " +
     " "+rstLoc.getInt("co_tipdoc")+" AS co_tipdoc1, "+rstLoc.getInt("co_doc")+" AS co_doc1, "+rstLoc.getInt("co_reg")+" AS co_reg1," +
     " "+rstLoc.getInt("co_bod")+" AS co_bod1, "+rstLoc.getDouble("saldo")+" AS saldo1, " +
     " "+rstLoc.getInt("ne_numdoc")+" AS ne_numdoc1, '"+rstLoc.getString("tx_codalt")+"' AS tx_codalt1, '"+rstLoc.getString("tx_codalt2")+"' AS tx_codalt21," +
     " '"+rstLoc.getString("tx_nomitm")+"' AS tx_nomitm1, '"+rstLoc.getString("tx_unimed")+"' AS tx_unimed1, "+rstLoc.getString("nd_pordes")+" AS nd_pordes1, " +
     " "+rstLoc.getString("co_itm")+" AS co_itm1, "+rstLoc.getString("nd_cosuni")+" AS nd_cosuni1, "+rstLoc.getString("nd_can")+" AS nd_can1,  "+rstLoc.getString("nd_preuni")+" AS nd_preuni1   ");

     intDatDevVen=1;

    }
    rstLoc.close();
    rstLoc=null;

    stmLoc.close();
    stmLoc=null;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer2;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JCheckBox chkMerAut;
    private javax.swing.JCheckBox chkMerDen;
    private javax.swing.JCheckBox chkMerPen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panRepot;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

 /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
              
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
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

                case INT_TBL_LIN:
                    strMsg="";
                    break;

               case INT_TBL_NOMBOD:
                    strMsg="Nombre de la bodega. ";
                    break;

                case INT_TBL_DESTIDC:
                    strMsg="Descripción corta del tipo documento";
                    break;
                case INT_TBL_DESTIDL:
                    strMsg="Descripción larga del tipo documento";
                    break;

                case INT_TBL_NUMDOC:
                    strMsg="Número de documento de confirmación";
                    break;

                    case INT_TBL_FECDOC:
                    strMsg="Fecha de documento de confirmación";
                    break;

                   case INT_TBL_BUT01:
                    strMsg="Permite ver el documento confirmado ";
                    break;

                      case INT_TBL_TXCODAL:
                    strMsg="Código alterno del item.  ";
                    break;

                      case INT_TBL_TXNOMITM:
                    strMsg="Nombre del item. ";
                    break;

                      case INT_TBL_CANTIDA:
                    strMsg="Cantidad de ingreso. ";
                    break;

                      case INT_TBL_CANMALEST:
                    strMsg="Cantidad en mal estado. ";
                    break;


                      case INT_TBL_CHKPRO:
                    strMsg="Autorizar cantidad en mal estado. ";
                    break;

                      case INT_TBL_CANAUT:
                    strMsg="Cantidad autorizada en mal estado. ";
                    break;

                      case INT_TBL_CANDEN:
                    strMsg="Cantidad denegada en mal estado. ";
                    break;


                   case INT_TBL_TXTOBS:
                    strMsg="Observación.  ";
                    break;

                    case INT_TBL_BUTOBS:
                    strMsg="Pantalla de Observación.  ";
                    break;

                 case INT_TBL_CANNUNREC:
                  strMsg="Cantidad nunca recibida. ";
                    break;


                      case INT_TBL_CHKPRONUNREC:
                    strMsg="Autorizar cantidad nunca recibida. ";
                    break;

                      case INT_TBL_CANNUNRECAUT:
                    strMsg="Cantidad autorizada nunca recibida.. ";
                    break;

                      case INT_TBL_CANNUNRECDEN:
                    strMsg="Cantidad denegada nunca recibida. ";
                    break;


                   case INT_TBL_TXTOBSNUNREC:
                    strMsg="Observación.  ";
                    break;

                    case INT_TBL_BUTOBSNUNREC:
                    strMsg="Pantalla de Observación.  ";
                    break;

               
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }



}
