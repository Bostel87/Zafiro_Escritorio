/*VARIOS.*/
SELECT * FROM tbm_cli LIMIT 10
SELECT * FROM tbm_cabForPag

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE CLIENTES (GRUPO).*/
SELECT Null AS co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir
FROM
(
SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide
FROM
(
SELECT MIN(co_emp) AS co_emp, tx_ide
FROM tbm_cli
GROUP BY tx_ide
) AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)
GROUP BY a2.co_emp, a2.tx_ide
) AS b1 
INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)
ORDER BY b2.tx_nom
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DATOS DE CREDITO DE CADA CLIENTE.*/
SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen
FROM tbm_cli AS a1, tbm_cabForPag AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag
AND a1.co_emp=1 AND a1.st_cli='S' AND a1.st_reg='A'
ORDER BY a1.tx_nom
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DATOS DE CREDITO DE CADA CLIENTE (GRUPO).*/
SELECT b1.co_emp, b1.tx_ide
, b2.co_emp, b2.co_cli, b2.tx_ide, b2.tx_nom, b2.co_forPag, b2.tx_des, b2.nd_monCre, b2.nd_maxDes, b2.ne_diaGra, b2.ne_diaGraChqFec, b2.st_cieRetPen
, b3.co_emp, b3.co_cli, b3.tx_ide, b3.tx_nom, b3.co_forPag, b3.tx_des, b3.nd_monCre, b3.nd_maxDes, b3.ne_diaGra, b3.ne_diaGraChqFec, b3.st_cieRetPen
, b4.co_emp, b4.co_cli, b4.tx_ide, b4.tx_nom, b4.co_forPag, b4.tx_des, b4.nd_monCre, b4.nd_maxDes, b4.ne_diaGra, b4.ne_diaGraChqFec, b4.st_cieRetPen
, b5.co_emp, b5.co_cli, b5.tx_ide, b5.tx_nom, b5.co_forPag, b5.tx_des, b5.nd_monCre, b5.nd_maxDes, b5.ne_diaGra, b5.ne_diaGraChqFec, b5.st_cieRetPen
FROM
(

/*GRUPO*/
SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide
FROM
(
SELECT MIN(co_emp) AS co_emp, tx_ide
FROM tbm_cli
GROUP BY tx_ide
--ORDER BY tx_ide
) AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)
--WHERE a2.st_cli='S' AND a2.st_reg='A'
GROUP BY a2.co_emp, a2.tx_ide

) AS b1 LEFT OUTER JOIN (
/*TUVAL S.A.*/
SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen
FROM tbm_cli AS a1
INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=1 AND a1.st_cli='S' AND a1.st_reg='A' --AND a1.co_cli=33
) AS b2 ON (b1.tx_ide=b2.tx_ide) LEFT OUTER JOIN (
/*CASTEK S.A.*/
SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen
FROM tbm_cli AS a1
INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=2 AND a1.st_cli='S' AND a1.st_reg='A' --AND a1.co_cli=27
) AS b3 ON (b1.tx_ide=b3.tx_ide) LEFT OUTER JOIN (
/*NOSITOL S.A.*/
SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen
FROM tbm_cli AS a1
INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=3 AND a1.st_cli='S' AND a1.st_reg='A' --AND a1.co_cli=28
) AS b4 ON (b1.tx_ide=b4.tx_ide) LEFT OUTER JOIN (
/*DIMULTI S.A.*/
SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen
FROM tbm_cli AS a1
INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)
WHERE a1.co_emp=4 AND a1.st_cli='S' AND a1.st_reg='A' --AND a1.co_cli=30
) AS b5 ON (b1.tx_ide=b5.tx_ide)

WHERE b2.co_emp IS NOT NULL OR b3.co_emp IS NOT NULL OR b4.co_emp IS NOT NULL OR b5.co_emp IS NOT NULL

