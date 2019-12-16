/*
 * ZafAsiDia_v01.java   
 *
 * Created on 22 de junio de 2005, 08:55 AM
 * v0.12
 * modificado el 18 de junio del 2007
 *      Objetivo:
 *      - Al generar diarios se realice el proceso de mayorización en línea
 *      - Que no se permita insertar diarios de años que no han sido creados en el sistema
 *      - Que no se permita idouble
 ngresar, modificar, anular, eliminar documentos que tienen
 *          *cierre mensual*
 *          *cierre de cuentas contables*
 *          *cierre anual*
 *
 *
 * VERSION v0.41.4: 
 */
package Librerias.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafEvt.ZafAsiDiaListener;
import Librerias.ZafEvt.ZafAsiDiaEvent;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import java.math.BigDecimal;
import javax.swing.event.EventListenerList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Esta clase manipula los datos de un asiento de diario. Es decir que permite: insertar, consultar,
 * actualizar, eliminar y anular un asiento de diario. Las funciones de esta clase incluyen como 
 * argumento la conexión que se debe utilizar para trabajar con la base de datos y que permitirán el
 * manejo de transaccionamiento por parte de los usuarios que hagan uso de esta clase. Esto evita que
 * se grabe procesos incompletos. Por ejemplo: para grabar un diario hay que hacer 2 pasos: insertar
 * la cabecera y luego insertar el detalle del diario. Si se presenta un error al grabar el detalle
 * quedaría grabada la cabecera y una parte del detalle lo cual estaría mal porque se debería grabar
 * el diario completo o no grabar nada. Este problema se evita con el uso de transaccionamiento ya
 * que sólo cuando se completa la operación completa se procede a hacer el "Commit" y si la
 * operación no se completa se hace el "RollBack".
 * <BR><BR><B>Uso de la función "generarDiario".</B>
 * <BR>Para conocer la forma en que un asiento de diario ha sido generado se utiliza la función
 * "getGeneracionDiario". Esta función puede retornar uno de los siguientes valores:
 * <UL>
 * <LI>0=Diario todavía no ha sido generado.
 * <LI>1=Diario generado por el sistema.
 * <LI>2=Diario generado por el usuario.
 * </UL>
 * La forma en que un asiento de diario ha sido generado hace que la función
 * "generarDiario" se comporte de una de las siguientes maneras:
 * <UL>
 * <LI><I>Diario todavóa no ha sido generado:</I> Al invocar la función "generarDiario" se conecta a la base de datos
 * para obtener las cuentas contables que se utilizarón en el asiento de diario.
 * <LI><I>Diario generado por el sistema:</I> Al invocar la función "generarDiario" sólo se actualiza el valor del
 * debe y del haber.
 * <LI><I>Diario generado por el usuario:</I> Al invocar la función "generarDiario" no se hace nada porque se supone
 * que el usuario ha modificado manualmente el asiento de diario. Se consideró
 * que el sistema no debóa regenerar el asiento de diario automóticamente cuando
 * el usuario ha modificado manualmente el asiento de diario porque en algunos 
 * casos se agregan varias cuentas adicionales y si el programa de manera automótica
 * regenera el asiento de diario se perderóa todo lo que el usuario adicionó de 
 * manera manual. Por ejemplo, en Contabilidad el sistema puede generar un asiento
 * de diario con 2 cuentas y el usuario puede agregar 20 cuentas. Si el sistema
 * regenera el asiento de diario de manera automótica se borraróan todas las cuentas
 * que el usuario agregó manualmente. Eso podróa traer problemas ya que el usuario
 * podróa no darse cuenta que todo lo que hizo se reemplazó por las cuentas que
 * generó automóticamente el sistema.
 * </UL>
 * <BR><B>Uso de los listener.</B>
 * <BR>Primero se ejecutan los mótodos que inician con el prefijo "before" y al final se ejecutan
 * los mótodos que inician con el prefijo "after". Por ejemplo:
 * <UL>
 * <LI>1) Primero se ejecuta el mótodo "beforeInsertar",
 * <LI>2) Luego se ejecuta el mótodo "insertarDiario",
 * <LI>3) Y finalmente se ejecuta el mótodo "afterInsertar",
 * </UL>
 * <BR>Para los listener que controlan el JTable el orden de ejecución es el siguiente:
 * <UL>
 * <LI>1) Primero se ejecuta el mótodo "beforeEditarCelda",
 * <LI>2) Luego se ejecuta el mótodo "beforeConsultarCuentas",
 * <LI>3) Luego se ejecuta el mótodo "afterConsultarCuentas",
 * <LI>3) Y finalmente se ejecuta el mótodo "afterEditarCelda",
 * </UL>
 * <BR>Ejemplo de como utilizar los listener:
 * <BR>"objAsiDia" es la instancia de "ZafAsiDia_v01".
 * <PRE>
 *            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
 *               public void beforeInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
 *                   System.out.println("beforeInsertar");
 *                   //Cancelar la operación cuado sea necesario.
 *                   if (objAsiDia.getGlosa().equals(""))
 *                       objAsiDia.cancelarOperacion();
 *               }
 *               public void afterInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
 *                   System.out.println("afterInsertar");
 *               }
 *               public void beforeEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
 *                   System.out.println("beforeEditarCelda");
 *                   //Cancelar la operación cuado sea necesario.
 *                   if (objAsiDia.getGlosa().equals(""))
 *                       objAsiDia.cancelarOperacion();
 *               }
 *               public void afterEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
 *                   System.out.println("afterEditarCelda");
 *               }
 *               public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
 *                   System.out.println("beforeConsultarCuentas");
 *                   objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
 *               }
 *               public void afterConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
 *                   System.out.println("afterConsultarCuentas");
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los mótodos. Sólo se debe implementar los mótodos que en realidad necesita.
 * @author  Eddye Lino
 */
public class ZafAsiDia extends javax.swing.JPanel
{
    //Constantes:
    private static final int INT_BEF_INS=0;                 /**Antes de insertar: Indica "Before insertar".*/
    private static final int INT_AFT_INS=1;                 /**Despuós de insertar: Indica "After insertar".*/
    private static final int INT_BEF_CON=2;                 /**Antes de consultar: Indica "Before consultar".*/
    private static final int INT_AFT_CON=3;                 /**Despuós de consultar: Indica "After consultar".*/
    private static final int INT_BEF_MOD=4;                 /**Antes de modificar: Indica "Before modificar".*/
    private static final int INT_AFT_MOD=5;                 /**Despuós de modificar: Indica "After modificar".*/
    private static final int INT_BEF_ELI=6;                 /**Antes de eliminar: Indica "Before eliminar".*/
    private static final int INT_AFT_ELI=7;                 /**Despuós de eliminar: Indica "After eliminar".*/
    private static final int INT_BEF_ANU=8;                 /**Antes de anular: Indica "Before anular".*/
    private static final int INT_AFT_ANU=9;                 /**Despuós de anular: Indica "After anular".*/
    private static final int INT_BEF_EDI_CEL=10;            /**Antes de editar celda: Indica "Before editar celda".*/
    private static final int INT_AFT_EDI_CEL=11;            /**Despuós de editar celda: Indica "After editar celda".*/
    private static final int INT_BEF_CON_CTA=12;            /**Antes de consultar las cuentas: Indica "Before consultar cuentas".*/
    private static final int INT_AFT_CON_CTA=13;            /**Despuós de consultar las cuentas: Indica "After consultar cuentas".*/
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN=0;             //Lónea
    private static final int INT_TBL_DAT_COD_CTA=1;         //Código de la cuenta (Sistema).
    private static final int INT_TBL_DAT_NUM_CTA=2;         //Nómero de la cuenta (Preimpreso).
    private static final int INT_TBL_DAT_BUT_CTA=3;         //Botón de consulta.
    private static final int INT_TBL_DAT_NOM_CTA=4;         //Nombre de la cuenta.
    private static final int INT_TBL_DAT_DEB=5;             //Debe.
    private static final int INT_TBL_DAT_HAB=6;             //Haber.
    private static final int INT_TBL_DAT_REF=7;             //Referencia.
    private static final int INT_TBL_DAT_EST_CON=8;         //Estado de conciliación bancaria de la cuenta
    //Constantes: Datos de cuentas.
    private static final int INT_CTA_COD_DOC=0;             //Lónea
    private static final int INT_CTA_COD_CTA=1;             //Código de la cuenta (Sistema).
    private static final int INT_CTA_NUM_CTA=2;             //Nómero de la cuenta (Preimpreso).
    private static final int INT_CTA_NOM_CTA=3;             //Nombre de la cuenta.
    private static final int INT_CTA_UBI_CTA=4;             //Ubicación de la cuenta.
    
    //Variables generales.
    protected EventListenerList objEveLisLis=new EventListenerList();
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod, objTblModAux;
    private ZafTblEdi objTblEdi;                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtRef;                //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoCta;       //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCta;       //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                      //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;
    //Detectar cambios de valores en las celdas.
    private ZafTblPopMnu objTblPopMnu;                      //PopupMenu: Establecer PeopuMenó en JTable.
    private ZafVenCon vcoCta;                               //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecCta;
    private Vector vecAux;
    private boolean blnCon;                         //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private boolean blnAsiDiaEdi;                   //true: El asiento de diario es editable.
    private int intCodDia;                          //Código del diario (Sistema).
    private String strNumDia;                       //Nómero de diario.
    private java.util.Date datFecDia;               //Fecha del diario.
    private String strEstDia;                       //Estado del diario.
    private java.util.Date datFecIng;               //Fecha de ingreso.
    private java.util.Date datFecMod;               //Fecha de modificación.
    private int intCodUsrIng;                       //Código de usuario de ingreso.
    private int intCodUsrMod;                       //Código de usuario de modificación.
    private double dblMonDeb, dblMonHab;            //Monto del debe, monto del haber.
    private BigDecimal bdeMonDeb, bdeMonHab;        //Monto del debe, monto del haber en BigDecimal
    //private BigDecimal bdeMonHab;
    private double dblMonIniDeb, dblMonIniHab;      //Monto inicial del debe, monto del haber.
    private byte bytDiaGen;                         //0=Diario no generado; 1=Diario generado por el Sistema; 2=Diario generado por el usuario.
    private String strCodPro;                       //Códigos a procesar (Retenciones, Tipos de documentos, bodegas, etc.).
    
    private boolean blnCanOpe;                      //Determina si se debe cancelar la operación.
    
    private String strMe;
    //Variables usadas para capturar la fecha vieja y nueva usadas para reversar y actualizar en proceso de mayorizacion
    private int intRefAniNew;
    private int intRefMesNew;
    
    private int intRefAniOld;
    private int intRefMesOld;
    
    //////////variables para pruebas en cxp///////////
    private int intBanNumCta=0;
    private int intBanBotCta=0;
    
    //PARA NO PERMITIR GUARDAR DATOS POR CIERRE DE MES O DE AóO
    private ArrayList arlRegAniMes, arlDatAniMes;
    final int INT_ARL_ANI_CIE=0;
    final int INT_ARL_MES_CIE=1;
    final int INT_ARL_TIP_CIE=2;
    
    //PARA NO PERMITIR GUARDAR DATOS POR CIERRE DE CUENTAS
    private ArrayList arlRegAniMesCta, arlDatAniMesCta;
    final int INT_ARL_ANI_CTA_CIE=0;
    final int INT_ARL_MES_CTA_CIE=1;
    final int INT_ARL_COD_CTA=2;
    
    /*PARA GUARDAR VALORES INICIALES DE LA CUENTA DEL DETALLE DEL MODELO
     *ESTO ME SIRVE PARA LOS PROCESOS DE MODIFICACION, ANULACION, ELIMINACION
     *PUESTO Q CUANDO SE REALIZA UNO DE ESTOS PROCESOS, SE DEBE PRIMERO QUITAR EL VALOR INICIAL DE ESA CUENTA(VALOR DEL DIARIO AL CONSULTAR)
     *Y LUEGO SUMARLE/RESTARLE EL NUEVO VALOR(VALOR INGRESADO POR MODIFICACION, ANULACION, ELIMINACION)
     */
    private ArrayList arlRegMayOnLin, arlDatMayOnLin;
    private int INT_ARL_MAY_LIN_COD_EMP=0;
    private int INT_ARL_MAY_LIN_COD_CTA=1;
    private int INT_ARL_MAY_LIN_MON_DEB=2;
    private int INT_ARL_MAY_LIN_MON_HAB=3;
    private int INT_ARL_MAY_LIN_EST_REG=4;
    private int INT_ARL_MAY_LIN_EST_CON_BAN=5;

    /*Para obtener el diario que se ha generado cuando se hace algun cambio en el documento (cuando se genera automaricamente
     * como en el cheque que se selecciona un registro y automaticamente se genera el diario con el valor del documento sin
     * necesidad que el usuario escriba alguna cuenta)
     */
    private ArrayList arlRegDetDiaMod, arlDatDetDiaMod;
    private int INT_ARL_DET_DIA_MOD_COD_EMP=0;
    private int INT_ARL_DET_DIA_MOD_COD_CTA=1;
    private int INT_ARL_DET_DIA_MOD_MON_DEB=2;
    private int INT_ARL_DET_DIA_MOD_MON_HAB=3;
    private int INT_ARL_DET_DIA_MOD_EST_REG=4;
    private int INT_ARL_DET_DIA_MOD_EST_CON_BAN=5;
    private int INT_ARL_DET_DIA_MOD_REF=6;

    private String strEstCnfDoc;
    
    /*intTipPro=0 insercion
     * 1 modificacion, 
     * 2 anulacion, eliminacion
     */
    private int intTipPro;
    
    //ESTA VARIABLE SE CREO PARA Q CONTENGA EL CODTIPDOC PARA EL NUEVO ESQUEMA DE CTAS POR USUARIO
    private int intCodTipDocGlb;
    
    private java.util.Date datAuxMayAnt;       //Captura la fecha del metodo de consulta
    
    private String strTblActUltDoc;
    private String strCodGrpTipDoc;
    
    private ArrayList arlRegDiaRet, arlDatDiaRet;
    final int INT_ARL_DIA_RET_COD_RTE=0;
    final int INT_ARL_DIA_RET_VAL=1;
    final int INT_ARL_DIA_RET_PRC=2;
    
    
    private ZafDatePicker dtpFecSis;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private String strFecGua;
    private java.util.Date datFecDBMod;

    private int intChgBusCri;
    private ZafSegMovInv objSegMovInv;
    private int intCodCtaPro;

    /** Crea una nueva instancia de la clase ZafAsiDia_v01. */
    public ZafAsiDia(ZafParSis obj)
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        blnCanOpe=false;
        strEstCnfDoc="";
        /*
         * Hacer que se configure el JPanel luego de ser creado. Fue necesario configurar el JPanel
         * en un Thread porque si el usuario abróa la "Ventana de consulta" y luego se cambiaba a otra
         * ventana fuera de Zafiro ocurróa que la ventana de consulta quedaba atrós de la ventana principal
         * de Zafiro. Se analizó el problema y se concluyo que el problema se presentaba porque al configurar
         * la ventana de consulta se le enviaba el padre y al parecer mientras no se termine de construir
         * el objeto ZafAsiDia_v01 el mótodo "this" devuelve null. Por eso la estrategia fue poner un Thread que
         * se estó ejecutando hasta que detecte que el objeto ZafAsiDia_v01 ha sido creado. Es decir, hasta que
         * sea diferente a null.
        */
//        objThrGUI=new ZafThreadGUI();
//        objThrGUI.setIndFunEje(-1);
//        objThrGUI.start();
        configurarFrm();
        strTblActUltDoc="";
        arlDatDiaRet=new ArrayList();
        intChgBusCri=-1;
        objSegMovInv=new ZafSegMovInv(objParSis, this);
    }
    
    
