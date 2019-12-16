/*VARIOS.*/
SELECT * FROM tbm_plaCta LIMIT 10
SELECT * FROM tbm_cabDia LIMIT 10
SELECT * FROM tbm_detDia LIMIT 10
SELECT * FROM tbm_salCta LIMIT 10
SELECT * FROM tbm_plaCta WHERE co_emp=1

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA BORRAR EL PERIODO ESPECIFICADO.*/
DELETE FROM tbm_salCta WHERE co_emp=1 AND co_per=200601
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 1/7: QUERY PARA OBTENER EL SALDO DE LAS CUENTAS.*/
INSERT INTO tbm_salCta(co_emp, co_cta, co_per, nd_salCta)
SELECT b1.co_emp, b1.co_cta, 200601 AS co_per, b2.nd_salCta
FROM tbm_plaCta AS b1
LEFT OUTER JOIN
(
SELECT a1.co_emp, a2.co_cta, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta
FROM tbm_cabDia AS a1
INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)
WHERE a1.co_emp=1 AND a1.st_reg='A' AND (a1.fe_dia BETWEEN '2006/01/01' AND '2006/01/31')
GROUP BY a1.co_emp, a2.co_cta
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)
WHERE b1.co_emp=1
ORDER BY b1.co_emp, b1.co_cta
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 2/7: QUERY PARA OBTENER EL SALDO DE LA CUENTA "Utilidad/Perdida".*/
UPDATE tbm_salCta
SET nd_salCta=b1.nd_salCta
FROM
(
SELECT a1.co_emp, a3.co_ctaRes AS co_cta, 200601 AS co_per, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)
WHERE a1.co_emp=1 AND a1.tx_niv1 IN ('4', '5', '6', '7', '8') AND a2.co_per=200601
GROUP BY a1.co_emp, a3.co_ctaRes
) AS b1
WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 3/7: QUERY PARA OBTENER LOS SALDOS DE LAS CUENTAS DE NIVEL 6.*/
UPDATE tbm_salCta
SET nd_salCta=b1.nd_salCta
FROM
(
SELECT a1.co_emp, a1.ne_pad AS co_cta, 200601 AS co_per, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a1.ne_niv=6 AND a2.co_per=200601
GROUP BY a1.co_emp, a1.ne_pad
) AS b1
WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 4/7: QUERY PARA OBTENER LOS SALDOS DE LAS CUENTAS DE NIVEL 5.*/
UPDATE tbm_salCta
SET nd_salCta=b1.nd_salCta
FROM
(
SELECT a1.co_emp, a1.ne_pad AS co_cta, 200601 AS co_per, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a1.ne_niv=5 AND a2.co_per=200601
GROUP BY a1.co_emp, a1.ne_pad
) AS b1
WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 5/7: QUERY PARA OBTENER LOS SALDOS DE LAS CUENTAS DE NIVEL 4.*/
UPDATE tbm_salCta
SET nd_salCta=b1.nd_salCta
FROM
(
SELECT a1.co_emp, a1.ne_pad AS co_cta, 200601 AS co_per, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a1.ne_niv=4 AND a2.co_per=200601
GROUP BY a1.co_emp, a1.ne_pad
) AS b1
WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 6/7: QUERY PARA OBTENER LOS SALDOS DE LAS CUENTAS DE NIVEL 3.*/
UPDATE tbm_salCta
SET nd_salCta=b1.nd_salCta
FROM
(
SELECT a1.co_emp, a1.ne_pad AS co_cta, 200601 AS co_per, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a1.ne_niv=3 AND a2.co_per=200601
GROUP BY a1.co_emp, a1.ne_pad
) AS b1
WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 7/7: QUERY PARA OBTENER LOS SALDOS DE LAS CUENTAS DE NIVEL 2.*/
UPDATE tbm_salCta
SET nd_salCta=b1.nd_salCta
FROM
(
SELECT a1.co_emp, a1.ne_pad AS co_cta, 200601 AS co_per, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a1.ne_niv=2 AND a2.co_per=200601
GROUP BY a1.co_emp, a1.ne_pad
) AS b1
WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PRUEBAS: QUERY PARA OBTENER LOS SALDOS DE LAS CUENTAS.*/
SELECT a1.co_emp, a1.co_cta, a1.ne_pad, a1.ne_niv, a1.tx_niv1, a1.tx_niv2, a1.tx_niv3, a1.tx_niv4,
a1.tx_niv5, a1.tx_niv6, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a2.co_per=200601
ORDER BY a1.co_emp, a1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/

/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PASO 1/7: QUERY PARA OBTENER EL SALDO DE LAS CUENTAS (GRUPO).*/
INSERT INTO tbm_salCta(co_emp, co_cta, co_per, nd_salCta)
SELECT b1.co_emp, b1.co_cta, 200601 AS co_per, b2.nd_salCta
FROM tbm_plaCta AS b1
LEFT OUTER JOIN
(
SELECT c1.co_emp, c1.co_cta, c2.nd_salCta
FROM tbm_equPlaCta AS c1
INNER JOIN 
(
SELECT a3.co_ctaMae, SUM(a2.nd_monDeb-a2.nd_monHab) AS nd_salCta
FROM tbm_cabDia AS a1
INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)
INNER JOIN tbm_equPlaCta AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)
WHERE a1.st_reg='A' AND (a1.fe_dia BETWEEN '2006/01/01' AND '2006/01/31')
GROUP BY a3.co_ctaMae
) AS c2 ON (c1.co_ctaMae=c2.co_ctaMae)
WHERE c1.co_emp=0
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)
WHERE b1.co_emp=0
ORDER BY b1.co_emp, b1.co_cta
/*----------------------------------------------------------------------------------------------------------------*/
select * from tbm_plaCta where co_emp=