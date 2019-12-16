/*VARIOS.*/
SELECT * FROM tbm_cabForPag LIMIT 10
SELECT * FROM tbm_forPagCli LIMIT 10
SELECT * FROM tbm_cli LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS FORMA DE PAGO QUE APARECE EN LA COTIZACIÓN.*/
SELECT a2.co_forPag, a2.tx_des
FROM tbm_cabCotVen AS a1
INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=24455
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS FORMAS DE PAGO AUTORIZADAS PARA EL CLIENTE.*/
SELECT a1.co_forPag, a1.tx_des, a2.st_reg
FROM tbm_cabForPag AS a1
INNER JOIN tbm_forPagCli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=1 AND a2.co_cli=(SELECT co_cli FROM tbm_cabCotVen WHERE co_emp=1 AND co_loc=1 AND co_cot=24455)
ORDER BY a1.co_emp, a1.co_forPag
/*----------------------------------------------------------------------------------------------------------------*/