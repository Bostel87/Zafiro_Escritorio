/*
 *  ZafConItm => Presenta Notificaciones al Abrir Zafiro.
 *  
 */
package Librerias.ZafConItm;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.io.DataInputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JProgressBar;

public class ZafConItm extends javax.swing.JPanel 
{
    private ZafParSis objParSis;
    private JProgressBar pgrConMem;
    private JButton butGarCol;
    private ArrayList arlDat, arlReg;
    ZafUtil objUti;
    java.awt.Frame parent2;
    javax.swing.JDesktopPane dskGen;
    int intEstado = 0;
    int intEstConFac = 0;
    int intEstConVenCon = 0;
    int intEstAutPen = 0;
    int intEstSolRevSuj = 0;
    int intEstSolRevTec = 0;
    int intEstSolRevConfIngBod = 0;
    int intEstSolDevVenAut = 0;
    int intEstItmFalstkFis = 0;
    int intEstImpGuia = 0;
    int intEstImpGuiaFac = 0;
    int INTCODREGCEN = 0;
    int intEstSolPenFac = 0;
    int intEstFacPenRec = 0;
    int intEstEgrIngPenConf = 0;
    int INT_CO_EMP_GLO = 0;
    int INT_CO_LOC_GLO = 0;
    int intCodTipDocOrdCom=2;   //Cod Tip Doc Faccom
    /*Diario para Confirmacion de Depositos*/
    int intEstConDep = 0;
    String TERMINAL = "S";
    int intDiaBusFacPenImp = 0;
    String strConBusFacPenImp = "";
    int intCodBodGrEgrIng = 0;
    int intEstMerMalEst = 0;
    private ServerSocket srvSck = null;
    private Socket sck = null;
    private final int intPuerto = 9001;
    private ZafThreadAlertaFaltantes objThrArr = null;
    private int intCodUsr = 0;
    String version = " v6.2 ";

