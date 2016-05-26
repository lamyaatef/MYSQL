<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
%>
<%
/**

 * Contract_verification_Batch_Search.jsp:
 * search for batchs according to DCM ID 
 * and one or non of the following (Batch Date - Batch Type - Batch Status Type).
 * If Batchs are found they are displayed.
 * You can close one batch for varification.
 * You can print one batch by pressing the print button beside this batch.
 * Or you may choose to print all batchs by pressing the print all button.
 * 
 * showBatchs(HttpServletRequest , HttpServletResponse , JspWriter): 
 * Display the search page header and the results after choosing 
 * the seach criteria and pressing the search button
 *
 * printDCM function is used to fill DCM ID combobox.
 *
 * printBatchType function is used to fill Batch Type combobox.
 *
 * printBatchStatusType function is used to fill Batch Status Type combobox.
 * 
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
    if(NonBlank(document.DCMSearch.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);%>, true, 'DCM ID'))
    {
      DCMSearch.submit();
    }
    return false;
  }
</SCRIPT>
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
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
      <%
      showBatchs(request, response, out);
      %>
  </body>
</html>

<%!
  /**
   * printDCM method:
   * fill DCM ID combobox.
   *
   * @param	HttpServletRequest request, JspWriter out, DCMDto dcmDto
   * @return  
   * @throws  IOException
   * @see    
   * 
  */

public void printDCM(javax.servlet.http.HttpServletRequest request, javax.servlet.jsp.JspWriter out, DCMDto dcmDto ) 
throws java.io.IOException
{
  out.println("<option value=\"\"></option>");
  if (dcmDto !=null)
  {
    for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
    {
      DCMModel model = dcmDto.getDcm(index);       
      out.println("<option value=\""+model.getDcmId()+"\"");
      out.print(">"+model.getDcmName()+"</option>");
    }
  }
  else
  {
  }
}

  /**
   * printBatchType method:
   * fill Batch Type combobox.
   *
   * @param	HttpServletRequest request, JspWriter out, BatchTypeDto batchTypeDto
   * @return  
   * @throws  IOException
   * @see    
   * 
  */

public void printBatchType(javax.servlet.http.HttpServletRequest request, javax.servlet.jsp.JspWriter out, BatchTypeDto batchTypeDto ) 
throws java.io.IOException
{
  out.println("<option value=\"\"></option>");
  if (batchTypeDto !=null)
  {
    for (int index = 0 ; index<batchTypeDto.getBatchModelsSize();index++)
    {
      BatchTypeModel model = batchTypeDto.getBatchTypeModel(index);       
      out.println("<option value=\""+model.getId()+"\"");
      out.print(">"+model.getName()+"</option>");
    }
  }
  else
  {  
  }
}

  /**
   * printBatchStatusType method:
   * fill Batch Status Type combobox.
   *
   * @param	HttpServletRequest request, JspWriter out, BatchStatusTypeDto batchStatusTypeDto
   * @return  
   * @throws  IOException
   * @see    
   * 
  */
  
