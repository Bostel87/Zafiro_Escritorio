/*VARIOS.*/
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_emp LIMIT 10
SELECT * FROM tbm_var LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL STOCK CONSOLIDADO DE LOS ITEMS.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.co_reg, a2.tx_desCor, a1.nd_pesItmKgr
FROM tbm_inv AS a1
LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)
WHERE a1.co_emp=1
AND a1.st_reg='A'
ORDER BY a1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE ITEMS A PRESENTAR EN LA VENTANA DE CONSULTA.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm
FROM tbm_inv AS a1
WHERE a1.co_emp=1
/*----------------------------------------------------------------------------------------------------------------*/
