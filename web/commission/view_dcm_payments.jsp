<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.commission.*"  
         import="com.mobinil.sds.web.interfaces.payment.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import="com.mobinil.sds.core.system.commission.factor.model.*"
         
%>
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>  
      <SCRIPT language=JavaScript src="../resources/js/tree.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
function checkBeforeSubmit()
    {
     if(eval("document.COMform.<%=CommissionInterfaceKey.INPUT_TEXT_DCM_CODE%>.value") == "")
      {
        alert("Please Enter DCM Code.");
        return;
      }
       COMform.submit();
      }
 function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(COMform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }

function setSearchValues(searchDcmCode,searchPaymentStatus,searchPaymentStartDateFrom,searchPaymentStartDateTo,searchPaymentEndDateFrom,searchPaymentEndDateTo)
{
  document.COMform.<%=CommissionInterfaceKey.INPUT_TEXT_DCM_CODE%>.value=searchDcmCode;
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS%>.value=searchPaymentStatus;
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM%>.value=searchPaymentStartDateFrom;  
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO%>.value=searchPaymentStartDateTo;
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM%>.value=searchPaymentEndDateFrom;
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO%>.value=searchPaymentEndDateTo;  
}
</script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  HashMap commissionDetails = (HashMap)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
  Vector paymentStatus = (Vector)objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_STATUS);

  String appName = request.getContextPath();

  Vector searchResults = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT);
  String strSearchDcmCode = "";
  String strSearchPaymentStatus = "";
  String strSearchPaymentStartDateFrom = "*";
  String strSearchPaymentStartDateTo = "*";
  String strSearchPaymentEndDateFrom = "*";
  String strSearchPaymentEndDateTo = "*";

  if(objDataHashMap.containsKey(CommissionInterfaceKey.INPUT_TEXT_DCM_CODE))
  strSearchDcmCode = (String)objDataHashMap.get(CommissionInterfaceKey.INPUT_TEXT_DCM_CODE);
  if(objDataHashMap.containsKey(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS))
  strSearchPaymentStatus = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
  if(objDataHashMap.containsKey(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM))
  strSearchPaymentStartDateFrom = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
  if(objDataHashMap.containsKey(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO))
  strSearchPaymentStartDateTo = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
  if(objDataHashMap.containsKey(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM))
  strSearchPaymentEndDateFrom = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);
  if(objDataHashMap.containsKey(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO))
  strSearchPaymentEndDateTo = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);
  
%>
    <CENTER>
      <H2> DCM Payments </H2>
    </CENTER>
<form name="COMform" action="" method="post">
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_DCM_CODE+"\""+
                  " value=\""+"\">");

   out.println("<input type=\"hidden\" name=\""+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+"\""+
                  " value=\""+"\">");             

