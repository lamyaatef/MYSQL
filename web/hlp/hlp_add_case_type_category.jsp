<%@ page contentType="text/html;charset=windows-1256"%>

<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.core.system.hlp.casepkg.model.*"
         import="com.mobinil.sds.core.system.hlp.casepkg.dto.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.persons.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <script>
    function checkBeforeSubmit()
    {
      var caseTypeCategoryName = document.getElementById("<%=HLPInterfaceKey.INPUT_TEXT_SUPER_TYPE_NAME%>").value;
      if(caseTypeCategoryName == "")
      {
        alert("Case type category name can not be empty");
      }
      else
      {
        //alert(caseTypeCategoryName);
        HLPform.submit();
      }
    }  
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String caseSuperTypeId = "";
  String caseSuperTypeName = "";
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    CaseTypeModel caseSuperTypeModel = (CaseTypeModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    caseSuperTypeId = caseSuperTypeModel.getCaseSuperTypeId();
    caseSuperTypeName = caseSuperTypeModel.getCaseSuperTypeName();
  }
%>
    <CENTER>
      <H2> Case Category </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID+"\""+
                  " value=\""+caseSuperTypeId+"\">");                   
%>
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Type Category Name</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=HLPInterfaceKey.INPUT_TEXT_SUPER_TYPE_NAME%>" id="<%=HLPInterfaceKey.INPUT_TEXT_SUPER_TYPE_NAME%>" value="<%=caseSuperTypeName%>"></td>
        </tr>  
   </table>   

   <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_CASE_TYPE_CATEGORY+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

      %>
      </center>
</form>
  </body>
</html>
