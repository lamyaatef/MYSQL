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
      <!--SCRIPT language=JavaScript src="../resources/js/tree.js" type=text/javascript></SCRIPT-->
    </head>
      <script type="text/javascript">
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

        function checkIfAnswerIsNumeric(inputName,inputValue)
        {
          if(!IsNumeric(inputValue))
          {
            alert('Answer must be number');
            eval("document.formSurveyDetails."+inputName+".value = ''");
          }
        }
        
        function changeAll(combobox,indexX) 
        {
        //alert(combobox.name);
          comboboxs=document.getElementsByTagName("SELECT");
          for (i=0;i<comboboxs.length;i++) 
          {
            if(comboboxs[i].name.match(combobox.name) == combobox.name && ! comboboxs[i].disabled)
            {
            //alert(comboboxs[i].name);
              var comboboxname = comboboxs[i].name;
              
              var comboboxnamearray = comboboxname.split("_");
              if(indexX == 1)
              {
                if(comboboxnamearray[2])
                {
                box = eval("document.AuthCall_"+comboboxnamearray[1]+".checkbox_"+comboboxnamearray[1]+"_"+comboboxnamearray[2]+"");
                box.checked = true;
                comboboxs[i].value = combobox.value;
                }
              }
              else if(indexX == 2)
              {
                if(comboboxnamearray[3])
                {
                box = eval("document.AuthCall_"+comboboxnamearray[2]+".checkbox_"+comboboxnamearray[2]+"_"+comboboxnamearray[3]+"");
                box.checked = true;
                comboboxs[i].value = combobox.value;
                }
              }
            }
          }
        }
        function Toggle(item) 
        {
          obj=document.getElementById(item);
          if (obj!=null) 
          {
            visible=(obj.style.display!="none")
            key=document.getElementById("x" + item);
            if (visible) 
            {
              obj.style.display="none";
              key.innerHTML="<img src='../resources/img/plus.gif' border='0'>";
            } 
            else 
            {
              obj.style.display="block";
              key.innerHTML="<img src='../resources/img/minus.gif' border='0'>";
            }
          }
        }
      </script>
  <body>
  <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formSurveyDetails" id="formSurveyDetails" method="post">  
<%


  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  Vector vecFilSurvey = new Vector();
  Vector vecFilGroupList = new Vector();
  HashMap hashMapFilGroupQuestionList = new HashMap();
  HashMap hashMapQuetionChoices = new HashMap();
  String errMsg = "";

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2) )
  vecFilSurvey = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2) ;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION) )  
  vecFilGroupList = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION) ;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  hashMapFilGroupQuestionList = (HashMap) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  if(objDataHashMap.containsKey(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES))
  hashMapQuetionChoices = (HashMap) objDataHashMap.get(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES);
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE) )
  errMsg = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE) ;

  if(errMsg.compareTo("")!=0)
  {
    out.println("<script>alert('"+errMsg+"');history.go(-1);</script>");
  }

  String filSurveyId = "";
  String filSurveyName = "";
  String filSurveyUserName = "";
  String filSurveyDate = "";
  String filSurveyDateYearFormat = "";
  String filSurveyCompleted = "";
  String filSurveyCompletedAnswer = "";
  for(int k=0;k<vecFilSurvey.size();k++)
  {
    FilSurveyModel filSurveyModel = (FilSurveyModel)vecFilSurvey.get(k);
    filSurveyId = filSurveyModel.getFilSurveyId();
    filSurveyName = filSurveyModel.getFilSurveyName();
    filSurveyUserName = filSurveyModel.getPersonFullName();
    filSurveyDate = filSurveyModel.getFilSurveyDate();
    filSurveyDateYearFormat = filSurveyDate.substring(0,10);
    filSurveyCompleted = filSurveyModel.getFilSurveyCompleted();
    if(filSurveyCompleted.compareTo("1")==0)
    {
      filSurveyCompletedAnswer = "Completed";
    }
    else if(filSurveyCompleted.compareTo("0")==0)
    {
      filSurveyCompletedAnswer = "Incomplete";
    }
  }
  String userId = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+userId+"\">");               

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SURVEY_ID+"\""+
                  " value=\""+filSurveyId+"\">");                  
