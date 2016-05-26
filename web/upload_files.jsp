<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.payment.*" 
         import="com.mobinil.sds.web.interfaces.commission.*"          
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.payment.model.*"
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />

<title>
<script language="JavaScript">
function submitForm()
{

    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    if(fileName != ".xls" || fileName!= ".zip")
    {
      alert("Invalid input. Please specify a right excel file.");
      return;
    }
    

  document.myform.submit();
  
}
</script>
</title>
</head>
<body>
<center><h2>Upload Files</h2></center>
<form name="COMform" action="" method="POST">
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

%>
<table style="BORDER-COLLAPSE: collapse" width="50" border="1" align="center" cellpadding="0" cellspacing="1">
<tr>
<td class=TableHeader>File</td>

<td><input type="file" name="file"></td>
</tr>
<tr>

<td align="center" colspan="2">
<%
  out.println("<input type=\"button\" name=\"submit\" id=\"submit\" value=\"Submit your file\" class=\"button\" onclick=\""+
  "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+CommissionInterfaceKey.ACTION_UPLOAD_FILES+"';"+
  "submitForm();\">");
  %>
</td>
</tr>
<table>
</form>
</body>
</html>
