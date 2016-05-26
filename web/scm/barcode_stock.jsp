<%-- 
    Document   : barcode_stock
    Created on : 28/10/2010, 14:52:23
    Author     : Ahmed Adel
--%>

<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>
<%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
            String formName="addNewStock";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>BarCode Stock</title>
        <script>
            function importBarcodes()
            {

                quantity=document.<%=formName%>.<%=SCMInterfaceKey.BARCODE_STOCK_IMPORT_QUANTITY %>.value;

                 if(quantity=="" ){
                    alert("Please Enter a Quantity");
                    return;
                }
                if(isNaN(quantity)){
                    alert("Quantity must be a number");
                    return;
                }
                if(quantity<=0){
                    alert("Quantity must be a positive number");
                    return;
                }
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_BARCODE_STOCK_IMPORT%>";
                document.<%=formName%>.submit();


            }
        </script>


    </head>
    <body>
        <br>
        <br>
        <div align="center">
        <h2>Barcode Stock</h2>
        <br>
        <br>
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

            <tr>
                <td class=TableHeader nowrap align=center ><font style="font-size: 15px;font-family: tahoma;line-height: 15px"><b>Remaining Barcode In Stock</b></font></td>
            </tr>
            <tr>
                <td align=center ><%=dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE)%><input type="hidden"  readonly="readonly" style="font-size: 11px;font-family: tahoma;line-height: 15px" value="<%=dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE)%>" name="<%=SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE %>"></td>
            </tr>
        </table>
        <br>
         <form name="<%=formName%>"  method="post" action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%=InterfaceKey.HASHMAP_KEY_USER_ID%>=<%=userId%>">
                   <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                    <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Barcodes Quantity:</font></td>
                        <td nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
                                <input type="text" name="<%=SCMInterfaceKey.BARCODE_STOCK_IMPORT_QUANTITY%>" value="<%=dataHashMap.get(SCMInterfaceKey.BARCODE_STOCK_IMPORT_QUANTITY)%>">
                            </font></td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <input type="button" class="button" name="Submit" value="Add Barcodes" onclick="importBarcodes();">
                        </td>
                    </tr>
                    </table>

         </form>
                            
        <%
        Vector barcodeStockReportStats=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_BARCODE_STOCK_STATS_REPORT);
        int barcodeStockReportStatsVectorSize=0;
        if(barcodeStockReportStats!=null&&barcodeStockReportStats.size()!=0){
         barcodeStockReportStatsVectorSize=barcodeStockReportStats.size();
         %>




               <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
                   <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Transaction Type</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Quantity</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">User</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Date</font></td>
                    </tr>
               <%}%>
               <%
                       
                for(int i=0;i<barcodeStockReportStatsVectorSize;i++){
                    BarcodeStockReportModel barcodStockReport= (BarcodeStockReportModel)barcodeStockReportStats.get(i);
                %>
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=barcodStockReport.getBarcodeStockType()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=barcodStockReport.getQuantity()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=barcodStockReport.getUserLogin()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=barcodStockReport.getUpdateIn().getDate()%>/<%=barcodStockReport.getUpdateIn().getMonth()+1 %>/<%=barcodStockReport.getUpdateIn().getYear()+1900 %></font></td>
                    </tr>
                <%}%>
                </table>
                <br>


                        <%
                                       if(confMessage!=null){
                                           
                                            out.println("<script>");
                                            out.println("alert(\""+confMessage+"\");");
                                            out.println("</script>");
                                          
                                           }
                        %>

        </div>
    </body>
</html>
