
select
CAM_PAYMENT_SCM_STATUS.STK_NUMBER AS "Mobile Number" ,

' '
as "External txn Number" ,' ' as "External txn Date", decode ( substr(dcm_code,0,1) , 'I' ,substr(dcm_code, 2) , dcm_code) dcm_code ,

to_char(
sum (cam_payment.SCM_COMMISSION_VALUE - cam_payment.deductions_Value)) Quantity,' ' Remarks

from
cam_memo ,cam_payment ,gen_dcm , CAM_PAYMENT_SCM_STATUS

where

cam_memo.memo_id = ?

and

cam_memo.memo_id = cam_payment.MEMO_ID

and

flag
= 'INCLUDED'

and

cam_payment.scm_id = gen_dcm.dcm_id

and

gen_dcm.dcm_id = CAM_PAYMENT_SCM_STATUS.SCM_ID

group
by
CAM_PAYMENT_SCM_STATUS.STK_NUMBER ,

decode
( substr(dcm_code,0,1) , 'I' , substr(dcm_code, 2) , dcm_code)

having

sum
(cam_payment.SCM_COMMISSION_VALUE - cam_payment.deductions_Value) > 0