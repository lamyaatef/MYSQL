<%-- 
    Document   : download_barcode_excel
    Created on : 14/12/2010, 17:28:38
    Author     : Ahmed Adel
--%>


<%@page import="com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey"%>
<%@page import="java.sql.Blob"%>
<%@page import="com.mobinil.sds.core.system.scm.dto.STKDistRequestViewerDTO"%>


<%@ page      import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              import="com.mobinil.sds.core.system.scm.dao.*"
              %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
            <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <body>
       <%
         HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = "";
   
   String str1 = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
   String str2 = (String)dataHashMap.get("USER_ID");
   String str3 = (String) request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
   
   System.out.println("in EXPORT Payment History JSP : HASHMAP_KEY_USER_ID "+str1+" and USER_ID "+str2+" session HASHMAP_KEY_USER_ID "+str3);
   
   if(str1==null)
   {
       if(str2==null)
       {
           if(str3==null)
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
       
   System.out.println("user id in EXPORT payment history jsp is Finally : "+userID);
   dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, userID);
   
         
         
         
         String Slach = System.getProperty("file.separator");
    String fileName = (String) dataHashMap.get(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK);
String filePath = request.getRealPath("/scm/upload")+Slach+fileName;

    
dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, fileName==null ? "" : (String) dataHashMap.get(SCMInterfaceKey.SEARCH_EXCEL_SHEET_LINK));
dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "scm"+Slach+"upload"+Slach);
session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);

        %>

<form action="com.mobinil.sds.web.controller.UploadingFileServlet" name="GenerateSheet" id="GenerateSheet" method="post">
    <center>
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
        
        <br><br><br><br><br><br><br><br><br><br><br><br><br>
    <input id="bckButton" name="bckButton" class='button' type='button' value='Back' onclick="back();"/>
    </center>
    </form>
    </body>
    <script>
        function back ()
        {
            document.GenerateSheet.action="com.mobinil.sds.web.controller.WebControllerServlet?";
            document.GenerateSheet.action=document.GenerateSheet.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=AdministrationInterfaceKey.ACTION_SHOW_HISTORY_FILE%>';
                                                                        
            document.GenerateSheet.submit();
            
        }
    </script>
    <script>
        document.GenerateSheet.submit();
        

    </script>
</html>
