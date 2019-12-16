/*VARIOS.*/
SELECT * FROM tbm_cabCotVen LIMIT 10
SELECT * FROM tbm_detCotVen LIMIT 10
SELECT * FROM tbm_pagMovInv LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE VENTAS DE CONTADO POR CLIENTE.*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, ABS(a2.mo_pag) AS mo_pag, ABS(a2.nd_abo) AS nd_abo
, -(a2.mo_pag+a2.nd_abo+(CASE WHEN a2.nd_monChq IS NULL THEN 0 ELSE (CASE WHEN a2.fe_ven<a2.fe_venChq THEN 0 ELSE a2.nd_monChq END) END)) AS nd_pen, a2.fe_venChq, a2.nd_monChq
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)
WHERE a1.co_emp=1
AND a1.co_cli= ( SELECT co_cli FROM tbm_cabCotVen WHERE co_emp=1 AND co_loc=4 AND co_cot=131469 ) 
AND a1.st_reg IN ('A','R','C','F')
AND a2.st_reg IN ('A','C')
AND (a2.mo_pag+a2.nd_abo)<0
AND a2.ne_diaCre=0 AND a2.nd_porRet=0
AND a4.ne_diaGra=0
AND a1.st_imp='S'
AND (a1.st_excDocConVenCon IS NULL OR a1.st_excDocConVenCon='N')
AND (a2.fe_venChq IS NULL OR a2.nd_monChq IS NULL OR a2.fe_ven<a2.fe_venChq OR (a2.mo_pag+a2.nd_monChq)<0)
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE VENTAS DE CONTADO POR LOCAL.*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, ABS(a2.mo_pag) AS mo_pag, ABS(a2.nd_abo) AS nd_abo
, -(a2.mo_pag+a2.nd_abo+(CASE WHEN a2.nd_monChq IS NULL THEN 0 ELSE (CASE WHEN a2.fe_ven<a2.fe_venChq THEN 0 ELSE a2.nd_monChq END) END)) AS nd_pen, a2.fe_venChq, a2.nd_monChq
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
INNER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)
WHERE a1.co_emp=1
AND a1.co_loc=4
AND a1.st_reg IN ('A','R','C','F')
AND a2.st_reg IN ('A','C')
AND (a2.mo_pag+a2.nd_abo)<0
AND a2.ne_diaCre=0 AND a2.nd_porRet=0
AND a3.ne_mod=1
AND a4.ne_diaGra=0
AND a1.st_imp='S'
AND (a1.st_excDocConVenCon IS NULL OR a1.st_excDocConVenCon='N')
AND (a2.fe_venChq IS NULL OR a2.nd_monChq IS NULL OR a2.fe_ven<a2.fe_venChq OR (a2.mo_pag+a2.nd_monChq)<0)
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*PRUEBAS.*/
SELECT * FROM tbm_cabMovInv WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND ne_numDoc=51311;
SELECT * FROM tbm_pagMovInv WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284;
--DATOS REALES:
UPDATE tbm_pagMovInv SET fe_venChq='2010/10/21', nd_monChq=402.08 WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284 AND co_reg=2;
/*CASO1: TODAVIA NO SE INGRESA EL CHEQUE.*/                                            UPDATE tbm_pagMovInv SET fe_venChq=NULL, nd_monChq=NULL WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284 AND co_reg=2;
/*CASO2: "FECHA DE VENCIMIENTO=FECHA DE CHEQUE" Y "VALOR A COBRAR=VALOR DEL CHEQUE".*/ UPDATE tbm_pagMovInv SET fe_venChq='2010/10/21', nd_monChq=402.09 WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284 AND co_reg=2;
/*CASO3: "FECHA DE VENCIMIENTO=FECHA DE CHEQUE" Y "VALOR A COBRAR>VALOR DEL CHEQUE".*/ UPDATE tbm_pagMovInv SET fe_venChq='2010/10/21', nd_monChq=200.00 WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284 AND co_reg=2;
/*CASO4: "FECHA DE VENCIMIENTO<FECHA DE CHEQUE" Y "VALOR A COBRAR=VALOR DEL CHEQUE".*/ UPDATE tbm_pagMovInv SET fe_venChq='2010/10/22', nd_monChq=402.09 WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284 AND co_reg=2;
/*CASO5: "FECHA DE VENCIMIENTO<FECHA DE CHEQUE" Y "VALOR A COBRAR>VALOR DEL CHEQUE".*/ UPDATE tbm_pagMovInv SET fe_venChq='2010/10/22', nd_monChq=200.00 WHERE co_emp=1 AND co_loc=4 AND co_tipDoc=1 AND co_doc=51284 AND co_reg=2;
/*----------------------------------------------------------------------------------------------------------------*/