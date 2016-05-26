<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>

    <%
            String appName = request.getContextPath();            
            String deleteAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACTION_PROCESS_DELETE_STKS;
            
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>Delete STK Number</title>

</head>

    <body onkeypress = "if(event.keyCode==13){stkStatus();}">
        <div align="center">
        <br>
        <br>
        <h2>Delete STK Number</h2>
        <br>
        <br>
        </div>
  <div name="select" align="center">
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">

     <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Type of import STK For Delete</font></td>
      <td nowrap align=center>
<select name="<%=SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY %>" id="<%=SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY %>" onchange="show(this);">
    <option></option>
    <option value="<%=SCMInterfaceKey.CONSTANT_ONE_DELETION %>">One by One</option>
    <option value="<%=SCMInterfaceKey.CONSTANT_FILE_DELETION %>">By Excel Sheet</option>
</select>
      </td>
 </TABLE>
</div>
<br>
<br>
    
<form name="showStatus" id="showStatus" action="<%=deleteAction%>"  method="post" enctype="multipart/form-data">
    <div name="oneByone" id="oneByone" align="center" style="display: none" >
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableTextNote nowrap align=center style="width:300px"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">STK Dial Number</font></td>
                <td  nowrap align=center>
                    <input type="text" name="<%=SCMInterfaceKey.CONTROL_INPUT_TEXT_STK_NUMBER%>" value="">
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="button" class="button" name="Submit" onclick="stkStatus();" value="Delete"></td>
            </tr>
        </table>
    </div>
    <div name="byExcel" id="byExcel" align="center" style="display: none">
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableTextNote style="width:300px" nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:</font></td>
                <td  nowrap align=center>
                    <input type="file" name="myfile">
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="button" class="button" id="excelstkbutton" name="Submit" value="Delete" onclick="submitchangestkexcel();"></td>
            </tr>
        </table>       
    </div>
</form>
        
        
        
    
 
    </body>
    <script>
function show(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
   if(selectedValue=="<%=SCMInterfaceKey.CONSTANT_ONE_DELETION%>")
        {
            document.getElementById("oneByone").style.display="";            
            document.getElementById("byExcel").style.display="none";
        }else if(selectedValue=="<%=SCMInterfaceKey.CONSTANT_FILE_DELETION %>"){
             document.getElementById("oneByone").style.display="none";
            document.getElementById("byExcel").style.display="";                        

        }else if(selectedValue=="")
            {
            document.getElementById("oneByone").style.display="none";
            document.getElementById("byExcel").style.display="none";            
            }
}
    
        function stkStatus()
        {
            var i=isInteger(document.showStatus.<%out.print(SCMInterfaceKey.CONTROL_INPUT_TEXT_STK_NUMBER);%>.value);
            if(i)
            { if(document.showStatus.<%out.print(SCMInterfaceKey.CONTROL_INPUT_TEXT_STK_NUMBER);%>.value=="")
                {
                    
                    alert("STK Number Cannot be Empty");
                    
                }else{
                    document.showStatus.action = '<%=deleteAction%>'+'&'+'<%out.print(SCMInterfaceKey.CONTROL_INPUT_TEXT_STK_NUMBER + "");%>='+document.showStatus.<%out.print(SCMInterfaceKey.CONTROL_INPUT_TEXT_STK_NUMBER);%>.value+
                    '&'+'<%=SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY %>='+document.getElementById("<%=SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY %>").value;
               // alert(document.showStatus.action);
                    document.showStatus.submit();
                }
            }else{
                
                alert("STK Number must be number");
                
            }
        }
 function submitchangestkexcel()
 {     

     if(document.showStatus.myfile.value.lastIndexOf(".xls")==-1&&document.showStatus.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }
     document.showStatus.action = '<%=deleteAction%>'+
         '&'+'<%=SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY %>='+document.getElementById("<%=SCMInterfaceKey.SELECT_FOR_ONE_OR_MANY %>").value;
     //alert(document.showStatus.action);
            document.showStatus.submit();
            
            

 }
 function isInteger(s)
    {   var i;
        for (i = 0; i < s.length; i++)
        {
            // Check that current character is number.
            var c = s.charAt(i);
            if (((c < "0") || (c > "9"))) return false;
        }
        if(s>0)
        {
            return true;
        }
    }
</script>
