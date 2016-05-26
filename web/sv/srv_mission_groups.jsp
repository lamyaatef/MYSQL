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

  Vector vecMissionGroupList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  Vector vecMissionGroupStatusList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_MESSAGE) ;
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_MISSION_GROUP_ID+"\""+
                  " value=\""+"\">");                
%>  
    <CENTER>
      <H2> Mission Group List</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="25%" nowrap align=middle>Mission Group Name</td>
        <TD width="10%" noWrap align=middle>Status</TD>
        <TD width="10%" noWrap align=middle>Edit</TD>
      </TR>
      <%
        String missionGroupId = "";
        String missionGroupName = "";
        String missionGroupStatusId = "";
        String missionGroupStatusName = "";
        
        for (int i=0; i<vecMissionGroupList.size(); i++)
        {
            MissionGroupModel missionGroupModel = (MissionGroupModel) vecMissionGroupList.get(i);            
            missionGroupId = missionGroupModel.getMissionGroupId();
            missionGroupName = missionGroupModel.getMissionGroupName();
            missionGroupStatusId = missionGroupModel.getMissionGroupStatusId();
            missionGroupStatusName = missionGroupModel.getMissionGroupStatusName();
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=missionGroupName%></td>
        <TD width="10%" noWrap align=middle>
            <select name="<%=SurveyInterfaceKey.INPUT_SELECT_MISSION_GROUP_STATUS%>_<%=missionGroupId%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_MISSION_GROUP_STATUS%>_<%=missionGroupId%>">
              <%
              for (int k=0; k<vecMissionGroupStatusList.size(); k++)
              {
                  MissionGroupStatusModel missionGroupStatusModel = (MissionGroupStatusModel) vecMissionGroupStatusList.get(k);
                  String missionGroupStatusIdX = missionGroupStatusModel.getMissionGroupStatusId();
                  String missionGroupStatusNameX = missionGroupStatusModel.getMissionGroupStatusName();
                  String selectedStatus = "";
                  if(missionGroupStatusId.compareTo(missionGroupStatusIdX) == 0)
                  {
                      selectedStatus = "selected";
                  }
              %>
              <option value="<%=missionGroupStatusIdX%>" <%=selectedStatus%>><%=missionGroupStatusNameX%></option>
              <%
              }
              %>
            </select></TD>
        <%    
        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"\" id=\"\" value=\"Edit\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_EDIT_MISSION_GROUP+"';"+
                    "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_MISSION_GROUP_ID+".value = '"+missionGroupId+"';formSurveyDetails.submit();\"></TD>");
        %>  
      </TR>
      <%
      }
      %>
    </table>

    <br><br>
      <center>
      <%
        out.println("<INPUT class=button type=button value=\" Add New Mission Group \" name=\"addnew\" id=\"addnew\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_CREATE_NEW_MISSION_GROUP+"';"+
                      "formSurveyDetails.submit();\">");
        out.println("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_UPDATE_MISSION_GROUP_STATUS+"';"+
                      "formSurveyDetails.submit();\">");
      %>
      </center>
      
  </form>  
  </body>
</html>
