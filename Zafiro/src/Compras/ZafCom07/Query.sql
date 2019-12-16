/*VARIOS.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag
SELECT * FROM tbm_inv WHERE co_emp=1
SELECT * FROM tbm_invMae LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS TIPOS DE DOCUMENTO QUE MANTIENEN SU COSTO.*/
SELECT * FROM tbm_cabTipDoc WHERE co_emp=1 AND co_loc=1 AND st_cosUniCal='N' ORDER BY co_emp, co_loc, co_tipDoc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS TIPOS DE DOCUMENTO QUE ACTUALIZAN SU COSTO.*/
SELECT * FROM tbm_cabTipDoc WHERE co_emp=1 AND co_loc=1 AND st_cosUniCal='S' ORDER BY co_emp, co_loc, co_tipDoc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DOCUMENTOS A LOS QUE SE VA A ACTUALIZAR EL COSTO.*/
SELECT a1.* 
FROM tbm_detMovInv AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1 AND a2.st_cosUniCal='S'
ORDER BY a1.co_tipDoc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 1: QUERY PARA OBTENER LOS SALDOS INICIALES DEL ITEM.*/
SELECT a2.nd_cosUni, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a2.co_itm=7631 AND a1.ne_secEmp IN 
(
SELECT MAX(b1.ne_secEmp)
FROM tbm_cabMovInv AS b1
INNER JOIN tbm_detMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
WHERE b1.co_emp=1 AND b2.co_itm=7631 AND b1.fe_doc<'2006/01/01'
)
ORDER BY a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 2: QUERY PARA OBTENER LOS REGISTROS QUE SE VAN A ACTUALIZAR EN "tbm_detMovInv".*/
SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a3.st_cosUniCal, a3.tx_natDoc, a1.st_reg
, a2.nd_can, a2.nd_cosUni, a2.nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
WHERE a1.co_emp=1 AND a2.co_itm=6382
AND a1.fe_doc BETWEEN '2006/04/01' AND '2006/06/30'
ORDER BY a1.ne_secEmp, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 2.1: QUERY PARA ACTUALIZAR EL COSTO UNITARIO DE LOS DOCUMENTOS QUE ACTUALIZAN SU COSTO EN "tbm_detMovInv".*/
SELECT a2.nd_cosUni
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a2.co_itm=7631 AND a1.ne_secEmp IN 
(
SELECT MIN(b1.ne_secEmp)
FROM tbm_cabMovInv AS b1
INNER JOIN tbm_detMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)
WHERE b1.co_emp=1 AND b2.co_itm=7631 AND b3.st_cosUniCal='N' AND b3.tx_natDoc IN ('I', 'A') AND b1.st_reg IN ('A','R','C','F') AND b1.fe_doc>'2006/01/31'
)
ORDER BY a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 3: QUERY PARA ACTUALIZAR LA TABLA "tbm_detMovInv".*/
UPDATE tbm_detMovInv
SET nd_cosUni=...
, nd_cosTot=...
, nd_exi=...
, nd_valExi=...
, nd_cosPro=...
WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1 AND co_doc=1 AND co_reg=1
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 4: QUERY PARA DETERMINAR SI SE DEBE ACTUALIZAR LA TABLA EN "tbm_inv".*/
SELECT COUNT(*)
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a2.co_itm=6382 AND a1.fe_doc>'2006/06/30'
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 5: QUERY PARA ACTUALIZAR EL COSTO UNITARIO DE TODOS LOS ITEMS EN "tbm_inv".*/
UPDATE tbm_inv
SET nd_cosUni=...
WHERE co_emp=... AND co_itm=...
/*----------------------------------------------------------------------------------------------------------------*/

/*MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 1: QUERY PARA OBTENER LOS SALDOS INICIALES DEL ITEM (GRUPO).*/
SELECT a2.nd_cosUniGrp AS nd_cosUni, a2.nd_exiGrp AS nd_exi, a2.nd_valExiGrp AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=448) 
AND a1.fe_doc<'2006/09/01'
ORDER BY a1.ne_secGrp, a2.co_reg



SELECT a2.nd_cosUniGrp AS nd_cosUni, a2.nd_exiGrp AS nd_exi, a2.nd_valExiGrp AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=448)
AND a1.ne_secGrp=
(
SELECT MAX(a1.ne_secGrp)
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=448) 
AND a1.fe_doc<'2006/09/01'
)




/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 2: QUERY PARA OBTENER LOS REGISTROS QUE SE VAN A ACTUALIZAR EN "tbm_detMovInv" (GRUPO).*/
SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.st_cosUniCal, a4.tx_natDoc, a1.st_reg
, a2.nd_can, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_cosTotGrp AS nd_cosTot, Null AS nd_salUni, Null AS nd_salVal, Null AS nd_cosPro
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=57) 
AND a1.fe_doc BETWEEN '2006/01/01' AND '2006/01/31'
ORDER BY a1.ne_secGrp, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 3: QUERY PARA ACTUALIZAR LA TABLA "tbm_detMovInv" (GRUPO).*/
UPDATE tbm_detMovInv
SET nd_cosUniGrp=...
, nd_cosTotGrp=...
, nd_exiGrp=...
, nd_valExiGrp=...
, nd_cosProGrp=...
WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1 AND co_doc=1 AND co_reg=1
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 4: QUERY PARA DETERMINAR SI SE DEBE ACTUALIZAR LA TABLA EN "tbm_inv" (GRUPO).*/
SELECT COUNT(*)
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=57)
AND a1.fe_doc>'2006/01/31'
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 5: QUERY PARA ACTUALIZAR EL COSTO UNITARIO DE TODOS LOS ITEMS EN "tbm_inv" (GRUPO).*/
UPDATE tbm_inv
SET nd_cosUni=...
WHERE co_emp=... AND co_itm=...
/*----------------------------------------------------------------------------------------------------------------*/





