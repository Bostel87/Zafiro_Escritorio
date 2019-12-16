/*VARIOS.*/
SELECT * FROM tbm_cabForPag LIMIT 10
SELECT * FROM tbm_detForPag LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LA ÚLTIMA FORMA DE PAGO.*/
SELECT MAX(a1.co_forPag)
FROM tbm_cabForPag AS a1
WHERE a1.co_emp=1
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

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA ACTUALIZR EL CAMPO "nd_pes" EN LA TABLA "tbm_cabForPag".
    nd_pes=MAX(ne_diaCre) + ne_tipForPag + (ne_tipForPag / ne_numPag) + SUM(ne_diaCre) / (MAX(ne_diaCre) * ne_numPag)
*/
UPDATE tbm_cabForPag
SET nd_pes=b1.nd_pes
FROM
(
SELECT a1.co_emp, a1.co_forPag, a1.tx_des, (a2.ne_maxDiaCre + a1.ne_tipForPag + (CAST(a1.ne_tipForPag AS NUMERIC(18,2))/a1.ne_numPag) + (CAST(a3.ne_sumDiaCre AS NUMERIC(18,2))/(a2.ne_maxDiaCre*a1.ne_numPag))) AS nd_pes
FROM tbm_cabForPag AS a1 INNER JOIN (SELECT co_emp, co_forPag, MAX(ne_diaCre) AS ne_maxDiaCre FROM tbm_detForPag GROUP BY co_emp, co_forPag) AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
INNER JOIN (SELECT co_emp, co_forPag, SUM(ne_diaCre) AS ne_sumDiaCre FROM tbm_detForPag GROUP BY co_emp, co_forPag HAVING SUM(ne_diaCre)<>0) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_forPag=a3.co_forPag)
WHERE a1.ne_tipForPag IN (2, 3)
ORDER BY a1.co_emp, a1.tx_des
) AS b1
WHERE tbm_cabForPag.co_emp=b1.co_emp AND tbm_cabForPag.co_forPag=b1.co_forPag;

UPDATE tbm_cabForPag SET st_regRep='P' WHERE st_regRep<>'P';
UPDATE tbm_detForPag SET st_regRep='P' WHERE st_regRep<>'P';
/*----------------------------------------------------------------------------------------------------------------*/

