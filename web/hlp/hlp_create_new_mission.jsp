<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.core.system.hlp.mission.model.*" 
         import="com.mobinil.sds.core.system.sv.surveys.model.*"
         import="java.sql.Timestamp"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(HLPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    
    function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
    
    function checkBeforeSubmit()
    {
      var missionName = document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_MISSION_NAME%>.value
      var missionStartDate = document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_MISSION_START_DATE%>.value
      var missionEndDate = document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_MISSION_END_DATE%>.value
      var surveyId = document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_FIL_SURVEY_ID%>.value

      var currentDate = new Date();
      var currentYear = parseFloat(currentDate.getYear());
      var currentMonth = parseFloat(currentDate.getMonth()+1);
      var currentDay = parseFloat(currentDate.getDate());
    
      if(missionName.length == 0)
      {
        alert("Mission Name can not be empty.");
        return;
      }
      else if(missionStartDate == "*")
      {
        alert("Mission start date can not be empty.");
        return;
      }
      else if(missionEndDate == "*")
      {
        alert("Mission end date can not be empty.");
        return;
      }
      else if(surveyId.length == 0)
      {
        alert("Survey can not be empty.");
        return;
      }
      else
      {
        var missionStartDatefirstIndex = missionStartDate.indexOf ("/");
        var missionStartDatelastIndex = missionStartDate.lastIndexOf ("/");
        missionStartDateMonth = parseFloat(missionStartDate.substring (0, missionStartDatefirstIndex));
        missionStartDateDate = parseFloat(missionStartDate.substring (missionStartDatefirstIndex+1, missionStartDatelastIndex));
        missionStartDateYear = parseFloat(missionStartDate.substring (missionStartDatelastIndex+1, missionStartDate.length));

        var missionEndDatefirstIndex = missionEndDate.indexOf ("/");
        var missionEndDatelastIndex = missionEndDate.lastIndexOf ("/");
        missionEndDateMonth = parseFloat(missionEndDate.substring (0, missionEndDatefirstIndex));
        missionEndDateDate = parseFloat(missionEndDate.substring (missionEndDatefirstIndex+1, missionEndDatelastIndex));
        missionEndDateYear = parseFloat(missionEndDate.substring (missionEndDatelastIndex+1, missionEndDate.length));

        if( currentYear < missionStartDateYear &&  missionStartDateYear < missionEndDateYear)
        {
            HLPform.submit();
        }
        else
        {
          if((currentMonth < missionStartDateMonth && currentYear <= missionStartDateYear)&& (missionStartDateMonth <missionEndDateMonth && missionStartDateYear <= missionEndDateYear))
          {
            HLPform.submit();
          }
          else
          {
             if((currentDay <= missionStartDateDate && currentMonth <= missionStartDateMonth  && currentYear <= missionStartDateYear )&& (missionStartDateDate < missionEndDateDate  && missionStartDateMonth <=missionEndDateMonth  && missionStartDateYear <= missionEndDateYear ))
             {
                HLPform.submit();
             }
             else
             {
              if((currentDay >= missionStartDateDate && currentMonth >= missionStartDateMonth && currentYear <= missionStartDateYear) && (missionStartDateDate < missionEndDateDate  && missionStartDateMonth <=missionEndDateMonth  && missionStartDateYear <= missionEndDateYear ))
              {
                HLPform.submit();
              } 
              else
             {
              if((currentDay <= missionStartDateDate && currentMonth <= missionStartDateMonth && currentYear <= missionStartDateYear) && (missionStartDateDate >= missionEndDateDate  && missionStartDateMonth >= missionEndDateMonth  && missionStartDateYear < missionEndDateYear ))
              {
                HLPform.submit();
              } 
             else
             {
                alert("End date must be greater than start date and start date must be greater than current date.");
                return;
             }
             }
          }
          }
        }
      }
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector vecSurveyList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  
  String strPageHeader = (String)objDataHashMap.get(HLPInterfaceKey.PAGE_HEADER);
  String strPreviousAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);

  MissionModel missionModel = null;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  {  
    missionModel = (MissionModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);      
  }

  String missionId = "";
  String missionName = "";
  String missionDescription = "";
  String strMissionStartDate = "*";
  String strMissionEndDate  = "*";
  String surveyId = "";
  Date creationDate = null;
  String missionStatusId = "";
  String missionStatusTypeId = "";
  String missionStatusTypeName = ""; 
  Timestamp missionStatusTimestamp = null;
  String userId = "";
  
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
  }
%>   
    <CENTER>
      <H2> <%=strPageHeader%> </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID+"\""+
                  " value=\""+missionId+"\">");                      
%> 
      
      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Mission Name</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=HLPInterfaceKey.INPUT_TEXT_MISSION_NAME%>" id="<%=HLPInterfaceKey.INPUT_TEXT_MISSION_NAME%>" value="<%=missionName%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Description</TD>
          <TD class=TableTextNote colSpan=4><textarea rows=5 cols=50 name="<%=HLPInterfaceKey.INPUT_TEXTAREA_MISSION_DESCRIPTION%>" id="<%=HLPInterfaceKey.INPUT_TEXTAREA_MISSION_DESCRIPTION%>"><%=missionDescription%></textarea></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Start Date</TD>
          <TD class=TableTextNote colSpan=4><Script>drawCalender('<%=HLPInterfaceKey.INPUT_TEXT_MISSION_START_DATE%>',"<%=strMissionStartDate%>");</script></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">End Date</TD>
          <TD class=TableTextNote colSpan=4><Script>drawCalender('<%=HLPInterfaceKey.INPUT_TEXT_MISSION_END_DATE%>',"<%=strMissionEndDate%>");</script></td>
        </tr>    
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Survey</TD>
          <TD class=TableTextNote colSpan=4>
            <select name="<%=HLPInterfaceKey.INPUT_SELECT_FIL_SURVEY_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_FIL_SURVEY_ID%>">
              <option value=""></option>
              <%
              for(int i=0;i<vecSurveyList.size();i++)
              {
                SurveyModel surveyModel = (SurveyModel)vecSurveyList.get(i);
                String strSurveyId = surveyModel.getSurveyId();
                String strSurveyName = surveyModel.getSurveyName();
                String strSurveyType = surveyModel.getSurveyTypeId();

                String surveySelected = "";
                if(strSurveyType.equals("1"))
                {
                 Utility.logger.debug("strSurveyType"+strSurveyType);
                if(strSurveyId.compareTo(surveyId)==0)surveySelected = "selected";
                %>
                <option value="<%=strSurveyId%>" <%=surveySelected%>><%=strSurveyName%></option>
                <%
                }
              }
              %>
            </select>
          </td>
        </tr>
       </table> 

    <br><br>
      <center>
      <%
        if(strPreviousAction.compareTo(HLPInterfaceKey.ACTION_HLP_CREATE_NEW_MISSION) == 0)
        {
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_NEW_MISSION+"';"+
                  "checkBeforeSubmit();\">");
        }
        else if(strPreviousAction.compareTo(HLPInterfaceKey.ACTION_HLP_EDIT_MISSION) == 0)
        {
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_UPDATE_MISSION_DETAILS+"';"+
                  "checkBeforeSubmit();\">");
        }
      %>
      </center>
       
</form>
</body>
</html>
