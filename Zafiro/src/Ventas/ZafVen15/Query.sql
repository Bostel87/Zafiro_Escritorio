/*VARIOS.*/
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbr_bodLocPrgUsr WHERE co_usr=7 LIMIT 10
SELECT * FROM tbr_bodEmpBodGrp LIMIT 10
--UPDATE tbr_bodLocPrgUsr SET st_reg='I' WHERE co_mnu=846

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER LAS BODEGAS.*/
SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom
FROM tbm_emp AS a1
INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)
INNER JOIN (
SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod
FROM tbr_bodLocPrgUsr AS b1
INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)
WHERE b1.co_mnu=846
AND b1.co_usr=7
AND b1.st_reg IN ('A','P')
GROUP BY b2.co_empGrp, b2.co_bodGrp
) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)
ORDER BY a1.co_emp, a2.co_bod
/*----------------------------------------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL STOCK POR BODEGA*/
SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor, b1.nd_preVta1, b2.nd_stkAct AS b2_nd_stkAct, b3.nd_stkAct AS b3_nd_stkAct, b4.nd_stkAct AS b4_nd_stkAct
FROM
(
/*QUERY PARA OBTENER LOS DATOS GENERALES DEL ITEM.*/
SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_preVta1
FROM tbm_inv AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)
WHERE a1.co_emp=1 AND a1.st_reg='A' --AND a2.co_itmMae=6799
) AS b1 LEFT OUTER JOIN (
/*QUERY PARA OBTENER EL STOCK FÍSICO DE LA BODEGA "CALIFORNIA".*/
SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)
INNER JOIN tbr_bodLocPrgUsr AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod)
WHERE a3.co_empGrp=0 AND a3.co_bodGrp=1 --AND a2.co_itmMae=6799
AND a4.co_mnu=846 AND a4.co_usr=7 AND a4.st_reg IN ('A','P')
GROUP BY a2.co_itmMae
) AS b2 ON (b1.co_itmMae=b2.co_itmMae) LEFT OUTER JOIN (
/*QUERY PARA OBTENER EL STOCK FÍSICO DE LA BODEGA "VÍA DAULE".*/
SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)
INNER JOIN tbr_bodLocPrgUsr AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod)
WHERE a3.co_empGrp=0 AND a3.co_bodGrp=2 --AND a2.co_itmMae=6799
AND a4.co_mnu=846 AND a4.co_usr=7 AND a4.st_reg IN ('A','P')
GROUP BY a2.co_itmMae
) AS b3 ON (b1.co_itmMae=b3.co_itmMae) LEFT OUTER JOIN (
/*QUERY PARA OBTENER EL STOCK FÍSICO DE LA BODEGA "QUITO NORTE".*/
SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct
FROM tbm_invBod AS a1
INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)
INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)
INNER JOIN tbr_bodLocPrgUsr AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod)
WHERE a3.co_empGrp=0 AND a3.co_bodGrp=3 --AND a2.co_itmMae=6799
AND a4.co_mnu=846 AND a4.co_usr=7 AND a4.st_reg IN ('A','P')
GROUP BY a2.co_itmMae
) AS b4 ON (b1.co_itmMae=b4.co_itmMae)
ORDER BY b1.tx_codAlt
/*----------------------------------------------------------------------------------------------------------------*/
