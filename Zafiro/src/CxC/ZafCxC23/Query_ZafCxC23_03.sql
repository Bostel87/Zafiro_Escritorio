/*VARIOS.*/
SELECT * FROM tbm_cli LIMIT 10
SELECT * FROM tbm_solCre LIMIT 10
SELECT * FROM tbm_refComSolCre LIMIT 10
SELECT * FROM tbm_refBanSolCre LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LA CABECERA DEL DOCUMENTO.*/
SELECT a1.co_emp, a1.co_sol, a1.co_cli, a2.tx_nom, a1.fe_sol, a2.tx_obsInfBurCre, a1.st_reg
FROM tbm_solCre AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)
WHERE a1.co_emp=1 AND a1.co_sol=1
ORDER BY a1.co_emp, a1.co_sol
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE REFERENCIAS COMERCIALES.*/
SELECT a1.co_ref, a1.tx_nom, a1.tx_tie, a1.tx_cupCre, a1.tx_plaCre, a1.tx_forPag, a1.st_pro, a1.ne_numPro
FROM tbm_refComSolCre AS a1
WHERE a1.co_emp=1 AND a1.co_sol=1
ORDER BY a1.co_emp, a1.co_sol, a1.co_ref
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE REFERENCIAS BANCARIAS.*/
SELECT a1.co_ref, a1.tx_nom, a1.tx_salProCta, a1.st_pro, a1.ne_numPro, a1.st_creDir, a1.tx_monCreDir, a1.st_creInd, a1.tx_monCreInd
FROM tbm_refBanSolCre AS a1
WHERE a1.co_emp=1 AND a1.co_sol=1
ORDER BY a1.co_emp, a1.co_sol, a1.co_ref
/*----------------------------------------------------------------------------------------------------------------*/

