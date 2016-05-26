<%--
    Document   : import_pos_cbill
    Created on : Oct 20, 2010, 8:24:47 AM
    Author     :  AHMED ADEL
--%>
<%@
page contentType="text/html;charset=windows-1252"
     import="java.util.*"
     import="com.mobinil.sds.web.interfaces.*"
     import="com.mobinil.sds.web.interfaces.scm.*"
     import="com.mobinil.sds.core.system.scm.dao.*"
     import="com.mobinil.sds.core.system.scm.model.*"
     %>
<%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            Vector<POSStatusHistory> POSStatusHistory=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_POS_STATUS_HISTORY);
            Vector<POSPaymentStatusHistory> POSPaymentStatusHistory=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_POS_PAYMENT_HISTORY);
            String showHistoryAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.GET_POS_STATUS_HISTORY;
            String message=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>POS STATUS/PAYMENT HISTORY</title>
   </head>
    <body>
        <h2 align="center">
            <br>
            <br>
            POS Status/Payment History
            <br>
            <br>
        </h2>
    <div align="center">
    <form name="showhistory" id="showhistory" action="<%=showHistoryAction%>"  method="post">
  <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
    <tr>
    <td class="TableTextNote" nowrap align=center >POS Code:</td>
    <td  nowrap align=center colspan="2">
    <input type="text" name="<%=SCMInterfaceKey.POS_CODE%>" value="<%=dataHashMap.get(SCMInterfaceKey.POS_CODE)%>">
    </td>
    </tr>
    <tr>
        <td nowrap align=center colspan="3">
        <input type="button" class="button" name="Submit" onclick="poshistory();" value="show">
        </td>
    </tr>


  </table>
    </form>
 </div>




    <div id="showResult" align="center" style="display: none">

        <h2 align="center"><%=POSDAO.getPOSNameFromCode((String)dataHashMap.get(SCMInterfaceKey.POS_CODE))%></h2>
            <h2>View POS Change Status History</h2>
            <%
       if(POSStatusHistory!=null&&POSStatusHistory.size()!=0){
                                                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr><td class=TableHeader nowrap align=center >Date</td>
                    <td  class=TableHeader nowrap align=center>User Name</td>
                    <td class=TableHeader nowrap align=center>Status</td>
                    <td class=TableHeader nowrap align=center>Reason</td></tr>

                    <%

                                for (int i = 0; i < POSStatusHistory.size(); i++) {
                                    POSStatusHistory POSStatusHistoryModel = (POSStatusHistory) POSStatusHistory.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSStatusHistoryModel.getChangeDate().getDate()%>/<%=POSStatusHistoryModel.getChangeDate().getMonth()+1%>/<%=POSStatusHistoryModel.getChangeDate().getYear()+1900%></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSStatusHistoryModel.getUserName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSStatusHistoryModel.getStatusName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSStatusHistoryModel.getReason()%></td></tr>
                    <%
                                }

                    %>
                </table>
                <%}else{
                    out.print("No Change Status History for this POS.");
                }

                %>

                <br>
                <br>
                <h2>View POS Payment Status History</h2>
                            <%
       if(POSPaymentStatusHistory!=null&&POSPaymentStatusHistory.size()!=0){
                                                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr><td class=TableHeader nowrap align=center >Date</td>
                        <td  class=TableHeader nowrap align=center>User Name</td>
                        <td class=TableHeader nowrap align=center>Status</td>
                        <td class=TableHeader nowrap align=center>Reason</td>
                    </tr>

                    <%

                                for (int i = 0; i < POSPaymentStatusHistory.size(); i++) {
                                     POSPaymentStatusHistory POSPaymentStatusHistoryModel = (POSPaymentStatusHistory) POSPaymentStatusHistory.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSPaymentStatusHistoryModel.getChangeDate().getDate()%>/<%=POSPaymentStatusHistoryModel.getChangeDate().getMonth()+1%>/<%=POSPaymentStatusHistoryModel.getChangeDate().getYear()+1900%></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSPaymentStatusHistoryModel.getUserName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSPaymentStatusHistoryModel.getStatusName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=POSPaymentStatusHistoryModel.getReason()%></td>
                    </tr>
                    <%
                                }

                    %>
                </table>
                <%}else{
                    out.print("No Change Payment Status History for this POS.");
                }

                %>
        </div>
        <%if (message.equals("Data")){ %>
        <script>
            document.getElementById("showResult").style.display="";
        </script>
        <%}%>
    </body>
    <script>
        function poshistory()
        {
             if(document.showhistory.<%out.print(SCMInterfaceKey.POS_CODE);%>.value=="")
        {

          alert("POS Code Cannot be Empty");
          return false;
        }
        document.showhistory.submit();
        }
    </script>



</html>
