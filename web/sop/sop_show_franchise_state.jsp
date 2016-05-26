<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
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
<%
  String appName = request.getContextPath();
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>    
      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
      <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
    </head>
  <body>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function setSearchValues(franchiseCode,itemCode)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>").value = franchiseCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_ITEM_CODE%>").value = itemCode;
        }
        function Toggle(item) 
        {
          obj=document.getElementById(item);
          if (obj!=null) 
          {
            visible=(obj.style.display!="none")
            key=document.getElementById("x"+item);
            if (visible) 
            {
              obj.style.display="none";
              key.innerHTML="<img src='../resources/img/plus.gif' border='0'>";
            } 
            else 
            {
              obj.style.display="block";
              key.innerHTML="<img src='../resources/img/minus.gif' border='0'>";
            }
          }
        }
</script>  
<script type="text/javascript">
 $(document).ready(function(){$("#FranchiseState").tablesorter(); });
</script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  if(objDataHashMap == null)
    objDataHashMap = new HashMap();
  //String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  FranchiseModel searchModel = null;
  FranchiseModel model = null;
  int rowNum = 0;
  int count = 0;
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL))
    {
      searchModel = (FranchiseModel) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_FRANCHISE_SEARCH_MODEL);
      rowNum = Integer.parseInt((String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
      count = Integer.parseInt((String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
      Utility.logger.debug("The count isssssssssssssssss " + count);
    }
  
%>
    <CENTER>
      <H2> Franchise Current State </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>

<%
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\""+rowNum+"\">");

   out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");
%>
  <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>"
                  value="">
  
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Franchise Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>' value="Any" ></td>
        <td align=middle>Item Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_ITEM_CODE%>' value="Any"></td>
        </td>
      </TR>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_FRANCHISE_STATE+"';"+
                  "SOPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('Any','Any');\">");          
        %>
        </td>
      </tr>
    </table> 
    <%
      if(searchModel != null)
      {
        Vector result = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        if(result.size() != 0)
        {
    %>
    <br><br>
   
    <TABLE class="tablesorter" id="FranchiseState">
    <thead>
    <tr>
      <th>Franchise Code</th>
      <th>Franchise Name</th>
      <th>Item Code</th>
      <th>LCS Code</th>
      <th>LCS Desc</th>
      <th>LCS last
       Date</th>
      <th>LCS Quantity</th>
      <th>PGW last Date </th>
      <th>PGW Quantity</th>
      <th>Current Stock Date </th>
      <th>Current Stock Quantity</th>
      <!--td colspan=4 align=center>LCS</td> 
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
      <td align=center>Quantity</td-->  
    </tr>
    </thead>
    <tbody>
    <%
      for(int i=0; i<result.size();i++)
      {
        model = (FranchiseModel) result.get(i);
        out.println("<tr>");
        out.println("<td>"+model.getCode()+"</td>");
        out.println("<td>"+model.getFranchiseName()+"</td>");
        out.println("<td>"+model.getItemCode()+"</td>");
        String lcsItemCode = model.getLCSItemCode();
        if(lcsItemCode == null)lcsItemCode = "";
        out.println("<td>"+lcsItemCode+"</td>"); 
        String lcsItemDescription = model.getLCSItemDescription();
        if(lcsItemDescription == null)lcsItemDescription = "";
        out.println("<td>"+lcsItemDescription+"</td>");
        out.println("<td>"+model.getLastLCSDate()+"</td>");        
        out.println("<td>"+model.getLCSQuantity()+"</td>");
        out.println("<td>"+model.getLastPGWDat()+"</td>");
        out.println("<td>"+model.getPGWQuantity()+"</td>");
        out.println("<td>"+model.getCurrentDate()+"</td>");
        out.println("<td>"+model.getItemQuantity()+"</td>");
        out.println("</tr>");
      }
    %>
    </tbody>
    <tr>
      <td colspan=11 align=center>
        <input type=button class=button value="Export to Excel" 
        onclick="document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=
        SOPInterfaceKey.ACTION_EXCEL_FRANCHISE_STATE%>'; document.SOPform.submit();">
      </td>
    </tr>
    </table>
    <br><br>

    
   
      <table width=95% border=0 cellspacing=0 cellpadding=0>
 <tr>
 <td align = 'right'>
<%
int totalRows = (rowNum+1)*49;
Utility.logger.debug("The total rows issssssssssss " + totalRows);
  

  if(rowNum>=1)
  {
    //out.println("<a href=\"#\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_PREVIOUS_FRANCHISE_STATE+"';"+"document.SOPform.submit();\">Previous</a></td>");

     out.println("<td align = 'right'><INPUT id=button4 class=button type=button value=\"<<\" name=button3 onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_PREVIOUS_FRANCHISE_STATE+"';"+"document.SOPform.submit();\">");
  }
  else
  {
    out.println("<INPUT id=button4 class=button type=button value=\"<<\" name=button3 disabled>");
  }

  if(result.size()>=49&&totalRows!=count)
  {
    //out.println("<td align = 'right'><a href=\"#\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_NEXT_FRANCHISE_STATE+"';"+"document.SOPform.submit();\">next</a>");

    out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_NEXT_FRANCHISE_STATE+"';"+"document.SOPform.submit();\"></td>");
  }
  else
  {
    out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 disabled></td>");
  }
  
    %> 

    </td>
    <tr>
   </table>
      <%
      }
      else
      {
    %>
    No franchises found, try another search issues.
    <%
      }
    %>
    
    <%
    }
    %>

<%
  if(searchModel != null)
      out.println("<script>setSearchValues('"+searchModel.getCode()+
      "','"+searchModel.getItemCode()+"');</script>");
%>
</body>
</html>
