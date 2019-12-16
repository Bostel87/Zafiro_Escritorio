/*
 * ZafCxC03.java
 *
 * Created on 27 de noviembre de 2004, 12:50 PM
 * DARIO CARDENAS MODIFICO EL 25/MARZO/2007
 * DOCUMENTO POSTFECHADOS APLICADO A UN DOCUMENTO
 * VERSION 2.4
 */
package CxC.ZafCxC03;//hola
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafVenCon.ZafVenCon;
//Eddye_ventana para documentos pendientes//
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafVenCon.ZafVenConCxC01;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.util.ArrayList;
/**
 *
 * @author  dCardenas
 */
public class ZafCxC03 extends javax.swing.JInternalFrame {
    final int INT_TBL_LINEA        =0;            //LINEA DE NUMERACION DE CANTIDAD DE REGISTROS///0
    final int INT_TBL_SEL          =1;            //LINEA DE SELECCION DE FACTURAS///1
    final int INT_TBL_BUT_DAT      =2;            //BOTON DE DATON DE FACTURAS POR CLIENTE///2    
    final int INT_TBL_COD_CLI      =3;            //Codigo de CLIENTE///3
    final int INT_TBL_NOM_CLI      =4;            //Nombre de CLIENTE///4
    final int INT_TBL_COD_LOC      =5;            //Codigo de LOCAL///5
    final int INT_TBL_COD_TIP_DOC  =6;            //CODIGO DEL TIPO DE DOCUMENTO///6
    final int INT_TBL_NOM_TIP_DOC  =7;            //NOMBRE DEL TIPO DE DOCUMENTO///7
    final int INT_TBL_COD_DOC      =8;            //CODIGO DEL DOCUMENTO///8
    final int INT_TBL_COD_REG      =9;            //CODIGO DE REGISTRO///9
    final int INT_TBL_NUM_DOC      =10;           //NUMERO DEL DOCUMENTO///10
    final int INT_TBL_FEC_DOC      =11;           //FECHA DE DOCUMENTO///11
    final int INT_TBL_DIA_CRE      =12;           //DIAS DE CREDITO///12
    final int INT_TBL_FEC_VEN      =13;           //FECHA DE VENCIMIENTO///13 
    final int INT_TBL_VAL_DOC      =14;           //VALOR DE DOCUMENTO///14
    final int INT_TBL_VAL_PEN      =15;           //VALOR PENDIENTE DE DOCUMENTO///15
    final int INT_TBL_COD_BAN      =16;           //CODIGO DEL BANCO///16
    final int INT_TBL_DES_BAN      =17;           //DESCRIPCION DEL BANCO///17
    final int INT_TBL_BUT_BAN      =18;           //BOTON DE BUSQUEDA DEL BANCO///18
    final int INT_TBL_NOM_BAN      =19;           //NOMBRE DEL BANCO///19
    final int INT_TBL_NUM_CTA      =20;           //NUMERO DE CUENTA///20
    final int INT_TBL_NUM_CHQ      =21;           //NUMERO DE CHEQUE///21
    final int INT_TBL_FEC_REC_CHQ  =22;           //FECHA DE RECEPCION DEL CHEQUE///22
    final int INT_TBL_FEC_VEN_CHQ  =23;           //FECHA VENCIMIENTO DEL CHEQUE///23
    final int INT_TBL_VAL_CHQ      =24;           //FECHA DE RECEPCION DEL CHEQUE///24
    final int INT_TBL_EST_REG      =25;           //ESTADO DE REGISTRO///25

    
    final int INT_GRP_BCO = 8;
    
    
    boolean blnCambios = false; //Detecta ke se hizo cambios en el documento
    //    final int varTipDoc=12;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecVen;
        javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon, strSQLA, strSQLB, strSQLC;
    private Vector vecDat, vecValPen, vecCab;
    private boolean blnHayCam;          //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    //private ZafTableModel objTblMod;
    //private ZafDefTblCelRen objDefTblCelRen;    //Formatear columnas en JTable.
    //private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodPro, strDesLarPro,strIdePro, strDirPro,strDesCorCta,strDesLarCta;    

    private ZafVenCon vcoTipDoc, vcoBan, vcoLoc;                //Ventana de consulta "Tipo de documento".
    
    private java.util.Vector vecReg;
    //private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, con, conCnsDet,conAnu, conRee, conIns, conReeA, conReeB, conReeC;

    private java.sql.Statement stmCab, stm, stmCnsDet, stmAnu, stmRee, stmReeA, stmReeB, stmReeC;
    private java.sql.ResultSet rstCab, rst, rstCnsDet, rstRee, rstReeFact, rstReeA, rstReeB, rstReeC;
    //private tblHilo objHilo;

    private ZafTblMod objTblMod;
    
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;    
    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;
    private ZafTblCelRenLbl objTblCelRenLblObl;
    ///private ZafCxC03.ZafTblModLis objTblModLis;
     private ZafTblModLis objTblModLis;
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;
    
    ////////////////////////////////////
    double VAL_ABO = 0.00, VAL_ABO_REG_SEL=0.00;
    double ABO_ASO_REG = 0.00;
    String fecdocSel="", fecVenDocSel="", strFecDia="";
    String EST_REG_REP="";
    int ULT_REG_DOC=0, CAN_REG_ABO_ASO=0, REG_CAN_FAC=0, REG_FAC=0, K=0;
    double porret = 0.000;
    String DIA_GRA="", EST_REG_FAC="", FEC_VEN_DOC="";
    int CAN_FAC_SAL_PND=0, CAN_FAC_EST_POS=0, intFilSel=0, k=0, CAN_REG_CON=0;
    ///////////////////////////////////
    
    private ZafTblCelRenChk objTblCelRenChkSopNec;
    private ZafTblCelEdiChk objTblCelEdiChkSopNec;
    private ZafTblCelRenChk objTblCelRenChkSopEnt;
    private ZafTblCelEdiChk objTblCelEdiChkSopEnt;
    private ZafTblCelRenChk objTblCelRenChkEstChq;
    private ZafTblCelEdiChk objTblCelEdiChkEstChq;

    private String strSopEnt="";
    
