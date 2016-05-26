<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dao.*"
         import="com.mobinil.sds.core.system.cr.sheet.dao.*"
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
     if(eval("document.CRform.<%=ContractRegistrationInterfaceKey.INPUT_SEARCH_SELECT_PRODUCT_NAME%>.value") == "")
      {
        alert("Product Name must not be empty.");
        return;
      }

      if(eval("document.CRform.<%=ContractRegistrationInterfaceKey.INPUT_TEXT_INVENTORY_ITEM_TYPE%>.value") == "")
      {
        alert("Inventory Item Type must not be empty.");
        return;
      }
      CRform.submit();
      }
    </script>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   lcsProductMappingModel lcsProductMappingModel = (lcsProductMappingModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

   Vector products = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
%> 

 <CENTER>
      <H2> LCS Product Mapping  </H2>
    </CENTER>
<form name='CRform' id='CRform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  String productName="";
  String inventoryTypeName="";
  String productId = "";
  String nextAction = ContractRegistrationInterfaceKey.ACTION_SAVE_LCS_PRODUCT_MAPPING;
  if(lcsProductMappingModel != null)
  {
    nextAction = ContractRegistrationInterfaceKey.ACTION_UPDATE_LCS_PRODUCT_MAPPING;
    if(lcsProductMappingModel.getProductName()!=null)
    productName = lcsProductMappingModel.getProductName();
    else
    productName = "";
    
    inventoryTypeName = lcsProductMappingModel.getInventoryItemType();
    if(lcsProductMappingModel.getProductId()!=null)
    productId = lcsProductMappingModel.getProductId();
    else
    productId = "";
    
  }
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_PRODUCT_ID+"\""+
                  " value=\""+productId+"\">"); 

     out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_INVENTORY_ITEM_TYPE+"\""+
                  " value=\""+inventoryTypeName+"\">"); 
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>  
<tr>
<td class=TableHeader nowrap>Product Name</td>
<td class=TableTextNote>
          <select name='<%=ContractRegistrationInterfaceKey.INPUT_SEARCH_SELECT_PRODUCT_NAME%>' id='<%=ContractRegistrationInterfaceKey.INPUT_SEARCH_SELECT_PRODUCT_NAME%>'>
              <%
              String productIdX = "";
              for(int j=0;j<products.size();j++)
              {
                ProductNameModel productNameModel = (ProductNameModel)products.get(j);
                productIdX = productNameModel.getProductId();
                String productNameX = productNameModel.getProductName();
                String caseSuperTypeSelected = "";
                if(productId.compareTo(productIdX) == 0)
                {
                  caseSuperTypeSelected = "selected";
                }
                
                %>
                <option value="<%=productIdX%>" <%=caseSuperTypeSelected%>><%=productNameX%></option>
                <%
              }
              %>
            </select>
        </td>
</tr>
<tr>
<td class=TableHeader nowrap>Inventory Item Type</td>
<td class=TableTextNote><input type="text" name="inventory_item_type" size ="40%" value="<%=inventoryTypeName%>"></td>
</tr>

</table>
</center>
<br>
<center>
<%
                  
     out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.CRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                 "checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>
</form>
</body>
</html>
