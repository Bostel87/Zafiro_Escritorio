
package RecursosHumanos.ZafRecHum03;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafValCedRuc.TuvalUtilitiesException;
import Librerias.ZafValCedRuc.VerificarId;
import Librerias.ZafValCedRuc.ZafRegCiv;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import RecursosHumanos.ZafRecHum20.ZafRecHum20Bean;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Pantalla de Mantenimiento para Empleados
 * @author Carlos Lainez, Roberto Flores.
 * Guayaquil 01/04/2011
 */
public class ZafRecHum03 extends javax.swing.JInternalFrame {
    private final String strVer = " v1.21";                     //Versión de clase
    private ZafRecHum03Bean zafRecHum03Bean;                    //Bean manejador de éste JInternalFrame
    public ZafDatePicker dtpFecNac;                             //Date Time Picker para la fecha de nacimiento
    public ZafDatePicker dtpFecIniCon;                          //Date Time Picker para la fecha de inicio de contrato
    public ZafDatePicker dtpFecFinPerPru;                       //Date Time Picker para la fecha de finalización de período de prueba
    public ZafDatePicker dtpFecFinCon;                          //Date Time Picker para la fecha de finalización de contrato
    public ZafDatePicker dtpFecReaFinCon;                       //Date Time Picker para la fecha real de finalización de contrato
    public ZafTblMod zafTblModCon;                              //Table Model para la Tabla de Contactos
    public ZafTblMod zafTblModDoc;                              //Table Model para la Tabla de Documentos
    private ZafTblCelRenChk zafTblCelRenChkEme;                 //Renderer de Check Box para campo Emergencia
    private ZafTblCelEdiChk zafTblCelEdiChkEme;                 //Editor de Check Box para campo Emergencia
    public ZafTblCelRenBut zafTblConCelRenBut;                  //Renderer para los botones de la tabla de contactos
    public ZafTblCelRenBut zafTblDocCelRenBut;                  //Renderer para los botones de la tabla de documentos
    private ZafTblCelEdiTxt zafTblCelEdiTxt;                    //Editor de Text para campo Nombre
    private String[] strTblConHdrTooTip;                        //Tooltips de la cabecera de las columnas de los contactos
    private String[] strTblDocHdrTooTip;                        //Tooltips de la cabecera de las columnas de los documentos
    private ZafTblCelEdiButVco zafTblCelEdiButVcoRel;           //Editor de Ventana para el Button Relacion
    private ZafTblCelEdiButVco zafTblCelEdiButVcoTipDoc;        //Editor de Ventana para el Button Tipos de documentos digitales
    private ZafTblCelEdiButGen zafTblCelEdiButGenFch;           //Editor de FileChooser
    private ZafTblCelEdiButGen zafTblCelEdiButGenVerDoc;        //Editor del Boton para ver los documentos
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Renderer de Label para el campo Fec.Nac.
    private ZafUtil objUti;                                     //Utilidades
    private ZafParSis objParSis;                                //Parámetros del Sistema

    public static final int INT_CON_LINEA = 0;                  //Índice de columna "Línea" de la Tabla de Contactos
    public static final int INT_CON_CODTRA = 1;                 //Índice de columna "Cód.Empleado" de la Tabla de Contactos
    public static final int INT_CON_CODREG = 2;                 //Índice de columna "Cód.Contacto" de la Tabla de Contactos
    public static final int INT_CON_NOMCON = 3;                 //Índice de columna "Nombre" de la Tabla de Contactos
    public static final int INT_CON_CODREL = 4;                 //Indice de columna "Cod.Relacion" de la Tabla de Contactos
    public static final int INT_CON_BUTREL = 5;                 //Índice de columna "..." de la Tabla de Contactos
    public static final int INT_CON_NOMREL = 6;                 //Índice de columna "Relación" de la Tabla de Contactos
    public static final int INT_CON_TEL1 = 7;                   //Índice de columna "Teléfono 1" de la Tabla de Contactos
    public static final int INT_CON_TEL2 = 8;                   //Índice de columna "Teléfono 2" de la Tabla de Contactos
    public static final int INT_CON_CHKCARFAM = 9;              //Índice de columna "Car.Fam" de la Tabla de Contactos
    public static final int INT_CON_FECNAC = 10;                //Índice de columna "Fec.Nac." de la Tabla de Contactos
    public static final int INT_CON_CHKEME = 11;                //Índice de columna "Emergencia" de la Tabla de Contactos
    public static final int INT_CON_OBS = 12;                   //Índice de columna "Observación" de la Tabla de Contactos
    public static final int INT_CON_BUTOBS = 13;                //Índice de columna "..." de la Tabla de Contactos

    public static final int INT_DOC_LINEA = 0;                  //Índice de columna "Línea" de la Tabla de Documentos Digitales
    public static final int INT_DOC_CODTRA = 1;                 //Índice de columna "Cod.Empleado" de la Tabla de Documentos Digitales
    public static final int INT_DOC_CODDOC = 2;                 //Índice de columna "Cod.Documento" de la Tabla de Documentos Digitales
    public static final int INT_DOC_CODTIPDOC = 3;              //Índice de columna "Cod.Tip.Doc." de la Tabla de Documentos Digitales
    public static final int INT_DOC_NOMTIPDOC = 4;              //Índice de columna "Tip.Dig." de la Tabla de Documentos Digitales
    public static final int INT_DOC_BUTTIPDOC = 5;              //Índice de columna "..." de la Tabla de Documentos Digitales
    public static final int INT_DOC_NOMARCLOC = 6;              //Índice de columna "Archivo local" de la Tabla de Documentos Digitales
    public static final int INT_DOC_NOMARC = 7;                 //Índice de columna "Nombre Archivo" de la Tabla de Documentos Digitales
    public static final int INT_DOC_BUTBUSARC = 8;              //Índice de columna "..." de la Tabla de Documentos Digitales
    public static final int INT_DOC_OBS = 9;                    //Índice de columna "Observacion" de la Tabla de Documentos Digitales
    public static final int INT_DOC_BUTOBS = 10;                //Índice de columna " " de la Tabla de Documentos Digitales
    public static final int INT_DOC_BUTVERDOC = 11;             //Índice de columna " " de la Tabla de Documentos Digitales
    
    public static final int INT_REP_LINEA = 0;                 //Índice de columna "Línea" de la Tabla de Reportes
    public static final int INT_REP_CODTRA = 1;                //Índice de columna "Cod.Trabajador" de la Tabla de Reportes
    public static final int INT_REP_APETRA = 2;                //Indice de columna "Apellido Trabajador" de la Tabla de Reportes
    public static final int INT_REP_NOMTRA = 3;                //Indice de columna "Nombre Trabajador" de la Tabla de Reportes
    public static final int INT_REP_CHKHORFIJ = 4;             //Indice de columna "Horario Fijo" de la Tabla de Reportes
    
    private ZafRecHum20Bean zafRecHum20Bean;                   //Bean manejador de éste JInternalFrame
    
    private String[] strTblRepHdrTooTip;                       //Tooltips de la cabecera de las columnas del reporte
    public ZafTblMod zafTblModRep;                             //Table Model para la Tabla de Reportes
    private ZafTblCelRenChk zafTblCelRenChk;                   //Renderer de Check Box para las empresas
    private ZafTblCelEdiChk zafTblCelEdiChk;                   //Editor de Check Box para las empresas

    private ZafTblBus objTblBus;
    
    private String strCodDep = "";
    private String strDesLarDep = "";
    
    private String strCodOfi = "";
    private String strDesLarOfi = "";
    
    private String strCodCarLab = "";
    private String strNomCarLab = "";
    
    private ZafVenCon vcoDep;                                   //Ventana de consulta.
    private ZafVenCon vcoOfi;                                   //Ventana de consulta.
    private ZafVenCon vcoCarLab;                                //Ventana de consulta.
    
    public List<Tbm_hortra> lisTbm_hortra=null;
    
