/*
 * ZafTblPopMnu.java
 *
 * Created on 24 de julio de 2005, 12:35 AM
 * v1
 */

package Librerias.ZafTblUti.ZafTblPopMnuFluEfe;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafParSis.ZafParSis;
import java.sql.DriverManager;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;

/**
 * Esta clase crea un menú de contexto para un JTable. Este menú permite realizar
 * muchas operaciones como por ejemplo: copiar, seleccionar fila, insertar filas,
 * indicar el tipo de selección, etc.
 * @author  Eddye Lino
 */
public class ZafTblPopMnuFluEfe extends javax.swing.JPopupMenu
{
    private javax.swing.JTable tblDatOri, tblDatDes;
    private ZafTblMod objTblModOri, objTblModDes;
    private javax.swing.JMenuItem mnuCor, mnuCop, mnuPeg;
    private javax.swing.JMenuItem mnuSelFil, mnuSelCol, mnuSelTod;
    private javax.swing.JMenuItem mnuInsFil, mnuEliFil, mnuBorCon;
    private javax.swing.JMenuItem mnuOcuCol;
    private javax.swing.JMenu mnuMosCol;
    private javax.swing.JMenuItem mnuMosTodCol;
    private javax.swing.JMenuItem mnuAjuAncCol;
    private javax.swing.JMenu mnuTipSel;
    private javax.swing.JMenuItem mnuTipSelCel, mnuTipSelFil, mnuTipSelCol;
    
    private javax.swing.JMenuItem mnuAgrGrpIng;//GRUPO
    private javax.swing.JMenuItem mnuAgrSubGrpIng;//SUBGRUPO
    private javax.swing.JMenuItem mnuAgrSubSubGrpIngLSC, mnuAgrSubSubGrpIngPmoPch, mnuAgrSubSubGrpIngRteFte, mnuAgrSubSubGrpIngFdoInv;//SUB-SUBGRUPO
    
    private javax.swing.JMenuItem mnuAgrGrpEgr;//GRUPO
    private javax.swing.JMenuItem mnuAgrSubGrpImpLoc;//SUBGRUPO
    private javax.swing.JMenuItem mnuAgrSubSubGrpPrvExt, mnuAgrSubSubGrpArnGtoImp, mnuAgrSubSubGrpComLoc;//SUB-SUBGRUPO
    
    private javax.swing.JMenuItem mnuAgrSubGrpImp; //SUBGRUPO
    private javax.swing.JMenuItem mnuAgrSubSubGrpIvaPgdImp, mnuAgrSubSubGrpIvaPgdLoc, mnuAgrSubSubGrpIvaPgdDec, mnuAgrSubSubGrpRetFte; //SUB-SUBGRUPO
    
    private javax.swing.JMenuItem mnuAgrSubGrpGtoGrl;//SUBGRUPO
    private javax.swing.JMenuItem mnuAgrSubSubGrpCosPer, mnuAgrSubSubGrpGtoGrl;//SUB-SUBGRUPO
    
    private javax.swing.JMenuItem mnuAgrSubGrpGtoFin;//SUB-GRUPO
    private javax.swing.JMenuItem mnuAgrSubSubGrpIntPrvExt, mnuAgrSubSubGrpDivBcoPch, mnuAgrSubSubGrpIntBcoPch;//SUB-SUBGRUPO
    
    private javax.swing.JMenuItem mnuAgrSubGrpNotOpe, mnuAgrSubGrpActFij;
        
    private int intColPeg, intFilPeg;   
    private int intCodSubGrp, intCodGrp, intColCodSubGrp, intColCodGrp, intColCodSubGrpBlk;
    
    private ZafParSis objParSis;
    private String strSQL;
    private ZafUtil objUti;
    
    private Vector vecReg, vecDat;  
    
    
    
