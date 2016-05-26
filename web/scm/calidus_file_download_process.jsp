<%@page import="com.mobinil.sds.core.system.scm.dao.PoiWriteExcelFile"%>
<%@page import="com.mobinil.sds.web.controller.FileServlet"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.*"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"  
           
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"                 
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.core.system.aacm.dao.AuthAgentDAO"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.aacm.*"
              import ="com.mobinil.sds.core.system.aacm.model.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
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
String aFile = request.getParameter("fileName");
System.out.println("a file : "+aFile);


//downloadFile(request, response, out, aFile);


String formAction = appName +"/servlet/com.mobinil.sds.web.controller.FileServlet";
	  
%>
<form action="<%out.print(formAction);%>" name="Myform" method="post">
    
</form>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.zip.ZipOutputStream"%>
<%@page import="java.util.zip.ZipEntry"%>

<%!
void addFile( ZipOutputStream outZip, File f, String name )
{
FileInputStream in = null ;
try
{
// Add ZIP entry to output stream.
outZip.putNextEntry( new ZipEntry( name ) ) ;

in = new FileInputStream( f ) ;

// Transfer bytes from the file to the ZIP file
byte[] buf = new byte[ 4096 ] ;
int len ;
while( ( len = in.read( buf ) ) > 0 )
{
outZip.write( buf, 0, len ) ;
}
}
catch( IOException ex ) { ex.printStackTrace(); }
finally
{
// Complete the entry
try{ outZip.closeEntry() ; } catch( IOException ex ) { }
try{ in.close() ; } catch( IOException ex ) { }
}
}
//ServletOutputStream outStream = null;
%>


<%!
  public void downloadFile(HttpServletRequest request, HttpServletResponse response, JspWriter out, String fName)
  {

try
{
// set the content type and the filename
response.setContentType( "application/zip" ) ;
response.addHeader( "Content-Disposition", "attachment; filename=myzipfile.zip" ) ;

// get a ZipOutputStream, so we can zip our files together
ZipOutputStream outZip = new ZipOutputStream( response.getOutputStream() );

// add some files to the zip...
addFile( outZip, new File("/home/sand/Downloads/SDSDCMExport/src/zip/ParticipantLamya.csv"), "ParticipantLamya.csv" ) ;
addFile( outZip, new File("/home/sand/Downloads/SDSDCMExport/src/zip/PositionLamya.csv"), "PositionLamya.csv" ) ;

// flush the stream, and close it
outZip.flush() ;
outZip.close() ;
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
  }
%>