    /** Creates new form ZafRecHum03 */
    public ZafRecHum03(ZafParSis obj) {
        try{
            zafRecHum03Bean = new ZafRecHum03Bean(this, obj, this.getClass().getSimpleName());
            strTblConHdrTooTip = new String[]{null,null,null,null,"Nombre del contacto",null,null,"Relación con el contacto Ejemplos: Padre,Madre,Hermano,Cónyuge,Amigo,etc","Teléfono 1","Teléfono 2","¿Es una carga familiar?","Fecha de nacimiento","En caso de emergencia llamar a...", "Observación", null};
            strTblDocHdrTooTip = new String[]{null,null,null,null,"Muestra los \"Tipos de documentos digitales\"",null,"Tipo de documento digital","Muestra un cuadro de dialogo para seleccionar el archivo a subir",null,null,null,"Muestra un cuadro de dialogo con la observacion","Muestra el documento digital subido"};
//            zafRecHum20Bean = new ZafRecHum20Bean(this, obj);
            strTblRepHdrTooTip = new String[]{null,"Código del Empleado","Nombres del Empleado","Apellidos del Empleado","¿Tiene horario fijo?"};
            initComponents();
//            txtCodDep.setVisible(false);
            dtpFecNac = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            dtpFecNac.setPreferredSize(new Dimension(70, 20));
            dtpFecNac.setText("");
            panGen.add(dtpFecNac);
            dtpFecNac.setBounds(130, 167, 150, 20);
            dtpFecNac.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
    //                dtpFecNacActionPerformed(evt);
                }
            });
            dtpFecIniCon = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            dtpFecIniCon.setPreferredSize(new Dimension(130, 20));
            dtpFecIniCon.setText("");
            panCon.add(dtpFecIniCon);
            dtpFecIniCon.setBounds(170, 60, 150, 20);
    //        dtpFecIniCon.setToolTipText("Fecha de finalización del periodo de prueba");
            dtpFecIniCon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
    //                dtpFecIniConActionPerformed(evt);
                }
            });
            dtpFecFinPerPru = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            dtpFecFinPerPru.setPreferredSize(new Dimension(130, 20));
            dtpFecFinPerPru.setText("");
            panCon.add(dtpFecFinPerPru);
            dtpFecFinPerPru.setBounds(170, 80, 150, 20);
    //        dtpFecFinPerPru.setToolTipText("Fecha de finalización del periodo de prueba");
            dtpFecFinPerPru.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
    //                dtpFecNacActionPerformed(evt);
                }
            });
            dtpFecFinCon = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            dtpFecFinCon.setPreferredSize(new Dimension(130, 20));
            dtpFecFinCon.setText("");
            panCon.add(dtpFecFinCon);
            dtpFecFinCon.setBounds(170, 100, 150, 20);
    //        dtpFecFinCon.setToolTipText("Fecha de finalización del contrato");
            dtpFecFinCon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
    //                dtpFecNacActionPerformed(evt);
                }
            });
            dtpFecReaFinCon = new ZafDatePicker(((JFrame)this.getParent()),"d/m/y");
            dtpFecReaFinCon.setPreferredSize(new Dimension(130, 20));
            dtpFecReaFinCon.setText("");
            panCon.add(dtpFecReaFinCon);
            dtpFecReaFinCon.setBounds(530, 60, 140, 20);
    //        dtpFecReaFinCon.setToolTipText("Fecha real de finalización del contrato");
            dtpFecReaFinCon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
    //                dtpFecNacActionPerformed(evt);
                }
            });
            setTitle(zafRecHum03Bean.getZafParSis().getNombreMenu()+strVer);
            zafRecHum03Bean.initTooBar(this);
            panBar.add(zafRecHum03Bean.getTooBar());
            initTblCon();
            initTblDoc();
            intTblConHorTra();

             //Configurar las ZafVenCon.
                configurarVenConDep();
                configurarVenConOfi();
                configurarVenCarLab();

            if (!zafRecHum03Bean.getZafPerUsr().isOpcionEnabled(2774)){
                tabGen.remove(panDoc);
            }
            objUti = new ZafUtil();
            objParSis = new ZafParSis();
            
        }
        catch(Exception e){
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        
    }

    
    
    private void intTblConHorTra(){
        //Títulos de Cabecera
        Vector vecCabRep=new Vector();
        vecCabRep.clear();
        vecCabRep.add(INT_REP_LINEA, "");
        vecCabRep.add(INT_REP_CODTRA, "Codigo");
        vecCabRep.add(INT_REP_APETRA, "Apellidos");
        vecCabRep.add(INT_REP_NOMTRA, "Nombres");
        vecCabRep.add(INT_REP_CHKHORFIJ, "Horario Fijo");

        lisTbm_hortra = zafRecHum03Bean.consultarLisGenCabHorTra();

        final int intColFij = 5;
        strTblRepHdrTooTip = new String[intColFij+lisTbm_hortra.size()];
        strTblRepHdrTooTip[0] = null;
        strTblRepHdrTooTip[1] = "Código del Empleado";
        strTblRepHdrTooTip[2] = "Apellidos del Empleado";
        strTblRepHdrTooTip[3] = "Nombres del Empleado";
        strTblRepHdrTooTip[4] = "¿Tiene horario fijo?";

        int cont = 5;
        if(lisTbm_hortra!=null){
            for(Tbm_hortra tmp:lisTbm_hortra){
                if(tmp.getStrTx_nom().length()>=10){
                    strTblRepHdrTooTip[cont]=tmp.getStrTx_nom();
                }else{
                    strTblRepHdrTooTip[cont]=null;
                }
                vecCabRep.add(vecCabRep.size(), tmp.getStrTx_nom());
                cont++;
            }
        }

        zafTblModRep = new ZafTblMod();
        zafTblModRep.setHeader(vecCabRep);
        zafTblModRep.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        tblRep.setModel(zafTblModRep);
        tblRep.setRowSelectionAllowed(true);
        tblRep.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        new ZafColNumerada(tblRep,INT_REP_LINEA);

        tblRep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Columnas editables
        Vector vecColEdi = new Vector();
        vecColEdi.add("" + INT_REP_CHKHORFIJ);

        //Ancho de columnas
        TableColumnModel tcmAux = tblRep.getColumnModel();
        tcmAux.getColumn(INT_REP_LINEA).setPreferredWidth(40);
        tcmAux.getColumn(INT_REP_CODTRA).setPreferredWidth(40);
        tcmAux.getColumn(INT_REP_APETRA).setPreferredWidth(170);
        tcmAux.getColumn(INT_REP_NOMTRA).setPreferredWidth(170);
        tcmAux.getColumn(INT_REP_CHKHORFIJ).setPreferredWidth(60);
        
        //Configurar JTable: Ocultar columnas del sistema.
        zafTblModRep.addSystemHiddenColumn(INT_REP_LINEA, tblRep);
        zafTblModRep.addSystemHiddenColumn(INT_REP_CODTRA, tblRep);
        zafTblModRep.addSystemHiddenColumn(INT_REP_APETRA, tblRep);
        zafTblModRep.addSystemHiddenColumn(INT_REP_NOMTRA, tblRep);

        zafTblCelRenChk = new ZafTblCelRenChk();
        zafTblCelEdiChk = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_REP_CHKHORFIJ).setCellRenderer(zafTblCelRenChk);
        tcmAux.getColumn(INT_REP_CHKHORFIJ).setCellEditor(zafTblCelEdiChk);
        if(lisTbm_hortra!=null){
            for(int i=1;i<=lisTbm_hortra.size();i++){
                

                //tcmAux.getColumn(INT_REP_APETRA+i).setPreferredWidth(100);
                tcmAux.getColumn(INT_REP_CHKHORFIJ+i).setCellRenderer(zafTblCelRenChk);
                tcmAux.getColumn(INT_REP_CHKHORFIJ+i).setCellEditor(zafTblCelEdiChk);
                zafTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    
                    int intMaxCol=tblRep.getColumnCount();
                    
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                     int intCol = tblRep.getSelectedColumn();//columna
                    int intFil = tblRep.getSelectedRow();//fila 
                    int columnaMax = tblRep.getColumnCount();
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblRep.getModel();

                for(int j=INT_REP_CHKHORFIJ+1;j<objTblMod.getColumnCount();j++){

                    if(objTblMod.getValueAt(intFil, j).equals(false)){
                        tblRep.setValueAt(false, intFil, ZafRecHum03.INT_REP_CHKHORFIJ);
                    }else{
                        tblRep.setValueAt(true, intFil, ZafRecHum03.INT_REP_CHKHORFIJ);
                        j=objTblMod.getColumnCount();
                    }
                }   
                }     
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    int intCol = tblRep.getSelectedColumn();//columna
                    int intFil = tblRep.getSelectedRow();//fila
                    for(int x=intColFij; x < intMaxCol; x++){
                        
                        if(x!=intCol){
                           
                            tblRep.setValueAt( new Boolean(false), intFil , x);
                        }
                    }
					//Se agrega validacion para seleccion de horarios. tony
                    int columnaMax = tblRep.getColumnCount();
                ZafTblMod objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblRep.getModel();

                for(int j=INT_REP_CHKHORFIJ+1;j<objTblMod.getColumnCount();j++){

                    if(objTblMod.getValueAt(intFil, j).equals(false)){
                        tblRep.setValueAt(false, intFil, ZafRecHum03.INT_REP_CHKHORFIJ);
                    }else{
                        tblRep.setValueAt(true, intFil, ZafRecHum03.INT_REP_CHKHORFIJ);
                        j=objTblMod.getColumnCount();
                    }
                }   
                }
               });

                vecColEdi.add("" + (INT_REP_CHKHORFIJ+i));
                tcmAux.getColumn(INT_REP_CHKHORFIJ+i).setPreferredWidth(60);
            }
        }

        //Acciones para el manejo de Tablas
        new ZafTblEdi(tblRep);

        zafTblCelEdiTxt = new ZafTblCelEdiTxt(tblRep);
