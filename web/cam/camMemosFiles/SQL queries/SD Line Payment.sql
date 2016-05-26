SELECT 
    xt1.DCM_CODE,
    xt1.DCM_NAME AS "DCM Name",
    xt1.COMMISSION_ITEM_NAME AS "ITEM NAME",
    xt1.COMMISSION_ITEM_AMOUNT AS "ITEM_COUNT",
    xt1.COMMISSION_FACTOR_VALUE AS "FACTOR VALUE",  
    xt1.xcommission_value AS "COMMISSION_VALUE" 
   
FROM        
        (SELECT tt8.DCM_ID, tt8.DCM_NAME  , tt8.DCM_CODE  DCM_CODE , tt6.COMMISSION_ITEM_NAME, tt6.COMMISSION_ITEM_AMOUNT, tt7.COMMISSION_FACTOR_VALUE,(tt6.COMMISSION_ITEM_AMOUNT * tt7.COMMISSION_FACTOR_VALUE) xcommission_value 
        
       
        FROM 
        
            CAM_MEMO tt1 
            JOIN CAM_PAYMENT tt2 ON (tt2.MEMO_ID = tt1.MEMO_ID)
            JOIN PAYMENT_DETAIL tt3 ON (tt2.PAYMENT_ID = tt3.PAYMENT_DETAIL_ID)
            JOIN PAYMENT_COMMISSION tt4 ON (tt4.PAYMENT_DETAIL_ID = tt3.PAYMENT_DETAIL_ID ) 
            JOIN COMMISSION_DETAIL tt5 ON (tt5.COMMISSION_DETAIL_ID = tt4.COMMISSION_DETAIL_ID)
            JOIN COMMISSION_ITEM tt6 ON (tt6.COMMISSION_DETAIL_ID = tt5.COMMISSION_DETAIL_ID )
            JOIN COMMISSION_FACTOR tt7 ON (tt6.COMMISSION_FACTOR_ID = tt7.COMMISSION_FACTOR_ID )
            JOIN GEN_DCM tt8 ON (tt6.DCM_ID = tt8.DCM_ID and tt8.dcm_id = tt2.SCM_ID)
            JOIN COMMISSION_TYPE_CATEGORY tt9 ON (tt5.COMMISSION_TYPE_CATEGORY_ID = tt9.COMMISSION_TYPE_CATEGORY_ID)
            JOIN COMMISSION_TYPE tt10 ON (tt9.COMMISSION_TYPE_ID = tt10.COMMISSION_TYPE_ID)                      
            
        WHERE  tt9.COMMISSION_TYPE_CATEGORY_NAME='Line Commission' AND tt1.MEMO_ID = ?      AND lower(tt2.FLAG) = 'included' and tt7.COMMISSION_FACTOR_VALUE != 0         
         
         ) xt1