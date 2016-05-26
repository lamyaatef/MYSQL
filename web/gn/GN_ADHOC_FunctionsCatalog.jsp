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
      <TITLE>Functions Catalog</TITLE>
   </HEAD>

   <div style="display:none" id=<%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>>
   
   <BODY>
    <form action="" name="formFunctionCatalog" id="formFunctionCatalog" method="post">
    <input type=hidden id=hiddenFunctionID name=hiddenFunctionID>
    <input type=hidden id=hiddenFunctionName name=hiddenFunctionName>
      <TABLE cellSpacing=2 cellPadding=4 width="100%" border=0>
        <TBODY>
        <TR>
        <TD class=TableHeader vAlign=top colSpan=9>Functions Catalog
          </TD>
        </TR>
          <TR>          
            <TD class=TableHeader vAlign=top>
            <select  cols=47 style="width:400px" rows=18 id="selectFunctionType" name="selectFunctionType" onChange="displayTypeFunctions()"> 
            <option value=""></option>
            <script>
                  var objArguments             = window.dialogArguments;
                  var nFunctionTypeIndex       = objArguments[0];
                  var nFunctionIndex           = objArguments[1];
                  var objFunctionTypes         = objArguments[2];

                  for(var i=0; i < objFunctionTypes.length;i++)
                  {
                     objFunctionType = objFunctionTypes[i];
                     var objOption = document.createElement("OPTION");
                     objOption.text = objFunctionType.m_strFunctionTypeName;
                     objOption.value = Number(i+1);
                     eval("document.formFunctionCatalog.selectFunctionType.add(objOption);");
                  }
                  if(nFunctionTypeIndex!= "" && nFunctionTypeIndex!= null) 
                  {
                     document.formFunctionCatalog.selectFunctionType.value = nFunctionTypeIndex;
                  }
            </script>
            </select>
          </TR>
        </table>
        
 <script>
      var objFunctionType;
      var strDivHTML               = "";

      for (var i = 0; i < objFunctionTypes.length; i++) 
      {
        objFunctionType = objFunctionTypes[Number(i)];
        strDivHTML += "<Div name=divFunctionType"+Number(i+1)+" id=divFunctionType"+Number(i+1);
        if(Number(nFunctionTypeIndex) == Number(i+1) && nFunctionTypeIndex!= "" && nFunctionTypeIndex!= null) 
        {
             strDivHTML += " style=\"display:block\"";
        }
        else
        {
             strDivHTML += " style=\"display:none\"";
        }
        strDivHTML += ">";

        if(Number(objFunctionType.m_arrFunctions.length) > 0)
        {   
            strDivHTML += "<Table border=1 width=100%>";
            for(var j=0;j<Number(objFunctionType.m_arrFunctions.length);j++)
            {
                 strDivHTML += "<Tr><td class=TableHeader><input type=radio name=radioFunctionDiv"+Number(i+1)+" id=radioFunctionDiv"+Number(i+1)+" value="+j+"></td>";
                 strDivHTML += "<td id=tdName"+Number(i+1)+"Row"+j+" name=tdName"+Number(i+1)+"Row"+j+" class=TableHeader>"+objFunctionType.m_arrFunctions[j].m_strFunctionName+"</td>";
                 strDivHTML += "<td class=TableHeader>"+objFunctionType.m_arrFunctions[j].m_strFunctionDescription+"</td></tr>";
            }
            strDivHTML += "</Table>";
         }
         strDivHTML += "</Div>";
       }
      document.write(strDivHTML);
      if(nFunctionIndex!= "" && nFunctionIndex!= null) 
      {
         if(eval("document.formFunctionCatalog.radioFunctionDiv"+Number(nFunctionTypeIndex)+".length") > 0)
           eval("document.formFunctionCatalog.radioFunctionDiv"+Number(nFunctionTypeIndex)+"["+Number(nFunctionIndex)+"].checked = true;");
         else
           eval("document.formFunctionCatalog.radioFunctionDiv"+Number(nFunctionTypeIndex)+".checked = true;");
      }
 
      function displayTypeFunctions()
      {
          var strDivName = "";
          for (var i= 0; i < objFunctionTypes.length; i++) 
          {
              strDivName = "divFunctionType"+Number(i+1);
              eval(strDivName+".style.display='none'");
          }
          
          if(document.formFunctionCatalog.selectFunctionType.value != "")
          {
              strDivName = "divFunctionType"+Number(document.formFunctionCatalog.selectFunctionType.value);
              eval(strDivName+".style.display='block'");
              if(eval("document.formFunctionCatalog.radioFunctionDiv"+document.formFunctionCatalog.selectFunctionType.value+".length") > 0)
                eval("document.formFunctionCatalog.radioFunctionDiv"+document.formFunctionCatalog.selectFunctionType.value+"["+Number(0)+"].checked = true");
              else
                eval("document.formFunctionCatalog.radioFunctionDiv"+document.formFunctionCatalog.selectFunctionType.value+".checked = true");
          }    
      }
      
      function butDone_onclick()
      {
          var arrReturn = new Array();  
          if(document.formFunctionCatalog.selectFunctionType.value != null && document.formFunctionCatalog.selectFunctionType.value!= "")
          {
              for (var i = 0; i < eval("document.formFunctionCatalog.radioFunctionDiv"+document.formFunctionCatalog.selectFunctionType.value+".length"); i++) 
              {
                  if(eval("document.formFunctionCatalog.radioFunctionDiv"+document.formFunctionCatalog.selectFunctionType.value+"["+i+"].checked"))
                  {
                      document.formFunctionCatalog.hiddenFunctionID.value = eval("document.formFunctionCatalog.radioFunctionDiv"+document.formFunctionCatalog.selectFunctionType.value+"["+i+"].value");
                      break;
                  }
              }
              document.formFunctionCatalog.hiddenFunctionName.value = eval("tdName"+document.formFunctionCatalog.selectFunctionType.value+"Row"+document.formFunctionCatalog.hiddenFunctionID.value+".innerText");
           
              arrReturn[0] = document.formFunctionCatalog.selectFunctionType.value;
              arrReturn[1] = document.formFunctionCatalog.hiddenFunctionID.value;
              arrReturn[2] = document.formFunctionCatalog.hiddenFunctionName.value;
           }   
           else
           {
              arrReturn[0] = 0;
              arrReturn[1] = 0;
              arrReturn[2] = "";
           }
          
          
          
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


</body>
</div>
<script><%out.print(InterfaceKey.HIDDEN_DIV_FOR_PAGE_LOADING);%>.style.display = "block"</script>
</html>