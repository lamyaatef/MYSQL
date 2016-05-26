<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"

         
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
<title>Insert title here</title>
</head>
<body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector accrualDataVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  
   <CENTER>
      <H2> Accrual Data </H2>
    </CENTER>
<form name='ACCform' id='ACCform' action='' method='post'>

<%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_ACCRUAL_VALUE+"\""+
          " value=\""+"\">"); 
  
%>
<%
if(accrualDataVec.size()!=0)
{%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
 <tr class=TableHeader>
          
          <td align='center'>
          Accrual Channel
          </td>
          <td align='center'>
          Accrual Activation Date From
          </td>
          <td align='center'>
          Accrual Activation Date To
          </td>
          <td align='center'>
          View Details
          </td>
          </tr>
          
 <%} 
	for (int i=0;i<accrualDataVec.size();i++)
	{
		AccrualDataModel accrualDataModel = (AccrualDataModel)accrualDataVec.get(i);
		String accrualDataId = accrualDataModel.getAccAccrualValueId();
		String channelId = accrualDataModel.getChannelId();
		String channelName = accrualDataModel.getChannelName();
		String activationDateFrom = accrualDataModel.getActivationDateFrom();
		String activationDateTo = accrualDataModel.getActivationDateTo();

%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
<td align='center'><%=channelName%></td>
<td align='center'><%=activationDateFrom%></td>
<td align='center'><%=activationDateTo%></td>
<td align='center'>

 <%
 out.print("<INPUT class=button type='button' value=\" Accrual Details \" name=\"details\" id=\"details\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_VIEW_ACCRUAL_DETAIL_DATA+"';"+
         "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_ACCRUAL_VALUE+".value = '"+accrualDataId+"';"+
         "ACCform.submit();\">");
   %>
   
   
</td>
</tr>
<%} %>
</TABLE>
</form>
</body>
</html>