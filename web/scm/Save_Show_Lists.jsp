<%-- 
    Document   : Save_Show_Lists
    Created on : Nov 8, 2016, 5:06:28 PM
    Author     : sand
--%>

<%@page import="java.util.HashMap"%>
<%@page import="com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
         <%
        HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String appName = request.getContextPath();
        String userID = "";
   System.out.println("/////////Save&Show JSP////////");
   String str1 = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
   System.out.println("str1//////// "+str1);
   String str2 = (String)dataHashMap.get("USER_ID");
   System.out.println("str2//////// "+str2);
   String str3="";
   if(request.getSession()!=null)
        str3 = (String) request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
   System.out.println("str3//////// "+str3);
   System.out.println("in Save&Show JSP : HASHMAP_KEY_USER_ID "+str1+" and USER_ID "+str2+" session HASHMAP_KEY_USER_ID "+str3);
   
   if(str1==null)
   {
       if(str2==null)
       {
           if(str3==null || str3.compareTo("")==0)
           {
               userID = "";
           }
           else
               userID = str3;
       }
       else
           userID = str2;
   }
   else
       userID = str1;
       
   System.out.println("user id in Save&Show jsp is Finally : "+userID);
   dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, userID);
   
        /*String user_id = request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID).toString();
        System.out.println("User Id in Save&Show List "+user_id);
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, user_id);*/
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
            if(actionName=="show_pos_list")
                document.GenerateSheet.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(AdministrationInterfaceKey.ACTION_SHOW_CROSSTAB_LIST);%>'  ;                                                          
            
            
            
            
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
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
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
        <a class="sds-links" href="#" onclick="goToLink('show_pos_list');">Show POS Code List Files</a>
        &nbsp;
        &nbsp;
        <br>
    </center>
    </form>
        
    </body>
</html>
