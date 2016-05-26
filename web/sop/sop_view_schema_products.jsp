<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
         %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
    <body>
        <script>
            function IsNumeric(sText)
            {
                var ValidChars = "0123456789.";
                var IsNumber=true;
                var Char;
                for (i = 0; i < sText.length && IsNumber == true; i++) 
                { 
                    Char = sText.charAt(i); 
                    if (ValidChars.indexOf(Char) == -1) 
                    {
                        IsNumber = false;
                    }
                }
                return IsNumber;
            }

            function checkBeforeSubmit()
            {
                var inputsObj = document.SOPform.getElementsByTagName("input");
                var flagnumbers = true;
                for(var i = 0;i<inputsObj.length;i++)
                {
                    if(inputsObj[i].type == "text")
                    {
                        if(!IsNumeric(inputsObj[i].value))
                        {
                            flagnumbers = false;
                        }
                    }
                }
                if(flagnumbers)
                {
                    SOPform.submit();
                }
                else
                {
                    alert('One of the product weight fields is not a number');
                }
            }

            function checkIsPoint(schemaproductid)
            {
                var ispointvalue = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_POINT%>_"+schemaproductid+".val        ue");
                var objproductweight = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_WEIGHT%>_"+schemaproductid+"");
                if(ispointvalue == "0")
                {
                    objproductweight.value = "0";
                    objproductweight.readOnly = true;
                }
                else if(ispointvalue == "1")
                {
                    objproductweight.readOnly = false;
                }
            }
        </script>  
        <%
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            String strChannelId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
            Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);

            SchemaModel schemaDetails = (SchemaModel) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
            String strCreationDate = schemaDetails.getCreationDate();
            if (strCreationDate == null) {
                strCreationDate = "";
            } else {
                strCreationDate = strCreationDate.substring(0, 10);
            }
            String strSchemaCode = schemaDetails.getSchemaCode();
            String strSchemaName = schemaDetails.getSchemaName();
            if (strSchemaName == null) {
                strSchemaName = "";
            }
            String strStartDate = schemaDetails.getStartDate();
            if (strStartDate == null) {
                strStartDate = "";
            } else {
                strStartDate = strStartDate.substring(0, 10);
            }
            String strEndDate = schemaDetails.getEndDate();
            if (strEndDate == null) {
                strEndDate = "";
            } else {
                strEndDate = strEndDate.substring(0, 10);
            }
            String strSchemaStatusName = schemaDetails.getSchemaStatusName();
            String strSchemaStatus = schemaDetails.getSchemaStatusId();

            Vector vecSchemaProductList = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        %>   
    <CENTER>
        <H2> Schema Products List </H2>
    </CENTER>
    <form name='SOPform' id='SOPform' action='' method='post'>
        <%
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                    + " value=\"" + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_NEXT_STATUS_ID + "\""
                    + " value=\"" + strSchemaStatus + "\">");

            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                    + " value=\"" + strUserID + "\">");

            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID + "\""
                    + " value=\"" + strChannelId + "\">");

        %> 
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
                <td align='middle'>Schema Code</td>
                <td align='middle'>Schema Name</td>
                <td align='middle'>Status</td>
                <td align='middle'>Creation Date</td>
                <td align='middle'>Start Date</td>
                <td align='middle'>End Date</td>
            </tr>
            <tr>
                <td align='middle'><%=strSchemaCode%></td>
                <td align='middle'><%=strSchemaName%></td>
                <td align='middle'><%=strSchemaStatus%></td>
                <td align='middle'><%=strCreationDate%></td>
                <td align='middle'><%=strStartDate%></td>
                <td align='middle'><%=strEndDate%></td>
            </tr>
        </table>
        <br>
        <%
            String schemaId = "";
            boolean boIsQuotaItem = false;
            boolean boIsNonQuotaItem = false;

            for (int i = 0; i < vecSchemaProductList.size(); i++) {
                ProductModel productModel = (ProductModel) vecSchemaProductList.get(i);
                String schemaProductId = productModel.getSchemaProductId();
                String productId = productModel.getProductId();
                schemaId = productModel.getSchemaId();
                String lcsProductCode = productModel.getLcsProductCode();
                String isActive = productModel.getIsActive();
                String hasQuantity = productModel.getHasQuantity();
                String productNameEnglish = productModel.getProductNameEnglish();
                if (productNameEnglish.compareTo("null") == 0) {
                    productNameEnglish = "";
                }
                String productNameArabic = productModel.getProductNameArabic();
                if (productNameArabic.compareTo("null") == 0) {
                    productNameArabic = "";
                }
                String productPrice = productModel.getProductPrice();
                String salesTax = productModel.getSalesTax();
                String productWeight = productModel.getProductWeight();
                String productDiscount = productModel.getProductDiscount();
                String isPointItem = productModel.getIsPointItem();
                String isPointAnswer = "";
                String isPointYesSelected = "";
                String isPointNoSelected = "";
                String isQuotaAnswer = "";
                String isQuotaYesSelected = "";
                String isQuotaNoSelected = "";
                if (isPointItem.compareTo("1") == 0) {
                    isPointAnswer = "YES";
                    isPointYesSelected = "selected";
                } else if (isPointItem.compareTo("0") == 0) {
                    isPointAnswer = "NO";
                    isPointNoSelected = "selected";
                }
                String isQuotaItem = productModel.getIsQuotaItem();
                if (isQuotaItem.compareTo("1") == 0) {
                    isQuotaAnswer = "YES";
                    isQuotaYesSelected = "selected";
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

                <%
                    if (strSchemaStatus.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING) == 0) {
                %>
                <td colspan=7>Quota Items</td>
                <%        } else {
                %>
                <td colspan=6>Quota Items</td>
                <%            }
                %>
            </tr>
            <TR class=TableHeader>
                <td width="1%" align=middle>#</td>
                <td width="10%" nowrap align=middle>English Name</td>
                <td width="10%" nowrap align=middle>Arabic Name</td>
                <TD width="10%" noWrap align=middle>LCS Product Code</TD>
                <TD width="10%" noWrap align=middle>Product Weight</TD>
                <TD width="10%" noWrap align=middle>Is Point?</TD>
                <%
                    if (strSchemaStatus.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING) == 0) {
                %>
                <TD width="10%" noWrap align=middle>Is Quota?</TD>
                <%            }
                %>
            </TR>

            <%
                }
            } else if (isQuotaItem.compareTo("0") == 0) {
                isQuotaAnswer = "NO";
                isQuotaNoSelected = "selected";
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
                <%
                    if (strSchemaStatus.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING) == 0) {
                %>
                <td colspan=7>Non Quota Items</td>
                <%        } else {
                %>
                <td colspan=6>Non Quota Items</td>
                <%            }
                %>
            </tr>
            <TR class=TableHeader>
                <td width="1%" align=middle>#</td>
                <td width="10%" nowrap align=middle>English Name</td>
                <td width="10%" nowrap align=middle>Arabic Name</td>
                <TD width="10%" noWrap align=middle>LCS Product Code</TD>
                <TD width="10%" noWrap align=middle>Product Weight</TD>
                <TD  width="10%" noWrap align=middle>Discount Percentage</TD>
                <TD width="10%" noWrap align=middle>Is Point?</TD>
                <%
                    if (strSchemaStatus.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING) == 0) {
                %>
                <TD width="10%" noWrap align=middle>Is Quota?</TD>
                <%            }
                %>
            </TR>

            <%
                    }
                }
            %>
            <TR class=<%=InterfaceKey.STYLE[i % 2]%>>
                <td align=middle><%=i + 1%></td>
            <input type="hidden" name='<%=SOPInterfaceKey.INPUT_HIDDEM_PRODUCT_PRICE%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEM_PRODUCT_PRICE%>_<%=schemaProductId%>' value='<%=productPrice%>'>
            <td width="10%" nowrap align=middle><%=productNameEnglish%></td>
            <td width="10%" nowrap align=middle><%=productNameArabic%></td>
            <TD width="10%" noWrap align=middle><%=lcsProductCode%></TD>
            <%
                if (strSchemaStatus.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING) == 0) {

            %>
            <TD width="10%" noWrap align=middle>
                <input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_WEIGHT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_WEIGHT%>_<%=schemaProductId%>' value='<%=productWeight%>'>
            </TD>
            <TD width="10%" noWrap align=middle>
                <input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_DISCOUNT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_DISCOUNT%>_<%=schemaProductId%>' value='<%=productDiscount%>'>

            </TD>

            <TD width="10%" noWrap align=middle>
                <select name='<%=SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_POINT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_POINT%>_<%=schemaProductId%>' onchange="checkIsPoint('<%=schemaProductId%>');">
                    <option value='0' <%=isPointNoSelected%>>NO</option>
                    <option value='1' <%=isPointYesSelected%>>YES</option>
                </select>
            </TD>

            <TD width="10%" noWrap align=middle>
                <select name='<%=SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_QUOTA%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_SELECT_PRODUCT_IS_QUOTA%>_<%=schemaProductId%>'>
                    <option value='0' <%=isQuotaNoSelected%>>NO</option>
                    <option value='1' <%=isQuotaYesSelected%>>YES</option>
                </select>
            </TD>
            <%
            } else {
            %>
            <TD width="10%" noWrap align=middle><%=productWeight%></TD>
            <TD width="10%" noWrap align=middle><%=productDiscount%></TD>
            <TD width="10%" noWrap align=middle><%=isPointAnswer%></TD>
            <%
                }
            %>
            </TR>
            <%
                }

                out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID + "\""
                        + " value=\"" + schemaId + "\">");
            %>
        </table>  

        <br><br>
        <center>
            <%
                if (strSchemaStatus.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING) == 0) {
                    out.print("<INPUT class=button type=button value=\" Update Products \" name=\"update\" id=\"update\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + SOPInterfaceKey.ACTION_UPDATE_SCHEMA_PRODUCTS + "';"
                            + "checkBeforeSubmit();\">");
                }

                out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
            %>
        </center>    
    </form>
</body>
</html>
