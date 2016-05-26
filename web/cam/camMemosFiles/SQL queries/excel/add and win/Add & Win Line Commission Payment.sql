SELECT 
    xt1.DCM_NAME AS "DCM Name",
	xt1.DCM_code AS "DCM Code",
    xt1.total_item_amount AS "Total lines ",
    xt1.xCommission_Total as "Commission Total Amount" ,
    abs (nvl(xt4.unreal_deduction, '0')) AS "UnReal Deduction",
    nvl(xt2.xCommission_Deduction,0)  as "Deduction", 
    (xt1.xCommission_Total - abs(nvl(xt4.unreal_deduction, 0)) - nvl(xt2.xCommission_Deduction,0)) as  "Net Amount"
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
            JOIN GEN_DCM tt8 ON (tt6.DCM_ID = tt8.DCM_ID and tt8.dcm_id = tt2.SCM_ID)
            JOIN COMMISSION_TYPE_CATEGORY tt9 ON (tt5.COMMISSION_TYPE_CATEGORY_ID = tt9.COMMISSION_TYPE_CATEGORY_ID)
            JOIN COMMISSION_TYPE tt10 ON (tt9.COMMISSION_TYPE_ID = tt10.COMMISSION_TYPE_ID)                      
            
        WHERE tt10.COMMISSION_TYPE_ID = 1 AND tt1.MEMO_ID = ?       AND lower(tt2.FLAG) = 'included'          
        GROUP BY 
            tt8.DCM_ID, 
            tt8.DCM_CODE
            , tt8.DCM_NAME 
         ) xt1 
    LEFT JOIN
  
    (SELECT  tt6.DCM_ID, SUM(tt6.COMMISSION_ITEM_AMOUNT * tt7.COMMISSION_FACTOR_VALUE) AS unreal_deduction
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
        WHERE tt9.COMMISSION_TYPE_CATEGORY_NAME='Authentication Penalty' AND tt1.MEMO_ID = ?           
        GROUP BY
         
            tt6.DCM_ID
         ) xt4 on xt1.DCM_ID = xt4.DCM_ID
              
    LEFT JOIN 
    (SELECT DCM_ID, SUM(DEDUCTION_VALUE )  xCommission_Deduction
        FROM CAM_DEDUCTION tt7
        group by tt7.DCM_ID
    )  xt2 on xt1.DCM_ID = xt2.DCM_ID
    order by dcm_name

	
