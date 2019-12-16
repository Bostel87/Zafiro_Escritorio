/*
 * ZafRptSis.java
 *
 * Created on 6 de enero de 2007, 10:47 PM
 */

package Librerias.ZafRptSis;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.print.PrintService;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

/**
 * Esta clase crea un cuadro de diálogo que presenta los reportes que tiene asignado el usuario especificado.
 * Consideraciones de acuerdo al usuario:
 * <UL>
 * <LI>Si el usuario es "admin" se presentan los reportes asignados al programa. Es decir, se utiliza la tabla "tbr_rptSisPrg".
 * <LI>Si es otro usuario se presentan los reportes asignados al usuario. Es decir, se utiliza la tabla "tbr_rptSisUsr".
 * </UL>
 * <BR>Consideraciones generales:
 * <UL>
 * <LI>El listado de reportes sólo se carga la primera vez que se invoca la función "cargarListadoReportes".
 * De ahí en adelante trabajará con los datos que ya se encuentran cargados. El motivo por el
 * cual ésta función carga los datos sólo una vez es para evitar estar realizando un acceso a
 * la Base de Datos de forma innecesaria. Si por algún motivo es necesario volver a conectarse
 * a la Base de Datos para actualizar el listado de reportes se deberá utilizar el botón "Consultar".
 * <LI>El cuadro de diálogo sólo se presenta si hay más de un reporte.
 * Es decir, si sólo hay un reporte y éste está seleccionado no se presenta el cuadro
 * de diálogo sino que automáticamente se procede a realizar la operación especificada
 * (Impresión directa, Impresión directa (Cuadro de dialogo de impresión), Vista preliminar)
 * <LI>El campo "objParSis.getTipoRutaReportes()" determina de donde se debe obtener la parte inicial de la ruta
 * del reporte. Se pueden presentar los siguientes casos:
 *      <UL>
 *      <LI>Si es "A" el sistema utiliza los campos "tbm_rptSis.tx_rutAbsRpt" + "tbm_rptSis.tx_rutRelRpt".
 *      <LI>Si es "R" el sistema utiliza el directorio donde se encuentra el archivo "Zafiro.jar" + "tbm_rptSis.tx_rutRelRpt".
 *      </UL>
 * </UL>
 * <BR>Ejemplo de como utilizar ésta clase:
 * <BR>"objRptSis" es la instancia de "ZafRptSis".
 * <PRE>
 *           private boolean generarRpt(int intTipRpt)
 *           {
 *               String strRutRpt, strNomRpt, strFecHorSer;
 *               int i, intNumTotRpt;
 *               boolean blnRes=true;
 *               try
 *               {
 *                   objRptSis.cargarListadoReportes(conCab);
 *                   objRptSis.show();
 *                   if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
 *                   {
 *                       //Obtener la fecha y hora del servidor.
 *                       datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
 *                       if (datFecAux==null)
 *                           return false;
 *                       strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
 *                       datFecAux=null;
 *                       intNumTotRpt=objRptSis.getNumeroTotalReportes();
 *                       for (i=0;i&lt;intNumTotRpt;i++)
 *                       {
 *                           if (objRptSis.isReporteSeleccionado(i))
 *                           {
 *                               switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
 *                               {
 *                                   case 19:
 *                                   default:
 *                                       strRutRpt=objRptSis.getRutaReporte(i);
 *                                       strNomRpt=objRptSis.getNombreReporte(i);
 *                                       //Inicializar los parametros que se van a pasar al reporte.
 *                                       java.util.Map mapPar=new java.util.HashMap();
 *                                       mapPar.put("SUBREPORT_DIR", strRutRpt);
 *                                       mapPar.put("co_emp", Short.valueOf(rstCab.getString("co_emp")));
 *                                       mapPar.put("co_loc", Short.valueOf(rstCab.getString("co_loc")));
 *                                       mapPar.put("co_tipDoc", Short.valueOf(rstCab.getString("co_tipDoc")));
 *                                       mapPar.put("co_doc", Integer.valueOf(rstCab.getString("co_doc")));
 *                                       mapPar.put("strCamAudRpt", this.getClass().getName() + "   " + strNomRpt + "   " + objParSis.getNombreUsuario() + "   " + strFecHorSer);
 *                                       objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
 *                                       break;
 *                               }
 *                           }
 *                       }
 *                   }
 *               }
 *               catch (Exception e)
 *               {
 *                   blnRes=false;
 *                   objUti.mostrarMsgErr_F1(this, e);
 *               }
 *               return blnRes;
 *           }
 * </PRE>
 * Nota.- Para cargar el listado de reportes se puede utilizar cualquiera de los siguientes métodos:
 * <UL>
 * <LI>objRptSis.cargarListadoReportes();
 * <LI>objRptSis.cargarListadoReportes(conCab);
 * <LI>objRptSis.cargarListadoReportes(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoMenu(), objParSis.getCodigoUsuario());
 * <LI>objRptSis.cargarListadoReportes(conCab, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoMenu(), objParSis.getCodigoUsuario());
 * </UL>
 * @author Eddye Lino
 */
