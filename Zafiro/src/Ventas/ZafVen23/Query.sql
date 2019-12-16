/*Varios.*/
SELECT * FROM tbm_cabCotVen LIMIT 10
SELECT * FROM tbm_usr LIMIT 10

/*----------------------------------------------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------------------------------------------*/
/*QUERY PARA OBRENER EL LISTADO DE COTIZACIONES DE VENTA Y FACTURAS.*/
select a.fe_cot, a.co_usr ,a.tx_nom, b.conteo, ROUND(b.suma,2) AS suma, c.conteoFac, ROUND(c.sumaFac,2) as sumaFac 
from ( select co_usr, fe_cot, tx_nom from ( select distinct c.fe_cot from tbm_cabcotven c where 1=1 and c.co_emp=1 AND c.fe_cot BETWEEN '2013-01-01' AND '2013-01-31' ) 
as x inner join ( select distinct u.co_usr , u.tx_nom from tbm_cabcotven c, tbm_usr u where u.co_usr = c.co_ven and c.co_emp=1 AND c.fe_cot BETWEEN '2013-01-01' AND '2013-01-31' ) 
as y on (x.fe_cot != y.co_usr) ) AS a 
left outer join ( select u.co_usr, c.fe_cot, count(c.co_cot) as conteo, sum(c.nd_tot) as suma 
from tbm_cabcotven c, tbm_usr u where c.co_ven = u.co_usr and c.co_emp=1 AND c.fe_cot BETWEEN '2013-01-01' AND '2013-01-31' 
group by u.co_usr, c.fe_cot) AS b on (a.co_usr = b.co_usr and a.fe_cot = b.fe_cot) 
left outer join ( select u.co_usr, c.fe_cot, count(c.co_cot) as conteoFac, sum(c.nd_tot) as sumaFac 
from tbm_cabcotven c, tbm_usr u where c.co_ven = u.co_usr and c.co_emp=1 AND c.fe_cot BETWEEN '2013-01-01' AND '2013-01-31' and c.st_reg='F' group by u.co_usr, c.fe_cot) 
AS c on (a.co_usr = c.co_usr and a.fe_cot = c.fe_cot) order by  a.co_usr ,a.fe_cot

/*----------------------------------------------------------------------------------------------------------------*/

