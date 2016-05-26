<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"

         
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
<title>Insert title here</title>
</head>
<body>
 <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String channelId = (String)objDataHashMap.get(AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Vector productValues = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  <CENTER>
      <H2> Product Values </H2>
    </CENTER>
<form name='ACCform' id='ACCform' action='' method='post'>
<%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
          " value=\""+channelId+"\">");  
  
%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
 <tr>
          <td class=TableHeader>
          Product Name
          </td>
          <td class=TableHeader>
          Product Value
          </td>
          </tr>
  <%        
      for (int i = 0 ; i<productValues.size();i++)
        {
    	  ProductValuesModel model = (ProductValuesModel)productValues.get(i);       
          String productId = model.getProductId();
          String productName = model.getProductName();
          String productValue = model.getProductValue();
          if (productValue == null)
        	  productValue = "0";
          %>
          <tr>
          <td class=TableTextNote nowrap><%=productName%></td>
          <td class=TableTextNote><input type="text" name="Product_Value<%=productId %>" size="5" value="<%=productValue%>"></td>
          </tr>
          <%} %>
    </TABLE>      
    <br>
<center>
<%
      
out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_SAVE_PRODUCT_VALUES+"';"+
"ACCform.submit();\">");

out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
      
%>



</center>
</form>
</body>
</html>