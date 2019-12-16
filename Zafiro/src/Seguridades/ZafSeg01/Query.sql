/*VARIOS.*/
SELECT * FROM tbr_perUsr
SELECT * FROM tbm_mnuSis

/*QUERY PARA OBTENER EL MENÚ DEL SISTEMA.*/
SELECT a1.co_mnu, a1.tx_tipMnu, a1.tx_nom, a1.tx_des, a1.ne_pad ,a1.ne_niv, a1.tx_acc 
FROM tbm_mnuSis AS a1 
WHERE a1.tx_tipMnu IN ('M','C','F','S') AND a1.st_reg='A' 
ORDER BY a1.ne_ubi

/*QUERY PARA OBTENER LAS OPCIONES A LAS QUE TIENE ACCESO EL USUARIO EN EL MENÚ DEL SISTEMA.*/
SELECT a1.co_mnu, a1.tx_tipMnu, a1.tx_nom, a1.tx_des, a1.ne_pad ,a1.ne_niv, a1.tx_acc
FROM tbm_mnuSis AS a1
LEFT OUTER JOIN tbr_perUsr AS a2 ON (a1.co_mnu=a2.co_mnu)
WHERE a2.co_emp=1 AND a2.co_loc=1 AND a2.co_usr=2 AND a1.tx_tipMnu IN ('M','C','F','S') AND a1.st_reg='A' 
ORDER BY a1.ne_ubi


