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
 * Admin_Choose_Contracts_For_Authentication_Call.jsp.
 * Displays Contratcs ready for authentication call. The page display
 * all contracts grouped by sheets.
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
  <body>
    <Center>
      <H2>Authentication Call Administration</H2>
    </Center>
        
    <left>
     <h3>Choose Contract for Authentication Call</h3>
    </left>
      <script type="text/javascript">
        function checkBeforSubmit()
        {
          var tables = document.getElementsByTagName("table");
          for(k=0; k<tables.length; k++)
          {
            if(tables[k].id.match('table_') == 'table_')
            {
              var rows = tables[k].rows;
              for(i=1; i<rows.length; i++)
              {
                var dataCells = rows[i].cells;
                var contractMainSimNumber = dataCells[1].innerText;
                var statusComboBox = dataCells[3].getElementsByTagName("select")[0];
                var warningComboBox = dataCells[4].getElementsByTagName("select")[0];
                if(statusComboBox != null)
                {
                  for (j=0;j<statusComboBox.length;j++) 
                  {
                    if(statusComboBox.options[j].selected == true && statusComboBox.options[j].text =='REJECTED AUTHINTICATION') 
                    {
                      if(warningComboBox != null && warningComboBox.selectedIndex == 0) 
                      {
                        alert('At least one warning must be selected for REJECTED AUTHINTICATION contract '+contractMainSimNumber+'.');
                        return false;
                      }
                    }
                  }
                }
              }
            }
          }
          return true;
        }
        function changeAll(combobox) 
        {
          comboboxs=document.getElementsByTagName("SELECT");
          for (i=0;i<comboboxs.length;i++) 
          {
            if(comboboxs[i].name.match(combobox.name) == combobox.name && ! comboboxs[i].disabled)
            {
              comboboxs[i].value = combobox.value;
            }
          }
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
 * used to display contracts grouped by sheets of the selected batchs.
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
          //ContractStatusTypeDto newContractStatusTypeDto = (ContractStatusTypeDto)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE);
          
//          Vector userWarning = (Vector)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_USER_WARNING);

 Hashtable batchAuthStat = (Hashtable)additionalCollection.get("batch_auth_statistics");
  Integer accVer= (Integer) batchAuthStat.get("ACCEPTED VERIFY");
    if (accVer ==null) accVer = new Integer(0);
    
    Integer accAuth= (Integer) batchAuthStat.get("ACCEPTED AUTHINTICATION");
    if (accAuth ==null) accAuth = new Integer(0);
    
    Integer rejAuth= (Integer) batchAuthStat.get("REJECTED AUTHINTICATION");
    if (rejAuth == null) rejAuth = new Integer(0);
    
    Integer percentage = (Integer)batchAuthStat.get("percentage");
    
    int total = accVer.intValue() + accAuth.intValue()+rejAuth.intValue();
    int neededAuth = Math.round(total * percentage.intValue()/100);
    
    
          out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"AuthCall\" method=\"post\">");
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                      " value=\""+AuthCallInterfaceKey.ACTION_ADMIN_SHOW_CONTRACT_DETAILS_FOR_AUTHENTICATION_CALL+"\">");

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

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+"\">");
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID+"\">");

          out.println("<input type=\"hidden\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+"\" value=\""+AuthCallInterfaceKey.VIEW_ALL_AUTH+"\">");

          
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
          
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center><font size=2>Accepted Verify</font></TD>");      
          out.println("<td nowrap align=center><font size=2>Accepted Authentication</font></TD>");
          out.println("<td nowrap align=center><font size=2>Rejected Authentication</font></TD>");
          out.println("<td nowrap align=center><font size=2>Required Authentication</font></TD>");
           out.println("<td nowrap align=center><font size=2>Remaining</font></TD>");
         
          out.println("</tr>");
             out.println("<TR>");
          out.println("<td nowrap>"+accVer+"</td>");
          out.println("<td nowrap>"+accAuth+"</td>");
          out.println("<td nowrap>"+rejAuth+"</td>");
          out.println("<td nowrap>"+neededAuth+"</td>");
          out.println("<td nowrap>"+(neededAuth-accAuth.intValue()-rejAuth.intValue())+"</td>");
             out.println("</TR>");
          
           out.println("</TABLE>");
           
            out.println("<br>");
          HashMap sheetStatistics = (HashMap)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_SHEET_AUTH_STATISTIC_MODEL);
          
          
          if(sheetStatistics != null)
          {
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            out.println("<TR class=TableHeader>");
            out.println("<td align=middle>Sheet Serial Number</td>");
            out.println("<td align=middle>Total Contracts</td>");
            out.println("<td align=middle>Total Accepted Authentication</td>");
            out.println("<td align=middle>Total Rejected Authentication</td>");
            out.println("<td align=middle>Required Authentication</td>");
            out.println("<td align=middle>Sheet Status</td>");
            out.println("<td align=middle>Authenticate</td>");
            out.println("</tr>");
                
            for(int j=0; j<sheetStatistics.size(); j++)
            {
              String sheetName = (String)sheetStatistics.keySet().toArray()[j];

        //      ContractDto newContractDto = (ContractDto)contractDtos.get(sheetName);
              SheetAuthinticationStatisticModel sheetStatistic  = (SheetAuthinticationStatisticModel) sheetsStatistic.get(sheetName);
              if (sheetStatistic==null)
              {
                sheetStatistic= new SheetAuthinticationStatisticModel();
              }
              String sheetID = sheetStatistic.getSheetId();
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
//              out.println("<A id=\"x"+sheetName +"\" href=\"javascript:Toggle(\'"+sheetName+"\');\">"+"<img src=\'"+appName+"/resources/img/plus.gif\'>"+"</A>");                               
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

              out.println("<td align=center><input class=button type=\"button\" name=\"Authenticate\" value=\"  Authenticate  \" ");

            out.println("onclick=\"document.AuthCall."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AuthCallInterfaceKey.ACTION_SHOW_SHEET_DETAILS_FOR_AUTHENTICATION_CALL+"'; "+
                  "document.AuthCall."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+".value="+sheetID+"; "+
                  "AuthCall.submit();\"></td>");
                        



                        
              out.println("</tr>");

            }
            out.print("</TABLE>"); 
          }
          
          out.print("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
          out.print("<tr>");

          out.print("<td align=center>");
/*
          out.println("<input class=button type=\"button\" name=\"update\" value=\"  Update  \" ");
          out.print("onclick=\"if(checkBeforSubmit()){document.AuthCall."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                    AuthCallInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS+"';AuthCall.submit();}\">");
*/

          out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
          out.println("onclick=\"document.AuthCall."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                      AuthCallInterfaceKey.ACTION_SEARCH_BATCH_AUTHENTICATION_CALL_BY_SHEET+"';AuthCall.submit();\">");
          out.print("</td>");
          out.print("</tr>");
          out.print("</table></form>");
        }
      }
    }
  }
%>
