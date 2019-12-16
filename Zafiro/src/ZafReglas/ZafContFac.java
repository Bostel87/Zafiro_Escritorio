/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author usuario
 */
public class ZafContFac extends JFrame{
    
    private JButton butCon;
    private JButton butPro;
    private JTable tblDat ;
    private JComboBox cboDoc;
    private JScrollPane spnDat;
    private JPanel  panCen;
    private JPanel panSup;
    //private JTabbedPane tabFrm;
    private JTextField txtNumOD;
    private JTextField txtBod;
    
    private ZafParSis objZafParSis;
    
    private ZafTblMod objTblMod; 
    
    
    
    
      //final int INT_TBL_LINEA=0;  // NUMERO DE LINEAS
      final int INT_TBL_CHKSEL=0; // SELECCION  DE FILA
      final int INT_TBL_CODCLI=1; // CODIGO CLIENTE
      final int INT_TBL_NOMCLI=2; // NOMBRE CLIENTE
      final int INT_TBL_CODEMP=3; // CODIGO EMPRESA
      final int INT_TBL_CODLOC=4; // CODIGO DEL LOCAL
      final int INT_TBL_CODTID=5; // CODIGO TIPO DE DOCUMENTO
      final int INT_TBL_CODOD=6; // CODIGO TIPO DE DOCUMENTO
      final int INT_TBL_FEDOC=7; // CODIGO TIPO DE DOCUMENTO
      
      
      public ZafContFac (/*ZafParSis objParSis*/){
          try{
            //objZafParSis= (ZafParSis)objParSis.clone();
            initComponents();      
            ConfigurarTabla();
          }/*catch(CloneNotSupportedException ex){
              ex.printStackTrace();
          }*/catch(Exception ex){
          
          }
      }

      
      private void initComponents(){
          
          panCen= new JPanel();
          panCen.setLayout(new BorderLayout());
          cboDoc= new JComboBox();
          butCon = new JButton("Consultar");
          butPro= new JButton("Procesar");
          spnDat= new JScrollPane();
          panSup= new JPanel();
          panSup.setPreferredSize(new Dimension(100,70));
          
          JLabel lblFac= new javax.swing.JLabel();
          lblFac.setFont(new java.awt.Font("SansSerif", 0, 11));
          lblFac.setText("Numero Fac:"); 
          panSup.add(lblFac);
          lblFac.setBounds(5, 10, 100, 20);
          txtNumOD=new JTextField();
          
          
          txtBod=new JTextField();
          
          panSup.setLayout(null);
          
          //panSup.add(cboDoc);
          //cboDoc.setBounds(10, 5, 60, 20);
          panSup.add(txtNumOD);
          txtNumOD.setBounds(60, 10, 100,20);
          
          JLabel lblCli= new javax.swing.JLabel();
          lblCli.setFont(new java.awt.Font("SansSerif", 0, 11));
          lblCli.setText("Cod Cliente:"); 
          
          panSup.add(lblCli);
          lblCli.setBounds(180, 10, 100, 20);
          
          
          panSup.add(txtBod);
          txtBod.setBounds(230, 10, 100, 20);
          
          panSup.add(butCon);
          butCon.setBounds(5, 35, 100, 20);
          butCon.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae) {
                 consultarOrdenes(Integer.parseInt(txtNumOD.getText())); 
              }
          });
          
          panSup.add(butPro);
          butPro.setBounds(120, 35, 100, 20);
          
          butPro.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent ae){
                generarGuia();
              }          
          });
          
          getContentPane().add(panSup,BorderLayout.NORTH);
          tblDat = new javax.swing.JTable();
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
           panCen.add(spnDat, java.awt.BorderLayout.CENTER);
           setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
           getContentPane().add(panCen,BorderLayout.CENTER);
                   java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
           setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
           setVisible(true);
      
      }
      
      
      
    private void ConfigurarTabla() {
     Vector vecCab=new Vector();
     try{
         //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            //vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CHKSEL,"");
            vecCab.add(INT_TBL_CODCLI,"Cód.Cli");
            vecCab.add(INT_TBL_NOMCLI,"Nom.CLi");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp");
            vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
            vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_CODOD,"Cod.Doc OD");
            vecCab.add(INT_TBL_FEDOC, "Fecha Doc");

            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            Vector vecEdi=new Vector();
            vecEdi.add("" + INT_TBL_CHKSEL);
            vecEdi.add("" + INT_TBL_NOMCLI);
            objTblMod.setColumnasEditables(vecEdi);            
            tblDat.setModel(objTblMod);  
            vecEdi=null;
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();  
            ZafTblCelRenChk objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellRenderer(objTblCelRenChk);
            
            
            ZafTblCelEdiChk objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            
            tcmAux.getColumn(INT_TBL_CHKSEL).setCellEditor(objTblCelEdiChk);
            
            
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intNumFil = tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                  int intNunFil = tblDat.getSelectedRow();
                  if(tblDat.getValueAt(intNunFil, INT_TBL_CHKSEL).toString().equals("true")){
                      System.out.println("hola");
                  }
        }});            
            

        }
     catch(Exception ex){
         ex.printStackTrace();
     }
    } 
    
    
    
    private void consultarOrdenes( int intNumOD){
      Statement stmLoc;
      ResultSet rstLoc;
      String strSql="";
      Vector vecData;
      Connection conn=null;

      try{
          conn =  java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.8.4/27julio", "postgres", "281273");
          stmLoc=conn.createStatement();
          vecData = new Vector();
          strSql="SELECT  co_clides,tx_nomclides,co_emp,co_loc,co_tipdoc,co_doc,fe_doc" +
                  " FROM TBM_CABGUIREM WHERE NE_NUMORDDES ="+intNumOD;
                  //" AND FE_DOC = CURRENT_DATE-1";
          rstLoc=stmLoc.executeQuery(strSql);
          while(rstLoc.next()){

             java.util.Vector vecReg = new java.util.Vector();
               //vecReg.add(INT_TBL_LINEA,"");
               vecReg.add(INT_TBL_CHKSEL, new Boolean(false) );
               vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_clides") );
               vecReg.add(INT_TBL_NOMCLI, rstLoc.getString("tx_nomclides") );
               vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
               vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
               vecReg.add(INT_TBL_CODTID, rstLoc.getString("co_tipdoc") );
               vecReg.add(INT_TBL_CODOD,rstLoc.getString("co_doc"));
               vecReg.add(INT_TBL_FEDOC,rstLoc.getString("fe_doc"));
               vecData.add(vecReg);
         }
          rstLoc.close();
          rstLoc=null;
          stmLoc.close();
          stmLoc=null;
          objTblMod.setData(vecData);
          tblDat.setModel(objTblMod); 
          
      }catch (Exception ex){
          ex.printStackTrace();
      }
    }
    
    
    
    private boolean generarGuia(){
        boolean booRet=false;
        Connection conn;
        try{        
            conn =  java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.8.4/27julio", "postgres", "281273");
            if(conn!=null){
                for(int i=0; i<tblDat.getRowCount(); i++){            
                    if( ((tblDat.getValueAt(i, INT_TBL_CHKSEL)==null?"false":(tblDat.getValueAt(i, INT_TBL_CHKSEL).toString().equals("")?"false":tblDat.getValueAt(i, INT_TBL_CHKSEL).toString())).equals("true")) ){                
                        ZafReglas.ZafGuiRem.ZafGenGuiRem objZafGuiRem=new ZafReglas.ZafGuiRem.ZafGenGuiRem(Integer.parseInt(txtBod.getText()), Integer.parseInt(txtNumOD.getText()));
                        objZafGuiRem.generarGuiRem(Integer.parseInt((String)tblDat.getValueAt(i, INT_TBL_CODLOC)),conn);
                        System.out.println("hola "+tblDat.getValueAt(i, INT_TBL_CODOD));
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return booRet;    
    }



    public static void main(String args[]){
        ZafContOD od=new ZafContOD();
    }    
    
}
