/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC55.java
 *
 * Created on Jan 19, 2010, 4:44:52 PM
 */
 
package CxC.ZafCxC55;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;

/**
 *
 * @author jayapata
 */
public class ZafCxC55 extends javax.swing.JInternalFrame
{

    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModDat;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
    private ZafTblTot objTblTotDoc;

    static final int INT_TBL_EMP_LIN = 0;
    static final int INT_TBL_EMP_CHK = 1;
    static final int INT_TBL_EMP_COD = 2;
    static final int INT_TBL_EMP_NOM = 3;

    static final int INT_TBL_DAT_LIN = 0;
    static final int INT_TBL_DAT_COD = 1;
    static final int INT_TBL_DAT_NOM = 2;
    int INT_TBL_DAT_LUN = 3;
    int INT_TBL_DAT_MAR = 4;
    int INT_TBL_DAT_MIE = 5;
    int INT_TBL_DAT_JUE = 6;
    int INT_TBL_DAT_VIE = 7;
    int INT_TBL_DAT_SAB = 8;
    int INT_TBL_DAT_DOM = 9;
    int INT_TBL_DAT_TOT1 = 10;
    int INT_TBL_DAT_TOT2 = 11;
    int INT_TBL_DAT_TOT3 = 12;
    int INT_TBL_DAT_TOT4 = 13;
    int INT_TBL_DAT_TOTFIN = 14;
    int INT_SEM_NUE=INT_TBL_DAT_TOT4;
    String strVersion=" v0.5";
    int intDiaHoy=-1;
    int intNumMesCon=0;
    String strMsnCol[] = new String[100];

