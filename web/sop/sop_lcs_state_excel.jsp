<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"

         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.sop.franchise.model.*"
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
  if(objDataHashMap == null)
    objDataHashMap = new HashMap();
  //String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  InformationModel searchModel = null;
  InformationModel model = null;
  String startDate=null, endDate=null;
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_LCS_SEARCH_MODEL))
  {
    searchModel = (InformationModel) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_LCS_SEARCH_MODEL);
    startDate = objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
    endDate = objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
  }
  response.addHeader("Content-Disposition", "attachment; filename=report.xls");
%>
    <CENTER>
      <H2> LCS State </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
    <%
      if(searchModel != null)
      {
        Vector result = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        if(result.size() != 0)
        {
    %>
    <br><br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    
    <tr class=TableHeader>
      <td  align=center>Franchise Code</td>
      <td  align=center>Item Code</td>
      <td  align=center>LCS Item Code</td>
      <td  align=center>LCS Item Description</td>
      <td  align=center>LCS Date</td> 
      <td  align=center>Quantity</td>   
    </tr> 
    <%
      for(int i=0; i<result.size();i++)
      {
        model = (InformationModel) result.get(i);
        out.println("<tr class="+InterfaceKey.STYLE[i%2]+">");
        out.println("<td>"+model.getFranchiseCode()+"</td>");
        out.println("<td>"+model.getItemCode()+"</td>");
        out.println("<td>"+model.getSourceItemCode()+"</td>");
        out.println("<td>"+model.getSourceItemName()+"</td>");
        out.println("<td align=center>"+model.getDate()+"</td>");        
        out.println("<td align=center>"+model.getQuantity()+"</td>");
        out.println("</tr>");
      }
    %>
    </table>
    <br><br>
    <%
      }
      else
      {
    %>
    No franchises found, try another search issues.
    <%
      }
    }
    %>

<%
  if(searchModel != null)
      out.println("<script>setSearchValues('"+searchModel.getFranchiseCode()+
      "','"+searchModel.getItemCode()+"','"+startDate+"','"+endDate+"');</script>");
%>
</body>
</html>
