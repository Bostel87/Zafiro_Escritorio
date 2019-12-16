/*VARIOS.*/
SELECT * FROM tbm_rptSis LIMIT 10
SELECT * FROM tbr_rptSisPrg LIMIT 10
SELECT * FROM tbm_mnuSis WHERE tx_nom LIKE 'Ordenes%' LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS REPORTES ASIGNADOS AL PROGRAMA.*/
SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutRpt, a1.tx_nomRpt, 'A' AS st_reg
FROM tbm_rptSis AS a1
INNER JOIN tbr_rptSisPrg AS a2 ON (a1.co_rpt=a2.co_rpt)
WHERE a2.co_mnu=789 AND a1.st_reg='A'
ORDER BY a1.co_rpt
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS REPORTES ASIGNADOS AL USUARIO.*/
SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutRpt, a1.tx_nomRpt, a2.st_reg
FROM tbm_rptSis AS a1
INNER JOIN tbr_rptSisUsr AS a2 ON (a1.co_rpt=a2.co_rpt)
WHERE a2.co_emp=1 AND a2.co_loc=1 AND a2.co_mnu=789 AND a2.co_usr=1 AND a1.st_reg='A' AND a2.st_reg IN ('A','S')
ORDER BY a2.ne_ord
/*----------------------------------------------------------------------------------------------------------------*/