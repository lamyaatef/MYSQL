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

  
  function checkBeforeSubmit()
  {
    var sumOfGroupWeights = parseInt(document.formDataView.<%=SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS%>.value);
    var groupWeight = parseInt(document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT%>.value);
    var sumWillBe = sumOfGroupWeights + groupWeight;
    if(sumWillBe > 100)
    {
      alert("Group weights for this survey will exceed 100");
      return;
    }

    var groupName = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_NAME%>.value;
    var groupWeightValue = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT%>.value;
    var groupOrderValue = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER%>.value;
    var groupReference = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_REFERENCE%>.value;

    if(groupName.length!=0)
    {
      if(IsNumeric(groupWeightValue) && groupWeightValue.length!=0)
      {
        if(IsNumeric(groupOrderValue) && groupOrderValue.length!=0)
        { 
          if(IsNumeric(groupReference) && groupReference.length!=0)
          {  
           document.formDataView.submit();
          }
          else
          {
            alert("Group Reference is not a number");
          }
        }
        else
        {
          alert("Group Order is not a number");
        }
      }
      else
      {
        alert("Group Weight is not a number");
      }
    }
    else
    {
      alert("Group name can not be empty.");
    }
  }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strSurveyId = (String) objDataHashMap.get (SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID) ;
  if(strSurveyId == null)
    strSurveyId = "";
  String strSumGroupWeight = (String) objDataHashMap.get (SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS) ;
  String strSurveyType = (String) objDataHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID);
  int intSumGroupWeight = 0;
  if(strSumGroupWeight!=null)
    intSumGroupWeight = Integer.parseInt(strSumGroupWeight);
  Vector vecGroupUpdated = new Vector();

  String groupId = "";
  String groupName = "";
  String surveyId = "";
  String groupWeight = "";
  String groupOrder = "";
  String groupReference = "";
  String groupStatusId = "";
  String groupStatusName = "";
  String groupDescription = "";

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    vecGroupUpdated = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
    GroupModel groupModel = (GroupModel)vecGroupUpdated.get(0);
    groupId = groupModel.getGroupId();
    groupName = groupModel.getGroupName();
    surveyId = groupModel.getSurveyId();
    groupStatusId = groupModel.getGroupStatusId();
    groupWeight = groupModel.getGroupWeight();
    int intgroupWeight = Integer.parseInt(groupWeight);
    if(groupStatusId.compareTo("1")==0)
    {
    intSumGroupWeight = intSumGroupWeight - intgroupWeight;
    }
    else
    {
    intSumGroupWeight = 0;  
    }
    groupOrder = groupModel.getGroupOrder();
    groupReference = groupModel.getGroupReference();
    groupStatusName = groupModel.getGroupStatusName();
    groupDescription = groupModel.getGroupDescription();
    if(groupDescription == null)groupDescription="";
  }

  String strGroupAction = (String) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ACTION) ;


  String strGroupStatus = "";
  String pageTitle = "";
  String GroupAction = "";
  if(strGroupAction.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_GROUP))
  {
      strGroupStatus = "1";
      pageTitle = " Create New Group";
      GroupAction = SurveyInterfaceKey.ACTION_SAVE_GROUP;
  }
  else if(strGroupAction.equals(SurveyInterfaceKey.ACTION_EDIT_GROUP))
  {
      strGroupStatus = groupStatusId;
      pageTitle = " Edit Group";
      GroupAction = SurveyInterfaceKey.ACTION_UPDATE_GROUP;
  }
  else if(strGroupAction.equals(  SurveyInterfaceKey.ACTION_SRV_CREATE_NEW_POS_GROUP))
  {
    strGroupStatus = "1";
    pageTitle = "Create New POS Group";
    GroupAction = SurveyInterfaceKey.ACTION_SRV_SAVE_POS_GROUP;
    }

  
%>  
    <CENTER>
      <H2> <%=pageTitle%></H2>
    </CENTER>
<form name='formDataView' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+groupId+"\">"); 

    out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                    " value=\""+strSurveyId+"\">");                

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_STATUS_ID+"\""+
                  " value=\""+strGroupStatus+"\">");

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS+"\""+
                  " value=\""+intSumGroupWeight+"\">");                 

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_TYPE_ID+"\""+
                  " value=\""+strSurveyType+"\">");                 
                  
%>
      <TABLE border=0 align='center' width='100%'>
        <TR class=TableTextNote>
          <TD width=\"20%\">Group&nbsp;Name *</TD>
          <TD colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_NAME%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_NAME%>" value="<%=groupName%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Group&nbsp;Weight *</TD>
          <TD colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_WEIGHT%>" value="<%=groupWeight%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Group&nbsp;Order *</TD>
          <TD colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_ORDER%>" value="<%=groupOrder%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Group&nbsp;Reference *</TD>
          <TD colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_REFERENCE%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_GROUP_REFERENCE%>" value="<%=groupReference%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Group&nbsp;Description *</TD>
          <TD colSpan=4><TEXTAREA style="WIDTH: 451px; HEIGHT: 84px" name="<%=SurveyInterfaceKey.INPUT_TEXTAREA_GROUP_DESCRIPTION%>" id="<%=SurveyInterfaceKey.INPUT_TEXTAREA_GROUP_DESCRIPTION%>" rows=4 cols=47><%=groupDescription%></TEXTAREA></td>
        </tr>
      </table>
      <br><br>
      <center>
      <%
        out.println("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.formDataView."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+GroupAction+"';"+
                    "checkBeforeSubmit();\">");
      %>
      </center>
</form>

  </body>
</html>
