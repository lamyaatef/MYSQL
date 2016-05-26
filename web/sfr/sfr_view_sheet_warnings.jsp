<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"
         import="java.sql.Timestamp"

          
         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"   
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<body>
    <CENTER>
      <H2> Sheet Warnings </H2>
    </CENTER>

<form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strSheetSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+
                  " value=\""+strSheetSerial+"\">");                   
                  
  Vector sheetWarnings = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);  

  Vector sheetDetails = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    String batchId = "";
    String posId = "";
    String posName = "";
    String posCode = "";
    String secondPosId = "";
    String secondPosCode = "";
    int sheetSIMCount = 0;
    String sheetStatusTypeIdX = "";
    String sheetStatusTypeNameX = "";
    int sheetSIMScanedTotal = 0;
    int sheetSIMScanedDublicate = 0;
    int sheetSIMScanedUnique = 0;
    if(sheetDetails.size()>0)
    {
      SheetModel sheetModel = (SheetModel)sheetDetails.get(0);
      batchId = sheetModel.getBatchId();
      posId = sheetModel.getPosId();
      posName = sheetModel.getPosName();
      posCode = sheetModel.getPosCode();
      secondPosId = sheetModel.getSecondPosId();
      secondPosCode = sheetModel.getSecondPosCode();
      sheetSIMCount = sheetModel.getSheetSIMCount();
      sheetStatusTypeIdX = sheetModel.getSheetStatusTypeId();
      sheetStatusTypeNameX = sheetModel.getSheetStatusTypeName();
      sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
      sheetSIMScanedDublicate = sheetModel.getSheetSIMScanedDublicate();
      sheetSIMScanedUnique = sheetModel.getSheetSIMScanedUnique();
    }
    %>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='center'>Sheet Serial</td>
        <td align='center'>POS Code</td>
        <td align='center'>Second POS Code</td>
        <td align='center'>Sheet SIM Count</td>
        <td align='center'>Sheet Status</td>
      </tr>
      <tr>
        <td align='center'><%=strSheetSerial%></td>
        <td align='center'><%=posCode%></td>
        <td align='center'><%=secondPosCode%></td>
        <td align='center'><%=sheetSIMCount%></td>
        <td align='center'><%=sheetStatusTypeNameX%></td>
      </tr>
      </table>

       <br><br>
      <%
      if(sheetWarnings.size()!=0)
      {
      %>
       <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td align='center'>Warning</td>
          <td align='center'>Warning Timestamp</td>
          <td align='center'>User</td>
        </tr>
      <%
      }
      else
      {
      %>
        <TABLE border=0>
        <tr>
          <td align='left'><h3>There no warnings for this sheet.</h3></td>
        </tr>
      <%
      }
      %>
        <%
        for(int i=0;i<sheetWarnings.size();i++)
        {
        SheetWarningModel SheetWarningModel = (SheetWarningModel)sheetWarnings.get(i);
        String sheetWarningId = SheetWarningModel.getSheetWarningId() ;
        String sheetWarningTypeId = SheetWarningModel.getSheetWarningTypeId();
        String sheetWarningTypeName = SheetWarningModel.getSheetWarningTypeName();
        String sheetId = SheetWarningModel.getSheetId();
        Timestamp sheetWarningTimestamp = SheetWarningModel.getSheetWarningTimestamp();
        String userId = SheetWarningModel.getUserId();
        String personFullName = SheetWarningModel.getPersonFullName();
        %>
        <TR class=TableTextNote>
          <td align='center'><%=sheetWarningTypeName%></td>
          <td align='center' nowrap><%=sheetWarningTimestamp%></td>
          <td align='center'><%=personFullName%></td>
        </tr>
        <%
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