//    public ZafAsiDia_v01(ZafParSis obj, int codTipDocRef){
//        intCodTipDocGlb=codTipDocRef;
//        initComponents();
//        //Inicializar objetos.
//        objParSis=obj;
//        if (!configurarFrm())
//            this.setEnabled(false);
//    }    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panNor = new javax.swing.JPanel();
        lblGlo = new javax.swing.JLabel();
        txtGlo = new javax.swing.JTextField();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panSur = new javax.swing.JPanel();
        lblDif = new javax.swing.JLabel();
        txtDif = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        panNor.setLayout(new java.awt.BorderLayout());

        lblGlo.setText("Glosa:");
        lblGlo.setPreferredSize(new java.awt.Dimension(100, 15));
        panNor.add(lblGlo, java.awt.BorderLayout.WEST);
        panNor.add(txtGlo, java.awt.BorderLayout.CENTER);

        add(panNor, java.awt.BorderLayout.NORTH);

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

        add(spnDat, java.awt.BorderLayout.CENTER);

        panSur.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblDif.setText("Diferencia:");
        panSur.add(lblDif);

        txtDif.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDif.setText("0.00");
        txtDif.setEnabled(false);
        txtDif.setPreferredSize(new java.awt.Dimension(120, 21));
        panSur.add(txtDif);

        add(panSur, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblGlo;
    private javax.swing.JPanel panNor;
    private javax.swing.JPanel panSur;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtDif;
    private javax.swing.JTextField txtGlo;
    // End of variables declaration//GEN-END:variables
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Inicializar variables.
            bytDiaGen=0;
            //Establecer el color de los campos.
            txtDif.setBackground(objParSis.getColorCamposSistema());
            //Configurar las ZafVenCon.
            configurarVenConCta();
            //Configurar los JTables.
            configurarTblDat();
            //Los siguientes arreglos son usados para guardar la información de meses y aóos q se han cerrado
            arlDatAniMes=new ArrayList();
            arlDatAniMesCta=new ArrayList();
            //Contiene campos con sus respectivos valores del detalle de diario afectado
            arlDatMayOnLin=new ArrayList();
            arlDatDetDiaMod=new ArrayList();
            
            dtpFecSis=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {

            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_BUT_CTA,"");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre de la cuenta");
            vecCab.add(INT_TBL_DAT_DEB,"Debe");
            vecCab.add(INT_TBL_DAT_HAB,"Haber");
            vecCab.add(INT_TBL_DAT_REF,"Referencia");
            vecCab.add(INT_TBL_DAT_EST_CON,"Estado de Conciliación");


            vecCta=new Vector();    //Almacena las cuentas.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_DEB, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_HAB, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_CTA);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menó de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_REF).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setPreferredWidth(80);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_CON).setResizable(false);





            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_NUM_CTA);
            vecAux.add("" + INT_TBL_DAT_BUT_CTA);
            vecAux.add("" + INT_TBL_DAT_DEB);
            vecAux.add("" + INT_TBL_DAT_HAB);
            vecAux.add("" + INT_TBL_DAT_REF);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_REF).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_COD_CTA;
            intColTbl[1]=INT_TBL_DAT_NUM_CTA;
            intColTbl[2]=INT_TBL_DAT_NOM_CTA;
            objTblCelEdiTxtVcoCta=new ZafTblCelEdiTxtVco(tblDat, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoCta);
            objTblCelEdiTxtVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
                    if(strEstCncDia.equals("S")){
                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>Desconcilie la cuenta en el documento a modificar y vuelva a intentarlo.</HTML>");
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
                    }
                    else if(strEstCncDia.equals("B")){
                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
                        objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
                    }
                    else{
                        //Permitir de manera predeterminada la operación.
                        blnCanOpe=false;
                        //Generar evento "beforeEditarCelda()".
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
                        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
                        if (blnCanOpe)
                        {
                            objTblCelEdiTxtVcoCta.setCancelarEdicion(true);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strEstCncDia.equals("B")){

                    }
                    else{
                        //Generar evento "afterEditarCelda()".
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);
                    }

                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intBanNumCta++;

                    //Generar evento "beforeConsultarCuentas()".
                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON_CTA);
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.setCriterio1(7);
                    if(objParSis.getCodigoUsuario()!=1)
                    {
                        strMe="";
                        strMe+=" AND a2.co_tipdoc=" + intCodTipDocGlb + "";
                        strMe+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                        vcoCta.setCondicionesSQL(strMe);
                    }
                    setPuntosCta();

                }

                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoCta.isConsultaAceptada())
                    {
                        bytDiaGen=2;
                        //Generar evento "afterConsultarCuentas()".
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_CON_CTA);
                    }
                }
            });
            
            objTblCelEdiButVcoCta=new ZafTblCelEdiButVco(tblDat, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setCellEditor(objTblCelEdiButVcoCta);
            objTblCelEdiButVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
                    if(strEstCncDia.equals("S")){
                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>No se puede presentar la ventana de consulta de una cuenta ya conciliada.</HTML>");
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
                        objTblCelEdiButVcoCta.setCancelarEdicion(true);
                    }
                    else if(strEstCncDia.equals("B")){
                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
                        objTblCelEdiButVcoCta.setCancelarEdicion(true);
                        vcoCta.setVisible(false);
                    }
                    else{
                        ///////////funcion para cxp///////////
                        intBanBotCta++;
                        //Permitir de manera predeterminada la operación.
                        blnCanOpe=false;
                        //Generar evento "beforeEditarCelda()".
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
                        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
                        if (blnCanOpe)
                        {
                            objTblCelEdiButVcoCta.setCancelarEdicion(true);
                        }
                    }


                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Generar evento "afterEditarCelda()".
                    if(strEstCncDia.equals("B")){
                        objTblCelEdiButVcoCta.setCancelarEdicion(true);
                    }
                    else{
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_EDI_CEL);
                    }

                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strEstCncDia.equals("B")){
                        objTblCelEdiButVcoCta.setCancelarEdicion(true);
                    }
                    else{
                        //Generar evento "beforeConsultarCuentas()".
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON_CTA);
                        if(intChgBusCri==-1){
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(7);
                        }
                        else{
                            vcoCta.setCampoBusqueda(2);
                            vcoCta.setCriterio1(11);
                        }



                        if(objParSis.getCodigoUsuario()!=1)
                        {
                            strMe="";
                            strMe+=" AND a2.co_tipdoc=" + intCodTipDocGlb + "";
                            strMe+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                            vcoCta.setCondicionesSQL(strMe);
                        }
                        setPuntosCta();
                    }

                }

                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strEstCncDia.equals("B")){
                    }
                    else{
                        if (objTblCelEdiButVcoCta.isConsultaAceptada())
                        {
                            bytDiaGen=2;
                            //Generar evento "afterConsultarCuentas()".
                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_CON_CTA);
                        }
                    }

                }
            });
            intColVen=null;
            intColTbl=null;
            
            objTblCelEdiTxtRef=new ZafTblCelEdiTxt();
            tcmAux.getColumn(INT_TBL_DAT_REF).setCellEditor(objTblCelEdiTxtRef);
            objTblCelEdiTxtRef.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
                    if(strEstCncDia.equals("S")){
                        mostrarMsgInf("<HTML>No se puede colocar referencia a está cuenta<BR>La cuenta ya fue conciliada, desconcilie la cuenta y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtRef.setCancelarEdicion(true);
                    }
                }
            });

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_DEB).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_HAB).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="";//estado de conciliacion bancaria de la cuenta
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strEstCncDia=tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON)==null?"":tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CON).toString();
                    if(strEstCncDia.equals("S")){
                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>La cuenta ya fue conciliada, desconcilie la cuenta y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }
                    if(strEstCncDia.equals("B")){
                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }
                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
            //Establecer parómetros predeterminados para la clase.
            setEditable(false);
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
     * Esta función inicializa el asiento de diario.
     * @return true: Si se pudo inicializar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean inicializar()
    {
        boolean blnRes=true;
        txtGlo.setText("");
        objTblMod.removeAllRows();
        objTblMod.inicializarEstadoFilas();
        dblMonIniDeb=0;
        dblMonIniHab=0;
        dblMonDeb=0;
        dblMonHab=0;
        //bdeMonHab=new BigDecimal("0");
        bdeMonDeb=BigDecimal.ZERO;
        bdeMonHab=BigDecimal.ZERO;
        bytDiaGen=0;
        return blnRes;
    }

    /**
     * Esta función determina si el diario es editable o no.
     * @return true: Si el diario es editable.
     * <BR>false: En el caso contrario.
     */
    public boolean isEditable()
    {
        return blnAsiDiaEdi;
    }
    
    /**
     * Esta función establece si el diario debe ser editable o no.
     * @param editable Puede tomar los siguientes valores:
     * <BR>true: Diario editable.
     * <BR>false: Diario no editable.
     */
    public void setEditable(boolean editable)
    {
        blnAsiDiaEdi=editable;
        txtGlo.setEditable(editable);
        txtGlo.setBackground(java.awt.Color.white);
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    
    
    
    public void setEditable(boolean editable, int codigo)
    {
        blnAsiDiaEdi=editable;
        intCodTipDocGlb=codigo;
        txtGlo.setEditable(editable);
        txtGlo.setBackground(java.awt.Color.white);
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }    
    
    ////////////////////////////////funciones de DCARDENAS///////////////////////////
    /**
     * Esta función obtiene el código del diario. O sea, el código del Sistema.
     * @return El código del diario.
     */
    public int getBanNumCta()
    {
        return intBanNumCta;
    }
    
    /**
     * Esta función obtiene el código del diario. O sea, el código del Sistema.
     * @return El código del diario.
     */
    public int getBanBotCta()
    {
        return intBanBotCta;
    }
    
    /**
     * Esta función obtiene el código del diario. O sea, el código del Sistema.
     * @return El código del diario.
     */
    public void setBanNumCta(int num)
    {
        intBanNumCta=num;
    }
    
    /**
     * Esta función obtiene el código del diario. O sea, el código del Sistema.
     * @return El código del diario.
     */
    public void setBanBotCta(int num)
    {
        intBanBotCta=num;
    }
    //////////////////////////fin de funciones DCARDENAS/////////////////////////////////////////
    
    /**
     * Esta función obtiene el código del diario. O sea, el código del Sistema.
     * @return El código del diario.
     */
    public int getCodigoDiario()
    {
        return intCodDia;
    }
    
    /**
     * Esta función establece el código del diario. O sea, el código del Sistema.
     * @param codigo El código del diario.
     */
    public void setCodigoDiario(int codigo)
    {
        intCodDia=codigo;
    }    
    
    /**
     * Esta función obtiene el nómero del diario. Por lo general es el nómero preimpreso.
     * @return El nómero del diario.
     */
    public String getNumeroDiario()
    {
        return strNumDia;
    }
    
    /**
     * Esta función establece el nómero del diario.
     * @param numero El nómero del diario.
     */
    public void setNumeroDiario(String numero)
    {
        strNumDia=numero;
    }

    /**
     * Esta función obtiene la fecha del diario.
     * @return La fecha del diario.
     */
    public java.util.Date getFechaDiario()
    {
        return datFecDia;
    }
    
    /**
     * Esta función establece la fecha del diario.
     * @param fecha La fecha del diario.
     */
    public void setFechaDiario(java.util.Date fecha)
    {
        datFecDia=fecha;
    }

    /**
     * Esta función obtiene la glosa del diario.
     * @return La glosa del diario.
     */
    public String getGlosa()
    {
        return txtGlo.getText();
    }
    
    /**
     * Esta función establece la glosa del diario.
     * @param glosa La glosa del diario.
     */
    public void setGlosa(String glosa)
    {
        txtGlo.setText(glosa);
    }
    
    /**
     * Esta función obtiene el estado del diario.
     * <BR>Puede devolver los siguientes valores.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return El estado del diario.
     */
    public String getEstadoDiario()
    {
        return strEstDia;
    }
    
    /**
     * Esta función establece el estado del diario.
     * <BR>Puede tomar los siguientes valores.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param estado El estado del diario.
     */
    public void setEstadoDiario(String estado)
    {
        strEstDia=estado;
    }
    
    /**
     * Esta función obtiene la fecha del diario.
     * @return La fecha del diario.
     */
    public java.util.Date getFechaIngresoDiario()
    {
        return datFecIng;
    }
    
    /**
     * Esta función establece la fecha de ingreso del diario.
     * @param fecha La fecha de ingreso del diario.
     */
    public void setFechaIngresoDiario(java.util.Date fecha)
    {
        datFecIng=fecha;
    }
    
    /**
     * Esta función obtiene la óltima fecha de modificación del diario.
     * @return La óltima fecha de modificación del diario.
     */
    public java.util.Date getFechaModificacionDiario()
    {
        return datFecMod;
    }
    
    /**
     * Esta función establece la óltima fecha de modificación del diario.
     * @param fecha La óltima fecha de modificación del diario.
     */
    public void setFechaModificacionDiario(java.util.Date fecha)
    {
        datFecMod=fecha;
    }
    
    /**
     * Esta función obtiene el código del usuario que ingresó el diario.
     * @return El código del usuario que ingresó el diario.
     */
    public int getUsuarioIngresoDiario()
    {
        return intCodUsrIng;
    }
    
    /**
     * Esta función establece el código del usuario que ingresó el diario.
     * @param glosa La glosa del diario.
     */
    public void setUsuarioIngresoDiario(int codigoUsuario)
    {
        intCodUsrIng=codigoUsuario;
    }
    
    /**
     * Esta función obtiene el código del óltimo usuario que modificó el diario.
     * @return El código del óltimo usuario que modificó el diario.
     */
    public int getUsuarioModificacionDiario()
    {
        return intCodUsrMod;
    }
    
    /**
     * Esta función establece el código del óltimo usuario que modificó el diario.
     * @param glosa Eel código del óltimo usuario que modificó el diario.
     */
    public void setUsuarioModificacionDiario(int codigoUsuario)
    {
        intCodUsrMod=codigoUsuario;
    }

    /**
     * Esta función determina si el asiento de diario estó cuadrado.
     * @return true: Si el diario estó cuadrado.
     * <BR>false: En el caso contrario.
     */
    public boolean isDiarioCuadrado()
    {
        try
        {
            if (objUti.isNumero(txtDif.getText()))
                //if (Double.parseDouble(txtDif.getText())==0)
                if ( new java.math.BigDecimal(txtDif.getText()).compareTo(new java.math.BigDecimal("0")) ==0  )
                    return true;
                else
                    return false;
            else
                return false;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    
    /**
     * Esta función obtiene los datos del detalle del diario.
     * El vector que retorna esta función tiene el siguiente formato:
     * <UL>
     * <LI>0) Lónea: Se debe asignar una cadena vacóa o <I>null</I>.
     * <LI>1) Código de la cuenta (Sistema).
     * <LI>2) Nómero de la cuenta (Preimpreso).
     * <LI>3) Botón de consulta: Se debe asignar una cadena vacóa o <I>null</I>.
     * <LI>4) Nombre de la cuenta.
     * <LI>5) Debe.
     * <LI>6) Haber.
     * <LI>7) Referencia: Se debe asignar una cadena vacóa o <I>null</I>.
     * </UL>
     * Ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Lónea</I></TD><TD><I>Código de la cuenta</I></TD><TD><I>Nómero de la cuenta</I></TD><TD><I>Botón de consulta</I></TD><TD><I>Nombre de la cuenta</I></TD><TD><I>Debe</I></TD><TD><I>Haber</I></TD><TD><I>Referencia</I></TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>28</TD><TD>1.01.03.01.01</TD><TD>&nbsp;</TD><TD>Clientes Guayaquil</TD><TD>1575.02016</TD><TD>0.00</TD><TD>&nbsp;</TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>1364</TD><TD>4.01.01.01</TD><TD>&nbsp;</TD><TD>Ventas Guayaquil</TD><TD>468.756</TD><TD>0.00</TD><TD>&nbsp;</TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>1364</TD><TD>4.01.01.01</TD><TD>&nbsp;</TD><TD>Ventas Guayaquil</TD><TD>0.00</TD><TD>1875.024</TD><TD>&nbsp;</TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>969</TD><TD>2.01.06.02.04</TD><TD>&nbsp;</TD><TD>IVA cobrado en ventas</TD><TD>0.00</TD><TD>168.75216</TD><TD>&nbsp;</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param datos El vector que contiene los datos del diario.
     */
    public Vector getDetalleDiario()
    {
        return vecDat;
    }
    
    /**
     * Esta función establece los datos del detalle del diario.
     * El vector debe tener el siguiente formato:
     * <UL>
     * <LI>0) Lónea: Se debe asignar una cadena vacóa o <I>null</I>.
     * <LI>1) Código de la cuenta (Sistema).
     * <LI>2) Nómero de la cuenta (Preimpreso).
     * <LI>3) Botón de consulta: Se debe asignar una cadena vacóa o <I>null</I>.
     * <LI>4) Nombre de la cuenta.
     * <LI>5) Debe.
     * <LI>6) Haber.
     * <LI>7) Referencia: Se debe asignar una cadena vacóa o <I>null</I>.
     * </UL>
     * Ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Lónea</I></TD><TD><I>Código de la cuenta</I></TD><TD><I>Nómero de la cuenta</I></TD><TD><I>Botón de consulta</I></TD><TD><I>Nombre de la cuenta</I></TD><TD><I>Debe</I></TD><TD><I>Haber</I></TD><TD><I>Referencia</I></TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>28</TD><TD>1.01.03.01.01</TD><TD>&nbsp;</TD><TD>Clientes Guayaquil</TD><TD>1575.02016</TD><TD>0.00</TD><TD>&nbsp;</TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>1364</TD><TD>4.01.01.01</TD><TD>&nbsp;</TD><TD>Ventas Guayaquil</TD><TD>468.756</TD><TD>0.00</TD><TD>&nbsp;</TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>1364</TD><TD>4.01.01.01</TD><TD>&nbsp;</TD><TD>Ventas Guayaquil</TD><TD>0.00</TD><TD>1875.024</TD><TD>&nbsp;</TD></TR>
     *     <TR><TD>&nbsp;</TD><TD>969</TD><TD>2.01.06.02.04</TD><TD>&nbsp;</TD><TD>IVA cobrado en ventas</TD><TD>0.00</TD><TD>168.75216</TD><TD>&nbsp;</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param datos El vector que contiene los datos del diario.
     */
    public void setDetalleDiario(Vector datos)
    {
        objTblMod.setData(datos);
        vecDat=datos;
        calcularDif();
    }
    
    /**
     * Esta función obtiene el monto del debe del diario. Es decir, devuelve la
     * suma de todos los valores de la columna del debe. 
     * @return El monto del debe del diario en double.
     */
    public double getMontoDebe()
    {
        return dblMonDeb;
    }
    
    /**
     * Esta función obtiene el monto del debe del diario. Es decir, devuelve la
     * suma de todos los valores de la columna del debe. 
     * @return El monto del debe del diario en BigDecimal.
     */
    public BigDecimal getMontoDebeBde()
    {
        return bdeMonDeb;
    }
    
    
    /**
     * Esta función obtiene el monto del haber del diario. Es decir, devuelve la
     * suma de todos los valores de la columna del haber. 
     * @return El monto del haber del diario.
     */
    public double getMontoHaber()
    {
        return dblMonHab;
    }
    
    /**
     * Esta función obtiene el monto del haber del diario. Es decir, devuelve la
     * suma de todos los valores de la columna del haber. 
     * @return El monto del haber del diario.
     */
    public BigDecimal getMontoHaberBde()
    {
        return bdeMonHab;
    }
    
    


    /**
     * Esta función obtiene el monto del haber del diario. Es decir, devuelve la
     * suma de todos los valores de la columna del haber.
     * @return El monto del haber del diario.
     */
//    public BigDecimal getMontoHaberBde()
//    {
//        return bdeMonHab;
//    }


    
    /**
     * Esta función obtiene el monto inicial del debe del diario. Es decir, el valor
     * que tenóa el asiento de diario antes de ser modificado por el usuario. 
     * @return El monto inicial del debe del diario.
     */
    public double getMontoInicialDebe()
    {
        return dblMonIniDeb;
    }
    
    /**
     * Esta función obtiene el monto inicial del haber del diario. Es decir, el valor
     * que tenóa el asiento de diario antes de ser modificado por el usuario. 
     * @return El monto inicial del haber del diario.
     */
    public double getMontoInicialHaber()
    {
        return dblMonIniHab;
    }
    
    /**
     * Esta función obtiene la diferencia del diario. Es decir, suma los valores
     * de la columna del debe y les resta los valores de la columna del haber. 
     * @return La diferencia entre el debe y el haber.
     */
    public double getDiferencia()
    {
        return (dblMonDeb-dblMonHab);
    }
    
    /**
     * Esta función obtiene la diferencia del diario. Es decir, suma los valores
     * de la columna del debe y les resta los valores de la columna del haber. 
     * @return La diferencia entre el debe y el haber.
     */
    public BigDecimal getDiferenciaBde()
    {
        return (bdeMonDeb.subtract(bdeMonHab));
    }
    
    
    /**
     * Esta función determina si el documento y el diario tienen el mismo monto.
     * Por ejemplo, cuando se hace una factura se genera el asiento de diario
     * automóticamente y el usuario puede hacer correciones en dicho asiento.
     * Puede darse el caso en el que el documento sea por un valor y el asiento
     * de diario estó por otro valor. Esto no deberóa ocurrir. Para evitar óste
     * problema se recomienda utilizar la función "isDocumentoCuadrado".
     * @param monto El monto del documento a evaluar.
     * @return true: Si el monto del documento es igual al monto del diario.
     * <BR>false: En el caso contrario.
     */
    public boolean isDocumentoCuadrado(double monto)
    {
        BigDecimal bdeMonto=objUti.redondearBigDecimal(""+monto, 2);

        if(bdeMonto.compareTo( objUti.redondearBigDecimal(bdeMonDeb,objParSis.getDecimalesBaseDatos()) ) ==0   &&  bdeMonto.compareTo(objUti.redondearBigDecimal(bdeMonHab,objParSis.getDecimalesBaseDatos()))==0        )
            return true;
        else
            return false;        
    }


  /**
     * Esta función determina si el documento y el diario tienen el mismo monto.
     * Por ejemplo, cuando se hace una factura se genera el asiento de diario
     * automóticamente y el usuario puede hacer correciones en dicho asiento.
     * Puede darse el caso en el que el documento sea por un valor y el asiento
     * de diario estó por otro valor. Esto no deberóa ocurrir. Para evitar óste
     * problema se recomienda utilizar la función "isDocumentoCuadrado".
     * @param monto El monto del documento a evaluar.
     * @return true: Si el monto del documento es igual al monto del diario.
     * <BR>false: En el caso contrario.
     * JoséMario Marín 7/Oct/2014
     */
    
    public boolean isDocumentoCuadradoBde(double monto)
    {
        System.out.println("ZafAsiDia.isDocumentoCuadradoBde... ");
        BigDecimal bdeMonto=objUti.redondearBigDecimal(""+monto, 2);
        if(bdeMonto.compareTo( objUti.redondearBigDecimal(bdeMonDeb,objParSis.getDecimalesBaseDatos()) ) ==0   &&  bdeMonto.compareTo(objUti.redondearBigDecimal(bdeMonHab,objParSis.getDecimalesBaseDatos()))==0        )
            return true;
        else
            return false; 
    }
    
    
    /**
     * Esta función determina si el documento y el diario tienen el mismo monto.
     * Por ejemplo, cuando se hace una factura se genera el asiento de diario
     * automóticamente y el usuario puede hacer correciones en dicho asiento.
     * Puede darse el caso en el que el documento sea por un valor y el asiento
     * de diario estó por otro valor. Esto no deberóa ocurrir. Para evitar óste
     * problema se recomienda utilizar la función "isDocumentoCuadrado".
     * @param monto El monto del documento a evaluar.
     * @return true: Si el monto del documento es igual al monto del diario.
     * <BR>false: En el caso contrario.
     */
    public boolean isDocumentoCuadrado(String monto)
    {
        BigDecimal bdeMon;
        //Convertir el monto a double.
        if (objUti.isNumero(monto))
            bdeMon=new BigDecimal(monto);
        else
            bdeMon=BigDecimal.ZERO;
        if (bdeMon.compareTo(objUti.redondearBigDecimal(bdeMonDeb,objParSis.getDecimalesBaseDatos()))==0   && bdeMon.compareTo(objUti.redondearBigDecimal(bdeMonHab,objParSis.getDecimalesBaseDatos()))==0   )
            return true;
        else
            return false;
    }
   
    
    /**
     * Esta función determina si el diario ha cambiado. Se considera que
     * el diario ha cambiado por alguno de los siguientes casos:
     * <UL>
     * <LI>Si una de las celdas ha sido modificada,
     * <LI>Si se ha insertado una fila.
     * <LI>Si se ha eliminado una fila.
     * </UL>
     * @return true: Si el diario ha sido modificado.
     * <BR>false: En el caso contrario.
     */
    public boolean isDiarioModificado()
    {
        return objTblMod.isDataModelChanged();
    }
    
    /**
     * Esta función establece si el diario ha cambiado. Se considera que
     * el diario ha cambiado por alguno de los siguientes casos:
     * <UL>
     * <LI>Si una de las celdas ha sido modificada,
     * <LI>Si se ha insertado una fila.
     * <LI>Si se ha eliminado una fila.
     * </UL>
     * @param modificado El estado que determina si el asiento de diario ha sido modificado.
     */
    public void setDiarioModificado(boolean modificado)
    {
        objTblMod.setDataModelChanged(modificado);
    }
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            if (isCamVal())
                if (insertarCab(conexion, empresa, local, tipoDocumento, null, null, null))
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                        if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento))
                        {
                            //Generar evento "afterInsertar()".
                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                            return true;
                        }
        }
        return false;
    }
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario.
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            if (isCamVal())
                if (insertarCab(conexion, empresa, local, tipoDocumento, String.valueOf(codigoDiario), null, null))
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                        if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento))
                        {
                            //Generar evento "afterInsertar()".
                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                            return true;
                        }
        }
        return false;
    }
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario.
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario, boolean a)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            if (isCamVal())
                if (insertarCab(conexion, empresa, local, tipoDocumento, String.valueOf(codigoDiario), null, null))
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                    {
                        //Generar evento "afterInsertar()".
                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                        return true;
                    }
        }
        return false;
    }
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param numeroDiario El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param fechaDiario La fecha del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    
    //usado para ZafCon54--emision de cheque masivo
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String numeroDiario, java.util.Date fechaDiario)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;
            intRefMesNew=0;
            intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );
            

            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            if (isCamVal()){
                if (insertarCab(conexion, empresa, local, tipoDocumento, null, numeroDiario, fechaDiario)){
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia)){
                        if( (getCamTblUltDoc().equals(""))   ||  (getCamTblUltDoc().equals("L"))  ){
                            
                            if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento)){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                    {
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                            }

                        }
                        else if(getCamTblUltDoc().equals("G")) {
                            
                            if (actualizarCabGrpTipDoc(conexion, empresa, local, strCodGrpTipDoc)){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                    {
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                            }

                        }

                    }
                }
            }
        }
        return false;
    }
    
    
    
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String numeroDiario, java.util.Date fechaDiario, int codigoMenuReferencial)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;
            intRefMesNew=0;
            intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );
            

            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            if (isCamVal()){
                if(codigoMenuReferencial!=0){
                    if (insertarCab(conexion, empresa, local, tipoDocumento, null, numeroDiario, fechaDiario)){
                        if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia)){
                            if( (getCamTblUltDoc().equals(""))   ||  (getCamTblUltDoc().equals("L"))  ){

                                if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento)){
                                    if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                        if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                        {
                                            //Generar evento "afterInsertar()".
                                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                            return true;
                                        }
                                    }
                                }

                            }
                            else if(getCamTblUltDoc().equals("G")) {

                                if (actualizarCabGrpTipDoc(conexion, empresa, local, strCodGrpTipDoc)){
                                    if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                        if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                        {
                                            //Generar evento "afterInsertar()".
                                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                            return true;
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    
    
    
    
    
    
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param numeroDiario El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param fechaDiario La fecha del diario.
     * @param estadoDiario El estado del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String numeroDiario, java.util.Date fechaDiario, String estadoDiario){
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe){
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;
            intRefMesNew=0;
            intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );

            
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            if (isCamVal())
                if (insertarCab(conexion, empresa, local, tipoDocumento, null, numeroDiario, fechaDiario, estadoDiario))
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                        if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento))
                            if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro))
                                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                {
                                    //Generar evento "afterInsertar()".
                                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                    return true;
                                }
        }
        return false;
    }
    
    
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param numeroDiario El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param fechaDiario La fecha del diario.
     * @param estadoDiario El estado del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDocumento, String numeroDiario, java.util.Date fechaDiario, String estadoDiario){
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe){
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;
            intRefMesNew=0;
            intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            if (isCamVal())
                if (insertarCab(conexion, empresa, local, tipoDocumento, String.valueOf(codigoDocumento), numeroDiario, fechaDiario, estadoDiario))
                    System.out.println("insertarCab");
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                        if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento))
                            if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro))
                                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                {
                                    //Generar evento "afterInsertar()".
                                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                    return true;
                                }
        }
        return false;
    }
    
    
    
    
    
    /**
     * Esta función consulta y presenta el asiento de diario.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea consultar.
     * @return true: Si se pudo consultar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean consultarDiario(int empresa, int local, int tipoDocumento, int codigoDiario)
    {
        boolean blnRes=true;
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI(empresa, local, tipoDocumento, codigoDiario);
            objThrGUI.setIndFunEje(0);
            objThrGUI.start();
        }
        else
        {
            objThrGUI=null;
        }
        return blnRes;
    }
    
    /**
     * Esta función consulta y presenta el asiento de diario completo. Es decir,
     * consulta todos los campos de la cebecera y el detalle. Debido a que visualmente
     * sólo se presenta la glosa y el detalle del diario se deberó utilizar las funoiones
     * que incluye esta clase para acceder a los datos del diario que se estó consultando.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea consultar.
     * @return true: Si se pudo consultar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean consultarDiarioCompleto(int empresa, int local, int tipoDocumento, int codigoDiario)
    {
        boolean blnRes=true;
        if (objThrGUI==null)
        {
            objThrGUI=new ZafThreadGUI(empresa, local, tipoDocumento, codigoDiario);
            objThrGUI.setIndFunEje(1);
            objThrGUI.start();
        }
        return blnRes;
    }
    
    /**
     * Esta función actualiza el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea actualizar.
     * @param numeroDiario El nómero de diario.
     * @param fechaDiario La fecha del diario.
     * @param estadoDiario El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean actualizarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario, String numeroDiario, java.util.Date fechaDiario, String estadoDiario)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeModificar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_MOD);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            int intFunAni=0;
            int intFunMes=0;
            intTipPro=1;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 ); 
            //PARA VALIDAR LA MODIFICACION DE UN DIARIO, A TRAVES DE PARAMETROS QUE SE RECIBEN Y DE LA FUNCION getFecDia()
            intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);
            intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
            datAuxMayAnt=getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario);
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if(      ( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  )     ||    ( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  )   ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            
            if(    ( ! (retAniMesCie(intFunAni)))   ||   ( ! (retAniMesCie(intRefAniNew)))    )
                return false;
            
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(   (intFunAni==intArlAni)){
                        if(    (intFunMes==intArlMes)){
                            mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                    
                    if( (intRefAniNew==intArlAni)  ){
                        if( (intRefMesNew==intArlMes)  ){
                            mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                    
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }

            
            if (isCamVal()){
                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario)){
                    if(obtieneDatosDetalleDiarioModificado(empresa)){
                        if( ! existenCuentasConciliadasModificadas()){
                            if( ! existenCuentaAnteriorCerrada(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                if (actualizarCab(conexion, empresa, local, tipoDocumento, codigoDiario, numeroDiario, fechaDiario, estadoDiario)){
                                    if (eliminarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                        if (insertarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                            if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, datAuxMayAnt, intTipPro)){
                                                //Generar evento "afterModificar()".
                                                fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_MOD);
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        else{
                            mostrarMsgInf("<HTML>Al modificar el documento se está cambiando una cuenta conciliada o su valor<BR>No puede cambiar una cuenta ni su valor si la cuenta está conciliada.<BR>Desconcilie la cuenta en el documento que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    /**
     * Esta función actualiza el asiento de diario del programa CIECAJ (Cierre de Caja) en la base de datos.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea actualizar.
     * @param numeroDiario El nómero de diario.
     * @param fechaDiario La fecha del diario.
     * @param estadoDiario El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean actualizarDiarioCieCaj(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario, String numeroDiario, String strFecDia, String estadoDiario, double dblValCta, String cuentaDebe, String cuentaHaber, int codigoMenuReferencial)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeModificar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_MOD);
        
        ////DARIO//////Formatear la fecha tipo String a tipo Date///////////
        java.util.Date fechaDiario;
        fechaDiario = (objUti.parseDate(strFecDia,"dd/MM/yyyy"));
        //////////////////////////////////////////////////////////////
        
        
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            int intFunAni=0;
            int intFunMes=0;
            intTipPro=1;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );        
            //PARA VALIDAR LA MODIFICACION DE UN DIARIO, A TRAVES DE PARAMETROS QUE SE RECIBEN Y DE LA FUNCION getFecDia()
            intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);
            intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
            
            datAuxMayAnt=getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario);
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if(      ( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  )     ||    ( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  )   ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            if(    ( ! (retAniMesCie(intFunAni)))   ||   ( ! (retAniMesCie(intRefAniNew)))    )
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(   (intFunAni==intArlAni) ||  (intRefAniNew==intArlAni)  ){
                        if(    (intFunMes==intArlMes) ||   (intRefMesNew==intArlMes)  ){
                            mostrarMsgInf("<HTML>El mes que desea modificar estó cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            
            intTipPro=3;
            
            if (isCamVal())
                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario))
                    if (actualizarCabCieCaj(conexion, empresa, local, tipoDocumento, codigoDiario, numeroDiario, fechaDiario, estadoDiario, codigoMenuReferencial))
                        if(mayorizaCtaCieCaj(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, datAuxMayAnt, intTipPro, dblValCta, cuentaDebe, cuentaHaber))
                        {
                            //Generar evento "afterModificar()".
                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_MOD);
                            return true;
                        }
        }
        return false;
    }
    


    /**
     * Esta función elimina el asiento de diario de la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea eliminar.
     * @return true: Si se pudo eliminar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean eliminarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeEliminar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_ELI);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            intTipPro=2;
            String strArlTipCie="";
            int intFunAni=0;
            int intFunMes=0;
            intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);
            intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intFunAni + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }        
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intFunAni)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intFunAni==intArlAni){
                        if(intFunMes==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de ELIMIBAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de ELIMINAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            
            
            if(intFunAni==anioActualBaseDatos(conexion)){
                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario))
                        if( ! existenCuentasConciliadasModificadas())
                            if( ! existenCuentaAnteriorCerrada(conexion, empresa, local, tipoDocumento, codigoDiario))
                                if (eliminarCab(conexion, empresa, local, tipoDocumento, codigoDiario))
                                    if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario ), getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario ), intTipPro))
                                    {
                                        //Generar evento "afterEliminar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_ELI);
                                        return true;
                                    }
            }
            else{
                mostrarMsgInf("<HTML>El documento que intenta eliminar no está ingresado en el presente año.<BR> Sólo se puede eliminar documentos del presente año.</HTML>");
                return false;
            }
            

        }
        return false;
    }
    
    /**
     * Esta función anula el asiento de diario de la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea anular.
     * @return true: Si se pudo anular el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean anularDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeAnular()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_ANU);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            int intFunAni=0;  
            int intFunMes=0;
            intTipPro=2;
            intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);//contiene el año del documento
            intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
            strEstCnfDoc=getEstadoConfirmacionBancariaDocumento(conexion, empresa, local, tipoDocumento, codigoDiario);
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intFunAni + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }

            if(strEstCnfDoc.equals("S")){
                mostrarMsgInf("<HTML>El documento no puede ser anulado porque tiene conciliación bancaria asociada.<BR>Desconcilie el documento y vuelva a intentarlo.</HTML>");
                return false;
            }




            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intFunAni)))
                return false;

            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){

                    if(intFunAni==intArlAni){
                        if(intFunMes==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de ANULAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de ANULAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //solo se permite anular documentos del presente año, si es año anterior o posterior(si esta creado en el sistema) no lo permite.
            
//            mostrarMsgInf("intFunAni: " + intFunAni);
//            mostrarMsgInf("anioActualBaseDatos(conexion): " + anioActualBaseDatos(conexion));
            
            
            if(  (objParSis.getCodigoUsuario()==1)  ||  (objParSis.getCodigoUsuario()==101)  ){
                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario))
                    if (anularCab(conexion, empresa, local, tipoDocumento, codigoDiario))
                        if(mayorizaCta(conexion, empresa,local, tipoDocumento, intCodDia, getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario), getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario), intTipPro))
                        {
                            //Generar evento "afterAnular()".
                            fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_ANU);
                            return true;
                        }               
            }
            else{
                if(intFunAni==anioActualBaseDatos(conexion)){
                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario))
                        if (anularCab(conexion, empresa, local, tipoDocumento, codigoDiario))
                            if(mayorizaCta(conexion, empresa,local, tipoDocumento, intCodDia, getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario), getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario), intTipPro))
                            {
                                //Generar evento "afterAnular()".
                                fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_ANU);
                                return true;
                            }
                }
                else{
                    mostrarMsgInf("<HTML>El documento que intenta anular no está ingresado en el presente año.<BR> Sólo se puede anular documentos del presente año.</HTML>");
                    return false;
                }
            }
            

        }
        return false;
    }
    
    
    /**
     * Esta función inserta la cabecera del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param strCodDia El código del diario. Es el código que maneja el sistema.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el código del diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el código del diario recibido.
     * </UL>
     * @param strNumDia El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param datFecDia La fecha del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, String strCodDia, String strNumDia, java.util.Date datFecDia)
    {
        int intCodUsr, intNumDia=-1;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                stm=con.createStatement();
                if (strCodDia==null)
                {
                    //Obtener el código del óltimo registro.
                    strSQL="";
                    strSQL+="SELECT CASE WHEN MAX(a1.co_dia) IS NULL THEN 0 ELSE MAX(a1.co_dia) END AS co_dia";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
//                    intCodDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                    if (intCodDia==-1)
//                        return false;
//                    intCodDia++;

                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodDia=rst.getInt("co_dia");
                    intCodDia++;

                }
                else
                {
                    intCodDia=Integer.parseInt(strCodDia);
                }
                if (strNumDia==null)
                {
                    //Obtener el nómero de diario.
                    strSQL="";
                    strSQL+="SELECT CASE WHEN a1.ne_ultDoc IS NULL THEN 0 ELSE a1.ne_ultDoc END AS ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
//                    intNumDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//                    if (intNumDia==-1)
//                        return false;
//                    intNumDia++;

                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNumDia=rst.getInt("ne_ultDoc");
                    intNumDia++;

                    

                    strNumDia="" + intNumDia;
                }
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                
                if( ! (fechaPermitida(con, intTipDoc, 'n', datFecDia, datFecDia))   )
                    blnRes=false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia, tx_numDia, fe_dia, tx_glo";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL+=" VALUES (";
                strSQL+="" + intCodEmp;
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intTipDoc;
                strSQL+=", " + intCodDia;
                strSQL+=", " + objUti.codificar(strNumDia);
                //Si la fecha recibida es null asigno la fecha del servidor.
                if (datFecDia==null)
                    strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaBaseDatos()) + "'";
                else{
                    //strSQL+=", '" + objUti.formatearFecha(datFecDia, objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=", '" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                    
                }
                strSQL+=", " + objUti.codificar(txtGlo.getText());
                strSQL+=", 'A'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=")";
                System.out.println(":)  insertarCab: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
     * Esta función inserta la cabecera del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param strCodDia El código del diario. Es el código que maneja el sistema.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el código del diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el código del diario recibido.
     * </UL>
     * @param strNumDia El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param datFecDia La fecha del diario.
     * @param estDia El estado del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, String strCodDia, String strNumDia, java.util.Date datFecDia, String estDia){
        int intCodUsr, intNumDia;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try{
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null){
                stm=con.createStatement();
                if (strCodDia==null){
                    //Obtener el código del óltimo registro.
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_dia)";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intCodDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intCodDia==-1)
                        return false;
                    intCodDia++;
                }
                else{
                    intCodDia=Integer.parseInt(strCodDia);
                }
                if (strNumDia==null){
                    //Obtener el nómero de diario.
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intNumDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumDia==-1)
                        return false;
                    intNumDia++;
                    strNumDia="" + intNumDia;
                }
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                
                    if( ! (fechaPermitida(con, intTipDoc, 'n', datFecDia, datFecDia))   )
                        blnRes=false;
                
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia, tx_numDia, fe_dia, tx_glo";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL+=" VALUES (";
                strSQL+="" + intCodEmp;
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intTipDoc;
                strSQL+=", " + intCodDia;
                strSQL+=", " + objUti.codificar(strNumDia);
                //Si la fecha recibida es null asigno la fecha del servidor.
                if (datFecDia==null)
                    strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaBaseDatos()) + "'";
                else{
                    //strSQL+=", '" + objUti.formatearFecha(datFecDia, objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=", '" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                strSQL+=", " + objUti.codificar(txtGlo.getText());
                strSQL+=", '" + estDia + "'"; 
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=")";
                System.out.println("insertarCab: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
     * Esta función inserta el detalle del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        int i;
        int intArlCodCta=0, intTblCodCta=0, intArlAni=0, intArlMes=0;
        boolean blnRes=true;
        String strEstCncCta="";

        try{
            System.out.println("ZZZZZZZZ");
            if (con!=null){
                System.out.println("QQQQQQ");
                stm=con.createStatement();
                for (i=0;i<objTblMod.getRowCountTrue();i++){
                    System.out.println("AAAAAA");
                    intTblCodCta=Integer.parseInt("" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) );
                    strEstCncCta=objTblMod.getValueAt(i,INT_TBL_DAT_EST_CON)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_EST_CON).toString();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detDia (co_emp, co_loc, co_tipDoc, co_dia, co_reg";
                    strSQL+=", co_cta, nd_monDeb, nd_monHab, tx_ref, st_conBan)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + intTipDoc;
                    strSQL+=", " + intCodDia;
                    strSQL+=", " + (i+1);
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA);
                    strSQL+=", " + new java.math.BigDecimal(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEB), 3));
                    strSQL+=", " + new java.math.BigDecimal(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_HAB), 3));
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_REF));
                    if(strEstCncCta.equals("S"))
                        strSQL+=", 'S'";
                    else
                        strSQL+=", Null";
                    strSQL+=")";
                    System.out.println("insertarDet**: " +strSQL);
                    stm.executeUpdate(strSQL);
                    
                }
                stm.close();
                stm=null;

                if(existenCuentaAnteriorCerrada(con, intCodEmp, intCodLoc, intTipDoc, intCodDia)){
                    System.out.println("************************ ");
                    blnRes=false;
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
     * Esta función carga la cabecera del registro. 
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarCab(int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.tx_glo";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtGlo.setText(rst.getString("tx_glo"));
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
     * Esta función carga la cabecera del registro.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarCabCom(int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.tx_numDia, a1.fe_dia, a1.tx_glo, a1.st_reg, a1.fe_ing";
                strSQL+=", a1.fe_ultMod, a1.co_usrIng, a1.co_usrMod";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtGlo.setText(rst.getString("tx_glo"));
                    strNumDia=rst.getString("tx_numDia");
                    datFecDia=rst.getDate("fe_dia");
                    strEstDia=rst.getString("st_reg");
                    datFecIng=rst.getDate("fe_ing");
                    datFecMod=rst.getDate("fe_ultMod");
                    intCodUsrIng=rst.getInt("co_usrIng");
                    intCodUsrMod=rst.getInt("co_usrMod");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
    
    
    private java.util.Date getFechaDiarioDB(Connection conFecha, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDiario){
        Statement stmFecDiaDB;
        ResultSet rstFecDiaDB;
        String strSQLFecDiaDB="";
        datFecDBMod=new java.util.Date();
        try{
            if(conFecha!=null){
                stmFecDiaDB=conFecha.createStatement();
                strSQLFecDiaDB="";
                strSQLFecDiaDB+="SELECT fe_dia";
                strSQLFecDiaDB+=" FROM tbm_cabDia";
                strSQLFecDiaDB+=" WHERE co_emp=" + codigoEmpresa + "";
                strSQLFecDiaDB+=" AND co_loc=" + codigoLocal + "";
                strSQLFecDiaDB+=" AND co_tipDoc=" + codigoTipoDocumento + "";
                strSQLFecDiaDB+=" AND co_dia=" + codigoDiario + "";
                rstFecDiaDB=stmFecDiaDB.executeQuery(strSQLFecDiaDB);
                if(rstFecDiaDB.next()){
                    datFecDBMod=rstFecDiaDB.getDate("fe_dia");
                }

                stmFecDiaDB.close();
                stmFecDiaDB=null;
                rstFecDiaDB.close();
                rstFecDiaDB=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return datFecDBMod;
    }
    
    
    
    /**
     * Esta función actualiza la cabecera del registro. 
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @param strNumDia El nómero de diario.
     * @param datFecDia La fecha del diario.
     * @param strEstReg El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia, String strNumDia, java.util.Date datFecDia, String strEstReg)
    {
        int intCodUsr;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                
                if( ! (fechaPermitida(con, intTipDoc, 'm', datFecDia, getFechaDiarioDB(con, intCodEmp, intCodLoc, intTipDoc, intCodDia)  ))   )
                    blnRes=false;
                
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabDia";
                strSQL+=" SET tx_numDia=" + objUti.codificar(strNumDia);
                //strSQL+=", fe_dia='" + objUti.formatearFecha(datFecDia, objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_dia='" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                if ( ! ( objParSis.getCodigoMenu()==1669) ){
                    strSQL+=", tx_glo=" + objUti.codificar(txtGlo.getText());
                }
                strSQL+=", st_reg='" + strEstReg + "'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + intCodUsr;
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
     * Esta función elimina la cabecera del registro. 
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        int intCodUsr;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabDia";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + intCodUsr;
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);
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
    
    /**
     * Esta función elimina el detalle del registro. 
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detDia";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);
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
    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabTipDoc(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc)
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                System.out.println("actualizarCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
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
    
    
    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabGrpTipDoc(java.sql.Connection con, int intCodEmp, int intCodLoc, String strCodGrpTipDoc)
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabGrpTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_grptipDoc=" + strCodGrpTipDoc;
                stm.executeUpdate(strSQL);
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
    
    
    
    
    
    /**
     * Esta función cambia el estado del diario a anulado.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea anular.
     * @return true: Si se pudo anular el diario.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        int intCodUsr;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabDia";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + intCodUsr;
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);


                String strCodCtaDet="0";
                for(int k=0; k<arlDatMayOnLin.size(); k++){
                    if(k==0)
                        strCodCtaDet="" + objUti.getStringValueAt(arlDatMayOnLin, k, INT_ARL_MAY_LIN_COD_CTA);
                    else
                        strCodCtaDet+=", " + objUti.getStringValueAt(arlDatMayOnLin, k, INT_ARL_MAY_LIN_COD_CTA);
                }

                strSQL="";
                strSQL+=" select a2.co_emp, a2.ne_ani, a2.ne_mes, a2.co_cta";
                strSQL+=" from tbm_cabciemencta AS a1 INNER JOIN tbm_detciemencta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a2.ne_ani=(SELECT extract(year from fe_dia) FROM tbm_cabDia ";
                strSQL+="                  WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc;
                strSQL+="                  AND co_tipDoc=" + intTipDoc + " AND co_dia=" + intCodDia + ")";
                strSQL+=" AND a2.ne_mes=(SELECT extract(month from fe_dia) FROM tbm_cabDia ";
                strSQL+="                  WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc;
                strSQL+="                  AND co_tipDoc=" + intTipDoc + " AND co_dia=" + intCodDia + ")";
                strSQL+=" AND a2.co_cta IN(" + strCodCtaDet + ");";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    mostrarMsgInf("<HTML>Una de las cuentas del asiento de diario está cerrada.<BR>Reaperture la cuenta y vuelva a intentarlo.<HTML>");
                    blnRes=false;
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;

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
     * Esta función carga el detalle del registro. 
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        int intNumTotReg, i;
        //double dblDeb, dblHab, dblTotDeb, dblTotHab;
        BigDecimal bdeDeb, bdeHab, bdeTotDeb, bdeTotHab;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                //--- Inicio bloque comentado el 17/May/2019 ---
                //Este bloque fue comentado debido a que el Numero y Nombre de la cuenta_contable ya no es traido de tbm_plaCta. Ahora, por el Plan de 
                //cuentas Unificado, estos datos son traidos de tbm_salCta.
                /*
                strSQL+="SELECT a1.co_cta, a2.tx_codCta, a2.tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a1.st_conBan";
                strSQL+=" FROM tbm_detDia AS a1, tbm_plaCta AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                */
                //--- Fin bloque comentado el 17/May/2019 ------
                strSQL+="SELECT a1.co_cta, a3.tx_codCta, a3.tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a1.st_conBan ";
                strSQL+="FROM tbm_detDia AS a1 ";
                strSQL+="INNER JOIN tbm_cabDia AS a2 on a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                strSQL+="INNER JOIN tbm_salCta AS a3 on a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta ";
                strSQL+="WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                strSQL+=" AND cast(a3.co_per as varchar) = ( substring(cast(a2.fe_dia as varchar), 1, 4) || '01' )";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                bdeTotDeb=new BigDecimal("0");
                bdeTotHab=new BigDecimal("0");
                while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                        vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                        bdeDeb=rst.getBigDecimal("nd_monDeb");
                        bdeHab=rst.getBigDecimal("nd_monHab");
                        bdeTotDeb=bdeTotDeb.add(bdeDeb);
                        bdeTotHab=bdeTotHab.add(bdeHab);
                        vecReg.add(INT_TBL_DAT_DEB,"" + bdeDeb);
                        vecReg.add(INT_TBL_DAT_HAB,"" + bdeHab);
                        vecReg.add(INT_TBL_DAT_REF,rst.getString("tx_ref"));
                        vecReg.add(INT_TBL_DAT_EST_CON,rst.getObject("st_conBan")==null?"":rst.getString("st_conBan"));
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
                //Almacenar el monto del debe y haber en variables globales.
                dblMonIniDeb=Double.parseDouble("" + bdeTotDeb);
                dblMonIniHab=Double.parseDouble("" + bdeTotHab);
                dblMonDeb=Double.parseDouble("" + bdeTotDeb);
                dblMonHab=Double.parseDouble("" + bdeTotHab);
                
                bdeMonDeb=bdeTotDeb;
                bdeMonHab=bdeTotHab;
                