ORDER BY b1.tx_ide
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA INSERTAR EN EL HISTÓRICO LOS DATOS DE TODOS LOS CLIENTES MODIFICADOS (GRUPO).*/
INSERT INTO tbh_cli(co_emp, co_cli, co_his, st_cli, st_prv, tx_tipIde, tx_ide, tx_nom, tx_dir
, tx_refUbi, tx_tel1, tx_tel2, tx_tel3, tx_tel, tx_fax, tx_cas, tx_dirWeb, tx_corEle, tx_tipPer, co_tipPer, co_ciu, co_zon
, tx_perCon, tx_telCon, tx_corEleCon, co_ven, co_grp, co_forPag, nd_monCre, ne_diaGra, ne_diaGraChqFec, nd_maxDes, nd_marUti
, tx_repLeg, st_cieRetPen, tx_idePro, tx_nomPro, tx_dirPro, tx_telPro, tx_nacPro, fe_conEmp, tx_tipActEmp, tx_obsPro, ne_numMaxVenCon
, tx_obsVen, tx_obsInv, tx_obsCxC, tx_obsCxP, fe_ultActDat, fe_proActDat, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng
, co_usrMod, fe_his, co_usrHis)
SELECT a2.co_emp, a2.co_cli, (CASE WHEN a3.co_his IS NULL THEN 1 ELSE a3.co_his+1 END) AS co_his, a2.st_cli, a2.st_prv, a2.tx_tipIde, a2.tx_ide, a2.tx_nom, a2.tx_dir
, a2.tx_refUbi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, a2.tx_fax, a2.tx_cas, a2.tx_dirWeb, a2.tx_corEle, a2.tx_tipPer, a2.co_tipPer, a2.co_ciu, a2.co_zon
, a2.tx_perCon, a2.tx_telCon, a2.tx_corEleCon, a2.co_ven, a2.co_grp, a2.co_forPag, a2.nd_monCre, a2.ne_diaGra, a2.ne_diaGraChqFec, a2.nd_maxDes, a2.nd_marUti
, a2.tx_repLeg, a2.st_cieRetPen, a2.tx_idePro, a2.tx_nomPro, a2.tx_dirPro, a2.tx_telPro, a2.tx_nacPro, a2.fe_conEmp, a2.tx_tipActEmp, a2.tx_obsPro, a2.ne_numMaxVenCon
, a2.tx_obsVen, a2.tx_obsInv, a2.tx_obsCxC, a2.tx_obsCxP, a2.fe_ultActDat, a2.fe_proActDat, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultMod, a2.co_usrIng
, a2.co_usrMod, CURRENT_TIMESTAMP AS fe_his, 1 AS co_usrHis
FROM 
(
--ESTO SE HACE DINÁMICAMENTE EN EL PROGRAMA.
SELECT 1 AS co_emp, 2601 AS co_cli, 1 AS co_forPag, 0.000000 AS nd_monCre, 6.00 AS nd_maxDes, 4 AS ne_diaGra, 0 AS ne_diaGraChqFec, CAST('N' AS CHAR) AS st_cieRetPen, CURRENT_TIMESTAMP AS fe_ultMod, 1 AS co_usrMod 
UNION ALL 
SELECT 1 AS co_emp, 2608 AS co_cli, 1 AS co_forPag, 0.000000 AS nd_monCre, 7.00 AS nd_maxDes, 8 AS ne_diaGra, 0 AS ne_diaGraChqFec, CAST('N' AS CHAR) AS st_cieRetPen, CURRENT_TIMESTAMP AS fe_ultMod, 1 AS co_usrMod 
UNION ALL 
SELECT 1 AS co_emp, 2610 AS co_cli, 1 AS co_forPag, 0.000000 AS nd_monCre, 8.00 AS nd_maxDes, 12 AS ne_diaGra, 0 AS ne_diaGraChqFec, CAST('N' AS CHAR) AS st_cieRetPen, CURRENT_TIMESTAMP AS fe_ultMod, 1 AS co_usrMod 
) AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)
LEFT OUTER JOIN 
(
SELECT co_emp, co_cli, MAX(co_his) AS co_his FROM tbh_cli GROUP BY co_emp, co_cli
) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA ACTUALIZAR LOS DATOS DE TODOS LOS CLIENTES (GRUPO).*/
UPDATE tbm_cli 
SET co_forPag=a1.co_forPag, nd_moncre=a1.nd_moncre, nd_maxdes=a1.nd_maxdes, ne_diaGra=a1.ne_diaGra, ne_diaGraChqFec=a1.ne_diaGraChqFec, st_cieRetPen=a1.st_cieRetPen, fe_ultMod=a1.fe_ultMod, co_usrMod=a1.co_usrMod 
FROM (
--ESTO SE HACE DINÁMICAMENTE EN EL PROGRAMA.
SELECT 1 AS co_emp, 2601 AS co_cli, 1 AS co_forPag, 0.000000 AS nd_monCre, 6.00 AS nd_maxDes, 4 AS ne_diaGra, 0 AS ne_diaGraChqFec, CAST('N' AS CHAR) AS st_cieRetPen, CURRENT_TIMESTAMP AS fe_ultMod, 1 AS co_usrMod 
UNION ALL 
SELECT 1 AS co_emp, 2608 AS co_cli, 1 AS co_forPag, 0.000000 AS nd_monCre, 7.00 AS nd_maxDes, 8 AS ne_diaGra, 0 AS ne_diaGraChqFec, CAST('N' AS CHAR) AS st_cieRetPen, CURRENT_TIMESTAMP AS fe_ultMod, 1 AS co_usrMod 
UNION ALL 
SELECT 1 AS co_emp, 2610 AS co_cli, 1 AS co_forPag, 0.000000 AS nd_monCre, 8.00 AS nd_maxDes, 12 AS ne_diaGra, 0 AS ne_diaGraChqFec, CAST('N' AS CHAR) AS st_cieRetPen, CURRENT_TIMESTAMP AS fe_ultMod, 1 AS co_usrMod 
) AS a1 
WHERE tbm_cli.co_emp=a1.co_emp AND tbm_cli.co_cli=a1.co_cli
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA DETERMINAR LOS CLIENTES QUE ESTAN REPETIDOS EN LA EMPRESA ESPECIFICADA.*/
SELECT tx_ide, COUNT(*) FROM tbm_cli WHERE co_emp=4 GROUP BY tx_ide HAVING COUNT(*)>1 ORDER BY tx_ide;
/*QUERY PARA DETERMINAR LOS CLIENTES QUE ESTAN REPETIDOS EN TODAS LAS EMPRESAS.*/
SELECT co_emp, tx_ide, COUNT(*) FROM tbm_cli GROUP BY co_emp, tx_ide HAVING COUNT(*)>1 ORDER BY co_emp, tx_ide;
/*ARTIFICIO: QUERY PARA RENOMBRAR EL CAMPO "tx_ide" Y EVITAR QUE EXISTAN CLIENTES REPETIDOS (SÓLO PARA PRUEBAS).*/
UPDATE tbm_cli
SET tx_ide=b1.tx_ideSis
, tx_obs2=b1.tx_obs2Sis
FROM
(
/*QUERY PARA OBTENER LOS DATOS NECESARIOS DE LOS CLIENTES REPETIDOS.*/
SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_obs2, (substr(a1.tx_ide, 1, 8) || '_' || a1.co_cli) AS tx_ideSis, (a1.tx_obs2 || '; tx_ide: ' || a1.tx_ide) AS tx_obs2Sis
FROM tbm_cli AS a1
INNER JOIN 
(
SELECT co_emp, tx_ide, COUNT(*) FROM tbm_cli GROUP BY co_emp, tx_ide HAVING COUNT(*)>1 ORDER BY co_emp, tx_ide
) AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)
ORDER BY a1.co_emp, a1.tx_ide 
) AS b1
WHERE tbm_cli.co_emp=b1.co_emp AND tbm_cli.co_cli=b1.co_cli;
/*----------------------------------------------------------------------------------------------------------------*/
