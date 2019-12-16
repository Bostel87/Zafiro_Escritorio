

/*
 * Created on 21 de Septiembre del 2017
 */
           
package Ventas.ZafVen42; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
                 
/**     
 * @author  Jota
 */
public class ZafVen42_11 extends javax.swing.JDialog {
    Connection CONN_GLO=null;
    Connection CONN_GLO_REM=null;
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl2, objTblCelRenLbl3;
    private ZafTblCelEdiTxt objTblCelEdiTxt; 
    private ZafParSis objParSis;
    private ZafTblMod objTblMod;
    private ZafInvItm objInvItm;  // Para trabajar con la informacion de tipo de documento
    private UltDocPrint objUltDocPrint;
    private ZafTblCelRenChk ZafTblCelRenChkDat;
    private ZafTblCelEdiChk objTblCelEdiChkBE;
    private ZafUtil objUti;
    private Connection con;
    private java.util.Date datFecAux;      
    private Statement stm;
    private ResultSet rst;
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_COD_EMP=1;       
    final int INT_TBL_COD_LOC=2;       
    final int INT_TBL_COD_COT=3;
    
    final int INT_TBL_COD_BOD_ORG=4;
    final int INT_TBL_NOM_BOD_ORG=5;
    final int INT_TBL_COD_BOD_DES=6;
    final int INT_TBL_NOM_BOD_DES=7;
    
    final int INT_TBL_COD_REG=8;       
    final int INT_TBL_COD_ITM=9;       
    final int INT_TBL_COD_ALT=10;      
    final int INT_TBL_NOM_ITM=11;      
    final int INT_TBL_CAN=12;      
    final int INT_TBL_CAN_AUT=13;       
    final int INT_TBL_CAN_PEN_FAC=14;    
    final int INT_TBL_CAN_CAN=15;     
    final int INT_TBL_CHK_CAN=16;  
    final int INT_TBL_NOUSAR=17;   
    final int INT_TBL_NOUSAR2=18;  
    final int INT_TBL_NOUSAR3=19;   
    
    int intNumCotBus=0;
    int intConStbBod=0;
    int[][] intColBodEmp = new int[30][2];
    
    private String strTit="Mensaje del Sistema Zafiro";
    
    private boolean blnCliRet;
     
    Vector vecCab=new Vector(); 
   
     
   
    public boolean blnAcepta = false; 
    ArrayList arlAuxColEdi=new java.util.ArrayList();
    private ArrayList arlDat;
    private ArrayList arlReg;
 
 
    private String strTitu="Pedidos a otras bodegas";
    private String strVersion=" v0.01";
     
      
   private java.awt.Frame Frame;
   

    public ZafVen42_11() {
    }
    
    private int intCodEmp, intCodLoc, intCodCot;
    /** Creates new form pantalla dialogo */
    
