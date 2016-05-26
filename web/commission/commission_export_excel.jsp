<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>

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
%>
<%
//response.setContentType("Application/vnd.excel");

%>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector paymentCommissionFactors = (Vector) objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR);
response.addHeader("Content-Disposition", "attachment; filename="+((FactorModel)((Vector)paymentCommissionFactors.get(0)).get(0)).getCommissionName()+".xls");

%>

<html>
<head>
   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>

</head>
<body>

<Table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<tr>
<td><center><H2><%=((FactorModel)((Vector)paymentCommissionFactors.get(0)).get(0)).getCommissionName()%></H2></center></td>
</tr>
<tr>


<%
out.println("<td class=TableHeader>DCM Code</td>");
out.println("<td class=TableHeader>DCM Name</td>");
out.println("<td class=TableHeader>Commission Name</td>");
out.println("<td class=TableHeader>Commission Type</td>");
out.println("<td class=TableHeader>Commission Category</td>");
out.println("<td class=TableHeader>ITEM_NAME</td>");
out.println("<td class=TableHeader>ITEM_COUNT</td>");
out.println("<td class=TableHeader>FACTOR_VALUE</td>");
out.println("<td class=TableHeader>COMMISSION_VALUE</td>");

%>
</tr>
<%
double totalPayment = 0;
double subTotal = 0;
int totalEligibleItems = 0;
int subTotalEligibleItems = 0;
int totalNotEligibleItems = 0;
int subTotalNotEligibleItems = 0;
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
  double comissionFactorValue = factorModel.getCommissionFactorValue();
  float fComissionValue = (float)(factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue());
  if(factorModel.getCommissionFactorValue()<0)
  {
    font = "#ff0000";  
    comissionFactorValue = comissionFactorValue*-1;
    fComissionValue = fComissionValue*-1;
  }
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">["+factorModel.getCommissionDCMCode()+"]</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionDCMName()+"</font></td>");  
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionName()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionType()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionCategory()+"</font></td>");  
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionItemName()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+factorModel.getCommissionItemAmount()+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+comissionFactorValue+"</font></td>");
  out.println("<td class=\""+InterfaceKey.STYLE[j%2]+"\"><font color=\""+font+"\">"+fComissionValue+"</font></td>");
  totalPayment += fComissionValue;
  if(factorModel.getCommissionFactorValue()!=0)
    totalEligibleItems += factorModel.getCommissionItemAmount();
    else
    totalNotEligibleItems += factorModel.getCommissionItemAmount();
  
  out.println("</tr>");
  if(j==0)
  {
    if(factors.size()>1)
    oldDCM = ((FactorModel)factors.get(j+1)).getCommissionDCMCode();
    subTotal=totalPayment;
  if(factorModel.getCommissionFactorValue()!=0)    
    subTotalEligibleItems = totalEligibleItems;
  else    
      subTotalNotEligibleItems = totalNotEligibleItems;
    if(!oldDCM.equals(factorModel.getCommissionDCMCode())){
      out.println("<tr><td class=TableHeader colspan=8> ["+factorModel.getCommissionDCMCode()+"] Total</td><td>"+subTotal+"</td></tr>");      
      subTotal = 0;
      subTotalEligibleItems=0;
      }
  }
  else
  {
    if(j+1 < factors.size() && oldDCM.equals(((FactorModel)factors.get(j+1)).getCommissionDCMCode()))
    {
      subTotal +=fComissionValue;
      if(factorModel.getCommissionFactorValue()!=0)
        subTotalEligibleItems +=  factorModel.getCommissionItemAmount();
      else
      subTotalNotEligibleItems +=  factorModel.getCommissionItemAmount();
      
    }
    else if(j+1 < factors.size() && !oldDCM.equals(((FactorModel)factors.get(j+1)).getCommissionDCMCode()))
    {
      subTotal += fComissionValue;
      if(factorModel.getCommissionFactorValue()!=0)
        subTotalEligibleItems +=  factorModel.getCommissionItemAmount();
      else
      subTotalNotEligibleItems +=  factorModel.getCommissionItemAmount();    
      out.println("<tr><td class=TableHeader colspan=2> ["+oldDCM+"] Total Eligible Items</td><td>"+subTotalEligibleItems+"</td>"+
                  "<td class=TableHeader colspan=2> ["+oldDCM+"] Total Non Eligible Items</td><td>"+subTotalNotEligibleItems+"</td>"+
                  "<td class=TableHeader colspan=2>"+
                  //oldDCM+" Total Payment"+
                  "</td><td>"+
                  //subTotal+
                  "</td></tr>");
  
      subTotal = 0; 
      subTotalEligibleItems = 0;
      subTotalNotEligibleItems =0;
      oldDCM = ((FactorModel)factors.get(j+1)).getCommissionDCMCode();
    }
    else if(j == factors.size()-1)
    {
      subTotal += fComissionValue;
      if(factorModel.getCommissionFactorValue()!=0)
        subTotalEligibleItems +=  factorModel.getCommissionItemAmount();
      else
      subTotalNotEligibleItems +=  factorModel.getCommissionItemAmount();    
      
      out.println("<tr><td class=TableHeader colspan=2> ["+oldDCM+"] Total Eligible Items</td><td>"+subTotalEligibleItems+"</td>"+
                  "<td class=TableHeader colspan=2> ["+oldDCM+"] Total Non Eligible Items</td><td>"+subTotalNotEligibleItems+"</td>"+
                  "<td class=TableHeader colspan=2>"+
                  //oldDCM+" Total Payment"+
                  "</td><td>"+
                  //subTotal+
                  "</td></tr>");

    }
  }
  

}

}
out.println("<tr>");
out.println("<td class =TableHeader colspan=2>Total Eligible</td>");
out.println("<td colspan=1 >"+totalEligibleItems+"</td>");
out.println("<td class =TableHeader colspan=2>Total Non Eligible</td>");
out.println("<td colspan=1 >"+totalNotEligibleItems+"</td>");
//out.println("<td class =TableHeader colspan=2>Total Payment</td>");
//out.println("<td colspan=1 >"+totalPayment+"</td>");
out.println("<td class =TableHeader colspan=2></td>");
out.println("<td colspan=1 ></td>");
out.println("</tr>");


%>

</table>
</body>
</html>
