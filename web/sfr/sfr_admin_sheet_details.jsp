<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.Timestamp"
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

        function checkBeforeSubmit(simCount,simTotalScaned)
        {
          if(simCount != simTotalScaned)
          {
            var inputs = document.getElementsByTagName("input");
            var warningChecked = 0;
            for(var i=0;i<inputs.length;i++)
            {
              if(inputs[i].name.indexOf('<%=SFRInterfaceKey.INPUT_SELECT_SHEET_WARNING_ID%>')==0)
              {
                if(inputs[i].checked == true)
                {
                  warningChecked += 1;  
                }
              }
            }
      
            if(warningChecked == 0)
            {
              alert("You must choose at least one warning because SIM Count not equal Total SIM Scaned.");
              return;
            }
            else
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
      <H2> Admin Sheet Details </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strBatchID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+
                  " value=\""+strBatchID+"\">");                  
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+
                  " value=\""+"\">");                  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+"\""+
                  " value=\""+"\">");      
                  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+"\""+
                  " value=\""+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+"\""+
                  " value=\""+"\">");  
                  
                  
  Vector sheets = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);                  
  Vector sheetStatusTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION); 
  Vector sheetWarnings = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2); 
  Vector batchDetails = (Vector)objDataHashMap.get(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION); 
  Vector sheetWarningTypes = (Vector)objDataHashMap.get(SFRInterfaceKey.SFR_HASHMAP_KEY_ADDITIONAL_COLLECTION); 

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
          <td align='center'>POS Name</td>
          <td align='center'>POS Code</td>
          <td align='center'>Second POS Code</td>
          <td align='center'>Contract Count</td>
          <td align='center'>Status</td>
        </tr>
        <%
        String sheetStatusTypeId ="";
        int sheetSIMScanedTotal = 0; 
        for(int i=0;i<sheets.size();i++)
        {
        SheetModel sheetModel = (SheetModel)sheets.get(i);
        String sheetSerial = sheetModel.getSheetSerial();
        String sheetId = sheetModel.getSheetId();
        String batchId = sheetModel.getBatchId();
        String posId = sheetModel.getPosId();
        String posName = sheetModel.getPosName();
        String posCode = sheetModel.getPosCode();
        String secondPosId = sheetModel.getSecondPosId();
        String secondPosCode = sheetModel.getSecondPosCode();
        int sheetSIMCount = sheetModel.getSheetSIMCount();
        sheetStatusTypeId = sheetModel.getSheetStatusTypeId();
        String sheetStatusTypeName = sheetModel.getSheetStatusTypeName();
        sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
        int sheetSIMScanedDublicate = sheetModel.getSheetSIMScanedDublicate();
        int sheetSIMScanedUnique = sheetModel.getSheetSIMScanedUnique();
        %>
        <TR class=TableTextNote>
          <td align='center'>
            <%
            out.println(sheetSerial);
            %>
          </td>
          <td align='center'><%=posName%></td>
          <td align='center'><%=posCode%></td>
          <td align='center'><%=secondPosCode%></td>
          <td align='center'><%=sheetSIMCount%></td>
          <td align='center'>
          <%
          out.println(sheetStatusTypeName+"<br>");
          if(sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED)==0 && sheetSIMScanedTotal>0)
          {
            out.println("<input type='button' class='button' name='"+SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID+"_"+sheetSerial+"' "+
                                            "id='"+SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID+"_"+sheetSerial+"' value='close' "+
                                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_ADMIN_SHEET_DETAIL_UPDATE_STATUS+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+SFRInterfaceKey.CONST_SIM_STATUS_TYPE_CLOSED+"';"+
                                            "checkBeforeSubmit("+sheetSIMCount+","+sheetSIMScanedTotal+");\">");
          }
          %>
          </td>
          </tr>
            <td colspan=7>
              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                <tr class=TableHeader>  
                  <td align=middle>Total Contracts Scaned</td>
                  <td align=middle>Unique Contracts Scaned</td>
                  <td align=middle>Dublicate Contracts Scaned</td>
                </tr>
                <tr class=TableTextNote>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_TOTAL+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedTotal+"</a>");
                  %>
                  </td>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_UNIQUE+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedUnique+"</a>");
                  %>
                  </td>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_ADMIN_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_DUBLICATE+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedDublicate+"</a>");
                  %>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </tr>
        <%
        }
        %>
      </table>

      <br><br>
      <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
          <tr class=TableHeader>
            <td align='center' colspan=3>Warnings</td>
          </tr>
          <%
          if(sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED)==0 && sheetSIMScanedTotal>0)
          {
            for(int w=0;w<sheetWarningTypes.size();w++)
            {
              SheetWarningTypeModel sheetWarningTypeModel = (SheetWarningTypeModel)sheetWarningTypes.get(w);
              String sheetWarningTypeId = sheetWarningTypeModel.getSheetWarningTypeId();
              String sheetWarningTypeName = sheetWarningTypeModel.getSheetWarningTypeName();
              String suggestedSheetStatusTypeId = sheetWarningTypeModel.getSuggestedSheetStatusTypeId();
              %>
              <tr class=TableTextNote>  
                <td align='center'>
                  <input type="checkbox" name='<%=SFRInterfaceKey.INPUT_SELECT_SHEET_WARNING_ID%>_<%=sheetWarningTypeId%>' id='<%=SFRInterfaceKey.INPUT_SELECT_SHEET_WARNING_ID%>_<%=sheetWarningTypeId%>'>
                </td>
                <td align='left' colspan=2>
                  <%=sheetWarningTypeName%>
                </td>
              </tr>
              <%
            }
          }
          else
          {
            for(int w=0;w<sheetWarnings.size();w++)
            {
            SheetWarningModel SheetWarningModel = (SheetWarningModel)sheetWarnings.get(w);
            String sheetWarningId = SheetWarningModel.getSheetWarningId() ;
            String sheetWarningTypeId = SheetWarningModel.getSheetWarningTypeId();
            String sheetWarningTypeName = SheetWarningModel.getSheetWarningTypeName();
            String sheetId = SheetWarningModel.getSheetId();
            Timestamp sheetWarningTimestamp = SheetWarningModel.getSheetWarningTimestamp();
            String userId = SheetWarningModel.getUserId();
            String personFullName = SheetWarningModel.getPersonFullName();
            %>
              <TR class=TableTextNote>
                <td align='center' nowrap><%=sheetWarningTypeName%></td>
                <td align='center' nowrap><%=sheetWarningTimestamp%></td>
                <td align='center' nowrap><%=personFullName%></td>
              </tr>
            <%
            }
          }
          %>
          
      </table>
      <br><br>
        <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
          <input type="button" class="button" value=" Print " onclick="window.print();">
        </center>
  </form>  
  </body>
</html>
