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
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      
    </head>
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
    var sumOfGroupWeights = parseInt(document.formDataView.<%=SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS%>.value);
    var questionWeight = parseInt(document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT%>.value);
    var sumWillBe = sumOfGroupWeights + questionWeight;
    if(sumWillBe > 100)
    {
      alert("Question weights for this group will exceed 100");
      return;
    }

    var questionWeightValue = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT%>.value;
    var questionOrderValue = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER%>.value;
    var questionValue = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXTAREA_QUESTION%>.value;

    if(questionValue.length != 0)
    {
      if(IsNumeric(questionWeightValue) && questionWeightValue.length!=0)
      {
        if(IsNumeric(questionOrderValue) && questionOrderValue.length!=0)
        {  
           document.formDataView.submit();
        }
        else
        {
          alert("Question Order is not a number.");
        }
      }
      else
      {
        alert("Question Weight is not a number.");
      }
    }
    else
    {
      alert("Question can not be empty.");
    }
  }  
  function data_view_RowSet_add(argCurrentValue,argCurrentName,argCounterName,argControlName,argArrayDataView)
  {
  //Number("+ argControlName + ".RowSet.getRowCount())
      ix = eval("document.formDataView."+argCounterName+".value = Number(document.formDataView."+argCounterName+".value) + 1;");
      ix = ix-1;
      eval(argControlName+".RowSet.add()");
      argCurrentName = argCurrentName+","+argCurrentName;
      for (var i = 0; i < eval(argArrayDataView+".length"); i++)
      {
         if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
         {
            eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                               
         }
      }
      var curpos = ix+1;
      //var strpos =  "user_defined_data_view__R"+curpos+"__C5"; 
      //document.getElementById(strpos).value = curpos;
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
      if (i==true){
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",3).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",3).getValue()")==true){
            eval(argObject+".getCell("+i+",3).setValue()") == false; 
          }//end if
        }//end for
      }//end else
    }
    
    function move_up(argObject)
    {
          var i = argObject.charAt(30);
          if(i==1)
          {
              var k = argObject.charAt(31);
              if(k=="_")
              {
                return;
              }
              else
              {
                i = i+k;
              }
          }
          var j = i-1;
            var tempfiledname = document.getElementById("user_defined_data_view__R"+i+"__C1").value;
            var tempfileddesc = document.getElementById("user_defined_data_view__R"+i+"__C2").value;
            var tempfiledsqlcash = document.getElementById("user_defined_data_view__R"+i+"__C4").value;
            var tempfiledid = document.getElementById("user_defined_data_view__R"+i+"__C5").value;
            while(!document.getElementById("user_defined_data_view__R"+j+"__C1"))
            {
              j=j-1;
              if(j==0)
              {
              return;
              }
            }
            var tempfilednameabove = document.getElementById("user_defined_data_view__R"+j+"__C1").value;
            var tempfileddescabove = document.getElementById("user_defined_data_view__R"+j+"__C2").value;
            var tempfiledsqlcashabove = document.getElementById("user_defined_data_view__R"+j+"__C4").value;
            var tempfiledidabove = document.getElementById("user_defined_data_view__R"+j+"__C5").value;

            document.getElementById("user_defined_data_view__R"+i+"__C1").value = tempfilednameabove;
            document.getElementById("user_defined_data_view__R"+i+"__C2").value = tempfileddescabove;
            document.getElementById("user_defined_data_view__R"+i+"__C4").value = tempfiledsqlcashabove;
            document.getElementById("user_defined_data_view__R"+i+"__C5").value = tempfiledidabove;

            document.getElementById("user_defined_data_view__R"+j+"__C1").value = tempfiledname;
            document.getElementById("user_defined_data_view__R"+j+"__C2").value = tempfileddesc;
            document.getElementById("user_defined_data_view__R"+j+"__C4").value = tempfiledsqlcash;
            document.getElementById("user_defined_data_view__R"+j+"__C5").value = tempfiledid;
    }

    function move_down(argObject)
    {
            var maxpos = document.getElementById("user_defined_data_view_count").value;
            var i = argObject.charAt(30);
            if(i==1)
            {
                var k = argObject.charAt(31);
                if(k=="_")
                {
                  return;
                }
                else
                {
                  i = i+k;
                }
            }
            if(i==maxpos)
            {
                return;
            }
            var j = (i*1)+1;
            var tempfiledname = document.getElementById("user_defined_data_view__R"+i+"__C1").value;
            var tempfileddesc = document.getElementById("user_defined_data_view__R"+i+"__C2").value;
            var tempfiledsqlcash = document.getElementById("user_defined_data_view__R"+i+"__C4").value;
            var tempfiledid = document.getElementById("user_defined_data_view__R"+i+"__C5").value;

            while(!document.getElementById("user_defined_data_view__R"+j+"__C1"))
            {
              j=j+1;
              if(j==maxpos || j>=maxpos)
              {
              return;
              }
            }
            
            var tempfilednameabove = document.getElementById("user_defined_data_view__R"+j+"__C1").value;
            var tempfileddescabove = document.getElementById("user_defined_data_view__R"+j+"__C2").value;
            var tempfiledsqlcashabove = document.getElementById("user_defined_data_view__R"+j+"__C4").value;
            var tempfiledidabove = document.getElementById("user_defined_data_view__R"+j+"__C5").value;

            document.getElementById("user_defined_data_view__R"+i+"__C1").value = tempfilednameabove;
            document.getElementById("user_defined_data_view__R"+i+"__C2").value = tempfileddescabove;
            document.getElementById("user_defined_data_view__R"+i+"__C4").value = tempfiledsqlcashabove;
            document.getElementById("user_defined_data_view__R"+i+"__C5").value = tempfiledidabove;

            document.getElementById("user_defined_data_view__R"+j+"__C1").value = tempfiledname;
            document.getElementById("user_defined_data_view__R"+j+"__C2").value = tempfileddesc;
            document.getElementById("user_defined_data_view__R"+j+"__C4").value = tempfiledsqlcash;
            document.getElementById("user_defined_data_view__R"+j+"__C5").value = tempfiledid;
    }    
    var UserDefinedDataViewArray =new Array();
