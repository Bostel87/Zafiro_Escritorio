
/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER CONSULTAR EGRESOS*/

SELECT Seg.co_Seg, z.* FROM ( 
 SELECT * FROM  
 ( 
 	SELECT CASE WHEN x.tx_tipMov IN ('E','V') THEN 'VENTA' ELSE 'REPOSICIÓN' END as MotTra, 
 	       CASE WHEN st_ConInvGui IN ('P','E') THEN st_conInvMov ELSE st_ConInvGui END as EstConInv,  
 	       x.CodEmpOrdDes, x.CodLocOrdDes, x.CodTipDocOrdDes, x.DesCorOrdDes, x.DesLarOrdDes,  
               x.CodDocOrdDes, x.NumOrdDes,  x.FecDocOrdDes, x.CodBodOrg, x.NomBodOrg, x.CodBodDes , x.NomBodDes, 
 	       x.CodRegMovInv, x.co_itm, x.tx_codAlt, x.tx_codAlt2, x.tx_nomItm, x.tx_uniMed, 
 	       x.nd_can, (x.nd_Can - (x.nd_CanCon + x.nd_canNunRec + x.nd_canTra)) as CanPenTot, 
 	       x.CanPenBod, (x.nd_pesItmKgr * x.CanPenBod) as nd_pesTotKgrEgrBod, 
 	       x.CanPenDes, (x.nd_pesItmKgr * x.CanPenDes) as nd_pesTotKgrEgrDes, 
 	       x.co_cli, x.tx_nomCli, x.tx_dirCliDes,  x.CodEmpMovInv, x.CodLocMovInv, x.CodTipDocMovInv, x.CodDocMovInv, x.NumDocMovInv, x.FecDocMovInv , x.diasSinConf 
 	FROM   
  	(      
  	    SELECT  a3.co_emp as CodEmpOrdDes, a3.co_loc as CodLocOrdDes, a3.co_tipDoc as CodTipDocOrdDes, t.tx_descor as DesCorOrdDes, t.tx_deslar as DesLarOrdDes, 
                    a3.co_doc as CodDocOrdDes, a3.ne_numOrdDes as NumOrdDes, a3.fe_doc as FecDocOrdDes,  
                    b.co_bodGrp as CodBodOrg, b1.tx_nom as NomBodOrg,   
                    CASE WHEN Sol.CodBodDes IS NULL THEN 0 ELSE Sol.CodBodDes END as CodBodDes, Sol.NomBodDes, 
                    a1.co_reg as CodRegMovInv, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm,  
                    a1.tx_uniMed, inv.nd_pesItmKgr, a1.nd_can*-1 as nd_can,  
                    CASE WHEN a2.nd_CanCon = 0 THEN a1.nd_CanCon*-1 ELSE abs(a2.nd_CanCon) END as nd_CanCon, 
                    CASE WHEN a1.nd_canNunRec = 0 THEN abs(a2.nd_CanNunRec) ELSE a1.nd_canNunRec*-1 END as nd_canNunRec,  
                    CASE WHEN a1.nd_canTra IS NULL THEN 0 ELSE a1.nd_canTra*-1 END as nd_canTra,  
                    CASE WHEN a1.nd_CanEgrBod IS NULL THEN 0 ELSE a1.nd_CanEgrBod*-1 END as CanPenBod,   
                    CASE WHEN a1.nd_CanDesEntCli IS NULL THEN 0 ELSE a1.nd_CanDesEntCli*-1  END as CanPenDes,   
                    a3.co_clides  as co_cli, a3.tx_nomclides as tx_nomCli, a3.tx_dirCliDes, a.tx_tipMov , (current_date - a3.fe_Doc) as diasSinConf,  
                    CASE WHEN a.st_conInv IS NULL THEN 'P' ELSE a.st_conInv END as st_conInvMov, a3.st_ConInv as st_ConInvGui,  
                    a.co_emp as CodEmpMovInv, a.co_loc as CodLocMovInv, a.co_tipDoc as CodTipDocMovInv, a.co_doc as CodDocMovInv, a.ne_numDoc as NumDocMovInv, a.fe_Doc as FecDocMovInv 
  	    FROM tbm_cabMovInv AS a  
  	    INNER JOIN tbm_detMovInv AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc  
  	    INNER JOIN tbm_detGuiRem as a2 ON (a2.co_empRel=a.co_emp AND a2.co_locRel=a.co_loc AND a2.co_tipDocRel=a.co_tipDoc AND a2.co_docRel=a.co_doc AND a2.co_regRel=a1.co_Reg)    
  	    INNER JOIN tbm_cabGuiRem as a3 ON (a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_loc AND a3.co_TipDoc=a2.co_tiPDoc AND a3.co_doc=a2.co_doc) 
  	    INNER JOIN tbm_cabTipDoc AS t ON (t.co_emp=a3.co_emp AND t.co_loc=a3.co_loc AND t.co_tipDoc=a3.co_tipDoc) 
  	    INNER JOIN tbr_bodEmpBodGrp AS b   
  	    ON ( case when a.co_tipDoc =206 and a.fe_doc>='2016-07-26' and a.fe_doc<'2016-09-09'  then  (b.co_bodgrp=a1.co_bod )  else (b.co_emp=a.co_emp and b.co_bod=a1.co_bod) end )   
  	    INNER JOIN tbm_bod as b1 ON (b1.co_Emp=b.co_empGrp AND b1.co_bod=b.co_bodGrp) 
  	    LEFT OUTER JOIN  
  	    ( 
  	    	select a.co_emp as CodEmpSol, a.co_loc as CodLocSol, a.co_tipDoc as CodTipDocSol, a.co_doc as CodDocSol,  
  	    	       t1.tx_DesCor as DesCorSol, t1.tx_DesLar as DesLarSol, a.ne_numDoc as NumDocSol, 
  	    	       a.co_bodDes as CodBodDes, b1.tx_nom as NomBodDes 
  	    	from tbm_cabSolTraInv as a  
  	    	inner join tbm_cabTipDoc as t1 on (t1.co_emp=a.co_emp AND t1.co_loc=a.co_loc AND t1.co_tipDoc=a.co_tipDoc)  
  	    	inner join tbm_bod as b1 on (b1.co_Emp=a.co_emp AND b1.co_bod=a.co_bodDes) 
  	    	where a.st_reg='A' 
  	    ) as Sol ON (Sol.CodEmpSol=a.co_empRelCabSolTraInv AND Sol.CodLocSol=a.co_locRelCabSolTraInv AND Sol.CodTipDocSol=a.co_tipDocRelCabSolTraInv AND Sol.CodDocSol=a.co_docRelCabSolTraInv)  
  	    INNER JOIN tbm_equinv as a4 ON (a4.co_emp=a1.co_emp and a4.co_itm=a1.co_itm ) 
  	    INNER JOIN tbm_inv as inv ON (inv.co_Emp=a1.co_Emp AND inv.co_itm=a1.co_itm)  
            WHERE a3.ne_numDoc=0 AND a.st_reg='A' AND a1.nd_can < 0 AND a1.st_meringegrfisbod='S' AND a3.st_conInv in ('P', 'E') AND a.tx_tipMov IS NOT NULL  AND a.st_conInv not in ('F') 
            AND ( b.co_empGrp=0 AND b.co_bodGrp IN ( 1,2,3,4,5,6,7,8,9,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31 ) ) 
   	    GROUP BY  a3.co_emp, a3.co_loc, a3.co_tipDoc, t.tx_descor, t.tx_deslar, a3.co_doc, a3.ne_numOrdDes, a3.fe_doc, 
   	              b.co_bodGrp, b1.tx_nom, Sol.CodBodDes, Sol.NomBodDes, 
		      a1.co_reg, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_uniMed, inv.nd_pesItmKgr, 		      a1.nd_can, a1.nd_canCon, a1.nd_canNunRec, a2.nd_canCon, a2.nd_canNunRec, a1.nd_canTra, a1.nd_canPen, 
                     a1.nd_CanEgrBod, a1.nd_CanDesEntCli, a3.co_clides, a3.tx_dirCliDes, a3.tx_nomclides ,a.tx_tipMov, a.st_conInv, a3.st_ConInv, 
                     a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc , a.ne_numDoc, a.fe_Doc 
 	) as x   
 ) as y WHERE  
 (y.CanPenBod>0 OR y.CanPenDes>0) OR y.CanPenTot>0   
 ) as z  LEFT OUTER JOIN tbm_cabSegMovInv as Seg   ON (Seg.co_empRelCabGuiRem=z.CodEmpOrdDes AND Seg.co_locRelCabGuiRem=z.CodLocOrdDes AND Seg.co_tipDocRelCabGuiRem=z.CodTipDocOrdDes AND Seg.co_DocRelCabGuiRem=z.CodDocOrdDes) 
 ORDER BY z.CodBodOrg,  z.NumOrdDes , z.FecDocOrdDes 