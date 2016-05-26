<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sa.users.dto.*" 
         import ="com.mobinil.sds.core.system.sa.users.model.*"
         import ="com.mobinil.sds.core.system.sa.persons.model.*"
         
         import ="com.mobinil.sds.core.system.sfr.sheets.model.*" 
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SFRform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function setSearchValues(batchId,batchDateFrom,batchDateTo)
        {
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID%>").value = batchId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM%>").value = batchDateFrom;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO%>").value = batchDateTo;
        }
</script>
  <body>
    <CENTER>
      <H2> Manage Pending Batches </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strAgentID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
  int rowNum = Integer.parseInt((String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM));
  int count = Integer.parseInt((String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_COUNT));
  Utility.logger.debug("The Row Num isssssssssssssssssss " + rowNum);
  Utility.logger.debug("The count isssssssssssssssssssssssss " + count);

  String strSearchBatchId = "";
  String strSearchBatchDateFrom = "*";
  String strSearchBatchDateTo = "*";
  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID)){strSearchBatchId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM)){strSearchBatchDateFrom = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO)){strSearchBatchDateTo = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO);}
  
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+"\">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\""+rowNum+"\">"); 

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+
                  " value=\""+"\">");  

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_STATUS_TYPE_ID+"\""+
                  " value=\""+"\">");               

    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID+"\""+
                  " value=\""+strAgentID+"\">");    
                  
  Vector batches = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);                  
  Vector batcheStatusTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector usersList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);

  %>  
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=7>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Batch ID</td>
        <td align=middle><input type='text' name='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID%>' id='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID%>'></td>
        <td align=middle>Creation Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_FROM%>',"*");</script></td>
        <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_CREATION_DATE_TO%>',"*");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=7>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES_SEARCH+"';SFRform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','*','*');\">");          
        %>
        </td>
      </tr>
      </table>
    
      <br><br>
      <%
      if(batches.size()>0)
      {
      %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td align='center'>Batch ID</td>
          <td>Agent</td>
          <td>Agent ID</td>
          <td>Creation Date</td>
          <td align='center'>Status</td>
        </tr>
<%
        for(int i=0;i<batches.size();i++)
        {
        BatchModel batchModel = (BatchModel)batches.get(i);
        String batchId = batchModel.getBatchId();
        String agentId = batchModel.getAgentId();
        String agentFullName = batchModel.getAgentFullName();
        if(agentFullName == null)agentFullName = "";
        String batchStatusTypeId = batchModel.getBatchStatusTypeId();
        String batchStatusTypeName = batchModel.getBatchStatusTypeName();
        Date batchDate = batchModel.getBatchDate();
        String batchSuggestedStatusId = batchModel.getBatchSuggestedStatus();
%>
        <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <td align='center'>
            <%
              out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES_VIEW_SHEETS+"';"+
                    "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+".value = '"+batchId+"';SFRform.submit();\">"+batchId+"</A>");
            %>
          </td>
          <td><%=agentFullName%></td>
          <td><%=agentId%></td>
          <td><%=batchDate%></td>
          <td align='center'>
            <%
            out.println(batchStatusTypeName+"<br>");
        
            out.println("<input type='button' class='button' name='"+SFRInterfaceKey.INPUT_SELECT_BATCH_STATUS_ID+"_"+batchId+"' id='"+SFRInterfaceKey.INPUT_SELECT_BATCH_STATUS_ID+"_"+batchId+"' value='Accept' "+
                          "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_MANAGE_PENDING_BATCHES_UPDATE_STATUS+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+".value = '"+batchId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_BATCH_STATUS_TYPE_ID+".value = '"+SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_ACCEPTED+"';SFRform.submit();\">");
            
            %>
            </select>
          </td>
        </tr>
<%
        }
%>
      </table>
      <br><br>
      <table width=95% border=0 cellspacing=0 cellpadding=0>
 <tr>
 <td align = 'right'>
<%
int totalRows = (rowNum+1)*30;
Utility.logger.debug("The total rows issssssssssss " + totalRows);
  

  if(rowNum>=1)
  {
    //out.println("<a href=\"#\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_PREVIOUS_PGW_STATE+"';"+"document.SOPform.submit();\">Previous</a></td>");

    out.println("<td align = 'right'><INPUT id=button4 class=button type=button value=\"<<\" name=button3 onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_PREVIOUS_MANAGE_PENDING_BATCHES+"';"+"document.SFRform.submit();\">");
  }
  else
  {
    out.println("<INPUT id=button4 class=button type=button value=\"<<\" name=button3 disabled>");
  }

  if(batches.size()>=30&&totalRows!=count)
  {
    //out.println("<td align = 'right'><a href=\"#\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_NEXT_PGW_STATE+"';"+"document.SOPform.submit();\">next</a>");

    out.println("<INPUT id=button3 class=button type=button value=\">>\" name=button3 onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_NEXT_MANAGE_PENDING_BATCHES+"';"+"document.SFRform.submit();\"></td>");
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
%>
      </form>
<%
      out.println("<script>setSearchValues('"+strSearchBatchId+"','"+strSearchBatchDateFrom+"','"+strSearchBatchDateTo+"');</script>");  
%>      
  </body>
</html>
