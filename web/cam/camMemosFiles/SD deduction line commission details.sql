SELECT 
    xt1.DCM_NAME AS "DCM Name" , 
    Aiwa,
    Cash,
    Magic,
    Mini_Primo,
    Primo,
    primo_monthly,
    Primo24,
    Sim_Swap,
    total_lines,
    Aiwa_amount,
    Cash_amount,
    Magic_amount,
    Mini_Primo_amount,
    Primo_amount,
    primo_monthly_amount,
    Primo24_amount,
    Sim_Swap_amount,
    total_amount
    
FROM        
        (SELECT tt8.DCM_ID, tt8.DCM_NAME  , tt8.DCM_CODE  DCM_CODE, 
       sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Aiwa',commission_item_amount,0)) as Aiwa,
sum(decode(substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Cash',commission_item_amount,0)) as Cash,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Magic',commission_item_amount,0)) as Magic,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Mini Primo',commission_item_amount,0)) as Mini_Primo,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Primo',commission_item_amount,0)) as Primo,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Primo Monthly',commission_item_amount,0)) as primo_monthly,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Primo24',commission_item_amount,0)) as Primo24,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Sim Swap',commission_item_amount,0)) as Sim_Swap,
sum(commission_item_amount) as total_lines,

sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Aiwa',(commission_item_amount*tt7.COMMISSION_FACTOR_VALUE) ,0)) as Aiwa_amount,
sum(decode(substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Cash',commission_item_amount,0)) as Cash_amount,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Magic',commission_item_amount,0)) as Magic_amount,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Mini Primo',commission_item_amount,0)) as Mini_Primo_amount,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Primo',commission_item_amount,0)) as Primo_amount,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Primo Monthly',commission_item_amount,0)) as primo_monthly_amount,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Primo24',commission_item_amount,0)) as Primo24_amount,
sum(decode( substr (commission_item_name, 0, instr(commission_item_name,'_')-1) ,'Sim Swap',commission_item_amount,0)) as Sim_Swap_amount,
sum(commission_item_amount) as total_amount

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
            
        WHERE tt9.COMMISSION_TYPE_CATEGORY_NAME='DIST_Line_SD Deduction' AND tt1.MEMO_ID = ?     AND lower(tt2.FLAG) = 'included' and tt7.COMMISSION_FACTOR_VALUE != 0          
        GROUP BY 
            tt8.DCM_ID, 
            tt8.DCM_CODE
            , tt8.DCM_NAME 
         ) xt1