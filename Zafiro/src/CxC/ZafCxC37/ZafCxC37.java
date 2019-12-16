/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC37.java
 *
 * Created on May 26, 2010, 12:12:42 PM
 */

package CxC.ZafCxC37;

import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector; /**/
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;

/**
 *
 * @author jayapata
 */
public class ZafCxC37 extends javax.swing.JInternalFrame
{
    Librerias.ZafParSis.ZafParSis objZafParSis;
    ZafUtil objUti; /**/
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod; /**/
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoTipDoc;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafThreadGUI objThrGUI;
    private ZafTblTot objTblTot;
    private ZafMouMotAda objMouMotAda;

    ZafVenCon objVenConTipdoc;

    static final int INT_TBL_LINEA=0;       // NUMERO DE LINEAS
    static final int INT_TBL_CHKSEL=1;      // SELECCION  DE FILA
    static final int INT_TBL_CODCLI=2;      // CODIGO DEL CLIENTE
    static final int INT_TBL_NOMCLI=3;      // NOMBRE DEL CLIENTE
    static final int INT_TBL_CODBAN=4;      // CODIGO DEL BANCO
    static final int INT_TBL_DSCBAN=5;      // DESCRIPCION CORTA DEL BANCO
    static final int INT_TBL_NOMBAN=6;      // NOMBRE DEL BANCO
    static final int INT_TBL_NUMCTA=7;      // NUMERO DE CUENTA DEL BANCO
    static final int INT_TBL_NUMCHQ=8;      // NUMERO DEL CHEQUE
    static final int INT_TBL_VALCHQ=9;      // VALOR DEL CHEQUE
    static final int INT_TBL_FECREC=10;     // FECHA DE VENCIMIENTO
    static final int INT_TBL_FECVEN=11;     // FECHA DE VENCIMIENTO
    static final int INT_TBL_BUTRECCHQ=12;  // BUTON PARA VER RECEPCION DE CHEQUE
    static final int INT_TBL_CHKASIFAC=13;  // SI YA ESTA ASIGNAGO TODAS LAS FACTURAS
    static final int INT_TBL_VALPENASI=14;  // VALOR PENDIEN DE ASIGNAR
    static final int INT_TBL_BUTFACASI=15;  // BUTON PARA VER FACTURAS ASIGNADAS
    static final int INT_TBL_CODTIPDOC=16;  // CODIGO TIPO DE DOCUMENTO
    static final int INT_TBL_DCOTIPDOC=17;  // DESCRIPCION CORTA DE TIPO DOCUMENTO
    static final int INT_TBL_BUTTIPDOC=18;  // BOTON DE BUSQUEDA DE  TIPO DOCUMENTO
    static final int INT_TBL_DLATIPDOC=19;  // DESCRIPCION LARGA TIPO DE DOCUMENTO
    static final int INT_TBL_FECASIBAN=20;  // FECHA DE ASIGNACION DE BANCO
    static final int INT_TBL_CHKDEPO=21;    // SI YA ESTA HECHO EL DEPOSITO
    static final int INT_TBL_BUTDEPO=22;    // BOTON PARA VER EL DEPOSITO
    static final int INT_TBL_ESTCHK=23;     // ESTADO DE SELECCION DE CHEQUES
    static final int INT_TBL_CODEMP=24;
    static final int INT_TBL_CODLOC=25;
    static final int INT_TBL_CODTID=26;
    static final int INT_TBL_CODDOC=27;
    static final int INT_TBL_CODREG=28;
    static final int INT_TBL_CODTIPDOCORI=29;

    Vector vecCab=new Vector(); 
    String strVersion=" v0.6";
    String strDesCodTipDoc="";
    String strDesLarTipDoc="";
    String strCodTipDoc="";
    String strSqlGlb="";

    javax.swing.JTextField txtCodTipDoc= new javax.swing.JTextField();
    private ZafPerUsr objPerUsr;

