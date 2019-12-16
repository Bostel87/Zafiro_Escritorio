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

/*QUERY PARA OBTENER LOS DATOS DEL ITEM.*/
SELECT a2.co_itmMae, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_cosUni
FROM tbm_inv AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)
WHERE a1.co_emp=1 AND a1.tx_codAlt='681-140I'

/*QUERY PARA OBTENER LAS BODEGAS.*/
SELECT a1.co_bod, a1.tx_nom
FROM tbm_bod AS a1
WHERE a1.co_emp=0 AND a1.st_reg='A'
ORDER BY a1.co_bod

/*QUERY PARA OBTENER LAS BODEGAS CON EL STOCK DEL ITEM.*/
SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct
FROM tbm_bod AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)
WHERE a1.co_emp=0 AND a1.st_reg='A' AND a2.co_itm=2
ORDER BY a1.co_bod

/*QUERY PARA OBTENER LAS BODEGAS DE ACUERDO AL LOCAL.*/
SELECT a1.co_bod, a1.tx_nom
FROM tbm_bod AS a1
INNER JOIN tbr_bodLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)
WHERE a1.co_emp=0 AND a2.co_loc=1 AND a1.st_reg='A'
ORDER BY a1.co_bod

/*QUERY PARA OBTENER LAS BODEGAS CON EL STOCK DEL ITEM DE ACUERDO AL LOCAL.*/
SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct
FROM tbm_bod AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)
INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)
WHERE a1.co_emp=0 AND a3.co_loc=1 AND a1.st_reg='A' AND a2.co_itm=2
ORDER BY a1.co_bod

/*QUERY PARA OBTENER EL DETALLE DEL REGISTRO.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.tx_uniMed, a1.co_bod, a2.tx_nom, a1.nd_can, a1.nd_cosUni 
FROM tbm_detMovInv AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)
WHERE a1.co_emp=0 AND a1.co_loc=1 AND a1.co_tipDoc=46 AND a1.co_doc=12
ORDER BY a1.co_reg

/*QUERY PARA PROBAR COMO REGENERAR EL CAMPO "ne_numFil".*/
SELECT co_emp, co_loc, co_tipDoc, co_doc, co_reg, ROUND((co_reg+1)/2,0) AS ne_numFil, co_itm, nd_can 
FROM tbm_detMovInv 
WHERE co_emp=0 AND co_loc=1 AND co_tipDoc=46
ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg

/*QUERY PARA REGENERAR EL CAMPO "ne_numFil".*/
UPDATE tbm_detMovInv SET ne_numFil=ROUND((co_reg+1)/2,0)
WHERE co_emp=0 AND co_loc=1 AND co_tipDoc=46

/*QUERY PARA OBTENER EL STOCK DEL ITEM EN LA BODEGA ESPECIFICADA.*/
SELECT a1.nd_stkAct FROM tbm_invBod AS a1 WHERE a1.co_emp=0 AND a1.co_bod=1 AND a1.co_itm=2402

/*----------------------------------------------------------------------------*/
SELECT * FROM tbm_bod order by  co_emp, co_bod
SELECT * FROM tbm_cabMovInv WHERE co_emp=0 and co_loc=1 and co_tipDoc=46
SELECT * FROM tbm_detMovInv WHERE co_emp=0 and co_loc=1 and co_tipDoc=46
DELETE FROM tbm_cabMovInv WHERE co_emp=0 and co_loc=1 and co_tipDoc=46
DELETE FROM tbm_detMovInv WHERE co_emp=0 and co_loc=1 and co_tipDoc=46
SELECT * FROM tbm_inv WHERE co_emp=0 AND co_itm=1

/*QUERY PARA CREAR ITEMS DE PRUEBA.*/
Código	Item	Cantidad	Costo
2	001-098L	100	50.25
3	001-102L	300	3.27
4	001-103L	800.63	1.25
5	001-105L	102	50
6	001-107L	156.87	0.63
SELECT * FROM tbm_inv WHERE co_emp=0 and tx_codAlt='001-107L'
/*QUERY PARA ACTUALIZAR ITEMS DE PRUEBA EN "tbm_invBod".*/
UPDATE tbm_invBod SET nd_stkAct=100 WHERE co_emp=0 AND co_bod=1 AND co_itm=2
UPDATE tbm_invBod SET nd_stkAct=300 WHERE co_emp=0 AND co_bod=1 AND co_itm=3
UPDATE tbm_invBod SET nd_stkAct=800.63 WHERE co_emp=0 AND co_bod=1 AND co_itm=4
UPDATE tbm_invBod SET nd_stkAct=102 WHERE co_emp=0 AND co_bod=1 AND co_itm=5
UPDATE tbm_invBod SET nd_stkAct=156.87 WHERE co_emp=0 AND co_bod=1 AND co_itm=6
/*QUERY PARA ACTUALIZAR ITEMS DE PRUEBA EN "tbm_inv".*/
UPDATE tbm_inv SET nd_stkAct=100, nd_cosUni=50.25 WHERE co_emp=0 AND co_itm=2
UPDATE tbm_inv SET nd_stkAct=300, nd_cosUni=3.27 WHERE co_emp=0 AND co_itm=3
UPDATE tbm_inv SET nd_stkAct=800.63, nd_cosUni=1.25 WHERE co_emp=0 AND co_itm=4
UPDATE tbm_inv SET nd_stkAct=102, nd_cosUni=50 WHERE co_emp=0 AND co_itm=5
UPDATE tbm_inv SET nd_stkAct=156.87, nd_cosUni=0.63 WHERE co_emp=0 AND co_itm=6


