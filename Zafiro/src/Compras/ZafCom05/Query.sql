/*VARIOS.*/
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_detMovInv LIMIT 10
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_loc LIMIT 10
SELECT * FROM tbm_bod LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbr_bodEmpBodGrp ORDER BY co_empGrp, co_bodGrp
SELECT * FROM tbm_detMovInv WHERE co_emp=4 AND co_loc=1 AND co_tipDoc=46 AND co_doc=75
SELECT * FROM tbr_bodLocPrgUsr ORDER BY co_emp, co_loc, co_mnu, co_usr, co_bod

SELECT a2.* 
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a1.fe_doc>='2009/05/01'
ORDER BY a1.co_emp, a2.tx_codAlt

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE BODEGAS (KARDEX - USUARIO "ADMIN").*/
SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom
FROM tbm_emp AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)
WHERE a1.co_emp=1
ORDER BY a1.co_emp, a2.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE BODEGAS (KARDEX - OTROS USUARIOS).*/
SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom
FROM tbm_emp AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)
INNER JOIN tbr_bodLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)
WHERE a3.co_emp=0 AND a3.co_loc=1 AND a3.co_mnu=237 AND a3.co_usr=24 AND a3.st_reg IN ('A','P')
ORDER BY a1.co_emp, a2.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE BODEGAS (KARDEX FÍSICO).*/
SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom
FROM tbm_emp AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)
INNER JOIN (
SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod
FROM tbr_bodLocPrgUsr AS b1
INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)
WHERE b1.co_emp=1 AND b1.co_loc=4 AND b1.co_mnu=907 AND b1.co_usr=24 AND b1.st_reg IN ('A','P')
GROUP BY b2.co_empGrp, b2.co_bodGrp
) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)
ORDER BY a1.co_emp, a2.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DE UN ITEM (KARDEX).*/
SELECT a1.st_reg, a2.co_emp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar
, a2.co_doc, a1.ne_numDoc, a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a2.nd_cosUni, a2.nd_preUni*(1-a2.nd_pordes/100) AS nd_preUni, a2.nd_pordes
, a2.nd_cosTot, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro, a1.co_cli, a1.tx_nomCli
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)
INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)
WHERE a1.co_emp=1
AND a2.co_itm=441 --021-252I
AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DE UN ITEM (KARDEX FÍSICO).*/
SELECT a1.st_reg, a2.co_emp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar
, a2.co_doc, a1.ne_numDoc, a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a2.nd_cosUni, a2.nd_preUni*(1-a2.nd_pordes/100) AS nd_preUni, a2.nd_pordes
, a2.nd_cosTot, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro, a1.co_cli, a1.tx_nomCli
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)
INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)
INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)
INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_bod=a7.co_bod)
WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=441)
AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
--AND a7.co_empGrp=0 AND a7.co_bodGrp=1
--AND a7.co_empGrp=0 AND a7.co_bodGrp=2
AND a7.co_empGrp=0 AND a7.co_bodGrp IN (1, 2)
ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DE UN ITEM (GRUPO).*/
SELECT a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar
, a2.co_doc, a1.ne_numDoc, a1.fe_doc, a2.nd_can, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_preUni*(1-a2.nd_pordes/100) AS nd_preUni
, a2.nd_cosTotGrp AS nd_cosTot, a2.nd_exiCon AS nd_exi, a2.nd_valExiCon AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro
, a1.co_cli, a1.tx_nomCli, a1.st_reg, a5.st_cosUniCal
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)
INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)
INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)
INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)
WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=441)
AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
ORDER BY a1.ne_secGrp, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PRUEBAS:*/
SELECT * FROM tbm_equInv WHERE co_itmMae=549

SELECT * FROM tbm_detMovInv WHERE co_emp=0 and co_itm=541
UNION ALL
SELECT * FROM tbm_detMovInv WHERE co_emp=1 and co_itm=541
UNION ALL
SELECT * FROM tbm_detMovInv WHERE co_emp=2 and co_itm=501
UNION ALL
SELECT * FROM tbm_detMovInv WHERE co_emp=3 and co_itm=514
UNION ALL
SELECT * FROM tbm_detMovInv WHERE co_emp=4 and co_itm=4358
/*----------------------------------------------------------------------------------------------------------------*/