/*VARIOS.*/
SELECT * FROM tbm_cli LIMIT 10
SELECT * FROM tbm_cabForPag LIMIT 10
SELECT * FROM tbm_forPagCli LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE CLIENTES QUE ESTÁN REPETIDOS.*/
SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a2.ne_numVec
FROM tbm_cli AS a1
INNER JOIN
(
SELECT co_emp, tx_ide, COUNT(*) AS ne_numVec FROM tbm_cli GROUP BY co_emp, tx_ide HAVING COUNT(*)>1 ORDER BY co_emp, tx_ide
) AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)
ORDER BY a1.co_emp, a1.tx_nom
/*----------------------------------------------------------------------------------------------------------------*/
