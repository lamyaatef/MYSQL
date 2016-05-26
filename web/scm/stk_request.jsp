<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <TITLE>STK Request</TITLE>



   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">
<div align="center">
    <br>
    <br>
   <h2 align="center">STK Request For POS </h2>
   <br>
   <br>

<%


String appName = request.getContextPath();
HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String USER_ID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


String alert = (String)dataHashMap.get(SCMInterfaceKey.SINGLE_IMPORT_Message);

if (alert != null )
{
	out.println("<script>");
	out.println("alert(\""+alert+"\");");
	out.println("</script>");
}

String excelformAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+SCMInterfaceKey.ACTION_STK_REQUEST_EXCEL+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;

String EntryformAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+SCMInterfaceKey.ACTION_SAVE_STK_POS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;
%>

<div name="select" align="center">
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
     <TR>
     <td class=TableTextNote nowrap align=center>
      Type of Request
      </td>
      <td nowrap align=center>
<select name="inputtype" id="inputtype" onchange="show(this);">
    <option></option>
    <option value="OnebyOne">One by One</option>
    <option value="ByMass">By Excel Sheet</option>
</select>
      </td>
     </TR>
 </TABLE>
</div>




<div name ="OnebyOne" id="OnebyOne" align="center" style="display:none">
<form name="entryForm" id="entryForm" action="<%out.print(EntryformAction);%>" method="post" enctype="multipart/form-data">

    <h3 style="font:font-family:arial;color:Black;font-size:15px" align="center">Import Single STK & POS</h3>

<table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
  <tr>
      <td  class="TableTextNote" align="center">POS Code </td>
    <td   align="center">
    <input type="text" name="<%=SCMInterfaceKey.POS_CODE%>" id="<%=SCMInterfaceKey.POS_CODE%>" /></td>
    <td class="TableTextNote" align="center">STK Dial</td>
    <td align="center">
    <input type="text" name="<%=SCMInterfaceKey.STK_DIAL%>" id="<%=SCMInterfaceKey.STK_DIAL%>" /></td>
  </tr>
</table>
<br>
<br>
<center>
  <input class="button"  type="button" name="Submit" value="Save" onclick="EntryForm();">
</center>  
<input type="hidden" id=<%=SCMInterfaceKey.SINGLE_IMPORT_Message%> name=<%=SCMInterfaceKey.SINGLE_IMPORT_Message%> value=<%= alert %>/>
</form>
</div>
<div name="Excel" id="Excel" align="center" style="display: none">
<form name="excelform" action="<%out.print(excelformAction);%> "method="post" enctype="multipart/form-data">
<table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
   <input type="hidden" name="hiddenField" align="center">
   <% out.println("<input type=hidden name="+SCMInterfaceKey.IMPORT_TABLE+" value="+SCMInterfaceKey.STK_POS_TABLE+" >");%>
	
        <br><h3 style="font:font-family:arial;color:black;font-size:15px" align="center">Import Bulk STK & POS by Excel File</h3>
        <tr>
            <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:<br>(.xls or .xlsx)</font></td>
            <td align="center"><input type="file" name="myfile"></td>
        </tr>
</table>
    <center>
    <br>
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="ExcelForm();">
    </center>
    </form>
     </div>
</div>
   </BODY>
</HTML>
     
     
        

<script>
function ExcelForm()
{
    if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }

 
       document.excelform.action = document.excelform.action+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value

      document.excelform.submit();
 
  
}
function EntryForm()

{
         if(document.entryForm.<%out.print(SCMInterfaceKey.POS_CODE);%>.value=="")
        {

          alert("POS Code Cannot be Empty");

        }else if(document.entryForm.<%out.print(SCMInterfaceKey.STK_DIAL);%>.value=="")
        {

          alert("STK Dial Cannot be Empty");

        }else

        {
	document.entryForm.action= document.entryForm.action+'&'+'<%out.print(SCMInterfaceKey.POS_CODE+"");%>='+document.entryForm.<%out.print(SCMInterfaceKey.POS_CODE);%>.value+
	'&'+'<%out.print(SCMInterfaceKey.STK_DIAL+"");%>='+document.entryForm.<%out.print(SCMInterfaceKey.STK_DIAL);%>.value
	//var alertMsg = document.entryForm.getElementById(<%= SCMInterfaceKey.SINGLE_IMPORT_Message %>).value;
	
	document.entryForm.submit();
        }
}

function show(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
   if(selectedValue=="OnebyOne")
        {
            document.getElementById("OnebyOne").style.display="";
            document.getElementById("Excel").style.display="none";
        }else if(selectedValue=="ByMass"){
             document.getElementById("OnebyOne").style.display="none";
            document.getElementById("Excel").style.display="";
        }else if(selectedValue=="")
            {
              document.getElementById("OnebyOne").style.display="none";
            document.getElementById("Excel").style.display="none";
            }
}
</script>         