//                txtDif.setText("" + objUti.formatearNumero((dblTotDeb-dblTotHab),objParSis.getFormatoNumero(),true));
                txtDif.setText("" + objUti.redondearBigDecimal((bdeTotDeb.subtract(bdeTotHab)), objParSis.getDecimalesBaseDatos()));
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
     * Esta función calcula la diferencia entre el debe y el haber.
     */
    private void calcularDif()
    {
        double dblDeb=0, dblHab=0, dblTotDeb=0, dblTotHab=0;
        int intFilPro, i;
        String strConCel;
        BigDecimal bdeTotHab=new BigDecimal("0");
        try
        {
            intFilPro=objTblMod.getRowCount();
            for (i=0; i<intFilPro; i++)
            {
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_DEB)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_DEB).toString();
                dblDeb=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                strConCel=(objTblMod.getValueAt(i, INT_TBL_DAT_HAB)==null)?"":objTblMod.getValueAt(i, INT_TBL_DAT_HAB).toString();
                dblHab=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                dblTotDeb+=dblDeb;
                dblTotHab+=dblHab;
                bdeTotHab=(objUti.isNumero(strConCel))?new BigDecimal(strConCel):new BigDecimal("0");
            }
            //Almacenar el monto del debe y haber en variables globales.
            dblMonDeb=dblTotDeb;
            dblMonHab=dblTotHab;
            
            bdeMonDeb=new BigDecimal(""+dblTotDeb);
            bdeMonHab=new BigDecimal(""+dblTotHab);
            
            //Calcular la diferencia.
            txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)), objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtDif.setText("[ERROR]");
        }
    }
    
    /**
     * Esta función determina si los campos son vólidos.
     * @return true: Si los campos son vólidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if(objParSis.getCodigoMenu()==1708){
            if(   (txtGlo.getText().equals(""))  ||  (txtGlo.getText().equals("null")) ||  (txtGlo.getText().equals(null))   ){
                mostrarMsgInf("<HTML>El campo GLOSA es obligatorio.<BR>Ingrese una glosa para el documento y vuelva a intentarlo</HTML>");
                return false;
            }
            if(objTblMod.getRowCountTrue()<=0){
                mostrarMsgInf("<HTML>El diario no se ha generado.<BR>Ingrese un diario para el documento y vuelva a intentarlo</HTML>");
                return false;
            }
            
        }

        //Validar el "Detalle del Asiento de Diario".
        objTblMod.removeEmptyRows();
        if (!objTblMod.isAllRowsComplete())
        {
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        
        

        return true;
    }
    
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento.
     * <BR>Es decir:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento". La cadena debe contener un "int".
     * @param valorDebe El valor para la cuenta del debe. La cadena debe contener un "double".
     * @param valorHaber El valor para la cuenta del haber. La cadena debe contener un "double".
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(String codigoTipoDocumento, String valorDebe, String valorHaber)
    {
        int intCodTipDoc;
        double dblValDeb, dblValHab;
        boolean blnRes=true;
        try
        {
            intCodTipDoc=Integer.parseInt(codigoTipoDocumento);
            dblValDeb=objUti.parseDouble(valorDebe);
            dblValHab=objUti.parseDouble(valorHaber);
            blnRes=generarDiario(intCodTipDoc, dblValDeb, dblValHab);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento.
     * <BR>Es decir:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param valorDebe El valor para la cuenta del debe.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int codigoTipoDocumento, double valorDebe, double valorHaber)
    {
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                        strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                        strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND b1.co_tipDoc=" + codigoTipoDocumento;
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else
                            {
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);

                            
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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


    ///---DARIO---codigo nuevo para generar asiento de depositos en transito que apunten a los nuevos campos -- 13/Ago/2008 --///
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento.
     * <BR>Es decir:
     * <UL>
     * <LI>Cuenta de debe Transito: Se obtiene de "tbm_cabTipDoc.co_ctaDebTra".
     * <LI>Cuenta de haber Transito: Se obtiene de "tbm_cabTipDoc.co_ctaHabTra".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param valorDebe El valor para la cuenta del debe.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiarioTra(int codigoTipoDocumento, double valorDebe, double valorHaber)
    {
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_ctaDebTra AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDebTra=a2.co_cta";
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT b1.co_ctaHabTra AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                        strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHabTra=b2.co_cta";
                        strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND b1.co_tipDoc=" + codigoTipoDocumento;
                                               
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else
                            {
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab) ),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <BR>Si la cuenta es de debe. O sea, ubicacionCuenta=D:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuenta".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * <BR>Si la cuenta es de haber. O sea, ubicacionCuenta=H:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuenta".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento". La cadena debe contener un "int".
     * @param codigoCuenta El código de la "Cuenta". La cadena debe contener un "int".
     * @param ubicacionCuenta La ubicación de la cuenta. Puede recibir "D" o "H".
     * @param valorDebe El valor para la cuenta del debe. La cadena debe contener un "double".
     * @param valorHaber El valor para la cuenta del haber. La cadena debe contener un "double".
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(String codigoTipoDocumento, String codigoCuenta, String ubicacionCuenta, String valorDebe, String valorHaber)
    {
        int intCodTipDoc, intCodCta;
        double dblValDeb, dblValHab;
        boolean blnRes=true;
        try
        {
            intCodTipDoc=Integer.parseInt(codigoTipoDocumento);
            intCodCta=Integer.parseInt(codigoCuenta);
            dblValDeb=objUti.parseDouble(valorDebe);
            dblValHab=objUti.parseDouble(valorHaber);
            blnRes=generarDiario(intCodTipDoc, intCodCta, ubicacionCuenta, dblValDeb, dblValHab);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <BR>Si la cuenta es de debe. O sea, ubicacionCuenta=D:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuenta".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * <BR>Si la cuenta es de haber. O sea, ubicacionCuenta=H:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuenta".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param codigoCuenta El código de la "Cuenta". Por lo general una cuenta de Banco.
     * @param ubicacionCuenta La ubicación de la cuenta. Puede recibir "D" o "H".
     * @param valorDebe El valor para la cuenta del debe.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int codigoTipoDocumento, int codigoCuenta, String ubicacionCuenta, double valorDebe, double valorHaber)
    {
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        if (ubicacionCuenta.equals("D"))
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'D' AS tx_natCta";
                            strSQL+=" FROM tbm_plaCta AS b1";
                            strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_cta=" + codigoCuenta;
                            strSQL+=" UNION ALL";
                            strSQL+=" SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta";
                            strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                            strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                            strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND b1.co_tipDoc=" + codigoTipoDocumento;
                        }
                        else
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                            strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                            strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                            strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" UNION ALL";
                            strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'H' AS tx_natCta";
                            strSQL+=" FROM tbm_plaCta AS b1";
                            strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_cta=" + codigoCuenta;
                        }
                        System.out.println("888 - strSQL: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                                System.out.println("si - tx_natCta: " + rst.getString("tx_natCta"));
                            }
                            else
                            {
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                                System.out.println("no - tx_natCta: " + rst.getString("tx_natCta"));
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab) ),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);

                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <BR>Si la cuenta es de debe. O sea, ubicacionCuenta=D:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuenta".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * <BR>Si la cuenta es de haber. O sea, ubicacionCuenta=H:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuenta".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento". La cadena debe contener un "int".
     * @param codigoCuenta El código de la "Cuenta". La cadena debe contener un "int".
     * @param valorDebe El valor para la cuenta del debe. La cadena debe contener un "double".
     * @param valorHaber El valor para la cuenta del haber. La cadena debe contener un "double".
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(String codigoTipoDocumento, String codigoCuenta, String valorDebe, String valorHaber)
    {
        int intCodTipDoc, intCodCta;
        double dblValDeb, dblValHab;
        boolean blnRes=true;
        try
        {
            intCodTipDoc=Integer.parseInt(codigoTipoDocumento);
            intCodCta=Integer.parseInt(codigoCuenta);
            dblValDeb=objUti.parseDouble(valorDebe);
            dblValHab=objUti.parseDouble(valorHaber);
            blnRes=generarDiario(intCodTipDoc, intCodCta, dblValDeb, dblValHab);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <BR>Si la cuenta es de debe. O sea, ubicacionCuenta=D:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuenta".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * <BR>Si la cuenta es de haber. O sea, ubicacionCuenta=H:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuenta".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param codigoCuenta El código de la "Cuenta". Por lo general una cuenta de Banco.
     * @param valorDebe El valor para la cuenta del debe.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int codigoTipoDocumento, int codigoCuenta, double valorDebe, double valorHaber)
    {
        boolean blnRes=true;
        String ubicacionCuenta="";
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Obtener la ubicación de la cuenta.
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_cta AS co_cta, a2.tx_codCta, a2.tx_desLar, a1.tx_ubiCta";
                        strSQL+=" FROM tbm_detTipDoc AS a1, tbm_plaCta AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        strSQL+=" AND a1.co_cta=" + codigoCuenta;
                        rst=stm.executeQuery(strSQL);
                        if (rst.next())
                        {
                            vecAux=new Vector();
                            vecAux.add(INT_TBL_DAT_LIN,"");
                            vecAux.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecAux.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecAux.add(INT_TBL_DAT_BUT_CTA,"");
                            vecAux.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            ubicacionCuenta=rst.getString("tx_ubiCta");
                            if (ubicacionCuenta.equals("D"))
                            {
                                vecAux.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecAux.add(INT_TBL_DAT_HAB,null);
                            }
                            else
                            {
                                vecAux.add(INT_TBL_DAT_DEB,null);
                                vecAux.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecAux.add(INT_TBL_DAT_REF,null);
                            vecAux.add(INT_TBL_DAT_EST_CON,null);

                        }
                        rst.close();
                        //Obtener la cuenta de debe/haber segón sea el caso.
                        if (ubicacionCuenta.equals("D"))
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar";
                            strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                            strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                            strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND b1.co_tipDoc=" + codigoTipoDocumento;
                        }
                        else
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar";
                            strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                            strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                            strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        }
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        if (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (ubicacionCuenta.equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            else
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
                        }
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        //Adicionar los registros a "vecDat" segón corresponda.
                        if (ubicacionCuenta.equals("D"))
                        {
                            vecDat.add(vecAux);
                            vecDat.add(vecReg);
                        }
                        else
                        {
                            vecDat.add(vecReg);
                            vecDat.add(vecAux);
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
//                    System.out.println("numero filas : " + objTblMod.getRowCountTrue());
//                    System.out.println("numero filas : " + objTblMod.getRowCount());
//                    System.out.println("valorDebe : " + valorDebe);
//                    System.out.println("valorHaber : " + valorHaber);
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y al tipo de retención
     * que se encuentra en la columna especificada en un JTable.
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param tabla El JTable del que se obtendran los datos para generar el diario.
     * @param columnaCasilla La columna donde se encuentra la casilla de verificación que determina si se debe o no procesar la fila.
     * @param columnaValores La columna de la que se obtendran los valores que se mostrarón en la columna del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int codigoTipoDocumento, javax.swing.JTable tabla, int columnaCasilla, int columnaRetencion, int columnaValores)
    {
        int i, j;
        boolean blnExiCod=false;
        Object objAux;
        String strAux1, strAux2;
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                case 1: //Diario generado por el Sistema.
                    getDatTipDocBasDat(codigoTipoDocumento);
                    if (getDatRetBasDat(tabla, columnaCasilla, columnaRetencion))
                    {
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Insertar la cuenta de debe.
                        objAux=vecCta.get(0);
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(1));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(2));
                        vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                        vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(3));
                        vecReg.add(INT_TBL_DAT_DEB,null);
                        vecReg.add(INT_TBL_DAT_HAB,null);
                        vecReg.add(INT_TBL_DAT_REF,null);
                        vecReg.add(INT_TBL_DAT_EST_CON,null);

                        vecDat.add(vecReg);
                        //Insertar la(s) cuenta(s) de haber.
                        objTblModAux=(ZafTblMod)tabla.getModel();
                        for (i=0; i<objTblModAux.getRowCountTrue(); i++)
                        {
                            if (objTblModAux.isChecked(i,columnaCasilla))
                            {
                                objAux=objTblModAux.getValueAt(i,columnaRetencion);
                                strAux1=(objAux==null?"":objAux.toString());
                                //Obtener el indice del "Tipo de retención" del ArrayList.
                                blnExiCod=false;
                                for (j=0; j<vecCta.size();j++)
                                {
                                    objAux=((Vector)vecCta.get(j)).get(0);
                                    strAux2=(objAux==null?"":objAux.toString());
                                    if (strAux1.equals(strAux2))
                                    {
                                        blnExiCod=true;
                                        break;
                                    }
                                }
                                if (blnExiCod)
                                {
                                    objAux=vecCta.get(j);
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(1));
                                    vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(2));
                                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                    vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(3));
                                    vecReg.add(INT_TBL_DAT_DEB,null);
                                    vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                    vecReg.add(INT_TBL_DAT_REF,null);
                                    vecReg.add(INT_TBL_DAT_EST_CON,null);

                                    vecDat.add(vecReg);
                                }
                                else
                                {
                                    objAux=vecCta.get(1);
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(1));
                                    vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(2));
                                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                    vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(3));
                                    vecReg.add(INT_TBL_DAT_DEB,null);
                                    vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                    vecReg.add(INT_TBL_DAT_REF,null);
                                    vecReg.add(INT_TBL_DAT_EST_CON,null);
                                    vecDat.add(vecReg);
                                }
                            }
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        calcularDif();
                        dblMonDeb=dblMonHab;
                        bdeMonDeb=bdeMonHab;
                        objTblMod.setValueAt("" + bdeMonDeb, 0, INT_TBL_DAT_DEB);
                        txtDif.setText("0");
                    }
                    break;
                case 2: //Diario generado por el usuario.
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
    
    
  


    
/*La siguiente función recibe como parómetro de fecha un NULL
 *pero es porque en la funcion de cabecera se captura la funcion del sistema.
 *no necesariamente se tiene q enviar NULL
 */    
    public boolean insertarDiario_OC(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String CodDiario,  String numeroDiario, java.util.Date fechaDiario, int codigoMenuReferencial)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );
            intTipPro=0;
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            if (isCamVal())
                if(codigoMenuReferencial!=0){
                    if (insertarCab(conexion, empresa, local, tipoDocumento, CodDiario, numeroDiario, fechaDiario, codigoMenuReferencial))
                        if (insertarDet(conexion, empresa, local, tipoDocumento,  Integer.parseInt(CodDiario) ))
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, Integer.parseInt(CodDiario), fechaDiario, fechaDiario, intTipPro))
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, Integer.parseInt(CodDiario)))                    
                                    {
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                }
                else{
                    return false;
                }
            
            

        }
        return false;   
    }
    
    
    
    
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento
     * que se encuentra en la columna especificada en un JTable.
     * @param tabla El JTable del que se obtendran los datos para generar el diario.
     * @param columnaCasilla La columna donde se encuentra la casilla de verificación que determina si se debe o no procesar la fila.
     * @param columnaTipoDocumento La columna donde se encuentra el tipo de documento.
     * @param columnaValores La columna de la que se obtendran los valores que se mostrarón en la columna del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(javax.swing.JTable tabla, int columnaCasilla, int columnaTipoDocumento, int columnaValores)
    {
        int i, j, k;
        boolean blnExiCod=false;
        Object objAux;
        String strAux1;
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                case 1: //Diario generado por el Sistema.
                    if (getDatTipDocBasDat(tabla, columnaCasilla, columnaTipoDocumento))
                    {
                        //Limpiar vector de datos.
                        vecDat.clear();
                        objTblModAux=(ZafTblMod)tabla.getModel();
                        for (i=0; i<objTblModAux.getRowCountTrue(); i++)
                        {
                            if (objTblModAux.isChecked(i,columnaCasilla))
                            {
                                objAux=objTblModAux.getValueAt(i,columnaTipoDocumento);
                                strAux1=(objAux==null?"":objAux.toString());
                                //Buscar la cuenta de debe y la cuenta de haber.
                                for (k=0; k<2; k++)
                                {
                                    //Obtener el indice del "Tipo de documento" del ArrayList.
                                    blnExiCod=false;
                                    for (j=0; j<vecCta.size();j++)
                                    {
                                        if ( k==0 && strAux1.equals(objUti.getStringValueAt(vecCta,j,INT_CTA_COD_DOC)) && objUti.getStringValueAt(vecCta,j,INT_CTA_UBI_CTA).equals("D") )
                                        {
                                            blnExiCod=true;
                                            break;
                                        }
                                        else if ( k==1 && strAux1.equals(objUti.getStringValueAt(vecCta,j,INT_CTA_COD_DOC)) && objUti.getStringValueAt(vecCta,j,INT_CTA_UBI_CTA).equals("H") )
                                        {
                                            blnExiCod=true;
                                            break;
                                        }
                                    }
                                    if (blnExiCod)
                                    {
                                        objAux=vecCta.get(j);
                                        vecReg=new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN,"");
                                        vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(INT_CTA_COD_CTA));
                                        vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(INT_CTA_NUM_CTA));
                                        vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                        vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(INT_CTA_NOM_CTA));
                                        if (k==0)
                                        {
                                            vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                            vecReg.add(INT_TBL_DAT_HAB,null);
                                        }
                                        else
                                        {
                                            vecReg.add(INT_TBL_DAT_DEB,null);
                                            vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                        }
                                        vecReg.add(INT_TBL_DAT_REF,null);
                                        vecReg.add(INT_TBL_DAT_EST_CON,null);
                                        vecDat.add(vecReg);
                                    }
                                    else
                                    {
                                        //Insertar la cuenta de debe.
                                        vecReg=new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN,"");
                                        vecReg.add(INT_TBL_DAT_COD_CTA,null);
                                        vecReg.add(INT_TBL_DAT_NUM_CTA,null);
                                        vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                        vecReg.add(INT_TBL_DAT_NOM_CTA,null);
                                        if (k==0)
                                        {
                                            vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                            vecReg.add(INT_TBL_DAT_HAB,null);
                                        }
                                        else
                                        {
                                            vecReg.add(INT_TBL_DAT_DEB,null);
                                            vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                        }
                                        vecReg.add(INT_TBL_DAT_REF,null);
                                        vecReg.add(INT_TBL_DAT_EST_CON,null);
                                        vecDat.add(vecReg);
                                    }
                                }
                            }
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        calcularDif();
                    }
                    break;
                case 2: //Diario generado por el usuario.
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
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento
     * que se encuentra en la columna especificada en un JTable.
     * @param tabla El JTable del que se obtendran los datos para generar el diario.
     * @param columnaCasilla La columna donde se encuentra la casilla de verificación que determina si se debe o no procesar la fila.
     * @param tipoDocumento El tipo de documento descrito en la cabecera del programa
     * @param columnaCodigoCuenta La columna de la cuenta que se desea formatear.
     * @param columnaValores La columna de la que se obtendran los valores que se mostrarón en la columna del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(javax.swing.JTable tabla, int columnaCasilla, int tipoDocumento, int columnaCodigoCuenta, int columnaValores)
    {
        int intAuxTipDoc, intAuxCodCta;
        String strNatCta="";
        boolean blnRes=true;
        Object objAux;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                case 1: //Diario generado por el Sistema.
                    vecDat.clear();
                    objTblModAux=(ZafTblMod)tabla.getModel();
                    for (int i=0; i<objTblModAux.getRowCountTrue(); i++){
                        if(objTblModAux.isChecked(i, columnaCasilla)){
                            intAuxTipDoc=tipoDocumento;
                            intAuxCodCta=Integer.parseInt(objTblModAux.getValueAt(i,columnaCodigoCuenta)==null?"0":objTblModAux.getValueAt(i,columnaCodigoCuenta).toString());
                            if (getCuentasTipoDocumento(intAuxTipDoc, "S", intAuxCodCta)){
                                for(int j=0;j<vecCta.size(); j++){
                                    objAux=vecCta.get(j);
                                    strNatCta="" + (  ((Vector)objAux).get(INT_CTA_UBI_CTA) );
                                    if(strNatCta.equals("D")){
                                        vecReg=new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN,"");
                                        vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(INT_CTA_COD_CTA));
                                        vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(INT_CTA_NUM_CTA));
                                        vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                        vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(INT_CTA_NOM_CTA));
                                        vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                        vecReg.add(INT_TBL_DAT_HAB,null);
                                        vecReg.add(INT_TBL_DAT_REF,null);
                                        vecReg.add(INT_TBL_DAT_EST_CON,null);
                                        vecDat.add(vecReg);
                                    }
                                    else{//cuenta al haber
                                        //Insertar la cuenta de debe.
                                        vecReg=new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN,"");
                                        vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(INT_CTA_COD_CTA));
                                        vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(INT_CTA_NUM_CTA));
                                        vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                        vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(INT_CTA_NOM_CTA));
                                        vecReg.add(INT_TBL_DAT_DEB,null);
                                        vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                        vecReg.add(INT_TBL_DAT_REF,null);
                                        vecReg.add(INT_TBL_DAT_EST_CON,null);
                                        vecDat.add(vecReg);
                                    }
                                }



                            }
                        }

                    }

                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    calcularDif();

                    break;
                case 2: //Diario generado por el usuario.
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
     * Esta función genera el "Asiento de diario" de transferencias de inventario de acuerdo a los datos
     * que se encuentran en las columnas especificadas en un JTable.
     * @param tabla El JTable del que se obtendran los datos para generar el diario.
     * @param columnaBodegaOrigen La columna donde se encuentra el código de la bodega origen.
     * @param columnaBodegaDestino La columna donde se encuentra el código de la bodega destino.
     * @param columnaValores La columna de la que se obtendran los valores que se mostrarón en la columna del debe/haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiarioTransferencias(javax.swing.JTable tabla, int columnaBodegaOrigen, int columnaBodegaDestino, int columnaValores)
    {
        int i, j, k;
        boolean blnExiCod=false;
        Object objAux=null;
        String strAux1;
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                case 1: //Diario generado por el Sistema.
                    if (getDatBodBasDat(tabla, columnaBodegaOrigen, columnaBodegaDestino))
                    {
                        //Limpiar vector de datos.
                        vecDat.clear();
                        objTblModAux=(ZafTblMod)tabla.getModel();
                        for (i=0; i<objTblModAux.getRowCountTrue(); i++)
                        {
                            //Buscar la cuenta de debe y la cuenta de haber.
                            for (k=0; k<2; k++)
                            {
                                switch (k)
                                {
                                    case 0:
                                        objAux=objTblModAux.getValueAt(i,columnaBodegaOrigen);
                                        break;
                                    case 1:
                                        objAux=objTblModAux.getValueAt(i,columnaBodegaDestino);
                                        break;
                                }
                                strAux1=(objAux==null?"":objAux.toString());
                                //Obtener el indice del "Tipo de documento" del Vector.
                                blnExiCod=false;
                                for (j=0; j<vecCta.size();j++)
                                {
                                    if ( strAux1.equals(objUti.getStringValueAt(vecCta,j,INT_CTA_COD_DOC)) )
                                    {
                                        blnExiCod=true;
                                        break;
                                    }
                                }
                                if (blnExiCod)
                                {
                                    objAux=vecCta.get(j);
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(INT_CTA_COD_CTA));
                                    vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(INT_CTA_NUM_CTA));
                                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                    vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(INT_CTA_NOM_CTA));
                                    if (k==0)
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,null);
                                        vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                    }
                                    else
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                        vecReg.add(INT_TBL_DAT_HAB,null);
                                    }
                                    vecReg.add(INT_TBL_DAT_REF,null);
                                    vecReg.add(INT_TBL_DAT_EST_CON,null);
                                    vecDat.add(vecReg);
                                }
                                else
                                {
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_COD_CTA,null);
                                    vecReg.add(INT_TBL_DAT_NUM_CTA,null);
                                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                    vecReg.add(INT_TBL_DAT_NOM_CTA,null);
                                    if (k==0)
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,null);
                                        vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                    }
                                    else
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                        vecReg.add(INT_TBL_DAT_HAB,null);
                                    }
                                    vecReg.add(INT_TBL_DAT_REF,null);
                                    vecReg.add(INT_TBL_DAT_EST_CON,null);
                                    vecDat.add(vecReg);
                                }
                            }
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        calcularDif();
                    }
                    break;
                case 2: //Diario generado por el usuario.
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
     * Esta función obtiene los códigos de las bodegas que deben aparecer en el asiento de diario.
     * Los datos de las bodegas quedan almacenados en un Vector. Al generar el diario se buscaró
     * la cuenta en dicho Vector lo cual evitaró consultar constantemente la base de datos. Sólo
     * si la bodega no existe en el Vector se consultaró la base de datos.
     * @param tblPro El JTable del que se obtendran los datos para generar el diario.
     * @param intColBodOrg La columna donde se encuentra el código de la bodega origen.
     * @param intColBodDes La columna donde se encuentra el código de la bodega destino.
     * @return true: Si el mótodo se ejecutó con óxito.
     * <BR>false: En el caso contrario.
     */
    private boolean getDatBodBasDat(javax.swing.JTable tblPro, int intColBodOrg, int intColBodDes)
    {
        int i;
        String strCodBod;
        boolean blnRes=true;
        try
        {
            //Obtener datos adicionales de la "Bodega" sólo si hay códigos a buscar.
            strCodBod=getCodBodPro(tblPro, intColBodOrg, intColBodDes);
            if (!strCodBod.equals(""))
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_bod, a2.co_cta, a2.tx_codCta, a2.tx_desLar, Null AS tx_ubi";
                    strSQL+=" FROM tbm_bod AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_ctaExi=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_bod IN (" + strCodBod + ")";
                    strSQL+=" ORDER BY a1.co_bod";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecCta.clear();
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_CTA_COD_DOC,rst.getString("co_bod"));
                        vecReg.add(INT_CTA_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_CTA_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_CTA_NOM_CTA,rst.getString("tx_desLar"));
                        vecReg.add(INT_CTA_UBI_CTA,rst.getString("tx_ubi"));
                        vecCta.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
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
     * Esta función obtiene el código de la bodega del que se debe obtener
     * la descripción corta, descripción larga, etc.
     * @param tblPro El JTable del que se va a obtener los códigos de las bodegas.
     * @param intColBodOrg La columna donde se encuentra el código de la bodega origen.
     * @param intColBodDes La columna donde se encuentra el código de la bodega destino.
     * @return La cadena que contiene los códigos que se deben obtener de la base de datos.
     * Por ejemplo, '1', '4', '5' indica que se debe obtener los datos de los documentos 1, 4 y 5.
     */
    private String getCodBodPro(javax.swing.JTable tblPro, int intColBodOrg, int intColBodDes)
    {
        int i, intNumFil;
        String strCta="";
        Object objAux;
        objTblModAux=(ZafTblMod)tblPro.getModel();
        intNumFil=objTblModAux.getRowCountTrue();
        for (i=0; i<intNumFil;i++)
        {
            //Bodega origen.
            objAux=tblPro.getValueAt(i,intColBodOrg);
            if (objAux!=null)
            {
                if (!objAux.toString().equals(""))
                {
                    if (strCta.length()==0)
                        strCta="'" + objAux.toString() + "'";
                    else
                    {
                        if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                            strCta+=",'" + objAux.toString() + "'";
                    }
                }
            }
            //Bodega destino.
            objAux=tblPro.getValueAt(i,intColBodDes);
            if (objAux!=null)
            {
                if (!objAux.toString().equals(""))
                {
                    if (strCta.length()==0)
                        strCta="'" + objAux.toString() + "'";
                    else
                    {
                        if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                            strCta+=",'" + objAux.toString() + "'";
                    }
                }
            }
        }
        if (strCodPro==null)
            strCodPro=strCta;
        else
        {
            if (strCodPro.equals(strCta))
                strCta="";
            else
                strCodPro=strCta;
        }
        objTblModAux=null;
        return strCta;
    }
    
    /**
     * Esta función obtiene la cuenta del debe y haber del Tipo de documento especificado.
     * Los datos quedan almacenados en un Vector donde la primera fila almacena la cuenta
     * del debe y la segunda fila la cuenta del haber.
     * @param intCodTipDoc El código del "Tipo de documento".
     * @return true: Si el mótodo se ejecutó con óxito.
     * <BR>false: En el caso contrario.
     */
    private boolean getDatTipDocBasDat(int intCodTipDoc)
    {
        int i;
        Object objAux;
        boolean blnExiCod=false;
        boolean blnRes=true;
        try
        {
            //Obtener datos adicionales del "Tipo de documento" sólo si hay códigos a buscar.
            for (i=0; i<vecCta.size(); i++)
            {
                objAux=objUti.getObjectValueAt(vecCta, i, 0);
                if (objAux!=null)
                    if (intCodTipDoc==Integer.parseInt(objAux.toString()))
                    {
                        blnExiCod=true;
                        break;
                    }
            }
            if (!blnExiCod)
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                    strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc;
                    strSQL+=" UNION ALL";
                    strSQL+=" SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta";
                    strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                    strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                    strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND b1.co_tipDoc=" + intCodTipDoc;
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecCta.clear();
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_CTA_COD_DOC,"" + intCodTipDoc);
                        vecReg.add(INT_CTA_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_CTA_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_CTA_NOM_CTA,rst.getString("tx_desLar"));
                        vecReg.add(INT_CTA_UBI_CTA,rst.getString("tx_natCta"));
                        vecCta.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
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
     * Esta función obtiene el código de las retenciones de las que se debe obtener
     * la descripción corta, descripción larga, etc.
     * @param tblDat El JTable del que se va a obtener los códigos de las retenciones.
     * @param intColRet La columna donde se encuentran los códigos de las retenciones.
     * @param intColChk La columna donde se encuentra el JCheckBox que indica si se debe procesar la fila.
     * @return La cadena que contiene los códigos que se deben obtener de la Base de datos.
     * Por ejemplo, '1', '4', '5' indica que se debe obtener los datos de los tipos de retenciones 1, 4 y 5.
     */
    private String getCodRetPro(javax.swing.JTable tblPro, int intColChk, int intColRet)
    {
        int i, intNumFil;
        String strCta="";
        Object objAux;
        objTblModAux=(ZafTblMod)tblPro.getModel();
        intNumFil=objTblModAux.getRowCountTrue();
        for (i=0; i<intNumFil;i++)
        {
            if (objTblModAux.isChecked(i,intColChk))
            {
                objAux=tblPro.getValueAt(i,intColRet);
                if (objAux!=null)
                {
                    if (!objAux.toString().equals(""))
                    {
                        if (strCta.length()==0)
                            strCta="'" + objAux.toString() + "'";
                        else
                        {
                            if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                strCta+=",'" + objAux.toString() + "'";
                        }
                    }
                }
            }
        }
        if (strCodPro==null)
            strCodPro=strCta;
        else
        {
            if (strCodPro.equals(strCta))
                strCta="";
            else
                strCodPro=strCta;
        }
        objTblModAux=null;
        return strCta;
    }
    
    /**
     * Esta función obtiene las cuentas de retención que deben aparecer en el asiento de diario.
     * Los datos quedan almacenados en un Vector donde los datos de las cuentas de retención serón
     * almacenadas a partir de la tercera fila ya que las dos primeras filas almacenan las cuentas
     * de debe y haber respectivamente.
     * @param intCodTipDoc El código del "Tipo de documento".
     * @return true: Si el mótodo se ejecutó con óxito.
     * <BR>false: En el caso contrario.
     */
    private boolean getDatRetBasDat(javax.swing.JTable tblPro, int intColChk, int intColRet)
    {
        int i;
        String strCodTipRet;
        boolean blnRes=true;
        try
        {
            //Obtener datos adicionales del "Tipo de retención" sólo si hay códigos a buscar.
            strCodTipRet=getCodRetPro(tblPro, intColChk, intColRet);
            if (!strCodTipRet.equals(""))
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipRet, a1.co_cta, a2.tx_codCta, a2.tx_desLar";
                    strSQL+=" FROM tbm_cabTipRet AS a1, tbm_plaCta AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_tipRet IN (" + strCodTipRet + ")";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    for (i=2;i<vecCta.size();i++)
                        vecCta.removeElementAt(i);
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_CTA_COD_DOC,rst.getString("co_tipRet"));
                        vecReg.add(INT_CTA_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_CTA_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_CTA_NOM_CTA,rst.getString("tx_desLar"));
                        vecCta.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
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
     * Esta función obtiene las cuentas de retención que deben aparecer en el asiento de diario.
     * Los datos quedan almacenados en un Vector donde los datos de las cuentas de retención serón
     * almacenadas a partir de la tercera fila ya que las dos primeras filas almacenan las cuentas
     * de debe y haber respectivamente.
     * @param intCodTipDoc El código del "Tipo de documento".
     * @return true: Si el mótodo se ejecutó con óxito.
     * <BR>false: En el caso contrario.
     */
    private boolean getDatTipDocBasDat(javax.swing.JTable tblPro, int intColChk, int intColTipDoc)
    {
        int i;
        String strCodTipDoc;
        boolean blnRes=true;
        try
        {
            //Obtener datos adicionales del "Tipo de retención" sólo si hay códigos a buscar.
            strCodTipDoc=getCodRetPro(tblPro, intColChk, intColTipDoc);
            if (!strCodTipDoc.equals(""))
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT c1.co_tipDoc, c1.co_cta, c1.tx_codCta, c1.tx_desLar, c1.tx_ubi";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_tipDoc, a2.co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_ubi";
                    strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc IN (" + strCodTipDoc + ")";
                    strSQL+=" UNION ALL";
                    strSQL+=" SELECT b1.co_tipDoc, b2.co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_ubi";
                    strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                    strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                    strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND b1.co_tipDoc IN (" + strCodTipDoc + ")";
                    strSQL+=" ) AS c1";
                    strSQL+=" ORDER BY c1.co_tipDoc, c1.tx_ubi";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecCta.clear();
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_CTA_COD_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_CTA_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_CTA_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_CTA_NOM_CTA,rst.getString("tx_desLar"));
                        vecReg.add(INT_CTA_UBI_CTA,rst.getString("tx_ubi"));
                        vecCta.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
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
     * Esta función obtiene las cuentas de retención que deben aparecer en el asiento de diario.
     * Los datos quedan almacenados en un Vector donde los datos de las cuentas de retención serón
     * almacenadas a partir de la tercera fila ya que las dos primeras filas almacenan las cuentas
     * de debe y haber respectivamente.
     * @param intCodTipDoc El código del "Tipo de documento".
     * @return true: Si el mótodo se ejecutó con óxito.
     * <BR>false: En el caso contrario.
     */
    private boolean getCuentasTipoDocumento(int intTipDoc, String isCuentaDebe, int codigoCuenta)
    {
        int i;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(isCuentaDebe.equals("S")){
                    strSQL="";
                    strSQL+="SELECT c1.co_tipDoc, c1.co_cta, c1.tx_codCta, c1.tx_desLar, c1.tx_ubi";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_tipDoc, a2.co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_ubi";
                    strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc IN (" + intTipDoc + ")";
                    strSQL+=" UNION ";
                    strSQL+=" SELECT " + intTipDoc + " AS co_tipDoc, b2.co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_ubi";
                    strSQL+=" FROM tbm_plaCta AS b2";
                    strSQL+=" WHERE ";
                    strSQL+=" b2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND b2.co_cta=" + codigoCuenta + "";
                    strSQL+=" ) AS c1";
                    strSQL+=" ORDER BY c1.co_tipDoc, c1.tx_ubi";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT c1.co_tipDoc, c1.co_cta, c1.tx_codCta, c1.tx_desLar, c1.tx_ubi";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT " + intTipDoc + " AS co_tipDoc, a2.co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_ubi";
                    strSQL+=" FROM tbm_plaCta AS a2";
                    strSQL+=" WHERE";
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_cta=" + codigoCuenta + "";
                    strSQL+=" UNION ";
                    strSQL+=" SELECT b1.co_tipDoc, b2.co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_ubi";
                    strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                    strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                    strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND b1.co_tipDoc IN (" + intTipDoc + ")";
                    strSQL+=" ) AS c1";
                    strSQL+=" ORDER BY c1.co_tipDoc, c1.tx_ubi";
                }


                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecCta.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_CTA_COD_DOC,rst.getString("co_tipDoc"));
                    vecReg.add(INT_CTA_COD_CTA,rst.getString("co_cta"));
                    vecReg.add(INT_CTA_NUM_CTA,rst.getString("tx_codCta"));
                    vecReg.add(INT_CTA_NOM_CTA,rst.getString("tx_desLar"));
                    vecReg.add(INT_CTA_UBI_CTA,rst.getString("tx_ubi"));
                    vecCta.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
     * Esta función obtiene la forma en que el asiento de diario ha sido generado.
     * @return Retorna uno de los siguientes valores:
     * <UL>
     * <LI>0=Diario todavóa no ha sido generado.
     * <LI>1=Diario generado por el sistema.
     * <LI>2=Diario generado por el usuario.
     * </UL>
     */
    public byte getGeneracionDiario()
    {
        return bytDiaGen;
    }
    
    /**
     * Esta función establece la forma en que el asiento de diario ha sido generado.
     * @param forma La forma en que el asiento de diario ha sido generado. 
     * <BR>Puede recibir uno de los siguientes valores:
     * <UL>
     * <LI>0=Diario todavóa no ha sido generado.
     * <LI>1=Diario generado por el sistema.
     * <LI>2=Diario generado por el usuario.
     * </UL>
     */
    public void setGeneracionDiario(byte forma)
    {
        bytDiaGen=forma;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que seró utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            else
            {
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_placta as a1";
                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface grófica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estó ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podróa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaróa informado en todo
     * momento de lo que ocurre. Si se desea hacer ósto es necesario utilizar ósta clase
     * ya que si no sólo se apreciaróa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        private int intCodEmp, intLoc, intTipDoc, intCodDia;
        private int intIndFun;

        public ZafThreadGUI()
        {
            
        }

        public ZafThreadGUI(int empresa, int local, int tipoDocumento, int diario)
        {
            intCodEmp=empresa;
            intLoc=local;
            intTipDoc=tipoDocumento;
            intCodDia=diario;
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case -1:
                    while (true)
                    {
                        try
                        {
                            sleep(50);
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println("Excepción: " + e.toString());
                        }
                        if (isObjZafAsiDiaCre())
                        {
                            configurarFrm();
                            break;
                        }
                    }
                    break;
                case 0: //consultarDiario.
                    //Permitir de manera predeterminada la operación.
                    blnCanOpe=false;
                    //Generar evento "beforeConsultar()".
                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON);
                    //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
                    if (!blnCanOpe)
                    {
                        if (consultarCab(intCodEmp, intLoc, intTipDoc, intCodDia))
                        {
                            if (cargarDetReg(intCodEmp, intLoc, intTipDoc, intCodDia))
                            {
                                obtieneDatosDetalleDiario(intCodEmp, intLoc, intTipDoc, intCodDia);
                                //Establecer el foco en el JTable sólo cuando haya datos.
                                if (tblDat.getRowCount()>0)
                                {
                                    tblDat.setRowSelectionInterval(0, 0);
                                    tblDat.requestFocus();
                                }
                                //Generar evento "afterConsultar()".
                                fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_CON);
                            }
                        }
                    }
                    break;
                case 1: //consultarDiarioCompleto.
                    //Permitir de manera predeterminada la operación.
                    blnCanOpe=false;
                    //Generar evento "beforeConsultar()".
                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_CON);
                    //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
                    if (!blnCanOpe)
                    {
                        if (consultarCabCom(intCodEmp, intLoc, intTipDoc, intCodDia))
                        {
                            if (cargarDetReg(intCodEmp, intLoc, intTipDoc, intCodDia))
                            {
                                obtieneDatosDetalleDiario(intCodEmp, intLoc, intTipDoc, intCodDia);
                                //Establecer el foco en el JTable sólo cuando haya datos.
                                if (tblDat.getRowCount()>0)
                                {
                                    tblDat.setRowSelectionInterval(0, 0);
                                    tblDat.requestFocus();
                                }
                                //Generar evento "afterConsultar()".
                                fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_CON);
                            }
                        }
                    }
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mós espacio.
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
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Nómero de la cuenta";
                    break;
                case INT_TBL_DAT_BUT_CTA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_DEB:
                    strMsg="Debe";
                    break;
                case INT_TBL_DAT_HAB:
                    strMsg="Haber";
                    break;
                case INT_TBL_DAT_REF:
                    strMsg="Referencia";
                    break;
                case INT_TBL_DAT_EST_CON:
                    strMsg="Estado de Conciliación Bancaria";
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
        public void tableChanged(javax.swing.event.TableModelEvent e)   {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularDif();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_DEB) || (tblDat.getEditingColumn()==INT_TBL_DAT_HAB) )
                        calcularDif();
                    break;
            }
        }
    }


    /*Ingrid Lino*/
    public boolean generarDiario(String codigoTipoDocumento, String codigoCuenta, String ubicacionCuenta, String valor, int retFte, String codCtaRetFte)
    {
        int intRetFte=retFte;
        int intCodTipDoc, intCodCta;
        double dblValDeb=0.00, dblValHab=0.00, dblValRteFteHab=0.00;
        boolean blnRes=true;
        double dblRteFteApl;
                
        try
        {
            intCodTipDoc=Integer.parseInt(codigoTipoDocumento);
            intCodCta=Integer.parseInt(codigoCuenta);            
            //dblRteFteApl=(intRetFte/100);
            
            
            switch (intRetFte)
            {
                case 1:
                    dblRteFteApl=0.01;
                    dblValDeb=Double.parseDouble(valor);
                    dblValRteFteHab=Double.parseDouble(valor)*(dblRteFteApl);
                    dblValHab=dblValDeb-dblValRteFteHab;  
                    break;
            }        
                        
            blnRes=generarDiario(intCodTipDoc, intCodCta, ubicacionCuenta, dblValDeb, dblValHab, dblValRteFteHab, codCtaRetFte);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /*Ingrid Lino*/
    public boolean generarDiario(int codigoTipoDocumento, int codigoCuenta, String ubicacionCuenta, double valorDebe, double valorHaber, double valorRteFte, String codCtaRetFte)
    {
        boolean blnRes=true;
        BigDecimal bdeMonRte;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        if (ubicacionCuenta.equals("D"))
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'D' AS tx_natCta";
                            strSQL+=" FROM tbm_plaCta AS b1";
                            strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_cta=" + codigoCuenta;
                            strSQL+=" UNION ALL";
                            strSQL+=" SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta";
                            strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                            strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                            strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND b1.co_tipDoc=" + codigoTipoDocumento;
                        }
                        else
                        {
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                            strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                            strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                            strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                            strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                            strSQL+=" UNION ALL";
                            strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'H' AS tx_natCta";
                            strSQL+=" FROM tbm_plaCta AS b1";
                            strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+=" AND b1.co_cta=" + codigoCuenta;
                        }
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));


                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,""+valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else
                            {                            
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,""+valorHaber);
                            }

                            
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
                            vecDat.add(vecReg);
                        }
                        
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
                            strSQL="";
                            strSQL+="SELECT distinct b1.co_ctahab AS co_cta, b2.tx_codCta, b2.tx_deslar, 'D' AS tx_natcta";
                            strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2 WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                            strSQL+=" AND b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" AND b2.co_cta=" + codCtaRetFte + "";
                            rst=stm.executeQuery(strSQL);
                             
                             //vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));

                            vecReg.add(INT_TBL_DAT_DEB,null);
                            vecReg.add(INT_TBL_DAT_HAB,""+valorRteFte);

                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
                            vecDat.add(vecReg);
                        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
                        
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        bdeMonRte=new BigDecimal(valorRteFte);
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab.add(bdeMonRte))),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    objTblMod.setValueAt("" + valorRteFte, 2, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);

                    bdeMonRte=new BigDecimal(valorRteFte);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab.add(bdeMonRte))),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
    
    private void setPuntosCta(){
        strAux=objTblCelEdiTxtVcoCta.getText();
        String strCodCtaOri=strAux;
        String strCodCtaDes="";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta=strCodCtaOri.length();
        int intLonCodCtaMen=intLonCodCta-1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig=intLonCodCta-2;
        if (strCodCtaOri.length()<=1)
            return;
        else{
//            if(  (strCodCtaOri.length() % 2) != 0 ){
                chrCtaOri=strCodCtaOri.charAt(1);
                if(chrCtaOri!='.'){
                    for (int i=0; i < strCodCtaOri.length(); i++){
                        if(i==0){
                            strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            strCodCtaDes=strCodCtaDes+".";
                        }
                        else{
                            if(  (strCodCtaOri.length() % 2) == 0 ){
                                if(((i % 2)==0)&&(i<intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                            else{
                                if(((i % 2)==0)&&(i!=intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }            
                                    
                        }
                    }
                    objTblCelEdiTxtVcoCta.setText(strCodCtaDes);
                }
//            }

            
            
 
        }
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podróa utilizar
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
    
    /**
     * Esta función obtiene la fecha del diario.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return La fecha del diario.
     */
    private java.util.Date getFecDia(java.sql.Connection conexion, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        java.util.Date datFecAux=null;
        boolean blnRes=true;
        try
        {
            con=conexion;
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.//
                strSQL="";
                strSQL+="SELECT a1.fe_dia";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                System.out.println("** getFecDia:" + strSQL);
                rst=stm.executeQuery(strSQL); 
                if (rst.next())
                {
                    datFecAux=rst.getDate("fe_dia");
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
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
        return datFecAux;
    }



    /**
     * Esta función obtiene el estado de conciliación del diario.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return "S" si el documento ya tiene confirmación "N" si no tiene.
     */
    private String getEstadoConfirmacionBancariaDocumento(java.sql.Connection conexion, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        String strEstCnfBan="";
        try{
            if (conexion!=null){
                stm=conexion.createStatement();
                //Armar la sentencia SQL.//
                strSQL="";
                strSQL+="SELECT a1.st_tieConBan";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strEstCnfBan=rst.getObject("st_tieConBan")==null?"":rst.getString("st_tieConBan");
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strEstCnfBan;
    }



 
    /**
     * Esta función establece el código del tipo de documento que se debe utilizar en la sentencia SQL cuando el usuario no es el "Administrador".
     * Es decir:
     * <UL>
     * <LI>Si es el usuario administrador se muestran todas las cuentas.
     * <LI>Si no es el usuario administrador se muestran sólo las cuentas autorizadas para dicho usuario en ese tipo de documento.
     * </UL>
     * @param codigo El código del tipo de documento.
     */
    public void setCodigoTipoDocumento(int codigo)
    {
        intCodTipDocGlb=codigo;
    }








    
    /**
     * Esta función obtiene el código del tipo de documento que se utiliza en la sentencia SQL cuando el usuario no es el "Administrador".
     * Es decir:
     * <UL>
     * <LI>Si es el usuario administrador se muestran todas las cuentas.
     * <LI>Si no es el usuario administrador se muestran sólo las cuentas autorizadas para dicho usuario en ese tipo de documento.
     * </UL>
     * @return El código del tipo de documento.
     */
    public int getCodigoTipoDocumento()
    {
        return intCodTipDocGlb;
    }    
    
    private boolean retAniMesCie(int anio){
        boolean blnRes=true;
        arlDatAniMes.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="select a1.ne_ani, a2.ne_mes, a1.tx_tipCie";
                strSQL+=" from tbm_cabciesis as a1 left outer join tbm_detciesis as a2";
                strSQL+=" on a1.co_emp=a2.co_emp and a1.ne_ani=a2.ne_ani";
                strSQL+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and a1.ne_ani=" + anio + "";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegAniMes=new ArrayList();
                    arlRegAniMes.add(INT_ARL_ANI_CIE, "" + rst.getInt("ne_ani"));
                    arlRegAniMes.add(INT_ARL_MES_CIE, "" + rst.getInt("ne_mes"));
                    arlRegAniMes.add(INT_ARL_TIP_CIE, "" + rst.getString("tx_tipCie"));
                    arlDatAniMes.add(arlRegAniMes);
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean retAniMesCtaCie(int anio, int mes){
        boolean blnRes=true;
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        arlDatAniMesCta.clear();
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTmp!=null){
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="select a1.ne_ani, a2.ne_mes, a2.co_cta";
                strSQL+=" from tbm_cabciemencta as a1 left outer join tbm_detciemencta as a2";
                strSQL+=" on a1.co_emp=a2.co_emp and a1.ne_ani=a2.ne_ani";
                strSQL+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and a1.ne_ani=" + anio + "";
                strSQL+=" and a2.ne_mes=" + mes + "";
                rstTmp=stmTmp.executeQuery(strSQL);
                while(rstTmp.next()){
                    arlRegAniMesCta=new ArrayList();
                    arlRegAniMesCta.add(INT_ARL_ANI_CTA_CIE, "" + rstTmp.getInt("ne_ani"));
                    arlRegAniMesCta.add(INT_ARL_MES_CTA_CIE, "" + rstTmp.getInt("ne_mes"));
                    arlRegAniMesCta.add(INT_ARL_COD_CTA, "" + rstTmp.getInt("co_cta"));
                    arlDatAniMesCta.add(arlRegAniMesCta);
                }
                conTmp.close();
                conTmp=null;
                stmTmp.close();
                stmTmp=null;
                rstTmp.close();
                rstTmp=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    public int getAni(){
        return intRefAniNew;
    }
    
    public int getMes(){
        return intRefMesNew;
    }

    
        
    private boolean mayorizaCta(java.sql.Connection con, int intCodEmp, int local, int tipoDocumento, int intCodDia, java.util.Date fechaDia, java.util.Date fechaDiaAnterior, int intTip)
    {
        boolean blnRes=true;
        //--- Inicio bloque comentado el 07/Feb/2019 ---
        //Este bloque fue comentado debido a que se creo un trigger para mayorizar en linea.
        /*
        double dlbValDeb=0.00;
        double dblValHab=0.00;
        double dblValSal=0.00;
        String strEstDoc="";
        int intCodCta=0;
        String strRefMesNew="", strRefMesOld="";
        
        intRefAniNew=0;
        intRefMesNew=0;
        strRefMesNew="";
        intRefAniNew=(fechaDia.getYear() + 1900);
        intRefMesNew=(fechaDia.getMonth() + 1 );
        strRefMesNew=intRefMesNew<=9?"0"+intRefMesNew:""+intRefMesNew;
        
        intRefAniOld=0;
        intRefMesOld=0;
        strRefMesOld="";
        intRefAniOld=(fechaDiaAnterior.getYear() + 1900);
        intRefMesOld=(fechaDiaAnterior.getMonth() + 1 );
        strRefMesOld=intRefMesOld<=9?"0"+intRefMesOld:""+intRefMesOld;
        
        
        
        int intTipLoc=intTip;
        try{
            if(con!=null){
                stm=con.createStatement();
                    //SI LA OPERACION ES INSERCION
                    if(intTipLoc==0){
                        for(int i=0; i<objTblMod.getRowCountTrue();i++){
                            dlbValDeb=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEB), 3));
                            dblValHab=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_HAB), 3));
                            
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta+(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + intCodEmp + "";
                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            stm.executeUpdate(strSQL);
                        }                    
                    }
                    //SI LA OPERACION ES MODIFICACION
                    if(intTipLoc==1){
                        //obtieneDatosDetalleDiario(con, intCodEmp, local, tipoDocumento, intCodDia);
                        //QUITA LOS VALORES DEL DIARIO INICIAL, PARA PODER SUMAR/RESTAR LOS NUEVOS VALORES
                        for(int i=0; i<arlDatMayOnLin.size();i++){
                            dlbValDeb=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_DEB), 3));
                            dblValHab=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_HAB), 3));

                            
                            intCodCta=objUti.getIntValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_COD_CTA);
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta-(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + intCodCta + "";
                            strSQL+="       and co_per=" + intRefAniOld + "" + strRefMesOld + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + intCodEmp + "";
                            strSQL+=" and tbm_salcta.co_cta=" + intCodCta + "";
//                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                            stm.executeUpdate(strSQL);
                        }
                        //SE SUMA/RESTA LOS NUEVOS VALORES
                        for(int i=0; i<objTblMod.getRowCountTrue();i++){
                            dlbValDeb=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEB), 3));
                            dblValHab=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_HAB), 3));
                            
                            
                            
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta+(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + intCodEmp + "";
                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            stm.executeUpdate(strSQL);
                        }
                    }
                
                //SI LA OPERACION ES ANULACION, ELIMINACION
                if(intTipLoc==2){
                    //QUITA LOS VALORES DEL DIARIO INICIAL, PARA PODER SUMAR/RESTAR LOS NUEVOS VALORES
                    for(int i=0; i<arlDatMayOnLin.size();i++){
                        dlbValDeb=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_DEB), 3));
                        dblValHab=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_HAB), 3));
                        
                        
                        intCodCta=objUti.getIntValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_COD_CTA);
                        strEstDoc=objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_EST_REG)==null?"":objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_EST_REG);
                        if( ! (strEstDoc.toString().equals("I"))   ){
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta-(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + intCodCta + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + intCodEmp + "";
                            strSQL+=" and tbm_salcta.co_cta=" + objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_COD_CTA), 3) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            stm.executeUpdate(strSQL);                            
                        }
                    }
                }
               

                
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniNew + "" + strRefMesNew + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                    
                    if(j==2){
                        strSQL="";
                        strSQL+="UPDATE tbm_salCta";
                        strSQL+=" SET nd_salCta=b1.nd_salCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intRefAniNew + "" + strRefMesNew + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
                        strSQL+=" AND a2.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                        stm.executeUpdate(strSQL);
                    }
                    
                }
                                                                
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniNew + "" + strRefMesNew + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                }
                
                
                //PARA REMAYORIZAR NIVELES ANTERIORES DEL PERIODO QUE ANTES ESTABA GUARDADO EN EL DOCUMENTO.
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniOld + "" + strRefMesOld + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                    
                    if(j==2){
                        strSQL="";
                        strSQL+="UPDATE tbm_salCta";
                        strSQL+=" SET nd_salCta=b1.nd_salCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intRefAniOld + "" + strRefMesOld + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
                        strSQL+=" AND a2.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                        stm.executeUpdate(strSQL);
                    }
                    
                }
                                                                
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniOld + "" + strRefMesOld + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                }
                
                
                
                
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        */
        //--- Fin bloque comentado el 07/Feb/2019 ------
        return blnRes;
    }
    
    
    
    private boolean mayorizaCtaCieCaj(java.sql.Connection con, int intCodEmp, int local, int tipoDocumento, int intCodDia, java.util.Date fechaDia, java.util.Date fechaDiaAnterior, int intTip, double dblValCta, String ctaDeb, String ctaHab)
    {
        boolean blnRes=true;
        //--- Inicio bloque comentado el 07/Feb/2019 ---
        //Este bloque fue comentado debido a que se creo un trigger para mayorizar en linea.
        /*
        double dlbValDeb=0.00;
        double dblValHab=0.00;
        double dblValSal=0.00;
        String strEstDoc="";        
        int intCodCta=0;
        String strRefMesNew="", strRefMesOld="";
        
        //Variable para Cierre de Caja//
        String strNumCta="", strRefMes="";
        
        intRefAniNew=0;
        intRefMesNew=0;
        strRefMesNew="";
        intRefAniNew=(fechaDia.getYear() + 1900);
        intRefMesNew=(fechaDia.getMonth() + 1 );
        strRefMesNew=intRefMesNew<=9?"0"+intRefMesNew:""+intRefMesNew;
        
        intRefAniOld=0;
        intRefMesOld=0;
        strRefMesOld="";
        intRefAniOld=(fechaDiaAnterior.getYear() + 1900);
        intRefMesOld=(fechaDiaAnterior.getMonth() + 1 );
        strRefMesOld=intRefMesOld<=9?"0"+intRefMesOld:""+intRefMesOld;
        
        
        
        int intTipLoc=intTip;
        try{
            if(con!=null){
                stm=con.createStatement();
                    //SI LA OPERACION ES INSERCION
                    if(intTipLoc==0){
                        for(int i=0; i<objTblMod.getRowCountTrue();i++){
                            dlbValDeb=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEB), 3));
                            dblValHab=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_HAB), 3));
                            
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta+(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            stm.executeUpdate(strSQL);
                        }                    
                    }
                    //SI LA OPERACION ES MODIFICACION
                    if(intTipLoc==1){
                        //obtieneDatosDetalleDiario(con, intCodEmp, local, tipoDocumento, intCodDia);
                        //QUITA LOS VALORES DEL DIARIO INICIAL, PARA PODER SUMAR/RESTAR LOS NUEVOS VALORES
                        for(int i=0; i<arlDatMayOnLin.size();i++){
                            dlbValDeb=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_DEB), 3));
                            dblValHab=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_HAB), 3));

                            
                            intCodCta=objUti.getIntValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_COD_CTA);
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta-(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + intCodCta + "";
                            strSQL+="       and co_per=" + intRefAniOld + "" + strRefMesOld + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" and tbm_salcta.co_cta=" + intCodCta + "";
//                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                            stm.executeUpdate(strSQL);
                        }
                        //SE SUMA/RESTA LOS NUEVOS VALORES
                        for(int i=0; i<objTblMod.getRowCountTrue();i++){
                            dlbValDeb=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEB), 3));
                            dblValHab=Double.parseDouble(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_HAB), 3));
                            
                            
                            
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta+(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            stm.executeUpdate(strSQL);
                        }
                    }
                
                //SI LA OPERACION ES ANULACION, ELIMINACION
                if(intTipLoc==2){
                    //QUITA LOS VALORES DEL DIARIO INICIAL, PARA PODER SUMAR/RESTAR LOS NUEVOS VALORES
                    for(int i=0; i<arlDatMayOnLin.size();i++){
                        dlbValDeb=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_DEB), 3));
                        dblValHab=Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_MON_HAB), 3));
                        
                        
                        intCodCta=objUti.getIntValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_COD_CTA);
                        strEstDoc=objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_EST_REG)==null?"":objUti.getStringValueAt(arlDatMayOnLin, i, INT_ARL_MAY_LIN_EST_REG);
                        if( ! (strEstDoc.toString().equals("I"))   ){
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta-(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            strSQL+="       and co_cta=" + intCodCta + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                            stm.executeUpdate(strSQL);                            
                        }
                    }
                }
                
                //SI LA OPERACION ES ACTUALIZACION DESDE RECEPCION DE EFECTIVO///
                if(intTipLoc==3)
                {
                        for(int i=0; i<2;i++)
                        {
                            if(i==0)
                            {
                                dlbValDeb = dblValCta;
                                dblValHab = 0;
                                strNumCta = ctaDeb;
                            }
                            else
                            {
                                dlbValDeb = 0;
                                dblValHab = dblValCta;
                                strNumCta = ctaHab;
                            }
                            strSQL="";
                            strSQL+="update tbm_salcta";
                            strSQL+=" set nd_salcta=x.saldo from (";
                            strSQL+=" 	select co_emp, co_cta, co_per,";
                            strSQL+=" 	case when nd_salcta is null then " + (dlbValDeb-dblValHab) + "";
                            strSQL+=" 	else";
                            strSQL+=" 	nd_salcta+(" + (dlbValDeb-dblValHab) + ") end as saldo";
                            strSQL+=" 	from tbm_salcta";
                            strSQL+=" 	where co_emp=" + intCodEmp + "";
                            ///strSQL+="       and co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+="       and co_cta=" + strNumCta + "";
                            strSQL+="       and co_per=" + intRefAniNew + "" + strRefMes + "";
                            strSQL+=" ) as x";
                            strSQL+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                            strSQL+=" and tbm_salcta.co_per=x.co_per";
                            strSQL+=" and tbm_salcta.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            ///strSQL+=" and tbm_salcta.co_cta=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA) + "";
                            strSQL+=" and tbm_salcta.co_cta=" + strNumCta + "";
                            strSQL+=" and tbm_salcta.co_per=" + intRefAniNew + "" + strRefMes + "";
                            stm.executeUpdate(strSQL);
                        }
                }
               

                
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniNew + "" + strRefMesNew + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                    
                    if(j==2){
                        strSQL="";
                        strSQL+="UPDATE tbm_salCta";
                        strSQL+=" SET nd_salCta=b1.nd_salCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intRefAniNew + "" + strRefMesNew + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
                        strSQL+=" AND a2.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                        stm.executeUpdate(strSQL);
                    }
                    
                }
                                                                
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniNew + "" + strRefMesNew + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniNew + "" + strRefMesNew + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                }
                
                
                //PARA REMAYORIZAR NIVELES ANTERIORES DEL PERIODO QUE ANTES ESTABA GUARDADO EN EL DOCUMENTO.
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniOld + "" + strRefMesOld + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                    
                    if(j==2){
                        strSQL="";
                        strSQL+="UPDATE tbm_salCta";
                        strSQL+=" SET nd_salCta=b1.nd_salCta";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intRefAniOld + "" + strRefMesOld + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
                        strSQL+=" AND a2.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                        strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                        stm.executeUpdate(strSQL);
                    }
                    
                }
                                                                
                for (int j=6; j>1; j--){
                    strSQL="";
                    strSQL+="UPDATE tbm_salCta";
                    strSQL+=" SET nd_salCta=b1.nd_salCta";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intRefAniOld + "" + strRefMesOld + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.ne_niv=" + j;
                    strSQL+=" AND a2.co_per=" + intRefAniOld + "" + strRefMesOld + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stm.executeUpdate(strSQL);
                }
                
                
                
                
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        */
        //--- Fin bloque comentado el 07/Feb/2019 ------
        return blnRes;
    }
    

    private boolean obtieneDatosDetalleDiario(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        arlDatMayOnLin.clear();
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a2.tx_codCta, a2.tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a3.st_reg";
                strSQL+=" , a1.st_conBan FROM tbm_plaCta AS a2 inner join (tbm_detDia AS a1";
                strSQL+=" inner join tbm_cabdia as a3";
                strSQL+=" on a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_dia=a3.co_dia";
                strSQL+=" )";
                strSQL+=" on a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg";
                rst=stm.executeQuery(strSQL);
                while (rst.next()){
                        //CAPTURA LOS DATOS INICIALES DE LOS SALDOS DEL DIARIO A MODIFICAR(ACTUALIZACION, ELIMINACION, ANULACION)
                        arlRegMayOnLin=new ArrayList();
                        arlRegMayOnLin.add(INT_ARL_MAY_LIN_COD_EMP, "" + intCodEmp);
                        arlRegMayOnLin.add(INT_ARL_MAY_LIN_COD_CTA, "" + rst.getString("co_cta"));
                        arlRegMayOnLin.add(INT_ARL_MAY_LIN_MON_DEB, "" + rst.getDouble("nd_monDeb"));
                        arlRegMayOnLin.add(INT_ARL_MAY_LIN_MON_HAB, "" + rst.getDouble("nd_monHab"));
                        arlRegMayOnLin.add(INT_ARL_MAY_LIN_EST_REG, "" + rst.getString("st_reg"));
                        arlRegMayOnLin.add(INT_ARL_MAY_LIN_EST_CON_BAN, "" + rst.getString("st_conBan"));

                        arlDatMayOnLin.add(arlRegMayOnLin);
                }
                rst.close();
                stm.close();
                rst=null;
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
    
    
    private boolean obtieneDatosDetalleDiario(int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        arlDatMayOnLin.clear();
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        try{
            conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conTmp!=null){
                stmTmp=conTmp.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a2.tx_codCta, a2.tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a3.st_reg";
                strSQL+=" , a1.st_conBan FROM tbm_plaCta AS a2 inner join (tbm_detDia AS a1";
                strSQL+=" inner join tbm_cabdia as a3";
                strSQL+=" on a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_dia=a3.co_dia";
                strSQL+=" )";
                strSQL+=" on a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg";
                rstTmp=stmTmp.executeQuery(strSQL);
                while (rstTmp.next()){
                    //CAPTURA LOS DATOS INICIALES DE LOS SALDOS DEL DIARIO A MODIFICAR(ACTUALIZACION, ELIMINACION, ANULACION)
                    arlRegMayOnLin=new ArrayList();
                    arlRegMayOnLin.add(INT_ARL_MAY_LIN_COD_EMP, "" + intCodEmp);
                    arlRegMayOnLin.add(INT_ARL_MAY_LIN_COD_CTA, "" + rstTmp.getString("co_cta"));
                    arlRegMayOnLin.add(INT_ARL_MAY_LIN_MON_DEB, "" + rstTmp.getDouble("nd_monDeb"));
                    arlRegMayOnLin.add(INT_ARL_MAY_LIN_MON_HAB, "" + rstTmp.getDouble("nd_monHab"));
                    arlRegMayOnLin.add(INT_ARL_MAY_LIN_EST_REG, "" + rstTmp.getString("st_reg"));
                    arlRegMayOnLin.add(INT_ARL_MAY_LIN_EST_CON_BAN, "" + rstTmp.getString("st_conBan"));
                    arlDatMayOnLin.add(arlRegMayOnLin);
                }
                conTmp.close();
                conTmp=null;
                rstTmp.close();
                stmTmp.close();
                rstTmp=null;
                stmTmp=null;
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
     * Esta función cancela el evento generado sobre la clase "ZafAsiDia_v01".
     */
    public void cancelarOperacion()
    {
        blnCanOpe=true;
    }
    
    /**
     * Esta función adiciona el listener que controlaró los eventos que se generen sobre la clase "ZafAsiDia_v01".
     * @param listener El objeto que implementa los mótodos de la interface "ZafAsiDiaListener".
     */
    public void addAsiDiaListener(ZafAsiDiaListener listener)
    {
        objEveLisLis.add(ZafAsiDiaListener.class, listener);
    }

    /**
     * Esta función remueve el listener que controlaró los eventos que se generen sobre la clase "ZafAsiDia_v01".
     * @param listener El objeto que implementa los mótodos de la interface "ZafAsiDiaListener".
     */
    public void removeAsiDiaListener(ZafAsiDiaListener listener)
    {
        objEveLisLis.remove(ZafAsiDiaListener.class, listener);
    }
    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafAsiDiaEvent".
     * @param metodo El mótodo que seró invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Mótodo</I></TD></TR>
     *     <TR><TD>INT_BEF_INS</TD><TD>Invoca al mótodo "beforeInsertar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_INS</TD><TD>Invoca al mótodo "afterInsertar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_CON</TD><TD>Invoca al mótodo "beforeConsultar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_CON</TD><TD>Invoca al mótodo "afterConsultar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_MOD</TD><TD>Invoca al mótodo "beforeModificar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_MOD</TD><TD>Invoca al mótodo "afterModificar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_ELI</TD><TD>Invoca al mótodo "beforeEliminar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_ELI</TD><TD>Invoca al mótodo "afterEliminar" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_ANU</TD><TD>Invoca al mótodo "beforeAnular" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_ANU</TD><TD>Invoca al mótodo "afterAnular" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireAsiDiaListener(ZafAsiDiaEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafAsiDiaListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_INS:
                        ((ZafAsiDiaListener)obj[i+1]).beforeInsertar(evt);
                        break;
                    case INT_AFT_INS:
                        ((ZafAsiDiaListener)obj[i+1]).afterInsertar(evt);
                        break;
                    case INT_BEF_CON:
                        ((ZafAsiDiaListener)obj[i+1]).beforeConsultar(evt);
                        break;
                    case INT_AFT_CON:
                        ((ZafAsiDiaListener)obj[i+1]).afterConsultar(evt);
                        break;
                    case INT_BEF_MOD:
                        ((ZafAsiDiaListener)obj[i+1]).beforeModificar(evt);
                        break;
                    case INT_AFT_MOD:
                        ((ZafAsiDiaListener)obj[i+1]).afterModificar(evt);
                        break;
                    case INT_BEF_ELI:
                        ((ZafAsiDiaListener)obj[i+1]).beforeEliminar(evt);
                        break;
                    case INT_AFT_ELI:
                        ((ZafAsiDiaListener)obj[i+1]).afterEliminar(evt);
                        break;
                    case INT_BEF_ANU:
                        ((ZafAsiDiaListener)obj[i+1]).beforeAnular(evt);
                        break;
                    case INT_AFT_ANU:
                        ((ZafAsiDiaListener)obj[i+1]).afterAnular(evt);
                        break;
                    case INT_BEF_EDI_CEL:
                        ((ZafAsiDiaListener)obj[i+1]).beforeEditarCelda(evt);
                        break;
                    case INT_AFT_EDI_CEL:
                        ((ZafAsiDiaListener)obj[i+1]).afterEditarCelda(evt);
                        break;
                    case INT_BEF_CON_CTA:
                        ((ZafAsiDiaListener)obj[i+1]).beforeConsultarCuentas(evt);
                        break;
                    case INT_AFT_CON_CTA:
                        ((ZafAsiDiaListener)obj[i+1]).afterConsultarCuentas(evt);
                        break;
                }
            }
        }
    }
 
    
    
    
    
    
      
     /**
     * Esta función genera el "Asiento de diario" de transferencias de inventario desde la cotizacion de venta de acuerdo a los datos
     * que se encuentran en las columnas especificadas en un JTable.
     * @param tabla El JTable del que se obtendran los datos para generar el diario.
     * @param columnaBodegaOrigen La columna donde se encuentra el código de la bodega origen.
     * @param columnaBodegaDestino La columna donde se encuentra el código de la bodega destino.
     * @param columnaValores La columna de la que se obtendran los valores que se mostrarón en la columna del debe/haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */  
    public boolean generarDiarioTransferencias_cotizacion(javax.swing.JTable tabla, int columnaBodegaOrigen, int columnaBodegaDestino, int columnaValores, java.sql.Connection conn_remota)
    {
        int i, j, k;
        boolean blnExiCod=false;
        Object objAux=null;
        String strAux1;
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                case 1: //Diario generado por el Sistema.
                    if (getDatBodBasDat_cotizacion(tabla, columnaBodegaOrigen, columnaBodegaDestino, conn_remota))
                    {
                        //Limpiar vector de datos.
                        vecDat.clear();
                        objTblModAux=(ZafTblMod)tabla.getModel();
                        for (i=0; i<objTblModAux.getRowCountTrue(); i++)
                        {
                            //Buscar la cuenta de debe y la cuenta de haber.
                            for (k=0; k<2; k++)
                            {
                                switch (k)
                                {
                                    case 0:
                                        objAux=objTblModAux.getValueAt(i,columnaBodegaOrigen);
                                        break;
                                    case 1:
                                        objAux=objTblModAux.getValueAt(i,columnaBodegaDestino);
                                        break;   
                                }
                                strAux1=(objAux==null?"":objAux.toString());
                                //Obtener el indice del "Tipo de documento" del Vector.
                                blnExiCod=false;
                                for (j=0; j<vecCta.size();j++)
                                {
                                    if ( strAux1.equals(objUti.getStringValueAt(vecCta,j,INT_CTA_COD_DOC)) )
                                    {
                                        blnExiCod=true;
                                        break;
                                    }
                                }
                                if (blnExiCod)
                                {
                                    objAux=vecCta.get(j);
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_COD_CTA,((Vector)objAux).get(INT_CTA_COD_CTA));
                                    vecReg.add(INT_TBL_DAT_NUM_CTA,((Vector)objAux).get(INT_CTA_NUM_CTA));
                                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                    vecReg.add(INT_TBL_DAT_NOM_CTA,((Vector)objAux).get(INT_CTA_NOM_CTA));
                                    if (k==0)
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,null);
                                        vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                    }
                                    else
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                        vecReg.add(INT_TBL_DAT_HAB,null);
                                    }
                                    vecReg.add(INT_TBL_DAT_REF,null);
                                    vecReg.add(INT_TBL_DAT_EST_CON,null);
                                    vecDat.add(vecReg);
                                }
                                else
                                {
                                    vecReg=new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN,"");
                                    vecReg.add(INT_TBL_DAT_COD_CTA,null);
                                    vecReg.add(INT_TBL_DAT_NUM_CTA,null);
                                    vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                                    vecReg.add(INT_TBL_DAT_NOM_CTA,null);
                                    if (k==0)
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,null);
                                        vecReg.add(INT_TBL_DAT_HAB,objTblModAux.getValueAt(i,columnaValores));
                                    }
                                    else
                                    {
                                        vecReg.add(INT_TBL_DAT_DEB,objTblModAux.getValueAt(i,columnaValores));
                                        vecReg.add(INT_TBL_DAT_HAB,null);
                                    }
                                    vecReg.add(INT_TBL_DAT_REF,null);
                                    vecReg.add(INT_TBL_DAT_EST_CON,null);
                                    vecDat.add(vecReg);
                                }
                            }
                        }
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        calcularDif();
                    }
                    break;
                case 2: //Diario generado por el usuario.
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
     * Esta función obtiene los códigos de las bodegas que deben aparecer en el asiento de diario.
     * Los datos de las bodegas quedan almacenados en un Vector. Al generar el diario se buscaró
     * la cuenta en dicho Vector lo cual evitaró consultar constantemente la base de datos. Sólo
     * si la bodega no existe en el Vector se consultaró la base de datos.
     * @param tblPro El JTable del que se obtendran los datos para generar el diario.
     * @param intColBodOrg La columna donde se encuentra el código de la bodega origen.
     * @param intColBodDes La columna donde se encuentra el código de la bodega destino.
     * @return true: Si el mótodo se ejecutó con óxito.
     * <BR>false: En el caso contrario.
     */
    private boolean getDatBodBasDat_cotizacion(javax.swing.JTable tblPro, int intColBodOrg, int intColBodDes, java.sql.Connection con_remota)
    {
        int i;
        String strCodBod;
        boolean blnRes=true;
        try
        {
            //Obtener datos adicionales de la "Bodega" sólo si hay códigos a buscar.
            strCodBod=getCodBodPro(tblPro, intColBodOrg, intColBodDes);
            if (!strCodBod.equals(""))
            {
                //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con_remota!=null)
                {
                    stm=con_remota.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_bod, a2.co_cta, a2.tx_codCta, a2.tx_desLar, Null AS tx_ubi";
                    strSQL+=" FROM tbm_bod AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_ctaExi=a2.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND a1.co_bod IN (" + strCodBod + ")";
                    strSQL+=" ORDER BY a1.co_bod";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecCta.clear();
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_CTA_COD_DOC,rst.getString("co_bod"));
                        vecReg.add(INT_CTA_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_CTA_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_CTA_NOM_CTA,rst.getString("tx_desLar"));
                        vecReg.add(INT_CTA_UBI_CTA,rst.getString("tx_ubi"));
                        vecCta.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                   // con.close();
                    rst=null;
                    stm=null;
                   // con=null;
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
     * Esta función determina si el objeto "ZafAsiDia_v01" ya fue creado.
     * Es decir que esta función devuelve true sólo cuando "this" es diferente a null.
     * @return true: Si el objeto "ZafAsiDia_v01" ya fue creado.
     * <BR>false: En el caso contrario.
     */
    public synchronized boolean isObjZafAsiDiaCre()
    {
        if (this==null)
            return false;
        else
            return true;
    }
    
    
   /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     ** @param codigoDocumento El código del documento.
     * @param numeroDiario El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param fechaDiario La fecha del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String codigoDocumento, String numeroDiario, java.util.Date fechaDiario)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".

        
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;
            intRefMesNew=0;
            intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );
            

            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el aóo<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho aóo todavóa no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar estó cerrado. <BR>Estó tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Estó tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            
            
            if (isCamVal()){
                if (insertarCab(conexion, empresa, local, tipoDocumento, codigoDocumento, numeroDiario, fechaDiario)){
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia)){
                        System.out.println("getCamTblUltDoc: " + getCamTblUltDoc());
                        if(  (getCamTblUltDoc().equals("L"))  ){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                        }
                        else if(getCamTblUltDoc().equals("G")) {//para tbm_cabGrpTipDOc - cheques
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }

                        }
                        else if(  (getCamTblUltDoc().equals(""))   ){
                            System.out.println("getCamTblUltDoc: " + getCamTblUltDoc());
                            if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento)){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                            }
                        }
                        else{
                            if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento)){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                            }
                        }
                            

                    }
                }
            }
        }
        return false;
    }
      
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <BR>Si la cuenta es de debe. O sea, ubicacionCuenta=D:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuentaDebe".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuentaHaber".
     * @param codigoTipoDocumento El código del "Tipo de documento". La cadena debe contener un "int".
     * @param codigoCuentaDebe El código de la "Cuenta". La cadena debe contener un "int".
     * @param valorDebe El valor para la cuenta del debe. La cadena debe contener un "String".
     * @param codigoCuentaHaber El código de la "Cuenta". La cadena debe contener un "int".
     * @param valorHaber El valor para la cuenta del haber. La cadena debe contener un "String".
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int intCodigoEmpresa, int intCodigoLocal, int codigoTipoDocumento, int codigoCuentaDebe,  String valorDebe, int codigoCuentaHaber, String valorHaber)
    {
        int intCodTipDoc, intCodCta;
        double dblValDeb, dblValHab;
        boolean blnRes=true;
        try
        {
            intCodTipDoc=codigoTipoDocumento;
            dblValDeb=objUti.parseDouble(valorDebe);
            dblValHab=objUti.parseDouble(valorHaber);
            blnRes=generarDiario(intCodigoEmpresa, intCodigoLocal, intCodTipDoc, codigoCuentaDebe, dblValDeb, codigoCuentaHaber, dblValHab);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuentaDebe".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuentaHaber".
     * </UL>
     * @param codigoTipoDocumento El cïdigo del "Tipo de documento".
     * @param codigoCuentaDebe El código de la "Cuenta Deudora". Por lo general tbr_ctatipdocusr.
     * @param valorDebe El valor para la cuenta del debe.
     * @param codigoCuentaHaber El código de la "Cuenta Acreedora". Por lo general una cuenta de Banco.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int intCodigoEmpresa, int intCodigoLocal, int codigoTipoDocumento, int codigoCuentaDebe, double valorDebe, int codigoCuentaHaber, double valorHaber){
        boolean blnRes=true;
        try{
            switch (bytDiaGen){
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + intCodigoEmpresa;
                        strSQL+=" AND b1.co_cta=" + codigoCuentaDebe;
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'H' AS tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + intCodigoEmpresa;
                        strSQL+=" AND b1.co_cta=" + codigoCuentaHaber;
                        System.out.println("A: generarDiario: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D")){
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;                        
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuentaDebe".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuentaHaber".
     * </UL>
     * @param intCodigoEmpresaGrupo El código del "Grupo".
     * @param codigoCuentaDebe El código de la "Cuenta Deudora".
     * @param valorDebe El valor para la cuenta del debe.
     * @param intCodigoEmpresa El código de la "Empresa-Importador".
     * @param codigoCuentaHaber El código de la "Cuenta Acreedora". Por lo general una cuenta de Banco.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int intCodigoEmpresaGrupo, int codigoCuentaDebe, double valorDebe, int intCodigoEmpresa, int codigoCuentaHaber, double valorHaber){
        boolean blnRes=true;
        try{
            switch (bytDiaGen){
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + intCodigoEmpresaGrupo;
                        strSQL+=" AND b1.co_cta=" + codigoCuentaDebe;
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'H' AS tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + intCodigoEmpresa;
                        strSQL+=" AND b1.co_cta=" + codigoCuentaHaber;
                        System.out.println("generarDiario: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D")){
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);

                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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






    public void setCamTblUltDoc(String strTblActUltDoc){
        this.strTblActUltDoc=strTblActUltDoc;
    }
    
    public String getCamTblUltDoc(){
         return strTblActUltDoc;
    }
    
    public void setCodTipDocGrp(String strCodGrpTipDoc){
        this.strCodGrpTipDoc=strCodGrpTipDoc;
    }
    
    public String getCodTipDocGrp(){
         return strCodGrpTipDoc;
    }    
    
    //para obtener el año de la fecha actual del servidor de base de datos
    public int anioActualBaseDatos(java.sql.Connection con){
        int intFecSis[];
        int intAniActBasDat=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="" + objParSis.getQueryFechaHoraBaseDatos() + " AS fecSis";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    dtpFecSis.setText(rst.getString("fecSis"));
                }
                intFecSis=dtpFecSis.getFecha(dtpFecSis.getText());
                intAniActBasDat=intFecSis[2];
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intAniActBasDat;
    }
    
    private int canChangeDate(Connection con, int tipoDocumento){
        int intTipModFec=0;
        Statement stmFecPer;
        ResultSet rstFecPer;
        try{
            if(con!=null){
                stmFecPer=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + tipoDocumento + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    System.out.println("canChangeDate: " + strSQL);
                    rstFecPer=stmFecPer.executeQuery(strSQL);
                    if(rstFecPer.next()){
                        intTipModFec=rstFecPer.getInt("ne_tipresmodfecdoc");
                    } 
                    else{
                        intTipModFec=1; /*JoseMario 19/Jul/2016*/
                    }
                    stmFecPer.close();
                    stmFecPer=null;
                    rstFecPer.close();
                    rstFecPer=null;
                }
            }
        }
        
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
        
    }
    
    
    private boolean fechaPermitida(Connection con, int tipoDocumento, char modo, java.util.Date datFecDocRef, java.util.Date datFecDB){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        try{
            intTipCamFec=canChangeDate(con, tipoDocumento);
            if(con!=null){
                switch(intTipCamFec){
                    case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                        if(objParSis.getCodigoUsuario()!=1){
                            if(modo=='n'){//insertar
                                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                                
                                strFecGua=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
                                String strMsj="";
                                strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                                strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                                strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                                //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");

                                switch (mostrarMsgCon(strMsj)){
                                    case 0: //YES_OPTION
                                        //System.out.println("POR YES");
                                        return true;
                                    case 1: //NO_OPTION
                                        //System.out.println("POR NO");
                                        return false;
                                    case 2: //CANCEL_OPTION
                                        //System.out.println("POR CANCEL");
                                        return false;
                                }
                                datFecAux=null;
                            }
                            else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                String strMsj="";
                                strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                                strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                                strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                                //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                                switch (mostrarMsgCon(strMsj)){
                                    case 0: //YES_OPTION
                                        return true;
                                    case 1: //NO_OPTION
                                        return false;
                                    case 2: //CANCEL_OPTION
                                        return false;
                                }

                            }
                        }
                        break;
                    case 1://no puede cambiarla para nada
                        if(modo=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            strFecGua=(objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy"));
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                                strFecGua=(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                //dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        
                        else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                            strFecDocTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                            strFecGua=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( datFecDB ) != 0 ){
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }  
                        }

                        break;
                    case 2://la fecha puede ser menor o igual a la q se presenta
                        if(modo=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            
                            
                            strFecGua=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                                strFecGua=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                            strFecDocTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecGua=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( datFecDB ) > 0 ){
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }  
                        }
                        break;
                    case 3://la fecha puede ser mayor o igual a la q se presenta
                        if(modo=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            
                            strFecGua=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                            
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                                strFecGua=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                            strFecDocTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecGua=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( datFecDB ) < 0 ){
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }  
                        }
                        break;
                    case 4:
                        strFecGua=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                        break;
                    default:
                        break;
                }
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return true;
    }
    
    
    /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta función inserta la cabecera del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param strCodDia El código del diario. Es el código que maneja el sistema.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el código del diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el código del diario recibido.
     * </UL>
     * @param strNumDia El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param datFecDia La fecha del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, String strCodDia, String strNumDia, java.util.Date datFecDia, int intCodMnuRef)
    {
        int intCodUsr, intNumDia;        
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                stm=con.createStatement();
                if (strCodDia==null)
                {
                    //Obtener el código del óltimo registro.
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_dia)";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intCodDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intCodDia==-1)
                        return false;
                    intCodDia++;
                }
                else
                {
                    intCodDia=Integer.parseInt(strCodDia);
                }
                if (strNumDia==null)
                {
                    //Obtener el nómero de diario.
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intNumDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumDia==-1)
                        return false;
                    intNumDia++;
                    strNumDia="" + intNumDia;
                }
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                
                if( ! (fechaPermitida(con, intCodEmp, intCodLoc, intTipDoc, 'n', datFecDia, datFecDia, intCodMnuRef))   )
                    blnRes=false;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia, tx_numDia, fe_dia, tx_glo";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL+=" VALUES (";
                strSQL+="" + intCodEmp;
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intTipDoc;
                strSQL+=", " + intCodDia;
                strSQL+=", " + objUti.codificar(strNumDia);
                //Si la fecha recibida es null asigno la fecha del servidor.
                if (datFecDia==null)
                    strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaBaseDatos()) + "'";
                else{
                    //strSQL+=", '" + objUti.formatearFecha(datFecDia, objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=", '" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                    
                }
                strSQL+=", " + objUti.codificar(txtGlo.getText());
                strSQL+=", 'A'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=")";
                System.out.println("insertarCab**: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
    
    
    
    private boolean fechaPermitida(Connection con, int codigoEmpresa, int codigoLocal, int tipoDocumento, char modo, java.util.Date datFecDocRef, java.util.Date datFecDB, int intCodMnuRef){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        try{
            intTipCamFec=canChangeDate(con, codigoEmpresa, codigoLocal, tipoDocumento, intCodMnuRef);
            if(con!=null){
                switch(intTipCamFec){
                    case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                        if(objParSis.getCodigoUsuario()!=1){
                            if(modo=='n'){//insertar
                                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                                
                                strFecGua=objUti.formatearFecha(datFecAux, "dd/MM/yyyy");
                                String strMsj="";
                                strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                                strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                                strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                                //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");

                                switch (mostrarMsgCon(strMsj)){
                                    case 0: //YES_OPTION
                                        //System.out.println("POR YES");
                                        return true;
                                    case 1: //NO_OPTION
                                        //System.out.println("POR NO");
                                        return false;
                                    case 2: //CANCEL_OPTION
                                        //System.out.println("POR CANCEL");
                                        return false;
                                }
                                datFecAux=null;
                            }
                            else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                String strMsj="";
                                strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                                strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                                strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                                //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");
                                switch (mostrarMsgCon(strMsj)){
                                    case 0: //YES_OPTION
                                        return true;
                                    case 1: //NO_OPTION
                                        return false;
                                    case 2: //CANCEL_OPTION
                                        return false;
                                }

                            }
                        }
                        break;
                    case 1://no puede cambiarla para nada
                        if(modo=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            strFecGua=(objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy"));
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                                strFecGua=(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                //dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        
                        else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                            strFecDocTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                            strFecGua=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( datFecDB ) != 0 ){
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }  
                        }

                        break;
                    case 2://la fecha puede ser menor o igual a la q se presenta
                        if(modo=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            
                            
                            strFecGua=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                                strFecGua=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                            strFecDocTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecGua=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( datFecDB ) > 0 ){
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }  
                        }
                        break;
                    case 3://la fecha puede ser mayor o igual a la q se presenta
                        if(modo=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");

                            strFecGua=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                            
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                                strFecGua=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        else if(    (modo=='x')  ||  (modo=='m')   ){//modificar
                            strFecDocTmp="";
                            strFecDocTmp=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            strFecGua=objUti.formatearFecha(datFecDocRef, "dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( datFecDB ) < 0 ){
                                strFecGua=objUti.formatearFecha(datFecDB, "dd/MM/yyyy");
                                //dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }  
                        }
                        break;
                    case 4:
                        strFecGua=objUti.formatearFecha(datFecDocRef,"dd/MM/yyyy");
                        break;
                    default:
                        break;
                }
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return true;
    }
    
    
    private int canChangeDate(Connection con, int intCodEmpRef, int intCodLocRef, int tipoDocumento, int intCodMnuRef){
        int intTipModFec=0;
        Statement stmFecPer;
        ResultSet rstFecPer;
        try{
            if(con!=null){
                stmFecPer=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + intCodEmpRef + "";
                    strSQL+=" AND co_loc=" + intCodLocRef + "";
                    strSQL+=" AND co_tipDoc=" + tipoDocumento + "";
                    strSQL+=" AND co_mnu=" + intCodMnuRef + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstFecPer=stmFecPer.executeQuery(strSQL);
                    if(rstFecPer.next()){
                        intTipModFec=rstFecPer.getInt("ne_tipresmodfecdoc");
                    }
                    stmFecPer.close();
                    stmFecPer=null;
                    rstFecPer.close();
                    rstFecPer=null;
                }
            }
        }
        
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
        
    }
    
    /**
     * Esta función actualiza el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea actualizar.
     * @param numeroDiario El nómero de diario.
     * @param fechaDiario La fecha del diario.
     * @param estadoDiario El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean actualizarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario, String numeroDiario, java.util.Date fechaDiario, String estadoDiario, int codigoMenuReferencial)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeModificar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_MOD);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            int intFunAni=0;
            int intFunMes=0;
            intTipPro=1;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 ); 
            //PARA VALIDAR LA MODIFICACION DE UN DIARIO, A TRAVES DE PARAMETROS QUE SE RECIBEN Y DE LA FUNCION getFecDia()
            intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);
            intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
            datAuxMayAnt=getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario);
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if(      ( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  )     ||    ( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  )   ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            
            
            if(    ( ! (retAniMesCie(intFunAni)))   ||   ( ! (retAniMesCie(intRefAniNew)))    )
                return false;
            
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                

                
                
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(   (intFunAni==intArlAni)){
                        if(    (intFunMes==intArlMes)){
                            mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                    
                    if( (intRefAniNew==intArlAni)  ){
                        if( (intRefMesNew==intArlMes)  ){
                            mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                    
                    
                    
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            
            if (isCamVal()){
                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario)){
                    if(obtieneDatosDetalleDiarioModificado(empresa)){
                        if( ! existenCuentasConciliadasModificadas()){
                            if( ! existenCuentaAnteriorCerrada(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                if (actualizarCab(conexion, empresa, local, tipoDocumento, codigoDiario, numeroDiario, fechaDiario, estadoDiario, codigoMenuReferencial)){
                                    if (eliminarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                        if (insertarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                            if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, datAuxMayAnt, intTipPro)){
                                                //Generar evento "afterModificar()".
                                                fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_MOD);
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            mostrarMsgInf("<HTML>Al modificar el documento se está cambiando una cuenta conciliada o su valor<BR>No puede cambiar una cuenta ni su valor si la cuenta está conciliada.<BR>Desconcilie la cuenta en el documento que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                }
            }




        }
        return false;
    }

    /*DARIO CARDENAS FUNCION PARA AJUSTES DE CENTAVOS*/
    /**
     * Esta función actualiza el asiento de diario en la base de datos.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea actualizar.
     * @param numeroDiario El nómero de diario.
     * @param fechaDiario La fecha del diario.
     * @param estadoDiario El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean actualizarDiarioAju(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario, String numeroDiario, java.util.Date fechaDiario, String estadoDiario, int codigoMenuReferencial)
    {
        //Permitir de manera predeterminada la operación.//
        blnCanOpe=false;
        //Generar evento "beforeModificar()".//
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_MOD);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".//

        
        if (!blnCanOpe)
        {
            if(conexion!=null)
            {
                int intArlAni=0;
                int intArlMes=0;
                String strArlTipCie="";
                int intFunAni=0;
                int intFunMes=0;
                intTipPro=1;
                intRefAniNew=(fechaDiario.getYear() + 1900);
                intRefMesNew=(fechaDiario.getMonth() + 1 );

                //PARA VALIDAR LA MODIFICACION DE UN DIARIO, A TRAVES DE PARAMETROS QUE SE RECIBEN Y DE LA FUNCION getFecDia()//
                intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);
                intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
                datAuxMayAnt=getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario);

                //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
                if(      ( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  )     ||    ( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  )   ){
                    mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                    return false;
                }

                if(    ( ! (retAniMesCie(intFunAni)))   ||   ( ! (retAniMesCie(intRefAniNew)))    )
                    return false;

                for (int k=0; k<arlDatAniMes.size(); k++){
                    intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                    intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                    strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));




                    if( (strArlTipCie.toString().equals("M"))  ){
                        if(   (intFunAni==intArlAni)){
                            if(    (intFunMes==intArlMes)){
                                mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                                return false;
                            }
                        }

                        if( (intRefAniNew==intArlAni)  ){
                            if( (intRefMesNew==intArlMes)  ){
                                mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                                return false;
                            }
                        }



                    }
                    else{
                        mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                        return false;
                    }
                }

                if (isCamVal()){
                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario)){
                        if(obtieneDatosDetalleDiarioModificado(empresa)){
                            if( ! existenCuentasConciliadasModificadas()){
                                if( ! existenCuentaAnteriorCerrada(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                    if (actualizarCab(conexion, empresa, local, tipoDocumento, codigoDiario, numeroDiario, fechaDiario, estadoDiario, codigoMenuReferencial)){
                                        if (eliminarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                            if (insertarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, datAuxMayAnt, intTipPro))
                                                {
                                                    //Generar evento "afterModificar()".
                                                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_MOD);
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            else{
                                mostrarMsgInf("<HTML>Al modificar el documento se está cambiando una cuenta conciliada o su valor<BR>No puede cambiar una cuenta ni su valor si la cuenta está conciliada.<BR>Desconcilie la cuenta en el documento que desea modificar y vuelva a intentarlo.</HTML>");
                                return false;
                            }


                        }

                    }
                }

            }
        }
        
        return false;
    }

    
    /**
     * Esta función actualiza la cabecera del registro. 
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @param strNumDia El nómero de diario.
     * @param datFecDia La fecha del diario.
     * @param strEstReg El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia, String strNumDia, java.util.Date datFecDia, String strEstReg, int codigoMenuReferencial)
    {
        int intCodUsr;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                
//                if( ! (fechaPermitida(con, intTipDoc, 'm', datFecDia, getFechaDiarioDB(con, intCodEmp, intCodLoc, intTipDoc, intCodDia)  ))   )
//                    blnRes=false;
    
                
                
                if( ! (fechaPermitida(con, intCodEmp, intCodLoc, intTipDoc, 'm', datFecDia, getFechaDiarioDB(con, intCodEmp, intCodLoc, intTipDoc, intCodDia), codigoMenuReferencial))   )
                    blnRes=false;
                
                
                
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabDia";
                strSQL+=" SET tx_numDia=" + objUti.codificar(strNumDia);
                //strSQL+=", fe_dia='" + objUti.formatearFecha(datFecDia, objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_dia='" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", tx_glo=" + objUti.codificar(txtGlo.getText());
                strSQL+=", st_reg='" + strEstReg + "'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + intCodUsr;
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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


    /* Dario.- Funcion para actualizar datos de la cabecera de diario sin actualizar el campo tbm_cbadia.tx_glo*/
    /**
     * Esta función actualiza la cabecera del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @param strNumDia El nómero de diario.
     * @param datFecDia La fecha del diario.
     * @param strEstReg El estado del diario.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCabCieCaj(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia, String strNumDia, java.util.Date datFecDia, String strEstReg, int codigoMenuReferencial)
    {
        int intCodUsr;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {

//                if( ! (fechaPermitida(con, intTipDoc, 'm', datFecDia, getFechaDiarioDB(con, intCodEmp, intCodLoc, intTipDoc, intCodDia)  ))   )
//                    blnRes=false;



                if( ! (fechaPermitida(con, intCodEmp, intCodLoc, intTipDoc, 'm', datFecDia, getFechaDiarioDB(con, intCodEmp, intCodLoc, intTipDoc, intCodDia), codigoMenuReferencial))   )
                    blnRes=false;



                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabDia";
                strSQL+=" SET tx_numDia=" + objUti.codificar(strNumDia);
                strSQL+=", fe_dia='" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                /////strSQL+=", tx_glo=" + objUti.codificar(txtGlo.getText());
                strSQL+=", st_reg='" + strEstReg + "'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + intCodUsr;
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
    
    
    
    private boolean obtieneDatosDetalleDiarioModificado(int codigoEmpresa){
        boolean blnRes=true;
        arlDatDetDiaMod.clear();
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if( ! objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA).toString().equals("")){
                    arlRegDetDiaMod=new ArrayList();
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_COD_EMP, "" + codigoEmpresa);
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_COD_CTA, objTblMod.getValueAt(i,INT_TBL_DAT_COD_CTA)   );
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_MON_DEB, new java.math.BigDecimal(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_DEB), 3))  );
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_MON_HAB, new java.math.BigDecimal(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_HAB), 3))  );
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_EST_REG, objTblMod.getValueAt(i,INT_TBL_DAT_EST_CON)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_EST_CON).toString()  );
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_EST_CON_BAN, "");
                    arlRegDetDiaMod.add(INT_ARL_DET_DIA_MOD_REF, objTblMod.getValueAt(i,INT_TBL_DAT_REF)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_REF).toString());
                    arlDatDetDiaMod.add(arlRegDetDiaMod);
                }
            }



            System.out.println("arlDatDetDiaMod: " + arlDatDetDiaMod.toString());

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean existenCuentasConciliadasModificadas(){
        boolean blnRes=false;
        //valores que se encuentran en la base de datos
        String strCodCtaBasDat="";
        String strEstConBanCtaBasDat="";
        BigDecimal bdeValDebCtaBasDat=new BigDecimal("0");
        BigDecimal bdeValHabCtaBasDat=new BigDecimal("0");
        //valores en la tabla que acaban de generar por la modificacion hecha al documento(generacion automatica del diario)
        String strCodCtaTbl="";
        String strEstConBanCtaTbl="";
        BigDecimal bdeValDebCtaTbl=new BigDecimal("0");
        BigDecimal bdeValHabCtaTbl=new BigDecimal("0");
        int intExiConBanCta=0;
        try{
            for(int f=0; f<objTblMod.getRowCountTrue(); f++){
                objTblMod.setValueAt("N", f, INT_TBL_DAT_EST_CON);
            }
            for(int f=0; f<arlDatMayOnLin.size(); f++){
                intExiConBanCta=0;
                strEstConBanCtaBasDat=objUti.getObjectValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_EST_CON_BAN)==null?"":objUti.getStringValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_EST_CON_BAN).toString();
                if(strEstConBanCtaBasDat.equals("S")){//si la cuenta ya tenia una conciliacion bancaria
                    strCodCtaBasDat=objUti.getObjectValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_COD_CTA)==null?"":objUti.getStringValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_COD_CTA).toString();
                    bdeValDebCtaBasDat=new BigDecimal("" + objUti.getObjectValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_MON_DEB)==null?"0":objUti.getStringValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_MON_DEB).toString());
                    bdeValHabCtaBasDat=new BigDecimal("" + objUti.getObjectValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_MON_HAB)==null?"0":objUti.getStringValueAt(arlDatMayOnLin, f, INT_ARL_MAY_LIN_MON_HAB).toString());

                    for(int g=0; g<arlDatDetDiaMod.size(); g++){
                        strEstConBanCtaTbl=objUti.getObjectValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_EST_CON_BAN)==null?"":objUti.getStringValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_EST_CON_BAN).toString();
                        if(strEstConBanCtaTbl.equals("")){
                            strCodCtaTbl=objUti.getObjectValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_COD_CTA)==null?"":objUti.getStringValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_COD_CTA).toString();
                            bdeValDebCtaTbl=new BigDecimal("" + objUti.getObjectValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_MON_DEB)==null?"0":objUti.getStringValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_MON_DEB).toString());
                            bdeValHabCtaTbl=new BigDecimal("" + objUti.getObjectValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_MON_HAB)==null?"0":objUti.getStringValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_MON_HAB).toString());
                            if(strCodCtaBasDat.equals(strCodCtaTbl)){
                                if(bdeValDebCtaBasDat.compareTo(bdeValDebCtaTbl)==0){
                                    if(bdeValHabCtaBasDat.compareTo(bdeValHabCtaTbl)==0){
                                        intExiConBanCta++;
                                        objUti.setStringValueAt(arlDatDetDiaMod, g, INT_ARL_DET_DIA_MOD_EST_CON_BAN, "S");
                                        objTblMod.setValueAt("S", g, INT_TBL_DAT_EST_CON);
                                        break;
                                    }

                                }
                            }
                        }
                    }

                    if(intExiConBanCta==0){
                        blnRes=true;
                        break;
                    }
                }
            }

        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    private boolean existenCuentaAnteriorCerrada(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia){
        boolean blnRes=false;
        String strCodCtaDet="0";
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int k=0; k<arlDatMayOnLin.size(); k++){
                    if(k==0)
                        strCodCtaDet="" + objUti.getStringValueAt(arlDatMayOnLin, k, INT_ARL_MAY_LIN_COD_CTA);
                    else
                        strCodCtaDet+=", " + objUti.getStringValueAt(arlDatMayOnLin, k, INT_ARL_MAY_LIN_COD_CTA);
                }


//                for(int k=0; k<objTblMod.getRowCountTrue(); k++){
//                    if(strCodCtaDet.equals(""))
//                        strCodCtaDet="" + objTblMod.getValueAt(k,INT_TBL_DAT_COD_CTA);
//                    else
//                        strCodCtaDet+="," + objTblMod.getValueAt(k,INT_TBL_DAT_COD_CTA);
//                }

                strSQL="";
                strSQL+=" select a2.co_emp, a2.ne_ani, a2.ne_mes, a2.co_cta";
                strSQL+=" from tbm_cabciemencta AS a1 INNER JOIN tbm_detciemencta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a2.ne_ani=(SELECT extract(year from fe_dia) FROM tbm_cabDia ";
                strSQL+="                  WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc;
                strSQL+="                  AND co_tipDoc=" + intTipDoc + " AND co_dia=" + intCodDia + ")";
                strSQL+=" AND a2.ne_mes=(SELECT extract(month from fe_dia) FROM tbm_cabDia ";
                strSQL+="                  WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc;
                strSQL+="                  AND co_tipDoc=" + intTipDoc + " AND co_dia=" + intCodDia + ")";
                strSQL+=" AND a2.co_cta IN(" + strCodCtaDet + ");";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    mostrarMsgInf("<HTML>Una de las cuentas cambiadas en el asiento de diario estaba cerrada.<BR>Reaperture la cuenta y vuelva a intentarlo.<HTML>");
                    blnRes=true;
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

/**
 * Función que permite obtener el detalle del diario que ingreso el usuario.
 * La cuenta de banco que genera la transferencia y las otras cuentas ingresadas por el usuario.
 * @return ArrayList: el arreglo que contiene el detalle del diario
 */
//    private ArrayList getDetalleDiarioTransferenciaBancaria(){
//        if(! obtieneDatosDetalleDiarioModificado(objParSis.getCodigoEmpresa()))
//            arlDatDetDiaMod=null;
//        return arlDatDetDiaMod;
//    }

    public ArrayList getDetalleDiarioTransferenciaDiario() {
        if(! obtieneDatosDetalleDiarioModificado(objParSis.getCodigoEmpresa()))
            arlDatDetDiaMod=null;
        return arlDatDetDiaMod;
    }

    public void setDetalleDiarioTransferenciaDiario(ArrayList arlDatDetDiaMod) {
        this.arlDatDetDiaMod = arlDatDetDiaMod;
    }

    public int getIntChgBusCri() {
        return intChgBusCri;
    }

    public void setIntChgBusCri(int intChgBusCri) {
        this.intChgBusCri = intChgBusCri;
    }


   /**
     * Esta función inserta el asiento de diario en la base de datos.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     ** @param codigoDocumento El código del documento.
     * @param numeroDiario El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param fechaDiario La fecha del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */

    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String codigoDocumento, String numeroDiario, java.util.Date fechaDiario, int menuPrograma)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".


        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;
            intRefMesNew=0;
            intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );


            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el aóo<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho aóo todavóa no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar estó cerrado. <BR>Estó tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Estó tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;


            if (isCamVal()){
                if (insertarCab(conexion, empresa, local, tipoDocumento, codigoDocumento, numeroDiario, fechaDiario, menuPrograma)){                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia)){
                        System.out.println("getCamTblUltDoc: " + getCamTblUltDoc());
                        if(  (getCamTblUltDoc().equals("L"))  ){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                        }
                        else if(getCamTblUltDoc().equals("G")) {//para tbm_cabGrpTipDOc - cheques
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }

                        }
                        else if(  (getCamTblUltDoc().equals(""))   ){
                            System.out.println("getCamTblUltDoc: " + getCamTblUltDoc());
                            if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento)){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                            }
                        }
                        else{
                            if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento)){
                                if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro)){
                                    if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia)){
                                        //Generar evento "afterInsertar()".
                                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                        return true;
                                    }
                                }
                            }
                        }


                    }
                }
            }
        }
        return false;
    }



    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <BR>Si la cuenta es de debe. O sea, ubicacionCuenta=D:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuentaDebe".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuentaHaber".
     * @param codigoTipoDocumento El código del "Tipo de documento". La cadena debe contener un "int".
     * @param codigoCuentaDebe El código de la "Cuenta". La cadena debe contener un "int".
     * @param valorDebe El valor para la cuenta del debe. La cadena debe contener un "String".
     * @param codigoCuentaHaber El código de la "Cuenta". La cadena debe contener un "int".
     * @param valorHaber El valor para la cuenta del haber. La cadena debe contener un "String".
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(String codigoTipoDocumento, int codigoCuentaDebe,  String valorDebe, int codigoCuentaHaber, String valorHaber)
    {
        int intCodTipDoc, intCodCta;
        double dblValDeb, dblValHab;
        boolean blnRes=true;
        try
        {
            intCodTipDoc=Integer.parseInt(codigoTipoDocumento);
            dblValDeb=objUti.parseDouble(valorDebe);
            dblValHab=objUti.parseDouble(valorHaber);
            blnRes=generarDiario(intCodTipDoc, codigoCuentaDebe, dblValDeb, codigoCuentaHaber, dblValHab);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento y a la cuenta especificada.
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "codigoCuentaDebe".
     * <LI>Cuenta de haber: Se obtiene de "codigoCuentaHaber".
     * </UL>
     * @param codigoTipoDocumento El cïdigo del "Tipo de documento".
     * @param codigoCuentaDebe El código de la "Cuenta Deudora". Por lo general tbr_ctatipdocusr.
     * @param valorDebe El valor para la cuenta del debe.
     * @param codigoCuentaHaber El código de la "Cuenta Acreedora". Por lo general una cuenta de Banco.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int codigoTipoDocumento, int codigoCuentaDebe, double valorDebe, int codigoCuentaHaber, double valorHaber){
        boolean blnRes=true;
        try{
            switch (bytDiaGen){
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND b1.co_cta=" + codigoCuentaDebe;
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'H' AS tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS b1";
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND b1.co_cta=" + codigoCuentaHaber;

                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D")){
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    dblMonDeb=valorDebe;
                    dblMonHab=valorHaber;
                    
                    bdeMonDeb=new BigDecimal(valorDebe);
                    bdeMonHab=new BigDecimal(valorHaber);
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param numeroDiario El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param fechaDiario La fecha del diario.
     * @param fechaVencimiento La fecha de vencimiento del Pedido Embarcado por Importación.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, String numeroDiario, java.util.Date fechaDiario, java.util.Date fechaVencimiento, BigDecimal valorDocumento){
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeInsertar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_INS);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe){
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            intRefAniNew=0;intRefMesNew=0;intTipPro=0;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 );
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            //ESTE CODIGO ES NUEVO, Y PERMITE VALIDAR Q NO SE INGRESEN DIARIOS CON CIERRES MENSUALES O ANUALES
            if( ! (retAniMesCie(intRefAniNew)))
                return false;        
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(intRefAniNew==intArlAni){
                        if(intRefMesNew==intArlMes){
                            mostrarMsgInf("<HTML>El mes que desea ingresar está cerrado. <BR>Está tratando de INSERTAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }                
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de INSERTAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            //Si la cadena estó vacóa se asigna null para que la función se encargue de generar el nómero de diario.
            if (numeroDiario.equals(""))
                numeroDiario=null;
            if (isCamVal())
                if (insertarCab(conexion, empresa, local, tipoDocumento, null, numeroDiario, fechaDiario, fechaVencimiento, valorDocumento))
                    if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                        if (actualizarCabTipDoc(conexion, empresa, local, tipoDocumento))
                            if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, fechaDiario, intTipPro))
                                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, intCodDia))
                                {
                                    //Generar evento "afterInsertar()".
                                    fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_INS);
                                    return true;
                                }
        }
        return false;
    }
    
    

    /**
     * Esta función inserta la cabecera del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param strCodDia El código del diario. Es el código que maneja el sistema.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el código del diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el código del diario recibido.
     * </UL>
     * @param strNumDia El nómero de diario. Por lo general es el nómero preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró el nómero de diario de la base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró el nómero de diario recibido.
     * </UL>
     * @param datFecDia La fecha del diario.
     * @param datFecVct La fecha de vencimiento del Pedido Embarcado por Importación.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parómetro recibido es <I>null</I> la función obtendró la fecha del servidor de base de datos.
     * <LI>Si el parómetro recibido es diferente a <I>null</I> utilizaró la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, String strCodDia, String strNumDia, java.util.Date datFecDia, java.util.Date datFecVct, BigDecimal valorDocumento){
        int intCodUsr, intNumDia;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try{
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null){
                stm=con.createStatement();
                if (strCodDia==null){
                    //Obtener el código del óltimo registro.
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_dia)";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intCodDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intCodDia==-1)
                        return false;
                    intCodDia++;
                }
                else{
                    intCodDia=Integer.parseInt(strCodDia);
                }
                if (strNumDia==null){
                    //Obtener el nómero de diario.
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intNumDia=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                    if (intNumDia==-1)
                        return false;
                    intNumDia++;
                    strNumDia="" + intNumDia;
                }
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                
                    if( ! (fechaPermitida(con, intTipDoc, 'n', datFecDia, datFecDia))   )
                        blnRes=false;
                
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia, tx_numDia, fe_dia, tx_glo";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, fe_ven, nd_valDoc)";
                strSQL+=" VALUES (";
                strSQL+="" + intCodEmp;
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intTipDoc;
                strSQL+=", " + intCodDia;
                strSQL+=", " + objUti.codificar(strNumDia);
                //Si la fecha recibida es null asigno la fecha del servidor.
                if (datFecDia==null)
                    strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaBaseDatos()) + "'";
                else{
                    //strSQL+=", '" + objUti.formatearFecha(datFecDia, objParSis.getFormatoFechaBaseDatos()) + "'";
                    strSQL+=", '" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                strSQL+=", " + objUti.codificar(txtGlo.getText());
                strSQL+=", 'A'"; 
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=", '" + objUti.formatearFecha(datFecVct, objParSis.getFormatoFechaBaseDatos()) + "'";//fe_ven
                strSQL+=", " + valorDocumento + "";//nd_valDoc
                strSQL+=")";
                System.out.println("insertarCab: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
     * Esta función actualiza el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigoDiario El código del diario que se desea actualizar.
     * @param numeroDiario El nómero de diario.
     * @param fechaDiario La fecha del diario.
     * @param fechaVencimiento La fecha de vencimiento del Pedido Embarcado por Importación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean actualizarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int codigoDiario, String numeroDiario, java.util.Date fechaDiario, java.util.Date fechaVencimiento, BigDecimal valorDocumento)
    {
        //Permitir de manera predeterminada la operación.
        blnCanOpe=false;
        //Generar evento "beforeModificar()".
        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_MOD);
        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
        if (!blnCanOpe)
        {
            int intArlAni=0;
            int intArlMes=0;
            String strArlTipCie="";
            int intFunAni=0;
            int intFunMes=0;
            intTipPro=1;
            intRefAniNew=(fechaDiario.getYear() + 1900);
            intRefMesNew=(fechaDiario.getMonth() + 1 ); 
            //PARA VALIDAR LA MODIFICACION DE UN DIARIO, A TRAVES DE PARAMETROS QUE SE RECIBEN Y DE LA FUNCION getFecDia()
            intFunAni=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getYear() + 1900);
            intFunMes=(getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario).getMonth() + 1 );
            datAuxMayAnt=getFecDia(conexion, empresa, local, tipoDocumento, codigoDiario);
            
            //SI EL AóO NO HA SIDO CREADO EN EL SISTEMA NO SE DEBE PERMITIR INGRESAR(NO EXISTE EL ANIO EN tbm_anicresis)
            if(      ( ! (objParSis.isAnioDocumentoCreadoSistema(intRefAniNew))  )     ||    ( ! (objParSis.isAnioDocumentoCreadoSistema(intFunAni))  )   ){
                mostrarMsgInf("<HTML>El documento no puede ser grabado en el año<FONT COLOR=\"blue\"> " + intRefAniNew + " </FONT> debido a que dicho año todavía no ha sido creado en el sistema<BR>Notifique este problema a su Administrador del Sistema</HTML>");
                return false;
            }
            
            if(    ( ! (retAniMesCie(intFunAni)))   ||   ( ! (retAniMesCie(intRefAniNew)))    )
                return false;
            
            for (int k=0; k<arlDatAniMes.size(); k++){
                intArlAni=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_ANI_CIE);
                intArlMes=objUti.getIntValueAt(arlDatAniMes, k, INT_ARL_MES_CIE);
                strArlTipCie=(objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE)==null?"":objUti.getStringValueAt(arlDatAniMes, k, INT_ARL_TIP_CIE));
                
                if( (strArlTipCie.toString().equals("M"))  ){
                    if(   (intFunAni==intArlAni)){
                        if(    (intFunMes==intArlMes)){
                            mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                    
                    if( (intRefAniNew==intArlAni)  ){
                        if( (intRefMesNew==intArlMes)  ){
                            mostrarMsgInf("<HTML>El mes que desea modificar está cerrado. <BR>Está tratando de MODIFICAR un documento de un periodo cerrado. <BR>Aperture el mes que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                    
                }
                else{
                    mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo que tiene un cierre anual. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }

            
            if (isCamVal()){
                if(obtieneDatosDetalleDiario(conexion, empresa, local, tipoDocumento, codigoDiario)){
                    if(obtieneDatosDetalleDiarioModificado(empresa)){
                        if( ! existenCuentasConciliadasModificadas()){
                            if( ! existenCuentaAnteriorCerrada(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                if (actualizarCab(conexion, empresa, local, tipoDocumento, codigoDiario, numeroDiario, fechaDiario, fechaVencimiento, valorDocumento)){
                                    if (eliminarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                        if (insertarDet(conexion, empresa, local, tipoDocumento, codigoDiario)){
                                            if(mayorizaCta(conexion, empresa, local, tipoDocumento, intCodDia, fechaDiario, datAuxMayAnt, intTipPro)){
                                                //Generar evento "afterModificar()".
                                                fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_AFT_MOD);
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        else{
                            mostrarMsgInf("<HTML>Al modificar el documento se está cambiando una cuenta conciliada o su valor<BR>No puede cambiar una cuenta ni su valor si la cuenta está conciliada.<BR>Desconcilie la cuenta en el documento que desea modificar y vuelva a intentarlo.</HTML>");
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    
    /**
     * Esta función actualiza la cabecera del registro. 
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @param strNumDia El nómero de diario.
     * @param datFecDia La fecha del diario.
     * @param datFecVct La fecha de vencimiento del Pedido Embarcado por Importación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Cadena</I></TD><TD><I>Formato</I></TD></TR>
     *     <TR><TD>A</TD><TD>Diario activo</TD></TR>
     *     <TR><TD>I</TD><TD>Diario anulado</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia, String strNumDia, java.util.Date datFecDia, java.util.Date datFecVct, BigDecimal valDoc)
    {
        int intCodUsr;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objParSis.getCodigoUsuario();
            if (con!=null)
            {
                
                if( ! (fechaPermitida(con, intTipDoc, 'm', datFecDia, getFechaDiarioDB(con, intCodEmp, intCodLoc, intTipDoc, intCodDia)  ))   )
                    blnRes=false;
                
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabDia";
                strSQL+=" SET tx_numDia=" + objUti.codificar(strNumDia);
                strSQL+=", fe_dia='" + objUti.formatearFecha(strFecGua, "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_ven='" + objUti.formatearFecha(datFecVct, objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", nd_valDoc=" + (objUti.redondearBigDecimal(valDoc, objParSis.getDecimalesMostrar())) + "";
                if ( ! ( objParSis.getCodigoMenu()==1669) ){
                    strSQL+=", tx_glo=" + objUti.codificar(txtGlo.getText());
                }
                strSQL+=", st_reg='A'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + intCodUsr;
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecSer=null;
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
    
    
    
    
    
   //nuevo 
    
    
    
    
    
    
    /**
     * Esta función genera el "Asiento de diario" de acuerdo a la empresa tipo de documento.
     * <BR>Es decir:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento". La cadena debe contener un "int".
     * @param valorDebe El valor para la cuenta del debe. La cadena debe contener un "double".
     * @param valorHaber El valor para la cuenta del haber. La cadena debe contener un "double".
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(String codigoEmpresa, String codigoLocal, String codigoTipoDocumento, BigDecimal valorDebe, BigDecimal valorHaber)
    {
        int intCodEmp, intCodLoc, intCodTipDoc;
        BigDecimal bdeValDeb=BigDecimal.ZERO, bdeValHab=BigDecimal.ZERO;
        boolean blnRes=true;
        try
        {
            intCodEmp=Integer.parseInt(codigoEmpresa);
            intCodLoc=Integer.parseInt(codigoLocal);
            intCodTipDoc=Integer.parseInt(codigoTipoDocumento);
            bdeValDeb=valorDebe;
            bdeValHab=valorHaber;
            blnRes=generarDiario(intCodEmp, intCodLoc, intCodTipDoc, bdeValDeb, bdeValHab);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento.
     * <BR>Es decir:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param valorDebe El valor para la cuenta del debe.
     * @param valorHaber El valor para la cuenta del haber.
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDiario(int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, BigDecimal valorDebe, BigDecimal valorHaber)
    {
        boolean blnRes=true;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                        strSQL+=" AND a1.co_emp=" + codigoEmpresa;
                        strSQL+=" AND a1.co_loc=" + codigoLocal;
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2";
                        strSQL+=" WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta";
                        strSQL+=" AND b1.co_emp=" + codigoEmpresa;
                        strSQL+=" AND b1.co_loc=" + codigoLocal;
                        strSQL+=" AND b1.co_tipDoc=" + codigoTipoDocumento;
                        System.out.println("aaaaaa: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else
                            {
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);

                            
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
                        //Almacenar el monto del debe y haber en variables globales.
                        bdeMonDeb=valorDebe;
                        bdeMonHab=valorHaber;
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    bdeMonDeb=valorDebe;
                    bdeMonHab=valorHaber;
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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

    
    
    
    
    
    
    
    
    
    
    //Provisiones
        
    /**
     * Función que permite generar el Asiento de Diario de Provisiones
     * @param conexion Conexión a la base de datos
     * @param codigoEmp Código de Empresa de la Provisión
     * @param codigoLoc Código de Local de la Provisión
     * @param codigoTipDoc Código de Tipo de documento de la Provisión
     * @param codigoDoc    Código de documento de la Provisión
     * @param codigoEmpRel Código de empresa relacionada (Instancia relacionada)
     * @param codigoLocRel Código de local relacionada (Instancia relacionada)
     * @param codigoTipDocRel Código de tipo de documento relacionada (Instancia relacionada)
     * @param codigoDocRel Código de tipo de documento relacionada (Instancia relacionada)
     * @param codigoSeguimiento Código de seguimiento
     * @param numeroPedido Número de Pedido
     * @param valorDebe Valor del debe del asiento de diario
     * @param valorHaber Valor del haber del asiento de diario
     * @return 
     */
    public boolean generarDiario(Connection conexion
            , String codigoEmp, String codigoLoc, String codigoTipDoc, String codigoDoc  /*Provision*/
            , String codigoEmpRel, String codigoLocRel, String codigoTipDocRel, String codigoDocRel  /*Pedido Embarcado*/
            , Object codigoSeguimiento, int intCodCtaDeb, String numeroPedido, BigDecimal valorDebe, BigDecimal valorHaber, String tipoDiario){
        
        int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
        int intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel;
        BigDecimal bdeValDeb=BigDecimal.ZERO, bdeValHab=BigDecimal.ZERO;
        boolean blnRes=true;
        try
        {
            if(conexion!=null){
                //Provisión
                intCodEmp=Integer.parseInt(codigoEmp);
                intCodLoc=Integer.parseInt(codigoLoc);
                intCodTipDoc=Integer.parseInt(codigoTipDoc);
                intCodDoc=Integer.parseInt(codigoDoc);
                //Pedido Embarcado
                intCodEmpRel=Integer.parseInt(codigoEmpRel);
                intCodLocRel=Integer.parseInt(codigoLocRel);
                intCodTipDocRel=Integer.parseInt(codigoTipDocRel);
                intCodDocRel=Integer.parseInt(codigoDocRel);
                
                bdeValDeb=valorDebe;
                bdeValHab=valorHaber;
                System.out.println("intCodCtaDeb: " + intCodCtaDeb);
                //blnRes=generarDiario(intCodEmp, intCodLoc, intCodTipDoc, bdeValDeb, bdeValHab, nombreExportador);
                blnRes=generarDiarioProvision(conexion, codigoSeguimiento, intCodCtaDeb, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc
                        , intCodEmpRel, intCodLocRel, intCodTipDocRel, intCodDocRel, numeroPedido, bdeValDeb, bdeValHab, tipoDiario);
            }
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite generar el asiento de diario de provisión
     * @param conexion  Conexión a la base de datos
     * @param codigoSeguimiento   Código de seguimiento
     * @param codigoEmp Código de empresa 
     * @param codigoLoc Código de local
     * @param codigoTipDoc Código de Tipo de documento
     * @param codigoEmpRel Código de empresa relacionada
     * @param codigoLocRel Código de local relacionada
     * @param codigoTipDocRel Código de tipo de documento relacionada
     * @param codigoCtaMerTra Código de cuenta de mercadería en tránsito
     * @param numeroPedido Número de pedido
     * @param valorDebe Valor del debe del documento
     * @param valorHaber Valor de haber del documento
     * @return 
     */
    private boolean generarDiarioProvision(Connection conexion, Object codigoSeguimiento, int intCodCtaDeb, int codigoEmp, int codigoLoc, int codigoTipDoc, int codigoDoc
                                    , int codigoEmpRel, int codigoLocRel, int codigoTipDocRel, int codigoDocRel
                                    , String numeroPedido, BigDecimal valorDebe, BigDecimal valorHaber, String tipoDiario){
        boolean blnRes=false;
        try{
            if(conexion!=null){
                if(insertarCuentaProvision(conexion, codigoSeguimiento, codigoEmp, numeroPedido, codigoEmpRel, codigoLocRel, codigoTipDocRel, codigoDocRel)){
                    if(generarCuentaProvision(conexion, codigoEmp, intCodCtaDeb, codigoEmpRel, codigoLocRel, codigoTipDocRel, codigoDocRel, valorDebe, valorHaber, tipoDiario)){
                        blnRes=true;
    //                    if(insertarDiarioProvision(conexion, codigoEmp, codigoLoc, codigoTipDoc, codigoDoc, codigoEmpRel, codigoLocRel, codigoTipDocRel, codigoDocRel)){
    //                    }
                    }
                }
            }

        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    /**
     * Función que permite generar la cuenta de provisión de importación
     * @param conexion Corresponde a la conexión de la base de datos
     * @param codigoEmpresa Corresponde al código de la empresa de la cuenta
     * @param nombreCuentaContable Corresponde al nombre de la cuenta contable de Mercadería en tránsito
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean insertarCuentaProvision(Connection conexion, Object codigoSeguimiento, int codigoEmpresa, String numeroPedido, int codigoEmpRel, int codigoLocRel, int codigoTipDocRel, int codigoDocRel){
        boolean blnRes=true;
        intCodCtaPro=0;
        try{
            if(conexion!=null){
                stm=conexion.createStatement();
                strSQL="";
                strSQL+=" (SELECT MAX(co_cta) AS co_ctaPro FROM tbm_plaCta WHERE co_emp=" + codigoEmpresa + ");";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaPro=rst.getInt("co_ctaPro");
                }
                intCodCtaPro++;
                
                rst.close();
                rst=null;
                
                strSQL="";
                strSQL+=" INSERT INTO tbm_plaCta(";
                strSQL+="       co_emp, co_cta, tx_codalt, tx_deslar, tx_niv1, tx_niv2, tx_niv3, tx_niv4, tx_niv5, tx_codcta, tx_tipcta";
                strSQL+="       , tx_natcta, st_aut, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, ne_pad, ne_niv";
                strSQL+="       , st_ctafluefe, st_ctaflufon, st_ctapre, st_regrep, st_ctaban, ne_ultnumchq)";
                strSQL+=" SELECT b1.co_emp, (SELECT MAX(co_cta)+1 FROM tbm_plaCta WHERE co_emp=" + codigoEmpresa + ")";
                strSQL+="       , Null, 'Provisiones por Pagar(" + numeroPedido + ")', b1.tx_niv1, b1.tx_niv2, b1.tx_niv3, b1.tx_niv4, b2.tx_niv5";
                strSQL+="       , b1.tx_niv1 || '.' || b1.tx_niv2 || '.' || b1.tx_niv3 || '.' || b1.tx_niv4 || '.' || b2.tx_niv5";
                strSQL+="       , 'D', 'H', 'N', Null, Null, 'A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, b1.co_ctaPad, 5, 'N','N', 'N', 'M', 'N', 0";
                strSQL+=" FROM(";
                strSQL+="	SELECT a1.co_emp,        a1.tx_niv1       , a1.tx_niv2       , a1.tx_niv3       , a1.tx_niv4       , a2.co_ctaPad";
                strSQL+="	FROM tbm_plaCta AS a1 INNER JOIN(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_ctaCabPasImp AS co_ctaPad, a2.ne_niv";
                strSQL+=" 			FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2               ON a1.co_emp=a2.co_emp";
		strSQL+=" 			WHERE a1.co_emp=" + codigoEmpresa + " AND a2.co_cta=a1.co_ctaCabPasImp";
		strSQL+=" 	) AS a2       ON a1.co_emp=a2.co_emp AND a1.ne_pad=a2.co_ctaPad";
                strSQL+=" 	WHERE a1.tx_tipCta='D' AND a1.ne_niv=(a2.ne_niv+1)";
                strSQL+=" 	GROUP BY a1.co_emp,         a1.tx_niv1       , a1.tx_niv2       , a1.tx_niv3       , a1.tx_niv4       , a2.co_ctaPad";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+="         SELECT a1.co_emp, a1.tx_niv1, a1.tx_niv2, a1.tx_niv3, a1.tx_niv4, (MAX(CAST(a1.tx_niv5 AS SMALLINT))+1) AS tx_niv5, a1.ne_pad AS co_ctaPad";
                strSQL+="         FROM tbm_plaCta AS a1 	WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQL+="         GROUP BY a1.co_emp,         a1.tx_niv1       , a1.tx_niv2       , a1.tx_niv3       , a1.tx_niv4, a1.ne_pad";
                strSQL+=" ) AS b2 ON b1.co_emp=b2.co_emp AND b1.co_ctaPad=b2.co_ctaPad AND b1.tx_niv1=b2.tx_niv1 AND b1.tx_niv2=b2.tx_niv2 AND b1.tx_niv3=b2.tx_niv3";
                strSQL+=" AND b1.tx_niv4=b2.tx_niv4;";
                //tbm_salCta
                for(int i=1; i<=12; i++){
                    strSQL+=" INSERT INTO tbm_salcta(co_emp, co_cta, co_per, nd_salcta, st_regrep, st_salctadef, tx_codCta, tx_desLar, ne_pad, ne_niv)";
                    strSQL+=" (";
                    strSQL+=" SELECT "+ codigoEmpresa + ", " + intCodCtaPro + "";
                    strSQL+="     , (SELECT CAST((SELECT EXTRACT('year' FROM CURRENT_DATE) || (CASE WHEN " + i + ">9 THEN '' || " + i + " ELSE '0' || " + i + " END)) AS INTEGER ))";
                    strSQL+="     , 0.00, 'I', 'N', b1.tx_niv1 || '.' || b1.tx_niv2 || '.' || b1.tx_niv3 || '.' || b1.tx_niv4 || '.' || b2.tx_niv5";
                    strSQL+="     , 'Provisiones por Pagar(" + numeroPedido + ")'";
                    strSQL+="     , b1.co_ctaPad, 5";
                    strSQL+="     ";
                    strSQL+=" FROM(";
                    strSQL+="	SELECT a1.co_emp,        a1.tx_niv1       , a1.tx_niv2       , a1.tx_niv3       , a1.tx_niv4       , a2.co_ctaPad";
                    strSQL+="	FROM tbm_plaCta AS a1 INNER JOIN(";
                    strSQL+=" 			SELECT a1.co_emp, a1.co_ctaCabPasImp AS co_ctaPad, a2.ne_niv";
                    strSQL+=" 			FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2               ON a1.co_emp=a2.co_emp";
                    strSQL+=" 			WHERE a1.co_emp=" + codigoEmpresa + " AND a2.co_cta=a1.co_ctaCabPasImp";
                    strSQL+=" 	) AS a2       ON a1.co_emp=a2.co_emp AND a1.ne_pad=a2.co_ctaPad";
                    strSQL+=" 	WHERE a1.tx_tipCta='D' AND a1.ne_niv=(a2.ne_niv+1)";
                    strSQL+=" 	GROUP BY a1.co_emp,         a1.tx_niv1       , a1.tx_niv2       , a1.tx_niv3       , a1.tx_niv4       , a2.co_ctaPad";
                    strSQL+=" ) AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+="         SELECT a1.co_emp, a1.tx_niv1, a1.tx_niv2, a1.tx_niv3, a1.tx_niv4, (MAX(CAST(a1.tx_niv5 AS SMALLINT))+1) AS tx_niv5, a1.ne_pad AS co_ctaPad";
                    strSQL+="         FROM tbm_plaCta AS a1 	WHERE a1.co_emp=" + codigoEmpresa + "";
                    strSQL+="         GROUP BY a1.co_emp,         a1.tx_niv1       , a1.tx_niv2       , a1.tx_niv3       , a1.tx_niv4, a1.ne_pad";
                    strSQL+=" ) AS b2 ON b1.co_emp=b2.co_emp AND b1.co_ctaPad=b2.co_ctaPad AND b1.tx_niv1=b2.tx_niv1 AND b1.tx_niv2=b2.tx_niv2 AND b1.tx_niv3=b2.tx_niv3";
                    strSQL+=" AND b1.tx_niv4=b2.tx_niv4";  
                    strSQL+=") ;";
                }
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET co_ctaPro=" + intCodCtaPro + "";
                strSQL+=" WHERE co_emp=" + codigoEmpRel + "";
                strSQL+=" AND co_loc=" + codigoLocRel + "";
                strSQL+=" AND co_tipDoc=" + codigoTipDocRel + "";
                strSQL+=" AND co_doc=" + codigoDocRel + "";
                strSQL+=";";
                System.out.println("insertarCuentaProvision: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
        
    /**
     * Esta función genera el "Asiento de diario" de acuerdo al tipo de documento.
     * <BR>Es decir:
     * <UL>
     * <LI>Cuenta de debe: Se obtiene de "tbm_cabTipDoc.co_ctaDeb".
     * <LI>Cuenta de haber: Se obtiene de "tbm_cabTipDoc.co_ctaHab".
     * </UL>
     * @param codigoTipoDocumento El código del "Tipo de documento".
     * @param valorDebe El valor para la cuenta del debe.
     * @param valorHaber El valor para la cuenta del haber.
     * @param tipoDiario Establece si el diario es un diario normal o un diario de Reversión. (R:Reversión   N:Normal). Si es Diario normal, la cuenta de Pedido de activo va al debe y la cuenta de Provisión va al haber, si es un diario de reversión, están cuentas deben ser registradas a la inversa
     * @return true: Si se pudo generar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    private boolean generarCuentaProvision(Connection conexion, int codigoEmpresa, int codigoCtaDeb, int codigoEmpRel, int codigoLocRel
                                   , int codigoTipDocRel, int codigoDocRel, BigDecimal valorDebe, BigDecimal valorHaber, String tipoDiario){
        boolean blnRes=true;
        String strTipDia=tipoDiario;//R:Reversión   N:Normal
        try{
            switch (bytDiaGen){
                case 0: //Diario no generado.
                    if (conexion!=null){
                        stm=conexion.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_natCta";
                        strSQL+=" FROM tbm_plaCta AS a1";
                        strSQL+=" WHERE a1.co_emp=" + codigoEmpresa + " AND a1.co_cta=" + codigoCtaDeb + "";
                        strSQL+=" UNION ALL";
                        strSQL+=" SELECT a2.co_emp, a2.co_cta, a2.tx_codCta, a2.tx_desLar, a2.tx_natCta";
                        strSQL+=" FROM tbm_cabPedEmbImp AS a1";
                        strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a2";
                        strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaPro=a2.co_cta";
                        strSQL+=" WHERE a1.co_emp=" + codigoEmpRel + " AND a1.co_loc=" + codigoLocRel + "";
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipDocRel + " AND a1.co_doc=" + codigoDocRel + "";
                        System.out.println("generarCuentaProvision: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next()){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            
                            if(strTipDia.equals("N")){//Diario normal
                                if (rst.getString("tx_natCta").equals("D")){
                                    vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                    vecReg.add(INT_TBL_DAT_HAB,null);
                                }
                                else{
                                    vecReg.add(INT_TBL_DAT_DEB,null);
                                    vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                                }
                            }
                            else if(strTipDia.equals("R")){//Diario de reversión
                                if (rst.getString("tx_natCta").equals("H")){
                                    vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                    vecReg.add(INT_TBL_DAT_HAB,null);
                                }
                                else{
                                    vecReg.add(INT_TBL_DAT_DEB,null);
                                    vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                                }
                            }
                            

                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);
                            vecDat.add(vecReg);
                            
                            System.out.println("co_cta: " + rst.getString("co_cta"));
                            System.out.println("tx_codCta: " + rst.getString("tx_codCta"));
                            System.out.println("tx_desLar: " + rst.getString("tx_desLar"));
                            
                            
                        }
                        rst.close();
                        stm.close();
                        rst=null;
                        stm=null;
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        //Almacenar el monto del debe y haber en variables globales.
                        bdeMonDeb=valorDebe;
                        bdeMonHab=valorHaber;
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
                case 1: //Diario generado por el Sistema.
                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
                    //Almacenar el monto del debe y haber en variables globales.
                    bdeMonDeb=valorDebe;
                    bdeMonHab=valorHaber;
                    
                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                    //Indicar que el asiento de diario ha sido generado.
                    bytDiaGen=1;
                    break;
                case 2: //Diario generado por el usuario.
                    break;
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
     * Función que permite generar asientos de diario de Provision
     * @return 
     */
    public boolean insertarDiarioProvision(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento
                                            , int codEmpPedEmb, int codLocPedEmb, int codTipDocPedEmb, int codDocPedEmb){
        boolean blnRes=false;
        java.util.Date datFecSer;
        try{
            if(conexion!=null){
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                
                if(insertarCab(conexion, codigoEmpresa, codigoLocal, codigoTipoDocumento, "" + codigoDocumento, null, datFecSer, 'A')){
                    if(insertarDet(conexion, codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento)){
                        System.out.println("AAA");
                        if(objSegMovInv.getCodSegIngImp(conexion, codEmpPedEmb, codLocPedEmb, codTipDocPedEmb, codDocPedEmb)){//Obtiene el codigo de seguimiento de la instancia anterior (Nota de Pedido)
                            System.out.println("BBB");
                            if(objSegMovInv.setSegMovInvCompra(conexion, objSegMovInv.getObjCodSegInsAnt()
                                , 8, codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento, null
                                , 2, codEmpPedEmb, codLocPedEmb, codTipDocPedEmb, codDocPedEmb, null)){
                                System.out.println("CCC");
                                blnRes=true;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Este metodo permite desmayorizar/mayorizar las cuentas de acuerdo al criterio seleccionado.
     * @return true: Si se pudo realizar el proceso.
     * <BR>false: En el caso contrario.
     */
    public boolean remayorizarPeriodo(java.sql.Connection con, int intCodEmp, int intAnoCor, int intMesCor, boolean blnMosMsj)
    {
        int i, j, k;
        String strPer;
        String strFecRan[][];
        boolean blnRes=true;
        double dblSalCta=0.00;
        String strMes="", strAni="", strMesCor, strFecDes, strFecHas;
        int intMes=0;
        
        try
        {
            //butCon.setText("Detener");
            //lblMsgSis.setText("Desmayorizando cuentas...");
            
            if (con != null)
            {  stm = con.createStatement();
               strMesCor = (intMesCor < 10)? "0" + (intMesCor) :"" + (intMesCor);
               strFecDes = "01/01/" + intAnoCor; //dd/mm/aaaa
               strFecHas = "01/" + strMesCor + "/" + intAnoCor; //dd/mm/aaaa
               //Procesar las cuentas de la "Empresa seleccionada".
               strFecRan = objUti.getIntervalosMensualesRangoFechas(strFecDes, strFecHas, "dd/MM/yyyy");
               
               //Desmayorizar
               //------------
               //pgrSis.setMinimum(0);
               //pgrSis.setMaximum(strFecRan.length);
               //pgrSis.setValue(0);
               k = 0;
               
               for (i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {   //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCta";
                       strSQL+=" SET nd_salCta=" + dblSalCta + "";
                       strSQL+=" WHERE co_emp=" + intCodEmp;
                       strSQL+=" AND co_per=" + strPer;
                       stm.executeUpdate(strSQL);
                   }
                   else
                   {   if (blnMosMsj == true)
                       {   switch(intMes)
                           {   case 1:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Enero </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 2:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Febrero </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 3:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Marzo </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 4:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Abril </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 5:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Mayo </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 6:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Junio </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 7:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Julio </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 8:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Agosto </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 9:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Septiembre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 10:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Octubre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 11:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Noviembre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 12:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Diciembre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                           } //switch(intMes)
                       } //if (blnMosMsj == true)
                   } //if (! isMesCerrado(strMes, strAni))

                   k++;
                   //pgrSis.setValue(k);
               } //for (i = 0; i < strFecRan.length; i++)
               
               //Mayorizar
               //---------
               //pgrSis.setMinimum(0);
               //pgrSis.setMaximum(7*strFecRan.length);
               //pgrSis.setValue(0);
               k = 0;
               
               for (i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {   //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+=" UPDATE tbm_salcta";
                       strSQL+=" set nd_salCta=x.nd_salcta from (";
                       strSQL+=" SELECT b1.co_emp, b1.co_cta, " + strPer + " AS co_per, b2.nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS b1";
                       strSQL+=" LEFT OUTER JOIN (";
                       //strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta";
                       strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM( (case when a2.nd_monDeb is null then 0 else a2.nd_monDeb end) - (case when a2.nd_monHab is null then 0 else a2.nd_monHab end) ) AS nd_salCta";
                       strSQL+=" FROM tbm_cabDia AS a1";
                       strSQL+=" INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.st_reg='A'";
                       strSQL+=" AND (a1.fe_dia BETWEEN '" + strFecRan[i][0] + "' AND '" + strFecRan[i][1] + "')";
                       strSQL+=" GROUP BY a1.co_emp, a2.co_cta";
                       strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                       strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                       strSQL+=" ORDER BY b1.co_emp, b1.co_cta";
                       strSQL+=" ) as x";
                       strSQL+=" WHERE x.co_emp=tbm_salcta.co_emp AND x.co_cta=tbm_salcta.co_cta AND x.co_per=tbm_salcta.co_per";
                       //System.out.println("update 1: " +strSQL);
                       stm.executeUpdate(strSQL);
                       k++;
                       //pgrSis.setValue(k);
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCta";
                       strSQL+=" SET nd_salCta=(CASE WHEN tbm_salCta.nd_salCta IS NULL THEN 0 ELSE tbm_salCta.nd_salCta END) + b1.nd_salCta";
                       strSQL+=" FROM (";
                       strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS a1";
                       strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                       strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.tx_niv1 IN ('4', '5', '6', '7', '8')";
                       strSQL+=" AND a2.co_per=" + strPer;
                       strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                       strSQL+=" ) AS b1";
                       strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                       //System.out.println("update 2: " +strSQL);
                       stm.executeUpdate(strSQL);
                       k++;
                       //pgrSis.setValue(k);
                       
                       for (j = 6; j > 1; j--)
                       {   //Armar la sentencia SQL.
                           strSQL="";
                           strSQL+="UPDATE tbm_salCta";
                           strSQL+=" SET nd_salCta=b1.nd_salCta";
                           strSQL+=" FROM (";
                           //strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                           strSQL+=" SELECT a1.co_emp, a2.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                           strSQL+=" FROM tbm_plaCta AS a1";
                           strSQL+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                           //strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                           strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                           //strSQL+=" AND a1.ne_niv=" + j;
                           strSQL+=" AND a2.ne_niv=" + j;
                           strSQL+=" AND a2.co_per=" + strPer;
                           //strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                           strSQL+=" GROUP BY a1.co_emp, a2.ne_pad";
                           strSQL+=" ) AS b1";
                           strSQL+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                           //System.out.println("UPDATE 3: " +strSQL);
                           stm.executeUpdate(strSQL);
                           k++;
                           //pgrSis.setValue(k);
                       }
                   } //if (! isMesCerrado(strMes, strAni))
                   else
                   {   if (blnMosMsj == true)
                       {   switch(intMes)
                           {   case 1:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Enero </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 2:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Febrero </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 3:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Marzo </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 4:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Abril </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 5:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Mayo </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 6:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Junio </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 7:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Julio </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 8:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Agosto </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 9:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Septiembre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 10:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Octubre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 11:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Noviembre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                               case 12:
                                   mostrarMsgInf("<HTML>El mes de <FONT COLOR=\"blue\">Diciembre </FONT> estÃ¡ cerrado.<BR>Se procesan sÃ³lo los meses que tengan perÃ­odo abierto.</HTML>");
                                   break;
                           } //switch(intMes)
                       } //if (blnMosMsj == true)
                   } //if (! isMesCerrado(strMes, strAni))
               } //for (i = 0; i < strFecRan.length; i++)
                        
               stm.close();
               stm = null;

               //lblMsgSis.setText("Listo");
               //pgrSis.setValue(0);
               //butCon.setText("Consultar");

               if (! procesarCuentasEfectivo(con, intCodEmp, intAnoCor, intMesCor))
               {    
                  blnRes = false;
               }
            } //if (con != null)
        } //try
        
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Metodo remayorizarPeriodo()
    
    /**
     * Esta funcion establece si el mes que se desea procesar 
     * ha sido cerrado.
     * @param periodo El periodo que se desea saber si ha sido cerrado.
     */
    private boolean isMesCerrado(java.sql.Connection con, int intCodEmp, String mes, String anio)
    {
        boolean blnRes=false;
        Statement stmMesCer;
        ResultSet rstMesCer;
        
        try
        {
            if (con != null)
            {
                stmMesCer=con.createStatement();
                strSQL="";
                strSQL+="SELECT ne_mes FROM tbm_cabCieSis AS a1";
                strSQL+=" INNER JOIN tbm_detCieSis AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.ne_ani=" + anio + "";
                strSQL+=" AND a2.ne_mes=" + mes + "";
                //System.out.println("SQL ISMESCERRADO: " + strSQL);
                rstMesCer = stmMesCer.executeQuery(strSQL);
                if (rstMesCer.next())
                {
                    blnRes=true;
                }
                stmMesCer.close();
                stmMesCer=null;
                rstMesCer.close();
                rstMesCer=null;
            }
        }
        catch(java.sql.SQLException e){
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion isMesCerrado()

    /**
     * Esta funcion permite mayorizar/desmayorizar las cuentas de efectivo de acuerdo al criterio seleccionado.
     * @return true: Si se pudo realizar el proceso.
     * <BR>false: En el caso contrario.
     */
    private boolean procesarCuentasEfectivo(java.sql.Connection con, int intCodEmp, int intAnoCor, int intMesCor)
    {
        String strPer;
        String strFecRan[][];
        boolean blnRes=true;
        double dblSalCta=0.00;
        String strMes="", strAni="", strMesCor, strFecDes, strFecHas;
        int intMes=0;
        
        try
        {
            //butCon.setText("Detener");
            //lblMsgSis.setText("Desmayorizando cuentas de efectivo...");
            
            if (con != null)
            {
               stm = con.createStatement();
               strMesCor = (intMesCor < 10)? "0" + (intMesCor) :"" + (intMesCor);
               strFecDes = "01/01/" + intAnoCor; //dd/mm/aaaa
               strFecHas = "01/" + strMesCor + "/" + intAnoCor; //dd/mm/aaaa
               //Procesar las cuentas de la "Empresa seleccionada".
               strFecRan = objUti.getIntervalosMensualesRangoFechas(strFecDes, strFecHas, "dd/MM/yyyy");
               
               //Desmayorizar
               //------------
               for (int i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCtaEfe";
                       strSQL+=" SET nd_salCta=" + dblSalCta + "";
                       strSQL+=" WHERE co_emp=" + intCodEmp;
                       strSQL+=" AND co_per=" + strPer;
                       stm.executeUpdate(strSQL);
                   }
               }
                    
               //Mayorizar
               //---------
               for (int i = 0; i < strFecRan.length; i++)
               {
                   strPer="" + (objUti.getMes(strFecRan[i][0],"yyyy/MM/dd") + 1);
                   intMes=Integer.parseInt(strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL MES FUE CERRADO
                   strMes=((strPer.length()==1)?"0"+strPer:strPer);
                   strPer="" + objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd") + ((strPer.length()==1)?"0"+strPer:strPer);
                   //VARIABLE USADA PARA VERIFICAR SI MAYORIZA O NO PORQUE EL ANIO FUE CERRADO
                   strAni=""+objUti.getAnio(strFecRan[i][0],"yyyy/MM/dd");

                   if (! isMesCerrado(con, intCodEmp, strMes, strAni))
                   {
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+=" update tbm_salctaEfe";
                       strSQL+=" set nd_salCta=x.nd_salcta from (";
                       strSQL+=" SELECT b1.co_emp, b1.co_cta, " + strPer + " AS co_per, b2.nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS b1";
                       strSQL+=" LEFT OUTER JOIN (";
                       //inicio de lo nuevo
                       //strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta";
                       strSQL+=" SELECT a1.co_emp, a2.co_cta, SUM( (case when a2.nd_monDeb is null then 0 else a2.nd_monDeb end) - (case when a2.nd_monHab is null then 0 else a2.nd_monHab end) ) AS nd_salCta";
                       strSQL+=" FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ";
                       strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                       strSQL+=" INNER JOIN (";
                       strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                       strSQL+=" 	FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ";
                       strSQL+=" 	ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.st_reg='A'";
                       strSQL+=" AND (a1.fe_dia BETWEEN '" + strFecRan[i][0] + "' AND '" + strFecRan[i][1] + "')";
                       strSQL+=" 	AND a2.co_cta IN ";
                       strSQL+="                 (SELECT co_cta FROM tbm_plaCta WHERE co_emp=" + intCodEmp + " AND st_reg NOT IN('I','E') AND st_ctaFluEfe='S')";
                       strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                       strSQL+=" 	ORDER BY a1.co_tipDoc, a1.co_dia";
                       strSQL+=" ) as x";
                       strSQL+=" ON a1.co_emp=x.co_emp AND a1.co_loc=x.co_loc AND a1.co_tipDoc=x.co_tipDoc AND a1.co_dia=x.co_dia";
                       strSQL+=" INNER JOIN tbm_plaCta AS y";
                       strSQL+=" ON a1.co_emp=y.co_emp AND a2.co_cta=y.co_cta";
                       //strSQL+=" WHERE y.st_ctaFluEfe='N'";
                       strSQL+=" GROUP BY a1.co_emp, a2.co_cta";
                       //fin de lo nuevo
                       strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)";
                       strSQL+=" WHERE b1.co_emp=" + intCodEmp;
                       strSQL+=" ORDER BY b1.co_emp, b1.co_cta";
                       strSQL+=" ) as x";
                       strSQL+=" WHERE x.co_emp=tbm_salctaEfe.co_emp AND x.co_cta=tbm_salctaEfe.co_cta AND x.co_per=tbm_salctaEfe.co_per";
                       //System.out.println("INSERT: " +strSQL);
                       stm.executeUpdate(strSQL);
                       //Armar la sentencia SQL.
                       strSQL="";
                       strSQL+="UPDATE tbm_salCtaEfe";
                       strSQL+=" SET nd_salCta=(CASE WHEN tbm_salCtaEfe.nd_salCta IS NULL THEN 0 ELSE tbm_salCtaEfe.nd_salCta END) + b1.nd_salCta";
                       strSQL+=" FROM (";
                       strSQL+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                       strSQL+=" FROM tbm_plaCta AS a1";
                       strSQL+=" INNER JOIN tbm_salCtaEfe AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                       strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                       strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                       strSQL+=" AND a1.tx_niv1 IN ('4', '5', '6', '7', '8')";
                       strSQL+=" AND a2.co_per=" + strPer;
                       strSQL+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                       strSQL+=" ) AS b1";
                       strSQL+=" WHERE tbm_salCtaEfe.co_emp=b1.co_emp AND tbm_salCtaEfe.co_cta=b1.co_cta AND tbm_salCtaEfe.co_per=b1.co_per";
                       //System.out.println("UPDATE: " +strSQL);
                       stm.executeUpdate(strSQL);
                       for (int j = 6; j > 1; j--)
                       {
                           //Armar la sentencia SQL.
                           strSQL="";
                           strSQL+="UPDATE tbm_salCtaEfe";
                           strSQL+=" SET nd_salCta=b1.nd_salCta";
                           strSQL+=" FROM (";
                           strSQL+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + strPer + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                           strSQL+=" FROM tbm_plaCta AS a1";
                           strSQL+=" INNER JOIN tbm_salCtaEfe AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                           strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                           strSQL+=" AND a1.ne_niv=" + j;
                           strSQL+=" AND a2.co_per=" + strPer;
                           strSQL+=" GROUP BY a1.co_emp, a1.ne_pad";
                           strSQL+=" ) AS b1";
                           strSQL+=" WHERE tbm_salCtaEfe.co_emp=b1.co_emp AND tbm_salCtaEfe.co_cta=b1.co_cta AND tbm_salCtaEfe.co_per=b1.co_per";
                           //System.out.println("UPDATE EN FOR: " +strSQL);
                           stm.executeUpdate(strSQL);
                       }
                   } //if (! isMesCerrado(strMes, strAni))
               } //for (int i = 0; i < strFecRan.length; i++)
                
                stm.close();
                stm=null;
                
                //lblMsgSis.setText("Listo");
                //pgrSis.setValue(0);
                //butCon.setText("Consultar");
            } //if (con != null)
        } //try
        
        catch (java.sql.SQLException e){
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion procesarCuentasEfectivo()
	
	
    
	/**
     * Metodo utilizado para regenerar el diario en cobro de cheques 
     * para facturas que se estan cobrando de diferentes local al logoneado
     * christian mateo     * 
     * @param codigoTipoDocumento tipo de documento
     * @param valorDebe valor del debe cta diferente de la de cliente
     * @return boolean
     */
    public boolean reestructurargenerarDiarioCheques(int codigoTipoDocumento, double valorDebe, HashMap hshmp)
    {
        boolean blnRes=true;
        double valorHaber=0d;
        try
        {
            switch (bytDiaGen)
            {
                case 0: //Diario no generado.
                case 1:
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null)
                    {
                        stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta";
                        strSQL+=" FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2";
                        strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a1.co_tipDoc=" + codigoTipoDocumento;
                        rst=stm.executeQuery(strSQL);
                        //Limpiar vector de datos.
                        vecDat.clear();
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                            vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_codCta"));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("tx_desLar"));
                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            /*else
                            {
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" + valorHaber);
                            }*/
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);

                            
                            vecDat.add(vecReg);
                        }
                        Iterator it=hshmp.entrySet().iterator();
                        valorHaber=0;
                        while (it.hasNext()) { 
                            Map.Entry e = (Map.Entry)it.next(); 
                            ArrayList lstValCta=(ArrayList)e.getValue();
//                            System.out.println(e.getKey() + " " + e.getValue()); 

                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_CTA,e.getKey());
                            vecReg.add(INT_TBL_DAT_NUM_CTA,(String)lstValCta.get(0));
                            vecReg.add(INT_TBL_DAT_BUT_CTA,"");
                            vecReg.add(INT_TBL_DAT_NOM_CTA,(String)lstValCta.get(1));
/*                            if (rst.getString("tx_natCta").equals("D"))
                            {
                                vecReg.add(INT_TBL_DAT_DEB,"" + valorDebe);
                                vecReg.add(INT_TBL_DAT_HAB,null);
                            }
                            else
                            {*/
                                vecReg.add(INT_TBL_DAT_DEB,null);
                                vecReg.add(INT_TBL_DAT_HAB,"" +  ((BigDecimal)lstValCta.get(2)).doubleValue());
                            //}
                            vecReg.add(INT_TBL_DAT_REF,null);
                            vecReg.add(INT_TBL_DAT_EST_CON,null);

                            
                            vecDat.add(vecReg);
                            valorHaber +=((BigDecimal)lstValCta.get(2)).doubleValue();
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
                        //Almacenar el monto del debe y haber en variables globales.
                        dblMonDeb=valorDebe;
                        dblMonHab=valorHaber;
                        
                        bdeMonDeb=new BigDecimal(valorDebe);
                        bdeMonHab=new BigDecimal(valorHaber);
                        
                        
                        txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
                        //Indicar que el asiento de diario ha sido generado.
                        bytDiaGen=1;
                    }
                    break;
//                case 1: //Diario generado por el Sistema.
//                    objTblMod.setValueAt("" + valorDebe, 0, INT_TBL_DAT_DEB);
//                    objTblMod.setValueAt("" + valorHaber, 1, INT_TBL_DAT_HAB);
//                    //Almacenar el monto del debe y haber en variables globales.
//                    dblMonDeb=valorDebe;
//                    dblMonHab=valorHaber;
//                    
//                    bdeMonDeb=new BigDecimal(valorDebe);
//                    bdeMonHab=new BigDecimal(valorHaber);
//                    
//                    txtDif.setText("" + objUti.redondearBigDecimal((bdeMonDeb.subtract(bdeMonHab)),objParSis.getDecimalesBaseDatos()));
//                    //Indicar que el asiento de diario ha sido generado.
//                    bytDiaGen=1;
//                    break;
//                case 2: //Diario generado por el usuario.
//                    break;
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
	
	
}