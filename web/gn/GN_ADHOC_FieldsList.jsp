<%@ page buffer="2048kb" %>
<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import= "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.web.interfaces.*"%>
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
      <TITLE>Fields List</TITLE>
   </HEAD>


   <BODY>
    <form action="" name="formFieldsList" id="formFieldsList" method="post">
    <input type=hidden id=hiddenFieldID name=hiddenFieldID>

      <TABLE cellSpacing=2 cellPadding=4 width="100%" border=0>
        <TBODY>
        
        <TR>
        <TD class=TableHeader vAlign=top id=tdListName name=tdListName colSpan=9>
          </TD>
        </tr>  
        </table>
        
<script>
      var objArguments          = window.dialogArguments;
      var strFieldType          = objArguments[0];
      var nFieldIndex           = Number(objArguments[1]);
      var objFields             = objArguments[2];

      tdListName.innerText = strFieldType;

      var strDivHTML               = "";
      var strFieldName             = "";
      
      
          
      if(Number(objFields.length) > 0)
      {   
              
          try
          {
              for (var i = 0; i < objFields.length; i++) 
              {
                try
                {
                    if(objFields[i] != "" && objFields[i] != null)  
                    {
                        if(i == 0)
                          strDivHTML += "<Table border=1 width=100%>";
                        if(strFieldType.indexOf('constant')!= -1)
                          strFieldName = objFields[i].m_strConstantName;
                        else
                        if(strFieldType.indexOf('function')!= -1)
                            strFieldName = objFields[i];
                        else
                        if(strFieldType.indexOf('parameter') != -1)
                            strFieldName = objFields[i].m_strParameterName;
                        else
                        if(strFieldType.indexOf('field')!= -1)
                            strFieldName = objFields[i];
                        else
                        if(strFieldType.indexOf('data view')!= -1)
                            strFieldName = objFields[i];    

                        strDivHTML += "<Tr><td style='width:10px' class=TableHeader><input type=radio name=radioField value="+Number(i)+"></td>";
                        strDivHTML += "<td id=tdName"+Number(i)+" name=tdName"+Number(i)+" class=TableHeader>"+strFieldName+"</td></tr>";
                        strFieldName = "";
                    }
                }
                catch(e)
                {
                        if(i == 0)
                          strDivHTML += "<Table border=1 width=100%>";
                        if(strFieldType.indexOf('constant')!= -1)
                          strFieldName = objFields[i].m_strConstantName;
                        else
                        if(strFieldType.indexOf('function')!= -1)
                            strFieldName = objFields[i];
                        else
                        if(strFieldType.indexOf('parameter') != -1)
                            strFieldName = objFields[i].m_strParameterName;
                        else
                        if(strFieldType.indexOf('field')!= -1)
                            strFieldName = objFields[i];
                        else
                        if(strFieldType.indexOf('data view')!= -1)
                            strFieldName = objFields[i];    

                        strDivHTML += "<Tr><td style='width:10px' class=TableHeader><input type=radio name=radioField value="+Number(i)+"></td>";
                        strDivHTML += "<td id=tdName"+Number(i)+" name=tdName"+Number(i)+" class=TableHeader>"+strFieldName+"</td></tr>";
                        strFieldName = "";
                  }
              }     
         }     
         catch(e)
         {
                  
         }
         strDivHTML += "</Table>";    
         document.write(strDivHTML);
         try
         {
            if(objFields.length > 0)
            {
               if(eval("document.formFieldsList.radioField.length") > 0)
               {
                  eval("document.formFieldsList.radioField[0].checked = true;");
                  eval("document.formFieldsList.radioField["+Number(nFieldIndex)+"].checked = true;");
               }  
               else
                  eval("document.formFieldsList.radioField.checked = true;");
            }
          }
          catch(e)
          {
          }
      }      
      function butDone_onclick()
      {
          var arrReturn = new Array();  
          if(objFields.length > 1)
          {
              for (var i = 0; i <= eval("document.formFieldsList.radioField.length"); i++) 
              {
                  if(eval("document.formFieldsList.radioField["+i+"].checked"))
                  {
                      document.formFieldsList.hiddenFieldID.value = eval("Number(document.formFieldsList.radioField["+i+"].value)");
                      break;
                  }
              }
              arrReturn[0] = Number(document.formFieldsList.hiddenFieldID.value);
          }
          else
            arrReturn[0] = 0;
                  
          window.returnValue =arrReturn;
          window.close();
      }
      
      function butCancel_onclick()
      {
          window.close();
      }

 </script >       
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
   