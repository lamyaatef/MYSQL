<%@ page  import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"

          import = "com.mobinil.sds.web.interfaces.dcm.*"
          
          import = "com.mobinil.sds.core.system.sv.surveys.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.sv.*"
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    var missionGroupName = document.formDataView.<%=SurveyInterfaceKey.INPUT_TEXT_MISSION_GROUP_NAME%>.value;
    if(missionGroupName.length==0)
    {
      alert("Mission Group Name can not be empty.");
    }
    else
    {
      formDataView.submit(); 
    }
  }
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
  </head>
<SCRIPT language="javascript">
  function checkQuotes()
  {
    var nKeyCode = event.keyCode;
    if( Number(nKeyCode)== 34 )
    {
        alert("You are not allowed to use the (\") character");
        event.keyCode =0;
        return false;
    }    
    if( Number(nKeyCode)== 39 )
    {
       alert("You are not allowed to use the (\') character");
       event.keyCode = 0;
       return false;
    }   
    return true;
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
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
      if (i==true){
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",2).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",2).getValue()")==true){
            eval(argObject+".getCell("+i+",2).setValue == false; ") 
          }//end if
        }//end for
      }//end else
    }

    var UserDefinedDataViewArray =new Array();    
    function popUp(argObj,argVersionArrayName,argDescriptionArrayName)
    {
        var nRowIndex;
        var nSelectedIndex = 0;
        var strPopUpColumnIDVersion = new String();
        var strPopUpColumnIDDescription = new String();
        var strID= new String(argObj.id);
        nRowIndex = strID.substring(strID.indexOf("__R")+3,strID.indexOf("__C"));
        strPopUpColumnIDVersion = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDVersion +=2
        strPopUpColumnIDDescription = strID.substring(0,strID.indexOf("__C")+3);
        strPopUpColumnIDDescription +=3;

        eval("nSelectedIndex = document.formDataView."+strID+".selectedIndex");
        if(nSelectedIndex > 0)
        {
            var strDescrioption;
            var arrPairs=new Array();
            var strPairs=new String(eval(argVersionArrayName+"["+nSelectedIndex+"];"));
            strDescrioption = eval(argDescriptionArrayName+"["+nSelectedIndex+"];");
            arrPairs = strPairs.split(",");

           eval("document.formDataView."+strPopUpColumnID+".add(objOption);");
     eval("document.formDataView."+strPopUpColumnIDVersion+".value ="+arrPairs[0]+";");
     if(strDescrioption == "" || strDescrioption == null || strDescrioption == "null")
         strDescrioption = "N/A";
     eval("document.formDataView."+strPopUpColumnIDDescription+".value ='"+strDescrioption+"';");

      }
      else
      {
        eval("document.formDataView."+strPopUpColumnIDVersion+".value ='';");
        eval("document.formDataView."+strPopUpColumnIDDescription+".value ='';");
      }
    }
  </SCRIPT>
<body>
<script>
      var arrSurveysArray=new Array
             ( 
  <%fillInSurveysArray(request,response,out);%>                    
             )
</script>             
    <%
    showMissionGroupDetails(request, response, out);
    %>        
    <center>
      <%
        out.println("<input class='button' type='button' value='Save' onclick=checkbeforSubmit();>");
      %>  
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
    </center>
      </form>
<%!
  public void showMissionGroupDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Mission Group</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);

    
    String formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +SurveyInterfaceKey.ACTION_SAVE_MISSION_GROUP;
    
    out.println("<form name='formDataView' action='"+formActionSave+"' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    MissionGroupModel missionGroupModel = null;
    if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
    {
      missionGroupModel = (MissionGroupModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    }  

    Vector vecFilSurveyMissionGroup = new Vector();
    if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2))
    {
      vecFilSurveyMissionGroup = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
    }
    String missionGroupId = "";
    String missionGroupName = "";
    if(missionGroupModel!=null)
    {
      missionGroupId = missionGroupModel.getMissionGroupId();
      missionGroupName = missionGroupModel.getMissionGroupName();
    }

    out.println("<input type='hidden' name='"+SurveyInterfaceKey.INPUT_HIDDEN_MISSION_GROUP_ID+"' id='"+SurveyInterfaceKey.INPUT_HIDDEN_MISSION_GROUP_ID+"' value='"+missionGroupId+"'>");
    

    out.println("<table border=0 align='center' width='100%'>");

    out.println("<TR>");

    out.println("</TR>");
      out.println("<Td class=TableTextNote>Mission Group Name");
      out.println("</TD>");
      out.println("<Td class=TableTextNote><input type='text' name='"+SurveyInterfaceKey.INPUT_TEXT_MISSION_GROUP_NAME+"' id='"+SurveyInterfaceKey.INPUT_TEXT_MISSION_GROUP_NAME+"' value='"+missionGroupName+"' ");
      out.println("</TD>");
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Fil Surveys");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count>");
    out.println("<SCRIPT>  ");
    out.println("   var user_defined_data_view=new DeepGrid(\"user_defined_data_view\",0,\"\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Fil Survey\",null,150,\"center\",DG_SELECT,arrSurveysArray,null,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('user_defined_data_view')\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,null)   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");

}
%>
<%!
    public void fillInSurveysArray(HttpServletRequest request, HttpServletResponse response, JspWriter out)
    throws ServletException, IOException
    {
        String strFilSurveyID;
        String strFilSurveyName;
        HashMap hmDataHashMap = new HashMap(100);
        hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector filSurveyList = (Vector)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        if(filSurveyList != null)
        for (int i = 0; i <filSurveyList.size() ; i++) 
        {
           FilSurveyModel filSurveyModel = (FilSurveyModel)filSurveyList.get(i);
           
           strFilSurveyID = filSurveyModel.getFilSurveyId();
           strFilSurveyName = filSurveyModel.getFilSurveyName();
           out.println("new Array('"+strFilSurveyName+"',"+strFilSurveyID+")");
           if(i<filSurveyList.size()-1)
             out.println(",");
        }
    }
    %>
</body>
</html>