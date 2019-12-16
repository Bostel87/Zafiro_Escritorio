/*Varios.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag

/*Obtener el listado de documentos.*/
SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_ordDoc, a1.ne_numDoc, a1.fe_doc
, a1.co_cli, a1.tx_nomCli, a1.co_forPag, a1.tx_desForPag, a1.nd_sub, a1.nd_porIva
FROM tbm_cabMovInv AS a1, tbm_cabTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
ORDER BY a1.co_emp, a1.co_loc, a1.ne_ordDoc

/*Cargar Detalle */
SELECT a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc1, a1.ne_numDoc2, 
       a1.fe_doc, a1.fe_ven, a1.co_cta, a3.tx_desLar AS a3_tx_desLar, a1.co_cli, a1.tx_nomCli, a1.nd_monDoc, 
       a1.st_reg, a1.fe_ing, a1.co_usrIng, a5.tx_usr AS a5_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, 
       a6.tx_usr AS a6_tx_usr, a1.tx_comMod 
FROM tbm_cabPag AS a1 
LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) 
LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta) 
LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_usrIng=a5.co_usr) 
LEFT OUTER JOIN tbm_usr AS a6 ON (a1.co_usrMod=a6.co_usr) 
WHERE a1.co_emp=2 
AND a1.co_tipDoc in ( select co_tipDoc from tbr_tipDocUsr where co_emp=2 and co_loc=1 and co_mnu=318 AND co_usr=102)
AND a1.fe_doc BETWEEN '2016-08-01' AND '2016-08-31' 
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.fe_doc, a1.ne_numDoc1