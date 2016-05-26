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
  FranchiseModel searchModel = null;
  FranchiseModel model = null;
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL))
    searchModel = (FranchiseModel) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL);

  response.addHeader("Content-Disposition", "attachment; filename=report.xls");
%>
    <CENTER>
      <H2> Franchise Current State </H2>
    </CENTER>
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
      <td rowspan=2 align=center>Franchise Code</td>
      <td rowspan=2 align=center>Franchise Name</td>
      <td rowspan=2 align=center>Item Code</td>
      <td colspan=4 align=center>LCS</td> 
      <td colspan=2 align=center>PGW</td>   
      <td colspan=2 align=center>Current Stock</td>
    </tr> 
    <tr class=TableHeader>
      <td align=center>Code</td>
      <td align=center>Desc</td>
      <td align=center>Date</td>
      <td align=center>Quantity</td>    
      <td align=center>Date</td>
      <td align=center>Quantity</td>
      <td align=center>Date</td>
      <td align=center>Quantity</td>  
    </tr>
    <%
      for(int i=0; i<result.size();i++)
      {
        model = (FranchiseModel) result.get(i);
        out.println("<tr class="+InterfaceKey.STYLE[i%2]+">");
        out.println("<td>"+model.getCode()+"</td>");
        out.println("<td>"+model.getFranchiseName()+"</td>");
        out.println("<td>"+model.getItemCode()+"</td>");
        out.println("<td align=center nowrap>"+model.getLCSItemCode()+"</td>");        
        out.println("<td align=center nowrap>"+model.getLCSItemDescription()+"</td>"); 
        out.println("<td align=center>"+model.getLastLCSDate()+"</td>");        
        out.println("<td align=center>"+model.getLCSQuantity()+"</td>");
        out.println("<td align=center>"+model.getLastPGWDat()+"</td>");
        out.println("<td align=center>"+model.getPGWQuantity()+"</td>");
        out.println("<td align=center>"+model.getCurrentDate()+"</td>");
        out.println("<td align=center>"+model.getItemQuantity()+"</td>");
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
</body>
</html>
