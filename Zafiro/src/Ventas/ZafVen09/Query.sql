/*VARIOS.*/
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_detMovInv LIMIT 10
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_loc LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS VENTAS DEL PERIODO SELECCIONADO.*/
SELECT -SUM(a1.nd_sub) AS nd_sub, -SUM(a1.nd_valIva) AS nd_valIva, -SUM(a1.nd_tot) AS nd_tot
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a2.ne_mod=1 AND a1.st_reg IN ('A','R','C','F') AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')
AND a1.fe_doc BETWEEN '2006/01/01' AND '2006/01/31'
/*----------------------------------------------------------------------------------------------------------------*/
