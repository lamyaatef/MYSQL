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
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.cr.importlog.model.*"
%>
<%
/**
 * Import_Log_Search.jsp:
 * search for Excel Sheets importes according to DCM ID 
 * and import log date.
 * If Excel Sheets are found they are displayed.
 * You can display the contenets of one Excel Sheet by clicking its link.
 * 
 * showSheets(HttpServletRequest , HttpServletResponse , JspWriter): 
 * Display the search page header and the results after choosing 
 * the seach criteria and pressing the search button
 *
 * printDCM function is used to fill DCM ID combobox.
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
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <%
    showSheets(request, response, out);
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
   * showSheets method:
   * Display the search page header and the results after choosing 
   * the seach criteria and pressing the search button.
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
    HashMap dataHashMap ;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Hashtable additonalCollection =(Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    Object []keys =dataHashMap.keySet().toArray();
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    DCMDto dcmDto= (DCMDto) additonalCollection.get(ContractRegistrationInterfaceKey.OBJ_DCM_DTO);
    out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"DCMSearch\" method=\"post\">");

    String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+ContractRegistrationInterfaceKey.ACTION_SEARCH_IMPORT_LOG+"\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("<TR class=TableHeader>");
    out.println("<td nowrap align=center>");
    out.println("DCM");
    out.println("</TD>");
    out.println("<td nowrap align=center>");
    out.println("Import Log Date");
    out.println("</td>");
    out.println("<TR class=TableHeader>");
    out.println("<td nowrap align=center><select  name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+"\" size=\"1\">");
    printDCM(request,out,dcmDto);
    out.println("</select></TD>");
    out.println("<td nowrap align=center>");
    out.println("<input class=\"input\"  name=\""+ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE+"\" Readonly>");
    out.println("<A onclick=\"showCalendar(DCMSearch."+ ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE+",'dd/mm/yyyy','Choose date')\" href=\"javascript:void(0)\" >");
    out.print("<img border=\"0\" src=\""+appName+"/resources/img/icon_calendar.gif\" width=\"20\" height=\"16\">");
    out.println("</a>");
    out.println("</td>");
    out.println("</TR>");
    out.println("</TABLE>");
    out.println("<br>");
    out.println("<center>");
    out.println("<input class=\"button\" type=\"button\" name=\"search\" value=\"  Search  \" onclick=\"checkbeforSubmit();\">");
    out.println("</center>");
    out.println("</form>");
    Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    String searchDcmID = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
    String searchbatchDate = (String)additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE);

    out.println("<script>");
    out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+".value="
                +searchDcmID+";");
    if(searchbatchDate != null && (! searchbatchDate.equals("")))
    {
      out.println("DCMSearch."+ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE+".value=\""+searchbatchDate+"\";");
    }
    out.println("</script>");

    if(dataVector != null && dataVector.size()!=0)
    {
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchPrint\" method=\"post\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

      //out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"print\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                  "\" value=\""+additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID)+"\">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE+
                  "\" value=\""+additonalCollection.get(ContractRegistrationInterfaceKey.CONTROL_IMPORT_LOG_DATE)+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=middle width=\"10%\">Import Log Date</td>");
      out.println("<td nowrap align=middle width=\"20%\">Import Log Time</td>");
      out.println("<td nowrap align=middle>File Name</td>");
      out.println("<td nowrap align=middle>Download</td></TR>");

      

      for(int i = 0; i<dataVector.size(); i++)
      {
        ImportLogModel newImportLogModel = (ImportLogModel)dataVector.elementAt(i);
        String DCMID = newImportLogModel.getDCMID();
        String importLogDate = newImportLogModel.getExcelImportLogDate();
        String importLogTime = newImportLogModel.getExcelImportLogTime();
        String fileName = newImportLogModel.getFileName();
        out.println("<TR>");
        out.println("<td nowrap>"+importLogDate+"</td>");
        out.println("<td nowrap>"+importLogTime+"</td>");
        out.println("<td nowrap>"+fileName+"</td>");
        out.println("<td nowrap><input type=button class=button name=button value=download onclick=\"excel("+newImportLogModel.getExcelImportLogID()+");\"/><td>");
        out.print("</TR>");
      }
      out.println("</TABLE>");
      out.println("</Form>");
    }
    else 
    {
      if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION) != null)
      {
        if(((String)request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION)).compareTo(ContractRegistrationInterfaceKey.ACTION_SEARCH_IMPORT_LOG)==0)
        {
          out.println("<h4>No results available.</h4>");
        }
      }
    }
  }

%>

<script>
function excel (i)
{
    var id=i;
    document.BatchPrint.action="com.mobinil.sds.web.controller.WebControllerServlet?";
    document.BatchPrint.action=document.BatchPrint.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=ContractRegistrationInterfaceKey.ACTION_DOWNLOAD_EXCEL_LOG%>'+'&'+
     '<%=ContractRegistrationInterfaceKey.DOWNLOAD_EXCEL_LOG_ID%>'+'='+id
      document.BatchPrint.submit();
      //setTimeout("location.reload(true);",4000);
}
</script>