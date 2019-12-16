/*VARIOS.*/
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbr_bodEmp LIMIT 10
SELECT * FROM tbm_invBod LIMIT 10
SELECT * FROM tbm_equInv WHERE co_itmMae=1
SELECT * FROM tbm_invBod WHERE co_emp=4 AND co_itm=2204
SELECT * FROM tbm_bod WHERE co_emp=4 ORDER BY co_bod
SELECT * FROM tbm_invMae WHERE co_itmMae=1
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_detMovInv LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10

/*QUERY PARA OBTENER EL CÓDIGO MAESTRO Y LOS CÓDIGOS QUE TIENEN EN CADA EMPRESA.*/
SELECT a1.co_itmMae, a2.co_emp, a2.co_bod, a2.co_itm, a2.nd_stkAct
FROM tbm_equInv AS a1, tbm_invBod AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm
ORDER BY a1.co_itmMae, a2.co_emp, a2.co_bod, a2.co_itm
LIMIT 10

/*QUERY PARA OBTENER EL CÓDIGO MAESTRO Y LOS CÓDIGOS QUE TIENEN EN CADA EMPRESA (CON STOCK).*/
SELECT a1.co_itmMae, a2.co_emp, a2.co_bod, a2.co_itm, a2.nd_stkAct
FROM tbm_equInv AS a1, tbm_invBod AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm
AND a2.nd_stkAct>0 
ORDER BY a1.co_itmMae, a2.co_emp, a2.co_bod, a2.co_itm
LIMIT 10

/*QUERY PARA OBTENER EL CODIGO MAESTRO DEL ITEM.*/
SELECT a1.co_itmMae
FROM tbm_equInv AS a1
WHERE a1.co_emp=1 AND a1.co_itm=2202

/*QUERY PARA OBTENER LAS BODEGAS ASOCIADAS AL LOCAL DE UNA EMPRESA.*/
SELECT a1.co_empPer, a1.co_bodPer
FROM tbr_bodEmp AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1

/*QUERY PARA OBTENER EL INVENTARIO SÓLO DE LOS ITEMS DE LAS BODEGAS ASOCIADAS.*/
SELECT a1.co_itmMae, a2.co_emp, a2.co_bod, a2.co_itm, a2.nd_stkAct
FROM tbm_equInv AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)
WHERE a3.co_emp=1 AND a3.co_loc=1 AND a1.co_itmMae=1
ORDER BY a1.co_itmMae, a2.co_emp, a2.co_bod, a2.co_itm

/*QUERY PARA OBTENER EL STOCK CONSOLIDADO DE UN ITEM EN PARTICULAR.*/
SELECT a1.co_itmMae, SUM(a2.nd_stkAct) AS nd_stkAct
FROM tbm_equInv AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)
WHERE a3.co_emp=1 AND a3.co_loc=1 AND a1.co_itmMae=
(
SELECT b1.co_itmMae
FROM tbm_equInv AS b1
WHERE b1.co_emp=1 AND b1.co_itm=487
)
AND a2.nd_stkAct>0
GROUP BY a1.co_itmMae

/*QUERY PARA ACTUALIZAR EL STOCK CONSOLIDADO.*/
UPDATE tbm_invMae
SET nd_stkAct=10
WHERE co_itmMae=
(
SELECT b1.co_itmMae
FROM tbm_equInv AS b1
WHERE b1.co_emp=1 AND b1.co_itm=2202
)

/*QUERY PARA OBTENER EL STOCK CONSOLIDADO DE UN ITEM SIN IMPORTAR LAS BODEGAS.*/
SELECT a1.nd_stkAct
FROM tbm_invMae As a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_itmMae=a2.co_itmMae)
WHERE a2.co_emp=1 AND a2.co_itm=5

/*---------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS VENTAS DE LOS ITEMS.*/
SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a3.st_cosUniCal, a2.nd_can, a2.nd_cosUni, a2.nd_porDes
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
WHERE a2.co_itm=864 AND a1.st_reg IN ('A','R','C','F') AND a1.fe_doc>='2005/01/01'
ORDER BY a1.fe_doc, a1.co_emp, a1.ne_ordDoc

SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a3.tx_desCor, a2.co_doc, a2.co_reg, a3.st_cosUniCal, a2.nd_can, a2.nd_cosUni, a2.nd_porDes, a2.nd_exiCon, a2.nd_valExiCon
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
WHERE a2.co_itm=2644 AND a1.st_reg IN ('A','R','C','F') AND a1.fe_doc>='2005/01/01'
ORDER BY a1.fe_doc, a1.co_emp, a1.ne_ordDoc


select * from tbm_invMae where co_itmMae=1467