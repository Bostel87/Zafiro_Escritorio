/*VARIOS.*/
SELECT * FROM tbm_cabDia LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_detTipDoc LIMIT 10
SELECT * FROM tbr_tipDocPrg LIMIT 10

/*QUERY PARA OBTENER EL DOCUMENTO PREDETERMINADO*/
SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc
FROM tbm_cabTipDoc AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=
(
SELECT co_tipDoc FROM tbr_tipDocPrg WHERE co_emp=1 AND co_loc=1 AND co_mnu=256 AND st_reg='S'
)

/*QUERY PARA OBTENER LA CABECERA.*/
SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia, a1.tx_numDia, a1.fe_dia
FROM tbm_cabDia AS a1, tbm_cabTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
LIMIT 10

/*QUERY PARA DETERMINAR SI EL CAMPO"tx_numDia" EXISTE.*/
SELECT a1.tx_numDia 
FROM tbm_cabDia AS a1  
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=30 AND a1.tx_numDia='205'