<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
         import="com.mobinil.sds.core.system.sop.schemas.dao.*"
         import="com.mobinil.sds.core.system.sop.schemas.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    
    document.SOPform.submit();
  }
</script>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
</head>
<SCRIPT language="javascript">

  var rules=new Array();
  rules[0]='<%=SOPInterfaceKey.INPUT_TEXT_LCS_PRODCUT_CODE%>|required|LCS Product Code must not be empty.';
  rules[1]='<%=SOPInterfaceKey.INPUT_TEXT_IS_ACTIVE%>|required|IS Active must not be empty.';
  rules[2]='<%=SOPInterfaceKey.INPUT_TEXT_HAS_QUANTITY%>|required|HAS Quantity must not be empty.';
  rules[3]='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE%>|required|Product Price must not be empty.';
  rules[4]='<%=SOPInterfaceKey.INPUT_TEXT_SALES_TAX%>|required|Sales Tax must not be empty.';
  </SCRIPT>
<body>
<!--script>
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
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_LCS_PRODCUT_CODE%>.value") == "")
      {
        alert("LCS Product Code must not be empty.");
        return;
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_IS_ACTIVE%>.value") == "")
      {
        alert("IS Active must not be empty.");
        return;
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_HAS_QUANTITY%>.value") == "")
      {
        alert("Has Quantity must not be empty.");
        return;
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_PRICE%>.value") == "")
      {
        alert("Product Price must not be empty.");
        return;
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_SALES_TAX%>.value") == "")
      {
        alert("Sales Tax must not be empty.");
        return;
      }
      if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_SALES_TAX%>.value")))
      {
         alert("Sales Tax must be a number.");
         return; 
      }
      SOPform.submit();
    }
</script-->
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  
  ProductModel prodcutModel = (ProductModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
%> 
 <CENTER>
      <H2> Product  </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
                  
   

String productID = "";
String productCode = "";
String isActive ="";
String hasQuantity = "";
String productNameEnglish = "";
String productNameArabic = "";
String productPrice = "";
String salesTax = "";
String nextAction = SOPInterfaceKey.ACTION_SAVE_PRODUCT_PGW;

String selectedIsActiveYes = "";
String selectedIsActiveNo = "";
String selectedHasQuantityYes = "";
String selectedHasQuantityNo = "";
if (prodcutModel != null)
{
  nextAction = SOPInterfaceKey.ACTION_UPDATE_PRODUCT_PGW;
  productID = prodcutModel.getProductId();
  productCode = prodcutModel.getLcsProductCode();
  isActive = prodcutModel.getIsActive();
  if(isActive.compareTo("1")==0)
  selectedIsActiveYes = "selected";
  else selectedIsActiveNo = "selected";
  hasQuantity = prodcutModel.getHasQuantity();
  if(hasQuantity.compareTo("1")==0)
  selectedHasQuantityYes = "selected";
  else selectedHasQuantityNo = "selected";
  
  productNameEnglish = prodcutModel.getProductNameEnglish();
  if (productNameEnglish == null)
  productNameEnglish = "";
  productNameArabic = prodcutModel.getProductNameArabic();
  if(productNameArabic == null)productNameArabic = "";
  productPrice = prodcutModel.getProductPrice();
  salesTax = prodcutModel.getSalesTax();
}

      out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+"\""+
      " value=\""+productID+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>    
    
<tr>
<td class=TableHeader nowrap>LCS Product Code</td>
<td class=TableTextNote><input type="text" name="LCS_Product_Code" size="5" value="<%=productCode%>"></td>
</td>
</tr>
<tr class=TableTextNote>
<td class=TableHeader nowrap>IS Active</td>
<td class=TableTextNote>
<select name ="IS_Active">
<option value =''></option>
<option value ="1" <%=selectedIsActiveYes%>>yes</option>
<option value ="0" <%=selectedIsActiveNo%>>no</option>
</select>
</td>
</tr>
<tr class=TableTextNote>
<td class=TableHeader nowrap>Has Quantity</td>
<td class=TableTextNote>
<select name="Has_Quantity" >
<option value =''></option>
<option value ="1" <%=selectedHasQuantityYes%>>yes</option>
<option value ="0" <%=selectedHasQuantityNo%>>no</option>
</select>
</td>
</tr>
<tr class=TableTextNote>
<td class=TableHeader nowrap>Product name in English</td>
<td class=TableTextNote><input type="text" name="Product_name_in_English" size="20" value="<%=productNameEnglish%>"></td>
</tr>
<tr class=TableTextNote>
<td class=TableHeader nowrap>Product name in Arabic</td>
<td class=TableTextNote><input type="text" name="Product_name_in_Arabic" size="20" value="<%=productNameArabic%>"></td>
</tr>
<tr class=TableTextNote>
<td class=TableHeader nowrap>Product Price</td>
<td class=TableTextNote><input type ="text" name="Product_Price" size="10" value="<%=productPrice%>"></td>
</tr>
<tr class=TableTextNote>
<td class=TableHeader nowrap>Sales Tax</td>
<td class=TableTextNote><input type="text" name="Sales_Tax" size ="10" value="<%=salesTax%>"></td>
</tr>
<tr>
</tr>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
      "if(performCheck('SOPform', rules, 'classic')){SOPform.submit();}\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>