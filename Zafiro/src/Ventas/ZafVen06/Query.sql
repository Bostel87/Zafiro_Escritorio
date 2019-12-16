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

/*QUERY PARA OBTENER EL LISTADO DE ITEMS A PRESENTAR EN LA VENTANA DE CONSULTA.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm
FROM tbm_inv AS a1
WHERE a1.co_emp=1

/*QUERY PARA OBTENER EL STOCK CONSOLIDADO DE LAS BODEGAS PERMITIDAS DE CADA ITEM.*/
SELECT a2.co_itmMae, a2.co_itm, a2.tx_codAlt, a2.tx_nomItm, a2.tx_desCor, a1.nd_stkAct, a2.nd_preVta1
FROM
(
SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct
FROM tbm_equInv AS b1
INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)
INNER JOIN tbr_bodEmp AS b3 ON (b2.co_emp=b3.co_empPer AND b2.co_bod=b3.co_bodPer)
WHERE b3.co_emp=1 AND b3.co_loc=1
GROUP BY b1.co_itmMae
) AS a1,
(
SELECT c2.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_nomItm, c3.tx_desCor, c1.nd_preVta1
FROM tbm_inv AS c1
INNER JOIN tbm_equInv AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)
LEFT OUTER JOIN tbm_var AS c3 ON (c1.co_uni=c3.co_reg)
WHERE c2.co_emp=1
) AS a2
WHERE a1.co_itmMae=a2.co_itmMae
ORDER BY a2.co_itmMae

/*QUERY PARA OBTENER EL STOCK QUE TIENE EL ITEM EN CADA BODEGA DE CADA EMPRESA.*/
SELECT a2.co_emp, a3.tx_nom, a2.co_bod, a4.tx_nom, a2.co_itm, a5.tx_codAlt, a2.nd_stkAct
FROM tbm_equInv AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbm_emp AS a3 ON (a2.co_emp=a3.co_emp)
INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)
INNER JOIN tbm_inv AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_itm=a5.co_itm)
WHERE a1.co_itmMae=1
ORDER BY a1.co_itmMae, a2.co_emp, a2.co_bod
