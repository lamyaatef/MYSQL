<%
/**
 * This page shows all the connections details currently stored in the database. 
 * It has buttons for editing a certain connection and a button for adding a 
 * new connection.
 * 
 * @author Michael Gameel\\
 */ 
 %>
 
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.deu.*"
         import="com.mobinil.sds.core.system.deu.connection.dao.*"
         import="com.mobinil.sds.core.system.deu.connection.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<HTML><HEAD>                        
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(request.getContextPath());%>/resources/css/Template2.css">

<SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>

<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>Connection Settings</H2>
    </Center>
    
<FORM id=frmConnectionDetails name=frmConnectionDetails action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>

 <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >      
 <input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_CONNECTION_ID);%>" >   
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
                            Connection ID</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Connection&nbsp; Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >IP</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Port Number</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Schema</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >User Name</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Description</TD>
                            <TD class=TableHeader vAlign=top
                              align=middle >Edit</TD>
                            </TR>


<%
  showConnections(request, response, out);
%>
</TBODY></TABLE></TD></TR>
                                     <TR><TD>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnOdd vAlign=top noWrap align=middle colSpan=2>
                <INPUT class=button id=AddConnection type=button value=" Add Connection  " name=AddConnection onclick="document.frmConnectionDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(DEUKeyInterface.ACTION_NEW_CONNECTION);%>';frmConnectionDetails.submit();">
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
 * generates the HTML code for showing the connections encapsulated in the dataHashMap attribute of the request
 * 
 * @param	request	HttpServletRequest for this page
 * @param	response	HttpServletResponse object for this page
 * @param	out	JspWriter Object for this page
 */ 
public void showConnections(HttpServletRequest request, HttpServletResponse response, JspWriter out)
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

        String connectionID= ((ConnectionModel)dataVector.elementAt(i)).getConnectionID();
        String connectionName= ((ConnectionModel)dataVector.elementAt(i)).getConnectionName();
        String connectionIP= ((ConnectionModel)dataVector.elementAt(i)).getServerIP();
        String connectionPort= ((ConnectionModel)dataVector.elementAt(i)).getServerPort();
        String connectionSchema= ((ConnectionModel)dataVector.elementAt(i)).getSchema();
        String connectionUser= ((ConnectionModel)dataVector.elementAt(i)).getUserName();
        String connectionPass=((ConnectionModel)dataVector.elementAt(i)).getPassword();
        String connectionDesc=((ConnectionModel)dataVector.elementAt(i)).getDescription();
        out.print("<TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionID+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionName+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionIP+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionPort+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionSchema+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionUser+"</TD>");
//        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=center><B>"+connectionPass+"</B></TD>");
        if(connectionDesc==null)
          connectionDesc="No available description.";
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+connectionDesc+"</TD>");
        out.print("<TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top align=left>"+
        "<TABLE cellSpacing=0 cellPadding=1 width='100%' bgColor=#ffffff border=0>"+
        "<TBODY><TR><TD class="+DEUKeyInterface.STYLE[i%2]+" vAlign=top noWrap align=center>"+
        "<INPUT class=button type=button id=EditConnection"+i+" value='  Edit  ' name='EditConnection"+i+
        "' onclick=\"document.frmConnectionDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+DEUKeyInterface.ACTION_EDIT_CONNECTION+"'; "
        +"document.frmConnectionDetails."+DEUKeyInterface.HIDDEN_CONNECTION_ID+".value='"+connectionID+"'; "
        +"frmConnectionDetails.submit();\" ></TD></TR></TBODY></TABLE></TD>");
        out.print("</TR>\n");
      }
    }
  }
%>


