<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dao.*"
         import="com.mobinil.sds.core.system.cr.sheet.dao.*"
%>
<%
/**
 * Sheet_Contracts_Verification.jsp:
 * Display the sheet data as a header followed by the contracts of this sheet.
 * 
 * showSheets(HttpServletRequest , HttpServletResponse , JspWriter): 
 * used to display the sheets of the selected batchs.
 *
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    if(NonBlank(document.ContractHistory.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);%>, true, 'Contract Main SIM Number'))
    {
      document.ContractHistory.submit();
    }
    return false;
  }
</SCRIPT>
<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
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
      <H2>Contract History</H2>
        <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ContractHistory" method="post">

          <%
          String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
          %>

          <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
          value="<%out.print(ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACT_HISTORY);%>">

          <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="<%out.print(userID);%>">

          <table  cellspacing="2" cellpadding="1" border="0" width="100%">
          <tr>
            <td align=right>Contract Main SIM Number :</td>
            <td align=left><input type="text" name="<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);%>" size="24" maxlength="24"></td>
          </tr>
          <tr>
            <td colspan=2 align=center>
              <input class=button type="button" name="ok" value="Show History" 
              onclick="checkbeforSubmit();">
              <input class=button type="Reset" name="reset" value="Reset">
            </td>
          </tr>
        </table>
        
      </form>
    </Center>
  </body>
</html>

<%
 
    
      /* HashMap vecHashMap = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
        
        if(vecHashMap != null)
        {out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          if(vecHashMap.size() != 0)
          {
            //out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            out.println("<tr><td td colspan=5>");
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            String tempBatchID = null;
            Set set = vecHashMap.entrySet();
           Iterator i = set.iterator();
           while(i.hasNext())
          {
            Map.Entry me = (Map.Entry)i.next();
            System.out.println("sheet serial number: "+me.getKey());
            Vector sheetHistoryVector = (Vector)me.getValue();
            for(int k=0; k<sheetHistoryVector.size(); k++)
            { 
              SheetDto newSheetDto = (SheetDto)sheetHistoryVector.elementAt(k);
              SheetModel newSheetModel = newSheetDto.getSheet(0);
              String sheetID = newSheetModel.getSheetId();
              String sheetSerialNo = newSheetModel.getSheetName();
              out.println("<script>SheetHistory."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+".value=\""+sheetSerialNo+"\"</script>");
              String sheetType = newSheetModel.getSheetTypeName();           
              String sheetDCMCode = newSheetModel.getSheetDCMCode();
              String sheetDCMName = newSheetModel.getSheetDCMName();
              String sheetSuperDealerCode = newSheetModel.getSheetSuperDealerCode();
              String sheetSuperDealerName = newSheetModel.getSheetSuperDealerName();
              String sheetPOSCode = newSheetModel.getSheetPOSCode();
              String sheetPOSName = newSheetModel.getSheetPOSName();
              String sheetBatchID = newSheetModel.getSheetBatchID();
              if(sheetBatchID ==null)
              {
                sheetBatchID = "";
              }
              //if (tempBatchID!=null && (tempBatchID.compareTo(sheetBatchID)!=0))
              //{
                //out.println("</TABLE></td></tr>");
              //}
              if (tempBatchID==null || (tempBatchID.compareTo(sheetBatchID)!=0))
              {
                out.println("<TR class=TableHeader>");
                out.println("<td nowrap align=center>Sheet Serial Number</TD>");
                out.println("<td nowrap align=center>Sheet Recieve Date</TD>");
                out.println("<td nowrap align=center>Sheet Type</TD>");
                out.println("<td nowrap align=center>DCM Code</TD>");
                out.println("<td nowrap align=center>DCM Name</TD>");
                
                out.println("</tr>");
                             
               
                out.println("<TR>");
                out.println("<td nowrap align=center>"+sheetSerialNo+"</TD>");
                out.println("<td nowrap align=center>"+newSheetModel.getSheetStatusDate()+"</TD>");
                out.println("<td nowrap>"+sheetType+"</TD>");
                out.println("<td nowrap align=center>"+sheetDCMCode+"</TD>");
                out.println("<td nowrap align=center>"+sheetDCMName+"</TD>");
                
                
                
                out.println("</tr>");
                 out.println("<TR class=TableHeader>");
                 out.println("<td nowrap align=center>Super Dealer Code</TD>");
                out.println("<td nowrap align=center>Super Dealer Name</TD>");
                out.println("<td nowrap align=center>POS Code</TD>");
                out.println("<td nowrap align=center>POS Name</TD>");
                out.println("<td nowrap align=center>Batch ID</TD>");  
                out.println("</tr>");
                             
               
                out.println("<TR>");
                 out.println("<td nowrap align=center>"+sheetSuperDealerCode+"</TD>");
                 out.println("<td nowrap align=center>"+sheetSuperDealerName+"</TD>");
                out.println("<td nowrap align=center>"+sheetPOSCode+"</TD>");
                out.println("<td nowrap align=center>"+sheetPOSName+"</TD>");
                out.println("<td nowrap align=center>"+sheetBatchID+"</TD>");
                 out.println("</tr>");
                
              }
              
            
              
              tempBatchID = sheetBatchID;
            }
            out.println("</TABLE></td></tr>");
          }
          out.println("</TABLE>");
                  
          }
          else
          {
          out.println("<h4 align=left>No results available.</h4>");
          }
        }*/
      
     
    
  
