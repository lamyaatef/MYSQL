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
  
    function checkBeforeUpdate()
    {
      var statusList = document.formSurveyDetails.getElementsByTagName("select");
      var sumgroupweight = 0;
      for(var i = 0;i<statusList.length;i++)
      {
        var selectname = statusList[i].name;
        var groupnextstatus = statusList[i].value;
        var groupid = selectname.substring(<%=SurveyInterfaceKey.INPUT_SELECT_GROUP_STATUS.length()+1%>,selectname.length);
        var groupweight = parseInt(eval("document.formSurveyDetails.<%=SurveyInterfaceKey.INPUT_HIDDEN_GROUP_WEIGHT%>_"+groupid+".value"));
        if(groupnextstatus == 1)
        {
          sumgroupweight = sumgroupweight + groupweight;
        }
      }
      if(sumgroupweight > 100)
      {
        alert("Group weights for this group will exceed 100");
        return;
      }
      var orderList = document.formSurveyDetails.getElementsByTagName("input");
      for(var i = 0;i<orderList.length;i++)
      {
        var ordervalue = orderList[i].value;
        if(orderList[i].type == "text")
        {
          if(!IsNumeric(ordervalue) || ordervalue.length ==0)
          {
            alert("Group Order is not a number");
            return;
          }
        }
      }
        document.formSurveyDetails.submit();
    }
  </script>  
<form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formSurveyDetails" id="formSurveyDetails" method="post">  
<%

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+"\">");   
                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  Vector vecSurveyGroups = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  Vector vecGroupStatusList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2) ;

  if(objDataHashMap.containsKey(SurveyInterfaceKey.ERROR_SUM_OF_GROUP_WEIGHTS))
  {
    String rejectedGroupWeights = (String)objDataHashMap.get (SurveyInterfaceKey.ERROR_SUM_OF_GROUP_WEIGHTS) ;
    out.println("<script>alert('Error in the group weights');</script>");
  }
  
  Vector vecSurveyDetails = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;

  SurveyModel surveyModel = (SurveyModel) vecSurveyDetails.get(0);            
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

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+surveyId+"\">");   
%>  
    <CENTER>
      <H2> Groups List</H2>
    </CENTER>
    <h3>Survey Name : <%=surveyName%></h3>
    <br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="25%" nowrap align=middle>Group Name</td>
        <td width="25%" nowrap align=middle>Weight</td>
        <TD width="10%" noWrap align=middle>Reference</TD>
        <TD width="10%" noWrap align=middle>Description</TD>
        <TD width="10%" noWrap align=middle>Order</TD>
        <TD width="10%" noWrap align=middle>Status</TD>
        <TD width="10%" noWrap align=middle>Questions</TD>
        <TD width="10%" noWrap align=middle>Edit</TD>
      </TR>
<%
      String groupId = "";
      String groupName = "";
      String groupSurveyId = "";
      String groupWeight = "";
      String groupOrder = "";
      String groupReference = "";
      String groupStatusId = "";
      String groupStatusName = "";
      String groupDescription = "";

      String statusId = "";
      String statusName = "";

      int sumGroupWeight = 0;
      for (int i=0; i<vecSurveyGroups.size(); i++)
      {
      GroupModel groupModel = (GroupModel) vecSurveyGroups.get(i);
      groupId = groupModel.getGroupId();
      groupName = groupModel.getGroupName();
      groupSurveyId = groupModel.getSurveyId();
      groupStatusId = groupModel.getGroupStatusId();
      groupWeight = groupModel.getGroupWeight();
      int intGroupWeight = Integer.parseInt(groupWeight);
      if(groupStatusId.compareTo("1")==0)
      {
      sumGroupWeight += intGroupWeight;
      }
      groupOrder = groupModel.getGroupOrder(); 
      groupReference = groupModel.getGroupReference();
      groupStatusName = groupModel.getGroupStatusName();
      groupDescription = groupModel.getGroupDescription();      
      if(groupDescription == null)groupDescription=""; 
%>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=groupName%></td>
        <TD width="10%" noWrap align=middle><%=groupWeight%></TD>
        <TD width="10%" noWrap align=middle><%=groupReference%></TD>
        <td width="20%" nowrap align=middle><%=groupDescription%></td>
        <td width="10%" nowrap align=middle><input type="text" name="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER%>_<%=groupId%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER%>_<%=groupId%>" value="<%=groupOrder%>"></td>
        <TD width="10%" noWrap align=middle>
            <select name="<%=SurveyInterfaceKey.INPUT_SELECT_GROUP_STATUS%><%=groupStatusId%><%=groupId%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_GROUP_STATUS%><%=groupStatusId%><%=groupId%>">
              <%
              for (int k=0; k<vecGroupStatusList.size(); k++)
              {
                  GroupStatusModel groupStatusModel = (GroupStatusModel) vecGroupStatusList.get(k);
                  statusId = groupStatusModel.getGroupStatusId();
                  statusName = groupStatusModel.getGroupStatusName();
                  String selectedStatus = "";
                  if(statusId.equals(groupStatusId))
                  {
                      selectedStatus = "selected";
                  }
              %>
              <option value="<%=statusId%>" <%=selectedStatus%>><%=statusName%></option>
              <%
              }
              %>
            </select>
        </TD>
        <%
        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"viewquestions\" id=\"viewquestions\" value=\"Questions\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_VIEW_ALL_QUESTIONS+"';"+
                      "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value = '"+groupId+"';formSurveyDetails.submit();\"></TD>");

        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"editgroup\" id=\"editgroup\" value=\"Edit\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_EDIT_GROUP+"';"+
                      "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value = '"+groupId+"';formSurveyDetails.submit();\"></TD>");
      %>
      </TR>
      <%
      out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_WEIGHT+"_"+groupId+"\""+
                  " value=\""+groupWeight+"\">");
      }
      %>
    </table>

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Group \" name=\"addnew\" id=\"addnew\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_CREATE_NEW_GROUP+"';"+
                  "formSurveyDetails.submit();\">");
        out.print("<INPUT class=button type=button value=\" Update \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_UPDATE_GROUPS_STATUS+"';"+
                  "checkBeforeUpdate();\">");

        out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS+"\""+
                  " value=\""+sumGroupWeight+"\">");            
      %>
      </center>
    </form>
  </body>
</html>