public void printBatchStatusType(javax.servlet.http.HttpServletRequest request, javax.servlet.jsp.JspWriter out, BatchStatusTypeDto batchStatusTypeDto ) 
throws java.io.IOException
{
  out.println("<option value=\"\"></option>");
  if (batchStatusTypeDto !=null)
  {
    for (int index = 0 ; index<batchStatusTypeDto.getBatchModelsSize();index++)
    {
      BatchStatusTypeModel model = batchStatusTypeDto.getBatchStatusTypeModel(index);       
      out.println("<option value=\""+model.getId()+"\"");
      out.print(">"+model.getName()+"</option>");
    }
  }
  else
  {  
  }
}

  /**
   * showBatchs method:
   * Display the search page header and the results after choosing 
   * the seach criteria and pressing the search button.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException if the jsp failed
   * @see    
   * 
  */

  public void showBatchs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap ;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    if(dataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
    {
      String strJobId = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);

      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchPrint\" method=\"post\">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_JOB_ID+"\""+
                    " value=\""+strJobId+"\">"); 
                    
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+ContractRegistrationInterfaceKey.ACTION_ADMIN_OPEN_ARCHIVING_PROCESS+"\">");

      out.println("</form>"); 
                    
      out.println("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"300\" height=\"100\">");
             out.println("<param name=\"movie\" value=\"../resources/img/loading.swf\">");
             out.println("<param name=\"quality\" value=\"high\">");
             out.println("<embed src=\"../resources/img/loading.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\"300\" height=\"100\"></embed>");
       out.println("</object>");
      out.println("<script>");
      out.println("setTimeout(\"BatchPrint.submit();\", 60000);");
      out.println("</script>");

      
    }
    else
    {

    out.println("<Center>");
      out.println("<H2>Contract Verification Administration</H2>");
    out.println("</Center>");
        
    out.println("<left>");
     out.println("<h3>Batch Search</h3>");
    out.println("</left>");
    
    Hashtable additonalCollection =(Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    Object []keys =dataHashMap.keySet().toArray();
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    DCMDto dcmDto= (DCMDto) additonalCollection.get(ContractRegistrationInterfaceKey.OBJ_DCM_DTO);
    BatchTypeDto batchTypeDto= (BatchTypeDto)additonalCollection.get(ContractRegistrationInterfaceKey.OBJ_BATCH_TYPE_DTO);
    BatchStatusTypeDto batchStatusTypeDto= (BatchStatusTypeDto) additonalCollection.get(ContractRegistrationInterfaceKey.OBJ_BATCH_STATUS_TYPE_DTO);   
    out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"DCMSearch\" method=\"post\">");

    String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE+"\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+ContractRegistrationInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION+"\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("<TR class=TableHeader>");
    out.println("<td nowrap align=center>");
    out.println("DCM ID");
    out.println("</TD>");
    out.println("<td nowrap align=center>");
    out.println("Creation Date");
    out.println("</td>");
    out.println("<td nowrap align=center>");
    out.println("Batch Type");
    out.println("</TD>");
    out.println("<td nowrap align=center>");
    out.println("Batch Status Type");
    out.println("</TR>");
    out.println("<TR class=TableHeader>");
    out.println("<td nowrap align=center><select  name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+"\" size=\"1\">");
    printDCM(request,out,dcmDto);
    out.println("</select></TD>");
    out.println("<td nowrap align=center>");
    out.println("<input class=\"input\"  name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+"\" Readonly>");
    out.println("<A onclick=\"showCalendar(DCMSearch."+ ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+",'dd/mm/yyyy','Choose date')\" href=\"javascript:void(0)\" >");
    out.print("<img border=\"0\" src=\""+appName+"/resources/img/icon_calendar.gif\" width=\"20\" height=\"16\">");
    out.println("</a>");
    out.println("</td>");
    out.println("<td nowrap align=center><select  name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+"\" size=\"1\">");
    printBatchType(request,out,batchTypeDto);
    out.println("</select></TD>");
    out.println("<td nowrap align=center><select  name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+"\" size=\"1\">");
    printBatchStatusType(request,out, batchStatusTypeDto);
    out.println("</select></TD>");
    out.println("</TR>");
    out.println("</TABLE>");
    out.println("<br>");
    out.println("<center>");
    out.println("<input class=button type=\"button\" name=\"search\" value=\"  Search  \" onclick=\"checkbeforSubmit();\">");
    out.println("</center>");
    out.println("</form>");
    Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    String searchDcmID = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
    String searchbatchDate = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
    String searchbatchType = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
    String searchbatchStatusType = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);

    out.println("<script>");
    out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+".value="
                +searchDcmID+";");
    if(searchbatchDate != null && (! searchbatchDate.equals("")))
    {
      out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+".value=\""+searchbatchDate+"\";");
    }
    if(searchbatchType != null && (! searchbatchType.equals("")))
    {
      out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+".value=\""+searchbatchType+"\";");
    }
    if(searchbatchStatusType != null && (! searchbatchStatusType.equals("")))
    {
      out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+".value=\""+searchbatchStatusType+"\";");
    }
    out.println("</script>");

    if(dataVector != null && dataVector.size()!=0)
    {
      String dcmID = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
      String batchDate = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
      String batchType = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
      String batchStatusType = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);

      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchPrint\" method=\"post\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"print\">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                  "\" value="+userID+">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                  "\" value=\""+dcmID+"\">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                  "\" value=\""+batchDate+"\">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                  "\" value=\""+batchType+"\">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                  "\" value=\""+batchStatusType+"\">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                  "\" value=\"\">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+
                  "\" value=\"\">");
                  
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=middle>Batch ID</td>");
      out.println("<td nowrap align=middle>Distributer Name</td>");
      out.println("<td nowrap align=middle>Batch Type</td>");
      out.println("<td nowrap align=middle>Batch Status</td>");
      out.println("<td nowrap align=middle>Print</td>");
      out.println("<td nowrap align=middle>Authenticate</td>");
      if (channelId.equals("1")){
      out.println("<td nowrap align=middle>Archiving</td></tr>");}
      for(int i = 0; i<dataVector.size(); i++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(i);
        String batchID = newBatchDto.getBatchModel().getBatchId();
        String archivingFlag = newBatchDto.getBatchModel().getArchivingFlag();
        String batchDCM = newBatchDto.getBatchModel().getBatchDCMName();
        batchType = newBatchDto.getBatchModel().getBatchType();
        String batchStatus = newBatchDto.getBatchModel().getBatchStatus();
        out.println("<TR>");
        out.println("<td align=center nowrap><a href=\"javascript:"+
        "document.BatchPrint."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION+"';"+
        "document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+".value="+batchID+";"+
        "document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+".value="+archivingFlag+";"+
        "BatchPrint.submit();\">");
        out.println(batchID+"</a></td>");
        out.println("<td nowrap>"+batchDCM+"</td>");
        out.println("<td nowrap>"+batchType+"</td>");
        out.println("<td nowrap>"+batchStatus+"</td>");
        out.println("<td nowrap align=center>");
        out.println("<input class=button type=\"button\" name=\"print\" value=\"   Print   \" ");
        out.print("onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                  InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS+"&"+
                  ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+"=,"+batchID+"&"+
                  ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+"=,"+archivingFlag+"');\"></td>");
	
        out.println("<td nowrap align=center>");  
        if(batchStatus.compareTo(com.mobinil.sds.core.system.cr.batch.model.BatchModel.STATUS_OPEN_AUTHINTICATION)==0)// && (archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_NOT_ENTERED_ARCHIVING_PROCESS)==0 || archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_FINISHED_ARCHIVING_PROCESS)==0))
        {
          out.println("<input class=button type=\"button\" name=\"closeForVerification\" value=\"Authinticate\""+
          " onclick=\"document.BatchPrint."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_ADMIN_AUTHINTICATE_BATCH_CONTRACT_VERIFICATION+"';"+
          " document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+".value="+batchID+";"+
          "document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+".value="+archivingFlag+";"+
          " BatchPrint.submit();\">");
        }
        out.println("</td>");
        if (channelId.equals("1"))
        {
        out.println("<td nowrap align=center>");
        if(archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_NOT_ENTERED_ARCHIVING_PROCESS)==0)
        {
        out.println("<input class=button type=\"button\" name=\"openArchivingProcess\" value=\"Open Archiving\""+
        " onclick=\"document.BatchPrint."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_ADMIN_OPEN_ARCHIVING_PROCESS+"';"+
        " document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+".value="+batchID+";"+
        "document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+".value="+archivingFlag+";"+
        " BatchPrint.submit();\">");
        }
        else if(archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_ENTERED_ARCHIVING_PROCESS)==0)
        {
          out.println("Opened For Archiving");  
        }
        else if(archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_FINISHED_ARCHIVING_PROCESS)==0)
        {
          out.println("Finished Archiving");  
        }
        out.println("</td>");
        }
        out.print("</tr>");
      }
      out.println("</TABLE>");
      out.println("</Form>");

      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"PrintAll\" method=\"post\" target= >");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"print_all\">");
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      String batchID="";
      String archivingFlag="";
      for(int j = 0; j<dataVector.size(); j++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(j);
        batchID +=",";
        batchID += newBatchDto.getBatchModel().getBatchId();
        archivingFlag += ",";
        archivingFlag +=newBatchDto.getBatchModel().getArchivingFlag();
      }
      out.println("<input class=button type=\"button\" name=\"print_all\" value=\" Print All \" ");
      out.print("onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS+"&"+
                ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+"="+batchID+"&"+
                ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+"="+archivingFlag+"');\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table>");
      out.println("</Form>");
    }
    else 
    {
      if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION) != null)
      {
        if(((String)request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION)).compareTo(ContractRegistrationInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION)==0)
        {
          out.println("<h4>No results available.</h4>");
        }
      }
    }

    }
  }
%>