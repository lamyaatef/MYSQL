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
          import = "com.mobinil.sds.core.system.dcm.group.model.*"          
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
  </SCRIPT>
<body>
      
    <%
    showPOSRelationDetails(request, response, out);
    %>        
    <center>
  <%      
    out.println("<input class='button' type='button' value='Save' onclick=\"checkbeforSubmit();document.formDataView."+
                  InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DCMInterfaceKey.ACTION_DCM_SAVE_GROUP_POS+"';formDataView.submit();\">");
  %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </form>
    </center>
 
<%!
  public void showPOSRelationDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  { 
    
    String appName = request.getContextPath();
    
    out.println("<CENTER>");
    out.println("  <H2>Assign POS To POS Group</H2>");
    out.println("</CENTER>");
  
    
    HashMap dataHashMap = new HashMap(100);
    
    out.println("<form name='formDataView' action='' method='post'>");

    

    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    GroupModel groupModel = (GroupModel)dataHashMap.get(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL);
    String strErr = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    if(!strErr.equals("") && strErr != null)
      out.println("<script language='javascript'> alert('ERROR :"+strErr+"'); </script>");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+groupModel.getGroupID()+"\">");  
    

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("    <TR class=TableHeader>");
    out.println("      <TD align='center'>Group Name</TD>");
    out.println("      <TD align='center'>Description</TD>");
    out.println("      <TD align='center'>Status</TD>");
    out.println("    </tr>");
    out.println("    <TR>");
    out.println("      <TD align='center'>"+groupModel.getGroupName()+"</TD>");
    out.println("      <TD align='center'>"+groupModel.getGroupDescription()+"</TD>");
    out.println("      <TD align='center'>"+groupModel.getGroupStatusName()+"</TD>");
    out.println("    </tr>");
    out.println("  </table>  ");
    out.println("  <br>");

    
    out.println("<table border=0 align='center' width='40%'>");
    
    out.println("<TR>");
    out.println("  <TD class=TableHeader vAlign=top colSpan=2>POSs");
    out.println("    <A onclick=\"data_view_RowSet_add('2','half_day','pos_count','pos','UserDefinedDataViewArray');\"><IMG"); 
    out.println("    alt=\"Click Here to add New Row for Data view ... \" "); 
    out.println("    src=\"../resources/img/img_sign_dn.gif\" border=0></A> "); 
    out.println("  </TD>");
    out.println("</TR>");


    out.println("<tr>");
    out.println("<td colspan=2><INPUT type=hidden size=15 value=0 name=pos_count>");
    out.println("<SCRIPT>  ");
    out.println("   var pos=new DeepGrid(\"pos\",0,\"\")   ");
    out.println("   pos.ColumnHeaders.add(null,null,\"POS Code\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   pos.ColumnHeaders.add(null,null,\"POS Name\",null,150,\"center\",DG_TEXT,null,null,null)   ");
    out.println("   pos.ColumnHeaders.add(null,null,null,null,150,\"center\",DG_HIDDEN,\"new\",null,null)   ");
    out.println("   pos.ColumnHeaders.add(null,null,\"Remove\",null,60,null,DG_BOOLEAN,null,null,\"onClick = app_need_removeRows('pos')\")   ");
    out.println("   pos.ColumnHeaders.add(null,null,\"\",null,0,\"center\",DG_HIDDEN,null)   ");
    out.println("</SCRIPT>  ");  
    for(int i = 0 ; i < groupModel.getGroupPOSNames().size() ; i++)
    {
        out.println("<SCRIPT>  ");
      out.println("data_view_RowSet_add('2','half_day','pos_count','pos','UserDefinedDataViewArray');");
      int j = i+1;

      out.println("document.formDataView.pos__R"+j+"__C1.value='"+groupModel.getGroupPOSCodes().get(i)+"';");
      out.println("document.formDataView.pos__R"+j+"__C2.value='"+groupModel.getGroupPOSNames().get(i)+"';");
      out.println("document.formDataView.pos__R"+j+"__C3.value='old';");
      out.println("</SCRIPT>  "); 
      out.println("<input type='hidden' name='pos__R"+j+"C__6' value='"+groupModel.getGroupPOSIDs().get(i)+"'>");
            
      }
 
    out.println("</td>");
    out.println("</tr>");
    
    out.println("</table>");
}
%>

</body>
</html>