%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=8>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>DCM Code</td>
        <td align=middle><input type='text' name='<%=CommissionInterfaceKey.INPUT_TEXT_DCM_CODE%>' id='<%=CommissionInterfaceKey.INPUT_TEXT_DCM_CODE%>'></td>
        <td align=middle>Payment Status</td>
        <td align=middle>
        <select name="<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS%>">
    <option value=""></option>
    <%
      for(int i = 0 ; i < paymentStatus.size() ; i++)
      {
        GenericModel paymentStatusModel = (GenericModel)paymentStatus.get(i);
        String strPaymentStatusId = paymentStatusModel.get_field_1_value();
        String strPaymentStatusName = paymentStatusModel.get_field_2_value();
        //Utility.logger.debug("The iddddddddddddddddd issss " + strPaymentStatusId);
        if(Integer.parseInt(strPaymentStatusId) >= 2 && Integer.parseInt(strPaymentStatusId) < 4)
              %>
              <option value="<%=strPaymentStatusId%>"><%=strPaymentStatusName%></option>
              <%
      }
    %>
    </select>
    </td>
    </tr>
    <TR class=TableTextNote>
    <td align=middle>Payment Start Date From</td>
    <td align=middle><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM%>',"*" );</script></td>
    <td align=middle>Payment Start Date To</td>
    <td align=middle><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO%>',"*");</script></td>
    </tr>
    <TR class=TableTextNote>
    <td align=middle>Payment End Date From</td>
    <td align=middle><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM%>',"*");</script></td>
    <td align=middle>Payment End Date To</td>
    <td align=middle><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO%>',"*");</script></td>
   </tr>
   <tr>
        <td align='center' colspan=8>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchDcmPayment\" id=\"searchDcmPayment\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+CommissionInterfaceKey.ACTION_SEARCH_DCM_PAYMENTS+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','*','*','*','*');\">");          
        %>
        </td>
      </tr>
      </table>
      <br><br>
      <%
        if(searchResults.size()!=0)
        {
      %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
      <td>Name</td>
      <td>Type</td>
      <td>Start Date</td>
      <td>End Date</td>
      <td>Status</td>
      <%
        }
        double totalPayment = 0;
        for(int i = 0 ; i < searchResults.size() ; i++)
        {
          PaymentModel paymentModel = (PaymentModel)searchResults.get(i);
          int paymentId = paymentModel.getPaymentID();
          String paymentName = paymentModel.getPaymentName();
          String paymentType = paymentModel.getPaymentTypeName();
          Date paymentStartDate = paymentModel.getPaymentStartDate();
          Date paymentEndDate = paymentModel.getPaymentEndDate();
          String paymentStatusName = paymentModel.getPaymentStatusName();
          //double factorValue = paymentModel.getCommissionFactorValue();
          //Utility.logger.debug("Factor Value is " + factorValue);
          //int commissionItemAmount = paymentModel.getCommissionItemAmount();
          //Utility.logger.debug("The commission amount is " + commissionItemAmount);
          Vector paymentCommissionVec = (Vector)commissionDetails.get(paymentId+"");
          
      %>
     <TR class=<%=InterfaceKey.STYLE[i%2]%>>
     <%
          //out.println("<TD><a href=\"#\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+PaymentInterfaceKey.ACTION_VIEW_PAYMENT+"';"+
                      //"document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                      //"COMform.submit();\"><IMG height=16 hspace=0 src=\""+appName+"/resources/img/plus.gif\" width=16 border=0 ></a>"+paymentName+"</TD>");
       out.println("<td>");
       out.println("<a id=x"+paymentModel.getPaymentID()+" href=\"javascript:Toggle('"+paymentModel.getPaymentID()+"');\">");
       out.print("<IMG height=16 hspace=0 src=\""+appName+"/resources/img/plus.gif\" width=16 border=0 >");
       out.print("</a>");
       out.print(paymentModel.getPaymentName()+"</TD>");
          %>
          <TD><%=paymentType%></TD>
          <TD><%=paymentStartDate%></TD>
          <TD><%=paymentEndDate%></TD>
          <TD><%=paymentStatusName%></TD>
      </tr>
      <%
        out.println("<TR class=TableHeader>");
        out.println("<TD class=TableTextNote colspan=5>");
        out.println("<div style=\"DISPLAY: none\" id="+paymentModel.getPaymentID()+" name="+paymentModel.getPaymentName()+">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");
        out.println("<td  class=TableHeader>DCM Name</td>");
        out.println("<td  class=TableHeader>Commission Name</td>");
        out.println("<td  class=TableHeader>Commission Type</td>");
        out.println("<td  class=TableHeader>Commission Category</td>");
        out.println("<td  class=TableHeader>ITEM_NAME</td>");
        out.println("<td  class=TableHeader>ITEM_AMOUNT</td>");
        out.println("<td  class=TableHeader>COMMISSION_VALUE</td>");
        out.println("</tr>");
        double total = 0;
        double totals = 0;
        double commission = 0;
        int itemAmount = 0;
        String commissionName="";
        String newCommissionName ="";
        for(int j = 0 ; j < paymentCommissionVec.size() ; j++) 
        {
        
          FactorModel factorModel = (FactorModel)paymentCommissionVec.get(j);
          if(j == 0)      
              newCommissionName = factorModel.getCommissionName();          

          if(!newCommissionName.equals(factorModel.getCommissionName())){
              Utility.logger.debug(newCommissionName+" commission :  "+paymentName+" : " +commission);
              total+=commission;
              Utility.logger.debug(newCommissionName+" total:  "+paymentName+" : "+total);
                out.println("<tr><td  class=TableHeader colspan=6>Total</td><td>"+total+"</td></tr>");
              newCommissionName = factorModel.getCommissionName();
              total =0;
              //total = factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue();
              Utility.logger.debug(newCommissionName+" new total:  "+paymentName+" : "+total);
              }
              else{
              total+= commission;
              }
            
          out.println("<TR>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+factorModel.getCommissionDCMName()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+factorModel.getCommissionName()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+factorModel.getCommissionType()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+factorModel.getCommissionCategory()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+factorModel.getCommissionItemName()+"</TD>");
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+factorModel.getCommissionItemAmount()+"</TD>");
          commission = (factorModel.getCommissionItemAmount()*factorModel.getCommissionFactorValue());
          totalPayment+=commission;
          out.println("<TD class="+InterfaceKey.STYLE[j%2]+">"+commission+"</TD>");
          
          if(j == paymentCommissionVec.size()-1){
                total+=commission;
                itemAmount+=factorModel.getCommissionItemAmount();
                out.println("<tr><td  class=TableHeader colspan=6>Total</td><td>"+total+"</td></tr>");
                
                total = 0;
              }

              
              

        }
        out.println("</TABLE></td></tr>");
        
        out.println("</div>");
      }
      Utility.logger.debug("Total payment is " + totalPayment);
      %>
      </tr>
      <% if (totalPayment!=0)
      {
      %>
      <tr>
      <td class =TableHeader colspan=4>Total Payment</td>
      <td><%=totalPayment%></td>
      <%
      }
      %>
      </table>
        <%
       out.println("<script>setSearchValues('"+strSearchDcmCode+"','"+strSearchPaymentStatus+"','"+strSearchPaymentStartDateFrom+"','"+strSearchPaymentStartDateTo+"','"+strSearchPaymentEndDateFrom+"','"+strSearchPaymentEndDateTo+"')</script>");
       %>
  </form>
  </body>
</html>