%>
<br>

<%
/**
 * showContracts method:
 * Display the contracts of the verified sheet.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  
    
    
      
        Vector contractsVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        if(contractsVector != null)
        {
          if(contractsVector.size() != 0)
          {
            
            String tempSheetID = null;
            for(int i=0; i<contractsVector.size(); i++)
            { out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
              ContractHistoryModel newContractHistoryModel = (ContractHistoryModel)contractsVector.elementAt(i);
              String contractID = newContractHistoryModel.getId();
              String contractSheetID = newContractHistoryModel.getSheetId();
              String contractMainSimNum = newContractHistoryModel.getMainSimNum();
              String contractSheetSerial = newContractHistoryModel.getSheetSerialNo();
              Vector<SheetDto> sheetHistoryVector = SheetDao.getSheetHistory(contractSheetSerial);
              for(int k=0; k<sheetHistoryVector.size(); k++)
              {  
              SheetDto newSheetDto = (SheetDto)sheetHistoryVector.elementAt(k);
              SheetModel newSheetModel = newSheetDto.getSheet(0);
              String sheetDCMCode = newSheetModel.getSheetDCMCode();
              String sheetDCMName = newSheetModel.getSheetDCMName();
              String sheetSuperDealerCode = newSheetModel.getSheetSuperDealerCode();
              String sheetSuperDealerName = newSheetModel.getSheetSuperDealerName();
              String sheetPOSCode = newSheetModel.getSheetPOSCode();              
              String sheetPOSName = newSheetModel.getSheetPOSName();
              String sheetBatchID = newSheetModel.getSheetBatchID();
             
              
              if(contractSheetID ==null)
              {
                contractSheetID = "";
              }
              //if(tempSheetID!=null && (tempSheetID.compareTo(contractSheetID)!=0))
              //{
                //out.println("</TABLE></td></tr>");
              //}
//              if(tempSheetID==null || (tempSheetID.compareTo(contractSheetID)!=0))
//              {
                out.println("<TR class=TableHeader>");
                out.println("<td nowrap align=center>Batch ID</TD>");
                out.println("<td nowrap align=center>Sheet Serial</TD>");
                out.println("<td nowrap align=center>Import Date</TD>");
                out.println("<td nowrap align=center>DCM Code</TD>");
                out.println("<td nowrap align=center>DCM Name</TD>");
               
                out.println("</tr>");
                out.println("<TR>");
                out.println("<td nowrap align=center>"+sheetBatchID+"</TD>");
                out.println("<td nowrap align=center>"+contractSheetSerial+"</TD>");
                out.println("<td nowrap align=center>"+newContractHistoryModel.getContractStatusDate()+"</TD>");
                 out.println("<td nowrap align=center>"+sheetDCMCode+"</TD>");
                  out.println("<td nowrap align=center>"+sheetDCMName+"</TD>");
                  
                 out.println("</tr>");
                 out.println("<TR class=TableHeader>");
                out.println("<td nowrap align=center>POS Code</TD>");
                out.println("<td nowrap align=center  colspan=2>POS Name</TD>");
                out.println("<td nowrap align=center >Super Dealer Code</TD>");
                out.println("<td nowrap align=center >Super Dealer Name</TD>");
                out.println("</tr>");
                out.println("<TR>");
                out.println("<td nowrap align=center>"+sheetPOSCode+"</TD>");
                out.println("<td nowrap align=center colspan=2>"+sheetPOSName+"</TD>");
                out.println("<td nowrap align=center >"+sheetSuperDealerCode+"</TD>");
                 out.println("<td nowrap align=center >"+sheetSuperDealerName+"</TD>");
                 
                 out.println("</tr>");
                 out.println("<tr><td colspan=5>");
                out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
                out.println("<TR class=TableHeader>");
                out.println("<td nowrap align=center >Contract Main SIM Number</TD>"); 
                out.println("<td width=\"20%\" nowrap align=middle>Contract Status</td>");
                out.println("<td width=\"20%\" nowrap align=middle>Status Date</td>");
                out.println("<td width=\"20%\" nowrap align=middle>User</td>");
                out.println("<td width=\"20%\" nowrap align=middle>Warnings</td></tr>");
                out.println("</tr>");
                out.println("<script>ContractHistory."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".value=\""+contractMainSimNum+"\"</script>");
                out.println("<TR>");
                out.println("<td align=center>"+contractMainSimNum+"</TD>");    
                out.println("<td align=center>"+newContractHistoryModel.getContractStatusName()+"</td>");
              out.println("<td align=center>"+newContractHistoryModel.getContractStatusDate()+"</td>");
              String contractUserName = newContractHistoryModel.getUserName();
              if(contractUserName==null)
              contractUserName = "";
              out.println("<td align=center>"+contractUserName+"</td>");
              out.println("<td align=center>");
            
              String contractWarningName = newContractHistoryModel.getContractWarningName();
              if(contractWarningName != null)
              {
                out.print(contractWarningName);
              }
                out.println("</tr>");
                //out.println("<tr><td colspan=4>");
                //out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
                
  //             }
              
             
             
             
              
              
              out.println("</td></tr>");
              out.println("</TABLE></td></tr>");
              tempSheetID = contractSheetID;
            }
            
            out.println("</TABLE>");
            out.println("<br>");
            }  
                  
          }
          else
          {
          
            out.println("<h4 align=left>No results available.</h4>");
          }
        }
      
      
    
  
%>
