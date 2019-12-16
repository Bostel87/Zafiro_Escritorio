/*VARIOS.*/
SELECT * FROM tbm_cabCotVen LIMIT 10
SELECT * FROM tbm_detCotVen LIMIT 10
SELECT * FROM tbm_cli LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL DETALLE DE LA COTIZACIÓN CON LOS DATOS QUE SE MOSTRARÁN.*/
SELECT d2.co_itm, d2.tx_codAlt, d2.tx_nomItm, d2.nd_can, d2.st_traAutTot, d1.nd_stkAct, d3.nd_stkAct AS nd_stkBodCen
FROM (
SELECT a1.co_itmMae, SUM(a2.nd_stkAct) AS nd_stkAct
FROM tbm_equInv AS a1
INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmp AS a3 ON (a2.co_emp=a3.co_empPer AND a2.co_bod=a3.co_bodPer)
INNER JOIN tbm_inv AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_itm=a4.co_itm)
WHERE a3.co_emp=2
AND a3.co_loc=1
AND a4.st_reg='A'
GROUP BY a1.co_itmMae
) AS d1 INNER JOIN (
SELECT b2.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b4.co_reg, b4.nd_can, b4.st_traAutTot
FROM tbm_inv AS b1
INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)
LEFT OUTER JOIN tbm_detCotVen AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_itm=b4.co_itm)
WHERE b4.co_emp=2
AND b4.co_loc=1
AND b4.co_cot=6499
AND b1.st_reg='A'
) AS d2 ON (d1.co_itmMae=d2.co_itmMae)
INNER JOIN (
SELECT c1.co_itmMae, SUM(c2.nd_stkAct) AS nd_stkAct
FROM tbm_equInv AS c1
INNER JOIN tbm_invBod AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm)
INNER JOIN tbr_bodEmp AS c3 ON (c2.co_emp=c3.co_empPer AND c2.co_bod=c3.co_bodPer)
INNER JOIN tbm_inv AS c4 ON (c1.co_emp=c4.co_emp AND c1.co_itm=c4.co_itm)
WHERE c3.co_emp=1 --QUEMADO
AND c3.co_loc=1 --QUEMADO
AND c4.st_reg='A'
GROUP BY c1.co_itmMae
) AS d3 ON (d1.co_itmMae=d3.co_itmMae)
WHERE d1.co_itmMae IS NOT NULL
ORDER BY d2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

