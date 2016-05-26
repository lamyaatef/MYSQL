<%-- 
    Document   : iqrarPrintManyRequest
    Created on : Oct 31, 2010, 2:03:55 PM
    Author     : Salma
--%>

<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <TITLE>Upload POS Codes</TITLE>
      
   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">

<center>
          <br><br>
      <h2>Upload POS Codes For Iqrar Printing</h2>
      </center>
<br><br>
<script>
function submitFormPDF()
{

    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    

  document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+"47"+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+"INSERT"
  document.myform.submit();

}
</script>
<%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="+
                    SCMInterfaceKey.ACTION_MANY_POS_IQRAR_SAVE
                    +
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="+strUserID;
%>
<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">

    <center>

  <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                        <tr class=TableTextNote>
                            <td>
                                POS Codes File
                            </td>
                            <td>
                                <input type="file" name="myfile" id="myfile">
                            </td>
                        </tr>
                    </table>
                    <br>

    <input class="button" type="button" name="Submit" value="Submit your file" onclick="submitFormPDF();">
    </center>
    </form>
   </BODY>
</HTML>
