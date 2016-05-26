<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="java.sql.Timestamp"
         
         import="com.mobinil.sds.core.system.hlp.mission.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String errMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+errMsg+"');</script>");
  }
  MissionModel missionModel = (MissionModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  String missionId = "";
  String missionName = "";
  String missionDescription = "";
  String strMissionStartDate = null;
  String strMissionEndDate  = null;
  String surveyId = "";
  Date creationDate = null;
  String missionStatusId = "";
  String missionStatusTypeId = "";
  String missionStatusTypeName = ""; 
  Timestamp missionStatusTimestamp = null;
  String userId = "";
  int numberOfUsers =0;
  int numberOfTargetUsers =0;
  int numberOfCompletedSurveys = 0;
  int numberOfIncompletedSurveys = 0;
  
  
  if(missionModel != null)
  {
    missionId = missionModel.getMissionId();
    missionName = missionModel.getMissionName();
    missionDescription = missionModel.getMissionDescription();
    if(missionDescription == null)missionDescription = "";
    Date missionStartDate = missionModel.getMissionStartDate();
    strMissionStartDate = (missionStartDate.getMonth()+1)+"/"+missionStartDate.getDate()+"/"+(missionStartDate.getYear()+1900);
    Date missionEndDate = missionModel.getMissionEndDate() ;
    strMissionEndDate = (missionEndDate.getMonth()+1)+"/"+missionEndDate.getDate()+"/"+(missionEndDate.getYear()+1900);
    surveyId = missionModel.getSurveyId();
    creationDate = missionModel.getCreationDate();
    missionStatusId = missionModel.getMissionStatusId();
    missionStatusTypeId = missionModel.getMissionStatusTypeId();
    missionStatusTypeName = missionModel.getMissionStatusTypeName(); 
    missionStatusTimestamp = missionModel.getMissionStatusTimestamp();
    userId = missionModel.getUserId();
    numberOfUsers =missionModel.getNumberOfUsers();
    numberOfTargetUsers =missionModel.getNumberOfTargetUsers();
    numberOfCompletedSurveys = missionModel.getNumberOfSurveysCompleted();
    numberOfIncompletedSurveys = missionModel.getNumberOfSurveysIncompleted();
  }       
%>

    <CENTER>
      <H2> Select Target User </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID+"\""+
                  " value=\""+missionId+"\">");   

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_DCM_CATEGORY+"\""+
                  " value=\""+"\">");                   
                
%> 

     <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1 align="center">
        <TR class=TableHeader>
          <TD>Mission Name</TD>
          <TD>Description</TD>
          <TD>Start Date</TD>
          <TD>End Date</TD>
          <TD>Status</TD>
        </tr>
        <TR class=TableTextNote>
          <TD><%=missionName%></TD>
          <TD><%=missionDescription%></TD>
          <TD><%=strMissionStartDate%></TD>
          <TD><%=strMissionEndDate%></TD>
          <TD><%=missionStatusTypeName%></TD>
        </tr>
      </table>  

      
      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='center'>Target Users</td>
        <td align='center'>Surveys Completed</td>
        <td align='center'>Surveys Not Completed</td>
      </tr>
      <tr>
      <%
        out.println("<td align='center'><a href=\"#\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_USER_MISSION_DCM_LIST+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_DCM_CATEGORY+".value = '"+HLPInterfaceKey.DCM_CATEGORY_SURVEYS_ALL+"';"+
                      "HLPform.submit();\">"+numberOfTargetUsers+"</a></td>");
                      
        out.println("<td align='center'><a href=\"#\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_USER_MISSION_DCM_LIST+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_DCM_CATEGORY+".value = '"+HLPInterfaceKey.DCM_CATEGORY_SURVEYS_COMPLETED+"';"+
                      "HLPform.submit();\">"+numberOfCompletedSurveys+"</a></td>");
        
        out.println("<td align='center'><a href=\"#\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_USER_MISSION_DCM_LIST+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_DCM_CATEGORY+".value = '"+HLPInterfaceKey.DCM_CATEGORY_SURVEYS_INCOMPLETED+"';"+
                      "HLPform.submit();\">"+numberOfIncompletedSurveys+"</a></td>");
      %>
      </tr>
      </table>
      
      <br><br>
      <table align='center' border=0 width='60%'>
        <tr>
          <td>Please enter DCM Code : </td>
          <td><input type='text' name='<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>' id='<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>'></td>
        </tr>
        <tr>
          <td colspan=2 align='center'>
           <%
            out.print("<INPUT class=button type=button value=\" View Survey \" name=\"viewsurvey\" id=\"viewsurvey\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_TARGET_USER_SURVEY+"';"+
                      "HLPform.submit();\">");

            out.print("<INPUT class=button type=button value=\" Get random incompleted DCM \" name=\"getrandom\" id=\"getrandom\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_GET_RANDOM_INCOMPLETED_DCM+"';"+
                      "HLPform.submit();\">");

            out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

          %>
          
          </td>
        </tr>
      </table>

      <%
      if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
      {
      DCMDto dcmdto = (DCMDto) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
      int dcmmodelsize = dcmdto.getDcmModelsSize();
      if(dcmmodelsize > 0)
      {
      %>
      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1 align="center">
        <tr class="TableHeader">
          <td align='center'>DCM Name</td>
          <td align='center'>DCM Code</td>
        </tr>
      <%
      }
      for(int i=0;i<dcmmodelsize;i++)
      {
        DCMModel dcmmodel = dcmdto.getDcm(i);
        int dcmID= dcmmodel.getDcmId();
        String dcmCode = dcmmodel.getDcmCode();
        String dcmName = dcmmodel.getDcmName();
        String dcmLevel = dcmmodel.getDcmLevel();
        %>
        <tr>
          <td align='center'><%=dcmName%></td>
          <%
          out.println("<td align='center'><a href=\"#\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_TARGET_USER_SURVEY+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_TEXT_DCM_CODE+".value = '"+dcmCode+"';"+
                      "HLPform.submit();\">"+dcmCode+"</a></td>");

          %>            
        </tr>
        <%
      }
      if(dcmmodelsize > 0)
      {
      %>
      </table>
      <%
      }
      }
      %>
</body>
</html>
