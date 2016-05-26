<%-- 
    Document   : barcode_request
    Created on : 27/10/2010, 16:45:34
    Author     : Ahmed Adel
--%>
<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>
<%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String confMessage=(String)dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_CONF_MESSAGE);
            String repId=(String)dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_REP_ID);
            String formName="viewBarcodeBalance";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String excelformAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.ACTION_BARCODE_REQUEST_EXCEL+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
            +userId;
            String typeRequest =(String)dataHashMap.get(SCMInterfaceKey.ACTION_BARCODE_REQUEST_TYPE);
            if(typeRequest==null)
            typeRequest="0";
            %>
<html>
    <head>
        <script>
            function viewBalanceForm(){
                    if(document.<%=formName%>.<%=SCMInterfaceKey.BARCODE_REQUEST_POS_CODE %>.value=="" ){
                    alert("Please Enter a POS Code");
                    return;
                }
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_POS_BARCODE_BALANCE%>";
            document.<%=formName%>.submit();
        }
        function submitSaveRequest(){
                quantity=document.<%=formName%>.<%=SCMInterfaceKey.BARCODE_REQUEST_QUANTITY %>.value;
                remainingInStock=document.<%=formName%>.<%=SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE%>.value;
                check=remainingInStock-quantity;
                 if(quantity=="" ){
                    alert("Please Enter a Quantity");
                    return;
                }
                if(isNaN(quantity)){
                    alert("Quantity must be a number");
                    return;
                }
                if(check<0){
                    alert("Quantity exceeds the remaining quantity in the stock");
                    return;
                }
                if(quantity<0){
                    alert("Quantity must be a positive number");
                    return;
                }
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_ACCEPT_POS_BARCODE_REQUEST%>";
            document.<%=formName%>.submit();
        }

function show(selectBox)
{
    var selectedIndex=selectBox.selectedIndex;
    var selectedValue=selectBox.options[selectedIndex].value;
   if(selectedValue=="OnebyOne")
        {
            document.getElementById("OnebyOne").style.display="";
            document.getElementById("Excel").style.display="none";
            document.getElementById("posBalance").style.display="none";

        }else if(selectedValue=="ByMass"){
             document.getElementById("OnebyOne").style.display="none";
            document.getElementById("Excel").style.display="";
            document.getElementById("confMessage").style.display="none";
            document.getElementById("posBalance").style.display="none";
        }else if(selectedValue=="")
            {
               document.getElementById("confMessage").style.display="none";
              document.getElementById("OnebyOne").style.display="none";
            document.getElementById("Excel").style.display="none";
            document.getElementById("posBalance").style.display="none";
        }
}
function ExcelForm()
{
    if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }


       document.excelform.action = document.excelform.action+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value
       +'&'+'<%out.print(SCMInterfaceKey.BARCODE_REQUEST_REP_ID+"");%>='+document.viewBarcodeBalance.<%out.print(SCMInterfaceKey.BARCODE_REQUEST_REP_ID);%>.value

      document.excelform.submit();


}
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>BarCode Request</title>


    </head>
    <body>

        <div align="center">

        <br>
        <br>
        <h2>Barcode Request</h2>
        <br>
        <br>


                <form name="<%=formName%>"  method="post" action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%=InterfaceKey.HASHMAP_KEY_USER_ID%>=<%=userId%>">
                   <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
                    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                    <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Requested By:</font></td>
                        <td  nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
                                <select name="<%=SCMInterfaceKey.BARCODE_REQUEST_REP_ID%>">
                                    <%
                                    Vector<DCMUserDetailModel> reps=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REPS);
                                    for(int i=0;i<reps.size();i++){
                                        DCMUserDetailModel rep=reps.get(i);
                                    %>
                                    <option value="<%=rep.getUserId() %>" <%
                                    if(repId!=null){
                                    int repIdInt=Integer.parseInt(repId);
                                    if(repIdInt==rep.getUserId())
                                        out.print("selected");
                                    }
                                    %>><%=rep.getUserName() %></option>
                                    <%}
                                    %>
                                </select>
                            </font></td>
                            </tr>
                       <TR>
                        <td class=TableTextNote nowrap align=center>
                        Type of Request
                        </td>
                        <td nowrap align=center>
                        <select name="inputtype" id="inputtype" onchange="show(this);">
                        <option></option>
                        <option value="OnebyOne" <%
                                    if(typeRequest.equals("1"))
                                    {
                                        out.print("selected");
                                        
                                    }
                                    %>>One by One</option>
                        <option value="ByMass">By Excel Sheet</option>
                        </select>
                        </td>
                        </TR>
                    </table>
                
