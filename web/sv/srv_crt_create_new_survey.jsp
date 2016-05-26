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
  <script>
  
  function checkBeforeSubmit()
  {
    var surveyName = document.formSurveyDetails.<%=SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME%>.value;
    if(surveyName == "")
    {
      alert("Survey name can not be empty.");
      return;
    }
    else
    {
      formSurveyDetails.submit();
    }
  }
  </script>
<form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formSurveyDetails" id="formSurveyDetails" method="post">  
<%

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_MESSAGE) ;
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  
  Vector vecSurveyTypeList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;

  Vector vecSurveyUpdated = new Vector();

  String XsurveyId = "";
  String XsurveyName = "";
  String XsurveyStatusId = "";
  String XsurveyStatusName = "";
  String XsurveyTypeId = "";
  String XsurveyTypeName = "";
  String XsurveyDescription = "";  
  String XsurveyCategoryId = "";
  String XsurveyCategoryName = "";
  String XsurveyTypeStatusId = "";
  String XsurveyTypeStatusName = "";
  String XsurveyCategoryStatusId = "";
  String XsurveyCategoryStatusName = "";

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  {
    vecSurveyUpdated = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;

    SurveyModel surveyModel = (SurveyModel) vecSurveyUpdated.get(0);            
    XsurveyId = surveyModel.getSurveyId();
    XsurveyName = surveyModel.getSurveyName();
    XsurveyStatusId = surveyModel.getSurveyStatusId();
    XsurveyStatusName = surveyModel.getSurveyStatusName();
    XsurveyTypeId = surveyModel.getSurveyTypeId();
    XsurveyTypeName = surveyModel.getSurveyTypeName();
    XsurveyDescription = surveyModel.getSurveyDescription();  
    XsurveyCategoryId = surveyModel.getSurveyCategoryId();
    XsurveyCategoryName = surveyModel.getSurveyCategoryName();
    XsurveyTypeStatusId = surveyModel.getSurveyTypeStatusId();
    XsurveyTypeStatusName = surveyModel.getSurveyTypeStatusName();
    XsurveyCategoryStatusId = surveyModel.getSurveyCategoryStatusId();
    XsurveyCategoryStatusName = surveyModel.getSurveyCategoryStatusName();  
  }

  String strSurveyAction = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION) ;

  String strSurveyStatus = "";
  String pageTitle = "";
  String surveyAction = "";
  if(strSurveyAction.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_SURVEY))
  {
      strSurveyStatus = "1";
      pageTitle = " Create New Survey";
      surveyAction = SurveyInterfaceKey.ACTION_SAVE_SURVEY;
  }
  else
  {
      strSurveyStatus = XsurveyStatusId;
      pageTitle = " Edit Survey";
      surveyAction = SurveyInterfaceKey.ACTION_UPDATE_SURVEY;
  }

%>
    <CENTER>
      <H2><%=pageTitle%></H2>
    </CENTER>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                  
  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_STATUS+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+"\">");
  
%>

      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Survey&nbsp;Name *</TD>
          <TD class=TableTextNote colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME%>" value="<%=XsurveyName%>"></td>
        </tr>
        <!--TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Survey&nbsp;Type *</TD>
          <TD class=TableTextNote colSpan=4-->
          <%if(vecSurveyTypeList != null){%>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Survey&nbsp;Type *</TD>
             <TD class=TableTextNote colSpan=4><SELECT  name="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_TYPE%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_TYPE%>">
              <%
              String surveyTypeId = "";
              String surveyTypeName = "";
              String surveyTypeStatusId = "";
              String surveyTypeStatusName = "";
              String surveyCategoryId = "";
              String surveyCategoryName = "";
              
              for (int i=0; i<vecSurveyTypeList.size(); i++)
              {
                  SurveyTypeModel surveyTypeModel = (SurveyTypeModel) vecSurveyTypeList.get(i);            
                  surveyTypeId = surveyTypeModel.getSurveyTypeId();
                  surveyTypeName = surveyTypeModel.getSurveyTypeName();
                  surveyTypeStatusId = surveyTypeModel.getSurveyTypeStatusId();
                  surveyTypeStatusName = surveyTypeModel.getSurveyTypeStatusName();
                  surveyCategoryId = surveyTypeModel.getSurveyCategoryId();
                  surveyCategoryName = surveyTypeModel.getSurveyCategoryName();

                  String surveyTypeSelected = "";
                  if(surveyTypeId.equals(XsurveyTypeId))
//                  if(surveyTypeId.compareTo("1") ==0 )
                  {
                      surveyTypeSelected = "selected";
                  }
                  %>
                  <option value="<%=surveyTypeId%>" <%=surveyTypeSelected%>><%=surveyTypeName%></option>
                  <%
              }
              %>
              </select></td></tr>
              <%}else{%>
              <input type="hidden" name="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY_TYPE%>" value="2"><%}%>
          <!--/td>
        </tr-->
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Survey&nbsp;Description *</TD>
          <TD class=TableTextNote colSpan=4><TEXTAREA id="<%=SurveyInterfaceKey.INPUT_TEXTAREA_SURVEY_DESCRIPTION%>" style="WIDTH: 451px; HEIGHT: 84px" name="<%=SurveyInterfaceKey.INPUT_TEXTAREA_SURVEY_DESCRIPTION%>" rows=4 cols=47><%=XsurveyDescription%></TEXTAREA></td>
        </tr>
      </table>

      <center>
      <br><br>
      <%
        out.print("<INPUT class=button onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+surveyAction+"';"+
                   "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_STATUS+".value = '"+strSurveyStatus+"';document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+".value = '"+XsurveyId+"';checkBeforeSubmit();\" type=button value=\" Save \" name=\"save\" id=\"save\">");
      %>
      </center>
</form>      
  </body>
</html>
