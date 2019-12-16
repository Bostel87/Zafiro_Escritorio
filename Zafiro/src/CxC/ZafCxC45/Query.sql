/*Varios.*/
SELECT * FROM tbm_cli LIMIT 10;
SELECT * FROM tbm_cabMovInv LIMIT 10;
SELECT * FROM tbm_cli WHERE co_emp=1 AND co_cli=1851;
SELECT co_emp, tx_ruc, COUNT(*) FROM tbm_cabMovInv GROUP BY co_emp, tx_ruc HAVING COUNT(*)>1 ORDER BY tx_ruc;
SELECT * FROM tbm_parSis ORDER BY co_par;
SELECT * FROM tbr_parEmp ORDER BY co_emp, co_par;
SELECT * FROM tbr_parUsr;
SELECT tx_ruc, co_emp, co_loc, COUNT(*) FROM tbm_cabMovInv WHERE co_emp=2 AND co_tipDoc=1 GROUP BY tx_ruc, co_emp, co_loc ORDER BY tx_ruc, co_emp;

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE PAGOS REALIZADOS POR EL CLIENTE.*/
SELECT b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.co_reg, b1.ne_numDoc, b1.fe_doc, b2.fe_ven, b3.fe_pag, b2.mo_pag, b3.nd_abo, b2.ne_diaCre, (b3.fe_pag-b1.fe_doc) AS ne_diaPag, b4.ne_diaGra
FROM tbm_cabMovInv AS b1
INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
LEFT OUTER JOIN 
(
SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, a1.fe_doc AS fe_pag, a1.fe_ing, a2.nd_abo
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
RIGHT OUTER JOIN tbm_pagMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg)
INNER JOIN tbm_cabMovInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)
WHERE a1.co_emp=1 AND a1.st_reg='A' 
AND a4.co_cli=1851 AND (a3.nd_porRet=0 OR a3.nd_porRet IS NULL)
AND a4.st_reg IN ('A','R','C','F') AND a3.st_reg IN ('A','C') 
) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag)
INNER JOIN tbm_cli AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_cli=b4.co_cli)
INNER JOIN tbm_cabTipDoc AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_loc=b5.co_loc AND b1.co_tipDoc=b5.co_tipDoc)
WHERE b1.co_emp=1 
AND b1.co_cli=1851 AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL)
AND b1.st_reg IN ('A','R','C','F') AND b2.st_reg IN ('A','C')
AND b5.ne_mod=1 AND b5.tx_natDoc='E'
ORDER BY b1.fe_doc, b1.fe_ing, b2.co_reg, b3.fe_pag, b3.fe_ing, b3.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE PAGOS REALIZADOS POR EL CLIENTE (GRUPO).*/
SELECT b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.co_reg, b1.ne_numDoc, b1.fe_doc, b2.fe_ven, b3.fe_pag, b2.mo_pag, b3.nd_abo, b2.ne_diaCre, (b3.fe_pag-b1.fe_doc) AS ne_diaPag, b4.ne_diaGra
FROM tbm_cabMovInv AS b1
INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
LEFT OUTER JOIN 
(
SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, a1.fe_doc AS fe_pag, a1.fe_ing, a2.nd_abo
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
RIGHT OUTER JOIN tbm_pagMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg)
INNER JOIN tbm_cabMovInv AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)
WHERE a1.st_reg='A' 
AND a4.tx_ruc='0190138976001' AND (a3.nd_porRet=0 OR a3.nd_porRet IS NULL)
AND a4.st_reg IN ('A','R','C','F') AND a3.st_reg IN ('A','C') 
) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag)
INNER JOIN tbm_cli AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_cli=b4.co_cli)
INNER JOIN tbm_cabTipDoc AS b5 ON (b1.co_emp=b5.co_emp AND b1.co_loc=b5.co_loc AND b1.co_tipDoc=b5.co_tipDoc)
WHERE b1.tx_ruc='0190138976001' AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL)
AND b1.st_reg IN ('A','R','C','F') AND b2.st_reg IN ('A','C')
AND b5.ne_mod=1 AND b5.tx_natDoc='E'
ORDER BY b1.fe_doc, b1.fe_ing, b2.co_reg, b3.fe_pag, b3.fe_ing, b3.co_reg
/*----------------------------------------------------------------------------------------------------------------*/
