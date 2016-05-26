<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.branch.model.*"      
%>

<% 
String appName = request.getContextPath();
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {

  }
</SCRIPT>
<html>

  <head>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
  </head>
<SCRIPT language="javascript">
  function drawCalender(argOrder,argValue)
  {
      document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(formDataView."+argOrder+",'yyyy-mm-dd','Choose date')\">");
      document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
  }
  function callDrawCalendar(argOrder)
  {
      var part1 = argOrder.split("IMG__");
      var part2 = part1[1].split("C");
      var order = eval(part2[1])-1;
      var name = part2[0] + "C" + order;
      name = "formDataView."+name; 
      showCalendar(eval(name) , 'yyyy-mm-dd' ,'chooseDate');
  }
  function checkMainBranch(currentValue)
  {
    var x;
  
    if(currentValue == 'false'){
      x = true;
      }
   else{
      x = false;
    }
      return x;      
  }
  function move_up(argobj)
    {
          var i = argobj.charAt(13);
          var k = argobj.charAt(14);
          if(k=="_")
          {
            
          }
          else
          {
              i = i+k;
          }
          if(i==1)
          {
              return;
          }
          if(document.getElementById("field__R"+i+"__C1"))
          {
          var tempfiledType = document.getElementById("field__R"+i+"__C1").value;
          var tempfiledName = document.getElementById("field__R"+i+"__C2").value;
          selectlist = eval("document.formDataViewGroupBy.field__R"+i+"__C2");
          var j = i-1;
          while(!document.getElementById("field__R"+j+"__C1"))
          {
            j=j-1;
            if(j==0)
            {
            return;
            }
          }  
          var tempfiledTypeabove = document.getElementById("field__R"+j+"__C1").value;
          var tempfiledNameabove = document.getElementById("field__R"+j+"__C2").value;
          selectlistabove = eval("document.formDataViewGroupBy.field__R"+j+"__C2");
          
          var selectlistabovearr = new Array()
          for(var m=0;m<selectlistabove.options.length;m++)
          {
              selectlistabovearr[m] = selectlistabove.options[m].value+"&"+selectlistabove.options[m].text
          }
          var selectlistarr = new Array()
          for(var s=0;s<selectlist.options.length;s++)
          {
              selectlistarr[s] = selectlist.options[s].value+"&"+selectlist.options[s].text
          }
          
          selectnameX = "field__R"+i+"__C2";
          selectnameaboveX = "field__R"+j+"__C2";
          removeAllOptions(selectnameX);
          removeAllOptions(selectnameaboveX);

          document.getElementById("field__R"+i+"__C1").value = tempfiledTypeabove;
          for(var f=0;f<selectlistabovearr.length;f++)
          {
              optionX = selectlistabovearr[f].split("&");
              optionvalueX = optionX[0];
              optiontextX = optionX[1];
              addOption(optionvalueX,optiontextX,selectnameX);
          }
          document.getElementById("field__R"+i+"__C2").value = tempfiledNameabove;
          
          document.getElementById("field__R"+j+"__C1").value = tempfiledType;
          for(var n=0;n<selectlistarr.length;n++)
          {
              optionY = selectlistarr[n].split("&");
              optionvalueY = optionY[0];
              optiontextY = optionY[1];
              addOption(optionvalueY,optiontextY,selectnameaboveX);
          }          
          document.getElementById("field__R"+j+"__C2").value = tempfiledName;
          }
          else
          {
          return;
          }
    }

    
		function removeAllOptions(selectname)
		{
		var elSel = document.getElementById(selectname);
		if (elSel.length == 0) 
			{
		  	return;
		  }
		elSel.remove(0);
		removeAllOptions(selectname)  	
		}
		      
		function addOption(optvalue,opttext,selectname)
		{
			var elSel = document.getElementById(selectname);
			var elOptNew = new Option();
		
			elOptNew.text =  opttext;
			elOptNew.value =  optvalue;
			
			elSel.options[elSel.length] = elOptNew;		
		}
    function move_down(argobj)
    {
          var i = argobj.charAt(13);
          var k = argobj.charAt(14);
          if(k=="_")
          {
              i = (i*1)+1;
          }
          else
          {
              i = i+k;
              i = (i*1)+1;
          }
          if(document.getElementById("field__R"+i+"__C1"))
          {
          var tempfiledType = document.getElementById("field__R"+i+"__C1").value;
          var tempfiledName = document.getElementById("field__R"+i+"__C2").value;
          selectlist = eval("document.formDataViewGroupBy.field__R"+i+"__C2");
          var j = i-1;
          while(!document.getElementById("field__R"+j+"__C1"))
          {
            j=j-1;
            if(j==0)
            {
            return;
            }
          }  
          var tempfiledTypeabove = document.getElementById("field__R"+j+"__C1").value;
          var tempfiledNameabove = document.getElementById("field__R"+j+"__C2").value;
          selectlistabove = eval("document.formDataViewGroupBy.field__R"+j+"__C2");
          
          var selectlistabovearr = new Array()
          for(var m=0;m<selectlistabove.options.length;m++)
          {
              selectlistabovearr[m] = selectlistabove.options[m].value+"&"+selectlistabove.options[m].text
          }
          var selectlistarr = new Array()
          for(var s=0;s<selectlist.options.length;s++)
          {
              selectlistarr[s] = selectlist.options[s].value+"&"+selectlist.options[s].text
          }
          
          selectnameX = "field__R"+i+"__C2";
          selectnameaboveX = "field__R"+j+"__C2";
          removeAllOptions(selectnameX);
          removeAllOptions(selectnameaboveX);

          document.getElementById("field__R"+i+"__C1").value = tempfiledTypeabove;
          for(var f=0;f<selectlistabovearr.length;f++)
          {
              optionX = selectlistabovearr[f].split("&");
              optionvalueX = optionX[0];
              optiontextX = optionX[1];
              addOption(optionvalueX,optiontextX,selectnameX);
          }
          document.getElementById("field__R"+i+"__C2").value = tempfiledNameabove;
          
          document.getElementById("field__R"+j+"__C1").value = tempfiledType;
          for(var n=0;n<selectlistarr.length;n++)
          {
              optionY = selectlistarr[n].split("&");
              optionvalueY = optionY[0];
              optiontextY = optionY[1];
              addOption(optionvalueY,optiontextY,selectnameaboveX);
          }          
          document.getElementById("field__R"+j+"__C2").value = tempfiledName;
          }
          else
          {
          return;
          }
    }
    
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
          if(eval(argObject+".getCell("+i+",9).getValue()")==true){
            eval(argObject+".RowSet.deleteRow("+i+");");
          }//end if
        }//end for
      }//end if
      else
      {
        for(var i=eval(argObject+".getRowCount()");i>=1;i--){
          if(eval(argObject+".getCell("+i+",5).getValue()")==true){
            eval(argObject+".getCell("+i+",5).setValue == false; ") 
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
    <%
    showPOSRelationDetails(request, response, out);
    %>        
    <br><br>
    <center>
      <%
        out.println("<input class=\"button\" type=\"button\" value=\"Save\" onclick=\"formDataView.submit();\">");      
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
    </center> 
      </form>
<%!
  public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>POS Branch Details</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);

    String formActionSave = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                        +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +DCMInterfaceKey.ACTION_DCM_SAVE_NEW_BRANCH;
    
    out.println("<form name='formDataView' action='"+formActionSave+"' method='post'>");
    


    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    BranchModel branchPOSVector = (BranchModel) dataHashMap.get(DCMInterfaceKey.DCM_BRANCHES_POS_VECTOR);
    int branchID = 0 ;
    if(branchPOSVector != null)
      branchID = branchPOSVector.get_branch_id();
    Utility.logger.debug("branchID:"+branchID);
//    String branchID = (String)dataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_BRANCH_ID);
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_BRANCH_ID+"\""+
                  " value=\""+branchID+"\">"); 
    
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Name</TD>");
    out.println("      <TD><input type='text' name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME+"' id=''></TD>");
    out.println("</tr>");
    /*out.println("<TR class=TableTextNote>");
    out.println("      <TD>Description</TD>");
    out.println("      <TD><TEXTAREA name=\"\" id=\"\" cols=50 rows=5></TEXTAREA></TD>");
    out.println("</tr>");      
    
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>Start Date</TD>");
    out.println("      <TD><Script>drawCalender('name1',\"*\");</script></TD>");
    out.println("</tr>");   
    out.println("<TR class=TableTextNote>");
    out.println("      <TD>End Date</TD>");
    out.println("      <TD><Script>drawCalender('name2',\"*\");</script></TD>");
    out.println("</tr>");   
   */ 
    out.println("<TR class=TableTextNote>");
    out.println("      <TD colspan=2>");
    
    out.println("<table border=0 align='center' width='100%'>");
    
    out.println("<TR>");
    out.println("  <TD class=TableTextNote vAlign=top colSpan=2>Branches");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','branches_count','branches','UserDefinedDataViewArray'); \"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name='branches_count'>");
    out.println("<SCRIPT>  ");
    out.println("   var branches=new DeepGrid(\"branches\",0,\"\")   ");

    out.println("   branches.ColumnHeaders.add(null,null,\"POS Code\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   branches.ColumnHeaders.add(null,null,\"POS Name\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   branches.ColumnHeaders.add(null,null,\"Start Date\",null,60,\"center\",DG_READONLY,null,null,null,null)");
    out.println("   branches.ColumnHeaders.add(null,null,\"\",null,20,\"center\",DG_IMAGE,\"../resources/img/icon_calendar.gif\",null,\"onClick = callDrawCalendar(this.name); \" )    ");
    out.println("   branches.ColumnHeaders.add(null,null,\"End Date\",null,60,\"center\",DG_READONLY,null,null,null,null)");
    out.println("   branches.ColumnHeaders.add(null,null,\"\",null,20,\"center\",DG_IMAGE,\"../resources/img/icon_calendar.gif\",null,\"onClick = callDrawCalendar(this.name); \" )    ");    
    out.println("   branches.ColumnHeaders.add(null,null,\"Main Branch\",null,50,\"center\",DG_BOOLEAN,null,null,\"onclick=this.value=checkMainBranch(this.value);\")  ");
    out.println("   branches.ColumnHeaders.add(null,null,\"Sales Branch\",null,50,\"center\",DG_BOOLEAN,null,null,\"onclick=this.value=checkMainBranch(this.value);\")  ");
    out.println("   branches.ColumnHeaders.add(null,null,\"Remove\",null,50,\"center\",DG_BOOLEAN,null,null,\"onClick=  app_need_removeRows('branches')\")   ");    
    out.println("   branches.ColumnHeaders.add(null,null,\"\",null,50,\"center\",DG_HIDDEN,\"new\",null,null)   ");    
    out.println("   branches.ColumnHeaders.add(null,null,\"\",null,50,\"center\",DG_HIDDEN,\"0\",null,null)   ");        
    out.println("   data_view_RowSet_add('2','half_day','branches_count','branches','UserDefinedDataViewArray'); ");
    if(branchPOSVector != null)
    {
      out.println("document.formDataView."+DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME+".value='"+branchPOSVector.get_branch_name()+"'");
      out.println("document.formDataView.branches__R1__C1.value='"+(String)branchPOSVector.get_pos_code().get(0)+"';"); 
      out.println("document.formDataView.branches__R1__C2.value='"+(String)branchPOSVector.get_pos_name().get(0)+"';");       
      out.println("document.formDataView.branches__R1__C3.value='"+branchPOSVector.get_start_date()+"';");
      out.println("document.formDataView.branches__R1__C5.value='"+branchPOSVector.get_end_date()+"';");
      if(Integer.parseInt((String)branchPOSVector.get_main_branch().get(0)) == 0) out.println("document.formDataView.CHK__branches__R1__C7.checked=false;");
      if(Integer.parseInt((String)branchPOSVector.get_main_branch().get(0)) == 1) out.println("document.formDataView.CHK__branches__R1__C7.checked=true;");
      if(Integer.parseInt((String)branchPOSVector.get_main_sales_branch().get(0)) == 0) out.println("document.formDataView.CHK__branches__R1__C8.checked=false;");
      if(Integer.parseInt((String)branchPOSVector.get_main_sales_branch().get(0)) == 1) out.println("document.formDataView.CHK__branches__R1__C8.checked=true;");      
      out.println("document.formDataView.branches__R1__C10.value='old';"); 
      out.println("document.formDataView.branches__R1__C11.value='"+branchPOSVector.get_branchPOSID().get(0)+"';");       

    
      for(int i = 1 ; i < branchPOSVector.get_pos_code().size(); i++)
      {
          out.println("data_view_RowSet_add('2','half_day','branches_count','branches','UserDefinedDataViewArray');")      ;

         int j = i+1;
        out.println("document.formDataView.branches__R"+j+"__C1.value='"+(String)branchPOSVector.get_pos_code().get(i)+"';"); 
        out.println("document.formDataView.branches__R"+j+"__C2.value='"+(String)branchPOSVector.get_pos_name().get(i)+"';");          
        out.println("document.formDataView.branches__R"+j+"__C3.value='"+branchPOSVector.get_start_date()+"';");
        out.println("document.formDataView.branches__R"+j+"__C5.value='"+branchPOSVector.get_end_date()+"';");
        if(Integer.parseInt((String)branchPOSVector.get_main_branch().get(i)) == 0) out.println("document.formDataView.CHK__branches__R"+j+"__C7.checked=false;");
        if(Integer.parseInt((String)branchPOSVector.get_main_branch().get(i)) == 1) out.println("document.formDataView.CHK__branches__R"+j+"__C7.checked=true;");
        if(Integer.parseInt((String)branchPOSVector.get_main_sales_branch().get(i)) == 0) out.println("document.formDataView.CHK__branches__R"+j+"__C8.checked="+false+";");
        if(Integer.parseInt((String)branchPOSVector.get_main_sales_branch().get(i)) == 1) out.println("document.formDataView.CHK__branches__R"+j+"__C8.checked="+true+";");         
        out.println("document.formDataView.branches__R"+j+"__C10.value='old';"); 
      out.println("document.formDataView.branches__R"+j+"__C11.value='"+branchPOSVector.get_branchPOSID().get(i)+"';");               


      
      }
    }
    out.println("</SCRIPT>  ");  
    out.println("</td>");
    out.println("</tr>");

    out.println("</table>");

    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
    
}
%>

</body>
</html>