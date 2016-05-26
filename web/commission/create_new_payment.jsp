<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"         
         import="com.mobinil.sds.web.interfaces.payment.*"  
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="../resources/css/Template1.css" rel="stylesheet" type="text/css" />
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
</head>
<script language="javascript">
function checkForSubmit()
{
  var paymentStartDate = document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE%>.value
  var paymentEndDate = document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE%>.value

  if(eval("document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME%>.value") == "")
      {
        alert("Payment Name must not be empty.");
        return;
      }

  else if (paymentStartDate == "*")
      {
        alert("Payment Start must not be empty.");
        return;
      }

  else if (paymentEndDate == "*")
      {
        alert("Payment End must not be empty.");
        return;
      }

  else
    {
      var paymentStartDatefirstIndex = paymentStartDate.indexOf ("/");
      var PaymentStartDatelastIndex = paymentStartDate.lastIndexOf ("/");
      paymentStartDateYear = parseFloat(paymentStartDate.substring (0, paymentStartDatefirstIndex));
      paymentStartDateMonth = parseFloat(paymentStartDate.substring (paymentStartDatefirstIndex+1, PaymentStartDatelastIndex));
      PaymentStartDateDay = parseFloat(paymentStartDate.substring (PaymentStartDatelastIndex+1, paymentStartDate.length));

      var paymentEndDatefirstIndex = paymentEndDate.indexOf ("/");
      var PaymentEndDatelastIndex = paymentEndDate.lastIndexOf ("/");
      paymentEndDateYear = parseFloat(paymentEndDate.substring (0, paymentEndDatefirstIndex));
      paymentEndDateMonth = parseFloat(paymentEndDate.substring (paymentEndDatefirstIndex+1, PaymentEndDatelastIndex));
      PaymentEndDateDay = parseFloat(paymentEndDate.substring (PaymentEndDatelastIndex+1, paymentEndDate.length));
      if( paymentStartDateYear < paymentEndDateYear)
        {
           COMform.submit();
        }
        else
        {
          if(paymentStartDateMonth < paymentEndDateMonth && paymentStartDateYear <= paymentEndDateYear)
          {
            COMform.submit();
          }
          else
          {
             if(PaymentStartDateDay < PaymentEndDateDay && currentMonth <= paymentEndDateMonth && paymentStartDateYear <= paymentEndDateYear)
             {
                COMform.submit();
             }
             else
             {
                alert("Payment Start Date must be greater than Payment End date");
                return;
             }
          }
        } 
    }
}
</script>
<script>
function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(COMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
</script>

<body>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector paymentTypes = (Vector)objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE);


%>

<center><H2>Payment Details</H2></center>
<form name='COMform' action='' method='post'>
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
                                    
%>
<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
  <tr>
    <td width="20%" height="27" class="TableTextNote">Payment name </td>
    <td width="80%" class="TableTextNote"><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME%>" /></td>
  </tr>
  <tr>
    <td height="22" class="TableTextNote">Payment Type </td>
    <td class="TableTextNote"><select name="<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE%>">
    <%
    for(int i = 0; i < paymentTypes.size() ; i++)
    {
      GenericModel paymentTypeModel = (GenericModel)paymentTypes.get(i);
     out.println("<option value='"+paymentTypeModel.get_primary_key_value()+"'> "+paymentTypeModel.get_field_2_value()+"</option>");
    }
    %>
    </select>
    </td>
  </tr>
  <tr>
    <td height="27" class="TableTextNote">Payment Start Date </td>
    <td class="TableTextNote"><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE%>' , "*");</script></td>
  </tr>
  <tr>
    <td height="24" class="TableTextNote">Payment End Date </td>
    <td class="TableTextNote"><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE%>' , "*");</script></td>
  </tr>
  <tr>
    <td colspan="2" class="TableTextNote"><div align="center">
      <%
      out.println("<input name=\"Submit\" type=\"button\" class=\"button\" value=\"Save\" onclick=\"document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+
                  ".value='"+PaymentInterfaceKey.ACTION_SAVE_NEW_PAYMENT+"'; checkForSubmit();"+
                  "\" />");
      %>
    </div></td>
  </tr>
</table>
</form>
</body>
</html>
