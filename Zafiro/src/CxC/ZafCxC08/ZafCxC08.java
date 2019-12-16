/*
 * ZafCxC08.java
 *
 * Created on 22 de septiembre de 2005, 9:08
 */

package CxC.ZafCxC08;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
/**
 *
 * @author  jsalazar
 */
public class ZafCxC08 extends javax.swing.JInternalFrame {
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;        //Render: Presentar JLabel en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;        //Editor: JTextField en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut objTblCelEdiBut;        //Editor: JButton en celda.
    private Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon objTblCelEdiTxtCon;  //Editor: JTextField de consulta en celda.
    private ZafColNumerada objColNum;
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafParSis objZafParSis;
    private ZafUtil objUti;        
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafUtil.ZafCliente_dat objCliente;
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private Librerias.ZafUtil.ZafCtaCtb objCtaCtb;
    private Librerias.ZafUtil.UltDocPrint objUltDocPrint;
    private Librerias.ZafAsiDia.ZafAsiDia objDiario;
    private javax.swing.JInternalFrame jfrThis;
    private Vector vecDat,vecCab,vecDetDiario;
    private CxC.ZafCxC08.ZafCxC08_01 objAnexo1;
    private CxC.ZafCxC08.ZafCxC08_02 objAnexo2;
    private java.sql.ResultSet rstCab;
    private boolean blnHayCam = false; //Detecta ke se hizo cambios en el documento
    LisTextos objlisCambios;     // Instancia de clase que detecta cambios
    mitoolbar objTooBar;
    ButFndTbl ObjChq;
    ButFndDoc ObjDoc;      

    //Constantes
    private final int INT_TBL_LINEA     = 0;
    private final int INT_TBL_COD_BCO   = 1;    
    private final int INT_TBL_NOM_BCO   = 2;
    private final int INT_TBL_NUM_CTA   = 3;
    private final int INT_TBL_OBJ_DOC   = 4;
    private final int INT_TBL_NUM_CHQ   = 5;    
    private final int INT_TBL_BUT_CHQ   = 6;
    private final int INT_TBL_FEC_REC   = 7;
    private final int INT_TBL_FEC_VEN   = 8;
    private final int INT_TBL_VAL_CHQ   = 9;
    private final int INT_TBL_COD_PRO   = 10;
    private final int INT_TBL_BUT_PRO   = 11;
    private final int INT_TBL_TIP_PRO   = 12;
    private final int INT_TBL_BUT_REL   = 13;
    //VECTOR OBJ_CHQ
    private final int INT_VEC_TIP_DOC   = 0;
    private final int INT_VEC_COD_DOC   = 1;
    private final int INT_VEC_COD_REG   = 2;
  
            
    /** Creates new form ZafCxC08 */
    public ZafCxC08(ZafParSis obj) {
        initComponents();
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis    = this;
//            jfrThis.setTitle(objZafParSis.getNombreMenu());
            
            this.setTitle(objZafParSis.getNombreMenu() + " v0.1");
            lblTitForm.setText(objZafParSis.getNombreMenu());
            
            objTipDoc  = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
            objCtaCtb  = new Librerias.ZafUtil.ZafCtaCtb(objZafParSis);
            objUti     = new ZafUtil();
            objDiario  = new Librerias.ZafAsiDia.ZafAsiDia(objZafParSis);
            objCliente = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);  
            objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objZafParSis);
            objAnexo1  = null;
            objTooBar = new mitoolbar(this);
            cargaTipoDocPredeterminado();
            panAsiDia.add(objDiario,java.awt.BorderLayout.CENTER);
            panTooBar.add(objTooBar,"Center");
            txtCodTipDoc.setVisible(false);
            txtUsoCta.setVisible(false);
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(516, 4, 148, 20);
            txtNomTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtDetTipDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodCta.setBackground(objZafParSis.getColorCamposObligatorios());
            txtDetCta.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodCli.setBackground(objZafParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objZafParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
            txtNumDoc.setBackground(objZafParSis.getColorCamposSistema());
            txtTot.setBackground(objZafParSis.getColorCamposSistema());
            txtTot.setEnabled(false);
            if (!Configurar_tabla())
                exitForm();
            this.setBounds(10,10, 700, 450);
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
    }

    private void exitForm(){
        dispose();
    }
    private boolean Configurar_tabla() {
        boolean blnRes=false;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_COD_BCO,"Cod.Bco.");
            vecCab.add(INT_TBL_NOM_BCO,"Banco");            
            vecCab.add(INT_TBL_NUM_CTA,"Num.Cta.");
            vecCab.add(INT_TBL_OBJ_DOC,"");
            vecCab.add(INT_TBL_NUM_CHQ,"Num.Chq.");
            vecCab.add(INT_TBL_BUT_CHQ,"");
            vecCab.add(INT_TBL_FEC_REC,"Fec.Rec.");
            vecCab.add(INT_TBL_FEC_VEN,"Fec.Vto.");
            vecCab.add(INT_TBL_VAL_CHQ,"Valor");
            vecCab.add(INT_TBL_COD_PRO,"Cod.Pro.");            
            vecCab.add(INT_TBL_BUT_PRO,"");            
            vecCab.add(INT_TBL_TIP_PRO,"Tipo de protesto");            
            vecCab.add(INT_TBL_BUT_REL,"Doc.Rel.");            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_NUM_CHQ, objTblMod.INT_COL_INT, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_COD_PRO, objTblMod.INT_COL_INT, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_NUM_CHQ);
            arlAux.add("" + INT_TBL_COD_PRO);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objZafParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LINEA);
            //Configurar JTable: Establecer el menú de contexto.
            new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(30);          
            tcmAux.getColumn(INT_TBL_COD_BCO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOM_BCO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FEC_REC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_VAL_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_COD_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_TIP_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BUT_REL).setPreferredWidth(20);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setResizable(false);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setResizable(false);
            tcmAux.getColumn(INT_TBL_BUT_REL).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_OBJ_DOC).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUT_CHQ);
            vecAux.add("" + INT_TBL_BUT_PRO);
            vecAux.add("" + INT_TBL_BUT_REL);
            vecAux.add("" + INT_TBL_NUM_CHQ);
            vecAux.add("" + INT_TBL_COD_PRO);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());            
            tcmAux.getColumn(INT_TBL_TIP_PRO).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;

            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_COD_BCO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_BCO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NUM_CTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_REC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_VEN).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
           
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("######",true,true);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COD_PRO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_CHQ).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_BUT_REL).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            //Armar la sentencia SQL.            
            String strSQL="";
            strSQL=" Select co_reg,tx_deslar from tbm_var where co_grp=9";            
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_COD_PRO;
            intColTbl[1]=INT_TBL_TIP_PRO;
            objTblCelEdiTxtCon=new Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon("Listado tipos de protesto", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL,  "co_reg,tx_deslar"," Codigo,Descripcion", intColVen, intColTbl);
            objTblCelEdiTxtCon.setCampoBusqueda("co_reg", objTblCelEdiTxtCon.INT_CAM_NUM);
            tcmAux.getColumn(INT_TBL_COD_PRO).setCellEditor(objTblCelEdiTxtCon);
            objTblCelEdiTxtCon=null;

            objTblCelEdiBut=new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut("Listado tipos de protesto", tblDat, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL,  "co_reg,tx_deslar"," Codigo,Descripcion", intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_BUT_PRO).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut=null;
            intColVen=null;
            intColTbl=null;
