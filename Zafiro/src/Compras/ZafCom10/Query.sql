/*Varios.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag

/*Obtener el listado de documentos.*/
SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_ordDoc, a1.ne_numDoc, a1.fe_doc
, a1.co_cli, a1.tx_nomCli, a1.co_forPag, a1.tx_desForPag, a1.nd_sub, a1.nd_porIva, a1.nd_tot, a1.st_reg
FROM tbm_cabMovInv AS a1, tbm_cabTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
ORDER BY a1.co_emp, a1.co_loc, a1.ne_ordDoc