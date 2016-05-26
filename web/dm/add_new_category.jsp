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
    </head>
  <body>
<script>
    
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
      SOPform.submit();
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
 
            
%>   
    <CENTER>
      <H2> Category </H2>
    </CENTER>
<form name='DMform' id='DMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");
  
               
%> 
      <center>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1> 
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Category Name</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=DMInterfaceKey.INPUT_TEXT_CATEGORY_NAME%>" id="<%=DMInterfaceKey.INPUT_TEXT_CATEGORY_NAME%>" value=""></td>
        </tr>
       
       </table> 
</center>
    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_SAVE_NEW_CATEGORY+"';"+
                  "DMform.submit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
