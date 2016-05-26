<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.ac.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
%>
<%
/**
 * Choose_Contracts_For_Authentication_Call.jsp.
 * Displays Contratcs ready for authentication call. By default the page display
 * all authenticated contracts grouped by sheets. Contracts displayed category 
 * could be changed using one of the radio buttons.
 * 
 * 
 * 
 * showSheets(HttpServletRequest , HttpServletResponse , JspWriter): 
 * used to display contracts grouped by sheets of the selected batchs.
 *
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 
 
    String appName = request.getContextPath();
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <Center>
      <H2>Authentication Call</H2>
    </Center>
        
    <left>
     <h3>Choose Contract for Authentication Call</h3>
    </left>
      <SCRIPT language=JavaScript>
        function checkbeforSubmit()
        {
          if(document.AuthCall.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);%>.disabled==false)
          {
            if(NonBlank(document.AuthCall.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);%>, true, 'Contract Main Sim NO'))
            {
              document.AuthCall.submit();          
            }
            return false;
          }
          else
          {
            document.AuthCall.submit();          
          }
        }
        
        function removeRow(tableName,rowId)
        {
          var table = document.getElementById(tableName);
          var tBody = table.getElementsByTagName("tbody")[0];
          var row = document.getElementById(rowId);
          tBody.removeChild(row);
        }
      </script>
      <%
        showSheets(request, response, out);
      %>
  </body>
</html>


<% 
  out.println("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">");
  out.println("<script type=\"text/javascript\">");
  out.println("function Toggle(item) {");
  out.println("obj=document.getElementById(item);");
  out.println("if (obj!=null) {");
  out.println("visible=(obj.style.display!=\"none\")");
  out.println("key=document.getElementById(\"x\" + item);");     
  out.println("if (visible) {");
  out.println("obj.style.display=\"none\";");
  out.println("key.innerHTML=\"<img src=\'"+appName+"/resources/img/plus.gif\'>\";");
  out.println("} else {");
  out.println("obj.style.display=\"block\";");
  out.println("key.innerHTML=\"<img src='"+appName+"/resources/img/minus.gif\'>\";");      
  out.println("}}}");
  out.println("</script>");  
%>
<%!
/**
 * showSheets method:
 * Display contracts grouped by sheets of the selected batch.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  public void showSheets(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    HashMap sheetsStatistic = (HashMap) dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL);

    Hashtable additionalCollection = (Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

    Hashtable sheetsReimportedTable = (Hashtable)dataHashMap.get(ContractRegistrationInterfaceKey.SHEET_REIMPORTED_TABLE);

    //Utility.logger.debug("sheetsReimportedTable " + sheetsReimportedTable);

    
    
    Vector dataVector = (Vector)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_BATCH_DTO);
    if(dataVector != null && dataVector.size()!=0)
    {
      for(int i = 0; i<dataVector.size(); i++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(i);
        if(newBatchDto != null)
        {
          String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          String dcmID = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
          String batchID = newBatchDto.getBatchModel().getBatchId();
          String batchDate = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
          String batchType = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
          String batchStatusType = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);

          String sheetId = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
          
          if (sheetId ==null)
          sheetId="";

          boolean sheetIsReImported = false;

          if(sheetsReimportedTable!=null && sheetsReimportedTable.containsKey(sheetId))
          {
          sheetIsReImported = true; 
          }

          out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"AuthCall\" method=\"post\">");
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                      " value=\""+AuthCallInterfaceKey.ACTION_SHOW_SHEET_DETAILS_FOR_AUTHENTICATION_CALL+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                      "\" value="+userID+">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                      "\" value=\""+batchID+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                      "\" value=\""+dcmID+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                      "\" value=\""+batchDate+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                      "\" value=\""+batchType+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                      "\" value=\""+batchStatusType+"\">");

          
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+"\" value=\""+sheetId+"\">");

          
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID+"\">");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center><font size=2>Batch ID</font></TD>");      
          out.println("<td nowrap align=center><font size=2>DCM Name</font></TD>");
          out.println("<td nowrap align=center><font size=2>Creation Date</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Type</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Status</font></TD></TR>");
          String dcmName = newBatchDto.getBatchModel().getBatchDCMName();;
          String sheetsBatchDate = newBatchDto.getBatchModel().getBatchDate();
          String sheetsBatchType = newBatchDto.getBatchModel().getBatchType();
          String batchStatus = newBatchDto.getBatchModel().getBatchStatus();
          out.println("<TR>");
          out.println("<td nowrap align=center>"+batchID+"</a></td>");
          out.println("<td nowrap>"+dcmName+"</td>");
          out.println("<td nowrap align=center>"+sheetsBatchDate+"</td>");
          out.println("<td nowrap>"+sheetsBatchType+"</td>");
          out.println("<td nowrap>"+batchStatus+"</td>");
          out.print("</tr>");
          out.println("</TABLE>");
          out.println("<br>");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0>");

          out.println("<TR><td colspan=2><input type=\"radio\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+
                      "\" value=\""+AuthCallInterfaceKey.VIEW_ALL_AUTH+"\" onclick=\"AuthCall.view.disabled=false;"+
                      "AuthCall."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".disabled=true;"+
                      "\">View All Authinticated</TD></TR>");

          out.println("<TR><td colspan=2><input type=\"radio\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+
                      "\" value=\""+AuthCallInterfaceKey.VIEW_ALL+"\" onclick=\"AuthCall.view.disabled=false;"+
                      "AuthCall."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".disabled=true;"+
                      "\">View All Contracts</TD></TR>");      

          out.println("<TR><td colspan=2><input type=\"radio\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+
                      "\" value=\""+AuthCallInterfaceKey.ADD_RANDOM_CONTRACTS+"\" onclick=\"AuthCall.view.disabled=false;"+
                      "AuthCall."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".disabled=true;"+
                      "\">Add Random Contracts</TD></TR>");

          out.println("<TR><td width=\"35%\"><input type=\"radio\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+
                      "\" value=\""+AuthCallInterfaceKey.ADD_CONTRACT+"\" onclick=\"AuthCall.view.disabled=false;"+
                      "AuthCall."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".disabled=false;"+
                      "\">Add Contract with this Main Sim NO</TD>");
          out.println("<td><input class=input type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+"\" size=\"21\" maxlength=\"20\" disabled></TD></TR>");

/*
          out.println("<TR><td width=\"35%\"><input type=\"radio\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+
                      "\" value=\""+AuthCallInterfaceKey.ADD_SHEET+"\" onclick=\"AuthCall.view.disabled=false;"+
                      "AuthCall."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".disabled=true;"+
                      "AuthCall."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+".disabled=false;\">Add Contracts of this Sheet Serial NO</TD>");

                      
          out.println("<td><input class=input type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+"\" disabled></TD></TR>");
*/


          out.println("<TR><td colspan=2 align=center><input class=button type=\"button\" name=\"view\" value=\"   View   \""+
                      " onclick=\"checkbeforSubmit();\" disabled></TD></TR>");      
          out.println("</TABLE>");
          out.println("<script>document.AuthCall."+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+".value = '"+dataHashMap.get(AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS)+"';</script>");
          out.println("<br>");
          
          HashMap contractDtos = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
          if(contractDtos != null)
          {
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            out.println("<TR class=TableHeader>");
            out.println("<td align=middle>Sheet Serial Number</td>");
            out.println("<td align=middle>Total Contracts</td>");
            out.println("<td align=middle>Total Accepted Authentication</td>");
            out.println("<td align=middle>Total Rejected Authentication</td>");
            out.println("<td align=middle>Required Authentication</td>");
            out.println("<td align=middle>Sheet Status</td>");
            out.println("</tr>");
                
            for(int j=0; j<contractDtos.size(); j++)
            {
              String sheetName = (String)contractDtos.keySet().toArray()[j];
              ContractDto newContractDto = (ContractDto)contractDtos.get(sheetName);
              SheetAuthinticationStatisticModel sheetStatistic  = (SheetAuthinticationStatisticModel) sheetsStatistic.get(sheetName);
              if (sheetStatistic==null)
              {
                sheetStatistic= new SheetAuthinticationStatisticModel();
              }
              String sheetSerialNum = sheetStatistic.getSheetSerial();
              int totalContracts = sheetStatistic.getTotalContractsEligableForAuthintication();
              
              int totalAccepted = sheetStatistic.getTotalContractsAuthinticated();

              if (sheetStatistic.getDiscardUnknownUnreachble() )
              {
              totalAccepted= totalAccepted - sheetStatistic.getTotalAcceptedPosUnknown() -sheetStatistic.getTotalAcceptedUnrechable();            
              }
              
              int totalRejected= sheetStatistic.getTotalContractsRejectedAuthinticated();

              if (sheetStatistic.getDiscardUnknownUnreachble())
              {
              totalRejected = totalRejected - sheetStatistic.getTotalRejectedPosUnknown() - sheetStatistic.getTotalRejectedUnreachable();
              }
              
              
              int neededToCommission = sheetStatistic.getTotalContractsNeededToCommission();

              out.println("<Tr>");
              out.println("<td>");               
                               
              out.println(sheetSerialNum);
              out.println("</td>");
              out.println("<td align=middle>");
              out.println(totalContracts);
              out.println("</td>");
              out.println("<td align=middle>");
              out.println(totalAccepted);
              out.println("</td>");
              out.println("<td align=middle>");
              out.println(totalRejected);
              out.println("</td>");              
              out.println("<td align=middle>");
              out.println(neededToCommission);
              out.println("</td>");
              out.println("<td align=middle>");
              out.println(sheetStatistic.getSheetStatus());
              out.println("</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td colspan=5>");
              
              out.println("<div style=\"display:block\" id="+sheetName + " name="+sheetName+">");
              out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1 name=\""+sheetName +"\">");
              out.println("<TR class=TableHeader>");
              out.println("<td nowrap align=middle>Remove</td>");
              out.println("<td nowrap align=middle>Sheet Serial Number</td>");
              out.println("<td nowrap align=middle>Contract Main Sim No</td>");
              out.println("<td nowrap align=left>Contract Type</td>");
              out.println("<td nowrap align=middle>Contract Status</td>");
              out.println("</tr>");
              for(int k=0; k<newContractDto.getContractModelsSize(); k++)
              {                                          
                ContractModel newContractModel = newContractDto.getContract(k);
                String sheetID = newContractModel.getSheetId();
                String contractID = newContractModel.getId();
                String contractMainSimNo = newContractModel.getMainSimNum();
                String contractType = newContractModel.getTypeName();
                String contractStatus = newContractModel.getStatusTypeName();
                String sheetSerialNo = newContractModel.getSheetSerialNo();
                boolean reimported = newContractModel.getReImportedFlag();
                if(reimported ||sheetIsReImported)
                {
                  out.println("<TR class=LockedRow id=\""+sheetName+k+"\">");
                }
                else
                {
                  out.println("<TR id=\""+sheetName+k+"\">");
                }

                if(contractStatus.equals("ACCEPTED VERIFY") &&(!newContractModel.isContractPrevRejectedInAuthentication()))
                {
                  out.println("<td nowrap align=center><input type=\"checkbox\" name=\"remove\" value=\"1\" onclick=removeRow("+sheetName+","+sheetName+k+");></td>");
                }
                else
                {
                  out.println("<td nowrap align = center> </td>");
                }
                out.println("<td nowrap align=center>"+sheetSerialNo+"</td>");
                if(reimported || sheetIsReImported)
                {
                  out.println("<td nowrap align=left>"+contractMainSimNo+"</td>");
                }
                else
                {
                  out.println("<td nowrap align=left><a href=\"javascript:"+
                  "document.AuthCall."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AuthCallInterfaceKey.ACTION_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL_BY_SHEET+"'; "+
                  "document.AuthCall."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID+".value="+contractID+"; "+
                  "AuthCall.submit();\">"+contractMainSimNo+"</a></td>");
                }
                out.println("<td nowrap align=left>"+contractType+"</td>");
                out.println("<td nowrap align=left align=center>"+contractStatus);
                out.println("<input type=\"hidden\" name=\""+AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT+"\" value=\""+sheetID+"_"+contractID+"\">");
                out.println("</td>");
                out.print("</tr>");
              }
              out.print("</TABLE>"); 
              out.print("</div>");  
              out.println("</td>");
              out.println("</tr>");
            }
            out.print("</TABLE>"); 
          }
          
          out.print("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
          out.print("<tr>");
          out.print("<td align=center>");
          out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
          out.println("onclick=\"document.AuthCall."+
                      InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                      AuthCallInterfaceKey.ACTION_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN_BY_SHEET+"';AuthCall.submit();\">");
          out.print("</td>");
          out.print("</tr>");
          out.print("</table></form>");
        }
      }
    }
  }
%>