//Botones foraneos
            ObjChq = new ButFndTbl(tblDat,INT_TBL_BUT_CHQ);
            ObjDoc = new ButFndDoc(tblDat,INT_TBL_BUT_REL);      
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt();
            
            objTblCelEdiTxt=null;
            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_COD_PRO).setCellRenderer(objTblCelRenLbl);            
            objTblCelEdiTxt=null;
            //Libero los objetos auxiliares.
            tcmAux=null;
             
            //Configurar JTable: Centrar columnas.
            blnRes=true;    
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(jfrThis,e);
        }
        return blnRes;                        
    }
    private void cargaTipoDocPredeterminado()
    {
        objTipDoc.DocumentoPredeterminado();
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());
        if (objTooBar.getEstado()=='n' ) cargaNum_Doc_Preimpreso();
    }
    private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtNomTipDoc.setText(objTipDoc.gettx_descor());
        txtDetTipDoc.setText(objTipDoc.gettx_deslar());        
        if (objTooBar.getEstado()=='n' ) cargaNum_Doc_Preimpreso();
    }
    private void FndTipoDocumento(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFndTipDoc = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Nombre,Descripcion","cabtip.co_tipdoc,tx_descor,tx_deslar",
           " select cabtip.co_tipdoc,tx_descor,tx_deslar from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc) where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and co_mnu = " + objZafParSis.getCodigoMenu()  , strBusqueda, 
                 objZafParSis.getStringConexion(), 
                 objZafParSis.getUsuarioBaseDatos(), 
                 objZafParSis.getClaveBaseDatos()
            );               
        objFndTipDoc.setTitle("Listado Tipos de Documentos");
        
         switch (TipoBusqueda)
         {            
             case 1:
                 objFndTipDoc.setSelectedCamBus(1);
                if(!objFndTipDoc.buscar("docprg.tx_descor = '" + txtNomTipDoc.getText()+ "'"))
                    objFndTipDoc.show();
                 break;
             case 2:
                 objFndTipDoc.setSelectedCamBus(2);
                if(!objFndTipDoc.buscar("docprg.tx_deslar = '" + txtDetTipDoc.getText()+"'"))
                    objFndTipDoc.show();                 
                 break;
             default:
                 objFndTipDoc.show();
                 break;
         }
        if(!objFndTipDoc.GetCamSel(1).equals("")){
            cargarCabTipoDoc(Integer.parseInt(objFndTipDoc.GetCamSel(1)));                   
            if (objTooBar.getEstado()=='n' ) cargaNum_Doc_Preimpreso();
        }
    }
    private void FndProveedor(String strBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
         new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Nombre,Direccion,RUC/CI","co_cli,tx_nom,tx_dir",
           "select co_cli,tx_nom,tx_dir,tx_ide from tbm_cli where co_emp = " + objZafParSis.getCodigoEmpresa(), strBusqueda, 
                 objZafParSis.getStringConexion(), 
                 objZafParSis.getUsuarioBaseDatos(), 
                 objZafParSis.getClaveBaseDatos()
           );        
         
         objFnd.setTitle("Listado Proveedores");

         if(strBusqueda.equals("")){
            objFnd.show();
         }else{
             objFnd.setSelectedCamBus(0);
             if(objUti.isNumero(txtCodCli.getText())){
                if(!objFnd.buscar("co_cli = " + txtCodCli.getText()))
                    objFnd.show();
             }else{
                 objFnd.setSelectedCamBus(1);
                 if(txtNomCli.getText().equals(""))
                    objFnd.show();
                 else{
                     if(!objFnd.buscar("tx_nom = '" + txtNomCli.getText() + "'"))
                        objFnd.show();
                     }
                }
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtCodCli.setText(objFnd.GetCamSel(1));
            txtNomCli.setText(objFnd.GetCamSel(2));
        }
         if(!txtCodCli.getText().equals(""))
             objCliente.setCliente(Integer.parseInt(txtCodCli.getText()));
         else {
             if (objCliente==null) objCliente = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis);       
         }
    }

    private void FndCta(String strBusqueda,int TipoBusqueda)
    {
        Librerias.ZafConsulta.ZafConsulta  objFnd = 
        new Librerias.ZafConsulta.ZafConsulta( javax.swing.JOptionPane.getFrameForComponent(this),
           "Codigo,Alias,Descripcion,Debe/Haber","cta.co_cta , tx_codcta,tx_deslar , tx_ubicta",
             "select  cta.co_cta , tx_codcta,tx_deslar , tx_ubicta from tbm_placta as cta left outer join tbm_dettipdoc as  det on( cta.co_emp = det.co_emp and cta.co_cta = det.co_cta)" +
                " where   cta.co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                        " co_loc     =  " + objZafParSis.getCodigoLocal()   + " and " +
                        " co_tipdoc  = " + objTipDoc.getco_tipdoc()  ,strBusqueda, 
         objZafParSis.getStringConexion(), 
         objZafParSis.getUsuarioBaseDatos(), 
         objZafParSis.getClaveBaseDatos()
         );        

        objFnd.setTitle("Listado Cuentas por Documento");
        
        switch (TipoBusqueda)
        {
            case 1:                                        
                 objFnd.setSelectedCamBus(0);
                if(!objFnd.buscar("cta.co_cta = " + txtCodCta.getText()))
                    objFnd.show();
                 break;
            case 2:
                 objFnd.setSelectedCamBus(2);
                if(!objFnd.buscar("tx_deslar = '" + txtDetTipDoc.getText()+"'"))
                    objFnd.show();                 
                 break;
            default:
                objFnd.show();
                break;             
         }
        if(!objFnd.GetCamSel(1).equals("")){
            txtUsoCta.setText(objFnd.GetCamSel(4).toString());
            txtCodCta.setText(objFnd.GetCamSel(1).toString());
            txtDetCta.setText(objFnd.GetCamSel(3).toString());
            if (Double.parseDouble(txtTot.getText())>0) generaAsiento();
        }
    }
    
    private boolean Verifica()
    {
        if (txtCodCli.getText().equals("")){
            MensajeInf("Ingrese cliente a consultar"); 
            return false;
        }
//        if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_CHQ)==null || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_CHQ).equals("")){            
//            return false;
//        }                         
        return true;
    }
    private boolean cargaAnexo1(int intColCon)
    {
        try{
            if (!Verifica()) return false;
            objAnexo1  = new CxC.ZafCxC08.ZafCxC08_01(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis,objTipDoc,objCliente);
            objAnexo1.clear();
            objAnexo1.setCliente(Integer.parseInt(txtCodCli.getText()));
            objAnexo1.setCheque(Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_CHQ)==null || tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_CHQ).equals("")?"0":tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_CHQ).toString()));
            Vector vecTmp = objAnexo1.visualiza(intColCon);
            if (vecTmp!=null){
                tblDat.setValueAt((Integer)vecTmp.get(INT_TBL_COD_BCO),tblDat.getSelectedRow(),INT_TBL_COD_BCO);
                tblDat.setValueAt(vecTmp.get(INT_TBL_NOM_BCO).toString(),tblDat.getSelectedRow(),INT_TBL_NOM_BCO);
                tblDat.setValueAt(vecTmp.get(INT_TBL_NUM_CTA).toString(),tblDat.getSelectedRow(),INT_TBL_NUM_CTA);
                tblDat.setValueAt(vecTmp.get(INT_TBL_NUM_CHQ).toString(),tblDat.getSelectedRow(),INT_TBL_NUM_CHQ);
                tblDat.setValueAt((Vector)vecTmp.get(INT_TBL_OBJ_DOC),tblDat.getSelectedRow(),INT_TBL_OBJ_DOC);
                tblDat.setValueAt(vecTmp.get(INT_TBL_NUM_CHQ).toString(),tblDat.getSelectedRow(),INT_TBL_NUM_CHQ);
                tblDat.setValueAt(vecTmp.get(INT_TBL_FEC_REC).toString(),tblDat.getSelectedRow(),INT_TBL_FEC_REC);
                tblDat.setValueAt(vecTmp.get(INT_TBL_FEC_VEN).toString(),tblDat.getSelectedRow(),INT_TBL_FEC_VEN);
                tblDat.setValueAt((Double)vecTmp.get(INT_TBL_VAL_CHQ),tblDat.getSelectedRow(),INT_TBL_VAL_CHQ);
            }else{
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_COD_BCO);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_NOM_BCO);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_NUM_CTA);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_NUM_CHQ);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_OBJ_DOC);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_NUM_CHQ);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_FEC_REC);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_FEC_VEN);
                tblDat.setValueAt(null,tblDat.getSelectedRow(),INT_TBL_VAL_CHQ);
            }
            tblDat.setValueAt(ObjChq, tblDat.getSelectedRow(), INT_TBL_BUT_CHQ);
            tblDat.setValueAt(ObjDoc, tblDat.getSelectedRow(), INT_TBL_BUT_REL);
            calculaTotal();
            generaAsiento();               
            objAnexo1 = null;
        }catch(Exception e){            
            objUti.mostrarMsgErr_F1(this, e);
            return false;
        }
        return true;
    }
    private boolean cargaAnexo2()
    {
        boolean blnRes = false;
        try{
            if (!Verifica()) return false;
            if (tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_NUM_CHQ)==null){
                MensajeInf("No ha ingresado cheque para revisar documentos relacionados.");
                return false;
            }
            Vector vecTmp;
            vecTmp = ((Vector)tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_OBJ_DOC));
            objAnexo2 = new ZafCxC08_02(objZafParSis,vecTmp);
            objAnexo2.show();
            objAnexo2 = null;
            vecTmp = null;
            blnRes = true;
        }catch(Exception e){            
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
                
    }
    private void calculaTotal()
    {
        try{
        double dbltotal=0;
        for (int i=0;i<tblDat.getRowCount();i++){
            if (tblDat.getValueAt(i,INT_TBL_VAL_CHQ)!=null){
                dbltotal =objUti.redondeo(dbltotal + Double.parseDouble(tblDat.getValueAt(i,INT_TBL_VAL_CHQ).toString()),6);
            }
        }
        txtTot.setText(""+dbltotal);        
        }catch (NumberFormatException e){
            txtTot.setText("0.0");
            objUti.mostrarMsgErr_F1(this, e);
        }catch(Exception e){            
            txtTot.setText("0.0");
            objUti.mostrarMsgErr_F1(this, e);         
        }
    }        
    private void MensajeInf(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- CxC";
            obj.showMessageDialog(jfrThis,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    private void clnTextos(){
        txtCodDoc.setText("");
        txtCodCli.setText("");
        txtCodTipDoc.setText("");
        txtNomCli.setText("");
        txtDetTipDoc.setText("");
        txtCodCta.setText("");
        txtDetCta.setText("");
        txtUsoCta.setText("");        
        txtTot.setText("0.0");
        txtNumDoc.setText("");        
        txaObs1.setText("");
        txaObs2.setText("");
        
        vecDat.removeAllElements();
        tblDat.removeAll();
        objDiario.inicializar();
        cargaTipoDocPredeterminado();
        objTblMod.removeAllRows();
        objTblMod.setRowCount(1); 
        tblDat.setValueAt(ObjChq, 0, INT_TBL_BUT_CHQ);
        tblDat.setValueAt(ObjDoc, 0, INT_TBL_BUT_REL);        
    }

    private void setEditable(boolean editable)
   {
        if (editable==true){
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }else{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }       
   }

    /**
     * Metodo que genera vector de diario para la clase ZafAsiDia
     * Genera segun el tipo de documento y su naturaleza, bodegas y si genera o no IVA
     *@autor: jsalazar
     */
    private void generaAsiento(){
        java.util.Vector vecReg;
        
        /* Vector RegDiario */
        int INT_LINEA      = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON  = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE   = 5; //5) Debe. 
        int INT_VEC_HABER  = 6; //6) Haber. 
        int INT_VEC_REF    = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        
        if (vecDetDiario==null)
            vecDetDiario = new java.util.Vector();
        else
            vecDetDiario.removeAllElements();

        if(txtUsoCta.getText().equals("H"))
        {
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer(objTipDoc.getco_ctadeb()));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCtb(objTipDoc.getco_ctadeb()));
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCta(objTipDoc.getco_ctadeb()));
            vecReg.add(INT_VEC_DEBE, new Double((txtTot.getText().equals("")?0:Double.parseDouble(txtTot.getText())))); //total
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecDetDiario.add(vecReg);

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer(txtCodCta.getText().equals("")?"0":txtCodCta.getText()));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCtb(Integer.parseInt(txtCodCta.getText().equals("")?"0":txtCodCta.getText())));
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCta(Integer.parseInt(txtCodCta.getText().equals("")?"0":txtCodCta.getText())));
            vecReg.add(INT_VEC_DEBE, new Double(0)); 
            vecReg.add(INT_VEC_HABER, new Double((txtTot.getText().equals("")?0:Double.parseDouble(txtTot.getText()))));
            vecReg.add(INT_VEC_REF, null);
            vecDetDiario.add(vecReg);       
        }else{            
            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer(txtCodCta.getText().equals("")?"0":txtCodCta.getText()));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCtb(Integer.parseInt(txtCodCta.getText().equals("")?"0":txtCodCta.getText())));
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCta(Integer.parseInt(txtCodCta.getText().equals("")?"0":txtCodCta.getText())));            
            vecReg.add(INT_VEC_DEBE, new Double((txtTot.getText().equals("")?0:Double.parseDouble(txtTot.getText()))));
            vecReg.add(INT_VEC_HABER, new Double(0)); 
            vecReg.add(INT_VEC_REF, null);
            vecDetDiario.add(vecReg);       

            vecReg = new java.util.Vector();
            vecReg.add(INT_LINEA, null);                
            vecReg.add(INT_VEC_CODCTA,new Integer(objTipDoc.getco_ctahab()));
            vecReg.add(INT_VEC_NUMCTA,objCtaCtb.getNumCtaCtb(objTipDoc.getco_ctahab()));
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA,objCtaCtb.getNomCta(objTipDoc.getco_ctahab()));
            vecReg.add(INT_VEC_DEBE, new Double(0));             
            vecReg.add(INT_VEC_HABER, new Double((txtTot.getText().equals("")?0:Double.parseDouble(txtTot.getText()))));            
            vecReg.add(INT_VEC_REF, null);
            vecDetDiario.add(vecReg);            

        }

        objDiario.setDetalleDiario(vecDetDiario);
        if (objTooBar.getEstado()=='n') objDiario.setGlosa("CxC " + objTipDoc.gettx_descor()+" #" + (txtNumDoc.getText().equals("")?"0":txtNumDoc.getText())+ " Cli: " + (txtCodCli.getText().equals("")?"0":txtCodCli.getText()));
    }
    

 private boolean validaCampos()
    {
        if (txtCodTipDoc.getText().equals("")){
            MensajeInf("Asigne el tipo de documento a almacenar");
            return false;
        }
        if (txtCodCli.getText().equals("")){
            MensajeInf("Asigne el Cliente");
            return false;
        }
        if (txtCodCta.getText().equals("")){
            MensajeInf("Asigne Cuenta");
            return false;
        }
        if (txtNumDoc.getText().equals("")){
            MensajeInf("Asigne Numero del documento");
            return false;            
        }
        if (txtTot.getText().equals("")){
            MensajeInf("No tiene valor asignado el documento por pagar");
            return false;            
        }
        if (!txtFecDoc.isFecha()){
            MensajeInf("Asigne fecha del documento");
            return false;                        
        }

        objTblMod.removeEmptyRows();
        if (!objTblMod.isAllRowsComplete()){
            MensajeInf("Existen datos incompletos en el cheque protesto. \nRevise filas sombreadas");
            return false;                                    
        }
        if (!objDiario.isDiarioCuadrado()){
            MensajeInf("Asiento descuadrado.");
            tabCxC.setSelectedIndex(1);
            return false;                                    
        }
        if (!objDiario.isDocumentoCuadrado(txtTot.getText())){
            MensajeInf("Asiento y Total del documento no cuadran.\n Por favor verifique!!");
            tabCxC.setSelectedIndex(1);
            return false;                                                
        }
        return true;
    }      
    private void cargaNum_Doc_Preimpreso(){
        int intNumeroDoc = objUltDocPrint.getUltDoc(objTipDoc.getco_tipdoc());
        intNumeroDoc++;
        txtNumDoc.setText("" + intNumeroDoc);        
    }
 
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        panFrm = new javax.swing.JPanel();
        lblTitForm = new javax.swing.JLabel();
        tabCxC = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        txtUsoCta = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        txtNomTipDoc = new javax.swing.JTextField();
        txtCodCta = new javax.swing.JTextField();
        txtDetCta = new javax.swing.JTextField();
        txtDetTipDoc = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        butTipDoc = new javax.swing.JButton();
        butCli = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        txtNumDoc = new javax.swing.JTextField();
        panCen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panTooBar = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
        panFrm.setLayout(new java.awt.BorderLayout());

        lblTitForm.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTitForm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitForm.setText("T\u00edtulo");
        panFrm.add(lblTitForm, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setLayout(null);

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 84));
        jLabel2.setText("Tipo de documento:");
        jLabel2.setToolTipText("Tipo de documento");
        panGenCab.add(jLabel2);
        jLabel2.setBounds(0, 4, 100, 20);

        jLabel3.setText("Cuenta:");
        jLabel3.setToolTipText("Cuenta");
        panGenCab.add(jLabel3);
        jLabel3.setBounds(0, 24, 100, 20);

        jLabel4.setText("Cliente:");
        jLabel4.setToolTipText("Cliente");
        panGenCab.add(jLabel4);
        jLabel4.setBounds(0, 44, 100, 20);

        jLabel5.setText("C\u00f3digo del documento:");
        jLabel5.setToolTipText("C\u00f3digo del documento");
        panGenCab.add(jLabel5);
        jLabel5.setBounds(0, 64, 100, 20);

        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 64, 80, 20);

        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });

        panGenCab.add(txtCodCli);
        txtCodCli.setBounds(100, 44, 56, 20);

        panGenCab.add(txtUsoCta);
        txtUsoCta.setBounds(68, 24, 32, 20);

        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        txtNomTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipDocActionPerformed(evt);
            }
        });

        panGenCab.add(txtNomTipDoc);
        txtNomTipDoc.setBounds(100, 4, 56, 20);

        txtCodCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCtaActionPerformed(evt);
            }
        });

        panGenCab.add(txtCodCta);
        txtCodCta.setBounds(100, 24, 80, 20);

        txtDetCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetCtaActionPerformed(evt);
            }
        });

        panGenCab.add(txtDetCta);
        txtDetCta.setBounds(180, 24, 212, 20);

        txtDetTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDetTipDocActionPerformed(evt);
            }
        });

        panGenCab.add(txtDetTipDoc);
        txtDetTipDoc.setBounds(156, 4, 236, 20);

        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });

        panGenCab.add(txtNomCli);
        txtNomCli.setBounds(156, 44, 236, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });

        panGenCab.add(butCta);
        butCta.setBounds(392, 24, 20, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });

        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(392, 4, 20, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });

        panGenCab.add(butCli);
        butCli.setBounds(392, 44, 20, 20);

        jLabel6.setText("Fecha del documento:");
        jLabel6.setToolTipText("Fecha del documento");
        panGenCab.add(jLabel6);
        jLabel6.setBounds(416, 4, 100, 20);

        jLabel7.setText("N\u00famero de documento:");
        jLabel7.setToolTipText("N\u00famero de documento");
        panGenCab.add(jLabel7);
        jLabel7.setBounds(416, 24, 100, 20);

        jLabel8.setText("Valor del documento:");
        jLabel8.setToolTipText("Valor del documento");
        panGenCab.add(jLabel8);
        jLabel8.setBounds(416, 44, 100, 20);

        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtTot);
        txtTot.setBounds(516, 44, 148, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });

        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(516, 24, 148, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

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

        panCen.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGen.add(panCen, java.awt.BorderLayout.CENTER);

        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        lblObs1.setText("Observaci\u00f3n1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observaci\u00f3n2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabCxC.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());

        tabCxC.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabCxC, java.awt.BorderLayout.CENTER);

        panTooBar.setLayout(new java.awt.BorderLayout());

        panFrm.add(panTooBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
        FndTipoDocumento("",0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
        // TODO add your handling code here:
        if (objTooBar.getEstado()=='n') objDiario.setGlosa("CxC " + objTipDoc.gettx_descor()+" #" + (txtNumDoc.getText().equals("")?"0":txtNumDoc.getText())+ " Cli: " + (txtCodCli.getText().equals("")?"0":txtCodCli.getText()));
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtDetCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDetCtaActionPerformed
        // TODO add your handling code here:
        FndCta(txtDetCta.getText(),2);     
    }//GEN-LAST:event_txtDetCtaActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        // TODO add your handling code here:
        FndCta("",0);     
    }//GEN-LAST:event_butCtaActionPerformed

    private void txtCodCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCtaActionPerformed
        // TODO add your handling code here:
        FndCta(txtCodCta.getText(),1);        
    }//GEN-LAST:event_txtCodCtaActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        FndProveedor("");
    }//GEN-LAST:event_butCliActionPerformed

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        FndProveedor(txtNomCli.getText());
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        FndProveedor(txtCodCli.getText());
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
        // TODO add your handling code here:
        FndTipoDocumento(txtNomTipDoc.getText(),1);
    }//GEN-LAST:event_txtNomTipDocActionPerformed

    private void txtDetTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDetTipDocActionPerformed
        // TODO add your handling code here:
        FndTipoDocumento(txtDetTipDoc.getText(),2);
    }//GEN-LAST:event_txtDetTipDocActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCta;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTitForm;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panTooBar;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabCxC;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDetCta;
    private javax.swing.JTextField txtDetTipDoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtTot;
    private javax.swing.JTextField txtUsoCta;
    // End of variables declaration//GEN-END:variables
   

    private String sqlFil()
    {
        String strSQL = "";
        
        if (!txtCodTipDoc.getText().equals("")){
            strSQL += " and cab.co_tipdoc = " + txtCodTipDoc.getText();
        }else{
            cargaTipoDocPredeterminado();
            strSQL += " and cab.co_tipdoc = " + txtCodTipDoc.getText();
        }
        if (!txtCodCli.getText().equals("")){
            strSQL += " and co_cli = " + txtCodCli.getText();
        }
        if (!txtCodDoc.getText().equals("")){
            strSQL += " and cab.co_doc = " + txtCodDoc.getText();
        }
        if (!txtNumDoc.getText().equals("")){
            strSQL += " and ne_numdoc = " + txtNumDoc.getText();
        }
        if (!txtCodCta.getText().equals("")){
            strSQL += " and cab.co_cta = " + txtCodCta.getText();
        }
        if(txtFecDoc.isFecha()){
                int FecSql[] = txtFecDoc.getFecha(txtFecDoc.getText());
                String strFecSql = "#" + FecSql[2] + "/" + FecSql[1] + "/" +FecSql[0] +"#" ;
                strSQL += " and cab.fe_doc = '" +  strFecSql + "'";
        }    
         return strSQL;
    }
    private boolean _consultar(String strFiltro){
        try{
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            try
            {
                if (con!=null){
                    java.sql.Statement stm  = con.createStatement();                                        
                    String strSQL = "  select cab.co_emp,cab.co_loc,cab.co_tipdoc,cab.co_doc,ne_numdoc,fe_doc,co_cli,nd_tot,cab.co_cta,tx_deslar as tx_descta,tx_ubicta,cab.tx_obs1,cab.tx_obs2,cab.st_reg " +
                                    " from tbm_cabmovinv as cab  left outer join tbm_dettipdoc as dettip on (cab.co_emp = dettip.co_emp  and  cab.co_loc = dettip.co_loc and cab.co_tipdoc = dettip.co_tipdoc and cab.co_cta = dettip.co_cta) left outer join tbm_placta as cta on (dettip.co_emp = cta.co_emp and dettip.co_cta = cta.co_cta) "+ 
                                    " where cab.co_emp = " + objZafParSis.getCodigoEmpresa() + " and cab.co_loc = " + objZafParSis.getCodigoLocal(); // +" and cab.co_tipdoc = 30 and cab.co_doc= 2 ";
                    strSQL += strFiltro + " order by co_tipdoc,co_doc";
                    //System.out.println(strSQL);
                    rstCab=stm.executeQuery(strSQL);
                    if(rstCab.next()){
                        rstCab.last();
                        refrescaDatos();
                    }
                    else{
                        clnTextos();
                        return false;
                    }
                     con.close();                    
                }
            }catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(jfrThis, e);
                return false;
            }
        }catch (Exception e){            
                objUti.mostrarMsgErr_F1(jfrThis, e);
                return false;
        }
        return true;
    }    
    public void  refrescaDatos(){
        try{//odbc,usuario,password        
            int intNumDoc = 0;
       
                if(rstCab != null){                    
                    java.sql.Connection   con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());

                    intNumDoc = rstCab.getInt("co_doc");
                    txtCodDoc.setText(""+intNumDoc);
                    objCliente.setCliente(rstCab.getInt("co_cli"));
                    txtCodCli.setText(((rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli")));
                    txtNomCli.setText(objCliente.getNombre());
                    txtNumDoc.setText(((rstCab.getString("ne_numdoc")==null)?"":rstCab.getString("ne_numdoc")));                    
                    cargarCabTipoDoc(rstCab.getInt("co_tipdoc"));                   
                    if(rstCab.getDate("fe_doc")==null){
                      txtFecDoc.setText("");  
                    }else{
                        java.util.Date dateObj = rstCab.getDate("fe_doc");
                        java.util.Calendar calObj = java.util.Calendar.getInstance();
                        calObj.setTime(dateObj);
                        txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                                          calObj.get(java.util.Calendar.MONTH)+1     ,
                                          calObj.get(java.util.Calendar.YEAR)        );
                    }                    
                    txtCodCta.setText(((rstCab.getString("co_cta")==null)?"":rstCab.getString("co_cta")));
                    txtDetCta.setText(((rstCab.getString("tx_descta")==null)?"":rstCab.getString("tx_descta")));
                    txtUsoCta.setText(((rstCab.getString("tx_ubicta")==null)?"":rstCab.getString("tx_ubicta")));
                    txtTot.setText(((rstCab.getString("nd_tot")==null)?"0.0":rstCab.getString("nd_tot")));
                    //Extrayendo los datos del detalle respectivo a esta orden de compra
                    String strSQL = " Select co_banchq,v.tx_deslar as tx_desban,tx_numctachq,tx_numchq,fe_recchq,fe_venchq,sum(nd_monchq) as nd_monchq,co_prochq, var.tx_deslar as tx_despro " +
                                    " from tbm_cabmovinv as cab left outer join tbm_chqpro as chq on (cab.co_emp = chq.co_emp and cab.co_loc = chq.co_loc and cab.co_tipdoc = chq.co_tipdoc and cab.co_doc = chq.co_doc) left outer join tbm_pagmovinv as pag on (co_locpro=pag.co_loc and co_tipdocpro = pag.co_tipdoc and co_docpro=pag.co_doc and co_regpro=pag.co_reg)  left outer join tbm_var as var on (pag.co_prochq = var.co_reg ) left outer join tbm_var as v on ( pag.co_banchq = v.co_reg)" +
                                    " where cab.co_emp = " + objZafParSis.getCodigoEmpresa() + " and cab.co_loc = " + objZafParSis.getCodigoLocal()+ " and cab.co_tipdoc = " + rstCab.getInt("co_tipdoc") +" and cab.co_doc= "+ rstCab.getInt("co_doc") +
                                    " group by  co_banchq,v.tx_deslar,tx_numctachq,tx_numchq,fe_recchq,fe_venchq,co_prochq,var.tx_deslar "+
                                    " order by  co_banchq,tx_numctachq,tx_numchq";
                    //System.out.println(strSQL);
                    java.sql.Statement stm = con.createStatement();                    
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    java.sql.ResultSet rstAux;
                    java.sql.Statement stmAux = con.createStatement(); ;
                    vecDat=null;
                    vecDat = new Vector();
                     for(int i=0 ; rst.next() ; i++){
                            strSQL = " Select co_tipdoc,co_doc,co_reg "+
                                     " from tbm_pagmovinv  "+
                                     " where co_emp = " + objZafParSis.getCodigoEmpresa()+" and co_loc = " + objZafParSis.getCodigoLocal()+" and co_banchq="+rst.getInt("co_banchq")+" and tx_numctachq='"+rst.getString("tx_numctachq")+"' and tx_numchq = '" + rst.getString("tx_numchq") + "' and st_reg='A'" +
                                     " order by co_tipdoc,co_doc,co_reg";
                            //System.out.println(strSQL);
                            rstAux = stmAux.executeQuery(strSQL);
                            Vector vecObj = new Vector();
                            while (rstAux.next()){
                                Vector vecTmp = new Vector();
                                vecTmp.add(INT_VEC_TIP_DOC,new Integer(rstAux.getInt("co_tipdoc")));
                                vecTmp.add(INT_VEC_COD_DOC,new Integer(rstAux.getInt("co_doc")));
                                vecTmp.add(INT_VEC_COD_REG,new Integer(rstAux.getInt("co_reg")));                                
                                vecObj.add(vecTmp);
                            }                            
                            rstAux.close();
                            stmAux.close();
                                
                            Vector vecReg = new Vector();
                            vecReg.add(INT_TBL_LINEA,"");
                            vecReg.add(INT_TBL_COD_BCO,new Integer(rst.getInt("co_banchq")));
                            vecReg.add(INT_TBL_NOM_BCO,rst.getString("tx_desban"));
                            vecReg.add(INT_TBL_NUM_CTA,rst.getString("tx_numctachq"));
                            vecReg.add(INT_TBL_OBJ_DOC,vecObj);
                            vecReg.add(INT_TBL_NUM_CHQ,rst.getString("tx_numchq"));
                            vecReg.add(INT_TBL_BUT_CHQ,"");                            
                            vecReg.add(INT_TBL_FEC_REC,rst.getString("fe_recchq"));
                            vecReg.add(INT_TBL_FEC_VEN,rst.getString("fe_venchq"));
                            vecReg.add(INT_TBL_VAL_CHQ,new Double(rst.getDouble("nd_monchq")));
                            vecReg.add(INT_TBL_COD_PRO,new Integer(rst.getInt("co_prochq")));
                            vecReg.add(INT_TBL_BUT_PRO,ObjChq);
                            vecReg.add(INT_TBL_TIP_PRO,rst.getString("tx_despro"));
                            vecReg.add(INT_TBL_BUT_REL,ObjDoc);
                            vecDat.add(vecReg);            
                     }
//                     System.out.println("size:"+vecDat.size());
                     objTblMod.setData(vecDat);
                     tblDat .setModel(objTblMod);


                //Pie de pagina
                    txaObs1.setText(((rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1")));
                    txaObs2.setText(((rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2")));
                                        
                /*
                 * CARGANDO DATOS DEL TAB ASIENTO DE DIARIO
                 */
                   objDiario.consultarDiarioCompleto(objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(), rstCab.getInt("co_doc"));
                    
                /*
                 * VERIFICANDO SI SE ENCUENTRA EN ESTADO ANULADO
                 */
                    String strStatus = rstCab.getString("st_reg");
                    if(strStatus.equals("I")){
                        lblTitForm.setText( "Documento por pagar #ANULADO#");
                        objUti.desactivarCom(jfrThis);
                    }else{
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(jfrThis);
                        }
                    }
                    con.close();
                }
            blnHayCam = false; // Seteando que no se ha hecho cambios
               
        }catch(java.sql.SQLException Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
              
        }catch(Exception Evt){
              objUti.mostrarMsgErr_F1(jfrThis, Evt);
        }                                     
    }
    
    private int Mensaje(){
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Desea guardar los cambios efectuados a éste registro?\n";
        strMsg+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();

        return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
    }
    
    private boolean isAnulada()
    {
       boolean blnRes = false;
      java.sql.Connection conTmp ;
      java.sql.Statement stmTmp;
      String strSQL = "";
      try
      {
          conTmp = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
          if (conTmp!=null)
          {
              stmTmp = conTmp.createStatement();
              strSQL = "";
              strSQL = " Select count(*) from tbm_cabmovinv ";
              strSQL += " where  co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()   + " and  co_tipDoc = " + txtCodTipDoc.getText() + " and  co_doc = " +  txtCodDoc.getText() + " and st_reg = 'I' " ;
              if (objUti.getNumeroRegistro(this,objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0){                  
                  MensajeInf("Documento anulado no se puede modificar");
                  return true;
              }else
                  blnRes = false;
              stmTmp.close();
              conTmp.close();
          }

       }catch(java.sql.SQLException Evt){    
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
       }catch(Exception Evt)  {
            objUti.mostrarMsgErr_F1(jfrThis, Evt);
            blnRes = true;
       }
      return blnRes;
    }               
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
      /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
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
    
  /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                refrescaDatos();                
            }
            else
            {
                MensajeInf("Error al cargar registro");
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }        
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                java.sql.Statement stm=con.createStatement();
                String strSQL = "  select cab.co_emp,cab.co_loc,cab.co_tipdoc,cab.co_doc,ne_numdoc,fe_doc,co_cli,nd_tot,cab.co_cta,tx_deslar as tx_descta,tx_ubicta,cab.tx_obs1,cab.tx_obs2,cab.st_reg " +
                    " from tbm_cabmovinv as cab  left outer join tbm_dettipdoc as dettip on (cab.co_emp = dettip.co_emp  and  cab.co_loc = dettip.co_loc and cab.co_tipdoc = dettip.co_tipdoc and cab.co_cta = dettip.co_cta) left outer join tbm_placta as cta on (dettip.co_emp = cta.co_emp and dettip.co_cta = cta.co_cta) "+ 
                     " Where  cab.co_emp=" + rstCab.getString("co_emp") +
                      " AND cab.co_loc="    + rstCab.getString("co_loc") +
                      " AND cab.co_tipDoc=" + rstCab.getString("co_tipDoc") +
                      " AND cab.co_doc="    + rstCab.getString("co_doc") ;
//                System.out.println(strSQL);
                java.sql.ResultSet rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    String strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
            
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Mostrar la posición relativa del registro.
                intPosRel=rstCab.getRow();
                rstCab.last();
                objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
                rstCab.absolute(intPosRel);
                blnHayCam=false;
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
    
    public class mitoolbar extends Librerias.ZafToolBar.ZafToolBar{
        public mitoolbar(javax.swing.JInternalFrame jfrThis){
            super(jfrThis, objZafParSis);
        }

        public boolean beforeAceptar() {
            return true;
        }        
        public boolean beforeAnular() {
            return true;
        }        
        public boolean beforeCancelar() {
            return true;
        }        
        public boolean beforeConsultar() {
            return true;
        }        
        public boolean beforeEliminar() {
            return true;
        }        
        public boolean beforeImprimir() {
            return true;
        }        
        public boolean beforeInsertar() {
            return true;
        }        
        public boolean beforeModificar() {
            return true;
        }        
        public boolean beforeVistaPreliminar() {
            return true;
        }
                
        public boolean anular()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAnterior(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                        }
                    }
                    else
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

        public void clickFin() {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
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

        public void clickInicio(){
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
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

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
                    rstCab.close();
//                    stmCab.close();
//                    conCab.close();
                    rstCab=null;
//                    stmCab=null;
//                    conCab=null;
                }

                clnTextos();
                if(!txtFecDoc.isFecha())
                    txtFecDoc.setHoy(); 
                txtCodDoc.setEnabled(false);
                objDiario.setEditable(true);  
                objDiario.inicializar();   
                setEditable(true);
                objDiario.setEditable(true);
                blnHayCam=false;
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

        public void clickSiguiente(){
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
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
        
        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            try
            {
                String strAux=objTooBar.getEstadoRegistro();
                if (strAux.equals("Eliminado"))
                {
                    MensajeInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                    return false;
                }
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar()
        {
            if (!validaCampos())
                return false;
            if (!insertarReg())
                return false;
            clnTextos();
            blnHayCam=false;
            return true;
        }

        public boolean modificar()
        {
            String strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                MensajeInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                MensajeInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!validaCampos())
                return false;
            if (!actualizarReg())
                return false;
            blnHayCam=false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam)
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
//                    stmCab.close();
//                    conCab.close();
                    rstCab=null;
//                    stmCab=null;
//                    conCab=null;
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
            clnTextos();
            blnHayCam=false;
            return blnRes;
        }
           
        public boolean anularReg() {
            return false;
        }
        
        public void clickAceptar() {
        }
        
        
        public void clickAnular() {
        }
        
        public void clickCancelar() {
        }
        
        public void clickConsultar() {
            clnTextos();
            if(!txtFecDoc.isFecha())
                txtFecDoc.setHoy(); 
            txtCodDoc.setEnabled(true);
            objDiario.setEditable(false);  
            objDiario.inicializar();   
            setEditable(false);            
        }
        
        public void clickEliminar() {
        }
        
        
        public void clickImprimir() {
        }
                
        public void clickModificar() {
            txtCodDoc.setEnabled(false);
            objDiario.setEditable(true);  
            setEditable(true);
        }
        
        
        public void clickVisPreliminar() {
        }
        
        public boolean consultarReg() {
            return _consultar(sqlFil()); 
        }
        
        public boolean eliminarReg() {
            return false;
        }
        
        public boolean insertarReg() {
            boolean blnRes = false;
            int intCodDoc = 0, intOrdDoc = 0;
            String strSQL;
            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());            
            
            try{                
              java.sql.Connection conIns = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
               try{
                if (conIns!=null){
                    conIns.setAutoCommit(false);
                    java.sql.PreparedStatement pstIns;
                    java.sql.Statement stm = conIns.createStatement();
                    if (!validaCampos()){
                        return false;
                    }
                    int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                    String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                    //Obtengo el ultimo numero de documento (Sequecial)
                     strSQL= "SELECT Max(co_doc) as ultdoc FROM tbm_cabmovinv where " +
                          " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                          " and co_loc = " + objZafParSis.getCodigoLocal() +
                          " and co_tipDoc = " + txtCodTipDoc.getText() ;
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodDoc = rst.getInt("ultdoc");
                    intCodDoc++;
                    txtCodDoc.setText(""+intCodDoc);
                    strSQL= "SELECT Max(ne_orddoc) as ne_orddoc  FROM tbm_cabMovInv where " +
                      " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                      " and co_loc = " + objZafParSis.getCodigoLocal() ;
                    rst = stm.executeQuery(strSQL);
                    if(rst.next()){
                        intOrdDoc = rst.getInt("ne_orddoc");
                    }
                    intOrdDoc++;
                    strSQL = " Insert Into tbm_cabmovinv (co_emp,co_loc,co_tipdoc,co_doc,ne_orddoc,ne_numdoc,fe_doc,co_cli,tx_ruc,tx_nomcli,tx_dircli,tx_telcli,nd_sub,nd_tot,co_cta,tx_obs1,tx_obs2,fe_ing,co_usring)";
                    strSQL +=" values (" +
                            objZafParSis.getCodigoEmpresa() + ","+
                            objZafParSis.getCodigoLocal() + ","+
                            objTipDoc.getco_tipdoc() + ","+
                            intCodDoc + ","+
                            intOrdDoc + ","+
                            txtNumDoc.getText() + 
                            ",'"+ strFecha + "',"+
                            txtCodCli.getText() + 
                            ",'"+ objCliente.getIdentificacion() + "','"+ objCliente.getNombre()+ "','"+ objCliente.getDireccion() + "','"+ objCliente.getTelefono()+ "',"+
                            txtTot.getText() + ","+
                            txtTot.getText() + ","+
                            txtCodCta.getText() + 
                            ",'"+ txaObs1.getText() + "','"+ txaObs2.getText() + "','"+ strFecSis + "',"+ objZafParSis.getCodigoUsuario()+ ")";
                    pstIns = conIns.prepareStatement(strSQL);
                    pstIns.executeUpdate();
                    //PagMovInv
                    strSQL =  "";
                    strSQL =  " Insert Into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,fe_ven,mo_pag)";
                    strSQL += " values ("+
                             objZafParSis.getCodigoEmpresa() + ","+
                             objZafParSis.getCodigoLocal() + ","+
                             objTipDoc.getco_tipdoc() + ","+ intCodDoc +
                             ",1,'"+ strFecha + "',"+ txtTot.getText() + ")";
                    //System.out.println(strSQL);
                    pstIns = conIns.prepareStatement(strSQL);
                    pstIns.executeUpdate();   
                    objTblMod.removeEmptyRows();      
                    int intCountReg=1;
                    for (int i=0;i<tblDat.getRowCount();i++)
                    {
                        if (tblDat.getValueAt(i,INT_TBL_OBJ_DOC)!=null){
                            for (int j=0;j<((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).size();j++)
                            {
                                strSQL =  "";
                                strSQL =  " Update tbm_pagmovinv set co_prochq = "+tblDat.getValueAt(i,INT_TBL_COD_PRO).toString()+                                        
                                          " where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_TIP_DOC).toString() + " and co_doc = " + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_DOC).toString() + " and co_reg="+ ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_REG).toString() ;
                                //System.out.println(strSQL);
                                pstIns = conIns.prepareStatement(strSQL);
                                pstIns.executeUpdate(); 
                                strSQL =  "";
                                strSQL =  " Insert Into tbm_chqpro (co_emp,co_loc,co_tipdoc,co_doc,co_reg,co_locpro,co_tipdocpro,co_docpro,co_regpro)" +
                                          " values (" + objZafParSis.getCodigoEmpresa() +","+ objZafParSis.getCodigoLocal() + ","+ objTipDoc.getco_tipdoc()+","+txtNumDoc.getText()+","+(intCountReg)+","+ objZafParSis.getCodigoLocal() +"," + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_TIP_DOC).toString() + "," + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_DOC).toString() + ","+ ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_REG).toString() +")";
                                //System.out.println(strSQL);
                                pstIns = conIns.prepareStatement(strSQL);
                                pstIns.executeUpdate(); 
                                intCountReg++;
                            }
                        }
                    }
                    if (!objDiario.insertarDiario(conIns,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objTipDoc.getco_tipdoc(),intCodDoc)){
                        conIns.rollback();
                        conIns.close();
                        return false;
                    }
                    conIns.commit();
                    
                    rst.close();
                    stm.close();                   
                    conIns.close();
                    objTooBar.setEstado('m');
                    blnRes=true;
                }
                }catch (java.sql.SQLException e){
                    conIns.rollback();
                    conIns.close();
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(jfrThis, e);
                }
            }catch(Exception e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(jfrThis, e);
            }            
            return blnRes;
        }
        
        public boolean actualizarReg() {
            boolean blnRes=false;
            if(!validaCampos())
              return false;
            if (isAnulada()){
                return false;
            }          
            String strSQL;
            String strFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaHoraBaseDatos());                        
            try{                
              java.sql.Connection conMod = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
               try{
                    if (conMod!=null){
                        conMod.setAutoCommit(false);
                        java.sql.PreparedStatement pstMod;
                        
                        int Fecha[] =  txtFecDoc.getFecha(txtFecDoc.getText());
                        String strFecha = "#" + Fecha[2] + "/"+Fecha[1] + "/" + Fecha[0] + "#";
                        
                        /*********************************************\    
                         * Armando el Update para los datos          *   
                         * de la cabecera de cheque protestado       *
                        \*********************************************/

                       strSQL =" Update tbm_cabMovInv set " +
                               " fe_doc  = '" + strFecha            + "', " +
                               " co_cli  = "  + txtCodCli.getText() + ",  " + 
                               " ne_numDoc="  + txtNumDoc.getText() + ",  " + 
                               " tx_nomCli='" + txtNomCli.getText() + "', " +
                               " tx_dirCli='" + objCliente.getDireccion() + "', " +
                               " tx_ruc='"    + objCliente.getIdentificacion() + "', " +
                               " tx_telCli='" + objCliente.getTelefono()       + "', " +
                               " co_cta =" + txtCodCta.getText() + "," +
                               " tx_obs1 = '" + txaObs1.getText()   + "', " + 
                               " tx_obs2 = '" + txaObs2.getText()   + "', " + 
                               " nd_sub  = "  + objUti.redondeo(Double.parseDouble(txtTot.getText()),6)    + ",  " +
                               " nd_tot  = "  + objUti.redondeo(Double.parseDouble(txtTot.getText()),6)    + ",  " +                               
                               " fe_ultMod   =  '"+strFecSis+"', " +
                               " co_usrMod   = "+ objZafParSis.getCodigoUsuario() + " " +
                               " where " +
                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                               " co_tipDoc = " + txtCodTipDoc.getText()       + " and "+  
                               " co_doc = " +  txtCodDoc.getText();    
                        //System.out.println(strSQL);
                        pstMod = conMod.prepareStatement(strSQL);                                     
                        strSQL =" Update tbm_pagMovInv set " +
                               " fe_doc  = '" + strFecha            + "', " +
                               " mo_pag  = "  + txtTot.getText() + ",  " + 
                               " fe_ultMod   =  '"+strFecSis+"', " +
                               " co_usrMod   = "+ objZafParSis.getCodigoUsuario() + " " +
                               " where " +
                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                               " co_tipDoc = " + txtCodTipDoc.getText()       + " and "+  
                               " co_doc = " +  txtCodDoc.getText();    
                        //System.out.println(strSQL);
                        pstMod = conMod.prepareStatement(strSQL);   
                        strSQL = " Select co_emp,co_locpro,co_tipdocpro,co_docpro,co_regpro  from tbm_chqpro"+ 
                                 "  where " +
                                 " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                                 " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                                 " co_doc = " +  txtCodDoc.getText()            + " and "+
                                 " co_tipDoc = " + txtCodTipDoc.getText() ;
                        java.sql.Statement stm  = conMod.createStatement();
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        while (rst.next()){
                                strSQL =  "";
                                strSQL =  " Update tbm_pagmovinv set co_prochq = null"+                                        
                                          " where co_emp =" + rst.getInt("co_emp") +" and co_loc = "+ rst.getInt("co_locpro") +" and co_tipdoc = " + rst.getInt("co_tipdocpro") + " and co_doc = " + rst.getInt("co_docpro") + " and co_reg="+ rst.getInt("co_regpro") ;
                                //System.out.println(strSQL);
                                pstMod = conMod.prepareStatement(strSQL);
                                pstMod.executeUpdate();                                                                         
                        }

                        strSQL =" DELETE FROM tbm_chqpro " +
                               "  where " +
                               " co_emp = " + objZafParSis.getCodigoEmpresa() + " and "+  
                               " co_loc = " + objZafParSis.getCodigoLocal()   + " and "+  
                               " co_doc = " +  txtCodDoc.getText()            + " and "+
                               " co_tipDoc = " + txtCodTipDoc.getText() ;
                        /* Ejecutando el Delete */
                        //System.out.println(strSQL);
                        pstMod = conMod.prepareStatement(strSQL);
                        pstMod.executeUpdate();
                        objTblMod.removeEmptyRows();      
                        int intCountReg=1;
                        for (int i=0;i<tblDat.getRowCount();i++)
                        {
                            if (tblDat.getValueAt(i,INT_TBL_OBJ_DOC)!=null){
                                for (int j=0;j<((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).size();j++)
                                {
                                    strSQL =  "";
                                    strSQL =  " Update tbm_pagmovinv set co_prochq = "+tblDat.getValueAt(i,INT_TBL_COD_PRO).toString()+                                        
                                              " where co_emp =" + objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_TIP_DOC).toString() + " and co_doc = " + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_DOC).toString() + " and co_reg="+ ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_REG).toString() ;
                                    //System.out.println(strSQL);
                                    pstMod = conMod.prepareStatement(strSQL);
                                    pstMod.executeUpdate();                                     
                                    strSQL =  "";
                                    strSQL =  " Insert Into tbm_chqpro (co_emp,co_loc,co_tipdoc,co_doc,co_reg,co_locpro,co_tipdocpro,co_docpro,co_regpro)" +
                                              " values (" + objZafParSis.getCodigoEmpresa() +","+ objZafParSis.getCodigoLocal() + ","+ objTipDoc.getco_tipdoc()+","+txtNumDoc.getText()+","+(intCountReg)+","+ objZafParSis.getCodigoLocal() +"," + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_TIP_DOC).toString() + "," + ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_DOC).toString() + ","+ ((Vector)((Vector)tblDat.getValueAt(i,INT_TBL_OBJ_DOC)).get(j)).get(INT_VEC_COD_REG).toString() +")";
                                    //System.out.println(strSQL);
                                    pstMod = conMod.prepareStatement(strSQL);
                                    pstMod.executeUpdate(); 
                                    intCountReg++;
                                }
                            }
                        }
                        
                        if (!objDiario.actualizarDiario(conMod, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objTipDoc.getco_tipdoc(),Integer.parseInt(txtCodDoc.getText()),txtNumDoc.getText(), objDiario.getFechaDiario(), "A")){
                            conMod.rollback();
                            conMod.close();
                            return false;
                        }
                        
                        conMod.commit();
                        conMod.close();
                        blnRes = true;
                    }
                }catch (java.sql.SQLException e){
                    conMod.rollback();
                    conMod.close();
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(jfrThis, e);
                }
            }catch(Exception e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(jfrThis, e);
            }            
            return blnRes;
        }
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
      
        public boolean imprimir() {
            return true;
        }
        
        public boolean vistaPreliminar() {
            return true;
        }
        
    }
    public class ButFndTbl extends Librerias.ZafTableColBut.ZafTableColBut{
        
        public ButFndTbl(javax.swing.JTable tbl, int ind){
            super(tbl,ind);
        }
        public void butCLick() {
            cargaAnexo1(INT_TBL_BUT_CHQ);
        }        
    }
    public class ButFndDoc extends Librerias.ZafTableColBut.ZafTableColBut{
        
        public ButFndDoc(javax.swing.JTable tbl, int ind){
            super(tbl,ind);
        }
        public void butCLick() {
            cargaAnexo2();
        }        
    }
   /*
     * Clase de tipo documenet listener para detectar los cambios en 
     * los textcomponent
     */
    class LisTextos implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
    }        
    
    /*
     * Agrega el listener para detectar que hubo algun cambio en la caja de texto
     */
    private void addListenerCambio(){
        objlisCambios = new LisTextos();
        //Cabecera
            
            txtCodTipDoc.getDocument().addDocumentListener(objlisCambios);
            txtCodCli.getDocument().addDocumentListener(objlisCambios);
            txtCodCta.getDocument().addDocumentListener(objlisCambios);
            txtNumDoc.getDocument().addDocumentListener(objlisCambios);            
            txtCodDoc.getDocument().addDocumentListener(objlisCambios);
        

        //Pie de pagina
            txaObs1.getDocument().addDocumentListener(objlisCambios);
            txaObs2.getDocument().addDocumentListener(objlisCambios);
            txtTot.getDocument().addDocumentListener(objlisCambios);
        
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
                case INT_TBL_COD_BCO:
                    strMsg="Código del banco";
                    break;
                case INT_TBL_NOM_BCO:
                    strMsg="Banco";
                    break;
                case INT_TBL_NUM_CTA:
                    strMsg="Numero de cuenta";
                    break;
                case INT_TBL_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_FEC_REC:
                    strMsg="Fecha de recepción";
                    break;
                case INT_TBL_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_COD_PRO:
                    strMsg="Código del protesto";
                    break;
                case INT_TBL_TIP_PRO:
                    strMsg="Tipo de protesto";
                    break;
                case INT_TBL_BUT_REL:
                    strMsg="Documentos relacionados";
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
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    if ( (tblDat.getEditingColumn()==INT_TBL_NUM_CHQ) )
                        if (objAnexo1==null)cargaAnexo1(INT_TBL_NUM_CHQ);
                    break;
            }
        }
    }

}
