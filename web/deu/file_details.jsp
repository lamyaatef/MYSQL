<%
/**
 *This page shows all the files currently stored in the database. 
 It has buttons for editing a certain file and a button for adding a new file. 
 * 
 * @author Michael Gameel
 */ 
 %>
 
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.deu.*"
         import="com.mobinil.sds.core.system.deu.file.dao.*"
         import="com.mobinil.sds.core.system.deu.file.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<HTML><HEAD>                        
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(request.getContextPath());%>/resources/css/Template2.css">
<SCRIPT language=JavaScript src="<%out.print(request.getContextPath());%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>File Settings</H2>
    </Center>

<form name="frmFileDetails" action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_FILE_ID);%>" >
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=1
            width="100%" bgColor=#ffffff border=1>
              <TBODY>
              <TR>
                <TD vAlign=top colSpan=2>
                <TABLE class=main cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
  <TBODY>
  <TR>
    <TD>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0>
                    <TBODY>
                    <TR>
                      <TD class=TableTextColumnEven vAlign=top align=middle>
                        <TABLE class=main style="BORDER-COLLAPSE: collapse"
                        cellSpacing=0 cellPadding=2 width="100%" align=center
                        border=1>
                          <TBODY>
                          <TR>
                            <TD class=TableHeader vAlign=top align=middle >
                            File ID</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >File Path</TD>
                              <TD class=TableHeader vAlign=top
                              align=middle >File Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >File Type</TD>
  <!--                          <TD class=TableTextColumnEven vAlign=top
                              align=middle >Sector Separator</TD>-->
                            <TD class=TableHeader vAlign=top
                              align=middle >Time Stamped</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Encoding</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Description</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Edit</TD>
                            </TR>
                            <%
  showFiles(request, response, out);
%>
</TBODY></TABLE></TD></TR>
                                     <TR><TD>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnOdd vAlign=top noWrap align=middle colSpan=2>
                <INPUT class=button id=AddFile type=button value=" Add File  " name=AddFile onclick="document.frmFileDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(DEUKeyInterface.ACTION_NEW_FILE);%>';frmFileDetails.submit();">
                </TD></TR></TBODY></TABLE>
                    </TD></TR></TBODY></TABLE>
</TD></TR></TBODY></TABLE>
<CENTER>  
<p><A onclick="window.print();" href="#"><IMG src="<%out.print(request.getContextPath());%>/resources/img/printicon.gif" border=0 height="31"></A>
</p>
</CENTER>
                    </TBODY></TABLE></FORM></BODY></HTML>
<%!
/**
 * generates the HTML code for showing the files encapsulated in 
 the dataHashMap attribute of the request
 * 
 * @param	request	HttpServletRequest for this page
 * @param	response	HttpServletResponse object for this page
 * @param	out	JspWriter Object for this page
 */ 
public void showFiles(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
     String msg = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
      if(msg !=null)
      {
      out.println("<script>alert('"+msg+"');</script>");
      }
     
    
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
      
        String fileID = ((FileModel)dataVector.elementAt(i)).getFileID();
        String name = ((FileModel)dataVector.elementAt(i)).getName();
        String fileName = ((FileModel)dataVector.elementAt(i)).getFileName();
        String filePath = ((FileModel)dataVector.elementAt(i)).getFilePath();
        String fileExtension = ((FileModel)dataVector.elementAt(i)).getFileExtension();
        String separator = ((FileModel)dataVector.elementAt(i)).getSeparator();
        String timeStamped =((FileModel)dataVector.elementAt(i)).getTimeStamped();
        String encoding = ((FileModel)dataVector.elementAt(i)).getEncoding();
        String description = ((FileModel)dataVector.elementAt(i)).getDescription();
        out.print("<TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+fileID+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+name+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+filePath+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+fileName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+fileExtension+"</TD>");
        if((separator==null)||(separator.equals("null"))) separator="";
    //    out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center><B>"+separator+"</B></TD>");
        if(timeStamped.equals("1"))
          timeStamped="Y";
        else timeStamped="N"; 
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+timeStamped+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+encoding+"</TD>");
        if((description==null)||(description.equals("null")))
          description="No available description.";
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+description+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+
        "<TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0>"+
        "<TBODY><TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top noWrap align=center>"+
        "<INPUT class=button type=button id=EditFile"+i+" value='  Edit  ' name='EditFile"+i+
        "' onclick=\"document.frmFileDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_EDIT_FILE+"'; "
        +"document.frmFileDetails."+DEUKeyInterface.HIDDEN_FILE_ID+".value='"+fileID+"'; "
        +"frmFileDetails.submit();\" ></TD></TR></TBODY></TABLE></TD>");
        out.print("</TR>\n");
      }
    }
  }
%>


