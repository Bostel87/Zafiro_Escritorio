/*
 *
 * v0.2 
 */


package Compras.ZafCom37;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author  Javier Ayapata
 */
public class ZafCom37_01 extends javax.swing.JDialog 
{    
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_EMP=1;
    private static final int INT_TBL_DAT_COD_LOC=2;
    private static final int INT_TBL_DAT_COD_TIP_DOC=3;
    private static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    private static final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    private static final int INT_TBL_DAT_NUM_DOC=6;
    private static final int INT_TBL_DAT_FEC_DOC=7;
    private static final int INT_TBL_DAT_COD_CLI=8;
    private static final int INT_TBL_DAT_NOM_CLI=9;
    private static final int INT_TBL_DAT_CAN_SIN_CNF=10;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblBus objTblBus;                                   //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                   //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                   //JTable de totales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private Vector vecReg, vecDat, vecCab;
    private String strSQL;
    private String strTipCnf;
    private int intCodReg;
    
    public ZafCom37_01(java.awt.Frame parent, boolean modal, ZafParSis obj, String tipoConfirmacion, int codigoRegistro)
    {
        super(parent, modal);
        initComponents();
        //Inicializar objetos.
        objParSis = obj;
        strTipCnf = tipoConfirmacion;
        intCodReg = codigoRegistro;
        
        if(strTipCnf.equals("IB"))
            lblTit.setText("Documentos pendientes de confirmar ingreso (Bodega) a la fecha de corte");
        else if(strTipCnf.equals("ID"))
            lblTit.setText("Documentos pendientes de confirmar ingreso (Despacho) a la fecha de corte");
        else if(strTipCnf.equals("EB") )
            lblTit.setText("Documentos pendientes de confirmar egreso (Bodega) a la fecha de corte");
        else if(strTipCnf.equals("ED"))
            lblTit.setText("Documentos pendientes de confirmar egreso (Despacho) a la fecha de corte");

        if(configurarFrm())
            cargarDocumentosPendientesConfirmar();
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 12)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Documentos pendientes de confirmar a la fecha de corte");
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

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panGenDet.add(spnTot, java.awt.BorderLayout.SOUTH);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        PanFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.EAST);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-630)/2, (screenSize.height-350)/2, 630, 350);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
            exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }//GEN-LAST:event_exitForm
      
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butCer;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables
       
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(11);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,         "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,         "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,     "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_NUM_DOC,         "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,         "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,         "Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,         "Cliente");
            vecCab.add(INT_TBL_DAT_CAN_SIN_CNF,     "Can.Sin.Cnf.");

            objUti=new ZafUtil();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(120);

            tcmAux.getColumn(INT_TBL_DAT_CAN_SIN_CNF).setPreferredWidth(76);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SIN_CNF).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CAN_SIN_CNF};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {    blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    private boolean cargarDocumentosPendientesConfirmar()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(strTipCnf.equals("IB"))
                {
                    strSQL="";
                    strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a4.tx_desCor, a4.tx_desLar, ";
                    strSQL+="        a3.co_cli, a3.tx_nomCli, a3.ne_numDoc, a3.fe_doc, a2.nd_canPenConBod as nd_canPenCon";
                    strSQL+=" FROM tbm_conInv AS a1 ";
                    strSQL+=" INNER JOIN tbm_conInvDocIngBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_reg=a2.co_con";
                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_reg=" + intCodReg + "";
                    strSQL+=" AND a2.nd_canPenConBod > 0 ";
                }
                else if(strTipCnf.equals("ID"))
                {
                    strSQL="";
                    strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a4.tx_desCor, a4.tx_desLar, ";
                    strSQL+="        a3.co_cli, a3.tx_nomCli, a3.ne_numDoc, a3.fe_doc, a2.nd_canPenConDes as nd_canPenCon";
                    strSQL+=" FROM tbm_conInv AS a1 ";
                    strSQL+=" INNER JOIN tbm_conInvDocIngBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_reg=a2.co_con";
                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_reg=" + intCodReg + "";
                    strSQL+=" AND a2.nd_canPenConDes > 0 ";
                }
                else  if(strTipCnf.equals("EB"))
                {
                    strSQL="";
                    strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc";
                    strSQL+="       , CASE WHEN a3.ne_numOrdDes IS NULL THEN a4.tx_desCor ELSE CAST('ORDDES' AS CHARACTER VARYING) END AS tx_desCor";
                    strSQL+="       , CASE WHEN a3.ne_numOrdDes IS NULL THEN a4.tx_desLar ELSE CAST('Orden de Despacho' AS CHARACTER VARYING) END AS tx_desLar";
                    strSQL+="       , a3.co_clides AS co_cli, a3.tx_nomclides AS tx_nomCli";
                    strSQL+="       , CASE WHEN a3.ne_numOrdDes IS NULL THEN a3.ne_numDoc ELSE a3.ne_numOrdDes END AS ne_numDoc";
                    strSQL+="       , a3.fe_doc, a2.nd_canPenConBod as nd_canPenCon";
                    strSQL+=" FROM tbm_conInv AS a1 ";
                    strSQL+=" INNER JOIN tbm_conInvDocEgrBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_reg=a2.co_con";
                    strSQL+=" INNER JOIN tbm_cabGuiRem AS a3 ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_reg=" + intCodReg + "";
                    strSQL+=" AND a2.nd_canPenConBod > 0 ";
                }
                else  if(strTipCnf.equals("ED"))
                {
                    strSQL="";
                    strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc";
                    strSQL+="       , CASE WHEN a3.ne_numOrdDes IS NULL THEN a4.tx_desCor ELSE CAST('ORDDES' AS CHARACTER VARYING) END AS tx_desCor";
                    strSQL+="       , CASE WHEN a3.ne_numOrdDes IS NULL THEN a4.tx_desLar ELSE CAST('Orden de Despacho' AS CHARACTER VARYING) END AS tx_desLar";
                    strSQL+="       , a3.co_clides AS co_cli, a3.tx_nomclides AS tx_nomCli";
                    strSQL+="       , CASE WHEN a3.ne_numOrdDes IS NULL THEN a3.ne_numDoc ELSE a3.ne_numOrdDes END AS ne_numDoc";
                    strSQL+="       , a3.fe_doc, a2.nd_canPenConDes as nd_canPenCon";
                    strSQL+=" FROM tbm_conInv AS a1 ";
                    strSQL+=" INNER JOIN tbm_conInvDocEgrBod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_reg=a2.co_con";
                    strSQL+=" INNER JOIN tbm_cabGuiRem AS a3 ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_reg=" + intCodReg + "";
                    strSQL+=" AND a2.nd_canPenConDes > 0 ";
                }
                
                System.out.println("SQL cargarDocumentosPendientesConfirmar: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_COD_CLI,         "" + rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    vecReg.add(INT_TBL_DAT_NOM_CLI,         "" + rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                    vecReg.add(INT_TBL_DAT_CAN_SIN_CNF,     "" + rst.getString("nd_canPenCon"));
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
                objTblTot.calcularTotales();
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)   {   blnRes=false;   objUti.mostrarMsgErr_F1(this, e);  }
        catch (Exception e)   {    blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
   
}
