<%-- 
    Document   : newjsp
    Created on : 23/12/2010, 16:27:57
    Author     : Ahmed Adel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.commission.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              import="com.mobinil.sds.core.system.commission.model.*"
              import="com.mobinil.sds.core.system.scm.dao.*"
              import="java.util.*"%>
 <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Vector <RatedFileModel> filesId=(Vector) dataHashMap.get(CommissionInterfaceKey.VECTOR_RATED_FILES);
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
        <h2>Management Rated Activity Files</h2>
        <br>

        <% if (filesId.size()!=0&&filesId!=null)
        {%>
        <form name="requests" action="<%=action%>" method="post">
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
                <tr>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Request ID</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Day From</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Month From</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Year From</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Day To</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Month To</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Year To</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Start Time</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">End Time</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Total Time Of Processing</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Delete</font></td>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">View Detail</font></td>
                </tr>
             <% for (int i=0 ; i<filesId.size() ; i++) {%>
                <tr>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Integer.toString(filesId.get(i).getFileId())%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=filesId.get(i).getDayFrom()%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=GenericDAO.getMonthName(filesId.get(i).getMonthFrom())%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=filesId.get(i).getYearFrom()%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=filesId.get(i).getDayTo()%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=GenericDAO.getMonthName(filesId.get(i).getMonthTo())%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=filesId.get(i).getYearTo()%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=filesId.get(i).getStartTime().toString()%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%if(filesId.get(i).getEndTime()!=null){out.print(filesId.get(i).getEndTime().toString());}else{%>Not finished yet<%}%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%if(filesId.get(i).getTotal()!=null){out.print(filesId.get(i).getTotal().toString()+" Sec");}else{%>Not finished yet<%}%></td>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="button" class="button"  name="delete" id="delete" <%if(filesId.get(i).getFlag().equals("1")||filesId.get(i).getFlag().equals("3")){%> disabled value="This File Cannot delete" <%}else{%>  value="Delete"<%} %> onclick="deletefile(<%=filesId.get(i).getFileId()%>)">
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="button" class="button"  name="error" value="View Error Report" <%if(filesId.get(i).getFlag().equals("1")){%> disabled <%}%>onclick="viewdetail(<%=filesId.get(i).getFileId()%>)">
                    </td>
             </tr>
             <%}%>
        </table>
        </form>
       <% }else {%>
                 <h2> There's No Files</h2>
                 <%}%>
                 </center>
        </body>
<script>
function deletefile (i)
{
    var id=i;
    document.requests.action="com.mobinil.sds.web.controller.WebControllerServlet?";
    document.requests.action=document.requests.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=CommissionInterfaceKey.ACTION_DELETE_RATED_ACTIVITY%>'+'&'+
     '<%=CommissionInterfaceKey.RATED_FILE_ID%>'+'='+id
      document.requests.submit();
}
function viewdetail (i)
{
    var id=i;
    document.requests.action="com.mobinil.sds.web.controller.WebControllerServlet?";
    document.requests.action=document.requests.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=CommissionInterfaceKey.ACTION_VIEW_RATED_ACTIVITY%>'+'&'+
     '<%=CommissionInterfaceKey.RATED_FILE_ID%>'+'='+id
      document.requests.submit();
}
</script>


</html>
