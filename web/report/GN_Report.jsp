<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
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
%>
<HTML>
  <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
  <TITLE></TITLE>
  </HEAD>
  <BODY>


    <script type="text/javascript">

      function toggle(item1)
      {
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          if(divs[i].id != item1)
          {
            divs[i].style.display="none";
          }
        }
        obj=document.getElementById(item1);
        if (obj!=null)
        {
          visible = obj.style.display!="none";
          if (visible) {
            obj.style.display="none";
          } 
          else {
             obj.style.display="";
          }
        }
      }
    </script>

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
  int MAX_ROWS_PER_PAGE = 20;
  try
  {    
    hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    if(hmData.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
    {
      argOut.println("<form name=\"reportForm\" action=\"\" method=\"post\">");

      String strJobId = (String)hmData.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
    
      argOut.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_JOB_ID+"\""+
                  " value=\""+strJobId+"\">"); 

      argOut.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+ReportInterfaceKey.ACTION_PREVIEW_REPORT+"\">");

      argOut.println("</form>");

      argOut.println("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"300\" height=\"100\">");
             argOut.println("<param name=\"movie\" value=\"../resources/img/loading.swf\">");
             argOut.println("<param name=\"quality\" value=\"high\">");
             argOut.println("<embed src=\"../resources/img/loading.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\"300\" height=\"100\"></embed>");
       argOut.println("</object>");
      argOut.println("<script>");
      argOut.println("setTimeout(\"reportForm.submit();\", 5000);");
      argOut.println("</script>");
    }
    else
    {
    dtoQueryViewer = (QueryViewerDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    colHeaders = dtoQueryViewer.getHeaderInterfaceFields();
    colRows = dtoQueryViewer.getRows();

    strDataViewPreviewHTML = new StringBuffer ();
   strDataViewPreviewHTML.append(" <H2><P align=center>" );
   strDataViewPreviewHTML.append(dtoQueryViewer.getM_dataViewName());
   strDataViewPreviewHTML.append(" </P></H2>" );
    
   strDataViewPreviewHTML.append("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" border=0 style=\"WIDTH: 100%\">");
    int x=0;
    if (colRows.size() == 0) {
        strDataViewPreviewHTML.append("<TR><TD>");
        strDataViewPreviewHTML.append("<B>No available records.</B>");
        strDataViewPreviewHTML.append("</TD></TR>");
    }
    for (int j = 0; j < colRows.size(); j++) 
    {
      if(j == (x*MAX_ROWS_PER_PAGE))
      {
        strDataViewPreviewHTML.append("<TR><TD>");
        if((x*MAX_ROWS_PER_PAGE) == 0)
        {
          strDataViewPreviewHTML.append("<div style=\"DISPLAY: \" id="+x+">");
        }
        else
        {
          strDataViewPreviewHTML.append("<div style=\"DISPLAY: none\" id="+x+">");
        }
        strDataViewPreviewHTML.append("<Table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        strDataViewPreviewHTML.append("<TR class=TableHeader>");
    
        for (int i = 0; i < colHeaders.size(); i++) 
        {
          strHeaderName = (String)colHeaders.elementAt(i);
          strDataViewPreviewHTML.append("<TD>"+strHeaderName+"</TD>");
        }
        strDataViewPreviewHTML.append("</TR>");
      }
      strDataViewPreviewHTML.append("<TR class=TableTextNote>");
      dtoDataRow = (DataRowDTO)colRows.elementAt(j);
      colCells = dtoDataRow.getValues();
      for (int k = 0; k < colCells.size(); k++) 
      {
        strCellValue = (String)colCells.elementAt(k);
        strDataViewPreviewHTML.append("<TD class=TableTextNote>"+strCellValue+"</TD>");
      }
      strDataViewPreviewHTML.append("</TR>");
      if((j+1 == ((x*MAX_ROWS_PER_PAGE)+MAX_ROWS_PER_PAGE)) || (colRows.size() == (j+1)))
      {
        //strDataViewPreviewHTML.append("<TR><TD colspan="+colCells.size()+"><P align=center>");
        strDataViewPreviewHTML.append("<TR><TD colspan="+colCells.size()+"><P align=center>");
        if((x*MAX_ROWS_PER_PAGE) == 0)
        {
          strDataViewPreviewHTML.append("<INPUT id=button3 class=button type=button value=\"<<\" name=button3 disabled>");
        }
        else
        {
          strDataViewPreviewHTML.append("<INPUT id=button3 class=button type=button value=\"<<\" name=button3 onclick=\"toggle('"+(x-1)+"');\">");
        }
        strDataViewPreviewHTML.append("&nbsp;");
        if(colRows.size() - (x*MAX_ROWS_PER_PAGE) <= MAX_ROWS_PER_PAGE)
        {
          strDataViewPreviewHTML.append("<INPUT id=button4 type=button value=\">>\" class=button name=button4 disabled>");
        }
        else
        {
          strDataViewPreviewHTML.append("<INPUT id=button4 type=button value=\">>\" class=button name=button4 onclick=\"toggle('"+(x+1)+"');\">");
        }
        strDataViewPreviewHTML.append("</P></TD></TR>");
        strDataViewPreviewHTML.append("</table></div>");
        strDataViewPreviewHTML.append("</TD></TR>");
        x++;
      }
    }
    strDataViewPreviewHTML.append("</TABLE>");
    argOut.println(strDataViewPreviewHTML.toString());
    }
  }
  catch(Exception objExp)
  {}
}
%>
