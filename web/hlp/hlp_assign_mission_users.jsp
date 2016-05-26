<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.sql.Timestamp" 
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.core.system.hlp.mission.model.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.persons.model.*"
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
      HLPform.submit();
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
  Vector usersVector = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  MissionModel missionModel = (MissionModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector vecAssignedUsers = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  
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
      <H2> Assign Mission Users </H2>
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
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
        <TR class=TableHeader>
          <TD align='center'>User Full Name </TD>
          <TD align='center'>User E-mail </TD>
          <TD align='center' width="10%"></td>
        </tr>
        <%
        for(int i=0;i<usersVector.size();i++)
        {
        UserDTO userDTO = (UserDTO)usersVector.get(i);
        UserModel userModel = (UserModel)userDTO.getUserModel();
        PersonModel personModel = userModel.getPersonModel();
        int personId = personModel.getPersonID();
        String personEmail = personModel.getPersonEMail();
        String personFullName = personModel.getPersonFullName();
        String checkedUser = "";
          for(int j=0;j<vecAssignedUsers.size();j++)
          {
            String strAssingedUser = (String)vecAssignedUsers.get(j);
            if(strAssingedUser.compareTo(personId+"") == 0)
            {
              checkedUser = "checked";
            }
          }
        %>
        <TR>
          <TD align='center'><%=personFullName%></TD>
          <TD align='center'><%=personEmail%></TD>
          <TD align='center' width="10%">
            <input type='checkbox' name='<%=HLPInterfaceKey.INPUT_CHECKBOX_MISSION_USER_ID%>_<%=personId%>' id='<%=HLPInterfaceKey.INPUT_CHECKBOX_MISSION_USER_ID%>_<%=personId%>' <%=checkedUser%>>
          </td>
        </tr>
        <%
        }
        %>
       </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_MISSION_USERS+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
      %>
      </center>
       
</form>
</body>
</html>
