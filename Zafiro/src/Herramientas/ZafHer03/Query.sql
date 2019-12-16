/*VARIOS.*/
SELECT * FROM tbm_cabCotVen LIMIT 10
SELECT * FROM tbm_detCotVen LIMIT 10
SELECT * FROM tbm_cli LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_cabMovInv LIMIT 10
SELECT * FROM tbm_cabAutCotVen LIMIT 10
SELECT * FROM tbm_detAutCotVen LIMIT 10
SELECT * FROM tbm_regNeg LIMIT 10

/*QUERY PARA OBTENER LAS COTIZACIONES DE VENTA POR AUTORIZAR.*/
SELECT a1.co_emp, a1.co_loc, Null AS co_tipDoc, Null AS tx_desCor, Null AS tx_desLar, a1.co_cot AS co_doc
, a1.co_cot AS ne_numDoc, a1.fe_cot AS fe_doc, a1.co_cli, a2.tx_nom AS tx_nomCli, a1.nd_tot
FROM tbm_cabCotVen AS a1
INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.st_reg='P'
ORDER BY a1.co_emp, a1.co_loc, a1.co_cot

/*QUERY PARA OBTENER LA CABECERA DE LA AUTORIZACIÓN.*/
SELECT a1.co_aut, a1.tx_obs1, a1.tx_obs2, a1.ne_valAut
FROM tbm_cabAutCotVen AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=3359 AND a1.co_aut=
(
SELECT MAX(b1.co_aut) AS co_aut FROM tbm_cabAutCotVen AS b1 WHERE b1.co_emp=1 AND b1.co_loc=1 AND b1.co_cot=3359
)

/*QUERY PARA OBTENER EL DETALLE DE LA AUTORIZACIÓN.*/
SELECT a1.co_reg, a1.co_regNeg, a2.tx_desCor, a2.tx_desLar, a1.st_reg, a1.tx_obs1, a3.co_usr, a3.nd_par1, a3.nd_par2, a2.tx_claMot, a2.tx_funVerAut
FROM tbm_detAutCotVen AS a1
INNER JOIN tbm_regNeg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_regNeg=a2.co_reg)
LEFT OUTER JOIN tbm_autDoc AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_reg=a3.co_reg AND a3.co_usr=24)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=2932 AND a1.co_aut=1
AND a1.st_cum='N'
ORDER by a1.co_reg


/*PRUEBAS: tbm_cabCotVen*/
SELECT * FROM tbm_cabCotVen AS a1 WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=2932

/*PRUEBAS: tbm_cabAutCotVen*/
SELECT a1.*
FROM tbm_cabAutCotVen AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=2932 AND a1.co_aut=1

/*PRUEBAS: tbm_detAutCotVen*/
SELECT a1.*
FROM tbm_detAutCotVen AS a1
INNER JOIN tbm_regNeg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_regNeg=a2.co_reg)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=2932 AND a1.co_aut=1
AND a1.st_cum='N'
ORDER by a1.co_reg

/*PRUEBAS: UPDATE*/
UPDATE tbm_cabCotVen SET st_reg='P' WHERE co_emp=1 AND co_loc=1 AND co_cot=3359
UPDATE tbm_cabAutCotVen SET st_reg='P' WHERE co_emp=1 AND co_loc=1 AND co_cot=3359 AND co_aut=1
UPDATE tbm_detAutCotVen SET st_reg='P' WHERE co_emp=1 AND co_loc=1 AND co_cot=3359 AND co_aut=1 AND st_cum='N'

select current_timestamp