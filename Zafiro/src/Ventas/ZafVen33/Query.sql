/*VARIOS.*/
SELECT * FROM tbm_inv LIMIT 10
SELECT * FROM tbm_equInv LIMIT 10
SELECT * FROM tbr_bodEmp LIMIT 10
SELECT * FROM tbm_invBod LIMIT 10
SELECT * FROM tbm_equInv WHERE co_itmMae=1
SELECT * FROM tbm_invBod WHERE co_emp=4 AND co_itm=2204
SELECT * FROM tbm_bod WHERE co_emp=4 ORDER BY co_bod
SELECT * FROM tbm_invMae WHERE co_itmMae=1
SELECT * FROM tbm_emp LIMIT 10
SELECT * FROM tbm_bod LIMIT 10
SELECT * FROM tbm_var LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBTENER EL LISTADO DE ITEMS A PRESENTAR EN LA VENTANA DE CONSULTA.*/

zabven33  
SELECT a.fe_doc,a.co_grp,a.tx_deslar, a.tx_codalt, a.co_itm, a.tx_nomitm, a.val, a.anio,  b.valcate 
FROM(SELECT a.fe_cot as fe_doc,a.co_grp,a.tx_deslar, a.tx_codalt, a.co_itm, a.tx_nomitm, d.val,a.anio,''as valcate 
     FROM(SELECT y.co_grp,y.tx_deslar,y.co_itm, x.fe_cot, y.tx_codalt, y.tx_nomitm , x.anio 
          FROM(SELECT distinct extract(month from c.fe_doc) as fe_cot, extract(Year from c.fe_doc) as anio  
               FROM tbm_cabmovinv c  
               WHERE 1=1   and c.co_emp=1 AND c.fe_doc BETWEEN '2013-01-01' AND '2013-03-31' 
               ) as x  
          INNER JOIN (SELECT CASE WHEN a.co_grp IS NULL THEN 0 ELSE a.co_grp END, a.tx_deslar, b.co_itm, 
                             b.tx_codalt, b.tx_nomitm 
                      FROM (SELECT PADRE.co_grp,PADRE.tx_deslar, a3.co_itm  
                            FROM tbm_grpInvImp as PADRE 
                            INNER JOIN tbm_grpInvImp as HIJO ON (PADRE.co_grp=HIJO.co_grppad) 
                            INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=HIJO.co_grp) 
                            WHERE PADRE.co_grppad IS NULL  and a3.co_emp=1 
                            GROUP BY PADRE.co_grp,PADRE.tx_deslar, HIJO.co_grp, a3.co_grpimp, a3.co_itm 
                            ORDER BY PADRE.co_grp) as a   
                      RIGHT OUTER JOIN 
                      (SELECT distinct a2.co_itm, a3.tx_codalt, a3.tx_nomitm 
                       FROM tbm_cabmovinv as a1 
                       INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND 
                                                          a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
                       INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli) 
                       INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm ) 
                       WHERE a7.co_empGrp IS NULL and a1.co_emp=1 AND 
                             a1.fe_doc BETWEEN '2013-01-01' AND '2013-03-31' AND a1.st_reg IN ('A','R','C','F') and 
                             a1.co_tipdoc in (1,3)  
                      )AS b  ON (a.co_itm=b.co_itm ) 
                      WHERE 1=1 ORDER BY 1 
                      )as y on(x.fe_cot != y.co_itm) 
)AS a LEFT OUTER JOIN(SELECT extract (month from a1.fe_doc) as fe_doc, a3.co_itm, 
                             ROUND(sum(-(a2.nd_can)*a3.nd_pesItmKgr),2) as val 
                      FROM tbm_cabmovinv as a1  
                      INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND 
                                                         a1.co_tipDoc=a4.co_tipDoc) 
                      INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli) 
                      INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND 
                                                         a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
                      INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) 
                      WHERE a1.st_reg IN ('A','R','C','F') AND a1.co_tipdoc in (1,3) AND  
                            a7.co_empGrp IS NULL and a1.co_emp=1 AND 
                            a1.fe_doc BETWEEN '2013-01-01' AND '2013-03-31' 
                      GROUP BY a3.co_itm, extract(month from a1.fe_doc),a3.tx_codalt, a3.tx_nomitm 
                      )as d ON (a.co_itm=d.co_itm and a.fe_cot=d.fe_doc) 
