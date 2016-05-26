<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sv.*"
         import="com.mobinil.sds.core.system.sv.surveys.model.*"
%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<body>
<Center><h2> POS Groups</h2></center>
<form name="SRVform" action="" method="post">
<%

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  Vector POSGroup = (Vector)objDataHashMap.get(SurveyInterfaceKey.VECTOR_POS_GROUP);
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
    out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+"\">");
                  
%>                  

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
  <TR>
    <TD class="TableHeader" colspan="3">Groups </TD>
  </TR>
  <TR>
    <TD class="TableHeader" align="center"> Group Name</TD>
    <TD class="TableHeader" align="center"> Group Weight</TD>
    <TD class="TableHeader" align="center"> Group Questions </TD>
  </TR>
<%
for(int i = 0 ; i < POSGroup.size() ; i++)
{
GroupModel groupModel = (GroupModel)POSGroup.get(i);
  out.println("<TR>");
  Utility.logger.debug(i+"");
    out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=\"center\">"+groupModel.getGroupName()+"</TD>");
    out.println("<TD  class="+InterfaceKey.STYLE[i%2]+" align=\"center\"> "+groupModel.getGroupWeight()+"</TD>");
    out.println("<TD class="+InterfaceKey.STYLE[i%2]+"  align=\"center\"> ");
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Questions\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_POS_GROUP_QUESTION+"';"+
                "document.SRVform."+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value='"+groupModel.getGroupId()+"';"+
                "SRVform.submit();"+"\">");
                
    out.println("</TD>");
  out.println("</TR>");
  }
  %>
  <TR>
    <TD align="center" colspan="3">
<%
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Create New Group\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_GROUP+"';"+
                "SRVform.submit();"+
                "\">");
    %>
    </TD>
  </TR>

</table>
</form>
</body>

</html>
