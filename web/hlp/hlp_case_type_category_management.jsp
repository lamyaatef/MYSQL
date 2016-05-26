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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector superTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
%>
    <CENTER>
      <H2> Case Type Category Management </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID+"\""+
                  " value=\""+"\">");                
%> 
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
     <TR class=TableHeader>
      <TD>Case Type Category Name</TD>
      <TD align="center">Edit</TD>
      <!--TD align="center">Delete</TD-->
     </tr> 
     <%
      for(int f=0;f<superTypeList.size();f++)
      {
        CaseTypeModel caseSuperTypeModel = (CaseTypeModel)superTypeList.get(f);
        String strCaseSupreTypeId = caseSuperTypeModel.getCaseSuperTypeId();
        String strCaseSuperTypeName = caseSuperTypeModel.getCaseSuperTypeName(); 
     %>
     <TR class=<%=InterfaceKey.STYLE[f%2]%>>
      <TD><%=strCaseSuperTypeName%></TD>
      <TD align="center">
      <%
        out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_EDIT_CASE_TYPE_CATEGORY+"';"+
                  "document.HLPform."+HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID+".value = '"+strCaseSupreTypeId+"';"+
                  "HLPform.submit();\">");
      %>
      </TD>
      <!--TD align="center">
      <%
        out.print("<INPUT class=button type=button value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_DELETE_CASE_TYPE_CATEGORY+"';"+
                  "document.HLPform."+HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID+".value = '"+strCaseSupreTypeId+"';"+
                  "HLPform.submit();\">");
      %>
      </TD-->
     </tr>
     <%
      }
     %>
  </table>   

  <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Case Type Category \" name=\"addnew\" id=\"addnew\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_ADD_CASE_TYPE_CATEGORY+"';"+
                  "HLPform.submit();\">");

      %>
      </center>
</form>
  </body>
</html>
