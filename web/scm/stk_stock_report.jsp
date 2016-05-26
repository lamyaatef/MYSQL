<%--
    Document   : import_pos_cbill
    Created on : Oct 20, 2010, 8:24:47 AM
    Author     :  AHMED ADEL & AHMED SAFWAT
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
            Vector<STKReportModel> assignedSTKFromStock=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ASSIGNED_STK_FROM_STOCK);
            Vector<STKReportModel> importedSTKInStock=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_IMPORTED_STK_FROM_STOCK);
            Integer quantityRemainingInStock=(Integer)dataHashMap.get(SCMInterfaceKey.STK_QUANTITY_REMAINING);
            System.out.println("jsp quantityRemainingInStock is "+quantityRemainingInStock);
            String stockId=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_HIDDEN_STOCK_ID);
           String excelAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
+InterfaceKey.HASHMAP_KEY_ACTION+"="
+SCMInterfaceKey.EXPORT_EXCEL_IN_STOCK_STK;

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>STK STOCK REPORT</title>
   </head>
    <body>
        
        <div align="center">
            <br>
            <br>
            <h2>STK STOCK REPORT</h2>
            <br>
            <br>
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

            <tr>
                <td class=TableHeader nowrap align=center ><font style="font-size: 15px;font-family: tahoma;line-height: 15px"><b>Quantity Remaining In The Stock</b></font></td>
            </tr>
            <tr>
                <td align=center ><%=quantityRemainingInStock==null?0:quantityRemainingInStock.intValue()%></td>
            </tr>
        </table>
            <%if(quantityRemainingInStock>0){%>
            <center>
                <form name="exportexcel" method="post" action="<%=excelAction%>">
                    <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_HIDDEN_STOCK_ID%>" id="<%=SCMInterfaceKey.CONTROL_HIDDEN_STOCK_ID%>" value="<%=stockId%>">
                    <input type="submit" name="export" value="Export dials in stock ">
                </form>
            </center>
              <%}%>
            <br>
            <h3>Assigned STK In the Stock</h3>
            <%
       if(assignedSTKFromStock!=null&&!assignedSTKFromStock.isEmpty()){

                                           %>

                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr><td class=TableHeader nowrap align=center >Date</td><td  class=TableHeader nowrap align=center>User Mail</td><td class=TableHeader nowrap align=center>Quantity</td></tr>

                    <%

                                for (int i = 0; i < assignedSTKFromStock.size(); i++) {
                                    STKReportModel stkReportModel = (STKReportModel) assignedSTKFromStock.get(i);
                    %>
    <pg:item>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=stkReportModel.getDate().getDate()%>/<%=stkReportModel.getDate().getMonth()+1%>/<%=stkReportModel.getDate().getYear()+1900%></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=stkReportModel.getUserMail() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=stkReportModel.getQuantity() %></td></tr>
   </pg:item>
                    <%
                                }

                    %>

                </table>
                <%}else{
                    out.print("No Assigned STK.");
                }

                %>

                <br>
                <br>
                <h3>Imported STK in the Stock</h3>
                            <%
       if(importedSTKInStock!=null&&!importedSTKInStock.isEmpty()){
                                                %>
                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    <tr><td class=TableHeader nowrap align=center >Date</td><td  class=TableHeader nowrap align=center>User Mail</td><td class=TableHeader nowrap align=center>Quantity</td></tr>

                    <%

                                for (int i = 0; i < importedSTKInStock.size(); i++) {
                                    STKReportModel stkReportModel = (STKReportModel) importedSTKInStock.get(i);
                    %>


                    <tr><td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=stkReportModel.getDate().getDate()%>/<%=stkReportModel.getDate().getMonth()+1%>/<%=stkReportModel.getDate().getYear()+1900%></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=stkReportModel.getUserMail() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=stkReportModel.getQuantity() %></td></tr>
                    <%
                                }

                    %>
                </table>
                <%}else{
                    out.print("No Imported STK.");
                }

                %>
        </div>


    </body>
</html>
