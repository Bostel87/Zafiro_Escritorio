/*VARIOS.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_pagMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag
SELECT * FROM tbm_plaCta LIMIT 10
SELECT * FROM tbm_var LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL EL SALDO DE CADA CLIENTE.*/
SELECT a3.co_cli, a3.tx_ide, a3.tx_nom, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal
FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cli AS A3
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc
AND a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli
AND a1.co_emp=1 AND a3.st_cli='S' AND a2.st_reg IN ('A','C')
GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom
HAVING SUM(a2.mo_pag+a2.nd_abo)<>0
ORDER BY a3.tx_nom
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS CREDITOS/DEBITOS DEL CLIENTE/PROVEEDOR SELECCIONADO.*/
SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen
, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.co_proChq, a4.tx_desLar AS a4_tx_desLar
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_proChq=a4.co_reg)
WHERE a1.co_emp=1 AND a1.co_cli=3 AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DEL CREDITO/DEBITO SELECCIONADO.*/
SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc1, a1.ne_numDoc2
, a1.fe_doc, a1.fe_ven, a2.nd_abo, a1.co_cta, a4.tx_codCta, a4.tx_desLar AS a4_tx_desLar, a2.co_tipDocCon
, a5.tx_desCor AS a5_tx_desCor, a5.tx_desLar AS a5_tx_desLar, a2.co_banChq, a6.tx_desCor AS a6_tx_desCor
, a6.tx_desLar AS a6_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
LEFT OUTER JOIN tbm_plaCta AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta)
LEFT OUTER JOIN tbm_cabTipDoc AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDocCon=a5.co_tipDoc)
LEFT OUTER JOIN tbm_var AS a6 ON (a2.co_banChq=a6.co_reg)
WHERE a2.co_emp=1 AND a2.co_locPag=1 AND a2.co_tipDocPag=1 AND a2.co_docPag=1668 AND a2.co_regPag=1 AND a1.st_reg='A'
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS PAGOS REALIZADOS POR EL CLIENTE/PROVEEDOR SELECCIONADO.*/
SELECT a1.co_loc, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc1, a1.ne_numDoc2
, a1.fe_doc, a1.fe_ven, a2.nd_abo, a1.co_cta, a5.tx_codCta, a5.tx_desLar AS a5_tx_desLar, a2.co_tipDocCon
, a6.tx_desCor AS a6_tx_desCor, a6.tx_desLar AS a6_tx_desLar, a2.co_banChq, a7.tx_desCor AS a7_tx_desCor
, a7.tx_desLar AS a7_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
LEFT OUTER JOIN tbm_plaCta AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cta=a5.co_cta)
LEFT OUTER JOIN tbm_cabTipDoc AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_loc=a6.co_loc AND a2.co_tipDocCon=a6.co_tipDoc)
LEFT OUTER JOIN tbm_var AS a7 ON (a2.co_banChq=a7.co_reg)
WHERE a3.co_emp=1 AND a3.co_cli=3 AND a1.st_reg='A'
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL MOVIMIENTO DEL PAGO SELECCIONADO.*/
SELECT a1.co_loc, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen
, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.co_proChq, a5.tx_desLar AS a5_tx_desLar
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_detPag AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_proChq=a5.co_reg)
WHERE a3.co_emp=1 AND a3.co_loc=1 AND a3.co_tipDoc=18 AND a3.co_doc=47 AND a3.co_reg=2
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/