    private int intSig=1;                                   //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    private ZafTblCelEdiBut objTblCelEdiBut;
    private ZafTblCelRenBut objTblCelRenBut;

    
    Librerias.ZafDate.ZafDatePicker objDtePck ; //Para realizar operaciones de fechas
    Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi objDteEdiCel; //Para realizar operaciones en la celda de fechas///
    java.util.Calendar calObj ; //Para realizar operaciones de fecha
    private String strFormatoFecha="d/m/y"; // Formato de fecha a usar 
    private String strFormatoFechaBase="y-m-d"; // Formato de fecha que usa la base
    
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConCodBan;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConNomBan;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConCodCli;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConNomCli;
    
    private ZafTblCelEdiBut objTblCelEdiButCli;
    private ZafTblCelRenBut objTblCelRenButCli;
    private ZafTblCelEdiBut objTblCelEdiButTipDoc;
    private ZafTblCelRenBut objTblCelRenButTipDoc;
    private ZafTblCelEdiBut objTblCelEdiButBco;
    private ZafTblCelRenBut objTblCelRenButBco;
    private ZafTblCelEdiTxt objTblCelEdiTxtChq;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtNumFac;
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoBco;   //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBco;   //Editor: JTextField de consulta en celda.
    
    //Eddye_ventana de documentos pendientes//
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafVenConCxC01 objVenConCxC01;
    