public class ZafRptSis extends javax.swing.JDialog
{
    //Constantes: Columnas del JTable.
    public static final int INT_OPC_CAN=0;                     /**Un valor para getOpcionSeleccionada: Indica "Botón Cancelar".*/
    public static final int INT_OPC_ACE=1;                     /**Un valor para getOpcionSeleccionada: Indica "Botón Aceptar".*/
    
    static final int INT_TBL_DAT_LIN=0;                        //Línea.
    static final int INT_TBL_DAT_CHK=1;                        //Casilla de verificación.
    static final int INT_TBL_DAT_COD_RPT=2;                    //Código del reporte.
    static final int INT_TBL_DAT_DEC_RPT=3;                    //Descripción corta del reporte.
    static final int INT_TBL_DAT_DEL_RPT=4;                    //Descripción larga del reporte.
    static final int INT_TBL_DAT_RUT_ABS_RPT=5;                //Ruta absoluta del reporte.
    static final int INT_TBL_DAT_RUT_REL_RPT=6;                //Ruta relativa del reporte.
    static final int INT_TBL_DAT_NOM_RPT=7;                    //Nombre del reporte.
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblEdi objTblEdi;                               //Editor: Editor del JTable.
    private ZafTblCelRenChk objTblCelRenChk;                   //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                   //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                         //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private int intOpcSelDlg;                                  //Opción seleccionada en el JDialog.
    private int intCodEmp;                                     //Código de la empresa.
    private int intCodLoc;                                     //Código del local.
    private int intCodMnu;                                     //Código del tipo de documento.
    private int intCodUsr;                                     //Código del docuemnto.
    private boolean blnConRecNul;                              //Determina si la conexión recibida es null.
    private boolean blnConEje;                                 //Determina si la consulta ya fue ejecutada.
    
    /** Creates new form ZafRptSis */
    public ZafRptSis(java.awt.Frame parent, boolean modal, ZafParSis obj)
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        intOpcSelDlg=INT_OPC_CAN;
        blnConEje=false;
        if (!configurarFrm())
            exitForm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnObs = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panRpt.setLayout(new java.awt.BorderLayout());

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

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnObs.setPreferredSize(new java.awt.Dimension(4, 36));

        txaObs.setEditable(false);
        txaObs.setLineWrap(true);
        spnObs.setViewportView(txaObs);

        panRpt.add(spnObs, java.awt.BorderLayout.SOUTH);

