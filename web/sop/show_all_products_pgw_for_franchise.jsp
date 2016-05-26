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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
  <body>
<script>
 function setSearchValues(lcsProductCode,productNameEnglish)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE%>").value = lcsProductCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH%>").value = productNameEnglish;
        }
</script>

<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  //Utility.logger.debug("Channel id isssssssssssssssss " + strChannelId);

  Vector vecSchemaProdcut = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  String searchLcsProductCode = "";
  String searchProductNameEnglish = "";

  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE))
  {
    searchLcsProductCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE);
  }
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH))
  {
    searchProductNameEnglish = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH);
  }
%>
<CENTER>
<H2> Product PGW List </H2>
</CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 

   out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+"\""+
                  " value=\""+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_LCS_PRODCUT_CODE+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_NAME_IN_ENGLISH+"\""+
                  " value=\""+"\">");               

%>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
       <td align=middle>LCS Product Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_LCS_PRODUCT_CODE%>' value ="<%=searchLcsProductCode%>"></td>
        <td align=middle>Product Name in English</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_PRODUCT_NAME_ENGLISH%>' value ="<%=searchProductNameEnglish%>"></td>
         </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchInvoice\" id=\"searchInvoice\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_PRODUCT_PGW+"';"+
                  "SOPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','');\">");          
        %>
        </td>
      </tr>
    </table> 
<br><br>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
<%
    if(vecSchemaProdcut.size()!=0)
    {
%>
    <tr class=TableHeader>
    <td align='center'>Product ID</td>
     <td align='center'>LCS Product Code</td>
      <td align='center'>Is Active</td>
       <td align='center'>Has Quantity</td>
        <td align='center'>Product name in English</td>
         <td align='center'>Product name in Arabic</td>
          <td align='center'>Product price</td>
           <td align='center'>Sales Tax</td>
            <td align='center'>Edit</td>
             <td align='center'>Delete</td>
      </tr>
<%
 }
 for (int i=0;i<vecSchemaProdcut.size();i++)
 {
    ProductModel productModel = (ProductModel)vecSchemaProdcut.get(i);
    String productId = productModel.getProductId();
    String lcsProductCode = productModel.getLcsProductCode();
    String isActive = productModel.getIsActive();
    if(isActive.compareTo("1")==0)
    isActive = "Yes";
    else
    isActive = "No";
    String hasQuantity = productModel.getHasQuantity();
    if(hasQuantity.compareTo("1")==0)
    hasQuantity = "Yes";
    else
    hasQuantity = "No";
    String productNameEnglish = productModel.getProductNameEnglish();
    if (productNameEnglish == null)
    productNameEnglish = "";
    String productNameArabic = productModel.getProductNameArabic();
    if (productNameArabic == null)
    productNameArabic = "";
    String prodcutPrice = productModel.getProductPrice();
    String salesTax = productModel.getSalesTax();
%>

<tr class=<%=InterfaceKey.STYLE[i%2]%>>
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

       <td>
            <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_DELETE_PRODUCT_FROM_PGW+"';"+
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
      out.print("<INPUT class=button type='button' value=\" Create New Product \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_NEW_PRODUCT_PGW+"';"+
                  "SOPform.submit();\">");
%>
</center>
 <%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_REQUEST)==0)
    {
      out.println("<script>setSearchValues('"+searchLcsProductCode+"','"+searchProductNameEnglish+"');</script>");
    }
  }
%>
</form>
</body>
</html>