    private ZafTblCelEdiTxt objTblCelEdiTxtNumDoc;    
    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxC03(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();

            if (!configurarFrm())
                exitForm();
           
            vecDat.clear();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panVal = new javax.swing.JPanel();
        spnValExc = new javax.swing.JScrollPane();
        tblFacDet = new javax.swing.JTable();
        spnObs = new javax.swing.JScrollPane();
        panTxa = new javax.swing.JPanel();
        butGua = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Informaci\u00f3n del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panVal.setLayout(new java.awt.BorderLayout());

        panVal.setPreferredSize(new java.awt.Dimension(935, 400));
        spnValExc.setBorder(new javax.swing.border.TitledBorder(""));
        spnValExc.setPreferredSize(new java.awt.Dimension(20, 0));
        tblFacDet.setModel(new javax.swing.table.DefaultTableModel(
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
        spnValExc.setViewportView(tblFacDet);

        panVal.add(spnValExc, java.awt.BorderLayout.CENTER);

        panDat.add(panVal, java.awt.BorderLayout.CENTER);

        spnObs.setPreferredSize(new java.awt.Dimension(519, 40));
        panTxa.setPreferredSize(new java.awt.Dimension(98, 20));
        butGua.setToolTipText("Guarda la Informacion General");
        butGua.setLabel("Guardar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });

        panTxa.add(butGua);

        butLim.setToolTipText("Limpia el contenido de los campos");
        butLim.setLabel("Limpiar");
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });

        panTxa.add(butLim);

        butCer.setToolTipText("Salir del programa");
        butCer.setLabel("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        panTxa.add(butCer);

        spnObs.setViewportView(panTxa);

        panDat.add(spnObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed

//        System.out.println("---CLICK BUT GUARDAR---");
        
        if(K>0)
        {
//            System.out.println("EXISTEN REGISTROS SELECCIONADOS");
            if(isCamVal())
            {
                if (insertarReg())
                {
//                            System.out.println("funko insertar");
                            mostrarMsgInfReg("La operaciï¿½n GUARDAR registros se realizï¿½ con ï¿½xito");
                        }
                        else{
//                            System.out.println("no funko dividir");
                            mostrarMsgInfReg("La operaciï¿½n GUARDAR registros no se pudo realizar");
                }                
                vecDat.clear();
            }
        }
        else
            if(K==0)
            {
//                System.out.println(" NO EXISTEN REGISTROS SELECCIONADOS");
                mostrarMsgInfReg("No se puede GUARDAR el documento, no posee registros seleccionados");
            }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        String strTit, strMsg;
//        try
//        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
    }//GEN-LAST:event_butCerActionPerformed

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        limpiarFrm();
    }//GEN-LAST:event_butLimActionPerformed

    /*
     * FUNCION PARA SABER SI EL DATO INGRESADO ES NUMERICO
     */
    public static boolean IsNumeric(String s)
    {
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) < '0' || s.charAt(i) > '9')
            {
                return false;
            }
        }
        return true;
    }
    
    /*
     *FUNCION PARA LIMPIAR FORMULARIO
     */
    protected void limpiarFrm(){
        vecDat.clear();
        objTblMod.removeAllRows();
        objTblMod.insertRow();
        K=0;
    }
                
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexiï¿½n si estï¿½ abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }                        
    }//GEN-LAST:event_exitForm
    
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butLim;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panTxa;
    private javax.swing.JPanel panVal;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JScrollPane spnValExc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblFacDet;
    // End of variables declaration//GEN-END:variables
 
     private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   

   
    private boolean isCamVal()
    {
        //Validar que el JTable de detalle estï¿½ completo.
        int intcountTrue = objTblMod.getRowCountTrue();
        int intcount = objTblMod.getRowCount();
        
//        System.out.println("---isCamVal()--intcountTrue---: " + intcountTrue);
//        System.out.println("---isCamVal()--intcount---: " + intcount);
        
        if(K==0)
        {
            objTblMod.removeEmptyRows();
            if (!objTblMod.isAllRowsComplete())
            {
                mostrarMsgInf("<HTML>El detalle del documento contiene filas que estï¿½n incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                tblFacDet.setRowSelectionInterval(0, 0);
                tblFacDet.changeSelection(0, INT_TBL_NUM_DOC, true, true);
                tblFacDet.requestFocus();
                return false;
            }
        }        
        else
        {
            objTblMod.removeEmptyRows();
            if (!objTblMod.isAllRowsComplete())
            {
                mostrarMsgInf("<HTML>El detalle del documento contiene filas que estï¿½n incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                tblFacDet.setRowSelectionInterval(0, 0);
                tblFacDet.changeSelection(0, INT_TBL_FEC_VEN_CHQ, true, true);
                tblFacDet.requestFocus();
                return false;
            }
        }
                
        return true;
    }
       
   
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {                        
            //Configurar JTable: Establecer el modelo.            
            this.setTitle(objParSis.getNombreMenu()+" V2.4");
            lblTit.setText(objParSis.getNombreMenu());            
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(35);  //Almacena las cabeceras
            vecCab.clear();
            
            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            
            ///strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            strFecDia=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
//            System.out.println("---fecha del sistema de hoy---: " + strFecDia);
    
            
            configurarVenConBco();
            //Eddye_ventana de documentos pendientes///
            configurarVenConDocAbi();
            
            vecCab.add(INT_TBL_LINEA,"");///0
            vecCab.add(INT_TBL_SEL,"");///1
            vecCab.add(INT_TBL_BUT_DAT,"...");///2
            vecCab.add(INT_TBL_COD_CLI,"Cï¿½d.Cli.");///3
            vecCab.add(INT_TBL_NOM_CLI,"Nom.Cli.");///4
            vecCab.add(INT_TBL_COD_LOC,"Cï¿½d.Loc.");///5
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc.");///6
            vecCab.add(INT_TBL_NOM_TIP_DOC,"Nom.Tip.Doc.");///7
            vecCab.add(INT_TBL_COD_DOC,"Cï¿½d.Doc.");///8
            vecCab.add(INT_TBL_COD_REG,"Cï¿½d.Reg.");///9
            vecCab.add(INT_TBL_NUM_DOC,"Nï¿½m.Doc.");///10
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");///11
            vecCab.add(INT_TBL_DIA_CRE,"Dï¿½a.Crï¿½.");///12
            vecCab.add(INT_TBL_FEC_VEN,"Fec.Ven.");///13
            vecCab.add(INT_TBL_VAL_DOC,"Val.Doc.");///14
            vecCab.add(INT_TBL_VAL_PEN,"Val.Pen.");///15
            vecCab.add(INT_TBL_COD_BAN,"Cod.Ban.");///16
            vecCab.add(INT_TBL_DES_BAN,"Banco");///17
            vecCab.add(INT_TBL_BUT_BAN,"...");///18
            vecCab.add(INT_TBL_NOM_BAN,"Nom.Ban.");///19
            vecCab.add(INT_TBL_NUM_CTA,"Nï¿½m.Cta.");///20
            vecCab.add(INT_TBL_NUM_CHQ,"Nï¿½m.Chq.");///21
            vecCab.add(INT_TBL_FEC_REC_CHQ,"Fec.Rec.Chq.");///22
            vecCab.add(INT_TBL_FEC_VEN_CHQ, "Fec.Ven.Chq.");///23
            vecCab.add(INT_TBL_VAL_CHQ,"Val.Chq.");///24
            vecCab.add(INT_TBL_EST_REG, "Est.Reg.");///25
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblFacDet.setModel(objTblMod);      
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblFacDet);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_NUM_DOC);
            arlAux.add("" + INT_TBL_FEC_VEN_CHQ);
            objTblMod.setColumnasObligatorias(arlAux);
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            arlAux=null;
            

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true); 
            objTblCelRenLbl.setFormatoNumerico("###,###,##0.00");
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);            
            objTblCelRenLbl=null;
            
            ///renderizador para centrar los datos de las columnas///
            objTblCelRenLbl=new ZafTblCelRenLbl();            
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);                        
            objTblCelRenLbl=null;
            
            ///renderizador para centrar los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            ///renderizador para centrar los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_REC_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            
            
            tblFacDet.setRowSelectionAllowed(true);
            tblFacDet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblFacDet,INT_TBL_LINEA);
            tblFacDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(20);///0
            tblFacDet.getColumnModel().getColumn(INT_TBL_SEL).setPreferredWidth(20);///1
            tblFacDet.getColumnModel().getColumn(INT_TBL_BUT_DAT).setPreferredWidth(25);///2            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_CLI).setPreferredWidth(50);///3
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_CLI).setPreferredWidth(120);///4
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_LOC).setPreferredWidth(45);///5
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(50);///6
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setPreferredWidth(60);///7
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(10);///8
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setPreferredWidth(10);///9
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(65);///10
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_DOC).setPreferredWidth(90);///11
            tblFacDet.getColumnModel().getColumn(INT_TBL_DIA_CRE).setPreferredWidth(50);///12
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN).setPreferredWidth(90);///13
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_DOC).setPreferredWidth(80);///14
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_PEN).setPreferredWidth(80);///15            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setPreferredWidth(30);///16
            tblFacDet.getColumnModel().getColumn(INT_TBL_DES_BAN).setPreferredWidth(65);///17
            tblFacDet.getColumnModel().getColumn(INT_TBL_BUT_BAN).setPreferredWidth(25);///18
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_BAN).setPreferredWidth(90);///19
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_CTA).setPreferredWidth(80);///20
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(80);///21
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_REC_CHQ).setPreferredWidth(100);///22
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN_CHQ).setPreferredWidth(100);///23
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setPreferredWidth(100);///24
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setPreferredWidth(30);///25
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFacDet.getTableHeader().setReorderingAllowed(false);
           
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setResizable(false);    

            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_REC_CHQ).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN_CHQ).setCellEditor(new Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi(strFormatoFecha));
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_SEL);///1
            vecAux.add("" + INT_TBL_BUT_DAT);///2
            vecAux.add("" + INT_TBL_NUM_DOC);///10
            vecAux.add("" + INT_TBL_DES_BAN);///17
            vecAux.add("" + INT_TBL_BUT_BAN);///18
            vecAux.add("" + INT_TBL_NUM_CTA);///20
            vecAux.add("" + INT_TBL_NUM_CHQ);///21
            vecAux.add("" + INT_TBL_FEC_REC_CHQ);///22
            vecAux.add("" + INT_TBL_FEC_VEN_CHQ);///23
            vecAux.add("" + INT_TBL_VAL_CHQ);///24
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblEdi=new ZafTblEdi(tblFacDet);
            ///setEditable(true);
                        
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tblFacDet.getColumnModel().getColumn(INT_TBL_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelEdiChkMain=new ZafTblCelEdiChk();
            tblFacDet.getColumnModel().getColumn(INT_TBL_SEL).setCellEditor(objTblCelEdiChkMain);
            
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {                           
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
//                                System.out.println("---afterEdit--despues de dar click EN CELDA DE SELECCION---");
                                
                                double valpnd = Math.abs(objUti.parseDouble(objTblMod.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_VAL_PEN)));
//                                System.out.println("---valpnd--- " + valpnd);
                                
                                ///if(objTblCelEdiChkMain.isChecked())
                                if(objTblCelEdiChkMain.isSelected() == true)
                                {
//                                    System.out.println("---hizo click---");
                                    ///objTblMod.setValueAt(objTblMod.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_VAL_PEN),tblFacDet.getSelectedRow(),INT_TBL_VAL_CHQ);
                                    objTblMod.setValueAt(new Double(valpnd),tblFacDet.getSelectedRow(),INT_TBL_VAL_CHQ);
                                    rtnDatosRegSel();
                                    K++;
//                                    System.out.println("---selecciono K = " + K + " registros desde casilla de seleccion---" );
                                    objTblMod.setValueAt(new String(strFecDia), tblFacDet.getSelectedRow(),INT_TBL_FEC_REC_CHQ);
                                }
                                else
                                {
                                    objTblMod.setValueAt(null, tblFacDet.getSelectedRow(), INT_TBL_VAL_CHQ);
                                    K--;
//                                    System.out.println("---desactivo K = " + K + " registros desde casilla de seleccion---" );
                                    objTblMod.setValueAt(null, tblFacDet.getSelectedRow(), INT_TBL_FEC_REC_CHQ);
                                }
                }
            });
              
