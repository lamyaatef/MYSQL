<%-- 
    Document   : ivr_task
    Created on : 26/12/2010, 16:40:14
    Author     : Ahmed Adel
--%>
<%@ page contentType="text/html;charset=windows-1252"
              import="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.te.*"
              import ="java.io.*"
              %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
String appName = request.getContextPath();
HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String USER_ID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+TaskInterfaceKey.ACTION_SAVE_IVR_TASK +"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+USER_ID;
%>
    <body>
        <center>
        <h2>Create IVR Task</h2>
    
        <form name="create" action="<%=formAction%>" method="post" >
            <br>
            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="30%" border=1>
            <tr>
            <td>Task Name</td>
            <td>
            <input type="text" name="<%=TaskInterfaceKey.CONTROL_IVR_TASK_NAME%>">
            </td>
            </tr>
            </table>
            <br>
            <input type="submit" value="add">
            
        </form>
        </center>


    </body>
</html>
