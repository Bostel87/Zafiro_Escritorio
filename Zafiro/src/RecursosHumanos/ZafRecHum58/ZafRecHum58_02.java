package RecursosHumanos.ZafRecHum58;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Maestros.ZafMae07.ZafMae07_01;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author  Roberto Flores
 * Guayaquil 18/03/2013
 */
public class ZafRecHum58_02 extends javax.swing.JDialog
{
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;

    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.

    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private ArrayList arlParDlg;
 
    //Variables de la clase.
    private int intOpcSelDlg;                                   //Opción seleccionada en el JDialog.

     // TABLA DE DATOS
    private final int INT_TBL_LIN= 0 ;
    private final int INT_TBL_FECHA= 1 ;
    private final int INT_TBL_REM= 2 ;
    private final int INT_TBL_DES= 3 ;
    private final int INT_TBL_OBS= 4 ;
    private final int INT_TBL_BUT_OBS= 5 ;

    private String strCodNov;
    private ZafRecHum58 objZafRecHum58;
    
    /** Creates new form ZafRecHum58_02 */
    public ZafRecHum58_02(java.awt.Frame parent, boolean modal, ZafParSis obj) 
    {
        super(parent, modal);
        
        vecDat=new Vector();
        //Inicializar objetos.
        objParSis=obj;
        this.strCodNov=strCodNov;
        initComponents();
        intOpcSelDlg=0;
        if (!configurarFrm())
            exitForm();
    }
    
    public boolean cargarDatos(){
        boolean blnRes=true;
        java.sql.Connection conCab;
        try
        {
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                if (cargarCabReg(conCab))
                {
                        blnRes=true;
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
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
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

        setSize(new java.awt.Dimension(600, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        intOpcSelDlg=0;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        intOpcSelDlg=0;
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
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
            strAux="Historial de reenvios";
            this.setTitle(strAux + " v1.0");
            lblTit.setText(strAux);
            arlParDlg=new ArrayList();
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
    
    public void setParDlg(ZafParSis objZafParsis, ZafRecHum58 objRecHum58,String strCodNov) 
    {
        this.objParSis=objZafParsis;
        this.strCodNov=strCodNov;
        this.objZafRecHum58=objRecHum58;
        cargarDatos();
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_FECHA,"Fecha");
    vecCab.add(INT_TBL_REM,"Remitente");
    vecCab.add(INT_TBL_DES,"Destinatario");
    vecCab.add(INT_TBL_OBS,"Observación");
    vecCab.add(INT_TBL_BUT_OBS,"");
    
    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);
    
    //Configurar ZafTblMod: Establecer las columnas obligatorias.
    java.util.ArrayList arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL_BUT_OBS);
    objTblMod.setColumnasObligatorias(arlAux);
    arlAux=null;
    //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
    objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);

    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_FECHA).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_REM).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_DES).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_OBS).setPreferredWidth(220);
    tcmAux.getColumn(INT_TBL_BUT_OBS).setPreferredWidth(20);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
    
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_BUT_OBS);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;

    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BUT_OBS).setCellRenderer(zafTblDocCelRenBut);
    ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_BUT_OBS, "Observación") {
        public void butCLick() {
            int intSelFil = tblDat.getSelectedRow();
            String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_OBS).toString());
            ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum58_02.this), true, strObs);
            zafMae07_01.show();
            if (zafMae07_01.getAceptar()) {
                tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_OBS);
            }
        }
    };
  
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    return blnres;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(java.sql.Connection conCab)
    {
        boolean blnRes=true;
        java.util.Date datFecDoc;
        java.sql.ResultSet rstLoc;
        Vector vecDataCon = null;
        java.sql.Statement stm;
        int intPosRel;
        String strSql="";
        try
        {
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                vecDataCon = new Vector();
                stm=conCab.createStatement();
                strSql="";
//                if(objParSis.getCodigoUsuario()==1){
                    strSql+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom FROM tbm_reenovrechum AS a";
                    strSql+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
                    strSql+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
                    strSql+=" where a.co_nov = " + strCodNov;
                    strSql+=" order by co_reg";
//                }if(objParSis.getCodigoUsuario()==166){
//                    strSql+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom FROM tbm_reenovrechum AS a";
//                    strSql+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
//                    strSql+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
//                    strSql+=" where a.co_nov = " + strCodNov;
//                    strSql+=" order by co_reg";
//                }
//                else{
//                    strSql+="select a.*, b.tx_usr as tx_usrrem, b.tx_nom as tx_usrremnom, c.tx_usr as tx_usrdes,  c.tx_nom as tx_usrdesnom FROM tbm_reenovrechum AS a";
//                    strSql+=" inner join tbm_usr b on (b.co_usr=a.co_usrrem)";
//                    strSql+=" inner join tbm_usr c on (c.co_usr=a.co_usrdes)";
//                    strSql+=" where a.co_nov = " + strCodNov;
//                    strSql+=" and a.co_usrdes = " + objParSis.getCodigoUsuario();
//                    strSql+=" order by co_reg";
//                }
                
                System.out.println("strConTbm_NovRecHum: " + strSql);
                rstLoc=stm.executeQuery(strSql);
                while (rstLoc.next())
                {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LIN,"");
                    vecReg.add(INT_TBL_FECHA,rstLoc.getString("co_nov"));
                    vecReg.add(INT_TBL_FECHA,rstLoc.getString("fe_ing").substring(0,10));
                    vecReg.add(INT_TBL_REM,rstLoc.getString("tx_usrrem"));
                    vecReg.add(INT_TBL_DES,rstLoc.getString("tx_usrdes"));
                    vecReg.add(INT_TBL_OBS,rstLoc.getString("tx_obs1"));
                    vecReg.add(INT_TBL_BUT_OBS,"...");
                    vecDataCon.add(vecReg);
                }
                objTblMod.setData(vecDataCon);
                tblDat .setModel(objTblMod);   
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
//    /**
//     * Esta función permite limpiar el formulario.
//     * @return true: Si se pudo limpiar la ventana sin ningún problema.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean limpiarFrm()
//    {
//        boolean blnRes=true;
//        try
//        {
//            objTblMod.removeAllRows();
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//        }
//        return blnRes;
//    }
  
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
                case INT_TBL_FECHA:
                    strMsg="Fecha de reenvio";
                    break;
                case INT_TBL_REM:
                    strMsg="Remitente";
                    break;
                case INT_TBL_DES:
                    strMsg="Destinatario";
                    break;
                case INT_TBL_OBS:
                    strMsg="";
                    break;
                case INT_TBL_BUT_OBS:
                    strMsg="Observación";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
}