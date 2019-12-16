/*Cabecera:*/
SELECT * FROM tbm_cabDia WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1 AND co_dia=4868
DELETE FROM tbm_cabDia WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1 AND co_dia=4868

/*Detalle:*/
SELECT * FROM tbm_detDia WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1 AND co_dia=4868
DELETE FROM tbm_detDia WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1 AND co_dia=4868

/*Ultimo documento ingresado.*/
SELECT * FROM tbm_cabTipDoc WHERE co_emp=1 AND co_loc=1 ORDER BY co_tipDoc
UPDATE tbm_cabTipDoc SET ne_ultDoc=ne_ultDoc-1 WHERE co_emp=1 AND co_loc=1 AND co_tipDoc=1
