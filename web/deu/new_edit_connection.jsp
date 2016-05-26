<%
/**
 *This page is responsible for adding or updating a connection.
 After adding the connection the user is redirected to connection_details.jsp
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
         import="com.mobinil.sds.core.system.deu.connection.dao.*"
         import="com.mobinil.sds.core.system.deu.connection.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<HTML><HEAD>
<META content="Microsoft FrontPage 5.0" name=GENERATOR>
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(request.getContextPath());%>/resources/css/Template2.css">
<SCRIPT language=JavaScript src="<%out.print(request.getContextPath());%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
<script language="JavaScript" src="<%out.print(request.getContextPath());%>/resources/js/gen_validatorv2.js" type="text/javascript"></script>
<META content="Microsoft FrontPage 5.0" name=GENERATOR></HEAD>
<Center>
      <H2>Connection Details</H2>
    </Center>
<BODY leftMargin=0 topMargin=0>

<%
      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      if(dataHashMap !=null)
      {
       String text="Add";
       String action=DEUKeyInterface.ACTION_ADD_CONNECTION;
       String connectionID = (String)dataHashMap.get(DEUKeyInterface.HIDDEN_CONNECTION_ID);
       String connectionName =""; 
       String serverIP = "";
       String serverPort = ""; 
       String schema = "";
       String userName ="";
       String password = "";
       String description = "";


      String ErrorMsg = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);

      if (ErrorMsg!=null && ErrorMsg.compareTo("NULL")!=0)
      {
        out.println("<script> alert('"+ErrorMsg.replace('\n',' ')+"'); </script>");

        connectionName =  (String)dataHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME);; 
        serverIP = (String)dataHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP);
        serverPort = (String)dataHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);
        schema = (String)dataHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA);
        userName =(String)dataHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER);
        description = (String)dataHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);    
      }
      
      
      
      
      
       ConnectionModel newConnectionModel = (ConnectionModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

      if (connectionID!=null)
      {
      text="Update";
        action=DEUKeyInterface.ACTION_UPDATE_CONNECTION;
      }
       
      if(newConnectionModel != null )
      {
        text="Update";
        action=DEUKeyInterface.ACTION_UPDATE_CONNECTION;
        connectionID = newConnectionModel.getConnectionID(); 
        connectionName = newConnectionModel.getConnectionName();
        serverIP = newConnectionModel.getServerIP(); 
        serverPort = newConnectionModel.getServerPort();
        schema = newConnectionModel.getSchema(); 
        userName =newConnectionModel.getUserName(); 
        password = newConnectionModel.getPassword();
        description = newConnectionModel.getDescription(); 
        if((description==null)||(description.equals("null")))
          description="";
      }
    %>
<FORM id=frmAddConn name=frmAddConn action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" value="<%=DEUKeyInterface.ACTION_SHOW_CONNECTION_SETTINGS%>">
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_CONNECTION_ID);%>" value="<%=connectionID%>">

<%
if (ErrorMsg!=null && ErrorMsg.compareTo("NULL")==0)
{
//here i should submit teh above form

out.println("</form>");
out.println("<script>frmAddConn.submit();</script>");
}
%>


<TABLE class=main cellSpacing=0 cellPadding=2 width="100%" align=center
border=0>
  <TBODY>
  <TR>
    <TD>
    
      <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#ffffff
        border=0><TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0 height="151">
              <TBODY>
              <TR class=TableHeader >
                <TD colspan=2 align=left height="16"><%=text%> Connection:</TD>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21"> Connection Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 type="text" size="55" maxlength="30" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME);%>" title='Enter UDDB Connection Name' value="<%=connectionName%>"></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="18" >IP: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="18">
                <input class=input2 maxlength="15" type="text" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP);%>" title='Enter UDDB IP' value="<%=serverIP%>"></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">Port Number: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 maxlength="5" type="text" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);%>" title='Enter UDDB Port Number' ONKEYPRESS="if ((event.keyCode < 48) ||
            (event.keyCode > 57)) event.returnValue = false;" value="<%=serverPort%>"></TD></TR>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="16">Schema: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="16"> 
                <input class=input2 maxlength="50" type="text" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA);%>" title='Enter UDDB Schema Name' value="<%=schema%>"></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="16">User Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="16">
                <input class=input2 type="text" maxlength="30" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER);%>" title='Enter Connection User Name' value="<%=userName%>"></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">Password: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 type="password" maxlength="30" size="55" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PASS);%>" title='Enter UDDB Password'>
                                <%
                if(text.equalsIgnoreCase("Update"))
                  out.print("<BR><I>Leave textbox empty to keep password unchanged</I>");
                %>
                </TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">Confirm Password: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 type="password" maxlength="30" size="55" name="pass2" title='Confirm UDDB Password'></TD>
              </tr>
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="52">Connection Description (Optional):</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="52">
            <TEXTAREA ID="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);%>" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);%>" title="Enter text to describe UDDB" rows="3" cols="61" onpaste="if (document.frmAddConn.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);%>.value.length>=300) event.returnValue = false;" 
            ONINPUT="if (document.frmAddConn.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);%>.value.length>=300) event.returnValue = false;" 
            ONKEYPRESS="if (document.frmAddConn.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);%>.value.length>=300) event.returnValue = false;"><%=description%></TEXTAREA></TD>
              </tr>
              </TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnodd vAlign=top noWrap align=middle colSpan=2>
                
                <input class=button type=submit value="<%=text%> Connection" title='<%=text%> Connection' name="AddConnection" onclick="document.frmAddConn.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';">
                <%
                 if(text.equalsIgnoreCase("Update"))
                {
                  out.print("<input class=button type=submit value='Delete' title='Delete Connection' name='deleteConnection' onclick=\"document.frmAddConn."+InterfaceKey.HASHMAP_KEY_ACTION+".value='deu_delete_connection';\">");
                }
                %>
<!--
                <input type=button class=button value='Cancel' name='Cancel' onclick='history.go(-1);'>
 -->               
                </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></FORM>

<script language="JavaScript" type="text/javascript">

function checkDigit(input) 
{
	var inp = input
	var aStr1 = new String(inp);
	for(i=0; i < aStr1.length; i++)
	{
		var curStr = aStr1.substr(i,1)
		if(!isDigit(curStr)) return false;
	}
	return true;
}

function isDigit(num) 
{
	if ( num.length > 1 ){return false;}
	var string="1234567890";
	if (string.indexOf(num)!=-1){return true;}
	return false;
}

 var frmvalidator = new Validator("frmAddConn");
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME);%>","req","Please enter Connection Name");
 
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP);%>","req","Please enter DB IP");
 
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);%>","req","Please enter DB Port");
 
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA);%>","req","Please enter DB Schema");
 
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER);%>","req","Please enter DB Username");
 
<%if (text.equalsIgnoreCase("Add")){%> 
  frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PASS);%>","req","Please enter DB Password");
<%}%>

frmvalidator.setAddnlValidationFunction("DoCustomValidation"); 

function DoCustomValidation()
{
  var frm = document.forms["frmAddConn"];
  if(frm.pass2.value!=frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PASS);%>.value)
  {
    alert('Password confirmation failed!');
    return false;
  }
  if(isValidIPAddress(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP);%>.value)==false)
  {
    alert('Invalid IP Address');
    return false;
  }
  if ((!checkDigit(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);%>.value))||(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);%>.value>65535)||(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);%>.value<1))
  {
    alert('Invalid PORT Number');
    return false;
  }
}

function isValidIPAddress(ipaddr) {
   var re = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
   if (re.test(ipaddr)) {
      var parts = ipaddr.split(".");
      if (parseInt(parseFloat(parts[0])) == 0) { return false; }
      for (var i=0; i<parts.length; i++) {
         if (parseInt(parseFloat(parts[i])) > 255) { return false; }
      }
      return true;
   } else {
      return false;
   }
}


</script><%}%></BODY></HTML>