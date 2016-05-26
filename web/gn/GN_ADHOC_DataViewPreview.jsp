<%@ page buffer="256kb" %>
<%@ page contentType="text/html;charset=windows-1256"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.IOException"
         import="java.io.PrintWriter"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
%>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/Template1.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      <TITLE>Data View Creation</TITLE>

   </HEAD>

   <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
   
   <BODY leftmargin=0 topmargin=0>
   <form id="formDataViewPreview" name="formDataViewPreview" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post" target="_parent">
   <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" id="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="" >    

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
   
    HashMap hmData                             = new HashMap(100);
    QueryViewerDTO dtoQueryViewer              = null;
    DataRowDTO dtoDataRow                      = null;
    Vector colRows                             = null;
    Vector colCells                            = null;
    Vector colHeaders                          = null;
    String strHeaderName                       = null;
    String strCellValue                        = null;
    String strErrorEncountered                 = null;
    String strSQL                              = null;
    try
    {    
        hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
        strErrorEncountered = (String)hmData.get(InterfaceKey.HASHMAP_KEY_MESSAGE); 
        dtoQueryViewer = (QueryViewerDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
        strSQL =  dtoQueryViewer.getSQLCode();
//        strErrorEncountered = dtoQueryViewer.getErrorMessage();
        if(strErrorEncountered != "null" && strErrorEncountered != "" && strErrorEncountered != null)
        {
            if(strErrorEncountered == null)
                strErrorEncountered = " Empty value found while was expecting a full one..";
            strDataViewPreviewHTML = new StringBuffer("<TR><TD class=TableTextNote>SQL: "+ strSQL +"</TD></TR><TR><TD><font size=2 color=red><b><u> The following error has encountered while trying to preview your data view:</b></u></font><br>");
            strDataViewPreviewHTML.append(" <font size=2 color=black><b> - Error Message:" + strErrorEncountered +"</b></font><br><br>");
            strDataViewPreviewHTML.append(" <font size=2 color=black><b>Please fix the problem then try again.</b></font></TD></TR><Tr><TD class=TableTextNote><Center><input type=button class=button name=butBackToWizard id=butBackToWizard value=\"Back To Wizard\" onclick=\"window.open('../gn/GN_ADHOC_DataViewWizard.jsp','Right')\"></Center></Td></tr></Table>");
        }
        else
        {
            colHeaders = dtoQueryViewer.getHeaderInterfaceFields();
            colRows = dtoQueryViewer.getRows();
            strDataViewPreviewHTML = new StringBuffer("<TR><TD class=TableTextNote>SQL: "+ strSQL+  " </TD></TR><TR><TD><Table style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            strDataViewPreviewHTML.append("<TR class=TableHeader>");
    
            for (int i = 0; i < colHeaders.size(); i++) 
            {
                strHeaderName = (String)colHeaders.elementAt(i);
                strDataViewPreviewHTML.append("<TD>"+strHeaderName+"</TD>");
            }
            strDataViewPreviewHTML.append("</TR>");    

            for (int j = 0; j < colRows.size(); j++) 
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
             strDataViewPreviewHTML.append("</TABLE></TD></TR><Tr><TD class=TableTextNote><Center><input type=button class=button name=butBackToWizard id=butBackToWizard value=\"Back To Wizard\" onclick=\"window.open('../gn/GN_ADHOC_DataViewWizard.jsp','Right')\"></Center></Td></tr></TABLE>");
           //  argOut.println("<script language=\"javascript\"> ");
           //  argOut.println(" </script>");
 
        }
        argOut.println(strDataViewPreviewHTML.toString());
     }
     catch(Exception objExp)
     {
      
     }
 }
%>


 <!--  <CENTER>
      <H2>Data View Preview</H2>
    </CENTER>-->
        <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
          <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>
           <tr class=TableHeader>
            <td class=TableHeader><font color=darkblue size=3><center>Data View Preview</center></td>
            <td class=TableHeader></td>
           </tr>
          <tr class=TableHeader>
            <td class=TableHeader>&nbsp;</td>
            <td class=TableHeader>&nbsp;</td>
           </tr>

    <% drawDataViewResults(request,out);%>
    </form>
   </BODY>

</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>

</HTML>