    /**
     * Creates a new instance of ZafConItm
     */
    public ZafConItm(ZafParSis obj, Frame parent) 
    {
        try 
        {
            objParSis = obj;
            parent2 = parent;
            objUti = new ZafUtil();
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    public ZafConItm(ZafParSis obj, java.awt.Frame parent, javax.swing.JDesktopPane dskGe) 
    {
        try 
        {
            objParSis = obj;
            parent2 = parent;
            dskGen = dskGe;
            this.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            this.setLayout(new java.awt.BorderLayout());
            this.setPreferredSize(new java.awt.Dimension(140, 20));

            objUti = new ZafUtil();

            //Configurar el JProgressBar.
            pgrConMem = new JProgressBar();
            pgrConMem.setBorderPainted(false);
            pgrConMem.setStringPainted(true);
            pgrConMem.setBackground(new java.awt.Color(255, 255, 255));
            pgrConMem.setForeground(new java.awt.Color(0, 0, 0));
            //Configurar el JButton.
            butGarCol = new JButton("...");
            butGarCol.setToolTipText("Liberar memoria");
            butGarCol.setPreferredSize(new java.awt.Dimension(20, 20));
            butGarCol.setFocusPainted(false);
            butGarCol.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    butGarColActionPerformed(evt);
                }
            });

            // Validacion donde Si es Miguel o Don Luigi mostrara un mensaje donde aparecen los items que NO tienen Precios

            intCodUsr = objParSis.getCodigoUsuario();

            //     psolorzano           acattan            gmosquera   
            if ((intCodUsr == 7) || (intCodUsr == 8) || (intCodUsr == 33))
            {
                intDiaBusFacPenImp = 1;
                cargarListadoFacPenImp();
            }
            if ((intCodUsr == 44)) //       pperez
            {
                intDiaBusFacPenImp = 1;
                strConBusFacPenImp = " a.co_emp=2 and a.co_loc=1 and ";
                cargarListadoFacPenImp();
            }
            if ((intCodUsr == 39)) //      pgraf
            {
                intDiaBusFacPenImp = 2;
                strConBusFacPenImp = " a.co_emp=2 and a.co_loc=1 and ";
                cargarListadoFacPenImp();
            }
            if ((intCodUsr == 58))  // Azuleta
            {   
                intDiaBusFacPenImp = 2;
                strConBusFacPenImp = " a.co_emp=2 and a.co_loc=4 and ";
                cargarListadoFacPenImp();
            }
            //        ppezo              wmoran
            if ((intCodUsr == 17) || (intCodUsr == 102)) 
            {
                intDiaBusFacPenImp = 2;
                cargarListadoFacPenImp();
            }

            /**Lista de Ítems sin Precios.             * 
             * terminal I que no tienen precio.
             * El Ing.Ruiz ya no se encarga de actualización de precios.
             */
            //if(intCodUsr==24) {  // Lsensi  24 
            if (intCodUsr == 93) //csensi 93  20/Sep/2016
            {
                TERMINAL = "I";
                butGarColActionPerformed2();
                intDiaBusFacPenImp = 5;
                // cargarListadoFacPenImp();
            }
            if (intCodUsr == 12)  // Miguel
            { 
                butGarColActionPerformed2();
            }

            if ( (intCodUsr == 57)  || (intCodUsr == 41) || (intCodUsr == 33)  || (intCodUsr == 102) || (intCodUsr == 7) || (intCodUsr == 8) || (intCodUsr == 17)  || (intCodUsr == 92))
            {
                cargarListadoFacVenCon();
            }

            if ((intCodUsr == 57) || (intCodUsr == 41) || (intCodUsr == 33) || (intCodUsr == 102) || (intCodUsr == 7) || (intCodUsr == 8) || (intCodUsr == 17) ) 
            {
                cargarListadoCotPenRec();
            }

            //Listado de Items Pendientes de Realizar Conteo.
            //Inventaristas
            if ( (intCodUsr == 86)  || (intCodUsr == 281) || (intCodUsr == 237)  || (intCodUsr == 238) || (intCodUsr == 233) || (intCodUsr == 338))
            {
                cargarListadoItmPenConInv();
            }


//      if(  (intCodUsr==24) )
//          cargarListadoSolPenAut();


            if ((intCodUsr == 21)) 
            {
                cargarListadoSolPenRevSuj();
                cargarListadoSolRevConfIngBod();
            }

            if ((intCodUsr == 15)) {
                cargarListadoSolPenRevTec();
            }

            if ((intCodUsr == 71)) {
                cargarListadoSolDevVenAut();
            }

            /*Dario para Confirmacion de Depositos*/
            if ((intCodUsr == 71) || (intCodUsr == 81) || (intCodUsr == 102) || (intCodUsr == 91)) 
            {
                cargarListadoConDep();
            }

            if ((intCodUsr == 21)) 
            {
                cargarListadoItmFalstkFis();
            }


            if ((intCodUsr == 21)) 
            {
                intCodBodGrEgrIng = 1;
                cargarListadoEgrIngPenConf();
            }

            if ((intCodUsr == 75)) {
                intCodBodGrEgrIng = 2;
                cargarListadoEgrIngPenConf();
            }

            if ((intCodUsr == 61)) {
                intCodBodGrEgrIng = 3;
                cargarListadoEgrIngPenConf();
            }

            if ((intCodUsr == 68)) {
                intCodBodGrEgrIng = 5;
                cargarListadoEgrIngPenConf();
            }

            if ((intCodUsr == 44)) {
                intCodBodGrEgrIng = 11;
                cargarListadoEgrIngPenConf();
            }

            /* Solicitudes Pendientes de Volver a Facturar */
            if ((intCodUsr == 91) || (intCodUsr == 102)) {
                cargarListadoSolPenFac();
                cargarListadoFacPenRec();
            }

            if ((intCodUsr == 43)) {
                cargarListadoFacPenRec();
            }

            // LISTADO DE FACTURAS NO IMPRESAS Y ACTIVAS Y QUE YA TIENEN MAS DE 7 DIAS
            if ((intCodUsr == 91)) 
            {
                cargarListadoFacNoImp();
            }

            // LISTADO DE MERCADERIA EN MAL ESTADO
            if ((intCodUsr == 110)) 
            {   // para  jfalcones
                cargarListadoMerMalEst();
            }

            /**
             * PARA IMPRIMIR LAS ORDENES DE COMPRA NO IMPRESAS.
             *
             */
            if ((intCodUsr == 7) || (intCodUsr == 8)   // ventas de tuval
                    || (intCodUsr == 17) || (intCodUsr == 33) || (intCodUsr == 92)  // ventas de dimulti
                    || (intCodUsr == 41) || (intCodUsr == 27)  // ventas de quito
                    || (intCodUsr == 58)  // ventas de manta
                    || (intCodUsr == 127)  // ventas de santo domingo
                    //|| (intCodUsr==1)   // sistemas
                    )
            {
                cargarFacComNoImp();
            }

            /**
             * CONFIRMACIONES DE INGRESO PENDIENTES
             */
            if ((intCodUsr == 343)          // gvillon    => Bod. California
                    || (intCodUsr == 176)   // dmoreira   => Bod. Vía a Daule
                    || (intCodUsr == 327)   // dsantamaria=> Bod. Quito Norte
                    || (intCodUsr == 263)   // cchavez    => Bod. Manta
                    //|| (intCodUsr == 180) // jpalma     => Bod. Santo Domingo
                    || (intCodUsr == 304)   // dñauta     => Bod. Cuenca
                    || (intCodUsr == 253)   // emorales   => Bod. Inmaconsa (Accesorios)
                    || (intCodUsr == 133)   // acuadrado  => Bod. Inmaconsa (Accesorios)
                    || (intCodUsr == 348)   // hgalarza   => Bod. Inmaconsa 
                    //|| (intCodUsr == 341) // bizquierdo => Bod. Inmaconsa 
                    //|| (intCodUsr == 163) // fbermeo    => Bod. Inmaconsa 
                    )
            {
                cargarDocIngPenConf();
            }
            

            /**
             * EFLORESA 2012-07-02 NOTIFICACIONES DE ORDENES DE DESPACHO CON
             * ITEMS FALTANTES/DAÑADOS LESPINOZA(intCodUsr==145)
             * JITURRALDE(intCodUsr==75) FCASTELLANOS(intCodUsr==141)
             * GCAMPOS(intCodUsr==68) JRAMIREZ(intCodUsr==127) HROMO
             * (intCodUsr==140) PSOLORZANO (intCodUsr==7) MPENAFIEL
             * (intCodUsr==148)
             *
             */
            if ((intCodUsr == 145) // BODEGA TUVAL
                    || (intCodUsr == 75) // BODEGA DIMULTI
                    || (intCodUsr == 141) // BODEGA CASTEK UIO
                    || (intCodUsr == 68) // BODEGA CASTEK MAN
                    || (intCodUsr == 127) // BODEGA CASTEK SDO
                    || (intCodUsr == 140) // OPERACIONES
                    || (intCodUsr == 148) // INVENTARISTAS
                    || (intCodUsr == 7) || (intCodUsr == 8) || (intCodUsr == 123) || (intCodUsr == 144) // VENTAS TUVAL                
                    || (intCodUsr == 17) || (intCodUsr == 33) || (intCodUsr == 92) || (intCodUsr == 88) // VENTAS DIMULTI                
                    || (intCodUsr == 41) || (intCodUsr == 27) || (intCodUsr == 149) // VENTAS CASTEK UIO                
                    || (intCodUsr == 58) || (intCodUsr == 85) // VENTAS CASTEK MAN                
                    || (intCodUsr == 155) // VENTAS CASTEK SANTO DOMINGO
                    ) {
                cargarOrdDesFalDan();
            }

            //Adicionar objetos al JDialog.
            this.add(pgrConMem, java.awt.BorderLayout.CENTER);
            this.add(butGarCol, java.awt.BorderLayout.EAST);
            // this.add(butGarCol2, java.awt.BorderLayout.WEST);

            arlDat = new ArrayList();

            //Crear y correr el hilo que controla la memoria.
            ZafThrConMem objThrConMem = new ZafThrConMem();
            objThrConMem.start();

            iniciarAlertaFaltantes();

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }

    }

    /**
     * Esta función le sugiere al "Garbage Collector" que libere la memoria.
     */
    private void butGarColActionPerformed(java.awt.event.ActionEvent evt) {
        //System.gc();
        Runtime.getRuntime().gc();
    }

    private void cargarFacComNoImp() {
        try {
            ZafThrComNoImp obj = new ZafThrComNoImp();
            obj.start();
        } catch (Exception e) {
        }
    }

    /**
     * Notificaciones de Documentos Pendientes de Confirmación de Ingreso.
     */
    private void cargarDocIngPenConf() 
    {
        try 
        {
            ZafThrIngPenConf obj = new ZafThrIngPenConf();
            obj.start();
        }
        catch (Exception e) 
        {
        }
    }

    /**
     * EFLORESA NOTIFICACION DE ITEMS FALTANTES/DAÑADOS
     */
    private void cargarOrdDesFalDan() {
        try {
            ZafThrConOrdDesFalDan objConOrdDesFalDan = new ZafThrConOrdDesFalDan();
            objConOrdDesFalDan.start();
            objConOrdDesFalDan = null;
        } catch (Exception e) {
            ;
        }
    }

    private class ZafThrComNoImp extends Thread 
    {

        public ZafThrComNoImp() {
        }

        @Override
        public void run() {
            try {

                boolean blnRes = false;
                String strSql = "SELECT co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, fe_doc from tbm_cabmovinv "
                        + " where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_tipdoc="+intCodTipDocOrdCom+" and st_imp='N' ";

                java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet rst = stm.executeQuery(strSql);

                arlDat.clear();
                if (rst.next()) {

                    blnRes = true;
                }
                rst.close();
                stm.close();
                conn.close();
                rst = null;
                stm = null;
                conn = null;

                if (blnRes) {
                    ZafConItm_14 objLis = new ZafConItm_14(parent2, true, objParSis);
                    //objLis.show();
                    objLis.setVisible(true);
                }

            } catch (Exception e) {
            }

        }
    }

    private class ZafThrIngPenConf extends Thread 
    {
        public ZafThrIngPenConf() 
        {
        }

        @Override
        public void run() 
        {
            boolean blnRes = false;
            try 
            {
                String strSql="";
                
                Compras.ZafCom79.ZafCom79_01 objZafCom79_01 = new  Compras.ZafCom79.ZafCom79_01(parent2, true, objParSis);
                strSql = objZafCom79_01.getQueryItemsPendientesNotificacion(); 
              
                //System.out.println("ZafThrIngPenConf: " + strSql);
                java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet rst = stm.executeQuery(strSql);

                if (rst.next()) 
                {
                    blnRes = true;
                }
                rst.close();
                rst = null;

                if (blnRes) 
                {
                    stm.close();
                    conn.close();
                    stm = null;
                    conn = null;

                    objZafCom79_01.setVisible(true);
                } 
            }
            catch (Exception e) 
            {
                blnRes = false;
            }

        }
    }

    private void butGarColActionPerformed2() {
        try {
            if (intEstado == 0) {
                ZafThrConItm objThrConItm = new ZafThrConItm();
                objThrConItm.start();
                intEstado = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoFacPenImp() {
        try {

            if (intEstConFac == 0) {
                ZafThrConFacPenImp objThrConFacPenImp = new ZafThrConFacPenImp();
                objThrConFacPenImp.start();
                intEstConFac = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoFacVenCon() {
        try {

            if (intEstConVenCon == 0) {
                ZafThrConFacVenCon objThrConFacVenCon = new ZafThrConFacVenCon();
                objThrConFacVenCon.start();
                intEstConVenCon = 1;
            }
        } catch (Exception e) {
        }
    }
    
    /* Carga el listado de Items Pendientes de Realizar Conteo de Inventario.*/
    private void cargarListadoItmPenConInv() 
    {
        try 
        {
            if (intEstConVenCon == 0) 
            {
                ZafThrConItmPenConInv objThrConItmPenConInv = new ZafThrConItmPenConInv();
                objThrConItmPenConInv.start();
                intEstConVenCon = 1;
            }
        } catch (Exception e) {
        }
    }

    /*Dario para Confirmacion de Depositos*/
    private void cargarListadoConDep() {
        try {
            if (intEstConDep == 0) {
                ZafThrConDep objThrConDep = new ZafThrConDep();
                objThrConDep.start();
                intEstConDep = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoCotPenRec() {
        try {

            if (intEstConVenCon == 0) {
                ZafThrLisCotPenRec objThrLisCotPenRec = new ZafThrLisCotPenRec();
                objThrLisCotPenRec.start();
                intEstConVenCon = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoEgrIngPenConf() {
        try {

            if (intEstEgrIngPenConf == 0) {
                ZafThrLisEgrIngPenConf objThrLisEgrIngPenConf = new ZafThrLisEgrIngPenConf();
                objThrLisEgrIngPenConf.start();
                intEstEgrIngPenConf = 1;
            }
        } catch (Exception e) {
        }
    }

    private class ZafThrLisEgrIngPenConf extends Thread {

        public ZafThrLisEgrIngPenConf() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    String strEgr = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a4.tx_nom,  a3.tx_descor, a.ne_numdoc, a.fe_doc,  a.st_coninv  , a.st_tieguisec, a.st_reg  "
                            + "  ,CASE WHEN  a.st_coninv  IN ('P') THEN 'PENDIENTE' ELSE 'PARCIAL' END AS estado   "
                            + "   FROM tbm_cabguirem as a  "
                            + "  INNER JOIN tbm_cabtipdoc AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc AND a3.co_tipdoc=a.co_tipdoc ) "
                            + "  INNER JOIN tbm_loc AS a4 ON(a4.co_emp=a.co_emp AND a4.co_loc=a.co_loc ) "
                            + "  INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar)  "
                            + "  WHERE  a.st_reg not in('E')   and a.co_tipdoc in ( 102, 101  )  "
                            + "  AND ( a6.co_empGrp=0 AND a6.co_bodGrp=( " + intCodBodGrEgrIng + " ) )  and  a.st_tieguisec='N' and a.st_reg  not in ('I','E') and a.st_coninv in ('E','P') "
                            + "   AND a.ne_numdoc != 0   "
                            + "  ORDER BY  a.ne_numdoc  ";


                    String strIng = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a4.tx_nom,  a3.tx_descor,  a.ne_numdoc, a.fe_doc,  a.st_coninv   "
                            + " ,CASE WHEN  a.st_coninv  IN ('P') THEN 'PENDIENTE' ELSE 'PARCIAL' END AS estado   "
                            + " FROM tbm_cabmovinv as a "
                            + " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipdoc=a.co_tipdoc and a2.co_doc=a.co_doc) "
                            + " INNER JOIN tbm_cabtipdoc AS a3 ON(a3.co_emp=a.co_emp AND a3.co_loc=a.co_loc AND a3.co_tipdoc=a.co_tipdoc ) "
                            + " INNER JOIN tbm_loc AS a4 ON(a4.co_emp=a.co_emp AND a4.co_loc=a.co_loc ) "
                            + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a2.co_bod) "
                            + " WHERE  ( a6.co_empGrp=0 AND a6.co_bodGrp=( " + intCodBodGrEgrIng + " ) )  and a.st_reg  not in ('I','E') and a.st_coninv in ('E','P') and a2.nd_can > 0 "
                            + " GROUP BY a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a4.tx_nom, a3.tx_descor, a.ne_numdoc, a.fe_doc,  a.st_coninv  "
                            + " ORDER BY  a.ne_numdoc   ";


                    ZafConItm_11 objLis = new ZafConItm_11(parent2, false, arlDat, objParSis, dskGen, "Listado Pendiente de Confirmación de Ingresos y Egresos", strEgr, strIng);
                    objLis.show();
                    sleep(10000000);

                }
            } catch (InterruptedException e) {
                System.out.println("Excepción: " + e.toString());
            }
        }
    }

    private class ZafThrLisCotPenRec extends Thread {

        public ZafThrLisCotPenRec() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    String strSQL;

                    sleep(20000);

                    strSQL = "SELECT  a.co_emp, a.co_loc, a.co_cot, a.fe_cot, a.co_cli, a1.tx_nom, a.nd_tot "
                            + " FROM tbm_cabcotven AS a "
                            + " INNER JOIN tbm_cli AS a1 ON(a1.co_emp=a.co_emp AND a1.co_cli=a.co_cli ) "
                            + " WHERE a.co_emp= " + intCodEmp + " AND a.co_loc=" + intCodLoc + " AND a.st_reg NOT IN ('F','I','E')  "
                            + " AND a.co_ven=" + objParSis.getCodigoUsuario() + " "
                            + " AND  " + objParSis.getFuncionFechaHoraBaseDatos() + " >= a.fe_procon ";


                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlDat.clear();
                    while (rst.next()) {

                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("co_emp"));
                        arlReg.add(1, rst.getString("co_loc"));
                        arlReg.add(2, rst.getString("co_cot"));
                        arlReg.add(3, rst.getString("fe_cot"));
                        arlReg.add(4, rst.getString("co_cli"));
                        arlReg.add(5, rst.getString("tx_nom"));
                        arlReg.add(6, rst.getString("nd_tot"));
                        arlDat.add(arlReg);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_05 obj1 = new ZafConItm_05(parent2, false, arlDat, objParSis, dskGen);
                        obj1.show();
                        intEst = 0;
                    }
                    sleep(10000000); //Dos Horas y ...
                    

                }
            } catch (InterruptedException e) 
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private void cargarListadoSolPenAut() {
        try {

            if (intEstAutPen == 0) {
                ZafThrLisSolPenAut objThrLisSolPenAut = new ZafThrLisSolPenAut();
                objThrLisSolPenAut.start();
                intEstAutPen = 1;
            }
        } catch (Exception e) {
        }
    }

    private class ZafThrLisSolPenAut extends Thread {

        public ZafThrLisSolPenAut() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strSQLAux = "";

                    String strTit = "Listado de solicitudes pendiente de autorización. ";
                    sleep(20000);

                    if (!(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())) {
                        strSQLAux = " a.co_emp= " + intCodEmp + " AND a.co_loc=" + intCodLoc + " AND ";
                    }

                    strSQL = "SELECT a.co_emp, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc FROM tbm_cabsoldevven AS a "
                            + " WHERE  " + strSQLAux + " "
                            + "  a.co_tipdoc=108  AND  a.st_reg='A'  AND a.st_aut='P' ";


                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    System.out.println("" + strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("co_emp"));
                        arlDatSol.add(1, rst.getString("co_loc"));
                        arlDatSol.add(2, rst.getString("fe_doc"));
                        arlDatSol.add(3, rst.getString("tx_nomusrsol"));
                        arlDatSol.add(4, rst.getString("ne_numdoc"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_06 obj1 = new ZafConItm_06(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }
                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private void cargarListadoSolPenRevSuj() {
        try {

            if (intEstSolRevSuj == 0) {
                ZafThrLisSolPenRevSuj objThrLisSolPenRevSuj = new ZafThrLisSolPenRevSuj();
                objThrLisSolPenRevSuj.start();
                intEstSolRevSuj = 1;
            }
        } catch (Exception e) {
        }
    }

    private class ZafThrLisSolPenRevSuj extends Thread {

        public ZafThrLisSolPenRevSuj() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de solicitudes en recepción de Mercaderia. ";
                    sleep(20000);

                    strSQL = "SELECT a.co_emp, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc FROM tbm_cabsoldevven AS a WHERE a.co_emp= " + intCodEmp + " AND a.co_loc=" + intCodLoc + " "
                            + " AND a.co_tipdoc=108  AND  a.st_reg='A'  AND a.st_aut='A' AND a.st_recMerDev='N' AND a.st_tipdev='C' and a.st_meraceingsis='N'  ";


                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    System.out.println("" + strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("co_emp"));
                        arlDatSol.add(1, rst.getString("co_loc"));
                        arlDatSol.add(2, rst.getString("fe_doc"));
                        arlDatSol.add(3, rst.getString("tx_nomusrsol"));
                        arlDatSol.add(4, rst.getString("ne_numdoc"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_06 obj1 = new ZafConItm_06(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private void cargarListadoSolPenRevTec() {
        try {
            if (intEstSolRevTec == 0) {
                ZafThrLisSolPenRevTec objThrLisSolPenRevTec = new ZafThrLisSolPenRevTec();
                objThrLisSolPenRevTec.start();
                intEstSolRevTec = 1;
            }
        } catch (Exception e) {
        }
    }

    private class ZafThrLisSolPenRevTec extends Thread {

        public ZafThrLisSolPenRevTec() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de solicitudes en revición técnica. ";
                    sleep(20000);


                    strSQL = "select a.co_emp, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc from tbm_cabsoldevven as a "
                            + " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " WHERE a.co_emp= " + intCodEmp + " AND a.co_loc=" + intCodLoc + " "
                            + " and a.co_tipdoc=108 and a.st_reg='A' "
                            + " and a.st_aut='A' and a.st_revTecMerDev='N' AND a.st_tipdev='C'  AND a.st_revtec='S' AND a1.st_revtec='S' AND a1.nd_canrec > 0 "
                            + " GROUP BY a.co_emp, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc ";

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("co_emp"));
                        arlDatSol.add(1, rst.getString("co_loc"));
                        arlDatSol.add(2, rst.getString("fe_doc"));
                        arlDatSol.add(3, rst.getString("tx_nomusrsol"));
                        arlDatSol.add(4, rst.getString("ne_numdoc"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_06 obj1 = new ZafConItm_06(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private void cargarListadoSolRevConfIngBod() {
        try {
            if (intEstSolRevConfIngBod == 0) {
                ZafThrLisSolRevConfIngBod objThrLisSolRevConfIngBod = new ZafThrLisSolRevConfIngBod();
                objThrLisSolRevConfIngBod.start();
                intEstSolRevConfIngBod = 1;
            }
        } catch (Exception e) {
        }
    }

    private class ZafThrLisSolRevConfIngBod extends Thread {

        public ZafThrLisSolRevConfIngBod() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "List. solici. en revición y confir. de Ing. Egre. Bodega. ";
                    sleep(20000);


                    strSQL = "select a.co_emp, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc from tbm_cabsoldevven as a "
                            + " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " WHERE a.co_emp= " + intCodEmp + " AND a.co_loc=" + intCodLoc + " "
                            + " AND  a.co_tipdoc=108 and a.st_reg='A' AND a.st_aut='A' "
                            + " AND a.st_RevBodMerDev='N' AND a.st_tipdev='C' "
                            + " AND ( ( a1.st_revtec='N' AND a1.nd_canrec > 0 ) "
                            + " OR  ( a1.st_revtec='S' AND a1.nd_canrevtecace > 0 ) "
                            + " OR  ( a1.st_revtec='S' AND a1.nd_canrevtecrec > 0 )  ) "
                            + " GROUP BY a.co_emp, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc ";

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("co_emp"));
                        arlDatSol.add(1, rst.getString("co_loc"));
                        arlDatSol.add(2, rst.getString("fe_doc"));
                        arlDatSol.add(3, rst.getString("tx_nomusrsol"));
                        arlDatSol.add(4, rst.getString("ne_numdoc"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_06 obj1 = new ZafConItm_06(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private void cargarListadoSolDevVenAut() {
        try {
            if (intEstSolDevVenAut == 0) {
                ZafThrLisSolDevVenAut objThrLisSolDevVenAut = new ZafThrLisSolDevVenAut();
                objThrLisSolDevVenAut.start();
                intEstSolDevVenAut = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoSolPenFac() {
        try {
            if (intEstSolPenFac == 0) {
                ZafThrLisSolPenFac objThrLisSolPenFac = new ZafThrLisSolPenFac();
                objThrLisSolPenFac.start();
                intEstSolPenFac = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoFacPenRec() {
        try {
            if (intEstFacPenRec == 0) {
                ZafThrLisFacPenRec objThrLisFacPenRec = new ZafThrLisFacPenRec();
                objThrLisFacPenRec.start();
                intEstFacPenRec = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoFacNoImp() {
        try {
            if (intEstFacPenRec == 0) {
                ZafThrLisFacNoImp objThrLisFacNoImp = new ZafThrLisFacNoImp();
                objThrLisFacNoImp.start();
                intEstFacPenRec = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoMerMalEst() {
        try {
            if (intEstMerMalEst == 0) {
                ZafThrLisMerMalEst objThrLisMerMalEst = new ZafThrLisMerMalEst();
                objThrLisMerMalEst.start();
                intEstMerMalEst = 1;
            }
        } catch (Exception e) {
        }
    }

    private void cargarListadoItmFalstkFis() {
        try {
            if (intEstItmFalstkFis == 0) {
                ZafThrLisItmFalstkFis objThrLisItmFalstkFis = new ZafThrLisItmFalstkFis();
                objThrLisItmFalstkFis.start();
                intEstItmFalstkFis = 1;
            }
        } catch (Exception e) {
        }
    }

    private class ZafThrLisItmFalstkFis extends Thread {

        public ZafThrLisItmFalstkFis() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de faltante Fisico de stock. ";
                    sleep(2000);


                    strSQL = "SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.tx_codalt, a.tx_nomitm, a1.tx_nom as local , a2.tx_nom as bodega "
                            + " FROM tbm_detsoldevven AS a "
                            + " inner join tbm_loc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc) "
                            + " inner join tbm_bod as a2 on (a2.co_emp = a.co_emp and a2.co_bod=a.co_bod ) "
                            + " where a.st_devfalstk='S'  and a.st_devfalstkace='S' and a.st_revFalStk='N'  ";
                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("co_emp"));
                        arlDatSol.add(1, rst.getString("co_loc"));
                        arlDatSol.add(2, rst.getString("co_tipdoc"));
                        arlDatSol.add(3, rst.getString("co_doc"));
                        arlDatSol.add(4, rst.getString("co_reg"));
                        arlDatSol.add(5, rst.getString("local"));
                        arlDatSol.add(6, rst.getString("tx_codalt"));
                        arlDatSol.add(7, rst.getString("tx_nomitm"));
                        arlDatSol.add(8, rst.getString("bodega"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_08 obj1 = new ZafConItm_08(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrLisSolDevVenAut extends Thread {

        public ZafThrLisSolDevVenAut() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de solicitudes Pendiente a Devolución. ";
                    sleep(2000);


                    strSQL = "select a.co_emp, a2.tx_nom, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc from tbm_cabsoldevven as a "
                            + " INNER JOIN  tbm_detsoldevven AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc AND a1.co_doc=a.co_doc ) "
                            + " inner join tbm_loc as a2 on (a2.co_emp=a.co_emp and a2.co_loc=a.co_loc) "
                            + " WHERE " + // a.co_emp= "+intCodEmp+" AND a.co_loc="+intCodLoc+"  and " +
                            " a.co_tipdoc=108 and a.st_reg='A' AND a.st_aut='A' "
                            + " AND  CASE WHEN a.st_tipDev  IN ('C') THEN   a.st_RevBodMerDev='S' AND a.st_meraceingsis='N' AND a1.nd_canRevBodAce > 0 "
                            + " ELSE a.st_meraceingsis='N' END "
                            + " GROUP BY a.co_emp, a2.tx_nom, a.co_loc, a.fe_doc, a.tx_nomusrsol, a.ne_numdoc ORDER BY a.co_Emp ";

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("co_emp"));
                        arlDatSol.add(1, rst.getString("tx_nom"));
                        arlDatSol.add(2, rst.getString("fe_doc"));
                        arlDatSol.add(3, rst.getString("tx_nomusrsol"));
                        arlDatSol.add(4, rst.getString("ne_numdoc"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_06 obj1 = new ZafConItm_06(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrLisSolPenFac extends Thread {

        public ZafThrLisSolPenFac() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de solicitudes Pendiente de Facturar. ";
                    sleep(2000);


                    strSQL = "SELECT a.co_loc, a.co_tipdoc, a.co_doc, a.co_cli, a.tx_nomcli, a1.ne_numdoc as numfac, a1.ne_numcot, a.ne_numdoc  "
                            + " FROM tbm_cabsoldevven AS a    "
                            + " INNER JOIN tbm_cabmovinv AS a1 ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel )  "
                            + " WHERE a.co_emp=" + intCodEmp + "   AND a.co_loc=" + intCodLoc + "   AND a.st_reg='A' AND a.st_aut='A' AND a.st_tipdev='C'  "
                            + "  AND a.st_meraceingsis='S' AND a.st_volfacmersindev='S' AND a.st_mersindevfac='N' ";

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("numfac"));
                        arlDatSol.add(1, rst.getString("ne_numdoc"));
                        arlDatSol.add(2, rst.getString("co_cli"));
                        arlDatSol.add(3, rst.getString("tx_nomcli"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_09 obj1 = new ZafConItm_09(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrLisFacNoImp extends Thread {

        public ZafThrLisFacNoImp() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de Facturas no impresas que tienen mas de 7 dias. ";
                    sleep(2000);

                    strSQL = "select  a1.tx_nom,  a.co_doc, a.ne_numcot ,a.fe_doc, a.co_cli, a.tx_nomcli  from tbm_cabmovinv as a "
                            + " inner join tbm_loc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc)  "
                            + " where  a.co_tipdoc=1 and a.st_imp='N' and a.ne_numdoc=0  and  a.st_reg not in ('I','E') and current_date >= (a.fe_doc+7) "
                            + " order by a1.tx_nom, a.tx_nomcli ";

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    arlSol.clear();
                    while (rst.next()) {
                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("tx_nom"));
                        arlDatSol.add(1, rst.getString("co_doc"));
                        arlDatSol.add(2, rst.getString("ne_numcot"));
                        arlDatSol.add(3, rst.getString("fe_doc"));
                        arlDatSol.add(4, rst.getString("co_cli"));
                        arlDatSol.add(5, rst.getString("tx_nomcli"));
                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_12 obj1 = new ZafConItm_12(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(8000000);

                }
            } catch (InterruptedException e) {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrLisMerMalEst extends Thread {

        public ZafThrLisMerMalEst() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de mercaderia confirmada en mal estado. ";
                    sleep(2000);

                    strSQL = "select a5.tx_nom as nombodegr,  a6.tx_descor, a3.ne_numdoc,  a.co_emp, "
                            + " x.tx_nom as nomboding, x.tx_codalt, x.tx_nomitm, x.nd_canmalest "
                            + " from (  "
                            + "  select a1.co_emp, a1.co_locrel, a1.co_tipdocrel, a1.co_docrel,  a1.co_regrel, a1.co_bod, a2.tx_nom, a1.co_itm, a3.tx_codalt, a3.tx_nomitm ,a1.nd_canmalest, "
                            + " a1.nd_canmalestaut, a1.nd_canmalestden  from tbm_cabingegrmerbod as a  "
                            + " inner join tbm_detingegrmerbod as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )  "
                            + " inner join tbm_bod as a2 on (a2.co_emp=a1.co_emp and a2.co_bod=a1.co_bod ) "
                            + " inner join tbm_inv as a3 on (a3.co_emp=a1.co_emp and a3.co_itm=a1.co_itm ) "
                            + " where   a.st_reg not in ('E','I') and  a1.nd_canmalest > 0  and  "
                            + " ( case when a1.nd_canmalestaut is null then 0 else a1.nd_canmalestaut end  + "
                            + "   case when a1.nd_canmalestden is null then 0 else a1.nd_canmalestden end  ) <= 0  "
                            + " ) as x "
                            + "  left join tbr_detmovinv as a on (a.co_emprel=x.co_emp and a.co_locrel=x.co_locrel and a.co_tipdocrel=x.co_tipdocrel and a.co_docrel=x.co_docrel and a.co_regrel=x.co_regrel ) "
                            + " left join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg ) "
                            + " left join tbm_detguirem as a2 on (a2.co_emprel=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc and a2.co_regrel=a1.co_reg ) "
                            + " left join tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) "
                            + " left join tbm_cabtipdoc as a6 on (a6.co_emp=a3.co_emp and a6.co_loc=a3.co_loc and a6.co_tipdoc=a3.co_tipdoc  ) "
                            + " left join tbr_bodempbodgrp as a4 on (a4.co_emp=a3.co_emp and a4.co_bod=a3.co_ptopar ) "
                            + " left join tbm_bod as a5 on (a5.co_emp=a4.co_empgrp  and a5.co_bod=a4.co_bodgrp ) ";



                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    arlSol.clear();
                    while (rst.next()) {
                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("nombodegr"));
                        arlDatSol.add(1, rst.getString("tx_descor"));
                        arlDatSol.add(2, rst.getString("ne_numdoc"));
                        arlDatSol.add(3, rst.getString("nomboding"));
                        arlDatSol.add(4, rst.getString("tx_codalt"));
                        arlDatSol.add(5, rst.getString("tx_nomitm"));
                        arlDatSol.add(6, rst.getString("nd_canmalest"));
                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_13 obj1 = new ZafConItm_13(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10800000);  // cada 3 horas se ejecuta


                }
            } catch (InterruptedException e) {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrLisFacPenRec extends Thread {

        public ZafThrLisFacPenRec() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    ArrayList arlSol = new ArrayList();
                    ArrayList arlDatSol;
                    String strSQL;
                    String strTit = "Listado de Facturas Pendiente de Recibir. ";
                    sleep(2000);


                    strSQL = "select ne_numdoc, fe_doc, co_cli, tx_nomcli  from "
                            + " tbm_cabmovinv where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=1 and  ne_numvecrecdoc = 0  "
                            + " and  " + objParSis.getFuncionFechaHoraBaseDatos() + " > (fe_doc+7)  and st_reg not in ('I','E')  order by fe_doc  ";
                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlSol.clear();
                    while (rst.next()) {

                        arlDatSol = new ArrayList();
                        arlDatSol.add(0, rst.getString("ne_numdoc"));
                        arlDatSol.add(1, rst.getString("fe_doc"));
                        arlDatSol.add(2, rst.getString("co_cli"));
                        arlDatSol.add(3, rst.getString("tx_nomcli"));

                        arlSol.add(arlDatSol);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;
                    if (intEst == 1) {
                        ZafConItm_10 obj1 = new ZafConItm_10(parent2, false, arlSol, objParSis, dskGen, strTit);
                        obj1.show();
                        intEst = 0;
                    }

                    arlSol = null;
                    arlDatSol = null;

                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrConItm extends Thread {

        private static final float KB = 1024.0f;
        private static final float MB = 1048576.0f;
        private static final String SKB = " KB";
        private static final String SMB = " MB";
        private static final String SLASH = " / ";
        private float heap = Runtime.getRuntime().freeMemory();
        private float total = Runtime.getRuntime().totalMemory();
        private float divisor = KB, remain = KB;
        private int itotal = 0, iremain = 0;
        private String sufix = null, sremain = null, stotal = null;

        public ZafThrConItm() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    String strSQL;


//            strSQL="SELECT * FROM (" +
//            " select a.co_itm, sum(b.nd_stkact) , a.tx_codalt, a. tx_nomitm ,a.nd_prevta1 from tbm_inv  as a " +
//            " inner join tbm_invbod as b on (b.co_emp=a.co_emp and b.co_itm=a.co_itm) " +
//            " where a.co_emp=0 and  upper(a.tx_codalt) like '%"+TERMINAL+"'  group by a.co_itm, a.tx_codalt, a. tx_nomitm,a.nd_prevta1 " +
//            " ) as x where x.sum>0  AND ( x.nd_prevta1=0  or x.nd_prevta1 is null )";
//

                    strSQL = "SELECT * FROM ( "
                            + " select a.co_itmmae,  sum(a2.nd_stkact) , a1.tx_codalt, a1.tx_nomitm , a1.nd_prevta1  from tbm_equinv as a "
                            + " inner join tbm_inv as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm) "
                            + " inner join tbm_invbod as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) "
                            + " inner join tbr_bodEmpBodGrp as a3 on (a3.co_emp=a2.co_emp and a3.co_bod=a2.co_bod) "
                            + " where a.co_emp <> 0  and  upper(a1.tx_codalt) like '%" + TERMINAL + "'   and a3.co_bodgrp != 12 " + // 12 bodega faltantes
                            "  group by a.co_itmmae,  a1.tx_codalt, a1.tx_nomitm, a1.nd_prevta1 "
                            + "  ) as x where x.sum>0  AND ( x.nd_prevta1=0  or x.nd_prevta1 is null ) ";




                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);
                    arlDat.clear();
                    while (rst.next()) {

                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("tx_codalt"));
                        arlReg.add(1, rst.getString("tx_nomitm"));
                        arlReg.add(2, rst.getString("co_itmmae"));

                        arlDat.add(arlReg);
                        intEst = 1;

                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_02 obj1 = new ZafConItm_02(parent2, true, arlDat, objParSis);
                        obj1.show();
                        intEst = 0;
                    }
                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrConFacPenImp extends Thread {

        public ZafThrConFacPenImp() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    String strSQL;

                    if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                        strSQL = " select a.co_emp, a.co_loc,   b2.tx_nom,  a.fe_doc, a.ne_numcot, b.tx_nom as usr  from tbm_cabmovinv as a  "
                                + " left join tbm_usr as b on (b.co_usr=a.co_com) "
                                + " left join tbm_loc as b2 on (b2.co_emp=a.co_emp and  b2.co_loc=a.co_loc) "
                                + " where " + strConBusFacPenImp + "  a.co_tipdoc=1 and  a.ne_numdoc = 0 and a.st_reg not in('I','E') and  current_date >= (a.fe_Doc+" + intDiaBusFacPenImp + ") ";
                    } else {
                        strSQL = " select a.co_emp, a.co_loc,  b2.tx_nom,  a.fe_doc, a.ne_numcot, b.tx_nom as usr from tbm_cabmovinv as a  "
                                + " left join tbm_usr as b on (b.co_usr=a.co_com) "
                                + " left join tbm_loc as b2 on (b2.co_emp=a.co_emp and  b2.co_loc=a.co_loc) "
                                + " where a.co_emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + " and a.co_tipdoc=1 and  a.ne_numdoc = 0  and a.st_reg not in('I','E') and  current_date >= (a.fe_Doc+" + intDiaBusFacPenImp + ") ";
                    }

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlDat.clear();
                    while (rst.next()) {

                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("tx_nom"));
                        arlReg.add(1, rst.getString("fe_doc"));
                        arlReg.add(2, rst.getString("ne_numcot"));
                        arlReg.add(3, rst.getString("usr"));
                        arlReg.add(4, rst.getString("co_emp"));
                        arlReg.add(5, rst.getString("co_loc"));
                        arlDat.add(arlReg);
                        intEst = 1;

                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_03 obj1 = new ZafConItm_03(parent2, false, arlDat, objParSis, dskGen);
                        obj1.show();
                        intEst = 0;
                    }
                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrConFacVenCon extends Thread 
    { 
        public ZafThrConFacVenCon() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    String strSQL;

                    sleep(20000);


                    strSQL = "select b.ne_numdoc , b.fe_doc, cli.tx_nom,  abs(a.mo_pag+a.nd_abo) as valpen, b.st_coninv from tbm_pagmovinv as a  "
                            + " inner join tbm_cabmovinv as b on (b.co_emp=a.co_emp and b.co_loc=a.co_loc and b.co_tipdoc=a.co_tipdoc and b.co_doc=a.co_doc) "
                            + " inner join tbm_cabtipdoc as b1 on (b1.co_emp=b.co_emp and b1.co_loc=b.co_loc and b1.co_tipdoc=b.co_tipdoc ) "
                            + " inner join tbm_cli as cli on (cli.co_emp=b.co_emp and cli.co_cli=b.co_cli) "
                            + " where b.st_reg not in ('I','E') and  a.st_reg in ('A','C') AND b.st_imp in ('S')  "
                            + " and  a.co_Emp=" + intCodEmp + " and a.co_loc=" + intCodLoc + " "
                            + " and b1.ne_mod=1 "
                            + " and cli.ne_diagra=0 "
                            + " and ( a.ne_diacre=0 or a.ne_diacre is null ) "
                            + " and ( a.nd_porret=0 or a.nd_porret is null ) "
                            + " and (a.mo_pag+a.nd_abo) < 0  order by b.ne_numdoc ";
                    System.out.println("> " + strSQL);

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlDat.clear();
                    while (rst.next()) {

                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("st_coninv"));
                        arlReg.add(1, rst.getString("ne_numdoc"));
                        arlReg.add(2, rst.getString("fe_doc"));
                        arlReg.add(3, rst.getString("tx_nom"));
                        arlReg.add(4, rst.getString("valpen"));
                        arlDat.add(arlReg);
                        intEst = 1;

                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_04 obj1 = new ZafConItm_04(parent2, false, arlDat, objParSis, dskGen);
                        obj1.show();
                        intEst = 0;
                    }
                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    private class ZafThrConItmPenConInv extends Thread  //Rose
    { 

        public ZafThrConItmPenConInv() 
        {
        }

        @Override
        public void run()
        {
            try
            {
                while (true) 
                {
                    int intEst = 0;
                    String strSQL;

                    sleep(20000);

                    strSQL  = " SELECT a.co_bod, b.tx_nom as bodega, a.co_itm, c.tx_CodAlt , c.tx_CodAlt2, c.tx_nomItm, a3.tx_ubi, a3.st_impOrd, a.co_usrResCon, d.tx_usr ";
                    strSQL += " FROM tbm_ConInv as a ";
                    strSQL += " INNER JOIN tbm_usr as d ON (a.co_usrResCon=d.co_usr) ";
                    strSQL += " INNER JOIN tbm_bod as b ON (a.co_Emp=b.co_emp and a.co_bod=b.co_bod) ";
                    strSQL += " INNER JOIN tbm_inv as c ON (b.co_Emp=c.co_Emp and a.co_itm=c.co_itm) ";
                    strSQL += " INNER JOIN tbm_equInv AS a1 ON (c.co_emp=a1.co_emp AND c.co_itm=a1.co_itm) ";
                    strSQL += " LEFT OUTER JOIN ";
                    strSQL += " ( ";
                    strSQL += "   SELECT DISTINCT  b3.co_bodgrp, b1.co_itmMae, b2.tx_ubi, b2.st_impOrd ";
                    strSQL += "   FROM tbm_equInv as b1 ";
                    strSQL += "   INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) ";
                    strSQL += "   INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod) ";
                    strSQL += "   WHERE b3.co_empgrp="+objParSis.getCodigoEmpresaGrupo();
                    strSQL += " ) as a3 ON (a1.co_itmMae=a3.co_itmMae and a.co_bod=a3.co_bodGrp) ";
                    strSQL += " WHERE a.fe_ReaCon is  null AND a.st_ConRea='N' AND a.co_usrrescon="+objParSis.getCodigoUsuario();
                    strSQL += " GROUP BY a.co_bod, b.tx_nom, a.co_itm, c.tx_CodAlt , c.tx_CodAlt2, c.tx_nomItm, a3.tx_ubi, a3.st_impOrd, a.co_usrResCon, d.tx_usr ";
                    strSQL += " ORDER BY a.co_bod, c.tx_CodAlt ";
                    
                    System.out.println("ZafThrConItmPenConInv: " + strSQL);

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlDat.clear();
                    while (rst.next()) 
                    {
                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("co_bod"));
                        arlReg.add(1, rst.getString("bodega"));
                        arlReg.add(2, rst.getString("co_itm"));
                        arlReg.add(3, rst.getString("tx_CodAlt"));
                        arlReg.add(4, rst.getString("tx_CodAlt2"));
                        arlReg.add(5, rst.getString("tx_nomItm"));
                        arlReg.add(6, rst.getString("tx_ubi"));
                        arlReg.add(7, rst.getString("co_usrResCon"));
                        arlReg.add(8, rst.getString("tx_usr"));
                        arlDat.add(arlReg);
                        intEst = 1;
                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1)
                    {
                        ZafConItm_16 obj1 = new ZafConItm_16(parent2, false, arlDat, objParSis, dskGen);
                        obj1.show();
                        intEst = 0;
                    }
                    sleep(10000000); //Dos Horas 
                }
            } catch (InterruptedException e) 
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }
    
    /*Dario para Confirmacion de Depositos*/
    private class ZafThrConDep extends Thread {

        public ZafThrConDep() {
        }

        @Override
        public void run() {
            try {
                while (true) {

                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intEst = 0;
                    String strSQL;

                    ///sleep( 20000 );

                    /*
                     ---2.-Query para mostrarDatDep()--ZafCon52---INSERTAR---:  
                     SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, 
                     a2.fe_doc,  a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, 
                     a3.ne_mod, a2.st_condepban
                     FROM tbm_detpag as a1  
                     INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)  
                     INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc)  
                     INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta)  
                     LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc)  
                     WHERE a1.co_emp=1 AND a1.co_loc= 4 
                     AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I') AND a1.co_tipdoc NOT IN (107)  
                     AND a2.st_condepban NOT IN ('S') AND a2.fe_doc < current_date AND a4.st_ctaban IN ('S') AND a3.st_utiCtaTra IN ('S')
                     ---AND a2.fe_doc = '2009/03/19' 
                     AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0  
                     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar,  
                     a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban
                     ORDER BY a1.co_tipdoc, a1.co_doc, a2.fe_doc
                     */

                    strSQL = " SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, sum(a1.nd_abo) as mondoc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc,  "
                            + " a3.tx_descor, a3.tx_deslar, a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban "
                            + " FROM tbm_detpag as a1  "
                            + " INNER JOIN tbm_cabpag as a2 ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc) "
                            + " INNER JOIN tbm_cabtipdoc as a3 ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoccon=a3.co_tipdoc) "
                            + " INNER JOIN tbm_placta as a4 ON (a3.co_emp=a4.co_emp and a3.co_ctadeb=a4.co_cta) "
                            + " LEFT OUTER JOIN tbm_cabtipdoc as a5 ON (a1.co_emp=a5.co_emp and a1.co_loc=a5.co_loc and a1.co_tipdoccon=a5.co_tipdoc) "
                            + " WHERE a1.co_emp=" + intCodEmp + " AND a1.co_loc=" + intCodLoc + " "
                            + " AND a2.st_reg NOT IN ('E','I') AND a3.tx_natdoc IN ('I') AND a1.co_tipdoc NOT IN (107) "
                            + " AND a2.st_condepban IN ('N') AND a2.fe_doc < current_date AND a4.st_ctaban IN ('S') "
                            + " AND a4.tx_tipcta='D' AND a3.co_ctadeb > 0 AND a3.st_utiCtaTra IN ('S')"
                            + " GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_tipdoccon, a2.ne_numdoc1, a2.fe_doc, a3.tx_descor, a3.tx_deslar, "
                            + " a3.co_ctadeb, a3.co_ctahab, a3.co_ctadebtra, a3.co_ctahabtra, a4.st_ctaban, a3.ne_mod, a2.st_condepban "
                            + " ORDER BY a1.co_tipdoc, a2.fe_doc, a2.ne_numdoc1 ";

                    //System.out.println("---strSQL---ZafThrConDep()---: " + strSQL);

                    java.sql.Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    java.sql.Statement stm = conn.createStatement();
                    java.sql.ResultSet rst = stm.executeQuery(strSQL);

                    arlDat.clear();
                    while (rst.next()) {

                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("ne_numdoc1"));
                        arlReg.add(1, rst.getString("fe_doc"));
                        arlReg.add(2, rst.getString("tx_deslar"));
                        arlReg.add(3, rst.getString("mondoc"));
                        arlDat.add(arlReg);
                        intEst = 1;

                    }
                    rst.close();
                    stm.close();
                    conn.close();
                    rst = null;
                    stm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_07 obj7 = new ZafConItm_07(parent2, false, arlDat, objParSis, dskGen);
                        obj7.show();
                        intEst = 0;
                    }
                    sleep(10000000);

                }
            } catch (InterruptedException e) //InterruptedException
            {
                System.out.println("Excepción: " + e.toString());
            } catch (SQLException Evt) {
            }
        }
    }

    /**
     * Esta clase crea un hilo que muestra el consumo de memoria en un
     * JProgressBar.
     */
    private class ZafThrConMem extends Thread {

        private static final float KB = 1024.0f;
        private static final float MB = 1048576.0f;
        private static final String SKB = " KB";
        private static final String SMB = " MB";
        private static final String SLASH = " / ";
        private float heap = Runtime.getRuntime().freeMemory();
        private float total = Runtime.getRuntime().totalMemory();
        private float divisor = KB, remain = KB;
        private int itotal = 0, iremain = 0;
        private String sufix = null, sremain = null, stotal = null;

        public ZafThrConMem() {
        }

        @Override
        public void run() {
            try {
                while (true) {
                    heap = Runtime.getRuntime().freeMemory();
                    total = Runtime.getRuntime().totalMemory();

                    divisor = (total < MB) ? KB : MB;
                    sufix = (total < MB) ? SKB : SMB;

                    heap = heap / divisor;
                    total = total / divisor;
                    remain = total - heap;

                    itotal = Math.round(total * 1000);
                    iremain = Math.round(remain * 1000);

                    sremain = String.valueOf(remain);
                    stotal = String.valueOf(total);

                    pgrConMem.setMaximum(itotal);
                    pgrConMem.setValue(iremain);
                    pgrConMem.setString(new StringBuffer(sremain.substring(0, sremain.indexOf('.') + 2)).append(SLASH).
                            append(stotal.substring(0, stotal.indexOf('.') + 2)).append(sufix).toString());

                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("Excepción: " + e.toString());
            }
        }
    }

    private class ZafThrConOrdDesFalDan extends Thread {

        ArrayList arlDat = null, arlReg = null;
        private String strLocal;
        int intInd = 0;

        public ZafThrConOrdDesFalDan() {
            arlDat = new ArrayList();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int intCodEmp = objParSis.getCodigoEmpresa();
                    int intCodLoc = objParSis.getCodigoLocal();
                    int intCodUsr = objParSis.getCodigoUsuario();
                    int intEst = 0;
                    String strSQL, strAux = " ";

                    if ((intCodUsr == 7) || (intCodUsr == 8) || (intCodUsr == 123) || (intCodUsr == 144) // ventas de tuval 
                            || (intCodUsr == 17) || (intCodUsr == 33) || (intCodUsr == 92) || (intCodUsr == 88) // ventas de dimulti
                            || (intCodUsr == 41) || (intCodUsr == 27) || (intCodUsr == 149) // ventas de quito  
                            || (intCodUsr == 58) || (intCodUsr == 85) // ventas de manta 
                            || (intCodUsr == 155) // ventas de santo domingo
                            ) {
                        strAux = " and a4.co_com = " + intCodUsr + " ";
                    }

                    strSQL = " select * "
                            + " from ( "
                            + " select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor, a1.tx_deslar, a.fe_doc, a.tx_nomclides, "
                            + " case when a.ne_numorddes is null then 0 else a.ne_numorddes end as numorddes, a.tx_dirclides, "
                            + " case when a.st_coninv = 'P' then 'PENDIENTE' else case when a.st_coninv = 'E' then 'PARCIAL' else case when a.st_coninv = 'C' then 'COMPLETA' end end end as st_coninv, "
                            + " case when a.st_orddesfal is null or a.st_orddesfal = 'N' then 'N' else 'S' end as faltantes, "
                            + " case when a.st_orddesdan is null or a.st_orddesdan = 'N' then 'N' else 'S' end as daniados, "
                            + " a7.tx_nom as nomloc, "
                            + " case when a.co_clides in (5, 3516, 603, 1039, 3117, 498, 886, 2854, 446, 789) then 'N' else 'S' end as esCliente, "
                            + " a4.ne_numdoc as numFac "
                            + " from tbm_cabguirem as a "
                            + " inner join tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
                            + " inner join tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc ) "
                            //+ " left join tbm_var as a2 on (a2.co_reg=a.co_forret ) "
                            + " inner join tbm_detguirem as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc) "
                            + " left join tbm_loc as a7 on (a7.co_emp=a.co_emp and a7.co_loc=a.co_loc) "
                            + " inner join tbm_cabmovinv as a4 on (a3.co_emprel=a4.co_emp and a3.co_locrel=a4.co_loc and a3.co_tipdocrel=a4.co_tipdoc and a3.co_docrel=a4.co_doc) "
                            + " where a.st_reg='A' "
                            //+ " and a.st_coninv in ('P','E') "
                            + " and (a.st_orddesfal = 'S' or a.st_orddesdan = 'S') "
                            + " and a.co_emp=" + intCodEmp + " "
                            + " and a.co_loc=" + intCodLoc + " "
                            + strAux
                            + " ) as z "
                            + " where numorddes > 0 "
                            + " group by co_emp, co_loc, co_tipdoc, co_doc, tx_descor, tx_deslar, fe_doc, tx_nomclides, numorddes, tx_dirclides, st_coninv, faltantes, daniados, nomloc, esCliente, numFac "
                            + " order by numorddes ";

                    System.out.println("ZafConItm.ZafThrConOrdDesFalDan.run: " + strSQL);

                    Connection conn = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    PreparedStatement pstm = conn.prepareStatement(strSQL);
                    ResultSet rst = pstm.executeQuery();

                    arlDat.clear();
                    while (rst.next()) {
                        strLocal = rst.getString("nomloc");
                        strLocal = strLocal.substring(0, strLocal.indexOf("(")).replace('(', ' ').trim();

                        arlReg = new ArrayList();
                        arlReg.add(0, rst.getString("co_emp"));
                        arlReg.add(1, strLocal);
                        arlReg.add(2, rst.getString("co_tipdoc"));
                        arlReg.add(3, rst.getString("co_doc"));
                        arlReg.add(4, rst.getString("tx_descor"));
                        arlReg.add(5, rst.getString("tx_deslar"));
                        arlReg.add(6, rst.getString("fe_doc"));
                        arlReg.add(7, rst.getString("tx_nomclides"));
                        arlReg.add(8, rst.getString("numorddes"));
                        arlReg.add(9, rst.getString("numFac"));
                        arlReg.add(10, rst.getString("st_coninv"));
                        arlReg.add(11, rst.getString("faltantes"));
                        arlReg.add(12, rst.getString("daniados"));
                        arlDat.add(arlReg);
                        intEst = 1;
                    }

                    if (rst != null) {
                        rst.close();
                    }
                    if (pstm != null) {
                        pstm.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                    rst = null;
                    pstm = null;
                    conn = null;

                    if (intEst == 1) {
                        ZafConItm_15 obj1 = new ZafConItm_15(parent2, false, arlDat, objParSis, dskGen);
                        obj1.setVisible(true);
                        intEst = 0;
                        obj1 = null;
                    }
                    sleep(10000000);
                }
            } catch (InterruptedException e) {
                ;
            } catch (SQLException e) {
                System.out.println("ZafConItm.ZafThrConOrdDesFalDan.run : " + e);
            } catch (Exception e) {
                System.out.println("ZafConItm.ZafThrConOrdDesFalDan.run : " + e);
            }
        }
    }

    private void iniciarAlertaFaltantes() {
        try {
            if ((intCodUsr == 7) || (intCodUsr == 8) || (intCodUsr == 123) || (intCodUsr == 144) // ventas de tuval 
                    || (intCodUsr == 17) || (intCodUsr == 33) || (intCodUsr == 92) || (intCodUsr == 88) // ventas de dimulti
                    || (intCodUsr == 41) || (intCodUsr == 27) || (intCodUsr == 149) // ventas de quito  
                    || (intCodUsr == 58) || (intCodUsr == 85) // ventas de manta 
                    || (intCodUsr == 155) // ventas de santo domingo
                    || (intCodUsr == 148) // inventarista
                    ) {
                srvSck = new ServerSocket(intPuerto);
                objThrArr = new ZafThreadAlertaFaltantes();
                objThrArr.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ZafThreadAlertaFaltantes extends Thread {

        private String StrMensRec;

        public ZafThreadAlertaFaltantes() {
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sck = srvSck.accept();
                    DataInputStream dis = new DataInputStream(sck.getInputStream());

                    StrMensRec = dis.readUTF();

                    if (!StrMensRec.equals("")) {
                        if (Integer.parseInt(StrMensRec) == intCodUsr) {
                            cargarOrdDesFalDan();
                        }
                    }
                    objThrArr = null;
                    dis.close();
                    sck.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
