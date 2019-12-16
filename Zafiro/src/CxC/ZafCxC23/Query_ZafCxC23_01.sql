/*VARIOS.*/
SELECT * FROM tbm_cli LIMIT 10
SELECT * FROM tbm_cabForPag LIMIT 10
SELECT * FROM tbm_forPagCli LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS FORMAS DE PAGO ASIGNADAS AL CLIENTE.*/
SELECT 'N' AS st_aut, a1.co_forPag, a1.tx_des, 'N' AS st_reg
FROM tbm_cabForPag AS a1
WHERE a1.co_emp=1 AND a1.st_reg IN ('A','P') AND a1.co_forPag NOT IN (SELECT co_forPag FROM tbm_forPagCli WHERE co_emp=1 AND co_cli=24)
UNION ALL
SELECT 'S' AS st_aut, a1.co_forPag, a1.tx_des, a2.st_reg
FROM tbm_cabForPag AS a1
INNER JOIN tbm_forPagCli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=1 AND a2.co_cli=24 AND a1.st_reg IN ('A','P')
ORDER BY co_forPag
/*----------------------------------------------------------------------------------------------------------------*/



