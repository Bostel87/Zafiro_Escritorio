/*VARIOS.*/
SELECT * FROM tbm_plaCta LIMIT 10
SELECT * FROM tbm_cabDia
SELECT * FROM tbm_detDia
SELECT * FROM tbm_salCta LIMIT 10
SELECT * FROM tbm_anicresis

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS AÑOS CREADOS EN EL SISTEMA.*/
SELECT a1.ne_ani
FROM tbm_aniCreSis AS a1
WHERE a1.co_emp=1
ORDER BY a1.ne_ani
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL EL SALDO DE CADA CUENTA (USUARIO ADMINISTRADOR).*/
SELECT a1.co_cta, a1.ne_niv, a1.tx_codCta, a1.tx_desLar, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
WHERE a1.co_emp=1 AND a2.co_per<=200910 AND a1.st_reg='A'
GROUP BY a1.co_cta, a1.ne_niv, a1.tx_codCta, a1.tx_desLar
ORDER BY a1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL EL SALDO DE CADA CUENTA (OTROS USUARIOS).*/
SELECT a1.co_cta, a1.ne_niv, a1.tx_codCta, a1.tx_desLar, SUM(a2.nd_salCta) AS nd_salCta
FROM tbm_plaCta AS a1
INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)
INNER JOIN tbr_ctaLocPrgUsr AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)
WHERE a1.co_emp=1 AND a3.co_loc=4 AND a3.co_mnu=341 AND a3.co_usr=23 AND a2.co_per<=200910 AND a1.st_reg='A'
GROUP BY a1.co_cta, a1.ne_niv, a1.tx_codCta, a1.tx_desLar
ORDER BY a1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/
