/*
 * ZafCopAva.java
 *
 * Created on 18 de abril de 2011, 10:48 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblPopMnu;
import javax.swing.JTable;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import java.util.Vector;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCopAva extends javax.swing.JDialog
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                 //Línea
    static final int INT_TBL_DAT_CHK=1;                 //Casilla de verificación.
    static final int INT_TBL_DAT_NUM_COL=2;             //Número de columna.
    static final int INT_TBL_DAT_DEC_COL=3;             //Descripcion corta de la columna.
    static final int INT_TBL_DAT_DEL_COL=4;             //Descripción larga de la columna.
    //Variables
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnMarTodChkTblDat=true;            //Marcar todas las casillas de verificación del JTable de datos a copiar.
    //Variables de la clase.
    private int intOpcSelDlg;                           //Opción seleccionada en el JDialog.
    private JTable tblDatCop;                           //Tabla que contiene los datos a copiar.
    
    /** Creates new form ZafCopAva */
    public ZafCopAva(java.awt.Frame parent, boolean modal, JTable tblDatCop)
    {
        super(parent, modal);
        this.tblDatCop=tblDatCop;
        initComponents();
        //Inicializar objetos.
        intOpcSelDlg=0;
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
        panNor = new javax.swing.JPanel();
        chkCopCabTab = new javax.swing.JCheckBox();
        chkMosColOcuSis = new javax.swing.JCheckBox();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        panNor.setPreferredSize(new java.awt.Dimension(10, 40));
        panNor.setLayout(new java.awt.GridLayout(2, 1));

        chkCopCabTab.setSelected(true);
        chkCopCabTab.setText("Copiar la cabecera de la tabla");
        panNor.add(chkCopCabTab);

        chkMosColOcuSis.setText("Mostrar las columnas ocultas por el Sistema");
        chkMosColOcuSis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosColOcuSisActionPerformed(evt);
            }
        });
        panNor.add(chkMosColOcuSis);

        panFrm.add(panNor, java.awt.BorderLayout.PAGE_START);

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

        panFrm.add(spnDat, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-300)/2, 400, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        intOpcSelDlg=0;
        dispose();
    }//GEN-LAST:event_exitForm

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        intOpcSelDlg=1;
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg=0;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void chkMosColOcuSisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosColOcuSisActionPerformed
        cargarCol();
    }//GEN-LAST:event_chkMosColOcuSisActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        intOpcSelDlg=0;
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JCheckBox chkCopCabTab;
    private javax.swing.JCheckBox chkMosColOcuSis;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNor;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
 
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle("Copia avanzada v0.1 b1");
            //Configurar objetos.
//            txaObs1.setEditable(false);
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
            vecCab=new Vector(5);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_NUM_COL,"Núm.Col.");
            vecCab.add(INT_TBL_DAT_DEC_COL,"Columna");
            vecCab.add(INT_TBL_DAT_DEL_COL,"Descripción");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
//            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DEC_COL).setPreferredWidth(324);
            tcmAux.getColumn(INT_TBL_DAT_DEL_COL).setPreferredWidth(220);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_COL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_COL, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
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
            objTblCelEdiChk=null;

            //Configurar JTable: Establecer el modo de operación.
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
     * Esta función permite cargar las columnas correspondientes a la tabla que contiene los datos que se desea copiar.
     * @return true: Si se pudo cargar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCol()
    {
        int i, intNumCol;
        boolean blnRes=true;
        try
        {
            //Limpiar vector de datos.
            vecDat.clear();
            intNumCol=tblDatCop.getColumnModel().getColumnCount();
            //Obtener los registros.
            if (chkMosColOcuSis.isSelected())
            {
                //Mostrar todas las columnas.
                for (i=0; i<intNumCol; i++)
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CHK,(tblDatCop.isColumnSelected(i)?Boolean.TRUE:null));
                    vecReg.add(INT_TBL_DAT_NUM_COL,"" + i);
                    vecReg.add(INT_TBL_DAT_DEC_COL,tblDatCop.getColumnModel().getColumn(i).getHeaderValue());
                    vecReg.add(INT_TBL_DAT_DEL_COL,"");
                    vecDat.add(vecReg);
                }
            }
            else
            {
                //Mostrar todas las columnas a excepción de las columnas ocultas por el Sistema.
                for (i=0; i<intNumCol; i++)
                {
                    if (!((Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDatCop.getModel()).isSystemHiddenColumn(i))
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,(tblDatCop.isColumnSelected(i)?Boolean.TRUE:null));
                        vecReg.add(INT_TBL_DAT_NUM_COL,"" + i);
                        vecReg.add(INT_TBL_DAT_DEC_COL,tblDatCop.getColumnModel().getColumn(i).getHeaderValue());
                        vecReg.add(INT_TBL_DAT_DEL_COL,"");
                        vecDat.add(vecReg);
                    }
                }
            }
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();
            blnMarTodChkTblDat=false;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK)
            {
                if (blnMarTodChkTblDat)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
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
    public int getOpcSelDlg()
    {
        return intOpcSelDlg;
    }

    /**
     * PENDIENTE DOCUMENTAR
     * Esta función muestra el cuadro de dialogo y refresca la tabla.
     * Se refresca la tabla porque hay programas donde las columnas de las tablas se generan dinámicamente.
     * Es decir, si sólo se cargara los datos de la tabla a copiar la primera vez se 
     * de diálogo sino que automáticamente se procede a realizar la operación especificada
     * (Impresión directa, Impresión directa (Cuadro de dialogo de impresión), Vista preliminar).
     */
    public void setVisible(boolean visible)
    {
        cargarCol();
        super.setVisible(visible);
    }

    // PENDIENTE DOCUMENTAR
    public boolean isSelCopCabTbl()
    {
        return chkCopCabTab.isSelected();
    }

    /**
     * PENDIENTE DOCUMENTAR
     * Obtiene la fecha inicial en la que se comenzó a utilizar la base de datos seleccionada.
     * @return Un arreglo que contiene la fecha. El arreglo contiene 3 elementos que representan
     * el año, mes y día.
     */
    public int[] getColCop()
    {
        int i, intColCop[];
        vecAux=new Vector();
        //Obtener las filas que estan seleccionadas.
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                vecAux.add("" + objTblMod.getValueAt(i, INT_TBL_DAT_NUM_COL));
            }
        }
        //Pasar los datos del vector al arreglo que se va a devolver.
        intColCop=new int[vecAux.size()];
        for (i=0; i<vecAux.size(); i++)
        {
            intColCop[i]=Integer.parseInt(vecAux.get(i).toString());
        }
        vecAux.clear();
        vecAux=null;
        return intColCop;
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
                    strMsg="";
                    break;
                case INT_TBL_DAT_NUM_COL:
                    strMsg="Numero de columna";
                    break;
                case INT_TBL_DAT_DEC_COL:
                    strMsg="Descripción corta de la columna";
                    break;
                case INT_TBL_DAT_DEL_COL:
                    strMsg="Descripción larga de la columna";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