//        zafTblCelEdiTxt.getDocument().addDocumentListener(zafRecHum20Bean.getDocLis());
        tcmAux.getColumn(INT_REP_CODTRA).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_REP_APETRA).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_REP_NOMTRA).setCellEditor(zafTblCelEdiTxt);
        zafTblCelEdiTxt = null;
        zafTblModRep.setColumnasEditables(vecColEdi);
        vecColEdi = null;

        ZafTblPopMnu ZafTblPopMn = new ZafTblPopMnu(tblRep);
//        ZafTblPopMn.setEliminarFilaVisible(true);
        
//        Vector vecTblCon = new Vector();
        Vector vecRegCon = new Vector();
        if(lisTbm_hortra!=null){
            Vector vecTblCon = new Vector();
//            for(Tbm_hortra tmp:lisTbm_hortra){
                
                vecRegCon.add(INT_REP_LINEA,"");
                vecRegCon.add(INT_REP_CODTRA,"");
                vecRegCon.add(INT_REP_APETRA,"");
                vecRegCon.add(INT_REP_NOMTRA,"");
                vecRegCon.add(INT_REP_CHKHORFIJ,"");
                for(Tbm_hortra tmp:lisTbm_hortra){
                    vecRegCon.add(INT_REP_CHKHORFIJ+1,"");
                }
//            }
            vecTblCon.add(vecRegCon);
            if(!vecTblCon.isEmpty()){
                zafTblModRep.setData(vecTblCon);
                tblRep.setModel(zafTblModRep);
            }
        }
        
        
        //Configurar JTable: Establecer el ordenamiento en la tabla.
//                objTblOrd=new ZafTblOrd(tblRep);
        
        //Configurar JTable: Editor de búsqueda.
