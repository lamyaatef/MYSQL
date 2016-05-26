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
  <script>
   function checkBeforeSubmit()
    {
	   if(eval("document.CRform.<%=ContractRegistrationInterfaceKey.INPUT_TEXT_DCM_CODE%>.value") == "")
	      {
	        alert("DCM Code must not be empty.");
	        return;
	      }
	    
     

      if(eval("document.CRform.<%=ContractRegistrationInterfaceKey.INPUT_TEXT_LCS_NAME%>.value") == "")
      {
        alert("LCS Name must not be empty.");
        return;
      }
      CRform.submit();
      }
    </script>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   LcsDcmMappingModel lcsDcmMappingModel = (LcsDcmMappingModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

%> 

 <CENTER>
      <H2> LCS DCM Mapping  </H2>
    </CENTER>
<form name='CRform' id='CRform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
	
  String dcmLcsId = "";
  String dcmCode="";
  String dcmName="";
  String lcsName="";
  String nextAction = ContractRegistrationInterfaceKey.ACTION_SAVE_LCS_DCM_MAPPING;
  if(lcsDcmMappingModel != null)
  {
    nextAction = ContractRegistrationInterfaceKey.ACTION_UPDATE_LCS_DCM_MAPPING;
    dcmLcsId = lcsDcmMappingModel.getDcmLcsNameId();
    dcmCode = lcsDcmMappingModel.getDcmCode();
    if (dcmCode == null)dcmCode = "";
    dcmName = lcsDcmMappingModel.getDcmName();
    lcsName = lcsDcmMappingModel.getLcsName();
    if (lcsName == null) lcsName = ""; 
  }
  
  out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.INPUT_HIDDEN_DCM_LCS_NAME_ID+"\""+
          " value=\""+dcmLcsId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_DCM_CODE+"\""+
          " value=\""+dcmCode+"\">"); 
  
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_DCM_NAME+"\""+
                  " value=\""+dcmName+"\">"); 

     out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey .INPUT_HIDDEN_LCS_NAME+"\""+
                  " value=\""+lcsName+"\">"); 
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>  
<tr>
<td class=TableHeader nowrap>DCM Code</td>
<td class=TableTextNote><input type="text" name="dcm_code" size ="40%" value="<%=dcmCode%>"></td>
</tr>

<tr>
<td class=TableHeader nowrap>LCS Name</td>
<td class=TableTextNote><input type="text" name="lcs_name" size ="40%" value="<%=lcsName%>"></td>
</tr>

</table>
</center>
<br>
<center>
<%
                  
     out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.CRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                 "checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>
</form>
</body>
</html>
