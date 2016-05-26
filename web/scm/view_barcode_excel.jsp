<%-- 
    Document   : view_barcode_excel
    Created on : 14/12/2010, 16:42:42
    Author     : Ahmed Adel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"
           import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
               import="java.util.*"%>
 <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String distinationPage=(String)dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
            String totalPageNumbers=(String)dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
            int totalPagesInt = totalPageNumbers !=null && totalPageNumbers.compareTo("")!=0 ? Integer.parseInt(totalPageNumbers) : 0;
            Vector <BarcodePOSRequestModel> Requests=(Vector) dataHashMap.get(SCMInterfaceKey.VECTOR_BARCODE_REQUESTS);
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
        <h2>BarCodes Requests </h2>
        <br>

        <% if (Requests.size()!=0&&Requests!=null)
        {%>
        <form name="requests" action="<%=action%>" method="post">
            
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                <tr>
            <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Request ID</font></td>
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Request Date</font></td>
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Requester Name</font></td>
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Download Count</font></td>
             <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Download Excel</font></td>
                </tr>
             <% for (int i=0 ; i<Requests.size() ; i++) {%>
                <tr>
                    <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getRequestId()%></td>
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getDate()%></td>
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getPersonName() %></td>
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=Requests.get(i).getNoOfDownload() %></td>
             <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="button" class="button" name="button" value="download" onclick="excel(<%=Requests.get(i).getRequestId()%>,'<%=Requests.get(i).getPersonName()%>')">
                 </td>
             </tr>
             <%}%>
        </table>
        <%if (totalPagesInt>0){%>
        <div align="center">
                  <jsp:include page="pagingTable.jsp"  flush="true" >
                                      <jsp:param   name="action_name_when_click_enter" value="view_barcoderequest_excel"/>
                                      <jsp:param   name="first_page_number" value="0"/>
                                      <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                                      <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


                </jsp:include>
                      </div>
        <%}%>
        </form>
                 
       <% }else {%>
                 <h2> There's No Request</h2>
                 <%}%>
                 </center>
        </body>
<script>
function excel (i,pname)
{
    var id=i;
    document.requests.action="com.mobinil.sds.web.controller.WebControllerServlet?";
    document.requests.action=document.requests.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.DOWNLOAD_BARCODEREQUEST_EXCEL%>'+'&'+
     '<%=SCMInterfaceKey.BARCODE_REQUEST_ID%>'+'='+id+'&'+'<%=SCMInterfaceKey.BARCODE_PERSON_NAME %>'+'='+pname
      document.requests.submit();
      setTimeout("location.reload(true);",4000);
}

 function DevChangePageActionWithSubmit(action)
    {
document.requests.action="com.mobinil.sds.web.controller.WebControllerServlet?";
    document.requests.action=document.requests.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+action;
      <%--document.requests.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value =  document.requests.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value =action--%>
    document.requests.submit();
    }

</script>


</html>
