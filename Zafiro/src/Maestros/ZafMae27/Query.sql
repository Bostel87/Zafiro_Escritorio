/*VARIOS.*/
SELECT * FROM tbm_mnuSis LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbr_tipDocPrg LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS PROGRAMAS.*/
SELECT a1.co_mnu, a1.tx_nom
FROM tbm_mnuSis AS a1
WHERE a1.tx_tipMnu='C'
AND a1.st_reg='A'
ORDER BY a1.ne_ubi
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS USUARIOS.*/
SELECT a1.co_usr, a1.tx_usr, a1.tx_nom
FROM tbm_usr AS a1
WHERE a1.st_usrSis='S'
AND a1.st_reg='A'
ORDER BY a1.tx_usr
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS TIPOS DE DOCUMENTOS POR PROGRAMA (INSERTAR).*/
SELECT 'N' AS st_sel, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, 'N' AS st_pre
FROM tbm_cabTipDoc AS a1
INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1
AND a1.co_loc=1
AND a2.co_mnu=256
AND a1.st_reg='A'
ORDER BY a1.tx_desCor
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DATOS DE LA TABLA "tbr_tipDocUsr".*/
SELECT a1.co_emp, a1.co_loc, a1.co_mnu, a2.tx_nom AS a2_tx_nom, a1.co_usr, a3.tx_usr, a3.tx_nom AS a3_tx_nom
FROM tbr_tipDocUsr AS a1
INNER JOIN tbm_mnuSis AS a2 ON (a1.co_mnu=a2.co_mnu)
INNER JOIN tbm_usr AS a3 ON (a1.co_usr=a3.co_usr)
WHERE a1.co_emp=1
AND a1.co_loc=1
GROUP BY a1.co_emp, a1.co_loc, a1.co_mnu, a2.tx_nom, a1.co_usr, a3.tx_usr, a3.tx_nom
ORDER BY a1.co_mnu, a1.co_usr
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS TIPOS DE DOCUMENTOS POR USUARIO (CONSULTAR).*/
SELECT 'S' AS st_sel, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a2.st_reg
FROM tbm_cabTipDoc AS a1
INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1
AND a1.co_loc=1
AND a2.co_mnu=327
AND a2.co_usr=30
ORDER BY a1.tx_desCor
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS TIPOS DE DOCUMENTOS POR USUARIO (MODIFICAR).*/
SELECT 'N' AS st_sel, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, 'N' AS st_pre
FROM tbm_cabTipDoc AS a1
INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1
AND a1.co_loc=1
AND a2.co_mnu=327
AND a1.st_reg='A'
AND a1.co_tipDoc NOT IN (SELECT co_tipDoc FROM tbr_tipDocUsr WHERE co_emp=1 AND co_loc=1 AND co_mnu=327 AND co_usr=36)
UNION ALL
SELECT 'S' AS st_sel, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a2.st_reg
FROM tbm_cabTipDoc AS a1
INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1
AND a1.co_loc=1
AND a2.co_mnu=327
AND a2.co_usr=36
ORDER BY tx_desCor
/*----------------------------------------------------------------------------------------------------------------*/
