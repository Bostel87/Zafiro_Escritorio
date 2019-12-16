/*VARIOS.*/
SELECT * FROM tbm_invMae LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_var LIMIT 10
SELECT * FROM tbm_bod LIMIT 10
SELECT * FROM tbm_invBod LIMIT 10
SELECT * FROM tbr_bodLoc LIMIT 10
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_detMovInv LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS BODEGAS DE ACUERDO AL LOCAL.*/
SELECT a1.co_bod, a1.tx_nom
FROM tbm_bod AS a1
INNER JOIN tbr_bodLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)
WHERE a1.co_emp=1 AND a2.co_loc=1 AND a1.st_reg='A'
ORDER BY a1.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DATOS DEL ITEM.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a2.nd_stkAct, a1.nd_cosUni, a1.st_ser
FROM tbm_inv AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)
WHERE a1.co_emp=1 AND a2.co_bod=1 AND a1.st_reg='A'
ORDER BY a1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL STOCK DE UN ITEM EN PARTICULAR.*/
SELECT nd_stkAct
FROM tbm_invBod
WHERE co_emp=1 AND co_bod=1 AND co_itm=13384
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA ACTUALIZAR EL STOCK EN "tbm_invBod".*/
UPDATE tbm_invBod
SET nd_stkAct=nd_stkAct+0
FROM
(
/*QUERY PARA OBTENER LOS DATOS DEL ITEM EN LA EMPRESA SELECCIONADA.*/
SELECT 1 AS co_emp, 1 AS co_bod, 5375 AS co_itm
UNION ALL
/*QUERY PARA OBTENER LOS DATOS DEL ITEM EN LA EMPRESA GRUPO.*/
SELECT a1.co_emp, a2.co_bodGrp, a1.co_itm
FROM tbm_equInv AS a1
INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a1.co_emp=a2.co_empGrp)
WHERE a1.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=5375)
AND a1.co_emp=0 AND a2.co_emp=1 AND a2.co_bod=1
) AS b1
WHERE tbm_invBod.co_emp=b1.co_emp AND tbm_invBod.co_bod=b1.co_bod AND tbm_invBod.co_itm=b1.co_itm
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA ACTUALIZAR EL STOCK EN  "tbm_inv" EN FUNCIÓN DEL STOCK DE LAS BODEGAS*/
UPDATE tbm_inv
SET nd_stkAct=b1.nd_stkAct
FROM 
(
/*QUERY PARA OBTENER EL STOCK DE LOS ITEMS EN LA EMPRESA SELECCIONADA.*/
SELECT a1.co_emp, a1.co_itm, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
WHERE a1.co_emp=1 AND a1.co_itm IN (8018, 8019, 8037)
GROUP BY a1.co_emp, a1.co_itm
UNION ALL
/*QUERY PARA OBTENER EL STOCK DE LOS ITEMS EN LA EMPRESA GRUPO.*/
SELECT a1.co_emp, a1.co_itm, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND  a1.co_itm=a2.co_itm)
WHERE a2.co_itmMae IN (SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm IN (8018, 8019, 8037))
AND a2.co_emp=0
GROUP BY a1.co_emp, a1.co_itm
) AS b1
WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL DETALLE DEL REGISTRO.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.tx_uniMed, a1.co_bod, a2.tx_nom, a1.nd_can, a1.nd_cosUni 
FROM tbm_detMovInv AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)
WHERE a1.co_emp=0 AND a1.co_loc=1 AND a1.co_tipDoc=46 AND a1.co_doc=12
ORDER BY a1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LA FECHA DESDE LA QUE SE DEBE RECOSTEAR UN ITEM (EMPRESA SELECCIONADA).*/
SELECT MAX(a1.fe_doc)
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a2.co_emp=1 AND a2.co_itm=1590 AND a1.fe_doc<='2006/01/31'
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LA FECHA DESDE LA QUE SE DEBE RECOSTEAR UN ITEM (GRUPO).*/
SELECT MAX(a1.fe_doc)
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=1590)
AND a1.fe_doc<='2006/01/09'

SELECT MAX(a1.fe_doc)
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)
WHERE a3.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=1 AND co_itm=1590)
AND a1.fe_doc<='2006/01/09'
/*----------------------------------------------------------------------------------------------------------------*/










SELECT co_emp, co_loc, co_tipDoc, co_doc, ne_numDoc, fe_doc, nd_sub
FROM tbm_cabMovInv
WHERE co_emp=1
ORDER BY co_emp, fe_doc, ne_numDoc

SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
GROUP BY by a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor
ORDER BY by a1.co_emp, a1.co_loc, a1.co_tipDoc

/*Enero-Febrero*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a2.tx_natDoc, a1.fe_ing
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=2 AND a1.fe_doc<='2006/02/28'
ORDER BY a1.co_emp, a1.fe_doc ASC, a2.tx_natDoc, a1.ne_numDoc DESC
/*Marzo en adelante*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a2.tx_natDoc, a1.fe_ing
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=2 AND a1.fe_doc>='2006/03/01'
ORDER BY a1.co_emp, a1.fe_doc ASC, a1.fe_ing
/*QUERY PARA COMPROBAR QUE EL NÚMERO DE DOCUMENTOS COINCIDA CON EL TOTAL DE DATOS COPIADOS A EXCEL.*/
SELECT COUNT(*) FROM tbm_cabMovInv


CREATE TABLE tbt_secDoc (
    ne_secGrp INT4,
    ne_secEmp INT4,
    co_emp INT4,
    co_loc INT4,
    co_tipDoc INT4,
    co_doc INT4
);

SELECT * FROM tbt_secDoc ORDER BY ne_secGrp

DROP TABLE tbt_secDoc

UPDATE tbm_cabMovInv 
SET ne_secGrp=b1.ne_secGrp
, ne_secEmp=b1.ne_secEmp
FROM tbt_secDoc AS b1
WHERE tbm_cabMovInv.co_emp=b1.co_emp AND tbm_cabMovInv.co_loc=b1.co_loc AND tbm_cabMovInv.co_tipDoc=b1.co_tipDoc AND tbm_cabMovInv.co_doc=b1.co_doc

/*----------------------------------------PRUEBAS--------------*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv WHERE co_emp=1 AND co_itm=147

