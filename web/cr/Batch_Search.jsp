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
 * Batch_Search.jsp:
 * search for batchs according to DCM ID 
 * and one or non of the following (Batch Date - Batch Type - Batch Status Type).
 * If Batchs are found they are displayed.
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
    <Center>
      <H2>Delivery Verification</H2>
    </Center>
        
    <left>
     <h3>Batch Search</h3>
    </left>
    
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
    Hashtable additonalCollection =(Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);    
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

    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE+"\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+ContractRegistrationInterfaceKey.ACTION_SEARCH_BATCH_DELIVERY+"\">");
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
    out.println("Batch Status Type</td>");
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
    
    String searchDcmID = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
    String searchbatchDate = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
    String searchbatchType = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
    String searchbatchStatusType = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);

    out.println("<script>");
    if(searchDcmID != null && (! searchDcmID.equals("")))
    {
      out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+".value="+searchDcmID+";");
    }
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

    Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    if(dataVector != null && dataVector.size()!=0)
    {
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchPrint\" method=\"post\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"print\">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                  "\" value="+userID+">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                  "\" value=\""+searchDcmID+"\">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                  "\" value=\""+searchbatchDate+"\">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                  "\" value=\""+searchbatchType+"\">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                  "\" value=\""+searchbatchStatusType+"\">");

      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                  "\" value=\"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=middle>Batch ID</td>");
      out.println("<td nowrap align=middle>Distributer Name</td>");
      out.println("<td nowrap align=middle>Batch Type</td>");
      out.println("<td nowrap align=middle>Batch Status</td>");
      out.println("<td nowrap align=middle>Print</td></tr>");
      for(int i = 0; i<dataVector.size(); i++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(i);
        String batchID = newBatchDto.getBatchModel().getBatchId();
        String batchDCM = newBatchDto.getBatchModel().getBatchDCMName();
        String batchType = newBatchDto.getBatchModel().getBatchType();
        String batchStatus = newBatchDto.getBatchModel().getBatchStatus();
        out.println("<TR>");
        out.println("<td align=center nowrap><a href=\"javascript:"+
        "document.BatchPrint."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_DETAILS+"';"+
        "document.BatchPrint."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+".value="+batchID+";"+
        "BatchPrint.submit();\">");
        out.println(batchID+"</a></td>");
        out.println("<td nowrap>"+batchDCM+"</td>");
        out.println("<td nowrap>"+batchType+"</td>");
        out.println("<td nowrap>"+batchStatus+"</td>");
        out.println("<td nowrap align=center>");
        out.println("<input class=button type=\"button\" name=\"print\" value=\"   Print   \" ");
        out.print("onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                  InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS+"&"+
                  ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+"=,"+batchID+"');return false;\">");
        out.print("</td></tr>");
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
      for(int j = 0; j<dataVector.size(); j++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(j);
        batchID +=",";
        batchID += newBatchDto.getBatchModel().getBatchId();
      }
      out.println("<input class=button type=\"button\" name=\"print_all\" value=\" Print All \" ");
      out.print("onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS+"&"+
                ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+"="+batchID+"');return false;\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table>");
      out.println("</Form>");
    }
    else 
    {
      if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION) != null)
      {
        if(((String)request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION)).compareTo(ContractRegistrationInterfaceKey.ACTION_SEARCH_BATCH_DELIVERY)==0)
        {
          out.println("<h4>No results available.</h4>");
        }
      }
    }
  }
%>