GROUP BY a.co_grp,a.tx_deslar ,a.co_itm, a.tx_codalt, a.tx_nomitm, a.anio, a.fe_cot, d.val 
ORDER BY  a.co_grp, a.tx_codalt, a.co_itm, a.anio, a.fe_cot 
)as a 
LEFT OUTER JOIN ( /* UNION UNION UNION UNION UNION */  
SELECT fe_doc, co_grp,tx_deslar,'' as tx_codalt,'' as co_itm,'' as tx_nomitm, '' as val , anio, sum(val) as valCate   
FROM(SELECT a.fe_cot as fe_doc,a.co_grp,a.tx_deslar, a.tx_codalt, a.co_itm, a.tx_nomitm, d.val, a.anio  
     FROM(SELECT y.co_grp,y.tx_deslar,y.co_itm, x.fe_cot, y.tx_codalt, y.tx_nomitm , x.anio 
          FROM(SELECT distinct extract(month from c.fe_doc) as fe_cot, extract(Year from c.fe_doc) as anio  
               FROM tbm_cabmovinv c  
               WHERE 1=1 and c.co_emp=1 AND c.fe_doc BETWEEN '2013-01-01' AND '2013-03-31' 
               ) as x  
          INNER JOIN (SELECT CASE WHEN a.co_grp IS NULL THEN 0 ELSE a.co_grp END, a.tx_deslar, b.co_itm, 
                             b.tx_codalt, b.tx_nomitm 
                      FROM (SELECT PADRE.co_grp,PADRE.tx_deslar, a3.co_itm  
                            FROM tbm_grpInvImp as PADRE 
                            INNER JOIN tbm_grpInvImp as HIJO ON (PADRE.co_grp=HIJO.co_grppad) 
                            INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=HIJO.co_grp) 
                            WHERE PADRE.co_grppad IS NULL and a3.co_emp=1 
                            GROUP BY PADRE.co_grp,PADRE.tx_deslar, HIJO.co_grp, a3.co_grpimp, a3.co_itm 
                            ORDER BY PADRE.co_grp
                            ) as a   
                      RIGHT OUTER JOIN(SELECT distinct a2.co_itm, a3.tx_codalt , a3.tx_nomitm 
                                       FROM tbm_cabmovinv as a1 
                                       INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND 
                                                                          a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
                                     INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli) 
                                     INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm ) 
                                     WHERE a7.co_empGrp IS NULL and a1.co_emp=1 AND 
                                           a1.fe_doc BETWEEN '2013-01-01' AND '2013-03-31' AND 
                                           a1.st_reg IN ('A','R','C','F') and a1.co_tipdoc in (1,3)  
                                     )AS b  ON (a.co_itm=b.co_itm ) 
                     WHERE 1=1 ORDER BY 1 
                     )as y on(x.fe_cot != y.co_itm) 
)AS a 
LEFT OUTER JOIN(SELECT extract (month from a1.fe_doc) as fe_doc , a3.co_itm, 
                       ROUND(sum(-(a2.nd_can)*a3.nd_pesItmKgr),2) as val 
                FROM tbm_cabmovinv as a1  
                INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND 
                                                   a1.co_tipDoc=a4.co_tipDoc) 
                INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli) 
                INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND 
                                                   a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) 
                INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) 
                WHERE a1.st_reg IN ('A','R','C','F') AND a1.co_tipdoc in (1,3) AND  
                      a7.co_empGrp IS NULL and a1.co_emp=1 AND a1.fe_doc BETWEEN '2013-01-01' AND '2013-03-31' 
                GROUP BY a3.co_itm, extract(month from a1.fe_doc),a3.tx_codalt, a3.tx_nomitm 
                )as d ON (a.co_itm=d.co_itm and a.fe_cot=d.fe_doc) 
GROUP BY a.co_grp,a.tx_deslar ,a.co_itm, a.tx_codalt, a.tx_nomitm, a.anio, a.fe_cot, d.val 
) as x 
GROUP BY  fe_doc,co_grp,tx_deslar, anio 
ORDER BY co_grp, anio, fe_doc  
) as B ON (a.fe_doc=b.fe_doc and a.co_grp=b.co_grp and a.anio=b.anio)  
ORDER BY /*a.co_grp ,*/a.tx_deslar,a.co_grp,a.tx_nomitm , a.anio, a.fe_doc 
/*----------------------------------------------------------------------------------------------------------------*/
