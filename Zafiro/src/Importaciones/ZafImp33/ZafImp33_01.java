/*
 * ZafImp33_01.java
 * Panel "Cargos a Pagar"
 */

package Importaciones.ZafImp33;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.event.EventListenerList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import javax.swing.JScrollBar;
/**
 * @author  Ingrid Lino
 */
public class ZafImp33_01 extends javax.swing.JPanel
{
    //Constantes: Columnas del JTable.
    public static final int INT_TBL_DAT_LIN=0;                     //Línea
    public static final int INT_TBL_DAT_TIP_CAR_PAG_IMP=1;         //Tipo(F:Flete, A:Arancel, G:Gasto) de cargo a pagar por importacion
    public static final int INT_TBL_DAT_COD_CAR_PAG_IMP=2;         //Código de cargo a pagar por importacion
    public static final int INT_TBL_DAT_NOM_CAR_PAG_IMP=3;         //Nombre de cargo a pagar por importacion
    public static final int INT_TBL_DAT_VAL_CAR_PAG_IMP=4;         //Valor de cargo a pagar por importacion
    public static final int INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX=5;     //Valor auxiliar de cargo a pagar por importacion(me sirve cuando se haga algun cambio)
    public static final int INT_TBL_DAT_TIP_NOT_PED=6;             //Para saber que tipo de nota de pedido es el que se debe aplicar.
    public static final int INT_TBL_DAT_GEN_PRO=7;                 //Permite conocer si debe generar provision o no
    public static final int INT_TBL_DAT_TIP_PAG_EXT=8;             //Indica si el tipo de pago es al exterior.
    public static final int INT_TBL_DAT_GEN_ASI_DIA=9;             //Permite conocer si debe generar ese rubro en el asiento de diario
    public static final int INT_TBL_DAT_EST_CAR_PAG_CON=10;             //Permite conocer si debe generar ese rubro en el asiento de diario
    
    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;    

    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    public ZafTblMod objTblMod; //Rosee
    private ZafTblTot objTblTot;
    private ZafTblCelRenLbl objTblCelRenLbl;             //Render: Presentar JLabel en JTable.
    public ZafTblCelEdiTxt objTblCelEdiTxt;    //Rosee         //Editor: JTextField en celda.
    private ZafMouMotAda objMouMotAda;                   //ToolTipText en TableHeader.
    private ZafTblModLisImp33_01 objTblModLis;           //Detectar cambios de valores en las celdas.
    private ZafTblPopMnu objTblPopMnu;                   //PopupMenu: Establecer PopMenú en JTable.
    private ZafTblEdi objTblEdi;
    private ZafImp33_02 objImp33_02;
    
    private Vector vecDat, vecCab, vecReg, vecAux;

    private boolean blnCon;                              //true: Continua la ejecución del hilo.
    private boolean blnAsiDiaEdi;                        //true: El asiento de diario es editable.
    private boolean blnCanOpe;                           //Determina si se debe cancelar la operación.
    private boolean blnHayCam;
    public boolean blnIsPerModCarPag;
    
    private BigDecimal bgdFleItm;
    private int intFilItm, intColItm;

    private int intFilAra, intColAra;
    private int intFilGas, intColGas;
    
    private BigDecimal bgdDerAra;
    private BigDecimal bgdTotGas;
    private BigDecimal bgdTotPro;
    private BigDecimal bgdValTotAra;
    private BigDecimal bgdValTotFac;
    private BigDecimal bgdValTotPagExt;
    private String strNumPedCon;
    private String strSQL;    

    protected EventListenerList objEveLisLis=new EventListenerList();
    private char chrOpcManDatTooBar;

