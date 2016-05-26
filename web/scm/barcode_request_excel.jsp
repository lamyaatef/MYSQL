<%@page import="java.sql.Connection"%>
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
        <script>
        function isInteger(s)
    {   var i;
        for (i = 0; i < s.length; i++)
        {
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        if(s>0)
        {
            return true;
        }
    }
        </script>
<%
	String appName = request.getContextPath();
	DiskFileUpload upload = new DiskFileUpload();
	List items = upload.parseRequest(request);
	String fileUniqueName = "";
        String Slach=System.getProperty("file.separator");
	String baseDirectory = request.getRealPath(Slach+"scm"+Slach+"upload"+Slach);
	String tableId = (String) request
			.getParameter(SCMInterfaceKey.IMPORT_TABLE);

        String Rep=(String)request.getParameter(SCMInterfaceKey.BARCODE_REQUEST_REP_ID);


	HashMap dataHashMap = (HashMap) request
			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	String strUserID = request
			.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
	String fileNameOnClient = "";
        String AssignAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACCEPT_BARCODE_REQUEST_EXCEL+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="+strUserID;

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


	Vector errorVector = new Vector();
	Vector statusVector = new Vector();

	DataImportEngine importEngine = new DataImportEngine();

	DataImportReport importReport = importEngine
			.ImportFile(baseDirectory + fileUniqueName,
					SCMInterfaceKey.INSERT_OPERATION, tableId);

	Vector report = importReport.getReport();
	
        Vector<BarCodeCaseModel> RefusedPOS=BarcodeDAO.beforeGetRecievedBarcodeForExcelSheet();

        

        int RefusedRows=RefusedPOS.size();
	Vector<BarCodePOSRequestDataModel>BarCodePOSRequestData=BarcodeDAO.GetRecievedBarcodeForExcelSheet();
       
        if(BarCodePOSRequestData.size() != 0)
			{
			printToStream("<h3>", out);
			printToStream("POSs BarCode Report", out);
                        
			printToStream("</h3>", out);

                        printToStream("<h3>", out);
			printToStream("Remaining BarCode Quantity in Stock is ", out);
                        Connection con = Utility.getConnection();
                        printToStream(Integer.toString(BarcodeDAO.getBarcodeStockRemainingQuantity(con)), out);
			printToStream("</h3>", out);

                        %>
                        <form name="assginbarcodes"  action="<%=AssignAction%>" method="post">
                            <input type="hidden" name="<%=SCMInterfaceKey.BARCODE_REQUEST_REP_ID%>" value="<%=Rep%>" >
                            <%
			printToStream(
					"<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",
					out);
			printToStream("<TR class=TableHeader>", out);
			printToStream(
					"<td size =20% nowrap align=left>POS Code</td>", out);
			printToStream(
					"<td size= 10% nowrap align=left>CR</td>",
					out);
			printToStream(
					"<td size =10% nowrap align=left>SFR</td>",
					out);
			

                        printToStream(
					"<td size =10% nowrap align=left>Balance</td>",
					out);
                        printToStream(
					"<td size =10% nowrap align=left>Request Quantity</td>",
					out);
                        printToStream(
					"<td size =10% nowrap align=left>Remove</td>",
					out);
			printToStream("</tr>", out);
                        

			for (int i = 0; i < BarCodePOSRequestData.size(); i++)
			{
				BarCodePOSRequestDataModel POS = (BarCodePOSRequestDataModel) BarCodePOSRequestData.get(i);

				printToStream("<tr>", out);
				printToStream(
						"<TD class=TableTextNote align=left size=\"20%\">",
						out);
                                printToStream(POS.getPOS_CODE(), out);
                                printToStream("<input type=\"hidden\" name=\"poscode:"+POS.getPOS_CODE()+"\" id=\"poscode:"+POS.getPOS_CODE()+"\" value=\""+POS.getPOS_CODE()+"\""+POS.getPOS_CODE(), out);
				
				printToStream("</td>", out);
				printToStream(
						"<TD class=TableTextNote align=left size=\"20%\">",
						out);
				printToStream(Integer.toString(POS.getCR_Delivered()) , out);
				printToStream("</td>", out);
				printToStream(
						"<TD class=TableTextNote align=left size=\"60%\">",
						out);
				printToStream(Integer.toString(POS.getSFR_Delivered()), out);
				printToStream("</td>", out);

                                printToStream(
						"<TD class=TableTextNote align=left size=\"60%\">",
						out);
				printToStream(Integer.toString(POS.getPOSBalance()), out);
				printToStream("</td>", out);

				printToStream(
						"<TD class=TableTextNote align=left size=\"60%\">",
						out);
				printToStream("<input type=text name=quantity:"+POS.getPOS_CODE()+" id=quantity:"+i+" value="+Integer.toString(POS.getRequsetedQuantity())+">", out);
				printToStream("</td>", out);

                                printToStream(
						"<TD class=TableTextNote align=left size=\"60%\">",
						out);
				printToStream("<input type=checkbox name=check:"+POS.getPOS_CODE()+" id=check:"+i+" value="+POS.getPOS_CODE()+" >", out);
				printToStream("</td>", out);

				printToStream("</tr>", out);

			}


			printToStream("</TABLE>", out);
%>
<br>
<center>
    <input class="button"  type="button" name="Submit"  value="Assgin BarCodes To POSs" onclick="assign(<%=BarCodePOSRequestData.size()%>);">
</center>
<%
                       
                        }

        %>
                        </form>
        <%

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

		if (report.size() == 0 & RefusedRows ==0)

		{
			
			printToStream("<h3>", out);
			printToStream("0 Rows Refused", out);
			printToStream("</h3>", out);

		} else
		{
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

			} if (RefusedRows!=0){

				printToStream("<h3>",out);
				printToStream(report.size()+" Records were refused",out);
				printToStream("</h3>",out);
				printToStream("<h3>",out);
				printToStream(RefusedRows+" POSs were refused",out);
				printToStream("</h3>",out);
				String distLink=PoiWriteExcelFile.exportRefusedPOSBarCodeExcel(RefusedPOS,baseDirectory);
				dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,distLink);
                                dataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"scm"+Slach+"upload"+Slach);



                                session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);
                                %>
                                <div name="Excel" id="Excel" align="center">
                                <%out.println("<form action=\"com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
                                  %>
                                 <input class="button"  type="submit" name="Submit" id="download" value="Download Excel Sheet" onclick="Sheet();"/>
                                 <%
                                  out.println("</form>");%>
                                  </div>
<%

			}

		}


	}
%>
<script>
function Sheet()
{
    document.getElementById("download").disabled=true;
    document.GenerateSheet.submit();


}

function assign(size)
{
   var max=size;
   var sum=0;
   var checkes=0;

    for(var i=0;i<max;i++){


        if(document.getElementById("check:"+i).checked==true)
        {
            checkes++;
            continue;
          
        }
        else{
            if(parseInt(document.getElementById("quantity:"+i).value)<=0){
                alert("Quantitiy must be positive and more than zero");
                return;
            }
            var x=isInteger(document.getElementById("quantity:"+i).value);

                if(!x)
                {
                    alert("The Quantity must be number");
                    return;
                }
            sum=parseInt(sum)+parseInt(document.getElementById("quantity:"+i).value);
            }
        }
    <%Connection con = Utility.getConnection();%>
    if(sum>parseInt('<%=BarcodeDAO.getBarcodeStockRemainingQuantity(con)%>'))
    {
           alert("The Requested Quantity more than stock ");
           return;
    }
   else{
      
         if(checkes==max){
           alert("At least one POS must be unselected");
           return;
         }
         document.assginbarcodes.submit();
        }
}

</script>