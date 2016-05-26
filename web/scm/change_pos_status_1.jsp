<%-- 
    Document   : change_pos_status
    Created on : 31/10/2010, 13:40:53
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
            +SCMInterfaceKey.GET_POS_STATUS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="+userId;
            Vector <POSModel> POSStatusVector=(Vector)dataHashMap.get(SCMInterfaceKey.POS_STATUS_VECTOR);
            String changeStatusAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACTION_CHANGE_POS_STATUS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="+userId;
            String alert=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
            String excelAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.CHANGE_POS_STATUS_SHEET+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="+userId;
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
       <title>Change POS Status</title>

</head>

    <body onkeypress = "if(event.keyCode==13){showStatus.submit();}">
        <div align="center">
        <br>
        <br>
        <h2>Change POS Status</h2>
        <br>
        <br>
        </div>
  <div name="select" align="center">
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
    
     <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Types of request:</font></td>
      <td nowrap align=center>
<select name="inputtype" id="inputtype" onchange="show(this);">
    <option></option>
    <option value="oneByone">One by One</option>
    <option value="byExcel">By Excel Sheet</option>
</select>
      </td>
 </TABLE>

        <%if(alert!=null)
        {%>
        <script>
            alert("<%=alert%>");
        </script>
        <%}%>

        <%if(dataHashMap.get(SCMInterfaceKey.POS_CODE)==null)
            {
                    dataHashMap.put(SCMInterfaceKey.POS_CODE,"");

            }
        %>