///////////////////////////////////////////////////////////////////////////////////////////////////////
            objTblCelEdiTxtChq=new ZafTblCelEdiTxt(tblFacDet);
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setCellEditor(objTblCelEdiTxtChq);
///////////////////////////////////////////////////////////////////////////////////////////////////////                       
            objTblCelEdiTxtChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtChq.getText().equals(""))
                    {
                        objTblMod.setValueAt( new Boolean(false), tblFacDet.getSelectedRow(), INT_TBL_SEL);
                        K--;
//                        System.out.println("---se borraron K = " + K + " registros desde el valor del cheque---" );
                    }
                    else
                    {
                        objTblMod.setValueAt(new Boolean(true), tblFacDet.getSelectedRow(), INT_TBL_SEL);
                        K++;
//                        System.out.println("---se activaron K = " + K + " registros desde el valor del cheque---" );
                    }
                            }

                });
         
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
            //Metodo Renderizador para el Boton de informacion del banco   
            ////////////////
            objTblCelRenBut=new ZafTblCelRenBut();
            tblFacDet.getColumnModel().getColumn(INT_TBL_BUT_DAT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            //------------------------------------------------------------------
            //Eddye_ventana de documentos pendientes//
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblFacDet.getColumnModel().getColumn(INT_TBL_BUT_DAT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFilSel, intFilSelVenCon[], i, j;
                    String strCodCli, strNomCli;
                    if (tblFacDet.getSelectedColumn()==INT_TBL_BUT_DAT)
                    {
                        objVenConCxC01.setVisible(true);
                    }
                    if (objVenConCxC01.getSelectedButton()==objVenConCxC01.INT_BUT_ACE)
                    {
                        intFilSel=tblFacDet.getSelectedRow();
                        intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                        strCodCli=objVenConCxC01.getCodigoCliente();
                        strNomCli=objVenConCxC01.getNombreCliente();
                        j=intFilSel;
                        for (i=0; i<intFilSelVenCon.length; i++)
                        {
                            if (objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_LIN)!="P")
                            { 
                                objTblMod.insertRow(j);
                                objTblMod.setValueAt(strCodCli, j, INT_TBL_COD_CLI);
                                objTblMod.setValueAt(strNomCli, j, INT_TBL_NOM_CLI);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_COD_LOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_COD_TIP_DOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_NOM_TIP_DOC);
                                ///objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_NOMDOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_COD_DOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_COD_REG);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_FEC_DOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DIA_CRE);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_FEC_VEN);
//                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_POR_RET);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_VAL_DOC);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_PEN), j, INT_TBL_VAL_PEN);                                
                                ///objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_EST_POS), j, INT_TBL_STSCHE);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_BAN), j, INT_TBL_COD_BAN);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NOM_BAN), j, INT_TBL_NOM_BAN);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NUM_CTA), j, INT_TBL_NUM_CTA);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NUM_CHQ), j, INT_TBL_NUM_CHQ);
                                objTblMod.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_CHQ), j, INT_TBL_VAL_CHQ);
                                objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                                j++;
                            }
                        }
                        tblFacDet.requestFocus();
                        objTblMod.removeEmptyRows();
                    }
                }
            });            
            //------------------------------------------------------------------
            
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
            //Metodo Renderizador para el Boton de informacion del banco   
            ////////////////
            objTblCelRenBut=new ZafTblCelRenBut();
            tblFacDet.getColumnModel().getColumn(INT_TBL_BUT_BAN).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //Configurar JTable: Editor de celdas.
            int intColVenBco[]=new int[3];
            intColVenBco[0]=1;
            intColVenBco[1]=2;
            intColVenBco[2]=3;
            int intColTblBco[]=new int[3];
            intColTblBco[0]=INT_TBL_COD_BAN;
            intColTblBco[1]=INT_TBL_DES_BAN;
            intColTblBco[2]=INT_TBL_NOM_BAN;
            
            objTblCelEdiTxtVcoBco=new ZafTblCelEdiTxtVco(tblFacDet, vcoBan, intColVenBco, intColTblBco);
            tblFacDet.getColumnModel().getColumn(INT_TBL_DES_BAN).setCellEditor(objTblCelEdiTxtVcoBco);
            objTblCelEdiTxtVcoBco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBan.setCampoBusqueda(1);
                    vcoBan.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoBco.isConsultaAceptada())
                    {
//                        System.out.println("---1.- desban---");
                    }
                }
            });
            
            objTblCelEdiButVcoBco=new ZafTblCelEdiButVco(tblFacDet, vcoBan, intColVenBco, intColTblBco);
            tblFacDet.getColumnModel().getColumn(INT_TBL_BUT_BAN).setCellEditor(objTblCelEdiButVcoBco);
            objTblCelEdiButVcoBco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoBco.isConsultaAceptada())
                    {
//                        System.out.println("---2.- botban---");
                    }
                }
            });
            intColVenBco=null;
            intColTblBco=null;
            ///////////////            
            
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            
            //Metodo de renderizador para editar texto en la columna del Numero de Documento//
            objTblCelEdiTxtNumDoc=new ZafTblCelEdiTxt(tblFacDet);            
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellEditor(objTblCelEdiTxtNumDoc);                    
            objTblCelEdiTxtNumDoc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel=Integer.parseInt(""+tblFacDet.getSelectedRow());
                    k=0;
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {                                                                                                                  
                    if (objTblCelEdiTxtNumDoc.getText().equals(""))
                    {
                        objTblMod.setChecked( false, tblFacDet.getSelectedRow(), INT_TBL_SEL);
                    }
                    else
                    {
                        ///System.out.println("---EL DATO NO SE REPITE AHORA...ESO CREO--: " + k);
//                        System.out.println("---intFilSel---NUM_DOC--- " + intFilSel);
                        
                        ///listaDocPnd(intFilSel);
                        listaDocPostFechar();
                        
//                        strAux=objUti.parseString(objTblMod.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_LINEA));
//                        System.out.println("---strAux estado de la linea--numdoc--: " + strAux);
                    }
                }
            });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
       private void listaDocPostFechar()
       {
       java.sql.Connection conTmpPnd;
       java.sql.Statement stmTmpPnd;
       java.sql.ResultSet rstTmpPnd;
       String strSQL, strAux;
       boolean blnRes=true;
       int intCanRegCon=0;
       
       try{
           
           conTmpPnd=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conTmpPnd!=null){
               stmTmpPnd=conTmpPnd.createStatement();              
               strAux="";
               
               
               strSQL="";  
               strSQL+=" SELECT COUNT(*)";
               strSQL+=" FROM (";
               strSQL+=" SELECT a2.co_loc, a2.co_cli, a2.tx_nomcli, a3.co_tipdoc, a3.tx_descor, a2.fe_doc, a1.ne_diacre, a1.fe_ven, ";
               strSQL+=" a1.co_reg,  a1.co_doc, a1.co_tipret as CodTipRet, a1.nd_porret, (a1.mo_pag) as monDoc, a1.nd_abo as AboDoc, (a1.mo_pag+a1.nd_abo) as valPnd, ";
               strSQL+=" a1.st_sop, a1.st_entsop, a1.st_pos, a1.co_loc, a2.ne_numdoc, a1.st_pos, a1.co_banchq, a1.st_reg, ";
               strSQL+=" a1.tx_numctachq, a1.fe_venchq, a1.fe_recchq, a1.nd_monchq, a1.tx_numchq, a1.ne_diagra ";
               strSQL+=" FROM tbm_pagmovinv AS a1 ";
               strSQL+=" INNER JOIN tbm_cabmovinv AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipdoc AND a1.co_doc = a2.co_doc)";
               strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp = a3.co_emp AND a1.co_loc = a3.co_loc AND a1.co_tipDoc = a3.co_tipdoc) ";
               strSQL+=" WHERE ";
               strSQL+=" a1.co_emp =" + objParSis.getCodigoEmpresa();
               ///strSQL+=" AND a1.co_loc =" + objParSis.getCodigoLocal();
               strSQL+=" AND a2.ne_numdoc =" + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_NUM_DOC);
               strSQL+=" AND abs(a1.mo_pag+a1.nd_abo)>0 ";
               strSQL+=" AND (a2.st_reg IN ('A','C','R','F') AND a1.st_reg IN ('A','C')) ";
               strSQL+=" AND (a1.nd_porret=0 OR a1.nd_porret is null)";
               strSQL+=" ";
               strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a1.co_reg ";
               strSQL+=" ) AS B1";
               intCanRegCon=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
               CAN_REG_CON = intCanRegCon;
               System.out.println("---CANTIDAD DE REGISTROS CONSULTADOS --CAN_REG_CON--: " + CAN_REG_CON);
               
               
               strSQL="";               
               strSQL+=" SELECT a2.co_loc, a2.co_cli, a2.tx_nomcli, a3.co_tipdoc, a3.tx_descor, a2.fe_doc, a1.ne_diacre, a1.fe_ven, ";
               strSQL+=" a1.co_reg,  a1.co_doc, a1.co_tipret as CodTipRet, a1.nd_porret, (a1.mo_pag) as monDoc, a1.nd_abo as AboDoc, (a1.mo_pag+a1.nd_abo) as valPnd, ";
               strSQL+=" a1.st_sop, a1.st_entsop, a1.st_pos, a1.co_loc, a2.ne_numdoc, a1.st_pos, a1.co_banchq, a1.st_reg, ";
               strSQL+=" a1.tx_numctachq, a1.fe_venchq, a1.fe_recchq, a1.nd_monchq, a1.tx_numchq, a1.ne_diagra ";
               strSQL+=" FROM tbm_pagmovinv AS a1 ";
               strSQL+=" INNER JOIN tbm_cabmovinv AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipdoc AND a1.co_doc = a2.co_doc)";
               strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp = a3.co_emp AND a1.co_loc = a3.co_loc AND a1.co_tipDoc = a3.co_tipdoc) ";
               strSQL+=" WHERE ";
               strSQL+=" a1.co_emp =" + objParSis.getCodigoEmpresa();
               ///strSQL+=" AND a1.co_loc =" + objParSis.getCodigoLocal();
               strSQL+=" AND a2.ne_numdoc =" + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_NUM_DOC);
               strSQL+=" AND abs(a1.mo_pag+a1.nd_abo)>0 ";
               strSQL+=" AND (a2.st_reg IN ('A','C','R','F') AND a1.st_reg IN ('A','C')) ";
               strSQL+=" AND (a1.nd_porret=0 OR a1.nd_porret is null)";
               strSQL+=" ";
               strSQL+=" ORDER BY a1.co_tipdoc, a1.co_doc, a1.co_reg ";
               
               System.out.println("---SQL de lista Doc a postfechar: " + strSQL);
               rstTmpPnd=stmTmpPnd.executeQuery(strSQL);
               //vecDat.clear();
                //while (rstTmpPnd.next())
               for(int i=0;rstTmpPnd.next();i++)
                {
                    
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LINEA,"");///0
                    

                    String strEstPoschq="";
                    strEstPoschq=rstTmpPnd.getString("st_pos");
                    
                    vecReg.add(INT_TBL_SEL,"");///1
                    vecReg.add(INT_TBL_BUT_DAT,"");///2
                    vecReg.add(INT_TBL_COD_CLI,rstTmpPnd.getString("co_cli"));///3
                    vecReg.add(INT_TBL_NOM_CLI,rstTmpPnd.getString("tx_nomcli"));///4
                    vecReg.add(INT_TBL_COD_LOC,rstTmpPnd.getString("co_loc"));///5
                    vecReg.add(INT_TBL_COD_TIP_DOC,rstTmpPnd.getString("co_tipdoc"));///6
                    vecReg.add(INT_TBL_NOM_TIP_DOC,rstTmpPnd.getString("tx_descor"));///7
                    vecReg.add(INT_TBL_COD_DOC,rstTmpPnd.getString("co_doc"));///8
                    vecReg.add(INT_TBL_COD_REG,rstTmpPnd.getString("co_reg"));///9
                    vecReg.add(INT_TBL_NUM_DOC,rstTmpPnd.getString("ne_numdoc"));///10
                    vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rstTmpPnd.getDate("fe_doc"), "dd/MM/yyyy"));///11
                    vecReg.add(INT_TBL_DIA_CRE,rstTmpPnd.getString("ne_diacre"));///12
                    vecReg.add(INT_TBL_FEC_VEN, objUti.formatearFecha(rstTmpPnd.getDate("fe_ven"), "dd/MM/yyyy"));///13
                    ///vecReg.add(INT_TBL_VAL_DOC,""+Math.abs(rstTmpPnd.getDouble("monDoc")));///14
                    vecReg.add(INT_TBL_VAL_DOC,""+rstTmpPnd.getDouble("monDoc"));///14
                    ///vecReg.add(INT_TBL_VAL_PEN,""+Math.abs(rstTmpPnd.getDouble("valPnd")));///15
                    vecReg.add(INT_TBL_VAL_PEN,""+rstTmpPnd.getDouble("valPnd"));///15
                                       
                    if(strEstPoschq.equals("S"))
                    {
                        
                        int intCodBco=rstTmpPnd.getInt("co_banchq");
                        ///System.out.println("El codigo del banco es:"+intCodBco);
                        
                        //vecReg.add(INT_TBL_CO_BAN, rstTmpPnd.getString("co_banchq"));
                        vecReg.add(INT_TBL_COD_BAN, ""+intCodBco);///16
                        String strNomBco=retNomBco(intCodBco);
                        vecReg.add(INT_TBL_DES_BAN,strNomBco);///17
                        vecReg.add(INT_TBL_BUT_BAN,"");///18                        
                        vecReg.add(INT_TBL_NOM_BAN,strNomBco);///19
                        vecReg.add(INT_TBL_NUM_CTA, rstTmpPnd.getString("tx_numctachq"));///20
                        vecReg.add(INT_TBL_NUM_CHQ,rstTmpPnd.getString("tx_numchq"));///21
                        //vecReg.add(INT_TBL_FE_REC_CHE, objUti.formatearFecha(rstTmpPnd.getDate("fe_recchq"), "dd-MM-yyyy"));                    
                        //vecReg.add(INT_TBL_FE_VEN_CHE, objUti.formatearFecha(rstTmpPnd.getDate("fe_venchq"), "dd-MM-yyyy"));

                        
                        String tmp2=rstTmpPnd.getString("fe_recChq");                        

                        if(tmp2==null){
                            String tmpFec="";
                            vecReg.add(INT_TBL_FEC_REC_CHQ, tmpFec);///22
                        }
                            
                        else{
                            java.util.Calendar calFecPag = java.util.Calendar.getInstance();                        
                            calFecPag.setTime(rstTmpPnd.getDate("fe_recchq"));
                            Librerias.ZafDate.ZafDatePicker dtePckPag = 
                             new Librerias.ZafDate.ZafDatePicker(calFecPag.get(java.util.Calendar.DAY_OF_MONTH), 
                                                                (calFecPag.get(java.util.Calendar.MONTH)+1), 
                                                                calFecPag.get(java.util.Calendar.YEAR), new javax.swing.JFrame(), "d/m/y"     
                             );                                                
                            vecReg.add(INT_TBL_FEC_REC_CHQ, dtePckPag.getText());///22
                        }
                        
                        String tmp3=rstTmpPnd.getString("fe_venChq");
                        if(tmp3==null){
                            String tmpFec3="";
                            vecReg.add(INT_TBL_FEC_VEN_CHQ, tmpFec3);///23
                        }                            
                        else{
                            java.util.Calendar calFecPag2 = java.util.Calendar.getInstance();                        
                            calFecPag2.setTime(rstTmpPnd.getDate("fe_venChq"));
                            Librerias.ZafDate.ZafDatePicker dtePckPag2 = 
                             new Librerias.ZafDate.ZafDatePicker(calFecPag2.get(java.util.Calendar.DAY_OF_MONTH), 
                                                                (calFecPag2.get(java.util.Calendar.MONTH)+1), 
                                                                calFecPag2.get(java.util.Calendar.YEAR), new javax.swing.JFrame(), "d/m/y"     
                             );                                                
                            vecReg.add(INT_TBL_FEC_VEN_CHQ, dtePckPag2.getText());///23
                        }
                        
                        
                        double dblAuxAbo=rstTmpPnd.getDouble("nd_monchq");
                        vecReg.add(INT_TBL_VAL_CHQ, ""+dblAuxAbo);///24
                        
                        vecReg.add(INT_TBL_EST_REG,rstTmpPnd.getString("st_reg"));///25
                        

                        if (dblAuxAbo!=0)
                        vecReg.setElementAt(new Boolean(true),INT_TBL_SEL);
                        ///System.out.println("esta postfechado SI");
                    }
                    else
                    {                                        
                        vecReg.add(INT_TBL_COD_BAN,"");///16
                        vecReg.add(INT_TBL_DES_BAN,"");///17
                        vecReg.add(INT_TBL_BUT_BAN,"");///18
                        vecReg.add(INT_TBL_NOM_BAN,"");///19
                        vecReg.add(INT_TBL_NUM_CTA,"");///20
                        vecReg.add(INT_TBL_NUM_CHQ,"");///21
                        vecReg.add(INT_TBL_FEC_REC_CHQ,"");///22
                        vecReg.add(INT_TBL_FEC_VEN_CHQ,"");///23
                        vecReg.add(INT_TBL_VAL_CHQ,"");///24
//                        vecReg.add(INT_TBL_DIA_GRA,rstTmpPnd.getString("ne_diagra"));
//                        vecReg.add(INT_TBL_COD_LOC,rstTmpPnd.getString("co_loc"));
                        vecReg.add(INT_TBL_EST_REG,rstTmpPnd.getString("st_reg"));///25
//                        System.out.println("esta postfechado NO");
                    }
                    vecDat.add(vecReg);
                }
                objTblMod.setData(vecDat);
                tblFacDet.setModel(objTblMod);
                vecDat.clear();
                rstTmpPnd.close();
                stmTmpPnd.close();
                conTmpPnd.close();
                rstTmpPnd=null;
                stmTmpPnd=null;
                conTmpPnd=null;
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
    }

   /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
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
            arlAli.add("Cï¿½d.Reg.");
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
            ///System.out.println("--- configurarVenConBco() -- "+strSQL);
            
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
    
    //Eddye_ventana de documentospendientes///
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Documentos abiertos".
     */
    private boolean configurarVenConDocAbi()
    {
        boolean blnRes=true;
        try
        {
            objVenConCxC01=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de documentos abiertos");
            
            ///cobros masivos//
            if(objParSis.getCodigoMenu()== 256)
                objVenConCxC01.setTipoConsulta(2);
            else
            {
                objVenConCxC01.setTipoConsulta(2);
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
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
//            
            if(intCodUsr==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                System.out.println("---Query consulta usuario ADMIN: "+strSQL);
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.co_usr";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                System.out.println("---Query consulta usuario VARIOS: "+strSQL);
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
    

    protected String strNomDctoDos(String codDocDos){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        int intCodDos=Integer.parseInt(codDocDos);
        System.out.println("el codigo es:"+intCodDos);
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select a1.tx_descor from tbm_cabtipdoc as a1";
                que+=" where";
                que+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and a1.co_tipdoc=" + intCodDos + "";                                                
                System.out.println("estoy dentro de la funcion de tipdoc2:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_descor");
                    System.out.println("este es el nombre del document:" +auxTipDoc);
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
    
    
    protected String strNomBco(String codBco){
        java.sql.Connection conBco;
        java.sql.Statement stmBco;
        java.sql.ResultSet rstBco;
        String que, auxBco="";        
        int intCodBco=Integer.parseInt(codBco);
        System.out.println("el codigo es:"+intCodBco);
        try{            
            conBco=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conBco!=null){
                stmBco=conBco.createStatement();
                que="";                                
                que+=" select a1.tx_descor from tbm_var as a1, tbm_grpvar as a2";
                que+=" where a1.co_grp=a2.co_grp and";                
                que+=" a2.co_grp=" + INT_GRP_BCO + "";
                System.out.println("estoy dentro de la funcion de bancos:"+que);
                rstBco=stmBco.executeQuery(que);
                if (rstBco.next()){
                    auxBco=rstBco.getString("tx_descor");
                    System.out.println("este es el nombre del document:" +auxBco);
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
        return auxBco;
        
    }        
    
 
    
    public void clnTextos() {
        //Cabecera
        
//        txtDoc.setText("");
//        txtDoc.setEditable(false);
//        txtFecDoc.setText("");
//        txtTipDocCod.setText("");
//        txtTipDocNom.setText("");
//        txtCodDoc.setText("");
      
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
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="select tx_descor from tbm_cabtipdoc";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" and co_tipdoc=" + tmpFun + "";
                System.out.println("en cargarCabReg:"+strSQL);
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
    
    
    /**
     * Esta funciï¿½n permite cargar datos del registro seleccionado.
     * @return true: Si se pudo cargar los datos del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean rtnDatosRegSel()
    {
        int intPosRel, intCodEmp, intCanRegCan=0, intCanRegCon=0, intUltReg=0;
        boolean blnRes=true;
        double dblSumRegPag=0.00, dblValTotFac=0.00, dblSalFacPag=0.00, valabo=0.00;
        double valpnd=0.00, valret=0.00;
        String stRegRep="", strDiaGra="";
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conRee=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conRee!=null)
            {
                stmRee = conRee.createStatement();
                
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                if(objTblCelEdiChkMain.isChecked())
                ///if(objTblCelEdiChkMain.isChecked() == true)
                {
                    strSQL="";
                    strSQL+=" SELECT ";
                    strSQL+=" a1.co_emp as CoEmp, a1.co_loc as CoLoc, a1.co_tipdoc as CoTipDoc, a1.co_doc as coDoc, a1.ne_numdoc as NeNumDoc, a2.co_reg as coReg,  ";
                    strSQL+=" a1.co_cli, a1.tx_nomcli, a1.co_forpag, a1.tx_desforpag, a1.fe_doc as fecDoc, round(abs(a1.nd_tot),2) as TotFac, ";
                    strSQL+=" round(abs(a2.mo_pag),2) as valDoc, round(abs(a2.nd_abo),2) as Abono, round(abs(a2.mo_pag+a2.nd_abo),5) as valPnd, ";
                    strSQL+=" a2.ne_diaCre as diaCre, a2.ne_diagra as DiaGra, a2.fe_ven as fecVen, a2.co_tipret as CodTipRet, a2.nd_porRet as porRet, a2.st_reg as stReg, a2.st_regrep as stRegRep ";
                    strSQL+="FROM tbm_cabmovinv AS a1 ";
                    strSQL+="INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                    strSQL+=" AND a1.co_loc = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipdoc = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_TIP_DOC) + "";
                    strSQL+=" AND a1.co_doc = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_DOC) + "";
                    ///strSQL+=" AND a2.co_reg = " + rstCabRee.getString("CoReg") + "";
                    strSQL+=" AND a2.co_reg = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_REG) + "";
                    strSQL+=" AND a1.st_reg <> 'E' AND a2.st_reg IN ('A','C') ";
                    ///strSQL+=" AND round(abs(a2.mo_pag+a2.nd_abo),2) = 0 ";
                    strSQL+=" ORDER BY a2.co_reg ";
                    System.out.println("---QUERY--rtnDatosRegSel(): " +strSQL);
                    rstRee=stmRee.executeQuery(strSQL);
//
//                    ///if (rstRee.next())
                    for(int i=0 ; rstRee.next() ; i++)
                    {                    
                        valabo = Double.parseDouble(rstRee.getString("Abono"));
                        valpnd = Double.parseDouble(rstRee.getString("valPnd"));
                        valret = Double.parseDouble(rstRee.getString("porRet"));
                        fecdocSel = objUti.formatearFecha(rstRee.getDate("fecDoc"),"dd/MM/yyyy");
                        fecVenDocSel = objUti.formatearFecha(rstRee.getDate("fecVen"),"dd/MM/yyyy");
                        stRegRep = rstRee.getString("stRegRep");
                        strDiaGra = rstRee.getString("DiaGra");
                    }
                    VAL_ABO_REG_SEL = valabo;
                    EST_REG_REP = stRegRep;
                    DIA_GRA = strDiaGra;
//                    System.out.println("---VALABO_REG_SEL: " + valabo);
//                    System.out.println("---VALPND_REG_SEL: " + valpnd);
//                    System.out.println("---VALRET_REG_SEL: " + valret);
//                    System.out.println("---FECDOC_REG_SEL: " + fecdocSel);
//                    System.out.println("---FEC_VEN_DOC_REG_SEL: " + fecVenDocSel);
//                    System.out.println("---EST_REG_REP: " + EST_REG_REP);
//                    System.out.println("---DIA_GRA: " + DIA_GRA);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    
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
    
    
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            ///System.out.println("ZafAsiDia.tableChanged");
            ///System.out.println("Celda modificada: (" + tblFacDet.getEditingRow() + "," + tblFacDet.getEditingColumn() + ")");
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    System.out.println("Se elimina fila...");
                    //camSelChk();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
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
    
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

 
    //para el boton de guardar
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarTbmPagMovInv())
                {
                        con.commit();
                        blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    private boolean actualizarTbmPagMovInv()
    {
        int intCodEmp, i;
        double dblAbo1, dblAbo2;
        boolean blnRes=true;
        try
        {                
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        //if(objTblMod.getValueAt(i,INT_TBL_LINEA)=="M"){
//                        System.out.println(" ---ENTRO AL FOR---DENTRO DE actualizarTbmPagMovInv()--- ");
                        if (objTblMod.isChecked(i, INT_TBL_SEL))
                        {
//                            System.out.println(" ---ENTRO AL IF---DENTRO DE actualizarTbmPagMovInv()--- ");
                            String strFecRecChq=objUti.formatearFecha(tblFacDet.getValueAt(i, INT_TBL_FEC_REC_CHQ).toString(), "dd/MM/yyy", "yyyy/MM/dd");
                            String strFecVncChq=objUti.formatearFecha(tblFacDet.getValueAt(i, INT_TBL_FEC_VEN_CHQ).toString(), "dd/MM/yyy", "yyyy/MM/dd");
                            
//                            System.out.println(" ---FECHA_RECEPCION_CHEQUE---strFecRecChq--- " + strFecRecChq);
//                            System.out.println(" ---FECHA_VENCIMIENTO_CHEQUE---strFecVncChq--- " + strFecVncChq);
                            
                            
                                   /////////////////////////
                                   
                                   strSQL="";
                                   strSQL+="Update tbm_pagMovInv set";
                                   strSQL+=" st_entSop='S',";
                                   strSQL+=" st_pos='S',";
                                   strSQL+=" fe_recchq='#" + strFecRecChq + "#',";
                                   strSQL+=" fe_venchq='#" + strFecVncChq + "#',";
                                   strSQL+=" co_banChq=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_BAN)=="")?null:tblFacDet.getValueAt(i, INT_TBL_COD_BAN)) + ",";
                                   strSQL+=" tx_numCtaChq='" + ((tblFacDet.getValueAt(i, INT_TBL_NUM_CTA)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_NUM_CTA)) + "',";
                                   strSQL+=" tx_numChq='" + ((tblFacDet.getValueAt(i, INT_TBL_NUM_CHQ)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_NUM_CHQ)) + "',";
                                   strSQL+=" nd_monChq=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_VAL_CHQ), 3) + "";
                                   strSQL+=" where";
                                   strSQL+=" co_emp=" + objParSis.getCodigoEmpresa() + " and" ;
                                   ///strSQL+=" co_loc=" + objParSis.getCodigoLocal() + " and";
                                   strSQL+=" co_loc=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_LOC)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COD_LOC)) + " and";
                                   strSQL+=" co_tipdoc=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_TIP_DOC)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COD_TIP_DOC)) + " and";
                                   strSQL+=" co_doc=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_DOC)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COD_DOC)) + " and";
                                   strSQL+=" co_reg=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_REG)==null)?"0": tblFacDet.getValueAt(i, INT_TBL_COD_REG)) + "";
                                   System.out.println("---Query en actualizarTbmpagmovinv:"+strSQL);
                                   stm.executeUpdate(strSQL);
                                   
                              ///}
                        }
//                        else
//                            System.out.println(" ---ELSE---DENTRO DE actualizarTbmPagMovInv()--- ");
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
    
 
     private void mostrarMsgInfReg(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }              
    

     
    public String retNomBco(int intCodBco)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_descor from tbm_var";
                que+=" where co_reg=" + intCodBco + "";                                                
                System.out.println("el query del nombre del bco es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_descor");
                    System.out.println("este es el nombre del bco:" +auxTipDoc);
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
    
}




//CxC.ZafCxC01_hoy.ZafCxC01_hoy obj = new CxC.ZafCxC01_hoy.ZafCxC01_hoy(objParSis);
//this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
//obj.show();