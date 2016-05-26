<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"
         %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
    <body>
        <%
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String strRequestId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID);

            String strDcmName = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
            String strRequestCreationDate = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE);
            String strRequestStatus = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS);

            Vector vecRequestDetail = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        %>  
    <CENTER>
        <H2> Requests Detail </H2>
    </CENTER>
    <form name='SOPform' id='SOPform' action='' method='post'>
        <%
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                    + " value=\"" + strUserID + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID + "\""
                    + " value=\"" + strRequestId + "\">");
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
            <tr class=TableHeader>
                <td>DCM Name</td>
                <td>Request Creation Date</td>
                <td>Request Status</td>
            </tr>
            <tr>
                <td><%=strDcmName%></td>
                <td><%=strRequestCreationDate%></td>
                <td><%=strRequestStatus%></td>
            </tr> 
        </table>
        <br>
        <%
            boolean boIsQuotaItem = false;
            boolean boIsNonQuotaItem = false;
            for (int i = 0; i < vecRequestDetail.size(); i++) {
                RequestDetailModel requestDetailModel = (RequestDetailModel) vecRequestDetail.get(i);
                String requestDetailId = requestDetailModel.getRequestDetailId();
                String requestId = requestDetailModel.getRequestId();
                String schemaProductId = requestDetailModel.getSchemaProductId();
                String minimumLimit = requestDetailModel.getMinimumLimit();
                String maximumLimit = requestDetailModel.getMaximumLimit();
                String productAmount = requestDetailModel.getProductAmount();
                String productDiscount = requestDetailModel.getProductDiscount();
                String productDiscountAmount = requestDetailModel.getProductDiscountAmount();
                String productNetAmount = requestDetailModel.getProductNetAmount();
                String hasQuantity = requestDetailModel.getHasQuantity();
                String productNameEnglish = requestDetailModel.getProductNameEnglish();
                String productNameArabic = requestDetailModel.getProductNameArabic();
                String productPrice = requestDetailModel.getProductPrice();
                String salesTax = requestDetailModel.getSalesTax();
                String productWeight = requestDetailModel.getProductWeight();
                String isPointItemAnswer = "";
                String isPointItem = requestDetailModel.getIsPointItem();
                if (isPointItem.compareTo("1") == 0) {
                    isPointItemAnswer = "YES";
                } else if (isPointItem.compareTo("0") == 0) {
                    isPointItemAnswer = "NO";
                }
                String isQuotaItemAnswer = "";
                String isQuotaItem = requestDetailModel.getIsQuotaItem();
                if (isQuotaItem.compareTo("1") == 0) {
                    isQuotaItemAnswer = "YES";
                    if (!boIsQuotaItem) {
                        boIsQuotaItem = true;
                        if (boIsNonQuotaItem) {
        %>
        </table>
        <br>
        <%    }
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
            <tr>
                <td colspan=7>Quota Items</td>
            </tr>
            <tr class=TableHeader>
                <td>Product Name</td>
                <td>Weight</td>
                <td>Is Point Item</td>
                <td>Price</td>
                <td>Amount</td>                
                <td>Discount Percentage</td>
                <td>Discount Amount</td>
                <td>Net Amount</td>
                <td>Min Limit</td>
                <td>Max Limit</td>
            </tr>
            <%
                }
            } else if (isQuotaItem.compareTo("0") == 0) {
                isQuotaItemAnswer = "NO";
                if (!boIsNonQuotaItem) {
                    boIsNonQuotaItem = true;
                    if (boIsQuotaItem) {
            %>
        </table>
        <br>
        <%    }
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
            <tr>
                <td colspan=7>Non Quota Items</td>
            </tr>
            <tr class=TableHeader>
                <td>Product Name</td>
                <td>Weight</td>
                <td>Is Point Item</td>
                <td>Price</td>
                <td>Amount</td>
                <td>Discount Percentage</td>
                <td>Discount Amount</td>
                <td>Net Amount</td>
                <td>Min Limit</td>
                <td>Max Limit</td>
            </tr>
            <%
                    }
                }

            %>
            <tr class=<%=InterfaceKey.STYLE[i % 2]%>>
                <td><%=productNameEnglish%></td>
                <td><%=productWeight%></td>
                <td><%=isPointItemAnswer%></td>
                <td><%=productPrice%></td>
                <td><%=productAmount%></td>
                <td><%=productDiscount%></td>
                <td><%=productDiscountAmount%></td>
                <td><%=productNetAmount%></td>
                <td><%=minimumLimit%></td>
                <td><%=maximumLimit%></td>
            </tr>        
            <%
                }
            %>  
        </table>
        <br><br>
        <center>
            <input type="button" class="button" value=" Print " onclick="window.print();">
            <INPUT class="button" type="button" value=" Back " name="back" id="back" onclick="history.go(-1);">
        </center>
    </body>
</html>
