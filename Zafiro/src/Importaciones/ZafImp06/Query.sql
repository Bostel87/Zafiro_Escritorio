

/*----------------------------------------------------------------------------------------------------------------*/
 SELECT a3.co_itm,a3.tx_codalt as tx_codAltItm,a3.tx_nomitm, a4.tx_desLar as tx_uniMed, 
PADRE.co_grp, PADRE.tx_desCor as tx_desCorGru, PADRE.tx_deslar as tx_desLarGru 
FROM tbm_grpInvImp as PADRE 
INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=PADRE.co_grp) 
INNER JOIN tbm_var as a4 ON (a3.co_uni=a4.co_reg) 
WHERE a3.tx_codAlt like '%I' and a3.co_emp=0 AND 
((LOWER(a3.tx_codAlt) BETWEEN '680' AND '680') OR LOWER(a3.tx_codAlt) LIKE '680%') 
ORDER BY a3.tx_codalt
/*----------------------------------------------------------------------------------------------------------------*/
