--Listado de Guías de Remisión (Cabecera)
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a1.tx_datDocOriGuiRem, a1.ne_numDoc, a1.fe_doc, a1.co_cliDes, a1.tx_nomCliDes, a4.co_bodGrp, a2.tx_nom AS a2_tx_nom, a1.nd_pesTotKgr
, a1.co_veh, (CASE WHEN a1.co_veh IS NULL THEN a1.tx_plaVehTra ELSE a5.tx_pla END) AS tx_plaVehTra
, a1.co_cho, (CASE WHEN a1.co_cho IS NULL THEN a1.tx_ideTra ELSE a6.tx_ide END) AS tx_ideTra, (CASE WHEN a1.co_cho IS NULL THEN a1.tx_nomTra ELSE a6.tx_nom || ' ' || a6.tx_ape END) AS tx_nomTra
, a1.st_reg, a1.fe_ing, a1.co_usrIng, a7.tx_usr AS a7_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a8.tx_usr AS a8_tx_usr, a1.tx_comUltMod
FROM tbm_cabGuiRem AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_ptoPar=a2.co_bod)
INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)
INNER JOIN tbr_bodEmpBodGrp AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)
LEFT OUTER JOIN tbm_veh AS a5 ON (a1.co_veh=a5.co_veh)
LEFT OUTER JOIN tbm_tra AS a6 ON (a1.co_cho=a6.co_tra)
LEFT OUTER JOIN tbm_usr AS a7 ON (a1.co_usrIng=a7.co_usr)
LEFT OUTER JOIN tbm_usr AS a8 ON (a1.co_usrMod=a8.co_usr)
WHERE (a1.fe_doc>='2014/10/01' AND a1.fe_doc<='2014/10/31') AND a1.ne_numOrdDes=0
AND a4.co_empGrp=0 AND a4.co_bodGrp=1
ORDER BY a4.co_bodGrp, a1.ne_numDoc



--Listado de Guías de Remisión (Detallado)
SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.tx_datDocOriGuiRem, a1.ne_numDoc, a1.fe_doc, a1.co_cliDes, a1.tx_nomCliDes, a5.co_bodGrp, a3.tx_nom AS a3_tx_nom
, a2.co_itm, a2.tx_codAlt, a2.tx_nomItm, a2.tx_uniMed, a2.nd_can, a2.nd_pesTotKgr
, a1.co_veh, (CASE WHEN a1.co_veh IS NULL THEN a1.tx_plaVehTra ELSE a6.tx_pla END) AS tx_plaVehTra
, a1.co_cho, (CASE WHEN a1.co_cho IS NULL THEN a1.tx_ideTra ELSE a7.tx_ide END) AS tx_ideTra, (CASE WHEN a1.co_cho IS NULL THEN a1.tx_nomTra ELSE a7.tx_nom || ' ' || a7.tx_ape END) AS tx_nomTra
, a1.st_reg
FROM tbm_cabGuiRem AS a1
INNER JOIN tbm_detGuiRem AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)
INNER JOIN tbm_bod AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ptoPar=a3.co_bod)
INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)
INNER JOIN tbr_bodEmpBodGrp AS a5 ON (a3.co_emp=a5.co_emp AND a3.co_bod=a5.co_bod)
LEFT OUTER JOIN tbm_veh AS a6 ON (a1.co_veh=a6.co_veh)
LEFT OUTER JOIN tbm_tra AS a7 ON (a1.co_cho=a7.co_tra)
WHERE a1.ne_numOrdDes=0 AND (a1.fe_doc>='2014/10/01' AND a1.fe_doc<='2014/10/15')
AND a5.co_empGrp=0 AND a5.co_bodGrp=1
ORDER BY a5.co_bodGrp, a1.ne_numDoc, a2.co_reg



--Listado de Vehículos.
SELECT a1.co_veh, a1.tx_pla, a1.tx_desLar
SELECT a1.*
FROM tbm_veh AS a1
ORDER BY a1.co_veh



--Listado de Choferes.
SELECT a1.co_tra, a1.tx_ide, (a1.tx_nom || ' ' || a1.tx_ape) AS tx_nomTra
FROM tbm_tra AS a1
INNER JOIN tbm_traEmp AS a2 ON (a1.co_tra=a2.co_tra)
INNER JOIN tbm_carLab AS a3 ON (a2.co_car=a3.co_car)
INNER JOIN tbm_carLabPre AS a4 ON (a3.co_carPre=a4.co_carPre)
WHERE a1.st_reg='A'
ORDER BY tx_nomTra