</div>
<br>
<br>
<div name="oneByone" id="oneByone" align="center" style="display: none" >
    <form name="showStatus" id="showStatus" action="<%=showStatusAction%>"  method="post" enctype="multipart/form-data">
  <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="80%" border="1" >
    <tr>
    <td class="TableTextNote" nowrap align=center >POS Code:</td>
    <td  nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
    <input type="text" name="<%=SCMInterfaceKey.POS_CODE%>" value="<%=dataHashMap.get(SCMInterfaceKey.POS_CODE)%>">
    </td>
    </tr>
    <tr>
    <td align=center colspan="3">
    <input type="button" class="button" name="Submit" onclick="posStatus();" value="show"></td>
    </tr>
  </table>
    </form>

    
 </div>
    <div name="statusResult" id="statusResult" align="center" style="display:none" >
    <%if (POSStatusVector!=null&&POSStatusVector.size()!=0)
        {

    %>
    <script>
    document.getElementById("inputtype").value="oneByone";
    document.getElementById("oneByone").style.display="";
    </script>
    
       <form name="changeStatus" id="changeStatus" action="<%=changeStatusAction%>"  method="post" enctype="multipart/form-data">
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">POS Name</font></td>
                <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">POS Status</font></td>
               <%if (POSStatusVector.get(0).getPayment_Status()!=null)
                 {%>
                <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Payment Status</font></td>
                <%}%>
            </tr>
            <tr>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=POSStatusVector.get(0).getPOS_NAME()%></td>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=POSStatusVector.get(0).getPOS_Status()%></td>
                <%if (POSStatusVector.get(0).getPayment_Status()!=null)
                 {%>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><%=POSStatusVector.get(0).getPayment_Status()%></td>
                <%}%>
            </tr>
            </table>
            <br>

             <%if (POSStatusVector.get(0).getPayment_Status()!=null&&POSStatusVector.get(0).getPOS_Status().equals("ACTIVE"))
                 {%>
            <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font>Change Status</td>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="radio" name="status" value="pos" onclick="showpos();">POS Status</td>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="radio" name="status"  value="payment" onclick="showpayment();">Payment Status</td>
           </tr>
            </table>
                <%}%>
            

            <br>
           
            <select name="<%=SCMInterfaceKey.POS_STATUS_CHANGE_LIST%>" id="changepos" style="display: none">
                <option value="1">ACTIVE</option>
                <option value="7">Stop Dealing (Closed)</option>
                <option value="18">Pending</option>
                <option value="19">Closed Add & Win</option>
                <option value="20">Add & Win</option>
                <option value="21">Retailer</option>
                <option value="22">Potential</option>
                <option value="23">Rejected</option>
                <option value="24">Not Identified</option>
                <option value="25">Re Accepted</option>
                <option value="26">Waiting List</option>
                <option value="27">Accepted (need First Support)</option>
                <option value="28">Integrated (Temporary Closed)</option>
                <option value="29">Van</option>
                <option value="30">New</option>
                <option value="31">New and Data Entered</option>
            </select>
            <%if (POSStatusVector.get(0).getPayment_Status()!=null&&POSStatusVector.get(0).getPOS_Status().equals("ACTIVE")){%>
              <select name="<%=SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST%>" id="changepayment" style="display: none">
                <option value="1">Eligible</option>
                <option value="2">Non Eligible</option>
                <option value="3">Suspended</option>
                <%}%>
            </select>
            <br>
            <br>
            <label id="reasonlabel" style="display: none" align="center" >Reason</label>
            <br>
            <textarea rows="10" cols="50" align="center" name="<%=SCMInterfaceKey.CHANGE_POS_REASON%>" id="reason" style="display: none"></textarea>
            <br>
            <br>
            <input type="button" class="button" id="posbutton" name="Submit" value="Change POS Status" style="display: none" onclick="submitchangepos();">
            <%if (POSStatusVector.get(0).getPayment_Status()!=null){%>
            <input align="center" type="button" class="button" id="paymentbutton" name="Submit" value="Change Payment Status" style="display: none" onclick="submitchangepayment();">
            <%}%>
       </form>
   
                <script>
                    document.getElementById("statusResult").style.display="";
                </script>
                    <%if (POSStatusVector.get(0).getPayment_Status()==null||!POSStatusVector.get(0).getPOS_Status().equals("ACTIVE")){%>
                    <script>
                    document.getElementById("reasonlabel").style.display="";
                    document.getElementById("reason").style.display="";
                    document.getElementById("changepos").style.display="";
                    document.getElementById("posbutton").style.display="";
                     </script>
                     <%}%>
              

        <%}%>
 </div>
 

 <div name="byExcel" id="byExcel" align="center" style="display: none">

     <form name="excelform" id="excelform" method="post" action="<%=excelAction%>" enctype="multipart/form-data">

    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
    <tr>
    <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:</font></td>
    <td nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
     <% out.println("<input type=hidden name="+SCMInterfaceKey.IMPORT_TABLE+"  value="+SCMInterfaceKey.POS_STATUS_TABLE+">");%>
    <input type="file" name="myfile">
    </tr>
    </table>

    <br>

    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font>Change Status</td>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="radio" name="status" value="pos" onclick="showexcelpos();">POS Status</td>
                <td  nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font><input type="radio" name="status"  value="payment" onclick="showexcelpayment();">Payment Status</td>
            </tr>
     </table>

    <br>

    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="0" >
        <tr>
            <td align="center">
    <select name="<%=SCMInterfaceKey.POS_STATUS_CHANGE_LIST%>" id="excelchangepos" style="display: none">
                <option value="1">ACTIVE</option>
                <option value="7">Stop Dealing (Closed)</option>
                <option value="18">Pending</option>
    </select>

            <select name="<%=SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST%>" id="excelchangepayment" style="display: none">
                        <option value="1">Eligible</option>
                        <option value="2">Non Eligible</option>
                        <option value="3">Suspended</option>

            </select>
             </td>
        </tr>

        <tr>
            <td align="center" style="vertical-align:middle">
            <label align="center" id="excelreasonlabel" style="display: none">Reason</label>
            </td>
        </tr>
        <tr>
            <td align="center" style="vertical-align:middle">
            <textarea  cols="50" rows="10" name="<%=SCMInterfaceKey.CHANGE_POS_REASON%>" id="excelreason" style="display: none"></textarea></td>
        </tr>
    <tr>
        <td align="center" >
        <input type="button" class="button" id="excelposbutton" name="Submit" value="Change POS Status" style="display: none" onclick="submitchangeposexcel();">
        <input type="button" class="button" id="excelpaymentbutton" name="Submit" value="Change Payment Status" style="display: none" onclick="submitchangepaymentexcel();">
        </td>
    </tr>
 </table>
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
        }else if(selectedValue=="")
            {
            document.getElementById("oneByone").style.display="none";
            document.getElementById("byExcel").style.display="none";
            document.getElementById("statusResult").style.display="none";
            }
}
    </script>
    <script>
function showpos()
{
    document.getElementById("reasonlabel").style.display="";
    document.getElementById("reason").style.display="";
    document.getElementById("changepos").style.display="";
    document.getElementById("posbutton").style.display="";
    document.getElementById("paymentbutton").style.display="none";
    document.getElementById("changepayment").style.display="none";

}
    </script>
