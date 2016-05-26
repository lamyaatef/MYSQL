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
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  String strInvoiceNumber = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER);

  String strInvoiceDetailId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID);

  Vector vecInvoice = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
%> 
<CENTER>
      <H2> Invoices Detail Data </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+"\""+
                  " value=\""+"\">");  
                  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID+"\""+
                  " value=\""+"\">");
%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
     <td align='center'>Invoice Number</td>
     <td align='center'>Schema Product ID</td>
      <td align='center'>Product Quantity</td>
       <td align='center'>Product Price</td>
        <td align='center'>Invoice Detail ID</td>
         <td align='center'>Edit</td>
    </tr>
<%
 for (int i=0;i<vecInvoice.size();i++)
 {
  InvoiceModel invoiceModel =new InvoiceModel();
  invoiceModel = (InvoiceModel)vecInvoice.get(i);
  String schemaProductId = invoiceModel.getSchemaProductId();
  if (schemaProductId == null)
  schemaProductId = "";
  String productQuantity = invoiceModel.getProductQuantity();
  if (productQuantity == null)
  productQuantity = "";
  String productPrice = invoiceModel.getProductPrice();
  if (productPrice == null)
  productPrice = "";
  String invoiceDetailId = invoiceModel.getInvoiceDetailId();
  if (invoiceDetailId == null)
  invoiceDetailId = "";
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
<td><%=strInvoiceNumber%></td>
<td><%=schemaProductId%></td>
<td><%=productQuantity%></td>
<td><%=productPrice%></td>
<td><%=invoiceDetailId%></td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_ADD_EDIT_INVOICE_DETAIL+"';"+
          "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_DETAIL_ID+".value = '"+invoiceDetailId+"';"+
          "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+".value = '"+strInvoiceNumber+"';"+
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
      out.print("<INPUT class=button type='button' value=\" Create New Invoice Detail\" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_NEW_INVOICE_DETAIL+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_INVOICE_NUMBER+".value = '"+strInvoiceNumber+"';"+
                  "SOPform.submit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

      
%>

</center>
</form>
  </body>
</html>