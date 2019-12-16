/*Varios.*/
SELECT * FROM tbm_cabCotVen LIMIT 10
SELECT * FROM tbm_detCotVen LIMIT 10
SELECT * FROM tbm_pagCotVen LIMIT 10
SELECT * FROM tbm_cli LIMIT 10
SELECT * FROM tbm_cabForPag LIMIT 10
SELECT * FROM tbm_usr LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBRENER EL LISTADO DE COTIZACIONES DE VENTA.*/
SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot, a1.co_cli, a2.tx_nom, a1.co_forPag, a3.tx_des, a1.co_ven, a4.tx_nom AS a4_tx_nom
, a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.st_reg
FROM tbm_cabCotVen AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)
INNER JOIN tbm_cabForPag AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_forPag=a3.co_forPag)
INNER JOIN tbm_usr AS a4 ON (a1.co_ven=a4.co_usr)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.fe_cot BETWEEN '2006/12/01' AND '2006/12/31'
ORDER BY a1.co_emp, a1.co_loc, a1.co_cot
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBRENER EL LISTADO DE COTIZACIONES DE VENTA (GRUPO).*/
SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot, a1.co_cli, a2.tx_nom, a1.co_forPag, a3.tx_des, a1.co_ven, a4.tx_nom AS a4_tx_nom
, a1.nd_sub, a1.nd_valIva, a1.nd_tot, a1.st_reg
FROM tbm_cabCotVen AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)
INNER JOIN tbm_cabForPag AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_forPag=a3.co_forPag)
INNER JOIN tbm_usr AS a4 ON (a1.co_ven=a4.co_usr)
WHERE a1.fe_cot BETWEEN '2007/01/01' AND '2007/01/31'
ORDER BY a1.co_emp, a1.co_loc, a1.co_cot
/*----------------------------------------------------------------------------------------------------------------*/

