<%-- 
    Document   : change_stk_status_process
    Created on : 2/11/2010, 17:30:53
    Author     : Ahmed Adel
--%>

<%@
page contentType="text/html;charset=windows-1252"
	import="com.mobinil.sds.core.utilities.Utility"
	import="org.apache.commons.fileupload.*" import="java.util.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.scm.*"
	import="com.mobinil.sds.core.system.sa.importdata.*"
	import="com.mobinil.sds.core.system.sa.importdata.model.*"
        import="com.mobinil.sds.core.system.scm.model.*"
	import="com.mobinil.sds.core.system.scm.dao.*" import="java.io.*"%>

<%!public void printToStream(String s, JspWriter out) throws Exception
	{
		out.println(s);
	}%>

<%
	String appName = request.getContextPath();
	DiskFileUpload upload = new DiskFileUpload();
	List items = upload.parseRequest(request);
	String fileUniqueName = "";
        String Slach=System.getProperty("file.separator");
	String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);
	String tableId = (String) request
			.getParameter(SCMInterfaceKey.IMPORT_TABLE);

        String STKStatus=(String) request.getParameter(SCMInterfaceKey.STK_STATUS_CHANGE_LIST);
        String reason=(String) request.getParameter(SCMInterfaceKey.CHANGE_STK_REASON);

	HashMap dataHashMap = (HashMap) request
			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	String strUserID = request
			.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
	String fileNameOnClient = "";

	Iterator itr = items.iterator();

	while (itr.hasNext())
	{
		FileItem item = (FileItem) itr.next();
		// check if the current item is a form field or an uploaded file
		if (item.isFormField())
		{
			// get the name of the field
			String fieldName = item.getFieldName();

			// if it is name, we can set it in request to thank the user
			if (fieldName.equals("name"))
				request.setAttribute("msg",
						"Thank You: " + item.getString());

		} else
		{
			// the item must be an uploaded file save it to disk. Note that there
			// seems to be a bug in item.getName() as it returns the full path on
			// the client's machine for the uploaded file name, instead of the file
			// name only. To overcome that, I have used a workaround using
			// fullFile.getName().
			//	File fullFile  = new File(item.getName());

			try
			{
				fileNameOnClient = item.getName();
				Utility.logger.debug("fileNameOnClient"
						+ fileNameOnClient);
				fileUniqueName = System.currentTimeMillis() + ".xls";
				File savedFile = new File(baseDirectory
						+ fileUniqueName);
				Utility.logger.debug("file " + savedFile);
				item.write(savedFile);
			} catch (Exception e)
			{
				out.println("Error File can not be imported");
				e.printStackTrace();
				return;
			}
		}
	}

	DataImportEngine importEngine = new DataImportEngine();

	DataImportReport importReport = importEngine
			.ImportFileWithRowNumber(baseDirectory + fileUniqueName,
					SCMInterfaceKey.INSERT_OPERATION, tableId);

	Vector report = importReport.getReport();

        Vector<STKStatusCase> refusedSTKs=new Vector();

        int changedSTKs=0;

        int RefusedSTKsNotExist=0;
        
        int errorsflag=0;
        
	int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

        refusedSTKs=STKDAO.beforeSTKExcelChange();

       RefusedSTKsNotExist=refusedSTKs.size();
        
       changedSTKs= STKDAO.STKExcelChange(STKStatus,strUserID,reason);
                    
         
    {

		printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""
				+ appName + "/resources/css/Template1.css\">", out);
		printToStream("<script type=\"text/javascript\">", out);
		printToStream("function Toggle(item) {", out);
		printToStream("obj=document.getElementById(item);", out);
		printToStream("if (obj!=null) {", out);
		printToStream("visible=(obj.style.display!=\"none\")", out);
		printToStream("key=document.getElementById(\"x\" + item);", out);
		printToStream("if (visible) {", out);
		printToStream("obj.style.display=\"none\";", out);
		printToStream("key.innerHTML=\"<img src=\'" + appName
				+ "/resources/img/plus.gif\'>\";", out);
		printToStream("} else {", out);
		printToStream("obj.style.display=\"block\";", out);
		printToStream("key.innerHTML=\"<img src='" + appName
				+ "/resources/img/minus.gif\'>\";", out);
		printToStream("}}}", out);
		printToStream("</script>", out);

		if (report.size() == 0 & RefusedSTKsNotExist ==0 )

		{
			
			printToStream("<h3>", out);
			printToStream("Operation Completed Successfully "
				       +changedSTKs+" POSs were changed", out);
			printToStream("</h3>", out);

		} else{
			if(report.size() != 0)
			{
			printToStream("<h3>", out);
			printToStream("Errors Report", out);
			printToStream("</h3>", out);

			printToStream(
					"<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",
					out);
			printToStream("<TR class=TableHeader>", out);
			printToStream(
					"<td size =20% nowrap align=left>Row Num</td>", out);
			printToStream(
					"<td size= 10% nowrap align=left>Cell Name</td>",
					out);
			printToStream(
					"<td size =10% nowrap align=left>Error Message</td>",
					out);
			printToStream("</tr>", out);

			for (int i = 0; i < report.size(); i++)
			{
				ErrorInfo error = (ErrorInfo) report.get(i);

				printToStream("<tr>", out);
				printToStream(
						"<TD class=TableTextNote align=left size=\"20%\">",
						out);
				if (error.getRowNum() > -1)
					printToStream((error.getRowNum()+1) + "", out);
				printToStream("</td>", out);
				printToStream(
						"<TD class=TableTextNote align=left size=\"20%\">",
						out);
				printToStream(error.getCellName(), out);
				printToStream("</td>", out);
				printToStream(
						"<TD class=TableTextNote align=left size=\"60%\">",
						out);
				printToStream(error.getErrorMsg(), out);
				printToStream("</td>", out);

				printToStream("</tr>", out);

			}

				printToStream("</td>", out);

				printToStream("</tr>", out);

                                printToStream("</TABLE>", out);

                                printToStream("<br>", out);
                                printToStream("<br>", out);
                                errorsflag=1;
                                printToStream("<h3>",out);
				printToStream(report.size()+" Records were refused",out);
				printToStream("</h3>",out);
                                printToStream("<h3>",out);
				printToStream(changedSTKs+" Stks were changed",out);
				printToStream("</h3>",out);
				printToStream("<h3>",out);
				printToStream(RefusedSTKsNotExist+" STKs were refused",out);
				printToStream("</h3>",out);

                             }
                                if (RefusedSTKsNotExist!=0){
				String distLink=PoiWriteExcelFile.ExportExcelSTKChanges(refusedSTKs, baseDirectory);
				dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,distLink);
                                dataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"scm"+Slach+"upload"+Slach);
                                session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);
                                if(errorsflag==0){
                                printToStream("<h3>",out);
				printToStream(report.size()+" Records were refused",out);
				printToStream("</h3>",out);
                                printToStream("<h3>",out);
				printToStream(changedSTKs+" Stks were changed",out);
				printToStream("</h3>",out);
				printToStream("<h3>",out);
				printToStream(RefusedSTKsNotExist+" STKs were refused",out);
				printToStream("</h3>",out);
                                }

                                %>
                                <div name="Excel" id="Excel" align="center">
                                <%out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
                                  %>
                                 <input class="button"  type="submit" name="Submit" id="download" value="Download Excel Sheet" onclick="Sheet();"/>
                                 <%
                                  out.println("</form>");%>
                                  </div>
                                  <%}

              }
                }
%>





  <script>
function Sheet()
{
    document.getElementById("download").disabled=true;
    document.GenerateSheet.submit();


}

</script>