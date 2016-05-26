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
		 import = "com.mobinil.sds.core.system.dataMigration.model.CategoryModel"
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
  Vector categoryVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  %>
  
  <CENTER>
      <H2> Category List</H2>
    </CENTER>
    
    <form  name='DMform' id='DMform' action='' method='post'>
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+"\""+
          " value=\""+"\">");  
    
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
<tr class=TableHeader>
      <td align='center'>Category Name</td>
      <td align='center'>Products</td>
      <td align='center'>Delete</td>
     
      </tr>
      <%
      for (int i =0;i<categoryVec.size();i++)
      {
    	  CategoryModel categoryModel = (CategoryModel)categoryVec.get(i);
    	  String categoryId = categoryModel.getCategoryId();
    	  String categoryName = categoryModel.getCategoryName();
    	  
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td align='center'><%=categoryName%></td>
      	<td align='center'>
      	<%
              out.print("<INPUT class=button type='button' value=\" Products \" name=\"product\" id=\"product\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_VIEW_CATEGORY_PRODUCT+"';"+
                  "document.DMform."+DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+".value = '"+categoryId+"';"+
                  "DMform.submit();\">");
          %>
      	</td>
      	<td align='center'>
      	<%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_DELETE_CATEGORY+"';"+
                  "document.DMform."+DMInterfaceKey.INPUT_HIDDEN_CATEGORY_ID+".value = '"+categoryId+"';"+
                  "DMform.submit();\">");
          %>
      	</td>
      	</tr>
    	  <%
      }
      %>
      
       </TABLE>
       </center>
       <br><br>
       <center>
       <%
      out.print("<INPUT class=button type='button' value=\" Add new Category  \" name=\"new\" id=\"new\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_CREATE_NEW_CATEGORY+"';"+
                 "DMform.submit();\">");
       
      // out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
       </center>
       </form>
</body>
</html>
