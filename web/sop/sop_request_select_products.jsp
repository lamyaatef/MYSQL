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
         import="com.mobinil.sds.web.interfaces.gn.ua.*"

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
    <body>
        <script>
            function checkBeforeSubmit()
            {
                var inputs = document.getElementsByTagName("input");
                var productsoverallquota = 0;
                for(var i=0;i<inputs.length;i++)
                {
                    var inputsubstring = inputs[i].name.split("_");
                    var schemaproductid = inputsubstring[1];
                    if(inputsubstring[0] == "<%=SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT%>")
                    {
          
                        productamount = inputs[i].value;
                        if(productamount == "")
                        {
                            productamount = 0;
                        }
                        else
                        {
                            if(!checknumber(productamount))
                            {
                                alert("Product Amount must be a number.")
                                return;
                            }
                            else
                            {
                                productamount = parseInt(productamount);
                                if(productamount != 0)
                                {

                       
                                    var minmaxvalue = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_MINIMUM_MAXIMUM_LIMIT%>_"+schemaproductid+".value");
                                    // var minmaxvalue = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_MINIMUM_MAXIMUM_LIMIT%>'_'+temp+'.value';
                                    //alert('1');
                                    //alert(minmaxvalue);
                                    var min_max_value = minmaxvalue.split("_");
                                    var minvalue = parseInt(min_max_value[0]);
                                    var maxvalue = parseInt(min_max_value[1]);
                                    var isquotaitem = parseInt(min_max_value[2]);
                                    var productprice = parseFloat(min_max_value[3]);
                                    var physicalAmount = parseInt(min_max_value[4]);
                                    if(productamount > maxvalue || productamount < minvalue || productamount > physicalAmount)
                                    {
                                        alert("Product amount is out of limit");
                                        return;
                                    }
                                    else
                                    {
                                        if(isquotaitem == 1)
                                        {
                                            var productcostquota = productprice * productamount;
                                            productsoverallquota = productsoverallquota + productcostquota;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                var dcmquota = parseInt(document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA%>.value);
      
                if(productsoverallquota>dcmquota)
                {
                    alert("Products amount quota : "+productsoverallquota+" exceeds dcm quota : "+dcmquota+" ");
                }
                else
                {
                    document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA%>.value = productsoverallquota;
                    SOPform.submit();
                }
      
            }
            function returnToPrevious()
            {        
    
                document.SOPform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SOPInterfaceKey.ACTION_CREATE_NEW_REQUEST%>';
                SOPform.submit();
    
            }

            function checknumber(text)
            {
                var x=text
                var anum=/(^\d+$)|(^\d+\.\d+$)/
                if (anum.test(x))
                    testresult=true
                else
                {
                    testresult=false
                }
                return (testresult)
            }

            function CalculateTotal(theForm) 
            {
                var sum = 0;
                var inputs = theForm.elements;
      
		
                for(var i=0;i<inputs.length;i++        )
                {
                    var price = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE%>");
                    //alert(price);
  
                    var iiii=inputs[i].id;
          
                    iiii=iiii+'00000000000000000000000000000';
                    var subSection = iiii.substring(29,0);
          
                    if(inputs[i].type == 'hidden' && subSection == 'hidden_price_amount_multiply_') 
                    {
                        //var val = parseInt(inputs[i].value);
                        var val= parseFloat(inputs[i].value);
                        //alert('value'+val.toFixed(1));
                        //alert('value'+subSection);
                        //val = val * parseInt(price); // change parseFloat to parseInt if you do not have decimals
                        if (!isNaN(val)) 
                        {
                            //alert('beforesum sum is'+sum);
                            //alert('beforesum val is'+val);
                            sum = sum + val;
                        } 
                    }
                }
                //alert('sum'+sum);
                //alert('inputsumvalue '+document.getElementById('txt_total_sum').value);
                document.getElementById('txt_total_sum').value = sum.toFixed(1);
                //alert('inputsumvalue '+document.getElementById('txt_total_sum').value);
            }

            function Multiply(theForm,control,discountControl,discountAmountControl,netPriceControl)
            { 
    	
        
                var amountId = control.name;
                //alert('amountId:'+amountId);
                var temp=amountId.split('_');
                var Id=temp[1];
                //alert('Id:'+Id);
                var amountValue = control.value;
                //alert('amountValue:'+amountValue);
                var price=document.getElementById('hidden_price_amount_'+Id).value ;
                //alert('price'+price);

                var selectDiscount=document.getElementById('select_discount_checkbox_'+Id).checked;
                // alert (selectDiscount);


                var total = parseFloat(price)* parseInt(amountValue);

                var netAmount;
                
                if(selectDiscount==true)
                {

                    var disocunt=discountControl.value;
                
                
                    var discountAmount= ((parseFloat(disocunt)*total)/100).toFixed(2);
                
                
                    discountAmountControl.value=discountAmount;
                
                    netAmount=(total-discountAmount).toFixed(2);
                
                    netPriceControl.value=netAmount;
                    discountAmountControl.disabled=false;

                }
                else if (selectDiscount==false) {
                    
                    netAmount =total.toFixed(2);
                
                    netPriceControl.value=netAmount;
                    discountAmountControl.disabled=true;
                    
            
                }
                //alert('total'+total);
                document.getElementById('hidden_price_amount_multiply_'+Id).value=netAmount;
                //CalculateTotal(theForm);
                CalculateTotal(document.SOPform);
            }

            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;

                return true;
            }

        </script>  
        <%            
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            String appName = request.getContextPath();
            
            HashMap objDataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            
            String strUserID = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String strChannelId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
            Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
            String warehouseId = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
            String strDcmID = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);
            System.out.println("The dcm id issssssssssss " + strDcmID);
            String strDcmQuota = (String) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA);
            if (strDcmQuota.compareTo("") == 0) {
                strDcmQuota = "0";
            }
            DCMModel dcmModel = (DCMModel) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
            String strDcmName = dcmModel.getDcmName();
            String strDcmCode = dcmModel.getDcmCode();
            Vector vecActiveSchemaProducts = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        %>  
    <CENTER>
        <H2> Product List </H2>
    </CENTER>
    <form name='SOPform' id='SOPform' action='' method='post'>
        <%            
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_ACTION + "\""
                    + " value=\"" + "\">");
            
            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_DCM_ID + "\""
                    + " value=\"" + strDcmID + "\">");
            
            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_DCM_QUOTA + "\""
                    + " value=\"" + strDcmQuota + "\">");
            
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\""
                    + " value=\"" + strUserID + "\">");
            
            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID + "\""
                    + " value=\"" + strChannelId + "\">");
            
            out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID + "\""
                    + " value=\"" + warehouseId + "\">");
            
            out.println("<input type=\"hidden\" name=\"" + UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME + "\" "
                    + "value=\"" + serverName + "\">");
            out.println("<input type=\"hidden\" name=\"" + UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT + "\" "
                    + "value=\"" + serverPort + "\">");
            out.println("<input type=\"hidden\" name=\"" + UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH + "\" "
                    + "value=\"" + appName + "\">");
        %>   
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <tr class=TableHeader>
                <td>DCM Name</td>
                <td>DCM Code</td>
                <td>DCM Quota</td>
            </tr>
            <tr>
                <td><%=strDcmName%></td>
                <td><%=strDcmCode%></td>
                <td><%=strDcmQuota%></td>   
            </tr>  
        </table>
        <br>
        <%            
            String schemaId = "";
            boolean boIsQuotaItem = false;
            boolean boIsNonQuotaItem = false;
            for (int i = 0; i < vecActiveSchemaProducts.size(); i++) {
                ProductModel productModel = (ProductModel) vecActiveSchemaProducts.get(i);
                String schemaProductId = productModel.getSchemaProductId();
                System.out.println("The schema product id issssssssss " + schemaProductId);
                String productId = productModel.getProductId();
                schemaId = productModel.getSchemaId();
                String lcsProductCode = productModel.getLcsProductCode();
                String isActive = productModel.getIsActive();
                String hasQuantity = productModel.getHasQuantity();
                String productNameEnglish = productModel.getProductNameEnglish();
                String productNameArabic = productModel.getProductNameArabic();
                String productPrice = productModel.getProductPrice();
                String salesTax = productModel.getSalesTax();
                String productWeight = productModel.getProductWeight();
                String productDiscount = productModel.getProductDiscount();
                String productDiscountAmount = productModel.getProductDiscountAmount();
                String productNetAmount = productModel.getProductNetAmount();
                String isPointItemAnswer = "";
                String isPointItem = productModel.getIsPointItem();
                if (isPointItem.compareTo("1") == 0) {
                    isPointItemAnswer = "YES";
                } else if (isPointItem.compareTo("0") == 0) {
                    isPointItemAnswer = "NO";
                }
                String isQuotaItemAnswer = "";
                String isQuotaItem = productModel.getIsQuotaItem();
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
            <tr><td colspan=9>Quota Items</td></tr>
            <tr class=TableHeader>
                <td>Code</td>
                <td>Name</td>
                <td>Weight</td>
                <td>Is Point Item</td>
                <td>Price</td>
                <td>Amount</td>
                <td>Min Limit</td>
                <td>Max Limit</td>
                <!--td>Physical Amount</td-->
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
            <tr><td colspan=9>Non Quota Items</td></tr>
            <tr class=TableHeader>

                <td>Select Discount</td>
                <td>Code</td>
                <td>Name</td>
                <td>Weight</td>
                <td>Is Point Item</td>
                <td>Price</td>
                <td>Amount</td>
                <td>Discount %</td>
                <td>Discount Amount</td>
                <td>Net Price</td>
                <td>Min Limit</td>
                <td>Max Limit</td>
                <!--td>Physical Amount</td-->
            </tr> 
            <%                        
                    }
                }
                String minimumLimit = productModel.getMinimumLimit();
                String maximumLimit = productModel.getMaximumLimit();
                String physicalAmount = productModel.getPhysicalAmount();
            %>
            <tr class=TableTextNote>
                <td><input type="checkbox" name="select_discount_checkbox_<%=schemaProductId%>" id="select_discount_checkbox_<%=schemaProductId%>" onchange="Multiply(document.SOPform,document.getElementById('<%=SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT%>_<%=schemaProductId%>'),document.getElementById('<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT%>_<%=schemaProductId%>'),document.getElementById('<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT_AMOUNT%>_<%=schemaProductId%>'),document.getElementById('<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_NET_AMOUNT%>_<%=schemaProductId%>'));CalculateTotal(document.SOPform)"></td>
                <td><%=lcsProductCode%></td>
                <td><%=productNameEnglish%></td>
                <td><%=productWeight%></td>
                <td><%=isPointItemAnswer%></td>
                <td><input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE%>_<%=schemaProductId%>' value='<%=productPrice%>'><%=productPrice%></td>
                <td><input onkeypress="return isNumberKey(event);" onContextMenu='return false;' onChange="Multiply(document.SOPform,this,document.getElementById('<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT%>_<%=schemaProductId%>'),document.getElementById('<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT_AMOUNT%>_<%=schemaProductId%>'),document.getElementById('<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_NET_AMOUNT%>_<%=schemaProductId%>'));CalculateTotal(document.SOPform)"  type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_REQUEST_PRODUCT_AMOUNT%>_<%=schemaProductId%>' value="0"></td>
                <td><input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT%>_<%=schemaProductId%>' value='<%=productDiscount%>'><%=productDiscount%></td>
                <td> <input type='text' name='<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT_AMOUNT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_DISCOUNT_AMOUNT%>_<%=schemaProductId%>' value='0' readonly>
                </td>
                <td>
                    <input type='text' name='<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_NET_AMOUNT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_NET_AMOUNT%>_<%=schemaProductId%>' value='0' readonly>
                </td>                    
                <td align='center'><input type='hidden' name='<%=SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT%>_<%=schemaProductId%>' value='<%=minimumLimit%>'><%=minimumLimit%></td>
                <td align='center'><input type='hidden' name='<%=SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT%>_<%=schemaProductId%>' value='<%=maximumLimit%>'><%=maximumLimit%></td>
            <input type='hidden' name='<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>_<%=schemaProductId%>' value='<%=physicalAmount%>'>
            <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE_Multiply%>_<%=schemaProductId%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE_Multiply%>_<%=schemaProductId%>' value=''>
            </tr>  
            <%                    
                    out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_MINIMUM_MAXIMUM_LIMIT + "_" + schemaProductId + "\""
                            + " value=\"" + minimumLimit + "_" + maximumLimit + "_" + isQuotaItem + "_" + productPrice + "\">");
                    
                }
            %>
        </table>

        <br><br>
        <center>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
                <tr class=TableHeader>
                    <td>
                        The Sum of the Amount: 
                    </td><td>
                        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_AMOUNT_PRICE%>' value='0' >
                        <input type='text' name='txt_total_sum' id='txt_total_sum' value='0' readonly></td>
            </table>
        </center>
        <br>
        <center> 
            <%                
                out.print("<INPUT class=button type='button' value=\" Save Request \" name=\"save\" id=\"save\" onclick=\"document.SOPform." + InterfaceKey.HASHMAP_KEY_ACTION + ".value = '" + SOPInterfaceKey.ACTION_SAVE_REQUEST + "';"
                        + "checkBeforeSubmit();\">");
                
                
                out.println("<input type=\"hidden\" name=\"" + SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID + "\""
                        + " value=\"" + schemaId + "\">");
                
                if (objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE)) {
                    String strMsg = (String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
                    out.println("<script>alert('" + strMsg + "');</script>");
                    out.println("<script>returnToPrevious();</script>");
                }
            %>
        </center>
    </body>
</html>
