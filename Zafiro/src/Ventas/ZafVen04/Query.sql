/*VARIOS.*/
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_detMovInv LIMIT 10
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_loc LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS VENTAS DE LOS ITEMS.*/
SELECT Null AS co_itmMae, a3.co_itm, a3.tx_codAlt, a3.tx_nomItm, -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen
, a3.nd_stkAct, a3.nd_preVta1, -SUM(a2.nd_tot) AS nd_venTot, -SUM(a2.nd_cosTot) AS nd_cosTot 
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_inv a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) 
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) 
WHERE a1.co_emp=1 AND a4.ne_mod=1 AND a1.st_reg IN ('A','R','C','F')
AND a1.fe_doc BETWEEN '2009/01/01' AND '2009/01/31' 
AND ( (a1.co_emp=1 AND a1.co_loc=1) OR (a1.co_emp=1 AND a1.co_loc=4) OR (a1.co_emp=1 AND a1.co_loc=5)) 
GROUP BY a3.co_itm, a3.tx_codAlt, a3.tx_nomItm, a3.nd_stkAct, a3.nd_preVta1 
ORDER BY a3.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DE UN ITEM.*/
SELECT a1.co_loc, a3.tx_nom, a1.co_tipDoc, a4.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli
, -a2.nd_can AS nd_can, a2.nd_preUni, a2.nd_cosUni
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a2.co_itm=1008 AND a4.ne_mod=1 AND a1.st_reg IN ('A','R','C','F') AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
AND a1.fe_doc BETWEEN '2005/01/20' AND '2005/01/20'
ORDER BY a1.co_loc, a1.fe_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS VENTAS DE LOS ITEMS (GRUPO).*/
SELECT b2.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b3.nd_uniVen, b4.nd_stkAct, b1.nd_preVta1, b3.nd_venTot, b3.nd_cosTot
FROM tbm_inv AS b1
INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)
INNER JOIN
(
SELECT a4.co_itmMae, -SUM(a2.nd_can) AS nd_uniVen, -SUM(a2.nd_tot) AS nd_venTot, -SUM(a2.nd_cosTotGrp) AS nd_cosTot
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
INNER JOIN tbm_equInv AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)
WHERE a3.ne_mod=1 AND a1.st_reg IN ('A','R','C','F') AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
AND a1.fe_doc BETWEEN '2009-05-15' AND '2009-05-15'
AND ( (a1.co_emp=0 AND a1.co_loc=1) OR (a1.co_emp=1 AND a1.co_loc=1) OR (a1.co_emp=1 AND a1.co_loc=4) OR (a1.co_emp=1 AND a1.co_loc=5) OR (a1.co_emp=2 AND a1.co_loc=1) OR (a1.co_emp=2 AND a1.co_loc=4) OR (a1.co_emp=2 AND a1.co_loc=5) OR (a1.co_emp=3 AND a1.co_loc=1) OR (a1.co_emp=3 AND a1.co_loc=2) OR (a1.co_emp=4 AND a1.co_loc=1) OR (a1.co_emp=4 AND a1.co_loc=2) OR (a1.co_emp=4 AND a1.co_loc=3)) 
GROUP BY a4.co_itmMae
) AS b3 ON (b2.co_itmMae=b3.co_itmMae)
INNER JOIN
(
SELECT a1.co_itmMae, SUM(a2.nd_stkAct) AS nd_stkAct
FROM tbm_equInv AS a1
INNER JOIN tbm_inv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a2.co_emp<>0
GROUP BY a1.co_itmMae
) AS b4 ON (b2.co_itmMae=b4.co_itmMae)
WHERE b1.co_emp=0
ORDER BY b1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DE UN ITEM (GRUPO).*/
SELECT a1.co_loc, a3.tx_nom, a1.co_tipDoc, a4.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli
, -a2.nd_can AS nd_can, a2.nd_preUni, a2.nd_cosUniGrp AS nd_cosUni
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
INNER JOIN tbm_equInv AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_itm=a5.co_itm)
WHERE a5.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm=35)
AND a4.ne_mod=1 AND a1.st_reg IN ('A','R','C','F') AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
AND a1.fe_doc BETWEEN '2007-01-01' AND '2007-01-31'
ORDER BY a1.co_emp, a1.co_loc, a1.fe_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*PRUEBAS--------------------------------------------------------------------------------*/
SELECT b2.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b3.nd_uniVen, b1.nd_stkAct, b1.nd_preVta1, b3.nd_venTot, b3.nd_cosTot
FROM tbm_inv AS b1
INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)
INNER JOIN
(
SELECT a3.co_itmMae, -SUM(a2.nd_can) AS nd_uniVen
, -SUM(a2.nd_can*a2.nd_preUni*(1-a2.nd_porDes/100)) AS nd_venTot, -SUM(a2.nd_can*(CASE WHEN a2.nd_exicon=0 THEN 0 ELSE a2.nd_valexicon/a2.nd_exicon END)) AS nd_cosTot
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
WHERE a4.ne_mod=1 AND a1.st_reg IN ('A','R','C','F')
AND a1.fe_doc BETWEEN '2006/01/01' AND '2006/02/28'
GROUP BY a3.co_itmMae
) AS b3 ON (b2.co_itmMae=b3.co_itmMae)
WHERE b1.co_emp=0
ORDER BY b2.co_itmMae
