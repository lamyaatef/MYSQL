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
         import="com.mobinil.sds.core.system.sop.requests.dao.*"
         import="com.mobinil.sds.core.system.sop.requests.model.*"
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
     if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_PRODUCT_ID%>.value") == "")
      {
        alert("Schema Product ID must not be empty.");
        return;
      }
     if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_PRODUCT_ID%>.value")))
      {
            alert("Schema Product ID must be a number.");
            return; 
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_QUANTITY%>.value") == "")
      {
        alert("Product Quantity must not be empty.");
        return;
      }
      if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_QUANTITY%>.value")))
      {
            alert("Product Quantity must be a number.");
            return; 
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE%>.value") == "")
      {
        alert("Product Price must not be empty.");
        return;
      }
      if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE%>.value")))
      {
            alert("Product Price must be a number.");
            return; 
      }
      SOPform.submit();
      }
</script>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   String strInvoiceNumber = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);
   
   InvoiceModel invoiceModel = (InvoiceModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

   String appName = request.getContextPath();
   
   Utility.logger.debug("App Name " + appName);
%> 
 <CENTER>
      <H2> Invoice Details </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

String nextAction = SOPInterfaceKey.ACTION_SAVE_INVOICE_DETAIL;
String schemaProductId = "";
String productQuantity = "";
String productPrice = "";
String invoiceDetailId = "";
String invoiceNumber = "";
if (invoiceModel!=null)
{
  nextAction = SOPInterfaceKey.ACTION_UPDATE_INVOICE_DETAIL_DATA;
  invoiceNumber = invoiceModel.getInvoiceNumber();
  if (invoiceNumber == null)
  invoiceNumber = "";
  schemaProductId = invoiceModel.getSchemaProductId();
  if (schemaProductId == null)
  schemaProductId = "";
  productQuantity = invoiceModel.getProductQuantity();
  if (productQuantity == null)
  productQuantity = "";
  productPrice = invoiceModel.getProductPrice();
  if (productPrice == null)
  productPrice = "";
  invoiceDetailId = invoiceModel.getInvoiceDetailId();
}
out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+"\""+
    " value=\""+strInvoiceNumber+"\">");

out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID+"\""+
    " value=\""+invoiceDetailId+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>Invoice Number</td>
<td class=TableTextNote><%=strInvoiceNumber%></td>
</tr>
<tr>
<td class=TableHeader nowrap>Schema Product ID</td>
<td class=TableTextNote><input type="text" name="schema_product_id" size="10" value="<%=schemaProductId%>"></td>
</tr>
<tr>
<td class=TableHeader nowrap>Product Quantity</td>
<td class=TableTextNote><input type="text" name="product_quantity" size="15" value="<%=productQuantity%>"></td>
</tr>
<tr>
<td class=TableHeader nowrap>Product Price</td>
<td class=TableTextNote><input type="text" name="Product_Price" size="15" value="<%=productPrice%>"></td>
</tr>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                  "checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>
</form>
</body>
</html>
