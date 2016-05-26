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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<body>
<Center><h2> POS Group Questions</h2></center>
<script>

</script>
<form name="SRVform" action="" method="POST">
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  Vector POSQuestion = (Vector)objDataHashMap.get(SurveyInterfaceKey.VECTOR_POS_QUESTION);
  Vector POSGroupQuestion = (Vector)objDataHashMap.get(SurveyInterfaceKey.VECTOR_POS_GROUP_QUESTION);  
  String groupID = (String)objDataHashMap.get(SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID);
  String error = (String)objDataHashMap.get(SurveyInterfaceKey.ERROR_SUM_OF_QUESTION_WEIGHTS);
  if(error!=null)
    out.println("<script>alert(\""+error+"\");</script>");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
    out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+groupID+"\">");
                  
%>                  

<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
 
  <TR>
    <TD class="TableHeader" align="center"> Question</TD>
    <TD class="TableHeader" align="center"> Question Weight</TD>
    <TD class="TableHeader" align="center"> Question Order</TD>
    <TD class="TableHeader" align="center"> Question Mandatory</TD>    
        <TD class="TableHeader" align="center"> Select Question </TD>    
  </TR>
  <%
  if(POSQuestion != null)
  {
  for(int i = 0 ; i < POSQuestion.size() ; i ++)
  {
    QuestionModel questionModel = (QuestionModel) POSQuestion.get(i);
    String QuestionWeight = "";
    String QuestionOrder = "";
    String QuestionMandatory = "";
    String disabled = "DISABLED";
    String Checked = "";
     String state = "new";
    for(int j = 0 ; j < POSGroupQuestion.size() ; j++)
    {
      QuestionModel groupQuestion = (QuestionModel)POSGroupQuestion.get(j);        
      if(groupQuestion.getQuestionId().equals(questionModel.getQuestionId()))
      {
        QuestionWeight = groupQuestion.getQuestionWeight();
        QuestionOrder = groupQuestion.getQuestionOrder();
        if(groupQuestion.getQuestionMandatory().equals("1")) QuestionMandatory = "CHECKED";
        Checked = "CHECKED";
        disabled = "";
        state ="old";
        break;
      }
      }
      String questionID = questionModel.getQuestionId();
      out.println("<TR>");
        out.println("<TD class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">"+questionModel.getQuestion()+" </TD>");
        out.println("<TD class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\"> <input type=\"text\" name=\""+SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT+
                    "_"+questionID+"\" value=\""+QuestionWeight+"\" "+disabled+"></TD>");
        out.println("<TD class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\"> <input type=\"text\" name=\""+SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER+
                    "_"+questionID+"\" value=\""+QuestionOrder+"\" "+disabled+"></TD>");
        out.println("<TD class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\"> <input type=\"checkbox\" name=\""+SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY+
                    "_"+questionID+"\" "+QuestionMandatory+" "+disabled+"></TD>    ");
        out.println("<TD class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\"> <input type=\"checkbox\" name=\""+SurveyInterfaceKey.INPUT_CHECK_BOX_SELECTED_QUESTION+
                    "_"+questionID+"\" "+Checked+" onclick=\""+
                    "if(document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER+"_"+questionID+".disabled==true){"+
                    "document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT+"_"+questionID+".disabled=false;"+
                    "document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER+"_"+questionID+".disabled=false;"+
                    "document.SRVform."+SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY+"_"+questionID+".disabled=false;}"+                    
                    "else{"+
                    "document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT+"_"+questionID+".disabled=true;"+
                    "document.SRVform."+SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER+"_"+questionID+".disabled=true;"+
                    "document.SRVform."+SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY+"_"+questionID+".disabled=true;}"+                    
                    "\"></TD>");   
        out.println("<input type='hidden' name='"+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID+"_"+questionID+"' value='"+questionID+"'>"); 
        out.println("<input type='hidden' name='state_"+questionID+"' value='"+state+"'>");         
      out.println("</TR>");
    
    }
  }
  %>
  <TR>
    <TD colspan="5" align="center">
    <%
    out.println("<input type=\"button\" name=\"newQuestion\" class=\"button\" value=\"Save Group Questions\" onclick=\""+
                "document.SRVform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SurveyInterfaceKey.ACTION_SRV_SAVE_GROUP_QUESTION+"';"+
                "SRVform.submit();\"></TD>");
    %>
  </TR>

</table>
</form>
</body>

</html>
