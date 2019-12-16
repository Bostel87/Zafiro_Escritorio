/* QUERY - COMBO PARA CARGAR PERIODO */
SELECT ne_ani,ne_mes  FROM tbm_ingegrmentra  GROUP BY ne_ani,ne_mes  ORDER BY ne_ani desc, ne_mes desc; 


/* QUERY - VENTANAS DE CONSULTA */
    --* CONSULTA DEPARTAMENTOS. *--
    SELECT co_dep, tx_descor, tx_deslar, st_reg   FROM tbm_dep  WHERE st_reg like 'A'  ORDER BY co_dep


    --* CONSULTA DE EMPRESAS. *--
    --- Por Empresas.  
    SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom FROM tbm_Emp AS a1 WHERE a1.co_emp=1
    --- Por Grupo
    SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom FROM tbm_Emp AS a1 

    --* CONSULTA DE EMPLEADOS *--
    --- Por Empresas.   
    SELECT a.co_tra,(a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra  FROM tbm_tra a  INNER JOIN tbm_traemp b ON(a.co_tra=b.co_tra)  WHERE b.st_reg like 'A'  AND b.co_emp<>0 AND b.co_emp =1 ORDER BY tx_nomTra 
    --- Por Grupo
    SELECT a.co_tra,(a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra  FROM tbm_tra a  INNER JOIN tbm_traemp b ON(a.co_tra=b.co_tra)  WHERE b.st_reg like 'A'  AND b.co_emp<>0 ORDER BY tx_nomTra 


/* QUERY - CARGAR DETALLE  */
SELECT a.co_emp, e.tx_nom as tx_nomEmp, a.co_Tra, (c.tx_ape ||' '|| c.tx_nom) as tx_nomTra, a.ne_tipPro, 
       a.nd_sue  as ValTotIng, d.DiaLab, a.nd_valSbu as SBU,  a.nd_porFonRes as PorFonRes, a.nd_porApoPatIes as PorApoPat, 
       a.nd_valDecTerSue as ValDTS, a.nd_valDecCuaSue as ValDCS,  a.nd_valFonRes as ValFonRes, a.nd_valVac as ValVac, a.nd_valApoPatIes as ValApoPatIes 
 FROM tbm_benSocMenTra as a 
 INNER JOIN tbm_traEmp as b ON (a.co_emp=b.co_Emp and a.co_Tra=b.co_tra) 
 INNER JOIN tbm_tra as c ON (b.co_Tra=c.co_tra) 
 INNER JOIN 
 ( 
   select  co_Emp, co_Tra, ne_numDiaLab as DiaLab 
   from tbm_datGenIngEgrMenTra 
   where (ne_Ani=2016 and ne_mes=2) 
   group by co_emp, co_Tra, DiaLab 
 ) as d ON (d.co_Emp=b.co_Emp and d.co_Tra=b.co_Tra) 
 INNER JOIN tbm_emp as e ON (b.co_emp=e.co_emp) 
 WHERE b.st_Reg='A' 
 AND (a.ne_Ani=2016 and a.ne_mes=2) 
 AND a.co_Tra NOT IN (select co_tra from tbm_traEmp where st_Reg='A' and (tx_forPagDecTerSue='M' or tx_forPagDecCuaSue='M' ))
 GROUP BY  a.co_emp, tx_nomEmp, a.co_Tra,  tx_nomTra, a.ne_tipPro, 
           ValTotIng, d.DiaLab,  SBU,  PorFonRes,  PorApoPat, ValDTS, ValDCS,  ValFonRes, ValVac,  ValApoPatIes 
 ORDER BY a.co_emp, tx_nomTra  