<%@ page  import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"

          import = "com.mobinil.sds.web.interfaces.dcm.*"
          
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import="com.mobinil.sds.core.system.dcm.user.model.*"
          import="com.mobinil.sds.core.system.dcm.function.model.*"
          import="com.mobinil.sds.core.system.dcm.group.model.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    formDataView.submit();
  }

  var arrTargetPeriodList = new Array
             ( 
  <%fillInTargetPeriod(request,response,out);%>                    
             )
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
      //for (var i = 0; i < eval(argArrayDataView+".length"); i++)
      //{
      //   if (eval(argArrayDataView+"["+i+"]") == argCurrentValue)
      //   {
      //      eval(argControlName+".RowSet.getCell("+Number(ix+1)+",1).cellElement.selectedIndex ="+i+";");                               
      //   }
      //} 
    }

    function app_need_removeRows(argObject)
    {
      i = confirm("This will remove this data")
      if (i==true){
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",4).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",4).getValue()")==true){
            eval(argObject+".getCell("+i+",4).setValue == false; ") 
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

    var arrFunctionList;
  </SCRIPT>
<body>
      
    <%
    showSalesAgentsTarget(request, response, out);
    %>        
    <center>
      <%
      out.println("<input class='button' type='button' value='Save' onclick=\""+
                  "checkbeforSubmit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
    </center>
      </form>
<%!
  public void showSalesAgentsTarget(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Assign Target With Group To Sales Agent</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);
    
    out.println("<form name='formDataView' action='' method='post'>");

    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+DCMInterfaceKey.ACTION_DCM_SAVE_ASSIGN_TARGET_TO_SALES_AGENT+"\">");

    DCMUserDetailModel dcmUserDetailModel = (DCMUserDetailModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    String userDetailId = dcmUserDetailModel.getUserDetailId();
    String userId = dcmUserDetailModel.getUserId();
    String userFullName = dcmUserDetailModel.getUserFullName();
    String userAddress = dcmUserDetailModel.getUserAddress();
    if(userAddress == null)userAddress = "";
    String userEmail = dcmUserDetailModel.getUserEmail();
    String userMobile = dcmUserDetailModel.getUserMobile();
    if(userMobile == null)userMobile = "";
    String regionId = dcmUserDetailModel.getRegionId();
    String regionName = dcmUserDetailModel.getRegionName();

    Vector functionForUser = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
    
    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+"\""+
                  " value=\""+userId+"\">");
    
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("    <TR class=TableHeader>");
    out.println("      <TD align='center'>Manager Name</TD>");
    out.println("      <TD align='center'>E-mail</TD>");
    out.println("      <TD align='center'>Mobile Phone</TD>");
    out.println("      <TD align='center'>Address</TD>");
    out.println("      <TD align='center'>Region</TD>");
    out.println("    </tr>");
    out.println("    <TR>");
    out.println("      <TD align='center'>"+userFullName+"</TD>");
    out.println("      <TD align='center'>"+userEmail+"</TD>");
    out.println("      <TD align='center'>"+userMobile+"</TD>");
    out.println("      <TD align='center'>"+userAddress+"</TD>");
    out.println("      <TD align='center'>"+regionName+"</TD>");
    out.println("    </tr>");
    out.println("  </table>  ");
    out.println("  <br>");


    for(int f=0;f<functionForUser.size();f++)
    {    
    FunctionModel functionModel = (FunctionModel)functionForUser.get(f);
    int functionID       = functionModel.get_function_id() ;
    String strFunctionId = functionID+"";

    out.println("<script>");
    out.println("arrFunctionList = new Array");
    out.println("         (  ");
    fillInGroups(request,response,out,strFunctionId);                    
    out.println("         ) ");
    out.println("</script>");
    
    String functionName  = functionModel.get_function_name() ;
    String functionDesc  = functionModel.get_function_desc() ;
    int functionStatusID = functionModel.get_function_status_id()  ;
    String functionStatusName = functionModel.get_function_status_name();
    String functionUnits = functionModel.get_function_units();
    
    out.println("<table border=0 align='center' width='70%'>");
    
    out.println("<TR>");
    out.println("  <TD class=TableHeader vAlign=top colSpan=2>"+functionName+"");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','target_count','fun_"+strFunctionId+"','"+strFunctionId+"Array');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Group Target ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");
    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value='0' name='target_count'>");
    out.println("<SCRIPT>  ");
    out.println("   var fun_"+strFunctionId+"=new DeepGrid(\"fun_"+strFunctionId+"\",0,\"\")   ");
    out.println("   fun_"+strFunctionId+".ColumnHeaders.add(null,null,\"Group\",null,150,\"center\",DG_SELECT,arrFunctionList,null,null)   ");
    out.println("   fun_"+strFunctionId+".ColumnHeaders.add(null,null,\"Target\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   fun_"+strFunctionId+".ColumnHeaders.add(null,null,\"Target Period\",null,150,\"center\",DG_SELECT,arrTargetPeriodList,null,null)   ");
    out.println("   fun_"+strFunctionId+".ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('fun_"+strFunctionId+"')\")   ");
    out.println("   fun_"+strFunctionId+".ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,1)   ");
    out.println("   fun_"+strFunctionId+".ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,"+strFunctionId+")   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");

    out.println("<br><br>");

    }

    Vector userGroupFunctionTargetAssignedTo = (Vector)dataHashMap.get(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION);
    HashMap functionCroupCount = new HashMap();
    for(int t=1;t<=userGroupFunctionTargetAssignedTo.size();t++)
    {
      DCMUserGroupFunctionTargetModel dcmUserGroupFunctionTargetModel = (DCMUserGroupFunctionTargetModel)userGroupFunctionTargetAssignedTo.get(t-1);
      String tUserGroupFunctionTargetId = dcmUserGroupFunctionTargetModel.getUserGroupFunctionTargetId();  
      String tGroupId = dcmUserGroupFunctionTargetModel.getGroupId();                       
      String tGroupName = dcmUserGroupFunctionTargetModel.getGroupName(); 
      String tFunctionId = dcmUserGroupFunctionTargetModel.getFunctionId();
      String tFunctionName = dcmUserGroupFunctionTargetModel.getFunctionName();
      String tTargetValue = dcmUserGroupFunctionTargetModel.getTargetValue();                   
      String tTargetDurationTypeId = dcmUserGroupFunctionTargetModel.getTargetDurationTypeId();
      String tTargetDurationTypeName = dcmUserGroupFunctionTargetModel.getTargetDurationTypeName();
      String tNumberOfDays = dcmUserGroupFunctionTargetModel.getNumberOfDayes();

      
      String strNextValue = "1";
      int intNextValue = 1;
      if(functionCroupCount.containsKey(tFunctionId))
      {
        strNextValue = (String)functionCroupCount.get(tFunctionId);
        intNextValue = Integer.parseInt(strNextValue);
        intNextValue += 1;
        functionCroupCount.remove(tFunctionId);
      }

      strNextValue = intNextValue+"";
      functionCroupCount.put(tFunctionId,strNextValue);
      
      out.println("<script>");
      out.println("fun_"+tFunctionId+".RowSet.add("+intNextValue+");");
      out.println("eval(\"document.formDataView.fun_"+tFunctionId+"__R"+intNextValue+"__C1.value = '"+ tGroupId +"';\");");
      out.println("eval(\"document.formDataView.fun_"+tFunctionId+"__R"+intNextValue+"__C2.value = '"+ tTargetValue +"';\");");
      out.println("eval(\"document.formDataView.fun_"+tFunctionId+"__R"+intNextValue+"__C3.value = '"+ tTargetDurationTypeId +"';\");");
      out.println("eval(\"document.formDataView.fun_"+tFunctionId+"__R"+intNextValue+"__C6.value = '"+ tFunctionId +"';\");");
      out.println("</script>");

      out.println("<input type=\"hidden\" name=\"fun_"+tFunctionId+"__R"+intNextValue+"__C7\""+
                  " value=\""+tUserGroupFunctionTargetId+"\">");      
    }
}
%>


