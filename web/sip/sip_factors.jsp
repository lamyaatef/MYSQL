<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"         
         import="com.mobinil.sds.core.system.sip.model.*"
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
<center><H2>SIP Factors</H2></center>
<body>
<SCRIPT language="javascript">
  $(document).ready(function(){$("#tableComFactors").tablesorter(); });
  var rules=new Array();
</script>  

<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String sipID = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
  Vector sipFactors = (Vector) objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_FACTORS);
  String sipType = (String)objDataHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_TYPE_ID);
%>

<form name="sipFactform" action="" method="post">
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
  out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_ID+"\""+
                  " value=\""+sipID+"\">");             
                  
  out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_TYPE_ID+"\""+
                  " value=\""+sipType+"\">");             
                                    
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
if(sipFactors!=null)
for(int i = 0 ; i < sipFactors.size() ; i++)
{
FactorModel factorModel = (FactorModel)sipFactors.get(i);
String strsipFactorName = factorModel.getsipReportFactorName();
String strLastFourChars = "";
String strFirstFourAndLastFourChars = "";
if(strsipFactorName.length()>=4)
{
  strLastFourChars = strsipFactorName.substring(strsipFactorName.length()-4,strsipFactorName.length());
}
else
{
  strLastFourChars =  strsipFactorName;
}

if(strsipFactorName.length()>=8)
{
  strFirstFourAndLastFourChars = strsipFactorName.substring(0,4) + strsipFactorName.substring(strsipFactorName.length()-4,strsipFactorName.length());
}
else
{
  strFirstFourAndLastFourChars =  strsipFactorName;
}



//if(commissionType.compareTo("2")==0)intCommissionValue = -1;
String sipFactorValue = factorModel.getsipReportFactorValue()+"";

if (sipFactorValue.contains(".0"))
   sipFactorValue = sipFactorValue.replace(".0","");
 

%>
<TR>
  <TD> <%=strsipFactorName%></TD>
  <TD><select type="text" name="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_FACTOR_VALUE%>_<%=factorModel.getsipReportFactorID()%>" id="<%=SIPInterfaceKey.CONTROL_TEXT_SIP_FACTOR_VALUE%>_<%=factorModel.getsipReportFactorID()%>" value="<%=sipFactorValue %>">
  <% 
  if (sipFactorValue.compareTo("0")==0){out.print("<option value=0 selected=\"selected\">Include</option> ");}
  else out.print("<option value=0 >Include</option> ");
  if (sipFactorValue.compareTo("1")==0){out.print("<option value=1 selected=\"selected\">Exclude</option> ");}
  else out.print("<option value=1 >Exclude</option> ");
  %>
   
  
  </select>
  <font color="#FFFFFF"><%=sipFactorValue%></font></TD>
  <TD><font color="#FFFFFF"><%=strLastFourChars%></font></TD>
  <TD><font color="#FFFFFF"><%=strFirstFourAndLastFourChars%></font></TD>
</TR>

<%
out.println("<script>rules["+i+"]='"+SIPInterfaceKey.CONTROL_TEXT_SIP_FACTOR_VALUE+"_"+factorModel.getsipReportFactorID()+"|numrange|0-100000000000000|Must be Positive';</script>");

}
%>
</tbody>
</table>
<center>
<%
  out.println("<input type=\"button\" name=\"save\" value=\"Save\" class=\"button\" onclick=\"document.sipFactform."+InterfaceKey.HASHMAP_KEY_ACTION+
              ".value='"+SIPInterfaceKey.ACTION_SAVE_SIP_FACTORS+"'; if(performCheck('sipFactform', rules, 'classic')){sipFactform.submit();}"+
              "\">");
  %>
</center>  
</form>
</body>
</html>
