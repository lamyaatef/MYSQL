<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function checkBeforeSubmit(simCount,simTotalScaned)
        {
          if(simCount != simTotalScaned)
          {
            if(confirm("SIM Count not equal Total SIM Scaned,Continue close sheet?"))
            {
              SFRform.submit();
            }
          }
          else
          { 
            SFRform.submit();
          }
        }
</script>
  <body>
    <CENTER>
      <H2> POS Pending Batch Sheets </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strBatchID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);

  String strAgentID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID+"\""+
                  " value=\""+strAgentID+"\">");
                  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+
                  " value=\""+strBatchID+"\">");                  
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+
                  " value=\""+"\">");                  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+"\""+
                  " value=\""+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+"\""+
                  " value=\""+"\">");  
                  
                  
  Vector sheets = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);                  
  Vector batchDetails = (Vector)objDataHashMap.get(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION); 
  String agentId = "";
  String agentFullName = "";
  String batchStatusTypeId = "";
  String batchStatusTypeName = "";
  Date batchDate = null;
  if(batchDetails.size()>0)
  {
    BatchModel batchModel = (BatchModel)batchDetails.get(0);
    agentId = batchModel.getAgentId();
    agentFullName = batchModel.getAgentFullName();
    if(agentFullName == null)agentFullName = "";
    batchStatusTypeId = batchModel.getBatchStatusTypeId();
    batchStatusTypeName = batchModel.getBatchStatusTypeName();
    batchDate = batchModel.getBatchDate();
  }
%>    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='center'>Batch ID</td>
        <td align='center'>Agent Name</td>
        <td align='center'>Batch Status</td>
        <td align='center'>Creation Date</td>
      </tr>
      <tr>
        <td align='center'><%=strBatchID%></td>
        <td align='center'><%=agentFullName%></td>
        <td align='center'><%=batchStatusTypeName%></td>
        <td align='center'><%=batchDate%></td>
      </tr>
      </table>
    
      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td align='center'>Sheet Serial</td>
          <td>POS Name</td>
          <td>POS Code</td>
          <td>Second POS Code</td>
          <td>Contract Count</td>
        </tr>
        <%
        for(int i=0;i<sheets.size();i++)
        {
        SheetModel sheetModel = (SheetModel)sheets.get(i);
        String sheetSerial = sheetModel.getSheetSerial();
        String batchId = sheetModel.getBatchId();
        String posId = sheetModel.getPosId();
        String posName = sheetModel.getPosName();
        String posCode = sheetModel.getPosCode();
        String secondPosId = sheetModel.getSecondPosId();
        String secondPosCode = sheetModel.getSecondPosCode();
        int sheetSIMCount = sheetModel.getSheetSIMCount();
        String sheetStatusTypeId = sheetModel.getSheetStatusTypeId();
        String sheetStatusTypeName = sheetModel.getSheetStatusTypeName();
        int sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
        int sheetSIMScanedDublicate = sheetModel.getSheetSIMScanedDublicate();
        int sheetSIMScanedUnique = sheetModel.getSheetSIMScanedUnique();
        %>
        <TR class=TableTextNote>
          <td align='center'>
            <%
            /*out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_TOTAL+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSerial+"</A>");
            */
            out.println(sheetSerial);
            %>
          </td>
          <td><%=posName%></td>
          <td><%=posCode%></td>
          <td><%=secondPosCode%></td>
          <td><%=sheetSIMCount%></td>
          </tr>
        <%
        }
        %>
      </table>
      <br><br>
        <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
        </center>
  </form>      
  </body>
</html>
