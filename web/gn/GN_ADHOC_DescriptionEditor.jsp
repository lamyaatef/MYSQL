<%@ page buffer="1024kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import= "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import="com.mobinil.sds.web.interfaces.*"%>

<SCRIPT>
// Added by Khaled Reda 4/8/2004
function checkQuotes()
{
    var nKeyCode = event.keyCode;
    if( Number(nKeyCode)== 34 )
    {
        alert("You are not allowed to use the (\") character");
        event.keyCode =0;
        return false;
    }    
    if( Number(nKeyCode)== 39 )
    {
       alert("You are not allowed to use the (\') character");
       event.keyCode = 0;
       return false;
    }
    if( Number(nKeyCode)== 13 )
    {
       event.keyCode = 0;
       return false;
    }   
    return true;
}
</SCRIPT>

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
      <TITLE>Adding Description</TITLE>
   </HEAD>

 
   <BODY>
    <form action="" name="formDescription" id="formDescription" method="post">
      <TABLE cellSpacing=2 cellPadding=4 width="100%" border=0>
        <TBODY>
        <TR>
        <TD class=TableHeader id=tdDescriptionHeader vAlign=top colSpan=9>
          </TD>
        </TR>
          <TR>          
            <TD class=TableHeader vAlign=top>
            <textarea cols=47 rows=18 id="textAreaDescription" name="textAreaDescription" onkeypress="checkQuotes()">
            </textarea>
          </TR>
        </table>
        <Center>
        <table>
          <tr>
            <td colspan=2 align=center>
              <input type="button" id="butDone" name="butDone" value="Done" class="button" onclick="return butDone_onclick()">
              <input type="button" id="butCancel" name="butCancel" value="Cancel" class="button" onclick="return butCancel_onclick()">
            </td>
          </tr>
        </table>
      </Center>

 <script>
      var objArguments         = window.dialogArguments;
      var objHeader          = objArguments[0];
      var strDescriptionValue    = objArguments[1];
      var nReadOnly    = objArguments[2];
      tdDescriptionHeader.innerText = objHeader; 
      document.formDescription.textAreaDescription.value = strDescriptionValue;
      if(nReadOnly == 1)
        document.formDescription.textAreaDescription.readOnly= true;
     
      function butDone_onclick()
      {
          var arrReturn = new Array();  
          arrReturn[0] = document.formDescription.textAreaDescription.value;
          window.returnValue =arrReturn;
          window.close();
      }
      
      function butCancel_onclick()
      {
          window.close();
      }

 </script >       