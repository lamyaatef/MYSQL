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
         import="com.mobinil.sds.core.system.commission.factor.model.*"
         import="com.mobinil.sds.core.system.payment.model.*"
%>

<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector paymentCommissionFactors = (Vector) objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR);
  PaymentModel paymentModel = (PaymentModel) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

%>

<html>
<head>
   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
<link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
</head>
<body>
<center><H2><%=paymentModel.getPaymentName()%></H2></center>
<Table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>

<tr>
<%
out.println("<td class=TableHeader>DCM Code</td>");
out.println("<td class=TableHeader>DCM Name</td>");
out.println("<td class=TableHeader>Commission Name</td>");
out.println("<td class=TableHeader>Commission Type</td>");
out.println("<td class=TableHeader>Commission Category</td>");
out.println("<td class=TableHeader>ITEM_NAME</td>");
out.println("<td class=TableHeader>ITEM_AMOUNT</td>");
out.println("<td class=TableHeader>COMMISSION_VALUE</td>");

%>
</tr>
<%
double totalPayment = 0;
double subTotal = 0;
String oldDCM="";
String font = "";
for(int i = 0 ; i < paymentCommissionFactors.size() ; i++)
{

  Vector factors = (Vector)paymentCommissionFactors.get(i);

  for(int j = 0 ; j < factors.size();j++)
  {
  font = "#000000";
  out.println("<tr>");
  FactorModel factorModel  = (FactorModel)factors.get(j);
  if(factorModel.getCommissionFactorValue()<0)font = "#ff0000";  
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">["+factorModel.getCommissionDCMCode()+"]</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionDCMName()+"</font></td>");  
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionName()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionType()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionCategory()+"</font></td>");  
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionItemName()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionItemAmount()+"</font></td>");

  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+(factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue())+"</font></td>");
  totalPayment += (factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue());
  
  out.println("</tr>");
  if(j==0)
  {
    oldDCM = factorModel.getCommissionDCMCode();
    subTotal=totalPayment;
  }
  else
  {
    if(j+1 < factors.size() && oldDCM.equals(((FactorModel)factors.get(j+1)).getCommissionDCMCode()))
    {
      subTotal +=(factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue());

    }
    else if(j+1 < factors.size())
    {
      subTotal += (factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue());
      out.println("<tr><td class=TableHeader colspan=7> ["+oldDCM+"] Total</td><td>"+subTotal+"</td></tr>");
      subTotal = 0; 
      oldDCM = ((FactorModel)factors.get(j+1)).getCommissionDCMCode();
    }
    else if(j == factors.size()-1)
    {
      subTotal += (factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue());
      out.println("<tr><td class=TableHeader colspan=7> ["+oldDCM+"] Total</td><td>"+subTotal+"</td></tr>");
      
    }
  }
  

}

}
out.println("<tr>");
out.println("<td class =TableHeader colspan=7>Total Payment</td>");
Utility.logger.debug(totalPayment+"");
out.println("<td colspan=1 >"+totalPayment+"</td>");
out.println("</tr>");
%>


</table>
<br><br>
        <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
        </center>
</body>
</html>
