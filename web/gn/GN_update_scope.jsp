<%@ page  import = "java.util.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.gn.scopes.*"
          import = "java.io.*"
          import = "com.mobinil.sds.core.system.gn.scopes.model.*"
          import = "javax.servlet.*" 
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
%>
<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    if(NonBlank(document.formDataView.<%out.print(ScopesInterfaceKey.CONTROL_TEXTAREA_SCOPE_NAME);%>, true, 'Scope Name'))
    {
      if(NonBlank(document.formDataView.<%out.print(ScopesInterfaceKey.CONTROL_SELECT_SCOPE_STATUS);%>, true, 'Scope Status'))
      {
          if(!checknumber(document.formDataView.<%out.print(ScopesInterfaceKey.CONTROL_TEXT_SCOPE_VERSION);%>.value))
          {
              alert("Scope Issue must be a number");
              return;
          }
              if(NonBlank(document.formDataView.<%out.print(ScopesInterfaceKey.CONTROL_TEXT_SCOPE_VERSION);%>, true, 'Scope Version'))
              {
                if(!checknumber(document.formDataView.<%out.print(ScopesInterfaceKey.CONTROL_TEXT_SCOPE_VERSION);%>.value))
                {
                    alert("Scope Version must be a number");
                    return;
                }
                  formDataView.submit();
              }
       }
    }
    return false;  
  }
</SCRIPT>
<html>
  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      
  </head>
  <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
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

  function checknumber(text)
  {
    var x=text
    var anum=/(^\d+$)|(^\d+\.\d+$)/
    if (anum.test(x))
    testresult=true
    else
    {
    testresult=false
    }
    return (testresult)
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
      var strpos =  "user_defined_data_view__R"+curpos+"__C5"; 
      document.getElementById(strpos).value = curpos;
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
            eval(argObject+".getCell("+i+",4).setValue()") == false; 
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
            var tempfiledsqlcash = document.getElementById("user_defined_data_view__R"+i+"__C3").value;
            var tempfileditemposition = document.getElementById("user_defined_data_view__R"+i+"__C5").value;
            var tempfiledid = document.getElementById("user_defined_data_view__R"+i+"__C6").value;
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
            var tempfiledsqlcashabove = document.getElementById("user_defined_data_view__R"+j+"__C3").value;
            var tempfileditempositionabove = document.getElementById("user_defined_data_view__R"+j+"__C5").value;
            var tempfiledidabove = document.getElementById("user_defined_data_view__R"+j+"__C6").value;

            document.getElementById("user_defined_data_view__R"+i+"__C1").value = tempfilednameabove;
            document.getElementById("user_defined_data_view__R"+i+"__C2").value = tempfileddescabove;
            document.getElementById("user_defined_data_view__R"+i+"__C3").value = tempfiledsqlcashabove;
            document.getElementById("user_defined_data_view__R"+i+"__C6").value = tempfiledidabove;

            document.getElementById("user_defined_data_view__R"+j+"__C1").value = tempfiledname;
            document.getElementById("user_defined_data_view__R"+j+"__C2").value = tempfileddesc;
            document.getElementById("user_defined_data_view__R"+j+"__C3").value = tempfiledsqlcash;
            document.getElementById("user_defined_data_view__R"+j+"__C6").value = tempfiledid;
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
            var tempfiledsqlcash = document.getElementById("user_defined_data_view__R"+i+"__C3").value;
            var tempfileditemposition = document.getElementById("user_defined_data_view__R"+i+"__C5").value;
            var tempfiledid = document.getElementById("user_defined_data_view__R"+i+"__C6").value;

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
            var tempfiledsqlcashabove = document.getElementById("user_defined_data_view__R"+j+"__C3").value;
            var tempfileditempositionabove = document.getElementById("user_defined_data_view__R"+j+"__C5").value;
            var tempfiledidabove = document.getElementById("user_defined_data_view__R"+j+"__C6").value;

            document.getElementById("user_defined_data_view__R"+i+"__C1").value = tempfilednameabove;
            document.getElementById("user_defined_data_view__R"+i+"__C2").value = tempfileddescabove;
            document.getElementById("user_defined_data_view__R"+i+"__C3").value = tempfiledsqlcashabove;
            document.getElementById("user_defined_data_view__R"+i+"__C6").value = tempfiledidabove;

            document.getElementById("user_defined_data_view__R"+j+"__C1").value = tempfiledname;
            document.getElementById("user_defined_data_view__R"+j+"__C2").value = tempfileddesc;
            document.getElementById("user_defined_data_view__R"+j+"__C3").value = tempfiledsqlcash;
            document.getElementById("user_defined_data_view__R"+j+"__C6").value = tempfiledid;
    }    
    var UserDefinedDataViewArray =new Array();
  </SCRIPT>  
<body>
    <%
    showScopeDetails(request, response, out);
    %>
    <center><input class="button" type='button' value='Save' onclick=checkbeforSubmit();></center>
      </form>
         
