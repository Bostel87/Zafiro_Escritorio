/*VARIOS.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_pagMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag

/*QUERY PARA OBTENER LOS DATOS QUE SE NECESITAN DEL CLIENTE.*/
SELECT a1.ne_diaGraChqFec
FROM tbm_cli AS a1
WHERE a1.co_emp=1 AND a1.co_cli=(SELECT co_cli FROM tbm_cabCotVen WHERE co_emp=1 AND co_loc=1 AND co_cot=3371)

/*QUERY PARA OBTENER LOS DOCUMENTOS ABIERTOS.*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, ABS(a2.mo_pag) AS mo_pag, ABS(a2.nd_abo) AS nd_abo, -(a2.mo_pag+a2.nd_abo) AS nd_pen 
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) 
WHERE a1.co_emp=1 AND a1.co_cli= ( SELECT co_cli FROM tbm_cabCotVen WHERE co_emp=1 AND co_loc=1 AND co_cot=3371) 
AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') AND (a2.mo_pag+a2.nd_abo)<>0 
AND a2.st_sop='S' AND a2.st_entSop='N' 
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg

