/*
 * ZafImp30_01.java
 *
 * Created on 28 de Diciembre de 2016, 16h43
 *
 * v0.1
 */
package Importaciones.ZafImp30;

import static Importaciones.ZafImp30.ZafImp30.INT_ARL_PED_EMB_VAL_DXP_TOT;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Rosa Zambrano
 * "Muestra Ventana con información de solicitudes de transferencias asociadas"
 */
public class ZafImp30_01 extends javax.swing.JDialog 
{
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_COD_EMP = 1;
    private static final int INT_TBL_DAT_COD_LOC = 2;
    private static final int INT_TBL_DAT_COD_TIP_DOC = 3;
    private static final int INT_TBL_DAT_COD_DOC = 4;
    private static final int INT_TBL_DAT_DES_COR = 5;
    private static final int INT_TBL_DAT_DES_LAR = 6;
    private static final int INT_TBL_DAT_NUM_PED = 7;
    private static final int INT_TBL_DAT_COD_CTA_PRO = 8;
    private static final int INT_TBL_DAT_VAL_DXP_TOT = 9;    //Obtiene el Valor Total de DxP enlazados a la provisión del pedido embarcado, incluye el valor del DxP Actual.
    private static final int INT_TBL_DAT_VAL_PRO_TOT = 10;   //Obtiene Valor de Provisión del Asiento de Diario del Pedido Embarcado.
    private static final int INT_TBL_DAT_CHK_ASI_PRO = 11;   
    private static final int INT_TBL_DAT_CHK_CIE = 12;
    
    //Constantes: Asiento de Diario Ajuste de Provisión.
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    
    //Variables.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafAsiDia objAsiDiaAjuProImp;
    private ZafImp objImp;
    private ZafImp30 objImp30;
    private ZafSegMovInv objSegMovInv;
    
    //Variables de la clase.
    private Vector vecReg, vecDat, vecCab;
    private boolean blnIsAce;  
    private Object objCodSegPedEmb;
    private ArrayList arlDatPedEmb;
    private int intCodTipDocDxP, intCodDocDxP;
    private String strNumDocAjuPro;
    private String strCodDiaAjuPro;
    private String strDesCorTipDocAjuPro;
    private String strCodCtaIng;
    private String strNumCtaIng;
    private String strNomCtaIng;  
    private String strCodCtaPro;
    private String strNumCtaPro;
    private String strNomCtaPro;
    private String strFecAux;
    private String strSQL, strAux;
    
    /*Indica si se va procesar automáticamente el cierre de pagos de los pedidos.
    True: Proceso automático, es decir, Cuando sea un solo pedido se cerrará automáticamente sin mostrar ventana.
    False: Muestra ventana, para cerrar pedidos el usuario debe seleccionar pedidos a cerrar.*/
    boolean blnProAutCiePag; 

