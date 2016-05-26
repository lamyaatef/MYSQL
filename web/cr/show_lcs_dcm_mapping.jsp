<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dao.*"
         import="com.mobinil.sds.core.system.cr.sheet.dao.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector vecLCS = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
%> 

<CENTER>
      <H2> LCS DCM Mapping </H2>
    </CENTER>

    <form name='CRform' id='CRform' action='' method='post'>
    <%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
  out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_LCS_NAME+"\""+
                  " value=\""+"\">"); 

  out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_DCM_NAME+"\""+
                  " value=\""+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_DCM_CODE+"\""+
          " value=\""+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_DCM_LCS_NAME_ID+"\""+
          " value=\""+"\">"); 
%>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
    <td align='center'>DCM Code</td>
    
     <td align='center'>LCS Name</td>
     <td align='center'>Edit</td>
      </tr>
      <%
 for (int i=0;i<vecLCS.size();i++)
 {
  LcsDcmMappingModel lcsDcmMappingModel = (LcsDcmMappingModel)vecLCS.get(i);
  String dcmLcsId = lcsDcmMappingModel.getDcmLcsNameId();
  String dcmCode = lcsDcmMappingModel.getDcmCode();
  if(dcmCode == null){dcmCode = "";}
  String dcmName = lcsDcmMappingModel.getDcmName();
  if(dcmName == null){dcmName = "";}
  String lcsName = lcsDcmMappingModel.getLcsName();
  if(lcsName == null){lcsName = "";}
%>
<tr class=TableTextNote>
<td align='center'><%=dcmCode%></td>

<td align='center'><%=lcsName%></td>
<td align ='center'>
    <%
      out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.CRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+ContractRegistrationInterfaceKey.ACTION_EDIT_LCS_DCM_MAPPING+"';"+
          "document.CRform."+ContractRegistrationInterfaceKey.INPUT_HIDDEN_DCM_LCS_NAME_ID+".value = '"+dcmLcsId+"';"+
          "CRform.submit();\">");
   %>
</td>

</tr>
<%
  }
%>
</table>
 <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Add New LCS DCM Mapping \" name=\"new\" id=\"new\" onclick=\"document.CRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+ContractRegistrationInterfaceKey.ACTION_ADD_LCS_DCM_MAPPING+"';"+
                  "CRform.submit();\">");
%>

</center>
 </form>
  </body>
</html>