    private String strNomSubGrpMay;
    private String strNomSubGrpMin;
    private int intVarCodSubGrp;

    
    
    
    /** Crea una nueva instancia de la clase ZafTblPopMnu. */
//    public ZafTblPopMnuFluEfe(javax.swing.JTable tablaOri, javax.swing.JTable tablaDes, int posicionPegado, int columnaGrupo, int columnaSubGrupo, String codGrp, String codSubGrp)
    public ZafTblPopMnuFluEfe(javax.swing.JTable tablaOri, javax.swing.JTable tablaDes, int columnaPegado, ZafParSis obj, int columnaCodigoSubGrupo, int columnaCodigoGrupo, int columnaCodigoSubGrpBloque)
    {
        tblDatOri=tablaOri;
        tblDatDes=tablaDes;
        objTblModOri=(ZafTblMod)tblDatOri.getModel();
        objTblModDes=(ZafTblMod)tblDatDes.getModel();
        intColPeg=columnaPegado;
        this.objParSis=obj;
        intColCodSubGrp=columnaCodigoSubGrupo;
        intColCodGrp=columnaCodigoGrupo;
        intColCodSubGrpBlk=columnaCodigoSubGrpBloque;       
        
        //Crear los menúes.
        mnuCor=new javax.swing.JMenuItem("Cortar");
        mnuCor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnuCor.setEnabled(false);
        mnuCop=new javax.swing.JMenuItem("Copiar");
        mnuCop.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuPeg=new javax.swing.JMenuItem("Pegar");
        mnuPeg.setEnabled(false);
        mnuPeg.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        
        mnuAgrGrpIng=new javax.swing.JMenu("Envia a Ingresos");
        
        mnuAgrSubGrpIng=new javax.swing.JMenu("Ingresos"); 
        mnuAgrSubSubGrpIngLSC=new javax.swing.JMenuItem("LSC-Efectivo");         
        mnuAgrSubSubGrpIngPmoPch=new javax.swing.JMenuItem("Ptmo. Pichincha"); 
        mnuAgrSubSubGrpIngRteFte=new javax.swing.JMenuItem("Retención Fuente");
        mnuAgrSubSubGrpIngFdoInv=new javax.swing.JMenuItem("Fondo Inversión");
        
        mnuAgrGrpEgr=new javax.swing.JMenu("Enviar a Egresos");
        
        mnuAgrSubGrpImpLoc=new javax.swing.JMenu("Importación y Local");
        mnuAgrSubSubGrpPrvExt=new javax.swing.JMenuItem("Proveedores Exterior");
        mnuAgrSubSubGrpArnGtoImp=new javax.swing.JMenuItem("Aranceles Gastos Importación");
        mnuAgrSubSubGrpComLoc=new javax.swing.JMenuItem("Compras Locales");
                        
        mnuAgrSubGrpImp=new javax.swing.JMenu("Impuestos");
        mnuAgrSubSubGrpIvaPgdImp=new javax.swing.JMenuItem("Iva Pagado Importación");
        mnuAgrSubSubGrpIvaPgdLoc=new javax.swing.JMenuItem("Iva Pagado Local");
        mnuAgrSubSubGrpIvaPgdDec=new javax.swing.JMenuItem("Iva Pagado Declaración");
        mnuAgrSubSubGrpRetFte=new javax.swing.JMenuItem("Retención Fuente");
        
        mnuAgrSubGrpGtoGrl=new javax.swing.JMenu("Gastos Generales");
        mnuAgrSubSubGrpCosPer=new javax.swing.JMenuItem("Costo Personal");
        mnuAgrSubSubGrpGtoGrl=new javax.swing.JMenuItem("Gastos Generales");
                               
        mnuAgrSubGrpGtoFin=new javax.swing.JMenu("Gastos Financieros");
        mnuAgrSubSubGrpIntPrvExt=new javax.swing.JMenuItem("Int. Proveedores Exterior");
        mnuAgrSubSubGrpDivBcoPch=new javax.swing.JMenuItem("Dividendo Bco. Pichincha");
        mnuAgrSubSubGrpIntBcoPch=new javax.swing.JMenuItem("Int. Bco Pichincha");
        
        mnuAgrSubGrpNotOpe=new javax.swing.JMenuItem("No Operativos");
        mnuAgrSubGrpActFij=new javax.swing.JMenuItem("Activo Fijo");
        
        mnuSelFil=new javax.swing.JMenuItem("Seleccionar fila");
        mnuSelCol=new javax.swing.JMenuItem("Seleccionar columna");
        mnuSelTod=new javax.swing.JMenuItem("Seleccionar todo");
        mnuInsFil=new javax.swing.JMenuItem("Insertar fila");
        mnuEliFil=new javax.swing.JMenuItem("Eliminar fila");
        //mnuEliFil.setEnabled(false);
        mnuBorCon=new javax.swing.JMenuItem("Borrar contenido");
        mnuBorCon.setEnabled(false);
        mnuOcuCol=new javax.swing.JMenuItem("Ocultar columna");
        mnuMosCol=new javax.swing.JMenu("Mostrar columna");
        mnuMosTodCol=new javax.swing.JMenuItem("Todas las columnas");
        mnuAjuAncCol=new javax.swing.JMenuItem("Ajustar ancho de columnas automáticamente");
        mnuTipSel=new javax.swing.JMenu("Tipo de selección");
        mnuTipSelCel=new javax.swing.JMenuItem("Selección por celda");
        mnuTipSelFil=new javax.swing.JMenuItem("Selección por fila");
        mnuTipSelCol=new javax.swing.JMenuItem("Selección por columna");
        //Adicionar los menúes.
        this.add(mnuCor);
        this.add(mnuCop);
        this.add(mnuPeg);
        this.addSeparator();
        
        
        
        this.add(mnuAgrGrpIng);
        mnuAgrGrpIng.add(mnuAgrSubGrpIng);
        mnuAgrSubGrpIng.add(mnuAgrSubSubGrpIngLSC);
        mnuAgrSubGrpIng.add(mnuAgrSubSubGrpIngPmoPch);
        mnuAgrSubGrpIng.add(mnuAgrSubSubGrpIngRteFte);
        mnuAgrSubGrpIng.add(mnuAgrSubSubGrpIngFdoInv);
        
        this.add(mnuAgrGrpEgr);
        
        mnuAgrGrpEgr.add(mnuAgrSubGrpImpLoc);
        mnuAgrSubGrpImpLoc.add(mnuAgrSubSubGrpPrvExt);
        mnuAgrSubGrpImpLoc.add(mnuAgrSubSubGrpArnGtoImp);
        mnuAgrSubGrpImpLoc.add(mnuAgrSubSubGrpComLoc);
        
        mnuAgrGrpEgr.add(mnuAgrSubGrpImp);
        mnuAgrSubGrpImp.add(mnuAgrSubSubGrpIvaPgdImp);
        mnuAgrSubGrpImp.add(mnuAgrSubSubGrpIvaPgdLoc);
        mnuAgrSubGrpImp.add(mnuAgrSubSubGrpIvaPgdDec);
        mnuAgrSubGrpImp.add(mnuAgrSubSubGrpRetFte);
        
        mnuAgrGrpEgr.add(mnuAgrSubGrpGtoGrl);
        mnuAgrSubGrpGtoGrl.add(mnuAgrSubSubGrpCosPer);
        mnuAgrSubGrpGtoGrl.add(mnuAgrSubSubGrpGtoGrl);
        
        mnuAgrGrpEgr.add(mnuAgrSubGrpGtoFin);
        mnuAgrSubGrpGtoFin.add(mnuAgrSubSubGrpIntPrvExt);
        mnuAgrSubGrpGtoFin.add(mnuAgrSubSubGrpDivBcoPch);
        mnuAgrSubGrpGtoFin.add(mnuAgrSubSubGrpIntBcoPch);

        mnuAgrGrpEgr.add(mnuAgrSubGrpNotOpe);
        mnuAgrGrpEgr.add(mnuAgrSubGrpActFij);                
        
        
        this.addSeparator();
        this.add(mnuSelFil);
        this.add(mnuSelCol);
        this.add(mnuSelTod);
        this.addSeparator();
        this.add(mnuInsFil);
        this.add(mnuEliFil);
        this.add(mnuBorCon);
        this.addSeparator();
        this.add(mnuOcuCol);
        mnuMosCol.add(mnuMosTodCol);
        mnuMosCol.addSeparator();
        this.add(mnuMosCol);
        this.addSeparator();
        this.add(mnuAjuAncCol);
        this.addSeparator();
        mnuTipSel.add(mnuTipSelCel);
        mnuTipSel.add(mnuTipSelFil);
        mnuTipSel.add(mnuTipSelCol);
        this.add(mnuTipSel);
        //Adicionar los listener.
        tblDatOri.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseReleased(java.awt.event.MouseEvent evt) {
//                tblDatOriMouseReleased(evt);
//            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatOriMouseReleased(evt);
            }
        });

       
        
