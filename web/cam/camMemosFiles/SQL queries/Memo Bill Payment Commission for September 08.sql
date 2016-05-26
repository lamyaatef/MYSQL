SELECT 
     xt1.DCM_CODE  AS "POSName" ,
    xt1.DCM_NAME AS "Mobile Count",   
     
    xt1.xCommission_Total as "Commission"
    
       
FROM
            
        (SELECT tt8.DCM_ID, tt8.DCM_NAME  , tt8.DCM_CODE  DCM_CODE, 
        SUM(tt6.COMMISSION_ITEM_AMOUNT * tt7.COMMISSION_FACTOR_VALUE)  xCommission_Total, 
        sum (tt6.COMMISSION_ITEM_AMOUNT ) total_item_amount
        FROM 
        
            CAM_MEMO tt1 
            JOIN CAM_PAYMENT tt2 ON (tt2.MEMO_ID = tt1.MEMO_ID)
            JOIN PAYMENT_DETAIL tt3 ON (tt2.PAYMENT_ID = tt3.PAYMENT_DETAIL_ID)
            JOIN PAYMENT_COMMISSION tt4 ON (tt4.PAYMENT_DETAIL_ID = tt3.PAYMENT_DETAIL_ID ) 
            JOIN COMMISSION_DETAIL tt5 ON (tt5.COMMISSION_DETAIL_ID = tt4.COMMISSION_DETAIL_ID)
            JOIN COMMISSION_ITEM tt6 ON (tt6.COMMISSION_DETAIL_ID = tt5.COMMISSION_DETAIL_ID )
            JOIN COMMISSION_FACTOR tt7 ON (tt6.COMMISSION_FACTOR_ID = tt7.COMMISSION_FACTOR_ID )
             JOIN GEN_DCM tt8 ON (tt6.DCM_ID = tt8.DCM_ID AND TT2.SCM_ID = TT8.DCM_ID)
            JOIN COMMISSION_TYPE_CATEGORY tt9 ON (tt5.COMMISSION_TYPE_CATEGORY_ID = tt9.COMMISSION_TYPE_CATEGORY_ID)
            JOIN COMMISSION_TYPE tt10 ON (tt9.COMMISSION_TYPE_ID = tt10.COMMISSION_TYPE_ID)            
        WHERE tt10.COMMISSION_TYPE_ID = 1 AND tt1.MEMO_ID = 151    AND lower(tt2.FLAG) = 'included'        
        GROUP BY 
            tt8.DCM_ID, 
            tt8.DCM_CODE
            , tt8.DCM_NAME 
         ) xt1 
    