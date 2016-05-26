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
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
    <body>
        <%
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String strRequestId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_ID);
            String strRequestCode = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CODE);

            String strDcmName = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
            String strRequestCreationDate = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE);
            //int requestCreationDateFirstIndex = strRequestCreationDate.indexOf ("-");
            //int requestCreationDateLastIndex = strRequestCreationDate.lastIndexOf ("-");
            //Utility.logger.debug("sssssssssssssssssss " + requestCreationDateFirstIndex);
            //Utility.logger.debug("sssssssssssssssssss " + requestCreationDateLastIndex);
            //String requestCreationDateYear =strRequestCreationDate.substring(0, requestCreationDateFirstIndex);
            //Utility.logger.debug("The year issssssssssssssssssssssss " + requestCreationDateYear);
            //String requestCreationDateMonth =strRequestCreationDate.substring (requestCreationDateFirstIndex+1, requestCreationDateLastIndex);
            //Utility.logger.debug("The month isssssssssssssssssssssssssss " + requestCreationDateMonth);
            //String requestCreationDateDay = strRequestCreationDate.substring (requestCreationDateLastIndex+1, strRequestCreationDate.length());
            //Utility.logger.debug("The day isssssssssssssssssssssssssss " + requestCreationDateDay);
            //strRequestCreationDate = requestCreationDateDay+"-"+requestCreationDateMonth+"-"+requestCreationDateYear;
            String strRequestStatus = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS);


            Vector vecRequestDetail = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
            String appName = request.getContextPath();
        %> 
    <center>
        <TABLE border=0 cellSpacing=0 cellPadding=0>
            <tr>
                <td><IMG src="<%out.print(appName);%>/resources/img/sds_mobinil.bmp"></td>
            </tr>
        </table>
    </center>
    <br><br>
    <CENTER>
        <H2> Sales Request </H2>
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
                <TD>Request ID</td>
                <td>DCM Name</td>
                <td>Request Creation Date</td>
                <td>Request Status</td>
            </tr>
            <tr>
                <td><%=strRequestId%></td>
                <td><%=strDcmName%></td>
                <td><%=strRequestCreationDate%></td>
                <td><%=strRequestStatus%></td>
            </tr> 
        </table>
        <br>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
            <tr class=TableHeader>
                <td>Product Name</td>
                <td>Price</td>
                <td>Amount</td>
                <td>Discount Percentage</td>
                <td>Discount Amount</td>
                <td>Net Amount</td>

            </tr>
            <%
                float totalPayment = 0;
                for (int i = 0; i < vecRequestDetail.size(); i++) {
                    RequestDetailModel requestDetailModel = (RequestDetailModel) vecRequestDetail.get(i);
                    String requestDetailId = requestDetailModel.getRequestDetailId();
                    String requestId = requestDetailModel.getRequestId();
                    String schemaProductId = requestDetailModel.getSchemaProductId();
                    String productAmount = requestDetailModel.getProductAmount();
                    String productDiscount = requestDetailModel.getProductDiscount();
                    String productDiscountAmount = requestDetailModel.getProductDiscountAmount();
                    String productNetAmount = requestDetailModel.getProductNetAmount();
                    String productNameEnglish = requestDetailModel.getProductNameEnglish();
                    String productNameArabic = requestDetailModel.getProductNameArabic();
                    String productPrice = requestDetailModel.getProductPrice();
                    float intProductPrice = Float.parseFloat(productPrice);
                    float intProductAmount = Float.parseFloat(productAmount);
                    float total =Float.parseFloat(productNetAmount);
                    totalPayment = total + totalPayment;
                    if (productAmount.compareTo("0") != 0) {
            %>


            <tr>
                <td width=25%><%=productNameEnglish%></td>
                <td width=25%><%=productPrice%></td>
                <td width=10%><%=productAmount%></td>
                <td width=10%><%=productDiscount%></td>
                <td width=10%><%=productDiscountAmount%></td>
                <td width=25%><%=productNetAmount%>L.E</td>
            </tr>
            <%
                    }
                }
            %>
            <tr class=TableTextNote>
                <td colspan=5><b>Total Payment (Without Tax) </b></td>
                <td><b><%=totalPayment%> L.E</b></td>
            </tr>
        </table>

        <br>
        <!--TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align='right>
    <tr>
    <td align = 'left' width=50%><b>Total Payment</b></td>
     <td align = 'left'><b><%=totalPayment%> L.E</b></td>
     </tr>
     </table-->
        <center>
            <input type="button" class="button" value=" Print " onclick="window.print();">
        </center>
    </form>
</body>
</html>
