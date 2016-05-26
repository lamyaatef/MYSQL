<%
/**
 *This page shows all the sources details currently stored in the database. 
 * It has buttons for editing a certain source and a button for adding 
 * a new source.
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
         import="com.mobinil.sds.core.system.deu.source.dao.*"
         import="com.mobinil.sds.core.system.deu.source.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<HTML><HEAD>                        
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>Source Settings</H2>
    </Center>
<FORM id=frmSourceDetails name=frmSourceDetails action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_SOURCE_ID);%>" >
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=1
            width="100%" bgColor=#ffffff border=1>
              <TBODY>
             
                <TD vAlign=top colSpan=2>
                <TABLE class=main cellSpacing=0 cellPadding=0 width="100%" align=center
border=0>
  <TBODY>
  <TR>
    <TD >
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0>
                    <TBODY>
                    <TR>
                      <TD class=TableTextColumn vAlign=top align=middle >
                        <TABLE class=main style="BORDER-COLLAPSE: collapse"
                        cellSpacing=0 cellPadding=2 width="100%" align=center
                        border=1>
                          <TBODY>
                          <TR>
                            <TD class=TableHeader vAlign=top align=middle >
                            Source ID</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Source Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Connection Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Description</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Edit</TD>
                            </TR>


<%
  showSources(request, response, out);
%>
</TBODY></TABLE></TD></TR>
                                     <TR><TD>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnOdd vAlign=top noWrap align=middle colSpan=2>
                <INPUT class=button id=AddSource type=button value=" Add Source  " name=AddSource onclick="document.frmSourceDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(DEUKeyInterface.ACTION_NEW_SOURCE);%>';frmSourceDetails.submit();">
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
 * generates the HTML code for showing the sources encapsulated in the dataHashMap attribute of the request
 * 
 * @param	request	HttpServletRequest for this page
 * @param	response	HttpServletResponse object for this page
 * @param	out	JspWriter Object for this page
 */ 
public void showSources(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);

    
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(!dataHashMap.isEmpty()){

    String msg = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    if(msg !=null)
      {
      out.println("<script>alert('"+msg+"');</script>");
      }
      
      Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<dataVector.size(); i++){
        String sourceID=((SourceModel)dataVector.elementAt(i)).getSourceID();
        String sourceName=((SourceModel)dataVector.elementAt(i)).getSourceName();
        String sourceSQL=((SourceModel)dataVector.elementAt(i)).getSourceSQL();
        String sourceConnection=((SourceModel)dataVector.elementAt(i)).getSourceConnection();
       // String sourceDataview=((SourceModel)dataVector.elementAt(i)).getSourceDataview();
        String sourceDescription=((SourceModel)dataVector.elementAt(i)).getSourceDescription();
        out.print("<TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+sourceID+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+sourceName+"</TD>");
//        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center><B>"+sourceSQL+"</B></TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+sourceConnection+"</TD>");
     //   out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center><B>"+sourceDataview+"</B></TD>");
        if(sourceDescription==null)
          sourceDescription="No available description.";
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+sourceDescription+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center>"+
        "<TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0>"+
        "<TBODY><TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top noWrap align=center>"+
        "<INPUT class=button type=button id=EditSource"+i+" value='  Edit  ' name='EditSource"+i+
        "' onclick=\"document.frmSourceDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_EDIT_SOURCE+"'; "
        +"document.frmSourceDetails."+DEUKeyInterface.HIDDEN_SOURCE_ID+".value='"+sourceID+"'; "
        +"frmSourceDetails.submit();\" ></TD></TR></TBODY></TABLE></TD>");
        out.print("</TR>\n");
      }
    }
  }
%>