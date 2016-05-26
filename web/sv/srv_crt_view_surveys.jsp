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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formSurveyDetails" id="formSurveyDetails" method="post">  
<%


  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  Vector vecSurveyList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  Vector vecSurveyStatusList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_MESSAGE) ;
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+"\">");                
%>  
    <CENTER>
      <H2> Surveys List</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="25%" nowrap align=middle>Survey Name</td>
        <td width="25%" nowrap align=middle>Description</td>
        <TD width="10%" noWrap align=middle>Type</TD>
        <TD width="10%" noWrap align=middle>Status</TD>
        <TD width="10%" noWrap align=middle>View Groups</TD>
        <TD width="10%" noWrap align=middle>Edit</TD>
      </TR>
      <%
        String surveyId = "";
        String surveyName = "";
        String surveyStatusId = "";
        String surveyStatusName = "";
        String surveyTypeId = "";
        String surveyTypeName = "";
        String surveyDescription = "";  
        String surveyCategoryId = "";
        String surveyCategoryName = "";
        String surveyTypeStatusId = "";
        String surveyTypeStatusName = "";
        String surveyCategoryStatusId = "";
        String surveyCategoryStatusName = "";

        String statusId = "";
        String statusName = "";
        
        for (int i=0; i<vecSurveyList.size(); i++)
        {
            SurveyModel surveyModel = (SurveyModel) vecSurveyList.get(i);            
            surveyId = surveyModel.getSurveyId();
            surveyName = surveyModel.getSurveyName();
            surveyStatusId = surveyModel.getSurveyStatusId();
            surveyStatusName = surveyModel.getSurveyStatusName();
            surveyTypeId = surveyModel.getSurveyTypeId();
            surveyTypeName = surveyModel.getSurveyTypeName();
            surveyDescription = surveyModel.getSurveyDescription();  
            if(surveyDescription == null)surveyDescription="";
            surveyCategoryId = surveyModel.getSurveyCategoryId();
            surveyCategoryName = surveyModel.getSurveyCategoryName();
            surveyTypeStatusId = surveyModel.getSurveyTypeStatusId();
            surveyTypeStatusName = surveyModel.getSurveyTypeStatusName();
            surveyCategoryStatusId = surveyModel.getSurveyCategoryStatusId();
            surveyCategoryStatusName = surveyModel.getSurveyCategoryStatusName();        
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=surveyName%></td>
        <td width="20%" nowrap align=middle><%=surveyDescription%></td>
        <TD width="10%" noWrap align=middle><%=surveyTypeName%></TD>
        <TD width="10%" noWrap align=middle>
            <select name="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS%>_<%=surveyId%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS%>_<%=surveyId%>">
              <%
              for (int k=0; k<vecSurveyStatusList.size(); k++)
              {
                  SurveyStatusModel surveyStatusModel = (SurveyStatusModel) vecSurveyStatusList.get(k);
                  statusId = surveyStatusModel.getSurveyStatusId();
                  statusName = surveyStatusModel.getSurveyStatusName();
                  String selectedStatus = "";
                  if(statusId.equals(surveyStatusId))
                  {
                      selectedStatus = "selected";
                  }
              %>
              <option value="<%=statusId%>" <%=selectedStatus%>><%=statusName%></option>
              <%
              }
              %>
            </select></TD>
        <%    
        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"\" id=\"\" value=\"View Groups\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_VIEW_SURVEY_GROUPS+"';"+
                    "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+".value = '"+surveyId+"';formSurveyDetails.submit();\"></TD>");

        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"\" id=\"\" value=\"Edit\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_EDIT_SURVEY+"';"+
                    "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+".value = '"+surveyId+"';formSurveyDetails.submit();\"></TD>");
        %>  
      </TR>
      <%
      }
      %>
    </table>

    <br><br>
      <center>
      <%
        out.println("<INPUT class=button type=button value=\" Add New Survey \" name=\"addnew\" id=\"addnew\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_CREATE_NEW_SURVEY+"';"+
                      "formSurveyDetails.submit();\">");
        out.println("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_UPDATE_SURVEYS_STATUS+"';"+
                      "formSurveyDetails.submit();\">");
      %>
      </center>
      
  </form>  
  </body>
</html>
