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
/*QUERY PARA OBTENER EL STOCK CONSOLIDADO DE LOS ITEMS.*/
SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor, a1.nd_stkAct, a1.nd_preVta1
FROM tbm_inv AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)
WHERE a1.co_emp=0
ORDER BY a1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL STOCK QUE TIENE EL ITEM EN CADA BODEGA DE CADA EMPRESA.*/
SELECT b2.co_emp, b3.tx_nom, b2.co_bod, b4.tx_nom AS b4_tx_nom, b5.co_itm, b5.tx_codAlt, b5.tx_codAlt2, b2.nd_stkAct
FROM tbm_equInv AS b1
INNER JOIN (
/*QUERY PARA OBTENER EL STOCK DEL ITEM EN CADA BODEGA (GRUPO)*/
SELECT a2.co_itmMae, a3.co_empGrp AS co_emp, a3.co_bodGrp AS co_bod, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)
WHERE a2.co_itmMae=6821 AND a3.co_empGrp=0
GROUP BY a2.co_itmMae, a3.co_empGrp, a3.co_bodGrp
UNION ALL
/*QUERY PARA OBTENER EL STOCK DEL ITEM EN CADA BODEGA (EMPRESA)*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_bod, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a2.co_itmMae=6821 AND a2.co_emp<>0
GROUP BY a2.co_itmMae, a1.co_emp, a1.co_bod
) AS b2 ON (b1.co_itmMae=b2.co_itmMae AND b1.co_emp=b2.co_emp)
INNER JOIN tbm_emp AS b3 ON (b2.co_emp=b3.co_emp)
INNER JOIN tbm_bod AS b4 ON (b2.co_emp=b4.co_emp AND b2.co_bod=b4.co_bod)
INNER JOIN tbm_inv AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_itm=b5.co_itm)
ORDER BY b1.co_itmMae, b2.co_emp, b2.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE ITEMS A PRESENTAR EN LA VENTANA DE CONSULTA.*/
SELECT a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a1.tx_desCor 
FROM tbm_inv as a LEFT JOIN tbm_var as a1 ON (a1.co_reg=a.co_uni) 
WHERE  a.co_emp=1
AND a.st_reg  IN ('A') 
ORDER BY a.tx_codalt 
/*----------------------------------------------------------------------------------------------------------------*/
