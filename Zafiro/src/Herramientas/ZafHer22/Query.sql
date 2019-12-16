/*VARIOS.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_pagMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE CLIENTES DONDE NO COINCIDE EL ABONO QUE DICE EL MÓDULO DE "VENTAS" CON EL ABONO QUE DICE EL MÓDULO DE "CXC".*/
SELECT b1.co_emp, b1.co_cli, b3.tx_ide, b3.tx_nom, b1.nd_aboModVen, b2.nd_aboModCxC
FROM
(
/*Deuda del cliente segun Ventas.*/
SELECT a1.co_emp, a1.co_cli, SUM(a2.nd_abo) AS nd_aboModVen
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') --AND a1.co_cli=303
GROUP BY a1.co_emp, a1.co_cli
) AS b1 INNER JOIN (
/*Deuda del cliente según CxC.*/
SELECT a1.co_emp, a3.co_cli, SUM(a2.nd_abo) AS nd_aboModCxC
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)
WHERE a1.co_emp=1 AND a1.st_reg='A' AND a3.st_reg IN ('A','R','C','F')
GROUP BY a1.co_emp, a3.co_cli
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)
INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli)
WHERE b1.nd_aboModVen<>b2.nd_aboModCxC AND b3.st_cli='S'
ORDER BY b1.co_emp, b1.co_cli
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS PAGOS DEL CLIENTE SELECCIONADO DONDE NO COINCIDE EL ABONO QUE DICE EL MÓDULO DE "VENTAS" CON EL ABONO QUE DICE EL MÓDULO DE "CXC".*/
SELECT b1.co_loc, b1.co_tipDoc, b4.tx_desCor, b4.tx_desLar, b1.co_doc, b1.co_reg, b1.ne_numDoc, b1.fe_doc, b1.ne_diaCre, b1.fe_ven, b1.nd_porRet, b1.nd_aboModVen, b2.nd_aboModCxC
FROM
(
/*Deuda del cliente segun Ventas.*/
SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.nd_abo AS nd_aboModVen, a1.co_cli, a1.ne_numDoc
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDOC AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 AND a1.co_cli=303 AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') 
) AS b1 LEFT OUTER JOIN (
/*Deuda del cliente según CxC.*/
SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)
WHERE a1.co_emp=1 AND a3.co_cli=303 AND a1.st_reg='A' AND a3.st_reg IN ('A','R','C','F')
GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locPag AND b1.co_tipDoc=b2.co_tipDocPag AND b1.co_doc=b2.co_docPag AND b1.co_reg=b2.co_regPag)
INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli)
INNER JOIN tbm_cabTipDoc AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_loc=b4.co_loc AND b1.co_tipDoc=b4.co_tipDoc)
ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS REGISTROS QUE TIENEN EL SIGNO INCORRECTO EN "tbm_pagMovInv".*/
SELECT b1.*
FROM
(
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.mo_pag, a2.tx_natDoc
, (CASE WHEN a2.tx_natDoc='I' THEN ABS(a1.mo_pag) ELSE -ABS(a1.mo_pag) END) AS mo_pagSigCor
FROM tbm_pagMovInv AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1 
AND a2.tx_natDoc IN ('I', 'E')
) AS b1
WHERE b1.mo_pag<>b1.mo_pagSigCor
ORDER BY b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS REGISTROS QUE TIENEN EL SIGNO INCORRECTO EN "tbm_detPag".*/
SELECT b1.*
FROM
(
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.nd_abo, a3.tx_natDoc
, (CASE WHEN a3.tx_natDoc='I' THEN ABS(a2.nd_abo) ELSE -ABS(a2.nd_abo) END) AS nd_aboSigCor
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
WHERE a1.co_emp=1 
AND a3.tx_natDoc IN ('I', 'E')
) AS b1
WHERE b1.nd_abo<>b1.nd_aboSigCor
ORDER BY b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*CONTROL3: QUERY PARA OBTENER LOS DOCUMENTOS EN "tbm_detPag" QUE ESTÁN DESCUADRADOS.*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_abo) AS nd_valDoc
FROM tbm_detPag AS a1
INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)
WHERE a1.co_emp=1
AND a2.tx_natDoc IN ('A')
AND a2.st_docCua='S'
GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
HAVING SUM(a1.nd_abo)<>0
ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*CONTROL4: QUERY PARA OBTENER LOS DOCUMENTOS DONDE NO COINCIDE LO QUE DICE "tbm_cabMovInv" CON LO QUE DICE "tbm_pagMovInv" PARA EL MODULO DE VENTAS.*/
SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_tot, b2.nd_pag, (b1.nd_tot-b2.nd_pag) AS nd_dif
FROM 
tbm_cabMovInv AS b1
INNER JOIN
(
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag
FROM
(
SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag
FROM tbm_cabMovInv
WHERE co_emp=1 --AND co_tipDoc=1
UNION ALL
SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(mo_pag) AS nd_pag
FROM tbm_pagMovInv
WHERE co_emp=1 --AND co_tipDoc=1 
AND st_reg IN ('A','F')
GROUP BY co_emp, co_loc, co_tipDoc, co_doc
) AS a1
GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)
WHERE b1.nd_tot<>b2.nd_pag
AND b3.st_genPag='S'
--WHERE (b1.nd_tot-b2.nd_pag)<-0.05 OR (b1.nd_tot-b2.nd_pag)>0.05
ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DOCUMENTOS DONDE NO COINCIDE LO QUE DICE "tbm_cabMovInv" CON LO QUE DICE "tbm_pagMovInv" PARA EL MODULO DE CxC.*/
SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_tot, b2.nd_pag, (b1.nd_tot-b2.nd_pag) AS nd_dif
FROM 
tbm_cabMovInv AS b1
INNER JOIN
(
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag
FROM
(
SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag
FROM tbm_cabMovInv
WHERE co_emp=1 --AND co_tipDoc=1
UNION ALL
SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(mo_pag) AS nd_pag
FROM tbm_pagMovInv
WHERE co_emp=1 --AND co_tipDoc=1 
AND st_reg IN ('A','C')
GROUP BY co_emp, co_loc, co_tipDoc, co_doc
) AS a1
GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
) AS b2  ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)
WHERE b1.nd_tot<>b2.nd_pag
AND b3.st_genPag='S'
--WHERE (b1.nd_tot-b2.nd_pag)<-0.05 OR (b1.nd_tot-b2.nd_pag)>0.05
ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS REGISTROS EN "tbm_pagMovInv" MARCADOS CON "F ó I" QUE TIENEN ABONO.*/
SELECT co_emp, co_loc, co_tipDoc, co_doc, co_reg, mo_pag, nd_abo, st_reg
FROM tbm_pagMovInv
WHERE co_emp=1
AND st_reg IN ('F', 'I') AND nd_abo<>0
ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*CONTROL7: QUERY PARA OBTENER LOS DOCUMENTOS DONDE NO COINCIDE LO QUE DICE "tbm_cabPag" CON LO QUE DICE "tbm_detPag".*/
SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc1, b1.fe_doc, b1.nd_monDoc, b2.nd_pag, (b1.nd_monDoc-b2.nd_pag) AS nd_dif
FROM 
tbm_cabPag AS b1
INNER JOIN
(
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag
FROM
(
SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag
FROM tbm_cabPag
WHERE co_emp=1
UNION ALL
SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(nd_abo) AS nd_pag
FROM tbm_detPag
WHERE co_emp=1
GROUP BY co_emp, co_loc, co_tipDoc, co_doc
ORDER BY co_emp, co_loc, co_tipDoc, co_doc
) AS a1
GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
) AS b2  ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)
INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)
WHERE b1.nd_monDoc<>b2.nd_pag
AND b3.tx_natDoc IN ('I', 'E')
ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS REGISTROS EN "tbm_detPag" QUE APUNTAN A REGISTROS MARCADOS CON "I ó F" EN "tbm_pagMovInv".*/
SELECT b1.*
FROM
(
SELECT a2.*
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1
AND a1.st_reg IN ('A')
) AS b1 INNER JOIN 
(
SELECT a2.*
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1
AND a2.st_reg NOT IN ('A','C')
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_locPag=b2.co_loc AND b1.co_tipDocPag=b2.co_tipDoc AND b1.co_docPag=b2.co_doc AND b1.co_regPag=b2.co_reg)
ORDER BY b1.co_tipDoc, b1.co_doc, b1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DOCUMENTOS EN "tbm_cabMovInv" DONDE LA FECHA NO COINCIDE CON LO QUE DICE "tbm_cabDia".*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a2.fe_dia
FROM tbm_cabMovinv AS a1
INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)
WHERE a1.co_emp=1 AND a1.fe_doc<>a2.fe_dia
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS DOCUMENTOS EN "tbm_cabPag" DONDE LA FECHA NO COINCIDE CON LO QUE DICE "tbm_cabDia".*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a2.fe_dia
FROM tbm_cabPag AS a1
INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)
WHERE a1.co_emp=1 AND a1.fe_doc<>a2.fe_dia
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LOS ASIENTOS DE DIARIO DESCUADRADOS.*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_dia, SUM(a2.nd_monDeb) AS nd_monDeb, SUM(a2.nd_monHab) AS nd_monHab, SUM(a2.nd_monDeb)-SUM(a2.nd_monHab) AS nd_dif, a1.st_reg
FROM tbm_cabDia AS a1
INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)
WHERE a1.co_emp=1
GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_dia, a1.st_reg
HAVING SUM(a2.nd_monDeb)<>SUM(a2.nd_monHab)
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*CONTROL12: QUERY PARA OBTENER LOS REGISTROS EN "tbm_detPag" QUE APUNTAN A REGISTROS QUE NO EXISTEN EN "tbm_pagMovInv".*/
SELECT c1.*
FROM
(
SELECT b1.*
FROM
(
SELECT a2.*, a1.st_reg
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1
) AS b1 LEFT OUTER JOIN 
(
SELECT a2.*
FROM tbm_cabMovInv AS a1 
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_locPag=b2.co_loc AND b1.co_tipDocPag=b2.co_tipDoc AND b1.co_docPag=b2.co_doc AND b1.co_regPag=b2.co_reg)
WHERE b2.co_reg IS NULL
--ORDER BY b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg
) AS c1
WHERE c1.co_locPag IS NOT NULL
ORDER BY c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_reg
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*CONTROL13: QUERY PARA OBTENER LOS DOCUMENTOS EN "tbm_detPag" QUE NO CUADRAN CON LO QUE DICE "tbm_detDia".*/
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.nd_abo, a2.nd_monDeb, (ABS(a1.nd_abo)-ABS(a2.nd_monDeb)) AS nd_dif
FROM 
(
SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(nd_abo) AS nd_abo FROM tbm_detPag WHERE co_emp=1 GROUP BY co_emp, co_loc, co_tipDoc, co_doc
) AS a1
FULL OUTER JOIN 
(
SELECT co_emp, co_loc, co_tipDoc, co_dia, SUM(nd_monDeb) AS nd_monDeb FROM tbm_detDia WHERE co_emp=1 GROUP BY co_emp, co_loc, co_tipDoc, co_dia
) AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)
WHERE ABS(a1.nd_abo)<>ABS(a2.nd_monDeb)
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*CONTROL14: QUERY PARA OBTENER LOS REGISTROS EN "tbm_pagMovInv" QUE NO CUADRAN CON LO QUE DICE "tbm_detPag".*/
SELECT c1.* 
FROM
(
SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.ne_numDoc, b1.nd_aboModVen
, (CASE WHEN b2.nd_aboModCxC IS NULL THEN 0 ELSE b2.nd_aboModCxC END) AS nd_aboModCxC
FROM
(
/*Deuda del cliente segun Ventas.*/
SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.nd_abo AS nd_aboModVen, a1.co_cli, a1.ne_numDoc
FROM tbm_cabMovInv AS a1
INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDOC AND a1.co_doc=a2.co_doc)
WHERE a1.co_emp=1 
AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') --AND a1.co_cli=96
) AS b1 LEFT OUTER JOIN (
/*Deuda del cliente según CxC.*/
SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC
FROM tbm_cabPag AS a1
INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)
WHERE a1.co_emp=1 
AND a1.st_reg='A' AND a3.st_reg IN ('A','R','C','F') --AND a3.co_cli=96
GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag
) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locPag AND b1.co_tipDoc=b2.co_tipDocPag AND b1.co_doc=b2.co_docPag AND b1.co_reg=b2.co_regPag)
INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli)
ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc
) AS c1
WHERE c1.nd_aboModVen IS NULL OR c1.nd_aboModCxC IS NULL OR c1.nd_aboModVen<>c1.nd_aboModCxC
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*FORMULA QUE UTILIZO EN EXCEL PARA CAMBIAR EL QUERY AL FORMATO QUE UTILIZO EN NetBeans.*/
="strSQLCon[7]+="" " & A1 & """;"
/*----------------------------------------------------------------------------------------------------------------*/