<script>
function showexcelpos()
{
    document.getElementById("excelreasonlabel").style.display="";
    document.getElementById("excelreason").style.display="";
    document.getElementById("excelchangepos").style.display="";
    document.getElementById("excelposbutton").style.display="";
    document.getElementById("excelpaymentbutton").style.display="none";
    document.getElementById("excelchangepayment").style.display="none";
}
</script>
<script>
function showexcelpayment()
{
   document.getElementById("excelreasonlabel").style.display="";
   document.getElementById("excelreason").style.display="";
   document.getElementById("excelchangepayment").style.display="";
   document.getElementById("excelchangepos").style.display="none";
   document.getElementById("excelpaymentbutton").style.display="";
   document.getElementById("excelposbutton").style.display="none";

}
</script>
<script>
function showpayment()
{
   document.getElementById("reasonlabel").style.display="";
   document.getElementById("reason").style.display="";
   document.getElementById("changepayment").style.display="";
   document.getElementById("changepos").style.display="none";
   document.getElementById("paymentbutton").style.display="";
   document.getElementById("posbutton").style.display="none";
}
</script>
 <script>
 function posStatus()
 {
     if(document.showStatus.<%out.print(SCMInterfaceKey.POS_CODE);%>.value=="")
        {

          alert("POS Code Cannot be Empty");

        }else{

 document.showStatus.action = document.showStatus.action+'&'+'<%out.print(SCMInterfaceKey.POS_CODE+"");%>='+document.showStatus.<%out.print(SCMInterfaceKey.POS_CODE);%>.value
 document.showStatus.submit();
            }
}
</script>
 <script>
function submitchangepos()
{
            if(document.getElementById("reason").value=="")
             {
                  alert("Reason Cannot be Empty");
            }else
               {
                document.changeStatus.action = document.changeStatus.action+'&'+'<%out.print(SCMInterfaceKey.POS_CODE+"");%>='+document.showStatus.<%out.print(SCMInterfaceKey.POS_CODE);%>.value+
            '&'+'<%=SCMInterfaceKey.POS_STATUS_CHANGE_LIST+""%>='+document.getElementById("changepos").value+
            '&'+'<%=SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST+""%>='+"0"
                +'&'+'<%out.print(SCMInterfaceKey.CHANGE_POS_REASON+"");%>='+document.changeStatus.<%out.print(SCMInterfaceKey.CHANGE_POS_REASON);%>.value
                document.changeStatus.submit();
                }
}
function submitchangepayment()
{
    if(document.getElementById("reason").value=="")
             {
                  alert("Reason Cannot be Empty");
            }else
               {
             document.changeStatus.action = document.changeStatus.action+'&'+'<%out.print(SCMInterfaceKey.POS_CODE+"");%>='+document.showStatus.<%out.print(SCMInterfaceKey.POS_CODE);%>.value+
            '&'+'<%=SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST+""%>='+document.getElementById("changepayment").value+
            '&'+'<%=SCMInterfaceKey.POS_STATUS_CHANGE_LIST+""%>='+"0"
            +'&'+'<%out.print(SCMInterfaceKey.CHANGE_POS_REASON+"");%>='+document.changeStatus.<%out.print(SCMInterfaceKey.CHANGE_POS_REASON);%>.value
            document.changeStatus.submit();
                }

}
</script>
<script>
function submitchangeposexcel()
{
    if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }
     if(document.getElementById("excelreason").value=="")
             {
                  alert("Reason Cannot be Empty");
            }else
               {

    document.excelform.action = '<%=excelAction%>'+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value+
            '&'+'<%=SCMInterfaceKey.POS_STATUS_CHANGE_LIST+""%>='+document.getElementById("excelchangepos").value+
            '&'+'<%=SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST+""%>='+"0"
            +'&'+'<%out.print(SCMInterfaceKey.CHANGE_POS_REASON+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.CHANGE_POS_REASON);%>.value

    document.excelform.submit();
                }
}
function submitchangepaymentexcel()
{

    if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }
             if(document.getElementById("excelreason").value=="")
             {
                  alert("Reason Cannot be Empty");
            }else
               {
            document.excelform.action=document.excelform.action+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value+
            '&'+'<%=SCMInterfaceKey.PAYMENT_STATUS_CHANGE_LIST+""%>='+document.getElementById("excelchangepayment").value+
            '&'+'<%=SCMInterfaceKey.POS_STATUS_CHANGE_LIST+""%>='+"0"
            +'&'+'<%out.print(SCMInterfaceKey.CHANGE_POS_REASON+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.CHANGE_POS_REASON);%>.value
            document.excelform.submit();
                }
             }

</script>
</html>
