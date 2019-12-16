
/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER BODEGAS*/

--Origen
SELECT a1.co_bodGrp as co_bod, a2.tx_nom
FROM tbr_bodlocprgusr as a
INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) 
INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod)
WHERE a.co_emp=4 AND a.co_loc=3 AND a.co_usr=17 AND a.co_mnu=846 AND a.tx_natBod='E'
ORDER BY a1.co_bodGrp

--Destino
SELECT a1.co_bodGrp as co_bod, a2.tx_nom
FROM tbr_bodlocprgusr as a
INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) 
INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod)
WHERE a.co_emp=4 AND a.co_loc=3 AND a.co_usr=17 AND a.co_mnu=846 AND a.tx_natBod='I'
ORDER BY a1.co_bodGrp

--Admin
SELECT a2.co_bod, a2.tx_nom , a2.st_reg
FROM tbm_emp AS a1 
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) 
WHERE a1.co_emp=0 AND a2.st_reg='A'
ORDER BY a1.co_emp, a2.co_bod  

/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER DESTINATARIOS/ CLIENTES */
--Grupo Admin
SELECT a.co_cli, a.tx_ide, a.tx_nom, a.tx_dir 
FROM  
(   
     select b2.co_emp, ' ' as co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir   
     from    
     (      
        select a2.co_emp, MAX(a2.co_cli) as co_cli, a2.tx_ide     
        from ( select MIN(co_emp) as co_emp, tx_ide from tbm_cli group by tx_ide  ) as a1      
        inner join tbm_cli as a2 on (a1.co_emp=a2.co_emp and a1.tx_ide=a2.tx_ide)      
        group by a2.co_emp, a2.tx_ide    
     ) as b1    
     inner join tbm_cli as b2 on (b1.co_emp=b2.co_emp and b1.co_cli=b2.co_cli)    
     where b2.co_Emp not in (select  co_Emp from tbm_Emp where st_reg='I') 
     and b2.st_Reg ='A'  and b2.st_cli='S'   
     order by b2.tx_nom 
) AS a 

--Grupo Usuarios
SELECT '' as co_cli, c.*  
FROM  
(    
    SELECT a1.tx_ide, a1.tx_nom    
    FROM tbm_cli AS a1     
    INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)     
    INNER JOIN     
    (        
        SELECT a2.co_emp as EmpAcc, a2.co_loc as LocAcc, a.co_emp, a.co_loc  
        FROM tbm_loc as a          
        INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp)         
        INNER JOIN tbr_locPrgUsr as a2 ON (a.co_Emp=a2.co_empRel AND a.co_loc=a2.co_locRel)         
        WHERE a2.co_emp=0      AND a2.co_loc=1      AND a2.co_mnu=3519      AND a2.co_usr=7      
        ORDER BY a.co_emp, a.co_loc      
      ) as b ON (a1.co_emp=b.co_emp and a2.co_loc=b.co_loc)    
      AND a1.st_reg='A' AND a1.st_cli='S'     
      GROUP BY  a1.tx_ide, a1.tx_nom    
      ORDER BY a1.tx_nom    
) as c 

/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/