//        objTblBus=new ZafTblBus(tblRep);
        tcmAux = null;
    }
    
    private void initTblCon(){
        //Títulos de Cabecera
        Vector vecCabCon=new Vector();
        vecCabCon.clear();
        vecCabCon.add(INT_CON_LINEA, "");
        vecCabCon.add(INT_CON_CODTRA, "Cód.Empleado");
        vecCabCon.add(INT_CON_CODREG, "Código");
        vecCabCon.add(INT_CON_NOMCON, "Nombre");
        vecCabCon.add(INT_CON_CODREL, "Cod.Relacion");
        vecCabCon.add(INT_CON_BUTREL, "...");
        vecCabCon.add(INT_CON_NOMREL, "Relación");
        vecCabCon.add(INT_CON_TEL1, "Teléfono 1");
        vecCabCon.add(INT_CON_TEL2, "Teléfono 2");
        vecCabCon.add(INT_CON_CHKCARFAM, "Car.Fam");
        vecCabCon.add(INT_CON_FECNAC, "Fec.Nac.");
        vecCabCon.add(INT_CON_CHKEME, "Emergencia");
        vecCabCon.add(INT_CON_OBS, "Observación");
        vecCabCon.add(INT_CON_BUTOBS, "...");

        zafTblModCon = new ZafTblMod();
        zafTblModCon.setHeader(vecCabCon);
        tblCon.setModel(zafTblModCon);
        tblCon.setRowSelectionAllowed(true);
        tblCon.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        new ZafColNumerada(tblCon,INT_CON_LINEA);

        tblCon.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        //Ancho de columnas
        TableColumnModel tcmAux = tblCon.getColumnModel();
        tcmAux.getColumn(INT_CON_LINEA).setPreferredWidth(40);
        tcmAux.getColumn(INT_CON_CODTRA).setWidth(0);
        tcmAux.getColumn(INT_CON_CODTRA).setMaxWidth(0);
        tcmAux.getColumn(INT_CON_CODTRA).setMinWidth(0);
        tcmAux.getColumn(INT_CON_CODTRA).setPreferredWidth(0);
        tcmAux.getColumn(INT_CON_CODREG).setWidth(0);
        tcmAux.getColumn(INT_CON_CODREG).setMaxWidth(0);
        tcmAux.getColumn(INT_CON_CODREG).setMinWidth(0);
        tcmAux.getColumn(INT_CON_CODREG).setPreferredWidth(0);
        tcmAux.getColumn(INT_CON_NOMCON).setPreferredWidth(200);
        tcmAux.getColumn(INT_CON_CODREL).setWidth(0);
        tcmAux.getColumn(INT_CON_CODREL).setMaxWidth(0);
        tcmAux.getColumn(INT_CON_CODREL).setMinWidth(0);
        tcmAux.getColumn(INT_CON_CODREL).setPreferredWidth(0);
        tcmAux.getColumn(INT_CON_BUTREL).setPreferredWidth(20);
        tcmAux.getColumn(INT_CON_NOMREL).setPreferredWidth(150);
        tcmAux.getColumn(INT_CON_TEL1).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_TEL2).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_CHKCARFAM).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_FECNAC).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_CHKEME).setPreferredWidth(100);
        tcmAux.getColumn(INT_CON_OBS).setPreferredWidth(200);
        tcmAux.getColumn(INT_CON_BUTOBS).setPreferredWidth(20);

        //Columnas editables
        Vector vecColEdi = new Vector();
        vecColEdi.add("" + INT_CON_NOMCON);
        vecColEdi.add("" + INT_CON_CODREL);
        vecColEdi.add("" + INT_CON_BUTREL);
        vecColEdi.add("" + INT_CON_TEL1);
        vecColEdi.add("" + INT_CON_TEL2);
        vecColEdi.add("" + INT_CON_FECNAC);
        vecColEdi.add("" + INT_CON_CHKEME);
        vecColEdi.add("" + INT_CON_OBS);
        vecColEdi.add("" + INT_CON_BUTOBS);
        zafTblModCon.setColumnasEditables(vecColEdi);
        vecColEdi = null;

        //Recordar filas eliminadas
        ArrayList arlAux = new ArrayList();
        arlAux.add("" + INT_CON_CODTRA);
        arlAux.add("" + INT_CON_CODREG);
        arlAux.add("" + INT_CON_CODREL);
        zafTblModCon.setColsSaveBeforeRemoveRow(arlAux);
        arlAux = null;

        //Acciones para el manejo de Tablas
        new ZafTblEdi(tblCon);

        zafTblCelEdiTxt = new ZafTblCelEdiTxt(tblCon);
        zafTblCelEdiTxt.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        tcmAux.getColumn(INT_CON_NOMCON).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_CON_CODREL).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_CON_NOMREL).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_CON_TEL1).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_CON_TEL2).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_CON_FECNAC).setCellEditor(new ZafDtePckEdi("d/m/y"));

        objTblCelRenLbl = new ZafTblCelRenLbl();
        tcmAux.getColumn(INT_CON_FECNAC).setCellRenderer(objTblCelRenLbl);
        objTblCelRenLbl = null;
        tcmAux.getColumn(INT_CON_OBS).setCellEditor(zafTblCelEdiTxt);

        zafTblCelEdiTxt.addTableEditorListener(zafRecHum03Bean.getZafTableAdapterCelEdiTxt());
        zafTblCelEdiTxt = null;

        zafTblCelRenChkEme = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_CON_CHKEME).setCellRenderer(zafTblCelRenChkEme);

        zafTblCelEdiChkEme = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_CON_CHKEME).setCellEditor(zafTblCelEdiChkEme);

        zafTblConCelRenBut = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_CON_BUTOBS).setCellRenderer(zafTblConCelRenBut);
        new ButCot(tblCon, INT_CON_BUTOBS);

        tcmAux.getColumn(INT_CON_BUTREL).setCellRenderer(zafTblConCelRenBut);
        
        zafTblCelEdiButVcoRel = new ZafTblCelEdiButVco(tblCon, zafRecHum03Bean.getZafVenTipFamCon(), new int[]{2}, new int[]{INT_CON_NOMREL});
        tcmAux.getColumn(INT_CON_BUTREL).setCellEditor(zafTblCelEdiButVcoRel);
        zafTblCelEdiButVcoRel.addTableEditorListener(zafRecHum03Bean.getZafTableAdapterButVcoRel());

        ZafTblPopMnu ZafTblPopMn = new ZafTblPopMnu(tblCon);
        ZafTblPopMn.setEliminarFilaVisible(true);
        tcmAux = null;
        zafTblModCon.setModoOperacion(ZafTblMod.INT_TBL_INS);
        
        //Menú Contextual para manejo de tablas
        new ZafTblPopMnu(tblCon);
    }

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ButCot extends ZafTableColBut_uni {
        public ButCot(JTable tbl, int intIdx) {
            super(tbl, intIdx, "Observación");
        }
        public void butCLick() {
            int intFil = tblCon.getSelectedRow();
            String strObs = (tblCon.getValueAt(intFil, INT_CON_OBS) == null ? "" : tblCon.getValueAt(intFil, INT_CON_OBS).toString());
            llamarVenObs(tblCon, strObs, intFil, INT_CON_OBS);
        }
    }

    private void llamarVenObs(JTable tbl,String strObs, int intFil, int intCol) {
        ZafMae07_01 obj1 = new ZafMae07_01(JOptionPane.getFrameForComponent(this), true, strObs);
        obj1.show();
        if (obj1.getAceptar()) {
            tbl.setValueAt(obj1.getObser(), intFil, intCol);
        }
        obj1 = null;
    }

    private void initTblDoc(){
        //Títulos de Cabecera
        Vector vecCabDoc=new Vector();
        vecCabDoc.clear();
        vecCabDoc.add(INT_DOC_LINEA, "");
        vecCabDoc.add(INT_DOC_CODTRA, "Cod.Empleado");
        vecCabDoc.add(INT_DOC_CODDOC, "Cod.Documento");
        vecCabDoc.add(INT_DOC_CODTIPDOC, "Cod.Tip.Doc.");
        vecCabDoc.add(INT_DOC_NOMTIPDOC, "Tip.Dig.");
        vecCabDoc.add(INT_DOC_BUTTIPDOC, "...");
        vecCabDoc.add(INT_DOC_NOMARCLOC, "Archivo local");
        vecCabDoc.add(INT_DOC_NOMARC, "Nombre Archivo");
        vecCabDoc.add(INT_DOC_BUTBUSARC, "...");
        vecCabDoc.add(INT_DOC_OBS, "Observacion");
        vecCabDoc.add(INT_DOC_BUTOBS, "");
        vecCabDoc.add(INT_DOC_BUTVERDOC, "");

        zafTblModDoc = new ZafTblMod();
        zafTblModDoc.setHeader(vecCabDoc);
        tblDoc.setModel(zafTblModDoc);
        tblDoc.setRowSelectionAllowed(true);
        tblDoc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        new ZafColNumerada(tblDoc,INT_DOC_LINEA);

        tblDoc.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Ancho de columnas
        TableColumnModel tcmAux = tblDoc.getColumnModel();
        tcmAux.getColumn(INT_DOC_LINEA).setPreferredWidth(40);
        tcmAux.getColumn(INT_DOC_CODTRA).setWidth(0);
        tcmAux.getColumn(INT_DOC_CODTRA).setMaxWidth(0);
        tcmAux.getColumn(INT_DOC_CODTRA).setMinWidth(0);
        tcmAux.getColumn(INT_DOC_CODTRA).setPreferredWidth(0);
        tcmAux.getColumn(INT_DOC_CODDOC).setWidth(0);
        tcmAux.getColumn(INT_DOC_CODDOC).setMaxWidth(0);
        tcmAux.getColumn(INT_DOC_CODDOC).setMinWidth(0);
        tcmAux.getColumn(INT_DOC_CODDOC).setPreferredWidth(0);
        tcmAux.getColumn(INT_DOC_CODTIPDOC).setWidth(0);
        tcmAux.getColumn(INT_DOC_CODTIPDOC).setMaxWidth(0);
        tcmAux.getColumn(INT_DOC_CODTIPDOC).setMinWidth(0);
        tcmAux.getColumn(INT_DOC_CODTIPDOC).setPreferredWidth(0);
        tcmAux.getColumn(INT_DOC_NOMTIPDOC).setPreferredWidth(100);
        tcmAux.getColumn(INT_DOC_BUTTIPDOC).setPreferredWidth(20);
        tcmAux.getColumn(INT_DOC_NOMARCLOC).setPreferredWidth(300);
        tcmAux.getColumn(INT_DOC_NOMARC).setWidth(0);
        tcmAux.getColumn(INT_DOC_NOMARC).setMaxWidth(0);
        tcmAux.getColumn(INT_DOC_NOMARC).setMinWidth(0);
        tcmAux.getColumn(INT_DOC_NOMARC).setPreferredWidth(0);
        tcmAux.getColumn(INT_DOC_BUTBUSARC).setPreferredWidth(20);
        tcmAux.getColumn(INT_DOC_OBS).setPreferredWidth(200);
        tcmAux.getColumn(INT_DOC_BUTOBS).setPreferredWidth(20);
        tcmAux.getColumn(INT_DOC_BUTVERDOC).setPreferredWidth(20);

        //Columnas editables
        Vector vecColEdi = new Vector();
        vecColEdi.add("" + INT_DOC_BUTTIPDOC);
        vecColEdi.add("" + INT_DOC_BUTBUSARC);
        vecColEdi.add("" + INT_DOC_BUTOBS);
        vecColEdi.add("" + INT_DOC_BUTVERDOC);
        zafTblModDoc.setColumnasEditables(vecColEdi);
        vecColEdi = null;

        //Recordar filas eliminadas
        ArrayList arlAux = new ArrayList();
        arlAux.add("" + INT_DOC_CODTRA);
        arlAux.add("" + INT_DOC_CODDOC);
        arlAux.add("" + INT_DOC_NOMARC);
        zafTblModDoc.setColsSaveBeforeRemoveRow(arlAux);
        arlAux = null;

        //Acciones para el manejo de Tablas
        new ZafTblEdi(tblDoc);

        zafTblCelEdiTxt = new ZafTblCelEdiTxt(tblCon);
        zafTblCelEdiTxt.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        tcmAux.getColumn(INT_DOC_NOMTIPDOC).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_DOC_NOMARCLOC).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_DOC_NOMARC).setCellEditor(zafTblCelEdiTxt);
        tcmAux.getColumn(INT_DOC_OBS).setCellEditor(zafTblCelEdiTxt);
        zafTblCelEdiTxt = null;

        zafTblDocCelRenBut = new ZafTblCelRenBut();
        tcmAux.getColumn(INT_DOC_BUTOBS).setCellRenderer(zafTblDocCelRenBut);
        ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDoc, INT_DOC_BUTOBS, "Observación") {
            public void butCLick() {
                int intSelFil = tblDoc.getSelectedRow();
                String strObs = (tblDoc.getValueAt(intSelFil, INT_DOC_OBS) == null ? "" : tblDoc.getValueAt(intSelFil, INT_DOC_OBS).toString());
                ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum03.this), true, strObs);
                zafMae07_01.show();
                if (zafMae07_01.getAceptar()) {
                    tblDoc.setValueAt(zafMae07_01.getObser(), intSelFil, INT_DOC_OBS);
                }
            }
        };
        tcmAux.getColumn(INT_DOC_BUTTIPDOC).setCellRenderer(zafTblDocCelRenBut);

        zafTblCelEdiButVcoTipDoc = new ZafTblCelEdiButVco(tblDoc, zafRecHum03Bean.getZafVenTipDocDig(), new int[]{3}, new int[]{INT_DOC_NOMTIPDOC});
        tcmAux.getColumn(INT_DOC_BUTTIPDOC).setCellEditor(zafTblCelEdiButVcoTipDoc);
        zafTblCelEdiButVcoTipDoc.addTableEditorListener(zafRecHum03Bean.getZafTableAdapterButVcoTipDoc());

        tcmAux.getColumn(INT_DOC_BUTBUSARC).setCellRenderer(zafTblDocCelRenBut);
        zafTblCelEdiButGenFch = new ZafTblCelEdiButGen();
        tcmAux.getColumn(INT_DOC_BUTBUSARC).setCellEditor(zafTblCelEdiButGenFch);
        zafTblCelEdiButGenFch.addTableEditorListener(zafRecHum03Bean.getZafTableAdapterButFilCho());

        tcmAux.getColumn(INT_DOC_BUTVERDOC).setCellRenderer(zafTblDocCelRenBut);
        zafTblCelEdiButGenVerDoc = new ZafTblCelEdiButGen();
        tcmAux.getColumn(INT_DOC_BUTVERDOC).setCellEditor(zafTblCelEdiButGenVerDoc);
        zafTblCelEdiButGenVerDoc.addTableEditorListener(zafRecHum03Bean.getZafTableAdapterButVerDoc());
        zafTblDocCelRenBut.addTblCelRenListener(zafRecHum03Bean.getZafTblCelRenAdapterBut());

        ZafTblPopMnu ZafTblPopMn = new ZafTblPopMnu(tblDoc);
        ZafTblPopMn.setEliminarFilaVisible(true);
        tcmAux = null;
        zafTblModDoc.setModoOperacion(ZafTblMod.INT_TBL_INS);

        //Menú Contextual para manejo de tablas
        new ZafTblPopMnu(tblDoc);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrSex = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panTab = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        lblCod = new javax.swing.JLabel();
        lblIde = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        lblDir = new javax.swing.JLabel();
        lblRef = new javax.swing.JLabel();
        lblTel1 = new javax.swing.JLabel();
        lblEma = new javax.swing.JLabel();
        lblFecNac = new javax.swing.JLabel();
        lblEstCiv = new javax.swing.JLabel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtIde = new javax.swing.JTextField();
        butCodTra = new javax.swing.JButton();
        txtNomTra = new javax.swing.JTextField();
        lblApe = new javax.swing.JLabel();
        txtApeTra = new javax.swing.JTextField();
        txtDir = new javax.swing.JTextField();
        txtRefDir = new javax.swing.JTextField();
        txtTel1 = new javax.swing.JTextField();
        lblTel2 = new javax.swing.JLabel();
        txtTel2 = new javax.swing.JTextField();
        lblTel3 = new javax.swing.JLabel();
        txtTel3 = new javax.swing.JTextField();
        txtEma = new javax.swing.JTextField();
        lblSex = new javax.swing.JLabel();
        optSexMas = new javax.swing.JRadioButton();
        optSexFem = new javax.swing.JRadioButton();
        lblCiu = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        txtNomCiu = new javax.swing.JTextField();
        butCiu = new javax.swing.JButton();
        txtCodEstCiv = new javax.swing.JTextField();
        txtNomEstCiv = new javax.swing.JTextField();
        butEstCiv = new javax.swing.JButton();
        lblNumHij = new javax.swing.JLabel();
        txtNumHij = new javax.swing.JTextField();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        lblTipCta = new javax.swing.JLabel();
        txtNumCta = new javax.swing.JTextField();
        lblNumCta = new javax.swing.JLabel();
        jCmbTipCta = new javax.swing.JComboBox();
        spnObs3 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panConFam = new javax.swing.JPanel();
        scrCon = new javax.swing.JScrollPane();
        tblCon = new JTable(){
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    public String getToolTipText(MouseEvent e) {
                        Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return strTblConHdrTooTip[realIndex];
                    }
                };
            }
        };
        panDoc = new javax.swing.JPanel();
        srcDoc = new javax.swing.JScrollPane();
        tblDoc = new javax.swing.JTable();
        panCon = new javax.swing.JPanel();
        lblFinCon = new javax.swing.JLabel();
        jCmbEstFinCon = new javax.swing.JComboBox();
        lblDep = new javax.swing.JLabel();
        lblEstFinCon = new javax.swing.JLabel();
        lblPerPru = new javax.swing.JLabel();
        lblEstPerPru = new javax.swing.JLabel();
        jCmbPla = new javax.swing.JComboBox();
        lblFinReaCon = new javax.swing.JLabel();
        lblIniCon = new javax.swing.JLabel();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        lblSec = new javax.swing.JLabel();
        jCmbEstPerPru = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblRep = new javax.swing.JTable();
        lblFinCon1 = new javax.swing.JLabel();
        txtValAlm = new javax.swing.JTextField();
        jCmbForRec = new javax.swing.JComboBox();
        jCmbRecAlm = new javax.swing.JComboBox();
        lblFinCon2 = new javax.swing.JLabel();
        lblCarLab = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtCodOfi = new javax.swing.JTextField();
        txtNomOfi = new javax.swing.JTextField();
        jBTOf = new javax.swing.JButton();
        lblDep2 = new javax.swing.JLabel();
        txtCodCarLab = new javax.swing.JTextField();
        txtNomCarLab = new javax.swing.JTextField();
        jBCL = new javax.swing.JButton();
        panBar = new javax.swing.JPanel();

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
            }
        });

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Empleados");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panTab.setLayout(new java.awt.BorderLayout());

        panGen.setLayout(null);

        lblCod.setText("Código:");
        panGen.add(lblCod);
        lblCod.setBounds(8, 20, 70, 20);

        lblIde.setText("Identificación:");
        panGen.add(lblIde);
        lblIde.setBounds(8, 41, 100, 20);

        lblNom.setText("Nombres:");
        panGen.add(lblNom);
        lblNom.setBounds(8, 62, 70, 20);

        lblDir.setText("Dirección:");
        panGen.add(lblDir);
        lblDir.setBounds(8, 83, 80, 20);

        lblRef.setText("Referencia:");
        lblRef.setToolTipText("Referencia de la Dirección");
        panGen.add(lblRef);
        lblRef.setBounds(8, 104, 80, 20);

        lblTel1.setText("Teléfono 1:");
        panGen.add(lblTel1);
        lblTel1.setBounds(8, 125, 80, 20);

        lblEma.setText("Correo Electrónico:");
        panGen.add(lblEma);
        lblEma.setBounds(8, 146, 120, 20);

        lblFecNac.setText("Fecha:");
        lblFecNac.setToolTipText("Fecha de Nacimiento");
        panGen.add(lblFecNac);
        lblFecNac.setBounds(8, 167, 90, 20);

        lblEstCiv.setText("Estado Civil:");
        panGen.add(lblEstCiv);
        lblEstCiv.setBounds(8, 188, 90, 20);

        lblObs1.setText("Observación 1:");
        panGen.add(lblObs1);
        lblObs1.setBounds(10, 240, 100, 20);

        lblObs2.setText("Observación 2:");
        panGen.add(lblObs2);
        lblObs2.setBounds(10, 280, 100, 20);

        txtCodTra.setBackground(zafRecHum03Bean.getZafParSis().getColorCamposSistema());
        txtCodTra.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panGen.add(txtCodTra);
        txtCodTra.setBounds(130, 20, 80, 20);

        txtIde.setBackground(zafRecHum03Bean.getZafParSis().getColorCamposObligatorios());
        txtIde.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtIde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeActionPerformed(evt);
            }
        });
        txtIde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeFocusLost(evt);
            }
        });
        panGen.add(txtIde);
        txtIde.setBounds(130, 41, 180, 20);

        butCodTra.setText("...");
        butCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCodTraActionPerformed(evt);
            }
        });
        panGen.add(butCodTra);
        butCodTra.setBounds(210, 20, 20, 20);

        txtNomTra.setBackground(zafRecHum03Bean.getZafParSis().getColorCamposObligatorios());
        txtNomTra.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panGen.add(txtNomTra);
        txtNomTra.setBounds(130, 62, 230, 20);

        lblApe.setText("Apellidos:");
        panGen.add(lblApe);
        lblApe.setBounds(363, 62, 65, 16);

        txtApeTra.setBackground(zafRecHum03Bean.getZafParSis().getColorCamposObligatorios());
        txtApeTra.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtApeTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApeTraActionPerformed(evt);
            }
        });
        panGen.add(txtApeTra);
        txtApeTra.setBounds(430, 62, 250, 20);

        txtDir.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirActionPerformed(evt);
            }
        });
        panGen.add(txtDir);
        txtDir.setBounds(130, 83, 550, 20);

        txtRefDir.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtRefDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRefDirActionPerformed(evt);
            }
        });
        panGen.add(txtRefDir);
        txtRefDir.setBounds(130, 104, 550, 20);

        txtTel1.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtTel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTel1ActionPerformed(evt);
            }
        });
        panGen.add(txtTel1);
        txtTel1.setBounds(130, 125, 130, 20);

        lblTel2.setText("Teléfono 2:");
        panGen.add(lblTel2);
        lblTel2.setBounds(290, 130, 80, 16);

        txtTel2.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtTel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTel2ActionPerformed(evt);
            }
        });
        panGen.add(txtTel2);
        txtTel2.setBounds(350, 125, 100, 20);

        lblTel3.setText("Teléfono 3:");
        panGen.add(lblTel3);
        lblTel3.setBounds(500, 125, 80, 16);

        txtTel3.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtTel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTel3ActionPerformed(evt);
            }
        });
        panGen.add(txtTel3);
        txtTel3.setBounds(580, 125, 100, 20);

        txtEma.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtEma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmaActionPerformed(evt);
            }
        });
        panGen.add(txtEma);
        txtEma.setBounds(130, 146, 130, 20);

        lblSex.setText("Sexo:");
        panGen.add(lblSex);
        lblSex.setBounds(290, 150, 60, 16);

        bgrSex.add(optSexMas);
        optSexMas.setText("Masculino");
        optSexMas.setOpaque(false);
        panGen.add(optSexMas);
        optSexMas.setBounds(350, 146, 100, 25);

        bgrSex.add(optSexFem);
        optSexFem.setText("Femenino");
        optSexFem.setOpaque(false);
        panGen.add(optSexFem);
        optSexFem.setBounds(450, 146, 110, 25);

        lblCiu.setText("Ciudad:");
        lblCiu.setToolTipText("Ciudad de Nacimiento");
        panGen.add(lblCiu);
        lblCiu.setBounds(290, 170, 70, 16);

        txtCodCiu.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtCodCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCiuActionPerformed(evt);
            }
        });
        panGen.add(txtCodCiu);
        txtCodCiu.setBounds(350, 167, 50, 20);
        panGen.add(txtNomCiu);
        txtNomCiu.setBounds(400, 167, 255, 20);

        butCiu.setText("...");
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panGen.add(butCiu);
        butCiu.setBounds(655, 167, 20, 20);

        txtCodEstCiv.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtCodEstCiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEstCivActionPerformed(evt);
            }
        });
        panGen.add(txtCodEstCiv);
        txtCodEstCiv.setBounds(130, 188, 30, 20);
        panGen.add(txtNomEstCiv);
        txtNomEstCiv.setBounds(160, 188, 255, 20);

        butEstCiv.setText("...");
        butEstCiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEstCivActionPerformed(evt);
            }
        });
        panGen.add(butEstCiv);
        butEstCiv.setBounds(415, 188, 20, 20);

        lblNumHij.setText("Número de Hijos:");
        panGen.add(lblNumHij);
        lblNumHij.setBounds(490, 188, 110, 16);

        txtNumHij.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        panGen.add(txtNumHij);
        txtNumHij.setBounds(605, 188, 50, 22);

        txaObs1.setLineWrap(true);
        txaObs1.setMinimumSize(new java.awt.Dimension(100, 10));
        txaObs1.setPreferredSize(new java.awt.Dimension(100, 10));
        txaObs1.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        spnObs1.setViewportView(txaObs1);

        panGen.add(spnObs1);
        spnObs1.setBounds(130, 240, 550, 40);

        lblTipCta.setText("Tipo de cuenta:");
        panGen.add(lblTipCta);
        lblTipCta.setBounds(270, 208, 120, 20);

        txtEma.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtNumCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCtaActionPerformed(evt);
            }
        });
        panGen.add(txtNumCta);
        txtNumCta.setBounds(130, 210, 130, 20);

        lblNumCta.setText("Número de cuenta:");
        panGen.add(lblNumCta);
        lblNumCta.setBounds(8, 208, 120, 20);

        jCmbTipCta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Cuenta de Ahorros", "Cuenta Corriente" }));
        panGen.add(jCmbTipCta);
        jCmbTipCta.setBounds(390, 210, 170, 20);

        txaObs2.setLineWrap(true);
        txaObs2.setMinimumSize(new java.awt.Dimension(100, 10));
        txaObs2.setPreferredSize(new java.awt.Dimension(100, 10));
        txaObs1.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        spnObs3.setViewportView(txaObs2);

        panGen.add(spnObs3);
        spnObs3.setBounds(130, 280, 550, 40);

        tabGen.addTab("General", panGen);

        panConFam.setLayout(new java.awt.BorderLayout());

        tblCon.setModel(new javax.swing.table.DefaultTableModel(
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
        scrCon.setViewportView(tblCon);

        panConFam.add(scrCon, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Familiares/Contactos", panConFam);

        panDoc.setLayout(new java.awt.BorderLayout());

        tblDoc.setModel(new javax.swing.table.DefaultTableModel(
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
        srcDoc.setViewportView(tblDoc);

        panDoc.add(srcDoc, java.awt.BorderLayout.CENTER);

        tabGen.addTab("Digitales", panDoc);

        panCon.setLayout(null);

        lblFinCon.setText("Forma Recibe Almuerzo:");
        lblFinCon.setToolTipText("Fecha de finalización del contrato");
        panCon.add(lblFinCon);
        lblFinCon.setBounds(330, 120, 150, 20);

        jCmbEstFinCon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pendiente", "Continuar contrato", "No continuar contrato" }));
        panCon.add(jCmbEstFinCon);
        jCmbEstFinCon.setBounds(520, 100, 150, 20);

        lblDep.setText("Departamento:");
        lblDep.setToolTipText("Fecha de inicio del contrato");
        panCon.add(lblDep);
        lblDep.setBounds(10, 40, 100, 20);

        lblEstFinCon.setText("Estado finalización de contrato:");
        lblEstFinCon.setToolTipText("");
        panCon.add(lblEstFinCon);
        lblEstFinCon.setBounds(330, 100, 200, 20);

        lblPerPru.setText("Período de prueba:");
        lblPerPru.setToolTipText("Fecha de finalización del periodo de prueba del contrato");
        panCon.add(lblPerPru);
        lblPerPru.setBounds(10, 80, 200, 20);

        lblEstPerPru.setText("Estado período de prueba:");
        lblEstPerPru.setToolTipText("");
        panCon.add(lblEstPerPru);
        lblEstPerPru.setBounds(330, 80, 200, 20);

        jCmbPla.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Plantilla", "Administrativa", "Ventas" }));
        jCmbPla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panCon.add(jCmbPla);
        jCmbPla.setBounds(520, 40, 150, 20);

        lblFinReaCon.setText("Finalización real del contrato:");
        lblFinReaCon.setToolTipText("Fecha de inicio del contrato");
        panCon.add(lblFinReaCon);
        lblFinReaCon.setBounds(330, 60, 190, 20);

        lblIniCon.setText("Inicio del contrato:");
        lblIniCon.setToolTipText("Fecha de inicio del contrato");
        panCon.add(lblIniCon);
        lblIniCon.setBounds(10, 60, 130, 20);
        panCon.add(txtNomDep);
        txtNomDep.setBounds(170, 40, 130, 20);

        butDep.setText("...");
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panCon.add(butDep);
        butDep.setBounds(300, 40, 20, 20);

        lblSec.setText("Plantilla:");
        lblSec.setToolTipText("");
        panCon.add(lblSec);
        lblSec.setBounds(330, 40, 130, 20);

        jCmbEstPerPru.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pendiente", "Continuar contrato", "No continuar contrato" }));
        panCon.add(jCmbEstPerPru);
        jCmbEstPerPru.setBounds(520, 80, 150, 20);

        jPanel1.setPreferredSize(new java.awt.Dimension(440, 402));
        jPanel1.setLayout(new java.awt.BorderLayout());

        tblRep.setModel(new javax.swing.table.DefaultTableModel(
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
        tblRep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblRepFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblRep);

        jPanel1.add(spnDat, java.awt.BorderLayout.CENTER);

        panCon.add(jPanel1);
        jPanel1.setBounds(0, 190, 680, 120);

        lblFinCon1.setText("Finalización del contrato:");
        lblFinCon1.setToolTipText("Fecha de finalización del contrato");
        panCon.add(lblFinCon1);
        lblFinCon1.setBounds(10, 100, 200, 20);

        txtValAlm.setToolTipText("Valor del almuerzo");
        txtValAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValAlmActionPerformed(evt);
            }
        });
        txtValAlm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValAlmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValAlmFocusLost(evt);
            }
        });
        txtValAlm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValAlmKeyPressed(evt);
            }
        });
        panCon.add(txtValAlm);
        txtValAlm.setBounds(630, 120, 40, 20);

        jCmbForRec.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Forma", "Rol", "Comida" }));
        jCmbForRec.setToolTipText("Forma en la que recibe el almuerzo");
        jCmbForRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbForRecActionPerformed(evt);
            }
        });
        panCon.add(jCmbForRec);
        jCmbForRec.setBounds(520, 120, 110, 22);

        jCmbRecAlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        jCmbRecAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbRecAlmActionPerformed(evt);
            }
        });
        panCon.add(jCmbRecAlm);
        jCmbRecAlm.setBounds(170, 120, 50, 22);

        lblFinCon2.setText("Recibe Almuerzo:");
        lblFinCon2.setToolTipText("Fecha de finalización del contrato");
        panCon.add(lblFinCon2);
        lblFinCon2.setBounds(10, 120, 130, 20);

        lblCarLab.setText("Cargo laboral:");
        lblCarLab.setToolTipText("Fecha de inicio del contrato");
        panCon.add(lblCarLab);
        lblCarLab.setBounds(10, 19, 100, 20);

        txtCodDep.setEnabled(false);
        txtCodEstCiv.getDocument().addDocumentListener(zafRecHum03Bean.getDocLis());
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        panCon.add(txtCodDep);
        txtCodDep.setBounds(140, 40, 30, 20);
        txtCodDep.setEditable(false);
        txtCodDep.setVisible(false);

        txtCodOfi.setEnabled(false);
        txtCodOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodOfiActionPerformed(evt);
            }
        });
        panCon.add(txtCodOfi);
        txtCodOfi.setBounds(490, 20, 30, 22);
        txtCodOfi.setVisible(false);

        txtNomOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOfiActionPerformed(evt);
            }
        });
        panCon.add(txtNomOfi);
        txtNomOfi.setBounds(520, 20, 130, 22);

        jBTOf.setText("jButton1");
        jBTOf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTOfActionPerformed(evt);
            }
        });
        panCon.add(jBTOf);
        jBTOf.setBounds(650, 20, 20, 20);

        lblDep2.setText("Oficina:");
        lblDep2.setToolTipText("Fecha de inicio del contrato");
        panCon.add(lblDep2);
        lblDep2.setBounds(330, 19, 100, 20);

        txtCodCarLab.setEnabled(false);
        panCon.add(txtCodCarLab);
        txtCodCarLab.setBounds(140, 20, 30, 22);
        //txtCodCarLab.setVisible(false);
        panCon.add(txtNomCarLab);
        txtNomCarLab.setBounds(170, 20, 130, 22);

        jBCL.setText("jButton1");
        jBCL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCLActionPerformed(evt);
            }
        });
        panCon.add(jBCL);
        jBCL.setBounds(300, 20, 20, 20);

        tabGen.addTab("Contrato", panCon);

        panTab.add(tabGen, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panTab, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 448);
    }// </editor-fold>//GEN-END:initComponents

    private void butCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCodTraActionPerformed
        zafRecHum03Bean.mostrarVenConDep();
    }//GEN-LAST:event_butCodTraActionPerformed

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
        zafRecHum03Bean.mostrarVenConCiu();
    }//GEN-LAST:event_butCiuActionPerformed

    private void butEstCivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEstCivActionPerformed
        zafRecHum03Bean.mostrarVenEstCiv();
    }//GEN-LAST:event_butEstCivActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtIdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeActionPerformed
        
            if(!zafRecHum03Bean.validarCedRep(txtIde.getText().trim())){
                try {

                    VerificarId verId = new VerificarId();

                    boolean blnExisteId = verId.verificarCedRegCiv(txtIde.getText());
                    if(blnExisteId){
                        ZafRegCiv zafRegCiv = verId.getRegistroCivil();
                        if(zafRegCiv!=null){
                            //info automaticamente se ingresa
                            String[] strArrNomApe = zafRegCiv.getStrNomCed().split(" ");
                            if(strArrNomApe!=null){
                                if(strArrNomApe.length==4){
                                    txtNomTra.setText(strArrNomApe[2] + " " + strArrNomApe[3]);
                                    txtApeTra.setText(strArrNomApe[0] + " " + strArrNomApe[1]);
                                    txtDir.requestFocus();
                                }
                                if(strArrNomApe.length>=5 || strArrNomApe.length<=3){


                                    String strNomApe = "";
                                    for(int i = 0; i < strArrNomApe.length; i++){
                                        strNomApe += strArrNomApe[i].toString() + " ";
                                    }

                                    String strTit, strMsg;
                                    strMsg ="Introduzca Datos:";
                                    strTit="Mensaje del sistema Zafiro";
                                    
                                    JPanel panel = new JPanel(new GridLayout(3,2));
                                    JLabel lblNomApe = new JLabel("Apellidos/Nombres:",JLabel.RIGHT);
                                    JTextField txtNomApe = new JTextField(strNomApe);
                                    panel.add(lblNomApe);
                                    panel.add(txtNomApe);
                                    JLabel lblApe = new JLabel("Apellidos:",JLabel.RIGHT);
                                    JTextField txtApe = new JTextField();
                                    panel.add(lblApe);
                                    panel.add(txtApe);
                                    JLabel lblNom = new JLabel("Nombres:",JLabel.RIGHT);
                                    JTextField txtNom = new JTextField();
                                    panel.add(lblNom);
                                    panel.add(txtNom);
                                    
                                    if(javax.swing.JOptionPane.showConfirmDialog(this,panel,strMsg,javax.swing.JOptionPane.OK_CANCEL_OPTION,javax.swing.JOptionPane.PLAIN_MESSAGE)==javax.swing.JOptionPane.OK_OPTION)
                                    {
                                        txtNomTra.setText(txtNom.getText());
                                        txtApeTra.setText(txtApe.getText());
                                        txtDir.requestFocus();
                                    }else{
                                        txtNomTra.requestFocus();
                                    }
                            }
                            String[] strArrFecNac = zafRegCiv.getStrFecNac().split("/");
                            if(strArrFecNac!=null){
                                if(strArrFecNac.length==3){
                                    dtpFecNac.setText(Integer.parseInt(strArrFecNac[2]), Integer.parseInt(strArrFecNac[1]), Integer.parseInt(strArrFecNac[0]));
                                }
                            }

                            String[] strEstCiv = zafRegCiv.getStrEstCiv().split("/");

                            if(strEstCiv!=null){
                                zafRecHum03Bean.consultarEstadoCivil(strEstCiv[0]);
                            }
                        }
                    }else{
                        boolean blnExisteIdNumVer = verId.verificarCed(txtIde.getText());
                        if(blnExisteIdNumVer){
                            txtNomTra.requestFocus();
                        }
                      }
                    }
                }catch (TuvalUtilitiesException ex) {
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = ex.getMessage();
                    JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    txtIde.requestFocus();
                    txtIde.selectAll();
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
//                objUti.mostrarMsgErr_F1(this, ex);
                txtNomTra.requestFocus();
            }catch (Exception ex) {
                ex.printStackTrace();
                objUti.mostrarMsgErr_F1(this, ex);
            }
        } 
    }//GEN-LAST:event_txtIdeActionPerformed

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtApeTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApeTraActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtApeTraActionPerformed

    private void txtDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtDirActionPerformed

    private void txtRefDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRefDirActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtRefDirActionPerformed

    private void txtTel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTel1ActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtTel1ActionPerformed

    private void txtTel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTel2ActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtTel2ActionPerformed

    private void txtTel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTel3ActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtTel3ActionPerformed

    private void txtEmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmaActionPerformed
        zafRecHum03Bean.getTooBar().consultar();
        zafRecHum03Bean.setBlnMod(false);
    }//GEN-LAST:event_txtEmaActionPerformed

    private void txtCodCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCiuActionPerformed
        try {
            //zafRecHum03Bean.getTooBar().consultar();
            zafRecHum03Bean.consultarCiudad(Integer.parseInt(txtCodCiu.getText()));
        } catch (Exception ex) {
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
            JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            //objUti.mostrarMsgErr_F1(zafRecHum03, ex);
        }
    }//GEN-LAST:event_txtCodCiuActionPerformed

    private void txtCodEstCivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEstCivActionPerformed
        try {
            //zafRecHum03Bean.getTooBar().consultar();
            zafRecHum03Bean.consultarEstadoCivil(Integer.parseInt(txtCodEstCiv.getText()));
        } catch (Exception ex) {
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "No se encontraron datos con los criterios de búsqueda.\nEspecifique otros criterios y vuelva a intentarlo";
            JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            //objUti.mostrarMsgErr_F1(this, ex);
        }
    }//GEN-LAST:event_txtCodEstCivActionPerformed

    private void txtIdeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeFocusLost
        //verificarCedula();
    }//GEN-LAST:event_txtIdeFocusLost

    private void txtNumCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCtaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumCtaActionPerformed

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        // TODO add your handling code here:
        strCodDep=txtCodDep.getText();
