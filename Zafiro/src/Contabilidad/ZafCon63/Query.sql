-- Formato Cuentas Contables.
SELECT a.co_for, a.tx_DesCor, a.tx_DesLar  ,*FROM tbm_cabForPlaCta as a WHERE a.co_Emp=1 AND a.st_reg='A' ORDER BY a.co_for;


-- Plan Ctas: Empresa.
SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.tx_nom as NomEmp
FROM tbm_plaCta AS a1 
INNER JOIN tbm_emp as a2 ON (a1.co_Emp=a2.co_emp) 
WHERE a1.st_reg='A' AND a1.co_Emp<>0
AND a1.co_emp=1
--AND co_Cta NOT IN (select co_ctaDet from tbm_detForPlaCta where co_Emp=1 and co_for=1 and  tx_tipCta='D' ) // Se comenta solicitado por Juan Rodas para visualizar todo el formato.
ORDER BY a1.tx_codCta 


-- Plan Ctas: Formato.
---*** Consulta (Cuentas Cabecera)  
SELECT  a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, a2.tx_desLar as nomFor,  a1.ne_niv,       
      (substring(a1.tx_codCta from 1 for 1 ) || '.' || substring(a1.tx_codCta from 2 for 100 )) as tx_codCta2     
FROM tbm_detForPlaCta  as a1  
INNER JOIN tbm_cabForPlaCta as a2 ON (a1.co_Emp=a2.co_emp and a1.co_for=a2.co_for)    
WHERE  a1.tx_tipCta='C' AND a1.co_Emp<>0 AND a1.co_emp=1 AND a1.co_for=1 
ORDER BY  tx_codCta2 

---*** Consulta (Cuentas Detalle)  
SELECT co_cta, tx_codCta, tx_desLar, tx_tipCta, ne_niv, 
FROM tbm_detForPlaCta      
WHERE co_ctaDet>0 AND tx_tipCta='D' AND co_Emp<>0 AND co_emp=1 AND co_for=1 AND ne_pad=4
ORDER BY  tx_codCta, tx_DesLar


--Consulta Naturaleza de Cuenta 
select tx_natCta from tbm_plaCta where co_cta=9 and co_Emp=1

-- Consulta Niveles y Naturaleza de Cuentas de Detalles.
SELECT ne_niv, tx_natCta FROM tbm_detForPlaCta WHERE co_Emp=1 AND co_For=1 AND co_Cta=4 AND tx_tipCta='C';

-- Consulta Ultimo Numero de Cuenta.
SELECT CASE WHEN MAX (co_Cta+1) IS NULL THEN 1 ELSE MAX (co_Cta+1) END AS numCta  
FROM tbm_detForPlaCta  
WHERE co_emp=1 AND co_for=1


--Valida Cuenta Exista
SELECT * FROM tbm_detForPlaCta 
WHERE co_Emp=1 AND co_for=1 AND co_ctaDet>0 and tx_tipCta='D' and co_ctaDet=9 

--Valida Saldo Cuentas
SELECT a.nd_SalCta FROM tbm_salCtaForPlaCta as a 
INNER JOIN tbm_DetForPlaCta as  b ON (a.co_Emp=b.co_Emp and a.co_for=b.co_for and a.co_cta=b.co_cta)
WHERE a.co_emp=1 AND a.co_for=1 --AND a.nd_SalCta > 0.00
AND a.co_cta in (select co_Cta from tbm_DetForPlaCta where co_emp=1 and co_for=1 and co_CtaDet=5)


--Inserta Cuenta Detalle
INSERT INTO tbm_detForPlaCta (co_emp, co_for, co_cta, tx_DesLar, ne_pad, ne_niv, tx_codCta, tx_tipCta, tx_natCta, tx_niv1, co_ctaDet) 
VALUES (1, 1, 41, 'FONDO FIJO GQUIL. (PATRICIA)', 4, 5, '1.01.01.01.05', 'C', 'D', 9);

-- Actualiza Cuenta Contables.
UPDATE tbm_detForPlaCta SET ne_pad=4, ne_niv=5, tx_natCta='D', tx_niv1='1' WHERE co_Emp=1 AND co_For=1 AND co_Cta=41;

-- eliminaCuentasTrasladadas:  
DELETE FROM tbm_DetForPlaCta  WHERE co_Emp=1 AND co_for=1 AND co_CtaDet=3857;