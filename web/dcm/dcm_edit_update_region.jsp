 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.region.model.*"
         import="com.mobinil.sds.core.system.dcm.region.dao.*"
         import="com.mobinil.sds.core.system.dcm.region.dto.*"

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
  if(eval("document.DCMform.<%=DCMInterfaceKey.INPUT_TEXT_REGION_NAME%>.value") == "")
      {
        alert("Region Name must not be empty.");
        return;
      }
  DCMform.submit();
}
</script>
<%  
   HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

   String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   String strRegionId = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_REGION_MANAG_TEXT_ID);
 
   RegionModel regionModel = (RegionModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
%>
<CENTER>
      <H2> Edit Region </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

 out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.CONTROL_REGION_MANAG_TEXT_ID+"\""+
                  " value=\""+"\">");

  String regionName = "";
  if(regionModel!=null)
  {
    regionName = regionModel.getRegionName();
    if(regionName ==null)
    regionName = "";
  }
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>  

<input type="hidden" name="region_id" size="15" value="<%=strRegionId%>">

<tr>
<td class=TableHeader nowrap>Region Name</td>
<td class=TableTextNote><input type="text" name="region_name" size="15" value="<%=regionName%>"></td>
</tr>
</table>
</center>
<br>
<center>
 <%
   out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value ='"+DCMInterfaceKey.ACTION_UPDATE_REGION_NAME+"';"+
  "checkBeforeSubmit();\">");

  out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");  
      
  %>
</center>
</form>
</body>
</html>