SELECT a1.ne_secGrp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar, a2.co_doc
, a1.ne_numDoc, a1.fe_doc, a2.nd_can, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_preUni*(1-a2.nd_pordes/100) AS nd_preUni
, a2.nd_cosTotGrp AS nd_cosTot, a2.nd_exiCon AS nd_exi, a2.nd_valExiCon AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro
, a1.co_cli, a1.tx_nomCli, a1.st_reg, a5.st_cosUniCal 
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc) 
INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod) 
INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc) 
INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm) 
WHERE a6.co_itmMae=( SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=57 ) 
AND NOT (a1.co_emp=1 AND a1.co_cli=602 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=1 AND a1.co_cli=603 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=1 AND a1.co_cli=2600 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=1 AND a1.co_cli=1039 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=2 AND a1.co_cli=2853 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=2 AND a1.co_cli=2854 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=2 AND a1.co_cli=2105 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=2 AND a1.co_cli=789 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=2 AND a1.co_cli=790 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=3 AND a1.co_cli=2857 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=3 AND a1.co_cli=2858 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=3 AND a1.co_cli=452 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=3 AND a1.co_cli=453 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=3 AND a1.co_cli=832 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=4 AND a1.co_cli=3116 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=4 AND a1.co_cli=3117 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=4 AND a1.co_cli=497 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=4 AND a1.co_cli=498 AND a1.co_cli IS NOT NULL) 
AND NOT (a1.co_emp=4 AND a1.co_cli=2294 AND a1.co_cli IS NOT NULL) 
ORDER BY a1.ne_secGrp, a2.co_reg









14_Nov_2007 - CASA
/*QUERY PARA OBTENER EL SALDO DE UN ITEM.*/
SELECT c1.ne_secEmp, c1.co_reg, c1.nd_cosUni, c1.nd_exi, c1.nd_valExi, c1.nd_cospro
FROM
(
/*MOVIMIENTOS*/
SELECT b1.ne_secEmp, b1.co_reg, b1.co_itm, b1.nd_cosUni, b1.nd_exi, b1.nd_valExi, b1.nd_cosPro
FROM 
(
SELECT a1.ne_secEmp, a2.co_reg, a2.co_itm, a2.nd_cosUni, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a1.fe_doc<'2007/01/01'
) AS b1 INNER JOIN 
(
/*------------------------------------------------------------*/
SELECT a2.co_itm, a1.fe_doc, MAX(a1.ne_secEmp) AS ne_secEmp
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN
(
SELECT b2.co_itm, MAX(b1.fe_doc) AS fe_doc
FROM tbm_cabMovInv AS b1
INNER JOIN tbm_detMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
WHERE b1.co_emp=1 AND b1.fe_doc<'2007/01/01' AND b1.st_reg IN ('A', 'R', 'C', 'F') AND b2.co_itm=10030
GROUP BY b2.co_itm
) AS a3 ON (a2.co_itm=a3.co_itm AND a1.fe_doc=a3.fe_doc)
WHERE a1.co_emp=1 AND a1.fe_doc<'2007/01/01' AND a1.st_reg IN ('A', 'R', 'C', 'F') AND a2.co_itm=10030 
GROUP BY a2.co_itm, a1.fe_doc
ORDER BY a2.co_itm
/*------------------------------------------------------------*/
) AS b2 ON (b1.co_itm=b2.co_itm AND b1.ne_secEmp=b2.ne_secEmp)
--ORDER BY b1.tx_codAlt
--ORDER BY b1.ne_secEmp
ORDER BY b1.co_itm
) AS c1
INNER JOIN (
/*QUERY PARA OBTENER LOS MÁXIMOS co_reg*/
SELECT a2.co_itm, a1.ne_secEmp, MAX(a2.co_reg) AS co_reg
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a1.fe_doc<'2007/01/01' AND a2.co_itm=10030
GROUP BY a2.co_itm, a1.ne_secEmp
) AS c2 ON (c1.co_itm=c2.co_itm AND c1.ne_secEmp=c2.ne_secEmp AND c1.co_reg=c2.co_reg)
ORDER BY c1.co_itm










/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
UPDATE tbm_detDia
SET st_regRep='D'
FROM
(
SELECT co_emp, co_loc, co_tipDoc, co_dia
FROM tbm_cabDia WHERE co_emp=1 AND co_tipDoc IN (48, 49) AND fe_dia>='2007/01/01'
) AS a1
WHERE tbm_detDia.co_emp=a1.co_emp AND tbm_detDia.co_loc=a1.co_loc AND tbm_detDia.co_tipDoc=a1.co_tipDoc AND tbm_detDia.co_dia=a1.co_dia

DELETE FROM tbm_detDia WHERE co_emp=1 AND co_tipDoc IN (48, 49) AND st_regRep='D'
DELETE FROM tbm_cabDia WHERE co_emp=1 AND co_tipDoc IN (48, 49) AND fe_dia>='2007/01/01'





SELECT co_emp, co_loc, co_tipDoc, co_doc
FROM tbm_cabMovInv WHERE co_emp=1 AND co_tipDoc IN (1, 3) AND fe_doc BETWEEN '2007/01/01' AND '2007/11/30'

SELECT * FROM tbm_detDia LIMIT 10