</body>
</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>
</html>
<%!
  /**
   * showScopeDetails method: 
   * Display the form in which a sheet type will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showScopeDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    String xxdataviewId = "";
    String xxdataviewIssue = "";
    String xxdataviewVersion = "";
    String xxdataviewName = "";
    String xxdataviewOverrideSQL = "";
    String xxdataviewTypeId = "";
    String xxdataviewTypeName = "";
    String xxdataviewDescription = "";
    String xxdataviewStatusId = "";
    String xxdataviewStatusName = "";
    String xxdataviewUnique = "";
  
    String yydataviewId;
    String yydataviewIssue;
    String yydataviewVersion;
    String yyfieldId;
    String yyfieldName;
    String yyfieldDescription;
    String yyfieldSQLCash;
    String yyfieldDisplayTypeId;
    String yyfieldDisplayTypeName;
    String yyfieldTypeName;
    String yyfieldTypeObjectId;
    String yyfieldRFObjrct;
    String yyitemPosition;   
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Scope</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);

    String formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +ScopesInterfaceKey.ACTION_SAVE_SCOPE;
    
    out.println("<form name='formDataView' action='"+formActionSave+"' method='post'>");


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    if(dataHashMap.get(ScopesInterfaceKey.HASHMAP_KEY_SCOPE_COLLECTION) != null)
    {
      Vector CurrentScopeVec = (Vector) dataHashMap.get(ScopesInterfaceKey.HASHMAP_KEY_SCOPE_COLLECTION);
            for(int k= 0; k<CurrentScopeVec.size(); k++)
            {
              ScopesModel scopeModel = (ScopesModel) CurrentScopeVec.get(k);            
              xxdataviewId = scopeModel.getDataviewId();
              xxdataviewIssue = scopeModel.getDataviewIssue();
              xxdataviewVersion = scopeModel.getDataviewVersion();
              xxdataviewName = scopeModel.getDataviewName();
              xxdataviewOverrideSQL = scopeModel.getDataviewOverrideSQL();
              xxdataviewTypeId = scopeModel.getDataviewTypeId();
              xxdataviewTypeName = scopeModel.getDataviewTypeName();
              xxdataviewDescription = scopeModel.getDataviewDescription();
              xxdataviewStatusId = scopeModel.getDataviewStatusId();
              xxdataviewStatusName = scopeModel.getDataviewStatusName();
              xxdataviewUnique = scopeModel.getDataviewUnique();
            }
    }
    
    Vector scopeStatusVec = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
          
    String scopeaction = (String) dataHashMap.get(ScopesInterfaceKey.SCOPE_ACTION);
    
    out.println("<input type='hidden' name='scopeaction' id='scopeaction' value='"+scopeaction+"'>");
    String scopeID = ScopesInterfaceKey.CONTROL_HIDDEN_SCOPE_ID;
    String scopeName = ScopesInterfaceKey.CONTROL_TEXTAREA_SCOPE_NAME;
    String scopeIssue = ScopesInterfaceKey.CONTROL_TEXT_SCOPE_ISSUE; 
    String scopeVersion = ScopesInterfaceKey.CONTROL_TEXT_SCOPE_VERSION;
    String scopeStatus = ScopesInterfaceKey.CONTROL_SELECT_SCOPE_STATUS;
    String scopeDesc = ScopesInterfaceKey.CONTROL_TEXTAREA_SCOPE_DESCRIPTION;

    out.println("<table border=0 align='center' width='100%'>");
    out.println("    <tr class=TableHeader>");
    out.println("        <td colspan=2><input type='hidden' name='"+scopeID+"' id='"+scopeID+"' value='"+xxdataviewId+"'></td>");
    out.println("    </tr>");
    out.println("    <tr class=TableHeader>");
    out.println("        <td class=TableHeader width='30%'>Scope Name</td>");
    out.println("        <td width='70%'><textarea name='"+scopeName+"' id='"+scopeName+"' cols=70 rows=3>"+xxdataviewName+"</textarea></td>");
    out.println("    </tr>");
    out.println("<tr class=TableHeader>");
    out.println("<td class=TableHeader>Scope Status</td>");
    out.println("<td>");
    out.println("<select name='"+scopeStatus+"' id='"+scopeStatus+"'>");
    out.println("      <option value=''></option>");
              ScopeStatusModel scopeStatusModel;  
              String scopeStatusIdX;
              String scopeStatusNameX;
              String scopeStatusDescX;
              String selectedX;
              for(int l= 0; l<scopeStatusVec.size(); l++)
              {
                scopeStatusModel = (ScopeStatusModel) scopeStatusVec.get(l);            
                scopeStatusIdX   = scopeStatusModel.getDataviewStatusId();
                scopeStatusNameX = scopeStatusModel.getDataviewStatusName();
                scopeStatusDescX = scopeStatusModel.getDataviewStatusDesc();
                selectedX = "";
                if(xxdataviewStatusId.equals(scopeStatusIdX)) 
                {
                    selectedX = "selected";
                }
                out.println("<option value='"+scopeStatusIdX+"' "+selectedX+">"+scopeStatusNameX+"</option>");
                scopeStatusIdX =""  ;
                scopeStatusNameX ="";
                scopeStatusDescX ="";
                selectedX = "";
              }
    
    out.println("</select>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr class=TableHeader>");
    out.println("    <td class=TableHeader>Scope Issue</td>");
    out.println("    <td><input type='text' name='"+scopeIssue+"' id='"+scopeIssue+"' value='"+xxdataviewIssue+"' readonly></td>");
    out.println("</tr>");
    out.println("<tr class=TableHeader>");
    out.println("    <td class=TableHeader>Scope Version</td>");
    out.println("    <td><input type='text' name='"+scopeVersion+"' id='"+scopeVersion+"' value='"+xxdataviewVersion+"'></td>");
    out.println("</tr>");
    out.println("<tr class=TableHeader>");
    out.println("    <td class=TableHeader>Scope Description</td>");
    out.println("    <td><textarea name='"+scopeDesc+"' id='"+scopeDesc+"' cols=70 rows=5>"+xxdataviewDescription+"</textarea></td>");
    out.println("</tr>");

    out.println("<TR>");
    out.println("  <TD class=TableHeader vAlign=top colSpan=2>Scope&nbsp;Fields");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','user_defined_data_view_count','user_defined_data_view','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=user_defined_data_view_count id=user_defined_data_view_count>");
    out.println("<SCRIPT>  ");
    out.println("   var user_defined_data_view=new DeepGrid(\"user_defined_data_view\",0,\"\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Field Name\",null,100,\"center\",DG_TEXT,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Description\",null,210,\"center\",DG_TEXTAREA,null)  ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Field SQL Cash\",null,210,\"center\",DG_TEXTAREA,null)  ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Remove\",null,20,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('user_defined_data_view')\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,10,\"center\",DG_HIDDEN,null)  ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"\",null,10,\"center\",DG_HIDDEN,null)   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Up\",null,20,null,DG_IMAGE,\"../resources/img/up.gif\",null,\"onClick = move_up(this.name);\")   ");
    out.println("   user_defined_data_view.ColumnHeaders.add(null,null,\"Down\",null,20,null,DG_IMAGE,\"../resources/img/down.gif\",null,\"onClick = move_down(this.name);\")   ");
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");
 
 if(dataHashMap.get(ScopesInterfaceKey.HASHMAP_KEY_SCOPE_FIELD_COLLECTION) != null)
    {
    
      int v = 0;

      Vector CurrentScopeFieldsVec = (Vector) dataHashMap.get(ScopesInterfaceKey.HASHMAP_KEY_SCOPE_FIELD_COLLECTION);
      int numberoffields = CurrentScopeFieldsVec.size();
      out.println("<script>");
            for(int h= 0; h<CurrentScopeFieldsVec.size(); h++)
            {
            v = h+1;
            
              FieldsModel scopefieldsModel = (FieldsModel) CurrentScopeFieldsVec.get(h);            

              yydataviewId = scopefieldsModel.getDataviewId();
              yydataviewIssue = scopefieldsModel.getDataviewIssue();
              yydataviewVersion = scopefieldsModel.getDataviewVersion();
              yyfieldId = scopefieldsModel.getFieldId();
              yyfieldName = scopefieldsModel.getFieldName();
              yyfieldDescription = scopefieldsModel.getFieldDescription();
              yyfieldSQLCash = scopefieldsModel.getFieldSQLCash();
              yyfieldDisplayTypeId = scopefieldsModel.getFieldDisplayTypeId();
              yyfieldDisplayTypeName = scopefieldsModel.getFieldDisplayTypeName();
              yyfieldTypeName = scopefieldsModel.getFieldTypeName();
              yyfieldTypeObjectId = scopefieldsModel.getFieldTypeObjectId();
              yyfieldRFObjrct = scopefieldsModel.getFieldRFObjrct();
              yyitemPosition = scopefieldsModel.getItemPosition();  
                  
                  out.println("eval(\"user_defined_data_view.RowSet.add("+v+");\"); ");
                  //eval("document.formDataView.user_defined_data_view__R"+Number(nUserDefinedDataViewCount)+"__C1.value = '"+ objDataView.m_nDataViewID+"';");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C1.value = '"+ yyfieldName+"';\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C2.value = '"+ yyfieldDescription+"';\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C3.value = '"+ yyfieldSQLCash+"';\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C5.value = '"+ v+"';\"); ");
                  out.println("eval(\"document.formDataView.user_defined_data_view__R"+v+"__C6.value = '"+ yyfieldId+"';\"); ");
            }
           out.println("eval(\"document.formDataView.user_defined_data_view_count.value = "+ numberoffields+" ;\"); ");
           out.println("</script>"); 
          
    }
}
%>