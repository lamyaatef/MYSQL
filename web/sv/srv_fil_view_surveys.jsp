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

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_MESSAGE) ;
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  
  Vector vecFilSurveyList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  Vector vecFilSurveyStatusList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String userId = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+userId+"\">");               
    
  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+"\">");                
%>
    <CENTER>
      <H2> Filling Surveys List</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="15%" nowrap align=middle>Survey Name</td>
        <td width="15%" nowrap align=middle>Description</td>
        <TD width="10%" noWrap align=middle>User</TD>
        <TD width="10%" noWrap align=middle>Date</TD>
        <TD width="10%" noWrap align=middle>Status</TD>
        <TD width="10%" noWrap align=middle>View Groups</TD>
      </TR>
      <%
        String filSurveyId = "";
        String filSurveyName = "";
        String filSurveyStatusId = "";
        String filSurveyStatusName = "";
        String filSurveyTypeId = "";
        String surveyTypeName = "";
        String filSurveyDescription = "";
        String filSurveyReferenceType = "";
        String filSurveyReferenceId = "";
        String filSurveyDate  = "";
        String filSurveyCompleted = "";
        String filSurveyValue = "";
        String surveyId = "";
        String filSurveyUserId = ""; 
        String surveyCategoryId = "";
        String surveyCategoryName = "";
        String surveyTypeStatusId = "";
        String surveyTypeStatusName = "";
        String surveyCategoryStatusId = "";
        String surveyCategoryStatusName = "";
        String personFullName = "";

        String filSurveyStatusIdX = "";
        String filSurveyStatusNameX = "";
        
        for (int i=0; i<vecFilSurveyList.size(); i++)
        {
            FilSurveyModel filSurveyModel = (FilSurveyModel) vecFilSurveyList.get(i); 
            filSurveyId = filSurveyModel.getFilSurveyId();
            filSurveyName = filSurveyModel.getFilSurveyName();
            filSurveyStatusId = filSurveyModel.getFilSurveyStatusId();
            filSurveyStatusName = filSurveyModel.getFilSurveyStatusName();
            filSurveyTypeId = filSurveyModel.getFilSurveyTypeId();
            surveyTypeName = filSurveyModel.getSurveyTypeName();
            filSurveyDescription = filSurveyModel.getFilSurveyDescription();
            if(filSurveyDescription == null || filSurveyDescription.compareTo("null")==0 )filSurveyDescription="";
            filSurveyReferenceType = filSurveyModel.getFilSurveyReferenceType();
            filSurveyReferenceId = filSurveyModel.getFilSurveyReferenceId();
            filSurveyDate  = filSurveyModel.getFilSurveyDate();
            String filSurveyDateYearFormat = filSurveyDate.substring(0,10);
            filSurveyCompleted = filSurveyModel.getFilSurveyCompleted();
            filSurveyValue = filSurveyModel.getFilSurveyValue();
            surveyId = filSurveyModel.getSurveyId();
            filSurveyUserId = filSurveyModel.getFilSurveyUserId(); 
            surveyCategoryId = filSurveyModel.getSurveyCategoryId();
            surveyCategoryName = filSurveyModel.getSurveyCategoryName();
            surveyTypeStatusId = filSurveyModel.getSurveyTypeStatusId();
            surveyTypeStatusName = filSurveyModel.getSurveyTypeStatusName();
            surveyCategoryStatusId = filSurveyModel.getSurveyCategoryStatusId();
            surveyCategoryStatusName = filSurveyModel.getSurveyCategoryStatusName();
            personFullName = filSurveyModel.getPersonFullName();            
      %>
      <TR class=TableTextNote>
        <td align=middle><%=filSurveyName%></td>
        <td align=middle><%=filSurveyDescription%></td>
        <TD align=middle><%=personFullName%></TD>
        <TD align=middle><%=filSurveyDateYearFormat%></TD>
        <TD align=middle><select name="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS%>_<%=filSurveyId%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_STATUS%>_<%=filSurveyId%>">
              <%
              for (int k=0; k<vecFilSurveyStatusList.size(); k++)
              {
                  FilSurveyStatusModel filSurveyStatusModel = (FilSurveyStatusModel) vecFilSurveyStatusList.get(k);
                  filSurveyStatusIdX = filSurveyStatusModel.getFilSurveyStatusId();
                  filSurveyStatusNameX = filSurveyStatusModel.getFilSurveyStatusName();
                  String selectedStatus = "";
                  if(filSurveyStatusIdX.equals(filSurveyStatusId))
                  {
                      selectedStatus = "selected";
                  }
              %>
              <option value="<%=filSurveyStatusIdX%>" <%=selectedStatus%>><%=filSurveyStatusNameX%></option>
              <%
              }
              %>
            </select></TD>
        <%    
        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"\" id=\"\" value=\"View Groups\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_FILLING_VIEW_SURVEY_GROUPS+"';"+
                    "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+".value = '"+filSurveyId+"';formSurveyDetails.submit();\"></TD>");
      }
      %>
      </TR>
    </table>

    <br><br>
      <center>
      <%
        out.println("<INPUT class=button type=button value=\" Add New Survey \" name=\"addnew\" id=\"addnew\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_FILLING_CREATE_NEW_SURVEY+"';"+
                      "formSurveyDetails.submit();\">");
        out.println("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_FILLING_UPDATE_SURVEY_STATUS+"';"+
                      "formSurveyDetails.submit();\">");
      %>
      </center>
      
    </form>
  </body>
</html>
