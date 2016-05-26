<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.web.interfaces.cam.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.cam.accrual.model.*"
         import="com.mobinil.sds.core.system.payment.dto.*"


%>
         


 <%
 String appName = request.getContextPath();
 HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

 String strTypeId = (String)objDataHashMap.get (com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID);

  boolean paymentTypePrivilage = false;
String pageTitle = "New Payment Type";

if(strTypeId!=null)
    {   
      paymentTypePrivilage = true; 
      pageTitle = "Edit Payment Type";
    }
 

 %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <SCRIPT language=JavaScript>
function loadValues(control,value)
  {
    document.formNewEditPaymentType.control.value=value;
  }
    
  function checkbeforSubmit()
  {
	  
    if(NonBlank(document.formNewEditPaymentType.<%out.print(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_INPUT_TEXT_PAYMENT_TYPE_NAME);%>, true, 'Payment Type Name'))
    {
      if(NonBlank(document.formNewEditPaymentType.<%out.print(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS);%>, true, 'Payment Type Status'))
      {
    	  if(NonBlank(document.formNewEditPaymentType.<%out.print(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS);%>, true, 'Payment Type Status')){
    		  document.formNewEditPaymentType.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.ACTION_SAVE_PAYMENT_TYPE);%>';
    	        formNewEditPaymentType.submit();
    	  }
        	  
        
      }
    } 
    return false;
  }
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  <%out.print(pageTitle);%> </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formNewEditPaymentType" id="formNewEditPaymentType" method="post" >

<%



Vector vecStatusList = (Vector) objDataHashMap.get(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.VEC_PAYMENT_TYPE_STATUS) ;
Vector vecMethodList = (Vector) objDataHashMap.get(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.VEC_PAYMENT_METHODS) ;
Vector accruals=(Vector)objDataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL);

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Payment&nbsp;Type&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_INPUT_TEXT_PAYMENT_TYPE_NAME+"\">");


out.println("</TD></TR>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Payment&nbsp;Method *</TD>");
out.println("<TD class=TableTextNote colSpan=4><SELECT "); 
out.println("name=\""+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD+"\">");

for (int i=0; i<vecMethodList.size(); i++)
{
	PaymentTypeTempModel status = (PaymentTypeTempModel)vecMethodList.elementAt (i);
out.println("<OPTION value="+status.getPAYMENT_TYPE_METHOD_ID());
    if (!paymentTypePrivilage &&i==0){
    out.println(" selected");
    }
    
out.println(">"+ status.getPAYMENT_TYPE_METHOD_NAME()+"</OPTION>");
} 
out.println("</SELECT></TD></TR>");

out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Payment&nbsp;Type&nbsp;Status *</TD>");
out.println("<TD class=TableTextNote colSpan=4><SELECT "); 
out.println("name=\""+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS+"\">");

for (int i=0; i<vecStatusList.size(); i++)
{
PaymentTypeStatusDTO status = (PaymentTypeStatusDTO)vecStatusList.elementAt (i);
out.println("<OPTION value="+status.getStatusId().intValue());
    if (!paymentTypePrivilage &&i==0){
    out.println(" selected");
    }
out.println(">"+ status.getStatusName()+"</OPTION>");
} 
out.println("</SELECT></TD></TR>");
//samuel ads for the accrual
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Accrual&nbsp *</TD>");
out.println("<TD class=TableTextNote colSpan=4><SELECT "); 
out.println("name=\""+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ACRRUAL+"\">");

for (int i=0; i<accruals.size(); i++)
{
	AccrualModel accrual = (AccrualModel)accruals.get(i);
out.println("<OPTION value="+accrual.getAccrual_id());

out.println(">"+ accrual.getAccrual_name()+"</OPTION>");
} 
out.println("</SELECT></TD></TR>");
//end of accrual
out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR></TABLE></TABLE>");
if(paymentTypePrivilage)
{
out.println("<input type=\"hidden\" name=\""+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID+"\""+
                  " value=\""+"\">");

        PaymentTypeDTO vecPaymentTypeDetails = (PaymentTypeDTO) objDataHashMap.get(com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.DTO_PAYMENT_TYPE_DETAIL) ;

        String paymentTypeName = vecPaymentTypeDetails.getTypeName();
        Integer paymentTypeId = vecPaymentTypeDetails.getTypeId();
        Integer paymentTypeMethodId = vecPaymentTypeDetails.getMethodTypeId();
        Integer paymentTypeStatus = vecPaymentTypeDetails.getTypeStatusId();

        out.println("<script>");
        out.println("document.formNewEditPaymentType."+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_INPUT_TEXT_PAYMENT_TYPE_NAME+".value='"+paymentTypeName+"';");
        out.println("document.formNewEditPaymentType."+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_STATUS+".value='"+paymentTypeStatus+"';");
        out.println("document.formNewEditPaymentType."+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_METHOD+".value='"+paymentTypeMethodId+"';");
        out.println("document.formNewEditPaymentType."+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.PARAM_LOAD_PAYMENT_TYPE_ID+".value='"+paymentTypeId+"';");
        out.println("document.formNewEditPaymentType."+com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey.CONTROL_SELECT_PAYMENT_TYPE_ACRRUAL+".value='"+vecPaymentTypeDetails.getPaymentAccrualId()+"';");
        out.println("</script>");        

}

%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>

 &nbsp;<INPUT class=button onclick="checkbeforSubmit();" type=button value=" Save " name=save></P></td>
</tr>
</table>
</form>

  </body>
</html>
