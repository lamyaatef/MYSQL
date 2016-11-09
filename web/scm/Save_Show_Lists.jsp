<%-- 
    Document   : Save_Show_Lists
    Created on : Nov 8, 2016, 5:06:28 PM
    Author     : sand
--%>

<%@page import="com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
         <%
        String appName = request.getContextPath();
        String user_id = request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID).toString();
        System.out.println("User Id "+user_id);
    %>
        <title>Save and Show Lists</title>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
            <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
            
            <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/sorttable.js" type="text/javascript"></SCRIPT>
     <script language=javascript type='text/javascript'>
        function goToLink(actionName)
        {
            if(actionName=="save_payment")
                document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SHOW_PAYMENT_LEVEL_HISTORY);%>';                                                            
            if(actionName=="show_payment")
                document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SHOW_HISTORY_FILE);%>';                                                            
            if(actionName=="save_list_month")
                document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SAVE_LIST_MONTH);%>' ;                                                           
            if(actionName=="show_list_month")
                document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SHOW_LIST_OF_THE_MONTH);%>'  ;                                                          
            if(actionName=="show_pos")
                document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SHOW_CROSSTAB);%>'  ;                                                          
            
            document.GenerateSheet.submit();
            
        }
    </script>
    </head>
   
   
    <body>
        <br>
        <br>
        <div align="center">
        <h2>Lists</h2></div>
        <br>
        <br>

        <form action="" name="GenerateSheet" id="GenerateSheet" method="post">
    <center>
        
      &nbsp;
        &nbsp;
        <br>
    <a class="sds-links" href="#" onclick="goToLink('save_payment');">Save Payment Level</a>
        &nbsp;
        &nbsp;
        <br>
        <a class="sds-links" href="#" onclick="goToLink('show_payment');">Show Payment Level File</a>
        &nbsp;
        &nbsp;
        <br>
        <a class="sds-links" href="#" onclick="goToLink('save_list_month');">Save List of the Month</a>
        &nbsp;
        &nbsp;
        <br>
        <a class="sds-links" href="#" onclick="goToLink('show_list_month');">Show List of the Month Files</a>
        &nbsp;
        &nbsp;
        <br>
        <a class="sds-links" href="#" onclick="goToLink('show_pos');">Show POS Code Files</a>
        &nbsp;
        &nbsp;
        <br>
    </center>
    </form>
        
    </body>
</html>