    /** Creates new form ZafCxC56 */
    public ZafCxC55(ZafParSis objParsis)
    {
        try
        {
            this.objZafParSis=(Librerias.ZafParSis.ZafParSis) objParsis.clone();
            initComponents();
            this.setTitle("" + objZafParSis.getNombreMenu()+ strVersion);
            lblTit.setText("" + objZafParSis.getNombreMenu());
            objUti = new ZafUtil();
        }
        catch (CloneNotSupportedException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void cargarConfiguracion()
    {
        configurarForm();
        cargarDatEmp();
        intNumMesCon=1;
        configurarFormDat();
        spnMesCon.setValue(new Integer(1));
    }

    private boolean configurarForm()
    {
        boolean blnres=false;
        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_EMP_LIN,"");
        vecCab.add(INT_TBL_EMP_CHK," ");
        vecCab.add(INT_TBL_EMP_COD,"Cód.Emp");
        vecCab.add(INT_TBL_EMP_NOM,"Empresa");
        objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblMod.setHeader(vecCab);
        tblEmp.setModel(objTblMod);
        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblEmp, INT_TBL_EMP_LIN);
        tblEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblEmp.getColumnModel();
        tblEmp.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_EMP_COD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_EMP_NOM).setPreferredWidth(130);
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_EMP_CHK);
        objTblMod.setColumnasEditables(vecAux);
        vecAux=null;
        //Configurar JTable: Editor de la tabla.
        new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblEmp);
        objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
        objTblCelRenChk=null;
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        return blnres;
    }

    private boolean configurarFormDat()
    {
        boolean blnres=false;
        Vector vecCab=new Vector();    //Almacena las cabeceras
        vecCab.clear();
        int[] intCol = new int[((intNumMesCon*4)+8)];
        vecCab.add(INT_TBL_DAT_LIN,"");
        vecCab.add(INT_TBL_DAT_COD,"Cód.Emp ");
        vecCab.add(INT_TBL_DAT_NOM,"Empresa");
        vecCab.add(INT_TBL_DAT_LUN,"Lunes"); //3
        vecCab.add(INT_TBL_DAT_MAR,"Martes"); //4
        vecCab.add(INT_TBL_DAT_MIE,"Miércoles"); //5
        vecCab.add(INT_TBL_DAT_JUE,"Jueves"); //6
        vecCab.add(INT_TBL_DAT_VIE,"Viernes"); //7
        vecCab.add(INT_TBL_DAT_SAB,"Sábado"); //8
        vecCab.add(INT_TBL_DAT_DOM,"Domingo"); //9
        vecCab.add(INT_TBL_DAT_TOT1,"Total");
        vecCab.add(INT_TBL_DAT_TOT2,"Total");
        vecCab.add(INT_TBL_DAT_TOT3,"Total");
        vecCab.add(INT_TBL_DAT_TOT4,"Total");
        intCol[0]=INT_TBL_DAT_LUN;
        intCol[1]=INT_TBL_DAT_MAR;
        intCol[2]=INT_TBL_DAT_MIE;
        intCol[3]=INT_TBL_DAT_JUE;
        intCol[4]=INT_TBL_DAT_VIE;
        intCol[5]=INT_TBL_DAT_SAB;
        intCol[6]=INT_TBL_DAT_DOM;
        intCol[7]=INT_TBL_DAT_TOT1;
        intCol[8]=INT_TBL_DAT_TOT2;
        intCol[9]=INT_TBL_DAT_TOT3;
        intCol[10]=INT_TBL_DAT_TOT4;
        int intArr=10;
        INT_SEM_NUE=INT_TBL_DAT_TOT4;
        for (int i=2; i<=intNumMesCon; i++)
        {
            INT_SEM_NUE++;
            intArr++;
            intCol[intArr]=INT_SEM_NUE;
            vecCab.add(INT_SEM_NUE,"Total");
            INT_SEM_NUE++;
            intArr++;
            intCol[intArr]=INT_SEM_NUE;
            vecCab.add(INT_SEM_NUE,"Total");
            INT_SEM_NUE++;
            intArr++;
            intCol[intArr]=INT_SEM_NUE;
            vecCab.add(INT_SEM_NUE,"Total");
            INT_SEM_NUE++;
            intArr++;
            intCol[intArr]=INT_SEM_NUE;
            vecCab.add(INT_SEM_NUE,"Total");
        }
        INT_SEM_NUE++;
        INT_TBL_DAT_TOTFIN = INT_SEM_NUE;
        vecCab.add(INT_TBL_DAT_TOTFIN,"Tot.Fin");
        intArr++;
        intCol[intArr]=INT_TBL_DAT_TOTFIN;  //****
        objTblModDat=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
        objTblModDat.setHeader(vecCab);
        tblDat.setModel(objTblModDat);
        //Configurar JTable: Establecer la fila de cabecera.
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        ZafMouMotAda objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
        objTblModDat.setColumnDataType(INT_TBL_DAT_LUN, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_MAR, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_MIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_JUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_VIE, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_SAB, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_DOM, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_TOT1, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_TOT2, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_TOT3, objTblModDat.INT_COL_DBL, new Integer(0), null);
        objTblModDat.setColumnDataType(INT_TBL_DAT_TOT4, objTblModDat.INT_COL_DBL, new Integer(0), null);
        INT_SEM_NUE=INT_TBL_DAT_TOT4;
        for (int i=2; i <= intNumMesCon; i++)
        {
            INT_SEM_NUE++;
            objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
            INT_SEM_NUE++;
            objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
            INT_SEM_NUE++;
            objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
            INT_SEM_NUE++;
            objTblModDat.setColumnDataType(INT_SEM_NUE, objTblModDat.INT_COL_DBL, new Integer(0), null);
        }
        objTblModDat.setColumnDataType(INT_TBL_DAT_TOTFIN, objTblModDat.INT_COL_DBL, new Integer(0), null);
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tblDat.getTableHeader().setReorderingAllowed(false);
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_COD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_NOM).setPreferredWidth(150);
        tcmAux.getColumn(INT_TBL_DAT_LUN).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_MAR).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_MIE).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_JUE).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_VIE).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_SAB).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_DOM).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_TOT1).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_TOT2).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_TOT3).setPreferredWidth(70);
        tcmAux.getColumn(INT_TBL_DAT_TOT4).setPreferredWidth(70);
        INT_SEM_NUE=INT_TBL_DAT_TOT4;
        for (int i=2; i<=intNumMesCon; i++)
        {
            INT_SEM_NUE++;
            tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
            INT_SEM_NUE++;
            tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
            INT_SEM_NUE++;
            tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
            INT_SEM_NUE++;
            tcmAux.getColumn(INT_SEM_NUE).setPreferredWidth(70);
        }
        tcmAux.getColumn(INT_TBL_DAT_TOTFIN).setPreferredWidth(70);
        ArrayList arlColHid=new ArrayList();
        if ((intDiaHoy+2)==INT_TBL_DAT_MAR)
            arlColHid.add(""+INT_TBL_DAT_LUN);
        if ((intDiaHoy+2)==INT_TBL_DAT_MIE )
        {
            arlColHid.add(""+INT_TBL_DAT_LUN);
            arlColHid.add(""+INT_TBL_DAT_MAR);
        }
        if ((intDiaHoy+2)==INT_TBL_DAT_JUE )
        {
            arlColHid.add(""+INT_TBL_DAT_LUN);
            arlColHid.add(""+INT_TBL_DAT_MAR);
            arlColHid.add(""+INT_TBL_DAT_MIE);
        }
        if ((intDiaHoy+2)==INT_TBL_DAT_VIE )
        {
            arlColHid.add(""+INT_TBL_DAT_LUN);
            arlColHid.add(""+INT_TBL_DAT_MAR);
            arlColHid.add(""+INT_TBL_DAT_MIE);
            arlColHid.add(""+INT_TBL_DAT_JUE);
        }
        if ((intDiaHoy+2)==INT_TBL_DAT_SAB )
        {
            arlColHid.add(""+INT_TBL_DAT_LUN);
            arlColHid.add(""+INT_TBL_DAT_MAR);
            arlColHid.add(""+INT_TBL_DAT_MIE);
            arlColHid.add(""+INT_TBL_DAT_JUE);
            arlColHid.add(""+INT_TBL_DAT_VIE);
        }
        if ((intDiaHoy+2)==INT_TBL_DAT_DOM )
        {
            arlColHid.add(""+INT_TBL_DAT_LUN);
            arlColHid.add(""+INT_TBL_DAT_MAR);
            arlColHid.add(""+INT_TBL_DAT_MIE);
            arlColHid.add(""+INT_TBL_DAT_JUE);
            arlColHid.add(""+INT_TBL_DAT_VIE);
            arlColHid.add(""+INT_TBL_DAT_SAB);
        }
        objTblModDat.setSystemHiddenColumns(arlColHid, tblDat);
        arlColHid=null;
        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        // vecAux.add("" + INT_TBL_EMP_CHK);
        objTblModDat.setColumnasEditables(vecAux);
        vecAux=null;
        //Configurar JTable: Editor de la tabla.
        //new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 1 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_LUN));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MAR));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_MIE));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_JUE));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VIE));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SAB));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_DOM));
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT1));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 2 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT2));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 3 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT3));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana 4 " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOT4));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;
        int intNumSem=4;
        INT_SEM_NUE=INT_TBL_DAT_TOT4;
        for (int i=2; i<=intNumMesCon; i++)
        {
            for (int x=0; x<4; x++)
            {
                INT_SEM_NUE++;
                intNumSem++;
                objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Semana "+intNumSem+" " );
                objTblHeaColGrpAmeSur.setHeight(16);
                objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_SEM_NUE));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
                objTblHeaColGrpAmeSur=null;
            }
        }
        objTblHeaColGrpAmeSur=new ZafTblHeaColGrp(" Total " );
        objTblHeaColGrpAmeSur.setHeight(16);
        objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_TOTFIN));
        objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
        objTblHeaColGrpAmeSur=null;
        Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
        objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
        tcmAux.getColumn( INT_TBL_DAT_LUN ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_MAR ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_MIE ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_JUE ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_VIE ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_SAB ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_DOM ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_TOT1 ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_TOT2 ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_TOT3 ).setCellRenderer(objTblCelRenLbl);
        tcmAux.getColumn( INT_TBL_DAT_TOT4 ).setCellRenderer(objTblCelRenLbl);
        INT_SEM_NUE=INT_TBL_DAT_TOT4;
        for (int i=2; i <= intNumMesCon; i++)
        {
            INT_SEM_NUE++;
            tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
            INT_SEM_NUE++;
            tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
            INT_SEM_NUE++;
            tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
            INT_SEM_NUE++;
            tcmAux.getColumn( INT_SEM_NUE ).setCellRenderer(objTblCelRenLbl);
        }
        tcmAux.getColumn( INT_TBL_DAT_TOTFIN ).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl=null;
        objTblTotDoc=new ZafTblTot(spnDat, spnDoctot , tblDat, tblTotDoc, intCol);
        tcmAux=null;
        new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
        objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
        return blnres;
    }

    private boolean cargarDatEmp()
    {
        boolean blnRes=false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try
        {
            conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                java.util.Vector vecData = new java.util.Vector();
                if (objZafParSis.getCodigoEmpresa()==objZafParSis.getCodigoEmpresaGrupo())
                    strSql="SELECT co_emp, tx_nom  FROM tbm_emp  where co_emp not in (0,3 ) order by co_emp  ";
                else
                    strSql="SELECT co_emp, tx_nom  FROM tbm_emp  where co_emp ="+objZafParSis.getCodigoEmpresa()+" order by co_emp  ";
                rstLoc=stmLoc.executeQuery(strSql);
                while (rstLoc.next())
                {
                    java.util.Vector vecReg=new java.util.Vector();
                    vecReg.add(INT_TBL_EMP_LIN, "");
                    vecReg.add(INT_TBL_EMP_CHK,  new Boolean(true) );
                    vecReg.add(INT_TBL_EMP_COD, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_EMP_NOM, rstLoc.getString("tx_nom") );
                    vecData.add(vecReg);
                }
                rstLoc.close();
                rstLoc=null;
                objTblMod.setData(vecData);
                tblEmp .setModel(objTblMod);
                strSql="select date_part('dow',CURRENT_DATE) as diahoy ";
                rstLoc=stmLoc.executeQuery(strSql);
                if (rstLoc.next())
                {
                    intDiaHoy=Integer.parseInt( rstLoc.getString("diahoy") );
                }
                if (intDiaHoy==0)
                    intDiaHoy=7;
                rstLoc.close();
                rstLoc=null;
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

    private boolean cargarMsnCabTblDat()
    {
        boolean blnRes=false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        try
        {
            conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                if ((1-intDiaHoy)<0)
                {
                    for (int i=(intDiaHoy-1); i>0; i--)
                    {
                        strSql+="  , (current_date-" + i + ") as difPenCob ";
                    }
                }
                int intDiaInc=0;
                for (intDiaInc=0; intDiaInc<=(7-intDiaHoy); intDiaInc++)
                {
                    strSql+="  , (current_date+" + intDiaInc + ") as difPenCob ";
                }
                intDiaInc=intDiaInc-1;
                strSql+="  ,( current_date  || ' a ' || (current_date+" + intDiaInc + ") ) as semana1 ";
                strSql+="  ,( (current_date+" + (intDiaInc+1) + ")  || ' a ' ||  (current_date+" + (intDiaInc+7) + ") ) as semana2 ";
                strSql+="  ,( (current_date+" + (intDiaInc+8) + ")  || ' a ' || (current_date+" + (intDiaInc+14) + ") ) as semana3 ";
                strSql+="  ,( (current_date+" + (intDiaInc+15) + ")  || ' a ' ||  (current_date+" + (intDiaInc+21) + ") ) as semana4 ";
                int intNumSem=4;
                int DiaDes=21;
                int DiaHas=21;
                //Generar desde el segundo mes en adelante.
                for (int i=2; i<=intNumMesCon; i++)
                {
                    intNumSem++;
                    DiaDes=DiaHas+1;
                    DiaHas=DiaDes+6;
                    strSql+="  ,( (current_date+" + (intDiaInc+DiaDes) + ")  || ' a ' ||  (current_date+" + (intDiaInc+DiaHas) + ") ) as semana" + intNumSem + " ";
                    intNumSem++;
                    DiaDes=DiaHas+1;
                    DiaHas=DiaHas+7;
                    strSql+="  ,( (current_date+" + (intDiaInc+DiaDes) + ")  || ' a ' || (current_date+" + (intDiaInc+DiaHas) + ") ) as semana" + intNumSem + " ";
                    intNumSem++;
                    DiaDes=DiaHas+1;
                    DiaHas=DiaHas+7;
                    strSql+="  ,( (current_date+" + (intDiaInc+DiaDes) + ")  || ' a ' ||  (current_date+" + (intDiaInc+DiaHas) + ") ) as semana" + intNumSem + " ";
                    intNumSem++;
                    DiaDes=DiaHas+1;
                    DiaHas=DiaHas+7;
                    strSql+="  ,( (current_date+" + (intDiaInc+DiaDes) + ")  || ' a ' || (current_date+" + (intDiaInc+DiaHas) + ") ) as semana" + intNumSem + " ";
                }
                strSql="SELECT  1  " + strSql + " ";
                int intNumSemMes=0;
                int intArr=-1;
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(2);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(3);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(4);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(5);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(6);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(7);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(8);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(9);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(10);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(11);
                    intArr++;
                    strMsnCol[intArr]=rstLoc.getString(12);
                    intNumSemMes = 4;
                    for (int i=2; i<=intNumMesCon; i++)
                    {
                        intNumSemMes++;
                        intArr++;
                        strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes );
                        intNumSemMes++;
                        intArr++;
                        strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes );
                        intNumSemMes++;
                        intArr++;
                        strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes );
                        intNumSemMes++;
                        intArr++;
                        strMsnCol[intArr]=rstLoc.getString("semana"+intNumSemMes );
                    }
                }
                rstLoc.close();
                rstLoc=null;
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

    private boolean cargarDatChqPenCob()
    {
        boolean blnRes=false;
        java.sql.Connection conn;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSql="";
        String strEmp="";
        String strSqlPri="";
        int intEmp=0;
        try
        {
            conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                java.util.Vector vecData = new java.util.Vector();
                for (int i=0; i<tblEmp.getRowCount(); i++)
                {
                    if (tblEmp.getValueAt(i, INT_TBL_EMP_CHK).toString().equals("true"))
                    {
                        if (intEmp==1)
                            strEmp+=" union all ";
                        strEmp+="select "+tblEmp.getValueAt(i, INT_TBL_EMP_COD).toString()+" as co_emp,  '"+tblEmp.getValueAt(i, INT_TBL_EMP_NOM).toString()+"' as nomemp ";
                        intEmp=1;
                    }
                }
                if (intEmp==1)
                {
                    if ( (1-intDiaHoy)<0)
                    {
                        for (int i=(intDiaHoy-1); i>0; i-- )
                        {
                            strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                            " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                            " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq = (current_date-"+i+") " +
                            " and a1.nd_valapl < a1.nd_monchq ) as difPenCob ";
                        }
                    }
                    int intDiaInc=0;
                    for (intDiaInc=0; intDiaInc<=(7-intDiaHoy); intDiaInc++)
                    {
                        strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                        " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                        " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq = (current_date+"+intDiaInc+") " +
                        " and a1.nd_valapl < a1.nd_monchq ) as difPenCob ";
                    }
                    intDiaInc=intDiaInc-1;
                    strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                    " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                    " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date  and (current_date+"+intDiaInc+") " +
                    " and a1.nd_valapl < a1.nd_monchq ) as semana1 ";
                    strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                    " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                    " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+1)+"  and (current_date+"+(intDiaInc+7)+") " +
                    " and a1.nd_valapl < a1.nd_monchq ) as semana2 ";
                    strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                    " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                    " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+8)+"  and (current_date+"+(intDiaInc+14)+") " +
                    " and a1.nd_valapl < a1.nd_monchq ) as semana3 ";
                    strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                    " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                    " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+15)+"  and (current_date+"+(intDiaInc+21)+") " +
                    " and a1.nd_valapl < a1.nd_monchq ) as semana4 ";
                    int intNumSem=4;
                    int DiaDes=21;
                    int DiaHas=21;
                    //Generar desde el segundo mes en adelante.
                    for (int i=2; i<=intNumMesCon; i++)
                    {
                        intNumSem++;
                        DiaDes=DiaHas+1;
                        DiaHas=DiaDes+6;
                        strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                        " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                        " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+DiaDes)+"  and (current_date+"+(intDiaInc+DiaHas)+") " +
                        " and a1.nd_valapl < a1.nd_monchq ) as semana"+intNumSem+" ";
                        intNumSem++;
                        DiaDes=DiaHas+1;
                        DiaHas=DiaHas+7;
                        strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                        " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                        " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+DiaDes)+"  and (current_date+"+(intDiaInc+DiaHas)+") " +
                        " and a1.nd_valapl < a1.nd_monchq ) as semana"+intNumSem+" ";
                        intNumSem++;
                        DiaDes=DiaHas+1;
                        DiaHas=DiaHas+7;
                        strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                        " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                        " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+DiaDes)+"  and (current_date+"+(intDiaInc+DiaHas)+") " +
                        " and a1.nd_valapl < a1.nd_monchq ) as semana"+intNumSem+" ";
                        intNumSem++;
                        DiaDes=DiaHas+1;
                        DiaHas=DiaHas+7;
                        strSql+="  ,( select   sum( (a1.nd_monchq - a1.nd_valapl ) ) as difPenCob  from tbm_cabrecdoc as a  " +
                        " inner join tbm_detrecdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                        " where a.co_emp=x.co_emp and a.co_tipdoc=94 and a.st_reg not in ('E','I')  and a1.fe_venchq between  current_date+"+(intDiaInc+DiaDes)+"  and (current_date+"+(intDiaInc+DiaHas)+") " +
                        " and a1.nd_valapl < a1.nd_monchq ) as semana"+intNumSem+" ";
                    }
                    int intNumSemMes = (4*intNumMesCon);
                    String strSumTotSem="";
                    for (int i=1; i<=intNumMesCon; i++)
                    {
                        if (!strSumTotSem.equals(""))
                        {
                            strSumTotSem+=" + "; intNumSemMes--;
                        }
                        strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end ) + ";
                        intNumSemMes--;
                        strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end ) + ";
                        intNumSemMes--;
                        strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end ) + ";
                        intNumSemMes--;
                        strSumTotSem+="( case when semana"+intNumSemMes+" is null then 0 else semana"+intNumSemMes+" end )  ";
                    }
                    strSqlPri="SELECT * ,(  "+strSumTotSem+"   ) as totalFin  FROM ( " +
                    " SELECT * "+strSql+" FROM ( " +
                    "" +strEmp+ " "+
                    " ) AS x "+
                    " ) AS x ";
                    rstLoc=stmLoc.executeQuery(strSqlPri);
                    while (rstLoc.next())
                    {
                        java.util.Vector vecReg = new java.util.Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD, rstLoc.getString(1) );
                        vecReg.add(INT_TBL_DAT_NOM, rstLoc.getString(2) );
                        vecReg.add(INT_TBL_DAT_LUN, rstLoc.getString(3) );
                        vecReg.add(INT_TBL_DAT_MAR, rstLoc.getString(4) );
                        vecReg.add(INT_TBL_DAT_MIE, rstLoc.getString(5) );
                        vecReg.add(INT_TBL_DAT_JUE, rstLoc.getString(6) );
                        vecReg.add(INT_TBL_DAT_VIE, rstLoc.getString(7) );
                        vecReg.add(INT_TBL_DAT_SAB, rstLoc.getString(8) );
                        vecReg.add(INT_TBL_DAT_DOM, rstLoc.getString(9) );
                        vecReg.add(INT_TBL_DAT_TOT1, rstLoc.getString(10) );
                        vecReg.add(INT_TBL_DAT_TOT2, rstLoc.getString(11) );
                        vecReg.add(INT_TBL_DAT_TOT3, rstLoc.getString(12) );
                        vecReg.add(INT_TBL_DAT_TOT4, rstLoc.getString(13) );
                        INT_SEM_NUE=INT_TBL_DAT_TOT4;
                        intNumSemMes = 4;
                        for (int i=2;i<=intNumMesCon;i++)
                        {
                            INT_SEM_NUE++;
                            intNumSemMes++;
                            vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes ) );
                            INT_SEM_NUE++;
                            intNumSemMes++;
                            vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes) );
                            INT_SEM_NUE++;
                            intNumSemMes++;
                            vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes) );
                            INT_SEM_NUE++;
                            intNumSemMes++;
                            vecReg.add(INT_SEM_NUE, rstLoc.getString("semana"+intNumSemMes) );
                        }
                        vecReg.add(INT_TBL_DAT_TOTFIN, rstLoc.getString("totalFin") );
                        vecData.add(vecReg);
                    }
                    rstLoc.close();
                    rstLoc=null;
                    objTblModDat.setData(vecData);
                    tblDat .setModel(objTblModDat);
                    objTblTotDoc.calcularTotales();
                }
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFilMes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spnMesCon = new javax.swing.JSpinner();
        sppRpt = new javax.swing.JSplitPane();
        panEmp = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        panDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnDoctot = new javax.swing.JScrollPane();
        tblTotDoc = new javax.swing.JTable();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panFilMes.setPreferredSize(new java.awt.Dimension(685, 28));
        panFilMes.setLayout(null);

        jLabel1.setText("Meses a consultar:");
        panFilMes.add(jLabel1);
        jLabel1.setBounds(4, 4, 120, 20);
        panFilMes.add(spnMesCon);
        spnMesCon.setBounds(124, 4, 80, 20);

        panFil.add(panFilMes, java.awt.BorderLayout.NORTH);

        sppRpt.setBorder(null);
        sppRpt.setDividerLocation(110);
        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.8);
        sppRpt.setOneTouchExpandable(true);

        panEmp.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de empresas"));
        panEmp.setPreferredSize(new java.awt.Dimension(324, 120));
        panEmp.setLayout(new java.awt.BorderLayout());

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblEmp);

        panEmp.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        sppRpt.setTopComponent(panEmp);

        panDat.setPreferredSize(new java.awt.Dimension(657, 190));
        panDat.setLayout(new java.awt.BorderLayout());

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

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        spnDoctot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotDoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDoctot.setViewportView(tblTotDoc);

        panDat.add(spnDoctot, java.awt.BorderLayout.SOUTH);

        sppRpt.setBottomComponent(panDat);

        panFil.add(sppRpt, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

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

        getContentPane().add(panBot, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        cargarConfiguracion();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        intNumMesCon=Integer.parseInt(spnMesCon.getModel().getValue().toString());
        if (intNumMesCon>0)
        {
            configurarFormDat();
            cargarMsnCabTblDat();
            cargarDatChqPenCob();
        }
        else
        {
            MensajeInf("NÚMERO DE MESES A CONSULTAR TIENE QUE SER MAYOR A 0 ");
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void MensajeInf(String strMensaje)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panEmp;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilMes;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDoctot;
    private javax.swing.JSpinner spnMesCon;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTable tblTotDoc;
    // End of variables declaration//GEN-END:variables



public String _getNomDat(int intCol){
  String strNom="";

  strNom="Fecha de corte: "+strMsnCol[intCol];

  return strNom;
}


    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            int intArr=-1;
            intArr++;
            if (intCol==INT_TBL_DAT_LUN)
            {
                strMsg=_getNomDat(intArr);
            }
            else
                intArr++;
            if (intCol==INT_TBL_DAT_MAR)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_MIE)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_JUE)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_VIE)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_SAB)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_DOM)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_TOT1)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_TOT2)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_TOT3)
                strMsg=_getNomDat(intArr);
            else
                intArr++;
            if (intCol==INT_TBL_DAT_TOT4)
                strMsg=_getNomDat(intArr);
            INT_SEM_NUE=INT_TBL_DAT_TOT4;
            for (int i=2; i<=intNumMesCon; i++)
            {
                INT_SEM_NUE++;
                intArr++;
                if ( intCol==INT_SEM_NUE)
                {
                    strMsg=_getNomDat(intArr);
                    break;
                }
                INT_SEM_NUE++;
                intArr++;
                if ( intCol==INT_SEM_NUE)
                {
                    strMsg=_getNomDat(intArr);
                    break;
                }
                INT_SEM_NUE++;
                intArr++;
                if ( intCol==INT_SEM_NUE)
                {
                    strMsg=_getNomDat(intArr);
                    break;
                }
                INT_SEM_NUE++;
                intArr++;
                if ( intCol==INT_SEM_NUE)
                {
                    strMsg=_getNomDat(intArr);
                    break;
                }
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
