<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.sfr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.sfr.sheets.model.*"
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
      SearchForm.submit();
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
  
  public void showSheets(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap ;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Hashtable additonalCollection =(Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    Object []keys =dataHashMap.keySet().toArray();
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SearchForm\" method=\"post\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+SFRInterfaceKey.ACTION_SFR_SEARCH_IMPORT_LOG+"\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("<TR class=TableHeader>");
    out.println("<td nowrap align=center>");
    out.println("Import Log Date");
    out.println("</td>");
    out.println("<TR class=TableHeader>");
    out.println("<td nowrap align=center>");
    out.println("<input class=\"input\"  name=\""+SFRInterfaceKey.INPUT_IMPORT_LOG_DATE+"\" Readonly>");
    out.println("<A onclick=\"showCalendar(SearchForm."+ SFRInterfaceKey.INPUT_IMPORT_LOG_DATE+",'dd/mm/yyyy','Choose date')\" href=\"javascript:void(0)\" >");
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

    String searchbatchDate = "";
    if(additonalCollection != null)
    {
      searchbatchDate = (String)additonalCollection.get(SFRInterfaceKey.INPUT_IMPORT_LOG_DATE);
    }
    out.println("<script>");

    if(searchbatchDate != null && (! searchbatchDate.equals("")))
    {
      out.println("SearchForm."+SFRInterfaceKey.INPUT_IMPORT_LOG_DATE+".value=\""+searchbatchDate+"\";");
    }
    out.println("</script>");

    if(dataVector != null && dataVector.size()!=0)
    {
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchPrint\" method=\"post\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"print\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
      out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_IMPORT_LOG_DATE+
                  "\" value=\""+additonalCollection.get(SFRInterfaceKey.INPUT_IMPORT_LOG_DATE)+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=middle width=\"10%\">Import Log Date</td>");
      out.println("<td nowrap align=middle width=\"20%\">Import Log Time</td>");
      out.println("<td nowrap align=middle>File Name</td>");

      

      for(int i = 0; i<dataVector.size(); i++)
      {
        SFRImportLogModel newImportLogModel = (SFRImportLogModel)dataVector.elementAt(i);
        String importLogDate = newImportLogModel.getExcelImportLogDate();
        String importLogTime = newImportLogModel.getExcelImportLogTime();
        String fileName = newImportLogModel.getFileName();
        out.println("<TR>");
        out.println("<td nowrap>"+importLogDate+"</td>");
        out.println("<td nowrap>"+importLogTime+"</td>");
        out.println("<td nowrap><a href=\""+appName+"/upload/"+fileName+"\">");
        out.println(fileName+"</a></td>");
        out.print("</tr>");
      }
      out.println("</TABLE>");
      out.println("</Form>");
    }
    else 
    {
      if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION) != null)
      {
        if(((String)request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION)).compareTo(SFRInterfaceKey.ACTION_SFR_SEARCH_IMPORT_LOG)==0)
        {
          out.println("<h4>No results available.</h4>");
        }
      }
    }
  }

%>