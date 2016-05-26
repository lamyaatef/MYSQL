<%@ page contentType="text/html;charset=windows-1252"
              import="com.mobinil.sds.core.utilities.Utility"
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
      <TITLE>STK report Type</TITLE>

   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">
       <div align="center">
   <br>
   <br>
   <h2 align="center">STK Stock report</h2>
   <br>
   <br>
<script>
function submitForm()
{   
  document.myform.submit();  
}
</script>
<%
String appName = request.getContextPath();
HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
HashMap <String,String> HMstocks = (HashMap <String,String>)dataHashMap.get(SCMInterfaceKey.HASHMAP_STOCKS_);
String USER_ID =(String)request.getSession(false).getValue(InterfaceKey.HASHMAP_KEY_USER_ID);
System.out.println("USER_ID from session is "+USER_ID);
String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+SCMInterfaceKey.ACTION_STK_STOCK_REPORT+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;
%>


<form name="myform" action="<%out.print(formAction);%>" method="post">
 

<%
    out.println("<center>");
    
%>
<table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1>
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
    <input class="button" type="button" name="Submit" value="Submit" onclick="submitForm();">
   </form>
       </div>
   </BODY>
</HTML>
              