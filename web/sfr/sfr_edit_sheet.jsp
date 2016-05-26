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
      function checknumber(text)
      {
        var x=text
        var anum=/(^\d+$)|(^\d+\.\d+$)/
        if (anum.test(x))
        testresult=true
        else
        {
        testresult=false
        }
        return (testresult)
      }

      function checkBeforeSubmit()
      {
        var sheetSIMCount = document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_SHEET_SIM_COUNT%>.value
        if(checknumber(sheetSIMCount))
        {
          SFRform.submit();
        }
        else
        {
          alert("Sheet SIM Count is not a number.");
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
  String strSheetSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);
  String strSheetId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID);
  
  String strAgentID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID);

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID+"\""+
                  " value=\""+strAgentID+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+
                  " value=\""+strBatchID+"\">"); 

  Vector sheetDetails = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);                  
  String sheetSerial = "";
  String sheetId = "";
  String batchId = "";
  String posId = "";
  String posName = "";
  String posCode = "";
  String secondPosId = "";
  String secondPosCode = "";
  int sheetSIMCount = 0;
  String sheetStatusTypeId = "";
  String sheetStatusTypeName = "";
  int sheetSIMScanedTotal = 0;
  int sheetSIMScanedDublicate = 0;
  int sheetSIMScanedUnique = 0;
  if(sheetDetails.size()>0)
  {
    SheetModel sheetModel = (SheetModel)sheetDetails.get(0);
    sheetSerial = sheetModel.getSheetSerial();
    sheetId = sheetModel.getSheetId();
    batchId = sheetModel.getBatchId();
    posId = sheetModel.getPosId();
    posName = sheetModel.getPosName();
    posCode = sheetModel.getPosCode();
    secondPosId = sheetModel.getSecondPosId();
    secondPosCode = sheetModel.getSecondPosCode();
    sheetSIMCount = sheetModel.getSheetSIMCount();
    sheetStatusTypeId = sheetModel.getSheetStatusTypeId();
    sheetStatusTypeName = sheetModel.getSheetStatusTypeName();
    sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
    sheetSIMScanedDublicate = sheetModel.getSheetSIMScanedDublicate();
    sheetSIMScanedUnique = sheetModel.getSheetSIMScanedUnique();
  }

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+
                  " value=\""+sheetSerial+"\">");                  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+"\""+
                  " value=\""+sheetId+"\">");

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
%>    
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
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
      <tr class=TableTextNote>
        <td align='center'>Sheet Serial</td>
        <td align='center'><%=sheetSerial%></td>
      </tr>
      <tr class=TableTextNote>
        <td align='center'>POS Code</td>
        <td align='center'><%=posCode%></td>
      </tr>
      <tr class=TableTextNote>
        <td align='center'>Second POS Code</td>
        <td align='center'><%=secondPosCode%></td>
      </tr>
      <tr class=TableTextNote>
        <td align='center'>Contracts Count</td>
        <td align='center'>
          <input type='text' name='<%=SFRInterfaceKey.INPUT_TEXT_SHEET_SIM_COUNT%>' id='<%=SFRInterfaceKey.INPUT_TEXT_SHEET_SIM_COUNT%>' value='<%=sheetSIMCount%>'>
        </td>
      </tr>
      </table>

      <br><br>
    <center>
    <input type="button" class="button" value=" Back " onclick="history.go(-1);">
<%
      
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SAVE_PENDING_SHEET+"';"+
                  "checkBeforeSubmit();\">");         
%>
    </center>
      
        
    </form>
  </body>
</html>
