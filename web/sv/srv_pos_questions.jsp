<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
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
<Center><h2> POS Questions</h2></center>
    <form name="SRVform" action="" method="POST">
<%
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
Vector QuestionsVec = (Vector)objDataHashMap.get(SurveyInterfaceKey.VECTOR_POS_QUESTION);
Utility.logger.debug("saaaaaaa  "+QuestionsVec.size());
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
%>                  
    
<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
  <TR>
    <TD class="TableHeader"></TD>
  </TR>
  <TR>
    <TD class="TableHeader">Question </TD>
  </TR>
<%  for(int i = 0 ; i < QuestionsVec.size() ; i++ )
{
QuestionModel questionModel = (QuestionModel)QuestionsVec.get(i);
%>
  <TR>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center"><%=questionModel.getQuestion()%></TD>
  </TR>

  <%}%>

  <TR>    <TD align="center">
    <%
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Create New Question\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_QUESTION+"';"+
                "SRVform.submit();\"></TD>");
    %>

    </TD>
  </TR>

</table>
</form>
</body>

</html>