</SCRIPT>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strGroupId = (String) objDataHashMap.get (SurveyInterfaceKey.INPUT_HIDDEN_GROUP_ID) ;
  Vector vecQuestionTypeList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION ) ;
  Vector vecQuestionCategoryList = (Vector) objDataHashMap.get (InterfaceKey.HASHMAP_KEY_COLLECTION ) ;

  String strSumQuestionWeight = (String) objDataHashMap.get (SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS) ;
  int intSumQuestionWeight = Integer.parseInt(strSumQuestionWeight);
  
  Vector vecQuestionChoices = new Vector();
  
  Vector vecQuestionUpdated = new Vector();

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

  if(objDataHashMap.containsKey(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_DETAILS))
  {
    vecQuestionUpdated = (Vector) objDataHashMap.get (SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_DETAILS) ;
    QuestionModel questionModel = (QuestionModel)vecQuestionUpdated.get(0);
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
      intSumQuestionWeight = intSumQuestionWeight - intQuestionWeight;
    }
    else
    {
      intSumQuestionWeight = 0;
    }
    questionOrder = questionModel.getQuestionOrder();
    questionMandatory = questionModel.getQuestionMandatory(); 
  }

  String strQuestionAction = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION) ;


  String strQuestionStatus = "";
  String pageTitle = "";
  String QuestionAction = "";
  if(strQuestionAction.equals(SurveyInterfaceKey.ACTION_CREATE_NEW_QUESTION))
  {
      strQuestionStatus = "1";
      pageTitle = " Create New Question";
      QuestionAction = SurveyInterfaceKey.ACTION_SAVE_QUESTION;
  }
  else
  {
      strQuestionStatus = questionStatusId;
      pageTitle = " Edit Question";
      QuestionAction = SurveyInterfaceKey.ACTION_UPDATE_QUESTION;
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
                  " value=\""+strGroupId+"\">");                

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_ID+"\""+
                  " value=\""+questionId+"\">");
                  
  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_QUESTION_STATUS_ID+"\""+
                  " value=\""+strQuestionStatus+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SurveyInterfaceKey.INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS+"\""+
                  " value=\""+intSumQuestionWeight+"\">");                 
