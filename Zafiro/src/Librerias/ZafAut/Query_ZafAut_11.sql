/*VARIOS.*/
SELECT * FROM tbm_cabCotVen LIMIT 10
SELECT * FROM tbm_detCotVen LIMIT 10
SELECT * FROM tbm_inv LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL DETALLE DE LA COTIZACIÓN CON LOS DATOS QUE SE MOSTRARÁN.*/
SELECT a1.co_itm, a1.tx_codAlt, a2.tx_nomItm AS a2_tx_nomItm, a1.tx_nomItm
FROM tbm_detCotVen AS a1
INNER JOIN tbm_inv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
WHERE a1.co_emp=1
AND a1.co_loc=4
AND a1.co_cot=119444
ORDER BY a1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/
