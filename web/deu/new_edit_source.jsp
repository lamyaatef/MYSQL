
<%
/**
 *This page is responsible for adding or updating a source. 
 *After adding the connection the user is redirected to source _details.jsp
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
         import="com.mobinil.sds.core.system.deu.connection.model.*"
         import="com.mobinil.sds.web.interfaces.*"
%>
<HTML><HEAD>
<TITLE>::::DEU ADMIN::::</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
<SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
<SCRIPT language="JavaScript" src="../resources/js/gen_validatorv2.js" type="text/javascript"></SCRIPT>
<SCRIPT language="JavaScript" src="../resources/js/Tokenizer.js" type="text/javascript"></SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0>
    <Center>
      <H2>Source Details</H2>
    </Center>
<%
      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector connections=null;
      connections = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

      if(dataHashMap !=null)
      {
       String text="Add";
       String action=DEUKeyInterface.ACTION_ADD_SOURCE;
       String sourceID = (String)dataHashMap.get(DEUKeyInterface.HIDDEN_CONNECTION_ID);
       String sourceName =""; 
       String sourceSQL = "";
       String connectionName = ""; 
      // String dataviewID = "";
       String description = "";
       SourceModel newSourceModel = (SourceModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newSourceModel != null)
      {
        text="Update";
        action=DEUKeyInterface.ACTION_UPDATE_SOURCE;
        sourceID = newSourceModel.getSourceID(); 
        sourceName = newSourceModel.getSourceName();
        sourceSQL = newSourceModel.getSourceSQL();
        connectionName = newSourceModel.getSourceConnection();
  //      dataviewID = newSourceModel.getSourceDataview(); 
        description = newSourceModel.getSourceDescription(); 
        if((description==null)||(description.equals("null")))
          description="";
      }
    %>
<FORM id=frmAddSource name=frmAddSource action='<%out.print(request.getContextPath());%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method=post>
<input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" >
<%if((connections!=null)&&(connections.size()!=0)){
%>
<input type="hidden" name="<%out.print(DEUKeyInterface.HIDDEN_SOURCE_ID);%>" value="<%=sourceID%>">
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
                <TD colspan=2 align=left height="16"><%=text%> Source:</TD>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21"> Source Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <input class=input2 type="text" maxlength="30" size="50" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_NAME);%>" title='Enter Source Name' value="<%=sourceName%>"></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="18">Source SQL Statement: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="18">
                <TEXTAREA ID="sql" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>" title="Enter SQL (must start with SELECT)" rows="3" cols="61" onpaste="if (document.frmAddSource.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>.value.length>=3000) event.returnValue = false;"
                ONINPUT="if (document.frmAddSource.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>.value.length>=3000) event.returnValue = false;"
            ONKEYPRESS="if (document.frmAddSource.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>.value.length>=3000) event.returnValue = false;"><%=sourceSQL%></TEXTAREA></TD></TR>
              <TR>
                <TD class=TableTextColumnodd vAlign=top noWrap align=left
                width="1%" height="21">Connection Name: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="21">
                <select name="<%out.print(DEUKeyInterface.CONTROL_SELECT_SOURCE_CONNECTION);%>">
            <%
              if(connections!=null)
              {
                for ( int i=0 ; i <connections.size();i++)
                {
                ConnectionModel connection =(ConnectionModel)(connections.get(i));
                  if((connection.getConnectionName()).equals(connectionName))
                    out.print("<OPTION Selected value='"+((ConnectionModel)(connections.get(i))).getConnectionID()+"'>");
                  else 
                    out.print("<OPTION value='"+connection.getConnectionID()+"'>");
                  out.print(connection.getConnectionName());
                  out.print("</option>\n");
                }
              }            %>
    </select></TD></TR>
              <!--<tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=right
                width="1%" height="16">Data View ID: </TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="16"> 
                <input class=input2 type="text" size="30" name="<%//out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_DATA_VIEW);%>" value="<%//out.print(dataviewID);%>" title='Enter Task Run Hour' ONKEYPRESS="if ((event.keyCode < 48) ||
            (event.keyCode > 57)) event.returnValue = false;"></TD>
              </tr>-->
              <tr>
                <TD class=TableTextColumnodd vAlign=top noWrap align=right
                width="1%" height="52">Source Description (Optional):</TD>
                <TD class=TableTextColumnodd vAlign=top noWrap height="52">
    <TEXTAREA ID="desc" name="<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_DESC);%>" title="Enter text to describe Task for later use" rows="3" cols="61" onpaste="if (document.frmAddSource.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_DESC);%>.value.length>=300) event.returnValue = false;" 
    ONINPUT="if (document.frmAddSource.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_DESC);%>.value.length>=300) event.returnValue = false;"
    ONKEYPRESS="if (document.frmAddSource.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_DESC);%>.value.length>=300) event.returnValue = false;"><%=description%></TEXTAREA></TD>
              </tr>
              </TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=1 width="100%" bgColor=#ffffff
            border=0>
              <TBODY>
              <TR class=TableHeader>
                <TD class=TableTextColumnodd vAlign=top noWrap align=middle colSpan=2>
                
                <input class=button type=submit value="<%=text%> Source" title='<%=text%> Source' name="AddSource" onclick="document.frmAddSource.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';">
                 <%
                 if(text.equalsIgnoreCase("Update"))
                {
                  out.print("<input class=button type=submit value='Delete' title='Delete Source' name='deleteSource' onclick=\"document.frmAddSource."+InterfaceKey.HASHMAP_KEY_ACTION+".value='deu_delete_source';\">");
                }
                %>
<!--
                <input type=button class=button value='Cancel' name='Cancel' onclick='history.go(-1);'>
-->
                </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
                </FORM>
                <script language="JavaScript" type="text/javascript">
 var frmvalidator = new Validator("frmAddSource");
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_NAME);%>","req","Please enter Source Name");
 
 frmvalidator.addValidation("<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>","req","Please enter Source SQL");
 
 frmvalidator.setAddnlValidationFunction("DoCustomValidation"); 

 function DoCustomValidation()
{
  var frm = document.forms["frmAddSource"];
  var tokens = frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>.value.tokenize(" ", " ", true);
  
  for(var i=0; i<tokens.length; i++)
  {
    if(tokens[i].toUpperCase()=="INTO")
    {
      alert('Invalid SELECT statement');
      return false;
    }
  }
  if(tokens[0].toUpperCase() != "SELECT")
   {
      alert('Invalid SELECT statement');
      return false;
   }
  if(frm.<%out.print(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);%>.value.length<15)
  {
    alert('Invalid SELECT statement');
    return false;
  } 
  else
    return true;
    }
    
</script>
<%}
else
{
  action=DEUKeyInterface.ACTION_NEW_CONNECTION;
  %>
<SCRIPT>
document.frmAddSource.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%=action%>';
alert('A Connection must be added first!');
document.frmAddSource.submit();
</SCRIPT>
<%}%>
<%}%></BODY></HTML>