/* CargarDet() */
SELECT Seg.co_Seg, Cot.co_emp as CodEmpCot, Cot.co_Loc as CodLocCot, cot.tx_nom as NomLocCot, Cot.fe_cot, Cot.co_Cot, 
        Sol.co_emp as CodEmpSol, Sol.co_loc as CodLocSol, Sol.co_tipDoc as CodTipDocSol, 
        Sol.tx_DesCor as DesCorSol, Sol.tx_DesLar as DesLarSol, Sol.co_doc as CodDocSol, 
        Sol.ne_numDoc as NumSol, Sol.fe_doc as FecDocSol, 
        CASE WHEN Cot.co_cli IS NOT NULL THEN Cot.co_cli ELSE Sol.CodCliDes END as co_cli, 
        CASE WHEN Cot.NomCli IS NOT NULL THEN Cot.NomCli ELSE Sol.NomCliDes END as NomCli, 
        Sol.co_bodOrg, Sol.NomBodOrg, Sol.co_bodDes, Sol.NomBodDes, 
        Sol.nd_pesTotKgr, Sol.EstAut, Sol.fe_Aut, Sol.tx_obsAut, Sol.st_ConInv, 
        Fac.CodEmpFacVen, Fac.CodLocFacVen, Fac.CodTipDocFacVen, Fac.DesCorFacVen, 
        Fac.DesLarFacVen, Fac.CodDocFacVen, Fac.NumDocFacVen, 
        OrdCli.CodEmpOrdCli, OrdCli.CodLocOrdCli, OrdCli.CodTipDocOrdCli, OrdCli.DesCorOrdCli, 
        OrdCli.DesLarOrdCli, OrdCli.CodDocOrdCli, OrdCli.ne_numOrdDes, 
        CASE WHEN Sol.st_ConInv='N' THEN  'N' ELSE 'S' END as OrdPed 
 FROM tbm_cabSegMovInv Seg 
 LEFT OUTER JOIN 
 ( 
    SELECT a4.co_Seg, cot.co_emp, cot.co_Loc, a2.tx_nom, cot.fe_cot, cot.co_Cot, cot.co_cli, a3.tx_ide, a3.tx_nom as NomCli 
    FROM tbm_cabCotVen as cot 
    INNER JOIN tbm_cabSegMovInv as a4 ON (a4.co_empRelCabCotVen=cot.co_emp AND a4.co_LocRelCabCotVen=cot.co_Loc AND a4.co_CotRelCabCotVen=cot.co_Cot) 
    INNER JOIN tbm_cli as a3 ON (cot.co_emp=a3.co_emp AND cot.co_cli=a3.co_cli) 
    INNER JOIN tbm_Loc as a2 ON (a2.co_emp=cot.co_Emp AND a2.co_Loc=cot.co_Loc) 
    
 ) as Cot ON (Cot.co_seg=Seg.co_seg ) 
 LEFT OUTER JOIN  
 ( 
    SELECT a8.co_seg, sol.co_emp, sol.co_Loc, sol.co_TipDoc, a4.tx_desCor, a4.tx_desLar, sol.co_doc, sol.ne_numDoc, sol.fe_doc, 
           a3.co_empDueBod as CodEmpDueBodDes, a6.co_cliEmpOrg as CodCliDes, a7.tx_ide as IdeCliDes, a5.tx_nom as NomCliDes, sol.co_bodOrg, a2.tx_nom as NomBodOrg, 
           sol.co_bodDes, a3.tx_nom as NomBodDes, sol.nd_pesTotKgr, sol.st_Aut, sol.fe_aut, sol.tx_obsAut, sol.st_ConInv, 
           CASE WHEN  a10.co_tipDoc IS NOT NULL THEN 'Y'  ELSE sol.st_Aut END as EstAut 
    FROM tbm_cabSoltraInv as sol 
    INNER JOIN tbm_cabSegMovInv as a8 
    ON (a8.co_empRelCabSolTraInv=sol.co_emp AND a8.co_LocRelCabSolTraInv=sol.co_Loc AND a8.co_tipDocRelCabSolTraInv=sol.co_tipDoc AND a8.co_docRelCabSolTraInv=sol.co_doc) 
    INNER JOIN tbm_bod as a2 ON (a2.co_emp=sol.co_emp AND a2.co_bod=sol.co_bodOrg) 
    INNER JOIN tbm_bod as a3 ON (a3.co_emp=sol.co_emp AND a3.co_bod=sol.co_bodDes) 
    LEFT JOIN tbm_cfgEmpRel as a6 ON (a6.co_empOrg=0 AND a6.co_EmpDes=a3.co_EmpDueBod) 
    LEFT JOIN tbm_cli as a7 ON (a7.co_emp=a6.co_empOrg AND a7.co_cli=a6.co_cliEmpOrg) 
    LEFT JOIN tbm_emp as a5 ON (a5.co_emp=a3.co_empDueBod) 
    INNER JOIN tbm_CabTipDoc as a4 ON (a4.co_emp=sol.co_emp AND a4.co_loc=sol.co_loc AND a4.co_tipDoc=sol.co_tipDoc) 
    LEFT JOIN 
    ( 
    	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, a1.ne_numDoc, a.tx_obs1 
    	FROM tbm_cfgtipdocutiproaut as a 
    	INNER JOIN tbm_cabSolTraInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc) 
    	WHERE a.co_emp=0 AND a.co_cfg in (2000, 2001) 
    ) as a10 ON (a10.co_emp=sol.co_emp AND a10.co_loc=sol.co_loc AND a10.co_tiPDoc=sol.co_tipDoc AND a10.co_doc=sol.co_doc)  
      WHERE Sol.co_tipDoc in (  SELECT a1.co_tipDoc  FROM tbm_cabTipDoc AS a1 INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) WHERE a1.co_emp=0 AND a1.co_loc=1 AND a2.co_mnu=3344 AND a2.co_usr=234 ORDER BY a1.tx_desCor)  
 ) as Sol ON (Sol.co_seg=Seg.co_seg ) 
 LEFT OUTER JOIN 
 ( 
     SELECT a3.co_seg, a1.ne_numCot, a1.co_emp as CodEmpFacVen, a1.co_loc as CodLocFacVen, a1.co_tipDoc as CodTipDocFacVen, a2.tx_DesCor as DesCorFacVen, 
            a2.tx_DesLar as DesLarFacVen, a1.co_doc as CodDocFacVen, a1.ne_numDoc as NumDocFacVen  
     FROM  tbm_cabMovInv as a1   
     INNER JOIN tbm_cabSegMovInv as a3  
     ON (a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_Doc=a3.co_docRelCabMovInv)  
     INNER JOIN tbm_cabCotVen as a5 ON(a5.co_emp=a1.co_Emp AND a5.co_loc=a1.co_loc AND a5.co_Cot=a1.ne_numCot) 
     INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) 
     INNER JOIN tbm_cli as a4 ON (a4.co_emp=a1.co_emp AND a4.co_cli=a1.co_cli ) 
     WHERE a1.ne_numCot >0 AND a1.co_tipDoc=228 /*AND a4.co_empGrp IS NULL*/ 
 ) as Fac ON (Fac.co_seg=Seg.co_seg) 
 LEFT OUTER JOIN  
 ( 
      SELECT a4.co_Seg, a.co_emp as CodEmpOrdCli, a.co_loc as CodLocOrdCli, a.co_tipDoc as CodTipDocOrdCli, a3.tx_DesCor as DesCorOrdCli,  
             a3.tx_DesLar as DesLarOrdCli, a.co_doc as CodDocOrdCli, a.ne_numOrdDes, a.fe_Doc, 
             a1.co_EmpRel as CodEmpFacVen, a1.co_locRel as CodLocFacVen, a1.co_tipDocRel as CodTipDocFacVen, a1.co_docRel as CodDocFacVen  
      FROM tbm_cabGuiRem as a   
      INNER JOIN tbm_detGuiRem as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc)  
      INNER JOIN tbm_cabSegMovInv as a4  
      ON (a.co_emp=a4.co_empRelCabGuiRem AND a.co_loc=a4.co_locRelCabGuiRem AND a.co_tipDoc=a4.co_tipDocRelCabGuiRem AND a.co_doc=a4.co_docRelCabGuiRem )  
      INNER JOIN tbm_cabTipDoc as a3 ON (a.co_Emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_tipDoc=a3.co_TipDoc)  
      WHERE a.ne_numOrdDes>0 
      GROUP BY a4.co_Seg, a.co_emp, a.co_loc, a.co_tipDoc, a3.tx_DesCor, a3.tx_DesLar, a.co_doc, a.ne_numOrdDes, a.fe_Doc,  
               a1.co_EmpRel, a1.co_locRel, a1.co_tipDocRel , a1.co_docRel  
 ) as OrdCli ON (OrdCli.co_Seg=Seg.co_Seg AND Fac.CodEmpFacVen=OrdCli.CodEmpFacVen AND Fac.CodLocFacVen=OrdCli.CodLocFacVen 
                 AND Fac.CodTipDocFacVen=OrdCli.CodTipDocFacVen AND Fac.CodDocFacVen=OrdCli.CodDocFacVen ) 
 WHERE Sol.co_tipDoc in (  SELECT a1.co_tipDoc  FROM tbm_cabTipDoc AS a1 INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) WHERE a1.co_emp=0 AND a1.co_loc=1 AND a2.co_mnu=3344 AND a2.co_usr=234 ORDER BY a1.tx_desCor)  AND  Sol.fe_doc BETWEEN '2016-08-07' AND '2016-09-06' AND (  (Sol.co_bodOrg=15)  )  AND (  (Sol.co_bodDes=1)  OR  (Sol.co_bodDes=2)  OR  (Sol.co_bodDes=3)  OR  (Sol.co_bodDes=5)  OR  (Sol.co_bodDes=11)  OR  (Sol.co_bodDes=28)  )  AND (Sol.st_conInv IS NULL OR Sol.st_conInv IN ('P', 'E')) 
 GROUP BY Seg.co_Seg, Cot.co_emp, Cot.co_Loc, cot.tx_nom, Cot.fe_cot, Cot.co_Cot,  
          Sol.co_emp, Sol.co_loc, Sol.co_tipDoc, Sol.tx_DesCor, Sol.tx_DesLar, Sol.co_doc,  
          Sol.ne_numDoc, Sol.fe_doc, Cot.co_cli, Sol.CodCliDes, Cot.NomCli, Sol.NomCliDes,  
          Sol.co_bodOrg, Sol.NomBodOrg, Sol.co_bodDes, Sol.NomBodDes,  
          Sol.nd_pesTotKgr, Sol.EstAut, Sol.fe_Aut, Sol.tx_obsAut, Sol.st_ConInv,  
 	   Fac.CodEmpFacVen, Fac.CodLocFacVen, Fac.CodTipDocFacVen, Fac.DesCorFacVen,  
          Fac.DesLarFacVen, Fac.CodDocFacVen, Fac.NumDocFacVen,   
          OrdCli.CodEmpOrdCli, OrdCli.CodLocOrdCli, OrdCli.CodTipDocOrdCli, OrdCli.DesCorOrdCli,  
          OrdCli.DesLarOrdCli, OrdCli.CodDocOrdCli, OrdCli.ne_numOrdDes 
  ORDER BY Cot.co_emp, Cot.co_Loc, Cot.fe_cot, Cot.co_Cot, Sol.co_tipDoc, Sol.ne_numDoc 