////////////////////////////////////////////////////////////////////
//        mnuAgrSubGrpIng.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {                
//                strNomSubGrpMay="INGRE%";
//                strNomSubGrpMin="ingre%";
//                intVarCodSubGrp=intRetornaCodigoSubGrp(strNomSubGrpMay, strNomSubGrpMin);                
//                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
//                mnuAgrSubGrpActionPerformed(evt);
//                strNomSubGrpMay="";
//                strNomSubGrpMin="";                
//            }
//        });        
        
        /*
         * PARA EL SUBGRUPO DE INGRESOS - LSC EFECTIVO
         */
        mnuAgrSubSubGrpIngLSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="LSC-EFECT%";
                strNomSubGrpMin="lsc-efect%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);                
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);                
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";                
            }
        });

        /*
         * PARA EL SUBGRUPO DE INGRESOS - PTMO. PICHINCHA
         */
        mnuAgrSubSubGrpIngPmoPch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="PRÉSTAMOS PICH%";
                strNomSubGrpMin="préstamos pich%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);                
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                System.out.println("FILA A INSERTAR 1 : "+intFilPeg);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";                
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE INGRESOS - RETENCIÓN FUENTE
         */
        mnuAgrSubSubGrpIngRteFte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="RETENCIÓN FU%";
                strNomSubGrpMin="retención fu%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);                
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";                
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE INGRESOS - FONDOS DE INVERSIÓN
         */
        mnuAgrSubSubGrpIngFdoInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="FONDO DE INV%";
                strNomSubGrpMin="fondo de inv%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        
        /*
         * PARA EL SUBGRUPO DE IMPORTACIONES Y LOCALES - PROVEEDORES EXTERIOR
         */
        mnuAgrSubSubGrpPrvExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="PROVEEDORES EXT%";
                strNomSubGrpMin="proveedores ext%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE IMPORTACIONES Y LOCALES - ARANCELES Y GTOS IMPORTACIONES
         */
        mnuAgrSubSubGrpArnGtoImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="ARANCELES Y G%";
                strNomSubGrpMin="aranceles y g%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE IMPORTACIONES Y LOCALES - COMPRAS LOCALES
         */
        mnuAgrSubSubGrpComLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="COMPRAS L%";
                strNomSubGrpMin="compras l%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE IMPUESTOS - IVA PAGADO IMPORTACIÓN
         */
        mnuAgrSubSubGrpIvaPgdImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="IVA PAGADO I%";
                strNomSubGrpMin="iva pagado i%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE IMPUESTOS - IVA PAGADO LOCAL
         */
        mnuAgrSubSubGrpIvaPgdLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="IVA PAGADO L%";
                strNomSubGrpMin="iva pagado l%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });

        /*
         * PARA EL SUBGRUPO DE IMPUESTOS - IVA PAGADO DECLARACIÓN
         */
        mnuAgrSubSubGrpIvaPgdDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="IVA PAGADO D%";
                strNomSubGrpMin="iva pagado d%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE IMPUESTOS - RETENCIÓN FUENTE
         */
        mnuAgrSubSubGrpRetFte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="RETENCIÓN EN LA F%";
                strNomSubGrpMin="retención en la f%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE GASTOS GENERALES - COSTO PERSONAL
         */
        mnuAgrSubSubGrpCosPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="COSTO PER%";
                strNomSubGrpMin="costo per%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE GASTOS GENERALES - GASTOS GENERALES
         */
        mnuAgrSubSubGrpGtoGrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="GASTOS G%";
                strNomSubGrpMin="GASTOS G%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });

        /*
         * PARA EL SUBGRUPO DE GASTOS FINANCIEROS - INTERESES PROVEEDORES EXTERIOR
         */
        mnuAgrSubSubGrpIntPrvExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="INTERESES PROV%";
                strNomSubGrpMin="intereses prov%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE GASTOS FINANCIEROS - DIVIDENDO BCO PICHINCHA
         */
        mnuAgrSubSubGrpDivBcoPch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="DIVIDENDO B%";
                strNomSubGrpMin="dividendo b%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        /*
         * PARA EL SUBGRUPO DE GASTOS FINANCIEROS - INTERESES BCO. PICHINCHA
         */
        mnuAgrSubSubGrpIntBcoPch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {                
                strNomSubGrpMay="INTERESES B%";
                strNomSubGrpMin="intereses b%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });
        
        mnuAgrSubGrpNotOpe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strNomSubGrpMay="NO OPERA%";
                strNomSubGrpMin="no opera%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });        

        mnuAgrSubGrpActFij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strNomSubGrpMay="ACTIV%";
                strNomSubGrpMin="activ%";
                intVarCodSubGrp=intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin);
                intFilPeg=intRetornaFilaPegadoDato(intVarCodSubGrp);
                mnuAgrSubGrpActionPerformed(evt);
                strNomSubGrpMay="";
                strNomSubGrpMin="";
            }
        });                
        
