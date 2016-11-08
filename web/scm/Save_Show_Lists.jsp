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
    %>
        <title>Save and Show Lists</title>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
            <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
            <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/sorttable.js" type="text/javascript"></SCRIPT>
     <script language=javascript type='text/javascript'>
        function goToLink()
        {
            /*document.GenerateSheet.action="com.mobinil.sds.web.controller.WebControllerServlet?";
            document.GenerateSheet.action=document.GenerateSheet.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=AdministrationInterfaceKey.ACTION_SAVE_PAYMENT_LEVEL_HISTORY%>';*/
    alert("hi");        
    document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SAVE_PAYMENT_LEVEL_HISTORY);%>'                                                            
    alert(document.GenerateSheet.action.value);        
    document.GenerateSheet.submit();
            
        }
    </script>
    </head>
   
   
    <body>
        <h1 style="color:blue;">Lists</h1>
        <form action="" name="GenerateSheet" id="GenerateSheet" method="post">
    <center>
        
    
    <a href="#" onclick="goToLink();">Save Payment Level</a>
        &nbsp;
        &nbsp;
        <br>
        <a href="#" onclick="goToLink();" >Show Payment Level Filessssss</a>
        &nbsp;
        &nbsp;
        <br>
    </center>
    </form>
        <a href="">Save Payment Level</a>
        &nbsp;
        &nbsp;
        <br>
        <a href="../sa/Save_Payment_Level_History.jsp">Show Payment Level Files</a>
        &nbsp;
        &nbsp;
        <br>
        <a href="">Save List of the Month</a>
        &nbsp;
        &nbsp;
        <br>
        <a href="">Show List of the Month Files</a>
        &nbsp;
        &nbsp;
        <br>
        <a href="">Save for a POS Code</a>
        &nbsp;
        &nbsp;
        <br>
        <a href="">Show POS Code Files</a>
        &nbsp;
        &nbsp;
        <br>
    </body>
</html>
