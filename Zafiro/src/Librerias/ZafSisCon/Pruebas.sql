SELECT * FROM tbm_inv WHERE co_emp=1 AND tx_codAlt='E6'
SELECT * FROM tbm_equInv WHERE co_emp=1 AND co_itm=9772
SELECT * FROM tbm_invMae WHERE co_itmMae=11090
UPDATE tbm_invMae SET nd_cosUni=1, nd_stkAct=2 WHERE co_itmMae=11090
SELECT * FROM tbm_detMovInv WHERE co_emp=1 AND co_itm=9772
UPDATE tbm_detMovInv SET nd_cosUni=1 WHERE co_emp=1 AND co_itm=9772

SELECT * FROM tbm_inv WHERE co_emp=1 AND tx_codAlt='E7'
SELECT * FROM tbm_equInv WHERE co_emp=1 AND co_itm=9773
SELECT * FROM tbm_invMae WHERE co_itmMae=11091
UPDATE tbm_invMae SET nd_cosUni=1, nd_stkAct=2 WHERE co_itmMae=11091
SELECT * FROM tbm_detMovInv WHERE co_emp=1 AND co_itm=9773
UPDATE tbm_detMovInv SET nd_cosUni=1 WHERE co_emp=1 AND co_itm=9772