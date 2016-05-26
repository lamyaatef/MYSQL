<%--
    Document   : change_stk_status
    Created on : 1/11/2010, 19:25:36
    Author     : Ahmed Adel
--%>

<%@page import="com.lowagie.text.Document"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>

    <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            String showStatusAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.GET_STK_STATUS;

            String changeStatusAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACTION_STK_CHANGE_STATUS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="+userId;

            String alert=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);

            STKOwnerModel STKStatus= (STKOwnerModel) dataHashMap.get(SCMInterfaceKey.STK_STATUS);

            String excelAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.CHANGE_STK_STATUS_SHEET;
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>Change STK Status</title>

</head>

    <body onkeypress = "if(event.keyCode==13){showStatus.submit();}">
        <div align="center">
        <br>
        <br>
        <h2>Change STK Status</h2>
        <br>
        <br>
        </div>
  <div name="select" align="center">
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">

     <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Type of import STK For Change:</font></td>
      <td nowrap align=center>
<select name="inputtype" id="inputtype" onchange="show(this);">
    <option></option>
    <option value="oneByone">One by One</option>
    <option value="byExcel">By Excel Sheet</option>
</select>
      </td>
 </TABLE>
</div>
        <%if(alert!=null)
        {%>
        <script>
            alert("<%=alert%>");
        </script>
        <%}%>
          <%if(dataHashMap.get(SCMInterfaceKey.STK_DIAL)==null)
            {
                    dataHashMap.put(SCMInterfaceKey.STK_DIAL,"");

            }
        %>

<br>
<br>
<div name="oneByone" id="oneByone" align="center" style="display: none" >
    <form name="showStatus" id="showStatus" action="<%=showStatusAction%>"  method="post" enctype="multipart/form-data">
  <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
    <tr>
    <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">STK Dial Number</font></td>
    <td  nowrap align=center>
    <input type="text" name="<%=SCMInterfaceKey.STK_DIAL %>" value="<%=dataHashMap.get(SCMInterfaceKey.STK_DIAL)%>">
    </td>
    </tr>
    <tr>
        <td colspan="2" align="center"><input type="button" class="button" name="Submit" onclick="stkStatus();" value="show"></td>
    </tr>
  </table>
    </form>


 </div>
    <div name="statusResult" id="statusResult" align="center" style="display:none" >
        <%if (STKStatus!=null&&!STKStatus.equals(""))
        {

    %>
    <script>
    document.getElementById("inputtype").value="oneByone";
    document.getElementById("oneByone").style.display="";
    </script>

       <form name="changeStatus" id="changeStatus" action="<%=changeStatusAction%>"  method="post" enctype="multipart/form-data">
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>

                <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">STK Status</font></td>
                <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">STK Change Status Reason</font></td>
            </tr>
            <tr>

                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=STKStatus.getStkStatusName()%></td>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=STKStatus.getReason()%></td>
            </tr>
            </table>
            <br>
            <br>
            <select name="<%=SCMInterfaceKey.STK_STATUS_CHANGE_LIST%>" id="changestk">
                <option value="4">ACTIVE</option>
                <option value="7">Suspended</option>
                <option value="3">Delete</option>
            </select>
            <br>
            <br>
            <label id="reasonlabel">Reason</label>
            <br>
            <textarea rows="10" cols="50" name="<%=SCMInterfaceKey.CHANGE_STK_REASON%>" id="reason"></textarea>
            <br>
            <br>
            <input type="button" class="button" id="stkbutton" name="Submit" value="Change STK Status"  onclick="submitchangestk();">
  

</form>

                <script>
                    document.getElementById("statusResult").style.display="";
                </script>
    <%}%>
    </div>
 <div name="byExcel" id="byExcel" align="center" style="display: none">

     <form name="excelform" id="excelform" action="<%=excelAction%>" method="post" enctype="multipart/form-data">

    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
    <tr>
    <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:</font></td>
    <td  nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
     <% out.println("<input type=hidden name="+SCMInterfaceKey.IMPORT_TABLE+" value="+SCMInterfaceKey.STK_STATUS_TABLE+">");%>
     <input type="file" name="myfile">
    </tr>
    </table>

    <br>
     <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font>Change Status</td>
                <td  nowrap align=center >
                <select name="<%=SCMInterfaceKey.STK_STATUS_CHANGE_LIST%>" id="changestk">
                <option value="4">ACTIVE</option>
                <option value="7">Suspended</option>
               </select>
                </td>
            </tr>
        </table>
    <br>
    <label id="reasonlabel">Reason</label>
    <br>
    <textarea rows="10" cols="50" name="<%=SCMInterfaceKey.CHANGE_STK_REASON%>" id="excelreason"></textarea>
    <br>
    <br>
    <input type="button" class="button" id="excelstkbutton" name="Submit" value="Change STKs Status" onclick="submitchangestkexcel();">

 </form>
 </div>
    </body>
    <script>
function show(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
   if(selectedValue=="oneByone")
        {
            document.getElementById("oneByone").style.display="";
            document.getElementById("statusResult").style.display="";
            document.getElementById("byExcel").style.display="none";
        }else if(selectedValue=="byExcel"){
             document.getElementById("oneByone").style.display="none";
            document.getElementById("byExcel").style.display="";
            document.getElementById("statusResult").style.display="none";
            document.getElementById("changestk").style.display="";

        }else if(selectedValue=="")
            {
            document.getElementById("oneByone").style.display="none";
            document.getElementById("byExcel").style.display="none";
            document.getElementById("statusResult").style.display="none";
            }
}
    </script>

    <script>
 function stkStatus()
 {
      var i=isInteger(document.showStatus.<%out.print(SCMInterfaceKey.STK_DIAL);%>.value);
     if(i)
     { if(document.showStatus.<%out.print(SCMInterfaceKey.STK_DIAL);%>.value=="")
        {

          alert("STK Number Cannot be Empty");

        }else{
 document.showStatus.action = document.showStatus.action+'&'+'<%out.print(SCMInterfaceKey.STK_DIAL+"");%>='+document.showStatus.<%out.print(SCMInterfaceKey.STK_DIAL);%>.value
 document.showStatus.submit();
 }
    }else{

        alert("STK Number must be number");

    }
}
    </script>
<script>
 function submitchangestk()

 {

            if(document.getElementById("reason").value=="")
             {
                  alert("Reason Cannot be Empty");
                  return false;
            }

            document.changeStatus.action = document.changeStatus.action+'&'+'<%out.print(SCMInterfaceKey.STK_DIAL+"");%>='+document.showStatus.<%out.print(SCMInterfaceKey.STK_DIAL);%>.value+
            '&'+'<%=SCMInterfaceKey.STK_STATUS_CHANGE_LIST+""%>='+document.getElementById("changestk").value+
            '&'+'<%=SCMInterfaceKey.CHANGE_STK_REASON+""%>='+document.getElementById("reason").value
            document.changeStatus.submit();
}
</script>
<script>
 function submitchangestkexcel()
 {
     if(document.getElementById("excelreason").value=="")
             {
                  alert("Reason Cannot be Empty");
                  return false;
         }

     if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }
     document.excelform.action = '<%=excelAction%>'+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value+
            '&'+'<%=SCMInterfaceKey.STK_STATUS_CHANGE_LIST+""%>='+document.getElementById("changestk").value+
            '&'+'<%=SCMInterfaceKey.CHANGE_STK_REASON+""%>='+document.getElementById("excelreason").value

            document.excelform.submit();

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