    /**
     * Constructor
     */
    public ZafImp30_01(java.awt.Frame parent, boolean modal, ZafParSis obj, Connection conexion, ArrayList DatPedEmb, boolean procesoAutomatico, int CodTipDocDxP, int CodDocDxP)
    {
        super(parent, modal);
        initComponents();
        
        con = conexion;
        arlDatPedEmb = DatPedEmb;
        blnProAutCiePag=procesoAutomatico;
        intCodTipDocDxP=CodTipDocDxP;
        intCodDocDxP=CodDocDxP;
        
        //Inicializar objetos.
        objParSis = obj;
        objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));    
        objImp30 = new ZafImp30(objParSis);
        objAsiDiaAjuProImp=new ZafAsiDia(objParSis);
        objSegMovInv=new ZafSegMovInv(objParSis, this);
        vecDatDia=new Vector();
        
        if (configurarTblDat()) {
            if(cargarDetReg())
            {
                if(blnProAutCiePag){
                    //System.out.println("Procesar automáticamente....");
                    aceptar();
                }
            }
        }
    }
    
    /**
     * Configuración de la tabla.
     * @return 
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_NUM_PED, "Núm.Ped.");
            vecCab.add(INT_TBL_DAT_COD_CTA_PRO, "Cta.Pro.");
            vecCab.add(INT_TBL_DAT_VAL_DXP_TOT, "Val.DxP.");
            vecCab.add(INT_TBL_DAT_VAL_PRO_TOT, "Val.Pro.");
            vecCab.add(INT_TBL_DAT_CHK_ASI_PRO, "Chk.Asi.Pro.");
            vecCab.add(INT_TBL_DAT_CHK_CIE, "..");
            
            objUti = new ZafUtil();

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA_PRO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DXP_TOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PRO_TOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ASI_PRO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setPreferredWidth(30);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA_PRO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_DXP_TOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_PRO_TOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_ASI_PRO, tblDat);            
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_CIE, tblDat);

            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
                      
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(objTblMod.isChecked(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_CHK_ASI_PRO)){
                        objTblCelRenLbl.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
       
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_CIE);
            objTblMod.addColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_ASI_PRO).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCre=new java.awt.Color(255,221,187);
                    if(objTblMod.isChecked(objTblCelRenChk.getRowRender(), INT_TBL_DAT_CHK_ASI_PRO)){
                        objTblCelRenChk.setBackground(colFonColCre);
                    }
                    else {
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setCellEditor(objTblCelEdiChk);  
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                        if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN).equals("C")) {      
                            objTblCelEdiChk.setCancelarEdicion(true);
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_CIE);
                        }
                        else  {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_CIE)){                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_CIE);
                        }
                        else {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_CIE);
                        }
                    }
                }
            });   
            
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_LIN:
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #FFDDBB'>&nbsp;</td><td>&nbsp;</td><td>Indica que son pedidos embarcados \"CON ASIENTO DE DIARIO DE PROVISIÓN\".</td></tr>"; //Color Crema
                    strMsg+="</table><br></html>";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del tipo de documento";
                    break;                    
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;   
                case INT_TBL_DAT_DES_COR:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR:
                    strMsg="Descripción larga del tipo de documento";
                    break;                    
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_CTA_PRO:
                    strMsg="Código de cuenta de provisión";
                    break;    
                case INT_TBL_DAT_VAL_DXP_TOT:
                    strMsg="Valor de Documento por Pagar Total";
                    break;                           
                case INT_TBL_DAT_VAL_PRO_TOT:
                    strMsg="Valor de provisión";
                    break;   
                case INT_TBL_DAT_CHK_ASI_PRO:
                    strMsg="Indica si tiene o no asiento de provisión";
                    break;                    
                case INT_TBL_DAT_CHK_CIE:
                    strMsg="Indica si pedido ha sido cerrado";
                    break;                      
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listado de Items según movimiento de importaciones...");
        setMinimumSize(new java.awt.Dimension(161, 157));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de pedidos a cerrar pagos locales");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

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

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Si presiona cancelar se borrará lo que ha ingresado");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.LINE_END);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(416, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        if (objTblMod.isDataModelChanged()) {
            aceptar();
        } 
        else {
            mostrarMsgInf("<HTML>Seleccione un registro y de click en el botón Aceptar.<BR>Si no desea seleccionar registro alguno de click en Cancelar.</HTML>");
        }    
    }//GEN-LAST:event_butAceActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        blnIsAce=false;
        dispose();
    }//GEN-LAST:event_butCanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   
    
    /**
     * Cargar pedidos embarcados a cerrar.
     * @return 
     */
    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        try 
        {
            vecDat= new Vector();
            //Obtener los registros.
            for(int i=0; i<arlDatPedEmb.size();i++)
            {
                vecReg = new Vector();
                vecReg.add(INT_TBL_DAT_LIN, "");
                vecReg.add(INT_TBL_DAT_COD_EMP, objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_COD_EMP));
                vecReg.add(INT_TBL_DAT_COD_LOC, objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_COD_LOC));
                vecReg.add(INT_TBL_DAT_COD_TIP_DOC, objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_COD_TIPDOC));
                vecReg.add(INT_TBL_DAT_COD_DOC, objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_COD_DOC));
                vecReg.add(INT_TBL_DAT_DES_COR, "PEDEMB");
                vecReg.add(INT_TBL_DAT_DES_LAR, "Pedido Embarcado");
                vecReg.add(INT_TBL_DAT_NUM_PED, objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_NUM_PED));
                vecReg.add(INT_TBL_DAT_COD_CTA_PRO, objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_COD_CTA_PRO));
                vecReg.add(INT_TBL_DAT_VAL_DXP_TOT, objUti.getDoubleValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_VAL_DXP_TOT));
                vecReg.add(INT_TBL_DAT_VAL_PRO_TOT, objUti.getDoubleValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_VAL_PRO_TOT));
                vecReg.add(INT_TBL_DAT_CHK_ASI_PRO, null);
                vecReg.add(INT_TBL_DAT_CHK_CIE, null);

                //Verifica si tiene o no provisión         
                strAux=(objUti.getStringValueAt(arlDatPedEmb, i, objImp30.INT_ARL_PED_EMB_CHK_ASI_PRO).toString());
                if(strAux.equals("S")){
                    vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_ASI_PRO);
                }
                
                //Si el proceso de cierre de pagos es automático.
                if(blnProAutCiePag){
                    vecReg.setElementAt("M", INT_TBL_DAT_LIN);
                    vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_CIE);
                }
                
                vecDat.add(vecReg);
            }
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void aceptar()
    {
        if(isCamVal()){
            //Genera Cierre de Pagos Locales
            if (generarCiePagLoc()) {
                blnIsAce=true;
                dispose();
            } 
            else {
                mostrarMsgInf("<HTML>Verifique los datos y vuelva a intentarlo.</HTML>");
            }
        } 
    }

    /**
    * Función que permite conocer si el modelo ha cambiado
    * @return true Si el modelo ha cambiado y se da click en Autorizar
    * <BR> false Si el modelo no ha cambiado, o si el modelo ha cambiado pero se da click en Cerrar
    */
    public boolean isCiePagLocPro() {
        return blnIsAce;
    }
    
    /**
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean isCamVal(){
        //Valida que exista algún pedido seleccionado para cerrar pagos.
        if(!objTblMod.isCheckedAnyRow(INT_TBL_DAT_CHK_CIE)){
            mostrarMsgInf("<HTML>Debe seleccionar los pedidos que serán cerrados de pagos.<BR>Si no desea cerrar ningún pedido de click en Cancelar.</HTML>");
            return false;
        }
        if(!validaEstCiePagLoc()){
            mostrarMsgInf("<HTML>Existen pedidos que ya han sido cerrados.</HTML>");
            return false;
        }
        return true;
    } 
    
    /**
     * Esta función valida que el pedido no haya sido cerrado para realizar pagos.
     * @return true: Si el pedido esta abierto, se puede continuar con el proceso de cerrar pedido.
     * <BR>false: En el caso contrario.
     */
    private boolean validaEstCiePagLoc()
    {
        boolean blnRes=true;
        String strLin="";
        try
        {
            if (con!=null)
            {
                for (int i=0; i<objTblMod.getRowCountTrue();i++)
                {
                    strLin = objTblMod.getValueAt(i, INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    
                    if (strLin.equals("M")) 
                    {
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_CIE))
                        {
                            stm=con.createStatement();

                            //Armar la sentencia SQL.
                            strSQL ="";
                            strSQL+=" SELECT * FROM tbm_cabPedEmbImp as a1";
                            strSQL+=" WHERE a1.st_ciePagLoc IN ('N')";
                            strSQL+=" AND a1.co_Emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP);        //co_emp
                            strSQL+=" AND a1.co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC);        //co_loc  
                            strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC); //co_tipDoc  
                            strSQL+=" AND a1.co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC);        //co_doc  
                            //System.out.println("validaEstCiePagLoc: "+strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(!rst.next())
                            {
                                blnRes=false;
                            }

                            rst.close();
                            rst=null;
                            stm.close();
                            stm=null;                            
                        }
                    }
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
     * Esta función genera el cierre de pagos locales de pedidos embarcados seleccionados.
     * @return true: Si se pudo generar cierre de pagos locales.
     * <BR>false: En el caso contrario.
     */
    private boolean generarCiePagLoc()
    {
        boolean blnRes = true;
        String strLin="";
        try 
        {
            if (con != null) 
            {
                for (int i=0; i<objTblMod.getRowCountTrue();i++)
                {
                    strLin = objTblMod.getValueAt(i, INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    strFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
                    
                    if (strLin.equals("M")) 
                    {
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_CIE))
                        {
                            if (procesarAjuProImp(i)) {
                                if (actualizarCiePagLoc(i)) {
                                    //System.out.println("generarCiePagLoc");
                                }
                                else 
                                    blnRes = false;
                            }
                            else 
                                blnRes = false;
                        }
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Función que procesa ajuste de provisión de importación
     * @param i
     * @return 
     */
    private boolean procesarAjuProImp(int i)
    {
        boolean blnRes=false;
        try
        {
            //Validar si tiene Asiento de provision. S=Tiene Provision, N=Tiene Provision.
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_ASI_PRO)){
                if(cargarCtaCon(i)){
                    if(generarAsiDiaAju(i)){
                        blnRes=true;
                    }
                    else{
                        System.out.println("Error al generar asiento de diario de ajuste.");
                    }
                }
                else{
                    System.out.println("Existen cuentas que no están configuradas para generar asiento de ajuste.");
                }
            }
            else{
                //No genera asiento de ajuste de provisión.
                System.out.println("Pedido no genera asiento de ajuste de provisión");
                blnRes=true;
            }
        }    
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return blnRes;    
    }

    /*
     * Función que carga las cuentas contables a utilizarse 
     * en el asiento de diario de ajuste de provisión.
     * @return 
     */
    private boolean cargarCtaCon(int filSel){
        boolean blnRes=false;
        boolean blnCtaCfg=true;
        strCodCtaPro="";
        strNumCtaPro="";
        strNomCtaPro="";
        strCodCtaIng="";
        strNumCtaIng="";
        strNomCtaIng="";  
        strNumDocAjuPro="";
        strCodDiaAjuPro="";
        strDesCorTipDocAjuPro="";
        try{
            if(con!=null){
                stm=con.createStatement();   
                //Asiento de Ajuste de provisión: Obtiene Cuenta de Provisión.
                strSQL ="";
                strSQL+=" SELECT a1.co_Cta, a1.tx_codCta, a1.tx_DesLar ";
                strSQL+=" FROM tbm_cabPedEmbImp AS a ";
                strSQL+=" INNER JOIN tbm_plaCta AS a1 ON a.co_imp=a1.co_emp AND a.co_CtaPro=a1.co_Cta ";
                strSQL+=" WHERE a1.st_reg IN ('A') AND a.st_Reg IN ('A')";
                strSQL+=" AND a.co_emp=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_EMP) + "";
                strSQL+=" AND a.co_loc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_LOC) + "";
                strSQL+=" AND a.co_TipDoc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_TIP_DOC)  + "";
                strSQL+=" AND a.co_doc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_DOC) + "";
                //System.out.println("cargarCtaCon.Cuenta Provisiones: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    strCodCtaPro=rst.getString("co_cta");
                    strNumCtaPro=rst.getString("tx_codCta");
                    strNomCtaPro=rst.getString("tx_desLar");    
                }
                else{
                    blnCtaCfg=false;
                }

                //Asiento de Ajuste de provisión: Obtiene Cuenta de Ingresos.
                //Obtiene datos del tipo de documento de ajuste de provisión [AJPRIM].
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_ctaHab as co_cta, a1.co_tipDoc, a2.tx_codCta, a2.tx_desLar";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";  
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + objImp.INT_COD_TIP_DOC_AJU_PRO + "";

                //<editor-fold defaultstate="collapsed" desc="/* comentado */">
                /* //Obtiene datos del tipo de documento del OPIMPO (DxP).
                strSQL ="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa() +"";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() +  "";
                strSQL+=" AND a2.co_tipDoc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_TIP_DOC)  + "";
                strSQL+=" AND a1.tx_niv1='7' "; */
                //</editor-fold>
                //System.out.println("cargarCtaCon.Cuenta Ingresos: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strCodCtaIng=rst.getString("co_cta");
                    strNumCtaIng=rst.getString("tx_codCta");
                    strNomCtaIng=rst.getString("tx_desLar");
                }
                else{
                    blnCtaCfg=false;
                }

                //Ultimo número de documento del ajuste de provisión (Por empresa).
                strSQL ="";
                strSQL+=" SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_numDoc, tx_desCor \n";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc="+ objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc="+ objImp.INT_COD_TIP_DOC_AJU_PRO;
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    strNumDocAjuPro = rst.getObject("ne_numDoc") == null ? "-1" : rst.getString("ne_numDoc");
                    strDesCorTipDocAjuPro= rst.getString("tx_desCor");
                }
                
                //Código de documento del ajuste de provisión (Por empresa).
                strSQL ="";
                strSQL+=" SELECT CASE WHEN (a1.co_dia) IS NULL THEN 1 ELSE (a1.co_dia+1) END AS co_dia\n";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp="+ objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc="+ objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc="+ objImp.INT_COD_TIP_DOC_AJU_PRO;
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    strCodDiaAjuPro = rst.getObject("co_dia") == null ? "-1" : rst.getString("co_dia");
                }                
                
                if(blnCtaCfg){
                    blnRes=true;
                }
                //System.out.println("strCodDiaAjuPro: "+strCodDiaAjuPro);
                rst.close();
                rst = null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    } 
    
    /**
     * Función que carga valores que tendrá el asiento de diario de ajuste de provisión
     * @return 
     */
    private boolean generarAsiDiaAju(int filSel)
    {
        boolean blnRes=false;
        BigDecimal bgdDxPTot=new BigDecimal("0");  //DxP.
        BigDecimal bgdProTot=new BigDecimal("0");  //Provisión.
        BigDecimal bgdAux=new BigDecimal("0"); 
        try
        {
            if(con!=null){
                //Obtener valores
                bgdDxPTot=new BigDecimal(objTblMod.getValueAt(filSel, INT_TBL_DAT_VAL_DXP_TOT)==null?"0":(objTblMod.getValueAt(filSel, INT_TBL_DAT_VAL_DXP_TOT).toString().equals("")?"0":objTblMod.getValueAt(filSel, INT_TBL_DAT_VAL_DXP_TOT).toString()));  
                bgdProTot=new BigDecimal(objTblMod.getValueAt(filSel, INT_TBL_DAT_VAL_PRO_TOT)==null?"0":(objTblMod.getValueAt(filSel, INT_TBL_DAT_VAL_PRO_TOT).toString().equals("")?"0":objTblMod.getValueAt(filSel, INT_TBL_DAT_VAL_PRO_TOT).toString()));  
                //System.out.println("bgdDxPTot: "+bgdDxPTot+"  <> bgdProTot: "+bgdProTot);
                bgdAux=bgdProTot.subtract(bgdDxPTot);
                //System.out.println("Valor asiento de ajuste de provisión: "+bgdAux);
                
                if(bgdAux.compareTo(new BigDecimal("0"))>0)
                {
                    //Se inicializa el asiento de diario
                    objAsiDiaAjuProImp.inicializar();
                    vecDatDia.clear();
                    //Provisión
                    vecRegDia=new Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPro);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPro);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA,"" + strNomCtaPro);
                    vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdAux);
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);

                    //Otros ingresos de importación
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaIng);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaIng);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA,"" + strNomCtaIng);
                    vecRegDia.add(INT_VEC_DIA_DEB, "0");
                    vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdAux);
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                
                    //Si existen valores pendientes de ajustar provisión, se genera el asiento de diario de ajuste.
                    if(!vecDatDia.isEmpty()){
                        objAsiDiaAjuProImp.setDetalleDiario(vecDatDia);
                        objAsiDiaAjuProImp.setGeneracionDiario((byte)1);
                        actualizarGlo(filSel);   
                        if (objAsiDiaAjuProImp.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objImp.INT_COD_TIP_DOC_AJU_PRO, strNumDocAjuPro, objUti.parseDate(strFecAux, objParSis.getFormatoFechaBaseDatos())))  {
                            if(insertarRelDxP_AsiDiaAjuProImp(filSel)){
                                blnRes=true;
                            }
                        }
                    }
                }
                else{
                    //No existen valores pendientes de provisión. Por tal motivo no es necesario generar asiento de ajuste de provisión.
                    System.out.println("Pedido no genera asiento de ajuste de provisión. No existen valores pendientes de provisión");
                    blnRes=true;
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
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo(int filSel)
    {
        //Glosa de asiento de ajuste de provisión.
        strAux="";
        strAux+="Ajuste Provisión: "+strDesCorTipDocAjuPro+" # "+strNumDocAjuPro+"; Pedido: "+ objTblMod.getValueAt(filSel, INT_TBL_DAT_NUM_PED) ;
        objAsiDiaAjuProImp.setGlosa(strAux);    
    } 
    
    /**
     * Función que permite relación entre Documento por Pagar y Asiento Diario de Ajuste Provisión de Importación.
     * En caso de existir un asiento de ajuste de provisión enlazarlo con el DxP actual.
     * @return 
     */
    private boolean insertarRelDxP_AsiDiaAjuProImp(int i){
        boolean blnRes=false;
        try{
            if(con!=null){
                stm=con.createStatement();
                objCodSegPedEmb=null;
                //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                strSQL="";
                strSQL+=" SELECT *FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+="         ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                strSQL+=" 	WHERE a1.co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                strSQL+=" 	AND a1.co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                strSQL+=" 	AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)+ "";
                strSQL+=" 	AND a1.co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                strSQL+=" ) AS b1";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegPedEmb=rst.getObject("co_segPedEmb");  //Ya existe seguimiento del pedido embarcado.
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;

                //Insertar en Seguimiento - Relación: Documento por Pagar vs Asiento Diario de Ajuste de Provisión de Importación.
                if(objSegMovInv.setSegMovInvCompra(con, objCodSegPedEmb
                                                   , 5, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodTipDocDxP, intCodDocDxP, "Null"
                                                   , 8, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objImp.INT_COD_TIP_DOC_AJU_PRO, strCodDiaAjuPro, "Null"
                   ))
                {
                    blnRes=true;
                }
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función realiza el cierre de los pagos locales de los pedidos embarcados.
     * @return true: Si se pudo actualizar
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCiePagLoc(int filSel)
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) 
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabPedEmbImp";
                strSQL+=" SET ";
                strSQL+=" st_ciePagLoc='S'";
                strSQL+=" , fe_ultMod=" + objParSis.getFuncionFechaHoraBaseDatos() + "";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_EMP) + "";
                strSQL+=" AND co_loc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_LOC) + "";
                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_TIP_DOC)  + "";
                strSQL+=" AND co_doc=" + objTblMod.getValueAt(filSel, INT_TBL_DAT_COD_DOC) + "; \n";
                System.out.println("actualizarCiePagLoc: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;    
            }                        
        }
        catch (java.sql.SQLException e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } 
        catch (Exception e) 
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    

  
    
    
}