//        optFil.setSelected(true);
//        optTod.setSelected(false);
        mostrarVenConDep(0);
    }//GEN-LAST:event_butDepActionPerformed

    private void tblRepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblRepFocusGained
//        if(txtMinGra.getText().equals("")){
//            txtMinGra.setText(" ");
//        }
    }//GEN-LAST:event_tblRepFocusGained

    private void jCmbRecAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbRecAlmActionPerformed
        // TODO add your handling code here:
        if(jCmbRecAlm.getSelectedIndex()==1){
            jCmbForRec.setEnabled(false);
            txtValAlm.setEnabled(false);
        }else{
            jCmbForRec.setEnabled(true);
            txtValAlm.setEnabled(true);
        }
    }//GEN-LAST:event_jCmbRecAlmActionPerformed

    private void jCmbForRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbForRecActionPerformed
        // TODO add your handling code here:
        if(jCmbForRec.getSelectedIndex()==0){
            txtValAlm.setEnabled(false);
        }else{
            txtValAlm.setEnabled(true);
            txtValAlm.requestFocus();
        }
    }//GEN-LAST:event_jCmbForRecActionPerformed

    private void txtValAlmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValAlmFocusLost
        boolean blnOk=true;
        try{
            if(!txtValAlm.getText().equals("")){
                
                double dblValAlm = Double.parseDouble(txtValAlm.getText());
                if(dblValAlm>2.5){
                    blnOk = false;
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Valor del almuerzo excede la cantidad permitida.\nLlene el campo y vuelva a intentarlo";
                    JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    txtValAlm.setText("");
                    txtValAlm.requestFocus();
                }
            }
        }catch(NumberFormatException ex){
            blnOk = false;
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Valor del almuerzo debe contener sólo números.\nLlene el campo y vuelva a intentarlo";
            JOptionPane.showMessageDialog(this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtValAlm.setText("");
            txtValAlm.requestFocus();
        }
        
    }//GEN-LAST:event_txtValAlmFocusLost

    private void txtValAlmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValAlmKeyPressed
