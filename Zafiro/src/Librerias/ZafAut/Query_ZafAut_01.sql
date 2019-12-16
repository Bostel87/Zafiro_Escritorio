/*VARIOS.*/
SELECT * FROM tbm_detCotVen LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10

/*QUERY PARA ACTUALIZAR EL MARGEN DE UTILIDAD DEL CLIENTE DE PRUEBA.*/
UPDATE tbm_cli SET nd_maxDes=25, nd_marUti=30 WHERE co_emp=1 AND co_cli=110

/*QUERY PARA OBTENER LOS DATOS QUE SE NECESITAN DEL CLIENTE.*/
SELECT a1.nd_maxDes, a1.nd_marUti
FROM tbm_cli AS a1
WHERE a1.co_emp=1 AND a1.co_cli=(SELECT co_cli FROM tbm_cabCotVen WHERE co_emp=1 AND co_loc=1 AND co_cot=3371)

/*QUERY PARA OBTENER EL DETALLE DE LA COTIZACIÓN.*/
SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_can, a1.nd_preUni, a1.nd_porDes, a3.nd_cosUni, a4.nd_preVta1, a1.nd_preCom
FROM tbm_detCotVen AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN
(
/*OBTENER EL COSTO UNITARIO DE LA EMPRESA GRUPO.*/
SELECT b2.co_itmMae, b1.nd_cosUni
FROM tbm_inv AS b1
INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)
WHERE b1.co_emp=0
) AS a3 ON (a2.co_itmMae=a3.co_itmMae)
INNER JOIN tbm_inv AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_itm=a4.co_itm)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cot=3371
ORDER BY a1.co_reg

