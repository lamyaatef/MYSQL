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

  Vector vecSchemaProdcut = new Vector();
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  {
    vecSchemaProdcut = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  }
  
%>
<center>
<H2> LCS Product List </H2>
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
     <td align='center'>Product Code</td>
      <td align='center'>Physical Amount</td>
       <td align='center'>Edit</td>
        <td align='center'>Delete</td>
    </tr>
<%
  for(int i=0;i<vecSchemaProdcut.size();i++)
  {
    ProductModel productModel = (ProductModel)vecSchemaProdcut.get(i);
    String productId = productModel.getProductId();
    String productCode = productModel.getProductCode();
    String physicalAmount = productModel.getPhysicalAmount();
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td><%=productId%></td>
   <td><%=productCode%></td>
    <td><%=physicalAmount%></td>
     <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_EDIT_PRODUCT_LCS+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+".value = '"+productId+"';"+
                  "SOPform.submit();\">");
          %>
           </td>
          <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_DELETE_PRODUCT_LCS+"';"+
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
      out.print("<INPUT class=button type='button' value=\" Create New Product \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_PRODUCT_LCS+"';"+
                  "SOPform.submit();\">");
%>
</center>
 </form>
  </body>
</html>
