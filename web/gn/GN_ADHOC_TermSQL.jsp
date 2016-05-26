<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import= "com.mobinil.sds.web.interfaces.gn.querybuilder.*"%>
<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/leftmenu.css">
      <SCRIPT language=JavaScript src="../resources/js/utilities.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/lst_add_remove.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/combobox.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/DataViews.js" type=text/javascript></SCRIPT>
      <TITLE>Term SQL</TITLE>
   </HEAD>
   <BODY>
    <form action="" name="formTermSQL" id="formTermSQL" method="post">
      <TABLE cellSpacing=2 cellPadding=4 width="100%" border=0>
        <TBODY>
        <TR>
        <TD class=TableHeader id=tdTermHeader vAlign=top colSpan=9>
          </TD>
        </TR>
          <TR>          
            <TD class=TableHeader vAlign=top>
            <textarea cols=47 rows=18 id="textAreaSQL" name="textAreaSQL"> 
            </textarea>
          </TR>
        </table>
        <Center>
        <table>
          <tr>
            <td colspan=2 align=center>
              <input type="button" id="butOk" name="butOk" value="Ok" class="button" onclick="return butOk_onclick()">
            </td>
          </tr>
        </table>
      </Center>

 <script>
      var objArguments         = window.dialogArguments;
      var objHeader          = objArguments[0];
      var strSQLValue    = objArguments[1];
      var nReadOnly    = objArguments[2];
      tdTermHeader.innerText = objHeader; 
      document.formTermSQL.textAreaSQL.value = strSQLValue;
      if(nReadOnly == 1)
        document.formTermSQL.textAreaSQL.readOnly= true;
     
      function butOk_onclick()
      {
          window.close();
      }

 </script >       