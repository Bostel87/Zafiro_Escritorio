/*VARIOS.*/
SELECT * FROM tbm_cabDia LIMIT 10
SELECT * FROM tbm_cabTipDoc LIMIT 10
SELECT * FROM tbm_detTipDoc LIMIT 10
SELECT * FROM tbr_tipDocPrg LIMIT 10
SELECT * FROM tbm_cabMovBan LIMIT 10

/*QUERY PARA OBTENER EL DOCUMENTO PREDETERMINADO*/
SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc
FROM tbm_cabTipDoc AS a1
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=
(
SELECT co_tipDoc FROM tbr_tipDocPrg WHERE co_emp=1 AND co_loc=1 AND co_mnu=256 AND st_reg='S'
)

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
SELECT a2.co_loc, a2.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a2.co_doc, a2.co_reg, a3.ne_numDoc, a3.fe_doc
, a2.co_tipRet, a5.tx_desCor AS a5_tx_desCor, a5.tx_desLar AS a5_tx_desLar, a2.nd_porRet, a2.tx_aplRet
, a3.nd_sub, a3.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a1.nd_abo
FROM tbm_detPag AS a1 
LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg) 
LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)
LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)
LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet)
WHERE a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=37 AND a1.co_doc=1 
ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg

/*MODIFICACIáN: QUERY PARA OBTENER EL DETALLE DEL DOCUMENTO.*/
SELECT a2.co_loc, a2.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a2.co_doc, a2.co_reg, a3.ne_numDoc, a3.fe_doc
, a2.co_tipRet, a5.tx_desCor AS a5_tx_desCor, a5.tx_desLar AS a5_tx_desLar, a2.nd_porRet, a2.tx_aplRet
, a3.nd_sub, a3.nd_porIva, a2.mo_pag, (a2.mo_pag+a2.nd_abo) AS nd_pen, a1.nd_abo
FROM tbm_detPag AS a1 
RIGHT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg AND a1.co_emp=1 AND a1.co_loc=1 AND a1.co_tipDoc=44 AND a1.co_doc=1) 
LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)
LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)
LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet)
WHERE a3.co_emp=1 AND a3.co_cli=1 AND ((a2.mo_pag+a2.nd_abo)>0 OR a1.nd_abo IS NOT NULL) AND a2.nd_porRet>0 AND a2.st_reg IN ('A','C')
ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg

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

/*CONTROLES QUE SERáN APLICADOS A áSTE PROGRAMA.*/
SELECT * FROM tbm_regNeg WHERE co_emp=1 AND co_loc=1
SELECT MAX(co_reg) FROM tbm_regNeg WHERE co_emp=1 AND co_loc=1
DELETE FROM tbm_regNeg WHERE co_emp=1 AND co_loc=1
-------------------------------------------------
INSERT INTO tbm_regNeg(co_emp, co_loc, co_reg, tx_desCor, tx_desLar, co_mnu, tx_nomFun, st_aut, tx_obs1, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)
VALUES (1, 1, 1, 'Autorizacián de cheque', 'Controla si se debe solicitar autorizacián para emitir cheques del banco seleccionado ', 574, 'validarAutCta', 'S', Null, 'A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1)

/*Pruebas.*/
SELECT * FROM tbm_pagMovInv where co_emp=1 and co_loc=1 and co_tipDoc=2 and co_doc=3
SELECT * FROM tbm_pagMovInv where co_emp=1 and co_loc=1 and co_tipDoc=8 and co_doc=159
update tbm_pagMovInv set nd_abo=0 where co_emp=1 and co_loc=1 and co_tipDoc=2 and co_doc=3 and co_reg=1
update tbm_pagMovInv set nd_abo=0 where co_emp=1 and co_loc=1 and co_tipDoc=8 and co_doc=159 and co_reg=1
SELECT * FROM tbm_detTipDoc WHERE co_tipDoc=36
UPDATE tbm_detTipDoc SET tx_ubiCta='H' WHERE co_tipDoc=36
UPDATE tbm_detTipDoc SET tx_ubiCta='D' WHERE co_tipDoc=36 AND co_reg=3

update tbm_pagMovInv set co_tipRet=1, tx_aplRet='S' where co_emp=1 and co_loc=1 and co_tipDoc=2 and co_doc=14 and co_reg=2
update tbm_pagMovInv set co_tipRet=2, tx_aplRet='I' where co_emp=1 and co_loc=1 and co_tipDoc=2 and co_doc=2484 and co_reg=4
