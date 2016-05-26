<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector vecSchemaProdcut = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
%> 
 <CENTER>
      <H2> Product Schema List </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+"\""+
                  " value=\""+"\">");  
%>
   <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
    <td align='center'>Product ID</td>
      <td align='center'>LCS Product Code</td>
       <td align='center'>Is Active</td>
        <td align='center'>Has Quantity</td>
         <td align='center'>Product name in English</td>
          <td align='center'>Product name in Arabic</td>
           <td align='center'>Prodcut price</td>
            <td align='center'>Sales Tax</td>
             <td align='center'>Edit</td>
    </tr>
<%
  for(int i=0;i<vecSchemaProdcut.size();i++)
  {
    ProductModel productModel = (ProductModel)vecSchemaProdcut.get(i);
    String productId = productModel.getProductId();
    String lcsProductCode = productModel.getLcsProductCode();
    String isActive = productModel.getIsActive();
    String hasQuantity = productModel.getHasQuantity();
    String productNameEnglish = productModel.getProductNameEnglish();
    if (productNameEnglish == null)
    productNameEnglish = "";
    String productNameArabic = productModel.getProductNameArabic();
    if (productNameArabic == null)
    productNameArabic = "";
    String prodcutPrice = productModel.getProductPrice();
    String salesTax = productModel.getSalesTax();
%>
 <tr class=TableTextNote>
  <td><%=productId%></td>
   <td><%=lcsProductCode%></td>
     <td><%=isActive%></td>
      <td><%=hasQuantity%></td>
       <td><%=productNameEnglish%></td>
        <td><%=productNameArabic%></td>
         <td><%=prodcutPrice%></td>
          <td><%=salesTax%></td>
           <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_EDIT_PRODUCT_PGW+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+".value = '"+productId+"';"+
                  "SOPform.submit();\">");
          %>
           </td>
 </tr>
<%
  }
%>
</table>
 <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Add New Product \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_NEW_PRODUCT_PGW+"';"+
                  "SOPform.submit();\">");
%>
</center>
 </form>
  </body>
</html>