////////////////////////////////////////////////////////////////////        
        
        
        
        mnuSelFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelFilActionPerformed(evt);
            }
        });
        mnuSelCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelColActionPerformed(evt);
            }
        });
        mnuSelTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelTodActionPerformed(evt);
            }
        });
        mnuInsFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsFilActionPerformed(evt);
            }
        });
        mnuEliFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEliFilActionPerformed(evt);
            }
        });
        mnuBorCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBorConActionPerformed(evt);
            }
        });
        mnuOcuCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOcuColActionPerformed(evt);
            }
        });
        mnuMosTodCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMosTodColActionPerformed(evt);
            }
        });
        mnuAjuAncCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAjuAncColActionPerformed(evt);
            }
        });
        mnuTipSelCel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTipSelCelActionPerformed(evt);
            }
        });
        mnuTipSelFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTipSelFilActionPerformed(evt);
            }
        });
        mnuTipSelCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTipSelColActionPerformed(evt);
            }
        });
    }
    
    /**
     * Esta función muestra el JPopupMenu.
     */
    private void tblDatOriMouseReleased(java.awt.event.MouseEvent evt)
    {
//        if (evt.isPopupTrigger())
//            this.show(evt.getComponent(),evt.getX(),evt.getY());
        if(evt.getButton()>=2)
            this.show(evt.getComponent(),evt.getX(),evt.getY());
    }
    

        
    
    private void mnuAgrSubGrpActionPerformed(java.awt.event.ActionEvent evt){        
        int intFilSel[], intColSel[];
        StringBuffer stb=new StringBuffer();
        Object objAux;
        String strDat, strFil[];
        java.util.StringTokenizer stkDat;        
        int intFilSelPeg, intColSelPeg;
        int intTmp=1, jTmp=0;
        
        
        try{                      
            intFilSel=tblDatOri.getSelectedRows();
            intColSel=tblDatOri.getSelectedColumns();
            //Obtener el detalle que se va a enviar al Portapapeles,
            for (int i=0; i<intFilSel.length; i++)
            {
                for (int j=0; j<intColSel.length; j++)
                {
                    objAux=objTblModOri.getValueAt(intFilSel[i], intColSel[j]);
                    //Adicionar "TAB" y "Enter" a la cadena que se va a pasar al Portapapeles.
                    if (j<intColSel.length-1)
                        stb.append((objAux==null?"":objAux.toString()) + "\t");
                    else
                        stb.append((objAux==null?"":objAux.toString()) + "\n");
                    objTblModOri.setValueAt(null, intFilSel[i], intColSel[j]);
                }
            }
            //Pasar cadena al Portapapeles.
            getToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(stb.toString()), null);
            
            //PARA PEGAR EL CONTENIDO EN LA TABLA DE GRUPOS            
            //intFilSelPeg=objTblModDes.getRowCountTrue();
            java.awt.datatransfer.Transferable objTra=getToolkit().getSystemClipboard().getContents(null);
            if (objTra!=null && objTra.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)){
                strDat=(String)objTra.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
                stkDat=new java.util.StringTokenizer(strDat,"\n",false);
                for (int i=0; stkDat.hasMoreTokens(); i++){
                    objTblModDes.insertRow((i+intFilPeg));                    
                    strFil=stkDat.nextToken().split("\t");                                        
                    for (int j=0; j<strFil.length; j++){
                        
                        System.out.println("I: "+i);
                        System.out.println("J: "+j);
                        System.out.println("NOMBRE >: "+strNomSubGrpMay);
                        System.out.println("NOMBRE <: "+strNomSubGrpMin);
                        System.out.println("FILA DE PEGADO : "+intFilPeg);
                        System.out.println("FILA DE PEGADO + i: "+(intFilPeg+i));
                        System.out.println("CODIGO SUBGRP : "+intColCodSubGrp);
                        System.out.println("CODIGO GRP : "+intColCodGrp);
                        System.out.println("CODIGO SUBGRP POR BLOQUE : "+intColCodSubGrpBlk);
                        System.out.println("COLUMNA DE PEGADO : "+intColPeg);
                        System.out.println("COLUMNA DE PEGADO +j: "+(j+intColPeg));
                        System.out.println("DATOS : "+strFil[j]);
                        
                        objTblModDes.setValueAt(strFil[j], (i+intFilPeg), (j + intColPeg));                        
                        System.out.println("FUNCION : "+(intRetornaCodigoSubGrp(strNomSubGrpMay, strNomSubGrpMin)));
                        objTblModDes.setValueAt(""+intRetornaCodigoSubGrp(strNomSubGrpMay, strNomSubGrpMin), (i+intFilPeg), intColCodSubGrp);
                        System.out.println("INGRESA 1");
                        objTblModDes.setValueAt(""+intRetornaCodigoGrp(strNomSubGrpMay, strNomSubGrpMin), (i+intFilPeg), intColCodGrp);
                        objTblModDes.setValueAt(""+intRetornaCodigoSubGrpBlk(strNomSubGrpMay, strNomSubGrpMin), (i+intFilPeg), intColCodSubGrpBlk);
                    }
                    //intFilasInsertadasIng++;
                }
                objTblModDes.removeEmptyRows();                                
                for(int j=(intFilSel.length-1); j>=0; j--){
                    objTblModOri.removeRow(intFilSel[j]);
                }                 
            }                                                           
            //Liberar recursos.
            strDat=null;
            strFil=null;
            stkDat=null;                                    
        }                
        catch (java.awt.HeadlessException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (IllegalStateException e)
        {
            System.out.println("Excepción: " + e.toString());
        }
        catch (Exception e)
        {
            System.out.println("Excepción: " + e.toString());
        }
    }        
    
    
    
          
    
    
    
    /**
     * Esta función selecciona la fila(s) actual(es).
     */
    private void mnuSelFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        tblDatOri.setColumnSelectionInterval(0, tblDatOri.getColumnCount()-1);
    }
    
    /**
     * Esta función selecciona la columna(s) actual(es).
     */
    private void mnuSelColActionPerformed(java.awt.event.ActionEvent evt)
    {
        tblDatOri.setRowSelectionInterval(0, tblDatOri.getRowCount()-1);
    }
    
    /**
     * Esta función selecciona todas las celdas de la tabla.
     */
    private void mnuSelTodActionPerformed(java.awt.event.ActionEvent evt)
    {
        tblDatOri.selectAll();
    }
    
    /**
     * Esta función inserta una fila en blanco en la posición seleccionada.
     */
    private void mnuInsFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        int intFilSel;
        intFilSel=tblDatOri.getSelectedRow();
        objTblModOri.insertRow(intFilSel);
        //Seleccionar la fila seleccionada.
        tblDatOri.setRowSelectionInterval(intFilSel, intFilSel);
    }
    
    /**
     * Esta función elimina las filas seleccionadas.
     */
    private void mnuEliFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        int i, intFilSel[];
        intFilSel=tblDatOri.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
            objTblModOri.removeRow(intFilSel[i]-i);
        intFilSel=null;
    }
    
    /**
     * Esta función borra el contenido de las celdas seleccionadas.
     */
    private void mnuBorConActionPerformed(java.awt.event.ActionEvent evt)
    {
        System.out.println("Borrar contenido");
        int intFilSel[], intColSel[], i, j;
        intFilSel=tblDatOri.getSelectedRows();
        intColSel=tblDatOri.getSelectedColumns();
        for (i=0; i<intFilSel.length; i++)
        {
            for (j=0; j<intColSel.length; j++)
            {
                objTblModOri.setValueAt(null, intFilSel[i], intColSel[j]);
//                System.out.println("Borrar: " + intFilSel[i] + "," + intColSel[j]);
            }
        }
        intFilSel=null;
        intColSel=null;
    }
    
    /**
     * Esta función oculta la columna seleccionada.
     */
    private void mnuOcuColActionPerformed(java.awt.event.ActionEvent evt)
    {
        System.out.println("Ocultar columna");
    }
    
    /**
     * Esta función muestra todas las columnas ocultas.
     */
    private void mnuMosTodColActionPerformed(java.awt.event.ActionEvent evt)
    {
        System.out.println("Mostrar todas las columnas");
    }
    
    /**
     * Esta función ajusta el ancho de todas las columnas automáticamente.
     */
    private void mnuAjuAncColActionPerformed(java.awt.event.ActionEvent evt)
    {
        System.out.println("Ajustar ancho de columnas automáticamente");
    }
    
    /**
     * Esta función establece que el tipo de selección sea por celdas.
     */
    private void mnuTipSelCelActionPerformed(java.awt.event.ActionEvent evt)
    {
        tblDatOri.setCellSelectionEnabled(true);
    }
    
    /**
     * Esta función establece que el tipo de selección sea por filas.
     */
    private void mnuTipSelFilActionPerformed(java.awt.event.ActionEvent evt)
    {
        tblDatOri.setRowSelectionAllowed(true);
        tblDatOri.setColumnSelectionAllowed(false);
    }

    /**
     * Esta función establece que el tipo de selección sea por columnas.
     */
    private void mnuTipSelColActionPerformed(java.awt.event.ActionEvent evt)
    {
        tblDatOri.setRowSelectionAllowed(false);
        tblDatOri.setColumnSelectionAllowed(true);
    }
    
    /**
     * Esta función selecciona las celdas de un JTable de acuerdo al tipo de selección.
     * El tipo de selección permite que se selecionen todas las celdas de una fila, de una columna
     * o ciertas celdas en particular. Esta selección es aparente ya que cuando se utilizan las
     * funciones "getSelectedRows()" y "getSelectedColumns()" para determinar las celdas seleccionadas
     * sólo se devuelve como celda seleccionada la celda actual cuando en realidad están selecionadas
     * todas las celdas de una columna(s) en particular o todas las celdas de una fila(s) en particular
     * Los métodos que permiten cortar, copiar, etc. utilizan dichos métodos para determinar las celdas
     * seleccionadas. Es por eso que antes de cortar, copiar, etc. se utiliza éste método para seleccionar
     * las celdas que deben estar seleccionadas de acuerdo al tipo de selección de celdas que tiene
     * establecido el JTable.
     * @return true: Si se pudieron seleccionar las celdas.
     * <BR>false: En el caso contrario.
     */
    private boolean seleccionarCel()
    {
        boolean blnRes=true;
        try
        {
            if (tblDatOri.getRowSelectionAllowed())
            {
                if (tblDatOri.getColumnSelectionAllowed())
                {
                    //Selección por celda.
                }
                else
                {
                    //Selección por fila.
                    tblDatOri.setColumnSelectionInterval(0, tblDatOri.getColumnCount()-1);
                }
            }
            else
            {
                if (tblDatOri.getColumnSelectionAllowed())
                {
                    //Selección por columna.
                    tblDatOri.setRowSelectionInterval(0, tblDatOri.getRowCount()-1);
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    
    public int intRetornaCodigoSubGrpBlk(String nomSubGrpMay, String nomSubGrpMin){        
        java.sql.Connection conCodSubGrp;
        java.sql.Statement stmCodSubGrp;
        java.sql.ResultSet rstCodSubGrp;
        String strNomSubGrpMay=nomSubGrpMay;
        String strNomSubGrpMin=nomSubGrpMin;        
        try{
            conCodSubGrp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCodSubGrp!=null){
                stmCodSubGrp=conCodSubGrp.createStatement();
                strSQL="";
                strSQL+="select co_subsubgrp";
                strSQL+=" from tbm_estfinsubsubgrp";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ((upper(tx_nom) like '" + strNomSubGrpMay +"' ) OR (lower(tx_nom) like '" + strNomSubGrpMin + "'))";                
//                System.out.println("EL CODIGO DEL SUBGRP POR BLOQUE: "+strSQL);
                rstCodSubGrp=stmCodSubGrp.executeQuery(strSQL);
                if (rstCodSubGrp.next()){
                    intCodSubGrp=rstCodSubGrp.getInt("co_subsubgrp");
                }
            }
            conCodSubGrp.close();
            conCodSubGrp=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodSubGrp;
    }    

    
    public int intRetornaCodigoSubGrp(String nomSubGrpMay, String nomSubGrpMin){        
        java.sql.Connection conCodSubGrp;
        java.sql.Statement stmCodSubGrp;
        java.sql.ResultSet rstCodSubGrp;
        String strNomSubGrpMay=nomSubGrpMay;
        String strNomSubGrpMin=nomSubGrpMin;        
        try{
            conCodSubGrp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCodSubGrp!=null){
                stmCodSubGrp=conCodSubGrp.createStatement();
                strSQL="";
                strSQL+="select co_subgrp";
                strSQL+=" from tbm_estfinsubsubgrp";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ((upper(tx_nom) like '" + strNomSubGrpMay +"' ) OR (lower(tx_nom) like '" + strNomSubGrpMin + "'))";                
                System.out.println("EL CODIGO DEL SUBGRP: "+strSQL);
                rstCodSubGrp=stmCodSubGrp.executeQuery(strSQL);
                if (rstCodSubGrp.next()){
                    intCodSubGrp=rstCodSubGrp.getInt("co_subsubgrp");
                }
            }
            conCodSubGrp.close();
            conCodSubGrp=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodSubGrp;
    }        
    
    
    
    
   
    public int intRetornaCodigoGrp(String nomSubGrpMay, String nomSubGrpMin){        
        java.sql.Connection conCodSubGrp;
        java.sql.Statement stmCodSubGrp;
        java.sql.ResultSet rstCodSubGrp;
        String strNomSubGrpMay=nomSubGrpMay;
        String strNomSubGrpMin=nomSubGrpMin;   
          
        try{
            conCodSubGrp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCodSubGrp!=null){
                stmCodSubGrp=conCodSubGrp.createStatement();
                strSQL="";
                strSQL+="select co_grp";
                strSQL+=" from tbm_estfinsubsubgrp";
                strSQL+=" where co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and ((upper(tx_nom) like '" + strNomSubGrpMay +"' ) OR (lower(tx_nom) like '" + strNomSubGrpMin + "'))";
                System.out.println("EL CODIGO DEL GRP: "+strSQL);
                rstCodSubGrp=stmCodSubGrp.executeQuery(strSQL);
                if (rstCodSubGrp.next()){
                    intCodGrp=rstCodSubGrp.getInt("co_grp");
                }
            }
            conCodSubGrp.close();
            conCodSubGrp=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodGrp;
    }        
        
   
    
    
    private int intRetornaFilaPegadoDato(int codSubGrp){
        //System.out.println("CODIGO DE SUBGRUPO - MAE: "+codSubGrp);
        int intFilaPegadoDato=0;        
        int intCodSubGrp;
        for (int i=0; i<objTblModDes.getRowCountTrue(); i++){
            intCodSubGrp=Integer.parseInt(""+objTblModDes.getValueAt(i, intColCodSubGrpBlk));
            if(intCodSubGrp==codSubGrp){                
                intFilaPegadoDato=i+1;
                System.out.println("FILA DE PEGADO CALCULADA: "+intFilaPegadoDato);
            }            
        }
       return intFilaPegadoDato;
    }    
    

    

    
    
    
    
    
    
}