    /** Crea una nueva instancia de la clase ZafImp33_01. */
    public ZafImp33_01(ZafParSis obj)
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        blnCanOpe=false;
        blnHayCam=false;
        bgdValTotAra=new BigDecimal(BigInteger.ZERO);
        strNumPedCon="";
        configurarFrm();
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
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
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(11);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_TIP_CAR_PAG_IMP,"Tip.Car.");
            vecCab.add(INT_TBL_DAT_COD_CAR_PAG_IMP,"Cód.Car.");
            vecCab.add(INT_TBL_DAT_NOM_CAR_PAG_IMP,"Cargo");
            vecCab.add(INT_TBL_DAT_VAL_CAR_PAG_IMP,"Valor");
            vecCab.add(INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,"Val.Aux.");
            vecCab.add(INT_TBL_DAT_TIP_NOT_PED,"Tip.Not.Ped.");
            vecCab.add(INT_TBL_DAT_GEN_PRO,"Gen.Pro.");
            vecCab.add(INT_TBL_DAT_TIP_PAG_EXT,"Pag.Ext.");
            vecCab.add(INT_TBL_DAT_GEN_ASI_DIA,"Gen.Asi.Dia.");
            vecCab.add(INT_TBL_DAT_EST_CAR_PAG_CON,"Est.Car.Pag.Con.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_CAR_PAG_IMP, objTblMod.INT_COL_DBL, null, null);
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX, objTblMod.INT_COL_DBL, null, null);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_VAL_CAR_PAG_IMP);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
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
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TIP_CAR_PAG_IMP).setPreferredWidth(43);
            tcmAux.getColumn(INT_TBL_DAT_COD_CAR_PAG_IMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CAR_PAG_IMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_GEN_PRO).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_TIP_PAG_EXT).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_GEN_ASI_DIA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_EST_CAR_PAG_CON).setPreferredWidth(40);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_COD_CAR_PAG_IMP).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
