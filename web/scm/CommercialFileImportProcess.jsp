<%@page import="com.mobinil.sds.core.system.scm.importdata.CommercialImporter"%>
<%@page import="com.mobinil.sds.core.system.scm.importdata.SupervisorImporter"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mobinil.sds.core.system.nomad.dao.NomadFileDAO"%>
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.request.dao.RequestDao"%>
<%@page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.nomad.dao.*"
              import ="java.io.*"              
              %>
    <%!
    public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }
  %>

<%
    
String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/sa/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
System.out.println("table id : "+tableId);
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
System.out.println("operation : "+operation);

String labelId =(String)request.getParameter(AdministrationInterfaceKey.QUERY_LABEL_ID);
System.out.println("label id : "+labelId);
Date tempDate = new Date();
String fileDateStr ="";
String fileNameOnClient="";

Iterator itr = items.iterator();

while(itr.hasNext()) {
	FileItem item = (FileItem) itr.next();          
	// check if the current item is a form field or an uploaded file
	if(item.isFormField()) 
  {            
	// get the name of the field
	String fieldName = item.getFieldName();  
	
	// if it is name, we can set it in request to thank the user
	if(fieldName.equals("name"))
		request.setAttribute("msg", "Thank You: " + item.getString());
		
	} 
  else 
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
        System.out.println("fileNameOnClient : " + fileNameOnClient);
        
        
        
       String string = "Temp_2014_09_19_01_00_00.csv";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
    Matcher m = p.matcher(fileNameOnClient);
    
    if(m.find())
    {
        tempDate = format.parse(m.group());
    }
   System.out.println("fileNameOnClient DATE : " + tempDate);
        
    
        
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");    
    
    fileDateStr = df.format(tempDate);
    System.out.println("fileNameOnClient DATE STRING: " + fileDateStr);
    
    
        Utility.logger.debug("fileNameOnClient : " + fileNameOnClient);
        fileUniqueName = System.currentTimeMillis()+".xls";
        File savedFile = new File(baseDirectory+fileUniqueName);	
        System.out.println("Saved File : "+savedFile);
        Utility.logger.debug("file " + savedFile);
        item.write(savedFile);
    }
    catch (Exception e)
    {
        out.println("Error File can not be imported");
        e.printStackTrace();
        return;
    }
	}
}


HashMap dataHashMap = null;
dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
dataHashMap.put(AdministrationInterfaceKey.TEXT_NOMAD_FILE_NAME,fileNameOnClient);


printToStream("DataImportEngine ", out);
System.out.println("-> import Commercial Sheet");

CommercialImporter superImporterObj = new CommercialImporter(fileDateStr, new Long(0), baseDirectory+fileUniqueName , 12);
  superImporterObj.clean();


out.println("Commercial Data Upload Was Completed");
  printToStream("<h3>",out);
  printToStream("Number of records inserted " + superImporterObj.getNumberOfRowsInserted(),out);
  printToStream("</h3>",out);




%>