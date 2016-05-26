<%@ page contentType="text/html;charset=windows-1252"%>
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
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <script>
    function checkBeforeSubmit()
    {
      var caseCategoryName = document.getElementById("<%=HLPInterfaceKey.INPUT_TEXT_CASE_TYPE_NAME%>").value;
      if(caseCategoryName == "")
      {
        alert("Case type name can not be empty");
      }
      else
      {
        HLPform.submit();
      }
    }  
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector superTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
  String caseTypeId = "";
  String caseTypeName = "";
  String caseSuperTypeId = "";
  String caseSuperTypeName = "";
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    CaseTypeModel caseTypeModel = (CaseTypeModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    caseTypeId = caseTypeModel.getCaseTypeId();
    caseTypeName = caseTypeModel.getCaseTypeName();
    caseSuperTypeId = caseTypeModel.getCaseSuperTypeId();
    caseSuperTypeName = caseTypeModel.getCaseSuperTypeName();
  }
%>
    <CENTER>
      <H2> Case Type </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID+"\""+
                  " value=\""+caseTypeId+"\">");                   
%>
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableTextNote>
          <TD width=\"20%\">Case Type Name</TD>
          <TD colSpan=4><INPUT type='text' name="<%=HLPInterfaceKey.INPUT_TEXT_CASE_TYPE_NAME%>" id="<%=HLPInterfaceKey.INPUT_TEXT_CASE_TYPE_NAME%>" value="<%=caseTypeName%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Case Type Category</TD>
          <td colSpan=4>
            <select name="<%=HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID%>">
              <%
              for(int j=0;j<superTypeList.size();j++)
              {
                CaseTypeModel caseSuperTypeModel = (CaseTypeModel)superTypeList.get(j);
                String strCaseSuperTypeIdX = caseSuperTypeModel.getCaseSuperTypeId();
                String strCaseSuperTypeNameX = caseSuperTypeModel.getCaseSuperTypeName();
                String caseSuperTypeSelected = "";
                if(caseSuperTypeId.compareTo(strCaseSuperTypeIdX) == 0)
                {
                  caseSuperTypeSelected = "selected";
                }
                %>
                <option value="<%=strCaseSuperTypeIdX%>" <%=caseSuperTypeSelected%>><%=strCaseSuperTypeNameX%></option>
                <%
              }
              %>
            </select>
          </td>
         </tr> 
   </table>   

   <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_CASE_TYPE+"';"+
                  "checkBeforeSubmit();\">");

       out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

      %>
      </center>
</form>
  </body>
</html>
