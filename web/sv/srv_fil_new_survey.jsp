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
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
%> 
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<script>
 	function drawCalender(argOrder,argValue)
	{
      document.write("<INPUT value="+argValue+" class=input readOnly name=\"survey_create_date"+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formSurveyDetails.survey_create_date"+argOrder+",'mm/dd/yyyy','Choose date')\">");
      document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
  }

</script>
  <body>
<form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formSurveyDetails" id="formSurveyDetails" method="post">  
<%

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserId = (String)objDataHashMap.get (InterfaceKey.HASHMAP_KEY_USER_ID);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserId+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                  
  Vector surveyList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);                

  DCMDto dcmdto = (DCMDto) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  int dcmmodelsize = dcmdto.getDcmModelsSize();
%>
    <CENTER>
      <H2> Create New Survey</H2>
    </CENTER>
  
      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">DCM&nbsp;Name *</TD>
          <TD class=TableTextNote colSpan=4>
            <SELECT style="WIDTH: 451px" name="<%=SurveyInterfaceKey.INPUT_SELECT_DCM_NAME%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_DCM_NAME%>">
<%
  for(int i=0;i<dcmmodelsize;i++)
  {
      DCMModel dcmmodel = dcmdto.getDcm(i);
      int dcmID= dcmmodel.getDcmId();
      String dcmCode = dcmmodel.getDcmCode();
      String dcmName = dcmmodel.getDcmName();
      String dcmLevel = dcmmodel.getDcmLevel();
      %>
      <option value="<%=dcmID%>"><%=dcmName%>      |      <%=dcmCode%></option>
      <%
  }
%>
            </select>
          </td>
        </tr>      
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Select Survey&nbsp; *</TD>
          <TD class=TableTextNote colSpan=4>
            <SELECT style="WIDTH: 451px" name="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_SURVEY%>">
<%
for(int j=0;j<surveyList.size();j++)
{
    SurveyModel surveyModel = (SurveyModel) surveyList.get(j); 
    String surveyId = surveyModel.getSurveyId();
    String surveyName = surveyModel.getSurveyName();
    String surveyStatusId = surveyModel.getSurveyStatusId();
    String surveyStatusName = surveyModel.getSurveyStatusName();
    String surveyTypeId = surveyModel.getSurveyTypeId();
    String surveyTypeName = surveyModel.getSurveyTypeName();
    String surveyDescription = surveyModel.getSurveyDescription();  
    String surveyCategoryId = surveyModel.getSurveyCategoryId();
    String surveyCategoryName = surveyModel.getSurveyCategoryName();
    String surveyTypeStatusId = surveyModel.getSurveyTypeStatusId();
    String surveyTypeStatusName = surveyModel.getSurveyTypeStatusName();
    String surveyCategoryStatusId = surveyModel.getSurveyCategoryStatusId();
    String surveyCategoryStatusName = surveyModel.getSurveyCategoryStatusName();
%>
    <option value="<%=surveyId%>"><%=surveyName%></option>
<%
}

%>
            </select>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Fil Survey&nbsp;Name *</TD>
          <TD class=TableTextNote colSpan=4><input type="text" name="<%=SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_SURVEY_NAME%>" value=""></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Date *</TD>
          <TD class=TableTextNote colSpan=4><Script>drawCalender(5,"*");</script></td>
        </tr>
      </table>

      <center>
        <%
        out.println("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_FILLING_SAVE_SURVEY+"';formSurveyDetails.submit();\">");
        %>
      </center>
    </form>
  </body>
</html>