//            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CAR_PAG_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_NOT_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_GEN_PRO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_PAG_EXT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_GEN_ASI_DIA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_CAR_PAG_CON, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_VAL_CAR_PAG_IMP);
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
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_CAR_PAG_IMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;


            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CAR_PAG_IMP).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strTipCar="";
                String strTipNotPed="";
                String strEstPedCon="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    strTipCar=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                    strTipNotPed=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_NOT_PED)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_NOT_PED).toString();
                    strEstPedCon=objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_CAR_PAG_CON)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_CAR_PAG_CON).toString();
                    if(blnIsPerModCarPag==true){
                        if( (strTipNotPed.equals("")) ){//no se ha seleccionado aun ningun Tipo de Nota de Pedido : TM FOB, TM CFR, .....   o si es el  TM CFR
                            objTblCelEdiTxt.setCancelarEdicion(true);
                        }
                        else if( (strTipNotPed.equals("2"))  || (strTipNotPed.equals("4"))){
                            if(strTipCar.equals("F")){
                                objTblCelEdiTxt.setCancelarEdicion(true);
                            }
                        }
                        else{
                            objTblCelEdiTxt.setCancelarEdicion(false);
                        }
                        
                        System.out.println("chrOpcManDatTooBar: " +chrOpcManDatTooBar);
                        System.out.println("strNumPedCon: " +strNumPedCon);
                        System.out.println("strEstPedCon: " +strEstPedCon);
                        
                        
                        
                        if( (chrOpcManDatTooBar=='x')  ||  (chrOpcManDatTooBar=='m')  ){//por modificacion
                            if(strNumPedCon.length()>0){//Pertenece a un consolidado
                                if(!strEstPedCon.equals("N")){
                                    objTblCelEdiTxt.setCancelarEdicion(true);
                                    mostrarMsgInf("<HTML>El Pedido pertenece a un consolidado.<BR>No se puede modificar un cargo que está configurado para cálculo consolidado.<BR>Por favor verifique y vuelva a intentarlo.</HTML>");
                                }
                                else
                                    objTblCelEdiTxt.setCancelarEdicion(false);//aunque pertenece a un pedido consolidado, el cargo a pagar no es consolidado por tanto si se permite editar
                            }
                            else//no es de consolidado y se permite modificar
                                objTblCelEdiTxt.setCancelarEdicion(false);
                        }
                        
                    }
                    else
                        objTblCelEdiTxt.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(strTipCar.equals("F")){
                        bgdFleItm=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                        calculaTotalFletes();
                        intFilItm=intFil;
                        intColItm=intCol;
                    }
                    else if(strTipCar.equals("A")){
                        calculaTotalAranceles();                           
                        
                        intFilAra=intFil;
                        intColAra=intCol;
                    }
                    else if(strTipCar.equals("G")){
                        calculaTotalGastos();
                        intFilGas=intFil;
                        intColGas=intCol;
                    }
                    objImp33_02.regenerarCalculos();

                }
            });

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLisImp33_01();
            objTblMod.addTableModelListener(objTblModLis);
            //Establecer parámetros predeterminados para la clase.
            setEditable(false);
            //Libero los objetos auxiliares.
            tcmAux=null;

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_CAR_PAG_IMP, INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
//            objTblTot.insertMoreRows(2);
//            objTblTot.setValueAt("Total Fletes:",    0, INT_TBL_DAT_NOM_CAR_PAG_IMP);
//            objTblTot.setValueAt("Total Aranceles:", 1, INT_TBL_DAT_NOM_CAR_PAG_IMP);
//            objTblTot.setValueAt("Total Gastos:",    2, INT_TBL_DAT_NOM_CAR_PAG_IMP);
              inicializarTablaTotales();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
                case INT_TBL_DAT_TIP_CAR_PAG_IMP:
                    strMsg="Tipo de Cargo";
                    break;
                case INT_TBL_DAT_COD_CAR_PAG_IMP:
                    strMsg="Código del cargo";
                    break;
                case INT_TBL_DAT_NOM_CAR_PAG_IMP:
                    strMsg="Nombre del cargo";
                    break;
                case INT_TBL_DAT_VAL_CAR_PAG_IMP:
                    strMsg="Valor a pagar";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLisImp33_01 implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)   {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    blnHayCam=true;
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    blnHayCam=true;
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    blnHayCam=true;
                    if ( (tblDat.getEditingColumn()==INT_TBL_DAT_VAL_CAR_PAG_IMP) ){

                    }
                    break;
            }
        }
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTot = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panActCar = new javax.swing.JPanel();
        butActCar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        add(panDat, java.awt.BorderLayout.CENTER);

        panTot.setPreferredSize(new java.awt.Dimension(100, 90));
        panTot.setLayout(new java.awt.BorderLayout());

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTot.setViewportView(tblTot);

        panTot.add(spnTot, java.awt.BorderLayout.CENTER);

        panActCar.setPreferredSize(new java.awt.Dimension(100, 30));
        panActCar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 1));

        butActCar.setText("Actualizar lista de cargos");
        butActCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butActCarActionPerformed(evt);
            }
        });
        panActCar.add(butActCar);

        panTot.add(panActCar, java.awt.BorderLayout.SOUTH);

        add(panTot, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void butActCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butActCarActionPerformed
        regenerarDatosCargoPagar();
        
    }//GEN-LAST:event_butActCarActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butActCar;
    private javax.swing.JPanel panActCar;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panTot;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables
    
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
     * Esta función inicializa el asiento de diario.
     * @return true: Si se pudo inicializar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean inicializar()
    {
        boolean blnRes=true;
        objTblMod.removeAllRows();
        objTblMod.initRowsState();
        blnHayCam=false;
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
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }
    
    public void setEditable(boolean editable, int codigo)
    {
        blnAsiDiaEdi=editable;
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }    


    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son vólidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if(objTblMod.getRowCountTrue()<=0){
            mostrarMsgInf("<HTML>Los cargos a pagar no se han generado.<BR>Verifique si han sido ingresados en el maestro y vuelva a intentarlo</HTML>");
            return false;
        }
        objTblMod.removeEmptyRows();
        return true;
    }
        
    /**
     * Esta función genera la información de Cargos a Pagar, presenta todos los cargos.
     * @return true: Si se pudo generar la información.
     * <BR>false: En el caso contrario.
     */
    public boolean generarDatosCargoPagar(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_carpag, a1.tx_nom, a1.tx_tipcarpag, a1.tx_obs1, a1.st_reg, a1.st_genPro, a1.st_genAsiDia";
                strSQL+="     , (CASE WHEN a1.st_pagExt IS NULL THEN 'N' ELSE a1.st_pagExt END) AS st_pagExt, a1.st_carPagPedCon";
                strSQL+=" FROM tbm_carPagImp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I')";
                strSQL+=" ORDER BY a1.ne_ubi";
                //strSQL+=" ORDER BY a1.tx_tipcarpag, a1.co_carpag";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_TIP_CAR_PAG_IMP,     rst.getString("tx_tipcarpag"));
                    vecReg.add(INT_TBL_DAT_COD_CAR_PAG_IMP,     rst.getString("co_carpag"));
                    vecReg.add(INT_TBL_DAT_NOM_CAR_PAG_IMP,     rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_VAL_CAR_PAG_IMP,     null);
                    vecReg.add(INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX, null);
                    vecReg.add(INT_TBL_DAT_TIP_NOT_PED,         "");
                    vecReg.add(INT_TBL_DAT_GEN_PRO,             rst.getString("st_genPro"));
                    vecReg.add(INT_TBL_DAT_TIP_PAG_EXT,         rst.getString("st_pagExt"));
                    vecReg.add(INT_TBL_DAT_GEN_ASI_DIA,         rst.getString("st_genAsiDia"));
                    vecReg.add(INT_TBL_DAT_EST_CAR_PAG_CON,     rst.getString("st_carPagPedCon"));
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
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
     * Esta función permite sumar el valor de todos los tipos de fletes en la tabla tbm_carPagImp
     * @return true: Si se pudo calcular el valor del flete
     * <BR>false: En el caso contrario.
     */
    public boolean calculaTotalFletes(){
        boolean blnRes=true;
        Object objValFilFle=null;
        BigDecimal bgdValTotFle=BigDecimal.ZERO;
        String strTipCar="";
        try{
            for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                strTipCar=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                if(strTipCar.equals("F")){
                    objValFilFle=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    if((objValFilFle==null) || (objValFilFle.equals("")) ){
                         blnRes=false;
                    }
                    else{
                        bgdValTotFle=bgdValTotFle.add(new BigDecimal(objValFilFle.toString()));
                    }                       
                }
            }

            if(bgdValTotFle.compareTo(BigDecimal.ZERO)>=0)
                 objTblTot.setValueAt(bgdValTotFle, 0, INT_TBL_DAT_VAL_CAR_PAG_IMP);

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite sumar el valor de todos los tipos de aranceles en la tabla tbm_carPagImp
     * @return true: Si se pudo calcular el valor del arancel
     * <BR>false: En el caso contrario.
     */
    public boolean calculaTotalAranceles(){
        boolean blnRes=true;
        Object objValFilTotAra=null;
        BigDecimal bgdValTotAra=BigDecimal.ZERO;
        String strTipCar="";
        try{
            for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                strTipCar=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                if(strTipCar.equals("A")){
                    objValFilTotAra=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    if((objValFilTotAra==null) || (objValFilTotAra.equals("")) ){
                        blnRes=false;
                    }
                    else{
                        bgdValTotAra=bgdValTotAra.add(new BigDecimal(objValFilTotAra.toString()));
                    }                   
                }
            }

            if(bgdValTotAra.compareTo(BigDecimal.ZERO)>=0){
                objTblTot.setValueAt(bgdValTotAra, 1, INT_TBL_DAT_VAL_CAR_PAG_IMP);
                bgdDerAra=bgdValTotAra;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite sumar el valor de todos los tipos de gastos en la tabla tbm_carPagImp
     * @return true: Si se pudo calcular el valor del gasto
     * <BR>false: En el caso contrario.
     */
    public boolean calculaTotalGastos(){
        boolean blnRes=true;
        Object objValFilTotGas=null;
        BigDecimal bgdValTotGas=new BigDecimal(BigInteger.ZERO);
        String strTipCar="";
        try{
            for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                strTipCar=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
                if(strTipCar.equals("G")){
                    objValFilTotGas=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    if((objValFilTotGas==null) || (objValFilTotGas.equals("")) ){                       
                        blnRes=false;
                    }
                    else{
                        bgdValTotGas=bgdValTotGas.add(new BigDecimal(objValFilTotGas.toString()));
                    }
                }
            }
            if(bgdValTotGas.compareTo(BigDecimal.ZERO)>=0){
                 objTblTot.setValueAt(bgdValTotGas, 2, INT_TBL_DAT_VAL_CAR_PAG_IMP);
                 bgdTotGas=bgdValTotGas;
            }   
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
   /**
    * Esta función permite sumar el valor de todos los tipos de gastos que generan provisión
    * @return true: Si se pudo calcular el el valor
    * <BR>false: En el caso contrario.
    */
   public boolean calculaTotalCargosProvision(){
       boolean blnRes=true;
       Object objValCarPag=null;
       BigDecimal bgdValTotCarPag=new BigDecimal(BigInteger.ZERO);
       String strGenPro="";
       try{
           for(int i=0;i<objTblMod.getRowCountTrue(); i++){
               strGenPro=objTblMod.getValueAt(i, INT_TBL_DAT_GEN_PRO)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_GEN_PRO).toString();
               if(strGenPro.equals("S")){
                   objValCarPag=objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP);
                   
                   if((objValCarPag==null) || (objValCarPag.equals("")) ){ 
                       blnRes=false;
                   }
                   else{
                       bgdValTotCarPag=bgdValTotCarPag.add(new BigDecimal(objValCarPag.toString()));
                   }
               }
           }
            if(bgdValTotCarPag.compareTo(BigDecimal.ZERO)>=0){
                bgdTotPro=bgdValTotCarPag;
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
    


    public BigDecimal getFleteItem() {
        return bgdFleItm;
    }

    public void setFleteItem(BigDecimal bgdFleItm) {
        this.bgdFleItm = bgdFleItm;
    }

    public BigDecimal getDerechosArancelarios() {
        return bgdDerAra;
    }

    public void setDerechosArancelarios(BigDecimal bgdDerAra) {
        this.bgdDerAra = bgdDerAra;
    }

    public int getFilaValorArancel() {
        return intFilAra;
    }

    public void setFilaValorArancel(int intFilAra) {
        this.intFilAra = intFilAra;
    }

    public int getColumnaValorArancel() {
        return intColAra;
    }

    public void setColumnaValorArancel(int intColAra) {
        this.intColAra = intColAra;
    }

    public BigDecimal getTotalGastos() {
        return bgdTotGas;
    }

    public void setTotalGastos(BigDecimal bgdTotGas) {
        this.bgdTotGas = bgdTotGas;
    }

    
    public BigDecimal getTotalProvision() {
        return bgdTotPro;
    }

    public void setTotalProvision(BigDecimal bgdTotPro) {
        this.bgdTotPro = bgdTotPro;
    }
    

    public int getFilaTotalGasto() {
        return intFilGas;
    }

    public void setFilaTotalGasto(int intFilAra) {
        this.intFilGas = intFilGas;
    }

    public int getColumnaTotalGasto() {
        return intColGas;
    }

    public void setColumnaTotalGasto(int intColAra) {
        this.intColGas = intColGas;
    }

    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    public void setValueAt(BigDecimal valor, int row, int col)
    {
        objTblMod.setValueAt(valor, row, col);
    }

    public void setObjectoImp33_02(ZafImp33_02 objImp33_02){
        this.objImp33_02=objImp33_02;
    }

    public ZafTblMod getTblModImp33_01() {
        return objTblMod;
    }

    public void setTblModImp33_01(ZafTblMod objTblMod) {
        this.objTblMod = objTblMod;
    }
      
    public void setTipoNotaPedido(int tipoNotaPedido){
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            objTblMod.setValueAt("" + tipoNotaPedido, i, INT_TBL_DAT_TIP_NOT_PED);
        }
    }

    public void setValorFleteInactivo(){
        String strTipCarIna="";
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            strTipCarIna=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
            if(strTipCarIna.equals("F")){
                objTblMod.setValueAt(null, i, INT_TBL_DAT_VAL_CAR_PAG_IMP);
            }
            calculaTotalFletes();
            objTblTot.setValueAt(null, 0, INT_TBL_DAT_VAL_CAR_PAG_IMP);
        }
    }

    public boolean asignarVectorModeloDatos(Vector vectorDatos){
        boolean blnRes=true;
        try{
            //Asignar vectores al modelo.
            objTblMod.setData(vectorDatos);
            tblDat.setModel(objTblMod);
            vecDat.clear();
//            calculaTotalFletes();
//            calculaTotalAranceles(); 
//            calculaTotalGastos(); 
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean limpiarTablaImp33_01(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
            inicializarTablaTotales();

            bgdFleItm=new BigDecimal(BigInteger.ZERO);
            intFilItm=-1;
            intColItm=-1;
            bgdDerAra=new BigDecimal(BigInteger.ZERO);
            intFilAra=-1;
            intColAra=-1;

            bgdTotGas=new BigDecimal(BigInteger.ZERO);
            intFilGas=-1;
            intColGas=-1;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función genera la información de Cargos a Pagar, presenta todos los cargos.
     * @return true: Si se pudo generar la información.
     * <BR>false: En el caso contrario.
     */
    public boolean regenerarDatosCargoPagar(){//si se da click en el boton de "Actualizar lista de cargos"
        boolean blnRes=true;
        int intCodCarPagImp=-1;
        String strCodCarPagImp="";
        int intFilUltTbl=-1;
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

            //antes de eliminar los que estaban cargados al inicio pero en tiempo de ejecucion fueron anulados o elimnados de la db
            //los tomo para luego verificar si los que estan almacenados como activos en la db estan todos en la tabla de cargos del JTable
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intCodCarPagImp=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));
                if(i==0)
                    strCodCarPagImp="" + intCodCarPagImp;
                else
                    strCodCarPagImp+="," + intCodCarPagImp;
            }
            intCodCarPagImp=-1;

            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();

                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    intCodCarPagImp=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_CAR_PAG_IMP).toString()));

                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_carpag, a1.tx_nom, a1.tx_tipcarpag, a1.tx_obs1, a1.st_reg, a1.st_genPro";
                    strSQL+=" FROM tbm_carpagimp AS a1";
                    strSQL+=" WHERE a1.st_reg NOT IN('E','I')";
                    strSQL+=" AND  a1.co_carpag IN(" + intCodCarPagImp + ")";
                    strSQL+=" ORDER BY a1.ne_ubi";
                    //strSQL+=" ORDER BY a1.tx_tipcarpag, a1.co_carpag";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        objTblMod.setValueAt(rst.getString("tx_nom"), i, INT_TBL_DAT_NOM_CAR_PAG_IMP);
                        objTblMod.setValueAt(rst.getString("tx_tipcarpag"), i, INT_TBL_DAT_TIP_CAR_PAG_IMP);
                        objTblMod.setValueAt(rst.getString("st_genPro"), i, INT_TBL_DAT_GEN_PRO);
                    }
                    else{
                        objTblMod.removeRow(i);
                    }
                }
               
                //para cargar los cargos que se han generado en el maestro en momento en que los datos ya estaban cargados en la tabla
                strSQL="";
                strSQL+=" SELECT a1.co_carpag, a1.tx_nom, a1.tx_tipcarpag, a1.tx_obs1, a1.st_reg, a1.st_genPro, a1.st_genAsiDia";
                strSQL+="     , (CASE WHEN a1.st_pagExt IS NULL THEN 'N' ELSE a1.st_pagExt END) AS st_pagExt, a1.st_carPagPedCon";
                strSQL+=" FROM tbm_carpagimp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I')";
                strSQL+=" AND  a1.co_carpag NOT IN(" + strCodCarPagImp + ")";
                strSQL+=" ORDER BY a1.ne_ubi";
                //strSQL+=" ORDER BY a1.tx_tipcarpag, a1.co_carpag";               
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    objTblMod.insertRow();
                    intFilUltTbl=objTblMod.getRowCountTrue();
                    objTblMod.setValueAt(rst.getString("co_carpag"), intFilUltTbl, INT_TBL_DAT_COD_CAR_PAG_IMP);
                    objTblMod.setValueAt(rst.getString("tx_nom"), intFilUltTbl, INT_TBL_DAT_NOM_CAR_PAG_IMP);
                    objTblMod.setValueAt(rst.getString("tx_tipcarpag"), intFilUltTbl, INT_TBL_DAT_TIP_CAR_PAG_IMP);
                    objTblMod.setValueAt(objTblMod.getValueAt(0, INT_TBL_DAT_TIP_NOT_PED), intFilUltTbl, INT_TBL_DAT_TIP_NOT_PED);
                    objTblMod.setValueAt(rst.getString("st_genPro"), intFilUltTbl, INT_TBL_DAT_GEN_PRO);
                    objTblMod.setValueAt(rst.getString("st_pagExt"), intFilUltTbl, INT_TBL_DAT_TIP_PAG_EXT);
                    objTblMod.setValueAt(rst.getString("st_genAsiDia"), intFilUltTbl, INT_TBL_DAT_GEN_ASI_DIA);
                    objTblMod.setValueAt(rst.getString("st_carPagPedCon"), intFilUltTbl, INT_TBL_DAT_EST_CAR_PAG_CON);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;

            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeEmptyRows();          
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean inicializarTablaTotales(){
        boolean blnRes=true;
        try{
            objTblTot.removeAllRows();
            objTblTot.insertMoreRows(3);
            objTblTot.setValueAt("Total Fletes:",    0, INT_TBL_DAT_NOM_CAR_PAG_IMP);
            objTblTot.setValueAt("Total Aranceles:", 1, INT_TBL_DAT_NOM_CAR_PAG_IMP);
            objTblTot.setValueAt("Total Gastos:",    2, INT_TBL_DAT_NOM_CAR_PAG_IMP);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public boolean isBlnHayCamImp33_01() {
        return blnHayCam;
    }

    public void setBlnHayCamCamImp33_01(boolean blnHayCam) {
        this.blnHayCam = blnHayCam;
    }
	
    public BigDecimal getValorTotalArancel() {
        bgdValTotAra=new BigDecimal(BigInteger.ZERO);
        bgdValTotAra=objUti.redondearBigDecimal(new BigDecimal(objTblTot.getValueAt(1, INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblTot.getValueAt(1, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblTot.getValueAt(1, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString())), objParSis.getDecimalesBaseDatos());
        return bgdValTotAra;
    }
	
    public BigDecimal getValorFleteTotal() {
        return bgdFleItm;
    }

    public void setValorFleteTotal(BigDecimal bgdFleItm) {
        String strTipCar="";

        for(int i=0;i<objTblMod.getRowCountTrue(); i++){
            strTipCar=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString();
            if(strTipCar.equals("F")){
               objTblMod.setValueAt(bgdFleItm, i, INT_TBL_DAT_VAL_CAR_PAG_IMP);
            }
        }
       this.bgdFleItm = bgdFleItm;
       calculaTotalFletes();
    }
    
    public void setBlnIsPerModCarPag(boolean blnIsPerModCarPag) {
        this.blnIsPerModCarPag = blnIsPerModCarPag;
    }	
    
    /**
     * Función se utiliza para calcular los totales de la tabla de cargos a pagar.
     * Inicialmente se utilizó para modificar pedido embarcado, luego de autorizar pago de impuestos.
     */
    public void calcularTotalesCargosPagar(){
        calculaTotalFletes();
        calculaTotalAranceles();
        calculaTotalGastos();
    }
    
    /**
     * Función que retorna el valor del cargo "Valor de la factura".
     * @return 
     */
    public BigDecimal getValorTotalFactura() {
        calcular_CargoValorTotalFactura();
        return bgdValTotFac;
    }
    
    /**
     * Función que retorna el valor del cargo "Valor Total a Pagar al exterior".
     * @return 
     */
    public BigDecimal getValorTotalPagoExterior() {
        calcular_CargoValorTotalPagoExterior();
        return bgdValTotPagExt;
    }        
    
    /**
     * Calcula el valor total de la factura, dentro de la tabla de cargos
     * @return 
     */
    private boolean calcular_CargoValorTotalFactura(){
        boolean blnRes=true;
        String strTipCar="";
        BigDecimal bgdValFac=new BigDecimal(BigInteger.ZERO);
        try{
            bgdValTotFac=new BigDecimal(BigInteger.ZERO);
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                strTipCar=(objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString());
                if(strTipCar.equals("V")){ 
                    bgdValFac=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                    bgdValTotFac=bgdValTotFac.add(bgdValFac);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    

    /**
     * Calcula el valor total de pago al exterior, dentro de la tabla de cargos
     * @return 
     */
    private boolean calcular_CargoValorTotalPagoExterior(){
        boolean blnRes=true;
        String strTipCar="", strTipPagExt="";
        BigDecimal bgdValFac=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasOri=new BigDecimal(BigInteger.ZERO);
        try{
            bgdValTotPagExt=new BigDecimal(BigInteger.ZERO);
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                strTipCar=(objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_CAR_PAG_IMP).toString());
                strTipPagExt=(objTblMod.getValueAt(i, INT_TBL_DAT_TIP_PAG_EXT)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_PAG_EXT).toString());
                if(strTipCar.equals("V")){ 
                    bgdValFac=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()));
                    bgdValTotPagExt=bgdValTotPagExt.add(bgdValFac);
                }
                else {
                    if(strTipPagExt.equals("S")) {  /* Valida los cargos a pagar al exterior, excluyendo VALOR DE LA FACTURA. */
                        bgdValGasOri=bgdValGasOri.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_CAR_PAG_IMP).toString())));
                    }
                }
            }
            /* El cargo: Valor de la factura, menos el (Valor de Gastos Origen + Valor de Intereses). Sería el valor que se validará con Tot.Fob/Cfr.*/
            bgdValTotPagExt=bgdValTotPagExt.subtract(bgdValGasOri); 
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }       
    
    
    /**
     * Función que permite setear el modo de operación del objeto ToolBar
     * @param opcionTooBar El objeto que se recibe referenciado de la clase principal
     */
    public void setModoOperacionTooBar(char opcionTooBar){
        chrOpcManDatTooBar=opcionTooBar;
    }
    
    /**
     * Función que permite setear el número de pedido consolidado
     * @param opcionTooBar El objeto de número de pedido que se recibe referenciado de la clase principal
     */
    public void setNumPedCon(String pedConsolidado){
        strNumPedCon=pedConsolidado;
    }

    

}