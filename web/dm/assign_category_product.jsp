<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.io.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dm.*"
         import = " com.mobinil.sds.core.system.*"
         import = "org.apache.poi.hssf.usermodel.HSSFCell"
         import = "org.apache.poi.hssf.usermodel.HSSFCellStyle"
		 import=" org.apache.poi.hssf.usermodel.HSSFRow"
		 import ="org.apache.poi.hssf.usermodel.HSSFSheet"
		 import=" org.apache.poi.hssf.usermodel.HSSFWorkbook"
		 import = "com.mobinil.sds.core.system.dataMigration.model.*"
		 import=" org.apache.poi.hssf.util.CellReference"
		 import=" org.apache.poi.poifs.filesystem.POIFSFileSystem"
%>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
<body>

<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector productVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  String categoryId = (String)objDataHashMap.get(DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID);
  %>
  
  <CENTER>
      <H2> Select Product</H2>
    </CENTER>
    
    <form  name='DMform' id='DMform' action='' method='post'>
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+"\""+
          " value=\""+categoryId+"\">");  
  
  out.println("<input type=\"hidden\" name=\""+DMInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+"\""+
          " value=\""+"\">"); 
    
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
<tr class=TableHeader>
      <td align='center'>Product Name</td>      
     
                <td>
                <select  name="<%=DMInterfaceKey.CONTROL_HIDDEN_PRODUCT_ID%>" id="<%=DMInterfaceKey.CONTROL_HIDDEN_PRODUCT_ID%>">
                <option></option>
                <%
                
                for(int i=0; i<productVec.size();i++){
                	
                	ProductModel  productModel = (ProductModel)productVec.get(i);
                    String product_id = productModel.getProductId();
                    String productName = productModel.getProductName();
                	
                	
                	%>
         <option value="<%=product_id%>"><%=productName%></option>
                <%} %>
                </select>
                </td>
                </tr>
      
       </TABLE>
       </center>
       <br><br>
       <center>
       <%
      out.print("<INPUT class=button type='button' value=\" Save  \" name=\"save\" id=\"save\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_SAVE_CATEGORY_PRODUCT+"';"+
                 "DMform.submit();\">");
       
       out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
       </center>
       </form>
</body>
</html>