%>
      <TABLE border=0 align='center' width='100%'>
        <TR class=TableTextNote>
          <TD width=\"20%\">Question&nbsp; *</TD>
          <TD colSpan=4><TEXTAREA style="WIDTH: 451px; HEIGHT: 84px" name="<%=SurveyInterfaceKey.INPUT_TEXTAREA_QUESTION%>" id="<%=SurveyInterfaceKey.INPUT_TEXTAREA_QUESTION%>" rows=4 cols=47><%=question%></TEXTAREA></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Question&nbsp;Weight *</TD>
          <TD colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_WEIGHT%>" value="<%=questionWeight%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Question&nbsp;Order *</TD>
          <TD colSpan=4><INPUT style="WIDTH: 452px; HEIGHT: 22px" size=66 name="<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER%>" id="<%=SurveyInterfaceKey.INPUT_TEXT_QUESTION_ORDER%>" value="<%=questionOrder%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Question&nbsp;Mandatory *</TD>
          <TD colSpan=4>
              <SELECT style="WIDTH: 451px" name="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_MANDATORY%>">
              <option value="1">YES</option>
              <option value="0">NO</option>
              </select>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Question&nbsp;Type *</TD>
          <TD colSpan=4>
              <SELECT style="WIDTH: 451px" name="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_TYPE%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_TYPE%>">
              <%
              String XquestionTypeId = "";
              String XquestionTypeName = "";
              
              for (int i=0; i<vecQuestionTypeList.size(); i++)
              {
                  QuestionTypeModel questionTypeModel = (QuestionTypeModel) vecQuestionTypeList.get(i);            
                  XquestionTypeId = questionTypeModel.getQuestionTypeId();
                  XquestionTypeName = questionTypeModel.getQuestionTypeName();

                  String questionTypeSelected = "";
                  if(questionTypeId.equals(XquestionTypeId))
                  {
                      questionTypeSelected = "selected";
                  }
                  %>
                  <option value="<%=XquestionTypeId%>" <%=questionTypeSelected%>><%=XquestionTypeName%></option>
                  <%
              }
              %>              
              </select>    
          </td>
        </tr>
        <!--TR class=TableHeader>
          <TD class=TableHeader width=\"20%\">Question&nbsp;Category *</TD>
          <TD class=TableHeader colSpan=4-->
              <SELECT style="WIDTH:451px;DISPLAY:none" name="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_CATEGORY%>" id="<%=SurveyInterfaceKey.INPUT_SELECT_QUESTION_CATEGORY%>">
              <%
              
              for (int i=0; i<vecQuestionCategoryList.size(); i++)
              {
                  QuestionCategoryModel questionCategoryModel = (QuestionCategoryModel) vecQuestionCategoryList.get(i);            
                  String XquestionCategoryId = questionCategoryModel.getQuestionCategoryId();
                  String XquestionCategoryName = questionCategoryModel.getQuestionCategoryName();

                  String questionCategorySelected = "";
                  //if(questionCategoryId.equals(XquestionCategoryId))
                  if(XquestionCategoryId.compareTo("1")==0)
                  {
                      questionCategorySelected = "selected";
                  }
                  %>
                  <option value="<%=XquestionCategoryId%>" <%=questionCategorySelected%>><%=XquestionCategoryName%></option>
                  <%
              }
              %>              
              </select>
          <!--/td>
        </tr-->
<%
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=5>Question&nbsp;Choices");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=5><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count id=user_defined_data_view_count>");
    out.println("<SCRIPT>  ");
    out.println("   var user_defined_data_view=new DeepGrid(\"user_defined_data_view\",0,\"\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Choice\",null,100,\"center\",DG_TEXT,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Choice Value\",null,100,\"center\",DG_TEXT,null)  ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Remove\",null,20,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('user_defined_data_view')\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,10,\"center\",DG_HIDDEN,null)  ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,10,\"center\",DG_HIDDEN,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Up\",null,20,null,DG_IMAGE,\"../resources/img/up.gif\",null,\"onClick = move_up(this.name);\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Down\",null,20,null,DG_IMAGE,\"../resources/img/down.gif\",null,\"onClick = move_down(this.name);\")   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");
%>
      </table>
      <center>
      <%
        out.println("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.formDataView."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+QuestionAction+"';"+
                    "checkBeforeSubmit();\">");


  if(objDataHashMap.containsKey(SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES))
  {
     vecQuestionChoices = (Vector)objDataHashMap.get (SurveyInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES) ; 
     int numberofchoices = vecQuestionChoices.size();
     out.println("<script>");
     int v = 0;
     for(int h= 0; h<vecQuestionChoices.size(); h++)
     {
        v=h+1;
        ChoiceModel choiceModel = (ChoiceModel) vecQuestionChoices.get(h);
        String choiceId = choiceModel.getChoiceId();
        String choice = choiceModel.getChoice();
        String choiceQuestionId = choiceModel.getQuestionId();
        String choiceValue = choiceModel.getChoiceValue();
        String choiceOrder = choiceModel.getChoiceOrder();
        
        out.println("eval(\"user_defined_data_view.RowSet.add("+v+");\"); ");
        out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C1.value = '"+ choice+"';\"); ");
        out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C2.value = '"+ choiceValue+"';\"); ");
        out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C4.value = '"+ choiceId+"';\"); ");
        out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C5.value = '"+ v+"';\"); ");            
     }
     out.println("eval(\"document.formDataView.user_defined_data_view_count.value = "+ numberofchoices+" ;\"); ");
     out.println("</script>"); 
  }                    
      %>
      </center>
</form>

  </body>
</html>
