/*VARIOS.*/
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_detMovInv LIMIT 10
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_loc LIMIT 10
SELECT * FROM tbm_bod ORDER BY co_emp, co_bod
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbr_bodEmpBodGrp ORDER BY co_empGrp, co_bodGrp
SELECT * FROM tbm_detMovInv WHERE co_emp=4 AND co_loc=1 AND co_tipDoc=46 AND co_doc=75
SELECT * FROM tbr_bodLocPrgUsr ORDER BY co_emp, co_loc, co_mnu, co_usr, co_bod

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS INGRESOS A BODEGA PENDIENTES, POR CONFIRMAR Y CONFIRMADOS COMPLETAMENTE (INGRESO FÍSICO).*/
SELECT a1.co_emp, a1.co_loc, a3.tx_nom AS a3_tx_nom, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli
, a2.co_itm, a2.tx_codAlt, a2.tx_nomItm, a2.tx_uniMed, a2.co_bod, a5.tx_nom AS a5_tx_nom, a2.nd_can, a1.st_conInv, a2.nd_canCon, a2.nd_canNunRec --, a2.st_merIngEgrFisBod, a1.st_conInv
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
INNER JOIN tbm_bod AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod)
INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_bod=a6.co_bod)
--INNER JOIN tbm_equInv AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_itm=a7.co_itm)
--WHERE a7.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=441)
WHERE a1.st_reg='A'
AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C') 
AND a2.st_merIngEgrFisBod='S' AND a2.nd_can>0
AND a6.co_empGrp=0 AND a6.co_bodGrp IN (1)
AND a1.fe_doc>='2009/07/01'
AND (
(a1.st_conInv IN ('P','E') AND (a2.nd_canCon+a2.nd_canNunRec)=0)  --Pendientes de confirmar
OR (a1.st_conInv=('E') AND (a2.nd_canCon+a2.nd_canNunRec)<>0 AND (a2.nd_canCon+a2.nd_canNunRec)<ABS(a2.nd_can)) --Confirmados parcialmente
OR (a1.st_conInv IN ('E','C') AND (a2.nd_canCon+a2.nd_canNunRec)=ABS(a2.nd_can)) --Completamente confirmados
)
ORDER BY a2.tx_codAlt, a1.fe_doc, a1.ne_secEmp, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*
1) Esté activo: 
	a1.st_reg='A'
2) Sea una Guía principal que NO tiene guías secundarias: 
	a1.st_tipGuiRem='P' AND a1.st_tieGuiSec='N'
   Sea una Guía principal que SI tiene guías secundarias pero no están creadas todas todavía: 
	a1.st_tipGuiRem='P' AND a1.st_tieGuiSec='N' AND a1.st_creTodGuiSec='N'
   Sea una Guía secundaria:
	a1.st_tipGuiRem='S'
3) Salga de bodega:
	AND a2.st_merEgrFisBod='S'
4) Salga de la bodega especificada:
	a6.co_empGrp=0 AND a6.co_bodGrp IN (1)
5) Fecha:
	AND a1.fe_doc>='2009/09/01'
6) Confirmación:
	AND (
	(a1.st_conInv IN ('P','E') AND (a2.nd_canCon+a2.nd_canNunRec)=0)  --Pendientes de confirmar
	OR (a1.st_conInv=('E') AND (a2.nd_canCon+a2.nd_canNunRec)<>0 AND (a2.nd_canCon+a2.nd_canNunRec)<ABS(a2.nd_can)) --Confirmados parcialmente
	OR (a1.st_conInv IN ('E','C') AND (a2.nd_canCon+a2.nd_canNunRec)=ABS(a2.nd_can)) --Completamente confirmados
	)
*/

/*QUERY PARA OBTENER LOS EGRESOS DE BODEGA PENDIENTES, POR CONFIRMAR Y CONFIRMADOS COMPLETAMENTE (EGRESO FÍSICO).*/
SELECT a1.co_emp, a1.co_loc, a3.tx_nom AS a3_tx_nom, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cliDes AS co_cli, a1.tx_nomCliDes AS tx_nomCli
, a2.co_itm, a2.tx_codAlt, a2.tx_nomItm, a2.tx_uniMed, a1.co_ptoPar AS co_bod, a5.tx_nom AS a5_tx_nom, ABS(a2.nd_can) AS nd_can, a1.st_conInv, a2.nd_canCon, a2.nd_canNunRec
FROM tbm_cabGuiRem AS a1
INNER JOIN tbm_detGuiRem as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
INNER JOIN tbm_bod AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_ptoPar=a5.co_bod)
INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a1.co_emp=a6.co_emp AND a1.co_ptoPar=a6.co_bod)
--INNER JOIN tbm_equInv AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_itm=a7.co_itm)
--WHERE a7.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=441)
WHERE a1.st_reg='A'
AND (
(a1.st_tipGuiRem='P' AND a1.st_tieGuiSec='N')
OR (a1.st_tipGuiRem='P' AND a1.st_tieGuiSec='N' AND a1.st_creTodGuiSec='N')
OR (a1.st_tipGuiRem='S')
)
AND a2.st_merEgrFisBod='S'
AND a6.co_empGrp=0 AND a6.co_bodGrp IN (1)
AND a1.fe_doc>='2009/09/01'
AND (
(a1.st_conInv IN ('P','E') AND (a2.nd_canCon+a2.nd_canNunRec)=0)  --Pendientes de confirmar
OR (a1.st_conInv=('E') AND (a2.nd_canCon+a2.nd_canNunRec)<>0 AND (a2.nd_canCon+a2.nd_canNunRec)<ABS(a2.nd_can)) --Confirmados parcialmente
OR (a1.st_conInv IN ('E','C') AND (a2.nd_canCon+a2.nd_canNunRec)=ABS(a2.nd_can)) --Completamente confirmados
)
ORDER BY a2.tx_codAlt, a1.fe_doc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/
