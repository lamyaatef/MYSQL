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
<script language="javascript">
 $(document).ready(function(){$("#FranchiseState").tablesorter(); });
</script>
<script>

        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" readonly class=input name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function setSearchValues(franchiseCode,itemCode,pgwItemCode,startDate,endDate,itemName)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>").value = franchiseCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_ITEM_CODE%>").value = itemCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_ITEM_NAME%>").value = itemName;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_PGW_ITEM_CODE%>").value = pgwItemCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_START_DATE%>").value = startDate;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_END_DATE%>").value = endDate;
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
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  if(objDataHashMap == null)
    objDataHashMap = new HashMap();
  //String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  InformationModel searchModel = null;
  InformationModel model = null;
  String startDate=null, endDate=null;
  int rowNum =0;
  int count = 0;
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_PGW_SEARCH_MODEL))
  {
    searchModel = (InformationModel) objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_PGW_SEARCH_MODEL);
    startDate = objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_START_DATE).toString();
    endDate = objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_END_DATE).toString();
    rowNum = Integer.parseInt((String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM));
    count = Integer.parseInt((String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_COUNT));
    //Utility.logger.debug("The count isssssssssssssssss " + count);
   
  }


 
                  //Utility.logger.debug("The Row Num issssssssssss " + rowNum);
  
%>
    <CENTER>
      <H2> PGW State </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>

<%
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\""+rowNum+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");
%>
  <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>"
                  value="<%=SOPInterfaceKey.ACTION_SEARCH_LCS_STATE%>">
  
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td>Franchise Code</td>
        <td colspan=2><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>' value="Any" ></td>
        <td>Item Code</td>
        <td colspan=2><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_ITEM_CODE%>' value="Any"></td>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_TEXT_PGW_ITEM_CODE%>' value="Any">
      </TR>
      <TR class=TableTextNote>
        <td>PGW Date From</td>
        <td colspan=2><Script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_START_DATE%>','Any');</script></td>
        <td>To</td>
        <td colspan=2><script>drawCalender('<%=SOPInterfaceKey.INPUT_TEXT_END_DATE%>','Any');</script></td>
        <input type='hidden' name='<%=SOPInterfaceKey.INPUT_TEXT_ITEM_NAME%>' value="Any">
      </TR>
      <tr>
        <td align='center' colspan=6>
        <%
        out.println("<INPUT class=button type=submit value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_PGW_STATE+"';"+
                  "\">");

        out.println("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('Any','Any','Any','Any','Any','Any');\">");          
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
      <th>Item Code</th>
      <!--td  align=center>Item Name</td>
      <td  align=center>PGW Item Code</td-->
      <th>PGW Date</th> 
      <th>Quantity</th>   
    </tr> 
    </thead>
    <tbody>
    <%
      for(int i=0; i<result.size();i++)
      {
        model = (InformationModel) result.get(i);
        out.println("<tr class="+InterfaceKey.STYLE[i%2]+">");
        out.println("<td>"+model.getFranchiseCode()+"</td>");
        out.println("<td>"+model.getItemCode()+"</td>");
        //out.println("<td>"+model.getSourceItemName()+"</td>");
        //out.println("<td>"+model.getSourceItemCode()+"</td>");
        out.println("<td align=center>"+model.getDate()+"</td>");        
        out.println("<td align=center>"+model.getQuantity()+"</td>");
        out.println("</tr>");
      }
    %>
    </tbody>
    <tr>
      <td colspan=8 align=center>
        <input type=button class=button value="Export to Excel" 
        onclick="document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=
        SOPInterfaceKey.ACTION_EXCEL_PGW_STATE%>'; document.SOPform.submit();">
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
    //out.println("<a href=\"#\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_PREVIOUS_PGW_STATE+"';"+"document.SOPform.submit();\">Previous</a></td>");

    out.println("<td align = 'right'><INPUT id=button4 class=button type=button value=\"<<\" name=button3 onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_PREVIOUS_PGW_STATE+"';"+"document.SOPform.submit();\">");
  }
  else
  {
    out.println("<INPUT id=button4 class=button type=button value=\"<<\" name=button3 disabled>");
  }

  if(result.size()>=49&&totalRows!=count)
  {
    //out.println("<td align = 'right'><a href=\"#\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_NEXT_PGW_STATE+"';"+"document.SOPform.submit();\">next</a>");

    out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_NEXT_PGW_STATE+"';"+"document.SOPform.submit();\"></td>");
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
      out.println("<script>setSearchValues('"+searchModel.getFranchiseCode()+
      "','"+searchModel.getItemCode()+"','"+searchModel.getSourceItemCode()+"','"+startDate+"','"+endDate+"','"+searchModel.getSourceItemName()+"');</script>");
%>
</body>
</html>
