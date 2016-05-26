<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"         
         import="com.mobinil.sds.core.system.commission.factor.model.*"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
<link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
</head>
<center><H2>Commission Factors</H2></center>
<body>
<SCRIPT language="javascript">
  $(document).ready(function(){$("#tableComFactors").tablesorter(); });
  var rules=new Array();
</script>  

<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String commissionID = (String)objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);
  Vector commissionFactors = (Vector) objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_FACTORS);
  Vector<FactorModel> commissionFactorsNotInDP = (Vector) objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_FACTORS_NOT_IN_DP);
  String commissionType = (String)objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID);
%>

<form name="COMform" action="" method="post">
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID+"\""+
                  " value=\""+commissionID+"\">");             
                  
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID+"\""+
                  " value=\""+commissionType+"\">");             
                                    
%>
<table class="tablesorter" id="tableComFactors">
<thead>
<TR>
<th>Factors Name</th>
<th>Factors Value</th>
<th>Arrange By Last Four Characters</th>
<th>Arrange By First and Last Four Characters</th>
</TR>
</thead>
<tbody>
<%
if(commissionFactors!=null)
for(int i = 0 ; i < commissionFactors.size() ; i++)
{
FactorModel factorModel = (FactorModel)commissionFactors.get(i);
String strCommissionFactorName = factorModel.getCommissionFactorName();
String strLastFourChars = "";
String strFirstFourAndLastFourChars = "";
if(strCommissionFactorName.length()>=4)
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


int intCommissionValue = 1;
if(commissionType.compareTo("2")==0)intCommissionValue = -1;
double commissionFactorValue = factorModel.getCommissionFactorValue()*intCommissionValue;
%>
<TR>
  <TD> <%=strCommissionFactorName%></TD>
  <TD><input disabled="true" type="text" name="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE%>_<%=factorModel.getCommissionFactorID()%>" id="<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE%>_<%=factorModel.getCommissionFactorID()%>" value="<%=commissionFactorValue%>">
  <font color="#FFFFFF"><%=commissionFactorValue%></font></TD>
  <TD><font color="#FFFFFF"><%=strLastFourChars%></font></TD>
  <TD><font color="#FFFFFF"><%=strFirstFourAndLastFourChars%></font></TD>
</TR>

<%
out.println("<script>rules["+i+"]='"+CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE+"_"+factorModel.getCommissionFactorID()+"|numrange|0-100000000000000|Must be Positive';</script>");

}
%>
</tbody>
</table>
<center>
    <%
    if (!commissionFactorsNotInDP.isEmpty())
       %>
       <h3>Commission factors doesn't exists in driving plan factors</h3>
       <table class="tablesorter" id="tableComFactorsNotDP">
    <thead>
<TR>
<th>Factors Name</th>
<th>Factors Value</th>
</TR>
</thead>
<tbody>
    <% 
for (FactorModel factorModel :commissionFactorsNotInDP )    
   {
    %>
    <tr>
        <td > <font style="color: red"><%=factorModel.getCommissionFactorName() %></font></td>
<td><font style="color: red"><%=factorModel.getCommissionFactorValue() %></font></td>
    </tr>
    
    <%
    
}
%>
</tbody>
       </table>
</center>  
</form>
</body>
</html>
