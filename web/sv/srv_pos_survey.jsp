<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
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
<Center><h2> POS Surveys</h2></center>  
<form name="SRVform" action="" method="POST">

<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  Vector POSSurvey = (Vector)objDataHashMap.get(SurveyInterfaceKey.VECTOR_POS_SURVEY);

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
 out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+"\">");                  
%>                  

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
  <TR>
    <TD class="TableHeader" colspan="2">Surveys </TD>
  </TR>
  <TR>
    <TD class="TableHeader" align="center"> Survey Name</TD>
    <TD class="TableHeader" align="center"> Survey Groups </TD>
  </TR>
  <%for(int i = 0 ; i < POSSurvey.size() ; i ++)
  {
  SurveyModel surveyModel = (SurveyModel)POSSurvey.get(i);
  %>
  <TR>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center"><%=surveyModel.getSurveyName()%></TD>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center"> 
 <%
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Groups\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_POS_SURVEY_GROUP+"';"+
                "document.SRVform."+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+".value='"+surveyModel.getSurveyId()+"';"+                
                "SRVform.submit();\"></TD>");
              
    }%>
    
    </TD>
  </TR>
  <TR>
    <TD align="center" colspan="3">

     <%
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Create New Survey\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_SURVEY+"';"+
                "SRVform.submit();\"></TD>");
    %>

    </TD>
  </TR>

</table>
</body>

</html>