<%!
  public void fillInGroups(HttpServletRequest request, HttpServletResponse response, JspWriter out,String functionId)
  throws ServletException, IOException
  {
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector groupList = (Vector)hmDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      boolean firstInput = true;
      if(groupList != null)
      for (int i = 0; i <groupList.size() ; i++) 
      {
        GroupModel groupModel = (GroupModel)groupList.get(i);
        int intGroupId = groupModel.getGroupID();
        String strGroupName = groupModel.getGroupName();
        Vector vecGroupFunctionIds = groupModel.getGroupFunctionIDs();
        for(int j=0;j<vecGroupFunctionIds.size();j++)
        {
          String strFunctionId = (String)vecGroupFunctionIds.get(j);
          if(strFunctionId.compareTo(functionId)==0)
          {
            if(firstInput)
            {
              firstInput = false;
            }
            else
            {
              out.println(",");
            }
            out.println("new Array('"+strGroupName+"',"+intGroupId+")");
          }
        }
      }
  }


  public void fillInTargetPeriod(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      HashMap hmDataHashMap = new HashMap(100);
      hmDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector targetPeriodList = (Vector)hmDataHashMap.get(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION);       
      if(targetPeriodList != null)
      for (int i = 0; i <targetPeriodList.size() ; i++) 
      {
        GenericModel genericModel = (GenericModel)targetPeriodList.get(i);
        String targetDurationTypeId = genericModel.get_primary_key_value();
        String targetDurationTypeName = genericModel.get_field_2_value();
        out.println("new Array('"+targetDurationTypeName+"',"+targetDurationTypeId+")");
        if(i<targetPeriodList.size()-1)
        out.println(",");
      }
  }
%>  
</body>
</html>