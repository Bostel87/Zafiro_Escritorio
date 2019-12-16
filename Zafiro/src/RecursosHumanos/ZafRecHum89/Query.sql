/* QUERY - COMBO PARA CARGAR PERIODO */
SELECT ne_ani,ne_mes FROM tbm_ingegrmentra GROUP BY ne_ani,ne_mes ORDER BY ne_ani desc, ne_mes desc;

/* QUERY - VENTANAS DE CONSULTA */
    --* CONSULTA RUBROS. *--
    SELECT a.co_rub, a.tx_nom FROM tbm_rubRolPag as a
    INNER JOIN tbm_ingEgrMenPrgTra as b ON (a.co_rub=b.co_rub)
    WHERE a.tx_tipRub='I'  AND a.tx_tipValRub='M'   AND a.st_Reg='A'
    GROUP BY a.co_rub, a.tx_nom
    ORDER BY a.co_rub


    --* CONSULTA DE EMPRESAS. *--
    --- Por Empresas.  
    SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom FROM tbm_Emp AS a1 WHERE a1.co_emp=1
    --- Por Grupo
    SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom FROM tbm_Emp AS a1 

    --* CONSULTA DE EMPLEADOS *--
    --- Por Empresas.   
    SELECT a.co_tra,(a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra FROM tbm_tra a  INNER JOIN tbm_traemp b ON(a.co_tra=b.co_tra)  WHERE b.st_reg like 'A'  AND b.co_emp<>0 AND b.co_emp =1 ORDER BY (a.tx_ape || ' ' || a.tx_nom) 
    --- Por Grupo
    SELECT a.co_tra,(a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra FROM tbm_tra a  INNER JOIN tbm_traemp b ON(a.co_tra=b.co_tra)  WHERE b.st_reg like 'A'  AND b.co_emp<>0 ORDER BY (a.tx_ape || ' ' || a.tx_nom) 


/* QUERY - VALIDACIONES */
    --* VALIDA ROL DE PAGO. *--
    SELECT * FROM tbm_cabrolpag  WHERE co_emp = 1 AND co_loc = 4 AND co_tipdoc in (192) AND ne_ani = 2016 AND ne_mes = 1  AND ne_per in (1) AND st_reg = 'A';
    
    --* VALIDA RRHH. *--
    select st_revfal, fe_autrevfal,co_usrrevfal, tx_comrevfal 
    from tbm_feccorrolpag 
    where co_emp <> 0
    and co_emp = 1
    and ne_ani =2016
    and ne_mes =3
    and st_RevFal='S' 

    --* VERIFICA DATOS EXISTENTES. *--
    SELECT * FROM tbm_ingegrmenprgtra  WHERE co_Emp <>0 AND co_Emp=1 AND co_tra=8 AND ne_Ani=2016 AND ne_mes=3 AND co_rub=33

/* QUERY - CARGAR DETALLE 1 */
-- En caso de que existan rubros en el periodo seleccionado.
SELECT b.co_emp, c.tx_nom as tx_nomEmp, b.co_tra, (a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra, d.nd_valRub 
FROM tbm_Tra as a 
INNER JOIN tbm_traEmp as b ON (a.co_tra=b.co_tra) 
INNER JOIN tbm_Emp as c ON (b.co_Emp=c.co_emp) 
INNER JOIN 
( 
    select * from tbm_ingEgrMenPrgTra 
    where co_Emp <>0
    and ne_Ani=2016
    and ne_mes=3 and co_rub=33
) as d ON (b.co_Emp=d.co_Emp AND a.co_Tra=d.co_Tra) 
WHERE b.st_reg='A' AND b.co_Emp <>0 AND b.co_emp = 1 AND b.st_recComVen='S' AND d.co_rub=33 AND d.ne_Ani=2016 AND d.ne_mes=3 
ORDER BY b.co_emp, tx_nomTra 


/* QUERY - CARGAR DETALLE 2 */
-- En caso de que NO existan rubros en el periodo seleccionado.
SELECT b.co_emp, c.tx_nom as tx_nomEmp, b.co_tra, (a.tx_Ape ||' '|| a.tx_nom) as tx_nomTra 
FROM tbm_Tra as a 
INNER JOIN tbm_traEmp as b ON (a.co_tra=b.co_tra) 
INNER JOIN tbm_Emp as c ON (b.co_Emp=c.co_emp) 
WHERE b.st_reg='A' AND b.co_Emp <>0 AND b.co_emp = 1 AND b.st_recComVen='S' 
ORDER BY b.co_emp, tx_nomTra 


/* QUERY - ACTUALIZA DATOS */
UPDATE tbm_ingEgrMenPrgTra  SET nd_valRub=2.0 WHERE co_Emp <>0 AND co_Emp=1 AND co_tra=8 AND ne_Ani=2016 AND ne_mes=3 AND co_rub=33 ; 

/* QUERY - INGRESA DATOS */
INSERT INTO tbm_ingEgrMenPrgTra (  co_Emp, co_tra, co_rub,  ne_Ani, ne_mes, nd_valRub)  VALUES(  1, 45, 33,  2016, 5, 0.0  ) ; 


