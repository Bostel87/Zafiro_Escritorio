
/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBRENER EL LISTADO DE VENTAS (DETALLADO POR ITEM).*/
SELECT a.co_emp, a.co_loc, a.co_tipDoc, a2.tx_desCor as desCorTipDoc, a2.tx_desLar as desLarTipDoc, a.co_doc, a.ne_numDoc,
       a.fe_doc, a.co_cli, a.tx_nomCli, a.co_forPag, a.tx_desForPag, a1.co_itm, a1.tx_CodAlt, a1.tx_nomItm, a1.tx_uniMed,
       abs(a1.nd_Can) as CanItm , a1.nd_preUni, a1.nd_porDes, a1.nd_tot, a.st_reg   
FROM tbm_CabMovInv as a 
INNER JOIN tbm_DetMovInv as a1 ON (a.co_emp=a1.co_Emp and a.co_loc=a1.co_loc and a.co_tipDoc=a1.co_TipDoc and a.co_doc=a1.co_doc)  
INNER JOIN tbm_CabTipDoc as a2 ON (a.co_Emp=a2.co_Emp and a.co_loc=a2.co_loc and a.co_tipDoc=a2.co_tipDoc) 
LEFT OUTER JOIN tbm_Cli as a3 ON (a.co_emp=a3.co_emp and a.co_cli=a3.co_cli)
WHERE  a.co_tipDoc IN ( select b.co_tipdoc from tbr_tipdocprg b where b.co_emp=1 and b.co_loc=4 and b.co_mnu=3516) 
AND a.fe_doc BETWEEN '2015-06-01' AND '2015-06-30' 
AND (  (a.co_emp=1 and a.co_loc=4)  OR  (a.co_emp=1 and a.co_loc=10)  ) 
AND a.co_cli = 1265 
AND a.co_com = 8 
ORDER BY a.co_emp, a.co_loc, a.co_tipDoc, a.fe_doc, a.ne_numDoc 
/*----------------------------------------------------------------------------------------------------------------*/

