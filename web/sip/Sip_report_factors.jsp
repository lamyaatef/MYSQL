<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import ="javax.servlet.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
          
         import="com.mobinil.sds.web.interfaces.commission.*"      
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.model.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.*"         
         import="com.mobinil.sds.core.system.commission.model.*"  
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"         
         import="com.mobinil.sds.core.system.commission.factor.model.*"

%>
<%
try{
String appName = request.getContextPath();
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
Vector commissionFactors = (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_FACTORS);
Vector commissionFactors_options = (Vector)objDataHashMap.get(SIPInterfaceKey.FACTOR_OPTIONS);
String commissionID = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
//Vector commissionFactors = (Vector) objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_FACTORS);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannel" id="formChannel" method="post">
<input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION %>" value="<%=SIPInterfaceKey.ACTION_FACTOR_UPDATE %>">
 
<%
out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID+"\""+
    	
        " value=\""+commissionID+"\">");   
//out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
  //                " value=\""+strUserID+"\">");             
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID+"\""+
                  " value=\""+commissionID+"\">");             
                  
  //out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID+"\""+
    //              " value=\""+commissionType+"\">");
                  %>

<table class="tablesorter" id="tableComFactors">
<thead>
<TR>
<th>Factors Name</th>
<th>Factors Value</th>
 
</TR>
</thead>
<tbody>
<%

if(commissionFactors!=null)
for(int i = 0 ; i < commissionFactors.size() ; i++)
{
FactorModel factorModel = (FactorModel)commissionFactors.get(i);
//System.out.println("aaaaaaa");
String strCommissionFactorName = factorModel.getCommissionFactorName();
//System.out.println("aaaaaaa");
String strLastFourChars = "";
String strFirstFourAndLastFourChars = "";
//System.out.println("aaaaaaa");
/*if(strCommissionFactorName.length()>=4)
{
  strLastFourChars = strCommissionFactorName.substring(strCommissionFactorName.length()-4,strCommissionFactorName.length());
}
else
{
  strLastFourChars =  strCommissionFactorName;
}

if(strCommissionFactorName.length()>=8)
{
  strFirstFourAndLastFourChars = strCommissionFactorName.substring(0,4) + strCommissionFactorName.substring(strCommissionFactorName.length()-4,strCommissionFactorName.length());
}
else
{
  strFirstFourAndLastFourChars =  strCommissionFactorName;
}

*/
int intCommissionValue = 1;
//if(commissionType.compareTo("2")==0)intCommissionValue = -1;
double commissionFactorValue = factorModel.getCommissionFactorValue()*intCommissionValue;
System.out.println("aaaaaaa");
%>
<TR>
  <TD> <%=strCommissionFactorName%></TD>
  <TD>
  <select  name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE%>_<%=factorModel.getCommissionFactorID()%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE%>_<%=factorModel.getCommissionFactorID()%>" value="<%=commissionFactorValue%>">>
  <%
  for(int l=commissionFactors_options.size()-1; l>=0; l--){
	  String slected="";
	  System.out.println(l);
	  if(l == commissionFactorValue)
		  slected="selected";
	  else
		  slected="";
  %>
  <option value="<%=(l) %>" <%=slected %>> <%=commissionFactors_options.get(l) %> </option>
  <%} %>
  </select>
  
  <font color="#FFFFFF"><%=commissionFactorValue%></font></TD>
  
  
</TR>

<%


}
%>
</tbody>
</table>
<center>
<%
  out.println("<input type=\"button\" name=\"save\" value=\"Save\" class=\"button\" onclick=\"document.formChannel."+InterfaceKey.HASHMAP_KEY_ACTION+
              ".value='"+SIPInterfaceKey.ACTION_FACTOR_UPDATE +"';formChannel.submit();"+
              "\">");
}catch(Exception e){e.printStackTrace();} 
  %>
</center>  
</form>
</body>
</html>
