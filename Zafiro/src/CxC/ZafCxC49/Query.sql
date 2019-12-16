/*VARIOS.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_pagMovInv LIMIT 10
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag
SELECT * FROM tbm_var LIMIT 10

/*QUERY PARA OBTENER LOS DOCUMENTOS ABIERTOS.*/
SELECT a1.co_emp, a1.co_loc, a1.co_cli, a4.tx_nom, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.co_banChq, a5.tx_desLar AS a5_tx_desLar
, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)
LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_banChq=a5.co_reg)
WHERE a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 AND a3.ne_mod=1
ORDER BY a2.co_emp, a4.tx_nom, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg