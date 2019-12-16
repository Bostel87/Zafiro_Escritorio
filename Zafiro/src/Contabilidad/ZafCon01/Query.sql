/*VARIOS.*/
SELECT * FROM tbm_plaCta LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS CUENTAS DE MAYOR.*/
SELECT a1.tx_niv1, a1.tx_niv2, a1.tx_niv3, a1.tx_niv4, a1.tx_niv5, a1.tx_niv6, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a1.tx_natCta
FROM tbm_plaCta AS a1
WHERE a1.co_emp=1
ORDER BY a1.tx_codCta
/*----------------------------------------------------------------------------------------------------------------*/

