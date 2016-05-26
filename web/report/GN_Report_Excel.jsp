<%@ page contentType="Application/vnd.excel;charset=windows-1256"%><%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.reports.*"
         import="com.mobinil.sds.core.system.gn.reports.dto.*" 
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
%><%
//response.setContentType("Application/vnd.excel");
HashMap hmDataX = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
QueryViewerDTO dtoQueryViewerX = (QueryViewerDTO)hmDataX.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    
response.addHeader("Content-Disposition", "attachment; filename="+dtoQueryViewerX.getM_dataViewName()+".xls");
%>

  

<HTML>
  <HEAD>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
   <TITLE></TITLE>
  </HEAD>
  <BODY>
  
      <%
        drawDataViewResults (request, out);

        	
      %>
  </BODY>
</HTML>

<%!     
/**
 * Draw the dataview preview 
 * @param argRequest HttpServletRequest,argOut JspWriter 
 * @throws Exception if the IO operation failed
 * @return 
 * @see 
 */
 
private void drawDataViewResults(HttpServletRequest argRequest,JspWriter argOut) throws IOException
{
  StringBuffer strDataViewPreviewHTML;
  HashMap hmData                      = new HashMap(100);
  QueryViewerDTO dtoQueryViewer = null;
  DataRowDTO dtoDataRow           = null;
  Vector colRows                         = null;
  Vector colCells                          = null;
  Vector colHeaders                     = null;
  String strHeaderName                = null;
  String strCellValue                    = null;
  int MAX_ROWS_PER_PAGE = 65000;
  try
  {    
    hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    dtoQueryViewer = (QueryViewerDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    colHeaders = dtoQueryViewer.getHeaderInterfaceFields();
    colRows = dtoQueryViewer.getRows();

   strDataViewPreviewHTML = new StringBuffer();
   strDataViewPreviewHTML.append(" <H2><P align=center>" );
   strDataViewPreviewHTML.append(dtoQueryViewer.getM_dataViewName());
   strDataViewPreviewHTML.append(" </P></H2>" );
   
    
   strDataViewPreviewHTML.append("<Table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");

    if (colRows.size() == 0) {
        strDataViewPreviewHTML.append("<TR><TD>");
        strDataViewPreviewHTML.append("<B>No available records.</B>");
        strDataViewPreviewHTML.append("</TD></TR>");
    }
    strDataViewPreviewHTML.append("<TR class=TableHeader>");
    for (int i = 0; i < colHeaders.size(); i++) 
    {
      strHeaderName = (String)colHeaders.elementAt(i);
      strDataViewPreviewHTML.append("<TD>"+strHeaderName+"</TD>");
    }
    strDataViewPreviewHTML.append("</TR>");

    for (int j = 0; j < colRows.size() && j < MAX_ROWS_PER_PAGE; j++) 
    {
      strDataViewPreviewHTML.append("<TR class=TableTextNote>");
      dtoDataRow = (DataRowDTO)colRows.elementAt(j);
      colCells = dtoDataRow.getValues();
      for (int k = 0; k < colCells.size(); k++) 
      {
        strCellValue = (String)colCells.elementAt(k);
        strDataViewPreviewHTML.append("<TD class=TableTextNote>"+strCellValue+"</TD>");
      }
      strDataViewPreviewHTML.append("</TR>");
    }
    strDataViewPreviewHTML.append("</TABLE>");
    argOut.println(strDataViewPreviewHTML.toString());
  }
  catch(Exception objExp)
  {}
}
%>
