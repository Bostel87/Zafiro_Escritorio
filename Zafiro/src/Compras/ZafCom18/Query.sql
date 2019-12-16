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
/*QUERY PARA OBTENER EL LISTADO DE ITEMS A PRESENTAR EN LA VENTANA DE CONSULTA.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor
FROM tbm_inv AS a1
LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)
WHERE a1.co_emp=1
AND a1.st_reg='A'
ORDER BY a1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE INVENTARIO.*/
SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a4.co_reg, a4.tx_desCor, a3.nd_stkAct, a1.nd_preVta1, a1.nd_marUti, a1.nd_pesItmKgr 
FROM tbm_inv AS a1 
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) 
LEFT OUTER JOIN ( 
SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct 
FROM tbm_equInv AS b1 
INNER JOIN tbm_inv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) 
--WHERE b2.co_emp=1 
WHERE b2.co_emp<>0 
GROUP BY b1.co_itmMae 
) AS a3 ON (a2.co_itmMae=a3.co_itmMae) 
LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg) 
WHERE a1.co_emp=1 AND a1.st_reg='A' 
ORDER BY a1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/