        panFrm.add(panRpt, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Consulta los reportes asignados al programa");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panFrm.add(panBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(400, 200));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        intOpcSelDlg=INT_OPC_CAN;
        dispose();
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        intOpcSelDlg=INT_OPC_ACE;
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg=INT_OPC_CAN;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        intOpcSelDlg=INT_OPC_CAN;
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs;
    // End of variables declaration//GEN-END:variables
 
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Reportes del Sistema";
            this.setTitle(strAux + " v1.1");
            lblTit.setText(strAux);
            //Configurar los JTables.
            configurarTblDat();
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
            vecCab=new Vector(8);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_RPT,"Cód.Rpt.");
            vecCab.add(INT_TBL_DAT_DEC_RPT,"Nombre");
            vecCab.add(INT_TBL_DAT_DEL_RPT,"Descripción");
            vecCab.add(INT_TBL_DAT_RUT_ABS_RPT,"Rut.Abs.Rpt.");
            vecCab.add(INT_TBL_DAT_RUT_REL_RPT,"Rut.Rel.Rpt.");
            vecCab.add(INT_TBL_DAT_NOM_RPT,"Nom.Rpt.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEC_RPT).setPreferredWidth(324);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_COD_RPT).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_RPT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_RPT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_RPT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_RPT).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_DEL_RPT).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_RPT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_RPT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_RPT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DEL_RPT).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_RUT_ABS_RPT).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_ABS_RPT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_ABS_RPT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_ABS_RPT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_ABS_RPT).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_RUT_REL_RPT).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_REL_RPT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_REL_RPT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_REL_RPT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_RUT_REL_RPT).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_NOM_RPT).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RPT).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RPT).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RPT).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_RPT).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
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
     * Esta función permite cargar el listado de reportes asignado al usuario.
     * @return true: Si se pudo cargar el listado de reportes.
     * <BR>false: En el caso contrario.
     * <BR><BR>Nota.- El listado de reportes sólo se carga la primera vez que se invoca a ésta función.
     * De ahí en adelante trabajará con los datos que ya se encuentran cargados. El motivo por el
     * cual ésta función carga los datos sólo una vez es para evitar estar realizando un acceso a
     * la Base de Datos de forma innecesaria. Si por algún motivo es necesario volver a conectarse
     * a la Base de Datos para actualizar el listado de reportes se deberá utilizar el botón "Consultar".
     */
    public boolean cargarListadoReportes()
    {
        return cargarListadoReportes(null);
    }

    /**
     * Esta función permite cargar el listado de reportes asignado al usuario.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @return true: Si se pudo cargar el listado de reportes.
     * <BR>false: En el caso contrario.
     * <BR><BR>Nota.- El listado de reportes sólo se carga la primera vez que se invoca a ésta función.
     * De ahí en adelante trabajará con los datos que ya se encuentran cargados. El motivo por el
     * cual ésta función carga los datos sólo una vez es para evitar estar realizando un acceso a
     * la Base de Datos de forma innecesaria. Si por algún motivo es necesario volver a conectarse
     * a la Base de Datos para actualizar el listado de reportes se deberá utilizar el botón "Consultar".
     */
    public boolean cargarListadoReportes(java.sql.Connection conexion)
    {
        boolean blnRes=true;
        try
        {
            //Consultar sólo si la consulta no ha sido ejecutada.
            if (!blnConEje)
            {
                this.intCodEmp=objParSis.getCodigoEmpresa();
                this.intCodLoc=objParSis.getCodigoLocal();
                this.intCodMnu=objParSis.getCodigoMenu();
                this.intCodUsr=objParSis.getCodigoUsuario();
                cargarDetReg(conexion);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el listado de reportes asignado al usuario.
     * @param codigoEmpresa El código de la empresa.
     * @param codigoLocal El código del local.
     * @param codigoMenu El código del menú.
     * @param codigoUsuario El código del usuario.
     * @return true: Si se pudo cargar el listado de reportes.
     * <BR>false: En el caso contrario.
     * <BR><BR>Nota.- El listado de reportes sólo se carga la primera vez que se invoca a ésta función.
     * De ahí en adelante trabajará con los datos que ya se encuentran cargados. El motivo por el
     * cual ésta función carga los datos sólo una vez es para evitar estar realizando un acceso a
     * la Base de Datos de forma innecesaria. Si por algún motivo es necesario volver a conectarse
     * a la Base de Datos para actualizar el listado de reportes se deberá utilizar el botón "Consultar".
     */
    public boolean cargarListadoReportes(int codigoEmpresa, int codigoLocal, int codigoMenu, int codigoUsuario)
    {
        boolean blnRes=true;
        try
        {
            //Consultar sólo si la consulta no ha sido ejecutada.
            if (!blnConEje)
            {
                this.intCodEmp=codigoEmpresa;
                this.intCodLoc=codigoLocal;
                this.intCodMnu=codigoMenu;
                this.intCodUsr=codigoUsuario;
                cargarDetReg(null);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el listado de reportes asignado al usuario.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param codigoEmpresa El código de la empresa.
     * @param codigoLocal El código del local.
     * @param codigoMenu El código del menú.
     * @param codigoUsuario El código del usuario.
     * @return true: Si se pudo cargar el listado de reportes.
     * <BR>false: En el caso contrario.
     * <BR><BR>Nota.- El listado de reportes sólo se carga la primera vez que se invoca a ésta función.
     * De ahí en adelante trabajará con los datos que ya se encuentran cargados. El motivo por el
     * cual ésta función carga los datos sólo una vez es para evitar estar realizando un acceso a
     * la Base de Datos de forma innecesaria. Si por algún motivo es necesario volver a conectarse
     * a la Base de Datos para actualizar el listado de reportes se deberá utilizar el botón "Consultar".
     */
    public boolean cargarListadoReportes(java.sql.Connection conexion, int codigoEmpresa, int codigoLocal, int codigoMenu, int codigoUsuario)
    {
        boolean blnRes=true;
        try
        {
            //Consultar sólo si la consulta no ha sido ejecutada.
            if (!blnConEje)
            {
                this.intCodEmp=codigoEmpresa;
                this.intCodLoc=codigoLocal;
                this.intCodMnu=codigoMenu;
                this.intCodUsr=codigoUsuario;
                cargarDetReg(conexion);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(java.sql.Connection conexion)
    {
        boolean blnRes=true;
        try
        {
            //Obtener la conexión sólo si es necesario.
            if (conexion==null)
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                blnConRecNul=true;
            }
            else
            {
                con=conexion;
                blnConRecNul=false;
            }
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los reportes.
                if (intCodUsr==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutAbsRpt, a1.tx_rutRelRpt, a1.tx_nomRpt, 'A' AS st_reg";
                    strSQL+=" FROM tbm_rptSis AS a1";
                    strSQL+=" INNER JOIN tbr_rptSisPrg AS a2 ON (a1.co_rpt=a2.co_rpt)";
                    strSQL+=" WHERE a2.co_mnu=" + intCodMnu;
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_rpt";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutAbsRpt, a1.tx_rutRelRpt, a1.tx_nomRpt, a2.st_reg";
                    strSQL+=" FROM tbm_rptSis AS a1";
                    strSQL+=" INNER JOIN tbr_rptSisUsr AS a2 ON (a1.co_rpt=a2.co_rpt)";
                    strSQL+=" WHERE a2.co_emp=" + intCodEmp;
                    strSQL+=" AND a2.co_loc=" + intCodLoc;
                    strSQL+=" AND a2.co_mnu=" + intCodMnu;
                    strSQL+=" AND a2.co_usr=" + intCodUsr;
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" AND a2.st_reg IN ('A','S')";
                    strSQL+=" ORDER BY a2.ne_ord";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,null);
                    vecReg.add(INT_TBL_DAT_COD_RPT,rst.getString("co_rpt"));
                    vecReg.add(INT_TBL_DAT_DEC_RPT,rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DEL_RPT,rst.getString("tx_desLar"));
                    strAux=rst.getString("tx_rutAbsRpt");
                    vecReg.add(INT_TBL_DAT_RUT_ABS_RPT,(strAux==null)?"":strAux);
                    strAux=rst.getString("tx_rutRelRpt");
                    vecReg.add(INT_TBL_DAT_RUT_REL_RPT,(strAux==null)?"":strAux);
                    vecReg.add(INT_TBL_DAT_NOM_RPT,rst.getString("tx_nomRpt"));
                    if (rst.getString("st_reg").equals("S"))
                        vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                //Cerrar la conexión sólo si la conexión se la realizó en ésta función.
                if (blnConRecNul)
                {
                    con.close();
                    con=null;
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Cambiar el estado de la variable que indica que la consulta ya fue ejecutada.
                blnConEje=true;

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
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar.
     * <LI>1: Click en el botón Aceptar.
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getOpcionSeleccionada()
    {
        return intOpcSelDlg;
    }
    
    /**
     * Esta función obtiene el número total de reportes. Se incluyen todos los reportes.
     * Es decir, los que están seleccionados y los que no lo están.
     * @return El número total de reportes.
     */
    public int getNumeroTotalReportes()
    {
        return objTblMod.getRowCountTrue();
    }
    
    /**
     * Esta función determina si el reporte que se encuentra en la fila especificada está seleccionado.
     * Es decir, si la casilla de verificación del reporte que se encuentra en la fila especificada está marcada.
     * @param fila La fila especificada.
     * @return true: Si el reporte especificado está seleccionado.
     * <BR>false: En el caso contrario.
     */
    public boolean isReporteSeleccionado(int fila)
    {
        return objTblMod.isChecked(fila, INT_TBL_DAT_CHK);
    }

    /**
     * Esta función obtiene el código del reporte que se encuentra en la fila especificada.
     * @param fila La fila especificada.
     * @return El código del reporte especificado.
     */
    public String getCodigoReporte(int fila)
    {
        return objTblMod.getValueAt(fila, INT_TBL_DAT_COD_RPT).toString();
    }
    
    /**
     * Esta función obtiene la ruta del reporte que se encuentra en la fila especificada.
     * @param fila La fila especificada.
     * @return La ruta del reporte especificado.
     * <BR>Nota.- El campo "objParSis.getTipoRutaReportes()" determina de donde se debe obtener la parte inicial de la ruta
     * del reporte. Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si es "A" el sistema utiliza los campos "tbm_rptSis.tx_rutAbsRpt" + "tbm_rptSis.tx_rutRelRpt".
     * <LI>Si es "R" el sistema utiliza el directorio donde se encuentra el archivo "Zafiro.jar" + "tbm_rptSis.tx_rutRelRpt".
     * </UL>
     */
    public String getRutaReporte(int fila)
    {
        if (objParSis.getTipoRutaReportes().equals("R"))
            return objParSis.getDirectorioSistema() + objTblMod.getValueAt(fila, INT_TBL_DAT_RUT_REL_RPT).toString();
        else
            return objTblMod.getValueAt(fila, INT_TBL_DAT_RUT_ABS_RPT).toString() + objTblMod.getValueAt(fila, INT_TBL_DAT_RUT_REL_RPT).toString();
    }
    
    /**
     * Esta función obtiene la ruta absoluta del reporte que se encuentra en la fila especificada.
     * @param fila La fila especificada.
     * @return La ruta del absoluta reporte especificado.
     * <BR>Nota.- El campo "objParSis.getTipoRutaReportes()" determina de donde se debe obtener la parte inicial de la ruta
     * absoluta del reporte. Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si es "A" el sistema utiliza los campos "tbm_rptSis.tx_rutAbsRpt"
     * <LI>Si es "R" el sistema utiliza el directorio donde se encuentra el archivo "Zafiro.jar" 
     * </UL>
     */
    public String getRutaAbsolutaReporte(int fila)
    {
        if (objParSis.getTipoRutaReportes().equals("R"))
            return objParSis.getDirectorioSistema();
        else
            return objTblMod.getValueAt(fila, INT_TBL_DAT_RUT_ABS_RPT).toString();
    }    
    
    /**
     * Esta función obtiene la ruta relativa del reporte que se encuentra en la fila especificada.
     * @param fila La fila especificada.
     * @return La ruta del relativa reporte especificado.
     */
    public String getRutaRelativaReporte(int fila)
    {
        return objTblMod.getValueAt(fila, INT_TBL_DAT_RUT_REL_RPT).toString();
    }    
    
    /**
     * Esta función obtiene el nombre del reporte que se encuentra en la fila especificada.
     * @param fila La fila especificada.
     * @return El nombre del reporte especificado.
     */
    public String getNombreReporte(int fila)
    {
        return objTblMod.getValueAt(fila, INT_TBL_DAT_NOM_RPT).toString();
    }
    
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param rutaReporte La ruta del reporte a generar.
     * @param nombreReporte El nombre del reporte a generar.
     * @param parametros Los parámetros que se van a pasar al reporte a generar.
     * @param tipoReporte El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    public boolean generarReporte(String rutaReporte, String nombreReporte, java.util.Map parametros, int tipoReporte)
    {
        boolean blnRes=true;
        try
        {
            //Generar el reporte.
            //Obtener la conexión sólo si es necesario.
            if (con==null || con.isClosed())
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con!=null)
            {
//                //Compilar el reporte.
//                net.sf.jasperreports.engine.JasperReport objJasRep=net.sf.jasperreports.engine.JasperCompileManager.compileReport(rutaReporte + nombreReporte);
                //Cargar el reporte.
                net.sf.jasperreports.engine.JasperReport objJasRep=(net.sf.jasperreports.engine.JasperReport)net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile(rutaReporte + nombreReporte);
                //Llenar el reporte.
                net.sf.jasperreports.engine.JasperPrint objJasPri=net.sf.jasperreports.engine.JasperFillManager.fillReport(objJasRep, parametros, con);
                switch (tipoReporte)
                {
                    case 0: //Impresión directa.
                        net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                        break;
                    case 1: //Impresión directa (Cuadro de dialogo de impresión).
                        net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, true);
                        break;
                    case 2: //Vista preliminar.
                        net.sf.jasperreports.view.JasperViewer.viewReport(objJasPri, false);
                        break;
                }
                //Cerrar la conexión sólo si la conexión se la realizó en ésta función.
                if (blnConRecNul)
                {
                    con.close();
                    con=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (net.sf.jasperreports.engine.JRException e)
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
     * Esta función muestra el cuadro de dialogo sólo si es necesario.
     * El cuadro de diálogo sólo se presenta si hay más de un reporte.
     * Es decir, si sólo hay un reporte y éste está seleccionado no se presenta el cuadro
     * de diálogo sino que automáticamente se procede a realizar la operación especificada
     * (Impresión directa, Impresión directa (Cuadro de dialogo de impresión), Vista preliminar).
     * @deprecated Reeemplazado por el método <I>setVisible(boolean)</I>
     * @see #setVisible(boolean visible)
     */
    public void show()
    {
        if (objTblMod.getRowCountTrue()==1)
        {
            if (objTblMod.isChecked(0, INT_TBL_DAT_CHK))
            {
                butAceActionPerformed(null);
            }
            else
            {
                super.show();
            }
        }
        else
        {
            super.show();
        }
    }

    /**
     * Esta función muestra el cuadro de dialogo sólo si es necesario.
     * El cuadro de diálogo sólo se presenta si hay más de un reporte.
     * Es decir, si sólo hay un reporte y éste está seleccionado no se presenta el cuadro
     * de diálogo sino que automáticamente se procede a realizar la operación especificada
     * (Impresión directa, Impresión directa (Cuadro de dialogo de impresión), Vista preliminar).
     */
    public void setVisible(boolean visible)
    {
        if (objTblMod.getRowCountTrue()==1)
        {
            if (objTblMod.isChecked(0, INT_TBL_DAT_CHK))
            {
                butAceActionPerformed(null);
            }
            else
            {
                super.setVisible(visible);
            }
        }
        else
        {
            super.setVisible(visible);
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
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Consultar".
                    if (!cargarDetReg(con))
                    {
                        //Inicializar objetos si no se pudo cargar los datos.
                        butCon.setText("Consultar");
                    }
                    //Establecer el foco en el JTable sólo cuando haya datos.
                    if (tblDat.getRowCount()>0)
                    {
                        tblDat.setRowSelectionInterval(0, 0);
                        tblDat.requestFocus();
                    }
                    break;
                case 1: //Botón "Aceptar".
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
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CHK:
                    strMsg="Reporte(s) a utilizar";
                    break;
                case INT_TBL_DAT_DEC_RPT:
                    strMsg="Nombre del reporte";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {
                strAux=tblDat.getValueAt(intMax,INT_TBL_DAT_DEL_RPT)==null?"":tblDat.getValueAt(intMax,INT_TBL_DAT_DEL_RPT).toString();
                txaObs.setText(strAux);
            }
        }
    }
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param rutaReporte La ruta del reporte a generar.
     * @param nombreReporte El nombre del reporte a generar.
     * @param parametros Los parámetros que se van a pasar al reporte a generar.
     * @param tipoReporte El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    public boolean generarReporteA5(String rutaReporte, String nombreReporte, java.util.Map parametros, int tipoReporte)
    {
        boolean blnRes=true;
        try
        {
            //Generar el reporte.
            //Obtener la conexión sólo si es necesario.
            if (con==null || con.isClosed())
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con!=null)
            {
                String DIRECCION_REPORTE_GUIA = "";
                String strNumImp= "";
                final String strRutaTemp= "/tmp/";
                final String DIRECCION_REPORTE_GUIA_VP;
                final String strNumImpVp;
                final java.util.Map parametrosVp;
                final String nombreReporteVp;
                final String strDireccionNuevaVp;
                DIRECCION_REPORTE_GUIA = rutaReporte + nombreReporte;
                //System.out.println(DIRECCION_REPORTE_GUIA);
                String strDireccionNueva = strRutaTemp+ nombreReporte;
                strDireccionNuevaVp=strDireccionNueva;
                DIRECCION_REPORTE_GUIA_VP=DIRECCION_REPORTE_GUIA;
                strNumImp = parametros.get("codTipDoc").toString()+parametros.get("codDoc").toString();
                strNumImpVp=strNumImp;
                parametrosVp=parametros;
                nombreReporteVp=nombreReporte;
                File filCarp =  new File(strRutaTemp);
                if (!filCarp.exists()) { 
                    filCarp.mkdir();
                }
                switch (tipoReporte)
                {
                    case 0: //Impresión directa.
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"), false);
                        break;
                    case 1: //Impresión directa (Cuadro de dialogo de impresión).
                        String fileName   = DIRECCION_REPORTE_GUIA;
                        String fileNumber = strNumImp;
                        String printerName = "";//Ej:sistemas_inmaconsa";
                        String jobName = "PDF Print Job";
                        String DireccionExpo = strDireccionNueva;
                        printPDF(fileName,fileNumber, printerName, jobName,parametros,con,DireccionExpo);
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, true);
                        break;
                    case 2: //Vista preliminar.
                        //JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
                        //JasperExportManager.exportReportToPdfFile(reportGuiaRem, DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "vp.pdf"));
                        File pdfA4 = null;
            File pdfA5=null;
            
            JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
            int intCantPaginas = 0;
            if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                intCantPaginas = reportGuiaRem.getPages().size();
            }
            JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
            pdfA4 = new File(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
            Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
            Rectangle recA5 = new Rectangle(PageSize.A5);
            float floRecA5 = recA5.getHeight() - 35;
            Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
            docu.setPageSize(recA52);
            PdfReader reader = new PdfReader(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
            FileOutputStream fos = new FileOutputStream(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
            PdfWriter writer = PdfWriter.getInstance(docu, fos);
            pdfA5 = new File(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
            docu.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page = null;
            //Se reduce la medida escalandolo de a4 a a5
            float Scale = 0.67f;
            for (int i = 0; i < intCantPaginas; i++) {
                if (i>0) {
                          docu.newPage();
                }
                            page = writer.getImportedPage(reader, i+1);
                             cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
            docu.close();
            pdfA4.delete();
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(
                        System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                // read/store the font cache.
                //controller.
                ResourceBundle messageBundle = ResourceBundle.getBundle(
                        PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_WIDTH, "148");
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_HEIGHT, "210");
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
                SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
                //System.out.println(factory.buildSaveAsFileButton().get);
                
                // add interactive mouse link annotation support via callback
                controller.getDocumentViewController().setAnnotationCallback(
                //new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
                new MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame();
                //applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                // Now that the GUI is all in place, we can try openning a PDF
                controller.openDocument(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                applicationFrame.addWindowListener(new WindowAdapter() {
                 @Override
                 public void windowClosing(WindowEvent e) {
                  File file = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + "vp.pdf"));
                                                if (file.exists()) {
                                                    file.delete();
                                                    file=null;
                                                }
                  File file2 = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + ".pdf"));
                                                if (file2.exists()) {
                                                    file2.delete();
                                                    file2=null;
                                                }
                 }
                 });

                // add the window event callback to dispose the controller and
                // currently open document.
                applicationFrame.addWindowListener(controller);

                // show the component
                applicationFrame.pack();
                applicationFrame.setVisible(true);              
                        break;
                }
                if (blnConRecNul)
                {
                    con.close();
                    con=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (net.sf.jasperreports.engine.JRException e)
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
        return blnRes;
    }
    /**
     * Metodo para imprimir directamente
     * @param filePath
     * @param fileNumber
     * @param printerName
     * @param jobName
     * @param parametros
     * @param con 
     */
    public void printPDF(String filePath,String fileNumber, String printerName, String jobName, java.util.Map parametros, Connection con,String strDireccionExportar) {
        FileInputStream fileInputStream = null;
        File pdfA4 = null;
        File pdfA5=null;
        try {
            JasperPrint reportGuiaRem = JasperFillManager.fillReport(filePath, parametros, con);
            int intCantPaginas = 0;
            if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                intCantPaginas = reportGuiaRem.getPages().size();
            }
            JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            pdfA4 = new File(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
            Rectangle recA5 = new Rectangle(PageSize.A5);
            float floRecA5 = recA5.getHeight() - 35;
            Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
            docu.setPageSize(recA52);
            PdfReader reader = new PdfReader(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            FileOutputStream fos = new FileOutputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            PdfWriter writer = PdfWriter.getInstance(docu, fos);
            pdfA5 = new File(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            docu.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page = null;
            //Se reduce la medida escalandolo de a4 a a5
            float Scale = 0.67f;
            for (int i = 0; i < intCantPaginas; i++) {
                if (i>0) {
                          docu.newPage();
                }
                            page = writer.getImportedPage(reader, i+1);
                             cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
            docu.close();
            fileInputStream = new FileInputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            byte[] pdfContent = new byte[fileInputStream.available()];
            fileInputStream.read(pdfContent, 0, fileInputStream.available());
            ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
            final PDFFile pdfFile = new PDFFile(buffer);
            PDFPrintPage pages = new PDFPrintPage(pdfFile);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            if (printJob.printDialog()) {//Devuelve true si el usuario decide imprimir
            System.out.println(printJob.getPrintService().getName());
            printJob.getPrintService().getName();
            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            printJob.setJobName(jobName);
            Book book = new Book();
            book.append(pages, pageFormat, pdfFile.getNumPages());
            printJob.setPageable(book);
            Paper paper = new Paper();
            if(System.getProperty("os.name").equals("Linux")){
            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()-200);
            }else{
            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()-90);
            }
            pageFormat.setPaper(paper);
            PrintService[] printServices = PrinterJob.lookupPrintServices();
            for (int count = 0; count < printServices.length; ++count) {
                if (printerName.equalsIgnoreCase(printJob.getPrintService().getName())) {
                    printJob.setPrintService(printServices[count]);
                    break;
                }
            }
            printJob.print();
            }
            fileInputStream.close();
            pdfA4.delete();
            pdfA5.delete();
        } catch (FileNotFoundException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (JRException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (IOException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (DocumentException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (PrinterException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } finally {
            try {
                fileInputStream.close();
                if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            } catch (IOException ex) {
                if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
      }
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param rutaReporte La ruta del reporte a generar.
     * @param nombreReporte El nombre del reporte a generar.
     * @param parametros Los parámetros que se van a pasar al reporte a generar.
     * @param tipoReporte El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    public boolean generarReporteA5Cot(String rutaReporte, String nombreReporte, java.util.Map parametros, int tipoReporte, String strNombrePdf)
    {
        boolean blnRes=true;
        try
        {
            //Generar el reporte.
            //Obtener la conexión sólo si es necesario.
            if (con==null || con.isClosed())
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con!=null)
            {
                String DIRECCION_REPORTE_GUIA = "";
                String strNumImp= "";
                final String strRutaTemp= "/tmp/";
                final String DIRECCION_REPORTE_GUIA_VP;
                final String strNumImpVp;
                final java.util.Map parametrosVp;
                final String nombreReporteVp;
                final String strDireccionNuevaVp;
                DIRECCION_REPORTE_GUIA = rutaReporte + nombreReporte;
                //System.out.println(DIRECCION_REPORTE_GUIA);
                String strDireccionNueva = strRutaTemp+ nombreReporte;
                strDireccionNuevaVp=strDireccionNueva;
                DIRECCION_REPORTE_GUIA_VP=DIRECCION_REPORTE_GUIA;
                strNumImp = strNombrePdf;//parametros.get("codLoc").toString()+parametros.get("co_cot").toString();
                strNumImpVp=strNumImp;
                parametrosVp=parametros;
                nombreReporteVp=nombreReporte;
                File filCarp =  new File(strRutaTemp);
                if (!filCarp.exists()) { 
                    filCarp.mkdir();
                }
                switch (tipoReporte)
                {
                    case 0: //Impresión directa.
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, false);
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"), false);
                        break;
                    case 1: //Impresión directa (Cuadro de dialogo de impresión).
                        String fileName   = DIRECCION_REPORTE_GUIA;
                        String fileNumber = strNumImp;
                        String printerName = "";//Ej:sistemas_inmaconsa";
                        String jobName = "PDF Print Job";
                        String DireccionExpo = strDireccionNueva;
                        printPDFCot(fileName,fileNumber, printerName, jobName,parametros,con,DireccionExpo);
                        //net.sf.jasperreports.engine.JasperPrintManager.printReport(objJasPri, true);
                        break;
                    case 2: //Vista preliminar.
                        //JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
                        //JasperExportManager.exportReportToPdfFile(reportGuiaRem, DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "vp.pdf"));
                        File pdfA4 = null;
            File pdfA5=null;
            
            JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parametros, con);
            int intCantPaginas = 0;
            if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                intCantPaginas = reportGuiaRem.getPages().size();
            }
            //JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
            JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionNueva.replace(".jasper", strNumImp +".pdf"));
            pdfA4 = new File(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
            Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
            Rectangle recA5 = new Rectangle(PageSize.A5);
            float floRecA5 = recA5.getHeight() - 35;
            Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
            docu.setPageSize(recA52);
            PdfReader reader = new PdfReader(strDireccionNueva.replace(".jasper", strNumImp + ".pdf"));
            FileOutputStream fos = new FileOutputStream(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
            PdfWriter writer = PdfWriter.getInstance(docu, fos);
            pdfA5 = new File(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
            docu.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page = null;
            //Se reduce la medida escalandolo de a4 a a5
            float Scale=0.58f;
                        if ((Integer)parametros.get("codEmp")==2) {//Validacion para Castek ya que no aparecia completa la hoja
                            Scale = 0.68f;
                        }else{
                            Scale = 0.58f;
                        }
            for (int i = 0; i < intCantPaginas; i++) {
                if (i>0) {
                          docu.newPage();
                }
                            page = writer.getImportedPage(reader, i+1);
                             cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
            docu.close();
            pdfA4.delete();
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(
                        System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
                // read/store the font cache.
                //controller.
                ResourceBundle messageBundle = ResourceBundle.getBundle(
                        PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_WIDTH, "148");
                properties.set(PropertiesManager.PROPERTY_PRINT_MEDIA_SIZE_HEIGHT, "210");
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
               
                SwingViewBuilder factory = new SwingViewBuilder(controller, properties);
                //factory.buildSaveAsFileButton();
                //System.out.println("HoLAAAAAAAAAAAAAAAAAAAAAAAAA" + controller.);
                // add interactive mouse link annotation support via callback
                controller.getDocumentViewController().setAnnotationCallback(
                //new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
                new MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame();
                //applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                // Now that the GUI is all in place, we can try openning a PDF
                
                controller.openDocument(strDireccionNueva.replace(".jasper", strNumImp + "vp.pdf"));
                applicationFrame.addWindowListener(new WindowAdapter() {
                 @Override
                 public void windowClosing(WindowEvent e) {
                  File file = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + "vp.pdf"));
                                                if (file.exists()) {
                                                    file.delete();
                                                    file=null;
                                                }
                  File file2 = new File(strDireccionNuevaVp.replace(".jasper", strNumImpVp + ".pdf"));
                                                if (file2.exists()) {
                                                    file2.delete();
                                                    file2=null;
                                                }
                 }
                 });

                // add the window event callback to dispose the controller and
                // currently open document.
                applicationFrame.addWindowListener(controller);

                // show the component
                applicationFrame.pack();
                applicationFrame.setVisible(true);              
                        break;
                }
                if (blnConRecNul)
                {
                    con.close();
                    con=null;
                }
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (net.sf.jasperreports.engine.JRException e)
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
        return blnRes;
    }
    /**
     * Metodo para imprimir directamente
     * @param filePath
     * @param fileNumber
     * @param printerName
     * @param jobName
     * @param parametros
     * @param con 
     */
    public void printPDFCot(String filePath,String fileNumber, String printerName, String jobName, java.util.Map parametros, Connection con,String strDireccionExportar) {
        FileInputStream fileInputStream = null;
        File pdfA4 = null;
        File pdfA5=null;
        try {
            JasperPrint reportGuiaRem = JasperFillManager.fillReport(filePath, parametros, con);
            int intCantPaginas = 0;
            if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                intCantPaginas = reportGuiaRem.getPages().size();
            }
            JasperExportManager.exportReportToPdfFile(reportGuiaRem, strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            pdfA4 = new File(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
            Rectangle recA5 = new Rectangle(PageSize.A5);
            float floRecA5 = recA5.getHeight() - 35;
            Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
            docu.setPageSize(recA52);
            PdfReader reader = new PdfReader(strDireccionExportar.replace(".jasper", fileNumber + ".pdf"));
            FileOutputStream fos = new FileOutputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            PdfWriter writer = PdfWriter.getInstance(docu, fos);
            pdfA5 = new File(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            docu.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page = null;
            //Se reduce la medida escalandolo de a4 a a5
            float Scale = 0.63f;
            for (int i = 0; i < intCantPaginas; i++) {
                if (i>0) {
                          docu.newPage();
                }
                            page = writer.getImportedPage(reader, i+1);
                             cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
            docu.close();
            fileInputStream = new FileInputStream(strDireccionExportar.replace(".jasper", fileNumber + "2.pdf"));
            byte[] pdfContent = new byte[fileInputStream.available()];
            fileInputStream.read(pdfContent, 0, fileInputStream.available());
            ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
            final PDFFile pdfFile = new PDFFile(buffer);
            PDFPrintPage pages = new PDFPrintPage(pdfFile);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            
            if (printJob.printDialog()) {//Devuelve true si el usuario decide imprimir
            //System.out.println(printJob.getPrintService().getName());
            //printJob.getPrintService().getName();
            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
            Paper paper = new Paper();
            //Posicion izquierda o derecha
            if(System.getProperty("os.name").equals("Linux")){
            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()-200);
            }else{
            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()-90);
            }
            pageFormat.setPaper(paper);
            printJob.setJobName(jobName);
            Book book = new Book();
            book.append(pages, pageFormat, pdfFile.getNumPages());
            printJob.setPageable(book);
            
            
//            PrintService[] printServices = PrinterJob.lookupPrintServices();
//            for (int count = 0; count < printServices.length; ++count) {
//                if (printerName.equalsIgnoreCase(printJob.getPrintService().getName())) {
//                    printJob.setPrintService(printServices[count]);
//                    break;
//                }
//            }
            //pageFormat.setOrientation(PageFormat.PORTRAIT);
            //printJob.setPrintable(pages,pageFormat);
            printJob.print();
            }
            fileInputStream.close();
            pdfA4.delete();
            pdfA5.delete();
        } catch (FileNotFoundException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (JRException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (IOException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (DocumentException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } catch (PrinterException ex) {
            if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            ex.printStackTrace();
            objUti.mostrarMsgErr_F1(this, ex);
        } finally {
            try {
                fileInputStream.close();
                if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
            } catch (IOException ex) {
                if (pdfA4!=null) {
                    pdfA4.delete();
                }
                if (pdfA5!=null) {
                    pdfA5.delete();
                }
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        }
      }
}
