/*
 * ZafMae31.java  
 *
 * Created on 7 de diciembre de 2007, 05:05 PM  tblDatCli 
 */

package Maestros.ZafMae48;  
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;

/**
 *   
 * @author  jayapata 
 */  
public class ZafMae48 extends javax.swing.JInternalFrame {
    
     ZafVenCon objVenConCli; //*****************
     ZafVenCon objVenConVen;
     ZafUtil objUti;
     Librerias.ZafParSis.ZafParSis objZafParSis;
     javax.swing.JInternalFrame jfrThis;
     private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
     private ZafMouMotAda objMouMotAda;
     private ZafThreadGUI objThrGUI;
     private String strCodPrv, strDesLarPrv;
     
     final int INT_TBL_LINEA=0;
     final int INT_TBL_COCLI=1;
     final int INT_TBL_IDCLI=2;
     final int INT_TBL_NOCLI=3;
     final int INT_TBL_STCLI=4;
     final int INT_TBL_PRCLI=5;
     
      final String VERSION = " ver 0.4 ";
       
      private boolean blnCon;   
      private Vector vecDat, vecCab, vecReg;
      String strTip="";
      String strConAuxCon="";
      int intCodMnu=0; 
      int intCodMod=0; 
    /** Creates new form ZafMae31 */
    public ZafMae48(ZafParSis obj) {
       try{
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jfrThis = this;
           
	   objUti = new ZafUtil();
          initComponents();
          this.setTitle(" "+objZafParSis.getNombreMenu()+" "+VERSION);
          
	  
	intCodMnu = objZafParSis.getCodigoMenu(); 
        switch (intCodMnu) {
            case 1781: intCodMod=3; chkmos.setText("Mostrar sólo los clientes.");  break;  // MODULO CXC
            case 1785: intCodMod=4; chkmos.setText("Mostrar sólo los proveedores.");  break;  // MODULO CXP
            default: intCodMod=6;  chkmos.setVisible(false);  break;  // MODULO MAESTRO
        }

            
	
	  
	  lblTit.setText( objZafParSis.getNombreMenu());
        
        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
      
    
      
    
    
  
    
   public void Configura_ventana_consulta(){
       
        configurarVenConClientes();  
        ConfiguararTablaCli();
    }
    
      
        
       
 
    
    
    
    
     private boolean configurarVenConClientes() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_cli");
            arlCam.add("a.tx_nom");
            arlCam.add("a.tx_dir");
            arlCam.add("a.tx_tel");
            arlCam.add("a.tx_ide");
            arlCam.add("a.tx_tipper");
            arlCam.add("a.nd_maxdes");
            arlCam.add("a.nd_maruti");
            arlCam.add("a.tx_desLar");
            arlCam.add("a.co_ven");
            arlCam.add("a.vendedor");
            arlCam.add("a.co_tipper");
            arlCam.add("a.ne_tipforpag");
            arlCam.add("a.nd_pes");
            arlCam.add("a.ne_diagra");
            arlCam.add("a.co_forpag");
            arlCam.add("a.tx_des");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Cli.");
            arlAli.add("Dirección");
            arlAli.add("Telefono");
            arlAli.add("RUC/CI");
            arlAli.add("TIPO");
            arlAli.add("Desc");
            arlAli.add("Utili");
            arlAli.add("Ciudad");
            arlAli.add("co_vende");
            arlAli.add("Vendedor");
            arlAli.add("CoDTipPer");
            arlAli.add("TipoForPag");
            arlAli.add("Peso");
            arlAli.add("D.Gracias");
            arlAli.add("CodForPag");
            arlAli.add("Descri");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("165");
            arlAncCol.add("160");
            arlAncCol.add("80");
            arlAncCol.add("80");           
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("40");
            arlAncCol.add("20");
            arlAncCol.add("30");
            arlAncCol.add("30");
            arlAncCol.add("20");
            arlAncCol.add("20");
            arlAncCol.add("40");
            
            //Armar la sentencia SQL.
            String  strSQL="";
            
            strSQL+="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper," +
            "  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des  " +
            " FROM ( " +
            " select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
            " , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des  FROM tbm_cli as a  " +
            " LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
            " LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
            " left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
            " WHERE a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.st_reg='A'   order by a.tx_nom  " +
            ") AS a";
            
	   if(!objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario())){
            
		 strSQL="SELECT co_cli, tx_nom, tx_dir, tx_tel, tx_ide, tx_tipper, nd_maxdes, nd_maruti , tx_desLar , co_ven, vendedor , co_tipper," +
		"  ne_tipforpag,  nd_pes , ne_diagra , co_forpag , tx_des  " +
		" FROM ( " +
		" select a.co_cli, a.tx_nom, a.tx_dir, a.tx_tel, a.tx_ide, a.tx_tipper, a.nd_maxdes, a.nd_maruti , ciu.tx_desLar , a.co_ven, ven.tx_nom as vendedor , a.co_tipper " +
		" , b.ne_tipforpag,  b.nd_pes, a.ne_diagra, a.co_forpag , b.tx_des, a.st_ivaven  FROM tbr_cliloc AS a1 " +
		" INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
		" LEFT JOIN tbm_Ciu as ciu on(ciu.co_Ciu=a.co_ciu)  " +
		" LEFT JOIN tbm_usr as ven on (ven.co_usr = a.co_ven)" +
		" left join tbm_cabforpag as b on (b.co_emp=a.co_emp and b.co_forpag=a.co_forpag) "+
		" WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'   order by a.tx_nom  " +
		") AS a";
		 
	       strConAuxCon=" a1.co_loc="+objZafParSis.getCodigoLocal();	 
	   } 
            
            int intColOcu[]=new int[12];
            intColOcu[0]=6;
            intColOcu[1]=7;
            intColOcu[2]=8;
            intColOcu[3]=9;
            intColOcu[4]=10;
            intColOcu[5]=11; 
            intColOcu[6]=12;
            intColOcu[7]=13;
            intColOcu[8]=14;
            intColOcu[9]=15;
            intColOcu[10]=16;
            intColOcu[11]=17;
             
            
            objVenConCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
      
      
      private void ConfiguararTablaCli(){
          try{  
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_COCLI,"Código");
            vecCab.add(INT_TBL_IDCLI,"Identificación");
            vecCab.add(INT_TBL_NOCLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_STCLI,"Cliente");
	    vecCab.add(INT_TBL_PRCLI,"Proveedor");
	    
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            tblDatCli.setModel(objTblMod);
            tblDatCli.setRowSelectionAllowed(true);
            tblDatCli.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDatCli,INT_TBL_LINEA);
            
            tblDatCli.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDatCli.getColumnModel();
            
             //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_COCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_IDCLI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_NOCLI).setPreferredWidth(360);
            tcmAux.getColumn(INT_TBL_STCLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PRCLI).setPreferredWidth(50);
           
	    
	    Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_STCLI).setCellRenderer(objTblCelRenChk);
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_STCLI).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      
                }
            });
            objTblCelRenChk=null;
	    objTblCelEdiChk=null;
	    
	    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_PRCLI).setCellRenderer(objTblCelRenChk);
            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_PRCLI).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      
                }
            });
            objTblCelRenChk=null;
	    objTblCelEdiChk=null;
		    
	    
	    //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            
	      switch (intCodMod) {
               case 3: vecAux.add("" + INT_TBL_STCLI);  break;
	       case 4: vecAux.add("" + INT_TBL_PRCLI);  break;
	       default:
		      vecAux.add("" + INT_TBL_PRCLI);
	              vecAux.add("" + INT_TBL_STCLI);
	       break;
	      }
	    
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;   
	    
            //OcultarColumna(INT_TBL_CRE, tcmAux);
            
            tblDatCli.getTableHeader().setReorderingAllowed(false);
            objMouMotAda=new ZafMouMotAda();
            tblDatCli.getTableHeader().addMouseMotionListener(objMouMotAda);
            
             new ZafTblOrd(tblDatCli);
             new ZafTblBus(tblDatCli);
             
             objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);     
	
            tcmAux=null;  
          }catch(Exception e){ objUti.mostrarMsgErr_F1(this,e);  }
      }  
          
      
          
   
    public void OcultarColumna(int Columna, javax.swing.table.TableColumnModel tcmAux){
        try{
            tcmAux.getColumn(Columna).setWidth(0);
            tcmAux.getColumn(Columna).setMaxWidth(0);
            tcmAux.getColumn(Columna).setMinWidth(0);
            tcmAux.getColumn(Columna).setPreferredWidth(0);
            tcmAux.getColumn(Columna).setResizable(false);
            
        }catch(Exception e){ objUti.mostrarMsgErr_F1(this,e);  }
    }
    
      
           
   
      
    
      
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optGrp = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCliCod = new javax.swing.JTextField();
        txtCliNom = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        chkmos = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        spnCreDeb = new javax.swing.JScrollPane();
        tblDatCli = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        lblTit.setText("jLabel1");
        jPanel2.add(lblTit);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(null);

        optGrp.add(optTod);
        optTod.setFont(new java.awt.Font("SansSerif", 0, 11));
        optTod.setSelected(true);
        optTod.setText("Todos los clientes");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        jPanel5.add(optTod);
        optTod.setBounds(0, 10, 400, 20);

        optGrp.add(optFil);
        optFil.setFont(new java.awt.Font("SansSerif", 0, 11));
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado");
        jPanel5.add(optFil);
        optFil.setBounds(0, 30, 400, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre de cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 11))); // NOI18N
        panNomCli.setLayout(null);

        lblNomCliDes.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomCliDes.setText("Desde:");
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 268, 20);

        lblNomCliHas.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblNomCliHas.setText("Hasta:");
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(336, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });
        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 268, 20);

        jPanel5.add(panNomCli);
        panNomCli.setBounds(20, 70, 660, 52);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        jPanel5.add(lblCli);
        lblCli.setBounds(20, 50, 50, 20);

        txtCliCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliCodActionPerformed(evt);
            }
        });
        txtCliCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliCodFocusLost(evt);
            }
        });
        jPanel5.add(txtCliCod);
        txtCliCod.setBounds(70, 50, 60, 20);

        txtCliNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliNomActionPerformed(evt);
            }
        });
        txtCliNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliNomFocusLost(evt);
            }
        });
        jPanel5.add(txtCliNom);
        txtCliNom.setBounds(130, 50, 530, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        jPanel5.add(butCli);
        butCli.setBounds(660, 50, 20, 20);

        chkmos.setFont(new java.awt.Font("SansSerif", 0, 11));
        chkmos.setText("Mostrar sólo los clientes.");
        jPanel5.add(chkmos);
        chkmos.setBounds(20, 130, 350, 20);

        tabFrm.addTab("Filtro", jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        tblDatCli.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCreDeb.setViewportView(tblDatCli);

        jPanel6.add(spnCreDeb, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel6);

        jPanel3.add(tabFrm, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setToolTipText("Cierra la ventana.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        jPanel4.add(panBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel10.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel10.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel10.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel10.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel10, java.awt.BorderLayout.EAST);

        jPanel4.add(panBarEst, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formInternalFrameClosing

    
    
         
    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
     }    
      
    
    
    
    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:
          if (optTod.isSelected())
        {
            txtCliCod.setText("");
            txtCliNom.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        // TODO add your handling code here:
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCliHasFocusLost
  
    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        // TODO add your handling code here:
         if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        // TODO add your handling code here:
          txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        // TODO add your handling code here:
         txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained
       
    
     
      
      
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        
       consultar();
         
         
    }//GEN-LAST:event_butConActionPerformed

    
    
    private void consultar(){
          if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
         
    }
    
    
    
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDatCli.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDatCli.setRowSelectionInterval(0, 0);
                tblDatCli.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
     
     
    
    
    
    private boolean cargarDetReg()
    {
        int intCodEmp, intNumTotReg, i;
        String strSqlCon="", strSql="";
        boolean blnRes=true;
        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objZafParSis.getCodigoEmpresa();
           // intCodLoc=objZafParSis.getCodigoLocal();
            Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                Statement stm=con.createStatement();
                //Obtener la condición.
                String strAux="",strChCliPrv="";
                if (txtCliCod.getText().length()>0)
                    strAux+=" AND a.co_cli=" + txtCliCod.getText();
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
               
                
	if(chkmos.isSelected()){
	       switch (intCodMod) {
		   case 3: strChCliPrv=" AND a.st_cli='S' ";  break;  // MODULO CXC
		   case 4: strChCliPrv=" AND a.st_prv='S' ";  break;  // MODULO CXP
                }
	}	 
		
		



       

                //Obtener el número total de registros.
               
                 strSqlCon ="SELECT COUNT(*) FROM ( "+
                 " select a.co_cli, a.tx_ide, a.tx_nom, a.st_cli, a.st_prv from tbm_cli as a " +
		         " WHERE a.co_emp="+intCodEmp+" "+strAux+ " "+ strChCliPrv +" ) AS X ";

                 strSql="SELECT * FROM ( "+
                 " select a.co_cli, a.tx_ide, a.tx_nom, a.st_cli, a.st_prv from tbm_cli as a " +
		         " where a.co_emp="+intCodEmp+" "+strAux+ " "+ strChCliPrv +" "+
		         " ) AS  X1 ORDER BY tx_nom ";

                if(!objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario())){

                    strSqlCon="SELECT COUNT(*) FROM ( " +
                    " SELECT a1.co_cli FROM tbr_cliloc AS a1 " +
                    " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
                    " WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  "+strAux+" "+strChCliPrv+" order by a.tx_nom  " +
                    ") AS a";

                    strSql="SELECT * FROM ( " +
                    " SELECT a.co_cli, a.tx_ide, a.tx_nom, a.st_cli, a.st_prv FROM tbr_cliloc AS a1 " +
                    " INNER JOIN tbm_cli as a ON (a.co_emp=a1.co_emp and a.co_cli=a1.co_cli)   " +
                    " WHERE a1.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a1.co_loc ="+objZafParSis.getCodigoLocal()+" and a.st_reg='A'  "+strAux+" "+strChCliPrv+"  order by a.tx_nom  " +
                    ") AS a";
               }

                  
                intNumTotReg=objUti.getNumeroRegistro(this, objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSqlCon);
                if (intNumTotReg==-1)
                    return false;


            	  
		//System.out.println(" .. "+ strSQL );
		
                ResultSet rst=stm.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LINEA,"");
                        vecReg.add(INT_TBL_COCLI, rst.getString("co_cli"));
                        vecReg.add(INT_TBL_IDCLI ,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_NOCLI, rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_STCLI, new Boolean((rst.getString("st_cli").equals("S")?true:false)) );
			vecReg.add(INT_TBL_PRCLI, new Boolean((rst.getString("st_prv").equals("S")?true:false)) );
		        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDatCli.setModel(objTblMod);
                vecDat.clear();
                if (intNumTotReg==tblDatCli.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDatCli.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
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
    
      
        
      
      
 
      
  
  

      
      
    
    private void txtCliNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusLost
        // TODO add your handling code here:
          if (!txtCliNom.getText().equalsIgnoreCase(strDesLarPrv)) {
            if (txtCliNom.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {  
                BuscarCliente("a.tx_nom",txtCliNom.getText(),1);
            }
        }
        else
            txtCliNom.setText(strDesLarPrv);
     
          
          //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCliCod.getText().length()>0)
        {
            optFil.setSelected(true);  
        }
           
                
            
    }//GEN-LAST:event_txtCliNomFocusLost

    private void txtCliCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusLost
        // TODO add your handling code here:
         if (!txtCliCod.getText().equalsIgnoreCase(strCodPrv)) {
            if (txtCliCod.getText().equals("")) {
                txtCliCod.setText("");
                txtCliNom.setText("");
            }
            else {
                BuscarCliente("a.co_cli",txtCliCod.getText(),0);
            }
        }
        else  txtCliCod.setText(strCodPrv);
        
         
         
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCliCod.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
         
    }//GEN-LAST:event_txtCliCodFocusLost

    
    
    
     
  public void BuscarCliente(String campo,String strBusqueda,int tipo){
      objVenConCli.setTitle("Listado de Clientes");
      if (objVenConCli.buscar(campo, strBusqueda )) {
          txtCliCod.setText(objVenConCli.getValueAt(1));
          txtCliNom.setText(objVenConCli.getValueAt(2));
         
      }
      else
      {     objVenConCli.setCampoBusqueda(tipo);
            objVenConCli.cargarDatos();
            objVenConCli.show();
            if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
                txtCliCod.setText(objVenConCli.getValueAt(1));
                txtCliNom.setText(objVenConCli.getValueAt(2));
              
            }
            else{
                txtCliCod.setText(strCodPrv);
                txtCliNom.setText(strDesLarPrv);
            }
      }
  }
  
  
  
    
    
    private void txtCliNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliNomFocusGained
        // TODO add your handling code here:
          strDesLarPrv=txtCliNom.getText();
        txtCliNom.selectAll();
    }//GEN-LAST:event_txtCliNomFocusGained

    private void txtCliCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCodFocusGained
        // TODO add your handling code here:
        strCodPrv=txtCliCod.getText();
        txtCliCod.selectAll();
    }//GEN-LAST:event_txtCliCodFocusGained

    private void txtCliNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliNomActionPerformed
        // TODO add your handling code here:
          txtCliNom.transferFocus();
    }//GEN-LAST:event_txtCliNomActionPerformed

    private void txtCliCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCodActionPerformed
        // TODO add your handling code here:
         txtCliCod.transferFocus();
    }//GEN-LAST:event_txtCliCodActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
         objVenConCli.setTitle("Listado de Clientes");
        objVenConCli.setCampoBusqueda(1);
        objVenConCli.show();
        if (objVenConCli.getSelectedButton()==objVenConCli.INT_BUT_ACE) {
            txtCliCod.setText(objVenConCli.getValueAt(1));
            txtCliNom.setText(objVenConCli.getValueAt(2));
        }
        
        
         //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCliCod.getText().length()>0)
        {
            optFil.setSelected(true);
        }
        
        
    }//GEN-LAST:event_butCliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
	// TODO add your handling code here:
	try
        {
            Connection con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
		con.setAutoCommit(false);
	
		 if(actualizar(con)){
		    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
		    String strTit, strMsg;
		    strTit="Mensaje del sistema Zafiro";
		    strMsg="La operación ACTUALIZAR se realizó con éxito.";
		    oppMsg.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
		   
		    con.commit();
		    System.gc(); 
		  //  dispose(); 
		}else{
		     con.rollback();
	   }
	   con.close();
	   con=null;
	  }
     } catch (Exception e){  objUti.mostrarMsgErr_F1(this, e);   }
	
}//GEN-LAST:event_butGuaActionPerformed
    
    
    
    private boolean actualizar(java.sql.Connection con){
	boolean blnres=true;
	int intCodEmp=0;
	String sql="";
	try
        {
            intCodEmp=objZafParSis.getCodigoEmpresa();
            if (con!=null)
            {
	        Statement stm=con.createStatement();
		for(int i=0; i < tblDatCli.getRowCount(); i++){
		 //  System.out.println(" "+ tblDatCli.getValueAt(i, INT_TBL_LINEA).toString() );
		  if(tblDatCli.getValueAt(i, INT_TBL_LINEA).toString().equals("M")){
		   
                    sql="UPDATE tbm_cli SET st_cli='"+(tblDatCli.getValueAt(i, INT_TBL_STCLI).toString().equals("true")?'S':'N')+"'," +
		    " st_prv='"+(tblDatCli.getValueAt(i, INT_TBL_PRCLI).toString().equals("true")?'S':'N')+"' " +
		    " WHERE co_emp="+intCodEmp+" AND  co_cli="+tblDatCli.getValueAt(i, INT_TBL_COCLI).toString()+" "+
		    " " +
                    " ;INSERT INTO tbm_benchq (co_emp, co_cli, co_reg, tx_benchq, st_reg, st_regrep) " +
                    " SELECT "+intCodEmp+", "+tblDatCli.getValueAt(i, INT_TBL_COCLI).toString()+", 1, '"+tblDatCli.getValueAt(i, INT_TBL_NOCLI).toString()+"', 'P', 'I' " +
                    " WHERE NOT EXISTS( select * from tbm_benchq WHERE co_emp="+intCodEmp+" AND co_cli="+tblDatCli.getValueAt(i, INT_TBL_COCLI).toString()+"  ) "; 
                    stm.executeUpdate(sql); 
                    
		  }  
		}
	    }
        }
        catch (java.sql.SQLException e){ blnres=false; objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e){  blnres=false; objUti.mostrarMsgErr_F1(this, e);   }
	return blnres;
    }
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JCheckBox chkmos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.ButtonGroup optGrp;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnCreDeb;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatCli;
    private javax.swing.JTextField txtCliCod;
    private javax.swing.JTextField txtCliNom;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    // End of variables declaration//GEN-END:variables
    
    
      
     private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblDatCli.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_LINEA:
                    strMsg="";
                    break;
                case INT_TBL_COCLI:
                    strMsg="Codigo de Cliente";
                    break;
                case INT_TBL_IDCLI:
                    strMsg="Identificación del Cliente";
                    break;
                case INT_TBL_NOCLI:
                    strMsg="Nombre del Cliente";
                    break;
               case INT_TBL_STCLI:
                    strMsg="Es Cliente";
                    break;
	        case INT_TBL_PRCLI:
                    strMsg="Es proveedor.";
                    break;
               	    
		    
               
		    
            }
            tblDatCli.getTableHeader().setToolTipText(strMsg);
        }
    }
 
      
    
}
