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
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(HLPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    
    function checkBeforeSubmit()
    {
      
    }

    function setSearchValues(missionName,missionStatusType,missionStartDateFrom,missionStartDateTo,missionEndDateFrom,missionEndDateTo)
    {
      document.HLPform.<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME%>.value = missionName ; 
      document.HLPform.<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS%>.value = missionStatusType ; 
      document.HLPform.<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM%>.value = missionStartDateFrom ; 
      document.HLPform.<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO%>.value = missionStartDateTo ; 
      document.HLPform.<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM%>.value = missionEndDateFrom ; 
      document.HLPform.<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO%>.value = missionEndDateTo ;
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector vecMissions = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);           
  Vector vecMissionStatusTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);           

  String searchMissionName = "";
  String searchMissionStatusTypeId = "";
  String searchMissionStartDateFrom = "*";
  String searchMissionStartDateTo = "*";
  String searchMissionEndDateFrom = "*";
  String searchMissionEndDateTo = "*";

  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME))
  searchMissionName = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS))
  searchMissionStatusTypeId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM))
  searchMissionStartDateFrom = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO))
  searchMissionStartDateTo = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM))
  searchMissionEndDateFrom = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO))
  searchMissionEndDateTo = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO);

%>   
    <CENTER>
      <H2> View Missions </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID+"\""+
                  " value=\""+"\">");                   
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=4>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Mission Name</td>
        <td align=middle><input type='text' name='<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_NAME%>'></td>
        <td align=middle>Mission Status</td>
        <td align=middle>
            <select name='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_MISSION_STATUS%>'>
            <option value=''></option>
              <%
              for(int j=0;j<vecMissionStatusTypes.size();j++)
              {
                MissionStatusTypeModel missionStatusTypeModel = (MissionStatusTypeModel)vecMissionStatusTypes.get(j);
                String missionStatusTypeIdX = missionStatusTypeModel.getMissionStatusTypeId();
                String missionStatusTypeNameX = missionStatusTypeModel.getMissionStatusTypeName();
                %>
                <option value='<%=missionStatusTypeIdX%>'><%=missionStatusTypeNameX%></option>
                <%
              }
              %>
            </select>
        </td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Start Date From</td>
        <td align=middle><Script>drawCalender('<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_FROM%>',"*");</script></td>
        <td align=middle>Start Date To</td>
        <td align=middle><Script>drawCalender('<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_START_DATE_TO%>',"*");</script></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>End Date From</td>
        <td align=middle><Script>drawCalender('<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_FROM%>',"*");</script></td>
        <td align=middle>End Date To</td>
        <td align=middle><Script>drawCalender('<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_MISSION_END_DATE_TO%>',"*");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=4>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SEARCH_MISSION+"';"+
                  "HLPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','*','*','*','*');\">");          
        %>
        </td>
      </tr>
      </table>

      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <%
        for(int i=0;i<vecMissions.size();i++)
        {
          if(i==0)
          {
          %>
            <TR class=TableHeader>
              <TD>Mission ID</TD>
              <TD>Mission Name</TD>
              <TD>Description</TD>
              <TD>Start Date</TD>
              <TD>End Date</TD>
              <TD align='center'>Status</TD>
              <TD align='center'>Target Users</TD>
            </tr>    
          <%  
          }
          MissionModel missionModel = (MissionModel)vecMissions.get(i);
          String missionId = missionModel.getMissionId();
          String missionName = missionModel.getMissionName();
          String missionDescription = missionModel.getMissionDescription();
          if(missionDescription == null)missionDescription = "";
          Date missionStartDate = missionModel.getMissionStartDate();
          Date missionEndDate = missionModel.getMissionEndDate() ;
          String surveyId = missionModel.getSurveyId();
          Date creationDate = missionModel.getCreationDate();
          String missionStatusId = missionModel.getMissionStatusId();
          String missionStatusTypeId = missionModel.getMissionStatusTypeId();
          String missionStatusTypeName = missionModel.getMissionStatusTypeName(); 
          Timestamp missionStatusTimestamp = missionModel.getMissionStatusTimestamp();
          String userId = missionModel.getUserId();
        %>
        <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <TD><%=missionId%></TD>
          <TD><%=missionName%></TD>
          <TD><%=missionDescription%></TD>
          <TD><%=missionStartDate%></TD>
          <TD><%=missionEndDate%></TD>
          <TD align='center'><%=missionStatusTypeName%></TD>
          <TD align='center'>
          <%
          if(missionStatusTypeId.compareTo("2")==0)
          {
            out.print("<INPUT class=button type=button value=\" Target Users \" name=\"targetusers\" id=\"targetusers\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SELECT_MISSION_TARGET_USER+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_MISSION_ID+".value = '"+missionId+"';"+
                      "HLPform.submit();\">");
          }
          %>
          </TD>
        </tr>
        <%
        }
        %>
      </table>  

    <script>
    setSearchValues('<%=searchMissionName%>','<%=searchMissionStatusTypeId%>','<%=searchMissionStartDateFrom%>','<%=searchMissionStartDateTo%>','<%=searchMissionEndDateFrom%>','<%=searchMissionEndDateTo%>');
    </script>
</form>
</body>
</html>
