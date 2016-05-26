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
      var sumquestionweight = 0;
      for(var i = 0;i<statusList.length;i++)
      {
        var selectname = statusList[i].name;
        var questionnextstatus = statusList[i].value;
        var questionid = selectname.substring(<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_STATUS.length()+1%>,selectname.length);
        var questionweight = parseInt(eval("document.formSurveyDetails.<%=SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_WEIGHT%>_"+questionid+".value"));
        if(questionnextstatus == 1)
        {
          sumquestionweight = sumquestionweight + questionweight;
        }
      }
      if(sumquestionweight > 100)
      {
        alert("Question weights for this group will exceed 100");
        return;
      }
      var orderList = document.formSurveyDetails.getElementsByTagName("input");
      for(var i = 0;i<orderList.length;i++)
      {
        var ordervalue = orderList[i].value;
        if(orderList[i].type == "text")
        {
          if(!IsNumeric(ordervalue) || ordervalue.length==0)
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
                  
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  Vector vecGroupQuestions = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  Vector vecQuestionStatusList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2) ;

  if(objDataHashMap.containsKey(SurveyInterfaceKey.ERROR_SUM_OF_QUESTION_WEIGHTS))
  {
    String rejectedQuestionWeights = (String)objDataHashMap.get (SurveyInterfaceKey.ERROR_SUM_OF_QUESTION_WEIGHTS) ;
    out.println("<script>alert('Error in the question weights');</script>");
  }
  
  Vector vecGroupDetails = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION) ;

  String groupId = "";
  String groupName = "";
  String surveyId = "";
  String groupWeight = "";
  String groupOrder = "";
  String groupReference = "";
  String groupStatusId = "";
  String groupStatusName = "";
  String groupDescription = "";

  GroupModel groupModel = (GroupModel) vecGroupDetails.get(0);            
  groupId = groupModel.getGroupId();
  groupName = groupModel.getGroupName();
  surveyId = groupModel.getSurveyId();
  groupWeight = groupModel.getGroupWeight();
  groupOrder = groupModel.getGroupOrder();
  groupReference = groupModel.getGroupReference();
  groupStatusId = groupModel.getGroupStatusId();
  groupStatusName = groupModel.getGroupStatusName();
  groupDescription = groupModel.getGroupDescription();

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+surveyId+"\">");   

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+groupId+"\">");  

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID+"\""+
                  " value=\""+"\">");                 
%>  
    <CENTER>
      <H2> Questions List</H2>
    </CENTER>
    <h3>Group Name : <%=groupName%></h3>
    <br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="25%" nowrap align=middle>Question</td>
        <td width="25%" nowrap align=middle>Weight</td>
        <TD width="10%" noWrap align=middle>Type</TD>
        <TD width="10%" noWrap align=middle>Mandatory</TD>
        <TD width="10%" noWrap align=middle>Order</TD>
        <TD width="10%" noWrap align=middle>Status</TD>
        <TD width="10%" noWrap align=middle>Edit</TD>
      </TR>
<%
      String questionId = "";
      String question = "";
      String questionStatusId = "";
      String questionStatusName = "";
      String questionTypeId = "";
      String questionTypeName = "";
      String questionCategoryId = "";
      String questionCategoryName = "";
      String questionGroupId = "";
      String questionWeight = "";
      String questionOrder = "";
      String questionMandatory = "";

      String statusId = "";
      String statusName = "";

      int sumQuestionWeight = 0;
      for (int i=0; i<vecGroupQuestions.size(); i++)
      {
      QuestionModel questionModel = (QuestionModel) vecGroupQuestions.get(i);
      questionId = questionModel.getQuestionId();
      question = questionModel.getQuestion();
      questionStatusId = questionModel.getQuestionStatusId();
      questionStatusName = questionModel.getQuestionStatusName();
      questionTypeId = questionModel.getQuestionTypeId();
      questionTypeName = questionModel.getQuestionTypeName();
      questionCategoryId = questionModel.getQuestionCategoryId();
      questionCategoryName = questionModel.getQuestionCategoryName();
      questionGroupId = questionModel.getGroupId();
      questionWeight = questionModel.getQuestionWeight();
      int intQuestionWeight = Integer.parseInt(questionWeight);
      if(questionStatusId.compareTo("1")==0)
      {
        sumQuestionWeight += intQuestionWeight;
      }
      questionOrder = questionModel.getQuestionOrder();
      questionMandatory = questionModel.getQuestionMandatory();   
%>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=question%></td>
        <TD width="10%" noWrap align=middle><%=questionWeight%></TD>
        <td width="10%" nowrap align=middle><%=questionTypeName%></td>
        <td width="10%" nowrap align=middle><%=questionMandatory%></td>
        <td width="10%" nowrap align=middle><input type="text" name="<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER%>_<%=questionId%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER%>_<%=questionId%>" value="<%=questionOrder%>"></td>
        <TD width="10%" noWrap align=middle>
            <select name="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_STATUS%><%=questionStatusId%><%=questionId%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_STATUS%><%=questionStatusId%><%=questionId%>">
              <%
              for (int k=0; k<vecQuestionStatusList.size(); k++)
              {
                  QuestionStatusModel questionStatusModel = (QuestionStatusModel) vecQuestionStatusList.get(k);
                  statusId = questionStatusModel.getQuestionStatusId();
                  statusName = questionStatusModel.getQuestionStatusName();
                  String selectedStatus = "";
                  if(statusId.equals(questionStatusId))
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
        out.println("<TD width=\"10%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"editgroup\" id=\"editgroup\" value=\"Edit\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_EDIT_QUESTION+"';"+
                      "document.formSurveyDetails."+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID+".value = '"+questionId+"';formSurveyDetails.submit();\"></TD>");
      %>
      </TR>
      <%
      out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_WEIGHT+"_"+questionId+"\" id=\""+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_WEIGHT+"_"+questionId+"\""+
                  " value=\""+questionWeight+"\">");
      }
      %>
    </table>

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Question \" name=\"addnew\" id=\"addnew\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_CREATE_NEW_QUESTION+"';"+
                  "formSurveyDetails.submit();\">");
        out.print("<INPUT class=button type=button value=\" Update \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_UPDATE_QUESTION_STATUS+"';"+
                  "checkBeforeUpdate();\">");

        out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS+"\""+
                  " value=\""+sumQuestionWeight+"\">");           
      %>
      </center>
    </form>
  </body>
</html>