    /** Creates new form ZafCxC37 */
    public ZafCxC37(Librerias.ZafParSis.ZafParSis obj)
    {
        try
        {
            this.objZafParSis=(Librerias.ZafParSis.ZafParSis)obj.clone();
            initComponents();
            this.setTitle(objZafParSis.getNombreMenu()+" "+ strVersion );
            lblTit.setText(objZafParSis.getNombreMenu());
            objUti = new ZafUtil();
            objPerUsr=new ZafPerUsr(this.objZafParSis);
            if (!(objZafParSis.getCodigoUsuario()==1))
            {
                if (!objPerUsr.isOpcionEnabled(1760))
                    butCon.setVisible(false);
                if (!objPerUsr.isOpcionEnabled(1761))
                    butGua.setVisible(false);
                if (!objPerUsr.isOpcionEnabled(1762))
                    butCer.setVisible(false);
                if (!objPerUsr.isOpcionEnabled(2601))
                    butBanPre.setVisible(false);
            }
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panCabRecChq.add(txtFecDoc);
            txtFecDoc.setBounds(80, 4, 92, 20);
            txtFecDoc.setHoy();
            chk3_1.setEnabled(false);
            chk3_2.setEnabled(false);
            chk5_1.setEnabled(false);
            chk5_2.setEnabled(false);
            txtNumChq.setEditable(false);
        }
        catch (CloneNotSupportedException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
    * Carga ventanas de consulta y configuracion de la tabla
    */
    public void Configura_ventana_consulta()
    {
    configurarVenConTipDoc();
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
            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";
            if (objZafParSis.getCodigoUsuario()==1)
            {
                Str_Sql="Select distinct a.co_tipdoc,a.tx_descor,a.tx_deslar from tbr_tipdocprg as b " +
                " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)" +
                " where   b.co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                " b.co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                " b.co_mnu = " + objZafParSis.getCodigoMenu();
            }
            else
            {
                Str_Sql ="SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar  "+
                " FROM tbr_tipDocUsr AS a1 inner join  tbm_cabTipDoc AS a ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipdoc=a1.co_tipdoc)"+
                " WHERE "+
                "  a1.co_emp=" + objZafParSis.getCodigoEmpresa()+""+
                " AND a1.co_loc=" + objZafParSis.getCodigoLocal()+""+
                " AND a1.co_mnu=" + objZafParSis.getCodigoMenu()+""+
                " AND a1.co_usr=" + objZafParSis.getCodigoUsuario();
            }
            objVenConTipdoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
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

    private boolean configurarTabla()
    {
        boolean blnRes=false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CHKSEL,"");
            vecCab.add(INT_TBL_CODCLI,"Cód.Cli.");
            vecCab.add(INT_TBL_NOMCLI,"Cliente.");
            vecCab.add(INT_TBL_CODBAN,"Cód.Ban.");
            vecCab.add(INT_TBL_DSCBAN,"Banco.");
            vecCab.add(INT_TBL_NOMBAN,"Nom.Ban");
            vecCab.add(INT_TBL_NUMCTA,"Núm.Cta.");
            vecCab.add(INT_TBL_NUMCHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_VALCHQ,"Val.Chq.");
            vecCab.add(INT_TBL_FECREC,"Fec.Rec.");
            vecCab.add(INT_TBL_FECVEN,"Fec.Ven.");
            vecCab.add(INT_TBL_BUTRECCHQ,"");
            vecCab.add(INT_TBL_CHKASIFAC,"Asi.Fac.");
            vecCab.add(INT_TBL_VALPENASI,"Val.Pen.Asi.");
            vecCab.add(INT_TBL_BUTFACASI,"");
            vecCab.add(INT_TBL_CODTIPDOC,"");
            vecCab.add(INT_TBL_DCOTIPDOC,"Tip.Doc");
            vecCab.add(INT_TBL_BUTTIPDOC,"");
            vecCab.add(INT_TBL_DLATIPDOC,"Tipo de documento");
            vecCab.add(INT_TBL_FECASIBAN,"Fec.Asi.Ban.");
            vecCab.add(INT_TBL_CHKDEPO,"Depo.");
            vecCab.add(INT_TBL_BUTDEPO,"");
            vecCab.add(INT_TBL_ESTCHK,"");
            vecCab.add(INT_TBL_CODEMP,"");
            vecCab.add(INT_TBL_CODLOC,"");
            vecCab.add(INT_TBL_CODTID,"");
            vecCab.add(INT_TBL_CODDOC,"");
            vecCab.add(INT_TBL_CODREG,"");
            vecCab.add(INT_TBL_CODTIPDOCORI,"");
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  /**/
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  /**/
            objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VALPENASI, objTblMod.INT_COL_DBL, new Integer(0), null);
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CHKSEL);
            vecAux.add("" + INT_TBL_DCOTIPDOC);
            vecAux.add("" + INT_TBL_BUTTIPDOC);
            vecAux.add("" + INT_TBL_BUTRECCHQ);
            vecAux.add("" + INT_TBL_BUTDEPO);
            vecAux.add("" +INT_TBL_BUTFACASI);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VALCHQ).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_VALPENASI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DSCBAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMBAN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUMCTA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NUMCHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECREC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DCOTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUTTIPDOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DLATIPDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUTRECCHQ).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CHKASIFAC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_BUTFACASI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FECASIBAN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CHKDEPO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BUTDEPO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_VALPENASI).setPreferredWidth(60);
            //Aqui se agrega las columnas que van a ser ocultas.
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODBAN);
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_ESTCHK);
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_CODTID);
            arlColHid.add(""+INT_TBL_CODDOC);
            arlColHid.add(""+INT_TBL_CODREG);
            arlColHid.add(""+INT_TBL_CODTIPDOCORI);
            if (!(objZafParSis.getCodigoUsuario()==1))
            {
                if (!objPerUsr.isOpcionEnabled(2550))
                    arlColHid.add(""+INT_TBL_BUTRECCHQ);
                if  (!objPerUsr.isOpcionEnabled(2551))
                    arlColHid.add(""+INT_TBL_BUTDEPO);
            }
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKASIFAC).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHKDEPO).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intNumFil = tblDat.getSelectedRow();
                    String strAsiFac=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
                    String strEstSel=  (tblDat.getValueAt(intNumFil, INT_TBL_ESTCHK)==null?"N":(tblDat.getValueAt(intNumFil, INT_TBL_ESTCHK).equals("")?"N":tblDat.getValueAt(intNumFil, INT_TBL_ESTCHK).toString()));
                    if ((strAsiFac.equals("false")))
                    {
                        MensajeInf("NO TIENE ASIGNADO FACTURAS EN SU TOTALIDAD ");
                        tblDat.setValueAt( new Boolean(false),intNumFil, INT_TBL_CHKSEL);
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }
                    if ((strEstSel.equals("S")))
                        objTblCelEdiChk.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intNunFil = tblDat.getSelectedRow();
                    calculaTotMonChq(intNunFil);
                }
            });
            int intColCli[]=new int[3];
            intColCli[0]=1;
            intColCli[1]=2;
            intColCli[2]=3;
            int intColTblCli[]=new int[3];
            intColTblCli[0]=INT_TBL_CODTIPDOC;
            intColTblCli[1]=INT_TBL_DCOTIPDOC;
            intColTblCli[2]=INT_TBL_DLATIPDOC;
            objTblCelEdiTxtVcoTipDoc=new ZafTblCelEdiTxtVco(tblDat, objVenConTipdoc, intColCli, intColTblCli );
            tcmAux.getColumn(INT_TBL_DCOTIPDOC).setCellEditor(objTblCelEdiTxtVcoTipDoc);
            objTblCelEdiTxtVcoTipDoc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intNumFil = tblDat.getSelectedRow();
                    String strAsiFac=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
                    String strDepo  =  (tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).toString()));
                    String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));
                    if((strAsiFac.equals("false")))
                        objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);
                    if(strDepo.equals("true"))
                        objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);
                    if((strSelRec.equals("false")))
                        objTblCelEdiTxtVcoTipDoc.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    objVenConTipdoc.setCampoBusqueda(1);
                    objVenConTipdoc.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    int intNumFil = tblDat.getSelectedRow();
                    String strTipDoc  =  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).toString()));
                    String strTipDocOri=  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).toString()));
                    if(!strTipDoc.equals(strTipDocOri))
                    {
                        tblDat.setValueAt("N",intNumFil, INT_TBL_ESTCHK);
                    }
                }
            });
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTRECCHQ).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTDEPO).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUTFACASI).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt)
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_BUTRECCHQ:
                            objTblCelRenBut.setText("...");
                            break;
                        case INT_TBL_BUTTIPDOC:
                            objTblCelRenBut.setText("...");
                            break;
                        case INT_TBL_BUTFACASI:
                            objTblCelRenBut.setText("...");
                            break;
                        case INT_TBL_BUTDEPO:
                            if((objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_CHKDEPO)==null?"false":objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_CHKDEPO).toString()).equals("false"))
                            {
                                objTblCelRenBut.setText("");
                            }
                            else
                            {
                                objTblCelRenBut.setText("...");
                            }
                            break;
                    }
                }
            });
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_BUTRECCHQ).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_BUTTIPDOC).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_BUTDEPO).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_BUTFACASI).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                            case INT_TBL_BUTRECCHQ:
                                break;
                            case INT_TBL_BUTFACASI:
                                break;
                            case INT_TBL_BUTTIPDOC:
                                break;
                            case INT_TBL_BUTDEPO:
                                if((objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_CHKDEPO)==null?"false":objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_CHKDEPO).toString()).equals("false"))
                                {
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                }
                                break;
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                            case INT_TBL_BUTRECCHQ:
                                ventRecChq( intFilSel);
                                break;
                            case INT_TBL_BUTTIPDOC:
                                butCLickButTipDoc( intFilSel );
                                break;
                            case INT_TBL_BUTDEPO:
                                ventDepo( intFilSel );
                                break;
                            case INT_TBL_BUTFACASI:
                                ventFacSig( intFilSel );
                                break;
                        }
                    }
                }
            });
            new ZafTblOrd(tblDat);
            new ZafTblBus(tblDat);
            int intCol[]={INT_TBL_VALCHQ};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            tcmAux=null;
            setEditable(true);
            blnRes=true;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this,e);
        }
        return blnRes;
    }

    public void setEditable(boolean editable)
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
    * Calcula el monto total de los cheques
    *
    */
    public void calculaTotMonChq(int intNumFil)
    {
        double dblValChq=0;
        try
        {
            String strSelReg=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));
            if(strSelReg.equals("false"))
            {
                tblDat.setValueAt( null,  intNumFil, INT_TBL_CODTIPDOC);
                tblDat.setValueAt( null,  intNumFil, INT_TBL_DCOTIPDOC);
                tblDat.setValueAt( null,  intNumFil, INT_TBL_DLATIPDOC); 
            }
            if(!txtCodTipDoc.getText().equals(""))
            {
                if(strSelReg.equals("true"))
                {
                    tblDat.setValueAt( txtCodTipDoc.getText(),  intNumFil, INT_TBL_CODTIPDOC);
                    tblDat.setValueAt( txtDesCodTitpDoc.getText(),  intNumFil, INT_TBL_DCOTIPDOC);
                    tblDat.setValueAt( txtDesLarTipDoc.getText(),  intNumFil, INT_TBL_DLATIPDOC);
                }
            }
            for(int i=0; i<tblDat.getRowCount(); i++)
            {
                if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) )
                {
                    if( ((tblDat.getValueAt(i, INT_TBL_ESTCHK)==null?"N":(tblDat.getValueAt(i, INT_TBL_ESTCHK).toString().equals("")?"N":tblDat.getValueAt(i, INT_TBL_ESTCHK).toString())).equals("N")) )
                    {
                        dblValChq+=Double.parseDouble(((tblDat.getValueAt(i, INT_TBL_VALCHQ)==null)?"0":(tblDat.getValueAt(i, INT_TBL_VALCHQ).toString())));
                    }
                }
            }
            dblValChq=objUti.redondear(dblValChq, 2);
            objTblTot.setValueAt( new Double(dblValChq) ,0, 9);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this,e);
        }
    }

    public void butCLickButTipDoc(int intNumFil)
    {
        try
        {
            if (intNumFil>=0)
            {
                String strAsiFac=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKASIFAC).toString()));
                String strDepo  =  (tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKDEPO).toString()));
                String strSelRec=  (tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).equals("")?"false":tblDat.getValueAt(intNumFil, INT_TBL_CHKSEL).toString()));
                if(strAsiFac.equals("true"))
                {
                    if(strDepo.equals("false"))
                    {
                        if(strSelRec.equals("true"))
                        {
                            listaTipDoc(intNumFil);
                        }
                        else
                            MensajeInf("SELECCIONE EL REGISTRO.. ");
                    }
                    else
                        MensajeInf("YA TIENE DEPOSITO NO ES POSOBLE CAMBIAR BANCO.. ");
                }
                else
                    MensajeInf("NO TIENE ASIGNADO FACTURAS EN SU TOTALIDAD ");
            }
        }
        catch (Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
        }
    }

    private void listaTipDoc(int intNumFil)
    {
        try
        {
            objVenConTipdoc.setTitle("Listado de tipo documento");
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
            {
                tblDat.setValueAt(objVenConTipdoc.getValueAt(1),intNumFil,INT_TBL_CODTIPDOC);
                tblDat.setValueAt(objVenConTipdoc.getValueAt(2),intNumFil,INT_TBL_DCOTIPDOC);
                tblDat.setValueAt(objVenConTipdoc.getValueAt(3),intNumFil,INT_TBL_DLATIPDOC);
                String strTipDoc  =  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOC).toString()));
                String strTipDocOri=  (tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI)==null?"":(tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).equals("")?"":tblDat.getValueAt(intNumFil, INT_TBL_CODTIPDOCORI).toString()));
                if (!strTipDoc.equals(strTipDocOri))
                {
                    tblDat.setValueAt("N",intNumFil, INT_TBL_ESTCHK);
                }
            }
        }
        catch(Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
        }
    }

    private void ventRecChq(int intNumFil)
    {
        String strCodEmp="",strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="";
        try
        {
            strCodEmp=tblDat.getValueAt(intNumFil, INT_TBL_CODEMP).toString();
            strCodLoc=tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
            strCodTipDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODTID).toString();
            strCodDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODDOC).toString();
            strCodReg=tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString();
            CxC.ZafCxC11.ZafCxC11 obj1 = new CxC.ZafCxC11.ZafCxC11(objZafParSis,  new Integer(Integer.parseInt(strCodEmp)), new Integer(Integer.parseInt(strCodLoc)), new Integer(Integer.parseInt(strCodTipDoc)), new Integer(Integer.parseInt(strCodDoc)), strCodReg, 1739 );
            this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        }
        catch(Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
        }
    }

    private void ventDepo(int intNumFil)
    {
        String strCodEmp="",strCodLoc="", strCodTipDoc="", strCodCli="", strCodBan="", strNumChq="", strNumCta="", strMonChq="";
        try
        {
            strCodEmp=tblDat.getValueAt(intNumFil, INT_TBL_CODEMP).toString();
            strCodLoc=tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
            strCodTipDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODTID).toString();
            strCodCli=tblDat.getValueAt(intNumFil, INT_TBL_CODCLI).toString();
            strCodBan=(tblDat.getValueAt(intNumFil, INT_TBL_CODBAN)==null?"":tblDat.getValueAt(intNumFil, INT_TBL_CODBAN).toString());
            strNumChq=(tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ)==null?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCHQ).toString());
            strNumCta=(tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA)==null?"":tblDat.getValueAt(intNumFil, INT_TBL_NUMCTA).toString());
            strMonChq=(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ)==null?"0":(tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString().equals("")?"0":tblDat.getValueAt(intNumFil, INT_TBL_VALCHQ).toString()));
            CxC.ZafCxC35.ZafCxC35 obj1 = new CxC.ZafCxC35.ZafCxC35(objZafParSis,  new Integer(Integer.parseInt(strCodEmp)), new Integer(Integer.parseInt(strCodLoc)), new Integer(Integer.parseInt(strCodTipDoc)), strCodCli, strCodBan, strNumChq , strNumCta, strMonChq,  1831 );
            this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        }
        catch(Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
        }
    }

    private void ventFacSig(int intNumFil)
    {
        String strCodEmp="",strCodLoc="", strCodTipDocRec="", strCodDoc="", strCodReg="";
        String strSql="";
        try
        {
            strCodEmp=tblDat.getValueAt(intNumFil, INT_TBL_CODEMP).toString();
            strCodLoc=tblDat.getValueAt(intNumFil, INT_TBL_CODLOC).toString();
            strCodTipDocRec=tblDat.getValueAt(intNumFil, INT_TBL_CODTID).toString();
            strCodDoc=tblDat.getValueAt(intNumFil, INT_TBL_CODDOC).toString();
            strCodReg=tblDat.getValueAt(intNumFil, INT_TBL_CODREG).toString();
            strSql="SELECT  a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a3.tx_descor, a3.tx_deslar, a2.ne_numdoc, a2.fe_doc , a1.ne_diacre, " +
            " a1.fe_ven, a1.mo_pag,   (a1.mo_pag +a1.nd_abo) as valpen,  a1.nd_monchq  " +
            " FROM tbr_detrecdocpagmovinv as a " +
            " inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emprel and a1.co_loc=a.co_locrel and a1.co_tipdoc=a.co_tipdocrel and a1.co_doc=a.co_docrel and a1.co_reg=a.co_regrel) " +
            " inner join tbm_cabmovinv as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) " +
            "INNER JOIN tbm_cabtipdoc as a3 ON (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc ) "+
            " WHERE a.co_emp="+strCodEmp+" and a.co_loc="+strCodLoc+" and a.co_tipdoc="+strCodTipDocRec+"  and a.co_doc= "+strCodDoc+"   and a.co_reg= "+strCodReg+"  and  a.st_reg='A' ";
            CxC.ZafCxC35.ZafCxC35_01 obj = new  CxC.ZafCxC35.ZafCxC35_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis, strSql  );
            obj.show();
            obj.dispose();
            obj=null;
        }
        catch (Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panCabRecChq = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDesCodTitpDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        butBanPre = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chk1 = new javax.swing.JRadioButton();
        txtNumChq = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        chk2 = new javax.swing.JRadioButton();
        chk2_1 = new javax.swing.JRadioButton();
        chk2_2 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        chk5 = new javax.swing.JRadioButton();
        chk5_1 = new javax.swing.JRadioButton();
        chk5_2 = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        chk3 = new javax.swing.JRadioButton();
        chk3_1 = new javax.swing.JRadioButton();
        chk3_2 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        chk4 = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        chk6 = new javax.swing.JRadioButton();
        panDat = new javax.swing.JPanel();
        panDetRecChq = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        panGenTabGen.setLayout(null);

        panCabRecChq.setPreferredSize(new java.awt.Dimension(100, 150));
        panCabRecChq.setLayout(null);

        jLabel6.setText("Fecha :"); // NOI18N
        panCabRecChq.add(jLabel6);
        jLabel6.setBounds(4, 4, 60, 20);

        jLabel3.setText("Tipo de documento:"); // NOI18N
        panCabRecChq.add(jLabel3);
        jLabel3.setBounds(180, 4, 120, 20);

        txtDesCodTitpDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCodTitpDocActionPerformed(evt);
            }
        });
        txtDesCodTitpDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCodTitpDocFocusLost(evt);
            }
        });
        panCabRecChq.add(txtDesCodTitpDoc);
        txtDesCodTitpDoc.setBounds(290, 4, 56, 20);

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
        panCabRecChq.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(346, 4, 294, 20);

        butTipDoc.setText(".."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabRecChq.add(butTipDoc);
        butTipDoc.setBounds(640, 4, 20, 20);

        butBanPre.setText("...");
        butBanPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBanPreActionPerformed(evt);
            }
        });
        panCabRecChq.add(butBanPre);
        butBanPre.setBounds(660, 4, 20, 20);

        panGenTabGen.add(panCabRecChq);
        panCabRecChq.setBounds(0, 0, 680, 28);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel1.setPreferredSize(new java.awt.Dimension(560, 30));
        jPanel1.setLayout(null);

        buttonGroup2.add(chk1);
        chk1.setText("Cheques específico :");
        chk1.setActionCommand("Cheque especificado : ");
        chk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk1ActionPerformed(evt);
            }
        });
        jPanel1.add(chk1);
        chk1.setBounds(4, 4, 150, 20);
        jPanel1.add(txtNumChq);
        txtNumChq.setBounds(160, 4, 100, 20);

        panGenTabGen.add(jPanel1);
        jPanel1.setBounds(4, 28, 560, 28);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel2.setLayout(null);

        buttonGroup2.add(chk2);
        chk2.setSelected(true);
        chk2.setText("Cheques sin banco asignados");
        chk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk2ActionPerformed(evt);
            }
        });
        jPanel2.add(chk2);
        chk2.setBounds(4, 4, 470, 20);

        buttonGroup1.add(chk2_1);
        chk2_1.setSelected(true);
        chk2_1.setText("Mostrar los cheques que vencen en la fecha especificada");
        jPanel2.add(chk2_1);
        chk2_1.setBounds(24, 24, 470, 20);

        buttonGroup1.add(chk2_2);
        chk2_2.setText("Mostrar los cheques que vencen hasta la fecha especificada");
        jPanel2.add(chk2_2);
        chk2_2.setBounds(24, 44, 470, 20);

        panGenTabGen.add(jPanel2);
        jPanel2.setBounds(4, 84, 560, 68);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel4.setLayout(null);

        buttonGroup2.add(chk5);
        chk5.setText("Todos los cheques");
        chk5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk5ActionPerformed(evt);
            }
        });
        jPanel4.add(chk5);
        chk5.setBounds(4, 4, 470, 20);

        buttonGroup1.add(chk5_1);
        chk5_1.setText("Mostrar los cheques que vencen en la fecha especificada");
        jPanel4.add(chk5_1);
        chk5_1.setBounds(24, 24, 470, 20);

        buttonGroup1.add(chk5_2);
        chk5_2.setText("Mostrar los cheques que vencen hasta la fecha especificada");
        jPanel4.add(chk5_2);
        chk5_2.setBounds(24, 44, 470, 20);

        panGenTabGen.add(jPanel4);
        jPanel4.setBounds(4, 248, 560, 68);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel6.setLayout(null);

        buttonGroup2.add(chk3);
        chk3.setText("Cheques con banco asignados");
        chk3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk3ActionPerformed(evt);
            }
        });
        jPanel6.add(chk3);
        chk3.setBounds(4, 4, 470, 20);

        buttonGroup1.add(chk3_1);
        chk3_1.setText("Mostrar los cheques a los que se le asigno banco en la fecha especificada");
        jPanel6.add(chk3_1);
        chk3_1.setBounds(24, 24, 470, 20);

        buttonGroup1.add(chk3_2);
        chk3_2.setText("Mostrar los cheques a los que se le asigno banco hasta la fecha especificada");
        jPanel6.add(chk3_2);
        chk3_2.setBounds(24, 44, 470, 20);

        panGenTabGen.add(jPanel6);
        jPanel6.setBounds(4, 152, 560, 68);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel7.setPreferredSize(new java.awt.Dimension(560, 30));
        jPanel7.setLayout(null);

        buttonGroup2.add(chk4);
        chk4.setText("Cheques depositados");
        chk4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk4ActionPerformed(evt);
            }
        });
        jPanel7.add(chk4);
        chk4.setBounds(4, 4, 470, 20);

        panGenTabGen.add(jPanel7);
        jPanel7.setBounds(4, 220, 560, 28);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        jPanel8.setPreferredSize(new java.awt.Dimension(560, 30));
        jPanel8.setLayout(null);

        buttonGroup2.add(chk6);
        chk6.setText("Cheques que estan pendiente de asignar facturas.");
        chk6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk6ActionPerformed(evt);
            }
        });
        jPanel8.add(chk6);
        chk6.setBounds(4, 4, 470, 20);

        panGenTabGen.add(jPanel8);
        jPanel8.setBounds(4, 56, 560, 28);

        tabFrm.addTab("General", panGenTabGen);

        panDat.setLayout(new java.awt.BorderLayout());

        panDetRecChq.setLayout(new java.awt.BorderLayout());

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

        panDetRecChq.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panDetRecChq.add(spnTot, java.awt.BorderLayout.SOUTH);

        panDat.add(panDetRecChq, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Datos", panDat);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(90, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(90, 23));
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

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if(!validar())
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private boolean validar()
    {
        boolean blnRes=false;
        try
        {
            if(chk1.isSelected())
            {
                if(txtNumChq.getText().equals(""))
                {
                    this.MensajeInf("INGRESE EL NUMERO DEL CHEQUE PARA LA BUSQUEDA ");
                    blnRes=true;
                }
            }
            else if(chk2.isSelected())
            {
                if(!txtFecDoc.isFecha())
                {
                    this.MensajeInf("SELECCIONE LA FECHA DE BUSQUEDA ");
                    blnRes=true;
                }
            }
            else if(chk3.isSelected())
            {
                if(!txtFecDoc.isFecha())
                {
                    this.MensajeInf("SELECCIONE LA FECHA DE BUSQUEDA ");
                    blnRes=true;
                }
            }
            else if(chk4.isSelected())
            {
                
            }
            else if(chk5.isSelected())
            {
                if(!txtFecDoc.isFecha())
                {
                    this.MensajeInf("SELECCIONE LA FECHA DE BUSQUEDA ");
                    blnRes=true;
                }
            }
        }
        catch (Exception evt)
        {
            objUti.mostrarMsgErr_F1(this, evt);
        }
        return blnRes;
    }



    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            String strSql="", strAuxSqlAux="", strAuxTDCon=" AND a.co_tipdoccon IS NULL ";
            String strFecSql="";
            if(txtFecDoc.isFecha())
            {
                int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
            }
            if(chk1.isSelected())
            {
                strAuxSqlAux=" AND a.tx_numchq='"+txtNumChq.getText()+"' ";
                strAuxTDCon="";
            }
            else if(chk2.isSelected())
            {
                if(chk2_1.isSelected())
                {
                    strAuxSqlAux = " AND a.fe_venchq = '"+strFecSql+"' ";
                }
                else if(chk2_2.isSelected())
                {
                    strAuxSqlAux = " AND a.fe_venchq <= '"+strFecSql+"' ";
                }
            }
            else if(chk3.isSelected())
            {
                if(chk3_1.isSelected())
                {
                    strAuxSqlAux = " AND a.fe_venchq = '"+strFecSql+"' ";
                }
                else if(chk3_2.isSelected())
                {
                    strAuxSqlAux = " AND a.fe_venchq <= '"+strFecSql+"' ";
                }
                strAuxTDCon=" AND a.co_tipdoccon IS NOT NULL ";
            }
            else if(chk4.isSelected())
            {
                strAuxSqlAux=" AND a.nd_valapl > 0 ";
                strAuxTDCon="";
            }
            else if(chk5.isSelected())
            {
                if(chk5_1.isSelected())
                {
                    strAuxSqlAux = " AND a.fe_venchq = '"+strFecSql+"' ";
                }
                else if(chk5_2.isSelected())
                {
                    strAuxSqlAux = " AND a.fe_venchq <= '"+strFecSql+"' ";
                }
                strAuxTDCon="";
            }
            else if(chk6.isSelected())
            {
                strAuxTDCon=" AND a.st_asidocrel='N' and a.nd_valapl <= 0  ";
            }
            strSqlGlb="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.fe_asitipdoccon, " +
            " CASE WHEN a.co_tipdoccon IS NULL THEN 'N' ELSE 'S' END AS est, " +
            " a.co_cli, a3.tx_nom, a.co_banchq, a2.tx_descor, a2.tx_deslar , a.tx_numctachq, a.tx_numchq, a.nd_monchq, a.fe_recchq, a.fe_venchq " +
            " , a.co_tipdoccon, a4.tx_descor as descortipdoc, a4.tx_deslar as deslartipdoc, a.nd_valapl, a.st_asidocrel " +
            " FROM tbm_detrecdoc as a  " +
            " INNER JOIN tbm_cabrecdoc AS a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc) " +
            " INNER JOIN tbm_cli as a3 ON (a3.co_emp=a.co_emp and a3.co_cli=a.co_cli) " +
            " LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a.co_banchq ) " +
            " LEFT  JOIN tbm_cabtipdoc as a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoccon ) "+
            " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a.co_loc="+objZafParSis.getCodigoLocal()+" " +
            " AND a.co_tipdoc= 94  "+strAuxTDCon+"  AND a.st_reg = 'A' " +
            " "+strAuxSqlAux+" AND a1.st_reg NOT IN ('I','E')  ORDER BY a.fe_venchq, a3.tx_nom ";
            strSqlGlb="select  "
            + "  ( select sum(x1.nd_monchq) from tbr_detrecdocpagmovinv as x  "
            + "    inner join tbm_pagmovinv as x1 on (x1.co_emp=x.co_emprel and x1.co_loc=x.co_locrel and x1.co_tipdoc=x.co_tipdocrel and x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel)  "
            + "    where x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_reg=a.co_reg and x.st_reg='A' "
            + "  ) as totvalasi "
            + " ,* from ( "+strSqlGlb+" ) as a ";
            strSqlGlb="select  ( nd_monchq - case when totvalasi is null then 0 else totvalasi end ) as valpenasifac  "
            + " ,* from ( "+strSqlGlb+" ) as x ";
            cargarDat(strSqlGlb);
            tabFrm.setSelectedIndex(1);
            objThrGUI=null;
        }
    }

    /**
    * Se encarga de cargar la informacion en la tabla
    * @return  true si esta correcto y false  si hay algun error
    */
    private boolean cargarDat(String strSql)
    {
        boolean blnRes=false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        double dblValChq=0;
        try
        {
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
            if(conn!=null)
            {
                stmLoc=conn.createStatement();
                java.util.Vector vecData = new java.util.Vector();
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA, "");
                    vecReg.add(INT_TBL_CHKSEL, new Boolean(rstLoc.getString("est").equals("S")?true:false)  );
                    vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
                    vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nom") );
                    vecReg.add(INT_TBL_CODBAN, rstLoc.getString("co_banchq") );
                    vecReg.add(INT_TBL_DSCBAN, rstLoc.getString("tx_descor") );
                    vecReg.add(INT_TBL_NOMBAN, rstLoc.getString("tx_deslar") );
                    vecReg.add(INT_TBL_NUMCTA, rstLoc.getString("tx_numctachq") );
                    vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
                    vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq") );
                    vecReg.add(INT_TBL_FECREC, rstLoc.getString("fe_recchq") );
                    vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_venchq") );
                    vecReg.add(INT_TBL_BUTRECCHQ, "" );
                    vecReg.add(INT_TBL_CHKASIFAC, new Boolean( rstLoc.getString("st_asidocrel").equals("S")?true:false)  );
                    vecReg.add(INT_TBL_VALPENASI, rstLoc.getString("valpenasifac") );
                    vecReg.add(INT_TBL_BUTFACASI, "" );
                    vecReg.add(INT_TBL_CODTIPDOC, rstLoc.getString("co_tipdoccon") );
                    vecReg.add(INT_TBL_DCOTIPDOC, rstLoc.getString("descortipdoc") );
                    vecReg.add(INT_TBL_BUTTIPDOC,"");
                    vecReg.add(INT_TBL_DLATIPDOC, rstLoc.getString("deslartipdoc") );
                    vecReg.add(INT_TBL_FECASIBAN, rstLoc.getString("fe_asitipdoccon") );
                    vecReg.add(INT_TBL_CHKDEPO, new Boolean( (rstLoc.getDouble("nd_valapl")>0?true:false) ) );
                    vecReg.add(INT_TBL_BUTDEPO, "" );
                    vecReg.add(INT_TBL_ESTCHK, rstLoc.getString("est"));
                    vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                    vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                    vecReg.add(INT_TBL_CODTIPDOCORI, rstLoc.getString("co_tipdoccon") );
                    vecData.add(vecReg);
                    if(rstLoc.getString("est").equals("S"))
                        dblValChq+=rstLoc.getDouble("nd_monchq");
                }
                rstLoc.close();
                rstLoc=null;
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);
                dblValChq=objUti.redondear(dblValChq, 2);
                objTblTot.setValueAt( new Double(dblValChq) ,0, 9 );
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
                stmLoc.close();
                stmLoc=null;
                conn.close();
                conn=null;
            }
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        System.gc();
        return blnRes;
    }

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea guardar ?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
             guardarDat();
             cargarDat(strSqlGlb);
        }
}//GEN-LAST:event_butGuaActionPerformed

    private boolean guardarDat()
    {
        boolean blnRes=false;
        java.sql.Connection conn;
        try
        {
            conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if(conn!=null)
            {
                conn.setAutoCommit(false);
                if(actualizarReg(conn))
                {
                   conn.commit();
                   blnRes=true;
                   MensajeInf("Los datos se Guardar con éxito.. ");
                }
                else
                    conn.rollback();
                conn.close();
                conn=null;
            }
        }
        catch(java.sql.SQLException Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch(Exception Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        System.gc();
        return blnRes;
    }

    public boolean actualizarReg(java.sql.Connection conn )
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        String strSql="";
        String strCodEmp="",strCodLoc="", strCodTipDoc="", strCodDoc="", strCodReg="", strCodTipDocCon="";
        String strFecSis="";
        String strObs="";
        try
        {
            stmLoc=conn.createStatement();
            strFecSis=objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());
            for(int i=0; i<tblDat.getRowCount(); i++)
            {
                if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) )
                {
                    if( ((tblDat.getValueAt(i, INT_TBL_ESTCHK)==null?"N":(tblDat.getValueAt(i, INT_TBL_ESTCHK).toString().equals("")?"N":tblDat.getValueAt(i, INT_TBL_ESTCHK).toString())).equals("N")) )
                    {
                        strCodEmp=tblDat.getValueAt(i, INT_TBL_CODEMP).toString();
                        strCodLoc=tblDat.getValueAt(i, INT_TBL_CODLOC).toString();
                        strCodTipDoc=tblDat.getValueAt(i, INT_TBL_CODTID).toString();
                        strCodDoc=tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                        strCodReg=tblDat.getValueAt(i, INT_TBL_CODREG).toString();
                        strObs="  Usuario que ASIGNO BANCO al cheque es: "+objZafParSis.getNombreUsuario()+" --en la maquina: "+objZafParSis.getNombreComputadoraConDirIP()+" -- en la fecha: "+strFecSis+" ";
                        strCodTipDocCon=(tblDat.getValueAt(i, INT_TBL_CODTIPDOC)==null?null:(tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString().equals("")?null:tblDat.getValueAt(i, INT_TBL_CODTIPDOC).toString()));
                        strSql +=" UPDATE tbm_detrecdoc SET co_tipdoccon="+strCodTipDocCon+", fe_asitipdoccon='"+strFecSis+"', tx_obs2='"+strObs+"' " +
                        " WHERE co_emp="+strCodEmp+" " +
                        " AND co_loc="+strCodLoc+" AND co_tipdoc="+strCodTipDoc+" AND co_doc="+strCodDoc+" AND co_reg="+strCodReg+" ; ";
                    }
                }
            }
            if(!(strSql.equals("")))
            stmLoc.executeUpdate(strSql);
            stmLoc.close();
            stmLoc=null;
            blnRes=true;
        }
        catch (java.sql.SQLException Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        catch (Exception Evt)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    /**
    * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
    */
    private void exitForm()
    {
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }
    
    private void txtDesCodTitpDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocActionPerformed
        txtDesCodTitpDoc.transferFocus();
}//GEN-LAST:event_txtDesCodTitpDocActionPerformed

    private void txtDesCodTitpDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusGained
        strDesCodTipDoc=txtDesCodTitpDoc.getText();
        txtDesCodTitpDoc.selectAll();
}//GEN-LAST:event_txtDesCodTitpDocFocusGained

    private void txtDesCodTitpDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCodTitpDocFocusLost
        if (!txtDesCodTitpDoc.getText().equalsIgnoreCase(strDesCodTipDoc))
        {
            if (txtDesCodTitpDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
                BuscarTipDoc("a.tx_descor",txtDesCodTitpDoc.getText(),1);
        }
        else
            txtDesCodTitpDoc.setText(strDesCodTipDoc);
    }//GEN-LAST:event_txtDesCodTitpDocFocusLost

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
                txtDesCodTitpDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
                BuscarTipDoc("a.tx_deslar",txtDesLarTipDoc.getText(),2);
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        objVenConTipdoc.setCampoBusqueda(1);
        objVenConTipdoc.setVisible(true);
        if (objVenConTipdoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
        {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
}//GEN-LAST:event_butTipDocActionPerformed

    private void chk5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk5ActionPerformed
        if(chk5.isSelected())
        {
            chk5_1.setEnabled(true);
            chk5_2.setEnabled(true);
            chk5_1.setSelected(true);
            txtFecDoc.setEnabled(true);
            txtNumChq.setEditable(false);
            chk3_1.setSelected(false);
            chk3_2.setSelected(false);
            chk3_1.setEnabled(false);
            chk3_2.setEnabled(false);
            chk2_1.setSelected(false);
            chk2_2.setSelected(false);
            chk2_1.setEnabled(false);
            chk2_2.setEnabled(false);
        }
    }//GEN-LAST:event_chk5ActionPerformed

    public void BuscarTipDoc(String campo,String strBusqueda,int tipo)
    {
        objVenConTipdoc.setTitle("Listado de Tipo de Documentos");
        if(objVenConTipdoc.buscar(campo, strBusqueda ))
        {
            txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
            txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
            txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
            strCodTipDoc=objVenConTipdoc.getValueAt(1);
        }
        else
        {
            objVenConTipdoc.setCampoBusqueda(tipo);
            objVenConTipdoc.cargarDatos();
            objVenConTipdoc.show();
            if (objVenConTipdoc.getSelectedButton()==objVenConTipdoc.INT_BUT_ACE)
            {
                txtCodTipDoc.setText(objVenConTipdoc.getValueAt(1));
                txtDesCodTitpDoc.setText(objVenConTipdoc.getValueAt(2));
                txtDesLarTipDoc.setText(objVenConTipdoc.getValueAt(3));
                strCodTipDoc=objVenConTipdoc.getValueAt(1);
            }
            else
            {
                txtCodTipDoc.setText(strCodTipDoc);
                txtDesCodTitpDoc.setText(strDesCodTipDoc);
                txtDesLarTipDoc.setText(strDesLarTipDoc);
            }
        }
    }

    private void chk3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk3ActionPerformed
        if(chk3.isSelected())
        {
            chk3_1.setEnabled(true);
            chk3_2.setEnabled(true);
            chk3_1.setSelected(true);
            txtFecDoc.setEnabled(true);
            txtNumChq.setEditable(false);
            chk5_1.setSelected(false);
            chk5_2.setSelected(false);
            chk5_1.setEnabled(false);
            chk5_2.setEnabled(false);
            chk2_1.setSelected(false);
            chk2_2.setSelected(false);
            chk2_1.setEnabled(false);
            chk2_2.setEnabled(false);
        }
    }//GEN-LAST:event_chk3ActionPerformed

    private void chk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk2ActionPerformed
        if(chk2.isSelected())
        {
            chk2_1.setEnabled(true);
            chk2_2.setEnabled(true);
            chk2_1.setSelected(true);
            txtFecDoc.setEnabled(true);
            txtNumChq.setEditable(false);
            chk5_1.setSelected(false);
            chk5_2.setSelected(false);
            chk5_1.setEnabled(false);
            chk5_2.setEnabled(false);
            chk3_1.setSelected(false);
            chk3_2.setSelected(false);
            chk3_1.setEnabled(false);
            chk3_2.setEnabled(false);
        }
    }//GEN-LAST:event_chk2ActionPerformed

    private void chk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk1ActionPerformed
        if(chk1.isSelected())
        {
            txtNumChq.setEditable(true);
            txtFecDoc.setEnabled(false);
            chk2_1.setSelected(false);
            chk2_2.setSelected(false);
            chk2_1.setEnabled(false);
            chk2_2.setEnabled(false);
            chk5_1.setSelected(false);
            chk5_2.setSelected(false);
            chk5_1.setEnabled(false);
            chk5_2.setEnabled(false);
            chk3_1.setSelected(false);
            chk3_2.setSelected(false);
            chk3_1.setEnabled(false);
            chk3_2.setEnabled(false);
        }
    }//GEN-LAST:event_chk1ActionPerformed

    private void chk4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk4ActionPerformed
        if(chk4.isSelected())
        {
           txtNumChq.setEditable(false);
           txtFecDoc.setEnabled(false);
           chk2_1.setSelected(false);
           chk2_2.setSelected(false);
           chk2_1.setEnabled(false);
           chk2_2.setEnabled(false);
           chk5_1.setSelected(false);
           chk5_2.setSelected(false);
           chk5_1.setEnabled(false);
           chk5_2.setEnabled(false);
           chk3_1.setSelected(false);
           chk3_2.setSelected(false);
           chk3_1.setEnabled(false);
           chk3_2.setEnabled(false);
        }
    }//GEN-LAST:event_chk4ActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
         exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    private void butBanPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBanPreActionPerformed
        ZafCxC37_01 obj = new  ZafCxC37_01(javax.swing.JOptionPane.getFrameForComponent(this), true,  objZafParSis );
        obj.setVisible(true);
    }//GEN-LAST:event_butBanPreActionPerformed

    private void chk6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk6ActionPerformed
        if(chk6.isSelected())
        {
           txtNumChq.setEditable(false);
           txtFecDoc.setEnabled(false);
           chk2_1.setSelected(false);
           chk2_2.setSelected(false);
           chk2_1.setEnabled(false);
           chk2_2.setEnabled(false);
           chk5_1.setSelected(false);
           chk5_2.setSelected(false);
           chk5_1.setEnabled(false);
           chk5_2.setEnabled(false);
           chk3_1.setSelected(false);
           chk3_2.setSelected(false);
           chk3_1.setEnabled(false);
           chk3_2.setEnabled(false);
        }
    }//GEN-LAST:event_chk6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBanPre;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butTipDoc;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton chk1;
    private javax.swing.JRadioButton chk2;
    private javax.swing.JRadioButton chk2_1;
    private javax.swing.JRadioButton chk2_2;
    private javax.swing.JRadioButton chk3;
    private javax.swing.JRadioButton chk3_1;
    private javax.swing.JRadioButton chk3_2;
    private javax.swing.JRadioButton chk4;
    private javax.swing.JRadioButton chk5;
    private javax.swing.JRadioButton chk5_1;
    private javax.swing.JRadioButton chk5_2;
    private javax.swing.JRadioButton chk6;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabRecChq;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDetRecChq;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtDesCodTitpDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumChq;
    // End of variables declaration//GEN-END:variables

    /**
    * Mensaje que presenta el sistema
    * @param strMensaje recibe el mensaje a mostrar
    */
    private void MensajeInf(String strMensaje)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_CHKSEL:
                    strMsg="Selección de cheques.";
                    break;
                case INT_TBL_CODCLI:
                    strMsg="Código de cliente.";
                    break;
                case INT_TBL_NOMCLI:
                    strMsg="Nombre del cliente.";
                    break;
                case INT_TBL_DSCBAN:
                    strMsg="Banco.";
                    break;
                case INT_TBL_NOMBAN:
                    strMsg="Nombre del Banco.";
                    break;
                case INT_TBL_NUMCTA:
                    strMsg="Número de cuenta.";
                    break;
                case INT_TBL_NUMCHQ:
                    strMsg="Número de cheque.";
                    break;
                case INT_TBL_VALCHQ:
                    strMsg="valor del cheque.";
                    break;
                case INT_TBL_FECREC:
                    strMsg="Fecha de recepción.";
                    break;
                case INT_TBL_FECVEN:
                    strMsg="Fecha de vencimiento del cheque.";
                    break;
                case INT_TBL_BUTRECCHQ:
                    strMsg="Muesta la recepcion del cheque.";
                    break;
                case INT_TBL_CHKASIFAC:
                    strMsg="Si ya esta asignado todas las facturas en su totalidad.";
                    break;
                case INT_TBL_BUTFACASI:
                    strMsg="Muesta la Facturas asignadas al cheque.";
                    break;
                case INT_TBL_DCOTIPDOC:
                    strMsg="Descripción corta del tipo de documento.";
                    break;
                case INT_TBL_BUTTIPDOC:
                    strMsg="Busqueda del tipo de documento.";
                    break;
                case INT_TBL_VALPENASI:
                    strMsg="Valor pendiente de asignar.";
                    break;
                case INT_TBL_DLATIPDOC:
                    strMsg="Descripción larga del tipo de documento.";
                    break;
                case INT_TBL_FECASIBAN:
                    strMsg="Fecha de la asignacion del banco.";
                    break;
                case INT_TBL_CHKDEPO:
                    strMsg="Si ya esta hecho el deposito.";
                    break;
                case INT_TBL_BUTDEPO:
                    strMsg="Presenta el deposito.";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 
    protected void finalize() throws Throwable
    {
        System.gc();
        super.finalize();
    }

}
