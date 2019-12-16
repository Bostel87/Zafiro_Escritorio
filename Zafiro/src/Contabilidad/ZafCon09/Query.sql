Tablas		Insertar	Modificar	Eliminar	Anular
----------------------------------------------------------------------
		PN	SA	PN	SA	PN	SA	PN	SA
                ----------	----------	----------	----------
tbm_cabPag	SI	SI	SI
tbm_detPag	SI	SI	SI
tbm_pagMovInv	SI		SI
tbm_cabDia	SI		SI
tbm_detDia	SI		SI
tbm_cabAutPag		SI
tbm_detAutPag		SI

Simbologáa: PN=Proceso normal; SA=Solicitar autorizacián
/*VARIOS.*/
SELECT * FROM tbm_cabDia LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_detTipDoc LIMIT 10
SELECT * FROM tbr_tipDocPrg LIMIT 10
SELECT * FROM tbm_cabMovBan LIMIT 10
SELECT * FROM tbm_cabAutPag
SELECT * FROM tbm_detAutPag
SELECT * FROM tbm_regNeg

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL DOCUMENTO PREDETERMINADO*/
SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc
FROM tbm_cabTipDoc AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=
(
SELECT co_tipDoc FROM tbr_tipDocPrg WHERE co_emp=1 AND co_loc=1 AND co_mnu=256 AND st_reg='S'
)
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL BENEFICIARIO PREDETERMINADO*/
SELECT a1.co_reg, a1.tx_benChq
FROM tbm_benChq AS a1
WHERE a1.co_emp=1 AND a1.co_cli=812 AND a1.st_reg='P'
/*----------------------------------------------------------------------------------------------------------------*/

/*QUERY PARA OBTENER LA CABECERA.*/
SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia, a1.tx_numDia, a1.fe_dia
FROM tbm_cabDia AS a1, tbm_cabTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
LIMIT 10

/*QUERY PARA DETERMINAR SI EL CAMPO "tx_numDia" EXISTE.*/
SELECT a1.tx_numDia 
FROM tbm_cabDia AS a1  
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=30 AND a1.tx_numDia='205'

/*QUERY PARA OBTENER LAS CUENTAS CONTABLES ASOCIADAS AL TIPO DE DOCUMENTO.*/
SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar
FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta
AND a2.co_emp=1 AND a2.co_loc=1 AND a2.co_tipDoc=18
ORDER BY a2.co_reg

/*QUERY PARA OBTENER LAS CUENTA CONTABLE PREDETERMINADA ASOCIADA A UN TIPO DE DOCUMENTO.*/
SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar
FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta
AND a2.co_emp=1 AND a2.co_loc=1 AND a2.co_tipDoc=18 AND a2.st_reg='S'

/*QUERY PARA OBTENER LAS CUENTAS DEL ASIENTO DE DIARIO (DE ACUERDO AL TIPO DE DOCUMENTO).*/
SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar
FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta
AND a2.co_emp=1 AND a2.co_loc=1 AND a2.co_tipDoc=18 AND 

/*QUERY PARA OBTENER LOS PAGOS QUE TENEMOS QUE HACER A NUESTROS PROVEEDORES.*/
SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen
FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc
AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc
AND a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cli=103 AND (a2.mo_pag+a2.nd_abo)>0 AND a2.nd_porRet=0
AND a2.st_reg IN ('A','C')
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg

/*CONSULTA:QUERY PARA OBTENER EL DETALLE DEL DOCUMENTO.*/
SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a4.nd_abo
FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2, tbm_cabTipDoc AS a3, tbm_detPag AS a4
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc
AND a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc
AND a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag
AND a4.co_emp=1
AND a4.co_loc=1
AND a4.co_tipDoc=36
AND a4.co_doc=1
ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg

