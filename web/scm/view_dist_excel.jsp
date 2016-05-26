<%-- 
    Document   : view_stk_dist_excel
    Created on : 13/02/2011, 16:42:42
    Author     : Mahmoud Abdel Aal
--%>

<%@page import="com.mobinil.sds.core.system.scm.dto.STKDistRequestViewerDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"
           import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
               import="java.util.*"%>
 <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Vector <STKDistRequestViewerDTO> Requests=(Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_DIST_REQUESTS);
            String action=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        
    </head>
    <body>
        <center>
        <br>
        <h2>Dist Requests </h2>
        <br>

        <% if (Requests.size()!=0&&Requests!=null)
        {%>
        <form name="requests" action="<%=action%>" method="post">
            
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                <tr>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Request ID</font></td>
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Request Date</font></td>
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Dist Name</font></td>
<!--             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Download Count</font></td>-->
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Download Excel</font></td>
                </tr>
             <% for (int i=0 ; i<Requests.size() ; i++) {%>
                <tr>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getStkDistRequestModel().getREQUEST_ID() %></td>
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getStkDistRequestModel().getUPDATE_IN() %></td>
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getDCM_NAME() %></td>
<!--             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getStkDistRequestModel().getNO_OF_DOWN() %></td>-->
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="button" class="button" name="button" value="download" onclick="excel(<%=Requests.get(i).getStkDistRequestModel().getREQUEST_ID()%>)">
                 </td>
             </tr>
             <%}%>
        </table>
        </form>
                 
       <% }else {%>
                 <h2> There's No Request</h2>
                 <%}%>
                 </center>
        </body>
<script>
function excel (i)
{
    var id=i;
    document.requests.action="com.mobinil.sds.web.controller.WebControllerServlet?";
    document.requests.action=document.requests.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.DOWNLOAD_DIST_REQUEST_EXCEL%>'+'&'+
     '<%=SCMInterfaceKey.DIST_REQUEST_ID%>'+'='+id
      document.requests.submit();
      //setTimeout("location.reload(true);",4000);
}
</script>


</html>
