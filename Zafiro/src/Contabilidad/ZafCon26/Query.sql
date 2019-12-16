/*Varios.*/
SELECT * FROM tbm_cabMovInv
SELECT * FROM tbm_detMovInv
SELECT * FROM tbm_cabTipDoc
SELECT * FROM tbm_cli
SELECT * FROM tbm_cabForPag

/*Obtener el listado de documentos.*/
SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_ordDoc, a1.ne_numDoc, a1.fe_doc
, a1.co_cli, a1.tx_nomCli, a1.co_forPag, a1.tx_desForPag, a1.nd_sub, a1.nd_porIva, a1.nd_tot, a1.st_reg
FROM tbm_cabMovInv AS a1, tbm_cabTipDoc AS a2
WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc
ORDER BY a1.co_emp, a1.co_loc, a1.ne_ordDoc

============================ R  E  T  E  N  C  I  O  N  E  S =============================

------QUERY para formulario de Retenciones---7754 Reg sin agruparlos por a7.co_tipdoc--
SELECT
a6.tx_ide as RUC, a7.ne_numdoc1 as NumRet, a3.fe_doc as FecReg, a3.fe_doc as FecEmi,
case a3.co_tipdoc
     when 1 then '01'
     when 2 then '01'
     when 38 then '01'
     when 57 then '03'
     else 'tipdoc'
  end as TipComp,
a3.ne_numDoc as ComVen, a3.tx_numAutSRI as NumAut, a3.tx_fecCad as FecCad, trunc(abs(a3.nd_sub),2) AS Base,
a3.tx_codsri as CodRet, trunc(abs(a3.nd_sub*0.01),2) AS ValRet
FROM tbm_detPag AS a1
RIGHT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc
AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)
LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc
AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)
LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc
AND a2.co_tipDoc=a4.co_tipDoc)
LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet)
LEFT OUTER JOIN tbm_cli AS a6 ON (a6.co_emp = a3.co_emp AND a6.co_cli = a3.co_cli)
LEFT OUTER JOIN tbm_cabpag AS a7 ON (a7.co_emp = a1.co_emp AND a7.co_loc = a1.co_loc
AND a7.co_tipdoc = a1.co_tipdoc AND a7.co_doc = a1.co_doc)
WHERE a3.co_emp= 1 and a3.fe_doc>='2006-01-01' and a2.nd_porRet=1 and a2.co_tipdoc in (1,2,38)
--and a7.co_tipdoc in (25,33,47) --agrupados por retenciones de documentos--hay 5616 reg--
AND a3.st_reg IN ('A','R','C','F')
AND ((a2.mo_pag+a2.nd_abo)>0 OR a1.nd_abo IS NOT NULL)
AND a2.nd_porRet>0
AND a2.st_reg IN ('A','C')
ORDER BY
a3.fe_doc, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg


---------MEJOR OPCION PARA MOSTRAR REPORTE DE RETENCIONES-----------------
-------------SOLO PARA RETENCIONES EN FACTURAS DE VENTAS Y CXCINI---------------
------4212 Reg-----
SELECT a3.tx_ide as RUC_CED, a2.tx_secdoc as Secdoc,LPAD(a4.tx_numChq,7,'0') as NumRet, a2.fe_doc as FecReg, 
a2.fe_doc as FecEmi,
case a2.co_tipdoc
                  when 1 then '01'
                  when 2 then '01'
                  when 7 then '01'
                  when 38 then '01'
                  when 57 then '03'  end as  TipCom,
'001001'||LPAD(a2.ne_numDoc,7,'0') as ComVen, a2.tx_numAutSRI as NumAut, a2.tx_fecCad as FecCad,
case a3.tx_tipper
                  when 'J' then round(abs(a2.nd_sub),2)
                  when 'N' then round(abs(a2.nd_sub),2)
                  when 'C' then round(abs(a2.nd_sub)*0.12,2)   end AS Base,
case a3.tx_tipper
                  when 'C' then '30B'
                  when 'J'  then '307'
                  when 'N' then '307'   end AS CodRet,
case a3.tx_tipper
                  when 'J' then round(abs(a2.nd_sub)*0.01,2)
                  when 'N' then round(abs(a2.nd_sub)*0.01,2)
                  when 'C' then round(abs(a2.nd_sub)*0.12*0.30,2) end AS ValRet
from tbm_pagmovinv as a1
inner join tbm_cabmovinv as a2
   on(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)
inner join tbm_cli as a3
   on(a2.co_emp=a3.co_emp AND a2.co_cli=a3.co_cli)
inner join tbm_detpag as a4
   on(a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_locpag AND a1.co_tipdoc=a4.co_tipdocpag
   AND a1.co_doc=a4.co_docpag AND a1.co_reg=a4.co_regpag)
inner join tbm_cabpag as a5
   on(a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipdoc=a5.co_tipdoc AND a4.co_doc=a5.co_doc)
inner join tbm_cabtipdoc as a6
  on(a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc)
inner join tbm_loc as a7
 on(a6.co_emp = a7.co_emp and a6.co_loc = a7.co_loc )--and a7.tx_secdoc = '001-001'
where a1.co_emp=1 and a1.co_loc=1 
and a5.co_tipdoc in (25,33)
and a1.co_tipdoc in (1,7)
ORDER BY a5.ne_numdoc1, a2.fe_doc