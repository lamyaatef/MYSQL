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
      <TITLE>Data Import</TITLE>



   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">

       <div align="center">
           <br>
           <br>
              <h2 align="center">Delete STKs Stock Management</h2>
              <br>
              <br>
<script>
function submitForm()
{
   if(document.myform.myfile.value.lastIndexOf(".xls")==-1&&document.myform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }

  document.myform.action = document.myform.action+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.myform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value+'&'+'<%out.print(SCMInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(SCMInterfaceKey.CONTROL_IMPORT_OPERATION);%>.value+'&'+'<%out.print(SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE+"=");%>'+document.myform.<%out.print(SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE);%>.value
  document.myform.submit();

}
</script>
<%
String appName = request.getContextPath();
HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
HashMap <String,String> HMstocks = (HashMap <String,String>)dataHashMap.get(SCMInterfaceKey.HASHMAP_STOCKS_);
String USER_ID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+SCMInterfaceKey.ACTION_IMPORT_STK_SHEET_PROCESS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;
%>


<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">


<%
    out.println("<center>");
    out.println("<table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=3 cellPadding=3 width=\"80%\" border=1>");
    out.println("<tr>");
    out.println("<td class=TableTextNote nowrap align=center>");
    out.println("Excel Delete File ");
    out.println("</td>");
    out.println("<td nowrap align=center>");
%>
   <input type="hidden" name="hiddenField">
   <% out.println("<input type=hidden name="+SCMInterfaceKey.IMPORT_TABLE+" value="+SCMInterfaceKey.QUERY_STK_TABLE+" >");%>
   <% out.println("<input type=hidden name="+SCMInterfaceKey.CONTROL_IMPORT_OPERATION+" value="+SCMInterfaceKey.DELETE_OPERATION+" >");%>
    <input type="file" name="myfile">
<%
    out.println("</td>");
    out.println("</tr>");
    
%>
<tr>
    <td class=TableTextNote nowrap align=center>
    Stock Type
    </td>
    <td nowrap align=center>
        <select id="<%=SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE %>" name="<%=SCMInterfaceKey.CONTROL_SELECT_STOCK_TYPE %>" style="width: 230px" >

            <%
            if (HMstocks != null && !HMstocks.isEmpty()) {
                            for (String key : HMstocks.keySet()) {
                                out.println("<option value=\""+key+"\">"+HMstocks.get(key)+"</option>");
                            }
                        }

%>
        </select>
    </tr>
    </table>


    <br>
    <br>
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="submitForm();">
   </form>
       </div>
   </BODY>
</HTML>
