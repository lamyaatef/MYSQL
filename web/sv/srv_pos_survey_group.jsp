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
<Center><h2> POS Survey Groups</h2></center>
    <form name="SRVform" action="" method="POST"> 
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  Vector POSSurveyGroup = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector POSGroup = (Vector)objDataHashMap.get(SurveyInterfaceKey.VECTOR_POS_GROUP);
  String surveyID = (String)objDataHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID);

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
    out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+"\">");
    out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+surveyID+"\">");
                  
%>                  

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
  <TR>
    <TD class="TableHeader" colspan="4">Groups </TD>
  </TR>
  <TR>
    <TD class="TableHeader" align="center"> Group Name</TD>
    <TD class="TableHeader" align="center"> Group Weight</TD>
    <TD class="TableHeader" align="center"> Select Group</TD>
    <TD class="TableHeader" align="center"> Group Questions </TD>
  </TR>
  <%for(int i = 0 ; i < POSGroup.size() ; i++)
  {
  GroupModel groupModel = (GroupModel)POSGroup.get(i);
  String weight = "";
  String checked = "";
  String disabled = "DISABLED";
  String state = "new";
    for(int j = 0 ; j < POSSurveyGroup.size() ; j++)
    {
      GroupModel surveyGroup = (GroupModel)POSSurveyGroup.get(j);
      if(groupModel.getGroupId().equals(surveyGroup.getGroupId()))
      {
        weight = surveyGroup.getGroupWeight();
        checked = "checked";
        disabled = "";
        state = "old";
      }
    }
      String groupID = groupModel.getGroupId();
  %>
  <TR>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center"><%=groupModel.getGroupName()%> </TD>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center"> <input type="texr" name="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT%>_<%=groupID%>" value="<%=weight%>" <%=disabled%>></TD>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center">
    <%out.println("<input type=\"checkbox\" name=\"\" class=\"button\" "+checked+" onclick=\""+
                  "if(document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT+"_"+groupID+".disabled==false)"+
                  "{document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT+"_"+groupID+".disabled=true;}"+
                  "else if(document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT+"_"+groupID+".disabled==true)"+
                  "{document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT+"_"+groupID+".disabled=false;}"+                  
                    "\">");
    out.println("<input type=\"hidden\" name=\"state_"+groupID+"\" value=\""+state+"\">");
    out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"_"+groupID+"\" value=\""+groupID+"\">");
                    %>
                    
    
    </TD>
    <TD class="<%=InterfaceKey.STYLE[i%2]%>" align="center"> 

    <%
    out.println("<input type=\"button\" name=\"groupquestion\" class=\"button\" value=\"Questions\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_POS_GROUP_QUESTION+"';"+
                "document.SRVform."+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value='"+groupID+"';"+                
                "SRVform.submit();\"></TD>");
    }%>
    </TD>   
  </TR>
  <TR>
    <TD align="center" colspan="4">

<%
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Save Survey Groups\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_SAVE_POS_SURVEY_GROUP+"';"+
                "SRVform.submit();\"></TD>");
    %>
    </TD>
  </TR>

</table>
</form>
</body>


</html>
