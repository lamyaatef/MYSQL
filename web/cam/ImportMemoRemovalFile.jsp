<%--
    Document   : Attachment
    Created on : 05/01/2009, 03:09:04 م
    Author     : samuel atef
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.memo.model.*"
         import="com.mobinil.sds.core.system.sa.importdata.model.*"
         %>
<%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Vector memos=(Vector)dataHashMap.get(MemoInterfaceKey.VECTOR_ALL_MEMOS);
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +MemoInterfaceKey.ACTION_IMPORT_MEMO_REMOVAL_FILE_PROCESS +
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                     +strUserID;
                  
                //    +strChannelId;
%>    

<script>

function submitForm()
{

  
  var tableId = document.myform.<%out.print(MemoInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value;
  if (tableId=='')
  {
  alert('Please Select a Table');
  return;
  }

  var operation = document.myform.<%out.print(MemoInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value;
  if (operation =='')
  {
  alert('Please Select an Operation');
  return;
  }

    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    if(fileName != ".xls")
    {
      alert("Invalid input. Please specify a right excel file.");
      return;
    }
  
  document.myform.action = document.myform.action+'&'+'<%out.print(MemoInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(MemoInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(MemoInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(MemoInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value
  document.myform.action = document.myform.action+'&'+'memoID='+document.getElementById('<%=MemoInterfaceKey.CONTROL_MEMO_ID%>').value;
  
   
  //document.myform.submit.disabled = true;
  
  document.myform.submit();
  
}
function toggleChange()
{
// document.getElementById("3").style.display='';
 document.getElementById("Import").disabled=false;
 //alert(document.getElementById('/</%=MemoInterfaceKey.CONTROL_MEMO_ID%>').value)
 jsmemoID=document.getElementById('<%=MemoInterfaceKey.CONTROL_MEMO_ID%>').value;
 //alert(jsmemoID);
 
}
function alertNoMemos()
{
    alert("There is No any READY Memos in the system");
}
</script>
<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Memo Removal File Import</TITLE>
      <center>
      <h2>Memo Removal File Import</h2>
      </center>
   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">

<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">


            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
                <tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
                  
                <br>
                 <tr class="TableHeader">
        <td>Choose memo</td>
        <td>
            <select name="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>" id="<%=MemoInterfaceKey.CONTROL_MEMO_ID%>" onchange="javaScript:toggleChange();">
            <option value=""></option>
            <%
             for(int k=0;k<memos.size();k++){
            	 MemoModel m=(MemoModel) memos.get(k);
            %>
             <option value="<%=m.getId()%>"><%=m.getName()%></option>
             <%}%>
             </select>
             <%if(memos.size()==0){ %>
             <script>
             alertNoMemos();
             </script>
             <%} %>
            </td>
        </tr>
    <%         
                  
    Vector tableVec = (Vector) dataHashMap.get(MemoInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

  //  out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
      out.println("<TR style='display:none'>");
   // out.println("<td class=TableHeader nowrap align=center>");
    //out.println("Table");
     out.println("</TD>");       
    out.println("<Select name="+MemoInterfaceKey.CONTROL_DATA_IMPORT_TABLES+" style=\"Display:none\">");
    for(int k= 0; k<tableVec.size(); k++)
    {
      DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    }
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");
    //out.println("<TR>");
    //out.println("<td class=TableHeader nowrap align=center>");
   // out.println("Operation");
   // out.println("</TD>");        
   // out.println("<td nowrap align=left>");
    out.println("<Select name="+MemoInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+" style=\"Display:none\">");
    out.println("<option></option>");
    out.println("<option value=\""+MemoInterfaceKey.DATA_IMPORT_INSERT+"\" selected>"+MemoInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    //out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");


    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("File");
    out.println("</td>");
    out.println("<td nowrap align=left>");
%>
   <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
<%
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
%>
<BR>
<BR>
<BR>
    <center> 
    
    <input type="button" disabled="disabled" class="button" name="Import" id="Import"  value="Import" onclick="submitForm();"/>
   
    </center>
    </form>
   </BODY>
</HTML>