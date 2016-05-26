<%@ page contentType="text/html;charset=windows-1252"
    import="com.mobinil.sds.web.interfaces.*"
    import ="com.mobinil.sds.web.interfaces.cr.*"
    import ="com.mobinil.sds.web.interfaces.gn.ua.*"
    import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
    import ="com.mobinil.sds.core.system.gn.dcm.model.*"
    import ="java.util.*"
%>

<%!
public void printDCM(javax.servlet.http.HttpServletRequest request, javax.servlet.jsp.JspWriter out ) throws java.io.IOException
{
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  if (dataHashMap!=null && dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
  Object dcmDtoObj = dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  DCMDto dcmDto = (DCMDto)dcmDtoObj;  
  if (dcmDto !=null)
  {
    for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
    {
      DCMModel model = dcmDto.getDcm(index);       
      out.println("<option value=\""+model.getDcmId()+"\"");
      out.print(">"+model.getDcmName()+"</option>");
    }
  }
  else
  {
  out.println("dafsd");
  }
  }
}
%>
<%    String appName = request.getContextPath();%>

<LINK REL=STYLESHEET TYPE="text/css" href="<%out.print(appName);%>/resources/css/Template1.css">

<SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>
Generate Batch
</title>
<center>
<h2>
Generate Batch Administration
</h2>
</center>
</head>
<%
String formName = ContractRegistrationInterfaceKey.GENERATE_BATCH_FORM_NAME;
%>
<body onkeypress = "if(event.keyCode==13){<%out.print(formName);%>.submit();}">
<form method="post" name="<%out.print(formName);%>" action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_ADMIN_GENERATE_BATCH);%>">
<%
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");

  String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">

<tr >
    <td class=TableHeader>
    DCM Code
    </td>
    <td>
    <select  name="<%out.print(ContractRegistrationInterfaceKey.GENERATE_BATCH_DCM_ID);%>">
    <% printDCM(request, out); %>    
    </select>
    </td>


</tr>
<tr>
    <td class=TableHeader>
    Date
    </td>
    <td>
    <%
/*
    java.util.Calendar cal = java.util.Calendar.getInstance();
    String curDate ="";  
    String day =cal.get(java.util.Calendar.DAY_OF_MONTH)+"";
    String month = (curDate+=cal.get(java.util.Calendar.MONTH)+1)+"";
    String year =cal.get(java.util.Calendar.YEAR)+"";
    if (day.length()==1)
    {day= "0"+day;}
    if (month.length()==1)
    {month="0"+month;}
    curDate=day+"/"+month+"/"+year;
    Utility.logger.debug("cur date" + curDate);
*/
    %>
      
    <input class="input"  name="<%=ContractRegistrationInterfaceKey.GENERATE_BATCH_DATE%>"  value="" Readonly>
     <A onclick="showCalendar(<%out.print(formName+"."+ ContractRegistrationInterfaceKey.GENERATE_BATCH_DATE);%>,'dd/mm/yyyy','Choose date')" href="javascript:void(0)" >
     <img border="0" src="<%out.print(appName);%>/resources/img/icon_calendar.gif" width="20" height="16">
     </a>
    </td>
</tr>
</table>
<br>
<center>
<input class="button" type="button" name="Generate Batch" value="Generate Batch" onclick="<%out.print(formName);%>.submit();">
</center>
</form>

</body>
<%
  Vector report = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  if (report!=null)
  {
    String reportS ="";
    for (int i  = 0 ; i < report.size();i++)
    {
      reportS+=  (String)report.elementAt(i) +"\\n" ;
    }
    out.println("<Script> alert(\""+reportS+"\"); </Script>");
  }
%>
</html>
