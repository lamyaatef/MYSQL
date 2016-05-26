<!-- 
/////////////////////// WARNING WARNING WARNING WARNING WARNING WARNING WARNING/////////////////////////////////////////////
THIS PAGE IS NO LONGER USED -- USED INITIALLY DURING THE DATAVIEW BUILDER 
DEVELOPMENT AS A START TESTING PAGE BUT NOW REPLACED BY DATA RETRIEVED FROM THE DATABASE 
AFTER THE INTEGRATION HAS BEEN MADE
/////////////////////// WARNING WARNING WARNING WARNING WARNING WARNING WARNING///////////////////////////////////////////
-->

<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
%>


<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <TITLE>Data View Builder</TITLE>
   </HEAD>
  <BODY bgColor=#ffffff leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
    <form id="formDataViewAction" name="formDataViewAction" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post" target="_parent">
    <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" id="<%=InterfaceKey.HASHMAP_KEY_ACTION%>">
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=0 width=0 border=0>
      <TBODY>
      <TR class=TableHeader>
        <TD vAlign=top width=163>
          <IMG height=16 alt="" src="../resources/img/index_10.gif" width=163>
        </TD>
      </TR>
      <TR class=TableHeader>
        <TD vAlign=top width=163>&nbsp;</TD>
      </TR>
      <TR>
        <TD vAlign=top width=163 background=../resources/img/allmenuleft.gif bgColor=#f6f6f6 height=32>
        
          <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
          <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
               <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
                <TBODY>
                <TR>
                <TD vAlign=top>
                  <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                  <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                  <a href="" target="Right"></a>
                  </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a id="linkNewDataView" name="linkNewDataView" href="javascript:link_onclick('<%=QueryBuilderInterfaceKey.ACTION_INITIALIZE_GENERAL_WIZARD%>','_parent')" >New Data View</a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a id="linkUpdateDataView" name="linkUpdateDataView" href="javascript:link_onclick('<%=QueryBuilderInterfaceKey.ACTION_LIST_GENERAL_DATAVIEWS%>','Right')">Update Data View </a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="Right"></a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
          </TD>
        </TR>
        <TR id=section1>
          <TD vAlign=top bgColor=#f6f6f6 colSpan=7>&nbsp;&nbsp;
          <IMG height=70 alt="" src="../resources/img/index_37.jpg" width=69>
        </TD>
      </TR>
        <TR>
          <TD vAlign=top bgColor=#f6f6f6 colSpan=7>&nbsp;&nbsp;
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#f1f1ed border=0>
            <TBODY>
            <TR>
              <TD vAlign=top>
                <DIV class=option onmouseover="this.style.background='#DEE7EF'" onmouseout="this.style.background='#f5f5f5'">
                <SPAN style="FONT-SIZE: 11px; COLOR: #7c8698; TEXT-DECORATION: none; text-indext: 15px; hover: #7c8698">
                <a href="" target="">Back to main menu</a>
                </SPAN></DIV></TD></TR></TBODY></TABLE>
        </TD>
      </TR>
    </TBODY>
    </TABLE>
    </form>
   </BODY>
<script language="javascript">
function link_onclick(argAction,argTarget)
{
  eval("document.formDataViewAction.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '"+argAction+"';");  
  document.formDataViewAction.target = argTarget;
  document.formDataViewAction.submit();
}
</script>

</HTML>
