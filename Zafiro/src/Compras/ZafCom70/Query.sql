
/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY CargarDocGuia() */
SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a4.tx_descor, a4.tx_deslar, a.ne_numdoc, a.fe_doc, a.tx_nomclides, 
       a.st_tipguirem, a.tx_datdocoriguirem, a.co_ptoPar , a.co_ptoDes  
FROM tbm_cabguirem as a 
INNER JOIN tbm_cabtipdoc  AS a4 ON (a4.co_emp=a.co_emp and a4.co_loc=a.co_loc and a4.co_tipdoc=a.co_tipdoc ) 
INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) 
INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a7.co_emp=a.co_emp AND a7.co_bod=a.co_ptoDes) 
WHERE  ( a6.co_empGrp=0 AND a6.co_bodGrp=( 15 ) ) AND ( a7.co_empGrp=0 AND a7.co_bodGrp=( 2 ) )  
AND ne_numdoc = 12345
/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY CargarDetReg() */
SELECT *  
FROM ( 
      SELECT cantotconf, case when cantotconf = can then 'C' else case when cantotconf > 0 then  'E' else 'P' end end  as estconf, x.*  
      FROM ( 
               SELECT (cancon+cannunrec+canmalest) as cantotconf, x.*  
               FROM ( 
                     SELECT (case when a4.nd_can is null then 0 else a4.nd_can end ) as can, 
                            (case when a4.nd_cancon is null then 0 else a4.nd_cancon end ) as cancon, 
                             (case when a4.nd_cannunrec is null then 0 else a4.nd_cannunrec end ) as cannunrec, 
                             (case when a4.nd_cantotmalest is null then 0 else a4.nd_cantotmalest end ) as canmalest, 
                             a6.tx_natDoc, a4.co_Bod, a4.st_meringegrfisbod, a4.co_emp, a4.co_loc, a4.co_tipdoc, 
                             a4.co_doc, a4.co_reg, a6.tx_descor, a6.tx_deslar, a5.ne_numdoc, 
                             a5.fe_doc ,a4.co_itm, a4.tx_codalt, a7.tx_codAlt2, a4.tx_nomitm, a4.tx_unimed, a4.nd_can, 
                             a4.nd_cancon, a4.nd_cantotmalest, a4.nd_cannunrec, 
                             a.co_loc as co_locrelguirem, a.co_tipdoc as co_tipDocGuiRem, a.co_doc as co_docrelguirem, a.co_reg as co_regrelguirem,  
                             case when a.nd_cancon is null then 0 else a.nd_cancon end as nd_cancongui, 
                             case when a2.nd_cancon is null then 0 else a2.nd_cancon end as nd_canconguitot,a7.st_impOrd 
                     FROM tbm_detguirem as a 
                     INNER JOIN tbr_detguirem AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg) 
                     INNER JOIN tbm_detguirem AS a2 ON (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_locrel and a2.co_tipdoc=a1.co_tipdocrel and a2.co_doc=a1.co_docrel and a2.co_reg=a1.co_regrel)  
                     INNER JOIN tbr_detmovinv AS a3 ON (a3.co_emp=a2.co_emprel and a3.co_loc=a2.co_locrel and a3.co_tipdoc=a2.co_tipdocrel and a3.co_doc=a2.co_docrel and a3.co_reg=a2.co_regrel)  
                     INNER JOIN tbm_detmovinv AS a4 ON (a4.co_emp=a3.co_emprel and a4.co_loc=a3.co_locrel and a4.co_tipdoc=a3.co_tipdocrel and a4.co_doc=a3.co_docrel and a4.co_reg=a3.co_regrel) 
                     INNER JOIN ( 
                                 SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2,a2.co_bod,a2.st_impOrd 
                                 FROM tbm_inv as a1 
                                 INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) 
                                 ) as a7 ON (a4.co_emp=a7.co_emp AND a4.co_itm=a7.co_itm AND a4.co_bod=a7.co_bod) 
                     INNER JOIN tbm_cabmovinv AS a5 ON (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc and a5.co_doc=a4.co_doc ) 
                     INNER JOIN tbm_cabtipdoc AS a6 ON (a6.co_emp=a5.co_emp and a6.co_loc=a5.co_loc and a6.co_tipdoc=a5.co_tipdoc ) 
                     WHERE  a.co_emp=1 and a.co_loc=10 and a.co_tipdoc=231 and a.co_doc= 4140 
) AS x ) AS x ) AS x /*WHERE estconf in ('P','E')*/ 