/*SOLUCIáN 1: MODIFICACIáN: QUERY PARA OBTENER EL DETALLE DEL DOCUMENTO.*/
SELECT d1.num_ocu, d1.co_loc, d1.co_tipDoc, d3.tx_desCor, d3.tx_desLar, d1.co_doc, d1.co_reg, d1.ne_numDoc
, d1.fe_doc, d1.ne_diaCre, d1.fe_ven, d1.mo_pag, d1.nd_pen, d2.nd_abo
FROM (
/*Documentos pendientes + Documentos pagados.*/
SELECT COUNT(*) AS num_ocu, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_reg, c1.ne_numDoc
, c1.fe_doc, c1.ne_diaCre, c1.fe_ven, c1.mo_pag, c1.nd_pen
FROM (
/*Documentos pendientes.*/
SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen
FROM tbm_cabMovInv AS a1, tbm_pagMovInv AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc
AND a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cli=103 AND (a2.mo_pag+a2.nd_abo)>0 AND a2.nd_porRet=0
AND a2.st_reg IN ('A','C')
UNION ALL
/*Documentos pagados.*/
SELECT b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.co_reg, b1.ne_numDoc
, b1.fe_doc, b2.ne_diaCre, b2.fe_ven, b2.mo_pag, (b2.mo_pag+b2.nd_abo) AS nd_pen
FROM tbm_cabMovInv AS b1, tbm_pagMovInv AS b2, tbm_detPag AS b3
WHERE b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc
AND b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag
AND b3.co_emp=1 AND b3.co_loc=1 AND b3.co_tipDoc=36 AND b3.co_doc=1
) AS c1
GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_reg, c1.ne_numDoc
, c1.fe_doc, c1.ne_diaCre, c1.fe_ven, c1.mo_pag, c1.nd_pen
) AS d1
LEFT OUTER JOIN tbm_detPag AS d2 ON (d1.co_emp=d2.co_emp AND d1.co_loc=d2.co_locPag AND d1.co_tipDoc=d2.co_tipDocPag AND d1.co_doc=d2.co_docPag AND d1.co_reg=d2.co_regPag AND d2.co_emp=1 AND d2.co_loc=1 AND d2.co_tipDoc=36 AND d2.co_doc=1)
LEFT OUTER JOIN tbm_cabTipDoc AS d3 ON (d1.co_emp=d3.co_emp AND d1.co_loc=d3.co_loc AND d1.co_tipDoc=d3.co_tipDoc)
ORDER BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.co_reg

/*SOLUCIáN 2: MODIFICACIáN: QUERY PARA OBTENER EL DETALLE DEL DOCUMENTO.*/
SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc
, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a4.nd_abo
FROM tbm_cabMovInv AS a1
LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
LEFT OUTER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
LEFT OUTER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag AND a4.co_emp=1 AND a4.co_loc=1 AND a4.co_tipDoc=36 AND a4.co_doc=3)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_cli=45 AND ((a2.mo_pag+a2.nd_abo)>0 OR a4.nd_abo IS NOT NULL) AND a2.nd_porRet=0 AND a2.st_reg IN ('A','C')
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg

/*QUERY PARA OBTENER EL ASIENTO DE DIARIO EN FUNCIáN DEL TIPO DE DOCUMENTO.*/
SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta
FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta
AND a1.co_emp=1
AND a1.co_loc=1
AND a1.co_tipDoc=36
UNION ALL
SELECT b1.co_ctaHab AS co_cta, b2.tx_codCta, b2.tx_desLar, 'H' AS tx_natCta
FROM tbm_cabTipDoc AS b1, tbm_plaCta AS b2
WHERE b1.co_emp=b2.co_emp AND b1.co_ctaHab=b2.co_cta
AND b1.co_emp=1
AND b1.co_loc=1
AND b1.co_tipDoc=36

/*QUERY PARA OBTENER EL ASIENTO DE DIARIO EN FUNCIáN DEL TIPO DE DOCUMENTO Y LA CUENTA CONTABLE.*/
SELECT a1.co_ctaDeb AS co_cta, a2.tx_codCta, a2.tx_desLar, 'D' AS tx_natCta
FROM tbm_cabTipDoc AS a1, tbm_plaCta AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta
AND a1.co_emp=1
AND a1.co_loc=1
AND a1.co_tipDoc=36
UNION ALL
SELECT b1.co_cta AS co_cta, b1.tx_codCta, b1.tx_desLar, 'H' AS tx_natCta
FROM tbm_plaCta AS b1
WHERE b1.co_emp=1
AND b1.co_cta=14


/*Pruebas.*/
SELECT * FROM tbm_pagMovInv where co_emp=1 and co_loc=1 and co_tipDoc=2 and co_doc=3
SELECT * FROM tbm_pagMovInv where co_emp=1 and co_loc=1 and co_tipDoc=8 and co_doc=159
update tbm_pagMovInv set nd_abo=0 where co_emp=1 and co_loc=1 and co_tipDoc=2 and co_doc=3 and co_reg=1
update tbm_pagMovInv set nd_abo=0 where co_emp=1 and co_loc=1 and co_tipDoc=8 and co_doc=159 and co_reg=1
SELECT * FROM tbm_detTipDoc WHERE co_tipDoc=36
UPDATE tbm_detTipDoc SET tx_ubiCta='H' WHERE co_tipDoc=36
UPDATE tbm_detTipDoc SET tx_ubiCta='D' WHERE co_tipDoc=36 AND co_reg=3