%>  
    <CENTER>
      <H2> Filling Survey Groups and Questions</H2>
    </CENTER>

    <left>
     <h3>Choose Group for Filling</h3>
    </left>
<%
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center><font size=2>Survey Name</font></TD>");      
          out.println("<td nowrap align=center><font size=2>User</font></TD>");
          out.println("<td nowrap align=center><font size=2>Date</font></TD>");
          out.println("<td nowrap align=center><font size=2>Completed</font></TD>");
          out.println("</tr>");

          out.println("<TR>");
          out.println("<td nowrap align=center>"+filSurveyName+"</a></td>");
          out.println("<td nowrap align=center>"+filSurveyUserName+"</td>");
          out.println("<td nowrap>"+filSurveyDateYearFormat+"</td>");
          out.println("<td nowrap align=center>"+filSurveyCompletedAnswer+"</td>");
          out.print("</tr>");
          out.println("</TABLE>");
          out.println("<br>");
          
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            out.println("<TR class=TableHeader>");
            out.println("<td align=middle>Group Name</td>");
            out.println("<td align=middle>Weight</td>");
            out.println("<td align=middle>Value</td>");
            out.println("<td align=middle>Description</td>");
            out.println("</tr>"); 
        for(int i=0;i<vecFilGroupList.size();i++)
        {
        FilGroupModel filGroupModel = (FilGroupModel)vecFilGroupList.get(i);
        String filGroupId = filGroupModel.getFilGroupId();
        String filGroupName = filGroupModel.getFilGroupName();
        String filGroupWeight = filGroupModel.getFilGroupWeight();
        String filGroupStatus = filGroupModel.getFilGroupStatusName();
        String filGroupValue = filGroupModel.getFilGroupValue();
        String filGroupDescription = filGroupModel.getFilGroupDescription();
        if(filGroupDescription == null || filGroupDescription.compareTo("null")==0)filGroupDescription="";
        
        String sheetName = ""+i;
        
              out.println("<Tr>");
              out.println("<td>");               
              out.println("<A id=\"x"+sheetName +"\" href=\"javascript:Toggle(\'"+sheetName+"\');\">"+"<img src=\'../resources/img/plus.gif\' border='0'>"+"</A>");                               
              out.println("<b>"+filGroupName+"</b>");
              out.println("</td>");
              out.println("<td align=middle>");
              out.println(filGroupWeight);
              out.println("</td>");
              out.println("<td align=middle>");
              out.println(filGroupValue);
              out.println("</td>");              
              out.println("<td align=middle>");
              out.println(filGroupDescription);
              out.println("</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td colspan=7>");
              
              out.println("<div style=\"display:none\" id="+sheetName+" name="+sheetName+">");

              out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1 id=\"table_"+sheetName+"\" name=\"table_"+sheetName +"\">");

              out.println("<TR class=TableHeader>");
              out.println("<td align=middle>Question</td>");
              out.println("<td align=middle>Answer<br>");
              out.println("<td align=middle>Weight</td>");
              out.println("<td align=middle>Mandatory</td>");
              out.println("<td align=middle>Category</td>");
              out.println("</tr>");
              if(hashMapFilGroupQuestionList.containsKey(filGroupId))
              {
                Vector vecGroupQuestions = (Vector)hashMapFilGroupQuestionList.get(filGroupId);
                for(int j=0;j<vecGroupQuestions.size();j++)
                {
                  FilQuestionModel filQuestionModel = (FilQuestionModel)vecGroupQuestions.get(j);
                  String filQuestionId = filQuestionModel.getFilQuestionId();
                  String filQuestion = filQuestionModel.getFilQuestion();
                  String filQuestionWeight = filQuestionModel.getFilQuestionWeight();
                  String filQuestionType = filQuestionModel.getQuestionTypeName();
                  String filQuestionTypeId = filQuestionModel.getFilQuestionTypeId();
                  String filQuestionCategory = filQuestionModel.getQuestionCategoryName();
                  String filQuestionAnswer = filQuestionModel.getFilQuestionAnswer();
                  String filQuestionMandatory = filQuestionModel.getFilQuestionMandatory();
                  String strQuestionMandatory = "";      
                  if(filQuestionMandatory.compareTo("0")==0)
                  {
                    strQuestionMandatory = "NO";
                  }
                  else if(filQuestionMandatory.compareTo("1")==0)
                  {
                    strQuestionMandatory = "YES";
                  }
                  String questionId = filQuestionModel.getQuestionId();
                  
                  out.println("<TR>");
                  out.println("<td align=middle>"+filQuestion+"</td>");
                  out.println("<td align=middle>");
                  if(filQuestionTypeId.compareTo("1") == 0)
                  {
                    out.println("<select name='"+SurveyInterfaceKey.INPUT_QUESTION_ANSWER+"_"+filQuestionId+"' id='"+SurveyInterfaceKey.INPUT_QUESTION_ANSWER+"_"+filQuestionId+"'>");
                    out.println("<option value='0'></option>");
                    if(hashMapQuetionChoices.containsKey(questionId))
                    {
                      Vector vecChoiceList = (Vector)hashMapQuetionChoices.get(questionId);
                      for(int c=0;c<vecChoiceList.size();c++)
                      {
                        ChoiceModel choiceModel = (ChoiceModel)vecChoiceList.get(c);
                        String choice = choiceModel.getChoice();
                        String choiceId = choiceModel.getChoiceId();
                        String choiceOrder = choiceModel.getChoiceOrder();
                        String choiceValue = choiceModel.getChoiceValue();
                        String choiceSelected = ""; 
                        if(filQuestionAnswer.compareTo(choiceId)==0)
                        {
                            choiceSelected = "selected";
                        }
                        out.println("<option value='"+choiceId+"' "+choiceSelected+">"+choice+"</option>");
                      }
                    }
                    out.println("</select></td>");
                  }
                  else if(filQuestionTypeId.compareTo("2") == 0)
                  {
                      if(filQuestionAnswer==null)filQuestionAnswer="";   
                      out.println("<input type='text' name='"+SurveyInterfaceKey.INPUT_QUESTION_ANSWER+"_"+filQuestionId+"' id='"+SurveyInterfaceKey.INPUT_QUESTION_ANSWER+"_"+filQuestionId+"' value='"+filQuestionAnswer+"' onchange='checkIfAnswerIsNumeric(this.name,this.value);'>");
                  }
                  else if(filQuestionTypeId.compareTo("3") == 0)
                  {
                      out.println("<textarea name='"+SurveyInterfaceKey.INPUT_QUESTION_ANSWER+"_"+filQuestionId+"' id='"+SurveyInterfaceKey.INPUT_QUESTION_ANSWER+"_"+filQuestionId+"'>"+filQuestionAnswer+"</textarea>");
                  }
                  out.println("<td align=middle>"+filQuestionWeight+"</td>");
                  out.println("<td align=middle>"+strQuestionMandatory+"</td>");
                  out.println("<td align=middle>"+filQuestionCategory+"</td>");
                  out.println("</tr>");
                }
              }
              out.println("</table>");
        }
        out.println("</table>");
%>
<br><br>
      <center>
      <%
        if(filSurveyCompleted.compareTo("0")==0)
        {
        out.println("<INPUT class=button type=button value=\" Update \" name=\"update\" id=\"update\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_FILLING_UPDATE_QUESTION_ANSWERS+"';"+
                      "formSurveyDetails.submit();\">");

        out.println("<INPUT class=button type=button value=\" Complete \" name=\"complete\" id=\"complete\" onclick=\"document.formSurveyDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SurveyInterfaceKey.ACTION_FILLING_COMPLETE_FIL_SURVEY+"';"+
                      "formSurveyDetails.submit();\">");

        }
        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");  
        
     %>
     
      </center>  
      
  </form>    
  </body>
</html>