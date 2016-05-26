<%-- 
    Document   : change_stk_number
    Created on : 9/12/2010, 09:45:36
    Author     : Ahmed Adel
--%>

<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
          import="com.mobinil.sds.core.system.request.model.*"
%>

<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">

    </head>
<%
String appName = request.getContextPath();
 String userID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
  +InterfaceKey.HASHMAP_KEY_ACTION+"="
+SCMInterfaceKey.ACTION_CHANGE_STK_NUMBER+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
+userID;
HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

String alert=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);

%>

    <body>
        <center>
            <h2>Change STK Number</h2>
        <form name="changeSTK" action="<%=formAction%>" method="post" >
             <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">

                 <td class=TableHeader nowrap align=center >Old STK Dial Number</td>
                 <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <input type="text" name="<%=SCMInterfaceKey.OLD_STK_DIAL_NUMBER%>" id="<%=SCMInterfaceKey.OLD_STK_DIAL_NUMBER%>">
                </td>
                 <td class=TableHeader nowrap align=center >New STK Dial Number</td>
                 <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <input type="text" name="<%=SCMInterfaceKey.NEW_STK_DIAL_NUMBER%>" id="<%=SCMInterfaceKey.NEW_STK_DIAL_NUMBER%>">
            </td>
            <tr>
                <td colspan="4" align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
            <input type="button" name="Submit" value="Change STK Number" onclick="change();">
            </td>
            </tr>
             </table>
            </form>
       </center>
        <%if(alert!=null&&!alert.equals("")&&!alert.equals(null))
    {%>
        <script>
            alert("<%=alert%>");
        </script>
        <%}%>
    </body>
<script>
function change(){
    
    if(document.changeSTK.<%=SCMInterfaceKey.OLD_STK_DIAL_NUMBER%>.value=="")
    {
        alert("You Must Enter STK old Dial Number ");
        return;
    }
      if(document.changeSTK.<%=SCMInterfaceKey.NEW_STK_DIAL_NUMBER%>.value=="")
    {
        alert("You Must Enter STK New Dial Number ");
        return;
    }
    document.changeSTK.submit();
}
</script>
</html>
