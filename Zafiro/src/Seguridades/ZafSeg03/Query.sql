/*VARIOS.*/
SELECT * FROM tbm_usr LIMIT 10
SELECT * FROM tbm_mnuSis LIMIT 10
SELECT * FROM tbr_perUsr LIMIT 10

/*QUERY PARA OBTENER LOS DATOS DEL USUARIO.*/
SELECT a1.co_usr, a1.tx_usr, a1.tx_nom
FROM tbm_usr AS a1
WHERE a1.st_reg='A'

/*QUERY PARA OBTENER LAS OPCIONES DEL MENU.*/
SELECT a1.co_mnu, ne_niv, a1.tx_nom
FROM tbm_mnuSis AS a1
WHERE a1.st_reg='A' AND tx_tipMnu<>'S'
ORDER BY a1.ne_ubi

/*QUERY PARA OBTENER LOS USUARIOS CON PERMISOS.*/
SELECT a1.co_emp, a1.co_loc, a1.co_usr
FROM tbr_perUsr a1
WHERE a1.co_emp=1 AND a1.co_loc=1
GROUP BY a1.co_emp, a1.co_loc, a1.co_usr
ORDER BY a1.co_emp, a1.co_loc, a1.co_usr

/*QUERY PARA OBTENER LOS DATOS DE UN USUARIO.*/
SELECT a1.co_emp, a1.co_loc, a1.co_usr, a2.tx_usr, a2.tx_nom, a2.st_reg
FROM tbr_perUsr a1, tbm_usr AS a2
WHERE a1.co_usr=a2.co_usr
AND a1.co_emp=1 AND a1.co_loc=1 AND a1.co_usr=2
GROUP BY a1.co_emp, a1.co_loc, a1.co_usr, a2.tx_usr, a2.tx_nom, a2.st_reg
ORDER BY a1.co_emp, a1.co_loc, a1.co_usr

/*QUERY PARA OBTENER LOS PERMISOS DE UN USUARIO (CONSULTAS).*/
SELECT a1.co_mnu, a1.ne_niv, a1.tx_nom, a1.ne_ubi, 2 AS ne_numOcu
FROM tbm_mnuSis AS a1
INNER JOIN tbr_perUsr AS a2 ON (a1.co_mnu=a2.co_mnu)
WHERE a2.co_emp=1 AND a2.co_loc=1 AND a2.co_usr=2 AND a1.st_reg='A' AND a1.tx_tipMnu<>'S'

/*QUERY PARA OBTENER LOS PERMISOS DE UN USUARIO (MODIFICACIÓN).*/
SELECT a1.co_mnu, a1.ne_niv, a1.tx_nom, COUNT(a1.co_mnu) AS ne_numOcu
FROM
(
SELECT b1.co_mnu, b1.ne_niv, b1.tx_nom, b1.ne_ubi
FROM tbm_mnuSis AS b1
WHERE b1.st_reg='A' AND b1.tx_tipMnu<>'S'
UNION ALL
SELECT c1.co_mnu, c1.ne_niv, c1.tx_nom, c1.ne_ubi
FROM tbm_mnuSis AS c1
INNER JOIN tbr_perUsr AS c2 ON (c1.co_mnu=c2.co_mnu)
WHERE c2.co_emp=1 AND c2.co_loc=1 AND c2.co_usr=2 AND c1.st_reg='A' AND c1.tx_tipMnu<>'S'
) AS a1
GROUP BY a1.co_mnu, a1.ne_niv, a1.tx_nom, a1.ne_ubi
ORDER BY a1.ne_ubi