<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"

         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<html>
 <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
  <body>
<script>
 function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }
 function setSearchValues(invoiceNumber,dcmId,paymentDate)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER%>").value = invoiceNumber;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID%>").value = dcmId;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE%>").value = paymentDate;
        }
        
</script>
  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector vecInvoice = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  String searchInvoiceNumber = "";
  String searchDcmId = "";
  String searchPaymentDate = "*";
  
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER))
  {
    searchInvoiceNumber = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID))
  {
    searchDcmId= (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE))
  {
    searchPaymentDate = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE);
  }
%>
<CENTER>
<H2> Invoices List </H2>
</CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+"\""+
                  " value=\""+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_PAYMENT_DATE+"\""+
                  " value=\""+"\">");
%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
      <td align=middle>Invoice Number</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_INVOICE_NUMBER%>' value ="<%=searchInvoiceNumber%>"></td>
        <td align=middle>DCM ID</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_DCM_ID%>' value ="<%=searchDcmId%>"></td>
         <td align=middle>Payment Date</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PAYMENT_DATE%>','<%=searchPaymentDate%>',"*");</script></td>
         </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchInvoice\" id=\"searchInvoice\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_INVOICE+"';"+
                  "SOPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','*');\">");          
        %>
        </td>
      </tr>
    <table> 

    <br><br>
     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <%
     if(vecInvoice.size()!=0)
     {
     %>
      <tr class=TableHeader>
    <td align='center'>Invoice Number</td>
     <td align='center'>DCM ID</td>
      <td align='center'>Total Amount</td>
       <td align='center'>Payment Serial Number</td>
        <td align='center'>Payment Date</td>
         <td align='center'>Edit</td>
          <td align='center'>Invoice Details</td>
      </tr>
<%
}
 for (int i=0;i<vecInvoice.size();i++)
 {
  InvoiceModel invoiceModel = (InvoiceModel)vecInvoice.get(i);
  String dcmId = invoiceModel.getDcmId();
  String totalAmount = invoiceModel.getTotalAmount();
  String invoiceNumber = invoiceModel.getInvoiceNumber();
  String paymentSerialNumber = invoiceModel.getPaymentSerialNumber();
  Date paymentDate = invoiceModel.getPaymentDate();
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
<td><%=invoiceNumber%></td>
<td><%=dcmId%></td>
<td><%=totalAmount%></td>
<td><%=paymentSerialNumber%></td>
<td><%=paymentDate%></td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTIOM_EDIT_INVOICE+"';"+
          "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+".value = '"+invoiceNumber+"';"+
          "SOPform.submit();\">");
   %>
</td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Invoice Details \" name=\"details\" id=\"details\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SHOW_INVOICE_DETAIL_DATA+"';"+
          "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+".value = '"+invoiceNumber+"';"+
          "SOPform.submit();\">");
   %>
</td>
</tr>
<%
  }
%>
</table>
 <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Create New Invoice \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_NEW_INVOICE+"';"+
                  "SOPform.submit();\">");
%>
</center>
 <%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_REQUEST)==0)
    {
      out.println("<script>setSearchValues('"+searchInvoiceNumber+"','"+searchDcmId+"','"+searchPaymentDate+"');</script>");
    }
  }
%>
</body>
</html>