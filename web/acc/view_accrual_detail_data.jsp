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
  Vector accrualDetialDataVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  
   <CENTER>
      <H2> Accrual Detial Data </H2>
    </CENTER>
<form name='ACCform' id='ACCform' action='' method='post'>

<%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  
%>
<%
if(accrualDetialDataVec.size()!=0)
{%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
 <tr class=TableHeader>
          
          <td align='center'>
          DCM Name
          </td>
          <td align='center'>
          Product Name
          </td>
          <td align='center'>
          Line Count
          </td>
          <td align='center'>
          DCM Value
          </td>
          <td align='center'>
          Product Value
          </td>
          <td align='center'>
          Accrual Value
          </td>
          </tr>
          
 <%} 
	for (int i=0;i<accrualDetialDataVec.size();i++)
	{
		AccrualDetailDataModel accrualDetialDataModel = (AccrualDetailDataModel)accrualDetialDataVec.get(i);
		String accAccrualValueDetialId = accrualDetialDataModel.getAccAccrualValueDetialId();
		String dcmId = accrualDetialDataModel.getDcmId();
		String productId = accrualDetialDataModel.getProductId();
		String lineCount = accrualDetialDataModel.getLineCount();
		String dcmValue = accrualDetialDataModel.getDcmValue();
		String productValue = accrualDetialDataModel.getProductValue();
		String accrualValue = accrualDetialDataModel.getAccrualValue();
		String accAccrualValueId = accrualDetialDataModel.getAccAccrualValueId();
		String dcmName = accrualDetialDataModel.getDcmName();
		String productName = accrualDetialDataModel.getProductName();

%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
<td align='center'><%=dcmName%></td>
<td align='center'><%=productName%></td>
<td align='center'><%=lineCount%></td>
<td align='center'><%=dcmValue%></td>
<td align='center'><%=productValue%></td>
<td align='center'><%=accrualValue%></td>

</tr>
<%} %>
</TABLE>
<br><br>
<center>
<%
	out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
	%>
</center>
</form>
</body>
</html>