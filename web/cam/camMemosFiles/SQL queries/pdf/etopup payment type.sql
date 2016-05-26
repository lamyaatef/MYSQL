SELECT 
    xt1.STK_NUMBER AS "Mobile Number",
    xt1.External_txn_Number AS "External txn Number",
    xt1.External_txn_Number AS "External txn Date",
    xt1.DCM_CODE,   
    xt1.xcommission_value as "Quantity",
    xt1.External_txn_Number AS "Remarks"
   
FROM        
        (SELECT tt8.DCM_ID, tt8.STK_NUMBER  , tt8.DCM_CODE  DCM_CODE ,sum(tt6.COMMISSION_ITEM_AMOUNT * tt7.COMMISSION_FACTOR_VALUE) xcommission_value, (select 'x' from dual)  External_txn_Number
        
       
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
            
        WHERE  tt1.MEMO_ID = 163        
        GROUP BY  tt8.DCM_CODE, tt8.STK_NUMBER
         ) xt1 
  
