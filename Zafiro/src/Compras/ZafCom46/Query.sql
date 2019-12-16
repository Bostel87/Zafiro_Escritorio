/*VARIOS.*/
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbr_bodEmp LIMIT 10
SELECT * FROM tbm_invBod LIMIT 10
SELECT * FROM tbm_equInv WHERE co_itmMae=1
SELECT * FROM tbm_invBod WHERE co_emp=4 AND co_itm=2204
SELECT * FROM tbm_bod WHERE co_emp=4 ORDER BY co_bod
SELECT * FROM tbm_invMae WHERE co_itmMae=1
SELECT * FROM tbm_emp LIMIT 10
SELECT * FROM tbm_bod LIMIT 10
SELECT * FROM tbm_var LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS BODEGAS (EMPRESA).*/
SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom
FROM tbm_emp AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)
WHERE a1.co_emp=1
ORDER BY a1.co_emp, a2.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL STOCK POR BODEGA (EMPRESA)*/
SELECT Null AS co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor, b2.nd_stkAct, b3.nd_stkAct
FROM
(
/*QUERY PARA OBTENER LOS DATOS COMUNES.*/
SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor
FROM tbm_inv AS a1
LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)
WHERE a1.co_emp=1
) AS b1 INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DE LA PRIMERA BODEGA.*/
SELECT a1.co_emp, a1.co_itm, a1.nd_stkAct
FROM tbm_invBod AS a1
WHERE a1.co_emp=1 AND a1.co_bod=1
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DE LA SEGUNDA BODEGA.*/
SELECT a1.co_emp, a1.co_itm, a1.nd_stkAct
FROM tbm_invBod AS a1
WHERE a1.co_emp=1 AND a1.co_bod=2
) AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_itm=b3.co_itm)
ORDER BY b1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL STOCK POR BODEGA (GRUPO)*/
SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor, b2.nd_stkAct, b3.nd_stkAct, b4.nd_stkAct, b5.nd_stkAct
FROM
(
/*QUERY PARA OBTENER LOS DATOS COMUNES.*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor
FROM tbm_inv AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)
WHERE a1.co_emp=0
) AS b1 INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DE LA "Grupo - Bodega California".*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a1.co_emp=0 AND a1.co_bod=1
) AS b2 ON (b1.co_itmMae=b2.co_itmMae) INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DE LA "Grupo - Bodega Quito".*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a1.co_emp=0 AND a1.co_bod=3
) AS b3 ON (b1.co_itmMae=b3.co_itmMae) INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DE LA "Tuval - Bodega California".*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a1.co_emp=1 AND a1.co_bod=1
) AS b4 ON (b1.co_itmMae=b4.co_itmMae) INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DE LA "Castek - Bodega Quito".*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a1.co_emp=2 AND a1.co_bod=1
) AS b5 ON (b1.co_itmMae=b5.co_itmMae)
ORDER BY b1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/