<br>
             <div name ="OnebyOne" id="OnebyOne" align="center" style="display:none">

            <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
                                    <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">POS Code</font></td>
                        <td align="center"><input type="text" name="<%=SCMInterfaceKey.BARCODE_REQUEST_POS_CODE%>" value="<%=dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_POS_CODE)%>"> </td>
                        
                                    </tr>
                                    <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Quantity</font></td>
                        <td align="center"><input type="text" name="<%=SCMInterfaceKey.BARCODE_REQUEST_QUANTITY%>" value="<%=dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_QUANTITY)%>"> </td>
                        
                    </tr>
                    <tr>
                        <td colspan="3" align="center"><input class="button" type="button" name="Submit" value="Show" onclick="viewBalanceForm();"></td>

                    </tr>

             </table>

             </div>

                         <%
                                    if(typeRequest.equals("1"))
                                    {%>
                                        <Script>
                                        document.getElementById("OnebyOne").style.display=""
                                        </Script>
                                    <%}
                %>




           <div id="confMessage">
                       <%

                        if(confMessage!=null)
                            {
                            if(confMessage.contains("Invalid")){
                        %>
                        <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:red"><%=confMessage%></font></div>
                            <%}else{
                        %>      <div id="confMessage"><font style="font-size: 14px;font-family: tahoma;line-height: 15px;color:green"><%=confMessage%></font></div>
                        <%
                                }
                            }%>
                            </div>
                            <br>
                           
            <div id="posBalance"

                 <% String message=(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);
                 if(message!=null){
                     out.print("style=\"display:\"\"\"");
                     }else{
                     out.print("style=\"display:\"none\"\"");
                    }
                     %>>

                                    <%
                                    Integer recievedBarcodefromCR=(Integer)dataHashMap.get(SCMInterfaceKey.BARCODE_POS_CR);
                                    Integer recievedBarcodefromSFR=(Integer)dataHashMap.get(SCMInterfaceKey.BARCODE_POS_SFR);
                                    Integer balanceForPOS=(Integer)dataHashMap.get(SCMInterfaceKey.BARCODE_POS_BALANCE);
                                    if(message!=null){

                                    %>
                                    <p><font style="font-size: 10px;font-family: tahoma;line-height: 15px;color:red">Note:</font><font style="font-size: 10px;font-family: tahoma;line-height: 15px">This Report For the Last Two Months and Untill Now</font></p>
                   <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                    <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">CR Delivered:</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">SFR Delievered:</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Balance:</font></td>
                    </tr>
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=recievedBarcodefromCR.intValue()==-1?0:recievedBarcodefromCR.intValue() %></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=recievedBarcodefromSFR.intValue()==-1?0:recievedBarcodefromSFR.intValue() %></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=balanceForPOS.intValue()==-1?0:balanceForPOS.intValue() %></font></td>
                    </tr>
                    <br>
                </table>
               <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                    <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 15px;font-family: tahoma;line-height: 15px"><b>Remaining Barcode In Stock</b></font></td>
                    </tr>
                    <tr>
                        <td align=center ><input type="hidden"  value="<%=dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE)%>" name="<%=SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE %>"><%=dataHashMap.get(SCMInterfaceKey.BARCODE_REQUEST_STOCK_REMAINING_BALANCE)%></td>
                    </tr>
                </table>
                    <br>
                    <br>
                    <center><input type="button" value="Save Request" class="button" onclick="submitSaveRequest();"></center>
                <%}
                %>
            </div>
        </form>
        </div>
<div name="Excel" id="Excel" align="center" style="display: none">
<form name="excelform" action="<%out.print(excelformAction);%> "method="post" enctype="multipart/form-data">
<table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="3" cellspacing="3">
   <input type="hidden" name="hiddenField" align="center">
   <% out.println("<input type=hidden name="+SCMInterfaceKey.IMPORT_TABLE+" value="+SCMInterfaceKey.POS_BARCODE_TABLE+" >");%>

        <br><h3 style="font:font-family:arial;color:black;font-size:15px" align="center">Import Bulk POS & Quantities by Excel File</h3>
        <tr>
            <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:<br>(.xls or .xlsx)</font></td>
            <td align="center"><input type="file" name="myfile"></td>
        </tr>
</table>
    <center>
    <br>
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="ExcelForm();">
    </center>
    </form>
     </div>

    </body>
</html>
