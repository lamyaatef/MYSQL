<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
         import="com.mobinil.sds.core.system.sop.requests.dao.*"
         import="com.mobinil.sds.core.system.sop.requests.model.*"
%>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
</head>
<body>
<script>
 function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
 function checkBeforeSubmit()
    {
      var invoiceDate = document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PAYMENT_DATE%>.value
      
     if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_DCM_ID%>.value") == "")
      {
        alert("DCM ID must not be empty.");
        return;
      }
     else if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_DCM_ID%>.value")))
      {
            alert("DCM ID must be a number.");
            return; 
      }
      else if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_TOTAL_AMOUNT%>.value") == "")
      {
        alert("Total Amount must not be empty.");
        return;
      }
      else if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_TOTAL_AMOUNT%>.value")))
      {
            alert("Total Amount must be a number.");
            return; 
      }
      else if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PAYMENT_SERIAL_NUMBER%>.value") == "")
      {
        alert("Payment Serial Number must not be empty.");
        return;
      }
      else if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PAYMENT_SERIAL_NUMBER%>.value")))
      {
            alert("Payment Serial Number must be a number.");
            return; 
      }
      else if(invoiceDate == "*")
      {
        alert("Payment Date must not be empty.");
        return;
      }
      else
      {
        var currentDate = new Date();
        var currentYear = parseFloat(currentDate.getYear());
        var currentMonth = parseFloat(currentDate.getMonth()+1);
        var currentDay = parseFloat(currentDate.getDate());
        var invoiceDatefirstIndex = invoiceDate.indexOf ("-");
        var invoiceDatelastIndex = invoiceDate.lastIndexOf ("-");
        invoiceDateYear = parseFloat(invoiceDate.substring (0, invoiceDatefirstIndex));
        invoiceDateMonth = parseFloat(invoiceDate.substring (invoiceDatefirstIndex+1, invoiceDatelastIndex));
        invoiceDateDay = parseFloat(invoiceDate.substring (invoiceDatelastIndex+1, invoiceDate.length));
        if( currentYear < invoiceDateYear)
        {
            SOPform.submit();
        }
        else
        {
          if(currentMonth < invoiceDateMonth && currentYear <= invoiceDateYear)
          {
            SOPform.submit();
          }
          else
          {
             if(currentDay < invoiceDateDay && currentMonth <= invoiceDateMonth && currentYear <= invoiceDateYear)
             {
                SOPform.submit();
             }
             else
             {
                alert("Invoice Date must be greater than current date");
                return;
             }
          }
        }
      }
    SOPform.submit();
      }
</script>

<script>
    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'yyyy-mm-dd','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
</script>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   InvoiceModel invoiceModel = (InvoiceModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
%> 
 <CENTER>
      <H2> Invoice  </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  String invoiceNumber="";
  String dcmId="";
  String totalAmount="";
  String paymentSerialNumber="";
  String strPaymentDate = "*";
  String nextAction = SOPInterfaceKey.ACTION_SAVE_NEW_INVOICE;
  if(invoiceModel != null)
  {
    nextAction = SOPInterfaceKey.ACTION_UPDATE_INVOICE_DATA;
    invoiceNumber = invoiceModel.getInvoiceNumber();
    dcmId = invoiceModel.getDcmId();
    totalAmount = invoiceModel.getTotalAmount();
    paymentSerialNumber = invoiceModel.getPaymentSerialNumber();
    Date paymentDate = invoiceModel.getPaymentDate();
    strPaymentDate = (paymentDate.getYear()+1900)+"-"+(paymentDate.getMonth()+1)+"-"+paymentDate.getDate();
  }
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+"\""+
      " value=\""+invoiceNumber+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>  
<tr>
<td class=TableHeader nowrap>DCM ID</td>
<td class=TableTextNote><input type="text" name="dcm_id" value="<%=dcmId%>"></td>
</tr>
<tr>
<td class=TableHeader nowrap>Total Amount</td>
<td class=TableTextNote><input type="text" name="total_amount" value="<%=totalAmount%>"></td>
</tr>
<tr>
<td class=TableHeader nowrap>Payment Serial Number</td>
<td class=TableTextNote><input type="text" name="payment_serial_number" value="<%=paymentSerialNumber%>"></td>
</tr>
<tr>
<td class=TableHeader nowrap>Payment Date</td>
<td class=TableTextNote><Script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_PAYMENT_DATE%>','<%=strPaymentDate%>',"*");</script></td>
</tr>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                  "checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>
</form>
</body>
</html>