    public ZafVen42_11(Frame parent,  ZafParSis ZafParSis, int CodEmp, int CodLoc, int CodCot) {
        super(parent, true);
        try{ 
          Frame=parent;
            System.out.println("ZafVen42_11");
          this.objParSis = ZafParSis;
          objUti = new ZafUtil();
          objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objParSis);
          

          objUltDocPrint = new Librerias.ZafUtil.UltDocPrint(objParSis);
          objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl2 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl3 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

          ZafTblCelRenChkDat = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
 
          initComponents();
          intCodEmp=CodEmp;intCodLoc=CodLoc;intCodCot=CodCot;
          configurarFrm();       

          this.setTitle( strTitu );
          lblTit.setText( strTitu + strVersion);  // José Marin 5/Marzo/2014

       }
       catch (Exception e){ 
           objUti.mostrarMsgErr_F1(this, e);  
       } 
    }   


        
    private void configurarFrm(){

          configuraTbl();      
          cargarDat(intCodEmp,intCodLoc,intCodCot);      
    } 
  
   
    private Vector vecData, vecReg;
 
    private boolean cargarDat(int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{    
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                vecData = new Vector();
                strSql="";
                strSql+=" SELECT a4.co_emp, a4.co_loc, a4.co_cot, a4.co_reg, a4.co_itm, a4.tx_codAlt, a4.tx_nomItm, \n";
                strSql+="        a7.co_bod as co_bodDes,a7.tx_nom as tx_nomBodDes, a3.co_bod as co_bodOrg, a3.tx_nom as tx_nomBodOrg , a1.nd_can \n";
                strSql+=" FROM tbm_pedOtrBodCotVen as a1  \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a2 ON (a1.co_empRel=a2.co_emp AND a1.co_bodRel= a2.co_bod and a2.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+") \n";
                strSql+=" INNER JOIN tbm_bod as a3 ON (a2.co_empGrp=a3.co_emp AND a2.co_bodGrp=a3.co_bod) \n";
                strSql+=" INNER JOIN tbm_detCotVen as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cot=a4.co_cot AND a1.co_reg=a4.co_reg) \n";
                strSql+=" INNER JOIN tbr_bodloc as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a5.st_reg='P') \n";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a6 ON (a5.co_emp=a6.co_emp AND a5.co_bod=a6.co_bod and a2.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+") \n";
                strSql+=" INNER JOIN tbm_bod as a7 ON (a6.co_empGrp=a7.co_emp AND a6.co_bodGrp=a7.co_bod) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" AND a3.co_bod<>a7.co_bod \n";
                strSql+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_cot, a4.co_reg \n";
                System.out.println("ZafVen42_11.cargarDat: \n"+ strSql );
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_LINEA,"");
                    vecReg.add( INT_TBL_COD_EMP, rstLoc.getString("co_emp") );
                    vecReg.add( INT_TBL_COD_LOC, rstLoc.getString("co_loc")); 
                    vecReg.add( INT_TBL_COD_COT, rstLoc.getString("co_cot")); 
                    
                    vecReg.add( INT_TBL_COD_BOD_ORG, rstLoc.getString("co_bodOrg")); 
                    vecReg.add( INT_TBL_NOM_BOD_ORG, rstLoc.getString("tx_nomBodOrg")); 
                    vecReg.add( INT_TBL_COD_BOD_DES, rstLoc.getString("co_bodDes")); 
                    vecReg.add( INT_TBL_NOM_BOD_DES, rstLoc.getString("tx_nomBodDes")); 
                    
                    
                    vecReg.add( INT_TBL_COD_REG, rstLoc.getString("co_reg") );   
                    vecReg.add( INT_TBL_COD_ITM, rstLoc.getString("co_itm") );   
                    vecReg.add( INT_TBL_COD_ALT, rstLoc.getString("tx_codAlt") );
                    vecReg.add( INT_TBL_NOM_ITM, rstLoc.getString("tx_nomItm") );
                    vecReg.add( INT_TBL_CAN,  rstLoc.getString("nd_can"));  
                    vecReg.add( INT_TBL_CAN_AUT,  "" );
                    vecReg.add( INT_TBL_CAN_PEN_FAC,  "" );
                    vecReg.add( INT_TBL_CAN_CAN,  "" );
                    vecReg.add( INT_TBL_CHK_CAN ,true);
                    vecReg.add( INT_TBL_NOUSAR, "" );
                    vecReg.add( INT_TBL_NOUSAR2, "" );
                    vecReg.add( INT_TBL_NOUSAR3, "" );
                    vecData.add(vecReg);
                }  
                
                objTblMod.setData(vecData);
                tblDat .setModel(objTblMod);         
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);    
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);

                blnRes=true;
            }
      }
      catch(java.sql.SQLException Evt) {  
          blnRes=false; 
          objUti.mostrarMsgErr_F1(Frame, Evt);  
      }
      catch(Exception Evt) { 
          blnRes=false; 
          objUti.mostrarMsgErr_F1(Frame, Evt); 
      }
       return blnRes;          
}   

   
 
    private void MensajeInf(String strMensaje){
        //JOptionPane obj =new JOptionPane();
        String strTit="Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(Frame,strMensaje,strTit,JOptionPane.INFORMATION_MESSAGE);
    }

    
  
    private boolean configuraTbl(){
           boolean blnRes=false;
           try{
                //Configurar JTable: Establecer el modelo.
                vecCab.clear();
                vecCab.add(INT_TBL_LINEA,"..");
                vecCab.add(INT_TBL_COD_EMP,"Cód.Emp.");
                vecCab.add(INT_TBL_COD_LOC,"Cód.Loc.");
                vecCab.add(INT_TBL_COD_COT,"Cód.Cot.");
                
                vecCab.add(INT_TBL_COD_BOD_ORG,"Cód.Bod.Org.");
                vecCab.add(INT_TBL_NOM_BOD_ORG,"Origen");
                vecCab.add(INT_TBL_COD_BOD_DES,"Cód.Bod.Des.");
                vecCab.add(INT_TBL_NOM_BOD_DES,"Destino");
                
                
                vecCab.add(INT_TBL_COD_REG,"Cód.Reg.");
                vecCab.add(INT_TBL_COD_ITM,"Cód.Itm.");
                vecCab.add(INT_TBL_COD_ALT,"Cód.Alt.");
                vecCab.add(INT_TBL_NOM_ITM,"Nom.Itm." );  
                vecCab.add(INT_TBL_CAN,"Cantidad" );
                vecCab.add(INT_TBL_CAN_AUT,"Can.Aut." );
                vecCab.add(INT_TBL_CAN_PEN_FAC,"Can.Pen.Fac." );
                vecCab.add(INT_TBL_CAN_CAN,"Can.Cancelada" );
                vecCab.add(INT_TBL_CHK_CAN,"");
                vecCab.add(INT_TBL_NOUSAR,"");
                vecCab.add(INT_TBL_NOUSAR2,"" );
                vecCab.add(INT_TBL_NOUSAR3,"" );

                objTblMod=new ZafTblMod();
                objTblMod.setHeader(vecCab);
                tblDat.setModel(objTblMod);

                //Configurar JTable: Establecer tipo de selección.
                tblDat.setRowSelectionAllowed(true);
                tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LINEA);


                ZafMouMotAda objMouMotAda=new ZafMouMotAda();
                tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                TableColumnModel tcmAux=tblDat.getColumnModel();

                //Tamaño de las celdas
                tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
                tcmAux.getColumn(INT_TBL_COD_EMP).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_COD_REG).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_COD_ITM).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_COD_ALT).setPreferredWidth(100);
                tcmAux.getColumn(INT_TBL_NOM_ITM).setPreferredWidth(130);
                tcmAux.getColumn(INT_TBL_CAN).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CAN_AUT).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CAN_PEN_FAC).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CAN_CAN).setPreferredWidth(60);
                tcmAux.getColumn(INT_TBL_CHK_CAN).setPreferredWidth(30);

                tcmAux.getColumn(INT_TBL_NOUSAR3).setPreferredWidth(10);

                /////////////////////////////////////

              

               objTblCelRenLbl.addTblCelRenListener(new ZafTblCelRenAdapter() {
                    @Override
                    public void beforeRender(ZafTblCelRenEvent evt) {
                        //Mostrar de color gris las columnas impares.

                        int intCell=objTblCelRenLbl.getRowRender();

                        String str=tblDat.getValueAt(intCell, INT_TBL_COD_LOC).toString();
                        if(str.equals("")){
                            objTblCelRenLbl.setBackground(Color.BLUE);
                            objTblCelRenLbl.setFont(new Font(objTblCelRenLbl.getFont().getFontName(), Font.BOLD,   objTblCelRenLbl.getFont().getSize()));
                            objTblCelRenLbl.setForeground(Color.WHITE);
                        } 
                        if(str.equals("1")){
                            objTblCelRenLbl.setBackground(Color.ORANGE);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        } 
                        if(str.equals("2")){
                            objTblCelRenLbl.setBackground(Color.WHITE);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        } 
                        if(str.equals("4")){
                            objTblCelRenLbl.setBackground(Color.YELLOW);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        }

                        if(str.equals("0")){
                            objTblCelRenLbl.setBackground(Color.BLACK);
                            objTblCelRenLbl.setForeground(Color.BLACK);
                        } 

                    }
                });

              objTblCelRenLbl2.setBackground(objParSis.getColorCamposObligatorios());
              objTblCelRenLbl2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
              objTblCelRenLbl2.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
              objTblCelRenLbl2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
              objTblMod.setColumnDataType(INT_TBL_CAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_CAN_AUT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_AUT).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_CAN_PEN_FAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_PEN_FAC).setCellRenderer(objTblCelRenLbl2);
              objTblMod.setColumnDataType(INT_TBL_CAN_CAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
              tcmAux.getColumn(INT_TBL_CAN_CAN).setCellRenderer(objTblCelRenLbl2);
              
              
 

              objTblCelRenLbl3.setBackground(objParSis.getColorCamposObligatorios());
              objTblCelRenLbl3.setHorizontalAlignment(JLabel.RIGHT);
              objTblCelRenLbl3.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);

               
 

                /* Aqui se agrega las columnas que van 
                    ha hacer ocultas
                 * */

                ArrayList arlColHid=new ArrayList();
                arlColHid.add(""+INT_TBL_COD_EMP);
                arlColHid.add(""+INT_TBL_COD_LOC);
                arlColHid.add(""+INT_TBL_COD_COT);
                
                arlColHid.add(""+INT_TBL_COD_BOD_ORG);
                arlColHid.add(""+INT_TBL_COD_BOD_DES);
                
                
                arlColHid.add(""+INT_TBL_COD_REG);
                arlColHid.add(""+INT_TBL_COD_ITM);
                arlColHid.add(""+INT_TBL_CHK_CAN);
                arlColHid.add(""+INT_TBL_CAN_CAN);
                
                arlColHid.add(""+INT_TBL_CAN_AUT);
                arlColHid.add(""+INT_TBL_CAN_PEN_FAC);
                arlColHid.add(""+INT_TBL_NOUSAR);
                arlColHid.add(""+INT_TBL_NOUSAR2);
                arlColHid.add(""+INT_TBL_NOUSAR3);

                objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
                arlColHid=null;

                tblDat.getTableHeader().setReorderingAllowed(false);

                ZafTblPopMnu zafTblPopMnu = new ZafTblPopMnu(tblDat);

               blnRes=true;
             }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(Frame,e);  }
            return blnRes;
      }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpInsGenFac = new javax.swing.ButtonGroup();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        panTbl = new javax.swing.JPanel();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panTit.setPreferredSize(new java.awt.Dimension(100, 48));
        panTit.setLayout(null);

        lblTit.setText("titulo");
        panTit.add(lblTit);
        lblTit.setBounds(210, 7, 300, 14);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        panTabGen.setLayout(new java.awt.BorderLayout());

        panTbl.setLayout(new java.awt.BorderLayout());

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
        scrTbl.setViewportView(tblDat);

        panTbl.add(scrTbl, java.awt.BorderLayout.CENTER);

        panTabGen.add(panTbl, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panFrm.add(tabGen, java.awt.BorderLayout.CENTER);
        tabGen.getAccessibleContext().setAccessibleName("General");

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCan.setText("Cerrar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBut.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(641, 470));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
    // TODO add your handling code here:
     cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // TODO add your handling code here:
    cerrarVen();
}//GEN-LAST:event_formWindowClosing

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
         
}//GEN-LAST:event_formWindowOpened
 
    
    
    private String strSql;
    
    
    
 
       
    private void cerrarVen(){
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        //JOptionPane oppMsg=new JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(JOptionPane.showConfirmDialog(this,strMsg,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ) {
            System.gc();
            blnAcepta=true;
            dispose();
        }
    }
    

    
    
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.ButtonGroup grpInsGenFac;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTbl;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    public boolean isBlnCliRet() {
        return blnCliRet;
    }

    public void setBlnCliRet(boolean blnCliRet) {
        this.blnCliRet = blnCliRet;
    }




     private class ZafMouMotAda extends MouseMotionAdapter
    {
         @Override
        public void mouseMoved(MouseEvent evt)
        {
        int intCol=tblDat.columnAtPoint(evt.getPoint());
        String strMsg="";
        switch (intCol)
        {
            case INT_TBL_LINEA:
                strMsg="";
            break;
            case INT_TBL_COD_EMP:
                strMsg="";
            break;
            
            case INT_TBL_NOM_BOD_ORG:
                strMsg="Nombre de la Bodega de Origen";
            break;
            case INT_TBL_NOM_BOD_DES:
                strMsg="Nombre de la Bodega de Destino";
            break;
            
            
            
            case INT_TBL_COD_REG:
                strMsg="Código de Registro";
            break;
            case INT_TBL_COD_ITM:
                strMsg="Código del Item";
            break;
            case INT_TBL_COD_ALT:
                strMsg="Código Alterno del Item";
            break;
            case INT_TBL_CAN:
                strMsg="Cantidad en la Cotizacion";
            break;
            case INT_TBL_CAN_AUT:
                strMsg="Cantidad Autorizada";
            break;
            case INT_TBL_CAN_PEN_FAC:
                strMsg="Cantidad Pendiente de Facturar";
            break;
             
             
            default:
                strMsg=" ";
            break;
            
        }
        tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    @Override
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }    
 

     
    
}