//        verificaNum();
    }//GEN-LAST:event_txtValAlmKeyPressed

    private void txtValAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValAlmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValAlmActionPerformed

    private void txtValAlmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValAlmFocusGained
        txtValAlm.selectAll();
    }//GEN-LAST:event_txtValAlmFocusGained

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void jBTOfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTOfActionPerformed
        strCodOfi=txtCodOfi.getText();
        mostrarVenConOfi(0);
    }//GEN-LAST:event_jBTOfActionPerformed

    private void jBCLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCLActionPerformed
        // TODO add your handling code here:
        strCodCarLab=txtCodCarLab.getText();
        mostrarVenConCarLab(0);
    }//GEN-LAST:event_jBCLActionPerformed

    private void txtNomOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOfiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomOfiActionPerformed

    private void txtCodOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodOfiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodOfiActionPerformed

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
    private boolean mostrarVenConCarLab(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCarLab.setCampoBusqueda(1);
                    vcoCarLab.setVisible(true);
                    if (vcoCarLab.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCarLab.setText(vcoCarLab.getValueAt(1));
                        txtNomCarLab.setText(vcoCarLab.getValueAt(2));
                    }
                    break;
//                case 1: //Búsqueda directa por "Codigo de Departamento".
//                    //vcoDep.setCampoBusqueda(0);
//                    vcoCarLab.setVisible(true);
//                    if (vcoCarLab.buscar("a1.co_ofi", txtCodOfi.getText()))
//                    {
//                        txtCodOfi.setText(vcoCarLab.getValueAt(1));
//                        txtNomOfi.setText(vcoCarLab.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoCarLab.setCampoBusqueda(1);
//                        vcoCarLab.setCriterio1(11);
//                        vcoCarLab.cargarDatos();
//                        vcoCarLab.setVisible(true);
//                        if (vcoCarLab.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            
//                            txtCodOfi.setText(vcoCarLab.getValueAt(1));
//                            txtNomOfi.setText(vcoCarLab.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomOfi.setText(strDesLarOfi);
//                        }
//                    }
//                    break;
//                case 2: //Búsqueda directa por "Descripción larga".
//                    vcoCarLab.setCampoBusqueda(2);
//                    //vcoDep.setVisible(true);
//                    if (vcoCarLab.buscar("a1.tx_nom", txtNomOfi.getText()))
//                    {
//                        txtCodOfi.setText(vcoCarLab.getValueAt(1));
//                        txtNomOfi.setText(vcoCarLab.getValueAt(3));
//                    }
//                    else
//                    {
//                        vcoCarLab.setCampoBusqueda(2);
//                        vcoCarLab.setCriterio1(11);
//                        vcoCarLab.cargarDatos();
//                        vcoCarLab.setVisible(true);
//                        if (vcoCarLab.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodOfi.setText(vcoCarLab.getValueAt(1));
//                            txtNomOfi.setText(vcoCarLab.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomOfi.setText(strDesLarOfi);
//                        }
//                    }
//                    break;
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
    private boolean mostrarVenConOfi(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoOfi.setCampoBusqueda(1);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(1);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoOfi.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.tx_nom", txtNomOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(2);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
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

    
//    private void verificaNum(){
//        
//    }
    

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
    private boolean mostrarVenConDep(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
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
    
    public boolean asignarCargoLaboral(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                
                case 0: //Búsqueda directa por "Codigo de Departamento".
                    vcoCarLab.setCampoBusqueda(0);
//                    vcoDep.setVisible(true);
                    if (vcoCarLab.buscar("a1.co_car", txtCodCarLab.getText()))
                    {
                        txtCodCarLab.setText(vcoCarLab.getValueAt(1));
                        txtNomCarLab.setText(vcoCarLab.getValueAt(2));
                    }
//                    else
//                    {
//                        vcoDep.setCampoBusqueda(1);
//                        vcoDep.setCriterio1(11);
//                        vcoDep.cargarDatos();
////                        vcoDep.setVisible(true);
//                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            
//                            txtCodDep.setText(vcoDep.getValueAt(1));
//                            txtNomDep.setText(vcoDep.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomDep.setText(strDesLarDep);
//                        }
//                    }
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
    
    public boolean asignarDepartamento(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                
                case 0: //Búsqueda directa por "Codigo de Departamento".
                    vcoDep.setCampoBusqueda(0);
//                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
//                    else
//                    {
//                        vcoDep.setCampoBusqueda(1);
//                        vcoDep.setCriterio1(11);
//                        vcoDep.cargarDatos();
////                        vcoDep.setVisible(true);
//                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            
//                            txtCodDep.setText(vcoDep.getValueAt(1));
//                            txtNomDep.setText(vcoDep.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomDep.setText(strDesLarDep);
//                        }
//                    }
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
    
    public boolean asignarOficina(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                
                case 0: //Búsqueda directa por "Codigo de Departamento".
                    vcoOfi.setCampoBusqueda(0);
//                    vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
//                    else
//                    {
//                        vcoDep.setCampoBusqueda(1);
//                        vcoDep.setCriterio1(11);
//                        vcoDep.cargarDatos();
////                        vcoDep.setVisible(true);
//                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            
//                            txtCodDep.setText(vcoDep.getValueAt(1));
//                            txtNomDep.setText(vcoDep.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomDep.setText(strDesLarDep);
//                        }
//                    }
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
//            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
//            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
//            arlAncCol.add("40");
            
            String strSQL="";
            if(zafRecHum03Bean.getZafParSis().getCodigoUsuario()==1){
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+zafRecHum03Bean.getZafParSis().getCodigoUsuario()+" "+
                        "and co_mnu="+zafRecHum03Bean.getZafParSis().getCodigoMenu()+")";
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), zafRecHum03Bean.getZafParSis(), "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Cargos Laborales".
     */
    private boolean configurarVenCarLab()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_car");
            arlCam.add("a1.tx_nomcar");
            arlCam.add("a1.co_comsec");
            arlCam.add("a1.tx_codcomsec");
            arlCam.add("a1.tx_nomcarcomsec");
            arlCam.add("a1.nd_minsec");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cargo");
            arlAli.add("Com.Sec");
            arlAli.add("Cód.Car.Com.Sec.");
            arlAli.add("Nom.Car.Com.Sec.");
            arlAli.add("Mín.Sec.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("60");
            arlAncCol.add("140");
            
            String strSQL="";
            strSQL="select co_car, tx_nomcar, co_comsec, tx_codcomsec, tx_nomcarcomsec, nd_minsec, tx_obs1 from tbm_carlab order by co_car";
            vcoCarLab=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), zafRecHum03Bean.getZafParSis(), "Listado de Cargos Laborales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConOfi()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ofi");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            String strSQL="select co_ofi, tx_nom from tbm_ofi where st_reg='A'";

            vcoOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), zafRecHum03Bean.getZafParSis(), "Listado de Oficinas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoOfi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private void dtpFecNacActionPerformed(java.awt.event.ActionEvent evt) {
        //zafRecHum03Bean.getTooBar().consultar();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.ButtonGroup bgrSex;
    public javax.swing.JButton butCiu;
    public javax.swing.JButton butCodTra;
    public javax.swing.JButton butDep;
    public javax.swing.JButton butEstCiv;
    private javax.swing.JButton jBCL;
    private javax.swing.JButton jBTOf;
    public javax.swing.JComboBox jCmbEstFinCon;
    public javax.swing.JComboBox jCmbEstPerPru;
    public javax.swing.JComboBox jCmbForRec;
    public javax.swing.JComboBox jCmbPla;
    public javax.swing.JComboBox jCmbRecAlm;
    public javax.swing.JComboBox jCmbTipCta;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblApe;
    private javax.swing.JLabel lblCarLab;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblDep;
    private javax.swing.JLabel lblDep2;
    private javax.swing.JLabel lblDir;
    private javax.swing.JLabel lblEma;
    private javax.swing.JLabel lblEstCiv;
    private javax.swing.JLabel lblEstFinCon;
    private javax.swing.JLabel lblEstPerPru;
    private javax.swing.JLabel lblFecNac;
    private javax.swing.JLabel lblFinCon;
    private javax.swing.JLabel lblFinCon1;
    private javax.swing.JLabel lblFinCon2;
    private javax.swing.JLabel lblFinReaCon;
    private javax.swing.JLabel lblIde;
    private javax.swing.JLabel lblIniCon;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblNumCta;
    private javax.swing.JLabel lblNumHij;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPerPru;
    private javax.swing.JLabel lblRef;
    private javax.swing.JLabel lblSec;
    private javax.swing.JLabel lblSex;
    private javax.swing.JLabel lblTel1;
    private javax.swing.JLabel lblTel2;
    private javax.swing.JLabel lblTel3;
    private javax.swing.JLabel lblTipCta;
    private javax.swing.JLabel lblTit;
    public javax.swing.JRadioButton optSexFem;
    public javax.swing.JRadioButton optSexMas;
    private javax.swing.JPanel panBar;
    public javax.swing.JPanel panCon;
    private javax.swing.JPanel panConFam;
    private javax.swing.JPanel panDoc;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panTab;
    private javax.swing.JScrollPane scrCon;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs3;
    private javax.swing.JScrollPane srcDoc;
    public javax.swing.JTabbedPane tabGen;
    public javax.swing.JTable tblCon;
    public javax.swing.JTable tblDoc;
    public javax.swing.JTable tblRep;
    public javax.swing.JTextArea txaObs1;
    public javax.swing.JTextArea txaObs2;
    public javax.swing.JTextField txtApeTra;
    public javax.swing.JTextField txtCodCarLab;
    public javax.swing.JTextField txtCodCiu;
    public javax.swing.JTextField txtCodDep;
    public javax.swing.JTextField txtCodEstCiv;
    public javax.swing.JTextField txtCodOfi;
    public javax.swing.JTextField txtCodTra;
    public javax.swing.JTextField txtDir;
    public javax.swing.JTextField txtEma;
    public javax.swing.JTextField txtIde;
    public javax.swing.JTextField txtNomCarLab;
    public javax.swing.JTextField txtNomCiu;
    public javax.swing.JTextField txtNomDep;
    public javax.swing.JTextField txtNomEstCiv;
    public javax.swing.JTextField txtNomOfi;
    public javax.swing.JTextField txtNomTra;
    public javax.swing.JTextField txtNumCta;
    public javax.swing.JTextField txtNumHij;
    public javax.swing.JTextField txtRefDir;
    public javax.swing.JTextField txtTel1;
    public javax.swing.JTextField txtTel2;
    public javax.swing.JTextField txtTel3;
    public javax.swing.JTextField txtValAlm;
    // End of variables declaration//GEN-END:variables

}