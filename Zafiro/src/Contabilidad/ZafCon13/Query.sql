/*VARIOS.*/
SELECT * FROM tbm_plaCta LIMIT 10
SELECT * FROM tbm_salCta LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL SALDO MENSUAL Y ACUMULADO DE LAS CUENTAS DE BALANCE.*/
SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
INNER JOIN
(
SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta
FROM tbm_salCta AS b1
WHERE b1.co_emp=1 AND b1.co_per<=200602
GROUP BY b1.co_emp, b1.co_cta
) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)
WHERE a1.co_emp=1 AND a2.co_per=200601 AND a1.tx_niv1 IN ('1', '2', '3')
ORDER BY a1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DE UN ITEM (GRUPO).*/
SELECT c1.ne_niv, c1.tx_codCta, c1.tx_desLar, SUM(c1.nd_salMen) AS nd_salMen, SUM(c1.nd_salAcu) AS nd_salAcu
FROM
(
SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
INNER JOIN
(
SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta
FROM tbm_salCta AS b1
WHERE b1.co_per<=200602 
--Excluir las cuentas de "Bodega" de la empresas.
AND NOT ( (b1.co_emp=1 AND b1.co_cta=407) OR (b1.co_emp=1 AND b1.co_cta=409) OR (b1.co_emp=2 AND b1.co_cta=74) OR (b1.co_emp=2 AND b1.co_cta=75) OR (b1.co_emp=3 AND b1.co_cta=357) OR (b1.co_emp=3 AND b1.co_cta=358) OR (b1.co_emp=4 AND b1.co_cta=383) OR (b1.co_emp=4 AND b1.co_cta=381) )
--Excluir las cuentas de "Costo de venta" de la empresas.
AND NOT ( (b1.co_emp=1 AND b1.co_cta=1447) OR (b1.co_emp=2 AND b1.co_cta=559) OR (b1.co_emp=3 AND b1.co_cta=1346) OR (b1.co_emp=4 AND b1.co_cta=1386) )
GROUP BY b1.co_emp, b1.co_cta
) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)
WHERE a2.co_per=200602 AND a1.tx_niv1 IN ('1', '2', '3')
) AS c1
GROUP BY c1.ne_niv, c1.tx_codCta, c1.tx_desLar
ORDER BY c1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PRUEBAS:*/
SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu 
FROM tbm_plaCta AS a1 
INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) 
INNER JOIN ( SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta 
FROM tbm_salCta AS b1 WHERE b1.co_emp=1 AND b1.co_per<=200501 
GROUP BY b1.co_emp, b1.co_cta ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta) 
WHERE a1.co_emp=1 AND a2.co_per=200501 AND a1.tx_niv1 IN ('1', '2', '3') 
ORDER BY a1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/