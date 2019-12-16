/*
 * CxC.ZafCxC15 
 *
 * Created on 06 de Abril de 2011, 21:10 PM
 */    
package CxC.ZafCxC15;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author  javier Ayapata     
 */
public class ZafCxC15 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                        //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                    //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                    //Código del local.
    static final int INT_TBL_DAT_NOM_LOC=3;                    //Nombre del local.
    static final int INT_TBL_DAT_COD_CLI=4;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=5;                    //Nombre del cliente.
    static final int INT_TBL_DAT_COD_GRP_CLI=6;                //Código del grupo del cliente.
    static final int INT_TBL_DAT_NOM_GRP_CLI=7;                //Nombre del grupo del cliente.    
    static final int INT_TBL_DAT_COD_TIP_DOC=8;                //Código del Tipo de Documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=9;                //Descripción corta del Tipo de Documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=10;               //Descripción larga del Tipo de Documento.
    static final int INT_TBL_DAT_COD_DOC=11;                   //Código del documento.
    static final int INT_TBL_DAT_COD_REG=12;                   //Código del registro.
    static final int INT_TBL_DAT_NUM_DOC=13;                   //Número de documento.
    static final int INT_TBL_DAT_FEC_DOC=14;                   //Fecha del documento.
    static final int INT_TBL_DAT_DIA_CRE=15;                   //Días de crédito.
    static final int INT_TBL_DAT_FEC_VEN=16;                   //Fecha de vencimiento.
    static final int INT_TBL_DAT_POR_RET=17;                   //Porcentaje de retención.
    static final int INT_TBL_DAT_VAL_DOC=18;                   //Valor del documento.
    static final int INT_TBL_DAT_VAL_ABO=19;                   //Valor del Abono.
    static final int INT_TBL_DAT_VAL_PEN=20;                   //Valor Pendiente.
    static final int INT_TBL_DAT_VAL_VEN=21;                   //Valor por vencer.
    static final int INT_TBL_DAT_VAL_30D=22;                   //Valor vencido (0-30 días).
    static final int INT_TBL_DAT_VAL_60D=23;                   //Valor vencido (31-60 días).
    static final int INT_TBL_DAT_VAL_90D=24;                   //Valor vencido (61-90 días).
    static final int INT_TBL_DAT_VAL_MAS=25;                   //Valor vencido (Más de 90 días).
    static final int INT_TBL_DAT_COD_BAN=26;                   //Código del Banco.
    static final int INT_TBL_DAT_NOM_BAN=27;                   //Nombre del Banco.
    static final int INT_TBL_DAT_NUM_CTA=28;                   //Número de cuenta.
    static final int INT_TBL_DAT_NUM_CHQ=29;                   //Número de cheque.
    static final int INT_TBL_DAT_FEC_REC_CHQ=30;               //Fecha de recepción del cheque.
    static final int INT_TBL_DAT_FEC_VEN_CHQ=31;               //Fecha de vencimiento del cheque.
    static final int INT_TBL_DAT_VAL_CHQ=32;                   //Valor del cheque.
    
    //JTable: TblLoc
    private final int INT_TBL_LOC_LIN=0;
    private final int INT_TBL_LOC_CHKSEL=1;
    private final int INT_TBL_LOC_CODEMP=2;
    private final int INT_TBL_LOC_NOMEMP=3;
    private final int INT_TBL_LOC_CODLOC=4;
    private final int INT_TBL_LOC_NOMLOC=5;
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModLoc;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                   //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                         //ToolTipText en TableHeader.
    private ZafTblTot objTblTot;                               //JTable de totales.
    private ZafDatePicker dtpFecCor;
    private ZafPerUsr objPerUsr;
    private ZafTblFilCab objTblFilCab; 
    private ZafTblPopMnu objTblPopMnu; 
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafVenCon vcoCli;                                  //Ventana de consulta "Cliente".
    private ZafVenCon vcoTipDoc;                               //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoLoc;                                  //Ventana de consulta "Local".
    private ZafVenCon vcoEmp;                                  //Ventana de consulta "Empresa.
    private ZafVenCon vcoVen;
    private Vector vecDat, vecCab, vecReg;
    
    private boolean blnMarTodChkTblLoc=true;                   //Marcar todas las casillas de verificación del JTable de Locales.
    
    private String strSQL, strAux;
    private String strCodEmp, strDesLarEmp, strDesLarTipDoc;   //Contenido del campo al obtener el foco.
    private String strTipDoc;                                  //Contenido del campo al obtener el foco.
    private String strCodCli, strIdeCli, strDesLarCli;
    private String strCodVen="", strDesVen="";    
    private String strVer=" v0.16 ";

    JLabel lblFecCor = new JLabel();
   
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC15(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();

            lblEmp.setVisible(false);
            txtCodEmp.setVisible(false);
            txtNomEmp.setVisible(false);
            butEmp.setVisible(false);

            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtNomEmp.setVisible(true);
                butEmp.setVisible(true);

                java.awt.Color color = txtCodCli.getBackground();
                txtCodCli.setEditable(false);
                txtCodCli.setBackground(color);

                lblVen.setVisible(false);
                txtCodVen.setVisible(false);
                txtNomVen.setVisible(false);
                butVen.setVisible(false);                
            }
            
            if (objParSis.getCodigoMenu()!=320)
            {
                optTod.setText("Todos los proveedores");
                optFil.setText("Sólo los proveedores que cumplan el criterio seleccionado");
                lblCli.setText("Proveedor:");
                panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de proveedor"));
                
                lblVen.setVisible(false);
                txtCodVen.setVisible(false);
                txtNomVen.setVisible(false);
                butVen.setVisible(false);                
            }
            lblFecCor.setFont(new java.awt.Font("Tahoma", 0, 11));
            lblFecCor.setText("Fecha de Corte: ");

            dtpFecCor = new ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            dtpFecCor.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecCor.setText("");
            panFil.add(dtpFecCor);
            panFil.add(lblFecCor);
            lblFecCor.setBounds(420, 10, 92, 20);// Coordenadas para la fecha de corte
            dtpFecCor.setBounds(520, 10, 92, 20);

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer );
            lblTit.setText(strAux);
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            objPerUsr=new ZafPerUsr(objParSis); 
            switch (objParSis.getCodigoMenu())
            {
                case 320: //Listado de ventas por item...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!objPerUsr.isOpcionEnabled(670))
                    {
                        butCon.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(671))
                    {
                        butCer.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3945))
                    {
                        chkVenCli.setSelected(false);
                        chkVenCli.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3946))
                    {
                        chkVenRel.setSelected(false);
                        chkVenRel.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3947))
                    {
                        chkPre.setSelected(false);
                        chkPre.setEnabled(false);
                    }
                    break;
            }

            configurarTblDat();
            configurarTblLoc();
            cargarTblLoc();    
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
     *
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
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            
            if (objParSis.getCodigoMenu()==320){
                vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
                vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");                
            } else{
                vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Prv.");
                vecCab.add(INT_TBL_DAT_NOM_CLI,"Proveedor");                                
            }
            
            vecCab.add(INT_TBL_DAT_COD_GRP_CLI,"Cód.Grp.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_GRP_CLI,"Grupo Cliente");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Día.Cre.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_POR_RET,"Por.Ret.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");            
            vecCab.add(INT_TBL_DAT_VAL_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_VAL_VEN,"Val.Por.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_30D,"0-30");
            vecCab.add(INT_TBL_DAT_VAL_60D,"31-60");
            vecCab.add(INT_TBL_DAT_VAL_90D,"61-90");
            vecCab.add(INT_TBL_DAT_VAL_MAS,"+90");
            vecCab.add(INT_TBL_DAT_COD_BAN,"Cód.Ban.");
            vecCab.add(INT_TBL_DAT_NOM_BAN,"Banco");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_DAT_VAL_CHQ,"Val.Chq.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            new ZafTblOrd(tblDat);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(140);//antes estaba 70
            tcmAux.getColumn(INT_TBL_DAT_COD_GRP_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_GRP_CLI).setPreferredWidth(100);            
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);//antes 80            
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(1);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS).setPreferredWidth(95);//antes estaba 60
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BAN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(95);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setPreferredWidth(95);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GRP_CLI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_ABO, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_DOC, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_30D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_60D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_90D).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_MAS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLis());
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC, INT_TBL_DAT_VAL_ABO, INT_TBL_DAT_VAL_PEN, INT_TBL_DAT_VAL_VEN, INT_TBL_DAT_VAL_30D, INT_TBL_DAT_VAL_60D, INT_TBL_DAT_VAL_90D, INT_TBL_DAT_VAL_MAS, INT_TBL_DAT_VAL_CHQ};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            //Libero los objetos auxiliares.
            tcmAux=null;
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_NOM_LOC:
                    strMsg="Nombre del local";
                    break;                    
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_COD_GRP_CLI:
                    strMsg="Código del grupo del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_GRP_CLI:
                    strMsg="Nombre del grupo del cliente/proveedor";
                    break;      
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_VAL_ABO:
                    strMsg="Abono realizado";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor Pendiente";
                    break;                    
                case INT_TBL_DAT_VAL_VEN:
                    strMsg="Valor por vencer";
                    break;
                case INT_TBL_DAT_VAL_30D:
                    strMsg="Valor vencido (0-30 días)";
                    break;
                case INT_TBL_DAT_VAL_60D:
                    strMsg="Valor vencido (31-60 días)";
                    break;
                case INT_TBL_DAT_VAL_90D:
                    strMsg="Valor vencido (61-90 días)";
                    break;
                case INT_TBL_DAT_VAL_MAS:
                    strMsg="Valor vencido (Más de 90 días)";
                    break;
                case INT_TBL_DAT_COD_BAN:
                    strMsg="Código del Banco";
                    break;
                case INT_TBL_DAT_NOM_BAN:
                    strMsg="Nombre del Banco";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_DAT_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_DAT_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_DAT_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     /**
     * Esta función configura el JTable "tblDatLoc".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN, "");
            vecCab.add(INT_TBL_LOC_CHKSEL, " ");
            vecCab.add(INT_TBL_LOC_CODEMP, "Cod.Emp");
            vecCab.add(INT_TBL_LOC_NOMEMP, "Empresa");
            vecCab.add(INT_TBL_LOC_CODLOC, "Cod.Loc");
            vecCab.add(INT_TBL_LOC_NOMLOC, "Local");
            
            objTblModLoc = new ZafTblMod(); 
            objTblModLoc.setHeader(vecCab);
            tblDatLoc.setModel(objTblModLoc);

            //Configurar JTable: Establecer tipo de selección.
            tblDatLoc.setRowSelectionAllowed(true);
            tblDatLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_LOC_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOMEMP).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_LOC_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOMLOC).setPreferredWidth(220);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setResizable(false);

            //new Librerias.ZafColNumerada.ZafColNumerada(tblDatLoc, INT_TBL_LOC_LIN);
            
            //Configurar JTable: Establecer el menú de contexto.
            new ZafTblPopMnu(tblDatLoc);
            //Configurar JTable: Editor de búsqueda.
            new ZafTblBus(tblDatLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            new ZafTblOrd(tblDatLoc);
            
            tblDatLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatLocMouseClicked(evt); 
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_LOC_CHKSEL);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellRenderer(objTblFilCab);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_LOC_CHKSEL).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk = null;
            
            objTblModLoc.setModoOperacion(objTblModLoc.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }    
    
    private void cargarTblLoc() 
    {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try 
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc=conLoc.createStatement();
                      
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.tx_nom AS tx_nomEmp, a2.co_loc, a2.tx_nom AS tx_nomLoc";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" WHERE a2.st_reg NOT IN ('E')";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())  {
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    }
                    else{
                        if(txtCodEmp.getText().length()>0)
                            strSQL+=" AND a1.co_emp=" + txtCodEmp.getText();
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                }
                else
                {
                    //Otros Usuarios
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.tx_nom  AS tx_nomEmp, a2.co_loc, a2.tx_nom AS tx_nomLoc";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    strSQL+=" WHERE a2.st_reg NOT IN ('E')";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())   {
                        strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                    }
                    else{
                        if(txtCodEmp.getText().length()>0) /* Si esta seleccionada una empresa, debe actualizarse la tabla de locales. */
                            strSQL+=" AND a1.co_emp=" + txtCodEmp.getText();
                    }                    
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                }             
                rstLoc=stmLoc.executeQuery(strSQL);
                vecDat.clear();
                while(rstLoc.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHKSEL, true);
                    vecReg.add(INT_TBL_LOC_CODEMP, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_LOC_NOMEMP, rstLoc.getString("tx_nomEmp") );
                    vecReg.add(INT_TBL_LOC_CODLOC, rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_LOC_NOMLOC, rstLoc.getString("tx_nomLoc") );
                    vecDat.add(vecReg);
                }
                objTblModLoc.setData(vecDat);
                tblDatLoc.setModel(objTblModLoc);
                 
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
            }
        } 
        catch (SQLException Evt) {  objUti.mostrarMsgErr_F1(this, Evt);  } 
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);   } 
    }    

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDatLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHKSEL)
            {
                if (blnMarTodChkTblLoc)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHKSEL);
                    }
                    blnMarTodChkTblLoc=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHKSEL);
                    }
                    blnMarTodChkTblLoc=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empresas".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("492");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1  where a1.co_emp != "+objParSis.getCodigoEmpresaGrupo()+" ";
            strSQL+=" ORDER BY a1.co_emp ";
            
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /*
     * MODIFICADO EFLORESA 2012-04-04
     * SE HACE LA CONSULTA SOLO LOS CLIENTES DEL LOCAL SELECCIONADO O EMPRESA/GRUPO
     * 
    */
    private boolean configurarVenConCli() {
        boolean blnRes=true;
        try {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            /*if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT ' ' AS co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir";
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide";
                strSQL+=" FROM (";
                strSQL+=" SELECT MIN(co_emp) AS co_emp, tx_ide";
                strSQL+=" FROM tbm_cli";
                strSQL+=" GROUP BY tx_ide";
                strSQL+=" ) AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)";
                strSQL+=" GROUP BY a2.co_emp, a2.tx_ide";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)";
                strSQL+=" AND b2.st_cli='S' AND b2.st_reg='A'";
                strSQL+=" ORDER BY b2.tx_nom";
            }
            else {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }*/
            
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))            {
                //Armar la sentencia SQL.
                strSQL=" SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir " + 
                       " FROM tbm_cli AS a1 " +
                       " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                       " AND a1.st_reg='A' " +
//                       " and a1.st_cli='S' " +
                       ((objParSis.getCodigoMenu()==320) ? " and a1.st_cli='S' " : " and a1.st_prv='S' ") +
                       " ORDER BY a1.tx_nom";
            } else {
                //Armar la sentencia SQL.
                strSQL=" select a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir " +
                       " from tbm_cli a, tbr_cliloc b " +
                       " where a.co_emp = b.co_emp " +
                       " and a.co_cli = b.co_cli " +
                       " and a.co_emp=" + objParSis.getCodigoEmpresa() + " " +
                       " and b.co_loc=" +objParSis.getCodigoLocal() + " " + 
                       " and a.st_reg='A' " +
//                       " and a.st_cli='S' " + 
                       ((objParSis.getCodigoMenu()==320) ? " and a.st_cli='S' " : " and a.st_prv='S' ") +
                       " order by a.tx_nom ";
            }
            
            vcoCli=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, JLabel.RIGHT);
        }catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     * 
     */
    private boolean configurarVenConTipDoc()   {
        boolean blnRes=true;
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
            arlAli.add("Código");
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
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
            strSQL+=" FROM tbm_cabTipDoc AS a1 ";
            strSQL+=" WHERE ";
            strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                
            if (objParSis.getCodigoMenu()==320){
                strSQL+=" AND a1.ne_mod IN (1,3)" ;
            } else{
                strSQL+=" AND a1.ne_mod IN (2,4)" ;                
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, JLabel.RIGHT);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConVen() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_usr");
            arlCam.add("a.tx_nom");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String  strSQL="";
            strSQL="select a.co_usr, a.tx_nom  from tbr_usremp as b" +
            " inner join tbm_usr as a on (a.co_usr=b.co_usr) " +
            " where b.co_emp="+objParSis.getCodigoEmpresa()+" and b.st_ven='S' and a.st_reg not in ('I')  order by a.tx_nom";

            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
    }

    private void buscarVendedor(String campo,String strBusqueda,int tipo){
       vcoVen.setTitle("Listado de Vendedores");
       if(vcoVen.buscar(campo, strBusqueda )) {
           txtCodVen.setText(vcoVen.getValueAt(1));
           txtNomVen.setText(vcoVen.getValueAt(2));
           optFil.setSelected(true);
       }
       else{
           vcoVen.setCampoBusqueda(tipo);
           vcoVen.cargarDatos();
           vcoVen.show();
           if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE) {
               txtCodVen.setText(vcoVen.getValueAt(1));
               txtNomVen.setText(vcoVen.getValueAt(2));
               optFil.setSelected(true);
           }
           else{
               txtCodVen.setText(strCodVen);
               txtNomVen.setText(strDesVen);
           }
       }
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
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(1);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        cargarTblLoc();
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        cargarTblLoc();
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoLoc.getValueAt(1));
                            txtNomEmp.setText(vcoLoc.getValueAt(2));
                            cargarTblLoc();
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        cargarTblLoc();
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            cargarTblLoc();
                        }
                        else
                        {
                            txtNomEmp.setText(strDesLarEmp);
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        String strLisLoc="";
        try
        {
            //Armar la sentencia SQL.
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))  
            {   //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                strSQL =" "; 
                strSQL+=" SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir ";
                strSQL+=" FROM tbm_cli AS a ";
                strSQL+=" WHERE a.st_reg='A' ";
                strSQL+=" "+((objParSis.getCodigoMenu()==320) ? " AND a.st_cli='S' " : " AND a.st_prv='S' ");
                strSQL+=" AND a.co_emp=" + objParSis.getCodigoEmpresa() ;
                strSQL+=" GROUP BY a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir ";
                strSQL+=" ORDER BY a.tx_nom";
            }
            else {
                strSQL ="";
                strSQL+=" SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir ";
                strSQL+=" FROM tbm_cli a, tbr_cliloc a1 ";
                strSQL+=" WHERE a.co_emp = a1.co_emp ";
                strSQL+=" AND a.co_cli = a1.co_cli ";
                strSQL+=" "+((objParSis.getCodigoMenu()==320) ? " AND a.st_cli='S' " : " AND a.st_prv='S' ");
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" AND a.co_emp=" + objParSis.getCodigoEmpresa() ;
                else{
                    if(txtCodEmp.getText().length()>0){
                        //Filtro: Locales
                        int i=0;
                        for (int j=0; j<objTblModLoc.getRowCountTrue(); j++)
                        {
                            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHKSEL))
                            {
                                if (i==0)
                                    strLisLoc+=" (a.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODEMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODLOC) + ")";
                                else
                                    strLisLoc+=" OR (a.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODEMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODLOC) + ")";
                                i++;
                            }
                        }
                        if (!strLisLoc.equals("")) {
                            strSQL+=" AND (" + strLisLoc + ")";
                        }            
                    }
                }
                strSQL+=" GROUP BY a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir ";
                strSQL+=" ORDER BY a.tx_nom";
            }
            vcoCli.setSentenciaSQL(strSQL);
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));///txtCodTipDoc   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);

                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_descor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                       // intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                           // intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_deslar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                           // intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                default:
                    txtDesCorTipDoc.requestFocus();
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
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        chkVenCli = new javax.swing.JCheckBox();
        chkVenRel = new javax.swing.JCheckBox();
        chkPre = new javax.swing.JCheckBox();
        chkMosCre = new javax.swing.JCheckBox();
        chkMosDeb = new javax.swing.JCheckBox();
        chkMosDocVen = new javax.swing.JCheckBox();
        chkMosRet = new javax.swing.JCheckBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtDesCorTipDoc = new javax.swing.JTextField();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblDatLoc = new javax.swing.JTable();
        chkMosLocIna = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana"); // NOI18N
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana"); // NOI18N
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setPreferredSize(new java.awt.Dimension(0, 700));

        panFil.setPreferredSize(new java.awt.Dimension(10, 410));
        panFil.setLayout(null);

        lblEmp.setText("Empresa:"); // NOI18N
        lblEmp.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblEmp);
        lblEmp.setBounds(10, 10, 80, 20);

        txtCodEmp.setVerifyInputWhenFocusTarget(false);
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(84, 10, 56, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(140, 10, 250, 20);

        butEmp.setText("..."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(390, 10, 20, 20);

        chkVenCli.setText("Ventas a clientes");
        chkVenCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVenCliActionPerformed(evt);
            }
        });
        panFil.add(chkVenCli);
        chkVenCli.setBounds(10, 40, 170, 23);

        chkVenRel.setText("Ventas a empresas relacionadas");
        chkVenRel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVenRelActionPerformed(evt);
            }
        });
        panFil.add(chkVenRel);
        chkVenRel.setBounds(10, 60, 230, 20);

        chkPre.setText("Préstamos");
        chkPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPreActionPerformed(evt);
            }
        });
        panFil.add(chkPre);
        chkPre.setBounds(10, 80, 230, 20);

        chkMosCre.setSelected(true);
        chkMosCre.setText("Mostrar los Créditos"); // NOI18N
        chkMosCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCreActionPerformed(evt);
            }
        });
        panFil.add(chkMosCre);
        chkMosCre.setBounds(240, 60, 150, 23);

        chkMosDeb.setSelected(true);
        chkMosDeb.setText("Mostrar los Débitos"); // NOI18N
        chkMosDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDebActionPerformed(evt);
            }
        });
        panFil.add(chkMosDeb);
        chkMosDeb.setBounds(240, 80, 150, 23);

        chkMosDocVen.setText("Mostrar sólo los documentos vencidos"); // NOI18N
        panFil.add(chkMosDocVen);
        chkMosDocVen.setBounds(400, 60, 250, 23);

        chkMosRet.setSelected(true);
        chkMosRet.setText("Mostrar las Retenciones"); // NOI18N
        panFil.add(chkMosRet);
        chkMosRet.setBounds(400, 80, 240, 23);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes"); // NOI18N
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 110, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 130, 400, 20);

        lblTipDoc.setText("Tipo de Documento:"); // NOI18N
        lblTipDoc.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(30, 155, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(122, 140, 0, 0);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(216, 155, 420, 20);

        butTipDoc.setText("..."); // NOI18N
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(636, 155, 20, 20);

        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblCli);
        lblCli.setBounds(30, 175, 120, 20);

        txtCodCli.setToolTipText("Código del cliente/proveedor");
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(160, 175, 56, 20);

        txtIdeCli.setToolTipText("Identificación del cliente/proveedor");
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        panFil.add(txtIdeCli);
        txtIdeCli.setBounds(216, 175, 100, 20);

        txtNomCli.setToolTipText("Nombre del cliente/proveedor");
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        panFil.add(txtNomCli);
        txtNomCli.setBounds(316, 175, 320, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(636, 175, 20, 20);

        lblVen.setText("Vendedor :");
        panFil.add(lblVen);
        lblVen.setBounds(30, 195, 120, 20);

        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(160, 195, 56, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(216, 195, 420, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(636, 195, 20, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(160, 155, 56, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cliente"));
        panNomCli.setLayout(null);

        lblNomCliDes.setText("Desde:"); // NOI18N
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 15, 60, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(70, 15, 200, 20);

        lblNomCliHas.setText("Hasta:"); // NOI18N
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(300, 15, 50, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(350, 15, 200, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(25, 215, 625, 42);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setPreferredSize(new java.awt.Dimension(464, 439));
        panLoc.setLayout(new java.awt.BorderLayout());

        tblDatLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblDatLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        panFil.add(panLoc);
        panLoc.setBounds(25, 255, 625, 122);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("General", spnFil);

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar"); // NOI18N
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar"); // NOI18N
        butCer.setToolTipText("Cierra la ventana."); // NOI18N
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo"); // NOI18N
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if(optTod.isSelected())
        {
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText(""); 
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void chkMosCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCreActionPerformed
        if (!chkMosDeb.isSelected())
        {
            chkMosCre.setSelected(true);
        }
    }//GEN-LAST:event_chkMosCreActionPerformed

    private void chkMosDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDebActionPerformed
        if (!chkMosCre.isSelected())
        {
            chkMosDeb.setSelected(true);
        }
    }//GEN-LAST:event_chkMosDebActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))  {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else {
                mostrarVenConTipDoc(1);
            }
        }
        else   {
            txtDesCorTipDoc.setText(strTipDoc);
        }
        
        //Seleccionar el JRadioButton de Filtro si es necesario.
        if (txtDesCorTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }

        
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();        
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        if(txtDesCorTipDoc.getText().length()>0)
        optFil.setSelected(true);//bostel
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
         //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))      {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");//bostel
                //txtDesLarTipDoc.setText("");
            }
            else  {
                mostrarVenConTipDoc(2);
            }
        }
        else   {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
        
        //Seleccionar el JRadioButton de Filtro si es necesario.
        if (txtDesLarTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }

    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged

    }//GEN-LAST:event_optFilItemStateChanged

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomEmp.getText().equalsIgnoreCase(strDesLarEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
        {
            txtNomEmp.setText(strDesLarEmp);
            
        }        
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strDesLarEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(1);
            }
        }
        else
        {
            txtCodEmp.setText(strCodEmp);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
       if (isCamVal()) // Para que aparezca la ventana cuando los Filtros por ventas a clienes, relacionada y prestamos no estan activas
          {
            if (butCon.getText().equals("Consultar"))
             {                
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
             }
          }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        //JOptionPane oppMsg=new JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
}//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
}//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if (txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            } else {
                mostrarVenConCli(1);
            }
        } else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de Filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodCliFocusLost

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
}//GEN-LAST:event_txtIdeCliActionPerformed

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli=txtIdeCli.getText();
        txtIdeCli.selectAll();
}//GEN-LAST:event_txtIdeCliFocusGained

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli)) {
            if (txtIdeCli.getText().equals("")) {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            } else {
                mostrarVenConCli(2);
            }
        } else
            txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de Filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtIdeCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
}//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
}//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            } else {
                mostrarVenConCli(3);
            }
        } else
            txtNomCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de Filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de Filtro si es necesario.
        if (txtCodCli.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butCliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        configurarVenConCli();
        configurarVenConTipDoc();
        configurarVenConEmp();
        configurarVenConVen();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
            buscarVendedor("a.co_usr",txtCodVen.getText(),0);
        }else
        txtCodVen.setText(strCodVen);
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
            buscarVendedor("a.tx_nom",txtNomVen.getText(),1);
        }else
        txtNomVen.setText(strDesVen);
    }//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        vcoVen.setTitle("Listado de Clientes");
        vcoVen.setCampoBusqueda(1);
        vcoVen.show();
        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE) {
            txtCodVen.setText(vcoVen.getValueAt(1));
            txtNomVen.setText(vcoVen.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilActionPerformed

    private void chkPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPreActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_chkPreActionPerformed

    private void chkVenCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVenCliActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_chkVenCliActionPerformed

    private void chkVenRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVenRelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkVenRelActionPerformed

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarTblLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed
    
      
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkMosCre;
    private javax.swing.JCheckBox chkMosDeb;
    private javax.swing.JCheckBox chkMosDocVen;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JCheckBox chkPre;
    private javax.swing.JCheckBox chkVenCli;
    private javax.swing.JCheckBox chkVenRel;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatLoc;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables
   
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this,strMsg,strTit,JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función permite verificar si un anio es bisiesto o no
     *@param intAni recibe 1 entero que representan el anio que queremos verificar 
     *@return true: Booleano que indica si es bisiesto
     * <Br> false: Si no es bisiesto
     */
    private boolean blnSiBis(int intAni)
    {
        int intMod1, intMod2, intMod3;
        intMod1 = intAni % 4;
        intMod2 = intAni % 100;
        intMod3 = intAni % 400;
        if (intMod1 == 0) 
        {
            if (intMod2 == 0) 
            {
                if (intMod3 == 0) 
                {
                    return true;
                } 
                else 
                    return false;
            }
            else 
                return true;
        }
        else 
            return false;
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
        public void run() 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (!cargarDetReg(sqlFiltro()))
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            pgrSis.setIndeterminate(false);

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */    
    private boolean isCamVal()
    {
        int intNumFilTblLoc, i, j;
        
        //Validar que esté seleccionada al menos un local.
        intNumFilTblLoc=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblLoc; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHKSEL)) 
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un local.<BR>Seleccione un local y vuelva a intentarlo.</HTML>");
            tblDatLoc.requestFocus();
            return false;
        }        
        
        
        if ( (!chkVenCli.isSelected()) && (!chkVenRel.isSelected()) && (!chkPre.isSelected()) )
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El siguiente Filtro es obligatorio:<BR><UL><LI>Ventas a clientes<LI>Ventas a empresas relacionadas<LI>Préstamos</UL>Seleccione al menos una de las opciones y vuelva a intentarlo.</HTML>");
            chkVenCli.requestFocus();
            return false;
        }
       
        return true;
    }

    private String sqlFiltro() 
    {
        String strFilSQL = "";
        String strChkFil = "";
        String strLocFil = "";
        String strCreDebFil = "";
        try 
        {
            //Filtro: Ventas por clientes, empresas relacionada y prestamos.
            if (chkVenCli.isSelected())
            {
                strChkFil+="cli.co_empGrp IS NULL";
            }
            if (chkVenRel.isSelected())
            {
                strChkFil+=(strChkFil.equals("")?"": " OR ") + "a2.co_cat IN (3, 4) AND cli.co_empGrp IS NOT NULL";
            }
            if (chkPre.isSelected())
            {
                strChkFil+=(strChkFil.equals("")?"": " OR ") + "a2.co_cat IN (23)";
            }
            strFilSQL+=" AND (" + strChkFil + ")";    
                
            
            //Filtro: Locales
            int i=0;
            for (int j=0; j<objTblModLoc.getRowCountTrue(); j++)
            {
                if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHKSEL))
                {
                    if (i==0)
                        strLocFil+=" (a.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODEMP) + " AND a.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODLOC) + ")";
                    else
                        strLocFil+=" OR (a.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODEMP) + " AND a.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_CODLOC) + ")";
                    i++;
                }
            }
            if (!strLocFil.equals("")) {
                strFilSQL+=" AND (" + strLocFil + ")";
            }            
            
            //Filtro: Movimiento de credito y débito.
            if (objParSis.getCodigoMenu()==320)
            {
                if (chkMosCre.isSelected()){
                    strCreDebFil+="  a1.mo_pag > 0  ";
                }
                if (chkMosDeb.isSelected())
                {
                    if( !strCreDebFil.equals("") )  
                       strCreDebFil+=" or a1.mo_pag < 0  ";
                    else 
                       strCreDebFil+=" a1.mo_pag < 0  ";
                }                 
            }
            else
            {
                if (chkMosCre.isSelected()) {
                    strCreDebFil+="  a1.mo_pag < 0  ";
                }
                
                if (chkMosDeb.isSelected())  {
                    if( !strCreDebFil.equals("") )  
                       strCreDebFil+=" or a1.mo_pag > 0  ";
                    else
                       strCreDebFil+=" a1.mo_pag > 0  ";
                }   
            }
            if (!strCreDebFil.equals("") )
                strFilSQL+=" AND ( "+strCreDebFil+" ) ";            
            if (!chkMosRet.isSelected()) 
                strFilSQL+=" AND a1.nd_porret = 0 ";       
            
            //Filtro: Fecha Corte
            String fechaCorte = objUti.formatearFecha(dtpFecCor.getText(),"dd/MM/yyyy","yyyy-MM-dd");
            if (chkMosDocVen.isSelected()) if(fechaCorte.equals("[ERROR]")) strFilSQL+=" and  a1.fe_ven <= current_date"; else strFilSQL+=" and  a1.fe_ven <= '"+fechaCorte+"'";

            //Filtro: Tipo Documento
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                if(!txtCodEmp.getText().equals(""))  strFilSQL+=" and a1.co_emp ="+txtCodEmp.getText()+" ";

                if(optFil.isSelected())
                {
                    if(!txtCodTipDoc.getText().equals(""))
                        strFilSQL+=" and a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    else
                        strFilSQL+=""+((objParSis.getCodigoMenu()==320)? " AND a2.ne_mod IN (1,3) ": " AND a2.ne_mod IN (2,4) ");

                    if(!txtIdeCli.getText().equals("")) 
                        strFilSQL+=" and cli.tx_ide = '"+txtIdeCli.getText()+"'   ";

                    if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0){
                        strFilSQL+="  AND (  ((LOWER(cli.tx_nom) BETWEEN LOWER('"+txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "') AND LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') ) OR LOWER(cli.tx_nom) LIKE LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%') ) ) ";
                    }
                }
                else{
                    strFilSQL+=""+((objParSis.getCodigoMenu()==320)? " AND a2.ne_mod IN (1,3) ": " AND a2.ne_mod IN (2,4) ");
                }
            }
            else{
                strFilSQL+="   AND a.co_emp = "+objParSis.getCodigoEmpresa()+" ";

                if (optFil.isSelected())
                {
                    if (!txtCodTipDoc.getText().equals(""))
                        strFilSQL+=" AND a.co_tipdoc="+txtCodTipDoc.getText()+" ";
                    else{
                        strFilSQL+=""+((objParSis.getCodigoMenu()==320)? " AND a2.ne_mod IN (1,3) ": " AND a2.ne_mod IN (2,4) ");
                    }   

                    if (!txtCodCli.getText().equals("")) strFilSQL+=" AND a.co_cli="+txtCodCli.getText()+" ";

                    if (!txtCodVen.getText().equals("")) strFilSQL+=" AND a.co_com="+txtCodVen.getText()+" ";

                    if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0){
                        strFilSQL+="  AND (  ((LOWER(cli.tx_nom) BETWEEN LOWER('"+txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "') AND LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') ) OR LOWER(cli.tx_nom) LIKE LOWER('" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%') ) ) ";
                    }
                } 
                else{
                    strFilSQL+=""+((objParSis.getCodigoMenu()==320)? " AND a2.ne_mod IN (1,3) ": " AND a2.ne_mod IN (2,4) ");
                }
            }
        }
        catch (Exception e){    strFilSQL="";       objUti.mostrarMsgErr_F1(this, e);    }          
        return strFilSQL;
    }

    private boolean cargarDetReg(String strAux){
        boolean blnRes=true;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql = "";
        try
        {
            Connection conLoc=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement();

                String fechaCorte =  objUti.formatearFecha(dtpFecCor.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                
                //Armar sentencia SQL
                strSql  = "";
                if (fechaCorte.equals("[ERROR]")){ /* Consulta: Normal (Sin Fecha Corte) */
                    strSql  = " SELECT case when dias < 0 then valpen else 0 end as valporven   "
                            + "      , case when dias between 0 and 30 then valpen else 0 end as val_1_30 "
                            + "      , case when dias between 31 and 60 then valpen else 0 end as val_31_60 "
                            + "      , case when dias between 61 and 90 then valpen else 0 end as val_61_90 "
                            + "      , case when dias  > 90 then valpen else 0 end as val_mas_90 "
                            + "      ,* "
                            + " FROM ( "
                            + "    select a1.co_banchq, a3.tx_deslar as nomban, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq"
                            + "         , a.co_emp, a.co_loc, loc.tx_nom as tx_nomLoc, a.co_cli, a.tx_nomcli, a4.co_grp as co_grpCli,a4.tx_nom as tx_nomGrpCli,a.co_tipdoc, a2.tx_descor, a2.tx_deslar,  a.co_doc, a1.co_reg, a.ne_numdoc "
                            + "         , a.fe_doc, a1.ne_diacre, a1.fe_ven, a1.nd_porret "
                            + "         , a1.mo_pag,  (a1.mo_pag+a1.nd_abo) as valpen "
                            + "         , (current_date - a1.fe_ven )  as dias  "
                            + "    from tbm_cabmovinv as a  "
                            + "    inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                            + "    inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                            + "    inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli ) "
                            + "    inner join tbm_loc as loc on (loc.co_emp=a.co_emp and loc.co_loc=a.co_loc ) "
                            + "    left join tbm_var as a3 on (a3.co_reg=a1.co_banchq ) "
                            + "    left outer join tbm_grpCli as a4 on (cli.co_emp=a4.co_emp and cli.co_grp=a4.co_grp)"/*Solicitado por Werner Campoverde: Mostrar Grupo de Clientes. Bostel*/
                            + "    where  a.st_reg not in ('E','I') and a1.st_reg in ('A','C') "
                            + "    "+strAux+"  "
                            + "    and (a1.mo_pag+a1.nd_abo) != 0  "
                            + "    order by  a.tx_nomcli, a1.fe_ven "
                            + " ) AS x   ";
                }
                else{ /* Consulta: Por Fecha Corte */
                    strSql  = " SELECT case when dias < 0 then valpen else 0 end as valporven "
                            + "      , case when dias between 0 and 30 then valpen else 0 end as val_1_30 "
                            + "      , case when dias between 31 and 60 then valpen else 0 end as val_31_60 "
                            + "      , case when dias between 61 and 90 then valpen else 0 end as val_61_90 "
                            + "      , case when dias  > 90 then valpen else 0 end as val_mas_90"
                            + "      , * "
                            + " FROM ( "
                            + "   select * ,( mo_pag+ sumabodet) as valpen    " 
                            + "   from ( select x.* ,  case when x1.sumabodet is null then 0 else x1.sumabodet end as sumabodet   "
                            + "          from ( select a1.co_banchq, a3.tx_deslar as nomban, a1.tx_numctachq, a1.tx_numchq, a1.fe_recchq"
                            + "                      , a1.fe_venchq, a1.nd_monchq, a.co_emp, a.co_loc, loc.tx_nom as tx_nomLoc, a.co_cli, a.tx_nomcli"
                            + "                      , a4.co_grp as co_grpCli, a4.tx_nom AS tx_nomGrpCli, a.co_tipdoc, a2.tx_descor"
                            + "                      , a2.tx_deslar,  a.co_doc, a1.co_reg, a.ne_numdoc"
                            + "                      , a.fe_doc, a1.ne_diacre, a1.fe_ven, a1.nd_porret, a1.mo_pag"
                            + "                      , ('"+fechaCorte+"' - a1.fe_ven )  as dias  "
                            + "                 from tbm_cabmovinv as a  "
                            + "                 inner join tbm_pagmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
                            + "                 inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc ) "
                            + "                 inner join tbm_cli as cli on (cli.co_emp=a.co_emp and cli.co_cli=a.co_cli ) "
                            + "                 inner join tbm_loc as loc on (loc.co_emp=a.co_emp and loc.co_loc=a.co_loc ) "
                            + "                 left join tbm_var as a3 on (a3.co_reg=a1.co_banchq ) "
                            + "                 left outer join tbm_grpCli as a4 on (cli.co_emp=a4.co_emp and cli.co_grp=a4.co_grp)"/*Solicitado por Werner Campoverde: Mostrar Grupo de Clientes. Bostel*/
                            + "                 where a.st_reg not in ('E','I') and a1.st_reg in ('A','C') and a.fe_doc <='"+fechaCorte+"' "
                            + "                 "+strAux+"  "
                            + "          ) as x   "
                            + "          left join ( " 
                            + "                 select x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag, sum(x1.nd_abo) as sumabodet " 
                            + "                 from tbm_detpag as x1  " 
                            + "                 inner join tbm_cabpag as x2 ON (x1.co_emp=x2.co_emp and x1.co_loc=x2.co_loc and x1.co_tipdoc=x2.co_tipdoc and x1.co_doc=x2.co_doc) " 
                            + "                 where x2.st_reg NOT IN ('E','I')   and  x1.st_reg NOT IN ('E','I')  " 
                            + "                 and x2.fe_doc <= '"+fechaCorte+"' " 
                            + "                 group by x1.co_emp, x1.co_locpag, x1.co_tipdocpag, x1.co_docpag, x1.co_regpag  " 
                            + "          ) as x1 on (x.co_emp=x1.co_emp and x.co_loc=x1.co_locpag and x.co_tipdoc=x1.co_tipdocpag and x.co_doc=x1.co_docpag and x.co_reg=x1.co_regpag )  " 
                            + "   ) as x   where   ( mo_pag + sumabodet ) != 0      "
                            + "   order by  tx_nomcli,  fe_ven " 
                            + " ) as x  ";                
                }
                System.out.println("ZafCxC15.cargarDetReg: "+ strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP, rstLoc.getString("co_emp") );
                    vecReg.add(INT_TBL_DAT_COD_LOC, rstLoc.getString("co_loc") );
                    vecReg.add(INT_TBL_DAT_NOM_LOC, rstLoc.getString("tx_nomLoc") );
                    vecReg.add(INT_TBL_DAT_COD_CLI, rstLoc.getString("co_cli") );
                    vecReg.add(INT_TBL_DAT_NOM_CLI, rstLoc.getString("tx_nomcli") );
                    vecReg.add(INT_TBL_DAT_COD_GRP_CLI,  rstLoc.getString("co_grpCli"));
                    vecReg.add(INT_TBL_DAT_NOM_GRP_CLI,  rstLoc.getString("tx_nomGrpCli"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,  rstLoc.getString("co_tipdoc") );
                    vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,  rstLoc.getString("tx_descor") );
                    vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,  rstLoc.getString("tx_deslar") );
                    vecReg.add(INT_TBL_DAT_COD_DOC, rstLoc.getString("co_doc") );
                    vecReg.add(INT_TBL_DAT_COD_REG, rstLoc.getString("co_reg") );
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rstLoc.getString("ne_numdoc") );
                    vecReg.add(INT_TBL_DAT_FEC_DOC, rstLoc.getString("fe_doc") );
                    vecReg.add(INT_TBL_DAT_DIA_CRE, rstLoc.getString("ne_diacre") );
                    vecReg.add(INT_TBL_DAT_FEC_VEN, rstLoc.getString("fe_ven") );
                    vecReg.add(INT_TBL_DAT_POR_RET, rstLoc.getString("nd_porret") );
                    vecReg.add(INT_TBL_DAT_VAL_DOC, rstLoc.getString("mo_pag") );
                    vecReg.add(INT_TBL_DAT_VAL_ABO, "" );
                    vecReg.add(INT_TBL_DAT_VAL_PEN, rstLoc.getString("valpen") );
                    vecReg.add(INT_TBL_DAT_VAL_VEN, rstLoc.getString("valporven") );
                    vecReg.add(INT_TBL_DAT_VAL_30D, rstLoc.getString("val_1_30") );
                    vecReg.add(INT_TBL_DAT_VAL_60D, rstLoc.getString("val_31_60") );
                    vecReg.add(INT_TBL_DAT_VAL_90D, rstLoc.getString("val_61_90") );
                    vecReg.add(INT_TBL_DAT_VAL_MAS, rstLoc.getString("val_mas_90") );
                    vecReg.add(INT_TBL_DAT_COD_BAN, rstLoc.getString("co_banchq") );
                    vecReg.add(INT_TBL_DAT_NOM_BAN, rstLoc.getString("nomban") );
                    vecReg.add(INT_TBL_DAT_NUM_CTA, rstLoc.getString("tx_numctachq") );
                    vecReg.add(INT_TBL_DAT_NUM_CHQ, rstLoc.getString("tx_numchq") );
                    vecReg.add(INT_TBL_DAT_FEC_REC_CHQ, rstLoc.getString("fe_recchq") );
                    vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ, rstLoc.getString("fe_venchq") );
                    vecReg.add(INT_TBL_DAT_VAL_CHQ, rstLoc.getString("nd_monchq") );
                    vecDat.add(vecReg);
                }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                objTblTot.calcularTotales();

                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;

                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e){  blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
        return blnRes;
    }

    
    
    
}