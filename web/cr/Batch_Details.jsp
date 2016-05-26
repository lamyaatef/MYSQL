<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<%
/**
 * Batch_Details.jsp:
 * Display the batch data as a header followed by the sheets of this batch.
 * Sheets displayed are those of a batch selected from the search results 
 * from "Batch_Search.jsp".
 * A sheet status can be changed through this page.
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
<script type="text/javascript">

  function checkBeforSubmit(tableName)
  {
    var table = document.getElementById(tableName);
    var rows = table.rows;
    for(i=1; i<rows.length; i++)
    {
      var dataCells = rows[i].cells;
      var sheetSerialNumber = dataCells[0].innerText;
      var statusComboBox = dataCells[4].getElementsByTagName("select")[0];
      var warningComboBox = dataCells[5].getElementsByTagName("select")[0];
      if(warningComboBox != null)
      {
        for (j=0;j<warningComboBox.length;j++) 
        {
          if(warningComboBox.options[j].selected == true && warningComboBox.options[j].text !='') 
          {
            if(statusComboBox != null && statusComboBox.selectedIndex <= 0) 
            {
              alert('A new status must be selected if a warning is selected for sheet '+sheetSerialNumber+'.');
              statusComboBox.focus();
              return false;
            }
          }
        }
      }
    }
    for(i=1; i<rows.length; i++)
    {
      var dataCells = rows[i].cells;
      var sheetSerialNumber = dataCells[0].innerText;
      var statusComboBox = dataCells[4].getElementsByTagName("select")[0];
      var warningComboBox = dataCells[5].getElementsByTagName("select")[0];
      if (statusComboBox != null) {
        for (j=0;j<statusComboBox.length;j++) 
        {
          if(statusComboBox.options[j].selected == true && statusComboBox.options[j].text =='REJECTED DELIVERY') 
          {
            if(warningComboBox.selectedIndex <= 0) 
            {
              alert('At least one warning must be selected for REJECTED DELIVERY sheet '+sheetSerialNumber+'.');
              return false;
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
<%    String appName = request.getContextPath();%>
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
      <H2>Delivery Verification</H2>
    </Center>
    <left>
     <h3>Batch Details</h3>
    </left>
      <%
      showSheets(request, response, out);
      %>
    </form>
  </body>
</html>

<%!
/**
 * showSheets method:
 * Display the sheets of the selected batch.
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
    Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    Hashtable additionalCollection = (Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    SheetStatusTypeDto newSheetStatusTypeDto = (SheetStatusTypeDto)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE);
    Vector userWarning = (Vector)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_USER_WARNING);
    if(dataVector != null && dataVector.size()!=0)
    {
      for(int i = 0; i<dataVector.size(); i++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(i);
        if(newBatchDto != null)
        {
          String batchID = newBatchDto.getBatchModel().getBatchId();

          String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

          out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchDetails\" method=\"post\">");

          String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE+"\">");
          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                      " value=\""+ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_STATUS+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                      "\" value="+userID+">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                      "\" value=\""+additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID)+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                      "\" value=\""+additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE)+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                      "\" value=\""+additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE)+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                      "\" value=\""+additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE)+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                      "\" value=\""+batchID+"\">");
                      
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center><font size=2>Batch ID</font></TD>");      
          out.println("<td nowrap align=center><font size=2>DCM Name</font></TD>");
          out.println("<td nowrap align=center><font size=2>Creation Date</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Type</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Status</font></TD></TR>");

          String dcmName = newBatchDto.getBatchModel().getBatchDCMName();;
          String date = newBatchDto.getBatchModel().getBatchDate();
          String batchType = newBatchDto.getBatchModel().getBatchType();
          String batchStatus = newBatchDto.getBatchModel().getBatchStatus();
          out.println("<TR>");
          out.println("<td nowrap align=center>"+batchID+"</a></td>");
          out.println("<td nowrap>"+dcmName+"</td>");
          out.println("<td nowrap align=center>"+date+"</td>");
          out.println("<td nowrap>"+batchType+"</td>");
          out.println("<td nowrap>"+batchStatus+"</td>");
          out.print("</tr>");
          out.println("<TR>");
          out.println("<TD colspan=6>");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1 id=\""+batchID+"\">");
          out.println("<TR class=TableHeader>");
          out.println("<td align=center width=\"10%\">Sheet Serial Number</td>");
          out.println("<td align=center width=\"10%\"><font size=2>POS Code</font></TD>");
          out.println("<td align=center width=\"10%\"><font size=2>Super Dealer Code</font></TD>");
          out.println("<td align=center width=\"12%\">Contract Type</td>");
          out.println("<td align=center width=\"5%\">Count</td>");
          out.println("<td align=center width=\"20%\">");
          out.println("Sheet Status<br>"); 
              
          out.println("<Select name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+"  onchange=\"changeAll(this);\">");
          out.println("<option>Apply status to all sheets</option>");
          for(int k= 0; k<newSheetStatusTypeDto.getSheetModelsSize(); k++)
          {
            int sheetStatusTypeID = newSheetStatusTypeDto.getSheetStatusTypeModel(k).getId();
            String sheetStatusTypeName = newSheetStatusTypeDto.getSheetStatusTypeModel(k).getName();
            if (sheetStatusTypeName.compareTo("REJECTED IMPORT")!=0)
            {
              out.println("<option value=\""+sheetStatusTypeID+"\">"+sheetStatusTypeName+"</option>");
            }
          }
          out.println("</Select>");

          out.println("</td>");
          out.println("<td nowrap align=center>Warnings<br>"); 
          out.println("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"_\"  onchange=\"changeAll(this);\">");
          out.println("<option>Add a warning to all sheets</option>");
          for(int m=0; m<userWarning.size(); m++)
          {
            SheetWarningModel userWarningModel = (SheetWarningModel)userWarning.elementAt(m);
            String warningID = userWarningModel.getSheetWarningID();
            String warningName = userWarningModel.getSheetWarningName();
            out.println("<option value="+warningID+">"+warningName+"</option>");
          }
        
          out.println("</select>");
          out.println("</td></tr>");
          for(int j=0; j<newBatchDto.getSheetModelsSize(); j++)
          {
            SheetModel newSheetModel = newBatchDto.getSheetAt(j);
            String sheetID = newSheetModel.getSheetId();
            String sheetName = newSheetModel.getSheetName();
            String sheetStatusName = newSheetModel.getSheetStatusName();
            String sheetPOSCode = newSheetModel.getSheetPOSCode();
            String sheetSuperDealerCode = newSheetModel.getSheetSuperDealerCode();
            SheetDto sheetDto = newBatchDto.getSheetDto();

            
            Vector sheetWarningsVector = sheetDto.getSheetWarning(sheetID);
            int contractCount = newSheetModel.getContractsCount();
            Enumeration contractsCountTypes = newSheetModel.getContractsTypesCount().keys();
            Enumeration contractsCountTypesCounter = newSheetModel.getContractsTypesCount().elements();

            

            
            if (newSheetModel.getSheetStatusName().compareTo(newSheetModel.STATUS_REJECTED_IMPORT)==0)
            {
             out.println("<TR>");
             out.println("<td nowrap align=center>");
             out.println(sheetName +"</td>");
            }
            else if (newSheetModel.isSheetReImportFlag())
            {
            out.println("<TR class=LockedRow>");
            out.println("<td nowrap align=center>");
            out.println(sheetName +"</td>");
            
            }
            
            else
            {
            out.println("<TR>");
            out.println("<td nowrap align=center>");
            out.println("<a href=\"javascript:"+
            "document.BatchDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_SHOW_SHEET_DETAILS+"';"+
            "document.BatchDetails."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+".value="+sheetID+";"+
            "BatchDetails.submit();\">"+sheetName+"</a></td>");
            }
            
            out.println("<td nowrap align=center>"+sheetPOSCode+"</td>");
            out.println("<td nowrap align=center>"+sheetSuperDealerCode+"</td>");
            out.println("<td nowrap align=left colspan=2>");
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=1 cellPadding=1 width=\"100%\" border=0>");
            while (contractsCountTypes.hasMoreElements())
            {          
              out.println("<tr><td width=\"65%\">"+(String)contractsCountTypes.nextElement()+"</td>");
              out.println("<td align=center>"+(String)contractsCountTypesCounter.nextElement()+"</td></tr>");
              out.println("<tr><td colspan =2 bgColor=#f1f1ed></td></tr>");
            }
            out.println("<tr><td bgColor=#f1f1ed><font style=\"FONT-WEIGHT: bold\">DEF Total</font></td>");
            out.println("<td bgColor=#f1f1ed align=center><font style=\"FONT-WEIGHT: bold\">"+contractCount+"</font></td></tr>");
            out.println("</Table>");
            out.println("</td>");
            out.println("<td nowrap align=center>");
            out.println(newSheetModel.getSheetStatusName()+"<br>");
            
            if(sheetStatusName.compareTo(SheetModel.STATUS_REJECTED_IMPORT)!=0  && !newSheetModel.isSheetReImportFlag())
            {
              out.println("<Select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+sheetID+"\">");
              out.println("<option></option>");
              for(int k= 0; k<newSheetStatusTypeDto.getSheetModelsSize(); k++)
              {
                int sheetStatusTypeID = newSheetStatusTypeDto.getSheetStatusTypeModel(k).getId();
                String sheetStatusTypeName = newSheetStatusTypeDto.getSheetStatusTypeModel(k).getName();
              /*
              if (newSheetModel.getSheetStatusId()==sheetStatusTypeID)
                {
                  out.println("<option value=\""+sheetStatusTypeID+"\"selected>"+sheetStatusTypeName+"</option>");
                }
                else
                {
                */
                  out.println("<option value=\""+sheetStatusTypeID+"\">"+sheetStatusTypeName+"</option>");
               // }
              }
              out.println("</Select>");
            }

            out.println("</td>");
            out.println("<td align=center>");
            if(sheetWarningsVector !=null)
            {
              String sheetWarnings = "";
              for (int l=0; l<sheetWarningsVector.size(); l++)
              {          
                sheetWarnings+=((SheetWarningModel)sheetWarningsVector.elementAt(l)).getSheetWarningName()+"<br>";
              }
              out.println(sheetWarnings);
            }

              if(sheetStatusName.compareTo(SheetModel.STATUS_REJECTED_IMPORT)!=0 && !newSheetModel.isSheetReImportFlag())
            {
          
              out.println("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"_"+sheetID+"\">");
              out.println("<option></option>");
              for(int m=0; m<userWarning.size(); m++)
              {
                SheetWarningModel userWarningModel = (SheetWarningModel)userWarning.elementAt(m);
                String warningID = userWarningModel.getSheetWarningID();
                String warningName = userWarningModel.getSheetWarningName();
                out.println("<option value="+warningID+">"+warningName+"</option>");
              }
              out.println("</select>");
            }
        
             

            out.println("</td></tr>");
          }
          out.println("</TABLE>");      
          out.print("</td>");
          out.print("</tr>");
          out.println("</TABLE>");
          out.println("<br>");
          out.println("<br>");
          out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
          out.println("<tr>");
          out.println("<td align=center>");
          out.println("<input class=button type=\"button\" name=\"print\" value=\"   Print   \" ");
          out.println("onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                      InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS+"&"+
                      ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+"=,"+batchID+"');return false;\">");
          out.println("<input class=button type=\"button\" name=\"apply\" value=\"  Apply  \" ");
          out.println("onclick=\"if(checkBeforSubmit('"+batchID+"')){document.BatchDetails."+
                      InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_APPLY_SHEET_STATUS+"';"+
                      "BatchDetails.submit();}\">");
          out.println("<input class=button type=\"button\" name=\"update\" value=\"  Update  \" ");
          out.println("onclick=\"if(checkBeforSubmit('"+batchID+"')){document.BatchDetails."+
                      InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_STATUS+"';"+
                      "BatchDetails.submit();}\">");
          out.println("<input class=button type=\"button\" name=\"cancel\" value=\"  Cancel  \" ");
          out.println("onclick=\"document.BatchDetails."+
                      InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_SEARCH_BATCH_DELIVERY+"';"+
                      "BatchDetails.submit();\">");
          out.println("</td>");
          out.println("</tr>");
          out.println("</table>");

        }
              
      }

    }
    
  }
%>