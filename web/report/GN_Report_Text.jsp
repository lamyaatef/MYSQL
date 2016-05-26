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
                  import="java.io.*"
%><%
HashMap hmDataX = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
QueryViewerDTO dtoQueryViewerX = (QueryViewerDTO)hmDataX.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 

System.out.println("report exporting text " + dtoQueryViewerX.isFile);

if (dtoQueryViewerX.isFile )
{
	System.out.println("i am in th is file");

	String appName = request.getContextPath();
	 
	 String fileName = dtoQueryViewerX.fileName;

	 System.out.println(fileName);
	 	 
	HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		
		
		
	System.out.println(response.getContentType());
	
	String fileC = request.getRealPath("/report/"+fileName);
	      File f  = new File(fileC);
	      
		//response.setContentType("text/plain");
		 
		 response.setContentType("application/octet-stream");		
		response.addHeader("Content-Disposition", "attachment; filename=\""+ fileName.replaceAll(" ","") + "\"");
		response.setContentLength((int)f.length());
		
		
	      java.io.FileInputStream fileInputStream =
              new java.io.FileInputStream(f);

			int i;
			i=fileInputStream.read();
			while ( i != -1) 
			{
				out.write(i);
				i=fileInputStream.read();
			}
			
			
			fileInputStream.close();
            		try{
            			new File(fileC).delete();
            		}
            		catch(Exception e)
            		{
            			e.printStackTrace();
            		}
            		
	System.out.println("after deleting the file");	
}
else
{
	response.addHeader("Content-Disposition", "attachment; filename="+dtoQueryViewerX.getM_dataViewName()+".txt");
	drawDataViewResults (request, out);
}
%><%!     
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
//  int MAX_ROWS_PER_PAGE = 10000;
  try
  {    
    hmData = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    dtoQueryViewer = (QueryViewerDTO)hmData.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT); 
    colHeaders = dtoQueryViewer.getHeaderInterfaceFields();
    colRows = dtoQueryViewer.getRows();

   strDataViewPreviewHTML = new StringBuffer();
 //  strDataViewPreviewHTML.append(dtoQueryViewer.getM_dataViewName()+"\n");
       
    if (colRows.size() == 0) {
//        strDataViewPreviewHTML.append("No available records.");
        argOut.println("No available records.");
    }
    for (int i = 0; i < colHeaders.size(); i++) 
    {
      strHeaderName = (String)colHeaders.elementAt(i);
//      strDataViewPreviewHTML.append(strHeaderName+",");
        if(i!=0)argOut.print(",");

        argOut.print(strHeaderName);
    }
    argOut.println();
//    strDataViewPreviewHTML.append("\n");

    for (int j = 0; j < colRows.size(); j++) 
    {
      dtoDataRow = (DataRowDTO)colRows.elementAt(j);
      colCells = dtoDataRow.getValues();
      for (int k = 0; k < colCells.size(); k++) 
      {
        strCellValue = (String)colCells.elementAt(k);
        //strDataViewPreviewHTML.append(strCellValue+",");
        if(k!=0)argOut.print(",");
        
        argOut.print(strCellValue);
      }
      //strDataViewPreviewHTML.append("\n");
      argOut.println();
    }
//    argOut.println(strDataViewPreviewHTML.toString());
  }
  catch(Exception objExp)
  {}
}%>