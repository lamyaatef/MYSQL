<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"

         import="com.mobinil.sds.core.system.cr.bundle.model.*"
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

  Vector productTypeVec = new Vector();
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  {
    productTypeVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  }
  
%>
<center>
<H2> Bundle Product Type List </H2>
    </CENTER>
<form name='BUNform' id='BUNform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
  out.println("<input type=\"hidden\" name=\""+AdministrationInterfaceKey.INPUT_HIDDEN_PRODUCT_TYPE_ID+"\""+
                  " value=\""+"\">");  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="75%" border=1> 
    <tr class=TableHeader>
    <td align='center'>Product Type ID</td>
     <td align='center'>Product Type Name</td>
      <td align='center'>Edit</td>
       
    </tr>
<%
  for(int i=0;i<productTypeVec.size();i++)
  {
    BundleModel bundleModel = (BundleModel)productTypeVec.get(i);
    String productTypeId = bundleModel.getProductTypeId();
    String productTypeName = bundleModel.getProductTypeName();
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td><%=productTypeId%></td>
   <td><%=productTypeName%></td>
     <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.BUNform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AdministrationInterfaceKey.ACTION_EDIT_BUNDLE_PRODUCT_TYPE+"';"+
                 "document.BUNform."+AdministrationInterfaceKey.INPUT_HIDDEN_PRODUCT_TYPE_ID+".value = '"+productTypeId+"';"+
                  "BUNform.submit();\">");
          %>
           </td>
          
 </tr>
<%
  }
%>
</table>
</center>
 <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Create New Product Type \" name=\"new\" id=\"new\" onclick=\"document.BUNform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AdministrationInterfaceKey.ACTION_CREATE_NEW_PRODUCT_TYPE+"';"+
                  "BUNform.submit();\">");
%>
</center>
 </form>
  </body>
</html>
